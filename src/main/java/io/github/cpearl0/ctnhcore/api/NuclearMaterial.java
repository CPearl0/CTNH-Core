package io.github.cpearl0.ctnhcore.api;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.resources.ResourceLocation;

public class NuclearMaterial extends Material {
    public Material getCarbideMaterial() {
        return new Material.Builder(GTCEu.id("")).get();
    }
    protected NuclearMaterial(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }
}
