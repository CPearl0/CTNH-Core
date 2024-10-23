package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.OverlayTieredMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CircuitBusPartMachine;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.VLVH;
import static com.gregtechceu.gtceu.api.GTValues.VLVT;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHRecipeModifiers {

    public static final RecipeModifier GCYM_REDUCTION = (machine, recipe, params, result) -> CTNHRecipeModifiers
            .reduction(machine, recipe, 0.8, 0.6);

    public static final RecipeModifier COIL_PARALLEL = (machine, recipe, params, result) -> GTRecipeModifiers.accurateParallel(machine, recipe, Math.min(2147483647, (int) Math.pow(2, ((double) ((CoilWorkableElectricMultiblockMachine) machine).getCoilType().getCoilTemperature() / 900))), false).getFirst();

    public static GTRecipe chemicalPlantOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                                  @NotNull OCResult result) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            GTRecipe recipe1 = reduction(machine, recipe, (1.0 - coilMachine.getCoilTier() * 0.05) * 0.8, (1.0 - coilMachine.getCoilTier() * 0.05) * 0.6);
            recipe1 = GTRecipeModifiers.hatchParallel(machine, recipe1, false, params, result);
            if (recipe1 != null) return RecipeHelper.applyOverclock(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK, recipe1, coilMachine.getOverclockVoltage(), params, result);
        }
        return null;
    }

    private static GTRecipe reduction(MetaMachine machine, @NotNull GTRecipe recipe, double v, double v1) {
        return recipe;
    }


}
