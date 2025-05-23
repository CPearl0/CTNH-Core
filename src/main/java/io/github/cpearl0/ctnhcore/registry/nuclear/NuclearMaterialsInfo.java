package io.github.cpearl0.ctnhcore.registry.nuclear;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.common.data.GTElements;
import dev.arbor.gtnn.data.GTNNElement;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialBuilder;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialIconSet;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import io.github.cpearl0.ctnhcore.data.CTNHMaterialFlags;

import java.util.HashSet;
import java.util.Set;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.common.data.GTElements.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.arbor.gtnn.data.GTNNMaterials.Thorium232;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearElements.*;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.*;

public class NuclearMaterialsInfo {
    public static Set<Material> fertileMaterial = new HashSet<>();
    public static void register() {
        //basic material
        Uranium = new CTNHMaterialBuilder(GTCEu.id("uranium"))
                .ingot()
                .ore()
                .color(0x1c991c)
                .element(U)
                .flags(CTNHMaterialFlags.GENERATE_WASTE,GENERATE_FRAME, GENERATE_GEAR, GENERATE_ROD, GENERATE_FOIL, GENERATE_BOLT_SCREW, GENERATE_PLATE, GENERATE_SMALL_GEAR, GENERATE_DENSE, GENERATE_SMALL_GEAR)
                .iconSet(MaterialIconSet.SHINY)
                .radioactiveHazard(1)
                .register();
        Plutonium = new CTNHMaterialBuilder(GTCEu.id("uranium"))
                .ingot()
                .ore()
                .color(0x9e1616)
                .element(Pu)
                .flags(CTNHMaterialFlags.GENERATE_WASTE,GENERATE_FRAME, GENERATE_GEAR, GENERATE_ROD, GENERATE_FOIL, GENERATE_BOLT_SCREW, GENERATE_PLATE, GENERATE_SMALL_GEAR, GENERATE_DENSE, GENERATE_SMALL_GEAR)
                .iconSet(MaterialIconSet.SHINY)
                .radioactiveHazard(1)
                .register();
        PlutoniumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_hexafluoride"))
                .gas()
                .color(0x9e1616)
                .flags(DISABLE_DECOMPOSITION)
                .register();
        ThoriumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("thorium_hexafluoride"))
                .gas()
                .color(Thorium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        ProtactiniumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("protactinium_hexafluoride"))
                .gas()
                .color(Protactinium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        NeptuniumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("neptunium_hexafluoride"))
                .gas()
                .color(Neptunium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        AmericiumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("americium_hexafluoride"))
                .gas()
                .color(Americium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        CuriumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_hexafluoride"))
                .gas()
                .color(Curium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        BerkeliumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("berkelium_hexafluoride"))
                .gas()
                .color(Berkelium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        CaliforniumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_hexafluoride"))
                .gas()
                .color(Californium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        EinsteiniumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("einsteinium_hexafluoride"))
                .gas()
                .color(Einsteinium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        FermiumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_hexafluoride"))
                .gas()
                .color(Fermium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();
        MendeleviumHexafluoride = new CTNHMaterialBuilder(GTCEu.id("mendelevium_hexafluoride"))
                .gas()
                .color(Mendelevium.getMaterialRGB())
                .flags(DISABLE_DECOMPOSITION)
                .register();

        //thorium
        Thorium233 = new CTNHMaterialBuilder(GTCEu.id("thorium_233"))
                .nuclear(false,false)
                .color(0x474242)
                .element(Th233)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideThorium233 = new CTNHMaterialBuilder(GTCEu.id("carbide_thorium_233"))
                .nuclear(false,false)
                .color(0x474242)
                .components(Thorium233, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).setCarbideMaterial(CarbideThorium233);
        OxideThorium233 = new CTNHMaterialBuilder(GTCEu.id("oxide_thorium_233"))
                .nuclear(false,false)
                .color(0x474242)
                .components(Thorium233, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).setOxideMaterial(OxideThorium233);
        NitrideThorium233 = new CTNHMaterialBuilder(GTCEu.id("nitride_thorium_233"))
                .nuclear(false,false)
                .color(0x474242)
                .components(Thorium233, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).setNitrideMaterial(NitrideThorium233);
        ZirconiumAlloyThorium233 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_thorium_233"))
                .nuclear(false,false)
                .color(0x474242)
                .components(Thorium233, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).setZirconiumAlloyMaterial(ZirconiumAlloyThorium233);
        Thorium233Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("thorium_233_hexafluoride"))
                .gas()
                .color(4670018)
                .components(Thorium233, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Thorium233HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("thorium_233_hexafluoride_steam_cracked"))
                .gas()
                .color(4670038)
                .radioactiveHazard(1)
                .register();
        // thorium_232
//        Thorium232.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .fertileDecay.put(Thorium233, 100);
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .fertileDecay.put(Protactinium233, 1000);
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .fertileDecay.put(Uranium233, 8900);
//        fertileMaterial.add(Thorium232);
//        CarbideThorium232 = new CTNHMaterialBuilder(GTCEu.id("carbide_thorium_232"))
//                .nuclear(false, false)
//                .color(20)
//                .components(Thorium232, 1, Carbon, 1)
//                .iconSet(CTNHMaterialIconSet.CARBIDE)
//                .radioactiveHazard(1)
//                .flags(DISABLE_DECOMPOSITION).register();
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .setCarbideMaterial(CarbideThorium232);
//        OxideThorium232 = new CTNHMaterialBuilder(GTCEu.id("oxide_thorium_232"))
//                .nuclear(false, false)
//                .color(0)
//                .components(Thorium232, 1, Oxygen, 2)
//                .iconSet(CTNHMaterialIconSet.OXIDE)
//                .radioactiveHazard(1)
//                .flags(DISABLE_DECOMPOSITION).register();
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .setOxideMaterial(OxideThorium232);
//        NitrideThorium232 = new CTNHMaterialBuilder(GTCEu.id("nitride_thorium_232"))
//                .nuclear(false, false)
//                .color(-20)
//                .components(Thorium232, 3, Nitrogen, 2)
//                .iconSet(CTNHMaterialIconSet.NITRIDE)
//                .radioactiveHazard(1)
//                .flags(DISABLE_DECOMPOSITION).register();
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .setNitrideMaterial(NitrideThorium232);
//        ZirconiumAlloyThorium232 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_thorium_232"))
//                .nuclear(false, false)
//                .color(0)
//                .components(Thorium232, 1, Zirconium, 1)
//                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
//                .radioactiveHazard(1)
//                .flags(DISABLE_DECOMPOSITION).register();
//        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
//                .setZirconiumAlloyMaterial(ZirconiumAlloyThorium232);
//        Thorium232Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("thorium_232_hexafluoride"))
//                .gas()
//                .color(0)
//                .components(Thorium232, 1, Fluorine, 6)
//                .radioactiveHazard(1)
//                .register();
//        Thorium232HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("thorium_232_hexafluoride_steam_cracked"))
//                .gas()
//                .color(20)
//                .radioactiveHazard(1)
//                .register();

// protactinium_233
        Protactinium233 = new CTNHMaterialBuilder(GTCEu.id("protactinium_233"))
                .nuclear(false, false)
                .color(10585446)
                .element(Pa233)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideProtactinium233 = new CTNHMaterialBuilder(GTCEu.id("carbide_protactinium_233"))
                .nuclear(false, false)
                .color(10585466)
                .components(Protactinium233, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideProtactinium233);
        OxideProtactinium233 = new CTNHMaterialBuilder(GTCEu.id("oxide_protactinium_233"))
                .nuclear(false, false)
                .color(10585446)
                .components(Protactinium233, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideProtactinium233);
        NitrideProtactinium233 = new CTNHMaterialBuilder(GTCEu.id("nitride_protactinium_233"))
                .nuclear(false, false)
                .color(10585426)
                .components(Protactinium233, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideProtactinium233);
        ZirconiumAlloyProtactinium233 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_protactinium_233"))
                .nuclear(false, false)
                .color(10585446)
                .components(Protactinium233, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyProtactinium233);
        Protactinium233Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("protactinium_233_hexafluoride"))
                .gas()
                .color(10585446)
                .components(Protactinium233, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Protactinium233HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("protactinium_233_hexafluoride_steam_cracked"))
                .gas()
                .color(10585466)
                .radioactiveHazard(1)
                .register();

// uranium_233
        Uranium233 = new CTNHMaterialBuilder(GTCEu.id("uranium_233"))
                .nuclear(false, true)
                .color(2135584)
                .element(U233)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(7);
        CarbideUranium233 = new CTNHMaterialBuilder(GTCEu.id("carbide_uranium_233"))
                .nuclear(false, false)
                .color(2135604)
                .components(Uranium233, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideUranium233);
        OxideUranium233 = new CTNHMaterialBuilder(GTCEu.id("oxide_uranium_233"))
                .nuclear(false, false)
                .color(2135584)
                .components(Uranium233, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideUranium233);
        NitrideUranium233 = new CTNHMaterialBuilder(GTCEu.id("nitride_uranium_233"))
                .nuclear(false, false)
                .color(2135564)
                .components(Uranium233, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideUranium233);
        ZirconiumAlloyUranium233 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_uranium_233"))
                .nuclear(false, false)
                .color(2135584)
                .components(Uranium233, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyUranium233);
        Uranium233Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("uranium_233_hexafluoride"))
                .gas()
                .color(2135584)
                .components(Uranium233, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Uranium233HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("uranium_233_hexafluoride_steam_cracked"))
                .gas()
                .color(2135604)
                .radioactiveHazard(1)
                .register();

// uranium_234
        Uranium234 = new CTNHMaterialBuilder(GTCEu.id("uranium_234"))
                .nuclear(true, false)
                .color(1940509)
                .element(U234)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Uranium234);
        CarbideUranium234 = new CTNHMaterialBuilder(GTCEu.id("carbide_uranium_234"))
                .nuclear(false, false)
                .color(1940529)
                .components(Uranium234, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideUranium234);
        OxideUranium234 = new CTNHMaterialBuilder(GTCEu.id("oxide_uranium_234"))
                .nuclear(false, false)
                .color(1940509)
                .components(Uranium234, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideUranium234);
        NitrideUranium234 = new CTNHMaterialBuilder(GTCEu.id("nitride_uranium_234"))
                .nuclear(false, false)
                .color(1940489)
                .components(Uranium234, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideUranium234);
        ZirconiumAlloyUranium234 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_uranium_234"))
                .nuclear(false, false)
                .color(1940509)
                .components(Uranium234, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyUranium234);
        Uranium234Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("uranium_234_hexafluoride"))
                .gas()
                .color(1940509)
                .components(Uranium234, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Uranium234HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("uranium_234_hexafluoride_steam_cracked"))
                .gas()
                .color(1940529)
                .radioactiveHazard(1)
                .register();


// uranium_239
        Uranium239 = new CTNHMaterialBuilder(GTCEu.id("uranium_239"))
                .nuclear(false, false)
                .color(885261)
                .element(U239)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideUranium239 = new CTNHMaterialBuilder(GTCEu.id("carbide_uranium_239"))
                .nuclear(false, false)
                .color(885281)
                .components(Uranium239, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideUranium239);
        OxideUranium239 = new CTNHMaterialBuilder(GTCEu.id("oxide_uranium_239"))
                .nuclear(false, false)
                .color(885261)
                .components(Uranium239, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideUranium239);
        NitrideUranium239 = new CTNHMaterialBuilder(GTCEu.id("nitride_uranium_239"))
                .nuclear(false, false)
                .color(885241)
                .components(Uranium239, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideUranium239);
        ZirconiumAlloyUranium239 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_uranium_239"))
                .nuclear(false, false)
                .color(885261)
                .components(Uranium239, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyUranium239);
        Uranium239Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("uranium_239_hexafluoride"))
                .gas()
                .color(885261)
                .components(Uranium239, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Uranium239HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("uranium_239_hexafluoride_steam_cracked"))
                .gas()
                .color(885281)
                .radioactiveHazard(1)
                .register();

// uranium_238
        Uranium238.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
        fertileMaterial.add(Uranium238);
        CarbideUranium238 = new CTNHMaterialBuilder(GTCEu.id("carbide_uranium_238"))
                .nuclear(false, false)
                .color(Uranium238.getMaterialRGB())
                .components(Uranium238, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideUranium238);
        OxideUranium238 = new CTNHMaterialBuilder(GTCEu.id("oxide_uranium_238"))
                .nuclear(false, false)
                .color(Uranium238.getMaterialRGB())
                .components(Uranium238, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideUranium238);
        NitrideUranium238 = new CTNHMaterialBuilder(GTCEu.id("nitride_uranium_238"))
                .nuclear(false, false)
                .color(Uranium238.getMaterialRGB())
                .components(Uranium238, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideUranium238);
        ZirconiumAlloyUranium238 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_uranium_238"))
                .nuclear(false, false)
                .color(Uranium238.getMaterialRGB())
                .components(Uranium238, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyUranium238);

// uranium_235
        Uranium235.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(10);
        CarbideUranium235 = new CTNHMaterialBuilder(GTCEu.id("carbide_uranium_235"))
                .nuclear(false, false)
                .color(Uranium235.getMaterialRGB())
                .components(Uranium235, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideUranium235);
        OxideUranium235 = new CTNHMaterialBuilder(GTCEu.id("oxide_uranium_235"))
                .nuclear(false, false)
                .color(Uranium235.getMaterialRGB())
                .components(Uranium235, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideUranium235);
        NitrideUranium235 = new CTNHMaterialBuilder(GTCEu.id("nitride_uranium_235"))
                .nuclear(false, false)
                .color(Uranium235.getMaterialRGB())
                .components(Uranium235, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideUranium235);
        ZirconiumAlloyUranium235 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_uranium_235"))
                .nuclear(false, false)
                .color(Uranium235.getMaterialRGB())
                .components(Uranium235, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyUranium235);

// neptunium_235
        Neptunium235 = new CTNHMaterialBuilder(GTCEu.id("neptunium_235"))
                .nuclear(false, false)
                .color(2839687)
                .element(Np235)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideNeptunium235 = new CTNHMaterialBuilder(GTCEu.id("carbide_neptunium_235"))
                .nuclear(false, false)
                .color(2839707)
                .components(Neptunium235, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideNeptunium235);
        OxideNeptunium235 = new CTNHMaterialBuilder(GTCEu.id("oxide_neptunium_235"))
                .nuclear(false, false)
                .color(2839687)
                .components(Neptunium235, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideNeptunium235);
        NitrideNeptunium235 = new CTNHMaterialBuilder(GTCEu.id("nitride_neptunium_235"))
                .nuclear(false, false)
                .color(2839667)
                .components(Neptunium235, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideNeptunium235);
        ZirconiumAlloyNeptunium235 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_neptunium_235"))
                .nuclear(false, false)
                .color(2839687)
                .components(Neptunium235, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyNeptunium235);
        Neptunium235Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("neptunium_235_hexafluoride"))
                .gas()
                .color(2839687)
                .components(Neptunium235, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Neptunium235HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("neptunium_235_hexafluoride_steam_cracked"))
                .gas()
                .color(2839707)
                .radioactiveHazard(1)
                .register();

// neptunium_237
        Neptunium237 = new CTNHMaterialBuilder(GTCEu.id("neptunium_237"))
                .nuclear(false, true)
                .color(2445445)
                .element(Np237)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(11);
        CarbideNeptunium237 = new CTNHMaterialBuilder(GTCEu.id("carbide_neptunium_237"))
                .nuclear(false, false)
                .color(2445465)
                .components(Neptunium237, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideNeptunium237);
        OxideNeptunium237 = new CTNHMaterialBuilder(GTCEu.id("oxide_neptunium_237"))
                .nuclear(false, false)
                .color(2445445)
                .components(Neptunium237, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideNeptunium237);
        NitrideNeptunium237 = new CTNHMaterialBuilder(GTCEu.id("nitride_neptunium_237"))
                .nuclear(false, false)
                .color(2445425)
                .components(Neptunium237, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideNeptunium237);
        ZirconiumAlloyNeptunium237 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_neptunium_237"))
                .nuclear(false, false)
                .color(2445445)
                .components(Neptunium237, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyNeptunium237);
        Neptunium237Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("neptunium_237_hexafluoride"))
                .gas()
                .color(2445445)
                .components(Neptunium237, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Neptunium237HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("neptunium_237_hexafluoride_steam_cracked"))
                .gas()
                .color(2445465)
                .radioactiveHazard(1)
                .register();

// neptunium_239
        Neptunium239 = new CTNHMaterialBuilder(GTCEu.id("neptunium_239"))
                .nuclear(false, false)
                .color(2050944)
                .element(Np239)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideNeptunium239 = new CTNHMaterialBuilder(GTCEu.id("carbide_neptunium_239"))
                .nuclear(false, false)
                .color(2050964)
                .components(Neptunium239, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideNeptunium239);
        OxideNeptunium239 = new CTNHMaterialBuilder(GTCEu.id("oxide_neptunium_239"))
                .nuclear(false, false)
                .color(2050944)
                .components(Neptunium239, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideNeptunium239);
        NitrideNeptunium239 = new CTNHMaterialBuilder(GTCEu.id("nitride_neptunium_239"))
                .nuclear(false, false)
                .color(2050924)
                .components(Neptunium239, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideNeptunium239);
        ZirconiumAlloyNeptunium239 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_neptunium_239"))
                .nuclear(false, false)
                .color(2050944)
                .components(Neptunium239, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyNeptunium239);
        Neptunium239Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("neptunium_239_hexafluoride"))
                .gas()
                .color(2050944)
                .components(Neptunium239, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Neptunium239HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("neptunium_239_hexafluoride_steam_cracked"))
                .gas()
                .color(2050964)
                .radioactiveHazard(1)
                .register();

// plutonium_240
        Plutonium240 = new CTNHMaterialBuilder(GTCEu.id("plutonium_240"))
                .nuclear(true, false)
                .color(10559006)
                .element(Pu240)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Plutonium240);
        CarbidePlutonium240 = new CTNHMaterialBuilder(GTCEu.id("carbide_plutonium_240"))
                .nuclear(false, false)
                .color(10559026)
                .components(Plutonium240, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbidePlutonium240);
        OxidePlutonium240 = new CTNHMaterialBuilder(GTCEu.id("oxide_plutonium_240"))
                .nuclear(false, false)
                .color(10559006)
                .components(Plutonium240, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxidePlutonium240);
        NitridePlutonium240 = new CTNHMaterialBuilder(GTCEu.id("nitride_plutonium_240"))
                .nuclear(false, false)
                .color(10558986)
                .components(Plutonium240, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitridePlutonium240);
        ZirconiumAlloyPlutonium240 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_plutonium_240"))
                .nuclear(false, false)
                .color(10559006)
                .components(Plutonium240, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyPlutonium240);
        Plutonium240Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_240_hexafluoride"))
                .gas()
                .color(10559006)
                .components(Plutonium240, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Plutonium240HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("plutonium_240_hexafluoride_steam_cracked"))
                .gas()
                .color(10559026)
                .radioactiveHazard(1)
                .register();

// plutonium_244
        Plutonium244 = new CTNHMaterialBuilder(GTCEu.id("plutonium_244"))
                .nuclear(true, false)
                .color(9705496)
                .element(Pu244)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Plutonium244);
        CarbidePlutonium244 = new CTNHMaterialBuilder(GTCEu.id("carbide_plutonium_244"))
                .nuclear(false, false)
                .color(9705516)
                .components(Plutonium244, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbidePlutonium244);
        OxidePlutonium244 = new CTNHMaterialBuilder(GTCEu.id("oxide_plutonium_244"))
                .nuclear(false, false)
                .color(9705496)
                .components(Plutonium244, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxidePlutonium244);
        NitridePlutonium244 = new CTNHMaterialBuilder(GTCEu.id("nitride_plutonium_244"))
                .nuclear(false, false)
                .color(9705476)
                .components(Plutonium244, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitridePlutonium244);
        ZirconiumAlloyPlutonium244 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_plutonium_244"))
                .nuclear(false, false)
                .color(9705496)
                .components(Plutonium244, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyPlutonium244);
        Plutonium244Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_244_hexafluoride"))
                .gas()
                .color(9705496)
                .components(Plutonium244, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Plutonium244HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("plutonium_244_hexafluoride_steam_cracked"))
                .gas()
                .color(9705516)
                .radioactiveHazard(1)
                .register();

// plutonium_245
        Plutonium245 = new CTNHMaterialBuilder(GTCEu.id("plutonium_245"))
                .nuclear(false, false)
                .color(10229013)
                .element(Pu245)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbidePlutonium245 = new CTNHMaterialBuilder(GTCEu.id("carbide_plutonium_245"))
                .nuclear(false, false)
                .color(10229033)
                .components(Plutonium245, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbidePlutonium245);
        OxidePlutonium245 = new CTNHMaterialBuilder(GTCEu.id("oxide_plutonium_245"))
                .nuclear(false, false)
                .color(10229013)
                .components(Plutonium245, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxidePlutonium245);
        NitridePlutonium245 = new CTNHMaterialBuilder(GTCEu.id("nitride_plutonium_245"))
                .nuclear(false, false)
                .color(10228993)
                .components(Plutonium245, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitridePlutonium245);
        ZirconiumAlloyPlutonium245 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_plutonium_245"))
                .nuclear(false, false)
                .color(10229013)
                .components(Plutonium245, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyPlutonium245);
        Plutonium245Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_245_hexafluoride"))
                .gas()
                .color(10229013)
                .components(Plutonium245, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Plutonium245HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("plutonium_245_hexafluoride_steam_cracked"))
                .gas()
                .color(10229033)
                .radioactiveHazard(1)
                .register();

// plutonium_239
        Plutonium239.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(10);
        CarbidePlutonium239 = new CTNHMaterialBuilder(GTCEu.id("carbide_plutonium_239"))
                .nuclear(false, false)
                .color(Plutonium239.getMaterialRGB())
                .components(Plutonium239, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbidePlutonium239);
        OxidePlutonium239 = new CTNHMaterialBuilder(GTCEu.id("oxide_plutonium_239"))
                .nuclear(false, false)
                .color(Plutonium239.getMaterialRGB())
                .components(Plutonium239, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxidePlutonium239);
        NitridePlutonium239 = new CTNHMaterialBuilder(GTCEu.id("nitride_plutonium_239"))
                .nuclear(false, false)
                .color(Plutonium239.getMaterialRGB())
                .components(Plutonium239, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitridePlutonium239);
        ZirconiumAlloyPlutonium239 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_plutonium_239"))
                .nuclear(false, false)
                .color(Plutonium239.getMaterialRGB())
                .components(Plutonium239, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyPlutonium239);
        Plutonium239Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_239_hexafluoride"))
                .gas()
                .color(Plutonium239.getMaterialRGB())
                .components(Plutonium239, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Plutonium239HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("plutonium_239_hexafluoride_steam_cracked"))
                .gas()
                .color(Plutonium239.getMaterialRGB())
                .radioactiveHazard(1)
                .register();

// plutonium_241
        Plutonium241.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(13);
        CarbidePlutonium241 = new CTNHMaterialBuilder(GTCEu.id("carbide_plutonium_241"))
                .nuclear(false, false)
                .color(Plutonium241.getMaterialRGB())
                .components(Plutonium241, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbidePlutonium241);
        OxidePlutonium241 = new CTNHMaterialBuilder(GTCEu.id("oxide_plutonium_241"))
                .nuclear(false, false)
                .color(Plutonium241.getMaterialRGB())
                .components(Plutonium241, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxidePlutonium241);
        NitridePlutonium241 = new CTNHMaterialBuilder(GTCEu.id("nitride_plutonium_241"))
                .nuclear(false, false)
                .color(Plutonium241.getMaterialRGB())
                .components(Plutonium241, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitridePlutonium241);
        ZirconiumAlloyPlutonium241 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_plutonium_241"))
                .nuclear(false, false)
                .color(Plutonium241.getMaterialRGB())
                .components(Plutonium241, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyPlutonium241);
        Plutonium241Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("plutonium_241_hexafluoride"))
                .gas()
                .color(Plutonium241.getMaterialRGB())
                .components(Plutonium241, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Plutonium241HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("plutonium_241_hexafluoride_steam_cracked"))
                .gas()
                .color(Plutonium241.getMaterialRGB())
                .radioactiveHazard(1)
                .register();

// americium_241
        Americium241 = new CTNHMaterialBuilder(GTCEu.id("americium_241"))
                .nuclear(false, false)
                .color(2522476)
                .element(Am241)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideAmericium241 = new CTNHMaterialBuilder(GTCEu.id("carbide_americium_241"))
                .nuclear(false, false)
                .color(2522496)
                .components(Americium241, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideAmericium241);
        OxideAmericium241 = new CTNHMaterialBuilder(GTCEu.id("oxide_americium_241"))
                .nuclear(false, false)
                .color(2522476)
                .components(Americium241, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideAmericium241);
        NitrideAmericium241 = new CTNHMaterialBuilder(GTCEu.id("nitride_americium_241"))
                .nuclear(false, false)
                .color(2522456)
                .components(Americium241, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideAmericium241);
        ZirconiumAlloyAmericium241 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_americium_241"))
                .nuclear(false, false)
                .color(2522476)
                .components(Americium241, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyAmericium241);
        Americium241Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("americium_241_hexafluoride"))
                .gas()
                .color(2522476)
                .components(Americium241, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Americium241HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("americium_241_hexafluoride_steam_cracked"))
                .gas()
                .color(2522496)
                .radioactiveHazard(1)
                .register();

// americium_243
        Americium243 = new CTNHMaterialBuilder(GTCEu.id("americium_243"))
                .nuclear(false, true)
                .color(2325867)
                .element(Am243)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(14);
        CarbideAmericium243 = new CTNHMaterialBuilder(GTCEu.id("carbide_americium_243"))
                .nuclear(false, false)
                .color(2325887)
                .components(Americium243, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideAmericium243);
        OxideAmericium243 = new CTNHMaterialBuilder(GTCEu.id("oxide_americium_243"))
                .nuclear(false, false)
                .color(2325867)
                .components(Americium243, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideAmericium243);
        NitrideAmericium243 = new CTNHMaterialBuilder(GTCEu.id("nitride_americium_243"))
                .nuclear(false, false)
                .color(2325847)
                .components(Americium243, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideAmericium243);
        ZirconiumAlloyAmericium243 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_americium_243"))
                .nuclear(false, false)
                .color(2325867)
                .components(Americium243, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyAmericium243);
        Americium243Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("americium_243_hexafluoride"))
                .gas()
                .color(2325867)
                .components(Americium243, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Americium243HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("americium_243_hexafluoride_steam_cracked"))
                .gas()
                .color(2325887)
                .radioactiveHazard(1)
                .register();

// americium_245
        Americium245 = new CTNHMaterialBuilder(GTCEu.id("americium_245"))
                .nuclear(false, false)
                .color(1996902)
                .element(Am245)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideAmericium245 = new CTNHMaterialBuilder(GTCEu.id("carbide_americium_245"))
                .nuclear(false, false)
                .color(1996922)
                .components(Americium245, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideAmericium245);
        OxideAmericium245 = new CTNHMaterialBuilder(GTCEu.id("oxide_americium_245"))
                .nuclear(false, false)
                .color(1996902)
                .components(Americium245, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideAmericium245);
        NitrideAmericium245 = new CTNHMaterialBuilder(GTCEu.id("nitride_americium_245"))
                .nuclear(false, false)
                .color(1996882)
                .components(Americium245, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideAmericium245);
        ZirconiumAlloyAmericium245 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_americium_245"))
                .nuclear(false, false)
                .color(1996902)
                .components(Americium245, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyAmericium245);
        Americium245Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("americium_245_hexafluoride"))
                .gas()
                .color(1996902)
                .components(Americium245, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Americium245HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("americium_245_hexafluoride_steam_cracked"))
                .gas()
                .color(1996922)
                .radioactiveHazard(1)
                .register();

// curium_245
        Curium245 = new CTNHMaterialBuilder(GTCEu.id("curium_245"))
                .nuclear(false, true)
                .color(7227202)
                .element(Cm245)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(13);
        CarbideCurium245 = new CTNHMaterialBuilder(GTCEu.id("carbide_curium_245"))
                .nuclear(false, false)
                .color(7227222)
                .components(Curium245, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCurium245);
        OxideCurium245 = new CTNHMaterialBuilder(GTCEu.id("oxide_curium_245"))
                .nuclear(false, false)
                .color(7227202)
                .components(Curium245, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCurium245);
        NitrideCurium245 = new CTNHMaterialBuilder(GTCEu.id("nitride_curium_245"))
                .nuclear(false, false)
                .color(7227182)
                .components(Curium245, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCurium245);
        ZirconiumAlloyCurium245 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_curium_245"))
                .nuclear(false, false)
                .color(7227202)
                .components(Curium245, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCurium245);
        Curium245Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_245_hexafluoride"))
                .gas()
                .color(7227202)
                .components(Curium245, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Curium245HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("curium_245_hexafluoride_steam_cracked"))
                .gas()
                .color(7227222)
                .radioactiveHazard(1)
                .register();

// curium_246
        Curium246 = new CTNHMaterialBuilder(GTCEu.id("curium_246"))
                .nuclear(true, false)
                .color(7029566)
                .element(Cm246)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Curium246);
        CarbideCurium246 = new CTNHMaterialBuilder(GTCEu.id("carbide_curium_246"))
                .nuclear(false, false)
                .color(7029586)
                .components(Curium246, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCurium246);
        OxideCurium246 = new CTNHMaterialBuilder(GTCEu.id("oxide_curium_246"))
                .nuclear(false, false)
                .color(7029566)
                .components(Curium246, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCurium246);
        NitrideCurium246 = new CTNHMaterialBuilder(GTCEu.id("nitride_curium_246"))
                .nuclear(false, false)
                .color(7029546)
                .components(Curium246, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCurium246);
        ZirconiumAlloyCurium246 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_curium_246"))
                .nuclear(false, false)
                .color(7029566)
                .components(Curium246, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCurium246);
        Curium246Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_246_hexafluoride"))
                .gas()
                .color(7029566)
                .components(Curium246, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Curium246HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("curium_246_hexafluoride_steam_cracked"))
                .gas()
                .color(7029586)
                .radioactiveHazard(1)
                .register();

// curium_247
        Curium247 = new CTNHMaterialBuilder(GTCEu.id("curium_247"))
                .nuclear(false, true)
                .color(6372407)
                .element(Cm247)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(16);
        CarbideCurium247 = new CTNHMaterialBuilder(GTCEu.id("carbide_curium_247"))
                .nuclear(false, false)
                .color(6372427)
                .components(Curium247, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCurium247);
        OxideCurium247 = new CTNHMaterialBuilder(GTCEu.id("oxide_curium_247"))
                .nuclear(false, false)
                .color(6372407)
                .components(Curium247, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCurium247);
        NitrideCurium247 = new CTNHMaterialBuilder(GTCEu.id("nitride_curium_247"))
                .nuclear(false, false)
                .color(6372387)
                .components(Curium247, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCurium247);
        ZirconiumAlloyCurium247 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_curium_247"))
                .nuclear(false, false)
                .color(6372407)
                .components(Curium247, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCurium247);
        Curium247Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_247_hexafluoride"))
                .gas()
                .color(6372407)
                .components(Curium247, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Curium247HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("curium_247_hexafluoride_steam_cracked"))
                .gas()
                .color(6372427)
                .radioactiveHazard(1)
                .register();

// curium_250
        Curium250 = new CTNHMaterialBuilder(GTCEu.id("curium_250"))
                .nuclear(true, false)
                .color(6043442)
                .element(Cm250)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Curium250);
        CarbideCurium250 = new CTNHMaterialBuilder(GTCEu.id("carbide_curium_250"))
                .nuclear(false, false)
                .color(6043462)
                .components(Curium250, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCurium250);
        OxideCurium250 = new CTNHMaterialBuilder(GTCEu.id("oxide_curium_250"))
                .nuclear(false, false)
                .color(6043442)
                .components(Curium250, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCurium250);
        NitrideCurium250 = new CTNHMaterialBuilder(GTCEu.id("nitride_curium_250"))
                .nuclear(false, false)
                .color(6043422)
                .components(Curium250, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCurium250);
        ZirconiumAlloyCurium250 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_curium_250"))
                .nuclear(false, false)
                .color(6043442)
                .components(Curium250, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCurium250);
        Curium250Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_250_hexafluoride"))
                .gas()
                .color(6043442)
                .components(Curium250, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Curium250HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("curium_250_hexafluoride_steam_cracked"))
                .gas()
                .color(6043462)
                .radioactiveHazard(1)
                .register();

// curium_251
        Curium251 = new CTNHMaterialBuilder(GTCEu.id("curium_251"))
                .nuclear(false, false)
                .color(5188905)
                .element(Cm251)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideCurium251 = new CTNHMaterialBuilder(GTCEu.id("carbide_curium_251"))
                .nuclear(false, false)
                .color(5188925)
                .components(Curium251, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCurium251);
        OxideCurium251 = new CTNHMaterialBuilder(GTCEu.id("oxide_curium_251"))
                .nuclear(false, false)
                .color(5188905)
                .components(Curium251, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCurium251);
        NitrideCurium251 = new CTNHMaterialBuilder(GTCEu.id("nitride_curium_251"))
                .nuclear(false, false)
                .color(5188885)
                .components(Curium251, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCurium251);
        ZirconiumAlloyCurium251 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_curium_251"))
                .nuclear(false, false)
                .color(5188905)
                .components(Curium251, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCurium251);
        Curium251Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("curium_251_hexafluoride"))
                .gas()
                .color(5188905)
                .components(Curium251, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Curium251HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("curium_251_hexafluoride_steam_cracked"))
                .gas()
                .color(5188925)
                .radioactiveHazard(1)
                .register();

// berkelium_247
        Berkelium247 = new CTNHMaterialBuilder(GTCEu.id("berkelium_247"))
                .nuclear(false, false)
                .color(5590136)
                .element(Bk247)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideBerkelium247 = new CTNHMaterialBuilder(GTCEu.id("carbide_berkelium_247"))
                .nuclear(false, false)
                .color(5590156)
                .components(Berkelium247, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideBerkelium247);
        OxideBerkelium247 = new CTNHMaterialBuilder(GTCEu.id("oxide_berkelium_247"))
                .nuclear(false, false)
                .color(5590136)
                .components(Berkelium247, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideBerkelium247);
        NitrideBerkelium247 = new CTNHMaterialBuilder(GTCEu.id("nitride_berkelium_247"))
                .nuclear(false, false)
                .color(5590116)
                .components(Berkelium247, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideBerkelium247);
        ZirconiumAlloyBerkelium247 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_berkelium_247"))
                .nuclear(false, false)
                .color(5590136)
                .components(Berkelium247, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyBerkelium247);
        Berkelium247Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("berkelium_247_hexafluoride"))
                .gas()
                .color(5590136)
                .components(Berkelium247, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Berkelium247HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("berkelium_247_hexafluoride_steam_cracked"))
                .gas()
                .color(5590156)
                .radioactiveHazard(1)
                .register();

// berkelium_249
        Berkelium249 = new CTNHMaterialBuilder(GTCEu.id("berkelium_249"))
                .nuclear(false, true)
                .color(5260915)
                .element(Bk249)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(17);
        CarbideBerkelium249 = new CTNHMaterialBuilder(GTCEu.id("carbide_berkelium_249"))
                .nuclear(false, false)
                .color(5260935)
                .components(Berkelium249, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideBerkelium249);
        OxideBerkelium249 = new CTNHMaterialBuilder(GTCEu.id("oxide_berkelium_249"))
                .nuclear(false, false)
                .color(5260915)
                .components(Berkelium249, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideBerkelium249);
        NitrideBerkelium249 = new CTNHMaterialBuilder(GTCEu.id("nitride_berkelium_249"))
                .nuclear(false, false)
                .color(5260895)
                .components(Berkelium249, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideBerkelium249);
        ZirconiumAlloyBerkelium249 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_berkelium_249"))
                .nuclear(false, false)
                .color(5260915)
                .components(Berkelium249, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyBerkelium249);
        Berkelium249Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("berkelium_249_hexafluoride"))
                .gas()
                .color(5260915)
                .components(Berkelium249, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Berkelium249HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("berkelium_249_hexafluoride_steam_cracked"))
                .gas()
                .color(5260935)
                .radioactiveHazard(1)
                .register();

// berkelium_251
        Berkelium251 = new CTNHMaterialBuilder(GTCEu.id("berkelium_251"))
                .nuclear(false, false)
                .color(4866158)
                .element(Bk251)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideBerkelium251 = new CTNHMaterialBuilder(GTCEu.id("carbide_berkelium_251"))
                .nuclear(false, false)
                .color(4866178)
                .components(Berkelium251, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideBerkelium251);
        OxideBerkelium251 = new CTNHMaterialBuilder(GTCEu.id("oxide_berkelium_251"))
                .nuclear(false, false)
                .color(4866158)
                .components(Berkelium251, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideBerkelium251);
        NitrideBerkelium251 = new CTNHMaterialBuilder(GTCEu.id("nitride_berkelium_251"))
                .nuclear(false, false)
                .color(4866138)
                .components(Berkelium251, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideBerkelium251);
        ZirconiumAlloyBerkelium251 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_berkelium_251"))
                .nuclear(false, false)
                .color(4866158)
                .components(Berkelium251, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyBerkelium251);
        Berkelium251Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("berkelium_251_hexafluoride"))
                .gas()
                .color(4866158)
                .components(Berkelium251, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Berkelium251HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("berkelium_251_hexafluoride_steam_cracked"))
                .gas()
                .color(4866178)
                .radioactiveHazard(1)
                .register();

// californium_251
        Californium251 = new CTNHMaterialBuilder(GTCEu.id("californium_251"))
                .nuclear(false, true)
                .color(10704144)
                .element(Cf251)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(16);
        CarbideCalifornium251 = new CTNHMaterialBuilder(GTCEu.id("carbide_californium_251"))
                .nuclear(false, false)
                .color(10704164)
                .components(Californium251, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCalifornium251);
        OxideCalifornium251 = new CTNHMaterialBuilder(GTCEu.id("oxide_californium_251"))
                .nuclear(false, false)
                .color(10704144)
                .components(Californium251, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCalifornium251);
        NitrideCalifornium251 = new CTNHMaterialBuilder(GTCEu.id("nitride_californium_251"))
                .nuclear(false, false)
                .color(10704124)
                .components(Californium251, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCalifornium251);
        ZirconiumAlloyCalifornium251 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_californium_251"))
                .nuclear(false, false)
                .color(10704144)
                .components(Californium251, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCalifornium251);
        Californium251Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_251_hexafluoride"))
                .gas()
                .color(10704144)
                .components(Californium251, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Californium251HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("californium_251_hexafluoride_steam_cracked"))
                .gas()
                .color(10704164)
                .radioactiveHazard(1)
                .register();

// californium_252
        Californium252 = new CTNHMaterialBuilder(GTCEu.id("californium_252"))
                .nuclear(true, false)
                .color(10244109)
                .element(Cf252)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Californium252);
        CarbideCalifornium252 = new CTNHMaterialBuilder(GTCEu.id("carbide_californium_252"))
                .nuclear(false, false)
                .color(10244129)
                .components(Californium252, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCalifornium252);
        OxideCalifornium252 = new CTNHMaterialBuilder(GTCEu.id("oxide_californium_252"))
                .nuclear(false, false)
                .color(10244109)
                .components(Californium252, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCalifornium252);
        NitrideCalifornium252 = new CTNHMaterialBuilder(GTCEu.id("nitride_californium_252"))
                .nuclear(false, false)
                .color(10244089)
                .components(Californium252, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCalifornium252);
        ZirconiumAlloyCalifornium252 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_californium_252"))
                .nuclear(false, false)
                .color(10244109)
                .components(Californium252, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCalifornium252);
        Californium252Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_252_hexafluoride"))
                .gas()
                .color(10244109)
                .components(Californium252, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Californium252HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("californium_252_hexafluoride_steam_cracked"))
                .gas()
                .color(10244129)
                .radioactiveHazard(1)
                .register();

// californium_253
        Californium253 = new CTNHMaterialBuilder(GTCEu.id("californium_253"))
                .nuclear(false, true)
                .color(9193226)
                .element(Cf253)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(19);
        CarbideCalifornium253 = new CTNHMaterialBuilder(GTCEu.id("carbide_californium_253"))
                .nuclear(false, false)
                .color(9193246)
                .components(Californium253, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCalifornium253);
        OxideCalifornium253 = new CTNHMaterialBuilder(GTCEu.id("oxide_californium_253"))
                .nuclear(false, false)
                .color(9193226)
                .components(Californium253, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCalifornium253);
        NitrideCalifornium253 = new CTNHMaterialBuilder(GTCEu.id("nitride_californium_253"))
                .nuclear(false, false)
                .color(9193206)
                .components(Californium253, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCalifornium253);
        ZirconiumAlloyCalifornium253 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_californium_253"))
                .nuclear(false, false)
                .color(9193226)
                .components(Californium253, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCalifornium253);
        Californium253Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_253_hexafluoride"))
                .gas()
                .color(9193226)
                .components(Californium253, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Californium253HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("californium_253_hexafluoride_steam_cracked"))
                .gas()
                .color(9193246)
                .radioactiveHazard(1)
                .register();

// californium_256
        Californium256 = new CTNHMaterialBuilder(GTCEu.id("californium_256"))
                .nuclear(true, false)
                .color(8536327)
                .element(Cf256)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Californium256);
        CarbideCalifornium256 = new CTNHMaterialBuilder(GTCEu.id("carbide_californium_256"))
                .nuclear(false, false)
                .color(8536347)
                .components(Californium256, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCalifornium256);
        OxideCalifornium256 = new CTNHMaterialBuilder(GTCEu.id("oxide_californium_256"))
                .nuclear(false, false)
                .color(8536327)
                .components(Californium256, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCalifornium256);
        NitrideCalifornium256 = new CTNHMaterialBuilder(GTCEu.id("nitride_californium_256"))
                .nuclear(false, false)
                .color(8536307)
                .components(Californium256, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCalifornium256);
        ZirconiumAlloyCalifornium256 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_californium_256"))
                .nuclear(false, false)
                .color(8536327)
                .components(Californium256, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCalifornium256);
        Californium256Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_256_hexafluoride"))
                .gas()
                .color(8536327)
                .components(Californium256, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Californium256HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("californium_256_hexafluoride_steam_cracked"))
                .gas()
                .color(8536347)
                .radioactiveHazard(1)
                .register();

// californium_257
        Californium257 = new CTNHMaterialBuilder(GTCEu.id("californium_257"))
                .nuclear(false, false)
                .color(7879687)
                .element(Cf257)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideCalifornium257 = new CTNHMaterialBuilder(GTCEu.id("carbide_californium_257"))
                .nuclear(false, false)
                .color(7879707)
                .components(Californium257, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideCalifornium257);
        OxideCalifornium257 = new CTNHMaterialBuilder(GTCEu.id("oxide_californium_257"))
                .nuclear(false, false)
                .color(7879687)
                .components(Californium257, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideCalifornium257);
        NitrideCalifornium257 = new CTNHMaterialBuilder(GTCEu.id("nitride_californium_257"))
                .nuclear(false, false)
                .color(7879667)
                .components(Californium257, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideCalifornium257);
        ZirconiumAlloyCalifornium257 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_californium_257"))
                .nuclear(false, false)
                .color(7879687)
                .components(Californium257, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyCalifornium257);
        Californium257Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("californium_257_hexafluoride"))
                .gas()
                .color(7879687)
                .components(Californium257, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Californium257HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("californium_257_hexafluoride_steam_cracked"))
                .gas()
                .color(7879707)
                .radioactiveHazard(1)
                .register();

// einsteinium_253
        Einsteinium253 = new CTNHMaterialBuilder(GTCEu.id("einsteinium_253"))
                .nuclear(false, false)
                .color(11897601)
                .element(Es253)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideEinsteinium253 = new CTNHMaterialBuilder(GTCEu.id("carbide_einsteinium_253"))
                .nuclear(false, false)
                .color(11897621)
                .components(Einsteinium253, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideEinsteinium253);
        OxideEinsteinium253 = new CTNHMaterialBuilder(GTCEu.id("oxide_einsteinium_253"))
                .nuclear(false, false)
                .color(11897601)
                .components(Einsteinium253, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideEinsteinium253);
        NitrideEinsteinium253 = new CTNHMaterialBuilder(GTCEu.id("nitride_einsteinium_253"))
                .nuclear(false, false)
                .color(11897581)
                .components(Einsteinium253, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideEinsteinium253);
        ZirconiumAlloyEinsteinium253 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_einsteinium_253"))
                .nuclear(false, false)
                .color(11897601)
                .components(Einsteinium253, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyEinsteinium253);
        Einsteinium253Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("einsteinium_253_hexafluoride"))
                .gas()
                .color(11897601)
                .components(Einsteinium253, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Einsteinium253HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("einsteinium_253_hexafluoride_steam_cracked"))
                .gas()
                .color(11897621)
                .radioactiveHazard(1)
                .register();

// einsteinium_255
        Einsteinium255 = new CTNHMaterialBuilder(GTCEu.id("einsteinium_255"))
                .nuclear(false, true)
                .color(10911488)
                .element(Es255)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(20);
        CarbideEinsteinium255 = new CTNHMaterialBuilder(GTCEu.id("carbide_einsteinium_255"))
                .nuclear(false, false)
                .color(10911508)
                .components(Einsteinium255, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideEinsteinium255);
        OxideEinsteinium255 = new CTNHMaterialBuilder(GTCEu.id("oxide_einsteinium_255"))
                .nuclear(false, false)
                .color(10911488)
                .components(Einsteinium255, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideEinsteinium255);
        NitrideEinsteinium255 = new CTNHMaterialBuilder(GTCEu.id("nitride_einsteinium_255"))
                .nuclear(false, false)
                .color(10911468)
                .components(Einsteinium255, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideEinsteinium255);
        ZirconiumAlloyEinsteinium255 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_einsteinium_255"))
                .nuclear(false, false)
                .color(10911488)
                .components(Einsteinium255, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyEinsteinium255);
        Einsteinium255Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("einsteinium_255_hexafluoride"))
                .gas()
                .color(10911488)
                .components(Einsteinium255, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Einsteinium255HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("einsteinium_255_hexafluoride_steam_cracked"))
                .gas()
                .color(10911508)
                .radioactiveHazard(1)
                .register();

// einsteinium_257
        Einsteinium257 = new CTNHMaterialBuilder(GTCEu.id("einsteinium_257"))
                .nuclear(false, false)
                .color(10254080)
                .element(Es257)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideEinsteinium257 = new CTNHMaterialBuilder(GTCEu.id("carbide_einsteinium_257"))
                .nuclear(false, false)
                .color(10254100)
                .components(Einsteinium257, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideEinsteinium257);
        OxideEinsteinium257 = new CTNHMaterialBuilder(GTCEu.id("oxide_einsteinium_257"))
                .nuclear(false, false)
                .color(10254080)
                .components(Einsteinium257, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideEinsteinium257);
        NitrideEinsteinium257 = new CTNHMaterialBuilder(GTCEu.id("nitride_einsteinium_257"))
                .nuclear(false, false)
                .color(10254060)
                .components(Einsteinium257, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideEinsteinium257);
        ZirconiumAlloyEinsteinium257 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_einsteinium_257"))
                .nuclear(false, false)
                .color(10254080)
                .components(Einsteinium257, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyEinsteinium257);
        Einsteinium257Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("einsteinium_257_hexafluoride"))
                .gas()
                .color(10254080)
                .components(Einsteinium257, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Einsteinium257HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("einsteinium_257_hexafluoride_steam_cracked"))
                .gas()
                .color(10254100)
                .radioactiveHazard(1)
                .register();

// fermium_257
        Fermium257 = new CTNHMaterialBuilder(GTCEu.id("fermium_257"))
                .nuclear(false, true)
                .color(12292313)
                .element(Fm257)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(19);
        CarbideFermium257 = new CTNHMaterialBuilder(GTCEu.id("carbide_fermium_257"))
                .nuclear(false, false)
                .color(12292333)
                .components(Fermium257, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideFermium257);
        OxideFermium257 = new CTNHMaterialBuilder(GTCEu.id("oxide_fermium_257"))
                .nuclear(false, false)
                .color(12292313)
                .components(Fermium257, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideFermium257);
        NitrideFermium257 = new CTNHMaterialBuilder(GTCEu.id("nitride_fermium_257"))
                .nuclear(false, false)
                .color(12292293)
                .components(Fermium257, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideFermium257);
        ZirconiumAlloyFermium257 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_fermium_257"))
                .nuclear(false, false)
                .color(12292313)
                .components(Fermium257, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyFermium257);
        Fermium257Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_257_hexafluoride"))
                .gas()
                .color(12292313)
                .components(Fermium257, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Fermium257HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("fermium_257_hexafluoride_steam_cracked"))
                .gas()
                .color(12292333)
                .radioactiveHazard(1)
                .register();

// fermium_258
        Fermium258 = new CTNHMaterialBuilder(GTCEu.id("fermium_258"))
                .nuclear(true, false)
                .color(11437260)
                .element(Fm258)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Fermium258);
        CarbideFermium258 = new CTNHMaterialBuilder(GTCEu.id("carbide_fermium_258"))
                .nuclear(false, false)
                .color(11437280)
                .components(Fermium258, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideFermium258);
        OxideFermium258 = new CTNHMaterialBuilder(GTCEu.id("oxide_fermium_258"))
                .nuclear(false, false)
                .color(11437260)
                .components(Fermium258, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideFermium258);
        NitrideFermium258 = new CTNHMaterialBuilder(GTCEu.id("nitride_fermium_258"))
                .nuclear(false, false)
                .color(11437240)
                .components(Fermium258, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideFermium258);
        ZirconiumAlloyFermium258 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_fermium_258"))
                .nuclear(false, false)
                .color(11437260)
                .components(Fermium258, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyFermium258);
        Fermium258Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_258_hexafluoride"))
                .gas()
                .color(11437260)
                .components(Fermium258, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Fermium258HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("fermium_258_hexafluoride_steam_cracked"))
                .gas()
                .color(11437280)
                .radioactiveHazard(1)
                .register();

// fermium_259
        Fermium259 = new CTNHMaterialBuilder(GTCEu.id("fermium_259"))
                .nuclear(false, true)
                .color(10779330)
                .element(Fm259)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(22);
        CarbideFermium259 = new CTNHMaterialBuilder(GTCEu.id("carbide_fermium_259"))
                .nuclear(false, false)
                .color(10779350)
                .components(Fermium259, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideFermium259);
        OxideFermium259 = new CTNHMaterialBuilder(GTCEu.id("oxide_fermium_259"))
                .nuclear(false, false)
                .color(10779330)
                .components(Fermium259, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideFermium259);
        NitrideFermium259 = new CTNHMaterialBuilder(GTCEu.id("nitride_fermium_259"))
                .nuclear(false, false)
                .color(10779310)
                .components(Fermium259, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideFermium259);
        ZirconiumAlloyFermium259 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_fermium_259"))
                .nuclear(false, false)
                .color(10779330)
                .components(Fermium259, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyFermium259);
        Fermium259Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_259_hexafluoride"))
                .gas()
                .color(10779330)
                .components(Fermium259, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Fermium259HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("fermium_259_hexafluoride_steam_cracked"))
                .gas()
                .color(10779350)
                .radioactiveHazard(1)
                .register();

// fermium_262
        Fermium262 = new CTNHMaterialBuilder(GTCEu.id("fermium_262"))
                .nuclear(true, false)
                .color(10318778)
                .element(Fm262)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        fertileMaterial.add(Fermium262);
        CarbideFermium262 = new CTNHMaterialBuilder(GTCEu.id("carbide_fermium_262"))
                .nuclear(false, false)
                .color(10318798)
                .components(Fermium262, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideFermium262);
        OxideFermium262 = new CTNHMaterialBuilder(GTCEu.id("oxide_fermium_262"))
                .nuclear(false, false)
                .color(10318778)
                .components(Fermium262, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideFermium262);
        NitrideFermium262 = new CTNHMaterialBuilder(GTCEu.id("nitride_fermium_262"))
                .nuclear(false, false)
                .color(10318758)
                .components(Fermium262, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideFermium262);
        ZirconiumAlloyFermium262 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_fermium_262"))
                .nuclear(false, false)
                .color(10318778)
                .components(Fermium262, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyFermium262);
        Fermium262Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_262_hexafluoride"))
                .gas()
                .color(10318778)
                .components(Fermium262, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Fermium262HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("fermium_262_hexafluoride_steam_cracked"))
                .gas()
                .color(10318798)
                .radioactiveHazard(1)
                .register();

// fermium_263
        Fermium263 = new CTNHMaterialBuilder(GTCEu.id("fermium_263"))
                .nuclear(false, false)
                .color(9463725)
                .element(Fm263)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideFermium263 = new CTNHMaterialBuilder(GTCEu.id("carbide_fermium_263"))
                .nuclear(false, false)
                .color(9463745)
                .components(Fermium263, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideFermium263);
        OxideFermium263 = new CTNHMaterialBuilder(GTCEu.id("oxide_fermium_263"))
                .nuclear(false, false)
                .color(9463725)
                .components(Fermium263, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideFermium263);
        NitrideFermium263 = new CTNHMaterialBuilder(GTCEu.id("nitride_fermium_263"))
                .nuclear(false, false)
                .color(9463705)
                .components(Fermium263, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideFermium263);
        ZirconiumAlloyFermium263 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_fermium_263"))
                .nuclear(false, false)
                .color(9463725)
                .components(Fermium263, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyFermium263);
        Fermium263Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("fermium_263_hexafluoride"))
                .gas()
                .color(9463725)
                .components(Fermium263, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Fermium263HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("fermium_263_hexafluoride_steam_cracked"))
                .gas()
                .color(9463745)
                .radioactiveHazard(1)
                .register();

// mendelevium_259
        Mendelevium259 = new CTNHMaterialBuilder(GTCEu.id("mendelevium_259"))
                .nuclear(false, false)
                .color(1590719)
                .element(Md259)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideMendelevium259 = new CTNHMaterialBuilder(GTCEu.id("carbide_mendelevium_259"))
                .nuclear(false, false)
                .color(1590739)
                .components(Mendelevium259, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideMendelevium259);
        OxideMendelevium259 = new CTNHMaterialBuilder(GTCEu.id("oxide_mendelevium_259"))
                .nuclear(false, false)
                .color(1590719)
                .components(Mendelevium259, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideMendelevium259);
        NitrideMendelevium259 = new CTNHMaterialBuilder(GTCEu.id("nitride_mendelevium_259"))
                .nuclear(false, false)
                .color(1590699)
                .components(Mendelevium259, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideMendelevium259);
        ZirconiumAlloyMendelevium259 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_mendelevium_259"))
                .nuclear(false, false)
                .color(1590719)
                .components(Mendelevium259, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyMendelevium259);
        Mendelevium259Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("mendelevium_259_hexafluoride"))
                .gas()
                .color(1590719)
                .components(Mendelevium259, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Mendelevium259HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("mendelevium_259_hexafluoride_steam_cracked"))
                .gas()
                .color(1590739)
                .radioactiveHazard(1)
                .register();

// mendelevium_261
        Mendelevium261 = new CTNHMaterialBuilder(GTCEu.id("mendelevium_261"))
                .nuclear(false, true)
                .color(1392304)
                .element(Md261)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setHeat(23);
        CarbideMendelevium261 = new CTNHMaterialBuilder(GTCEu.id("carbide_mendelevium_261"))
                .nuclear(false, false)
                .color(1392324)
                .components(Mendelevium261, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideMendelevium261);
        OxideMendelevium261 = new CTNHMaterialBuilder(GTCEu.id("oxide_mendelevium_261"))
                .nuclear(false, false)
                .color(1392304)
                .components(Mendelevium261, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideMendelevium261);
        NitrideMendelevium261 = new CTNHMaterialBuilder(GTCEu.id("nitride_mendelevium_261"))
                .nuclear(false, false)
                .color(1392284)
                .components(Mendelevium261, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideMendelevium261);
        ZirconiumAlloyMendelevium261 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_mendelevium_261"))
                .nuclear(false, false)
                .color(1392304)
                .components(Mendelevium261, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyMendelevium261);
        Mendelevium261Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("mendelevium_261_hexafluoride"))
                .gas()
                .color(1392304)
                .components(Mendelevium261, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Mendelevium261HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("mendelevium_261_hexafluoride_steam_cracked"))
                .gas()
                .color(1392324)
                .radioactiveHazard(1)
                .register();

// mendelevium_263
        Mendelevium263 = new CTNHMaterialBuilder(GTCEu.id("mendelevium_263"))
                .nuclear(false, false)
                .color(1194662)
                .element(Md263)
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        CarbideMendelevium263 = new CTNHMaterialBuilder(GTCEu.id("carbide_mendelevium_263"))
                .nuclear(false, false)
                .color(1194682)
                .components(Mendelevium263, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideMendelevium263);
        OxideMendelevium263 = new CTNHMaterialBuilder(GTCEu.id("oxide_mendelevium_263"))
                .nuclear(false, false)
                .color(1194662)
                .components(Mendelevium263, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideMendelevium263);
        NitrideMendelevium263 = new CTNHMaterialBuilder(GTCEu.id("nitride_mendelevium_263"))
                .nuclear(false, false)
                .color(1194642)
                .components(Mendelevium263, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideMendelevium263);
        ZirconiumAlloyMendelevium263 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_mendelevium_263"))
                .nuclear(false, false)
                .color(1194662)
                .components(Mendelevium263, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyMendelevium263);
        Mendelevium263Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("mendelevium_263_hexafluoride"))
                .gas()
                .color(1194662)
                .components(Mendelevium263, 1, Fluorine, 6)
                .radioactiveHazard(1)
                .register();
        Mendelevium263HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("mendelevium_263_hexafluoride_steam_cracked"))
                .gas()
                .color(1194682)
                .radioactiveHazard(1)
                .register();

        //        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Thorium);
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Thorium);
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Protactinium);
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Uranium);
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Uranium);
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Uranium);
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Uranium);
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Uranium);
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Neptunium);
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Neptunium);
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Neptunium);
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Plutonium);
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Plutonium);
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Plutonium);
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Plutonium);
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Plutonium);
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Americium);
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Americium);
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Americium);
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Curium);
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Curium);
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Curium);
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Curium);
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Curium);
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Berkelium);
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Berkelium);
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Berkelium);
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Californium);
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Californium);
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Californium);
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Californium);
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Californium);
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Einsteinium);
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Einsteinium);
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Einsteinium);
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Fermium);
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Fermium);
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Fermium);
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Fermium);
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Fermium);
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Mendelevium);
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Mendelevium);
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Mendelevium);

        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Uranium235, 9000);
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Uranium239, 100);
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Plutonium239, 8900);
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Neptunium239, 1000);
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Plutonium241, 9000);
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Plutonium245, 100);
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Americium245, 1000);
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Curium245, 8900);
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Curium247, 9000);
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Curium251, 100);
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Berkelium251, 1000);
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Californium251, 8900);
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Californium253, 9000);
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Californium257, 100);
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Einsteinium257, 1000);
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Fermium257, 8900);
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Fermium259, 9000);
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Fermium263, 1000);
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Mendelevium263, 9000);
    }
}
