package io.github.cpearl0.ctnhcore.client.model;

import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.Supplier;

public class ModelDefinition {
    public ModelDefinition(String name, Supplier<LayerDefinition> createBodyLayer) {
        this.name = name;
        this.LAYER_LOCATION = new ModelLayerLocation(CTNHCore.id(name),"main");
        this.createBodyLayer = createBodyLayer;
    }
    public String name;
    public ModelLayerLocation LAYER_LOCATION ;
    public Supplier<LayerDefinition> createBodyLayer;
}
