package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.block.MaterialBlock;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import dev.arbor.gtnn.data.GTNNRecipeTypes;
import dev.arbor.gtnn.data.GTNNRecipes;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.NanoscaleTriboelectricGenerator;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Objects;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class Sinope_Chemical extends CoilWorkableElectricMultiblockMachine implements ITieredMachine {
public int parallel=0;
public  int machine_tier=0;
    public Sinope_Chemical(IMachineBlockEntity holder){
        super(holder);
        var tier = getTier();


    }
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        var tier = getTier();
        var coil_tier=getCoilTier();
        var coil_type=getCoilType().getCoilTemperature();
        machine_tier=coil_type/1800;
        var blockpos= MachineUtils.getOffset(this,0,2,2);
        var coreblock = Objects.requireNonNull(getLevel()).getBlockState(blockpos).getBlock();
        if(coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block,Naquadah).get()))
        {
            parallel=8;
        }
        if(coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block,NaquadahEnriched).get()))
        {
            parallel=32;
        }
        if(coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block,Naquadria).get()))
        {
            parallel=128;
        }
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof Sinope_Chemical zmachine){
            var maxparallel= ParallelLogic.getParallelAmount(machine,recipe,zmachine.parallel);
            if(maxparallel==0)
                return ModifierFunction.NULL;
            var reduce=maxparallel*Math.max(1-0.005*maxparallel,0.75);
            var speed_up=zmachine.machine_tier*2;
            return ModifierFunction.builder()
                    .eutModifier(ContentModifier.multiplier((reduce)))
                    .inputModifier(ContentModifier.multiplier(maxparallel))
                    .outputModifier(ContentModifier.multiplier(maxparallel))
                    .parallels(maxparallel)
                    .durationMultiplier(reduce/speed_up)
                    .build();
        }
        return ModifierFunction.NULL;

    }
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();

        textList.add(textList.size(), Component.translatable("ctnh.sinope.level",String.format("%d",machine_tier*2)));
        textList.add(textList.size(), Component.translatable("ctnh.sinope.parallel",String.format("%d",parallel)));
    }

}
