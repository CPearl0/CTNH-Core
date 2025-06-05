package io.github.cpearl0.ctnhcore.mixin;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import dev.arbor.gtnn.api.machine.multiblock.ChemicalPlantMachine;
import dev.arbor.gtnn.api.machine.multiblock.part.CatalystHatchPartMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CatalystHatchPartMachine.class)
public class CatalystHatchPartMachineMixin extends MultiblockPartMachine {
    public CatalystHatchPartMachineMixin(IMachineBlockEntity holder) {
        super(holder);
    }

    /**
     * @author mo_guang
     * @reason fix
     */
    @Overwrite(remap = false)
    private float getChance(){
        for(var controller : controllers){
            if(controller instanceof ChemicalPlantMachine){
                return ((ChemicalPlantMachine) controller).getChance()  / 100f;
            }
        }
        return 1f;
    }
}
