package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeCapabilities;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class EternalWosMachine extends WorkableElectricMultiblockMachine {
    public double multiplier = 1;
    public EternalWosMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        getParts().forEach(part-> part.getRecipeHandlers().forEach(trait->{
            if(trait.getHandlerIO().equals(IO.IN) && trait.getCapability().equals(GTRecipeCapabilities.ITEM)){
                trait.getContents().forEach((content)->{
                    var count=((ItemStack)content).getTag().getCompound("data_model").getInt("data");
                    if(count<6) multiplier=0;
                    else if(count<48) multiplier=1;
                    else if(count<300) multiplier=1.5;
                    else if(count<900) multiplier=2;
                    else multiplier=3;
                });
            }
        }));
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        getParts().forEach(part-> part.getRecipeHandlers().forEach(trait->{
            if(trait.getHandlerIO().equals(IO.IN) && trait.getCapability().equals(GTRecipeCapabilities.ITEM)){
                trait.getContents().forEach((content)->{
                    var count = ((ItemStack)content).getTag().getCompound("data_model").getInt("data");
                    if(count < 900) ((ItemStack)content).getTag().getCompound("data_model").putInt("data",count + 1);
                });
            }
        }));
        super.afterWorking();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        if(machine instanceof EternalWosMachine dmachine) {
            var level = dmachine.self().getLevel();
            var pos= dmachine.self().getPos();
            pos = pos.offset(0,-11,0);
            var maxParallel = ParallelLogic.getParallelAmount(dmachine,recipe,2147483647);
            ModifierFunction parallel = CTNHRecipeModifiers.accurateParallel(machine,recipe,maxParallel);
            if(getMachine(level,pos) instanceof HellForgeMachine hmachine){
                var outputAmount=((FluidIngredient)(recipe.getOutputContents(GTRecipeCapabilities.FLUID).get(0).content)).getAmount();
                hmachine.will = hmachine.will + maxParallel * outputAmount * dmachine.multiplier/100000;
                dmachine.multiplier=0;
            }

            return ModifierFunction.builder().outputModifier(ContentModifier.multiplier(dmachine.multiplier)).build().compose(parallel);
        }
        return ModifierFunction.IDENTITY;
    }
}
