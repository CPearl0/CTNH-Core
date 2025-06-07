package io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.mo_guang.ctpp.common.machine.IKineticMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.KineticElectricMutiblockMachine;
import org.jetbrains.annotations.Nullable;

public class Hybrid_Power_Mixer extends KineticElectricMutiblockMachine implements ITieredMachine {
    public Hybrid_Power_Mixer(IMachineBlockEntity holder) {
        super(holder);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof Hybrid_Power_Mixer zmachine){
            var muti=1.0;
            var energy=1.0;
            var tier=zmachine.getTier();
            if(zmachine.speed>=64)
            {
                muti=0.8;
            }
            if(zmachine.speed>=128)
            {
                muti=0.8*(1-0.6*(zmachine.speed-128)/128);
                if(zmachine.Ktier>=zmachine.getTier())
                {
                    energy=1-(0.67*((zmachine.speed-128)/128));
                }
            }
            return  ModifierFunction.builder()
                    .durationMultiplier(muti)
                    .eutMultiplier(energy)
                    .build();
        }
        return ModifierFunction.NULL;

    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        boolean result = super.beforeWorking(recipe);
        this.previousSpeed = this.speed;
        this.speed = 256.0F;

        for(IMultiPart part : this.getParts()) {
            if (part instanceof IKineticMachine kineticPart) {
                if (this.inputPartsMax.contains(kineticPart.getKineticHolder().getBlockPos())) {
                    this.speed = Math.min(256, Math.abs(kineticPart.getKineticHolder().getSpeed()));
                }
            }
        }

        this.updateRotateBlocks(result);

        if(this.speed<64)
        {
            return false;
        }

        return result;
    }
}
