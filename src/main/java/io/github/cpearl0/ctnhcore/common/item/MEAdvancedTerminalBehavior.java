package io.github.cpearl0.ctnhcore.common.item;

import appeng.api.networking.IGrid;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.hepdd.gtmthings.api.pattern.AdvancedBlockPattern;
import com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MEAdvancedTerminalBehavior extends AdvancedTerminalBehavior implements IInteractionItem
{
    private ItemStack itemStack;
    public static final ThreadLocal<IGrid> GRID_CONTEXT = new ThreadLocal<>();
    public static final ThreadLocal<UseOnContext> USE_ON_CONTEXT = new ThreadLocal<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand hand) {
        //System.out.println("111");
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


    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        //System.out.println("222");
        ItemStack is = pContext.getItemInHand();

        if(is.getItem() instanceof MEAdvancedTerminalItem terminal
                && terminal.isUsable(is, pContext.getPlayer()))
        {
            GRID_CONTEXT.set(terminal.getLinkedGrid(is, pContext.getLevel(), null));
            USE_ON_CONTEXT.set(pContext);
            var result = super.useOn(pContext);
//            if(result.consumesAction())
//                terminal.usePower(is, 100);
            GRID_CONTEXT.remove();
            USE_ON_CONTEXT.remove();
            return result;
        }
        return InteractionResult.PASS;
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