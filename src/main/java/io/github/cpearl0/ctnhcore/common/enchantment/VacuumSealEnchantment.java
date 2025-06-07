package io.github.cpearl0.ctnhcore.common.enchantment;

import io.github.cpearl0.ctnhcore.registry.adventure.CTNHEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class VacuumSealEnchantment extends Enchantment {
    public VacuumSealEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
    }
    @Override
    public int getMaxCost(int pLevel) {
        return 200;
    }
    @Override
    public int getMinCost(int pLevel) {
        return 165;
    }
    @Override
    public boolean isTradeable() {
        return false;
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
    public static boolean hasFullEnchant(Entity entity) {
        if (entity instanceof Player player) {
            if (player.isCreative() || player.isSpectator()) return true;
            for (var armor : player.getArmorSlots()) {
                if (armor.getAllEnchantments().get(CTNHEnchantments.VACUUM_SEAL.get()) == null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
