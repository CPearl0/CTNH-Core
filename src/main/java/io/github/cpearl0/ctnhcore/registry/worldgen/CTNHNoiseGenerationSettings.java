package io.github.cpearl0.ctnhcore.registry.worldgen;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.world.level.levelgen.NoiseRouterData.*;

public class CTNHNoiseGenerationSettings {
    public static final ResourceKey<NoiseGeneratorSettings> ORBIT = ResourceKey.create(Registries.NOISE_SETTINGS, CTNHCore.id("orbit"));
    public static final ResourceKey<NoiseGeneratorSettings> ASTRAL_PLANET = ResourceKey.create(Registries.NOISE_SETTINGS, CTNHCore.id("astral_planet"));
    public static final ResourceKey<NoiseGeneratorSettings> ABYSS = ResourceKey.create(Registries.NOISE_SETTINGS, CTNHCore.id("abyss"));
    public static final SurfaceRules.RuleSource ASTRAL_GRASS_BLOCK = SurfaceRules.state(CTNHBlocks.ASTRAL_GRASS.getDefaultState());
    public static final SurfaceRules.RuleSource ASTRAL_DIRT = SurfaceRules.state(CTNHBlocks.ASTRAL_DIRT.getDefaultState());
    public static final SurfaceRules.RuleSource ASTRAL_STONE = SurfaceRules.state(CTNHBlocks.ASTRAL_STONE.getDefaultState());
    public static final SurfaceRules.RuleSource ASTRAL_SAND = SurfaceRules.state(CTNHBlocks.ASTRAL_SAND.getDefaultState());
    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> ctx){
        var holderGetter = ctx.lookup(Registries.DENSITY_FUNCTION);
        var holderGetter2 = ctx.lookup(Registries.NOISE);


        ctx.register(ORBIT, new NoiseGeneratorSettings(NoiseSettings.create(0, 256, 2, 1),
                Blocks.AIR.defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                new NoiseRouter(DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()),
                SurfaceRuleData.air(),
                List.of(),
                0,
                true,
                false,
                false,
                false));
        ctx.register(ASTRAL_PLANET, new NoiseGeneratorSettings(NoiseSettings.create(-64, 384, 1, 2),
                CTNHBlocks.ASTRAL_STONE.getDefaultState(),
                CTNHMaterials.starlight.getFluid().defaultFluidState().createLegacyBlock(),
                new NoiseRouter(DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_BARRIER), 0.5),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_LAVA)),
                        DensityFunctions.shiftedNoise2d(DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                0.25, holderGetter2.getOrThrow(Noises.TEMPERATURE)),
                        DensityFunctions.shiftedNoise2d(DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                0.25, holderGetter2.getOrThrow(Noises.VEGETATION)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CONTINENTS)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(EROSION)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.DEPTH)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(RIDGES)),
                        DensityFunctions.mul(DensityFunctions.constant(4),DensityFunctions.mul(new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.DEPTH)),DensityFunctions.cache2d(new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.FACTOR)))).quarterNegative()),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.FINAL_DENSITY)),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()),
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("ctnhcore:astral_bedrock", VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(5)), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
                        SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(CTNHBiomes.PLAGUE_WASTELAND), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0 ,false, CaveSurface.FLOOR), SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0,0), ASTRAL_GRASS_BLOCK), ASTRAL_DIRT))), ASTRAL_STONE)),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(CTNHBiomes.PLAGUE_DESERT), SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(58), 2),SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(4, false, CaveSurface.FLOOR), ASTRAL_SAND)))))),
                List.of(),
                64,
                false,
                false,
                false,
                false));
        ctx.register(ABYSS, new NoiseGeneratorSettings(NoiseSettings.create(-64, 384, 1, 2),
                CTNHBlocks.ASTRAL_STONE.getDefaultState(),
                CTNHMaterials.starlight.getFluid().defaultFluidState().createLegacyBlock(),
                new NoiseRouter(DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_BARRIER), 0.5),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143),
                        DensityFunctions.noise(holderGetter2.getOrThrow(Noises.AQUIFER_LAVA)),
                        DensityFunctions.shiftedNoise2d(DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                0.25, holderGetter2.getOrThrow(Noises.TEMPERATURE)),
                        DensityFunctions.shiftedNoise2d(DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB(holderGetter2.getOrThrow(Noises.SHIFT)))),
                                0.25, holderGetter2.getOrThrow(Noises.VEGETATION)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CONTINENTS)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(EROSION)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.DEPTH)),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(RIDGES)),
                        DensityFunctions.mul(DensityFunctions.constant(4),DensityFunctions.mul(new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.DEPTH)), new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.FACTOR)))),
                        new DensityFunctions.HolderHolder(holderGetter.getOrThrow(CTNHDensityFunctions.FINAL_DENSITY)),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()),
                SurfaceRuleData.overworld(),
                List.of(),
                64,
                false,
                false,
                false,
                false));
    }
}
