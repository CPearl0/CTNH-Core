package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;

public class EternalGarden extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public EternalGarden(IMachineBlockEntity holder){
        super(holder);
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {

        if(machine instanceof EternalGarden mmachine)
        {
            int tier=mmachine.getTier();
            int recipe_tier= RecipeHelper.getRecipeEUtTier(recipe);
            if(recipe.data.getString("type").equals("hydroangeas"))
            {
                int maxparallel=8+Math.max((tier-3),0)*4;
                int parallel= ParallelLogic.getParallelAmount(machine,recipe,maxparallel);
                return  ModifierFunction.builder()
                        .parallels(parallel)
                        .inputModifier(ContentModifier.multiplier(parallel))
                        .outputModifier(ContentModifier.multiplier(parallel*(1+0.2*Math.max(tier-recipe_tier,1))))
                        .build();
            }
        }
    return ModifierFunction.NULL;
    }
}
