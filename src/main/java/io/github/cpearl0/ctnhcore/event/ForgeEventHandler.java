package io.github.cpearl0.ctnhcore.event;

import com.momosoftworks.coldsweat.api.event.core.GatherDefaultTempModifiersEvent;
import com.momosoftworks.coldsweat.api.event.core.TempModifierRegisterEvent;
import com.momosoftworks.coldsweat.api.util.Placement;
import com.momosoftworks.coldsweat.api.util.Temperature;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.WindPowerArrayMachine;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {
    @SubscribeEvent
    public static void registerTempModifiers(TempModifierRegisterEvent event)
    {
        event.register(CTNHCore.id("underfloor_heating_system"), UnderfloorHeatingSystemTempModifier::new);
    }

    @SubscribeEvent
    public static void defineDefaultModifiers(GatherDefaultTempModifiersEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getTrait() == Temperature.Trait.WORLD) {
                event.addModifier(new UnderfloorHeatingSystemTempModifier().tickRate(10), Placement.Duplicates.BY_CLASS, Placement.AFTER_LAST);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.getServer().getTickCount() % 40 == 0) {
            UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.clear();
            WindPowerArrayMachine.clearNet();
        }
    }
}
