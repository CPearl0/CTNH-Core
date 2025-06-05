package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;

public class ManaCondenserMachine extends WorkableElectricMultiblockMachine {
    public ManaCondenserMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        return ModifierFunction.IDENTITY;
    }
}
