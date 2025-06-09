package io.github.cpearl0.ctnhcore.common.machine.trait;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import io.github.cpearl0.ctnhcore.common.machine.simple.SimpleComputationMachine;

public class SimpleComputationLogic extends ComputationLogic {

    public SimpleComputationLogic(SimpleComputationMachine machine) {
        super(machine);
    }

    @Override
    public boolean checkCWUt(GTRecipe recipe,boolean simulate){
        if(computationContainer==null)
            if(machine instanceof SimpleComputationMachine)
                computationContainer=((SimpleComputationMachine)machine).getComputationProvider();
        return super.checkCWUt(recipe,simulate);
    }

}
