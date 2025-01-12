package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public double basicRate = 1;
    public int SLOT_COUNT = 10;

    public int length = 0;
    @Persisted
    public final NotifiableItemStackHandler machineStorage;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FactoryMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public List<String> AvailableMachine = List.of("lathe","mechanical_press","centrifuge","crushing_wheel","deployer","blaze_burner","mechanical_mixer","basin","laser","mechanical_saw");
    public FactoryMachine(IMachineBlockEntity holder) {
        super(holder);
        this.machineStorage = createMachineStorage((byte) 64);
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
        int len = 1;
        while(true) {
            var pos = MachineUtils.getOffset(this,0,0,len);
            if(getLevel().getBlockState(pos).getBlock().equals(AllBlocks.ANDESITE_CASING.get())){
                len ++;
            }
            else {
                break;
            }
        }
        length = len;
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
            for(int i = 0; i < SLOT_COUNT/2 ;i++){
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i, size.width - 30 - 18*i, size.height - 30, true, true)
                                .setBackground(GuiTextures.SLOT));
            }
            for(int i = 0; i < SLOT_COUNT/2; i++){
                group.addWidget(
                        new SlotWidget(machineStorage.storage, i + 5, size.width - 30 - 18*i, size.height - 48, true, true)
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
        TOTAL_COUNT = CENTRIFUGE_COUNT + CRUSHING_COUNT + LASER_COUNT + LATHE_COUNT + DEPLOYER_COUNT + BURNER_COUNT + PRESSOR_COUNT + MIXER_COUNT + BASIN_COUNT + SAW_COUNT;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        updateVillagerCount();
        updateBasicRate();
        if (VILLAGER_COUNT == 0) {
            return false;
        }
        else {
            if (getRecipeType().equals(GTRecipeTypes.CENTRIFUGE_RECIPES) && CENTRIFUGE_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.LATHE_RECIPES) && LATHE_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.MACERATOR_RECIPES) && CRUSHING_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.EXTRACTOR_RECIPES) && BURNER_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.BENDER_RECIPES) && PRESSOR_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.MIXER_RECIPES) && MIXER_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.WIREMILL_RECIPES) && SAW_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES) && LASER_COUNT == 0) {
                return false;
            } else if (getRecipeType().equals(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES) && BASIN_COUNT == 0) {
                return false;
            }
        }
        if (!MachineUtils.canInputItem(CTNHItems.SIMPLE_NUTRITIOUS_MEAL.asStack(VILLAGER_COUNT), this)) {
            return false;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0){
            updateVillagerCount();
            if (getOffsetTimer() % 100 == 0) {
                if(!MachineUtils.inputItem(CTNHItems.SIMPLE_NUTRITIOUS_MEAL.asStack(VILLAGER_COUNT), this)){
                    recipeLogic.setProgress(0);
                }
            }
        }
        return super.onWorking();
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        if (machine instanceof FactoryMachine fmachine) {
            var modifierFunction = ModifierFunction.builder();
            var recipeType = fmachine.getRecipeType();
        if(fmachine.basicRate == 0) {
            return ModifierFunction.NULL;
        }
        modifierFunction.durationModifier(ContentModifier.multiplier(1/fmachine.basicRate));
            if (recipeType.equals(GTRecipeTypes.CENTRIFUGE_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.CENTRIFUGE_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.LATHE_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.LATHE_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.MACERATOR_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.CRUSHING_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.EXTRACTOR_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.BURNER_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.BENDER_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.PRESSOR_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.MIXER_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.MIXER_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.WIREMILL_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.SAW_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.LASER_COUNT)).build();
            } else if (recipeType.equals(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES)) {
                return modifierFunction.parallels((int) Math.sqrt(fmachine.BASIN_COUNT)).build();
            }
            throw new IllegalStateException("Unexpected value: " + recipeType);
        }
        return ModifierFunction.IDENTITY;
    }

    public double calculateDiversity() {
        double diversity = 1.5 - Math.pow((double) CRUSHING_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) MIXER_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) LATHE_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) BURNER_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) PRESSOR_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) MIXER_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) DEPLOYER_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) LASER_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) BASIN_COUNT / TOTAL_COUNT,2)
                             - Math.pow((double) SAW_COUNT / TOTAL_COUNT,2);
        return diversity;
    }

    public void updateVillagerCount() {
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
    }
    public void updateBasicRate() {
        updateMachineCount(getMachineStorageItem());
        basicRate = Math.min(VILLAGER_COUNT,(length - 2)/4 + 4) * calculateDiversity() * (1 + Math.sqrt(DEPLOYER_COUNT) / 4);
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        updateVillagerCount();
        updateBasicRate();
        super.addDisplayText(textList);
        textList.add(Component.translatable("ctnh.multiblock.sweat_shop.villager_count",VILLAGER_COUNT));
        textList.add(Component.translatable("ctnh.multiblock.sweat_shop.basic_rate",String.format("%.1f",basicRate)));
    }
}
