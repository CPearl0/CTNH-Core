package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import org.jetbrains.annotations.NotNull;

public class SuperEBF extends CoilWorkableElectricMultiblockMachine implements ITieredMachine {
    public SuperEBF(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted
    public int temperature=0;
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        temperature = getCoilType().getCoilTemperature();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        int pa=1;
        if (machine instanceof IMultiController controller) {
            if (controller.isFormed()) {
                int parallels = (Integer)controller.getParallelHatch().map((hatch) -> ParallelLogic.getParallelAmount(machine, recipe, hatch.getCurrentParallel())).orElse(0);
                if (parallels > 0) {
                    pa=parallels;
                }

            }
        }
        if(machine instanceof SuperEBF pmachine) {
            var speed=1;
            int parallel= ParallelLogic.getParallelAmount(machine,recipe,pa);
            return  ModifierFunction.builder()
                    .parallels(pa)
                    .durationMultiplier(0.5)
                    .inputModifier(ContentModifier.multiplier(pa))
                    .outputModifier(ContentModifier.multiplier(pa))
                    .build();
        }
        return ModifierFunction.NULL;

    }

}