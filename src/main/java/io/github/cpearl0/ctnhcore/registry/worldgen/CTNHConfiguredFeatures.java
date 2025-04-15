package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

public class CTNHConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> ASTRAL_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, CTNHCore.id("astral_tree"));
    public static final ResourceKey<ConfiguredFeature<?,?>> ASTRAL_FLOWER = ResourceKey.create(Registries.CONFIGURED_FEATURE, CTNHCore.id("astral_flower"));
    public static final ResourceKey<ConfiguredFeature<?,?>> ASTRAL_GRASS = ResourceKey.create(Registries.CONFIGURED_FEATURE, CTNHCore.id("astral_grass"));
    public static final ResourceKey<ConfiguredFeature<?,?>> ASTRAL_LAKE = ResourceKey.create(Registries.CONFIGURED_FEATURE, CTNHCore.id("astral_lake"));


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        FeatureUtils.register(ctx, ASTRAL_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider
                        .simple(CTNHBlocks.ASTRAL_LOG.get()),
                new ForkingTrunkPlacer(8, 0, 4),
                BlockStateProvider.simple(Blocks.AIR),
                new MegaJungleFoliagePlacer(ConstantInt.of(1), UniformInt.of(0, 1), 1),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                .dirt(BlockStateProvider.simple(CTNHBlocks.ASTRAL_DIRT.get()))
                .build());
        SimpleWeightedRandomList.Builder<BlockState> flower = SimpleWeightedRandomList.builder();
        flower.add(CTNHBlocks.BLUE_FLOWER.getDefaultState(), 2).add(CTNHBlocks.PINK_FLOWER.getDefaultState(), 1);
        FeatureUtils.register(ctx, ASTRAL_FLOWER, Feature.FLOWER, CTNHConfiguredFeatures.grassPatch(new WeightedStateProvider(flower), 64));
        SimpleWeightedRandomList.Builder<BlockState> grass = SimpleWeightedRandomList.builder();
        grass.add(CTNHBlocks.ASTRAL_GRASS.getDefaultState(), 6).add(CTNHBlocks.ASTRAL_TALL_GRASS.getDefaultState(), 1);
        FeatureUtils.register(ctx, ASTRAL_GRASS, Feature.RANDOM_PATCH, CTNHConfiguredFeatures.grassPatch(new WeightedStateProvider(grass), 64));
        FeatureUtils.register(ctx, ASTRAL_LAKE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CTNHMaterials.starlight.getFluid().defaultFluidState().createLegacyBlock()), BlockStateProvider.simple(CTNHBlocks.ASTRAL_COBBLESTONE.getDefaultState())));
    }
    private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int n) {
        return FeatureUtils.simpleRandomPatchConfiguration(n, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }
}
