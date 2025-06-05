package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;

import static com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys.PLASMA;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
public class Plasma_alloy_blast extends CoilWorkableElectricMultiblockMachine {
    public Plasma_alloy_blast(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted public int machine_level=0;
    @Persisted public int islasor=0;
    public void onStructureFormed() {
        super.onStructureFormed();
        var tier = getTier();
        var coil_tier=getCoilTier();
        var coil_type=getCoilType().getCoilTemperature();
        islasor=0;
        machine_level=coil_type/1800;
        for (IMultiPart part : getParts()) {
            if(part.self().holder.self().getCapability(GTCapability.CAPABILITY_LASER).isPresent())
            {
                islasor=1;
            }
        }

    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof Plasma_alloy_blast pmachine){
            var speed=1.0;
            var output=1.0;
            var eut=1.0;
            var total_speed=1.0;
            int parallel= ParallelLogic.getParallelAmount(machine,recipe,pmachine.machine_level*4);
            if(MachineUtils.inputFluid(Iron.getFluid(PLASMA,200*parallel),pmachine))
            {
                speed=4.0;
            }
            if(MachineUtils.inputFluid(Nickel.getFluid(PLASMA,200*parallel),pmachine))
            {
                speed=4.0;
            }
            total_speed+=speed;
            if(MachineUtils.inputFluid(Argon.getFluid(PLASMA,300*parallel),pmachine))
            {
                speed=3.0;
            }
            if(MachineUtils.inputFluid(Oxygen.getFluid(PLASMA,300*parallel),pmachine))
            {
                speed=3.0;
            }
            if(MachineUtils.inputFluid(Nitrogen.getFluid(PLASMA,300*parallel),pmachine))
            {
                speed=3.0;
            }
            total_speed+=speed;
            if(MachineUtils.inputFluid(Helium.getFluid(PLASMA,500*parallel),pmachine))
            {
                speed=2.0;
            }
            total_speed=speed;
            if(pmachine.getCoilType().getCoilTemperature()>10000)
            {
                total_speed+=((double) (pmachine.getCoilType().getCoilTemperature() - 10000) /1000);
            }
            if(MachineUtils.inputFluid(CTNHMaterials.COMPRESSED_ADAMANTITE.getFluid(PLASMA,100),pmachine))
            {
                total_speed*=5;
                eut=2.0;
            }
            if(MachineUtils.inputFluid(CTNHMaterials.COMPRESSED_AETHER.getFluid(PLASMA,50*parallel),pmachine))
            {
                total_speed*=10;
                output=1-0.2*(Math.random());
            }
            speed=speed*(0.25*pmachine.islasor);
            if(speed>50)
            {
                output=0.5*(Math.random());
            }
            if(speed<=1)
            {
                return ModifierFunction.NULL;
            }
            return ModifierFunction.builder()
                    .parallels(parallel)
                    .inputModifier(ContentModifier.multiplier(parallel))
                    .outputModifier(ContentModifier.multiplier(parallel*output))
                    .eutMultiplier(eut)
                    .durationMultiplier(1/total_speed)
                    .build();
        }
        return ModifierFunction.NULL;

    }
}
