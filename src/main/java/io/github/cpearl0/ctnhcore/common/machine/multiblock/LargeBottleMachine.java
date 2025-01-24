package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.fluids.PropertyFluidFilter;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.MultiblockTankMachine;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class LargeBottleMachine extends MultiblockTankMachine {
    public LargeBottleMachine(IMachineBlockEntity holder, int capacity, @Nullable PropertyFluidFilter filter, Object... args){
        super(holder,capacity,filter,args);
    }

    @Getter
    @DescSynced
    @RequireRerender
    private final Set<BlockPos> fluidBlockOffsets = new HashSet<>();

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        saveOffsets();
    }

    protected void saveOffsets() {
        Direction up = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped());
        Direction back = getFrontFacing().getOpposite();
        Direction clockWise;
        Direction counterClockWise;
        if (up == Direction.UP || up == Direction.DOWN) {
            clockWise = getFrontFacing().getClockWise();
            counterClockWise = getFrontFacing().getCounterClockWise();
        } else {
            clockWise = Direction.UP;
            counterClockWise = Direction.DOWN;


        }

        BlockPos pos = getPos();
        BlockPos center = pos.relative(up);

        for (int i = 0; i < 5; i++) {
            center = center.relative(back);
            fluidBlockOffsets.add(center.subtract(pos));
            fluidBlockOffsets.add(center.relative(clockWise).subtract(pos));
            fluidBlockOffsets.add(center.relative(counterClockWise).subtract(pos));
        }
    }
}
