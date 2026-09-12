package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.ChemicalPlantMachine;
import io.github.cpearl0.ctnhcore.common.machine.simple.EfficiencyGeneratorMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.machine.trait.multiblock.CoilMachineTrait;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.function.Function;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.getCoilEUtDiscount;

public class CTNHRecipeModifiers {

    @CN("线圈温度过低！")
    @EN("Coil temperature too low!")
    public static Lang gtceuRecipeModifierCoilTemperatureTooLow;

    @Key("gtceu.recipe_modifier.insufficient_eu_to_start_fusion")
    @CN("缺少足够能量以启动核聚变反应")
    @EN("Not enough energy to start the fusion reaction")
    public static Lang gtceuRecipeModifierInsufficientEuToStartFusion;

    @CN("电压等级过低！")
    @EN("Voltage tier too low!")
    public static Lang gtceuRecipeModifierInsufficientVoltage;

    public static Component accurateParallel(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe,
                                             int parallel) {
        var maxParallel = ParallelLogic.getParallelAmount(group, recipe, parallel);
        if (maxParallel <= 1) return null;
        recipe.multiplyAllContents(maxParallel);
        recipe.parallels *= maxParallel;
        return null;
    }

    public static @NotNull Component ebfOverclock(@NotNull MetaMachine machine, RecipeHandlerGroup group,
                                                  @NotNull GTRecipe recipe) {
        var coilMachine = machine.getTrait(CoilMachineTrait.class);
        if (coilMachine == null ||
                !(machine instanceof WorkableElectricMultiblockMachine workableElectricMultiblockMachine)) {
            return RecipeModifier.nullWrongType(CoilWorkableElectricMultiblockMachine.class, machine);
        }

        int blastFurnaceTemperature = coilMachine.getCoilType().getCoilTemperature() +
                (100 * Math.max(0, workableElectricMultiblockMachine.getTier() - GTValues.MV));
        int recipeTemp = recipe.data.getInt("ebf_temp");
        if (!recipe.data.contains("ebf_temp") || recipeTemp > blastFurnaceTemperature) {
            return gtceuRecipeModifierCoilTemperatureTooLow.translate();
        }

        if (recipe.tier > workableElectricMultiblockMachine.getTier()) {
            return gtceuRecipeModifierInsufficientVoltage.translate();
        }

        recipe.multiplyEUt(getCoilEUtDiscount(recipeTemp, blastFurnaceTemperature));

        OverclockingLogic logic = (p, v) -> OverclockingLogic.heatingCoilOC(p, v, recipeTemp, blastFurnaceTemperature);
        return logic.getModifier(machine, group, recipe, workableElectricMultiblockMachine.getTierVoltage());
    }

    public static final RecipeModifier GCYM_REDUCTION = (machine, group, recipe) -> CTNHRecipeModifiers
            .reduction(recipe, 0.8, 0.6);

    public static final RecipeModifier COIL_PARALLEL = (machine, group, recipe) -> CTNHRecipeModifiers.accurateParallel(
            machine, group, recipe,
            Math.min(2147483647, (int) Math.pow(2,
                    ((double) machine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() /
                            900))));

    public static final Function<OverclockingLogic, RecipeModifier> MT_ELECTRIC_OVERCLOCK = Util
            .memoize(logic -> (machine, group, recipe) -> {
                if (!(machine instanceof IOverclockMachine overclockMachine)) return null;
                if (machine instanceof ITieredMachine tieredMachine && recipe.tier > tieredMachine.getTier()) {
                    return gtceuRecipeModifierInsufficientVoltage.translate();
                }
                return logic.getModifier(machine, group, recipe, overclockMachine.getOverclockVoltage());
            });

    public static Component chemicalPlantModifier(MetaMachine machine, RecipeHandlerGroup group,
                                                  @NotNull GTRecipe recipe) {
        if (!(machine instanceof IMultiController multiController) || !multiController.isFormed())
            return null;
        if (machine instanceof ChemicalPlantMachine chemicalPlantMachine) {
            var speedMultiplier = 100.0 / (100.0 + (chemicalPlantMachine.getSpeedMultiplier()));
            var energyConsumeMultiplier = 1;
            var parallels = ParallelLogic.getParallelAmount(group, recipe, chemicalPlantMachine.getMaxParallel());

            if (parallels == 1 && speedMultiplier == 1.0 && energyConsumeMultiplier == 1.0)
                return null;
            recipe.multiplyAllContents(parallels);
            recipe.multiplyEUt(energyConsumeMultiplier);
            recipe.multiplyDuration(speedMultiplier);
            recipe.parallels *= parallels;
            return null;
        }
        return null;
    }

    public static Component superEbfOverclock(MetaMachine machine, RecipeHandlerGroup group, @NotNull GTRecipe recipe) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            var coilTrait = machine.getTrait(CoilMachineTrait.class);
            if (coilTrait == null) return RecipeModifier.nullWrongType(CoilMachineTrait.class, machine);
            final var blastFurnaceTemperature = coilTrait.getCoilType().getCoilTemperature() +
                    100 * Math.max(0, coilMachine.getTier() - GTValues.MV);
            var recipeTemp = recipe.data.getInt("ebf_temp");
            if (!recipe.data.contains("ebf_temp") || recipe.data.getInt("ebf_temp") > blastFurnaceTemperature) {
                return gtceuRecipeModifierCoilTemperatureTooLow.translate();
            }
            if (recipe.tier > coilMachine.getTier()) {
                return gtceuRecipeModifierInsufficientVoltage.translate();
            }
            recipe.multiplyEUt(getCoilEUtDiscount(recipeTemp, blastFurnaceTemperature));
            recipe.multiplyDuration(0.5);

            OverclockingLogic logic = (p, v) -> OverclockingLogic.heatingCoilOC(p, v, recipeTemp,
                    blastFurnaceTemperature);
            return logic.getModifier(machine, group, recipe, coilMachine.getOverclockVoltage());
        }
        return null;
    }

    private static Component reduction(@NotNull GTRecipe recipe, double duration, double eut) {
        recipe.multiplyDuration(duration);
        recipe.multiplyEUt(eut);
        return null;
    }

    public static Component rocketEngine(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        return EfficiencyGeneratorMachine.recipeModifier(machine, group, recipe);
    }
}
