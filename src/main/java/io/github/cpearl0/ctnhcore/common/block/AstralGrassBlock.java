package io.github.cpearl0.ctnhcore.common.block;

import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AstralGrassBlock extends TallGrassBlock {
    public AstralGrassBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(CTNHBlocks.ASTRAL_DIRT.get()) || pState.is(CTNHBlocks.ASTRAL_GRASS_BLOCK.get());
    }
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if ((CTNHBlocks.ASTRAL_TALL_GRASS.get()).defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(level, (CTNHBlocks.ASTRAL_TALL_GRASS.get()).defaultBlockState(), pos, 2);
        }
    }
}
