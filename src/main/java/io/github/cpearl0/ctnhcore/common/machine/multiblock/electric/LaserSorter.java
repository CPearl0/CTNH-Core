package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.data.recipe.utils.ComputationModifier;
import io.github.cpearl0.ctnhcore.utils.CTNHRecipeHelper;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.utils.GTUtil;

import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

import static com.gregtechceu.gtceu.api.GTValues.IV;

public class LaserSorter extends MultiblockComputationMachine {

    public LaserSorter(IMachineBlockEntity holder) {
        super(holder);
    }

    public int get_base(GTRecipe recipe) {
        final var inputCWUt = CTNHRecipeHelper.getInputCWUt(recipe);
        return inputCWUt > 0 ? inputCWUt :
                Math.max(8 * (GTUtil.getTierByVoltage(RecipeHelper.getRealEUtWithIO(recipe)) - IV), 8);
    }

    public int caculate_effency(@Nullable GTRecipe recipe) {
        var maxcwut = getCurrentCWUt();
        return maxcwut / get_base(recipe);
    }

    public int get_true_cwut(@Nullable GTRecipe recipe) {
        var base = get_base(recipe);
        var maxcwut = getCurrentCWUt();

        return (maxcwut / base) * base;
    }

    public boolean check_right(GTRecipe recipe) {
        return get_true_cwut(recipe) == getCurrentCWUt();
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof LaserSorter lmachine) {
            var input = 1.0;
            var muti = 0.25;
            var parallel = 1;
            if (lmachine.check_right(recipe)) {
                muti = lmachine.caculate_effency(recipe);
                parallel = (int) (muti * muti * muti);
                if (recipe.recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES)) {
                    input = 1.25;
                }
            }
            var maxparallel = ParallelLogic.getParallelAmount(group, recipe, parallel);
            int allowOverload = lmachine.getTier() - recipe.tier;

            recipe.multiplyInputs(maxparallel);
            recipe.multiplyOutputs((int) (maxparallel * input));
            recipe.multiplyDuration(1 / Math.pow(2, Math.min(allowOverload, muti)));
            recipe.multiplyEUt(maxparallel);
            recipe.parallels *= maxparallel;
            int true_cwut = lmachine.get_true_cwut(recipe);
            if (true_cwut > 0) {
                ComputationModifier.append(recipe, true_cwut);
            }
            return null;
        }
        return RecipeModifier.nullWrongType(LaserSorter.class, machine);
    }
}
