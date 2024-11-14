package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FactoryMachine extends WorkableElectricMultiblockMachine implements IMachineModifyDrops {
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

    public int length = 0;
    @Persisted
    public final NotifiableItemStackHandler machineStorage;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FactoryMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public List<String> AvailableMachine = List.of("lathe","mechanical_press","centrifuge","crushing_wheel","deployer","blaze_burner","mechanical_mixer","basin","laser","mechanical_saw");
    public FactoryMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
        this.length = getDefinition().getPatternFactory().get().getFormedRepetitionCount().length;
    }
    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 9, IO.NONE, IO.BOTH, slots -> new ItemStackTransfer(9) {
            @Override
            public int getSlotLimit(int slot) {
                return value;
            }

            @Override
            public void onContentsChanged() {
                var Machine = getMachineStorageItem();
                updateMachineCount(Machine);
                super.onContentsChanged();
            }
        }).setFilter(itemStack -> AvailableMachine.contains(itemStack.getItem().toString()));
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
            for(int i = 0; i < 9 ;i++){
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i, size.width - 30 - 18*i, size.height - 30, true, true)
                                .setBackground(GuiTextures.SLOT));
            }
        }
        return widget;
    }

    public List<ItemStack> getMachineStorageItem() {
        var ItemList = new ArrayList<ItemStack>();
        for(int i = 0; i < 9; i++){
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
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0){
            var level = getLevel();
            var pos = getPos();
            var facing = getFrontFacing();
            AABB area;
            switch (facing){
                case NORTH -> area = AABB.of(BoundingBox.fromCorners(pos.offset(-2,-1,0),pos.offset(2,2,length + 2)));
                case SOUTH -> area = AABB.of(BoundingBox.fromCorners(pos.offset(-2,-1,0),pos.offset(2,2,-length - 2)));
                case EAST -> area = AABB.of(BoundingBox.fromCorners(pos.offset(0,-1,-2),pos.offset(-length - 2,2,2)));
                case WEST -> area = AABB.of(BoundingBox.fromCorners(pos.offset(0,-1,-2),pos.offset(length + 2,2,2)));
                default -> throw new IllegalStateException("Unexpected value: ");
            }
            if (level != null) {
                var entities = level.getEntities(null,area);
                VILLAGER_COUNT = (int) entities.stream().filter(entity -> entity instanceof Villager).count();
            }
            MachineUtils.inputItem(CTNHItems.SIMPLE_NUTRITIOUS_MEAL.asStack(VILLAGER_COUNT),this);
        }
        return super.onWorking();
    }

    public static GTRecipe recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result){
        if (machine instanceof FactoryMachine fmachine) {
            var newrecipe = recipe.copy();
            var recipeType = fmachine.getRecipeType();
        var basicRate = Math.min(fmachine.VILLAGER_COUNT,(fmachine.length - 2)/4 + 4) * calculateDiversity(fmachine) * Math.sqrt(fmachine.DEPLOYER_COUNT);
        if(basicRate == 0) {
            return null;
        }
            newrecipe.duration = (int) (newrecipe.duration / basicRate);
            if (recipeType.equals(GTRecipeTypes.CENTRIFUGE_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.CENTRIFUGE_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.LATHE_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.LATHE_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.MACERATOR_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.CRUSHING_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.EXTRACTOR_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.BURNER_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.BENDER_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.PRESSOR_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.MIXER_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.MIXER_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.WIREMILL_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.SAW_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.LASER_COUNT), false).getFirst();
            } else if (recipeType.equals(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES)) {
                return GTRecipeModifiers.accurateParallel(fmachine, newrecipe, (int) Math.sqrt(fmachine.BASIN_COUNT), false).getFirst();
            }
            throw new IllegalStateException("Unexpected value: " + recipeType);
        }
        return recipe;
    }

    public static double calculateDiversity(FactoryMachine machine) {
        double diversity = 1.5 - Math.pow((double) machine.CRUSHING_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.MIXER_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.LATHE_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.BURNER_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.PRESSOR_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.MIXER_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.DEPLOYER_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.LASER_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.BASIN_COUNT / machine.TOTAL_COUNT,2)
                             - Math.pow((double) machine.SAW_COUNT / machine.TOTAL_COUNT,2);
        return diversity;
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
