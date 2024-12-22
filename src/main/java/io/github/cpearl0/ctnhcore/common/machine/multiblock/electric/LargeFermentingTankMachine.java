package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.MultiblockTankMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHMultiblockMachines;
import net.minecraft.world.level.material.Fluids;

public class LargeFermentingTankMachine extends FermentingTankMachine{
    public LargeFermentingTankMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if(getOffsetTimer() % 20 == 0){
            var level = getLevel();
            var pos1 = MachineUtils.getOffset(this,13,0,1);
            if(!level.getBlockState(pos1).getBlock().equals(CTNHMultiblockMachines.LARGE_BOTTLE.getBlock())){
                return true;
            }
            var machine1 = MetaMachine.getMachine(level,pos1);
            if (machine1 instanceof MultiblockTankMachine tankMachine) {
                var tank = tankMachine.getTank();
                if(tank.getFluidInTank(0).getFluid().equals(Fluids.WATER) && tank.getFluidInTank(0).getAmount() >= 100){
                    tank.getFluidInTank(0).setAmount(tank.getFluidInTank(0).getAmount() - 100);
                    Lower_limit = 0.5;
                }
                else if(tank.getFluidInTank(0).getFluid().equals(GTMaterials.SterileGrowthMedium.getFluid()) && tank.getFluidInTank(0).getAmount() >= 100){
                    tank.getFluidInTank(0).setAmount(tank.getFluidInTank(0).getAmount() - 100);
                    Lower_limit = 2;
                }
                else if(tank.getFluidInTank(0).getFluid().equals(CTNHMaterials.SimpleGrowthMedium.getFluid()) && tank.getFluidInTank(0).getAmount() >= 100){
                    tank.getFluidInTank(0).setAmount(tank.getFluidInTank(0).getAmount() - 100);
                    Lower_limit = 1.5;
                }
                else{
                    Lower_limit = 0.2;
                }
            }
        }
        return super.onWorking();
    }
}
