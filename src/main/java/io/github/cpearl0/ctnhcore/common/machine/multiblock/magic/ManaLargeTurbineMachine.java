package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.machine.multiblock.part.RotorHolderPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.item.BotaniaItems;

import java.util.List;

public class ManaLargeTurbineMachine extends WorkableElectricMultiblockMachine implements ITieredMachine, IMachineModifyDrops {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            ManaLargeTurbineMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public static final int MIN_DURABILITY_TO_WARN = 10;
    public final NotifiableItemStackHandler machineStorage;
    private final int BASE_EU_OUTPUT;
    @Getter
    private final int RestrictTier;
    @Getter
    private final int tier = GTValues.ULV;
    private int excessVoltage;
    public double efficiency = 1;

    public double consumpution_rate = 1;

    private List<String> Tier4_rune = List.of("asgard_rune","vanaheim_rune","alfheim_rune","midgard_rune", "joetunheim_rune","muspelheim_rune","nifheim_rune","nidavellir_rune","helheim_rune");

    public ManaLargeTurbineMachine(IMachineBlockEntity holder, int BaseEuOutput, int tier) {
        super(holder);
        this.RestrictTier = tier;
        this.BASE_EU_OUTPUT = BaseEuOutput;
        this.machineStorage = createMachineStorage((byte) 64);
    }

    //////////////////////////////////////
    // ****** Recipe Logic *******//
    //////////////////////////////////////
    @Nullable
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof ManaLargeTurbineMachine turbineMachine)) return ModifierFunction.NULL;
        if (turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeAir) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeEarth) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeWater) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeFire)){
            turbineMachine.efficiency = 1.5;
            turbineMachine.consumpution_rate = 0.9;
        }
        else if(turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSpring) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSummer) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeAutumn) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeWinter)){
            turbineMachine.efficiency = 2;
            turbineMachine.consumpution_rate = 0.75;
        }
        else if(turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeEnvy) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeGluttony) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeGreed) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeLust) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runePride) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSloth) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeWrath)){
            turbineMachine.efficiency = 3;
            turbineMachine.consumpution_rate = 0.6;
        }
        else if(turbineMachine.Tier4_rune.contains(turbineMachine.getMachineStorageItem().getItem().toString())){
            turbineMachine.efficiency = 4;
            turbineMachine.consumpution_rate = 0.5;
        }
        else {
            turbineMachine.efficiency = 1;
            turbineMachine.consumpution_rate = 1;
        }
        var rotorHolder = turbineMachine.getRotorHolder();
        var EUt = RecipeHelper.getOutputEUt(recipe);

        if (rotorHolder == null || EUt <= 0) return ModifierFunction.NULL;

        var turbineMaxVoltage = (int) turbineMachine.getOverclockVoltage();
        if (turbineMachine.excessVoltage >= turbineMaxVoltage) {
            turbineMachine.excessVoltage -= turbineMaxVoltage;
            return ModifierFunction.NULL;
        }

        double holderEfficiency = rotorHolder.getTotalEfficiency() / 100.0;

        // get the amount of parallel required to match the desired output voltage
        var maxParallel = (int) ((turbineMaxVoltage - turbineMachine.excessVoltage) / (EUt * holderEfficiency));

        // this is necessary to prevent over-consumption of fuel
        turbineMachine.excessVoltage += (int) (maxParallel * EUt * holderEfficiency - turbineMaxVoltage);
        int actualParallel = ParallelLogic.getParallelAmountFast(turbineMachine, recipe, maxParallel);
        return ModifierFunction.builder()
                .inputModifier(ContentModifier.multiplier(actualParallel))
                .outputModifier(ContentModifier.multiplier(actualParallel))
                .eutMultiplier(turbineMachine.productionBoost() * actualParallel)
                .durationMultiplier(holderEfficiency/turbineMachine.consumpution_rate)
                .build();
    }
    @Override
    public void onDrops(List<ItemStack> drops) {
        clearInventory(machineStorage.storage);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 100 == 0){
            var random = Math.random();
            if (getMachineStorageItem().getItem().equals(BotaniaItems.runeAir) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeEarth) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeWater) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeFire)){
                if (random <= 0.20){
                    consumeItem();
                }
            }
            else if(getMachineStorageItem().getItem().equals(BotaniaItems.runeSpring) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeSummer) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeAutumn) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeWinter)){
                if(random <= 0.10){
                    consumeItem();
                }
            }
            else if(getMachineStorageItem().getItem().equals(BotaniaItems.runeEnvy) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeGluttony) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeGreed) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeLust) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runePride) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeSloth) ||
                    getMachineStorageItem().getItem().equals(BotaniaItems.runeWrath)){
                if(random <= 0.05){
                    consumeItem();
                }
            }
            else if(Tier4_rune.contains(getMachineStorageItem().getItem().toString()))
            {
                if(random<=0.025)
                {
                    consumeItem();
                }
            }
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var rotorHolder = (RotorHolderPartMachine) getParts().stream().filter(part -> part instanceof RotorHolderPartMachine).findFirst().get();
            if (!rotorHolder.hasRotor()) {
                return false;
            }
            if (rotorHolder.getTier() > RestrictTier) {
                getRecipeLogic().interruptRecipe();
                return false;
            }
        return super.beforeWorking(recipe);
    }

    @Nullable
    private IRotorHolderMachine getRotorHolder() {
        for (IMultiPart part : getParts()) {
            if (part instanceof IRotorHolderMachine rotorHolder) {
                return rotorHolder;
            }
        }
        return null;
    }

    @Override
    public long getOverclockVoltage() {
        var rotorHolder = getRotorHolder();
        if (rotorHolder != null && rotorHolder.hasRotor())
            return (long) (BASE_EU_OUTPUT * efficiency * rotorHolder.getTotalPower() / 100);
        return 0;
    }

    protected double productionBoost() {
        var rotorHolder = getRotorHolder();
        if (rotorHolder != null && rotorHolder.hasRotor()) {
            int maxSpeed = rotorHolder.getMaxRotorHolderSpeed();
            int currentSpeed = rotorHolder.getRotorSpeed();
            if (currentSpeed >= maxSpeed) return 1;
            return Math.pow(1.0 * currentSpeed / maxSpeed, 2);
        }
        return 0;
    }

    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }

    @Override
    public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return capability != EURecipeCapability.CAP;
    }

    //////////////////////////////////////
    // ******* GUI ********//
    //////////////////////////////////////
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

    public void consumeItem() { machineStorage.extractItem(0, 1,false); }
    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            var rotorHolder = getRotorHolder();

            if (rotorHolder != null && rotorHolder.getRotorEfficiency() > 0) {
                textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_speed", FormattingUtil.formatNumbers(rotorHolder.getRotorSpeed()), FormattingUtil.formatNumbers(rotorHolder.getMaxRotorHolderSpeed())));
                textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency", rotorHolder.getTotalEfficiency()));

                long maxProduction = getOverclockVoltage();
                long currentProduction = isActive() && recipeLogic.getLastRecipe() != null ?
                        RecipeHelper.getOutputEUt(recipeLogic.getLastRecipe()) : 0;
                String voltageName = GTValues.VNF[GTUtil.getTierByVoltage(currentProduction)];

                if (isActive()) {
                    textList.add(3, Component.translatable("gtceu.multiblock.turbine.energy_per_tick", FormattingUtil.formatNumbers(currentProduction), voltageName));
                }

                int rotorDurability = rotorHolder.getRotorDurabilityPercent();
                if (rotorDurability > MIN_DURABILITY_TO_WARN) {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability));
                } else {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability).setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                }
                textList.add(Component.translatable("ctnh.manaturbine.efficiency",efficiency*100));
                textList.add(Component.translatable("ctnh.manaturbine.consumption_rate",consumpution_rate));
            }
        }
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
