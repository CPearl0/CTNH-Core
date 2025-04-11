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
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.Nicoll_Dyson_Beams;
import org.jetbrains.annotations.NotNull;

public class OverclockParallelMachine extends CoilWorkableElectricMultiblockMachine implements ITieredMachine {
    public OverclockParallelMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted public int temperature=0;
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
        if(machine instanceof OverclockParallelMachine pmachine) {
            var speed=1;
            int parallel= ParallelLogic.getParallelAmount(machine,recipe,pa);
            var eut=Math.max(0.25,1.01-0.01*parallel);
            var parallel_speed=Math.max(0.5,1.01-0.01*parallel);
            var coil_speed=((int) pmachine.temperature /1800)*0.5+1-0.5;
            return  ModifierFunction.builder()
                    .parallels(pa)
                    .eutMultiplier(eut)
                    .durationMultiplier(parallel_speed/coil_speed)
                    .inputModifier(ContentModifier.multiplier(pa))
                    .outputModifier(ContentModifier.multiplier(pa))
                    .build();
        }
        return ModifierFunction.NULL;

    }

}
