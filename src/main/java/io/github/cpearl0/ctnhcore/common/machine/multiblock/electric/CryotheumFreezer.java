package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import org.jetbrains.annotations.Nullable;

public class CryotheumFreezer extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public CryotheumFreezer(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var tier = getTier();
        if (MachineUtils.inputFluid(CTNHMaterials.Cryotheum.getFluid((int) (Math.pow(4, (tier - 4)) * 10)),this)){
            return super.beforeWorking(recipe);
        }
        getRecipeLogic().interruptRecipe();
        return false;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        int parallel = ParallelLogic.getParallelAmount(machine,recipe,16);
        var reduce = new ContentModifier(0.75 * parallel,0);
        if(parallel==0)
            return ModifierFunction.NULL;
        return ModifierFunction.builder()
                .eutModifier(reduce)
                .inputModifier(ContentModifier.multiplier(parallel))
                .outputModifier(ContentModifier.multiplier(parallel))
                .durationMultiplier(1/1.25)
                .parallels(parallel)
                .build();
    }
}
