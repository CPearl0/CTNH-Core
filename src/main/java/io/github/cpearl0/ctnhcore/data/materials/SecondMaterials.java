package io.github.cpearl0.ctnhcore.data.materials;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.data.material.CatalystProperty;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.METALLIC;
import static io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys.CATALYST;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials.*;

public class SecondMaterials {

    public static void init() {
        ArcaneCrystal = REGISTRATE.material(CTNHCore.id("arcane_crystal")).cnlang("神秘水晶")
                .dust().ore().gem().color(0x93AEFF).iconSet(MaterialIconSet.DIAMOND)
                .flags(MaterialFlags.GENERATE_LENS)
                .buildAndRegister();

        PalladiumOnPlatinum = REGISTRATE.material(CTNHCore.id("palladium_on_platinum")).cnlang("钯铂").dust()
                .color(0x233144).iconSet(MaterialIconSet.DULL)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(GTMaterials.Palladium, 1, GTMaterials.Platinum, 1)
                .buildAndRegister();

        RP1 = REGISTRATE.material(CTNHCore.id("rp_1_mixed_fuel")).cnlang("RP-1混合燃料").fluid().color(0xC02928)
                .iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        RP1RocketFuel = REGISTRATE.material(CTNHCore.id("rp_1_rocket_fuel")).cnlang("RP-1火箭燃料").fluid().color(0x9E2A2A)
                .iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        Kerosene = REGISTRATE.material(CTNHCore.id("kerosene")).cnlang("煤油").fluid().color(0x752275)
                .iconSet(MaterialIconSet.DULL).buildAndRegister();

        DenseHydrazineMixedFuel = REGISTRATE.material(CTNHCore.id("dense_hydrazine_mixed_fuel")).cnlang("浓缩肼混合燃料")
                .fluid().color(0x833D59).iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        Hydrazine = REGISTRATE.material(CTNHCore.id("hydrazine")).cnlang("肼").fluid().color(0xBBBBBB)
                .iconSet(MaterialIconSet.DULL).buildAndRegister();

        EthylAnthraQuinone = REGISTRATE.material(CTNHCore.id("ethyl_anthra_quinone")).cnlang("乙基蒽醌").fluid()
                .color(0xAABE77).iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        EthylAnthraHydroQuinone = REGISTRATE.material(CTNHCore.id("ethyl_anthra_hydro_quinone")).cnlang("乙基蒽醌醇").fluid()
                .color(0xC9E08D).iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        Anthracene = REGISTRATE.material(CTNHCore.id("anthracene")).cnlang("蒽").fluid().color(0xBBBABA)
                .iconSet(MaterialIconSet.DULL).buildAndRegister();

        MethylhydrazineNitrateRocketFuel = REGISTRATE.material(CTNHCore.id("methylhydrazine_nitrate_rocket_fuel"))
                .cnlang("CN3H7O3火箭燃料").fluid().color(0x613B87)
                .iconSet(MaterialIconSet.DULL).buildAndRegister();

        MethylHydrazine = REGISTRATE.material(CTNHCore.id("methyl_hydrazine")).cnlang("甲基肼").fluid().color(0x606060)
                .iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        UDMHRocketFuel = REGISTRATE.material(CTNHCore.id("udmh_rocket_fuel")).cnlang("H8N4C2O4火箭燃料").fluid()
                .color(0x2AA327).iconSet(MaterialIconSet.DULL)
                .buildAndRegister();

        UDMH = REGISTRATE.material(CTNHCore.id("udmh")).cnlang("偏二甲肼").fluid().color(0x050543)
                .iconSet(MaterialIconSet.DULL).buildAndRegister();

        OrangeMetal = REGISTRATE.material(CTNHCore.id("orange_metal")).cnlang("橙色金属").dust().color(0xfa7e23)
                .iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister();

        PhthalicAnhydride = REGISTRATE.material(CTNHCore.id("phthalic_anhydride")).cnlang("邻苯二甲酸酐").dust()
                .color(0x6C863A).iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister();

        VanadiumPentoxide = REGISTRATE.material(CTNHCore.id("vanadium_pentoxide")).cnlang("五氧化二钒").dust()
                .components(GTMaterials.Vanadium, 2, GTMaterials.Oxygen, 5).color(0xB5730F)
                .iconSet(METALLIC).buildAndRegister();

        BlackMatter = REGISTRATE.material(CTNHCore.id("black_matter")).cnlang("黑物质").dust().ingot().fluid()
                .components(GTMaterials.Lead, 3, GTMaterials.Manganese, 5, GTMaterials.Carbon, 12).color(0x000000)
                .iconSet(MaterialIconSet.DULL).appendFlags(GTMaterials.EXT_METAL, MaterialFlags.GENERATE_FRAME)
                .buildAndRegister();

        Cerrobase140 = REGISTRATE.material(CTNHCore.id("cerrobase_140")).cnlang("铋铅合金140").dust().fluid().components(
                GTMaterials.Bismuth, 47, GTMaterials.Lead, 25, GTMaterials.Tin, 13, GTMaterials.Cadmium, 10,
                GTMaterials.Indium, 5).color(0x9e9e9e).iconSet(METALLIC).blastTemp(1230)
                .buildAndRegister();

        PotassiumPyrosulfate = REGISTRATE.material(CTNHCore.id("potassium_pyrosulfate")).cnlang("焦硫酸钾").dust()
                .fluid(FluidStorageKeys.MOLTEN, new FluidBuilder())
                .components(GTMaterials.Potassium, 2, GTMaterials.Sulfur, 2, GTMaterials.Oxygen, 7).color(0xff9900)
                .iconSet(METALLIC).buildAndRegister();

        SodiumSulfate = REGISTRATE.material(CTNHCore.id("sodium_sulfate")).cnlang("硫酸钠").dust()
                .components(GTMaterials.Sodium, 2, GTMaterials.Sulfur, 1, GTMaterials.Oxygen, 4).color(0xF9F6CF)
                .iconSet(MaterialIconSet.SAND).buildAndRegister();

        ZincSulfate = REGISTRATE.material(CTNHCore.id("zinc_sulfate")).cnlang("硫酸锌").dust()
                .components(GTMaterials.Zinc, 1, GTMaterials.Sulfur, 1, GTMaterials.Oxygen, 4).color(0x533c1b)
                .iconSet(MaterialIconSet.SAND).buildAndRegister();

        Wollastonite = REGISTRATE.material(CTNHCore.id("wollastonite")).cnlang("硅灰石").dust().ore()
                .components(GTMaterials.Calcium, 1, GTMaterials.Silicon, 1, GTMaterials.Oxygen, 3).color(0xc4cbcf)
                .iconSet(MaterialIconSet.SAND)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister();

        Kaolinite = REGISTRATE.material(CTNHCore.id("kaolinite")).cnlang("高岭石").dust().ore().color(0x969090)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister();

        Dolomite = REGISTRATE.material(CTNHCore.id("dolomite")).cnlang("白云石").dust().ore().color(0x9F9191)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION).buildAndRegister();

        GraphiteUraniumMixture = REGISTRATE.material(CTNHCore.id("graphite_uranium_mixture")).cnlang("石墨-铀混合物").dust()
                .components(GTMaterials.Graphite, 3, GTMaterials.Uranium238, 1).color(0x2f734c)
                .iconSet(MaterialIconSet.METALLIC).buildAndRegister();

        PlutoniumOxideUraniumMixture = REGISTRATE.material(CTNHCore.id("plutonium_oxide_uranium_mixture"))
                .cnlang("氧化钚-铀混合物").dust().components(
                        GTMaterials.Plutonium239, 10, GTMaterials.Oxygen, 12, GTMaterials.Uranium238, 2,
                        GTMaterials.Carbon, 8)
                .color(0xc51d46).iconSet(MaterialIconSet.METALLIC).buildAndRegister();

        UraniumCarbideThoriumMixture = REGISTRATE.material(CTNHCore.id("uranium_carbide_thorium_mixture"))
                .cnlang("碳化铀-钍混合物").dust().components(
                        GTMaterials.Thorium, 11, Thorium232, 1, GTMaterials.Uranium235, 1, GTMaterials.Carbon, 3)
                .color(0x15231b).iconSet(MaterialIconSet.METALLIC).buildAndRegister();

        ThoriumBasedLiquidFuel = REGISTRATE.material(CTNHCore.id("thorium_based_liquid_fuel")).cnlang("钍基流体燃料").fluid()
                .color(0x3b264d).iconSet(METALLIC)
                .buildAndRegister().setFormula("Th432Li4D2Hg");

        ThoriumBasedLiquidFuelExcited = REGISTRATE.material(CTNHCore.id("thorium_based_liquid_fuel_excited"))
                .cnlang("钍基流体燃料(激发态)").fluid().color(0x3f2850)
                .iconSet(METALLIC).buildAndRegister().setFormula("*(Th432Li4D2HG)*");

        ThoriumBasedLiquidFuelDepleted = REGISTRATE.material(CTNHCore.id("thorium_based_liquid_fuel_depleted"))
                .cnlang("钍基流体燃料(枯竭态)").fluid().color(0x5d5166)
                .iconSet(METALLIC).buildAndRegister().setFormula("Th?Pr?B?In?");

        UraniumBasedLiquidFuel = REGISTRATE.material(CTNHCore.id("uranium_based_liquid_fuel")).cnlang("铀基流体燃料").fluid()
                .color(0x02ba05).iconSet(METALLIC)
                .buildAndRegister().setFormula("U36K8Qt4Rn");

        UraniumBasedLiquidFuelExcited = REGISTRATE.material(CTNHCore.id("uranium_based_liquid_fuel_excited"))
                .cnlang("铀基流体燃料(激发态)").fluid().color(0x04bc04)
                .iconSet(METALLIC).buildAndRegister().setFormula("*(U36K8Qt4Rn)*");

        UraniumBasedLiquidFuelDepleted = REGISTRATE.material(CTNHCore.id("uranium_based_liquid_fuel_depleted"))
                .cnlang("铀基流体燃料(枯竭态)").fluid().color(0x576d31)
                .iconSet(METALLIC).buildAndRegister().setFormula("PB?Bi?Ba?Xe?");

        PlutoniumBasedLiquidFuel = REGISTRATE.material(CTNHCore.id("plutonium_based_liquid_fuel")).cnlang("钚基流体燃料")
                .fluid().color(0xb71213)
                .iconSet(METALLIC).buildAndRegister().setFormula("Pu45Nt8Cs16Nq2");

        PlutoniumBasedLiquidFuelExcited = REGISTRATE.material(CTNHCore.id("plutonium_based_liquid_fuel_excited"))
                .cnlang("钚基流体燃料(激发态)").fluid().color(0xb81312)
                .iconSet(METALLIC).buildAndRegister().setFormula("*(Pu45Nt8Cs16Nq2)*");

        PlutoniumBasedLiquidFuelDepleted = REGISTRATE.material(CTNHCore.id("plutonium_based_liquid_fuel_depleted"))
                .cnlang("钚基流体燃料(枯竭态)").fluid().color(0x4e1414)
                .iconSet(METALLIC).buildAndRegister().setFormula("Th?Ce?Au?Kr?");

        RadiationProtection = REGISTRATE.material(CTNHCore.id("radiation_protection")).cnlang("防辐射").dust()
                .flags(MaterialFlags.GENERATE_FRAME).color(0x4C4C4B)
                .iconSet(METALLIC).buildAndRegister();

        NaquadahBasedLiquidFuel = REGISTRATE.material(CTNHCore.id("naquadah_based_liquid_fuel")).cnlang("硅岩流体燃料")
                .fluid().color(0x43b54a)
                .iconSet(METALLIC).buildAndRegister().setFormula("Nq42Ce16Nd16");

        NaquadahBasedLiquidFuelExcited = REGISTRATE.material(CTNHCore.id("naquadah_based_liquid_fuel_excited"))
                .cnlang("硅岩流体燃料(激发态)").fluid().color(0x41b349)
                .iconSet(METALLIC).buildAndRegister().setFormula("*(Nq42Ce16Nd16)*");

        NaquadahBasedLiquidFuelDepleted = REGISTRATE.material(CTNHCore.id("naquadah_based_liquid_fuel_depleted"))
                .cnlang("硅岩流体燃料(枯竭态)").fluid().color(0x215825)
                .iconSet(METALLIC).buildAndRegister().setFormula("Nq?Ke?Nd?");

        NeutroniumMixture = REGISTRATE.material(CTNHCore.id("neutronium_mixture")).cnlang("中子素混合物").dust()
                .color(0xFFFFFF).secondaryColor(0x000000)
                .iconSet(METALLIC).buildAndRegister().setFormula("?Nt?");

        MARM200Steel = REGISTRATE.material(CTNHCore.id("mar_m_200_steel")).cnlang("MAR-M200特种钢")
                .ingot()
                .fluid()
                .color(0x515151)
                .iconSet(MaterialIconSet.SHINY)
                .blastTemp(5000, BlastProperty.GasTier.HIGHER, GTValues.VA[GTValues.IV], 200)
                .components(GTMaterials.Niobium, 2, GTMaterials.Chromium, 9, GTMaterials.Aluminium, 5,
                        GTMaterials.Titanium, 2, GTMaterials.Cobalt, 10, GTMaterials.Tungsten, 13, GTMaterials.Nickel,
                        18)
                .flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROTOR,
                        MaterialFlags.GENERATE_ROD, MaterialFlags.GENERATE_FRAME)
                .buildAndRegister();

        STABALLOY = REGISTRATE.material(CTNHCore.id("staballoy"))
                .cnlang("贫铀合金")
                .ingot()
                .liquid()
                .color(0x444B42)
                .iconSet(METALLIC)
                .flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD, MaterialFlags.GENERATE_GEAR,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_BOLT_SCREW,
                        MaterialFlags.GENERATE_FOIL, MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_RING)
                .components(GTMaterials.Uranium238, 9, GTMaterials.Titanium, 1)
                .blastTemp(3450, BlastProperty.GasTier.MID, GTValues.VA[GTValues.EV], 300)
                .buildAndRegister();

        SNOW_STEEL = REGISTRATE.material(CTNHCore.id("snow_steel"))
                .cnlang("雪城钢")
                .formula("SNOWCITY")
                .ingot()
                .liquid()
                .color(0x00FFFF)
                .iconSet(METALLIC)
                .flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_FOIL, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_GEAR, MaterialFlags.GENERATE_BOLT_SCREW)
                .buildAndRegister();

        OrangeMetal.setProperty(CATALYST, new CatalystProperty(100));
    }
}
