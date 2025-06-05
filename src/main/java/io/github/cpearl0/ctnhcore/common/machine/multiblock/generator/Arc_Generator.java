package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeCapabilities;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.EternalWosMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.HellForgeMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

public class Arc_Generator extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public int arc=0;
    public int arc_max=0;
    public double efficiency=0;
    public  double rotor=0;
    public String ARC ="arc";
    public Arc_Generator(IMachineBlockEntity holder,double efficiency,int arc_max)
    {
        super(holder);
        this.efficiency=efficiency;
        this.arc_max=arc_max;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if(arc<recipe.data.getInt("requirearc"))
        {
            return false;
        }
        arc-=recipe.data.getInt("requirearc")/10;
        arc=Math.max(0,arc);

        return super.beforeWorking(recipe);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof Arc_Generator gmachine) {
            var arc_difference=recipe.data.getInt("maxarc")-recipe.data.getInt("requirearc");
            double rotor=Math.max(0.00001, (double) (gmachine.arc - recipe.data.getInt("requirearc")) /arc_difference);
            rotor=Math.min(gmachine.efficiency, rotor);
            gmachine.rotor=rotor;
            return ModifierFunction.builder()
                    .eutMultiplier(rotor)
                    .build();
        }
        return ModifierFunction.NULL;
    }
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();

        textList.add(textList.size(), Component.translatable("ctnh.arcgenerator.arc.1",String.format("%d",arc_max)));
        textList.add(textList.size(), Component.translatable("ctnh.arcgenerator.arc.2",String.format("%d",arc)));
        textList.add(textList.size(), Component.translatable("ctnh.arcgenerator.arc.3",String.format("%.2f",efficiency*100)));
        textList.add(textList.size(), Component.translatable("ctnh.arcgenerator.arc.4",String.format("%.2f",rotor*100)));
    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}
