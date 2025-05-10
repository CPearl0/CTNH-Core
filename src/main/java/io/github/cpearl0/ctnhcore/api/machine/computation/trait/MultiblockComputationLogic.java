package io.github.cpearl0.ctnhcore.api.machine.computation.trait;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import io.github.cpearl0.ctnhcore.api.machine.computation.MultiblockComputationMachine;

public class MultiblockComputationLogic extends ComputationLogic {
    public MultiblockComputationLogic(MultiblockComputationMachine machine) {
        super(machine);
    }
    @Override
    public boolean checkCWUt(GTRecipe recipe, boolean simulate){
        if(computationContainer==null)
            if(machine instanceof MultiblockComputationMachine)
                computationContainer=((MultiblockComputationMachine)machine).getComputationProvider();
        return super.checkCWUt(recipe,simulate);
    }


}
