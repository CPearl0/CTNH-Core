package io.github.cpearl0.ctnhcore.data.tags;

import com.aetherteam.aether.block.AetherBlocks;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import earth.terrarium.adastra.common.registry.ModBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class StoneTags {
    public static void init(RegistrateTagsProvider<Block> provider) {
        create(provider, CTNHTags.AD_ASTRA_STONES, ModBlocks.MOON_STONE.get(), ModBlocks.MARS_STONE.get(), ModBlocks.MERCURY_STONE.get(),
                ModBlocks.VENUS_STONE.get(), ModBlocks.GLACIO_STONE.get(), Blocks.BLACKSTONE, Blocks.BASALT, Blocks.DEEPSLATE, Blocks.SOUL_SOIL);
        create(provider, CTNHTags.AETHER_STONES, AetherBlocks.HOLYSTONE.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), AetherBlocks.ICESTONE.get());
    }

    public static void create(RegistrateTagsProvider<Block> provider, TagKey<Block> tagKey, Block... rls) {
        var builder = provider.addTag(tagKey);
        for (Block block : rls) {
            builder.addOptional(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)));
        }
    }
}
