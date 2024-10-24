package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyTooltip;
import com.gregtechceu.gtceu.api.gui.fancy.TooltipsPanel;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
    private static final FluidStack LUBRICANT_STACK = GTMaterials.Lubricant.getFluid(1);

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
        this.tier = tier + 7;
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

    protected GTRecipe getLubricantRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(LUBRICANT_STACK).buildRawRecipe();
    }

    protected GTRecipe getBoostRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(PLASMA[tier - 8]).buildRawRecipe();
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                          @NotNull OCResult result) {
        if (machine instanceof NaqReactorMachine engineMachine) {
            var EUt = RecipeHelper.getOutputEUt(recipe);
            // has lubricant
            if (EUt > 0 && engineMachine.getLubricantRecipe().matchRecipe(engineMachine).isSuccess()) {
                var maxParallel = (int) (engineMachine.getOverclockVoltage() / EUt); // get maximum parallel
                var parallelResult = GTRecipeModifiers.fastParallel(engineMachine, recipe, maxParallel, false);
                if (engineMachine.isBoosted) { // boost production
                    long eut = (long) (EUt * parallelResult.getSecond() * (MULTIPLE_RATE[engineMachine.tier - 8]));
                    result.init(-eut, recipe.duration, 1, params.getOcAmount());
                } else {
                    long eut = (long) (EUt * parallelResult.getSecond());
                    result.init(-eut, recipe.duration, 1, params.getOcAmount());
                }
                return recipe;
            }
        }
        return null;
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        // check lubricant
        long totalContinuousRunningTime = recipeLogic.getTotalContinuousRunningTime();
        if ((totalContinuousRunningTime == 1 || totalContinuousRunningTime % 72 == 0)) {
            // insufficient lubricant
            if (!getLubricantRecipe().handleRecipeIO(IO.IN, this, this.recipeLogic.getChanceCaches())) {
                recipeLogic.interruptRecipe();
                return false;
            }
        }
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
                if (tier == GTValues.UHV) {
                    if (isBoosted) {
                        textList.add(Component.translatable("ctnh.multiblock.naq_reactor_machine.oxygen_plasma_boosted"));
                    } else {
                        textList.add(Component
                                .translatable("ctnh.multiblock.naq_reactor_machine.supply_oxygen_plasma_to_boost"));
                    }
                } else if(tier == GTValues.UEV) {
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