package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.List;

public class Arc_Generator extends RecipeElectricMultiblockMachine implements ITieredMachine {

    @CN("电弧最大强度:%d")
    @EN("Max Arc Intensity: %d")
    public static Lang arcgeneratorInfo0;

    @CN("电弧强度:%d")
    @EN("Current Arc Intensity: %d")
    public static Lang arcgeneratorInfo1;

    @CN("支持最大效率:%.2f%%")
    @EN("Max Supported Efficiency: %.2f%%")
    public static Lang arcgeneratorInfo2;

    @CN("当前效率:%.2f%%")
    @EN("Current Efficiency: %.2f%%")
    public static Lang arcgeneratorInfo3;

    @CN("电弧强度不足：本次配方需要 %d，当前仅有 %d")
    @EN("Insufficient arc intensity: this recipe needs %d, currently %d")
    public static Lang arcIntensityInsufficient;

    public int arc = 0;
    public int arc_max = 0;
    public double efficiency = 0;
    public double rotor = 0;
    public String ARC = "arc";

    public Arc_Generator(IMachineBlockEntity holder, double efficiency, int arc_max) {
        super(holder);
        this.efficiency = efficiency;
        this.arc_max = arc_max;
    }

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        var requiredArc = recipe.data.getInt("requirearc");
        if (arc < requiredArc) {
            return arcIntensityInsufficient.translate(requiredArc, arc);
        }
        arc -= requiredArc / 10;
        arc = Math.max(0, arc);

        return super.beforeWorking(recipe);
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof Arc_Generator gmachine) {
            var arc_difference = recipe.data.getInt("maxarc") - recipe.data.getInt("requirearc");
            double rotor = Math.max(0.00001,
                    (double) (gmachine.arc - recipe.data.getInt("requirearc")) / arc_difference);
            rotor = Math.min(gmachine.efficiency, rotor);
            gmachine.rotor = rotor;
            recipe.multiplyEUt(rotor);
            return null;
        }
        return RecipeModifier.nullWrongType(Arc_Generator.class, machine);
    }

    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();

        textList.add(textList.size(),
                arcgeneratorInfo0.translate(String.format("%d", arc_max)));
        textList.add(textList.size(),
                arcgeneratorInfo1.translate(String.format("%d", arc)));
        textList.add(textList.size(),
                arcgeneratorInfo2.translate(String.format("%.2f", efficiency * 100)));
        textList.add(textList.size(),
                arcgeneratorInfo3.translate(String.format("%.2f", rotor * 100)));
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }
}
