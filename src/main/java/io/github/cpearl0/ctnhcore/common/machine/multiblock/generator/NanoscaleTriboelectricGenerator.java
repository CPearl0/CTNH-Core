package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class NanoscaleTriboelectricGenerator extends RecipeElectricMultiblockMachine implements ITieredMachine {

    @Persisted
    public final NotifiableItemStackHandler machineStorage;
    @Persisted
    public int parallel = 2048;
    @Persisted
    public double effencicy = 1.0;
    @Persisted
    public boolean is_consume = false;

    public NanoscaleTriboelectricGenerator(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = attachTrait(createMachineStorage((byte) 64));
    }

    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 1, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(1) {

                    @Override
                    public int getSlotLimit(int slot) {
                        return value;
                    }
                });
    }

    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            group.addWidget(
                    new SlotWidget(machineStorage.storage, 0, size.width - 30, size.height - 30, true, true)
                            .setBackground(GuiTextures.SLOT));
        }
        return widget;
    }

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        if (is_consume) {
            consumeItem();
        }
        is_consume = false;
        return super.beforeWorking(recipe);
    }

    public ItemStack getMachineStorageItem() {
        return machineStorage.getStackInSlot(0);
    }

    public static int eutgetParallelAmount(MetaMachine machine, GTRecipe recipe, int parallelLimit) {
        if (parallelLimit <= 1) return parallelLimit;
        if (!(machine instanceof IRecipeLogicMachine rlm)) return 1;
        // First check if we are limited by recipe inputs. This can short circuit a lot of consecutive checking
        int maxInputMultiplier = eutlimitByInput(rlm.getRecipeHandlerGroups().get(0), recipe, parallelLimit);
        if (maxInputMultiplier == 0) return 0;

        return maxInputMultiplier;
    }

    public static int eutlimitByInput(RecipeHandlerGroup holder, GTRecipe recipe, int parallelLimit) {
        IntSet multipliers = new IntOpenHashSet();

        // non-tick inputs.
        for (RecipeCapability<?> cap : recipe.inputs.keySet()) {
            if (cap.doMatchInRecipe()) {
                // Find the maximum number of recipes that can be performed from the contents of the input inventories
                multipliers.add(cap.getMaxParallelByInput(holder, recipe, parallelLimit, false));
            }
        }

        // tick inputs.
        for (RecipeCapability<?> cap : recipe.tickInputs.keySet()) {
            if (cap.doMatchInRecipe()) {
                // Find the maximum number of recipes that can be performed from the contents of the input inventories
                multipliers.add(cap.getMaxParallelByInput(holder, recipe, parallelLimit, true));
            }
        }
        if (multipliers.intStream().allMatch(value -> value == Integer.MAX_VALUE)) {
            return 0;
        }
        // Find the maximum number of recipes that can be performed from all available inputs
        return multipliers.intStream().min().orElse(0);
    }

    public void consumeItem() {
        machineStorage.extractItem(0, 1, false);
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof NanoscaleTriboelectricGenerator zmachine) {

            // 定义材料效率配置（材料 -> {效率, 消耗概率分母}）
            Map<Item, double[]> materialEfficiencyMap = Map.of(
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.Rubber).get(),
                    new double[] { 1.0, 512 },
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.Polyethylene).get(),
                    new double[] { 1.6, 1024 },
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.SiliconeRubber).get(),
                    new double[] { 2.4, 4096 },
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.Polytetrafluoroethylene).get(),
                    new double[] { 3.2, 65536 },
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.StyreneButadieneRubber).get(),
                    new double[] { 4.6, 131070 },
                    GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, GTMaterials.Polybenzimidazole).get(),
                    new double[] { 5.0, 1048576 });

            // 默认效率 0.8
            double efficiency = 0.8;
            int maxParallel = eutgetParallelAmount(zmachine, recipe, 1024);
            Item storageItem = zmachine.getMachineStorageItem().getItem();

            // 检查材料是否在配置中，并更新效率
            if (materialEfficiencyMap.containsKey(storageItem)) {
                double[] config = materialEfficiencyMap.get(storageItem);
                efficiency = config[0];
                if (Math.random() < (double) maxParallel / config[1]) {
                    zmachine.is_consume = true;
                }
            }

            if (maxParallel <= 0) {
                return CTNHCommonTooltips.recipeModifierInsufficientInput.translate();
            }

            recipe.multiplyAllContents(maxParallel);
            recipe.multiplyDuration(Math.sqrt(maxParallel));
            recipe.multiplyEUt((1 + maxParallel * 0.02) * efficiency);
            recipe.parallels *= maxParallel;
            return null;
        }
        return RecipeModifier.nullWrongType(NanoscaleTriboelectricGenerator.class, machine);
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }
}
