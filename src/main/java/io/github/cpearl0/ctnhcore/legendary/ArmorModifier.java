package io.github.cpearl0.ctnhcore.legendary;

import io.github.cpearl0.ctnhcore.registry.adventure.CTNHEnchantments;
import net.minecraft.world.entity.player.Player;
import sfiomn.legendarysurvivaloverhaul.api.temperature.DynamicModifierBase;

public class ArmorModifier extends DynamicModifierBase {
    public ArmorModifier() {
    }

    @Override
    public float applyDynamicPlayerInfluence(Player player, float currentTemperature, float currentResistance) {
        float sumWarming = 0;
        float sumCooling = 0;
        for (var armor : player.getArmorSlots()) {
            if (armor.getEnchantmentLevel(CTNHEnchantments.WARMING.get()) != 0) {
                sumWarming += armor.getEnchantmentLevel(CTNHEnchantments.WARMING.get());
            }
            if (armor.getEnchantmentLevel(CTNHEnchantments.COOLING.get()) != 0) {
                sumCooling += armor.getEnchantmentLevel(CTNHEnchantments.COOLING.get());
            }
        }
        float tempDiff = currentTemperature - currentResistance;
        if (tempDiff >= 0) {
            return -tempDiff * Math.min(1, sumCooling / 20);
        }
        else {
            return -tempDiff * Math.min(1, sumWarming / 20);
        }
    }
}
