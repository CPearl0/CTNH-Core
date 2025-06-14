package io.github.cpearl0.ctnhcore.common.item;

import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MEAdvancedTerminalBehavior extends AdvancedTerminalBehavior implements IInteractionItem, IActionHost
{
    private ItemStack itemStack;
    public static final ThreadLocal<IWirelessAccessPoint> ACCESS_POINT_CONTEXT = new ThreadLocal<>();
    public static final ThreadLocal<UseOnContext> USE_ON_CONTEXT = new ThreadLocal<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand hand) {
        ItemStack is = player.getItemInHand(hand);
        if(player.isShiftKeyDown())
        {
            return new InteractionResultHolder<>(InteractionResult.PASS, is);
        }

        if(!level.isClientSide() && is.getItem() instanceof MEAdvancedTerminalItem terminal && terminal.isUsable(is, player))
        {

            return super.use(item, level, player, hand);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, is);

    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        ItemStack is = pContext.getItemInHand();

        if(is.getItem() instanceof MEAdvancedTerminalItem terminal
                && terminal.isUsable(is, pContext.getPlayer()))
        {
            ACCESS_POINT_CONTEXT.set(terminal.getAccessPoint(is, pContext.getLevel()));
            //player.sendSystemMessage(Component.literal("set ACCESS_POINT_CONTEXT"));
            USE_ON_CONTEXT.set(pContext);
            //player.sendSystemMessage(Component.literal("set USE_ON_CONTEXT"));
            InteractionResult result = super.useOn(pContext);

            ACCESS_POINT_CONTEXT.remove();
            USE_ON_CONTEXT.remove();
            return result;
        }
        return InteractionResult.PASS;
    }


    @Override
    public @Nullable IGridNode getActionableNode() {
        return null;
    }
}

//class MEBuildHelper extends AdvancedBlockPattern {
//    public MEBuildHelper(TraceabilityPredicate[][][] predicatesIn, RelativeDirection[] structureDir, int[][] aisleRepetitions, int[] centerOffset) {
//        super(predicatesIn, structureDir, aisleRepetitions, centerOffset);
//    }
//
//
//    private static Pair<Integer, IItemHandler> getMatchStackWithHandler(List<ItemStack> candidates, LazyOptional<IItemHandler> cap) {
//
//    }
//}