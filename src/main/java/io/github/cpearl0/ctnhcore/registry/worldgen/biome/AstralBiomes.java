package io.github.cpearl0.ctnhcore.registry.worldgen.biome;

import io.github.cpearl0.ctnhcore.registry.sound.CTNHMusics;
import io.github.cpearl0.ctnhcore.registry.worldgen.CTNHPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AstralBiomes {
    public static Biome baseAstralSetting(BiomeGenerationSettings.Builder builder) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
                .temperature(0.7F)
                .downfall(0)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x50533)
                        .fogColor(0)
                        .skyColor(1447444)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(CTNHMusics.ASTRAL_BGM).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(builder.build()).build();
    }
    public static Biome baseOrbitSetting(BiomeGenerationSettings.Builder builder) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(0)
                        .skyColor(0)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(CTNHMusics.ASTRAL_BGM).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(builder.build()).build();
    }
    public static Biome plague_wasteland(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter,holderGetter2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTNHPlacements.ASTRAL_TREE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTNHPlacements.ASTRAL_GRASS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CTNHPlacements.ASTRAL_FLOWER);
        return baseAstralSetting(biomeBuilder);
    }
    public static Biome plague_desert(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter,holderGetter2);
        return baseAstralSetting(biomeBuilder);
    }
    public static Biome astral_orbit(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter,holderGetter2);
        return baseOrbitSetting(biomeBuilder);
    }
}
