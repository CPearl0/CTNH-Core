package io.github.cpearl0.ctnhcore.client.model;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Getter
public class ModelBase{

    public final ModelPart root;
    @Getter
    public final ModelDefinition definition;
    //在render中构造
    public ModelBase(ModelDefinition definition){
        this.definition = definition;
        this.root = definition.createBodyLayer.get().bakeRoot();
    }

}
