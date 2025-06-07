package io.github.cpearl0.ctnhcore.api.loot;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootBuilder {
    protected static LootTable.Builder createSelfDropDispatchTable(Block block, LootItemCondition.Builder builder, LootPoolEntryContainer.Builder<?> builder2) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(((LootPoolSingletonContainer.Builder) LootItem.lootTableItem(block).when(builder)).otherwise(builder2)));
    }
    public static LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike itemLike) {
        return LootBuilder.createSilkTouchDispatchTable(block, (LootPoolEntryContainer.Builder)LootBuilder.applyExplosionCondition(block, LootItem.lootTableItem(itemLike)));
    }
    protected static LootTable.Builder createSilkTouchDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return LootBuilder.createSelfDropDispatchTable(block, HAS_SILK_TOUCH, builder);
    }
    protected static <T extends ConditionUserBuilder<T>> T applyExplosionCondition(ItemLike itemLike, ConditionUserBuilder<T> conditionUserBuilder) {
            return conditionUserBuilder.when(ExplosionCondition.survivesExplosion());
    }
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
}
