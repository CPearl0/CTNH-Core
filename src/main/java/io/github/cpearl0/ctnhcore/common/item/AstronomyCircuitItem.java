package io.github.cpearl0.ctnhcore.common.item;

import io.github.cpearl0.ctnhcore.registry.CTNHWorlds;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.function.Supplier;

public class AstronomyCircuitItem extends Item {
    public static final ResourceLocation[][] DIMENSIONS = new ResourceLocation[][] {
            { CTNHWorlds.THE_AETHER },
            { CTNHWorlds.THE_AETHER , new ResourceLocation("ad_astra:moon") },
    };

    private final int tier;
    @Getter
    private final Supplier<Item> finishedItem;

    public AstronomyCircuitItem(Properties properties, int tier, Supplier<Item> finishedItem) {
        super(properties.stacksTo(1));
        this.tier = tier;
        this.finishedItem = finishedItem;
    }

    public static boolean workInLevel(ItemStack stack, Level level) {
        if (!(stack.getItem() instanceof AstronomyCircuitItem item))
            return false;
        return Arrays.asList(DIMENSIONS[item.tier]).contains(level.dimension().location());
    }

    public static boolean gainData(ItemStack stack, Level level) {
        if (!workInLevel(stack, level))
            return false;
        var tagName = level.dimension().location().getPath().concat("_data");
        var tag = stack.getOrCreateTag();
        var data = tag.contains(tagName) ? tag.getInt(tagName) : 0;
        if (data >= 99) {
            if (data == 99)
                tag.putInt(tagName, data + 1);
            return hasEnoughData(stack);
        }
        tag.putInt(tagName, data + 1);
        return false;
    }

    public static boolean hasEnoughData(ItemStack stack) {
        if (!(stack.getItem() instanceof AstronomyCircuitItem item))
            return false;
        var dimensions = DIMENSIONS[item.tier - 1];
        for (var dimension : dimensions) {
            var tagName = dimension.getPath().concat("_data");
            var tag = stack.getOrCreateTag();
            if (tag.contains(tagName) && tag.getInt(tagName) < 100)
                return false;
        }
        return true;
    }
}
