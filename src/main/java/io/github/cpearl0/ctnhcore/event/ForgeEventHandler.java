package io.github.cpearl0.ctnhcore.event;

import com.momosoftworks.coldsweat.api.event.core.GatherDefaultTempModifiersEvent;
import com.momosoftworks.coldsweat.api.event.core.TempModifierRegisterEvent;
import com.momosoftworks.coldsweat.api.util.Placement;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.block.LiquidBlazeBurnerBlock;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
        }
    }

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock evt) {
        try {
            if(evt.getLevel().isClientSide()) return;
            BlockState state = evt.getLevel().getBlockState(evt.getPos());
            if(evt.getItemStack().getItem() == CTNHItems.STRAW.get() && evt.getLevel().getBlockEntity(evt.getPos()) instanceof BlazeBurnerBlockEntity) {
                if(state.is(AllBlocks.BLAZE_BURNER.get())) {
                    BlockState newState = CTNHBlocks.LIQUID_BLAZE_BURNER.getDefaultState()
                            .setValue(LiquidBlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING/*state.getValue(BlazeBurnerBlock.HEAT_LEVEL)*/)
                            .setValue(LiquidBlazeBurnerBlock.FACING, state.getValue(BlazeBurnerBlock.FACING));
                    evt.getLevel().setBlockAndUpdate(evt.getPos(), newState);
                    if(!evt.getEntity().isCreative())
                        evt.getItemStack().shrink(1);
                    evt.setCancellationResult(InteractionResult.SUCCESS);
                    evt.setCanceled(true);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
