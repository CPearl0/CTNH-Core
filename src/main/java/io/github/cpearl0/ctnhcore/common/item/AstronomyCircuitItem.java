package io.github.cpearl0.ctnhcore.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class AstronomyCircuitItem extends Item {
    public static final ResourceLocation[][] DIMENSIONS = new ResourceLocation[][] {
            { Level.OVERWORLD.location() },
            { Level.OVERWORLD.location() , new ResourceLocation("ad_astra:moon") },
    };

    public final int tier;

    public AstronomyCircuitItem(Properties properties, int tier) {
        super(properties.stacksTo(1));
        this.tier = tier;
    }

    public static boolean workInLevel(ItemStack stack, Level level) {
        if (!(stack.getItem() instanceof AstronomyCircuitItem item))
            return false;
        return Arrays.asList(DIMENSIONS[item.tier]).contains(level.dimension().location());
    }

    public static void gainData(ItemStack stack, Level level) {
        if (!workInLevel(stack, level))
            return;
        var tagName = level.dimension().location().getPath().concat("_data");
        var tag = stack.getOrCreateTag();
        var data = tag.contains(tagName) ? tag.getInt(tagName) : 0;
        if (data >= 100)
            return;
        tag.putInt(tagName, data + 1);
    }
}
