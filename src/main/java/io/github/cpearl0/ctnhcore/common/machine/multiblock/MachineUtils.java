package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class MachineUtils {
    public static boolean inputItem(ItemStack itemStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputItems(itemStack).buildRawRecipe();
        if (Recipe.matchRecipe(machine).isSuccess()) {
            Recipe.handleRecipeIO(IO.IN, machine, machine.getRecipeLogic().getChanceCaches());
            return true;
        }
        return false;
    }
    public static boolean canInputItem(ItemStack itemStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputItems(itemStack).buildRawRecipe();
        return Recipe.matchRecipe(machine).isSuccess();
    }
    public static BlockPos getOffset(MetaMachine machine, int backoff) {
        var pos = machine.getPos();
        var facing = machine.getFrontFacing();
        switch (facing) {
            case NORTH -> {
                return pos.offset(0,0,backoff);
            }
            case SOUTH -> {
                return pos.offset(0,0,-backoff);
            }
            case WEST -> {
                return pos.offset(backoff,0,0);
            }
            case EAST -> {
                return pos.offset(-backoff,0,0);
            }
        }
        return pos;
    }
}
