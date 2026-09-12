package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.Pattern.CTNHPredicates;
import io.github.cpearl0.ctnhcore.client.renderer.DynamicCasingRender;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.ChemicalPlantMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.NeutronActivatorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.LargeNaquadahReactorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.registry.*;
import io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Prefix;
import com.ctnhlang.Suffix;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.Comparator;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

@Prefix("multiblock")
@Suffix("tooltip")
public class GTNNMultiblocks {

    public static void init() {
        CHEMICAL_PLANT = REGISTRATE.multiblock(
                "exxonmobil_chemical_plant",
                ChemicalPlantMachine::new)
                .cnLangValue("埃克森美孚化工厂")
                .recipeTypes(CTNHRecipeTypes.CHEMICAL_PLANT_RECIPES)
                .rotationState(RotationState.NON_Y_AXIS)
                .tooltips(chemical_plant)
                .recipeModifiers(CTNHRecipeModifiers::chemicalPlantModifier, GTRecipeModifiers.OC_NON_PERFECT)
                .appearanceBlock(GTBlocks.CASING_BRONZE_BRICKS)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("VVVVVVV", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                        .aisle("VBBBBBV", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                        .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                        .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                        .aisle("VBBBBBV", "#BCCCB#", "##DDD##", "##CCC##", "##DDD##", "#BCCCB#", "AAAAAAA")
                        .aisle("VBBBBBV", "#BBBBB#", "#######", "#######", "#######", "#BBBBB#", "AAAAAAA")
                        .aisle("VVVSVVV", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                        .where("S", controller(blocks(definition.getBlock())))
                        .where("V", CTNHPredicates.plantCasings
                                .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                .or(abilities(PartAbility.EXPORT_FLUIDS))
                                .or(abilities(PartAbility.EXPORT_ITEMS))
                                .or(abilities(PartAbility.IMPORT_ITEMS))
                                .or(abilities(PartAbility.IMPORT_FLUIDS))
                                .or(abilities(CTNHPartAbility.CATALYST).setMaxGlobalLimited(2))
                                .or(abilities(PartAbility.INPUT_ENERGY)
                                        .setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                        .where("A", CTNHPredicates.plantCasings)
                        .where("D", CTNHPredicates.pipeBlock)
                        .where("C", CTNHPredicates.coilBlock)
                        .where("B", CTNHPredicates.machineCasing)
                        .where("#", Predicates.any())
                        .build())
                // .shapeInfos(definition ->{
                // int maxSize = Ints.max(
                // CTNHBlockMaps.CasingBlock.size(),
                // CTNHBlockMaps.PipeBlock.size(),
                // CTNHBlockMaps.MachineCasingBlock.size(),
                // CTNHBlockMaps.CoilBlock.size()
                // );
                // return StructureUtils.getMatchingShapes(
                // definition.getPatternFactory().get(),
                // maxSize
                // );
                // })
                .partSorter(Comparator.comparingInt(a -> a.self().getPos().getY()))
                .model(GTMachineModels.createWorkableCasingMachineModel(
                        GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        CTNHCore.id("block/multiblock/chemical_plant"))
                        .andThen(b -> b.addDynamicRenderer(() -> new DynamicCasingRender(
                                GTBlocks.CASING_BRONZE_BRICKS.getDefaultState(),
                                DynamicCasingRender.ModelType.ChemicalPlant))))
                .register();
        NEUTRON_ACTIVATOR = REGISTRATE.multiblock("neutron_activator", NeutronActivatorMachine::new)
                .cnLangValue("中子活化器")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeTypes(CTNHRecipeTypes.NEUTRON_ACTIVATOR_RECIPES)
                .tooltips(neutron_activator[0].translate(),
                        neutron_activator[1].translate(),
                        neutron_activator[2].translate(),
                        neutron_activator[3].translate(),
                        neutron_activator[4].translate(),
                        neutron_activator[5].translate())
                .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
                .pattern(definition -> FactoryBlockPattern
                        .start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                        .aisle("AASAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                        .aisle("C###C", "#DDD#", "#DED#", "#DDD#", "C###C").setRepeatable(4, 34)
                        .aisle("VVVVV", "VBBBV", "VBBBV", "VBBBV", "VVVVV")
                        .where("S", controller(blocks(definition.get())))
                        .where(
                                "V",
                                blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).or(abilities(PartAbility.IMPORT_FLUIDS))
                                        .or(abilities(PartAbility.IMPORT_ITEMS)))
                        .where(
                                "A",
                                blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).or(abilities(PartAbility.EXPORT_FLUIDS))
                                        .or(abilities(PartAbility.EXPORT_ITEMS))
                                        .or(abilities(CTNHPartAbility.NEUTRON_ACCELERATOR))
                                        .or(abilities(CTNHPartAbility.NEUTRON_SENSOR))
                                        .or(autoAbilities(true, false, false)))
                        .where("B", blocks(CTNHBlocks.PROCESS_MACHINE_CASING.get()))
                        .where("C", blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Steel)))
                        .where("D", blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                        .where("E", blocks(CTNHBlocks.HIGH_SPEED_PIPE_BLOCK.get()))
                        .where("#", any()).build()

                )
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                        CTNHCore.id("block/multiblock/neutron_activator"))
                .register();
        LARGE_NAQUADAH_REACTOR = REGISTRATE.multiblock("large_naquadah_reactor", LargeNaquadahReactorMachine::new)
                .cnLangValue("大型硅岩发电堆")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeTypes(CTNHRecipeTypes.LARGE_NAQUADAH_REACTOR_RECIPES)
                .tooltips(large_naquadah_reactor[0].translate(),
                        large_naquadah_reactor[1].translate(),
                        large_naquadah_reactor[2].translate(),
                        large_naquadah_reactor[3].translate(),
                        large_naquadah_reactor[4].translate(),
                        large_naquadah_reactor[5].translate(),
                        large_naquadah_reactor[6].translate(),
                        large_naquadah_reactor[7].translate(),
                        large_naquadah_reactor[8].translate())
                .recipeModifier(LargeNaquadahReactorMachine::modifyRecipe)
                .appearanceBlock(CTNHBlocks.RADIATION_PROOF_MACHINE_CASING)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAAAAAA", "VBBBBBV", "VVVVVVV", "B#####B", "B#####B", "B#####B", "B#####B", "VVVVVVV")
                        .aisle("AAAAAAA", "B#####B", "V#####V", "#######", "#######", "#######", "#######", "VVVVVVV")
                        .aisle("AAAAAAA", "B#CCC#B", "V#CCC#V", "##CCC##", "##CCC##", "##CCC##", "##CCC##", "VVVVVVV")
                        .aisle("AAAAAAA", "B#CCC#B", "V#CDC#V", "##CDC##", "##CDC##", "##CDC##", "##CDC##", "VVVVVVV")
                        .aisle("AAAAAAA", "B#CCC#B", "V#CCC#V", "##CCC##", "##CCC##", "##CCC##", "##CCC##", "VVVVVVV")
                        .aisle("AAAAAAA", "B#####B", "V#####V", "#######", "#######", "#######", "#######", "VVVVVVV")
                        .aisle("AAASAAA", "VBBBBBV", "VVVVVVV", "B#####B", "B#####B", "B#####B", "B#####B", "VVVVVVV")
                        .where("S", controller(blocks(definition.get())))
                        .where("V", blocks(CTNHBlocks.RADIATION_PROOF_MACHINE_CASING.get())).where(
                                "A", blocks(CTNHBlocks.RADIATION_PROOF_MACHINE_CASING.get()).or(
                                        autoAbilities(
                                                true, false, false))
                                        .or(abilities(PartAbility.OUTPUT_ENERGY, PartAbility.OUTPUT_LASER)
                                                .setMinGlobalLimited(1, 1).setMaxGlobalLimited(6))
                                        .or(abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                        .or(abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1)))
                        .where("B",
                                blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, CTNHMaterials.RadiationProtection)))
                        .where("C", blocks(CTNHBlocks.FIELD_RESTRICTION_CASING.get()))
                        .where("D", blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get())).where("#", air())
                        .build())
                .workableCasingModel(
                        CTNHCore.id("block/casings/solid/radiation_proof_machine_casing"),
                        CTNHCore.id("block/multiblock/large_naquadah_reactor"))
                .register();
        LARGE_DEHYDRATOR = REGISTRATE.multiblock("large_dehydrator", RecipeElectricMultiblockMachine::new)
                .cnLangValue("大型脱水机")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeTypes(CTNHRecipeTypes.DEHYDRATOR_RECIPES)
                .recipeModifiers(
                        GTRecipeModifiers.DEFAULT_ENVIRONMENT_REQUIREMENT,
                        GTRecipeModifiers.PARALLEL_HATCH,
                        GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                .appearanceBlock(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("XXX", "CCC", "CCC", "CCC", "XXX")
                        .aisle("XXX", "C#C", "C#C", "C#C", "XXX")
                        .aisle("XSX", "CCC", "CCC", "CCC", "XXX")
                        .where('S', controller(blocks(definition.getBlock())))
                        .where(
                                'X', blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()).setMinGlobalLimited(9)
                                        .or(autoAbilities(definition.getRecipeTypes()))
                                        .or(autoAbilities(true, false, true)))
                        .where('C', blocks(GTBlocks.COIL_NAQUADAH.get()))
                        .where('#', air())
                        .build())
                .workableCasingModel(
                        GTCEu.id("block/casings/gcym/high_temperature_smelting_casing"),
                        GTCEu.id("block/multiblock/gcym/large_assembler"))
                .register();
    }

    public static MultiblockMachineDefinition CHEMICAL_PLANT;

    public static MultiblockMachineDefinition NEUTRON_ACTIVATOR;

    public static MultiblockMachineDefinition LARGE_DEHYDRATOR;

    public static MultiblockMachineDefinition LARGE_NAQUADAH_REACTOR;
    @CN({
            "§6线圈：§e+50%§6 速度/级",
            "§b管道方块：§e+2§b 并行 及 §e-20%§b 催化剂消耗概率/级",
            "§5电压机械方块：配方电压支持等级",
            "§1外壳机械方块：提供配方条件",
            "§o§7重工业，现在就在你家门口！"
    })
    @EN({
            "§6Coil: §e+50%§6 speed/tier",
            "§bPipe: §e+2§b parallel and §e-20%§b catalyst consumption/tier",
            "§5Voltage Casing: Recipe voltage support level",
            "§1Machine Casing: Provide recipe condition",
            "§o§7Heavy industry, right at your doorstep now!"
    })
    static Lang[] chemical_plant;
    @CN({
            "§o§7超光速运动!",
            "§6额外的高速管道方块提供配方时间减免，同时降低中子加速器的效率",
            "§6没有中子加速器运行时，中子动能每秒降低§e72KeV§6中子动能,运行配方时如果中子动能不在配方要求范围内，则只输出放射性废料！",
            "§6输入石墨/铍粉可以立即吸收§e10MeV§6中子动能",
            "§6当中子动能超过§41200MeV§6后将会爆炸！",
            "§6运行配方时如果中子动能不在配方要求范围内，则只输出§4放射性废料！§6"
    })
    @EN({
            "§o§7Faster-Light Movement!",
            "§6Extra high-speed pipe blocks provide recipe time reduction, and lower the efficiency of the neutron accelerator",
            "§6Without a neutron accelerator running, neutron kinetic energy decreases by §e72KeV §6neutron kinetic energy per second",
            "§6Absorb §e10MeV §6neutron kinetic energy immediately when input cesium or beryllium dust",
            "§6When the neutron kinetic energy exceeds §41200MeV§6, it will explode!",
            "§6if the neutron's kinetic energy falls outside the required range specified in the recipe, only radioactive waste will be produced as the output."
    })
    static Lang[] neutron_activator;

    @CN({
            "§o§7环境友好型!",
            "§6从高能流体中获取能量",
            "§6运行时需要消耗§e2400mB/s§6液态空气， 否则你的燃料将会被销毁",
            "§6输入液态燃料, 输入仓内出现不止§4一种§6燃料时，反应堆将会爆炸",
            "§6可以消耗§e1000mB/s§6冷却液获得§e150%效率提升",
            "§6消耗激发流体以提升输出功率",
            "液态铯        | §e2x功率 | §6180mB/s",
            "液态铀-235  | §e3x功率 | §6180mB/s",
            "液态硅岩      | §e4x功率 | §620mB/s"
    })
    @EN({
            "§o§7Environment Friendly!",
            "§6Get energy from high-power fluid",
            "§6When the reactor is running, it needs to consume §e2400mB/s§6 liquid air, otherwise your fuel will be destroyed",
            "§6Input liquid fuel, if there are more than §4one §6fuel in the input hatch, the reactor will explode",
            "§6Can consume §e1000mB/s§6 cooling fluid to get §e150% efficiency",
            "§6Consume igniting fluid to increase output power",
            "Cesium             | §e2x power | §6180mB/s",
            "Uranium-235   | §e3x power | §6180mB/s",
            "Naquadah       | §e4x power | §620mB/s"
    })
    static Lang[] large_naquadah_reactor;
}
