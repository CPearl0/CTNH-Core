package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.world.item.ItemStack;

public class MachineUtils {
    public static void inputItem(ItemStack itemStack, WorkableMultiblockMachine machine) {
        var Recipe = GTRecipeBuilder.ofRaw().inputItems(itemStack).buildRawRecipe();
        if (Recipe.matchRecipe(machine).isSuccess()) {
            Recipe.handleRecipeIO(IO.IN, machine, machine.getRecipeLogic().getChanceCaches());
        }
    }
}
