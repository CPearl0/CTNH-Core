package io.github.cpearl0.ctnhcore.registry;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.function.Supplier;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.BLOCK);
    }

    public static final BlockEntry<Block> CASING_REFLECT_LIGHT = createCasingBlock("reflect_light_casing",
            CTNHCore.id("block/casings/reflect_light_casing"));
    public static final BlockEntry<Block> CASING_TUNGSTENCU_DIAMOND_PLATING = createCasingBlock("tungstencu_diamond_plating_casing",
            CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"));

    public static final BlockEntry<Block> ENERGETIC_PHOTOVOLTAIC_BLOCK = createCasingBlock("energetic_photovoltaic_block",
            CTNHCore.id("block/energetic_photovoltaic_block"));
    public static final BlockEntry<Block> PULSATING_PHOTOVOLTAIC_BLOCK = createCasingBlock("pulsating_photovoltaic_block",
            CTNHCore.id("block/pulsating_photovoltaic_block"));
    public static final BlockEntry<Block> VIBRANT_PHOTOVOLTAIC_BLOCK = createCasingBlock("vibrant_photovoltaic_block",
            CTNHCore.id("block/vibrant_photovoltaic_block"));

    public static void init() {

    }

    // Utils
    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
                })
                .tag(TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("forge", "mineable/wrench")), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }
}
