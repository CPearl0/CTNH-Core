package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BlazeBlastFurnaceMachine extends CoilWorkableElectricMultiblockMachine {
    public BlazeBlastFurnaceMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if (MachineUtils.inputFluid(CTNHMaterials.Pyrotheum.getFluid((int) (Math.pow(2, (tier - 2)) * 5)),this)) {
                return super.onWorking();
            }
            else{
                getRecipeLogic().setProgress(0);
            }
        }
        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        var tier = getTier();
        if (MachineUtils.canInputFluid(CTNHMaterials.Pyrotheum.getFluid((int) (Math.pow(2, (tier - 2)) * 5)),this)){
            return super.beforeWorking(recipe);
        }
        getRecipeLogic().interruptRecipe();
        return false;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        AtomicInteger current = new AtomicInteger();
        getParts().forEach((part) -> {
                part.getRecipeHandlers().forEach((trait) -> {
                    if (trait.getHandlerIO() == IO.IN) {
                        trait.getContents().forEach((contents) -> {
                        if (contents instanceof FluidStack FluidContents) {
                            if (FluidContents.getFluid().equals(CTNHMaterials.Pyrotheum.getFluid())) {
                                current.addAndGet(FluidContents.getAmount());
                            }
                        }
                        });
                    }
                });
        });
        if (isFormed()) {
            textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
            textList.add(Component.translatable("ctnh.blaze_blast_furnace.pyrotheum", current));
        }
    }

    public static GTRecipe recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result) {
        var parallel = 8;
        var newrecipe = recipe.copy();
        newrecipe.tickInputs.put(EURecipeCapability.CAP, newrecipe.copyContents(newrecipe.tickInputs, ContentModifier.of(0.75, 0)).get(EURecipeCapability.CAP));
        newrecipe = GTRecipeModifiers.accurateParallel(machine, newrecipe, parallel, false).getFirst();
        return GTRecipeModifiers.ebfOverclock(machine, newrecipe, params, result);
    }
}
