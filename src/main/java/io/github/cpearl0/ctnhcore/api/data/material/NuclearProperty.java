package io.github.cpearl0.ctnhcore.api.data.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class NuclearProperty implements IMaterialProperty {
    @Getter
    @Setter
    private Material oxideMaterial;
    @Getter
    @Setter
    private Material carbideMaterial;
    @Getter
    @Setter
    private Material NitrideMaterial;
    @Getter
    @Setter
    private Material zirconiumAlloyMaterial;
    @Getter
    @Setter
    private Material fuelMaterial;
    @Getter
    @Setter
    private Material depletedFuelMaterial;
    @Getter
    @Setter
    private int heat;
    @Getter
    @Setter
    private boolean fissile;
    @Getter
    @Setter
    private boolean fertile;
    public Map<Material, Integer> fertileDecay = new HashMap<>();
    @Override
    public void verifyProperty(MaterialProperties materialProperties) {

    }
}
