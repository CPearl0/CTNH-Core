package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.renderer.HyperPlasmaTurbineRender;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.HyperPlasmaTurbineMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashSet;
import java.util.Set;

import static com.gregtechceu.gtceu.api.GTValues.ZPM;
import static com.gregtechceu.gtceu.common.data.GTBlocks.COIL_TRITANIUM;
import static com.gregtechceu.gtceu.common.data.GTBlocks.HIGH_POWER_CASING;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Neutronium;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.HYPER_PLASMA_TURBINE_ROTOR;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class HyperPlasmaTurbineRegister {
    public static MultiblockMachineDefinition register(){
        return REGISTRATE.multiblock("hyper_plasma_turbine", HyperPlasmaTurbineMachine::new)
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(GTRecipeTypes.PLASMA_GENERATOR_FUELS)
                .generator(true)
                .recipeModifier(HyperPlasmaTurbineMachine::recipeModifier, true)
                .appearanceBlock(NEUTRONIUM_REINFORCED_TURBINE_CASING)
                .pattern(definition ->{
                    return FactoryBlockPattern.start()
                            .aisle("#############################", "#############################", "#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################", "#############################", "#############################")
                            .aisle("#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################")
                            .aisle("#############################", "#############################", "#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BDAAAAAAAAAB#####BAAAAAAAAADB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "#CAAAAAAAAABEEEEEBAAAAAAAAAC#", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BDAAAAAAAAAB#####BAAAAAAAAADB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################", "#############################", "#############################")
                            .aisle("#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BAAAAAAAAAB#####BAAAAAAAAAB#", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "#CAAAAAAAAABFFFFFBAAAAAAAAAC#", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "#BAAAAAAAAAB#####BAAAAAAAAAB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################")
                            .aisle("#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BDAAAAAAAAAB#####BAAAAAAAAADB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BDAAAAAAAAABFFFFFBAAAAAAAAADB", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "#CAAAAAAAAABGHFGHBAAAAAAAAAC#", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "BDAAAAAAAAABFFFFFBAAAAAAAAADB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BDAAAAAAAAAB#####BAAAAAAAAADB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################")
                            .aisle("#BBBBBBBBBBB#####BBBBBBBBBBB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "#CAAAAAAAAABHGFHGBAAAAAAAAAC#", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BBBBBBBBBBB#####BBBBBBBBBBB#")
                            .aisle("#############################", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "#CAAAAAAAAABEEEEEBAAAAAAAAAC#", "#CAAAAAAAAABFFFFFBAAAAAAAAAC#", "#CAAAAAAAAABHGFHGBAAAAAAAAAC#", "#CAAAAAAAAABGHFGHBAAAAAAAAAC#", "I##RRRRRRRRKKKFKKKrrrrrrrr##i", "#CAAAAAAAAABGHFGHBAAAAAAAAAC#", "#CAAAAAAAAABHGFHGBAAAAAAAAAC#", "#CAAAAAAAAABFFFFFBAAAAAAAAAC#", "#CAAAAAAAAABEEEEEBAAAAAAAAAC#", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "#############################")
                            .aisle("#BBBBBBBBBBB#####BBBBBBBBBBB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "#CAAAAAAAAABHGFHGBAAAAAAAAAC#", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "BBAAAAAAAAABHGFHGBAAAAAAAAABB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BBBBBBBBBBB#####BBBBBBBBBBB#")
                            .aisle("#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BDAAAAAAAAAB#####BAAAAAAAAADB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BDAAAAAAAAABFFFFFBAAAAAAAAADB", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "#CAAAAAAAAABGHFGHBAAAAAAAAAC#", "BBAAAAAAAAABGHFGHBAAAAAAAAABB", "BDAAAAAAAAABFFFFFBAAAAAAAAADB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BDAAAAAAAAAB#####BAAAAAAAAADB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################")
                            .aisle("#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BAAAAAAAAAB#####BAAAAAAAAAB#", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "#CAAAAAAAAABFFFFFBAAAAAAAAAC#", "BBAAAAAAAAABBBBBBBAAAAAAAAABB", "BDAAAAAAAAABBBBBBBAAAAAAAAADB", "#BAAAAAAAAAB#####BAAAAAAAAAB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################")
                            .aisle("#############################", "#############################", "#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BDAAAAAAAAAB#####BAAAAAAAAADB", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "#CAAAAAAAAABEE@EEBAAAAAAAAAC#", "BBAAAAAAAAABEEEEEBAAAAAAAAABB", "BDAAAAAAAAAB#####BAAAAAAAAADB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################", "#############################", "#############################")
                            .aisle("#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################")
                            .aisle("#############################", "#############################", "#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################", "#############################", "#############################")
                            .where("#", Predicates.any())
                            .where("A", Predicates.air())
                            .where("B", Predicates.blocks(NEUTRONIUM_REINFORCED_TURBINE_CASING.get()))
                            .where("C", Predicates.blocks(GTBlocks.FUSION_GLASS.get()))
                            .where("D", Predicates.blocks(PLASMA_COOLED_CORE.get()))
                            .where("E", Predicates.blocks(NEUTRONIUM_REINFORCED_TURBINE_CASING.get())
                                    .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1)
                                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
                                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER, PartAbility.OUTPUT_ENERGY).setMaxGlobalLimited(1)))
                            )
                            .where("F", Predicates.frames(Neutronium))
                            .where("G", Predicates.blocks(GTBlocks.SUPERCONDUCTING_COIL.get()))
                            .where("H", Predicates.blocks(COIL_TRITANIUM.get()))
                            .where("I", Predicates.abilities(PartAbility.EXPORT_FLUIDS))
                            .where("i", Predicates.abilities(PartAbility.EXPORT_FLUIDS))
//                        .where("R", Predicates.states(HYPER_PLASMA_TURBINE_ROTOR.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST)))
//                        .where("r", Predicates.states(HYPER_PLASMA_TURBINE_ROTOR.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST)))
//                        .where("K", Predicates.states(AllBlocks.SHAFT.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X)))
                            .where("R", Predicates.blocks(HYPER_PLASMA_TURBINE_ROTOR.get()))
                            .where("r", Predicates.blocks(HYPER_PLASMA_TURBINE_ROTOR.get()))
                            .where("K", Predicates.blocks(AllBlocks.SHAFT.get()))
                            .where("@", Predicates.controller(Predicates.blocks(definition.get())))

                            .build();
                        })

//                .shapeInfo(definition -> MultiblockShapeInfo.builder()
//                        .aisle("#############################", "#############################", "#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################", "#############################", "#############################")
//                        .aisle("#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################")
//                        .aisle("#############################", "#############################", "#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BD#########B#####B#########DB", "BB#########BEEEEEB#########BB", "#C#########BfE@ceB#########C#", "BB#########BEEEEEB#########BB", "BD#########B#####B#########DB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################", "#############################", "#############################")
//                        .aisle("#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#B#########B#####B#########B#", "BD#########BBBBBBB#########DB", "BB#########BBBBBBB#########BB", "#C#########BFFFFFB#########C#", "BB#########BBBBBBB#########BB", "BD#########BBBBBBB#########DB", "#B#########B#####B#########B#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################")
//                        .aisle("#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BD#########B#####B#########DB", "BD#########BBBBBBB#########DB", "BD#########BFFFFFB#########DB", "BB#########BGHFGHB#########BB", "#C#########BGHFGHB#########C#", "BB#########BGHFGHB#########BB", "BD#########BFFFFFB#########DB", "BD#########BBBBBBB#########DB", "BD#########B#####B#########DB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################")
//                        .aisle("#BBBBBBBBBBB#####BBBBBBBBBBB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "BB#########BEEEEEB#########BB", "BB#########BBBBBBB#########BB", "BB#########BHGFHGB#########BB", "BB#########BHGFHGB#########BB", "#C#########BHGFHGB#########C#", "BB#########BGHFGHB#########BB", "BB#########BHGFHGB#########BB", "BB#########BBBBBBB#########BB", "BB#########BEEEEEB#########BB", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BBBBBBBBBBB#####BBBBBBBBBBB#")
//                        .aisle("#############################", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "#C#########BEEEEEB#########C#", "#C#########BFFFFFB#########C#", "#C#########BHGFHGB#########C#", "#C#########BGHFGHB#########C#", "I##RRRRRRRRKKKFKKKrrrrrrrr##i", "#C#########BGHFGHB#########C#", "#C#########BHGFHGB#########C#", "#C#########BFFFFFB#########C#", "#C#########BEEEEEB#########C#", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "#############################")
//                        .aisle("#BBBBBBBBBBB#####BBBBBBBBBBB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "BB#########BEEEEEB#########BB", "BB#########BBBBBBB#########BB", "BB#########BHGFHGB#########BB", "BB#########BGHFGHB#########BB", "#C#########BHGFHGB#########C#", "BB#########BHGFHGB#########BB", "BB#########BHGFHGB#########BB", "BB#########BBBBBBB#########BB", "BB#########BEEEEEB#########BB", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BBBBBBBBBBB#####BBBBBBBBBBB#")
//                        .aisle("#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BD#########B#####B#########DB", "BD#########BBBBBBB#########DB", "BD#########BFFFFFB#########DB", "BB#########BGHFGHB#########BB", "#C#########BGHFGHB#########C#", "BB#########BGHFGHB#########BB", "BD#########BFFFFFB#########DB", "BD#########BBBBBBB#########DB", "BD#########B#####B#########DB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################")
//                        .aisle("#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#B#########B#####B#########B#", "BD#########BBBBBBB#########DB", "BB#########BBBBBBB#########BB", "#C#########BFFFFFB#########C#", "BB#########BBBBBBB#########BB", "BD#########BBBBBBB#########DB", "#B#########B#####B#########B#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################")
//                        .aisle("#############################", "#############################", "#############################", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BD#########B#####B#########DB", "BB#########BEEEEEB#########BB", "#C#########BEEEEEB#########C#", "BB#########BEEEEEB#########BB", "BD#########B#####B#########DB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#############################", "#############################", "#############################")
//                        .aisle("#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#CCCCCCCCCCB#####BCCCCCCCCCC#", "BBCCCCCCCCCB#####BCCCCCCCCCBB", "#BCCCCCCCCCB#####BCCCCCCCCCB#", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################")
//                        .aisle("#############################", "#############################", "#############################", "#############################", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#BBBBBBBBBBB#####BBBBBBBBBBB#", "#############################", "#############################", "#############################", "#############################", "#############################")
//                        .where('#', Blocks.AIR)
//                        .where('B', NEUTRONIUM_REINFORCED_TURBINE_CASING.get())
//                        .where('C', GTBlocks.FUSION_GLASS.get())
//                        .where('D', PLASMA_COOLED_CORE.get())
//                        .where('E', NEUTRONIUM_REINFORCED_TURBINE_CASING.get())
//                        .where('F', GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt,Neutronium))
//                        .where('G', GTBlocks.SUPERCONDUCTING_COIL.get())
//                        .where('H', COIL_TRITANIUM.get())
//                        .where('I', GTMachines.FLUID_EXPORT_HATCH[ZPM],Direction.EAST)
//                        .where('i', GTMachines.FLUID_EXPORT_HATCH[ZPM],Direction.WEST)
//                        .where('R', HYPER_PLASMA_TURBINE_ROTOR.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))
//                        .where('r', HYPER_PLASMA_TURBINE_ROTOR.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))
//                        .where('K', AllBlocks.SHAFT.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X))
//                        .where('f',GTMachines.FLUID_IMPORT_HATCH[ZPM].getBlock())
//                        .where('c', GTResearchMachines.COMPUTATION_HATCH_RECEIVER.getBlock())
//                        .where('e',GTMachines.LASER_OUTPUT_HATCH_4096[ZPM].getBlock())
//                        .where('@', definition.getBlock())
//
//                        .build())



                .renderer(() -> new HyperPlasmaTurbineRender(CTNHCore.id("block/casings/neutronium_reinforced_turbine_casing"), GTCEu.id("block/multiblock/fusion_reactor")))
//                .hasTESR(true)
                .tooltips(
                        Component.translatable("ctnh.multiblock.hyper_plasma_turbine.tooltip0"),
                        Component.translatable("ctnh.multiblock.hyper_plasma_turbine.tooltip1",HyperPlasmaTurbineMachine.CWUtStair,HyperPlasmaTurbineMachine.CWUtStair),
                        Component.translatable("gtceu.multiblock.turbine.efficiency", HyperPlasmaTurbineMachine.getEfficiency()*100),
                        Component.translatable("gtceu.universal.tooltip.base_production_eut", HyperPlasmaTurbineMachine.BASE_EU_OUTPUT),
                        Component.translatable("gtceu.multiblock.laser.tooltip"))
                .register();
    }
//    //由MBPosHelper生成
//    //由DeepSeek修改参数
//    //A : WEST
//    public static Set<BlockPos> blockOffsetWEST0 = new HashSet<BlockPos>();
//    static{
//        blockOffsetWEST0.add(new BlockPos(3,0,0));
//        blockOffsetWEST0.add(new BlockPos(3,-2,-4));
//        blockOffsetWEST0.add(new BlockPos(3,-1,-4));
//        blockOffsetWEST0.add(new BlockPos(3,0,-4));
//        blockOffsetWEST0.add(new BlockPos(3,1,-4));
//        blockOffsetWEST0.add(new BlockPos(3,2,-4));
//        blockOffsetWEST0.add(new BlockPos(3,-3,-3));
//        blockOffsetWEST0.add(new BlockPos(3,-2,-3));
//        blockOffsetWEST0.add(new BlockPos(3,-1,-3));
//        blockOffsetWEST0.add(new BlockPos(3,0,-3));
//        blockOffsetWEST0.add(new BlockPos(3,1,-3));
//        blockOffsetWEST0.add(new BlockPos(3,2,-3));
//        blockOffsetWEST0.add(new BlockPos(3,3,-3));
//        blockOffsetWEST0.add(new BlockPos(3,-4,-2));
//        blockOffsetWEST0.add(new BlockPos(3,-3,-2));
//        blockOffsetWEST0.add(new BlockPos(3,-2,-2));
//        blockOffsetWEST0.add(new BlockPos(3,-1,-2));
//        blockOffsetWEST0.add(new BlockPos(3,0,-2));
//        blockOffsetWEST0.add(new BlockPos(3,1,-2));
//        blockOffsetWEST0.add(new BlockPos(3,2,-2));
//        blockOffsetWEST0.add(new BlockPos(3,3,-2));
//        blockOffsetWEST0.add(new BlockPos(3,4,-2));
//        blockOffsetWEST0.add(new BlockPos(3,-4,-1));
//        blockOffsetWEST0.add(new BlockPos(3,-3,-1));
//        blockOffsetWEST0.add(new BlockPos(3,-2,-1));
//        blockOffsetWEST0.add(new BlockPos(3,-1,-1));
//        blockOffsetWEST0.add(new BlockPos(3,0,-1));
//        blockOffsetWEST0.add(new BlockPos(3,1,-1));
//        blockOffsetWEST0.add(new BlockPos(3,2,-1));
//        blockOffsetWEST0.add(new BlockPos(3,3,-1));
//        blockOffsetWEST0.add(new BlockPos(3,4,-1));
//        blockOffsetWEST0.add(new BlockPos(3,-4,0));
//        blockOffsetWEST0.add(new BlockPos(3,-3,0));
//        blockOffsetWEST0.add(new BlockPos(3,-2,0));
//        blockOffsetWEST0.add(new BlockPos(3,-1,0));
//        blockOffsetWEST0.add(new BlockPos(3,1,0));
//        blockOffsetWEST0.add(new BlockPos(3,2,0));
//        blockOffsetWEST0.add(new BlockPos(3,3,0));
//        blockOffsetWEST0.add(new BlockPos(3,4,0));
//        blockOffsetWEST0.add(new BlockPos(3,-4,1));
//        blockOffsetWEST0.add(new BlockPos(3,-3,1));
//        blockOffsetWEST0.add(new BlockPos(3,-2,1));
//        blockOffsetWEST0.add(new BlockPos(3,-1,1));
//        blockOffsetWEST0.add(new BlockPos(3,0,1));
//        blockOffsetWEST0.add(new BlockPos(3,1,1));
//        blockOffsetWEST0.add(new BlockPos(3,2,1));
//        blockOffsetWEST0.add(new BlockPos(3,3,1));
//        blockOffsetWEST0.add(new BlockPos(3,4,1));
//        blockOffsetWEST0.add(new BlockPos(3,-4,2));
//        blockOffsetWEST0.add(new BlockPos(3,-3,2));
//        blockOffsetWEST0.add(new BlockPos(3,-2,2));
//        blockOffsetWEST0.add(new BlockPos(3,-1,2));
//        blockOffsetWEST0.add(new BlockPos(3,0,2));
//        blockOffsetWEST0.add(new BlockPos(3,1,2));
//        blockOffsetWEST0.add(new BlockPos(3,2,2));
//        blockOffsetWEST0.add(new BlockPos(3,3,2));
//        blockOffsetWEST0.add(new BlockPos(3,4,2));
//        blockOffsetWEST0.add(new BlockPos(3,-3,3));
//        blockOffsetWEST0.add(new BlockPos(3,-2,3));
//        blockOffsetWEST0.add(new BlockPos(3,-1,3));
//        blockOffsetWEST0.add(new BlockPos(3,0,3));
//        blockOffsetWEST0.add(new BlockPos(3,1,3));
//        blockOffsetWEST0.add(new BlockPos(3,2,3));
//        blockOffsetWEST0.add(new BlockPos(3,3,3));
//        blockOffsetWEST0.add(new BlockPos(3,-2,4));
//        blockOffsetWEST0.add(new BlockPos(3,-1,4));
//        blockOffsetWEST0.add(new BlockPos(3,0,4));
//        blockOffsetWEST0.add(new BlockPos(3,1,4));
//        blockOffsetWEST0.add(new BlockPos(3,2,4));
//    }
//    //A : EAST
//    public static Set<BlockPos> blockOffsetEAST0 = new HashSet<BlockPos>();
//    static{
//        blockOffsetEAST0.add(new BlockPos(-3,0,0));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,-4));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,-4));
//        blockOffsetEAST0.add(new BlockPos(-3,0,-4));
//        blockOffsetEAST0.add(new BlockPos(-3,1,-4));
//        blockOffsetEAST0.add(new BlockPos(-3,2,-4));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,0,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,1,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,2,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,3,-3));
//        blockOffsetEAST0.add(new BlockPos(-3,-4,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,0,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,1,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,2,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,3,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,4,-2));
//        blockOffsetEAST0.add(new BlockPos(-3,-4,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,0,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,1,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,2,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,3,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,4,-1));
//        blockOffsetEAST0.add(new BlockPos(-3,-4,0));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,0));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,0));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,0));
//        blockOffsetEAST0.add(new BlockPos(-3,1,0));
//        blockOffsetEAST0.add(new BlockPos(-3,2,0));
//        blockOffsetEAST0.add(new BlockPos(-3,3,0));
//        blockOffsetEAST0.add(new BlockPos(-3,4,0));
//        blockOffsetEAST0.add(new BlockPos(-3,-4,1));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,1));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,1));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,1));
//        blockOffsetEAST0.add(new BlockPos(-3,0,1));
//        blockOffsetEAST0.add(new BlockPos(-3,1,1));
//        blockOffsetEAST0.add(new BlockPos(-3,2,1));
//        blockOffsetEAST0.add(new BlockPos(-3,3,1));
//        blockOffsetEAST0.add(new BlockPos(-3,4,1));
//        blockOffsetEAST0.add(new BlockPos(-3,-4,2));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,2));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,2));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,2));
//        blockOffsetEAST0.add(new BlockPos(-3,0,2));
//        blockOffsetEAST0.add(new BlockPos(-3,1,2));
//        blockOffsetEAST0.add(new BlockPos(-3,2,2));
//        blockOffsetEAST0.add(new BlockPos(-3,3,2));
//        blockOffsetEAST0.add(new BlockPos(-3,4,2));
//        blockOffsetEAST0.add(new BlockPos(-3,-3,3));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,3));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,3));
//        blockOffsetEAST0.add(new BlockPos(-3,0,3));
//        blockOffsetEAST0.add(new BlockPos(-3,1,3));
//        blockOffsetEAST0.add(new BlockPos(-3,2,3));
//        blockOffsetEAST0.add(new BlockPos(-3,3,3));
//        blockOffsetEAST0.add(new BlockPos(-3,-2,4));
//        blockOffsetEAST0.add(new BlockPos(-3,-1,4));
//        blockOffsetEAST0.add(new BlockPos(-3,0,4));
//        blockOffsetEAST0.add(new BlockPos(-3,1,4));
//        blockOffsetEAST0.add(new BlockPos(-3,2,4));
//    }

}
