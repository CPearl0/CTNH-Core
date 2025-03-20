package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;
import wayoftime.bloodmagic.common.fluid.BloodMagicFluids;
import wayoftime.bloodmagic.demonaura.WorldDemonWillHandler;

import java.util.List;
import java.util.Objects;

public class DemonWillMachine extends WorkableElectricMultiblockMachine {
    @Persisted
    public final NotifiableItemStackHandler machineStorage;
    public boolean isBoosted = false;
    public double diversity = 1;
    public double difference = 0;
    public int Sacrifice_rune = 0;
    public int Speed_rune = 0;
    public int Capacity_rune = 0;
    public int Augmented_rune = 0;
    public BlockPos[] Runes = new BlockPos[]{
            MachineUtils.getOffset(this,0,34,-5),
            MachineUtils.getOffset(this,1,34,-5),
            MachineUtils.getOffset(this,2,34,-5),
            MachineUtils.getOffset(this,-1,34,-5),
            MachineUtils.getOffset(this,-2,34,-5),
            MachineUtils.getOffset(this,3,34,-4),
            MachineUtils.getOffset(this,-3,34,-4),
            MachineUtils.getOffset(this,5,34,-2),
            MachineUtils.getOffset(this,-5,34,-2),
            MachineUtils.getOffset(this,6,34,-1),
            MachineUtils.getOffset(this,6,34,0),
            MachineUtils.getOffset(this,6,34,1),
            MachineUtils.getOffset(this,6,34,2),
            MachineUtils.getOffset(this,6,34,3),
            MachineUtils.getOffset(this,-6,34,-1),
            MachineUtils.getOffset(this,-6,34,0),
            MachineUtils.getOffset(this,-6,34,1),
            MachineUtils.getOffset(this,-6,34,2),
            MachineUtils.getOffset(this,-6,34,3),
            MachineUtils.getOffset(this,5,34,4),
            MachineUtils.getOffset(this,-5,34,4),
            MachineUtils.getOffset(this,-3,34,6),
            MachineUtils.getOffset(this,3,34,6),
            MachineUtils.getOffset(this,0,34,7),
            MachineUtils.getOffset(this,-1,34,7),
            MachineUtils.getOffset(this,-2,34,7),
            MachineUtils.getOffset(this,1,34,7),
            MachineUtils.getOffset(this,2,34,7),
            MachineUtils.getOffset(this,0,32,-8),
            MachineUtils.getOffset(this,1,32,-8),
            MachineUtils.getOffset(this,2,32,-8),
            MachineUtils.getOffset(this,-1,32,-8),
            MachineUtils.getOffset(this,-2,32,-8),
            MachineUtils.getOffset(this,3,32,-7),
            MachineUtils.getOffset(this,4,32,-7),
            MachineUtils.getOffset(this,5,32,-7),
            MachineUtils.getOffset(this,-3,32,-7),
            MachineUtils.getOffset(this,-4,32,-7),
            MachineUtils.getOffset(this,-5,32,-7),
            MachineUtils.getOffset(this,-8,32,-4),
            MachineUtils.getOffset(this,-8,32,-3),
            MachineUtils.getOffset(this,-8,32,-2),
            MachineUtils.getOffset(this,8,32,-4),
            MachineUtils.getOffset(this,8,32,-3),
            MachineUtils.getOffset(this,8,32,-2),
            MachineUtils.getOffset(this,-9,32,-1),
            MachineUtils.getOffset(this,-9,32,0),
            MachineUtils.getOffset(this,-9,32,1),
            MachineUtils.getOffset(this,-9,32,2),
            MachineUtils.getOffset(this,-9,32,3),
            MachineUtils.getOffset(this,9,32,-1),
            MachineUtils.getOffset(this,9,32,0),
            MachineUtils.getOffset(this,9,32,1),
            MachineUtils.getOffset(this,9,32,2),
            MachineUtils.getOffset(this,9,32,3),
            MachineUtils.getOffset(this,-8,32,4),
            MachineUtils.getOffset(this,-8,32,5),
            MachineUtils.getOffset(this,-8,32,6),
            MachineUtils.getOffset(this,8,32,4),
            MachineUtils.getOffset(this,8,32,5),
            MachineUtils.getOffset(this,8,32,6),
            MachineUtils.getOffset(this,5,32,9),
            MachineUtils.getOffset(this,4,32,9),
            MachineUtils.getOffset(this,3,32,9),
            MachineUtils.getOffset(this,-5,32,9),
            MachineUtils.getOffset(this,-4,32,9),
            MachineUtils.getOffset(this,-3,32,9),
            MachineUtils.getOffset(this,-2,32,10),
            MachineUtils.getOffset(this,-1,32,10),
            MachineUtils.getOffset(this,0,32,10),
            MachineUtils.getOffset(this,1,32,10),
            MachineUtils.getOffset(this,2,32,10),
    };

    public EnumDemonWillType type = EnumDemonWillType.DEFAULT;
    public int MAX_WILL = 100;
    public List<String> enableTypes = List.of("vengeful_core","corrosive_core","steadfast_core","destructive_core");
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            DemonWillMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public DemonWillMachine(IMachineBlockEntity holder) {
        super(holder);
        machineStorage = createMachineStorage((byte) 1);
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
    protected NotifiableItemStackHandler createMachineStorage(byte value) {
        return new NotifiableItemStackHandler(
                this, 1, IO.NONE, IO.BOTH, slots -> new CustomItemStackHandler(1) {

            @Override
            public int getSlotLimit(int slot) {
                return value;
            }

            @Override
            public void onContentsChanged(int slot) {
                resetMode();
                super.onContentsChanged(slot);
            }
        }).setFilter(itemStack -> enableTypes.contains(itemStack.getItem().toString()));
    }

    public ItemStack getMachineStorageItem() {
        return machineStorage.getStackInSlot(0);
    }
    public double getTotalWillDifference() {
        var pos1 = MachineUtils.getOffset(this,10,0,-10);
        var pos2 = MachineUtils.getOffset(this,-10,0,10);
        double difference = 0;
        if (type == EnumDemonWillType.DEFAULT) {
            for (EnumDemonWillType type1 : EnumDemonWillType.values()) {
                var newDifference = getWillDifference(pos1,pos2,type1);
                if (newDifference == 0) {
                    continue;
                }
                difference += newDifference;
                adjustWillChunk(pos1,pos2,type1);
            }
        }
        else {
            difference += getWillDifference(pos1,pos2,type) * 5;
            adjustWillChunk(pos1,pos2,type);
        }
        return difference;
    }
    public double getWillDifference(BlockPos pos1,BlockPos pos2,EnumDemonWillType type1) {
        var will1 = WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()),pos1, type1);
        var will2 = WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()),pos2, type1);
        if (will1 == will2){
            return 0;
        }
        return (Math.abs(will1 - will2) + Capacity_rune * 2) * Math.pow(1.02,Augmented_rune);
    }
    public void adjustWillChunk(BlockPos pos1, BlockPos pos2,EnumDemonWillType type1) {
        var willChunk1 = WorldDemonWillHandler.getWillChunk(Objects.requireNonNull(getLevel()),pos1);
        var willChunk2 = WorldDemonWillHandler.getWillChunk(Objects.requireNonNull(getLevel()),pos2);
        var difference = getWillDifference(pos1,pos2,type1);
        if (willChunk1.getCurrentWill().getWill(type1) < willChunk2.getCurrentWill().getWill(type1)) {
            willChunk1.getCurrentWill().addWill(type1,Math.abs(difference) * 0.04 ,MAX_WILL);
            willChunk2.getCurrentWill().drainWill(type1, Math.abs(difference) * 0.08);
        }
        else {
            willChunk1.getCurrentWill().drainWill(type1,Math.abs(difference) * 0.08);
            willChunk2.getCurrentWill().addWill(type1, Math.abs(difference) * 0.04,MAX_WILL);
        }
    }
    public void calculateDiversity() {
        var pos1 = MachineUtils.getOffset(this,10,0,2);
        var pos2 = MachineUtils.getOffset(this,-10,0,2);
        double total1 = 0;
        double total2 = 0;
        double diversity1 = 1.2;
        double diversity2 = 1.2;
        for (EnumDemonWillType type1 : EnumDemonWillType.values()) {
            total1 += WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()),pos1,type1);
            total2 += WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()),pos2,type1);
        }
        for (EnumDemonWillType type1 : EnumDemonWillType.values()) {
            if(total1 == 0){
                diversity1 = 0.2;
            }
            else {
                var will = WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()), pos1, type1);
                diversity1 -= Math.pow(will / total1, 2);
            }
            if(total2 == 0){
                diversity2 = 0.2;
            }
            else {
                var will = WorldDemonWillHandler.getCurrentWill(Objects.requireNonNull(getLevel()), pos2, type1);
                diversity2 -= Math.pow(will / total2, 2);
            }
        }
        diversity = diversity1 * diversity2;
    }
    public void resetMode() {
        if (getMachineStorageItem() == null) {
            type = EnumDemonWillType.DEFAULT;
        }
        else {
            switch (getMachineStorageItem().getItem().toString()) {
                case "vengeful_core" -> type = EnumDemonWillType.VENGEFUL;
                case "corrosive_core" -> type = EnumDemonWillType.CORROSIVE;
                case "steadfast_core" -> type = EnumDemonWillType.STEADFAST;
                case "destructive_core" -> type = EnumDemonWillType.DESTRUCTIVE;
            }
        }
    }
    public void calculateRune() {
        Speed_rune = 0;
        Augmented_rune = 0;
        Capacity_rune = 0;
        Sacrifice_rune = 0;
        for(var rune : Runes){
            var runeBlock = Objects.requireNonNull(getLevel()).getBlockState(rune).getBlock();
            if (runeBlock.equals(BloodMagicBlocks.SPEED_RUNE.get())) {
                Speed_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.SPEED_RUNE_2.get())) {
                Speed_rune += 2;
            }
            else if (runeBlock.equals(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE.get())) {
                Augmented_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE_2.get())) {
                Augmented_rune += 2;
            }
            else if (runeBlock.equals(BloodMagicBlocks.CAPACITY_RUNE.get())) {
                Capacity_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.CAPACITY_RUNE_2.get())) {
                Capacity_rune += 2;
            }
            else if (runeBlock.equals(BloodMagicBlocks.SACRIFICE_RUNE.get()) || runeBlock.equals(BloodMagicBlocks.SELF_SACRIFICE_RUNE.get())){
                Sacrifice_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.SACRIFICE_RUNE_2.get()) || runeBlock.equals(BloodMagicBlocks.SELF_SACRIFICE_RUNE_2.get())){
                Sacrifice_rune += 2;
            }
        }
    }
    public double getBoostRate() {
        return 2 + 0.5 * Sacrifice_rune;
    }
    public GTRecipe getBloodRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(FluidIngredient.of(100,BloodMagicFluids.LIFE_ESSENCE_FLUID_FLOWING.get())).buildRawRecipe();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof DemonWillMachine dmachine) {
            var difference = dmachine.difference;
            var diversity = dmachine.diversity;
            var modifierFunction = ModifierFunction.builder().durationMultiplier(1 + dmachine.Speed_rune * 0.2);
            if (dmachine.isBoosted) {
                modifierFunction.eutMultiplier(diversity*Math.pow(difference,2)* dmachine.getBoostRate());
            }
            else {
                modifierFunction.eutMultiplier(diversity*Math.pow(difference,2));
            }
            return modifierFunction.build();
        }
        return ModifierFunction.IDENTITY;
    }
    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        long totalContinuousRunningTime = recipeLogic.getTotalContinuousRunningTime();
        // check boost fluid
        if ((totalContinuousRunningTime == 1 || totalContinuousRunningTime % 20 == 0)) {
            var boosterRecipe = getBloodRecipe();
            this.isBoosted = boosterRecipe.matchRecipe(this).isSuccess() &&
                    boosterRecipe.handleRecipeIO(IO.IN, this, this.recipeLogic.getChanceCaches());
        }
        return value;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        difference = getTotalWillDifference();
        if (type == EnumDemonWillType.DEFAULT) {
            calculateDiversity();
        }
        else {
            diversity = 1;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void onStructureFormed() {
        resetMode();
        calculateRune();
        super.onStructureFormed();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var outputEnergy = (isBoosted? getBoostRate() : 1) * diversity * Math.pow(difference,2) * 32;
        var voltageName = GTValues.VNF[GTUtil.getTierByVoltage((long) outputEnergy)];
        textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station2", FormattingUtil.formatNumbers(outputEnergy), voltageName));
        switch (type) {
            case DEFAULT -> textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.default"));
            case VENGEFUL -> textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.vengeful"));
            case CORROSIVE -> textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.corrosive"));
            case STEADFAST -> textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.steadfast"));
            case DESTRUCTIVE -> textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.destructive"));
        }
        textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.1",String.format("%.1f",difference)));
        if (isBoosted) {
            textList.add(Component.translatable("ctnh.multiblock.demon_generator.info.boosted"));
        }
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
