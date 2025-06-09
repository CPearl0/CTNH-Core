package io.github.cpearl0.ctnhcore.common.machine.trait;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MultiblockComputationMachine;

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
