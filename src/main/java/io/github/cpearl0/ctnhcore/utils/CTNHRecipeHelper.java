package io.github.cpearl0.ctnhcore.utils;

import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeCapabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import io.netty.util.internal.UnstableApi;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CTNHRecipeHelper {

    @UnstableApi
    public static int getInputCWUt(@NotNull GTRecipe recipe) {
        return recipe.tickInputs.getOrDefault(GTRecipeCapabilities.CWU, new ArrayList<>())
                .stream().reduce(0, Integer::sum);
    }

    public static ItemStack insertItemToOutput(NotifiableItemStackHandler handler, ItemStack stack, boolean simulate) {
        if (handler == null || stack.isEmpty()) {
            return stack;
        }
        if (!stack.isStackable()) {
            return insertToEmpty(handler, stack, simulate);
        }

        IntList emptySlots = new IntArrayList();
        int slots = handler.getSlots();

        for (int i = 0; i < slots; i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                emptySlots.add(i);
            } else if (ItemHandlerHelper.canItemStacksStack(stack, slotStack)) {
                stack = handler.insertItemInternal(i, stack, simulate);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
            }
        }

        for (int slot : emptySlots) {
            stack = handler.insertItemInternal(slot, stack, simulate);
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }
        return stack;
    }

    /**
     * Only inerts to empty slots. Perfect for not stackable items
     */
    public static ItemStack insertToEmpty(NotifiableItemStackHandler handler, ItemStack stack, boolean simulate) {
        if (handler == null || stack.isEmpty()) {
            return stack;
        }
        int slots = handler.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                stack = handler.insertItemInternal(i, stack, simulate);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }
}
