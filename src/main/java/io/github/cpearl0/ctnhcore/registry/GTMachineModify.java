package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public class GTMachineModify {

    public static BiConsumer<ItemStack, List<Component>> REDUCTION_INFO = (itemStack, list) -> list.add(Component.translatable("ctnh.gcym.reduction").withStyle(ChatFormatting.GREEN));
    public static void init() {
        GCYMMachines.LARGE_ARC_SMELTER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_ARC_SMELTER.setTooltipBuilder(GCYMMachines.LARGE_ARC_SMELTER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_ASSEMBLER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_ASSEMBLER.setTooltipBuilder(GCYMMachines.LARGE_ASSEMBLER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_AUTOCLAVE.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_AUTOCLAVE.setTooltipBuilder(GCYMMachines.LARGE_AUTOCLAVE.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_BREWER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_BREWER.setTooltipBuilder(GCYMMachines.LARGE_BREWER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_CENTRIFUGE.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_CENTRIFUGE.setTooltipBuilder(GCYMMachines.LARGE_CENTRIFUGE.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_CHEMICAL_BATH.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_CHEMICAL_BATH.setTooltipBuilder(GCYMMachines.LARGE_CHEMICAL_BATH.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_CUTTER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_CUTTER.setTooltipBuilder(GCYMMachines.LARGE_CUTTER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_CIRCUIT_ASSEMBLER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_CIRCUIT_ASSEMBLER.setTooltipBuilder(GCYMMachines.LARGE_CIRCUIT_ASSEMBLER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_DISTILLERY.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_DISTILLERY.setTooltipBuilder(GCYMMachines.LARGE_DISTILLERY.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_ELECTROLYZER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_ELECTROLYZER.setTooltipBuilder(GCYMMachines.LARGE_ELECTROLYZER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_ELECTROMAGNET.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_ELECTROMAGNET.setTooltipBuilder(GCYMMachines.LARGE_ELECTROMAGNET.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_EXTRACTOR.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_EXTRACTOR.setTooltipBuilder(GCYMMachines.LARGE_EXTRACTOR.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_EXTRUDER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_EXTRUDER.setTooltipBuilder(GCYMMachines.LARGE_EXTRUDER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_ENGRAVING_LASER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_ENGRAVING_LASER.setTooltipBuilder(GCYMMachines.LARGE_ENGRAVING_LASER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_MACERATION_TOWER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_MACERATION_TOWER.setTooltipBuilder(GCYMMachines.LARGE_MACERATION_TOWER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_MIXER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_MIXER.setTooltipBuilder(GCYMMachines.LARGE_MIXER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_MATERIAL_PRESS.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_MATERIAL_PRESS.setTooltipBuilder(GCYMMachines.LARGE_MATERIAL_PRESS.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_PACKER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_PACKER.setTooltipBuilder(GCYMMachines.LARGE_PACKER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_SOLIDIFIER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_SOLIDIFIER.setTooltipBuilder(GCYMMachines.LARGE_SOLIDIFIER.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_SIFTING_FUNNEL.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_SIFTING_FUNNEL.setTooltipBuilder(GCYMMachines.LARGE_SIFTING_FUNNEL.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.LARGE_WIREMILL.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.LARGE_WIREMILL.setTooltipBuilder(GCYMMachines.LARGE_WIREMILL.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.MEGA_BLAST_FURNACE.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.MEGA_BLAST_FURNACE.setTooltipBuilder(GCYMMachines.MEGA_BLAST_FURNACE.getTooltipBuilder().andThen(REDUCTION_INFO));
        GCYMMachines.MEGA_VACUUM_FREEZER.setRecipeModifier(new RecipeModifierList(CTNHRecipeModifiers.GCYM_REDUCTION,GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT_SUBTICK));
        GCYMMachines.MEGA_VACUUM_FREEZER.setTooltipBuilder(GCYMMachines.MEGA_VACUUM_FREEZER.getTooltipBuilder().andThen(REDUCTION_INFO));
    }
}
