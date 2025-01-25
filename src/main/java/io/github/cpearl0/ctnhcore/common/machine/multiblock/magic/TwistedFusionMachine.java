package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;

public class TwistedFusionMachine extends WorkableElectricMultiblockMachine {
    public TwistedFusionMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        if(recipe.getType().equals(GTRecipeTypes.FUSION_RECIPES)){
            var startEU=recipe.data.getLong("eu_to_start");
            ModifierFunction modifierFunction = ModifierFunction.IDENTITY;
            if(startEU<=160000000){
                modifierFunction = CTNHRecipeModifiers.accurateParallel(machine,recipe,64);
            }else if(startEU<=320000000){
                modifierFunction = CTNHRecipeModifiers.accurateParallel(machine,recipe,16);
            }else if(startEU<=480000000){
                modifierFunction = CTNHRecipeModifiers.accurateParallel(machine,recipe,4);
            }
            return modifierFunction;
        }
        return ModifierFunction.IDENTITY;
    }
}
