package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class CTNHTags {
    public static TagKey<Block> AD_ASTRA_STONES = TagUtil.createBlockTag("ad_astra_stones");
    public static TagKey<Block> AETHER_STONES = TagUtil.createBlockTag("aether_stones");
    public static TagKey<Block> ALFHEIM_STONES = TagUtil.createBlockTag("alfheim_stones");
    public static TagKey<Biome> TWILIGHT_TIER1 = TagUtil.createTag(Registries.BIOME, "twilight_tier1", true);
    public static TagKey<Biome> TWILIGHT_TIER2 = TagUtil.createTag(Registries.BIOME, "twilight_tier1", true);
    public static TagKey<Biome> TWILIGHT_TIER3 = TagUtil.createTag(Registries.BIOME, "twilight_tier1", true);
}
