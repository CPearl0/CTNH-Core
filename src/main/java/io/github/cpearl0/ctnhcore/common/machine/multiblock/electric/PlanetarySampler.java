package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;

public class PlanetarySampler extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public int machine_tier;
    public int max_machine=machine_tier+1;
    public int znum=0;
    public PlanetarySampler(IMachineBlockEntity holder,int machine_tier)
    {
        super(holder);
        this.machine_tier=machine_tier;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe)
    {
        if (machine instanceof PlanetarySampler zmachine){

            if(recipe.data.getBoolean("shoot")==true && zmachine.znum<= zmachine.max_machine)
            {
                if(zmachine.znum==0)
                    return ModifierFunction.NULL;
                return  ModifierFunction.builder().build();
            }

            }

        return ModifierFunction.NULL;
    }
}
