package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import org.jetbrains.annotations.Nullable;

public class ManaMachine extends WorkableElectricMultiblockMachine {
    public int parallel;
    public int basicConsumption;
    public ManaMachine(IMachineBlockEntity holder, int Parallel, int consumption) {
        super(holder);
        this.parallel = Parallel;
        this.basicConsumption = consumption;
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if (MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid((int) (Math.pow(2, tier) * basicConsumption)),this)) {
                return super.onWorking();
            }
            getRecipeLogic().setProgress(0);
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var tier = getTier();
        if (MachineUtils.canInputFluid(CTNHMaterials.Mana.getFluid((int) (Math.pow(2, tier) * basicConsumption)),this)){
            return super.beforeWorking(recipe);
        }
        return false;
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof ManaMachine mmachine) {
            return CTNHRecipeModifiers.accurateParallel(mmachine,recipe,mmachine.parallel);
        }
        return ModifierFunction.IDENTITY;
    }
}
