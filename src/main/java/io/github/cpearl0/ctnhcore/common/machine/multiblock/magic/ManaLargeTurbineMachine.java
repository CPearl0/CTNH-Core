package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.part.RotorHolderPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.item.BotaniaItems;

import java.util.List;

public class ManaLargeTurbineMachine extends WorkableElectricMultiblockMachine implements ITieredMachine, IMachineModifyDrops, IExplosionMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            ManaLargeTurbineMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public static final int MIN_DURABILITY_TO_WARN = 10;
    @Persisted
    public final NotifiableItemStackHandler machineStorage;

    @Getter
    private final int RestrictTier;
    @Getter
    private final int tier = GTValues.ULV;
    private int excessVoltage;
    public double efficiency = 1;
    public int parallel=0;
    public double consumpution_rate = 1;
    public double consumputionrate =1;
    public double consumputionefficiency=1;
    public double mana_consumption=1;
    private long BASE_EU_OUTPUT;
    private List<String> Tier4_rune = List.of("asgard_rune","vanaheim_rune","alfheim_rune","midgard_rune", "joetunheim_rune","muspelheim_rune","nifheim_rune","nidavellir_rune","helheim_rune");

    public ManaLargeTurbineMachine(IMachineBlockEntity holder, int tier,double consumptionrate,double consumputionefficiency) {
        super(holder);
        this.RestrictTier = tier;
        this.machineStorage = createMachineStorage((byte) 64);
        this.consumputionrate=consumptionrate;
        this.consumputionefficiency=consumputionefficiency;
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
            turbineMachine.consumpution_rate = 0.8;
        }
        else if(turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSpring) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSummer) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeAutumn) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeWinter)){
            turbineMachine.efficiency = 2;
            turbineMachine.consumpution_rate = 1.2;
        }
        else if(turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeEnvy) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeGluttony) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeGreed) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeLust) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runePride) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeSloth) ||
                turbineMachine.getMachineStorageItem().getItem().equals(BotaniaItems.runeWrath)){
            turbineMachine.efficiency = 2.5;
            turbineMachine.consumpution_rate = 1.0;
        }
        else if(turbineMachine.Tier4_rune.contains(turbineMachine.getMachineStorageItem().getItem().toString())){
            turbineMachine.efficiency = 4;
            turbineMachine.consumpution_rate = 0.6;
        }
        else if(turbineMachine.getMachineStorageItem().getItem().equals(CTNHItems.HORIZEN_RUNE.get())||turbineMachine.getMachineStorageItem().getItem().equals(CTNHItems.STARLIGHT_RUNE.get())||turbineMachine.getMachineStorageItem().getItem().equals(CTNHItems.TWIST_RUNE.get())||turbineMachine.getMachineStorageItem().getItem().equals(CTNHItems.PROLIFERATION_RUNE.get()))
        {
            turbineMachine.efficiency = 5;
            turbineMachine.consumpution_rate = 0.3;
        }
        else {
            turbineMachine.efficiency = 1;
            turbineMachine.consumpution_rate = 1;
        }
        var rotorHolder = turbineMachine.getRotorHolder();
        var EUt = RecipeHelper.getOutputEUt(recipe);
        turbineMachine.BASE_EU_OUTPUT=EUt;
        if (rotorHolder == null || EUt <= 0) return ModifierFunction.NULL;

        var turbineMaxVoltage = (int) turbineMachine.getOverclockVoltage();
        if (turbineMachine.excessVoltage >= turbineMaxVoltage) {
            turbineMachine.excessVoltage -= turbineMaxVoltage;
            return ModifierFunction.NULL;
        }

        double holderEfficiency = rotorHolder.getTotalEfficiency() / 100.0;

        // get the amount of parallel required to match the desired output voltage
        var maxParallel = (int) ((turbineMaxVoltage - turbineMachine.excessVoltage) / (EUt));
        int lose_mana=1;
        turbineMachine.mana_consumption=maxParallel*(turbineMachine.consumputionrate)*turbineMachine.consumpution_rate;
        if(!MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid(maxParallel),turbineMachine))
        {
            lose_mana=5;
        }
        // this is necessary to prevent over-consumption of fuel
        turbineMachine.excessVoltage += (int) (maxParallel * EUt - turbineMaxVoltage);
        int actualParallel = (int)ParallelLogic.getParallelAmountFast(turbineMachine, recipe, maxParallel);
        turbineMachine.parallel=actualParallel;
        return ModifierFunction.builder()
                .inputModifier(ContentModifier.multiplier(turbineMachine.consumputionrate))
                .outputModifier(ContentModifier.multiplier(turbineMachine.consumputionrate))
                .eutMultiplier(turbineMachine.productionBoost() * actualParallel/lose_mana)
                .durationMultiplier(holderEfficiency)
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
            else if(getMachineStorageItem().getItem().equals(CTNHItems.HORIZEN_RUNE.get())||getMachineStorageItem().getItem().equals(CTNHItems.STARLIGHT_RUNE.get())||getMachineStorageItem().getItem().equals(CTNHItems.TWIST_RUNE.get())||getMachineStorageItem().getItem().equals(CTNHItems.PROLIFERATION_RUNE.get())) {
                if(random<=0.02)
                {
                    consumeItem();
                }
            }
            else if(getMachineStorageItem().getItem().equals(CTNHItems.QUASAR_RUNE.get()))
            {
                doExplosion(3f);
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

        if (rotorHolder != null && rotorHolder.hasRotor()) {
            var p=rotorHolder.getTotalPower();
            var c=rotorHolder.getHolderPowerMultiplier();
            var d=rotorHolder.getRotorPower();
            return (long) (BASE_EU_OUTPUT * efficiency * rotorHolder.getTotalPower() / 100*consumputionefficiency);
        }
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
                textList.add(Component.translatable("ctnh.magic.parallel",parallel));
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
                textList.add(Component.translatable("ctnh.manaturbine.consumption_rate",mana_consumption));
            }
        }
    }
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
