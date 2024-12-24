package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;
import java.util.Objects;

import static sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil.getWorldTemperature;

public class FermentingTankMachine extends CoilWorkableElectricMultiblockMachine {
    public double Machine_Temperature = 0;
    public double Efficiency = 1;
    public double Lower_limit = 0.2;
    public FermentingTankMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        Machine_Temperature = getWorldTemperature(Objects.requireNonNull(getLevel()),getPos());
        super.addDisplayText(textList);
        textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component
                .translatable(FormattingUtil.formatNumbers(getCoilType().getCoilTemperature() + 100L * Math.max(0, getTier() - GTValues.MV)) + "K")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
        textList.add(textList.size(), Component.translatable("ctnh.fermenting_tank.growing_temperature", String.format("%.1f",Machine_Temperature)).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
        textList.add(textList.size(), Component.translatable("ctnh.fermenting_tank.growth_efficiency", String.format("%.1f", Efficiency * 100)));
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe){
        if(machine instanceof FermentingTankMachine fmachine){
            fmachine.Efficiency = 1;
            fmachine.Machine_Temperature = getWorldTemperature(Objects.requireNonNull(fmachine.getLevel()),fmachine.getPos());
            fmachine.getParts().forEach((part) -> {
                if(part instanceof FluidHatchPartMachine fpart) {
                    part.getRecipeHandlers().forEach((trait) -> {
                        if (trait.getHandlerIO() == IO.IN && trait.getCapability() == FluidRecipeCapability.CAP) {
                            trait.getContents().forEach((contents) -> {
                                if (contents instanceof FluidStack fluid) {
                                    double current = fluid.getAmount();
                                    var total = FluidHatchPartMachine.getTankCapacity(FluidHatchPartMachine.INITIAL_TANK_CAPACITY_1X, part.self().getDefinition().getTier());
                                    double density = current / total;
                                    double logistic = 8 * (density - Math.pow(density, 2));
                                    logistic = Math.max(fmachine.Lower_limit, logistic);
                                    fmachine.Efficiency *= logistic;
                                }
                            });
                        }
                    });
                }
            });
            if (fmachine.Machine_Temperature >= 36 && fmachine.Machine_Temperature <= 38) {
                fmachine.Efficiency *= 1.2;
            }
            else {
                fmachine.Efficiency /= Math.min(3, Math.pow(Math.max(36 - fmachine.Machine_Temperature, fmachine.Machine_Temperature - 38), 2) / 10 + 1);
            }
            return ModifierFunction.builder().durationModifier(ContentModifier.multiplier(1/fmachine.Efficiency)).build();
        }
        return ModifierFunction.IDENTITY;
    }
}
