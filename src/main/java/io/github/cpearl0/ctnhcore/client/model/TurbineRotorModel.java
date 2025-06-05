package io.github.cpearl0.ctnhcore.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHModelLayers;
import lombok.Getter;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

@Getter
public class TurbineRotorModel extends ModelBase {
    private final ModelPart turbine_rotor;
    private final ModelPart hub;
    private final ModelPart shaft;
    private final ModelPart blade;

    public TurbineRotorModel(){
        super(CTNHModelLayers.TURBINE_ROTOR_MODEL);
        this.turbine_rotor = root.getChild("turbine_rotor");
        this.hub = this.turbine_rotor.getChild("hub");
        this.shaft = this.turbine_rotor.getChild("shaft");
        this.blade = this.turbine_rotor.getChild("blade");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition turbine_rotor = partdefinition.addOrReplaceChild("turbine_rotor", CubeListBuilder.create(), PartPose.offset(0, 2, 0));/*记得把模型的转轴调到中心*/

        PartDefinition hub = turbine_rotor.addOrReplaceChild("hub", CubeListBuilder.create().texOffs(0, 128).addBox(-7.0F, -12.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition shaft = turbine_rotor.addOrReplaceChild("shaft", CubeListBuilder.create().texOffs(56, 128).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(80, 128).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition blade = turbine_rotor.addOrReplaceChild("blade", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition bone1 = blade.addOrReplaceChild("bone1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bone1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone2 = blade.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone3 = blade.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 32).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone4 = blade.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r4 = bone4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 48).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone5 = blade.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r5 = bone5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 64).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone6 = blade.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = bone6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 80).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone7 = blade.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r7 = bone7.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 96).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone8 = blade.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r8 = bone8.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 112).addBox(-72.0F, -2.0F, -7.0F, 65.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
}