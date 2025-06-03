package io.github.cpearl0.ctnhcore.registry;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.data.worldgen.bedrockfluid.BedrockFluidDefinition;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import earth.terrarium.adastra.api.planets.Planet;
import io.github.cpearl0.ctnhcore.CTNHCore;

import java.util.Set;

import static com.gregtechceu.gtceu.common.data.GTBedrockFluids.create;
public class CTNHFluidVeins {
    public static void init() {

    }
    public static BedrockFluidDefinition FLUORINE_VEIN_AETHER = create(CTNHCore.id("fluorine_vein"), vein -> {
        vein.dimensions(Set.of(AetherDimensions.AETHER_LEVEL));
        vein.fluid(() -> GTMaterials.Fluorine.getFluid());
        vein.weight(150);
        vein.minimumYield(120);
        vein.maximumYield(600);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition OXYGEN_VEIN_AETHER = create(CTNHCore.id("oxygen_vein"), vein -> {
        vein.dimensions(Set.of(AetherDimensions.AETHER_LEVEL));
        vein.fluid(() -> GTMaterials.Oxygen.getFluid());
        vein.weight(200);
        vein.minimumYield(120);
        vein.maximumYield(720);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition NITROGEN_VEIN_AETHER = create(CTNHCore.id("nitrogen_vein"), vein -> {
        vein.dimensions(Set.of(AetherDimensions.AETHER_LEVEL));
        vein.fluid(() -> GTMaterials.Nitrogen.getFluid());
        vein.weight(800);
        vein.minimumYield(120);
        vein.maximumYield(900);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition HYDROGEN_VEIN_AETHER = create(CTNHCore.id("hydrogen_vein"), vein -> {
        vein.dimensions(Set.of(AetherDimensions.AETHER_LEVEL));
        vein.fluid(() -> GTMaterials.Hydrogen.getFluid());
        vein.weight(100);
        vein.minimumYield(120);
        vein.maximumYield(600);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition MYSTERY_VEIN_MARS = create(CTNHCore.id("mars_mystery_fluid_vein"), vein ->{
        vein.dimensions(Set.of(Planet.MARS));
        vein.fluid(() -> CTNHMaterials.MysteryFluid.getFluid());
        vein.weight(100);
        vein.minimumYield(120);
        vein.maximumYield(600);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition WATER_VEIN_MARS = create(CTNHCore.id("mars_mystery_fluid_vein"), vein ->{
        vein.dimensions(Set.of(Planet.MARS));
        vein.fluid(() -> GTMaterials.DistilledWater.getFluid());
        vein.weight(800);
        vein.minimumYield(120);
        vein.maximumYield(600);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition HYDROCHLORIC_ACID_VEIN_MARS = create(CTNHCore.id("mars_hydrochloric_acid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MARS));
        vein.fluid(() -> GTMaterials.HydrochloricAcid.getFluid());
        vein.weight(200);
        vein.minimumYield(100);
        vein.maximumYield(500);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition IRON_LIQUID_VEIN_MERCURY = create(CTNHCore.id("mercury_iron_liquid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MERCURY));
        vein.fluid(() -> GTMaterials.Iron.getFluid());
        vein.weight(300);
        vein.minimumYield(200);
        vein.maximumYield(800);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(100);
    });
    public static BedrockFluidDefinition NICKEL_LIQUID_VEIN_MERCURY = create(CTNHCore.id("mercury_nickel_liquid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MERCURY));
        vein.fluid(() -> GTMaterials.Nickel.getFluid());
        vein.weight(200);
        vein.minimumYield(200);
        vein.maximumYield(800);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(100);
    });
    public static BedrockFluidDefinition SULFUR_LIQUID_VEIN_MERCURY = create(CTNHCore.id("mercury_sulfur_liquid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MERCURY));
        vein.fluid(() -> GTMaterials.Sulfur.getFluid());
        vein.weight(300);
        vein.minimumYield(200);
        vein.maximumYield(800);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(100);
    });
    public static BedrockFluidDefinition HELIUM3_VEIN_MOON = create(CTNHCore.id("moon_helium3_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MOON));
        vein.fluid(() -> GTMaterials.Helium3.getFluid());
        vein.weight(100);
        vein.minimumYield(100);
        vein.maximumYield(500);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(20);
    });
    public static BedrockFluidDefinition SALTWATER_VEIN_MOON = create(CTNHCore.id("moon_saltwater_vein"), vein -> {
        vein.dimensions(Set.of(Planet.MOON));
        vein.fluid(() -> GTMaterials.SaltWater.getFluid());
        vein.weight(800);
        vein.minimumYield(200);
        vein.maximumYield(800);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(100);
    });
    public static BedrockFluidDefinition SULFURIC_ACID_VEIN_VENUS = create(CTNHCore.id("venus_sulfuric_acid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.VENUS));
        vein.fluid(() -> GTMaterials.SulfuricAcid.getFluid());
        vein.weight(300);
        vein.minimumYield(150);
        vein.maximumYield(500);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition LEAD_LIQUID_VEIN_VENUS = create(CTNHCore.id("venus_lead_liquid_vein"), vein -> {
        vein.dimensions(Set.of(Planet.VENUS));
        vein.fluid(() -> GTMaterials.Lead.getFluid());
        vein.weight(100);
        vein.minimumYield(150);
        vein.maximumYield(500);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition CRYOTHEUM_VEIN_GLACIO = create(CTNHCore.id("glacio_cryotheum_vein"), vein -> {
        vein.dimensions(Set.of(Planet.GLACIO));
        vein.fluid(() -> CTNHMaterials.Cryotheum.getFluid());
        vein.weight(80);
        vein.minimumYield(150);
        vein.maximumYield(500);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(50);
    });
    public static BedrockFluidDefinition DISTILLED_WATER_VEIN_GLACIO = create(CTNHCore.id("glacio_distilled_water_vein"), vein -> {
        vein.dimensions(Set.of(Planet.GLACIO));
        vein.fluid(() -> GTMaterials.DistilledWater.getFluid());
        vein.weight(800);
        vein.minimumYield(200);
        vein.maximumYield(800);
        vein.depletionAmount(2);
        vein.depletionChance(1);
        vein.depletedYield(100);
    });
}
