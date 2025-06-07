package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NanoscaleTriboelectricGenerator extends WorkableElectricMultiblockMachine implements ITieredMachine {
    @Persisted
    public final NotifiableItemStackHandler machineStorage;
    @Persisted public int parallel=1024;
    @Persisted public double effencicy=1.0;
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            NanoscaleTriboelectricGenerator.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public NanoscaleTriboelectricGenerator(IMachineBlockEntity holder){
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
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

    public ItemStack getMachineStorageItem() {
        return machineStorage.getStackInSlot(0);
    }
    public static int eutgetParallelAmount(MetaMachine machine, GTRecipe recipe, int parallelLimit) {
        if (parallelLimit <= 1) return parallelLimit;
        if (!(machine instanceof IRecipeLogicMachine rlm)) return 1;
        // First check if we are limited by recipe inputs. This can short circuit a lot of consecutive checking
        int maxInputMultiplier = eutlimitByInput(rlm, recipe, parallelLimit);
        if (maxInputMultiplier == 0) return 0;

        return maxInputMultiplier;
    }

    public static int eutlimitByInput(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelLimit) {
        IntSet multipliers = new IntOpenHashSet();

        // non-tick inputs.
        for (RecipeCapability<?> cap : recipe.inputs.keySet()) {
            if (cap.doMatchInRecipe()) {
                // Find the maximum number of recipes that can be performed from the contents of the input inventories
                multipliers.add(cap.getMaxParallelRatio(holder, recipe, parallelLimit));
            }
        }

        // tick inputs.
        for (RecipeCapability<?> cap : recipe.tickInputs.keySet()) {
            if (cap.doMatchInRecipe()) {
                // Find the maximum number of recipes that can be performed from the contents of the input inventories
                multipliers.add(cap.getMaxParallelRatio(holder, recipe, parallelLimit));
            }
        }
        if (multipliers.intStream().allMatch(value -> value == Integer.MAX_VALUE)) {
            return 0;
        }
        // Find the maximum number of recipes that can be performed from all available inputs
        return multipliers.intStream().min().orElse(0);
    }

    public void consumeItem() { machineStorage.extractItem(0, 1,false); }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof NanoscaleTriboelectricGenerator zmachine) {
            double effencicy=0.8;
            var random=Math.random();
            var maxParallel = eutgetParallelAmount(zmachine,recipe,1024);
            if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Rubber).get()))
            {
                effencicy=1.0;
                if(random<(double)(maxParallel)/512)zmachine.consumeItem();
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Polyethylene).get()))
            {
                effencicy=1.6;
                if(random<(double)(maxParallel)/1024)zmachine.consumeItem();
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.SiliconeRubber).get()))
            {
                effencicy=2.4;
                if(random<(double)(maxParallel)/4096)zmachine.consumeItem();
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.StyreneButadieneRubber).get()))
            {
                effencicy=3.2;
                if(random<(double)(maxParallel)/65536)zmachine.consumeItem();
            }
            else if(zmachine.getMachineStorageItem().getItem().equals(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate,GTMaterials.Polybenzimidazole).get()))
            {
                effencicy=5.0;
                if(random<(double)(maxParallel)/1048576)zmachine.consumeItem();
            }

            if(maxParallel<=0)return ModifierFunction.NULL;
            return ModifierFunction.builder()
                    .inputModifier(ContentModifier.multiplier(maxParallel))
                    .outputModifier(ContentModifier.multiplier(maxParallel))
                    .durationMultiplier(2*Math.sqrt(maxParallel))
                    .eutMultiplier(maxParallel*(1+maxParallel*0.002)*effencicy)
                    .build();
        }
        return ModifierFunction.NULL;
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}
