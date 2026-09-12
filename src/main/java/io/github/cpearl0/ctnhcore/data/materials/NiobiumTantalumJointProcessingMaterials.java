package io.github.cpearl0.ctnhcore.data.materials;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class NiobiumTantalumJointProcessingMaterials {

    public static Material TANTALUM_ALKALINE_MIXTURE;
    public static Material TANTALITE_OXIDE;
    public static Material TANTALITE_FLUORINE;
    public static Material POTASSIUM_FLUORIDE;
    public static Material NIOBIUM_OXIDE;
    public static Material NIOBIUM_TANTALITE;

    public static void init() {
        TANTALUM_ALKALINE_MIXTURE = REGISTRATE.material(CTNHCore.id("tantalum_alkaline_mixture"))
                .cnlang("碱性铌钽混合物")
                .liquid()
                .color(0x46A3FF)
                .buildAndRegister();

        TANTALITE_OXIDE = REGISTRATE.material(CTNHCore.id("tantalite_oxide"))
                .cnlang("五氧化二钽")
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x46A3FF)
                .components(Tantalum, 2, Oxygen, 5)
                .buildAndRegister();

        TANTALITE_FLUORINE = REGISTRATE.material(CTNHCore.id("tantalite_fluorine"))
                .cnlang("铌钽复合氟化物")
                .liquid()
                .color(0x46A3AA)
                .buildAndRegister();

        POTASSIUM_FLUORIDE = REGISTRATE.material(CTNHCore.id("potassium_fluoride"))
                .cnlang("氟化钾")
                .dust()
                .color(0xFF6666)
                .components(Potassium, 1, Fluorine, 1)
                .buildAndRegister();

        NIOBIUM_OXIDE = REGISTRATE.material(CTNHCore.id("niobium_oxide"))
                .cnlang("五氧化二铌")
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xB2A9A9)
                .components(Niobium, 2, Oxygen, 5)
                .buildAndRegister();

        NIOBIUM_TANTALITE = REGISTRATE.material(CTNHCore.id("niobium_tantalite"))
                .cnlang("铌钽氧化复合物")
                .formula("Ta-Nb")
                .liquid()
                .color(0xCCFFFF)
                .buildAndRegister();
    }
}
