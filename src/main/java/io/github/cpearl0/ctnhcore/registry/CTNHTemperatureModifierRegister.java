package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.legendary.ArmorModifier;
import io.github.cpearl0.ctnhcore.legendary.UnderfloorHeatingSystemTempModifier;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendarysurvivaloverhaul.api.temperature.DynamicModifierBase;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;

import static sfiomn.legendarysurvivaloverhaul.registry.TemperatureModifierRegistry.DYNAMIC_MODIFIERS;
import static sfiomn.legendarysurvivaloverhaul.registry.TemperatureModifierRegistry.MODIFIERS;

public class CTNHTemperatureModifierRegister {
    public static void init () {

    }
    public static final RegistryObject<ModifierBase> UNDERFLOOR_HEATING_SYSTEM = MODIFIERS.register("underfloor_heating_system", UnderfloorHeatingSystemTempModifier::new);
    public static final RegistryObject<DynamicModifierBase> ARMOR_MODIFIER = DYNAMIC_MODIFIERS.register("armor_modifier", ArmorModifier::new);
}
