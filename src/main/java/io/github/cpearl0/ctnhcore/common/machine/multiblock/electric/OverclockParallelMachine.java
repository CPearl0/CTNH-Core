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
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OverclockParallelMachine extends CoilWorkableElectricMultiblockMachine implements ITieredMachine {
    public OverclockParallelMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted public int temperature=0;
    public double eff=0.0;
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
            var eut=Math.max(0.25,1.01-0.02*parallel);
            var parallel_speed=Math.max(0.25,1.01-0.02*parallel);
            var coil_speed=Math.max(0,((int)(pmachine.temperature/1800)*0.25-0.75))+1;
            pmachine.eff=1/(parallel_speed*coil_speed);
            return  ModifierFunction.builder()
                    .parallels(parallel)
                    .eutMultiplier(eut*parallel)
                    .durationMultiplier(parallel_speed/coil_speed)
                    .inputModifier(ContentModifier.multiplier(parallel))
                    .outputModifier(ContentModifier.multiplier(parallel))
                    .build();
        }
        return ModifierFunction.NULL;

    }
    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(Component.translatable("ctnh.lcr.coil", temperature + "K"));
        textList.add(Component.translatable("ctnh.lcr.speed", eff));
    }

}
