package io.github.cpearl0.ctnhcore.client.model;

import lombok.Getter;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Getter
public class ModelBase{

    public final ModelPart root;
    //在render中构造
    public ModelBase(BlockEntityRendererProvider.Context context, ModelDefinition definition){
        this.root = context.bakeLayer(definition.LAYER_LOCATION);
    }

}
