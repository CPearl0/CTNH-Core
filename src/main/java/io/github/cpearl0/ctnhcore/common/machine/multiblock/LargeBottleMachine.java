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
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LargeBottleMachine extends MultiblockTankMachine {
    public LargeBottleMachine(IMachineBlockEntity holder, int capacity, @Nullable PropertyFluidFilter filter, Object... args){
        super(holder,capacity,filter,args);
    }

    @Getter
    @DescSynced
    @RequireRerender
    private final Set<BlockPos> fluidBlockOffsets = new HashSet<>();
    public final List<BlockPos> SideBlockOffsets = List.of(
            MachineUtils.getOffset(this, 0, 1, 1),
            MachineUtils.getOffset(this, 1, 1, 1),
            MachineUtils.getOffset(this, 2, 1, 1),
            MachineUtils.getOffset(this, -1, 1, 1),
            MachineUtils.getOffset(this, -2, 1, 1),
            MachineUtils.getOffset(this, -3, 1, 2),
            MachineUtils.getOffset(this, 3, 1, 2),
            MachineUtils.getOffset(this, -3, 1, 3),
            MachineUtils.getOffset(this, 3, 1, 3),
            MachineUtils.getOffset(this, -3, 1, 4),
            MachineUtils.getOffset(this, 3, 1, 4),
            MachineUtils.getOffset(this, -3, 1, 5),
            MachineUtils.getOffset(this, 3, 1, 5),
            MachineUtils.getOffset(this, -3, 1, 6),
            MachineUtils.getOffset(this, 3, 1, 6),
            MachineUtils.getOffset(this, 0, 1, 7),
            MachineUtils.getOffset(this, 1, 1, 7),
            MachineUtils.getOffset(this, 2, 1, 7),
            MachineUtils.getOffset(this, -1, 1, 7),
            MachineUtils.getOffset(this, -2, 1, 7),
            MachineUtils.getOffset(this, 0, 2, 1),
            MachineUtils.getOffset(this, 1, 2, 1),
            MachineUtils.getOffset(this, 2, 2, 1),
            MachineUtils.getOffset(this, -1, 2, 1),
            MachineUtils.getOffset(this, -2, 2, 1),
            MachineUtils.getOffset(this, -3, 2, 2),
            MachineUtils.getOffset(this, 3, 2, 2),
            MachineUtils.getOffset(this, -3, 2, 3),
            MachineUtils.getOffset(this, 3, 2, 3),
            MachineUtils.getOffset(this, -3, 2, 4),
            MachineUtils.getOffset(this, 3, 2, 4),
            MachineUtils.getOffset(this, -3, 2, 5),
            MachineUtils.getOffset(this, 3, 2, 5),
            MachineUtils.getOffset(this, -3, 2, 6),
            MachineUtils.getOffset(this, 3, 2, 6),
            MachineUtils.getOffset(this, 0, 2, 7),
            MachineUtils.getOffset(this, 1, 2, 7),
            MachineUtils.getOffset(this, 2, 2, 7),
            MachineUtils.getOffset(this, -1, 2, 7),
            MachineUtils.getOffset(this, -2, 2, 7),
            MachineUtils.getOffset(this, 0, 5, 1),
            MachineUtils.getOffset(this, 1, 5, 1),
            MachineUtils.getOffset(this, 2, 5, 1),
            MachineUtils.getOffset(this, -1, 5, 1),
            MachineUtils.getOffset(this, -2, 5, 1),
            MachineUtils.getOffset(this, -3, 5, 2),
            MachineUtils.getOffset(this, 3, 5, 2),
            MachineUtils.getOffset(this, -3, 5, 3),
            MachineUtils.getOffset(this, 3, 5, 3),
            MachineUtils.getOffset(this, -3, 5, 4),
            MachineUtils.getOffset(this, 3, 5, 4),
            MachineUtils.getOffset(this, -3, 5, 5),
            MachineUtils.getOffset(this, 3, 5, 5),
            MachineUtils.getOffset(this, -3, 5, 6),
            MachineUtils.getOffset(this, 3, 5, 6),
            MachineUtils.getOffset(this, 0, 5, 7),
            MachineUtils.getOffset(this, 1, 5, 7),
            MachineUtils.getOffset(this, 2, 5, 7),
            MachineUtils.getOffset(this, -1, 5, 7),
            MachineUtils.getOffset(this, -2, 5, 7),
            MachineUtils.getOffset(this, 0, 4, 1),
            MachineUtils.getOffset(this, 1, 4, 1),
            MachineUtils.getOffset(this, 2, 4, 1),
            MachineUtils.getOffset(this, -1, 4, 1),
            MachineUtils.getOffset(this, -2, 4, 1),
            MachineUtils.getOffset(this, -3, 4, 2),
            MachineUtils.getOffset(this, 3, 4, 2),
            MachineUtils.getOffset(this, -3, 4, 3),
            MachineUtils.getOffset(this, 3, 4, 3),
            MachineUtils.getOffset(this, -3, 4, 4),
            MachineUtils.getOffset(this, 3, 4, 4),
            MachineUtils.getOffset(this, -3, 4, 5),
            MachineUtils.getOffset(this, 3, 4, 5),
            MachineUtils.getOffset(this, -3, 4, 6),
            MachineUtils.getOffset(this, 3, 4, 6),
            MachineUtils.getOffset(this, 0, 4, 7),
            MachineUtils.getOffset(this, 1, 4, 7),
            MachineUtils.getOffset(this, 2, 4, 7),
            MachineUtils.getOffset(this, -1, 4, 7),
            MachineUtils.getOffset(this, -2, 4, 7));

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
        var capacity = getTank().getTankCapacity(0);
        var amount = getTank().getFluidInTank(0).getAmount();
        var height = 12 * (double)amount / capacity;

        for (int i = 0; i < 5; i++) {
            center = center.relative(back);
            fluidBlockOffsets.add(center.subtract(pos));
            fluidBlockOffsets.add(center.relative(clockWise).subtract(pos));
            fluidBlockOffsets.add(center.relative(counterClockWise).subtract(pos));
        }
    }
}
