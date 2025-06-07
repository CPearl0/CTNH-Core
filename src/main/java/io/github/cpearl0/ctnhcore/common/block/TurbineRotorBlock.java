package io.github.cpearl0.ctnhcore.common.block;

import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.renderer.TurbineRotorRender;
import io.github.cpearl0.ctnhcore.common.blockentity.TurbineRotorBE;
import io.github.cpearl0.ctnhcore.registry.CTNHBlockEntities;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;


public class TurbineRotorBlock extends ActiveBlock implements EntityBlock {

    @Getter
    float R,G,B,A;//颜色通道

    public TurbineRotorBlock(Properties pProperties,float r,float g,float b,float a) {
        super(pProperties.noOcclusion());
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
        this.R = r;
        this.G = g;
        this.B = b;
        this.A = a;
    }
    public static NonNullFunction<Properties,TurbineRotorBlock> create(float r, float g, float b, float a) {
        return (p) -> new TurbineRotorBlock(p,r,g,b,a);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TurbineRotorBE(CTNHBlockEntities.TURBINE_ROTOR.get(), pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BlockStateProperties.FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        super.getStateForPlacement(context);
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}
