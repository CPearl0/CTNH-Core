package io.github.cpearl0.ctnhcore.common.machine.simple;

import io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips;
import io.github.cpearl0.ctnhcore.utils.CTNHRecipeHelper;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.network.chat.Component;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import org.jetbrains.annotations.NotNull;

public class EfficiencyGeneratorMachine extends SimpleGeneratorMachine {

    public int efficiency;

    public EfficiencyGeneratorMachine(IMachineBlockEntity holder, int tier, float hazardStrengthPerOperation,
                                      Int2IntFunction tankScalingFunction, Object... args) {
        super(holder, tier, hazardStrengthPerOperation, tankScalingFunction, args);
    }

    public EfficiencyGeneratorMachine(IMachineBlockEntity holder, int tier, Int2IntFunction tankScalingFunction,
                                      Int2IntFunction efficiencyFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
        efficiency = efficiencyFunction.apply(tier);
    }

    public static int naquadahReactor(int tier) {
        if (tier == 4) {
            return 80;
        }
        return (tier - 5) * 50 + 100;
    }

    public static int rocketEngine(int tier) {
        return 80 - (tier - 4) * 10;
    }

    public static int normal(int tier) {
        return tier * 20 + 100;
    }

    public static Component recipeModifier(@NotNull MetaMachine machine, RecipeHandlerGroup group,
                                           @NotNull GTRecipe recipe) {
        if (!(machine instanceof EfficiencyGeneratorMachine generator)) {
            return RecipeModifier.nullWrongType(EfficiencyGeneratorMachine.class, machine);
        }

        long recipeEUt = recipe.getOutputEUt();
        if (recipeEUt <= 0) return CTNHCommonTooltips.recipeModifierNoEuOutput.translate();

        long maxOutput = generator.getOverclockVoltage();
        int maxParallel = (int) (maxOutput / recipeEUt);
        if (maxParallel <= 0) return CTNHRecipeHelper.insufficientOutputPower(recipeEUt, maxOutput);

        int parallels = ParallelLogic.getParallelAmountFast(group, recipe, maxParallel);
        if (parallels <= 0) return CTNHCommonTooltips.recipeModifierInsufficientInput.translate();

        recipe.multiplyAllContents(parallels);
        recipe.parallels *= parallels;
        recipe.multiplyDuration((double) generator.efficiency / 100);
        return null;
    }
}
