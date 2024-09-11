package io.github.cpearl0.ctnhcore;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.github.cpearl0.ctnhcore.block.LiquidBlazeBurnerBlock;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CTNHGameEvent {
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
