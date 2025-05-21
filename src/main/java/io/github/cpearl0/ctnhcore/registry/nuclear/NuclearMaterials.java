package io.github.cpearl0.ctnhcore.registry.nuclear;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialBuilder;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialIconSet;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import io.github.cpearl0.ctnhcore.api.data.material.NuclearProperty;
import io.github.cpearl0.ctnhcore.registry.CTNHElements;

import static com.gregtechceu.gtceu.common.data.GTMaterials.Carbon;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearElements.Th233;

public class NuclearMaterials {
    public static void init() {
        NuclearMaterialsInfo.register();
    }
    public static Material Uranium;
    public static Material Plutonium;
    public static Material ThoriumHexafluoride;
    public static Material ProtactiniumHexafluoride;
    public static Material PlutoniumHexafluoride;
    public static Material NeptuniumHexafluoride;
    public static Material AmericiumHexafluoride;
    public static Material CuriumHexafluoride;
    public static Material BerkeliumHexafluoride;
    public static Material CaliforniumHexafluoride;
    public static Material EinsteiniumHexafluoride;
    public static Material FermiumHexafluoride;
    public static Material MendeleviumHexafluoride;
    public static Material Thorium233;
    public static Material CarbideThorium233;
    public static Material OxideThorium233;
    public static Material NitrideThorium233;
    public static Material ZirconiumAlloyThorium233;
    public static Material Thorium233Hexafluoride;
    public static Material Thorium233HexafluorideSteamCracked;

    // 镤系
    public static Material Protactinium233;
    public static Material CarbideProtactinium233;
    public static Material OxideProtactinium233;
    public static Material NitrideProtactinium233;
    public static Material ZirconiumAlloyProtactinium233;
    public static Material Protactinium233Hexafluoride;
    public static Material Protactinium233HexafluorideSteamCracked;

    // 铀系
    public static Material Uranium233;
    public static Material CarbideUranium233;
    public static Material OxideUranium233;
    public static Material NitrideUranium233;
    public static Material ZirconiumAlloyUranium233;
    public static Material Uranium233Hexafluoride;
    public static Material Uranium233HexafluorideSteamCracked;

    public static Material Uranium234;
    public static Material CarbideUranium234;
    public static Material OxideUranium234;
    public static Material NitrideUranium234;
    public static Material ZirconiumAlloyUranium234;
    public static Material Uranium234Hexafluoride;
    public static Material Uranium234HexafluorideSteamCracked;

    public static Material Uranium239;
    public static Material CarbideUranium239;
    public static Material OxideUranium239;
    public static Material NitrideUranium239;
    public static Material ZirconiumAlloyUranium239;
    public static Material Uranium239Hexafluoride;
    public static Material Uranium239HexafluorideSteamCracked;

    // 镎系
    public static Material Neptunium235;
    public static Material CarbideNeptunium235;
    public static Material OxideNeptunium235;
    public static Material NitrideNeptunium235;
    public static Material ZirconiumAlloyNeptunium235;
    public static Material Neptunium235Hexafluoride;
    public static Material Neptunium235HexafluorideSteamCracked;

    public static Material Neptunium237;
    public static Material CarbideNeptunium237;
    public static Material OxideNeptunium237;
    public static Material NitrideNeptunium237;
    public static Material ZirconiumAlloyNeptunium237;
    public static Material Neptunium237Hexafluoride;
    public static Material Neptunium237HexafluorideSteamCracked;

    public static Material Neptunium239;
    public static Material CarbideNeptunium239;
    public static Material OxideNeptunium239;
    public static Material NitrideNeptunium239;
    public static Material ZirconiumAlloyNeptunium239;
    public static Material Neptunium239Hexafluoride;
    public static Material Neptunium239HexafluorideSteamCracked;

    // 钚系
    public static Material Plutonium240;
    public static Material CarbidePlutonium240;
    public static Material OxidePlutonium240;
    public static Material NitridePlutonium240;
    public static Material ZirconiumAlloyPlutonium240;
    public static Material Plutonium240Hexafluoride;
    public static Material Plutonium240HexafluorideSteamCracked;

    public static Material Plutonium244;
    public static Material CarbidePlutonium244;
    public static Material OxidePlutonium244;
    public static Material NitridePlutonium244;
    public static Material ZirconiumAlloyPlutonium244;
    public static Material Plutonium244Hexafluoride;
    public static Material Plutonium244HexafluorideSteamCracked;

    public static Material Plutonium245;
    public static Material CarbidePlutonium245;
    public static Material OxidePlutonium245;
    public static Material NitridePlutonium245;
    public static Material ZirconiumAlloyPlutonium245;
    public static Material Plutonium245Hexafluoride;
    public static Material Plutonium245HexafluorideSteamCracked;

    // 镅系
    public static Material Americium241;
    public static Material CarbideAmericium241;
    public static Material OxideAmericium241;
    public static Material NitrideAmericium241;
    public static Material ZirconiumAlloyAmericium241;
    public static Material Americium241Hexafluoride;
    public static Material Americium241HexafluorideSteamCracked;

    public static Material Americium243;
    public static Material CarbideAmericium243;
    public static Material OxideAmericium243;
    public static Material NitrideAmericium243;
    public static Material ZirconiumAlloyAmericium243;
    public static Material Americium243Hexafluoride;
    public static Material Americium243HexafluorideSteamCracked;

    public static Material Americium245;
    public static Material CarbideAmericium245;
    public static Material OxideAmericium245;
    public static Material NitrideAmericium245;
    public static Material ZirconiumAlloyAmericium245;
    public static Material Americium245Hexafluoride;
    public static Material Americium245HexafluorideSteamCracked;

    // 锔系
    public static Material Curium245;
    public static Material CarbideCurium245;
    public static Material OxideCurium245;
    public static Material NitrideCurium245;
    public static Material ZirconiumAlloyCurium245;
    public static Material Curium245Hexafluoride;
    public static Material Curium245HexafluorideSteamCracked;

    public static Material Curium246;
    public static Material CarbideCurium246;
    public static Material OxideCurium246;
    public static Material NitrideCurium246;
    public static Material ZirconiumAlloyCurium246;
    public static Material Curium246Hexafluoride;
    public static Material Curium246HexafluorideSteamCracked;

    public static Material Curium247;
    public static Material CarbideCurium247;
    public static Material OxideCurium247;
    public static Material NitrideCurium247;
    public static Material ZirconiumAlloyCurium247;
    public static Material Curium247Hexafluoride;
    public static Material Curium247HexafluorideSteamCracked;

    public static Material Curium250;
    public static Material CarbideCurium250;
    public static Material OxideCurium250;
    public static Material NitrideCurium250;
    public static Material ZirconiumAlloyCurium250;
    public static Material Curium250Hexafluoride;
    public static Material Curium250HexafluorideSteamCracked;

    public static Material Curium251;
    public static Material CarbideCurium251;
    public static Material OxideCurium251;
    public static Material NitrideCurium251;
    public static Material ZirconiumAlloyCurium251;
    public static Material Curium251Hexafluoride;
    public static Material Curium251HexafluorideSteamCracked;
    // 锫系
    public static Material Berkelium247;
    public static Material CarbideBerkelium247;
    public static Material OxideBerkelium247;
    public static Material NitrideBerkelium247;
    public static Material ZirconiumAlloyBerkelium247;
    public static Material Berkelium247Hexafluoride;
    public static Material Berkelium247HexafluorideSteamCracked;

    public static Material Berkelium249;
    public static Material CarbideBerkelium249;
    public static Material OxideBerkelium249;
    public static Material NitrideBerkelium249;
    public static Material ZirconiumAlloyBerkelium249;
    public static Material Berkelium249Hexafluoride;
    public static Material Berkelium249HexafluorideSteamCracked;

    public static Material Berkelium251;
    public static Material CarbideBerkelium251;
    public static Material OxideBerkelium251;
    public static Material NitrideBerkelium251;
    public static Material ZirconiumAlloyBerkelium251;
    public static Material Berkelium251Hexafluoride;
    public static Material Berkelium251HexafluorideSteamCracked;

    // 锎系
    public static Material Californium251;
    public static Material CarbideCalifornium251;
    public static Material OxideCalifornium251;
    public static Material NitrideCalifornium251;
    public static Material ZirconiumAlloyCalifornium251;
    public static Material Californium251Hexafluoride;
    public static Material Californium251HexafluorideSteamCracked;

    public static Material Californium252;
    public static Material CarbideCalifornium252;
    public static Material OxideCalifornium252;
    public static Material NitrideCalifornium252;
    public static Material ZirconiumAlloyCalifornium252;
    public static Material Californium252Hexafluoride;
    public static Material Californium252HexafluorideSteamCracked;

    public static Material Californium253;
    public static Material CarbideCalifornium253;
    public static Material OxideCalifornium253;
    public static Material NitrideCalifornium253;
    public static Material ZirconiumAlloyCalifornium253;
    public static Material Californium253Hexafluoride;
    public static Material Californium253HexafluorideSteamCracked;

    public static Material Californium256;
    public static Material CarbideCalifornium256;
    public static Material OxideCalifornium256;
    public static Material NitrideCalifornium256;
    public static Material ZirconiumAlloyCalifornium256;
    public static Material Californium256Hexafluoride;
    public static Material Californium256HexafluorideSteamCracked;

    public static Material Californium257;
    public static Material CarbideCalifornium257;
    public static Material OxideCalifornium257;
    public static Material NitrideCalifornium257;
    public static Material ZirconiumAlloyCalifornium257;
    public static Material Californium257Hexafluoride;
    public static Material Californium257HexafluorideSteamCracked;

    // 锿系
    public static Material Einsteinium253;
    public static Material CarbideEinsteinium253;
    public static Material OxideEinsteinium253;
    public static Material NitrideEinsteinium253;
    public static Material ZirconiumAlloyEinsteinium253;
    public static Material Einsteinium253Hexafluoride;
    public static Material Einsteinium253HexafluorideSteamCracked;

    public static Material Einsteinium255;
    public static Material CarbideEinsteinium255;
    public static Material OxideEinsteinium255;
    public static Material NitrideEinsteinium255;
    public static Material ZirconiumAlloyEinsteinium255;
    public static Material Einsteinium255Hexafluoride;
    public static Material Einsteinium255HexafluorideSteamCracked;

    public static Material Einsteinium257;
    public static Material CarbideEinsteinium257;
    public static Material OxideEinsteinium257;
    public static Material NitrideEinsteinium257;
    public static Material ZirconiumAlloyEinsteinium257;
    public static Material Einsteinium257Hexafluoride;
    public static Material Einsteinium257HexafluorideSteamCracked;

    // 镄系
    public static Material Fermium257;
    public static Material CarbideFermium257;
    public static Material OxideFermium257;
    public static Material NitrideFermium257;
    public static Material ZirconiumAlloyFermium257;
    public static Material Fermium257Hexafluoride;
    public static Material Fermium257HexafluorideSteamCracked;

    public static Material Fermium258;
    public static Material CarbideFermium258;
    public static Material OxideFermium258;
    public static Material NitrideFermium258;
    public static Material ZirconiumAlloyFermium258;
    public static Material Fermium258Hexafluoride;
    public static Material Fermium258HexafluorideSteamCracked;

    public static Material Fermium259;
    public static Material CarbideFermium259;
    public static Material OxideFermium259;
    public static Material NitrideFermium259;
    public static Material ZirconiumAlloyFermium259;
    public static Material Fermium259Hexafluoride;
    public static Material Fermium259HexafluorideSteamCracked;

    public static Material Fermium262;
    public static Material CarbideFermium262;
    public static Material OxideFermium262;
    public static Material NitrideFermium262;
    public static Material ZirconiumAlloyFermium262;
    public static Material Fermium262Hexafluoride;
    public static Material Fermium262HexafluorideSteamCracked;

    public static Material Fermium263;
    public static Material CarbideFermium263;
    public static Material OxideFermium263;
    public static Material NitrideFermium263;
    public static Material ZirconiumAlloyFermium263;
    public static Material Fermium263Hexafluoride;
    public static Material Fermium263HexafluorideSteamCracked;

    // 钔系
    public static Material Mendelevium259;
    public static Material CarbideMendelevium259;
    public static Material OxideMendelevium259;
    public static Material NitrideMendelevium259;
    public static Material ZirconiumAlloyMendelevium259;
    public static Material Mendelevium259Hexafluoride;
    public static Material Mendelevium259HexafluorideSteamCracked;

    public static Material Mendelevium261;
    public static Material CarbideMendelevium261;
    public static Material OxideMendelevium261;
    public static Material NitrideMendelevium261;
    public static Material ZirconiumAlloyMendelevium261;
    public static Material Mendelevium261Hexafluoride;
    public static Material Mendelevium261HexafluorideSteamCracked;

    public static Material Mendelevium263;
    public static Material CarbideMendelevium263;
    public static Material OxideMendelevium263;
    public static Material NitrideMendelevium263;
    public static Material ZirconiumAlloyMendelevium263;
    public static Material Mendelevium263Hexafluoride;
    public static Material Mendelevium263HexafluorideSteamCracked;

    //原gtm特殊

    public static Material CarbideThorium232;
    public static Material OxideThorium232;
    public static Material NitrideThorium232;
    public static Material ZirconiumAlloyThorium232;

    public static Material CarbideUranium235;
    public static Material OxideUranium235;
    public static Material NitrideUranium235;
    public static Material ZirconiumAlloyUranium235;

    public static Material CarbideUranium238;
    public static Material OxideUranium238;
    public static Material NitrideUranium238;
    public static Material ZirconiumAlloyUranium238;

    public static Material CarbidePlutonium239;
    public static Material OxidePlutonium239;
    public static Material NitridePlutonium239;
    public static Material ZirconiumAlloyPlutonium239;
    public static Material Plutonium239Hexafluoride;
    public static Material Plutonium239HexafluorideSteamCracked;

    public static Material CarbidePlutonium241;
    public static Material OxidePlutonium241;
    public static Material NitridePlutonium241;
    public static Material ZirconiumAlloyPlutonium241;
    public static Material Plutonium241Hexafluoride;
    public static Material Plutonium241HexafluorideSteamCracked;

}
