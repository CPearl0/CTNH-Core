package io.github.cpearl0.ctnhcore.client.renderer;

import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.lowdragmc.lowdraglib.client.renderer.impl.IModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.TurbineRotorModel;
import io.github.cpearl0.ctnhcore.common.block.TurbineRotorBlock;
import io.github.cpearl0.ctnhcore.common.blockentity.TurbineRotorBE;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;

import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;

public class TurbineRotorRender implements IRenderer {

    public final static TurbineRotorRender INSTANCE = new TurbineRotorRender();

    private static final ResourceLocation TEXTURE =
            CTNHCore.id("textures/block/turbine_rotor/texture.png");
    @Getter
    private static final TurbineRotorModel model = new TurbineRotorModel();

    @Override
    public void renderItem(ItemStack stack, ItemDisplayContext transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model) {
        TurbineRotorBlock block = (TurbineRotorBlock)((BlockItem)stack.getItem()).getBlock();

        poseStack.pushPose();
        if(transformType == ItemDisplayContext.GUI) poseStack.scale(0.15f,0.15f,0.15f);

        poseStack.mulPose(Axis.XP.rotationDegrees(40));
        poseStack.mulPose(Axis.YP.rotationDegrees(RenderUtils.getTime()));  //转子旋转, YP为模型的正面

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(TEXTURE));
        TurbineRotorRender.model.getTurbine_rotor().render(
                poseStack,
                vertexConsumer,
                combinedLight,
                combinedOverlay,
                block.getR(),
                block.getG(),
                block.getB(),
                block.getA()
        );

        poseStack.popPose();
    }



    @Override
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
//        assert blockEntity instanceof TurbineRotorBE;
        var trbe = (TurbineRotorBE) blockEntity;
        TurbineRotorBlock block = (TurbineRotorBlock) trbe.getBlockState().getBlock();
        stack.pushPose();



        //调整模型位置，旋转中心一般在方块角落，要移到中心
        stack.translate(0.5, 0.5, 0.5);

        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        var modelAxis = RenderUtils.getFacingAxis(facing, Direction.UP);
        if (modelAxis != null)
            stack.mulPose(modelAxis.rotationDegrees(-90));  //把模型的正面转向facing方向
        else if (facing == Direction.DOWN)
            stack.mulPose(Axis.XP.rotationDegrees(180));  //倒过来
        if(block.isActive(blockEntity.getBlockState()))
            stack.mulPose(Axis.YP.rotationDegrees(RenderUtils.getTime() * trbe.getSpeed()));  //转子旋转, YP为模型的正面




        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(TEXTURE));
        model.getTurbine_rotor().render(
                stack,
                vertexConsumer,
                combinedLight,
                combinedOverlay,
                block.getR(),
                block.getG(),
                block.getB(),
                block.getA()
        );

        stack.popPose();
    }

    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }
}

