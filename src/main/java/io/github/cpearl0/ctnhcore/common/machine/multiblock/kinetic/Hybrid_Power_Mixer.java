package io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.KineticElectricMutiblockMachine;

public class Hybrid_Power_Mixer extends KineticElectricMutiblockMachine implements ITieredMachine {
    public Hybrid_Power_Mixer(IMachineBlockEntity holder) {
        super(holder);
    }
    @Override
    public int getTier() {

        var Ktier=this.Ktier+1;
        if(this.speed>=256)Ktier+=1.0;
        var Etier= GTUtil.getFloorTierByVoltage(getMaxVoltage());
        return Math.min(Ktier,Etier);
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
}
