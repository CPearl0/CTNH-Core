package io.github.cpearl0.ctnhcore.registry.machines;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.machine.cover.CreativeEnergyCover;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CreativeEnergyHatchPartMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CreativeInputBusPartMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CreativeInputHatchPartMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CreativeLaserHatchPartMachine;
import io.github.cpearl0.ctnhcore.common.machine.simple.DigitalMiner;
import io.github.cpearl0.ctnhcore.common.machine.simple.EfficiencyGeneratorMachine;
import io.github.cpearl0.ctnhcore.data.machines.GTNNMachines;
import io.github.cpearl0.ctnhcore.registry.CTNHCreativeModeTabs;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.utils.CTNHMachineUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer;
import com.gregtechceu.gtceu.common.data.GTCovers;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import com.ctnhlang.Prefix;
import com.ctnhlang.Suffix;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.OUT;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.GTMachines.CREATIVE_TOOLTIPS;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.utils.CTNHMachineUtils.*;

@Prefix("machine")
@Suffix("tooltip")
public class CTNHMachines {

    @Key("block.ctnhcore.abyssalalloy_coil_block")
    @CN("渊狱合金线圈")
    public static Lang blockAbyssalalloyCoilBlock;

    @Key("block.ctnhcore.atoms_split_blocks")
    @CN("原子裂解方块")
    public static Lang blockAtomsSplitBlocks;

    @Key("block.ctnhcore.black_titanium_coil_block")
    @CN("黑钛合金线圈")
    public static Lang blockBlackTitaniumCoilBlock;

    @Key("block.ctnhcore.hyper_plasma_turbine_rotor")
    @CN("超極等离子涡轮转子")
    public static Lang blockHyperPlasmaTurbineRotor;

    @Key("block.ctnhcore.infinity_coil_block")
    @CN("无尽线圈")
    public static Lang blockInfinityCoilBlock;

    @Key("block.ctnhcore.naquadah_firebox_casing")
    @CN("硅岩燃烧室")
    public static Lang blockNaquadahFireboxCasing;

    @Key("block.ctnhcore.pikyonium_coil_block")
    @CN("皮卡优线圈")
    public static Lang blockPikyoniumCoilBlock;

    @Key("block.ctnhcore.starmetal_coil_block")
    @CN("星辉线圈")
    public static Lang blockStarmetalCoilBlock;

    @Key("block.ctnhcore.titansteel_coil_block")
    @CN("泰坦钢线圈")
    public static Lang blockTitansteelCoilBlock;

    @Key("block.ctnhcore.advance_assembly_line")
    @CN("进阶装配线")
    public static Lang blockAdvanceAssemblyLine;

    @Key("block.ctnhcore.advanced_coke_oven")
    @CN("高级焦炉")
    public static Lang blockAdvancedCokeOven;

    @Key("block.ctnhcore.advanced_wind_power_array")
    @CN("进阶风力发电阵列")
    public static Lang blockAdvancedWindPowerArray;

    @Key("block.ctnhcore.arc_generator")
    @CN("电弧撕裂者")
    public static Lang blockArcGenerator;

    @Key("block.ctnhcore.arc_generator_mk1")
    @CN("超压电弧撕裂者MK1")
    public static Lang blockArcGeneratorMk1;

    @Key("block.ctnhcore.arc_generator_mk2")
    @CN("过载电弧撕裂者MK1")
    public static Lang blockArcGeneratorMk2;

    @Key("block.ctnhcore.astronomical_observatory")
    @CN("天文台")
    public static Lang blockAstronomicalObservatory;

    @Key("block.ctnhcore.bedrock_drilling_rigs")
    @CN("基岩钻机")
    public static Lang blockBedrockDrillingRigs;

    @Key("block.ctnhcore.bio_reactor")
    @CN("生物反应器")
    public static Lang blockBioReactor;

    @Key("block.ctnhcore.blaze_blast_furnace")
    @CN("§c炽焱高炉")
    public static Lang blockBlazeBlastFurnace;

    @Key("block.ctnhcore.chemical_vapor_deposition_machine")
    @CN("化学气相沉积器")
    public static Lang blockChemicalVaporDepositionMachine;

    @Key("block.ctnhcore.cnc_alloy_smelter")
    @CN("数控合金冶炼炉")
    public static Lang blockCncAlloySmelter;

    @Key("block.ctnhcore.coke_tower")
    @CN("焦化塔")
    public static Lang blockCokeTower;

    @Key("block.ctnhcore.combined_vapor_deposition_facility")
    @CN("集成沉积工厂")
    public static Lang blockCombinedVaporDepositionFacility;

    @Key("block.ctnhcore.condensing_discrete")
    @CN("冷凝离散塔")
    public static Lang blockCondensingDiscrete;

    @Key("block.ctnhcore.cryotheum_freezer")
    @CN("凛冰冷冻机")
    public static Lang blockCryotheumFreezer;

    @Key("block.ctnhcore.crystallizer")
    @CN("结晶器")
    public static Lang blockCrystallizer;

    @Key("block.ctnhcore.cultivationroom")
    @CN("培养室")
    public static Lang blockCultivationroom;

    @Key("block.ctnhcore.decay_pools_machine")
    @CN("衰变罐")
    public static Lang blockDecayPoolsMachine;

    @Key("block.ctnhcore.digestion_tank")
    @CN("化粪池")
    public static Lang blockDigestionTank;

    @Key("block.ctnhcore.dimensional_gas_collection_chamber")
    @CN("维度集气室")
    public static Lang blockDimensionalGasCollectionChamber;

    @Key("block.ctnhcore.drone_holder")
    @CN("无人机支架")
    public static Lang blockDroneHolder;

    @Key("block.ctnhcore.ev_chemical_generator")
    @CN("化学能发电机")
    public static Lang blockEvChemicalGenerator;

    @Key("block.ctnhcore.ev_dehydrator")
    @CN("§5高级脱水机 III§r")
    public static Lang blockEvDehydrator;

    @Key("block.ctnhcore.ev_naquadah_reactor")
    @CN("§5高级硅岩发电机 I")
    public static Lang blockEvNaquadahReactor;

    @Key("block.ctnhcore.ev_personal_computer")
    @CN("§5EV§r个人计算机")
    public static Lang blockEvPersonalComputer;

    @Key("block.ctnhcore.ev_rocket_engine")
    @CN("§5高级火箭引擎发电机 I")
    public static Lang blockEvRocketEngine;

    @Key("block.ctnhcore.fermenting_tank")
    @CN("发酵罐")
    public static Lang blockFermentingTank;

    @Key("block.ctnhcore.forest_sea_tree_farm")
    @CN("林海树场")
    public static Lang blockForestSeaTreeFarm;

    @Key("block.ctnhcore.fuel_refining_factory")
    @CN("燃料精炼厂")
    public static Lang blockFuelRefiningFactory;

    @Key("block.ctnhcore.gas_centrifuge")
    @CN("气体离心机")
    public static Lang blockGasCentrifuge;

    @Key("block.ctnhcore.greenhouse")
    @CN("温室")
    public static Lang blockGreenhouse;

    @Key("block.ctnhcore.hot_coolant_turbine")
    @CN("热冷却涡轮")
    public static Lang blockHotCoolantTurbine;

    @Key("block.ctnhcore.hv_dehydrator")
    @CN("§6高级脱水机 II§r")
    public static Lang blockHvDehydrator;

    @Key("block.ctnhcore.hv_digital_miner")
    @CN("§6进阶数字型采矿机 II§r")
    public static Lang blockHvDigitalMiner;

    @Key("block.ctnhcore.hv_energy_output_hatch_4a")
    @CN("4安§6HV§r动力仓")
    public static Lang blockHvEnergyOutputHatch4a;

    @Key("block.ctnhcore.hv_personal_computer")
    @CN("§6HV§r个人计算机")
    public static Lang blockHvPersonalComputer;

    @Key("block.ctnhcore.high_pressure_alkali_digester")
    @CN("高压碱煮釜")
    public static Lang blockHighPressureAlkaliDigester;

    @Key("block.ctnhcore.hyper_plasma_turbine")
    @CN("超極等离子涡轮")
    public static Lang blockHyperPlasmaTurbine;

    @Key("block.ctnhcore.industrial_primitive_blast_furnace")
    @CN("工业土高炉")
    public static Lang blockIndustrialPrimitiveBlastFurnace;

    @Key("block.ctnhcore.ion_exchanger")
    @CN("离子交换机")
    public static Lang blockIonExchanger;

    @Key("block.ctnhcore.iv_chemical_generator")
    @CN("化学能吞噬者")
    public static Lang blockIvChemicalGenerator;

    @Key("block.ctnhcore.iv_dehydrator")
    @CN("§9精英脱水机 §r")
    public static Lang blockIvDehydrator;

    @Key("block.ctnhcore.iv_naquadah_reactor")
    @CN("§9精英硅岩发电机 II")
    public static Lang blockIvNaquadahReactor;

    @Key("block.ctnhcore.iv_personal_computer")
    @CN("§9IV§r个人计算机")
    public static Lang blockIvPersonalComputer;

    @Key("block.ctnhcore.iv_rocket_engine")
    @CN("§9精英火箭引擎发电机 II")
    public static Lang blockIvRocketEngine;

    @Key("block.ctnhcore.large_bottle")
    @CN("发酵瓶")
    public static Lang blockLargeBottle;

    @Key("block.ctnhcore.large_fermenting_tank")
    @CN("大型发酵罐")
    public static Lang blockLargeFermentingTank;

    @Key("block.ctnhcore.large_steel_alloy_furnace")
    @CN("大型钢制合金炉")
    public static Lang blockLargeSteelAlloyFurnace;

    @Key("block.ctnhcore.large_steel_furnace")
    @CN("大型钢制熔炉")
    public static Lang blockLargeSteelFurnace;

    @Key("block.ctnhcore.lasersorder")
    @CN("激光分配仪")
    public static Lang blockLaserSorter;

    @Key("block.ctnhcore.luv_compressed_fusion_reactor")
    @CN("压缩核聚变反应堆控制电脑 MK-I")
    public static Lang blockLuvCompressedFusionReactor;

    @Key("block.ctnhcore.luv_dehydrator")
    @CN("§d精英脱水机 II§r")
    public static Lang blockLuvDehydrator;

    @Key("block.ctnhcore.luv_naquadah_reactor")
    @CN("§d精英硅岩发电机 III")
    public static Lang blockLuvNaquadahReactor;

    @Key("block.ctnhcore.luv_personal_computer")
    @CN("§dLuV§r个人计算机")
    public static Lang blockLuvPersonalComputer;

    @Key("block.ctnhcore.luv_rocket_engine")
    @CN("§d精英火箭引擎发电机 III")
    public static Lang blockLuvRocketEngine;

    @Key("block.ctnhcore.lv_digital_miner")
    @CN("数字型采矿机")
    public static Lang blockLvDigitalMiner;

    @Key("block.ctnhcore.lv_energy_output_hatch_4a")
    @CN("4安§7LV§r动力仓")
    public static Lang blockLvEnergyOutputHatch4a;

    @Key("block.ctnhcore.lv_personal_computer")
    @CN("§7LV§r个人计算机")
    public static Lang blockLvPersonalComputer;

    @Key("block.ctnhcore.lv_rotor_holder")
    @CN("§7LV§r转子支架")
    public static Lang blockLvRotorHolder;

    @Key("block.ctnhcore.martial_morality_eye")
    @CN("武德之眼")
    public static Lang blockMartialMoralityEye;

    @Key("block.ctnhcore.max_parallel_hatch")
    @CN("§c§lMAX§r并行控制仓")
    public static Lang blockMaxParallelHatch;

    @Key("block.ctnhcore.meadow")
    @CN("§6牧场")
    public static Lang blockMeadow;

    @Key("block.ctnhcore.mechanical_centrifuge")
    @CN("机械离心厂")
    public static Lang blockMechanicalCentrifuge;

    @Key("block.ctnhcore.mechanical_extractor")
    @CN("机械熔炼厂")
    public static Lang blockMechanicalExtractor;

    @Key("block.ctnhcore.mechanical_lathe")
    @CN("机械车床厂")
    public static Lang blockMechanicalLathe;

    @Key("block.ctnhcore.mechanical_mixer")
    @CN("机械搅拌厂")
    public static Lang blockMechanicalMixer;

    @Key("block.ctnhcore.mechanical_pressor")
    @CN("机械辊压厂")
    public static Lang blockMechanicalPressor;

    @Key("block.ctnhcore.mechanical_sifter")
    @CN("机械筛选厂")
    public static Lang blockMechanicalSifter;

    @Key("block.ctnhcore.mega_lcr")
    @CN("巨型化学反应釜")
    public static Lang blockMegaLcr;

    @Key("block.ctnhcore.mega_oil_cracking_unit")
    @CN("巨型原油裂解厂")
    public static Lang blockMegaOilCrackingUnit;

    @Key("block.ctnhcore.mv_dehydrator")
    @CN("§b高级脱水机 §r")
    public static Lang blockMvDehydrator;

    @Key("block.ctnhcore.mv_digital_miner")
    @CN("§b进阶数字型采矿机§r")
    public static Lang blockMvDigitalMiner;

    @Key("block.ctnhcore.mv_energy_output_hatch_4a")
    @CN("4安§bMV§r动力仓")
    public static Lang blockMvEnergyOutputHatch4a;

    @Key("block.ctnhcore.mv_personal_computer")
    @CN("§bMV§r个人计算机")
    public static Lang blockMvPersonalComputer;

    @Key("block.ctnhcore.mv_rotor_holder")
    @CN("§bMV§r转子支架")
    public static Lang blockMvRotorHolder;

    @Key("block.ctnhcore.nanogenetor")
    @CN("纳米摩擦发电机")
    public static Lang blockNanoGenerator;

    @Key("block.ctnhcore.naq_reactor_mk3")
    @CN("超级硅岩反应堆")
    public static Lang blockNaqReactorMk3;

    @Key("block.ctnhcore.neruo_martix_compiler")
    @CN("神经矩阵编译器")
    public static Lang blockNeuroMatrixCompiler;

    @Key("block.ctnhcore.opv_parallel_hatch")
    @CN("§9§lOpV§r并行控制仓")
    public static Lang blockOpvParallelHatch;

    @Key("block.ctnhcore.opv_personal_computer")
    @CN("§9§lOpV§r个人计算机")
    public static Lang blockOpvPersonalComputer;

    @Key("block.ctnhcore.oxidation_roasting_furnace")
    @CN("氧化焙烧炉")
    public static Lang blockOxidationRoastingFurnace;

    @Key("block.ctnhcore.photovoltaic_drone_station")
    @CN("光伏无人机道标基站")
    public static Lang blockPhotovoltaicDroneStation;

    @Key("block.ctnhcore.photovoltaic_power_station_energetic")
    @CN("充能光伏发电站")
    public static Lang blockPhotovoltaicPowerStationEnergetic;

    @Key("block.ctnhcore.photovoltaic_power_station_pulsating")
    @CN("脉冲光伏发电站")
    public static Lang blockPhotovoltaicPowerStationPulsating;

    @Key("block.ctnhcore.photovoltaic_power_station_vibrant")
    @CN("振动光伏发电站")
    public static Lang blockPhotovoltaicPowerStationVibrant;

    @Key("block.ctnhcore.plasma_alloy_blast_smelter")
    @CN("等离子合金冶炼转底炉")
    public static Lang blockPlasmaAlloyBlastSmelter;

    @Key("block.ctnhcore.plasma_condenser")
    @CN("等离子冷凝器")
    public static Lang blockPlasmaCondenser;

    @Key("block.ctnhcore.reduction_precipitation_tank")
    @CN("还原沉淀槽")
    public static Lang blockReductionPrecipitationTank;

    @Key("block.ctnhcore.seawater_desalting_factory")
    @CN("海水晒盐工厂")
    public static Lang blockSeawaterDesaltingFactory;

    @Key("block.ctnhcore.silica_rock_fuel_refinery")
    @CN("硅岩燃料精炼厂")
    public static Lang blockSilicaRockFuelRefinery;

    @Key("block.ctnhcore.sinope_chemical")
    @CN("SINOPE化工厂")
    public static Lang blockSinopeChemical;

    @Key("block.ctnhcore.sintering_kiln")
    @CN("烧结窑")
    public static Lang blockSinteringKiln;

    @Key("block.ctnhcore.slaughter_house")
    @CN("屠宰场")
    public static Lang blockSlaughterHouse;

    @Key("block.ctnhcore.solvent_extraction_tower")
    @CN("溶剂萃取塔")
    public static Lang blockSolventExtractionTower;

    @Key("block.ctnhcore.space_photovoltai_cbase_station")
    @CN("太空光伏基站")
    public static Lang blockSpacePhotovoltaicBaseStation;

    @Key("block.ctnhcore.sterile_cleanroom_maintenance_hatch")
    @CN("无菌超净间维护仓")
    public static Lang blockSterileCleanroomMaintenanceHatch;

    @Key("block.ctnhcore.super_centrifuge")
    @CN("超速离心机")
    public static Lang blockSuperCentrifuge;

    @Key("block.ctnhcore.super_ebf")
    @CN("超级电力高炉")
    public static Lang blockSuperEbf;

    @Key("block.ctnhcore.super_wind_power_array")
    @CN("超级风力发电阵列")
    public static Lang blockSuperWindPowerArray;

    @Key("block.ctnhcore.superconducting_penning_trap")
    @CN("超导潘宁势阱")
    public static Lang blockSuperconductingPenningTrap;

    @Key("block.ctnhcore.sweat_shop")
    @CN("§4血汗工厂")
    public static Lang blockSweatShop;

    @Key("block.ctnhcore.uev_parallel_hatch")
    @CN("§aUEV§r并行控制仓")
    public static Lang blockUevParallelHatch;

    @Key("block.ctnhcore.uev_personal_computer")
    @CN("§aUEV§r个人计算机")
    public static Lang blockUevPersonalComputer;

    @Key("block.ctnhcore.uhv_fluid_drilling_inf")
    @CN("无尽流体钻机")
    public static Lang blockUhvFluidDrillingInf;

    @Key("block.ctnhcore.uhv_neuro_compiler")
    @CN("§4UHV§r神经拟合仓")
    public static Lang blockUhvNeuroCompiler;

    @Key("block.ctnhcore.uhv_parallel_hatch")
    @CN("§4UHV§r并行控制仓")
    public static Lang blockUhvParallelHatch;

    @Key("block.ctnhcore.uhv_personal_computer")
    @CN("§4UHV§r个人计算机")
    public static Lang blockUhvPersonalComputer;

    @Key("block.ctnhcore.uiv_parallel_hatch")
    @CN("§2UIV§r并行控制仓")
    public static Lang blockUivParallelHatch;

    @Key("block.ctnhcore.uiv_personal_computer")
    @CN("§2UIV§r个人计算机")
    public static Lang blockUivPersonalComputer;

    @Key("block.ctnhcore.ultimate_combustion_engine")
    @CN("无尽内燃引擎")
    public static Lang blockUltimateCombustionEngine;

    @Key("block.ctnhcore.ultrasonic_apparatus")
    @CN("超声破碎仪")
    public static Lang blockUltrasonicApparatus;

    @Key("block.ctnhcore.ulv_rotor_holder")
    @CN("§8ULV§r转子支架")
    public static Lang blockUlvRotorHolder;

    @Key("block.ctnhcore.underfloor_heating_system")
    @CN("地暖")
    public static Lang blockUnderfloorHeatingSystem;

    @Key("block.ctnhcore.uv_compressed_fusion_reactor")
    @CN("压缩核聚变反应堆控制电脑 MK-III")
    public static Lang blockUvCompressedFusionReactor;

    @Key("block.ctnhcore.uv_naquadah_reactor")
    @CN("§3终极硅岩发电机 V")
    public static Lang blockUvNaquadahReactor;

    @Key("block.ctnhcore.uv_neuro_compiler")
    @CN("§3UV§r神经拟合仓")
    public static Lang blockUvNeuroCompiler;

    @Key("block.ctnhcore.uv_personal_computer")
    @CN("§3UV§r个人计算机")
    public static Lang blockUvPersonalComputer;

    @Key("block.ctnhcore.uxv_parallel_hatch")
    @CN("§eUXV§r并行控制仓")
    public static Lang blockUxvParallelHatch;

    @Key("block.ctnhcore.uxv_personal_computer")
    @CN("§eUXV§r个人计算机")
    public static Lang blockUxvPersonalComputer;

    @Key("block.ctnhcore.vacuum_sintering_tower")
    @CN("真空烧结厂")
    public static Lang blockVacuumSinteringTower;

    @Key("block.ctnhcore.void_miner")
    @CN("虚空采矿场")
    public static Lang blockVoidMiner;

    @Key("block.ctnhcore.wide_particle_accelerator")
    @CN("广粒子加速器")
    public static Lang blockWideParticleAccelerator;

    @Key("block.ctnhcore.wind_power_array")
    @CN("风力发电阵列")
    public static Lang blockWindPowerArray;

    @Key("block.ctnhcore.zpm_compressed_fusion_reactor")
    @CN("压缩核聚变反应堆控制电脑 MK-II")
    public static Lang blockZpmCompressedFusionReactor;

    @Key("block.ctnhcore.zpm_dehydrator")
    @CN("§c精英脱水机 III§r")
    public static Lang blockZpmDehydrator;

    @Key("block.ctnhcore.zpm_naquadah_reactor")
    @CN("§c精英硅岩发电机 IV")
    public static Lang blockZpmNaquadahReactor;

    @Key("block.ctnhcore.zpm_personal_computer")
    @CN("§cZPM§r个人计算机")
    public static Lang blockZpmPersonalComputer;

    @CN("效率: %s%%")
    @EN("Efficiency: %s%%")
    public static Lang machineNaquadahReactorTooltip;

    @CN("效率: %s%%")
    @EN("Efficiency: %s%%")
    public static Lang machineRocketEngineTooltip;

    @Key("ctnhcore.copyright.info")
    @CN("§6由CTNH添加")
    @EN("§6Added by CTNH")
    public static Lang ctnhCopyrightInfo;

    @CN("§7来自GTMThings的挖矿黑科技，速度更快且无采矿管道，仅挖取矿石")
    @EN("§7From GTMThings, faster speed and no mining pipes, only mining ores")
    public static Lang ctnhMachineDigitalMinerTooltip0;

    @CN("§b工作时自带自身区块强加载")
    @EN("§bForce loading self chunk while working")
    public static Lang ctnhMachineDigitalMinerTooltip1;

    @CN("工作时消耗§f%d EU/t§7，每个方块需要§f%d§7刻")
    @EN("Uses §f%d EU/t §7while working, each block takes §f%d§7 ticks")
    public static Lang ctnhMachineDigitalMinerTooltip2;

    @Key("ctnhcore.recipe_logic.insufficient_cwut")
    @CN("算力不足")
    @EN("Insufficient Computation")
    public static Lang ctnhRecipeLogicInsufficientCwut;

    @CN("允许同时处理至多4096个配方。")
    @EN("Allows up to 4096 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk10Tooltip;

    @CN("允许同时处理至多16384个配方。")
    @EN("Allows up to 16384 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk11Tooltip;

    @CN("允许同时处理至多65536个配方。")
    @EN("Allows up to 65536 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk12Tooltip;

    @CN("允许同时处理至多262144个配方。")
    @EN("Allows up to 262144 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk13Tooltip;

    @CN("允许同时处理至多1048576个配方。")
    @EN("Allows up to 1048576 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk14Tooltip;

    @CN("允许同时处理至多1024个配方。")
    @EN("Allows up to 1024 recipes to be processed simultaneously.")
    public static Lang gtceuMachineParallelHatchMk9Tooltip;

    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }

    public static MachineDefinition CATALYST_HATCH;
    public static MachineDefinition[] DEHYDRATOR;
    public static MachineDefinition[] NAQUADAH_REACTOR;
    public static MachineDefinition[] ROCKET_ENGINE;
    public static MachineDefinition CIRCUIT_BUS;
    public static MachineDefinition DRONEHOLDER;
    public static MachineDefinition[] COMPILERMACHINE;
    public static MachineDefinition[] PERSONAL_COMPUTER;
    public static MachineDefinition[] PARALLEL_HATCH;
    public static MachineDefinition[] ENERGY_OUTPUT_HATCH_4A_LOWER;
    public static MachineDefinition[] ROTOR_HOLDER_EXTEND;
    public static MachineDefinition STERILE_CLEANROOM_MAINTENANCE_HATCH;
    public static MachineDefinition[] DIGITAL_MINER;
    public static MachineDefinition CREATIVE_ENERGY_INPUT_HATCH;
    public static MachineDefinition CREATIVE_ITEM_INPUT_BUS;
    public static MachineDefinition CREATIVE_FLUID_INPUT_HATCH;
    public static MachineDefinition CREATIVE_LASER_INPUT_HATCH;
    public static CoverDefinition CREATIVE_ENERGY_COVER_DEF;

    private static Lang parallelHatchTooltip(int tier) {
        return switch (tier) {
            case UHV -> gtceuMachineParallelHatchMk9Tooltip;
            case UEV -> gtceuMachineParallelHatchMk10Tooltip;
            case UIV -> gtceuMachineParallelHatchMk11Tooltip;
            case UXV -> gtceuMachineParallelHatchMk12Tooltip;
            case OpV -> gtceuMachineParallelHatchMk13Tooltip;
            case MAX -> gtceuMachineParallelHatchMk14Tooltip;
            default -> throw new IllegalArgumentException("Unsupported parallel hatch tier: " + tier);
        };
    }

    public static void init() {
        GTNNMachines.init();

        CATALYST_HATCH = REGISTRATE
                .machine("catalyst_hatch", CatalystHatchPartMachine::new)
                .cnLangValue("催化剂仓")
                .langValue("Catalyst Hatch")
                .tier(EV)
                .rotationState(RotationState.ALL)
                .abilities(CTNHPartAbility.CATALYST)
                .colorOverlayTieredHullModel("overlay_catalyst_in", null, "overlay_catalyst_hatch")
                .tooltips()
                .register();
        DEHYDRATOR = CTNHMachineUtils.registerSimpleMachines("dehydrator",
                CTNHRecipeTypes.DEHYDRATOR_RECIPES, GTValues.tiersBetween(MV, ZPM));
        NAQUADAH_REACTOR = CTNHMachineUtils.registerEfficiencyGeneratorMachines(
                "naquadah_reactor",
                CTNHRecipeTypes.NAQUADAH_REACTOR_RECIPES,
                EfficiencyGeneratorMachine::recipeModifier,
                tier -> tier * 32000,
                EfficiencyGeneratorMachine::naquadahReactor,
                tiersBetween(EV, UV));
        ROCKET_ENGINE = registerEfficiencyGeneratorMachines(
                "rocket_engine",
                CTNHRecipeTypes.ROCKET_ENGINE_RECIPES,
                CTNHRecipeModifiers::rocketEngine,
                tier -> tier * 32000,
                EfficiencyGeneratorMachine::rocketEngine,
                tiersBetween(EV, LuV));
        // CIRCUIT_BUS = registerTieredMachines("circuit_bus",
        // CircuitBusPartMachine::new,
        // (tier, builder) -> builder
        // .langValue(GTValues.VNF[tier] + " Circuit Bus")
        // .rotationState(RotationState.ALL)
        // .abilities(CTNHPartAbility.CIRCUIT)
        // .modelProperty(IS_FORMED, false)
        // .colorOverlayTieredHullModel(GTCEu.id("block/multiblock/central_monitor"), null, null)
        // .register(),
        // HV);
        CIRCUIT_BUS = REGISTRATE.machine("circuit_bus", CircuitBusPartMachine::new)
                .cnLangValue("芯片总线")
                .tier(HV)
                .allowExtendedFacing(false)
                .abilities(CTNHPartAbility.CIRCUIT)
                .modelProperty(GTMachineModelProperties.IS_FORMED, false)
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                        GTCEu.id("block/multiblock/central_monitor"))
                .register();

        DRONEHOLDER = REGISTRATE.machine("drone_holder", DroneHolderMachine::new)
                .langValue("drone Holder")
                .tier(UV)
                .rotationState(RotationState.ALL)
                .abilities(CTNHPartAbility.DRONE)
                .modelProperty(IS_FORMED, false)
                .modelProperty(GTMachineModelProperties.RECIPE_LOGIC_STATUS, RecipeLogic.Status.IDLE)
                .model(GTMachineModels.createWorkableTieredHullMachineModel(GTCEu.id("block/machines/object_holder")))
                .register();
        COMPILERMACHINE = registerTieredMachines("neuro_compiler",
                CompilerMachine::new,
                (tier, builder) -> builder
                        .langValue(GTValues.VNF[tier] + " Neuro Compiler")
                        .rotationState(RotationState.ALL)
                        .abilities(CTNHPartAbility.COMPILER)
                        .colorOverlayTieredHullModel("huge_bus_in", null, null)
                        .register(),
                GTValues.tiersBetween(UV, UHV));

        PERSONAL_COMPUTER = registerSimpleComputationMachines("personal_computer",
                CTNHRecipeTypes.PERSONAL_COMPUTER);
        DIGITAL_MINER = registerTieredMachines("digital_miner",
                DigitalMiner::new,
                (tier, builder) -> builder
                        .langValue("%s Digital Miner %s".formatted(VLVH[tier], VLVT[tier]))
                        .rotationState(RotationState.NON_Y_AXIS)
                        .tooltipBuilder((stack, tooltip) -> {
                            int maxArea = DigitalMiner.getRange(tier);
                            long energyPerTick = VEX[tier - 1];
                            tooltip.add(ctnhMachineDigitalMinerTooltip0.translate());
                            tooltip.add(ctnhMachineDigitalMinerTooltip1.translate());
                            tooltip.add(ctnhMachineDigitalMinerTooltip2.translate(
                                    energyPerTick, (int) (40 / Math.pow(2, tier))));
                            tooltip.add(Component.translatable("gtceu.universal.tooltip.voltage_in",
                                    FormattingUtil.formatNumbers(VEX[tier]), GTValues.VNF[tier]));
                            tooltip.add(Component.translatable("gtceu.universal.tooltip.working_area_max", maxArea,
                                    maxArea));
                        })
                        .recipeType(CTNHRecipeTypes.DIGITAL_MINER)
                        .workableTieredHullModel(CTNHCore.id("block/machines/digital_miner"))
                        .register(),
                LV, MV, HV);

        PARALLEL_HATCH = registerTieredMachines("parallel_hatch",
                ParallelHatchPartMachine::new,
                (tier, builder) -> builder
                        .langValue(switch (tier) {
                            case UHV, UEV, UIV -> "Epic";
                            case UXV -> "Legendary";
                            case OpV -> "Eternal";
                            case MAX -> "MAX";
                            default -> "Simple"; // Should never be hit.
                        } + " Parallel Control Hatch")
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.PARALLEL_HATCH)
                        .modelProperty(IS_FORMED, false)
                        .modelProperty(GTMachineModelProperties.RECIPE_LOGIC_STATUS, RecipeLogic.Status.IDLE)
                        .model(createWorkableTieredHullMachineModel(
                                GTCEu.id("block/machines/parallel_hatch_mk" + (tier - 4)))
                                .andThen((ctx, prov, model) -> {
                                    model.addReplaceableTextures("bottom", "top", "side");
                                }))
                        .tooltips(parallelHatchTooltip(tier).translate(),
                                Component.translatable("gtceu.part_sharing.disabled"))
                        .register(),
                UHV, UEV, UIV, UXV, OpV, MAX);
        ENERGY_OUTPUT_HATCH_4A_LOWER = registerTieredMachines(
                "energy_output_hatch_4a",
                (holder, tier) -> new EnergyHatchPartMachine(holder, tier, OUT, 4),
                (tier, builder) -> builder
                        .langValue(VNF[tier] + " 4A Dynamo Hatch")
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.OUTPUT_ENERGY)
                        .modelProperty(IS_FORMED, false)
                        .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                                FormattingUtil.formatNumbers(V[tier]), VNF[tier]),
                                Component.translatable("gtceu.universal.tooltip.amperage_out", 4),
                                Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                        FormattingUtil
                                                .formatNumbers(EnergyHatchPartMachine.getHatchEnergyCapacity(tier, 4))),
                                Component.translatable("gtceu.machine.energy_hatch.output_hi_amp.tooltip"))
                        .overlayTieredHullModel(GTCEu.id("block/machine/part/energy_output_hatch_4a"))
                        .register(),
                GTValues.tiersBetween(LV, HV));
        ROTOR_HOLDER_EXTEND = registerTieredMachines("rotor_holder",
                RotorHolderPartMachine::new,
                (tier, builder) -> builder
                        .langValue("%s Rotor Holder".formatted(VNF[tier]))
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.ROTOR_HOLDER)
                        .modelProperty(IMultiController.IS_FORMED_PROPERTY, false)
                        .modelProperty(RotorHolderPartMachine.HAS_ROTOR_PROPERTY, false)
                        .modelProperty(RotorHolderPartMachine.ROTOR_SPINNING_PROPERTY, false)
                        .modelProperty(RotorHolderPartMachine.EMISSIVE_ROTOR_PROPERTY, false)
                        .model(createRotorHolderModel())
                        .tooltips(LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 0),
                                LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 1),
                                Component.translatable("gtceu.part_sharing.disabled"))
                        .register(),
                GTValues.tiersBetween(ULV, MV));

        STERILE_CLEANROOM_MAINTENANCE_HATCH = REGISTRATE
                .machine("sterile_cleanroom_maintenance_hatch",
                        holder -> new CleaningMaintenanceHatchPartMachine(holder, CleanroomType.STERILE_CLEANROOM))
                .rotationState(RotationState.ALL)
                .abilities(PartAbility.MAINTENANCE)
                .tooltips(Component.translatable("gtceu.part_sharing.disabled"),
                        Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                        Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
                .tooltipBuilder((stack, tooltips) -> {
                    tooltips.add(Component.literal("  ").append(Component
                            .translatable(CleanroomType.STERILE_CLEANROOM.getTranslationKey())
                            .withStyle(ChatFormatting.LIGHT_PURPLE)));
                })
                .modelProperty(MaintenanceHatchPartMachine.MAINTENANCE_TAPED_PROPERTY, false)
                .overlayTieredHullModel(CTNHCore.id("block/machine/part/sterile_cleanroom_maintenance_hatch"))
                .tier(UHV)
                .register();

        CREATIVE_ENERGY_INPUT_HATCH = REGISTRATE
                .machine("creative_energy_hatch", CreativeEnergyHatchPartMachine::new)
                .cnLangValue("创造模式能源仓")
                .langValue("Creative Energy Input Hatch")
                .rotationState(RotationState.ALL)
                .modelProperty(IS_FORMED, false)
                .overlayTieredHullModel(GTCEu.id("block/machine/part/energy_input_hatch"))
                .abilities(PartAbility.INPUT_ENERGY)
                .tier(MAX)
                .tooltipBuilder(CREATIVE_TOOLTIPS)
                .register();

        CREATIVE_ITEM_INPUT_BUS = REGISTRATE
                .machine("creative_item_input_bus", CreativeInputBusPartMachine::new)
                .cnLangValue("创造模式输入总线")
                .langValue("Creative Item Input Bus")
                .rotationState(RotationState.ALL)
                .modelProperty(IS_FORMED, false)
                .colorOverlayTieredHullModel(
                        GTCEu.id("block/overlay/machine/overlay_item_hatch_input"),
                        GTCEu.id("block/overlay/machine/overlay_pipe"),
                        GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"))
                .abilities(PartAbility.IMPORT_ITEMS)
                .tier(MAX)
                .tooltipBuilder(CREATIVE_TOOLTIPS)
                .register();

        CREATIVE_FLUID_INPUT_HATCH = REGISTRATE
                .machine("creative_fluid_input_hatch", CreativeInputHatchPartMachine::new)
                .cnLangValue("创造模式输入仓")
                .langValue("Creative Fluid Input Hatch")
                .rotationState(RotationState.ALL)
                .modelProperty(IS_FORMED, false)
                .colorOverlayTieredHullModel(
                        GTCEu.id("block/overlay/machine/overlay_fluid_hatch_input"),
                        GTCEu.id("block/overlay/machine/overlay_pipe_9x"),
                        GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"))
                .abilities(PartAbility.IMPORT_FLUIDS, PartAbility.IMPORT_FLUIDS_MULTI)
                .tier(MAX)
                .tooltipBuilder(CREATIVE_TOOLTIPS)
                .register();

        CREATIVE_LASER_INPUT_HATCH = REGISTRATE
                .machine("creative_laser_hatch", CreativeLaserHatchPartMachine::new)
                .cnLangValue("创造模式激光靶仓")
                .langValue("Creative Laser Input Hatch")
                .rotationState(RotationState.ALL)
                .modelProperty(IS_FORMED, false)
                .overlayTieredHullModel(GTCEu.id("block/machine/part/laser_target_hatch"))
                .abilities(PartAbility.INPUT_LASER)
                .tier(MAX)
                .tooltipBuilder(CREATIVE_TOOLTIPS)
                .register();
    }

    public static void initCovers() {
        CREATIVE_ENERGY_COVER_DEF = GTCovers.register(
                CTNHCore.id("creative_energy_cover"),
                CreativeEnergyCover::new,
                () -> () -> new SimpleCoverRenderer(CTNHCore.id("block/cover/overlay_creative_energy")));
    }
}
