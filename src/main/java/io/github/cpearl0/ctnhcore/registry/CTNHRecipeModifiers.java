package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import org.jetbrains.annotations.NotNull;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.getCoilEUtDiscount;

public class CTNHRecipeModifiers {
    public static final ModifierFunction accurateParallel(MetaMachine machine,GTRecipe recipe,int parallel) {
        var maxParallel = ParallelLogic.getParallelAmount(machine,recipe,parallel);
        return ModifierFunction.builder()
                .parallels(maxParallel)
                .inputModifier(ContentModifier.multiplier(maxParallel))
                .outputModifier(ContentModifier.multiplier(maxParallel))
                .eutMultiplier(maxParallel)
                .build();
    }

    public static final RecipeModifier GCYM_REDUCTION = (machine, recipe) -> CTNHRecipeModifiers
            .reduction(machine, recipe, 0.8, 0.6);

    public static final RecipeModifier COIL_PARALLEL = (machine, recipe) -> CTNHRecipeModifiers.accurateParallel(machine,recipe,Math.min(2147483647, (int) Math.pow(2, ((double) ((CoilWorkableElectricMultiblockMachine) machine).getCoilType().getCoilTemperature() / 900))));

//    public static ModifierFunction chemicalPlantOverclock(MetaMachine machine, @NotNull GTRecipe recipe) {
//        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
//            GTRecipe recipe1 = reduction(machine, recipe, (1.0 - coilMachine.getCoilTier() * 0.05) * 0.8, (1.0 - coilMachine.getCoilTier() * 0.05) * 0.6);
//            if (recipe1 != null) {
//                recipe1 = GTRecipeModifiers.hatchParallel(machine, recipe1);
//                if (recipe1 != null) return RecipeHelper.applyOverclock(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK, recipe1, coilMachine.getOverclockVoltage(), params, result);
//            }
//        }
//        return null;
//    }
    public static ModifierFunction superEbfOverclock(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            final var blastFurnaceTemperature = coilMachine.getCoilType().getCoilTemperature() +
                    100 * Math.max(0, coilMachine.getTier() - GTValues.MV);
            var recipeTemp = recipe.data.getInt("ebf_temp");
            if (!recipe.data.contains("ebf_temp") || recipe.data.getInt("ebf_temp") > blastFurnaceTemperature) {
                return ModifierFunction.NULL;
            }
            if (RecipeHelper.getRecipeEUtTier(recipe) > coilMachine.getTier()) {
                return ModifierFunction.NULL;
            }
            var discount = ModifierFunction.builder()
                    .eutMultiplier(getCoilEUtDiscount(recipeTemp, blastFurnaceTemperature))
                    .durationMultiplier(0.5)
                    .build();

            OverclockingLogic logic = (p, v) -> OverclockingLogic.heatingCoilOC(p, v, recipeTemp, blastFurnaceTemperature);
            var oc = logic.getModifier(machine, recipe, coilMachine.getOverclockVoltage());
            return oc.compose(discount);
        }
        return ModifierFunction.IDENTITY;
    }
    private static ModifierFunction reduction(MetaMachine machine, @NotNull GTRecipe recipe, double v, double v1) {
        return ModifierFunction.builder()
                .durationMultiplier(v)
                .eutMultiplier(v1)
                .build();
    }


}
