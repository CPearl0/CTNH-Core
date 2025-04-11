package io.github.cpearl0.ctnhcore.data;

import io.github.cpearl0.ctnhcore.CTNHCore;
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
//                .loot((registrateBlockLootTables, block) -> {
//                    try {
//                        registrateBlockLootTables.add((Block) block, (LootTable.Builder) method.invoke((Block)block, ASTRAL_COBBLESTONE.get()));
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvocationTargetException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
                .item(BlockItem::new)
                .build()
                .register();
        ASTRAL_GRASS = REGISTRATE.block("astral_grass", GrassBlock::new)
                .initialProperties(() -> Blocks.GRASS_BLOCK)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeBottomTop("astral_grass", CTNHCore.id("block/dirts/astral_grass_side"), CTNHCore.id("block/dirts/astral_dirt"), CTNHCore.id("block/dirts/astral_grass_top")));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
//                .loot((loottable,block) -> {
//                    try {
//                        loottable.add((Block) block, (LootTable.Builder) method.invoke((Block)block, ASTRAL_DIRT.get()));
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvocationTargetException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
                .item(BlockItem::new)
                .build()
                .register();
    }

}
