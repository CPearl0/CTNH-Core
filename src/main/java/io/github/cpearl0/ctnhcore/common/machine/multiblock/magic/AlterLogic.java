package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.List;
import java.util.Objects;

public class AlterLogic extends WorkableElectricMultiblockMachine {
    public int max_lp=10000;
    public int lp=0;
    public int add_lp=0;
public  int Speed_rune=0;
public  int Capacity_rune=0;
    public BlockPos[] Runes = new BlockPos[]{
            getPos().offset(1,2,-3),
            getPos().offset(-1,2,-3),
            getPos().offset(0,2,-4),
            getPos().offset(0,2,-2)

    };
    public void calculateRune() {
        Speed_rune = 0;
        Capacity_rune = 0;
        for(var rune : Runes){
            var runeBlock = Objects.requireNonNull(getLevel()).getBlockState(rune).getBlock();
            if (runeBlock.equals(BloodMagicBlocks.SPEED_RUNE.get())) {
                Speed_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.SPEED_RUNE_2.get())) {
                Speed_rune += 2;
            }
            else if (runeBlock.equals(BloodMagicBlocks.CAPACITY_RUNE.get())) {
                Capacity_rune ++;
            }
            else if (runeBlock.equals(BloodMagicBlocks.CAPACITY_RUNE_2.get())) {
                Capacity_rune += 2;
            }
        }
    }
    public void addDisplayText(List<Component> textList) {
        var tier = getTier();
        super.addDisplayText(textList);
        textList.add(textList.size(), Component.translatable("ctnh.lp_now",String.format("%d",lp)));
         textList.add(textList.size(), Component.translatable("ctnh.lp_max",String.format("%d",max_lp)));
    }

    @Override
    public void onStructureFormed() {

        calculateRune();
        var tier = getTier();
        max_lp=10000;
        max_lp+=10000*Math.max((tier-3),0);
        max_lp+=20000*Math.max((tier-5),0);
        max_lp+=2500*Capacity_rune;
        max_lp+=(2500*Capacity_rune*Math.max((tier-5),0));
        super.onStructureFormed();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        int consume=recipe.data.getInt("addlp");
        if (consume > 0) {
            add_lp=consume;
        }
        else{
            if(consume+lp<0)
            {
                return false;
            }
            else{
                lp+=consume;
            }
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        if(add_lp!=0) {
            if (lp + add_lp >= max_lp) lp = max_lp;
            else lp = lp + add_lp;
            add_lp = 0;
        }
    }

    public AlterLogic(IMachineBlockEntity holder){
        super(holder);
        var tier = getTier();

    }


    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof AlterLogic wmachine) {
            int tier=wmachine.getTier();
            int recipe_tier=recipe.ocLevel;
            return ModifierFunction.builder()
                    .build();
        }
        return ModifierFunction.NULL;
    }
}
