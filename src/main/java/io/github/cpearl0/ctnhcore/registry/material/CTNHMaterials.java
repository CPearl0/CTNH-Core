package io.github.cpearl0.ctnhcore.registry.material;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.data.materials.*;
import io.github.cpearl0.ctnhcore.data.recipe.chain.BrineChain;
import io.github.cpearl0.ctnhcore.registry.CTNHElements;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModItems;
import teamrazor.deepaether.init.DABlocks;
import teamrazor.deepaether.init.DAItems;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier.HIGHER;
import static com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier.HIGHEST;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterialFlags.GENERATE_HYPER_ROTOR;

public class CTNHMaterials {

    @Key("block.gtceu.starlight")
    @CN("星能液")
    @EN("Starlight Fluid")
    public static Lang blockGtceuStarlight;

    @Key("block.gtceu.sulfuric_acid")
    @CN("硫酸")
    @EN("Sulfuric Acid")
    public static Lang blockGtceuSulfuricAcid;

    public static void addFluid(Material material) {
        if (!material.hasProperty(PropertyKey.FLUID)) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        }
    }

    public static void addDust(Material material) {
        if (!material.hasProperty(PropertyKey.DUST)) {
            material.setProperty(PropertyKey.DUST, new DustProperty());
        }
    }

    public static void addOre(Material... materials) {
        materials[0].setProperty(PropertyKey.ORE, new OreProperty());
        if (materials.length == 2 && materials[1] != null) {
            var oreProperty = materials[0].getProperty(PropertyKey.ORE);
            oreProperty.setDirectSmeltResult(materials[1]);
            oreProperty.setOreByProducts(materials[1]);
            oreProperty.setSeparatedInto(materials[1]);
        }
    }

    public static void addGas(Material material) {
        if (!material.hasProperty(PropertyKey.FLUID)) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.GAS, new FluidBuilder()));
        }
    }

    public static void adjustAluminium(Material raw) {
        var ores = raw.getProperty(PropertyKey.ORE).getOreByProducts();
        var newOre = ores.stream().map(ore -> {
            if (ore.equals(Aluminium)) {
                return Alumina;
            } else return ore;
        }).toList();
        raw.getProperty(PropertyKey.ORE).getOreByProducts().clear();
        raw.getProperty(PropertyKey.ORE).setOreByProducts(newOre);
    }

    public static void adjustPreciousAlloy(Material raw) {
        var property = raw.getProperty(PropertyKey.ORE);
        var ores = property.getOreByProducts();
        var newOre = ores.stream().map(ore -> {
            if (ore.equals(Gold)) {
                return PreciousAlloy;
            } else return ore;
        }).toList();
        property.getOreByProducts().clear();
        property.setOreByProducts(newOre);
        if (property.getSeparatedInto() != null && !property.getSeparatedInto().isEmpty()) {
            var sep = property.getSeparatedInto();
            var newSep = sep.stream().map(ore -> {
                if (ore.equals(Gold)) {
                    return PreciousAlloy;
                } else return ore;
            }).toArray(Material[]::new);
            property.getSeparatedInto().clear();
            property.setSeparatedInto(newSep);
        }
    }

    // Aether
    public static Material Holystone;
    public static Material Zanite;
    public static Material Ambrosium;
    public static Material Skyjade;
    public static Material Stratus;

    // Custom
    public static Material Jasper;
    public static Material Eglinalloy;
    public static Material Inconel625;
    public static Material Abyssalalloy;
    public static Material Titansteel;
    public static Material Pikyonium;
    public static Material BlackTitanium;
    public static Material Infinity;
    public static Material QUASER_MANA;
    public static Material COMPRESSED_ADAMANTITE;
    public static Material COMPRESSED_AETHER;
    public static Material PreciousAlloy;
    public static Material Cryolite;
    public static Material Zircon;
    public static Material Zirkelite;
    public static Material Nickeline;
    public static Material TrisodiumPhosphate;
    public static Material FlowingAmberGold;
    public static Material SpecialCompositeSteelM77;
    public static Material HiddenAlloy;
    public static Material SpiritAsh;
    public static Material SteelLeaf;
    public static Material EclipseShadow;
    public static Material Dragonflame;
    public static Material PolarIceCore;
    public static Material IllusionIron;
    public static Material ToxicSwampAmber;
    public static Material LightningPattern;
    public static Material Alumina;
    public static Material ImpureOil;
    public static Material SimpleGrowthMedium;
    public static Material Pyrotheum;
    public static Material Cryotheum;
    public static Material BiologicalCultureMediumStockSolution;
    public static Material SterileBiologicalCultureMediumStockSolution;
    public static Material Ignitium;
    public static Material O_bar;
    public static Material H_bar;
    public static Material Sunnarium;
    public static Material HIKARIUM;
    public static Material siliconFluoride;
    public static Material carbonFluoride;
    public static Material zirconiumTetrachloride;
    public static Material siliconCarbide;
    public static Material HotSteam;
    public static Material HotDeuterium;
    public static Material HotSodium;
    public static Material HotSodiumPotassium;
    public static Material icestone;
    public static Material CombustibleIce;
    public static Material NQ_END_OF_GASOLINE;
    public static Material LIVING_METAL;
    public static Material MysteryFluid;
    public static Material COLORFUL_GEM;
    public static Material RhodiumSulfurCrystal;
    public static Material RutheniumAmalgam;
    public static Material OsmiumIronSpinel;
    public static Material MeteoricTroilite;
    public static Material PalladiumSulfide;
    public static Material SolarFlareBlackDiamond;
    public static Material Cerite;
    public static Material EuropiumFluorite;
    public static Material GadoliniteSm;
    public static Material Sperrylite;
    public static Material Wolframite;
    public static Material Germanite;
    public static Material Bismuthinite;
    public static Material Yttrofluorite;
    public static Material Tarkianite;
    public static Material Crocoite;
    public static Material Smithsonite;
    public static Material Roquesite;
    public static Material Rheniite;

    public static Material SpaceNeutronium;
    public static Material InfinityCatalyst;
    public static Material RP1;
    public static Material RP1RocketFuel;
    public static Material Kerosene;
    public static Material DenseHydrazineMixedFuel;
    public static Material Hydrazine;
    public static Material EthylAnthraQuinone;
    public static Material EthylAnthraHydroQuinone;
    public static Material Anthracene;
    public static Material MethylhydrazineNitrateRocketFuel;
    public static Material MethylHydrazine;
    public static Material UDMHRocketFuel;
    public static Material UDMH;
    public static Material OrangeMetal;
    public static Material PhthalicAnhydride;
    public static Material VanadiumPentoxide;
    public static Material BlackMatter;
    public static Material Cerrobase140;
    public static Material PotassiumPyrosulfate;
    public static Material SodiumSulfate;
    public static Material ZincSulfate;
    public static Material Wollastonite;
    public static Material ArcaneCrystal;
    public static Material PalladiumOnPlatinum;
    public static Material Kaolinite;
    public static Material Dolomite;
    public static Material GraphiteUraniumMixture;
    public static Material UraniumCarbideThoriumMixture;
    public static Material PlutoniumOxideUraniumMixture;
    public static Material ThoriumBasedLiquidFuelExcited;
    public static Material ThoriumBasedLiquidFuelDepleted;
    public static Material ThoriumBasedLiquidFuel;
    public static Material UraniumBasedLiquidFuelExcited;
    public static Material UraniumBasedLiquidFuelDepleted;
    public static Material UraniumBasedLiquidFuel;
    public static Material PlutoniumBasedLiquidFuelExcited;
    public static Material PlutoniumBasedLiquidFuelDepleted;
    public static Material PlutoniumBasedLiquidFuel;
    public static Material RadiationProtection;
    public static Material NaquadahBasedLiquidFuel;
    public static Material NaquadahBasedLiquidFuelExcited;
    public static Material NaquadahBasedLiquidFuelDepleted;
    public static Material IodizedBrine;
    public static Material IodineBrineMixture;
    public static Material BrominatedBrine;
    public static Material IodineSlurry;
    public static Material AcidicBrominatedBrine;
    public static Material BromineSulfateSolution;
    public static Material OverheatedBromineSulfateSolution;
    public static Material WetBromine;
    public static Material DebrominatedWater;
    public static Material NeutroniumMixture;
    public static Material MARM200Steel;
    public static Material STABALLOY;
    public static Material SNOW_STEEL;
    public static Material Thorium232;

    public static void init() {
        // Aether
        Holystone = REGISTRATE.material(CTNHCore.id("holystone"))
                .cnlang("神圣石")
                .dust()
                .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .buildAndRegister();

        Zanite = REGISTRATE.material(CTNHCore.id("zanite"))
                .cnlang("紫晶石")
                .gem()
                .color(0x9254ef).iconSet(EMERALD)
                .ore()
                .addOreByproducts(GTMaterials.Amethyst)
                .buildAndRegister();

        Ambrosium = REGISTRATE.material(CTNHCore.id("ambrosium"))
                .cnlang("神能晶")
                .gem()
                .color(0xf1ef5f).iconSet(GEM_HORIZONTAL)
                .ore()
                .buildAndRegister();

        Skyjade = REGISTRATE.material(CTNHCore.id("skyjade"))
                .cnlang("穹玉")
                .gem()
                .color(0xb0e564).iconSet(GEM_HORIZONTAL)
                .ore()
                .buildAndRegister();

        Stratus = REGISTRATE.material(CTNHCore.id("stratus"))
                .cnlang("云母钢")
                .ingot().liquid()
                .color(0xeac1d9).iconSet(METALLIC)
                .ore()
                .buildAndRegister();

        // Custom
        Jasper = REGISTRATE.material(CTNHCore.id("jasper"))
                .cnlang("碧玉")
                .gem()
                .ore()
                .color(0xC85050)
                .flags(GENERATE_PLATE, GENERATE_ROD)
                .iconSet(EMERALD)
                .buildAndRegister();
        Eglinalloy = REGISTRATE.material(CTNHCore.id("eglin_alloy"))
                .cnlang("埃格林合金")
                .ingot()
                .liquid()
                .color(0x6e3204)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL,
                        GENERATE_FRAME, GENERATE_RING)
                .iconSet(METALLIC)
                .blastTemp(1048)
                .components(Invar, 5, Iron, 4, Kanthal, 1, Sulfur, 1, Silver, 1, Carbon, 1)
                .buildAndRegister();
        Inconel625 = REGISTRATE.material(CTNHCore.id("inconel_625"))
                .cnlang("镍铬基合金-625")
                .ingot()
                .liquid()
                .color(0x6eab6c)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL,
                        GENERATE_FRAME, GENERATE_RING)
                .iconSet(METALLIC)
                .blastTemp(6200)
                .components(Nickel, 2, Aluminium, 2, Niobium, 1, Nichrome, 1)
                .buildAndRegister();
        Abyssalalloy = REGISTRATE.material(CTNHCore.id("abyssalalloy"))
                .cnlang("渊狱合金")
                .ingot().liquid()
                .blastTemp(10800, HIGHER, GTValues.VA[GTValues.UV], 1800)
                .components(StainlessSteel, 5, TungstenCarbide, 5, Nichrome, 5, Bronze, 5, IncoloyMA956, 5, Iodine, 1,
                        Germanium, 1, Radon, 1, Hafnium, 1)
                .color(0x9e706a)
                .iconSet(METALLIC)
                .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UHV], 4, 64)
                .buildAndRegister();
        Titansteel = REGISTRATE.material(CTNHCore.id("titansteel"))
                .cnlang("泰坦钢")
                .ingot().liquid()
                .blastTemp(12600, HIGHER, GTValues.VA[GTValues.UHV], 1200)
                .components(TitaniumTungstenCarbide, 4, Plutonium241, 1, Einsteinium, 2, Rhenium, 1, Erbium, 1, Jasper,
                        3)
                .color(0xe60000)
                .iconSet(METALLIC)
                .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UEV], 4, 64)
                .buildAndRegister();
        Pikyonium = REGISTRATE.material(CTNHCore.id("pikyonium"))
                .cnlang("皮卡优")
                .ingot()
                .liquid()
                .radioactiveHazard(3)
                .blastTemp(14400, HIGHER)
                .components(Inconel625, 8, Eglinalloy, 5, EnrichedNaquadahWaste, 4, TungstenSteel, 4, Cerium, 3,
                        Antimony,
                        2, Platinum, 2, Ytterbium, 1)
                .color(0x67abff)
                .iconSet(METALLIC)
                .flags(GENERATE_FINE_WIRE)
                .cableProperties(GTValues.V[UIV], 4, 128)
                .buildAndRegister();
        BlackTitanium = REGISTRATE.material(CTNHCore.id("black_titanium"))
                .cnlang("黑钛")
                .ingot()
                .liquid()
                .blastTemp(16200, HIGHEST, GTValues.VA[GTValues.UXV], 1400)
                .components(Titanium, 26, Lanthanum, 6, Tungsten, 4, Cobalt, 3, Manganese, 2, Phosphorus, 2, Palladium,
                        2,
                        Niobium, 1, Argon, 5)
                .color(0x6C003B)
                .iconSet(DULL)
                .flags(GENERATE_ROD, GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UXV], 4, 128)
                .buildAndRegister();
        Infinity = REGISTRATE.material(CTNHCore.id("my_infinity"))
                .cnlang("无尽")
                .ingot()
                .plasma()
                .radioactiveHazard(20)
                .blastTemp(32000, HIGHEST)
                .element(CTNHElements.INFINITY)
                .iconSet(METALLIC)
                .flags(GENERATE_FRAME)
                .cableProperties(GTValues.V[GTValues.MAX], 8192, 0, true)
                .buildAndRegister();
        // QUASER_MANA = REGISTRATE.material(CTNHCore.id("quaser_mana"))
        // .cnlang("类星体魔力")
        // .ingot().liquid()
        // .blastTemp(10800, HIGHER, GTValues.VA[GTValues.UV], 1800)
        // .color(0x00007F)
        // .iconSet(METALLIC)
        // .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
        // .cableProperties(GTValues.V[GTValues.UHV], 4, 64)
        // .buildAndRegister();
        COMPRESSED_ADAMANTITE = REGISTRATE.material(CTNHCore.id("compressed_adamantite"))
                .cnlang("压缩精金")
                .plasma()
                .buildAndRegister();
        COMPRESSED_AETHER = REGISTRATE.material(CTNHCore.id("compressed_aether"))
                .cnlang("精炼超能以太")
                .plasma()
                .buildAndRegister();
        PreciousAlloy = REGISTRATE.material(CTNHCore.id("precious_alloy"))
                .cnlang("贵金属")
                .ingot()
                .ore()
                .addOreByproducts(Silver)
                .liquid()
                .color(0xA4801F)
                .iconSet(METALLIC)
                .buildAndRegister()
                .setFormula("Au?", true);
        Cryolite = REGISTRATE.material(CTNHCore.id("cryolite"))
                .cnlang("冰晶石")
                .dust()
                .ore()
                .addOreByproducts(GTMaterials.Sodium, GTMaterials.BlueTopaz)
                .color(0x8D8686)
                .buildAndRegister()
                .setFormula("Na3AlF6", true);

        Zircon = REGISTRATE.material(CTNHCore.id("zircon"))
                .cnlang("锆石")
                .gem()
                .ore()
                .addOreByproducts(GTMaterials.SiliconDioxide, GTMaterials.Yttrium)
                .color(0xEB9E3F)
                .iconSet(GEM_VERTICAL)
                .components(Zirconium, 1, Silicon, 1, Oxygen, 4)
                .buildAndRegister();
        Zirkelite = REGISTRATE.material(CTNHCore.id("zirkelite"))
                .cnlang("钛锆钍石")
                .dust()
                .ore()
                .addOreByproducts(Thorium)
                .color(0x525e50)
                .components(Calcium, 2, Thorium, 2, Cerium, 1, Zirconium, 7, Rutile, 2, Niobium, 4, Oxygen, 10)
                .buildAndRegister();
        Nickeline = REGISTRATE.material(CTNHCore.id("nickeline"))
                .cnlang("红砷镍")
                .dust()
                .ore()
                .addOreByproducts(GTMaterials.ArsenicTrioxide)
                .color(0xe8a3a3)
                .components(Nickel, 1, Arsenic, 1)
                .buildAndRegister();
        TrisodiumPhosphate = REGISTRATE.material(CTNHCore.id("trisodium_phosphate"))
                .cnlang("磷酸三钠")
                .dust()
                .color(0xdbff67)
                .ore()
                .addOreByproducts(GTMaterials.Phosphate, GTMaterials.Salt)
                .components(Sodium, 3, Phosphate, 1)
                .buildAndRegister();
        FlowingAmberGold = REGISTRATE.material(CTNHCore.id("flowing_amber_gold"))
                .cnlang("通流琥珀金")
                .dust()
                .ingot(5)
                .liquid(new FluidBuilder().temperature(13100))
                .color(0xFFD700)
                .secondaryColor(0xFFDF66)
                .iconSet(METALLIC)
                .appendFlags(EXT2_METAL, MORTAR_GRINDABLE, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, DISABLE_DECOMPOSITION, GENERATE_FINE_WIRE, GENERATE_GEAR,
                        GENERATE_DENSE)
                .ore()
                .fluidPipeProperties(13100, 50000, true)
                .cableProperties(V[UIV], 128, 8)
                .blast(b -> b.temp(12500, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[GTValues.UV], 600)
                        .vacuumStats(VA[LuV]))
                .addOreByproducts(GTMaterials.HSSS, GTMaterials.Trinium)
                .components(Redstone, 9, BlueSteel, 4, HSSS, 12, Trinium, 5, Indium, 3, Electrum, 16)
                .buildAndRegister();
        SpecialCompositeSteelM77 = REGISTRATE.material(CTNHCore.id("special_composite_steel_m77"))
                .cnlang("特种复合钢-M77")
                .dust()
                .ingot(5)
                .liquid(new FluidBuilder().temperature(7788))
                .color(0xC0B380)
                .secondaryColor(0xD2C8A0)
                .iconSet(METALLIC)
                .appendFlags(EXT2_METAL, MORTAR_GRINDABLE, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, DISABLE_DECOMPOSITION, GENERATE_FINE_WIRE, GENERATE_GEAR,
                        GENERATE_DENSE)
                .ore()
                .fluidPipeProperties(7788, 12000, true)
                .cableProperties(V[UV], 32, 4)
                .blast(b -> b.temp(7200, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[GTValues.ZPM], 200)
                        .vacuumStats(VA[LuV]))
                .addOreByproducts(GTMaterials.Vanadium, GTMaterials.TungstenSteel)
                .components(HSLASteel, 18, TungstenSteel, 12, Vanadium, 5, Ultimet, 7, Naquadria, 4)
                .buildAndRegister();
        HiddenAlloy = REGISTRATE.material(CTNHCore.id("hidden_alloy"))
                .cnlang("幽匿合金")
                .dust()
                .ingot(4)
                .liquid(new FluidBuilder().temperature(9500))
                .color(0x204060)
                .secondaryColor(0x306080)
                .iconSet(METALLIC)
                .appendFlags(EXT2_METAL, MORTAR_GRINDABLE, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, DISABLE_DECOMPOSITION, GENERATE_FINE_WIRE, GENERATE_GEAR,
                        GENERATE_DENSE)
                .ore()
                .fluidPipeProperties(9500, 20000, true)
                .cableProperties(V[ZPM], 64, 2)
                .blast(b -> b.temp(9001, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[GTValues.LuV], 200)
                        .vacuumStats(VA[IV]))
                .addOreByproducts(GTMaterials.EchoShard, GTMaterials.Sculk)
                .components(EchoShard, 10, Sculk, 6, RedAlloy, 4, BlueAlloy, 4, Apatite, 4)
                .buildAndRegister();
        SpiritAsh = REGISTRATE.material(CTNHCore.id("spirit_ash"))
                .cnlang("巫师之骨")
                .dust()
                .color(0xcfbee4)
                .secondaryColor(0x306080)
                .ore()
                .addOreByproducts(TricalciumPhosphate, Phosphate)
                .components(CalciumChloride, 1, Redstone, 1, Biotite, 1)
                .buildAndRegister();
        SteelLeaf = REGISTRATE.material(CTNHCore.id("steel_leaf"))
                .cnlang("钢叶")
                .dust()
                .color(0x9db186)
                .secondaryColor(0x306080)
                .ore()
                .addOreByproducts(TricalciumPhosphate, Phosphate)
                .components(Uvarovite, 1, Malachite, 1, Olivine, 1, Carbon, 1)
                .buildAndRegister();
        EclipseShadow = REGISTRATE.material(CTNHCore.id("eclipse_shadow"))
                .cnlang("幽影")
                .ingot()
                .dust()
                .color(0x1A0A2E)
                .secondaryColor(0x6A00F4)
                .element(CTNHElements.SHADOWSTEEL)
                .iconSet(METALLIC)
                .ore()
                .addOreByproducts(Stibnite, Antimony)
                .blast(b -> b.temp(2500, BlastProperty.GasTier.HIGH))
                .buildAndRegister();
        Dragonflame = REGISTRATE.material(CTNHCore.id("dragonflame"))
                .cnlang("龙炎")
                .ingot()
                .dust()
                .color(0xFF4500)
                .secondaryColor(0xE25822)
                .element(CTNHElements.PYROSCALE)
                .iconSet(METALLIC)
                .blast(b -> b.temp(3600, BlastProperty.GasTier.HIGH))
                .ore()
                .addOreByproducts(Sulfur, BariumSulfide)
                .buildAndRegister();
        PolarIceCore = REGISTRATE.material(CTNHCore.id("polar_ice_core"))
                .cnlang("极寒晶核")
                .gem()
                .dust()
                .color(0x7FB6D6)
                .secondaryColor(0xB0E0E6)
                .element(CTNHElements.GLACIAL_CORE)
                .ore()
                .addOreByproducts(Ice, Bauxite)
                .buildAndRegister();
        IllusionIron = REGISTRATE.material(CTNHCore.id("illusion_iron"))
                .cnlang("幻铁")
                .ingot()
                .dust()
                .color(0xC0C0C0)
                .secondaryColor(0xA0A0FF)
                .element(CTNHElements.PHANTOM_IRON)
                .iconSet(METALLIC)
                .blast(b -> b.temp(1200, BlastProperty.GasTier.HIGH))
                .ore()
                .addOreByproducts(Glowstone, Kyanite)
                .buildAndRegister();
        ToxicSwampAmber = REGISTRATE.material(CTNHCore.id("toxic_swamp_amber"))
                .cnlang("毒沼琥珀")
                .gem()
                .dust()
                .color(0x2F4F4F)
                .secondaryColor(0x00FF7F)
                .element(CTNHElements.BOG_AMBER)
                .flags(GENERATE_LENS)
                .ore()
                .addOreByproducts(Sulfur, Realgar)
                .buildAndRegister();
        LightningPattern = REGISTRATE.material(CTNHCore.id("lightning_pattern"))
                .cnlang("雷纹")
                .ingot()
                .dust()
                .color(0x3A1D6B)
                .secondaryColor(0xFFD700)
                .element(CTNHElements.STORMVEIN)
                .iconSet(METALLIC)
                .blast(b -> b.temp(5400, BlastProperty.GasTier.HIGH))
                .ore()
                .addOreByproducts(BatteryAlloy, Wulfenite)
                .buildAndRegister();
        Alumina = REGISTRATE.material(CTNHCore.id("alumina"))
                .cnlang("氧化铝")
                .formula("Al2O3")
                .dust()
                .color(0x09474A)
                .ore()
                .buildAndRegister();
        ImpureOil = REGISTRATE.material(CTNHCore.id("impure_oil"))
                .cnlang("含杂原油")
                .liquid(new FluidBuilder().block())
                .color(0x171717)
                .buildAndRegister();
        SimpleGrowthMedium = REGISTRATE.material(CTNHCore.id("simple_growth_medium"))
                .cnlang("简易生长基")
                .liquid()
                .color(0xeef295)
                .buildAndRegister();
        Pyrotheum = REGISTRATE.material(CTNHCore.id("pyrotheum"))
                .cnlang("烈焰之炽焱")
                .dust()
                .liquid(new FluidBuilder().temperature(5700).customStill())
                .color(0xe8a62b)
                .buildAndRegister();
        Cryotheum = REGISTRATE.material(CTNHCore.id("cryotheum"))
                .cnlang("极寒之凛冰")
                .dust()
                .liquid(new FluidBuilder()
                        .temperature(20)
                        .customStill())
                .color(0x34daf7)
                .buildAndRegister();
        BiologicalCultureMediumStockSolution = REGISTRATE
                .material(CTNHCore.id("biologicalculturemediumstocksolution"))
                .cnlang("生物培养基原液")
                .liquid(new FluidBuilder()
                        .temperature(303)
                        .customStill())
                .color(0x228B22)
                .buildAndRegister();
        SterileBiologicalCultureMediumStockSolution = REGISTRATE
                .material(CTNHCore.id("sterilebiologicalculturemediumstocksolution"))
                .cnlang("无菌生物培养基原液")
                .liquid(new FluidBuilder()
                        .temperature(403)
                        .customStill())
                .color(0x228B22)
                .buildAndRegister();

        Ignitium = REGISTRATE.material(CTNHCore.id("ignitium"))
                .cnlang("腾炎")
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL,
                        GENERATE_FRAME, GENERATE_RING)
                .ingot()
                .color(0xffd919)
                .iconSet(METALLIC)
                .buildAndRegister();
        O_bar = REGISTRATE.material((CTNHCore.id("o_bar")))
                .cnlang("")
                .liquid(new FluidBuilder()
                        .temperature(0)
                        .customStill())
                .color(0X522719)
                .buildAndRegister();
        H_bar = REGISTRATE.material((CTNHCore.id("h_bar")))
                .cnlang("")
                .liquid(new FluidBuilder()
                        .temperature(0)
                        .customStill())
                .color(0XFFFF00)
                .buildAndRegister();
        Sunnarium = REGISTRATE.material((CTNHCore.id("sunnarium")))
                .cnlang("阳光化合物")
                .liquid()
                .dust()
                .ingot()
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL,
                        GENERATE_FRAME, GENERATE_RING)
                .plasma()
                .element(CTNHElements.SUNNARIUM)
                .color(0XFFFF01)
                .buildAndRegister();
        HIKARIUM = REGISTRATE.material((CTNHCore.id("hikarium")))
                .cnlang("§6光素")
                .liquid(new FluidBuilder()
                        .temperature(1000000)
                        .color(0XFFAA00)
                        .customStill())
                .dust()
                .ingot()
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW,
                        GENERATE_FOIL,
                        GENERATE_FRAME, GENERATE_RING)
                .plasma()
                .element(CTNHElements.HIKARIUM)
                .color(0XFFAA00)
                .buildAndRegister();
        siliconFluoride = REGISTRATE.material(CTNHCore.id("silicon_fluoride"))
                .cnlang("氟化硅")
                .liquid()
                .color(0x76868a)
                .components(Silicon, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        carbonFluoride = REGISTRATE.material(CTNHCore.id("carbon_fluoride"))
                .cnlang("氟化碳")
                .liquid()
                .color(0xb8956d)
                .components(Carbon, 1, Fluorine, 4)
                .buildAndRegister();
        zirconiumTetrachloride = REGISTRATE.material(CTNHCore.id("zirconium_tetrachloride"))
                .cnlang("四氯化锆")
                .dust()
                .color(0xd0db7d)
                .components(Zirconium, 1, Chlorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        siliconCarbide = REGISTRATE.material(CTNHCore.id("silicon_carbide"))
                .cnlang("碳化硅")
                .dust()
                .liquid(new FluidBuilder().temperature(3200))
                .color(0x6edade)
                .components(Silicon, 1, Carbon, 1)
                .blast(b -> b.temp(3000, BlastProperty.GasTier.MID)
                        .blastStats(VA[GTValues.EV])
                        .vacuumStats(VA[HV]))
                .buildAndRegister();
        HotSteam = REGISTRATE.material(CTNHCore.id("hot_steam"))
                .cnlang("过热蒸汽")
                .gas()
                .color(0xd4d4d4)
                .buildAndRegister();
        HotDeuterium = REGISTRATE.material(CTNHCore.id("hot_deuterium"))
                .cnlang("过热氘")
                .gas()
                .color(0xe6e857)
                .buildAndRegister();
        HotSodium = REGISTRATE.material(CTNHCore.id("hot_sodium"))
                .cnlang("过热钠")
                .gas()
                .color(0x237ad1)
                .buildAndRegister();
        HotSodiumPotassium = REGISTRATE.material(CTNHCore.id("hot_sodium_potassium"))
                .cnlang("过热钠钾合金")
                .gas()
                .color(0x39cf89)
                .buildAndRegister();

        icestone = REGISTRATE.material(CTNHCore.id("icestone"))
                .cnlang("冰石")
                .dust()
                .color(0xd7fffd)
                .buildAndRegister();
        CombustibleIce = REGISTRATE.material(CTNHCore.id("combustible_ice"))
                .cnlang("可燃冰")
                .formula("(CH4)(H2O)")
                .gem()
                .iconSet(LIGNITE)
                .color(0xebfbfc)
                .burnTime(6000)
                .ore()
                .buildAndRegister();
        // ManaFused = REGISTRATE.material(CTNHCore.id("mana_fused"))
        // .cnlang("蕴魔")
        // .dust()
        // .ore()
        // .color(0x4FC1FF)
        // .buildAndRegister();
        NQ_END_OF_GASOLINE = REGISTRATE.material(CTNHCore.id("nq_end_gasoline"))
                .cnlang("硅岩基终末燃油-NQ")
                .liquid()
                .color(0x000000)
                .element(CTNHElements.END_OF_OIL)
                .buildAndRegister();
        LIVING_METAL = REGISTRATE.material(CTNHCore.id("living_metal"))
                .cnlang("活体金属")
                .liquid()
                .color(0x000000)
                .element(CTNHElements.Living_Metal)
                .buildAndRegister();
        MysteryFluid = REGISTRATE.material(CTNHCore.id("mystery_fluid"))
                .cnlang("神秘液体")
                .liquid()
                .color(0x4ded1c)
                .buildAndRegister();
        COLORFUL_GEM = REGISTRATE.material(CTNHCore.id("colorful_gem"))
                .cnlang("异彩")
                .gem()
                .element(CTNHElements.COLORFUL_GEM)
                .color(0xFF0000)
                .secondaryColor(0x0000FF)
                .buildAndRegister();
        RhodiumSulfurCrystal = REGISTRATE.material(CTNHCore.id("rhodium_sulfur_crystal"))
                .cnlang("铑硫晶")
                .gem().ore()
                .color(0xFFD700).secondaryColor(0xC0C0C0)
                .iconSet(GEM_VERTICAL)
                .flags(GENERATE_LENS, PHOSPHORESCENT, NO_SMASHING)
                .components(RhodiumSulfate, 1, Sulfur, 3)
                .addOreByproducts(Sulfur, RhodiumSulfate, Pyrite)
                .buildAndRegister();
        RutheniumAmalgam = REGISTRATE.material(CTNHCore.id("ruthenium_amalgam"))
                .cnlang("钌汞齐")
                .ingot().ore()
                .liquid(new FluidBuilder().temperature(350))
                .color(0x2E8B57).secondaryColor(0x228B22)
                .iconSet(SHINY)
                .flags(GENERATE_FOIL, GENERATE_FINE_WIRE, STICKY, DISABLE_DECOMPOSITION)
                .components(Ruthenium, 1, Mercury, 2)
                .addOreByproducts(Cinnabar, RutheniumTetroxide, Cinnabar)
                .buildAndRegister();
        OsmiumIronSpinel = REGISTRATE.material(CTNHCore.id("osmium_iron_spinel"))
                .cnlang("锇铁尖晶石")
                .gem().ore()
                .color(0x000080).secondaryColor(0x000000)
                .iconSet(DIAMOND)
                .flags(GENERATE_PLATE, GENERATE_ROD, CRYSTALLIZABLE, DISABLE_DECOMPOSITION)
                .components(Osmium, 1, Iron, 2, Oxygen, 4)
                .addOreByproducts(RarestMetalMixture, Iron, Cinnabar)
                .buildAndRegister();
        MeteoricTroilite = REGISTRATE.material(CTNHCore.id("meteoric_troilite"))
                .cnlang("陨硫铁镍")
                .ore()
                .color(0x696969).secondaryColor(0x2F4F4F)
                .iconSet(METALLIC)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .components(Iron, 1, Nickel, 1, Sulfur, 1)
                .addOreByproducts(Nickel, Platinum, Iridium)
                .buildAndRegister();
        PalladiumSulfide = REGISTRATE.material(CTNHCore.id("palladium_sulfide"))
                .cnlang("硫晶钯矿")
                .gem().ore()
                .color(0xE6E6FA).secondaryColor(0xD8BFD8)
                .iconSet(GEM_HORIZONTAL)
                .flags(GENERATE_LENS, GENERATE_PLATE, DISABLE_DECOMPOSITION)
                .components(Palladium, 1, Sulfur, 1)
                .addOreByproducts(Palladium, Sulfur, Platinum)
                .buildAndRegister();
        SolarFlareBlackDiamond = REGISTRATE.material(CTNHCore.id("solar_flare_black_diamond"))
                .cnlang("太阳耀斑黑钻")
                .gem().ore()
                .color(0x000000).secondaryColor(0xFF4500)
                .iconSet(RUBY)
                .flags(GENERATE_LENS, PHOSPHORESCENT, NO_WORKING)
                .addOreByproducts(Diamond, NetherQuartz, Glowstone)
                .buildAndRegister();
        Cerite = REGISTRATE.material(CTNHCore.id("cerite"))
                .cnlang("铈硅石")
                .ore()
                .color(0xE6D8AD).secondaryColor(0xC9B37E)
                .iconSet(ROUGH)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(Cerium, 1, Silicon, 1, Oxygen, 3)
                .addOreByproducts(Lanthanum, Neodymium, Praseodymium)
                .buildAndRegister();
        EuropiumFluorite = REGISTRATE.material(CTNHCore.id("europium_fluorite"))
                .cnlang("铕萤石")
                .gem().ore()
                .color(0xDA70D6).secondaryColor(0x9932CC)
                .iconSet(OPAL)
                .flags(PHOSPHORESCENT, GENERATE_LENS)
                .components(Europium, 1, Calcium, 1, Fluorine, 2)
                .addOreByproducts(Lanthanum, Yttrium, Terbium)
                .buildAndRegister();
        GadoliniteSm = REGISTRATE.material(CTNHCore.id("gadolinite_sm"))
                .cnlang("钐硅铍钇")
                .ore()
                .color(0x2F4F4F).secondaryColor(0x1E3D3D)
                .iconSet(DULL)
                .components(Samarium, 1, Iron, 1, Beryllium, 1, Silicon, 2, Oxygen, 8)
                .addOreByproducts(Yttrium, Erbium, Terbium)
                .buildAndRegister();
        Sperrylite = REGISTRATE.material(CTNHCore.id("sperrylite"))
                .cnlang("砷铂")
                .ore()
                .color(0x8B8B83).secondaryColor(0x696969)
                .iconSet(SHINY)
                .components(Platinum, 1, Arsenic, 2)
                .addOreByproducts(Nickel, Cobalt, Iron)
                .buildAndRegister();
        Wolframite = REGISTRATE.material(CTNHCore.id("wolframite"))
                .cnlang("黑钨")
                .ore()
                .color(0x4B3A26).secondaryColor(0x2F1B0C)
                .iconSet(DULL)
                .components(Iron, 1, Manganese, 1, Tungsten, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .addOreByproducts(Tin, Molybdenum, Bismuth)
                .buildAndRegister();
        Germanite = REGISTRATE.material(CTNHCore.id("germanite"))
                .cnlang("锗镓")
                .ore()
                .color(0x708090).secondaryColor(0x2F4F4F)
                .iconSet(METALLIC)
                .components(Copper, 3, Germanium, 1, Gallium, 1, Sulfur, 4)
                .addOreByproducts(Zinc, Arsenic, Selenium)
                .buildAndRegister();
        Bismuthinite = REGISTRATE.material(CTNHCore.id("bismuthinite"))
                .cnlang("辉铋")
                .ore()
                .color(0x9C9C9C).secondaryColor(0x7A7A7A)
                .iconSet(METALLIC)
                .components(Bismuth, 2, Sulfur, 3)
                .addOreByproducts(Lead, Antimony, Silver)
                .buildAndRegister();
        Yttrofluorite = REGISTRATE.material(CTNHCore.id("yttrofluorite"))
                .cnlang("钇萤石")
                .ore()
                .color(0x77DD77).secondaryColor(0x50C878)
                .iconSet(OPAL)
                .components(Calcium, 1, Yttrium, 1, Fluorine, 4)
                .addOreByproducts(Lanthanum, Cerium, Neodymium)
                .buildAndRegister();
        Tarkianite = REGISTRATE.material(CTNHCore.id("tarkianite"))
                .cnlang("铼钼")
                .ore()
                .color(0x4682B4).secondaryColor(0x1E90FF)
                .iconSet(SHINY)
                .components(Rhenium, 1, Molybdenum, 1, Sulfur, 4)
                .flags(DISABLE_DECOMPOSITION)
                .addOreByproducts(Copper, Silver, Gold)
                .buildAndRegister();
        Crocoite = REGISTRATE.material(CTNHCore.id("crocoite"))
                .cnlang("红铬铅")
                .ore()
                .color(0xD22B2B).secondaryColor(0x8B0000)
                .iconSet(RUBY)
                .components(Lead, 1, Chromium, 1, Oxygen, 4)
                .addOreByproducts(Lead, Vanadium, Zinc)
                .buildAndRegister();
        Smithsonite = REGISTRATE.material(CTNHCore.id("smithsonite"))
                .cnlang("菱锌")
                .ore()
                .color(0x7FFFD4).secondaryColor(0x66CDAA)
                .iconSet(OPAL)
                .components(Zinc, 1, Carbon, 1, Oxygen, 3)
                .addOreByproducts(Calcium, Iron, Magnesium)
                .buildAndRegister();
        Roquesite = REGISTRATE.material(CTNHCore.id("roquesite"))
                .cnlang("铜铟")
                .ore()
                .color(0x9370DB).secondaryColor(0x8A2BE2)
                .iconSet(SHINY)
                .components(Copper, 1, Indium, 1, Sulfur, 2)
                .addOreByproducts(Gallium, Silver, Cadmium)
                .buildAndRegister();
        Rheniite = REGISTRATE.material(CTNHCore.id("rheniite"))
                .cnlang("辉铼")
                .ore()
                .color(0xA9A9A9).secondaryColor(0x808080)
                .iconSet(METALLIC)
                .components(Rhenium, 1, Sulfur, 2)
                .flags(DISABLE_DECOMPOSITION)
                .addOreByproducts(Molybdenum, Copper, Platinum)
                .buildAndRegister();
        Thorium232 = REGISTRATE.material(CTNHCore.id("thorium_232"))
                .cnlang("钍-232")
                .ingot(3)
                .liquid(new FluidBuilder().temperature(1405))
                .color(0x2c9f2c)
                .secondaryColor(0x33342c).iconSet(MaterialIconSet.RADIOACTIVE).appendFlags(GTMaterials.EXT_METAL)
                .element(CTNHElements.Th232).buildAndRegister();

        AdastraMaterials.init();
        SecondMaterials.init();
        PlatinumLineMaterials.init();
        NaquadahMaterials.init();
        BrineChain.init();
        EnderIOMaterials.init();
        GoldChainMaterials.init();
        BoronChainMaterials.init();
        AviationFabricMaterials.init();
        BauxiteProcessingMaterials.init();
        AeCrystalScienceMaterials.init();
        AeOmniMaterials.init();

        BedrockMaterials.init();
        BiodieselFertileSoilMaterials.init();
        ExtraterrestrialAtmosphereMaterials.init();
        GrapheneProductionLineMaterials.init();
        NewExplosivesProductionMaterials.init();
        NiobiumTantalumJointProcessingMaterials.init();
        PitchblendeRefiningMaterials.init();
        RareEarthMaterials.init();
        StonePowderMaterials.init();
        YeastRelatedMaterials.init();
        ZrHfSeparationMaterials.init();
        UncategorizedMaterials.init();

        WetWareLineMaterials.init();
        SpecialMaterials.init();
        CombustibleIce.setFormula("(CH4)(H2O)", true);

        var ore = new OreProperty();
        ore.addOreByProducts(Arsenic);
        ArsenicTrioxide.setProperty(PropertyKey.ORE, ore);

        addGas(Oganesson);
        addGas(Calcium);
        addFluid(Californium);
        addFluid(Caesium);
        addFluid(AmmoniumChloride);
        addDust(Praseodymium);

        adjustAluminium(Almandine);
        adjustAluminium(Emerald);
        adjustAluminium(GreenSapphire);
        adjustAluminium(Sapphire);
        adjustAluminium(Spodumene);
        adjustAluminium(GlauconiteSand);
        adjustAluminium(Pollucite);
        adjustAluminium(Bentonite);
        adjustAluminium(FullersEarth);
        adjustAluminium(Kyanite);
        adjustAluminium(Mica);
        adjustAluminium(Zeolite);

        adjustPreciousAlloy(Bornite);
        adjustPreciousAlloy(Chalcopyrite);
        adjustPreciousAlloy(Copper);
        adjustPreciousAlloy(Iron);
        adjustPreciousAlloy(Magnetite);
        adjustPreciousAlloy(Silver);
        adjustPreciousAlloy(Tarkianite);
        adjustPreciousAlloy(GraniticMineralSand);
        adjustPreciousAlloy(VanadiumMagnetite);
        adjustPreciousAlloy(BasalticMineralSand);

        var oreProp = Ruby.getProperty(PropertyKey.ORE);
        oreProp.getOreByProducts().clear();
        oreProp.setOreByProducts(Chromite, GarnetRed, Chromite);

        // Oxygen.getProperty(PropertyKey.FLUID).getStorage().store(FluidStorageKeys.GAS, ModFluids.OXYGEN, null);
        // GTFluids.handleNonMaterialFluids(Steel, () -> CMFluids.MOLTEN_STEEL.get().getSource());

        oreProp = Naquadah.getProperty(PropertyKey.ORE);
        oreProp.getOreByProducts().clear();
        oreProp.setOreByProducts(Sulfur, Barite, NaquadahMaterials.EnrichedNaquadahOxideMixture);
        oreProp.getSeparatedInto().clear();
        oreProp.setSeparatedInto(NaquadahMaterials.EnrichedNaquadahOxideMixture);

        // Enable Dense Plate for Hyper Rotor
        for (MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries())
            for (Material material : registry.getAllMaterials())
                if (material.hasAnyOfFlags(MaterialFlags.GENERATE_ROTOR, GENERATE_HYPER_ROTOR) &&
                        !material.hasFlag(GENERATE_DENSE))
                    material.addFlags(GENERATE_DENSE);
    }

    public static void tagPrefixIgnore() {
        TagPrefix.block.setIgnoredBlock(Holystone, AetherBlocks.HOLYSTONE);

        TagPrefix.gem.setIgnored(Zanite, AetherItems.ZANITE_GEMSTONE);
        TagPrefix.block.setIgnoredBlock(Zanite, AetherBlocks.ZANITE_BLOCK);

        TagPrefix.gem.setIgnored(Ambrosium, AetherItems.AMBROSIUM_SHARD);
        TagPrefix.block.setIgnoredBlock(Ambrosium, AetherBlocks.AMBROSIUM_BLOCK);

        TagPrefix.gem.setIgnored(Skyjade, DAItems.SKYJADE);
        TagPrefix.block.setIgnoredBlock(Skyjade, DABlocks.SKYJADE_BLOCK);

        TagPrefix.ingot.setIgnored(Stratus, DAItems.STRATUS_INGOT);
        TagPrefix.block.setIgnoredBlock(Stratus, DABlocks.STRATUS_BLOCK);

        TagPrefix.ingot.setIgnored(Ignitium, ModItems.IGNITIUM_INGOT);
        TagPrefix.block.setIgnoredBlock(Ignitium, ModBlocks.IGNITIUM_BLOCK);

        // hyperRotor.setIgnored(Neutronium);
    }

    public static class MaterialIcons {

        public static MaterialIconSet Neutron = new MaterialIconSet("neutron", DULL);
    }
}
