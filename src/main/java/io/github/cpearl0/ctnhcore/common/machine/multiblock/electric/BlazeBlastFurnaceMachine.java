package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
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

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        int parallel = 8;
        var reduce = new ContentModifier(0.75 * parallel,0);
        return ModifierFunction.builder()
                .eutModifier(reduce)
                .inputModifier(ContentModifier.multiplier(parallel))
                .outputModifier(ContentModifier.multiplier(parallel))
                .parallels(8)
                .build();
    }
}
