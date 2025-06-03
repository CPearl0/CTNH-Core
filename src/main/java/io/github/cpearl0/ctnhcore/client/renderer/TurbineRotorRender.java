package io.github.cpearl0.ctnhcore.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.TurbineRotorModel;
import io.github.cpearl0.ctnhcore.common.block.TurbineRotorBlock;
import io.github.cpearl0.ctnhcore.common.blockentity.TurbineRotorBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;

import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;

public class TurbineRotorRender implements BlockEntityRenderer<TurbineRotorBE> {

    private static final ResourceLocation TEXTURE =
            CTNHCore.id("textures/block/turbine_rotor/texture.png");
    private static final TurbineRotorModel model = new TurbineRotorModel();

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldRenderOffScreen(TurbineRotorBE pBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 24;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(
            TurbineRotorBE blockEntity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay
    ) {
        TurbineRotorBlock block = (TurbineRotorBlock) blockEntity.getBlockState().getBlock();
        poseStack.pushPose();


        //调整模型位置，旋转中心一般在方块角落，要移到中心
        poseStack.translate(0.5, 0.5, 0.5);

        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        var modelAxis = RenderUtils.getFacingAxis(facing, Direction.UP);
        if (modelAxis != null)
            poseStack.mulPose(modelAxis.rotationDegrees(-90));  //把模型的正面转向facing方向
        else if (facing == Direction.DOWN)
            poseStack.mulPose(Axis.XP.rotationDegrees(180));  //倒过来
        if(block.isActive(blockEntity.getBlockState()))
            poseStack.mulPose(Axis.YP.rotationDegrees((blockEntity.tick()) * blockEntity.getSpeed()));  //转子旋转, YP为模型的正面




        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(TEXTURE));
        model.getTurbine_rotor().render(
                poseStack,
                vertexConsumer,
                packedLight,
                packedOverlay,
                block.getR(),
                block.getG(),
                block.getB(),
                block.getA()
        );

        poseStack.popPose();
    }
}

