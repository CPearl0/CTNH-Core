package io.github.cpearl0.ctnhcore.common.block;

import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class AstralSaplingBlock extends SaplingBlock {

    public AstralSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(CTNHBlocks.ASTRAL_DIRT.get()) || pState.is(CTNHBlocks.ASTRAL_GRASS_BLOCK.get());
    }
}
