package io.github.cpearl0.ctnhcore.data.tags;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import io.github.cpearl0.ctnhcore.registry.CTNHTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.init.TFBiomes;

import java.util.Objects;

public class BiomeTags {
    public static void init(RegistrateTagsProvider<Biome> provider) {
        create(provider, CTNHTags.TWILIGHT_TIER1, TFBiomes.DARK_FOREST, TFBiomes.SNOWY_FOREST, TFBiomes.SWAMP);
        create(provider, CTNHTags.TWILIGHT_TIER2, TFBiomes.FIRE_SWAMP, TFBiomes.DARK_FOREST_CENTER, TFBiomes.GLACIER);
        create(provider, CTNHTags.TWILIGHT_TIER3, TFBiomes.HIGHLANDS);
    }
    public static void create(RegistrateTagsProvider<Biome> provider, TagKey<Biome> tagKey, ResourceKey<Biome>... rls) {
        var builder = provider.addTag(tagKey);
        for (ResourceKey<Biome> biome : rls) {
            builder.add(biome);
        }
    }
}
