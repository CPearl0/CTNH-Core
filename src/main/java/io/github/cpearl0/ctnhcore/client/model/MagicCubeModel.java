package io.github.cpearl0.ctnhcore.client.model;


import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHModelLayers;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Getter
public class MagicCubeModel extends ModelBase {

    private final ModelPart magic_cube;
    private final ModelPart ring;

    public MagicCubeModel(BlockEntityRendererProvider.Context context) {
        super(context, CTNHModelLayers.MAGIC_CUBE_MODEL);
        this.magic_cube = root.getChild("magic_cube");
        this.ring = magic_cube.getChild("ring");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition magic_cube = partdefinition.addOrReplaceChild("magic_cube", CubeListBuilder.create().texOffs(0, 48).addBox(-14.0F, -17.0F, 9.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 25.0F, -10.0F));

        PartDefinition ring = magic_cube.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -25.0F, -1.0F, 24.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}