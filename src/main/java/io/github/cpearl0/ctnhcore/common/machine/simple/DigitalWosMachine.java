package io.github.cpearl0.ctnhcore.common.machine.simple;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DigitalWosMachine extends SimpleTieredMachine {
    public double multiplier = 0;
    public DigitalWosMachine(IMachineBlockEntity holder, int tier, Int2IntFunction tankScalingFunction, Object... args){
        super(holder, tier, tankScalingFunction, args);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if(!importItems.isEmpty()) {
            ItemStack stack = (ItemStack) importItems.getContents().get(0);
            var count = stack.getTag().getCompound("data_model").getInt("data");
            if (count < 6) multiplier = 0;
            else if (count < 48) multiplier = 1;
            else if (count < 300) multiplier = 1.5;
            else if (count < 900) multiplier = 2;
            else multiplier = 3;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        if(!importItems.isEmpty()){
            ItemStack stack = (ItemStack) importItems.getContents().get(0);
            var count = stack.getTag().getCompound("data_model").getInt("data");
            if(count < 900) stack.getTag().getCompound("data_model").putInt("data",count + 1);
        }
        super.afterWorking();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
            if(machine instanceof DigitalWosMachine dmachine) {
                return ModifierFunction.builder().outputModifier(ContentModifier.multiplier(dmachine.multiplier)).build();
            }
            return ModifierFunction.IDENTITY;
    }
}
