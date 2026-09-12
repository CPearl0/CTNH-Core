package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryMachine extends RecipeElectricMultiblockMachine implements IMachineModifyDrops {

    @CN("基础效率：x%s")
    @EN("Base Productivity: x%s")
    public static Lang sweatShopInfoBasicRate;

    @CN("员工数量：%s")
    @EN("Employee Count: %s")
    public static Lang sweatShopInfoVillagerCount;

    public int CENTRIFUGE_COUNT = 0;
    public int LATHE_COUNT = 0;
    public int CRUSHING_COUNT = 0;
    public int DEPLOYER_COUNT = 0;
    public int BURNER_COUNT = 0;
    public int PRESSOR_COUNT = 0;
    public int MIXER_COUNT = 0;
    public int LASER_COUNT = 0;
    public int BASIN_COUNT = 0;
    public int SAW_COUNT = 0;
    public int TOTAL_COUNT = 0;
    public int VILLAGER_COUNT = 0;
    public double basicRate = 1;
    public int SLOT_COUNT = 10;

    public int length = 0;
    @Persisted
    public final NotifiableItemStackHandler machineStorage;

    public List<String> AvailableMachine = List.of("lathe", "mechanical_press", "centrifuge", "crushing_wheel",
            "deployer", "blaze_burner", "mechanical_mixer", "basin", "laser", "mechanical_saw");

    public FactoryMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = attachTrait(createMachineStorage((byte) 64));
    }

    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 9, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(SLOT_COUNT) {

                    @Override
                    public int getSlotLimit(int slot) {
                        return value;
                    }

                    @Override
                    public void onContentsChanged(int slot) {
                        var Machine = getMachineStorageItem();
                        updateMachineCount(Machine);
                        super.onContentsChanged(slot);
                    }
                }).setFilter(itemStack -> AvailableMachine.contains(itemStack.getItem().toString()));
    }

    @Override
    public void onStructureFormed() {
        int[] formedRepetitionCount = getMultiblockState().getMatchContext().get("formedRepetitionCount");
        if (formedRepetitionCount != null) {
            Arrays.stream(formedRepetitionCount).max().ifPresent(m -> length = m);
        }

        super.onStructureFormed();
    }

    @Override
    public void onDrops(List<ItemStack> drops) {
        clearInventory(machineStorage.storage);
    }

    @Override
    public @NotNull Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            for (int i = 0; i < SLOT_COUNT / 2; i++) {
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i, size.width - 30 - 18 * i, size.height - 30, true,
                                true)
                                .setBackground(GuiTextures.SLOT));
            }
            for (int i = 0; i < SLOT_COUNT / 2; i++) {
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i + 5, size.width - 30 - 18 * i, size.height - 48, true,
                                true)
                                .setBackground(GuiTextures.SLOT));
            }
        }
        return widget;
    }

    @Override
    public boolean keepSubscribing() {
        return true;
    }

    public List<ItemStack> getMachineStorageItem() {
        var ItemList = new ArrayList<ItemStack>();
        for (int i = 0; i < 10; i++) {
            ItemList.add(machineStorage.getStackInSlot(i));
        }
        return ItemList;
    }

    public void updateMachineCount(List<ItemStack> itemlist) {
        CENTRIFUGE_COUNT = 0;
        LATHE_COUNT = 0;
        CRUSHING_COUNT = 0;
        DEPLOYER_COUNT = 0;
        BURNER_COUNT = 0;
        PRESSOR_COUNT = 0;
        MIXER_COUNT = 0;
        LASER_COUNT = 0;
        BASIN_COUNT = 0;
        SAW_COUNT = 0;
        TOTAL_COUNT = 0;
        for (ItemStack itemStack : itemlist) {
            switch (itemStack.getItem().toString()) {
                case "centrifuge" -> CENTRIFUGE_COUNT = CENTRIFUGE_COUNT + itemStack.getCount();
                case "lathe" -> LATHE_COUNT = LATHE_COUNT + itemStack.getCount();
                case "crushing_wheel" -> CRUSHING_COUNT = CRUSHING_COUNT + itemStack.getCount();
                case "deployer" -> DEPLOYER_COUNT = DEPLOYER_COUNT + itemStack.getCount();
                case "blaze_burner" -> BURNER_COUNT = BURNER_COUNT + itemStack.getCount();
                case "mechanical_press" -> PRESSOR_COUNT = PRESSOR_COUNT + itemStack.getCount();
                case "mechanical_mixer" -> MIXER_COUNT = MIXER_COUNT + itemStack.getCount();
                case "laser" -> LASER_COUNT = LASER_COUNT + itemStack.getCount();
                case "basin" -> BASIN_COUNT = BASIN_COUNT + itemStack.getCount();
                case "mechanical_saw" -> SAW_COUNT = SAW_COUNT + itemStack.getCount();
            }
        }
        TOTAL_COUNT = CENTRIFUGE_COUNT + CRUSHING_COUNT + LASER_COUNT + LATHE_COUNT + DEPLOYER_COUNT + BURNER_COUNT +
                PRESSOR_COUNT + MIXER_COUNT + BASIN_COUNT + SAW_COUNT;
    }

    @CN("没有员工")
    @EN("No Employee")
    static Lang no_villager;

    @CN("缺少所需的生产资料")
    @EN("No Correct Machine")
    static Lang no_suitable_machine;

    @CN("员工饥肠辘辘！")
    @EN("Employees are hungry!")
    static Lang no_meal;

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        updateVillagerCount();
        updateBasicRate();
        if (VILLAGER_COUNT == 0) {
            return no_villager.translate();
        } else {
            if (recipe.recipeType.equals(GTRecipeTypes.CENTRIFUGE_RECIPES) && CENTRIFUGE_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.LATHE_RECIPES) && LATHE_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.MACERATOR_RECIPES) && CRUSHING_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.EXTRACTOR_RECIPES) && BURNER_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.BENDER_RECIPES) && PRESSOR_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.MIXER_RECIPES) && MIXER_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.WIREMILL_RECIPES) && SAW_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES) && LASER_COUNT == 0) {
                return no_suitable_machine.translate();
            } else if (recipe.recipeType.equals(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES) && BASIN_COUNT == 0) {
                return no_suitable_machine.translate();
            }
        }
        if (!MachineUtils.canInputItems(this, CTNHItems.SIMPLE_NUTRITIOUS_MEAL.asStack(VILLAGER_COUNT))) {
            return no_meal.translate();
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            updateVillagerCount();
            if (getOffsetTimer() % 100 == 0) {
                if (!MachineUtils.inputItems(this, CTNHItems.SIMPLE_NUTRITIOUS_MEAL.asStack(VILLAGER_COUNT))) {
                    getRecipeLogic().setProgress(0);
                }
            }
        }
        return super.onWorking();
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof FactoryMachine fmachine) {
            var recipeType = recipe.recipeType;
            var recipeTier = recipe.tier;
            if (fmachine.basicRate == 0) {
                return no_suitable_machine.translate();
            }
            recipe.multiplyDuration(2 / fmachine.basicRate * Math.pow(recipeTier, 2));
            if (recipeType.equals(GTRecipeTypes.CENTRIFUGE_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.CENTRIFUGE_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.LATHE_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.LATHE_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.MACERATOR_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.CRUSHING_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.EXTRACTOR_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.BURNER_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.BENDER_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.PRESSOR_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.MIXER_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.MIXER_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.WIREMILL_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.SAW_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.LASER_COUNT));
            } else if (recipeType.equals(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES)) {
                return CTNHRecipeModifiers.accurateParallel(machine, group, recipe,
                        (int) Math.sqrt(fmachine.BASIN_COUNT));
            }
            throw new IllegalStateException("Unexpected value: " + recipeType);
        }
        return null;
    }

    public double calculateDiversity() {
        double diversity = 1.5 - Math.pow((double) CRUSHING_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) MIXER_COUNT / TOTAL_COUNT, 2) - Math.pow((double) LATHE_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) BURNER_COUNT / TOTAL_COUNT, 2) - Math.pow((double) PRESSOR_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) CENTRIFUGE_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) DEPLOYER_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) LASER_COUNT / TOTAL_COUNT, 2) - Math.pow((double) BASIN_COUNT / TOTAL_COUNT, 2) -
                Math.pow((double) SAW_COUNT / TOTAL_COUNT, 2);
        return diversity;
    }

    public void updateVillagerCount() {
        var level = getLevel();
        var pos = getPos();
        var facing = getFrontFacing();
        AABB area;
        switch (facing) {
            case NORTH -> area = AABB.of(BoundingBox.fromCorners(pos.offset(-2, -1, 0), pos.offset(2, 2, length + 2)));
            case SOUTH -> area = AABB.of(BoundingBox.fromCorners(pos.offset(-2, -1, 0), pos.offset(2, 2, -length - 2)));
            case EAST -> area = AABB.of(BoundingBox.fromCorners(pos.offset(0, -1, -2), pos.offset(-length - 2, 2, 2)));
            case WEST -> area = AABB.of(BoundingBox.fromCorners(pos.offset(0, -1, -2), pos.offset(length + 2, 2, 2)));
            default -> throw new IllegalStateException("Unexpected value: ");
        }
        if (level != null) {
            var entities = level.getEntities(null, area);
            VILLAGER_COUNT = (int) entities.stream().filter(entity -> entity instanceof Villager).count();
        }
    }

    public void updateBasicRate() {
        updateMachineCount(getMachineStorageItem());
        basicRate = (double) Math.min(VILLAGER_COUNT, (length - 2) / 2 + 4) / 2 * calculateDiversity() *
                (1 + Math.sqrt(DEPLOYER_COUNT) / 4);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        updateVillagerCount();
        updateBasicRate();
        super.addDisplayText(textList);
        textList.add(sweatShopInfoVillagerCount.translate(VILLAGER_COUNT));
        textList.add(
                sweatShopInfoBasicRate.translate(String.format("%.2f", basicRate)));
    }
}
