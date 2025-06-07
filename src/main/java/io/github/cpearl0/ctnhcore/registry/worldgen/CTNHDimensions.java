package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.mojang.datafixers.util.Pair;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;

public class CTNHDimensions {
    public static final ResourceKey<LevelStem> ASTRAL_PLANET = ResourceKey.create(Registries.LEVEL_STEM, CTNHCore.id("astral_planet"));
    public static final Climate.ParameterPoint PLAGUE_WASTELAND_PARAMETER = createParameter(0.1F,0,0,0,0,0,0);
    public static final Climate.ParameterPoint PLAGUE_DESERT_PARAMETER = createParameter(0.2F,0,0,0,0,0,0);
    public static final ResourceKey<LevelStem> ASTRAL_ORBIT = ResourceKey.create(Registries.LEVEL_STEM, CTNHCore.id("astral_orbit"));
    public static void bootstrap(BootstapContext<LevelStem> ctx) {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = ctx.lookup(Registries.NOISE_SETTINGS);
        ctx.register(ASTRAL_ORBIT, new LevelStem(dimensionTypes.getOrThrow(CTNHDimensionTypes.ASTRAL_ORBIT),
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(
                                biomes.getOrThrow(CTNHBiomes.ASTRAL_ORBIT)),
                        noiseSettings.getOrThrow(CTNHNoiseGenerationSettings.ORBIT))));
        ctx.register(ASTRAL_PLANET, new LevelStem(dimensionTypes.getOrThrow(CTNHDimensionTypes.ASTRAL_PLANET),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(
                                List.of(Pair.of(PLAGUE_WASTELAND_PARAMETER, biomes.getOrThrow(CTNHBiomes.PLAGUE_WASTELAND)),
                                        Pair.of(PLAGUE_DESERT_PARAMETER, biomes.getOrThrow(CTNHBiomes.PLAGUE_DESERT)))
                        )),
                        noiseSettings.getOrThrow(CTNHNoiseGenerationSettings.ASTRAL_PLANET)
                )));
    }
    public static Climate.ParameterPoint createParameter(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, long offset) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
    }
}
