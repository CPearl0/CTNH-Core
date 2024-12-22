package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import java.util.List;


public class ChemicalGeneratorMachine extends WorkableElectricMultiblockMachine {
    private static final FluidStack OXYGEN_STACK = GTMaterials.Oxygen.getFluid((int) (20 * FluidHelper.getBucket() / 1000));
    private static final FluidStack LIQUID_OXYGEN_STACK = GTMaterials.Oxygen.getFluid(FluidStorageKeys.LIQUID,
            (int)(80 * FluidHelper.getBucket() / 1000));

    @Getter
    private final int tier;
    private boolean isOxygenBoosted = false;

    public ChemicalGeneratorMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier;
    }

    private boolean isExtreme() {
        return getTier() > GTValues.EV;
    }

    public boolean isBoostAllowed() {
        return getMaxVoltage() >= GTValues.V[getTier() + 1];
    }

    @Override
    public long getOverclockVoltage() {
        if (isOxygenBoosted)
            return GTValues.V[tier] * 2;
        else
            return GTValues.V[tier];
    }

    protected GTRecipe getBoostRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(isExtreme() ? LIQUID_OXYGEN_STACK : OXYGEN_STACK).buildRawRecipe();
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                          @NotNull OCResult result) {
        if (machine instanceof ChemicalGeneratorMachine engineMachine) {
            var EUt = RecipeHelper.getOutputEUt(recipe);
            // has lubricant
            if (EUt > 0) {
                var maxParallel = (int) (engineMachine.getOverclockVoltage() / EUt); // get maximum parallel
                var parallelResult = GTRecipeModifiers.fastParallel(engineMachine, recipe, maxParallel, false);
                if (engineMachine.isOxygenBoosted) { // boost production
                    long eut = (long) (EUt * parallelResult.getSecond() * (engineMachine.isExtreme() ? 2 : 1.5));
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
        var totalContinuousRunningTime = recipeLogic.getTotalContinuousRunningTime();
        if ((totalContinuousRunningTime == 1 || totalContinuousRunningTime % 20 == 0) && isBoostAllowed()) {
            var boosterRecipe = getBoostRecipe();
            this.isOxygenBoosted = boosterRecipe.matchRecipe(this).isSuccess() &&
                    boosterRecipe.handleRecipeIO(IO.IN, this, this.recipeLogic.getChanceCaches());
        }
        return value;
    }

    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            if (isBoostAllowed()) {
                if (!isExtreme()) {
                    if (isOxygenBoosted) {
                        textList.add(Component.translatable("gtceu.multiblock.large_combustion_engine.oxygen_boosted"));
                    } else {
                        textList.add(Component
                                .translatable("gtceu.multiblock.large_combustion_engine.supply_oxygen_to_boost"));
                    }
                } else {
                    if (isOxygenBoosted) {
                        textList.add(Component
                                .translatable("gtceu.multiblock.large_combustion_engine.liquid_oxygen_boosted"));
                    } else {
                        textList.add(Component.translatable(
                                "gtceu.multiblock.large_combustion_engine.supply_liquid_oxygen_to_boost"));
                    }
                }
            } else {
                textList.add(Component.translatable("gtceu.multiblock.large_combustion_engine.boost_disallowed"));
            }
        }
    }
}
