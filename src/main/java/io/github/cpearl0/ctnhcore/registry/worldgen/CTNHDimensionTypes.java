package io.github.cpearl0.ctnhcore.registry.worldgen;

import earth.terrarium.adastra.api.planets.Planet;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class CTNHDimensionTypes {
    public static final ResourceKey<DimensionType> ASTRAL_PLANET = ResourceKey.create(Registries.DIMENSION_TYPE, CTNHCore.id("astral_planet"));
    public static final ResourceKey<DimensionType> ASTRAL_ORBIT = ResourceKey.create(Registries.DIMENSION_TYPE, CTNHCore.id("astral_orbit"));
    public static void bootstrap(BootstapContext<DimensionType> ctx) {
        ctx.register(
                ASTRAL_PLANET,
                create(
                        OptionalLong.empty(),
                        true,
                        false,
                        false,
                        true,
                        1.0,
                        true,
                        false,
                        -64,
                        384,
                        384,
                        BlockTags.INFINIBURN_OVERWORLD,
                        CTNHCore.id("astral_planet"),
                        0.0f,
                        createMonsterSettings(
                                false,
                                false,
                                UniformInt.of(0, 7),
                                0)));
        ctx.register(
                ASTRAL_ORBIT,
                create(
                        OptionalLong.empty(),
                        true,
                        false,
                        false,
                        true,
                        1.0,
                        true,
                        false,
                        -64,
                        384,
                        384,
                        BlockTags.INFINIBURN_OVERWORLD,
                        CTNHCore.id("astral_orbit"),
                        0.0f,
                        createMonsterSettings(
                                false,
                                false,
                                UniformInt.of(0, 7),
                                0)));
    }
    public static DimensionType create(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, ResourceLocation effectsLocation, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkyLight, hasCeiling, ultraWarm, natural, coordinateScale, bedWorks, respawnAnchorWorks, minY, height, logicalHeight, infiniburn, effectsLocation, ambientLight, monsterSettings);
    }
    public static DimensionType.MonsterSettings createMonsterSettings(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
