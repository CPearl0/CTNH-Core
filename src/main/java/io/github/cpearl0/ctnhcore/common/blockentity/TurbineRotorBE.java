package io.github.cpearl0.ctnhcore.common.blockentity;

import io.github.cpearl0.ctnhcore.registry.CTNHBlockEntities;
import lombok.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class TurbineRotorBE extends BlockEntity {
    public TurbineRotorBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    public static TurbineRotorBE create(BlockPos pPos, BlockState pBlockState) {
        return new TurbineRotorBE(CTNHBlockEntities.TURBINE_ROTOR.get(), pPos, pBlockState);
    }
    private int speed=1;//旋转的角速度，默认为20度/s

    @Getter
    @Setter
    private int rotation=0;
    @Getter
    @Setter
    private boolean active=false;
    public void tick() {
        rotation=(rotation+speed)%360;
    }

}
