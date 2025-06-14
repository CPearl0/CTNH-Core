package io.github.cpearl0.ctnhcore.common.item;

import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.MEStorage;
import appeng.me.helpers.MachineSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.ACCESS_POINT_CONTEXT;
import static io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalBehavior.USE_ON_CONTEXT;

public class SingleItemHandler implements IItemHandler {

    private final ItemStack stack;
    private final AEItemKey key;
    private final IGrid grid;

    public SingleItemHandler(AEItemKey key, IGrid grid) {
        this.key = key;
        this.grid = grid;
        this.stack = key.toStack((int) grid.getStorageService().getInventory().getAvailableStacks().get(key));
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stack;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack; // 插入无效
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!stack.isEmpty() && amount > 0) {
            int extractAmount = Math.min(amount, stack.getCount());
            ItemStack result = stack.copy();
            result.setCount(extractAmount);
            var storage = grid.getStorageService().getInventory();
            if (!simulate) {
                var a = storage.extract(
                        key,
                        extractAmount,
                        Actionable.MODULATE,
                        IActionSource.ofMachine(ACCESS_POINT_CONTEXT.get())
                );
            }
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return false;
    }
}
