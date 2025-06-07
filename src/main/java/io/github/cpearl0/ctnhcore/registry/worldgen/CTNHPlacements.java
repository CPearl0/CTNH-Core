package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.gregtechceu.gtceu.api.data.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.data.worldgen.modifier.BiomePlacement;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.worldgen.modifier.RubberTreeChancePlacement;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.worldgen.CTNHConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class CTNHPlacements {
    public static final ResourceKey<PlacedFeature> ASTRAL_TREE = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_tree"));
    public static final ResourceKey<PlacedFeature> ASTRAL_FLOWER = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_flower"));
    public static final ResourceKey<PlacedFeature> ASTRAL_GRASS = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_grass"));
    public static final ResourceKey<PlacedFeature> ASTRAL_LAKE = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_lake"));
    public static final ResourceKey<PlacedFeature> ASTRAL_LAKE_UNDERGROUND = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_lake_underground"));
    public static final ResourceKey<PlacedFeature> VENUS_OCHRUM = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("venus_ochrum"));

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);

        PlacementUtils.register(ctx, ASTRAL_TREE, featureLookup.getOrThrow(CTNHConfiguredFeatures.ASTRAL_TREE),
                new BiomePlacement(List.of(
                        new BiomeWeightModifier(() -> HolderSet.direct(biomeLookup.getOrThrow(CTNHBiomes.PLAGUE_WASTELAND)), 50))),
                CountPlacement.of(12),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(CTNHBlocks.ASTRAL_SAPLING.get()));
        PlacementUtils.register(ctx, ASTRAL_FLOWER, featureLookup.getOrThrow(CTNHConfiguredFeatures.ASTRAL_FLOWER),
                new BiomePlacement(List.of(
                        new BiomeWeightModifier(() -> HolderSet.direct(biomeLookup.getOrThrow(CTNHBiomes.PLAGUE_WASTELAND)), 50))),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(CTNHBlocks.BLUE_FLOWER.get()));
        PlacementUtils.register(ctx, ASTRAL_GRASS, featureLookup.getOrThrow(CTNHConfiguredFeatures.ASTRAL_GRASS),
                new BiomePlacement(List.of(
                        new BiomeWeightModifier(() -> HolderSet.direct(biomeLookup.getOrThrow(CTNHBiomes.PLAGUE_WASTELAND)), 50))),
                CountPlacement.of(8),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(CTNHBlocks.ASTRAL_GRASS.get()));
        PlacementUtils.register(ctx, ASTRAL_LAKE_UNDERGROUND, featureLookup.getOrThrow(CTNHConfiguredFeatures.ASTRAL_LAKE),
                RarityFilter.onAverageOnceEvery(9),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5),
                BiomeFilter.biome());
        PlacementUtils.register(ctx, ASTRAL_LAKE, featureLookup.getOrThrow(CTNHConfiguredFeatures.ASTRAL_LAKE),
                RarityFilter.onAverageOnceEvery(50),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome());
        PlacementUtils.register(ctx, VENUS_OCHRUM, featureLookup.getOrThrow(CTNHConfiguredFeatures.VENUS_OCHRUM),
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(15), VerticalAnchor.absolute(70)),
                InSquarePlacement.spread(),
                CountPlacement.of(UniformInt.of(15, 40)),
                BiomeFilter.biome());
    }
}
