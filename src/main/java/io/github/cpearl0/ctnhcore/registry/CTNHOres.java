package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import dev.arbor.gtnn.data.GTNNOres;
import dev.arbor.gtnn.data.GTNNWorld;
import io.github.cpearl0.ctnhcore.CTNHCore;
import mythicbotany.register.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.block.Blocks;
import org.checkerframework.checker.units.qual.A;
import twilightforest.data.tags.BiomeTagGenerator;

import static com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers.*;
import static com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator.IndicatorPlacement.*;
import static com.gregtechceu.gtceu.common.data.GTOres.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHWorlds.*;

public class CTNHOres {
    public static void init() {
        MAGNETITE_VEIN_OW.layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Magnetite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.VanadiumMagnetite).size(1, 1))
                .layer(l -> l.weight(1).mat(CTNHMaterials.PreciousAlloy).size(1, 1))
            )
        );
        GTNNOres.INSTANCE.getGOLD_VEIN_TF().layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Magnetite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.VanadiumMagnetite).size(1, 1))
                .layer(l -> l.weight(1).mat(CTNHMaterials.PreciousAlloy).size(1, 1))
            )
        );
        GTRegistries.ORE_VEINS.remove(GTCEu.id("nether_quartz_vein"));
        GTRegistries.ORE_VEINS.remove(GTCEu.id("nickel_vein"));
        GTRegistries.ORE_VEINS.remove(GTCEu.id("galena_vein"));
        GTRegistries.ORE_VEINS.remove(GTCEu.id("sheldonite_vein"));
        MICA_VEIN.layer(WorldGenLayers.NETHERRACK)
                .dimensions(ResourceLocation.tryParse("minecraft:the_nether"))
                .biomes(BiomeTags.IS_NETHER)
                .heightRangeUniform(0, 25)
                .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(pattern -> pattern
                        .layer(l -> l.weight(3).mat(GTMaterials.Kyanite).size(2, 4))
                        .layer(l -> l.weight(2).mat(GTMaterials.Mica).size(1, 1))
                        .layer(l -> l.weight(2).mat(GTMaterials.Aluminium).size(1, 1))
                        .layer(l -> l.weight(1).mat(GTMaterials.Pollucite).size(1, 1))
                    )
                );


//        let ADASTRA = ["ad_astra:lunar_wastelands","ad_astra:glacio_ice_peaks","ad_astra:glacio_snowny_barrens",
//                "ad_astra:inferno_venus_barrens","ad_astra:martian_canyon_creek","ad_astra:martian_polar_caps",
//                "ad_astra:martian_wastelands","ad_astra:mercury_deltas","ad_astra:venus_wastelands","ad_extendra:cosmic_gaslands",
//                "ad_extendra:intriguing_wastelands"]
        // event.modify("gtceu:naquadah_vein", vein => {
        //     vein.layer("all_layer")
        //     ADASTRA.forEach(biome =>{
        //         vein.biomes(biome)
        //     })
        //     vein.dimensions(["ad_astra:mercury", "ad_extendra:jupiter"])
        // })
        // event.modify("gtceu:monazite_vein", vein => {
        //     vein.layer("all_layer")
        //     ADASTRA.forEach(biome =>{
        //         vein.biomes(biome)
        //     })
        //     vein.dimensions("ad_extendra:jupiter")
        // })
    }

    public static GTOreDefinition NETHER_QUARTZ_VEIN_OW = create(CTNHCore.id("nether_quartz_vein_ow"), vein -> vein
            .weight(80)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(STONE)
        .dimensions(ResourceLocation.tryParse("minecraft:overworld"))
        .heightRangeUniform(20, 60)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.NetherQuartz).size(2, 4))
                .layer(l -> l.weight(1).mat(GTMaterials.Quartzite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Opal).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.NetherQuartz)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition NICKEL_VEIN_OW = create(CTNHCore.id("nickel_vein"), vein -> vein
                    .weight(40)
            .clusterSize(40)
            .density(0.25F)
            .discardChanceOnAirExposure(0)
            .layer(WorldGenLayers.NETHERRACK)
            .dimensions(ResourceLocation.tryParse("minecraft:the_nether"))
            .heightRangeUniform(10, 60)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(pattern -> pattern
                    .layer(l -> l.weight(3).mat(GTMaterials.Garnierite).size(2, 4))
                    .layer(l -> l.weight(2).mat(GTMaterials.Nickel).size(1, 1))
                    .layer(l -> l.weight(2).mat(GTMaterials.Cobaltite).size(1, 1))
                    .layer(l -> l.weight(1).mat(GTMaterials.Pentlandite).size(1, 1))
                )
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Nickel)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
            ));
    public static GTOreDefinition ANCIENT_DEBRIS_VEIN = create(CTNHCore.id("ancient_debris_vein"), vein ->
            vein.weight(5).clusterSize(35)
            .density(0.4F)
            .discardChanceOnAirExposure(0)
            .layer(WorldGenLayers.NETHERRACK)
            .dimensions(ResourceLocation.tryParse("minecraft:the_nether"))
            .heightRangeUniform(0, 25)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(pattern -> pattern
                    .layer(l -> l.weight(3).mat(CTNHMaterials.PreciousAlloy).size(2, 4))
                    .layer(l -> l.weight(2).mat(GTMaterials.Sulfur).size(2, 2))
                    .layer(l -> l.weight(1).block(() -> Blocks.ANCIENT_DEBRIS).size(1, 1))
                    .layer(l -> l.weight(1).mat(GTMaterials.NetherQuartz).size(1, 1))
                )
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(CTNHMaterials.PreciousAlloy)
                    .placement(ABOVE)
                    .density(0.4F)
                    .radius(5)
            ));

    public static GTOreDefinition CHROMITE_VEIN = create(CTNHCore.id("chromite_vein"), vein ->
        vein.weight(60)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(ENDSTONE)
        .dimensions(ResourceLocation.tryParse("minecraft:the_end"))
        .heightRangeUniform(0, 40)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Chromite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.YellowLimonite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Magnetite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Lead).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Chromite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition CRYOLITE_VEIN = create(CTNHCore.id("cryolite_vein"), vein ->
        vein.weight(70)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(GTNNWorld.GTNNWorldGenLayers.TF)
        .dimensions(TWILIGHT_FOREST)
        .heightRangeUniform(-30, 0)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Cryolite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Silver).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Mica).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Lead).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Cryolite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition STEEL_LEAF_VEINN = create(CTNHCore.id("steel_leaf_vein"), vein ->
            vein.weight(20)
                    .clusterSize(40)
                    .density(0.35F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(CTNHMaterials.SteelLeaf).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Apatite).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Galena).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Pyrochlore).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.SteelLeaf)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition LICH_BONE_VEIN = create(CTNHCore.id("lich_bone_vein"), vein ->
            vein.weight(20)
                    .clusterSize(45)
                    .density(0.35F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_LICH_TOWER_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.TricalciumPhosphate).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Coal).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Graphite).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.SpiritAsh).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.SpiritAsh)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition TOXIC_SWAMP_AMBER_VEIN = create(CTNHCore.id("toxic_swamp_amber_vein"), vein ->
            vein.weight(50)
                    .clusterSize(35)
                    .density(0.45F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_LABYRINTH_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Cinnabar).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Galena).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Saltpeter).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.ToxicSwampAmber).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.ToxicSwampAmber)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition ILLUSION_IRON_VEIN = create(CTNHCore.id("illusion_iron_vein"), vein ->
            vein.weight(50)
                    .clusterSize(25)
                    .density(0.45F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Pyrite).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.VanadiumMagnetite).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Tantalite).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.IllusionIron).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.IllusionIron)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition ARCTIC_CRYSTAL_CORE_VEIN = create(CTNHCore.id("arctic_crystal_core_vein"), vein ->
            vein.weight(50)
                    .clusterSize(25)
                    .density(0.45F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Electrotine).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Kyanite).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Lapis).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.PolarIceCore).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.PolarIceCore)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition DRAGONFLAME_VEIN = create(CTNHCore.id("dragonflame_vein"), vein ->
            vein.weight(50)
                    .clusterSize(35)
                    .density(0.55F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Hematite).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Ruby).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Pyrochlore).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.Dragonflame).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.Dragonflame)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition ECLIPSE_SHADOW_VEIN = create(CTNHCore.id("eclipse_shadow_vein"), vein ->
            vein.weight(50)
                    .clusterSize(45)
                    .density(0.25F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_DARK_TOWER_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Stibnite).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Antimony).size(1, 1))
                                    .layer(l -> l.weight(1).mat(GTMaterials.Silver).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.EclipseShadow).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.EclipseShadow)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition LIGHTNING_VEIN_VEIN = create(CTNHCore.id("thunderstrike_vein"), vein ->
            vein.weight(50)
                    .clusterSize(65)
                    .density(0.65F)
                    .discardChanceOnAirExposure(0)
                    .layer(GTNNWorld.GTNNWorldGenLayers.TF)
                    .dimensions(TWILIGHT_FOREST)
                    .biomes(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES)
                    .heightRangeUniform(-33, 20)
                    .layeredVeinGenerator(generator -> generator
                            .buildLayerPattern(pattern -> pattern
                                    .layer(l -> l.weight(3).mat(GTMaterials.Gold).size(2, 4))
                                    .layer(l -> l.weight(2).mat(GTMaterials.Diamond).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.EclipseShadow).size(1, 1))
                                    .layer(l -> l.weight(1).mat(CTNHMaterials.LightningPattern).size(1, 1))
                            )
                    )
                    .surfaceIndicatorGenerator(indicator -> indicator
                            .surfaceRock(CTNHMaterials.LightningPattern)
                            .placement(ABOVE)
                            .density(0.4F)
                            .radius(5)
                    ));
    public static GTOreDefinition CRYOLITE_VEIN_AETHER = create(CTNHCore.id("cryolite_vein_aether"), vein ->
        vein.weight(40)
        .clusterSize(40)
        .density(0.45F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.AETHER)
        .dimensions(THE_AETHER)
        .heightRangeUniform(20, 80)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Cryolite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Silver).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Mica).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Lead).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Cryolite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition BAUXITE_VEIN_AETHER = create(CTNHCore.id("bauxite_vein_aether"), vein ->
        vein.weight(60)
        .clusterSize(40)
        .density(0.35F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.AETHER)
        .dimensions(THE_AETHER)
        .heightRangeUniform(20, 80)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Bauxite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Ilmenite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Aluminium).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Bauxite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition SCHEELITE_VEIN_AETHER = create(CTNHCore.id("scheelite_vein_aether"), vein ->
        vein.weight(50)
        .clusterSize(45)
        .density(0.45F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.AETHER)
        .dimensions(THE_AETHER)
        .heightRangeUniform(10, 50)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Scheelite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Tungstate).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Lithium).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Scheelite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZANITE_VEIN_AETHER = create(CTNHCore.id("zanite_vein_aether"), vein ->
        vein.weight(40)
        .clusterSize(45)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.AETHER)
        .dimensions(THE_AETHER)
        .heightRangeUniform(10, 60)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Zanite).size(2, 4))
                .layer(l -> l.weight(2).mat(CTNHMaterials.Ambrosium).size(1, 1))
                .layer(l -> l.weight(1).mat(CTNHMaterials.Skyjade).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Zanite)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition SHELDONITE_VEIN_MOON = create(CTNHCore.id("sheldonite_vein_moon"),vein ->
        vein.clusterSize(40)
        .density(0.3F)
        .weight(40)
        .layer(GTNNWorld.GTNNWorldGenLayers.AD)
        .heightRangeUniform(5, 50)
        .dimensions(MOON)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Bornite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Cooperite).size(1, 1))
                .layer(l -> l.weight(2).mat(GTMaterials.Platinum).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Palladium).size(1, 1))
                ))
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Platinum)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZIRCON_VEIN = create(CTNHCore.id("zircon_vein"), vein ->
        vein.weight(40)
        .clusterSize(40)
        .density(0.35F)
        .discardChanceOnAirExposure(0)
        .layer(ENDSTONE)
        .dimensions(ResourceLocation.tryParse("minecraft:the_end"))
        .heightRangeUniform(10, 50)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Zircon).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Cobalt).size(1, 1))
                .layer(l -> l.weight(2).mat(GTMaterials.Lead).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Uranium238).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Zircon)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZIRCON_VEIN_TW = create(CTNHCore.id("zircon_vein_tw"), vein ->
        vein.weight(40)
        .clusterSize(40)
        .density(0.35F)
        .discardChanceOnAirExposure(0)
        .layer(GTNNWorld.GTNNWorldGenLayers.TF)
        .dimensions(TWILIGHT_FOREST)
        .heightRangeUniform(-30, 0)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Zircon).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Cobalt).size(1, 1))
                .layer(l -> l.weight(2).mat(GTMaterials.Lead).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Uranium238).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Zircon)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZIRCON_VEIN_AETHER = create(CTNHCore.id("zircon_vein_aether"), vein ->
        vein.weight(80)
        .clusterSize(50)
        .density(0.35F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.AETHER)
        .dimensions(THE_AETHER)
        .heightRangeUniform(10, 40)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Zircon).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Cobalt).size(1, 1))
                .layer(l -> l.weight(2).mat(GTMaterials.Lead).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Uranium238).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Zircon)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZINC_VEIN = create(CTNHCore.id("zinc_vein"), vein ->
        vein.weight(60)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(STONE)
        .dimensions(ResourceLocation.tryParse("minecraft:overworld"))
        .heightRangeUniform(20, 50)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Zinc).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Copper).size(1, 1))
                .layer(l -> l.weight(2).mat(GTMaterials.YellowLimonite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Hematite).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Copper)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition PRECIOUS_ALLOY_VEIN = create(CTNHCore.id("precious_alloy_vein"), vein ->
        vein.weight(30)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(STONE)
                .biomes(BiomeTags.IS_OVERWORLD)
        .heightRangeUniform(-10, 30)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.PreciousAlloy).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.Silver).size(1, 3))
                .layer(l -> l.weight(2).mat(GTMaterials.Tin).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Copper).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.PreciousAlloy)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ARSENIC_VEIN = create(CTNHCore.id("arsenic_vein"), vein ->
        vein.weight(10)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(GTNNWorld.GTNNWorldGenLayers.TF)
        .dimensions(TWILIGHT_FOREST)
        .heightRangeUniform(-30, 0)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.Nickeline).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.ArsenicTrioxide).size(1, 3))
                .layer(l -> l.weight(2).mat(GTMaterials.Pentlandite).size(1, 1))
                .layer(l -> l.weight(1).mat(GTMaterials.Realgar).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.Nickeline)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition PHOSPHATE_VEIN = create(CTNHCore.id("phosphate_vein"), vein ->
        vein.weight(40)
        .clusterSize(30)
        .density(0.30F)
        .discardChanceOnAirExposure(0)
        .layer(GTNNWorld.GTNNWorldGenLayers.AD)
        .dimensions(MOON)
        .heightRangeUniform(-20, 50)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(GTMaterials.Apatite).size(2, 4))
                .layer(l -> l.weight(2).mat(GTMaterials.TricalciumPhosphate).size(1, 3))
                .layer(l -> l.weight(2).mat(CTNHMaterials.TrisodiumPhosphate).size(1, 2))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(CTNHMaterials.TrisodiumPhosphate)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        ));
    public static GTOreDefinition ZIRKELITE_VEIN = create(CTNHCore.id("zirkelite_vein"), vein ->
            vein.weight(60)
        .clusterSize(50)
        .density(0.25F)
            .discardChanceOnAirExposure(0)
        .layer(GTNNWorld.GTNNWorldGenLayers.AD)
        .dimensions(MARS, VENUS, MERCURY)
        .heightRangeUniform(30, 80)
        .layeredVeinGenerator(generator -> generator
            .buildLayerPattern(pattern -> pattern
            .layer(l -> l.weight(3).mat(CTNHMaterials.Zirkelite).size(2, 4))
            .layer(l -> l.weight(2).mat(GTMaterials.Thorium).size(1, 1))
            .layer(l -> l.weight(2).mat(CTNHMaterials.Zircon).size(1, 1))
            .layer(l -> l.weight(1).mat(GTMaterials.Ilmenite).size(1, 1))
        )
    )
    .surfaceIndicatorGenerator(indicator -> indicator
            .surfaceRock(CTNHMaterials.Zirkelite)
            .placement(ABOVE)
            .density(0.4F)
            .radius(5)
    ));
    public static GTOreDefinition COMBUSTIBLE_ICE_VEIN_AETHER = create(CTNHCore.id("combustible_ice_vein_aether"), vein ->
        vein.weight(80)
            .clusterSize(30)
            .density(0.55F)
            .discardChanceOnAirExposure(0)
            .layer(CTNHWorldgenLayers.AETHER)
            .dimensions(THE_AETHER)
            .heightRangeUniform(20, 80)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(pattern -> pattern
                    .layer(l -> l.weight(3).mat(CTNHMaterials.CombustibleIce).size(2, 4))
                    .layer(l -> l.weight(2).mat(GTMaterials.Coal).size(1, 1))
                    .layer(l -> l.weight(1).mat(GTMaterials.Opal).size(1, 1))
                    .layer(l -> l.weight(1).mat(CTNHMaterials.CombustibleIce).size(1, 1))
                )
            )
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(CTNHMaterials.CombustibleIce)
                    .placement(ABOVE)
                    .density(0.2F)
                    .radius(5)
            )
    );
    public static GTOreDefinition MANA_FUSED_VEIN = create(CTNHCore.id("mana_fused_vein"), vein ->
        vein.weight(80)
        .clusterSize(40)
        .density(0.25F)
        .discardChanceOnAirExposure(0)
        .layer(CTNHWorldgenLayers.ALFHEIM)
        .dimensions(ALFHEIM)
        .heightRangeUniform(20, 40)
        .layeredVeinGenerator(generator -> generator
                .buildLayerPattern(pattern -> pattern
                .layer(l -> l.weight(3).mat(CTNHMaterials.ManaFused).size(2, 4))
                .layer(l -> l.weight(2).block(() -> ModBlocks.goldOre).size(1, 1))
                .layer(l -> l.weight(1).block(() -> ModBlocks.dragonstoneOre).size(1, 1))
            )
        )
        .surfaceIndicatorGenerator(indicator -> indicator
                .surfaceRock(GTMaterials.Gold)
                .placement(ABOVE)
                .density(0.4F)
                .radius(5)
        )
    );
}
