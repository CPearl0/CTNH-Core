package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.CTNHCore;
import teamrazor.deepaether.init.DABlocks;
import teamrazor.deepaether.init.DAItems;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class CTNHMaterials {
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
    public static final Material Jasper = new Material.Builder(CTNHCore.id("jasper"))
            .gem()
            .ore()
            .color(0xC85050)
            .flags(GENERATE_PLATE, GENERATE_ROD)
            .iconSet(EMERALD)
            .buildAndRegister();
    public static final Material Eglinalloy = new Material.Builder(CTNHCore.id("eglin_alloy"))
            .ingot()
            .liquid()
            .color(0x6e3204)
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .iconSet(METALLIC)
            .blastTemp(1048)
            .components(Invar,5, Iron,4, Kanthal,1, Sulfur,1, Silver,1, Carbon,1)
            .buildAndRegister();
    public static final Material Inconel625 = new Material.Builder(CTNHCore.id("inconel_625"))
            .ingot()
            .liquid()
            .color(0x6eab6c)
            .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_FRAME, GENERATE_RING)
            .iconSet(METALLIC)
            .blastTemp(6200)
            .components(Nickel,2,Aluminium,2, Niobium,1,Nichrome,1)
            .buildAndRegister();
    public static final Material Abyssalalloy = new Material.Builder(CTNHCore.id("abyssalalloy"))
            .ingot().liquid()
            .blastTemp(10800, HIGHER, GTValues.VA[GTValues.UV], 1800)
            .components(StainlessSteel, 5,TungstenCarbide,5, Nichrome,5,Bronze,5,IncoloyMA956,5, Iodine, 1,Germanium,1,Radon,1 ,Hafnium,1)
            .color(0x9e706a)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UHV], 4, 64)
            .buildAndRegister();
    public static final Material Titansteel = new Material.Builder(CTNHCore.id("titansteel"))
            .ingot().liquid()
            .blastTemp(12600, HIGHER, GTValues.VA[GTValues.UHV], 1200)
            .components(TitaniumTungstenCarbide,4,Plutonium241,1, Einsteinium, 2,Rhenium,1, Erbium,1, Jasper,3)
            .color(0xe60000)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UEV], 4, 64)
            .buildAndRegister();
    public static final Material Pikyonium = new Material.Builder(CTNHCore.id("pikyonium"))
            .ingot()
            .liquid()
            .radioactiveHazard(3)
            .blastTemp(14400, HIGHER)
            .components(Inconel625,8, Eglinalloy,5, EnrichedNaquadahWaste,4, TungstenSteel,4, Cerium,3, Antimony,2, Platinum,2 ,Ytterbium,1)
            .color(0x67abff)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE)
            .cableProperties(GTValues.V[GTValues.UIV], 4, 128)
            .buildAndRegister();
    public static final Material BlackTitanium = new Material.Builder(CTNHCore.id("black_titanium"))
            .ingot()
            .liquid()
            .blastTemp(16200, HIGHEST, GTValues.VA[GTValues.UXV], 1400)
            .components(Titanium,26, Lanthanum,6, Tungsten,4, Cobalt,3, Manganese,2, Phosphorus,2, Palladium,2, Niobium,1, Argon,5)
            .color(0x6C003B)
            .iconSet(DULL)
            .flags(GENERATE_ROD, GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
            .cableProperties(GTValues.V[GTValues.UXV], 4, 128)
            .buildAndRegister();
    public static final Material Starmetal = new Material.Builder(CTNHCore.id("starmetal"))
            .ingot()
            .liquid()
            .plasma()
            .addOreByproducts(GTMaterials.Sapphire, GTMaterials.Polonium)
            .radioactiveHazard(6)
            .blastTemp(21800, HIGHEST)
            .element(CTNHElements.STARMETAL)
            .color(0x0000e6)
            .iconSet(METALLIC)
            .flags(GENERATE_FINE_WIRE)
            .cableProperties(GTValues.V[GTValues.OpV], 4, 256)
            .buildAndRegister();
    public static final Material Infinity = new Material.Builder(CTNHCore.id("my_infinity"))
            .ingot()
            .radioactiveHazard(20)
            .blastTemp(32000, HIGHEST)
            .element(CTNHElements.INFINITY)
            .iconSet(METALLIC)
            .flags(GENERATE_FRAME)
            .cableProperties(GTValues.V[GTValues.MAX], 8192, 0, true)
            .buildAndRegister();
    public static void init() {
        TagPrefix.block.setIgnored(Holystone, AetherBlocks.HOLYSTONE);

        TagPrefix.gem.setIgnored(Zanite, AetherItems.ZANITE_GEMSTONE);
        TagPrefix.block.setIgnored(Zanite, AetherBlocks.ZANITE_BLOCK);

        TagPrefix.gem.setIgnored(Ambrosium, AetherItems.AMBROSIUM_SHARD);
        TagPrefix.block.setIgnored(Ambrosium, AetherBlocks.AMBROSIUM_BLOCK);

        TagPrefix.gem.setIgnored(Skyjade, DAItems.SKYJADE);
        TagPrefix.block.setIgnored(Skyjade, DABlocks.SKYJADE_BLOCK);

        TagPrefix.ingot.setIgnored(Stratus, DAItems.STRATUS_INGOT);
        TagPrefix.block.setIgnored(Stratus, DABlocks.STRATUS_BLOCK);
    }
}
