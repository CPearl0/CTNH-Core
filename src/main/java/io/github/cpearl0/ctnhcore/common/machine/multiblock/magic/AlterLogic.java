package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.List;
import java.util.Objects;

public class AlterLogic extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public int max_lp=10000;
    public int lp=0;
    public int add_lp=0;
    public static final String MAX_LP="max_lp";
    public static final String LP = "lp";
    public BlockPos[] Runes = new BlockPos[]{
            MachineUtils.getOffset(this,1,2,3),
            MachineUtils.getOffset(this,-1,2,3),
            MachineUtils.getOffset(this,0,2,4),
            MachineUtils.getOffset(this,0,2,2)

    };
    public int calculateRune() {
        int Speed_rune = 0;
        int Capacity_rune = 0;
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
        return Capacity_rune;
    }
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();

        textList.add(textList.size(), Component.translatable("ctnh.lp_now",String.format("%d",lp)));
         textList.add(textList.size(), Component.translatable("ctnh.lp_max",String.format("%d",max_lp)));
    }
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        var tier = getTier();
        max_lp=10000;
        max_lp=max_lp+10000*Math.max((tier-3),0);
        max_lp=max_lp+30000*Math.max((tier-5),0);
        max_lp=max_lp+2500*calculateRune();
        max_lp=max_lp+(10000*calculateRune()*Math.max((tier-5),0));

    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        int consume=recipe.data.getInt("addlp");
        if (consume > 0) {
            add_lp=consume;
        }
        if (lp==max_lp&&consume>0){
            return  false;
        }
        else{
            if(consume+lp<0)
            {
                return false;
            }
            else{
                lp=lp+consume;
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
    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        if (!forDrop) {
            tag.putInt(MAX_LP, max_lp);
            tag.putInt(LP,lp);
        }
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        max_lp = tag.contains(MAX_LP) ? tag.getInt(MAX_LP) : 20000;
        lp=tag.contains(LP)? tag.getInt(LP):0;
    }
}

