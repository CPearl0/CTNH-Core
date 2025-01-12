package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZenithMachine extends WorkableElectricMultiblockMachine {
    public int parallel;
    public int basicConsumption;
    public int zenithconsumption;
    public int maxparallel;
    public int basic_parallel;
    public ZenithMachine(IMachineBlockEntity holder, int Parallel, int consumption,int zconsumption,int maxparallel) {
        super(holder);
        this.parallel = Parallel;
        this.basic_parallel=parallel;
        this.basicConsumption = consumption;
        this.zenithconsumption=zconsumption;
        this. maxparallel=maxparallel;
    }
    public void addDisplayText(List<Component> textList) {
        var tier = getTier();
        super.addDisplayText(textList);
        textList.add(textList.size(), Component.translatable("ctnh.zenith_now_parallel",String.format("%d",parallel)));
        textList.add(textList.size(), Component.translatable("ctnh.zenith_max_parallel",String.format("%d",basic_parallel+(maxparallel*(Math.max(tier-6,0))))));
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if (MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid((int) (Math.pow(2, tier) * basicConsumption)),this)) {
                return super.onWorking();
            }
            getRecipeLogic().setProgress(0);
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var tier = getTier();

        if (MachineUtils.canInputFluid(CTNHMaterials.Mana.getFluid((int) (Math.pow(2, tier) * basicConsumption)),this)){
            if (MachineUtils.canInputFluid(CTNHMaterials.Zenith_essence.getFluid((int) (2 * zenithconsumption)),this)){
                if(parallel<basic_parallel+(maxparallel*(Math.max(tier-6,0)))) {
                    parallel += (int) Math.pow(2,(Math.max(tier - 5, 0)));
                }
                else {
                    parallel=basic_parallel+(maxparallel*(Math.max(tier-6,0)));

                }
            }
            else{
                parallel-=4;
            }
            return super.beforeWorking(recipe);
        }
        return false;
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof ZenithMachine zmachine) {
            return CTNHRecipeModifiers.accurateParallel(machine,recipe, zmachine.parallel);
        }
        return ModifierFunction.IDENTITY;
    }
}
