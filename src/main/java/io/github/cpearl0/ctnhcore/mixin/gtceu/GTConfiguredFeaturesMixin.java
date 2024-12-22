package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTConfiguredFeatures;
import com.gregtechceu.gtceu.common.data.GTFeatures;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.worldgen.feature.configurations.FluidSproutConfiguration;
import com.gregtechceu.gtceu.common.worldgen.feature.configurations.StoneBlobConfiguration;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static com.gregtechceu.gtceu.common.data.GTConfiguredFeatures.*;

@Mixin(GTConfiguredFeatures.class)
public class GTConfiguredFeaturesMixin {
    /**
     * @author mo_guang
     * @reason change RAW_OIL
     */
    @Overwrite(remap = false)
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        FeatureUtils.register(ctx, RUBBER, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider
                        .simple(GTBlocks.RUBBER_LOG.get().changeNatural(GTBlocks.RUBBER_LOG.getDefaultState(), true)),
                new ForkingTrunkPlacer(5, 1, 3),
                BlockStateProvider.simple(GTBlocks.RUBBER_LEAVES.get()),
                new MegaJungleFoliagePlacer(ConstantInt.of(1), UniformInt.of(0, 1), 1),
                new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build());

        FeatureUtils.register(ctx, RED_GRANITE_BLOB, GTFeatures.STONE_BLOB.get(),
                new StoneBlobConfiguration(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                        GTBlocks.RED_GRANITE.getDefaultState()), UniformInt.of(20, 30)));
        FeatureUtils.register(ctx, MARBLE_BLOB, GTFeatures.STONE_BLOB.get(),
                new StoneBlobConfiguration(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                        GTBlocks.MARBLE.getDefaultState()), UniformInt.of(20, 30)));

        FeatureUtils.register(ctx, RAW_OIL_SPROUT, GTFeatures.FLUID_SPROUT.get(),
                new FluidSproutConfiguration(CTNHMaterials.ImpureOil.getFluid(), UniformInt.of(9, 13), UniformInt.of(6, 9),
                        0.4f));
    }
}
