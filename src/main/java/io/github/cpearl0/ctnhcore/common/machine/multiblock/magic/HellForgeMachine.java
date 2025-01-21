package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HellForgeMachine extends WorkableElectricMultiblockMachine {
    public double will = 0;
    public HellForgeMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var minimumDrain = recipe.data.getInt("minimumDrain");
        var drain=recipe.data.getInt("drain");
        if(will >= minimumDrain){
            will -= drain;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if(isFormed()) {
            textList.add(Component.translatable("ctnh.gtceu.hellforge.will", will));
        }
    }
}
