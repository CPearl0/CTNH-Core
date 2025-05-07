package io.github.cpearl0.ctnhcore.api.machine.trait;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class SimpleComputationContainer extends NotifiableComputationContainer {
    protected long lastTimeStamp;
    private int currentOutputCwu = 0;
    private int lastOutputCwu = 0;
    IOpticalComputationProvider computationProvider;

    public SimpleComputationContainer(MetaMachine machine) {
        super(machine, IO.IN, false);
    }


    @Override
    public int requestCWUt(int cwut, boolean simulate, @NotNull Collection<IOpticalComputationProvider> seen) {
        var latestTimeStamp = getMachine().getOffsetTimer();
        if (lastTimeStamp < latestTimeStamp) {
            lastOutputCwu = currentOutputCwu;
            currentOutputCwu = 0;
            lastTimeStamp = latestTimeStamp;
        }
        seen.add(this);
        var provider = getComputationProvider();
        return provider != null ? provider.requestCWUt(cwut, simulate, seen) : 0;
    }

    @Override
    public int getMaxCWUt(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        var provider = getComputationProvider();
        return provider != null ? provider.getMaxCWUt(seen) : 0;
    }

    @Override
    public boolean canBridge(@NotNull Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return true;
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        if(computationProvider !=null)return computationProvider;
        updateCWUtProvider();
        return computationProvider;
    }

    public void updateCWUtProvider()
    {
        /*这里有隔空连算力的bug，先不急（*/
        for (Direction direction : GTUtil.DIRECTIONS) {
            BlockEntity blockEntity = machine.getLevel().getBlockEntity(machine.getPos().relative(direction));
            if (blockEntity == null) continue;
            computationProvider = blockEntity.getCapability(GTCapability.CAPABILITY_COMPUTATION_PROVIDER, direction.getOpposite()).orElse(null);
            if(computationProvider !=null) return;
        }
    }
}
