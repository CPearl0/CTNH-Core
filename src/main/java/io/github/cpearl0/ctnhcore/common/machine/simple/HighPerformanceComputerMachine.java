package io.github.cpearl0.ctnhcore.common.machine.simple;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IWorkable;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public final class HighPerformanceComputerMachine extends TieredEnergyMachine implements IOpticalComputationProvider, IWorkable {

    /*属性*/
    @Setter @Getter
    boolean isWorkingEnabled=true;
    @Getter
    final long maxInputOutputAmperage = getMaxInputOutputAmperageStatic();
    int CWUtToProduce;
    long energyToDrain;
    int lastCWUt=0;


    public HighPerformanceComputerMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        energyContainer.setSideInputCondition((direction -> direction!= getFrontFacing()));
        /*HV为1，超过HV每级翻倍*/
        CWUtToProduce = (tier>=GTValues.HV?1<<(tier-GTValues.HV):0);
        energyToDrain = (long) GTValues.VA[tier] * maxInputOutputAmperage;
    }

    @Override
    protected NotifiableEnergyContainer createEnergyContainer(Object... args) {
        var tierVoltage = GTValues.VA[tier] * getMaxInputOutputAmperageStatic();
        return NotifiableEnergyContainer.receiverContainer(this,
                tierVoltage * 64L, tierVoltage, getMaxInputOutputAmperageStatic());
    }

    @Override
    public int requestCWUt(int cwut, boolean simulate, @NotNull Collection<IOpticalComputationProvider> seen) {
       seen.add(this);
       if (isActive()) {
           if (drainEnergy(simulate)) {
               int requestedCWUt=Math.min(cwut, CWUtToProduce);
               if(!simulate) {
                   lastCWUt = cwut;
               }
               return requestedCWUt;
           }
       }
       return 0;
    }

    @Override
    public int getMaxCWUt(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return isActive() ? CWUtToProduce : 0;
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

    @Override
    public boolean isActive(){
        return energyContainer.getEnergyStored() >= (energyToDrain>>2);
    }

    public static long getMaxInputOutputAmperageStatic() {
        return 16;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getMaxProgress() {
        return 0;
    }

}
