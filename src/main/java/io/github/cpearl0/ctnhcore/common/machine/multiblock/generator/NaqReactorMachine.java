package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;



@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NaqReactorMachine extends WorkableElectricMultiblockMachine implements ITieredMachine {

    private static final FluidStack OXYGEN_PLASMA_STACK = GTMaterials.Oxygen.getFluid(FluidStorageKeys.PLASMA,20);
    private static final FluidStack IRON_PLASMA_STACK = GTMaterials.Iron.getFluid(FluidStorageKeys.PLASMA, 20);
    private static final FluidStack NICKEL_PLASMA_STACK = GTMaterials.Nickel.getFluid(FluidStorageKeys.PLASMA,20);

    //依次填入增加效率的等离子体
    private static final FluidStack[] PLASMA = {OXYGEN_PLASMA_STACK,IRON_PLASMA_STACK,NICKEL_PLASMA_STACK};
    //依次填入增强发电的倍率
    private static final int[] MULTIPLE_RATE = {2,4,6} ;
    @Getter
    private final int tier;
    // runtime
    private boolean isBoosted = false;

    public NaqReactorMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier + 9;
    }

    public boolean isBoostAllowed() {
        return getMaxVoltage() >= GTValues.V[getTier() + 1];
    }

    //////////////////////////////////////
    // ****** Recipe Logic *******//
    //////////////////////////////////////

    @Override
    public long getOverclockVoltage() {
        if (isBoosted)
            return GTValues.V[tier] * 4;
        else
            return GTValues.V[tier];
    }

    protected GTRecipe getBoostRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(PLASMA[tier - 10]).buildRawRecipe();
    }

    @Nullable
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof NaqReactorMachine engineMachine) {
            var EUt = RecipeHelper.getOutputEUt(recipe);
            // has lubricant
            if (EUt > 0) {
                var maxParallel = (int) (engineMachine.getOverclockVoltage() / EUt); // get maximum parallel
                int actualParallel = ParallelLogic.getParallelAmount(engineMachine, recipe, maxParallel);
                double eutMultiplier = actualParallel * (engineMachine.isBoosted? MULTIPLE_RATE[engineMachine.tier - 10] : 1);
                return ModifierFunction.builder()
                        .inputModifier(ContentModifier.multiplier(actualParallel))
                        .outputModifier(ContentModifier.multiplier(actualParallel))
                        .eutMultiplier(eutMultiplier)
                        .parallels(actualParallel)
                        .build();
            }
        }
        return ModifierFunction.NULL;
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        long totalContinuousRunningTime = recipeLogic.getTotalContinuousRunningTime();
        // check boost fluid
        if ((totalContinuousRunningTime == 1 || totalContinuousRunningTime % 20 == 0) && isBoostAllowed()) {
            var boosterRecipe = getBoostRecipe();
            this.isBoosted = boosterRecipe.matchRecipe(this).isSuccess() &&
                    boosterRecipe.handleRecipeIO(IO.IN, this, this.recipeLogic.getChanceCaches());
        }
        return value;
    }

    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }

    //////////////////////////////////////
    // ******* GUI ********//
    //////////////////////////////////////

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            if (isBoostAllowed()) {
                if (tier == GTValues.UEV) {
                    if (isBoosted) {
                        textList.add(Component.translatable("ctnh.multiblock.naq_reactor_machine.oxygen_plasma_boosted"));
                    } else {
                        textList.add(Component
                                .translatable("ctnh.multiblock.naq_reactor_machine.supply_oxygen_plasma_to_boost"));
                    }
                } else if(tier == GTValues.UIV) {
                    if (isBoosted) {
                        textList.add(Component
                                .translatable("ctnh.multiblock.naq_reactor_machine.iron_plasma_boosted"));
                    } else {
                        textList.add(Component.translatable(
                                "ctnh.multiblock.naq_reactor_machine.supply_iron_plasma_to_boost"));
                    }
                }
                else {
                    if (isBoosted) {
                        textList.add(Component
                                .translatable("ctnh.multiblock.naq_reactor_machine.nickel_plasma_boosted"));
                    } else {
                        textList.add(Component.translatable(
                                "ctnh.multiblock.naq_reactor_machine.supply_nickel_plasma_to_boost"));
                    }
                }
            } else {
                textList.add(Component.translatable("ctnh.multiblock.naq_reactor_machine.boost_disallowed"));
            }
        }
    }
}