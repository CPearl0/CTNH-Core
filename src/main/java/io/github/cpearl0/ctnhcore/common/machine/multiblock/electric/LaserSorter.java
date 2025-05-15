package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.CWURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableComputationContainer;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.integration.jade.provider.ElectricContainerBlockProvider;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.api.machine.computation.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.api.machine.computation.trait.MultiblockComputationLogic;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.HyperPlasmaTurbineMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import lombok.Getter;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static com.gregtechceu.gtceu.api.GTValues.*;

public class LaserSorter extends MultiblockComputationMachine implements ITieredMachine,IOpticalComputationReceiver{
    int lastCWUt;
    private IOpticalComputationProvider computationProvider;
    public LaserSorter(IMachineBlockEntity holder) {
        super(holder);
        computationProvider=getComputationProvider();
    }
    @Override
    @NotNull
    public RecipeLogic createRecipeLogic(Object... args) {
        return new LaserSorter.LaserSorterRecipeLogic(this);
    }
    @Override
    @NotNull
    public MultiblockComputationLogic getRecipeLogic() {
        return (LaserSorter.LaserSorterRecipeLogic) recipeLogic;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var b=getMaxCWUt();
        var c=getLastCWUt();
        var provider = getComputationProvider();
        return super.beforeWorking(recipe);
    }
    public int caculate_effency(@Nullable GTRecipe recipe)
    {
        if(recipe.data.contains("cwut"))
        {
            var base=recipe.data.getInt("cwut");
            var maxcwut=getMaxCWUt();
            return (int)maxcwut/base;
        }
        var base=Math.max(8*(GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe))-IV),8);
        var maxcwut=getMaxCWUt();
        return (int)maxcwut/base;
    }
    public boolean check_right(@Nullable GTRecipe recipe)
    {
        if(recipe.data.contains("cwut"))
        {
            var base=recipe.data.getInt("cwut");
            var maxcwut=getMaxCWUt();
            var truecwut=((int)maxcwut/base)*base;
            return maxcwut==truecwut;
        }
        var base=Math.max(8*(GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe))-IV),8);
        var maxcwut=getMaxCWUt();
        var truecwut=((int)maxcwut/base)*base;
        return maxcwut==truecwut;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof LaserSorter lmachine) {
            var input=1.0;
            var muti=0.25;
            var parallel=1;
            if(lmachine.check_right(recipe))
            {
                muti=lmachine.caculate_effency(recipe);
                parallel=(int)(muti*muti*muti);
                if(recipe.recipeType.equals(GTRecipeTypes.LASER_ENGRAVER_RECIPES))
                {
                    input=1.25;
                }
            }
            var maxparallel=ParallelLogic.getParallelAmount(lmachine,recipe,parallel);
            int allow_overload=RecipeHelper.getRecipeEUtTier(recipe)-lmachine.getTier();
            return ModifierFunction.builder()
                    .parallels(maxparallel)
                    .inputModifier(ContentModifier.multiplier(maxparallel))
                    .outputModifier(ContentModifier.multiplier((int)maxparallel*input))
                    .durationMultiplier(1/(Math.pow(2,Math.min(allow_overload,muti))))
                    .build();
        }
        return ModifierFunction.NULL;
    }
    private static class LaserSorterRecipeLogic  extends MultiblockComputationLogic {
        public LaserSorterRecipeLogic(MultiblockComputationMachine machine) {
            super(machine);
        }
        @Override
        public int getCWUtToRequest(GTRecipe recipe) {
            if(recipe.data.contains("cwut"))
            {
                var base=recipe.data.getInt("cwut");
                var maxcwut=computationContainer.getMaxCWUt();
                var truecwut=((int)maxcwut/base)*base;
                return truecwut;
            }
            var base=Math.max(8*(GTUtil.getTierByVoltage(RecipeHelper.getInputEUt(recipe))-IV),8);
            var maxcwut=computationContainer.getMaxCWUt();
            var truecwut=((int)maxcwut/base)*base;
            return truecwut;
        }
    }



}
