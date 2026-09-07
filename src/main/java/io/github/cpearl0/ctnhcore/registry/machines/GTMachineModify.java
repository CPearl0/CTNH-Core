package io.github.cpearl0.ctnhcore.registry.machines;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.MultiblockComputationMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.machines.multiblock.MultiblocksA;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.CleanroomMachine;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import com.ctnh.ctnhastral.registry.CARecipeModifiers;
import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Prefix;
import com.ctnhlang.Suffix;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.api.pattern.Predicates.controller;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_LARGE_SCALE_ASSEMBLING;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_TEMPERED_GLASS;
import static com.gregtechceu.gtceu.common.data.machines.GTMultiMachines.*;
import static io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips.PERFECT_OVERCLOCK;

@Prefix("multiblock")
@Suffix("tooltip")
public class GTMachineModify {

    @CN("配方耗时x0.8，配方耗能x0.6")
    @EN("Recipe time ×0.8, recipe energy ×0.6")
    public static Lang gcymReduction;

    public static BiConsumer<ItemStack, List<Component>> REDUCTION_INFO = (itemStack, list) -> list
            .add(gcymReduction.translate().withStyle(ChatFormatting.GREEN));

    public static void init() {
        List<MachineDefinition> gcymMachinesToModify = Arrays.asList(
                GCYMMachines.LARGE_ARC_SMELTER,
                GCYMMachines.LARGE_AUTOCLAVE,
                GCYMMachines.LARGE_BREWER,
                GCYMMachines.LARGE_CENTRIFUGE,
                GCYMMachines.LARGE_CHEMICAL_BATH,
                GCYMMachines.LARGE_CUTTER,
                GCYMMachines.LARGE_CIRCUIT_ASSEMBLER,
                GCYMMachines.LARGE_DISTILLERY,
                GCYMMachines.LARGE_ELECTROLYZER,
                GCYMMachines.LARGE_ELECTROMAGNET,
                GCYMMachines.LARGE_EXTRACTOR,
                GCYMMachines.LARGE_EXTRUDER,
                GCYMMachines.LARGE_ENGRAVING_LASER,
                GCYMMachines.LARGE_MACERATION_TOWER,
                GCYMMachines.LARGE_MIXER,
                GCYMMachines.LARGE_MATERIAL_PRESS,
                GCYMMachines.LARGE_PACKER,
                GCYMMachines.LARGE_SOLIDIFIER,
                GCYMMachines.LARGE_SIFTING_FUNNEL,
                GCYMMachines.LARGE_WIREMILL,
                GCYMMachines.MEGA_BLAST_FURNACE,
                GCYMMachines.MEGA_VACUUM_FREEZER);
        RecipeModifier[] commonModifiers = {
                CTNHRecipeModifiers.GCYM_REDUCTION,
                GTRecipeModifiers.PARALLEL_HATCH,
                GTRecipeModifiers.OC_NON_PERFECT,
                GTRecipeModifiers.BATCH_MODE };
        for (MachineDefinition machine : gcymMachinesToModify) {
            machine.setRecipeModifiers(commonModifiers);
            machine.setTooltipBuilder(machine.getTooltipBuilder().andThen(REDUCTION_INFO));
        }
        LARGE_CHEMICAL_REACTOR.setTooltipBuilder(
                LARGE_CHEMICAL_REACTOR.getTooltipBuilder().andThen(
                        (stack, tooltip) -> {
                            tooltip.add(PERFECT_OVERCLOCK);
                        }));
        modifyGTAssembly();
        modifyCleanroom();
        modifyOxygenGenerators();

        PRIMITIVE_BLAST_FURNACE.setRecipeTypes(new GTRecipeType[] { GTRecipeTypes.DUMMY_RECIPES });
    }

    private static void modifyOxygenGenerators() {
        appendRecipeModifier(GTMachines.COMBUSTION, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(GTMachines.GAS_TURBINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(GTMachines.STEAM_TURBINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(LARGE_COMBUSTION_ENGINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(EXTREME_COMBUSTION_ENGINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(LARGE_GAS_TURBINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(LARGE_STEAM_TURBINE, CARecipeModifiers::oxygenRequirement);
        appendRecipeModifier(MultiblocksA.ULTIMATE_COMBUSTION_ENGINE, CARecipeModifiers::oxygenRequirement);
    }

    private static void appendRecipeModifier(MachineDefinition[] machines, RecipeModifier recipeModifier) {
        for (MachineDefinition machine : machines) {
            if (machine == null) continue;
            appendRecipeModifier(machine, recipeModifier);
        }
    }

    private static void appendRecipeModifier(MachineDefinition machine, RecipeModifier recipeModifier) {
        RecipeModifier[] modifiers = machine.getRecipeModifiers();
        RecipeModifier[] appended = Arrays.copyOf(modifiers, modifiers.length + 1);
        appended[modifiers.length] = recipeModifier;
        machine.setRecipeModifiers(appended);
    }

    @CN({
            "其他可用配方类型: 精密组装",
            "注意：在精密组装模式下无法并行"
    })
    @EN({
            "Other available recipe type: Precision Assembly",
            "NOTE: Parallelization is not possible in precision assembly mode"
    })
    static Lang[] precision_assembly;

    private static void modifyGTAssembly() {
        var lASB = GCYMMachines.LARGE_ASSEMBLER;
        lASB.setMachineSupplier(MultiblockComputationMachine::new);
        var lASBRecipeTypes = new ArrayList<>(Arrays.stream(lASB.getRecipeTypes()).toList());
        lASBRecipeTypes.add(CTNHRecipeTypes.PRECISION_ASSEMBLY_RECIPES);
        lASB.setRecipeTypes(lASBRecipeTypes.toArray(GTRecipeType[]::new));
        lASB.setTooltipBuilder(lASB.getTooltipBuilder().andThen((itemStack, components) -> {
            components.add(gcymReduction.translate().withStyle(ChatFormatting.GREEN));
            components.add(precision_assembly[0].translate());
            components.add(precision_assembly[1].translate());
        }));
        // lASB.setMachineSupplier(MultiblockComputationMachine::new);
        lASB.setPatternFactory(() -> FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "XXXXXXXXX", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XAAAXAAAX", "XGGGXXXXX")
                .aisle("XXXXXXXXX", "XGGGXXSXX", "XGGGX###X")
                .where('S', controller(blocks(lASB.get())))
                .where('X', blocks(CASING_LARGE_SCALE_ASSEMBLING.get()).setMinGlobalLimited(40)
                        .or(Predicates.autoAbilities(lASB.getRecipeTypes(), false, false, true, true, true,
                                true))
                        .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                        .or(Predicates.autoAbilities(true, false, true))
                        .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setMaxGlobalLimited(1)
                                .setPreviewCount(1)))
                .where('G', Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                .where('A', Predicates.air())
                .where('#', Predicates.any())
                .build());

        lASB.setRecipeModifiers(new RecipeModifier[] {
                CTNHRecipeModifiers.GCYM_REDUCTION,
                GTMachineModify::assemblyRecipeModifier,
                GTRecipeModifiers.BATCH_MODE });
    }

    private static Component assemblyRecipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe gtRecipe) {
        if (gtRecipe.recipeType == CTNHRecipeTypes.PRECISION_ASSEMBLY_RECIPES) {
            return GTRecipeModifiers.OC_NON_PERFECT.apply(machine, group, gtRecipe);
        } else {
            Component failure = GTRecipeModifiers.PARALLEL_HATCH.apply(machine, group, gtRecipe);
            return failure != null ? failure : GTRecipeModifiers.OC_NON_PERFECT.apply(machine, group, gtRecipe);
        }
    }

    @CN("高度大于长度或宽度时，将会停止工作")
    @EN("Stop working if height is greater than length or wight")
    static Lang cleanroom_restriction;

    private static void modifyCleanroom() {
        CLEANROOM.setBeforeWorking(
                (machine, recipe) -> {
                    if (machine instanceof CleanroomMachine cleanroom) {
                        int lDist = cleanroom.getLDist();
                        int rDist = cleanroom.getRDist();
                        int bDist = cleanroom.getBDist();
                        int fDist = cleanroom.getFDist();
                        int hDist = cleanroom.getHDist();
                        int length = lDist + rDist;
                        int width = bDist + fDist;
                        if (hDist <= length && hDist <= width) {
                            return null;
                        } else {
                            return cleanroom_restriction.translate();
                        }
                    }
                    return null;
                });
        CLEANROOM.setTooltipBuilder(CLEANROOM.getTooltipBuilder().andThen(
                (stack, tooltip) -> {
                    tooltip.add(cleanroom_restriction.translate());
                }));
    }
}
