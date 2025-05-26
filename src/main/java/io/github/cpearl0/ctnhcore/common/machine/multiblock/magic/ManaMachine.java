package io.github.cpearl0.ctnhcore.common.machine.multiblock.magic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ManaMachine extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public int parallel;
    public int basicConsumption;
    @Persisted public int consumption=2;
    @Persisted public int power=0;
    public ManaMachine(IMachineBlockEntity holder, int Parallel, int consumption) {
        super(holder);
        this.parallel = Parallel;
        this.basicConsumption = consumption;
    }
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if (MachineUtils.inputFluid(CTNHMaterials.Mana.getFluid((int)consumption),this)) {
                return super.onWorking();
            }

            getRecipeLogic().setProgress(0);
        }
        return super.onWorking();
    }
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        var tier = getTier();
        consumption=(int)Math.pow(2,tier)*basicConsumption;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var tier = getTier();
        if (MachineUtils.canInputFluid(CTNHMaterials.Mana.getFluid((int) consumption),this)){
            return super.beforeWorking(recipe);
        }
        return false;
    }


    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof ManaMachine mmachine) {
            int parallel= ParallelLogic.getParallelAmount(machine,recipe,1);
            var amount=(1-Math.min(0.75,0.01*parallel));
            var tier=mmachine.tier;
            var recipe_tier= RecipeHelper.getRecipeEUtTier(recipe);
            if(tier<5&&tier<=recipe_tier)
            {
                if(recipe.getType().equals(GTRecipeTypes.ASSEMBLER_RECIPES))
                {
                    amount+=0.01;
                }
                else {
                    amount += 0.5;
                }
            }

            if(MachineUtils.canInputItem(new ItemStack(CTNHItems.QUASAR_RUNE),mmachine))
            {
                mmachine.power=100;
            }
            if(mmachine.power>0)
            {
                mmachine.power-=1;
                amount=1;
                parallel=ParallelLogic.getParallelAmount(machine,recipe,Integer.MAX_VALUE-10);
            }
            return  ModifierFunction.builder()
                    .parallels(parallel)
                    .eutMultiplier(parallel*amount)
                    .inputModifier(ContentModifier.multiplier(parallel))
                    .outputModifier(ContentModifier.multiplier(parallel))
                    .durationMultiplier(amount)
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }
}
