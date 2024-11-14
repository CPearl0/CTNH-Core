package io.github.cpearl0.ctnhcore.registry;


import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHMaterials.*;

public class GTMaterialAddon {
    public static void init() {
        Duranium.addFlags(GENERATE_FRAME);
        Naquadria.addFlags(GENERATE_FRAME);
        NaquadahEnriched.addFlags(GENERATE_FRAME);
        Naquadah.addFlags(GENERATE_FRAME);
        //resetAluminium(Emerald,Beryllium);
    }
    public static void resetAluminium(Material material, Material byproduct1) {
        var oreproperty = material.getProperty(PropertyKey.ORE);
        material.setProperty(PropertyKey.ORE, new OreProperty(oreproperty.getOreMultiplier(),
                                oreproperty.getByProductMultiplier()));
        material.getProperty(PropertyKey.ORE).setOreByProducts(byproduct1,Alumina);
    }
}
