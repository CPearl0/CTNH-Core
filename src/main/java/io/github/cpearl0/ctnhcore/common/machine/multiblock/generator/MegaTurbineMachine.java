package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips;
import io.github.cpearl0.ctnhcore.utils.CTNHRecipeHelper;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.List;

public class MegaTurbineMachine extends RecipeElectricMultiblockMachine implements ITieredMachine {

    @CN("未找到涡轮转子支架，多方块结构不完整")
    @EN("No rotor holder found; the multiblock structure is incomplete")
    public static Lang missingRotorHolder;

    public static final int MIN_DURABILITY_TO_WARN = 10;

    private final long BASE_EU_OUTPUT;
    @Getter
    private final int tier;
    private long excessVoltage;

    public MegaTurbineMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier;
        this.BASE_EU_OUTPUT = GTValues.V[tier] * 32;
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
            return BASE_EU_OUTPUT * rotorHolder.getTotalPower() / 100;
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

    //////////////////////////////////////
    // ****** Recipe Logic *******//
    //////////////////////////////////////
    public static Component recipeModifier(@NotNull MetaMachine machine, RecipeHandlerGroup group,
                                           @NotNull GTRecipe recipe) {
        if (!(machine instanceof MegaTurbineMachine turbineMachine)) {
            return RecipeModifier.nullWrongType(MegaTurbineMachine.class, machine);
        }

        var rotorHolder = turbineMachine.getRotorHolder();
        if (rotorHolder == null) return missingRotorHolder.translate();
        if (!rotorHolder.hasRotor()) {
            return Component.translatable("gtceu.recipe_modifier.missing_valid_turbine_rotor");
        }

        long EUt = recipe.getOutputEUt();
        long turbineMaxVoltage = turbineMachine.getOverclockVoltage();
        double holderEfficiency = rotorHolder.getTotalEfficiency() / 100.0;

        if (EUt <= 0) return CTNHCommonTooltips.recipeModifierNoEuOutput.translate();
        if (holderEfficiency <= 0) {
            return Component.translatable("gtceu.recipe_modifier.missing_valid_turbine_rotor");
        }
        if (turbineMaxVoltage <= EUt) {
            return CTNHRecipeHelper.insufficientOutputPower(EUt, turbineMaxVoltage);
        }

        // get the amount of parallel required to match the desired output voltage
        int maxParallel = (int) (turbineMaxVoltage / EUt);
        int actualParallel = ParallelLogic.getParallelAmountFast(group, recipe, maxParallel);
        double eutMultiplier = turbineMachine.productionBoost();

        recipe.multiplyAllContents(actualParallel);
        recipe.multiplyEUt(eutMultiplier);
        recipe.parallels *= actualParallel;
        recipe.multiplyDuration(holderEfficiency);
        return null;
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }

    @Override
    public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return capability != EURecipeCapability.CAP;
    }

    //////////////////////////////////////
    // ******* GUI ********//
    //////////////////////////////////////

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            var rotorHolder = getRotorHolder();

            if (rotorHolder != null && rotorHolder.getRotorEfficiency() > 0) {
                textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_speed",
                        FormattingUtil.formatNumbers(rotorHolder.getRotorSpeed()),
                        FormattingUtil.formatNumbers(rotorHolder.getMaxRotorHolderSpeed())));
                textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency",
                        rotorHolder.getTotalEfficiency()));

                long maxProduction = getOverclockVoltage();
                long currentProduction = isActive() && recipeLogic.getLastRecipe() != null ?
                        recipeLogic.getLastRecipe().getOutputEUt() : 0;
                String voltageName = GTValues.VNF[GTUtil.getTierByVoltage(currentProduction)];

                if (isActive()) {
                    textList.add(3, Component.translatable("gtceu.multiblock.turbine.energy_per_tick",
                            FormattingUtil.formatNumbers(currentProduction),
                            FormattingUtil.formatNumbers(maxProduction)));
                }

                int rotorDurability = rotorHolder.getRotorDurabilityPercent();
                if (rotorDurability > MIN_DURABILITY_TO_WARN) {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability));
                } else {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability)
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                }
            }
        }
    }
}
