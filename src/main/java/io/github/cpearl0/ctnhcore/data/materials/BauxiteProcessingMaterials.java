package io.github.cpearl0.ctnhcore.data.materials;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class BauxiteProcessingMaterials {

    public static Material POTASSIUM_METABI_SULFITE;
    public static Material SODIUM_FLUORIDE;
    public static Material ALUMINIUM_TRIFLUORIDE;
    public static Material SODIUM_HYDROXIDE_BAUXITE;
    public static Material IMPURE_SODIUM_ALUMINATE_SOLUTION;
    public static Material PURE_SODIUM_ALUMINATE_SOLUTION;
    public static Material RED_MUD;
    public static Material NEUTRALISED_RED_MUD;
    public static Material RED_SLURRY;
    public static Material TITANYL_SULFATE;
    public static Material FERRIC_REE_CHLORIDE;
    public static Material ALUMINIUM_HYDROXIDE;
    public static Material SODIUM_HYDROXIDE_SOLUTION;
    public static Material RARE_EARTH_CHLORIDE_SOLUTION;
    public static Material REFINING_TITANIUM_TETRACHLORIDE;
    public static Material ALUMINIUM_CHLORIDE;
    public static Material TITANIUM_TETRACHLORIDE_V;

    public static void init() {
        POTASSIUM_METABI_SULFITE = REGISTRATE.material(CTNHCore.id("potassium_metabi_sulfite"))
                .cnlang("焦亚硫酸钾")
                .formula("K2S2O2")
                .dust()
                .color(0x8D6939)
                .buildAndRegister();

        SODIUM_FLUORIDE = REGISTRATE.material(CTNHCore.id("sodium_fluoride"))
                .cnlang("氟化钠")
                .formula("NaF")
                .dust()
                .color(0x480214)
                .buildAndRegister();

        ALUMINIUM_TRIFLUORIDE = REGISTRATE.material(CTNHCore.id("aluminium_trifluoride"))
                .cnlang("三氟化铝")
                .formula("AlF3")
                .dust()
                .color(0x9B513E)
                .buildAndRegister();

        SODIUM_HYDROXIDE_BAUXITE = REGISTRATE.material(CTNHCore.id("sodium_hydroxide_bauxite"))
                .cnlang("氢氧化钠-铝土矿混合物")
                .formula("(TiO2)(?)(Al2O3)2+4NaOH+nH2O")
                .liquid()
                .color(0xA46D2C)
                .buildAndRegister();

        IMPURE_SODIUM_ALUMINATE_SOLUTION = REGISTRATE.material(CTNHCore.id("impure_sodium_aluminate_solution"))
                .cnlang("含杂偏铝酸钠溶液")
                .formula("(TiO2)(?)+4NaAl(OH)4+nH2O")
                .liquid()
                .color(0xC95D3A)
                .buildAndRegister();

        PURE_SODIUM_ALUMINATE_SOLUTION = REGISTRATE.material(CTNHCore.id("pure_sodium_aluminate_solution"))
                .cnlang("纯净偏铝酸钠溶液")
                .formula("Al(OH)3+NaOH+H2O")
                .liquid()
                .color(0x803DC2)
                .buildAndRegister();

        RED_MUD = REGISTRATE.material(CTNHCore.id("red_mud"))
                .cnlang("赤泥")
                .formula("(TiO2)(Fe(OH)3)(?)+nH2O")
                .liquid()
                .color(0xB2370D)
                .buildAndRegister();

        NEUTRALISED_RED_MUD = REGISTRATE.material(CTNHCore.id("neutralised_red_mud"))
                .cnlang("中和赤泥")
                .formula("TiO2+(FeCl3)(?)+nH2O")
                .liquid()
                .color(0xAF3C15)
                .buildAndRegister();

        RED_SLURRY = REGISTRATE.material(CTNHCore.id("red_slurry"))
                .cnlang("赤泥浆液")
                .formula("TiO2+nH2O")
                .liquid()
                .color(0xAC401C)
                .buildAndRegister();

        TITANYL_SULFATE = REGISTRATE.material(CTNHCore.id("titanyl_sulfate"))
                .cnlang("硫酸钛酯")
                .formula("TiO(SO4)+nH2O")
                .liquid()
                .color(0xCC3C75)
                .buildAndRegister();

        ALUMINIUM_HYDROXIDE = REGISTRATE.material(CTNHCore.id("aluminium_hydroxide"))
                .cnlang("氢氧化铝")
                .formula("Al(OH)3")
                .dust()
                .color(0x5E929D)
                .buildAndRegister();

        SODIUM_HYDROXIDE_SOLUTION = REGISTRATE.material(CTNHCore.id("sodium_hydroxide_solution"))
                .cnlang("氢氧化钠溶液")
                .formula("NaOH+H2O")
                .liquid()
                .color(0x15286D)
                .buildAndRegister();

        RARE_EARTH_CHLORIDE_SOLUTION = REGISTRATE.material(CTNHCore.id("rare_earth_chloride_solution"))
                .cnlang("稀土氯化物")
                .liquid()
                .color(0x393927)
                .buildAndRegister();

        FERRIC_REE_CHLORIDE = REGISTRATE.material(CTNHCore.id("ferric_ree_chloride"))
                .cnlang("含稀土氯化铁溶液")
                .formula("(FeCl3)(RCl4)")
                .liquid()
                .color(0x3B3B2B)
                .buildAndRegister();

        REFINING_TITANIUM_TETRACHLORIDE = REGISTRATE.material(CTNHCore.id("refining_titanium_tetrachloride"))
                .cnlang("富集四氯化钛")
                .formula("+TiCl4")
                .liquid()
                .color(0x631CAB)
                .buildAndRegister();

        ALUMINIUM_CHLORIDE = REGISTRATE.material(CTNHCore.id("aluminium_chloride"))
                .cnlang("氯化铝")
                .formula("AlCl3")
                .dust()
                .color(0x045681)
                .buildAndRegister();

        TITANIUM_TETRACHLORIDE_V = REGISTRATE.material(CTNHCore.id("titanium_tetrachloride_v"))
                .cnlang("含钒四氯化钛")
                .formula("(V?)(TiCl4)")
                .liquid()
                .color(0x024789)
                .buildAndRegister();
    }
}
