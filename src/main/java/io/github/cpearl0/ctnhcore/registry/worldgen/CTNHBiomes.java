package io.github.cpearl0.ctnhcore.registry.worldgen;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.worldgen.biome.AstralBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CTNHBiomes {
    public static final ResourceKey<Biome> PLAGUE_WASTELAND = ResourceKey.create(Registries.BIOME, CTNHCore.id("plague_wasteland"));
    public static final ResourceKey<Biome> PLAGUE_DESERT = ResourceKey.create(Registries.BIOME, CTNHCore.id("plague_desert"));
    public static final ResourceKey<Biome> ASTRAL_ORBIT = ResourceKey.create(Registries.BIOME, CTNHCore.id("astral_orbit"));

    public static void bootstrap(BootstapContext<Biome> ctx) {
        HolderGetter<PlacedFeature> holderGetter = ctx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = ctx.lookup(Registries.CONFIGURED_CARVER);
        ctx.register(PLAGUE_WASTELAND, AstralBiomes.plague_wasteland(holderGetter,holderGetter2));
        ctx.register(PLAGUE_DESERT, AstralBiomes.plague_desert(holderGetter,holderGetter2));
        ctx.register(ASTRAL_ORBIT, AstralBiomes.astral_orbit(holderGetter,holderGetter2));
    }

}
