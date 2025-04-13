package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.gregtechceu.gtceu.api.data.worldgen.BiomeWeightModifier;
import com.gregtechceu.gtceu.api.data.worldgen.modifier.BiomePlacement;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.worldgen.modifier.RubberTreeChancePlacement;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.worldgen.CTNHConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class CTNHPlacements {
    public static final ResourceKey<PlacedFeature> ASTRAL_TREE = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_tree"));
    public static final ResourceKey<PlacedFeature> ASTRAL_FLOWER = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_flower"));
    public static final ResourceKey<PlacedFeature> ASTRAL_GRASS = ResourceKey.create(Registries.PLACED_FEATURE, CTNHCore.id("astral_grass"));
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
    }
}
