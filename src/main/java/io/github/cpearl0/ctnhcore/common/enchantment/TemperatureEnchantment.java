package io.github.cpearl0.ctnhcore.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TemperatureEnchantment extends Enchantment {
    public boolean isCold;
    public TemperatureEnchantment(boolean isCold) {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
        this.isCold = isCold;
    }
    @Override
    public int getMaxCost(int pLevel) {
        return pLevel * 50;
    }
    @Override
    public int getMinCost(int pLevel) {
        return pLevel * 45;
    }
    @Override
    public int getMaxLevel() {
        return 5;
    }

}
