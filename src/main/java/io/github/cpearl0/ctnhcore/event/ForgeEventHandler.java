package io.github.cpearl0.ctnhcore.event;

import earth.terrarium.adastra.api.systems.OxygenApi;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.enchantment.VacuumSealEnchantment;
import io.github.cpearl0.ctnhcore.legendary.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.WindPowerArrayMachine;
import io.github.cpearl0.ctnhcore.registry.adventure.CTNHEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendarysurvivaloverhaul.registry.AttributeRegistry;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.getServer().getTickCount() % 40 == 0) {
            UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.clear();
            WindPowerArrayMachine.clearNet();
            WindPowerArrayMachine.groupByNetwork();
        }
    }
    @SubscribeEvent
    public static void enchantmentTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.isCreative() || player.isSpectator()) return;
            player.getArmorSlots().forEach(armor -> {
                if (armor.getAllEnchantments().get(CTNHEnchantments.VACUUM_SEAL.get()) == null){
                    return;
                }
                player.setTicksFrozen(0);
                if (player.isEyeInFluid(FluidTags.WATER) && !player.level().getBlockState(BlockPos.containing(player.getX(), player.getEyeY(), player.getZ())).is(Blocks.BUBBLE_COLUMN)) {
                    player.setAirSupply(Math.min(player.getMaxAirSupply(), player.getAirSupply() + 4 * 10));
                }
            });
        }
    }
}
