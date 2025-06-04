package io.github.cpearl0.ctnhcore.registry.worldgen;

import com.mojang.datafixers.util.Pair;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class CTNHOverworldRegion extends Region {
    public CTNHOverworldRegion(int weight) {
        super(CTNHCore.id("overworld_region"), RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        super.addBiomes(registry, mapper);
        this.addBiome(mapper,
                Climate.Parameter.span(0.95F, 1.0F),
                Climate.Parameter.span(0.8F, 0.9F),
                Climate.Parameter.span(0.9F, 1.0F),
                Climate.Parameter.span(0.1F,0.25F),
                ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING.parameter(),
                ParameterUtils.Depth.SURFACE.parameter(),
                0.99F,CTNHBiomes.PLAGUE_WASTELAND);
    }
}
