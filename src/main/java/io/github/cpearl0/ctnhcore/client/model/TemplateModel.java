package io.github.cpearl0.ctnhcore.client.model;

import io.github.cpearl0.ctnhcore.CTNHCore;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

//导入BlockBench的模型
@Getter
public class TemplateModel extends ModelBase {

    public TemplateModel(BlockEntityRendererProvider.Context context, ModelDefinition definition) {
        super(context, definition);
        //其他的部件定义
    }

    public static LayerDefinition createBodyLayer() {
        throw new UnsupportedOperationException("模型类里一定要有模型");
    }
}
