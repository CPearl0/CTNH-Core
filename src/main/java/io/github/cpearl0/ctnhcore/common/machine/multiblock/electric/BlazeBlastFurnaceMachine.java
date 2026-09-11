package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.fluid.IFluidHandlerModifiable;
import com.gregtechceu.gtceu.common.machine.trait.multiblock.CoilMachineTrait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.List;

public class BlazeBlastFurnaceMachine extends CoilWorkableElectricMultiblockMachine {

    @CN("烈焰之炽焱：%d mB")
    @EN("Blazing Pyrotheum: %d mB")
    public static Lang blazeBlastFurnaceInfoPyrotheum;

    public BlazeBlastFurnaceMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            var tier = getTier();
            if (MachineUtils.inputFluids(this, CTNHMaterials.Pyrotheum.getFluid((int) (Math.pow(2, (tier - 2)) * 5)))) {
                return super.onWorking();
            } else {
                getRecipeLogic().setProgress(0);
            }
        }
        return super.onWorking();
    }

    @CN("烈焰之炽焱不足")
    @EN("Insufficient Pyrotheum")
    static Lang insufficient_pyrotheum;

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        var tier = getTier();
        if (MachineUtils.canInputFluids(this, CTNHMaterials.Pyrotheum.getFluid((int) (Math.pow(2, (tier - 2)) * 5)))) {
            return super.beforeWorking(recipe);
        }
        getRecipeLogic().interruptRecipe();
        return insufficient_pyrotheum.translate();
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        var fluidHandlers = getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP).stream()
                .filter(IFluidHandlerModifiable.class::isInstance)
                .map(IFluidHandlerModifiable.class::cast)
                .toArray(IFluidHandlerModifiable[]::new);

        var inputFluids = new CombinedTankWrapper(fluidHandlers);
        int current = 0;
        for (int i = 0; i < inputFluids.getTanks(); i++) {
            if (inputFluids.getFluidInTank(i).getFluid().isSame(CTNHMaterials.Pyrotheum.getFluid()))
                current += inputFluids.getFluidInTank(i).getAmount();

        }

        if (isFormed()) {
            textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                    Component.literal(getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K")
                            .withStyle(ChatFormatting.RED)));
            textList.add(blazeBlastFurnaceInfoPyrotheum.translate(current));
        }
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        int parallel = Math.max(1, ParallelLogic.getParallelAmount(group, recipe, 8));
        recipe.multiplyEUt(0.5);
        recipe.multiplyAllContents(parallel);
        recipe.parallels *= parallel;
        return null;
    }
}
