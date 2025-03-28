package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

public class CTNHConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> ASTRAL_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, CTNHCore.id("astral_tree"));
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        FeatureUtils.register(ctx, ASTRAL_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider
                        .simple(CTNHBlocks.ASTRAL_LOG.get()),
                new ForkingTrunkPlacer(8, 0, 4),
                BlockStateProvider.simple(Blocks.AIR),
                new MegaJungleFoliagePlacer(ConstantInt.of(1), UniformInt.of(0, 1), 1),
                new TwoLayersFeatureSize(1, 0, 2))
                .ignoreVines()
                //.dirt(BlockStateProvider.simple(CTNHBlocks.ASTRAL_DIRT.get()))
                //.dirt(BlockStateProvider.simple(CTNHBlocks.ASTRAL_GRASS.get()))
                .build());
    }
}
