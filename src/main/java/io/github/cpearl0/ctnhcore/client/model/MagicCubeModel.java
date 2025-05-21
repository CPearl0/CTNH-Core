package io.github.cpearl0.ctnhcore.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHModelLayers;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.joml.Quaternionf;

@Getter
public class MagicCubeModel extends ModelBase {

    private final ModelPart magic_cube;
    private final ModelPart ring;
    private final ModelPart kube;

    public MagicCubeModel() {
        super(CTNHModelLayers.MAGIC_CUBE_MODEL);
        this.magic_cube = root.getChild("magic_cube");
        this.ring = this.magic_cube.getChild("ring");
        this.kube = this.magic_cube.getChild("kube");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition magic_cube = partdefinition.addOrReplaceChild("magic_cube", CubeListBuilder.create(),
                PartPose.offset(0, 0, 0));

        PartDefinition ring = magic_cube.addOrReplaceChild("ring", CubeListBuilder.create(),
                PartPose.offset(0, 0.0F, 0));

        PartDefinition ring_r1 = ring.addOrReplaceChild("ring_r1",CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-12.0F, -12.0F, -12.0F,
                            24.0F, 24.0F, 24.0F
                            ,new CubeDeformation(0.0F))
                , PartPose.offsetAndRotation(0F, 0F, 0F, -0.7854F, 0.0F, 0.7854F));

        PartDefinition kube = magic_cube.addOrReplaceChild("kube", CubeListBuilder.create()
                .texOffs(0, 48)
                .addBox(-8.0F, -8.0F, -8.0F, 16.0F
                        , 16.0F, 16.0F
                        , new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    static Quaternionf qBase=new Quaternionf().rotateAxis((float)(60/(2* Math.PI)),0.7071f,0,0.7071f);


    public void render(int ticks, PoseStack stack, VertexConsumer consumer, int combinedOverlay){
        stack.pushPose();


        //Ring1
        stack.mulPose(qBase);
        stack.mulPose(Axis.YP.rotationDegrees(ticks));
        ring.render(stack, consumer, LightTexture.FULL_BRIGHT, combinedOverlay);

        //Ring2
        stack.scale(1.25f, 1.25f, 1.25f);
        stack.mulPose(qBase);
        stack.mulPose(Axis.YP.rotationDegrees(ticks * 2));
        ring.render(stack, consumer, LightTexture.FULL_BRIGHT, combinedOverlay);

        //Kube
        stack.mulPose(qBase);
        stack.mulPose(Axis.YP.rotationDegrees(ticks * (-0.6f)));
        kube.render(stack, consumer, LightTexture.FULL_BRIGHT, combinedOverlay);

        stack.popPose();
    }
}