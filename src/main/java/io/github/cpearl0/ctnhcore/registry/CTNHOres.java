package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import dev.arbor.gtnn.data.GTNNWorld;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.block.Blocks;
import org.checkerframework.checker.units.qual.A;

import static com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers.*;
import static com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator.IndicatorPlacement.*;
import static com.gregtechceu.gtceu.common.data.GTOres.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHWorlds.*;

public class CTNHOres {
    public static void init() {}

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
}
