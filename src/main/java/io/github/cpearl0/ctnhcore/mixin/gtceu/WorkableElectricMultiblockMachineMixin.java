package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.utils.GTUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(WorkableElectricMultiblockMachine.class)
public abstract class WorkableElectricMultiblockMachineMixin {
    @Shadow(remap = false)
    protected EnergyContainerList energyContainer;
    @Shadow(remap = false)
    public abstract EnergyContainerList getEnergyContainer();
    @Shadow(remap = false)
    public abstract boolean isGenerator();
    /**
     * @author mo_guang
     * @reason fix
     */
    @Overwrite(remap = false)
    public long getMaxVoltage() {
        if (this.energyContainer == null) {
            this.energyContainer = this.getEnergyContainer();
        }

        long highestVoltage;
        if (this.isGenerator()) {
            highestVoltage = this.energyContainer.getOutputVoltage();
            long amperage = this.energyContainer.getOutputAmperage();
            return amperage == 1L ? GTValues.VEX[GTUtil.getFloorTierByVoltage(highestVoltage)] : highestVoltage;
        } else {
            highestVoltage = this.energyContainer.getHighestInputVoltage();
            if (this.energyContainer.getNumHighestInputContainers() > 1) {
                int tier = GTUtil.getTierByVoltage(highestVoltage);
                return GTValues.V[Math.min(tier + 1, 14)];
            } else {
                return highestVoltage;
            }
        }
    }
}
