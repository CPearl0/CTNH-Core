package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import io.github.cpearl0.ctnhcore.api.machine.computation.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.MultiblockComputationLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gregtechceu.gtceu.api.GTValues.IV;

public class NeuroMatrixCompiler extends MultiblockComputationMachine implements ITieredMachine, IOpticalComputationReceiver {
    public NeuroMatrixCompiler(IMachineBlockEntity holder) {
        super(holder);
    }
    @Persisted public GTRecipe lastrecipe;
    @Persisted public List<Long> Equation=new ArrayList<Long>();
    @Persisted public List<Long> RawEquation;
    @Persisted public long Noise=1;
    @Persisted public long range;
    public List<Long> getRawEquation(int num, long Noise)
    {
        List<Long>Equation=new ArrayList<Long>();
        for(int i=1;i<num;i++)
        {
            Equation.add((long)(Math.random()*(range-Math.sqrt(range))+Math.sqrt(range)));
        }
        Equation.add((long)(Math.random()*(range*range)*Noise));
        return Equation;
    }
    public Long GetAnswer()
    {
        long answer=0;
        List<Long> Equationer=new ArrayList<Long>();
        Equationer.add(3L);
        Equationer.add(2L);
        for(int i=0;i<RawEquation.size()-1;i++)
        {
            answer=RawEquation.get(i)*Equationer.get(i);
        }
        answer+=(long)(Math.random()*(range*range)*Noise);
        return answer;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        var itemsR = recipe.getInputContents(ItemRecipeCapability.CAP).stream()
                .map(num -> (SizedIngredient) num.getContent()) // 转换为 SizedIngredient
                .map(SizedIngredient::getItems) // 获取 ItemStack 数组
                .filter(itemStacks -> itemStacks.length > 0) // 确保至少有一个 ItemStack
                .map(itemStacks -> itemStacks[0].getItem()) // 获取第一个 ItemStack 的 Item
                .collect(Collectors.toList()); // 收集到一个 List<Item>
        int parallel = ParallelLogic.getParallelAmount(machine,recipe,8);
        var reduce = new ContentModifier(0.5 * parallel,0);
        if(parallel==0)
            return ModifierFunction.NULL;
        return ModifierFunction.builder()
                .eutModifier(reduce)
                .inputModifier(ContentModifier.multiplier(parallel))
                .outputModifier(ContentModifier.multiplier(parallel))
                .parallels(parallel)
                .build();
    }
    private static class MatrixCompilerRecipeLogic  extends MultiblockComputationLogic {
        public MatrixCompilerRecipeLogic(MultiblockComputationMachine machine) {
            super(machine);
        }
        @Override
        public int getCWUtToRequest(GTRecipe recipe) {
            return 2;
        }
    }


}
