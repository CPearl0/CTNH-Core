package io.github.cpearl0.ctnhcore.common.item;

import appeng.api.config.Actionable;
import appeng.api.features.IGridLinkableHandler;
import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.networking.IGrid;
import appeng.core.localization.GuiText;
import appeng.core.localization.PlayerMessages;
import appeng.core.localization.Tooltips;
import appeng.items.tools.powered.powersink.AEBasePoweredItem;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocators;
import appeng.util.Platform;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.hepdd.gtmthings.common.item.AdvancedTerminalBehavior;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleSupplier;

public class MEAdvancedTerminalItem extends ComponentItem {

    //private final MEAdvancedTerminalBehavior handler = new MEAdvancedTerminalBehavior();
    public static final IGridLinkableHandler LINKABLE_HANDLER = new MEAdvancedTerminalItem.LinkableHandler();

    private static final String TAG_ACCESS_POINT_POS = "accessPoint";


    public MEAdvancedTerminalItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag advancedTooltips) {
        if (this.getLinkedPosition(stack) == null) {
            lines.add(Tooltips.of(GuiText.Unlinked, Tooltips.RED));
        } else {
            lines.add(Tooltips.of(GuiText.Linked, Tooltips.GREEN));
        }
        super.appendHoverText(stack, level, lines, advancedTooltips);
    }

    public boolean isUsable(ItemStack item, Player player) {
        return !item.isEmpty()
                && item.getItem() == this
                && getLinkedGrid(item, player.level(), player) != null
                ;
    }

    @Nullable
    public GlobalPos getLinkedPosition(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains(TAG_ACCESS_POINT_POS)) return null;

        return GlobalPos.CODEC.parse(NbtOps.INSTANCE, stack.getTag().get(TAG_ACCESS_POINT_POS))
                .result()
                .orElse(null);
    }

    @Nullable
    public IGrid getLinkedGrid(ItemStack item, Level level, @Nullable Player sendMessagesTo) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        var linkedPos = getLinkedPosition(item);
        if (linkedPos == null) {
            if (sendMessagesTo != null) {
                sendMessagesTo.displayClientMessage(PlayerMessages.DeviceNotLinked.text(), true);
            }
            return null;
        }

        var linkedLevel = serverLevel.getServer().getLevel(linkedPos.dimension());
        if (linkedLevel == null) {
            if (sendMessagesTo != null) {
                sendMessagesTo.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            }
            return null;
        }

        var be = Platform.getTickingBlockEntity(linkedLevel, linkedPos.pos());
        if (!(be instanceof IWirelessAccessPoint accessPoint)) {
            if (sendMessagesTo != null) {
                sendMessagesTo.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            }
            return null;
        }

        var grid = accessPoint.getGrid();
        if (grid == null) {
            if (sendMessagesTo != null) {
                sendMessagesTo.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            }
        }
        return grid;
    }

    private static class LinkableHandler implements IGridLinkableHandler {
        @Override
        public boolean canLink(ItemStack stack) {
            return stack.getItem() instanceof MEAdvancedTerminalItem;
        }

        @Override
        public void link(ItemStack itemStack, GlobalPos pos) {
            GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, pos)
                    .result()
                    .ifPresent(tag -> itemStack.getOrCreateTag().put(TAG_ACCESS_POINT_POS, tag));
        }

        @Override
        public void unlink(ItemStack itemStack) {
            itemStack.removeTagKey(TAG_ACCESS_POINT_POS);
        }
    }
//    @Override
//    public void attachComponents(IItemComponent... components) {
//        super.atta
//    }
}
