package io.github.cpearl0.ctnhcore.common.machine.simple;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class HighPerformanceComputer extends TieredEnergyMachine implements IOpticalComputationProvider {

    /*属性*/
    boolean isActive=true;
    int CWUtToProduce;
    long energyToDrain;
    int lastCWUt=0;

    public HighPerformanceComputer(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        /*HV为1，超过HV每级翻倍*/
        CWUtToProduce = (tier>=GTValues.HV?1<<(tier-GTValues.HV):0);
        energyToDrain = GTValues.V[tier];
    }


    @Override
    public int requestCWUt(int cwut, boolean simulate, @NotNull Collection<IOpticalComputationProvider> seen) {
       seen.add(this);
       if (isActive) {
           if (drainEnergy(simulate)) {
               int requestedCWUt=Math.min(cwut, CWUtToProduce);
               if(!simulate)lastCWUt=cwut;
               return requestedCWUt;
           }
       }
       return 0;
    }

    @Override
    public int getMaxCWUt(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return isActive ? CWUtToProduce : 0;
    }

    @Override
    public boolean canBridge(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return true;
    }

    private boolean drainEnergy(boolean simulate) {
        long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
            if (!simulate) energyContainer.removeEnergy(energyToDrain);
            return true;
        }
        return false;
    }

}
