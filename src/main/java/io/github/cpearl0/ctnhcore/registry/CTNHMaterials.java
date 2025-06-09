package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import dev.arbor.gtnn.data.GTNNMaterials;
import earth.terrarium.adastra.common.registry.ModBlocks;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials;
import lombok.Generated;
import net.minecraft.server.commands.PublishCommand;
import teamrazor.deepaether.init.DABlocks;
import teamrazor.deepaether.init.DAItems;
import vazkii.botania.common.block.BotaniaBlocks;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class CTNHMaterials {
    // Ad Astra
    public static final Material Moonstone = new Material.Builder(CTNHCore.id("moon_stone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    public static final Material Marsstone = new Material.Builder(CTNHCore.id("mars_stone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    public static final Material Venusstone = new Material.Builder(CTNHCore.id("venus_stone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();
    public static final Material Mercurystone = new Material.Builder(CTNHCore.id("mercury_stone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    public static final Material Glaciostone = new Material.Builder(CTNHCore.id("glacio_stone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    // Aether
    public static final Material Holystone = new Material.Builder(CTNHCore.id("holystone"))
            .dust()
            .color(0xababab).secondaryColor(0x757575).iconSet(ROUGH)
            .flags(DECOMPOSITION_BY_CENTRIFUGING)
            .buildAndRegister();

    public static final Material Zanite = new Material.Builder(CTNHCore.id("zanite"))
            .gem()
            .color(0x9254ef).iconSet(EMERALD)
            .ore()
            .addOreByproducts(GTMaterials.Amethyst)
            .buildAndRegister();

    public static final Material Ambrosium = new Material.Builder(CTNHCore.id("ambrosium"))
            .gem()
            .color(0xf1ef5f).iconSet(GEM_HORIZONTAL)
            .ore()
            .buildAndRegister();

    public static final Material Skyjade = new Material.Builder(CTNHCore.id("skyjade"))
            .gem()
            .color(0xb0e564).iconSet(GEM_HORIZONTAL)
            .ore()
            .buildAndRegister();

    public static final Material Stratus = new Material.Builder(CTNHCore.id("stratus"))
            .ingot().liquid()
            .color(0xeac1d9).iconSet(METALLIC)
            .ore()
            .buildAndRegister();

    // Custom
    public static final Material Jasper = new Material.Builder(GTCEu.id("jasper"))
            .gem()
            .ore()
            .color(0xC85050)
            .flags(GENERATE_PLATE, GENERATE_ROD)
            .iconSet(EMERALD)
            .buildAndRegister();
    public static final Material Eglinalloy = new Material.Builder(GTCEu.id("eglin_alloy"))
            .ingot()
            .liquid()
            .color(0x6e3204)
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .iconSet(METALLIC)
            .blastTemp(1048)
            .components(Invar,5, Iron,4, Kanthal,1, Sulfur,1, Silver,1, Carbon,1)
            .buildAndRegister();
    public static final Material Inconel625 = new Material.Builder(GTCEu.id("inconel_625"))
            .ingot()
            .liquid()
            .color(0x6eab6c)
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .iconSet(METALLIC)
            .blastTemp(6200)
            .components(Nickel,2,Aluminium,2, Niobium,1,Nichrome,1)
            .buildAndRegister();
    public static final Material Abyssalalloy = new Material.Builder(GTCEu.id("abyssalalloy"))
            .ingot().liquid()
            .blastTemp(10800, HIGHER, GTValues.VA[GTValues.UV], 1800)
            .components(StainlessSteel, 5,TungstenCarbide,5, Nichrome,5,Bronze,5,IncoloyMA956,5, Iodine, 1,Germanium,1,Radon,1 ,Hafnium,1)
            .color(0x9e706a)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UHV], 4, 64)
            .buildAndRegister();
    public static final Material Titansteel = new Material.Builder(GTCEu.id("titansteel"))
            .ingot().liquid()
            .blastTemp(12600, HIGHER, GTValues.VA[GTValues.UHV], 1200)
            .components(TitaniumTungstenCarbide,4,Plutonium241,1, Einsteinium, 2,Rhenium,1, Erbium,1, Jasper,3)
            .color(0xe60000)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UEV], 4, 64)
            .buildAndRegister();
    public static final Material Pikyonium = new Material.Builder(GTCEu.id("pikyonium"))
            .ingot()
            .liquid()
            .radioactiveHazard(3)
            .blastTemp(14400, HIGHER)
            .components(Inconel625,8, Eglinalloy,5, EnrichedNaquadahWaste,4, TungstenSteel,4, Cerium,3, Antimony,2, Platinum,2 ,Ytterbium,1)
            .color(0x67abff)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE)
            .cableProperties(GTValues.V[UIV], 4, 128)
            .buildAndRegister();
    public static final Material BlackTitanium = new Material.Builder(GTCEu.id("black_titanium"))
            .ingot()
            .liquid()
            .blastTemp(16200, HIGHEST, GTValues.VA[GTValues.UXV], 1400)
            .components(Titanium,26, Lanthanum,6, Tungsten,4, Cobalt,3, Manganese,2, Phosphorus,2, Palladium,2, Niobium,1, Argon,5)
            .color(0x6C003B)
            .iconSet(DULL)
            .flags(GENERATE_ROD, GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UXV], 4, 128)
            .buildAndRegister();
    public static final Material Starmetal = new Material.Builder(GTCEu.id("starmetal"))
            .ingot()
            .liquid()
            .plasma()
            .addOreByproducts(GTMaterials.Sapphire, GTMaterials.Polonium)
            .radioactiveHazard(6)
            .blastTemp(21800, HIGHEST)
            .element(CTNHElements.STARMETAL)
            .color(0xf4f4f4)
            .iconSet(MaterialIcons.StarsteelIcon)
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_ROTOR)
            .cableProperties(GTValues.V[GTValues.OpV], 4, 256)
            .buildAndRegister();
    public static final Material Infinity = new Material.Builder(GTCEu.id("my_infinity"))
            .ingot()
            .plasma()
            .radioactiveHazard(20)
            .blastTemp(32000, HIGHEST)
            .element(CTNHElements.INFINITY)
            .iconSet(METALLIC)
            .flags(GENERATE_FRAME)
            .cableProperties(GTValues.V[GTValues.MAX], 8192, 0, true)
            .buildAndRegister();
    public static final Material QUASER_MANA = new Material.Builder(GTCEu.id("quaser_mana"))
            .ingot().liquid()
            .blastTemp(10800, HIGHER, GTValues.VA[GTValues.UV], 1800)
            .color(0x00007F)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UHV], 4, 64)
            .buildAndRegister();
    public static  final Material COMPRESSED_ADAMANTITE= new Material.Builder(GTCEu.id("compressed_adamantite"))
            .plasma()
            .buildAndRegister();
    public static  final Material COMPRESSED_AETHER = new Material.Builder(GTCEu.id("compressed_aether"))
            .plasma()
            .buildAndRegister();
    public static final Material EVE = new Material.Builder(GTCEu.id("eve"))
            .plasma()
            .color(0x0000FF)
            .radioactiveHazard(20)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .buildAndRegister();
    public static final Material PreciousAlloy = new Material.Builder(GTCEu.id("precious_alloy"))
            .ingot()
            .ore()
            .addOreByproducts(Silver)
            .liquid()
            .color(0xA4801F)
            .iconSet(METALLIC)
            .buildAndRegister()
            .setFormula("Au?", true);
    public static final Material Cryolite = new Material.Builder(GTCEu.id("cryolite"))
            .dust()
            .ore()
            .addOreByproducts(GTMaterials.Sodium, GTMaterials.BlueTopaz)
            .color(0x8D8686)
            .buildAndRegister()
            .setFormula("Na3AlF6", true);

    public static final Material Zircon = new Material.Builder(GTCEu.id("zircon"))
            .gem()
            .ore()
            .addOreByproducts(GTMaterials.SiliconDioxide, GTMaterials.Yttrium)
            .color(0xEB9E3F)
            .iconSet(GEM_VERTICAL)
            .components(Zirconium,1, Silicon,1, Oxygen,4)
            .buildAndRegister();
    public static final Material Zirkelite = new Material.Builder(GTCEu.id("zirkelite"))
            .dust()
            .ore()
            .addOreByproducts(Thorium)
            .color(0x525e50)
            .components(Calcium,2, Thorium,2, Cerium,1, Zirconium,7, Rutile,2, Niobium,4, Oxygen,10)
            .buildAndRegister();
    public static final Material Nickeline = new Material.Builder(GTCEu.id("nickeline"))
            .dust()
            .ore()
            .addOreByproducts(GTMaterials.ArsenicTrioxide)
            .color(0xe8a3a3)
            .components(Nickel,1, Arsenic,1)
            .buildAndRegister();
    public static final Material TrisodiumPhosphate = new Material.Builder(GTCEu.id("trisodium_phosphate"))
            .dust()
            .color(0xdbff67)
            .ore()
            .addOreByproducts(GTMaterials.Phosphate, GTMaterials.Salt)
            .components(Sodium,3, Phosphate,1)
            .buildAndRegister();
    public static final Material FlowingAmberGold = new Material.Builder(GTCEu.id("flowing_amber_gold"))
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
                    .blastStats(VA[GTValues.UV], 6000)
                    .vacuumStats(VA[LuV]))
            .addOreByproducts(GTMaterials.HSSS, GTMaterials.Trinium)
            .components(Redstone,9, BlueSteel,4, HSSS,12, Trinium,5, Indium,3, Electrum,16)
            .buildAndRegister();
    public static final Material SpecialCompositeSteelM77 = new Material.Builder(GTCEu.id("special_composite_steel_m77"))
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
                    .blastStats(VA[GTValues.ZPM], 4000)
                    .vacuumStats(VA[LuV]))
            .addOreByproducts(GTMaterials.Vanadium, GTMaterials.TungstenSteel)
            .components(HSLASteel,18, TungstenSteel,12, Vanadium,5, Ultimet,7, Naquadria,4)
            .buildAndRegister();
    public static final Material HiddenAlloy = new Material.Builder(GTCEu.id("hidden_alloy"))
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
                    .blastStats(VA[GTValues.LuV], 2000)
                    .vacuumStats(VA[IV]))
            .addOreByproducts(GTMaterials.EchoShard, GTMaterials.Sculk)
            .components(EchoShard,10, Sculk,6, RedAlloy,4, BlueAlloy,4, Apatite,4)
            .buildAndRegister();
    public static final Material SpiritAsh = new Material.Builder(GTCEu.id("spirit_ash"))
            .dust()
            .color(0xcfbee4)
            .secondaryColor(0x306080)
            .ore()
            .addOreByproducts(TricalciumPhosphate, Phosphate)
            .components(CalciumChloride,1, Redstone,1, Biotite,1)
            .buildAndRegister();
    public static final Material SteelLeaf = new Material.Builder(GTCEu.id("steel_leaf"))
            .dust()
            .color(0x9db186)
            .secondaryColor(0x306080)
            .ore()
            .addOreByproducts(TricalciumPhosphate, Phosphate)
            .components(Uvarovite,1, Malachite,1, Olivine,1, Carbon,1)
            .buildAndRegister();
    public static final Material EclipseShadow = new Material.Builder(GTCEu.id("eclipse_shadow"))
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
    public static final Material Dragonflame = new Material.Builder(GTCEu.id("dragonflame"))
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
    public static final Material PolarIceCore = new Material.Builder(GTCEu.id("polar_ice_core"))
            .gem()
            .dust()
            .color(0x7FB6D6)
            .secondaryColor(0xB0E0E6)
            .element(CTNHElements.GLACIAL_CORE)
            .ore()
            .addOreByproducts(Ice, Bauxite)
            .buildAndRegister();
    public static final Material IllusionIron = new Material.Builder(GTCEu.id("illusion_iron"))
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
    public static final Material ToxicSwampAmber = new Material.Builder(GTCEu.id("toxic_swamp_amber"))
            .gem()
            .dust()
            .color(0x2F4F4F)
            .secondaryColor(0x00FF7F)
            .element(CTNHElements.BOG_AMBER)
            .ore()
            .addOreByproducts(Sulfur, Realgar)
            .buildAndRegister();
    public static final Material LightningPattern = new Material.Builder(GTCEu.id("lightning_pattern"))
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
    public static final Material Alumina = new Material.Builder(GTCEu.id("alumina"))
            .dust()
            .color(0x09474A)
            .ore()
            .buildAndRegister();
    public static final Material ImpureOil = new Material.Builder(GTCEu.id("impure_oil"))
            .liquid()
            .color(0x171717)
            .buildAndRegister();
    public static final Material SimpleGrowthMedium = new Material.Builder(GTCEu.id("simple_growth_medium"))
            .liquid()
            .color(0xeef295)
            .buildAndRegister();
    public static final Material Pyrotheum = new Material.Builder(GTCEu.id("pyrotheum"))
            .dust()
            .liquid(new FluidBuilder().temperature(5700).customStill())
            .color(0xe8a62b)
            .buildAndRegister();
    public static final Material Cryotheum = new Material.Builder(GTCEu.id("cryotheum"))
            .dust()
            .liquid(new FluidBuilder()
                    .temperature(20)
                    .customStill())
            .color(0x34daf7)
            .buildAndRegister();
    public static final Material BiologicalCultureMediumStockSolution = new Material.Builder(GTCEu.id("biologicalculturemediumstocksolution"))
            .liquid(new FluidBuilder()
                    .temperature(303)
                    .customStill())
            .color(0x228B22)
            .buildAndRegister();
    public static final Material SterileBiologicalCultureMediumStockSolution = new Material.Builder(GTCEu.id("sterilebiologicalculturemediumstocksolution"))
            .liquid(new FluidBuilder()
                    .temperature(403)
                    .customStill())
            .color(0x228B22)
            .buildAndRegister();
    public static final Material Mana = new Material.Builder(GTCEu.id("mana"))
            .liquid()
            .color(0x43e7ed)
            .buildAndRegister();
    public static final Material Zenith_essence = new Material.Builder(GTCEu.id("zenith_essence"))
            .liquid()
            .color(0x7D26CD)
            .secondaryColor(0x836FFF)
            .buildAndRegister();
    public static final Material AlfSteel = new Material.Builder(GTCEu.id("alfsteel"))
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .ingot()
            .color(0xFD9D31)
            .iconSet(METALLIC)
            .cableProperties(GTValues.V[GTValues.EV], 6, 1, false)
            .buildAndRegister();

    public static final Material Ignitium = new Material.Builder(GTCEu.id("ignitium"))
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .ingot()
            .color(0xffd919)
            .iconSet(METALLIC)
            .buildAndRegister();
    public static final Material O_bar = new Material.Builder((GTCEu.id("o_bar")))
            .liquid(new FluidBuilder()
                    .temperature(0)
                    .customStill())
            .color(0X522719)
            .buildAndRegister();
    public static final Material H_bar = new Material.Builder((GTCEu.id("h_bar")))
            .liquid(new FluidBuilder()
                    .temperature(0)
                    .customStill())
            .color(0XFFFF00)
            .buildAndRegister();
    public static final Material SUNNARIUM = new Material.Builder((GTCEu.id("sunnarium")))
            .liquid()
            .dust()
            .ingot()
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .plasma()
            .element(CTNHElements.SUNNARIUM)
            .color(0XFFFF01)
            .buildAndRegister();
    public static final Material HIKARIUM = new Material.Builder((GTCEu.id("hikarium")))
            .liquid(new FluidBuilder()
                    .temperature(1000000)
                    .color(0XFFAA00)
                    .customStill())
            .dust()
            .ingot()
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .plasma()
            .element(CTNHElements.HIKARIUM)
            .color(0XFFAA00)
            .buildAndRegister();
    public static final Material starlight = new Material.Builder(GTCEu.id("starlight"))
            .liquid(new FluidBuilder().temperature(50).textures(true,true).block())
            .buildAndRegister();
    public static final Material siliconFluoride = new Material.Builder(GTCEu.id("silicon_fluoride"))
            .liquid()
            .color(0x76868a)
            .components(Silicon,1 , Fluorine, 4)
            .buildAndRegister();
    public static final Material carbonFluoride = new Material.Builder(GTCEu.id("carbon_fluoride"))
            .liquid()
            .color(0xb8956d)
            .components(Carbon,1 , Fluorine, 4)
            .buildAndRegister();
    public static final Material zirconiumTetrachloride = new Material.Builder(GTCEu.id("zirconium_tetrachloride"))
            .dust()
            .color(0xd0db7d)
            .components(Zirconium,1 , Chlorine, 4)
            .flags(DISABLE_DECOMPOSITION)
            .buildAndRegister();
    public static final Material siliconCarbide = new Material.Builder(GTCEu.id("silicon_carbide"))
            .dust()
            .color(0x6edade)
            .components(Silicon, 1, Carbon, 1)
            .buildAndRegister();
    public static final Material HotSteam = new Material.Builder(GTCEu.id("hot_steam"))
            .gas()
            .color(0xd4d4d4)
            .buildAndRegister();
    public static final Material HotDeuterium = new Material.Builder(GTCEu.id("hot_deuterium"))
            .gas()
            .color(0xe6e857)
            .buildAndRegister();
    public static final Material HotSodium = new Material.Builder(GTCEu.id("hot_sodium"))
            .gas()
            .color(0x237ad1)
            .buildAndRegister();
    public static final Material HotSodiumPotassium = new Material.Builder(GTCEu.id("hot_sodium_potassium"))
            .gas()
            .color(0x39cf89)
            .buildAndRegister();
    public static final Material Livingrock = new Material.Builder(GTCEu.id("livingrock"))
            .dust()
            .color(0xfafafa)
            .buildAndRegister();
    public static final Material icestone = new Material.Builder(GTCEu.id("icestone"))
            .dust()
            .color(0xd7fffd)
            .buildAndRegister();
    public static final Material CombustibleIce = new Material.Builder(GTCEu.id("combustible_ice"))
            .gem()
            .iconSet(LIGNITE)
            .color(0xebfbfc)
            .burnTime(6000)
            .ore()
            .buildAndRegister();
    public static final Material ManaFused = new Material.Builder(GTCEu.id("mana_fused"))
            .dust()
            .ore()
            .color(0x4FC1FF)
            .buildAndRegister();
    public static final Material NQ_END_OF_GASOLINE =new Material.Builder(GTCEu.id("nq_end_gasoline"))
            .liquid()
            .color(0x000000)
            .element(CTNHElements.END_OF_OIL)
            .buildAndRegister();
    public static final Material LIVING_METAL=new Material.Builder(GTCEu.id("living_metal"))
            .liquid()
            .color(0x000000)
            .element(CTNHElements.Living_Metal)
            .buildAndRegister();
    public static final Material MysteryFluid = new Material.Builder(GTCEu.id("mystery_fluid"))
            .liquid()
            .color(0x4ded1c)
            .buildAndRegister();
    public static  final Material COLORFUL_GEM=new Material.Builder(GTCEu.id("colorful_gem"))
            .gem()
            .element(CTNHElements.COLORFUL_GEM)
            .color(0xFF0000) // 主颜色设为红色
            .secondaryColor(0x0000FF)
            .buildAndRegister();

    public static void init() {
        CombustibleIce.setFormula("(CH4)(H2O)", true);
        NuclearMaterials.init();
        TagPrefix.block.setIgnored(Moonstone, ModBlocks.MOON_STONE);
        TagPrefix.block.setIgnored(Marsstone, ModBlocks.MARS_STONE);
        TagPrefix.block.setIgnored(Venusstone, ModBlocks.VENUS_STONE);
        TagPrefix.block.setIgnored(Mercurystone, ModBlocks.MERCURY_STONE);
        TagPrefix.block.setIgnored(Glaciostone, ModBlocks.GLACIO_STONE);

        TagPrefix.block.setIgnored(Holystone, AetherBlocks.HOLYSTONE);

        TagPrefix.gem.setIgnored(Zanite, AetherItems.ZANITE_GEMSTONE);
        TagPrefix.block.setIgnored(Zanite, AetherBlocks.ZANITE_BLOCK);

        TagPrefix.gem.setIgnored(Ambrosium, AetherItems.AMBROSIUM_SHARD);
        TagPrefix.block.setIgnored(Ambrosium, AetherBlocks.AMBROSIUM_BLOCK);

        TagPrefix.gem.setIgnored(Skyjade, DAItems.SKYJADE);
        TagPrefix.block.setIgnored(Skyjade, DABlocks.SKYJADE_BLOCK);

        TagPrefix.ingot.setIgnored(Stratus, DAItems.STRATUS_INGOT);
        TagPrefix.block.setIgnored(Stratus, DABlocks.STRATUS_BLOCK);

        TagPrefix.block.setIgnored(Livingrock, BotaniaBlocks.livingrock);
    }

    public static class MaterialIcons {
        public static MaterialIconSet StarsteelIcon = new MaterialIconSet("starsteel", METALLIC);
    }

}
