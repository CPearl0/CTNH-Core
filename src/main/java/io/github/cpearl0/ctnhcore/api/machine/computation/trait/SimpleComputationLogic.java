package io.github.cpearl0.ctnhcore.api.machine.computation.trait;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.computation.SimpleComputationMachine;
import lombok.Setter;
import net.minecraft.network.chat.Component;

import java.util.*;

import static com.gregtechceu.gtceu.api.GTValues.HV;

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
