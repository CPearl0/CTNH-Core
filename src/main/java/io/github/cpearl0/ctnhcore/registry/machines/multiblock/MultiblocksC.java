package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.multithread.CNCAlloySmelter;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.impl.GrowingPlantRender;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.machine.trait.multiblock.CoilMachineTrait;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.Blocks;

import appeng.core.definitions.AEBlocks;
import com.ctnhlang.CN;
import com.ctnhlang.EN;
import org.joml.Vector3f;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.List;

import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.api.pattern.Predicates.heatingCoils;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING;
import static com.gregtechceu.gtceu.common.data.GCYMRecipeTypes.ALLOY_BLAST_RECIPES;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

// spotless:off
public class MultiblocksC {

    @CN("室内种植")
    @EN("Plant In Room")
    public static Lang greenhouseTooltip0;


    public static void init() {}

    public static final MultiblockMachineDefinition GREENHOUSE = REGISTRATE.multiblock("greenhouse", RecipeElectricMultiblockMachine::new)
            .cnLangValue("温室")
            .allowExtendedFacing(false)
            .recipeType(CTNHRecipeTypes.GREENHOUSE_RECIPES)
            .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT, GTRecipeModifiers.BATCH_MODE)
            .tooltips(greenhouseTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "#BBB#", "#BBB#", "##B##", "#####")
                    .aisle("ACCCA", "B###B", "B###B", "B###B", "B###B", "#BBB#", "##A##")
                    .aisle("ACCCA", "B###B", "B###B", "B###B", "B###B", "BBDBB", "#AAA#")
                    .aisle("ACCCA", "B###B", "B###B", "B###B", "B###B", "#BBB#", "##A##")
                    .aisle("AA@AA", "ABBBA", "ABBBA", "#BBB#", "#BBB#", "##B##", "#####")
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(Blocks.GLOWSTONE))
                    .where("B", Predicates.blocks(AEBlocks.QUARTZ_GLASS.block()))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("C", Predicates.blocks(Blocks.GRASS_BLOCK))
                    .build())
            .modelProperty(GTMachineModelProperties.RECIPE_LOGIC_STATUS, RecipeLogic.Status.IDLE)
            .model(createWorkableCasingMachineModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
                    .andThen(b -> b.addDynamicRenderer(() -> new GrowingPlantRender(List.of(
                            new Vector3f(-1, 1, -1),
                            new Vector3f(-1, 1, -2),
                            new Vector3f(-1, 1, -3),
                            new Vector3f(0, 1, -1),
                            new Vector3f(0, 1, -2),
                            new Vector3f(0, 1, -3),
                            new Vector3f(1, 1, -1),
                            new Vector3f(1, 1, -2),
                            new Vector3f(1, 1, -3)
                    ))))
            )
            .register();


    public static final MultiblockMachineDefinition CNC_ALLOY_SMELTER = REGISTRATE
            .multiblock("cnc_alloy_smelter", CNCAlloySmelter::new)
            .cnLangValue("数控合金冶炼炉")
            .langValue("CNC ALLOY Smelter")
            .recipeType(ALLOY_BLAST_RECIPES)
            .tooltips(MultiblocksA.megaLcrTooltip0.translate(),
                    MultiblocksA.megaLcrTooltip1.translate(),
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2")
            )
            .allowExtendedFacing(false)
            .allowFlip(false)
            //.rotationState(RotationState.NON_Y_AXIS)

            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, CTNHRecipeModifiers::ebfOverclock, GTRecipeModifiers.BATCH_MODE)
            .appearanceBlock(GTBlocks.COMPUTER_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####AAAAA#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("###AABBBBBAA###", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("##ABBCCCCCBBA##", "#####DDDDD#####", "#####DDDDD#####", "#####DDDDD#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "####AAAAAAA####")
                    .aisle("#ABCCEEEEECCBA#", "###DD#####DD###", "###DD#####DD###", "###DD#####DD###", "###AAFFFFFAA###", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "#####AAAAA#####", "###AACCGCCAA###")
                    .aisle("#ABCEHBBBHECBA#", "###D#######D###", "###D#######D###", "###D#######D###", "###AA#####AA###", "####AEEEEEA####", "####AEEEEEA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AEEEEEA####", "####A#####A####", "##AAGAAGAAGAA##")
                    .aisle("ABCEHBHBHBHECBA", "##D#########D##", "##D#########D##", "##D#########D##", "###F#######F###", "####E#####E####", "####E#####E####", "####FGCCCGF####", "####FGHHHGF####", "####FGHHHGF####", "####FGHHHGF####", "####FGCCCGF####", "####EGGGGGE####", "###A#G###G#A###", "##ACAGGGGGACA##")
                    .aisle("ABCEBHBBBHBECBA", "##D###B#B###D##", "##D###B#B###D##", "##D###B#B###D##", "###F##B#B##F###", "####E#B#B#E####", "####E#B#B#E####", "####FCB#BCF####", "####FHB#BHF####", "####FHB#BHF####", "####FHB#BHF####", "####FCB#BCF####", "####EGB#BGE####", "###A##BBB##A###", "##ACAGCGCGACA##")
                    .aisle("ABCEBBBCBBBECBA", "##D####G####D##", "##D####G####D##", "##D####G####D##", "###F###G###F###", "####E##G##E####", "####E##G##E####", "####FC#C#CF####", "####FH#H#HF####", "####FH#H#HF####", "####FH#H#HF####", "####FC#C#CF####", "####EG###GE####", "###A##BBB##A###", "##AGGGGIGGGGA##")
                    .aisle("ABCEBHBBBHBECBA", "##D###B#B###D##", "##D###B#B###D##", "##D###B#B###D##", "###F##B#B##F###", "####E#B#B#E####", "####E#B#B#E####", "####FCB#BCF####", "####FHB#BHF####", "####FHB#BHF####", "####FHB#BHF####", "####FCB#BCF####", "####EGB#BGE####", "###A##BBB##A###", "##ACAGCGCGACA##")
                    .aisle("ABCEHBHBHBHECBA", "##D#########D##", "##D#########D##", "##D#########D##", "###F#######F###", "####E#####E####", "####E#####E####", "####FGCCCGF####", "####FGHHHGF####", "####FGHHHGF####", "####FGHHHGF####", "####FGCCCGF####", "####EGGGGGE####", "###A#G###G#A###", "##ACAGGGGGACA##")
                    .aisle("#ABCEHBBBHECBA#", "###D#######D###", "###D#######D###", "###D#######D###", "###AA#####AA###", "####AEEEEEA####", "####AEEEEEA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AFFFFFA####", "####AEEEEEA####", "####A#####A####", "##AAGAAGAAGAA##")
                    .aisle("#ABCCEEEEECCBA#", "###DD#####DD###", "###DD#####DD###", "###DD#####DD###", "###AAFFFFFAA###", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "#####AAAAA#####", "###AACCGCCAA###")
                    .aisle("##ABBCCCCCBBA##", "#####DDDDD#####", "#####DD@DD#####", "#####DDDDD#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "####AAAAAAA####")
                    .aisle("###AABBBBBAA###", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("#####AAAAA#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .where("C", Predicates.blocks(GTBlocks.MACHINE_CASING_LuV.get()))
                    .where("D", Predicates.blocks(GTBlocks.COMPUTER_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes(),false, false, true, true, true,
                                    true))
                            .or(Predicates.autoAbilities(true, false, true))
                            .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                    .setMaxGlobalLimited(4).setPreviewCount(4))
                    )
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("H", heatingCoils())
                    .where("I", abilities(PartAbility.MUFFLER))
                    .where("B", Predicates.blocks(CTNHBlocks.CASING_POLYBENZIMIDAZOLE_PIPE.get()))
                    .where("G", Predicates.blocks(GTBlocks.COMPUTER_CASING.get()))
                    .where("A", Predicates.blocks(CASING_HIGH_TEMPERATURE_SMELTING.get()))
                    .where("E", Predicates.blocks(CTNHBlocks.NAQUADAH_FIREBOX.get()))
                    .where("F", Predicates.blocks(GTBlocks.FUSION_GLASS.get()))
                    .build())
            .additionalDisplay((controller, components) -> {
                var coilMachine = controller.self().getTrait(CoilMachineTrait.class);
                if (coilMachine != null && controller instanceof ITieredMachine tieredMachine
                        && controller.isFormed()
                ) {
                    components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                            Component
                                    .translatable(
                                            FormattingUtil
                                                    .formatNumbers(coilMachine.getCoilType().getCoilTemperature() +
                                                            100L * Math.max(0, tieredMachine.getTier() - GTValues.MV)) +
                                                    "K")
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
                }
            })
            .sidedWorkableCasingModel(GTCEu.id("block/casings/hpca/computer_casing"),
                    GTCEu.id("block/multiblock/gcym/blast_alloy_smelter"))
            .register();
}
// spotless:on
