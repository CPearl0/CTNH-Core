package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.machine.trait.multiblock.CoilMachineTrait;

import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.List;
import java.util.Objects;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class SinopeChemical extends CoilWorkableElectricMultiblockMachine implements ITieredMachine {

    @CN("线圈加速倍率:%d")
    @EN("Coil Accelerating Rate: %d")
    public static Lang sinopeChemicalInfoLevel;

    @CN("并行数:%d")
    @EN("Parallel Count: %d")
    public static Lang sinopeChemicalInfoParallel;

    @CN("线圈温度不足：无法计算加速等级（至少需要 1800K 的线圈）")
    @EN("Coil temperature too low: cannot compute the acceleration tier (requires a coil of at least 1800K)")
    public static Lang coilTierTooLow;
    public int parallel = 0;
    public int machine_tier = 0;

    public SinopeChemical(IMachineBlockEntity holder) {
        super(holder);
        var tier = getTier();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        var tier = getTier();
        var coil_type = getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature();
        machine_tier = coil_type / 1800;
        var blockpos = MachineUtils.getOffset(this, 0, 2, 2);
        var coreblock = Objects.requireNonNull(getLevel()).getBlockState(blockpos).getBlock();
        if (coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block, Naquadah).get())) {
            parallel = 8;
        }
        if (coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block, NaquadahEnriched).get())) {
            parallel = 32;
        }
        if (coreblock.equals(GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block, Naquadria).get())) {
            parallel = 128;
        }
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof SinopeChemical zmachine) {
            if (zmachine.machine_tier <= 0) return coilTierTooLow.translate();
            var maxparallel = ParallelLogic.getParallelAmount(group, recipe, zmachine.parallel);
            if (maxparallel == 0) return null;
            var reduce = Math.max(1 - 0.005 * maxparallel, 0.75);
            var speed_up = reduce / (zmachine.machine_tier);
            recipe.multiplyEUt(reduce);
            recipe.multiplyAllContents(maxparallel);
            recipe.multiplyDuration(speed_up);
            recipe.parallels *= maxparallel;
            return null;
        }
        return RecipeModifier.nullWrongType(SinopeChemical.class, machine);
    }

    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        var tier = getTier();

        textList.add(textList.size(), sinopeChemicalInfoLevel.translate(
                String.format("%d", machine_tier * 2)));
        textList.add(textList.size(),
                sinopeChemicalInfoParallel.translate(String.format("%d", parallel)));
    }
}
