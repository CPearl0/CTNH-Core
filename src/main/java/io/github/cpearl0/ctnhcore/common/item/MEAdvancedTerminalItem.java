package io.github.cpearl0.ctnhcore.common.item;

import appeng.api.features.GridLinkables;
import appeng.api.networking.IGrid;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocators;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.DoubleSupplier;

public class MEAdvancedTerminalItem extends WirelessTerminalItem implements IComponentItem {

    private final MEAdvancedTerminalBehavior handler = new MEAdvancedTerminalBehavior();
    public static final ThreadLocal<IGrid> GRID_CONTEXT = new ThreadLocal<>();
    public static final ThreadLocal<UseOnContext> USE_ON_CONTEXT = new ThreadLocal<>();

    public MEAdvancedTerminalItem(DoubleSupplier powerCapacity, Properties props) {
        super(powerCapacity, props);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack is = player.getItemInHand(hand);
        if(player.isShiftKeyDown())
        {
            return new InteractionResultHolder<>(InteractionResult.PASS, is);
        }

        if(!level.isClientSide() && checkPreconditions(is, player))
        {

            //System.out.println("Grid:" + this.getLinkedGrid(is, level, player));
            return handler.use(this, level, player, hand);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, is);

    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        ItemStack is = pContext.getItemInHand();
        if(!pContext.getLevel().isClientSide() && checkPreconditions(is, pContext.getPlayer()))
        {
            GRID_CONTEXT.set(this.getLinkedGrid(is, pContext.getLevel(), null));
            USE_ON_CONTEXT.set(pContext);
            var result =  handler.useOn(pContext);
            if(result.consumesAction())
                this.usePower(pContext.getPlayer(), 100, is);
            GRID_CONTEXT.remove();
            USE_ON_CONTEXT.remove();
            return result;
        }
        return InteractionResult.PASS;
    }

    @Override
    public List<IItemComponent> getComponents() {
        return List.of();
    }

    @Override
    public void attachComponents(IItemComponent... components) {

    }
}
