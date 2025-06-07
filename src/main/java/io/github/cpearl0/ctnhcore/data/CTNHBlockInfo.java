package io.github.cpearl0.ctnhcore.data;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.loot.LootBuilder;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.storage.loot.LootTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;

@SuppressWarnings("removal")
public class CTNHBlockInfo {
    public static void init() {

    }
    static {
        ASTRAL_STONE = REGISTRATE.block("astral_stone", Block::new)
                .initialProperties(() -> Blocks.STONE)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll("astral_stone", CTNHCore.id("block/stones/astral_stone")));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .loot((registrateBlockLootTables, block) -> {
                    registrateBlockLootTables.add(block, LootBuilder.createSingleItemTableWithSilkTouch(block, ASTRAL_COBBLESTONE.asItem()));
                })
                .item(BlockItem::new)
                .build()
                .register();
        ASTRAL_DIRT = REGISTRATE.block("astral_dirt", Block::new)
                .initialProperties(() -> Blocks.DIRT)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll("astral_dirt", CTNHCore.id("block/dirts/astral_dirt")));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .item(BlockItem::new)
                .build()
                .register();
        ASTRAL_GRASS_BLOCK = REGISTRATE.block("astral_grass_block", GrassBlock::new)
                .initialProperties(() -> Blocks.GRASS_BLOCK)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeBottomTop("astral_grass_block", CTNHCore.id("block/dirts/astral_grass_block_side"), CTNHCore.id("block/dirts/astral_dirt"), CTNHCore.id("block/dirts/astral_grass_block_top")));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .loot((loottable, block) -> {
                    loottable.add(block, LootBuilder.createSingleItemTableWithSilkTouch(ASTRAL_GRASS_BLOCK.get(), ASTRAL_DIRT.asItem()));
                })
                .item(BlockItem::new)
                .build()
                .register();
    }

}
