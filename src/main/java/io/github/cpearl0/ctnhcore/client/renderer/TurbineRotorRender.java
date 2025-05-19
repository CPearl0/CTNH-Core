package io.github.cpearl0.ctnhcore.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.TurbineRotorModel;
import io.github.cpearl0.ctnhcore.common.block.TurbineRotorBlock;
import io.github.cpearl0.ctnhcore.common.blockentity.TurbineRotorBE;
import io.github.cpearl0.ctnhcore.registry.CTNHModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.EnumMap;
import io.github.cpearl0.ctnhcore.client.renderer.utils.RenderUtils;

@OnlyIn(Dist.CLIENT)
public class TurbineRotorRender implements BlockEntityRenderer<TurbineRotorBE> {

    private static final ResourceLocation TEXTURE =
            CTNHCore.id("textures/block/turbine_rotor/texture.png");
    private final TurbineRotorModel model;

    public TurbineRotorRender(BlockEntityRendererProvider.Context context) {
        this.model = new TurbineRotorModel(context, CTNHModelLayers.TURBINE_ROTOR_MODEL);
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
        if(blockEntity.isActive())blockEntity.tick();
        poseStack.pushPose();


        //调整模型位置，旋转中心一般在方块角落，要移到中心
        poseStack.translate(0.5, 0.5, 0.5);

        Direction facing = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        var modelAxis = RenderUtils.getFacingAxis(facing, Direction.UP);
        if (modelAxis != null)
            poseStack.mulPose(modelAxis.rotationDegrees(-90));  //把模型的正面转向facing方向
        else if (facing == Direction.DOWN)
            poseStack.mulPose(Axis.XP.rotationDegrees(180));  //倒过来
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getRotation()));  //转子旋转, YP为模型的正面




        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(TEXTURE));
        model.getTurbine_rotor().render(
                poseStack,
                vertexConsumer,
                packedLight,
                packedOverlay,
                1.0F,1.0F,1.0F,1.0F
        );

        poseStack.popPose();
    }
}

