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
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Arc_Reactor extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public int arc;
    public boolean isconnect=false;
    public BlockPos pos;
    public Level level;
    @Override
    public void afterWorking() {
        super.afterWorking();
        if(!isconnect)
        {
            arc=arc;
        }
        else if(getMachine(level,pos) instanceof Arc_Generator gmachine&&isconnect){
            gmachine.arc+=arc;
        }
    }

    public Arc_Reactor(IMachineBlockEntity holder, int arc)
    {
        super(holder);
        this.arc=arc;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        if(machine instanceof Arc_Reactor dmachine) {
            var level = dmachine.self().getLevel();
            var pos= dmachine.self().getPos();

            pos = pos.offset(0,-5,0);
            if(getMachine(level,pos) instanceof Arc_Generator gmachine){
                dmachine.pos=pos;
                dmachine.level=level;
                dmachine.isconnect=true;
            }
            else
            {
                dmachine.isconnect=false;
            }
            return ModifierFunction.builder()
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();
        if(isconnect)
        {
            textList.add(textList.size(), Component.translatable("ctnh.arc.r.connect"));
        }
        textList.add(textList.size(), Component.translatable("ctnh.arc.r.arc",String.format("%d",arc)));


    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}
