package io.github.cpearl0.ctnhcore.common.block;

import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AstralFlowerBlock extends FlowerBlock {
    public AstralFlowerBlock(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(CTNHBlocks.ASTRAL_DIRT.get()) || pState.is(CTNHBlocks.ASTRAL_GRASS_BLOCK.get());
    }
}
