package io.github.cpearl0.ctnhcore.registry.adventure;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.enchantment.TemperatureEnchantment;
import io.github.cpearl0.ctnhcore.common.enchantment.VacuumSealEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CTNHEnchantments {
    public static DeferredRegister<Enchantment> Enchantments = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CTNHCore.MODID);
    public static final RegistryObject<VacuumSealEnchantment> VACUUM_SEAL = Enchantments.register("vacuum_seal", VacuumSealEnchantment::new);
    public static final RegistryObject<TemperatureEnchantment> WARMING = Enchantments.register("warming", () -> new TemperatureEnchantment(false));
    public static final RegistryObject<TemperatureEnchantment> COOLING = Enchantments.register("cooling", () -> new TemperatureEnchantment(true));

}
