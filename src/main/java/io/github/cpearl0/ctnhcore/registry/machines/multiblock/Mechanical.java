package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.KineticCentrifugeMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.KineticMixerMachine;
import io.github.cpearl0.ctnhcore.data.CreateRecipeTypes;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.lowdragmc.lowdraglib.utils.BlockInfo;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.mo_guang.ctpp.CTPP;
import com.mo_guang.ctpp.api.CTPPPartAbility;
import com.mo_guang.ctpp.api.pattern.FactoryStaticBlockPattern;
import com.mo_guang.ctpp.common.machine.multiblock.KineticWorkableMultiblockMachine;
import com.mo_guang.ctpp.registry.CTPPRecipeModifiers;
import com.mo_guang.ctpp.util.CommonTooltips;
import com.negodya1.vintageimprovements.VintageBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static com.gregtechceu.gtceu.common.data.GCYMBlocks.CASING_INDUSTRIAL_STEAM;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_STEEL_GEARBOX;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_TEMPERED_GLASS;
import static com.jesz.createdieselgenerators.CDGBlocks.BULK_FERMENTER;
import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class Mechanical {

    public final static MultiblockMachineDefinition MECHANICAL_PRESSOR = REGISTRATE
            .multiblock("mechanical_pressor", KineticWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CreateRecipeTypes.MECHANICAL_PRESSOR_RECIPES)
            .cnLangValue("机械辊压厂").appearanceBlock(AllBlocks.RAILWAY_CASING)
            .recipeModifier(CTPPRecipeModifiers.KINETIC_PARALLEL)
            .tooltips(CommonTooltips.KINETIC_OVERCLOCK.translate(),
                    CommonTooltips.INPUT_SPEED.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "BCCCB", "B###B", "BBBBB", "B###B", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#####", "B###B", "#####", "CEEEC", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#####", "B###B", "#####", "CEEEC", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#####", "B###B", "#####", "CEEEC", "AAAAA")
                    .aisle("AA@AA", "BCCCB", "B###B", "BBBBB", "B###B", "BCCCB", "AAAAA")
                    .where("A", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC))
                            .or(Predicates.abilities(CTPPPartAbility.MECHANICAL_UPGRADE).setExactLimit(1)))
                    .where("B", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("C", Predicates.blocks(AllBlocks.BRASS_CASING.get()))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(AllBlocks.DEPOT.get()))
                    .where("E", Predicates.blocks(AllBlocks.MECHANICAL_PRESS.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTPP.id("block/create/railway_casing"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();
    public final static MultiblockMachineDefinition MECHANICAL_MIXER = REGISTRATE
            .multiblock("mechanical_mixer", KineticMixerMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CreateRecipeTypes.MECHANICAL_MIXER_RECIPES)
            .cnLangValue("机械搅拌厂").appearanceBlock(AllBlocks.RAILWAY_CASING)
            .recipeModifier(CTPPRecipeModifiers.KINETIC_PARALLEL)
            .tooltips(CommonTooltips.KINETIC_OVERCLOCK.translate(),
                    CommonTooltips.INPUT_SPEED.translate())
            .pattern(definition -> FactoryStaticBlockPattern.start()
                    .aisle("###AAAAA###", "###########", "#####B#####", "#####B#####", "####CCC####", "####CCC####",
                            "####CCC####", "####CCC####", "####CCC####", "####AAA####", "###########")
                    .aisle("##AAAAAAA##", "####AAA####", "#####B#####", "####AAA####", "###C#E#C###", "###C#E#C###",
                            "###C#E#C###", "###C#E#C###", "###C#E#C###", "###A###A###", "####AAA####")
                    .aisle("##AAAAAAA##", "###AAAAA###", "#####B#####", "###AAAAA###", "##C##E##C##", "##C#####C##",
                            "##C#####C##", "##C#####C##", "##C##E##C##", "##A#####A##", "###AAAAA###")
                    .aisle("##AAAAAAA##", "###AAFAA###", "##BBBFBBB##", "##BAAFAAB##", "##CEEHEECGG", "##CE#H#EC#G",
                            "#CCE#H#EC#G", "#CCE#H#EC#G", "CCCEEEEECGG", "##A#####A##", "###AAAAA###")
                    .aisle("##AAAAAAA##", "###AAAAA###", "#####B#####", "###AAAAA###", "##C##E##C##", "##C#####C##",
                            "##C#####C##", "##C#####C##", "##C##E##C##", "##A#####A##", "###AAAAA###")
                    .aisle("##AAAAAAA##", "####AAA####", "#####B#####", "####AAA####", "###C#E#C###", "###C#E#C###",
                            "###C#E#C###", "###C#E#C###", "###C#E#C###", "###A###A###", "####AAA####")
                    .aisle("###AA@AA###", "###########", "#####B#####", "#####B#####", "####CCC####", "####CCC####",
                            "####CCC####", "####CCC####", "####CCC####", "####AAA####", "###########")
                    .where("A", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC).setMinGlobalLimited(1))
                            .or(Predicates.abilities(CTPPPartAbility.MECHANICAL_UPGRADE).setExactLimit(1)))
                    .where("B", Predicates.frames(GTMaterials.Steel))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("E", Predicates.blocks(Blocks.IRON_BLOCK), false)
                    .where("H", Predicates.blocks(AllBlocks.ANDESITE_ALLOY_BLOCK.get()), false)
                    .where("F", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("G", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .build())
            .workableCasingModel(CTPP.id("block/create/railway_casing"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();
    public final static MultiblockMachineDefinition MECHANICAL_CENTRIFUGE = REGISTRATE
            .multiblock("mechanical_centrifuge", KineticCentrifugeMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CreateRecipeTypes.MECHANICAL_CENTRIFUGE_RECIPES)
            .cnLangValue("机械离心厂").appearanceBlock(AllBlocks.RAILWAY_CASING)
            .recipeModifier(CTPPRecipeModifiers.KINETIC_PARALLEL)
            .tooltips(CommonTooltips.KINETIC_OVERCLOCK.translate(),
                    CommonTooltips.INPUT_SPEED.translate())
            .pattern(definition -> FactoryStaticBlockPattern.start()
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "#############")
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "#####BBB#####")
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "###BBCCCBB###")
                    .aisle("####DDDDD####", "#############", "#############", "#####E#E#####", "#############",
                            "##B##C#C##B##")
                    .aisle("###DDDDDDD###", "#####DDD#####", "#############", "#####E#E#####", "#############",
                            "##B##C#C##B##")
                    .aisle("###DDDDDDD###", "####DDDDD####", "AAA#######AAA", "AAAEEFFFEEAAA", "AAA#######AAA",
                            "#BCCCGGGCCCB#")
                    .aisle("###DDDDDDD###", "####DDHDD####", "AAA###H###AAA", "AAA##FHF##AAA", "AAA###H###AAA",
                            "#BC##GHG##CB#")
                    .aisle("###DDDDDDD###", "####DDDDD####", "AAA#######AAA", "AAAEEFFFEEAAA", "AAA#######AAA",
                            "#BCCCGGGCCCB#")
                    .aisle("###DDDDDDD###", "#####DDD#####", "#############", "#####E#E#####", "#############",
                            "##B##C#C##B##")
                    .aisle("####DD@DD####", "#############", "#############", "#####E#E#####", "#############",
                            "##B##C#C##B##")
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "###BBCCCBB###")
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "#####BBB#####")
                    .aisle("#############", "#############", "#####AAA#####", "#####AAA#####", "#####AAA#####",
                            "#############")
                    .where("D", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC).setMinGlobalLimited(1))
                            .or(Predicates.abilities(CTPPPartAbility.MECHANICAL_UPGRADE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CASING_INDUSTRIAL_STEAM.get()), false, 0)
                    .where("C", Predicates.blocks(AllBlocks.METAL_GIRDER.get()), false, 0)
                    .where("G", Predicates.blocks(AllBlocks.RAILWAY_CASING.get()), false, 0)
                    .where("E", Predicates.blocks(AllBlocks.METAL_GIRDER.get()), false, 1)
                    .where("F", Predicates.blocks(AllBlocks.RAILWAY_CASING.get()), false, 1)
                    .where("A", Predicates.blocks(BULK_FERMENTER.get()), false, 1)
                    .where("H", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTPP.id("block/create/railway_casing"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();
    public final static MultiblockMachineDefinition MECHANICAL_SIFTER = REGISTRATE
            .multiblock("mechanical_sifter", KineticWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CreateRecipeTypes.MECHANICAL_SIFTER_RECIPES)
            .cnLangValue("机械筛选厂").appearanceBlock(AllBlocks.RAILWAY_CASING)
            .recipeModifier(CTPPRecipeModifiers.KINETIC_PARALLEL)
            .tooltips(CommonTooltips.KINETIC_OVERCLOCK.translate(),
                    CommonTooltips.INPUT_SPEED.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "BCCCB", "B###B", "AAAAA", "B###B", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#EEE#", "ADDDA", "#####", "CFFFC", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#EEE#", "ADDDA", "#####", "CFFFC", "AAAAA")
                    .aisle("AAAAA", "CDDDC", "#EEE#", "ADDDA", "#####", "CFFFC", "AAAAA")
                    .aisle("AA@AA", "BCCCB", "B###B", "AAAAA", "B###B", "BCCCB", "AAAAA")
                    .where("A", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC))
                            .or(Predicates.abilities(CTPPPartAbility.MECHANICAL_UPGRADE).setExactLimit(1)))
                    .where("B", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("C", Predicates.blocks(AllBlocks.BRASS_CASING.get()))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(AllBlocks.BASIN.get()))
                    .where("E", Predicates.blocks(VintageBlocks.VIBRATING_TABLE.get()))
                    .where("F", Predicates.blocks(VintageBlocks.VACUUM_CHAMBER.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTPP.id("block/create/railway_casing"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    @CN("结构中的车床必须严格依照EMI结构信息页面展示的位置和方向摆放")
    @EN("The lathe in the structure must be placed exactly as shown in EMI's structure information, including position and orientation")
    public static Lang mechanicalLatheStructure;

    public final static MultiblockMachineDefinition MECHANICAL_LATHE = REGISTRATE
            .multiblock("mechanical_lathe", KineticWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CreateRecipeTypes.MECHANICAL_LATHE_RECIPES)
            .cnLangValue("机械车床厂").appearanceBlock(AllBlocks.RAILWAY_CASING)
            .recipeModifier(CTPPRecipeModifiers.KINETIC_PARALLEL)
            .tooltips(CommonTooltips.KINETIC_OVERCLOCK.translate(),
                    CommonTooltips.INPUT_SPEED.translate())
            .tooltips(mechanicalLatheStructure.translate().withStyle(ChatFormatting.DARK_RED))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAAA", "AAAAAAAB", "AAACCCCB", "BBBBBBBB", "B######B", "BDDDDDDB", "AAAAAAAA")
                    .aisle("AAAAAAAA", "A#AEFFEA", "ADAGGGGC", "B######B", "########", "D######D", "AAAAAAAA")
                    .aisle("AAAAAAAA", "A#AEFFEA", "ADAGGGGC", "B######B", "########", "D######D", "AAAAAAAA")
                    .aisle("AAAAAAAA", "A#AEFFEA", "ADAGGGGC", "B######B", "########", "D######D", "AAAAAAAA")
                    .aisle("AAAAAAAA", "A@AGGGGB", "AAAGGGGB", "BBBBBBBB", "B######B", "BDDDDDDB", "AAAAAAAA")
                    .where("A", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC))
                            .or(Predicates.abilities(CTPPPartAbility.MECHANICAL_UPGRADE).setExactLimit(1)))
                    .where("B", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("C", Predicates.blocks(AllBlocks.ITEM_VAULT.get()))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(AllBlocks.BRASS_CASING.get()))
                    .where("E", Predicates.blocks(VintageBlocks.LATHE_MOVING.get()))
                    .where("F", Predicates.blocks(VintageBlocks.LATHE_ROTATING.get()))
                    .where("G", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .shapeInfo(
                    definition -> MultiblockShapeInfo.builder()
                            .aisle("AAAAAAAA", "BGGGGA@A", "BGGGGAAA", "BBBBBBBB", "B######B", "BDDDDDDB", "AAAAAAAA")
                            .aisle("AAAAAAAA", "AYXFEA#A", "CGGGGADA", "B######B", "########", "D######D", "AAAAAAAA")
                            .aisle("AAAAAAAA", "AYXFEA#A", "CGGGGADA", "B######B", "########", "D######D", "AAAAAAAA")
                            .aisle("AAAAAAAA", "AYXFEA#A", "CGGGGADA", "B######B", "########", "D######D", "AAAAAAAA")
                            .aisle("AAAAAAAA", "BAAAAAAA", "BCCCCAAA", "BBBBBBBB", "B######B", "BDDDDDDB", "AAAAAAAA")
                            .where('A', AllBlocks.RAILWAY_CASING)
                            .where('B', AllBlocks.METAL_GIRDER)
                            .where('C', AllBlocks.ITEM_VAULT)
                            .where('#', BlockInfo.EMPTY)
                            .where('D', AllBlocks.BRASS_CASING)
                            .where('E',
                                    VintageBlocks.LATHE_MOVING.getDefaultState()
                                            .setValue(DirectionalKineticBlock.FACING, Direction.EAST))
                            .where('F',
                                    VintageBlocks.LATHE_ROTATING.getDefaultState().setValue(HORIZONTAL_FACING,
                                            Direction.WEST))
                            .where('Y',
                                    VintageBlocks.LATHE_MOVING.getDefaultState()
                                            .setValue(DirectionalKineticBlock.FACING, Direction.WEST))
                            .where('X',
                                    VintageBlocks.LATHE_ROTATING.getDefaultState().setValue(HORIZONTAL_FACING,
                                            Direction.EAST))
                            .where('G', CASING_TEMPERED_GLASS)
                            .where('@', Mechanical.MECHANICAL_LATHE, Direction.NORTH)
                            .build())
            .workableCasingModel(CTPP.id("block/create/railway_casing"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static void init() {}
}
