package io.github.cpearl0.ctnhcore.api;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import net.minecraft.resources.ResourceLocation;

public class FuelMaterial extends Material {
    public int heat;
    public boolean depleted;
    protected FuelMaterial(ResourceLocation resourceLocation, int heat) {
        super(resourceLocation);
        this.heat = heat;
        this.depleted = false;
    }
}
