package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.renderer.MartialMoralityEyeRender;
import io.github.cpearl0.ctnhcore.common.block.CTNHFusionCasingType;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.LargeBottleMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.SlaughterHouseMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.UnderfloorHeatingMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.rareearth.ProcessControlProfile;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.rareearth.ProcessControlledCoilMultiblockMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.rareearth.ProcessControlledElectricMultiblockMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.ChemicalGeneratorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.NaqReactorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotovoltaicPowerStationMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.IndustrialPrimitiveBlastFurnaceMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.MeadowMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.integration.legendary.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials;
import io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips;
import io.github.cpearl0.ctnhcore.utils.CTNHMachineUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.primitive.PrimitiveWorkableMachine;
import com.gregtechceu.gtceu.common.machine.trait.multiblock.CoilMachineTrait;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import com.enderio.base.common.init.EIOBlocks;
import com.mo_guang.ctpp.api.CTPPPartAbility;
import com.mo_guang.ctpp.api.pattern.FactoryStaticBlockPattern;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.util.entry.BlockEntry;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.HEAT_VENT;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterialBlocks.MATERIAL_BLOCKS;
import static com.gregtechceu.gtceu.common.data.GTMaterialItems.MATERIAL_ITEMS;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.DUMMY_RECIPES;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials.Ignitium;
import static net.minecraft.world.level.block.Blocks.*;

// spotless:off
public class MultiblocksA {

    @Key("ctnh.computer.a1")
    @CN("§c一切伟大之作都需要§4牺牲§r§j来铸就。其他生物或许不能理解，但他们必将§4服从§r。")
    @EN("§cAll great works require§4 sacrifice§r§j to forge. Other beings may not understand, but they will§4 obey§r.")
    public static Lang computerA1;


    @Key("ctnh.computer.a2")
    @CN("机器类型:§c突触凝练机")
    @EN("Machine type: §cSynapse Refining Machine")
    public static Lang computerA2;


    @Key("ctnh.computer.a3")
    @CN("将其他智慧生物作为§4湿件§r来进行运算，获得大量算力，甚至直接做成湿件")
    @EN("Uses other intelligent beings as §4wetware§r for computation, gaining massive processing power, even converting them into wetware.")
    public static Lang computerA3;


    @Key("ctnh.computer.a4")
    @CN("机制介绍占位符")
    @EN("Mechanism introduction placeholder")
    public static Lang computerA4;


    @Key("ctnh.computer.a5")
    @CN("该机器会超载所有智慧生物体的大脑。§4不可避免§r地§4永久损坏§r智慧生物的大脑，§4不会留下§r任何掉落物")
    @EN("This machine will overload the brains of all intelligent beings. §4Inevitable§r §4permanent damage§r to their brains,§4 no drops§r will be left.")
    public static Lang computerA5;


    @Key("ctnh.computer.a6")
    @CN("诸如村民这种§7低智慧§r的新人类的生命与智慧太低了，我们需要§c更加聪明，可爱和生命更高的生物§r")
    @EN("For beings like villagers, who are §7low-intelligence§r new humans, their life and intellect are too low. We need§c smarter, cuter, and more life-capable beings§r.")
    public static Lang computerA6;


    @Key("ctnh.computer.a7")
    @CN("为了无尽的知识，我们必须§4做出一切必要的牺牲§4")
    @EN("For endless knowledge, we must§4 make all necessary sacrifices§4.")
    public static Lang computerA7;


    @CN("高级焦炉")
    @EN("Advanced Coke Oven")
    public static Lang advancedCokeOvenTooltip0;


    @CN("§6§l自带32并行")
    @EN("§6§lComes with 32 parallelism")
    public static Lang advancedCokeOvenTooltip1;


    @CN("只可运行焦炉配方,且运行配方时间固定为15s")
    @EN("Can only run coke oven recipes, and recipe time is fixed at 15 seconds")
    public static Lang advancedCokeOvenTooltip2;


    @CN("产生大量的焦化产物与杂酚油")
    @EN("Produces a large amount of coke products and phenolic oil")
    public static Lang advancedCokeOvenTooltip3;


    @CN("§c§l不能使用焦炉仓")
    @EN("§c§lCannot use coke oven cells")
    public static Lang advancedCokeOvenTooltip4;


    @Key("ctnh.multiblock.arcreactor.tooltip")
    @CN("电弧发生者")
    @EN("Arc Generator")
    public static Lang arcreactorTooltip;


    @CN("知天易，逆天难")
    @EN("Knowing the heavens is easy, but defying them is difficult")
    public static Lang astronomicalTooltip0;


    @CN("无法在阳光直射下工作，工作时会自动为芯片总线中的芯片收集数据")
    @EN("Cannot work under direct sunlight, but will automatically collect data for the chips in the chip bus while working")
    public static Lang astronomicalTooltip1;


    @CN("一个大罐子")
    @EN("A big tank")
    public static Lang bioReactorTooltip0;


    @CN("比电力高炉快")
    @EN("Faster than an electric blast furnace.")
    public static Lang blazeBlastFurnaceTooltip0;


    @CN("每秒基础消耗§a10mB§r烈焰之炽焱，电压每超过§6HV§r一级，消耗量变为原来的两倍")
    @EN("Base consumption is §a10mB§r of Blazing Pyrotheum per second. For each voltage tier above §6HV§r, the consumption doubles.")
    public static Lang blazeBlastFurnaceTooltip1;


    @CN("运行耗能x0.75")
    @EN("Consumes 0.75x energy.")
    public static Lang blazeBlastFurnaceTooltip2;


    @CN("允许一次性处理8个配方")
    @EN("Allows processing of 8 recipes simultaneously.")
    public static Lang blazeBlastFurnaceTooltip3;


    @CN("拥有强大的焦化产能来支撑你的木化产线！")
    @EN("Boasts formidable coking capacity to fuel your petrochemical production line!")
    public static Lang cokeTowerTooltip0;


    @CN("有着如同工业熔炉一般的速度")
    @EN("Delivers blistering speeds rivaling industrial furnaces")
    public static Lang cokeTowerTooltip1;



    @CN("通过回流和冷却，将混合蒸气按沸点分离。")
    @EN("Separates mixed vapours by boiling point using reflux and cooling.")
    public static Lang condensingDiscreteTooltip1;


    @CN("参数范围：回流比 5～50；中段冷量 20～80%。")
    @EN("Parameter range: Reflux Ratio 5-50; Middle Cooling Share 20-80%.")
    public static Lang condensingDiscreteTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang condensingDiscreteTooltip3;


    @CN("调参原料：500 mB 盐水。")
    @EN("Retuning Material: 500 mB Salt Water.")
    public static Lang condensingDiscreteTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang condensingDiscreteTooltip5;



    @CN("控制冷却和过饱和度，使稀土沉淀结晶。")
    @EN("Controls cooling and supersaturation to crystallise rare-earth precipitates.")
    public static Lang crystallizerTooltip1;


    @CN("参数范围：冷却速率 5～50；过饱和度 105～140%。")
    @EN("Parameter range: Cooling Rate 5-50; Supersaturation 105-140%.")
    public static Lang crystallizerTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang crystallizerTooltip3;


    @CN("调参原料：1000 mB 盐水。")
    @EN("Retuning Material: 1000 mB Salt Water.")
    public static Lang crystallizerTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang crystallizerTooltip5;



    @CN("在高压下使用氢氧化钠处理稀土矿物。")
    @EN("Uses sodium hydroxide under high pressure to process rare-earth minerals.")
    public static Lang highPressureAlkaliDigesterTooltip1;


    @CN("参数范围：釜压 1200～2400 kPa；碱液浓度 20～50%。")
    @EN("Parameter range: Pressure 1200-2400 kPa; Caustic 20-50%.")
    public static Lang highPressureAlkaliDigesterTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang highPressureAlkaliDigesterTooltip3;


    @CN("调参原料：250 mB 氢氧化钠 + 500 mB 水。")
    @EN("Retuning Material: 250 mB Sodium Hydroxide + 500 mB Water.")
    public static Lang highPressureAlkaliDigesterTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang highPressureAlkaliDigesterTooltip5;



    @CN("在受控氧化气氛中焙烧稀土矿石和稀土盐。")
    @EN("Roasts rare-earth ores and salts in a controlled oxidising atmosphere.")
    public static Lang oxidationRoastingFurnaceTooltip1;


    @CN("参数范围：过量氧系数 95～125%；滚筒转速 2～12 rpm。")
    @EN("Parameter range: Excess Oxygen 95-125%; Drum Speed 2-12 rpm.")
    public static Lang oxidationRoastingFurnaceTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang oxidationRoastingFurnaceTooltip3;


    @CN("调参原料：500 mB 氧气。")
    @EN("Retuning Material: 500 mB Oxygen.")
    public static Lang oxidationRoastingFurnaceTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang oxidationRoastingFurnaceTooltip5;



    @CN("利用氧化还原反应和 pH 控制，选择性沉淀稀土元素。")
    @EN("Uses redox reactions and pH control to selectively precipitate rare-earth elements.")
    public static Lang reductionPrecipitationTankTooltip1;


    @CN("参数范围：氧化还原电位 -700～200 mV；槽液 pH 1.00～7.00。")
    @EN("Parameter range: Redox Potential -700-200 mV; Tank pH 1.00-7.00.")
    public static Lang reductionPrecipitationTankTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang reductionPrecipitationTankTooltip3;


    @CN("调参原料：500 mB 硫酸。")
    @EN("Retuning Material: 500 mB Sulfuric Acid.")
    public static Lang reductionPrecipitationTankTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang reductionPrecipitationTankTooltip5;



    @CN("利用水相和有机相的分配差异分离稀土元素。")
    @EN("Separates rare-earth elements using the distribution difference between aqueous and organic phases.")
    public static Lang solventExtractionTowerTooltip1;


    @CN("参数范围：水相 pH 2.00～7.00；O/A 比 0.50～2.00。")
    @EN("Parameter range: Aqueous pH 2.00-7.00; O/A Ratio 0.50-2.00.")
    public static Lang solventExtractionTowerTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang solventExtractionTowerTooltip3;


    @CN("调参原料：1000 mB 苯。")
    @EN("Retuning Material: 1000 mB Benzene.")
    public static Lang solventExtractionTowerTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang solventExtractionTowerTooltip5;


    @CN("衰变")
    @EN("Decay")
    public static Lang decayPoolsTooltip0;


    @CN("当电路板为0时为不通电状态---不启用世界加速")
    @EN("When the circuit board is set to 0, the machine is unpowered and world acceleration is disabled.")
    public static Lang decayPoolsTooltip1;


    @CN("当电路板为1时为通电状态---启用世界加速")
    @EN("When the circuit board is set to 1, the machine is powered and world acceleration is enabled.")
    public static Lang decayPoolsTooltip2;


    @CN("加速衰变过程")
    @EN("Accelerates the decay process.")
    public static Lang decayPoolsTooltip3;


    @Key("ctnh.multiblock.demon_generator.info.1")
    @CN("浓度差：%s")
    @EN("Concentration Difference: %s")
    public static Lang demonGeneratorInfo1;


    @Key("ctnh.multiblock.demon_generator.info.boosted")
    @CN("§b生命源质增幅已启用")
    @EN("§bLife Essence Boost Active")
    public static Lang demonGeneratorInfoBoosted;


    @Key("ctnh.multiblock.demon_generator.info.corrosive")
    @CN("专精增益：腐蚀")
    @EN("Specialization Boost: Corrosive")
    public static Lang demonGeneratorInfoCorrosive;


    @Key("ctnh.multiblock.demon_generator.info.default")
    @CN("专精增益：无")
    @EN("Specialization Boost: None")
    public static Lang demonGeneratorInfoDefault;


    @Key("ctnh.multiblock.demon_generator.info.destructive")
    @CN("专精增益：毁灭")
    @EN("Specialization Boost: Destructive")
    public static Lang demonGeneratorInfoDestructive;


    @Key("ctnh.multiblock.demon_generator.info.steadfast")
    @CN("专精增益：坚毅")
    @EN("Specialization Boost: Steadfast")
    public static Lang demonGeneratorInfoSteadfast;


    @Key("ctnh.multiblock.demon_generator.info.vengeful")
    @CN("专精增益：复仇")
    @EN("Specialization Boost: Vengeful")
    public static Lang demonGeneratorInfoVengeful;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.0")
    @CN("借用恶魔之力")
    @EN("Harnessing Demonic Power")
    public static Lang demonWillGeneratorTooltip0;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.01")
    @CN("允许使用变电仓，不限制变电仓数量")
    @EN("Allows voltage converter hatches; there is no limit on the number of converter hatches")
    public static Lang demonWillGeneratorTooltip01;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.1")
    @CN("利用机器两侧的区块内的恶魔意志浓度差发电，浓度差与发电量呈指数关系，当浓度差超过500时，超过500的浓度与发电量改为线性关系")
    @EN("Generates power from the difference in Demonic Will concentration between the chunks on both sides of the machine. The difference increases power output exponentially up to 500; above 500, the excess contributes linearly.")
    public static Lang demonWillGeneratorTooltip1;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.2")
    @CN("以机器两侧的恶魔合金方块处的意志浓度为基准进行计算")
    @EN("Calculations use the Will concentration at the Demonic Alloy blocks on both sides of the machine.")
    public static Lang demonWillGeneratorTooltip2;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.3")
    @CN("两侧区块中的各种恶魔意志的多样性会影响发电效率")
    @EN("The diversity of Demonic Wills in both chunks affects generation efficiency.")
    public static Lang demonWillGeneratorTooltip3;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.4")
    @CN("机器内可以放入意志核心，将机器转化为对于某种意志专精的模式，该模式下每秒会有5%的概率消耗一个核心")
    @EN("Will Cores can be inserted to specialize the machine in a type of Will; this mode has a 5% chance per second to consume one core.")
    public static Lang demonWillGeneratorTooltip4;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.5")
    @CN("机器内的符文方块可替换，从而起到不同的增益效果:\n§4献祭符文和牺牲符文----提高生命源质强化模式的发电倍率§r\n§3速度符文----提升一次配方运行的时长（节省恶魔意志消耗）§r\n§e增容符文----每一个符文增加2点恶魔意志浓度差§r\n§c超容符文----每一个符文增加百分之2的恶魔意志浓度差（叠乘）§r\n==============================")
    @EN("The rune blocks inside the machine can be replaced for different bonuses:\n§4Sacrifice and Self-Sacrifice Runes ---- Increase the power multiplier of Life Essence Fortified Mode§r\n§3Speed Runes ---- Increase the duration of one recipe operation (reducing Demonic Will consumption)§r\n§eCapacity Runes ---- Each rune adds 2 to the Demonic Will concentration difference§r\n§cOvercapacity Runes ---- Each rune adds 2% to the Demonic Will concentration difference (multiplicative)§r\n==============================")
    public static Lang demonWillGeneratorTooltip5;


    @Key("ctnh.multiblock.demon_will_generator.tooltip.6")
    @CN("输入§4生命源质§r开启血祭模式，发电量翻倍，每秒消耗§a100mb§r的生命源质")
    @EN("Insert §4Life Essence§r to activate Blood Sacrifice Mode, doubling power output while consuming §a100 mB§r of Life Essence per second")
    public static Lang demonWillGeneratorTooltip6;


    @CN("从海水中烘干出盐，很环保不是吗？")
    @EN("Drying salt out of seawater—eco-friendly, isn't it?")
    public static Lang desaltingFactoryTooltip0;


    @CN("其实产生的是很有价值的原料......")
    @EN("Actually, it produces very valuable materials...")
    public static Lang digestionTankTooltip0;


    @CN("化粪池堆肥机制：")
    @EN("Composting Mechanism of the Digestion Tank:")
    public static Lang digestionTankTooltip1;


    @CN("当化粪池温度处于§236§r至§238§r度之间时为最适生长温度，配方获得1.2倍效率，越偏离最适生长温度，配方效率越低，最低为三分之一")
    @EN("The optimal growth temperature is between §236§r and §238§r degrees. Recipes get 1.2x efficiency at optimal temperature. The further it deviates, the lower the efficiency, down to one-third.")
    public static Lang digestionTankTooltip2;


    @CN("一个专为微生物提供的生长罐，注意时刻关注他！")
    @EN("A tank designed specifically for microbial growth. Always keep an eye on it!")
    public static Lang fermentingTankTooltip0;


    @CN("发酵罐的生物生长机制：")
    @EN("Biological Growth Mechanism of the Fermenting Tank:")
    public static Lang fermentingTankTooltip1;


    @CN("当发酵罐温度处于§236§r至§238§r度之间时为最适生长温度，配方获得1.2倍效率，越偏离最适生长温度，配方效率越低，最低为三分之一")
    @EN("The optimal growth temperature is between §236§r and §238§r degrees. Recipes get 1.2x efficiency at optimal temperature. The further it deviates, the lower the efficiency, down to one-third.")
    public static Lang fermentingTankTooltip2;


    @CN("微生物的生长符合逻辑斯蒂方程，当输入仓内液体体积为容量的一半时，§2生长效率达到两倍§r，而满仓和空仓时生长效率最低，保底为20%")
    @EN("Microbial growth follows the logistic equation. When the liquid volume in the input tank is half of its capacity, §2growth efficiency doubles§r. Efficiency is lowest when the tank is full or empty, with a minimum of 20%.")
    public static Lang fermentingTankTooltip3;


    @Key("ctnh.multiblock.hybrid_mixer.tooltip.0")
    @CN("动力学的电力复兴")
    @EN("Kinetic-Electric Renaissance")
    public static Lang hybridMixerTooltip0;


    @Key("ctnh.multiblock.hybrid_mixer.tooltip.1")
    @CN("执行特殊的电压-应力驱动机制")
    @EN("Uses a special voltage-stress drive mechanism")
    public static Lang hybridMixerTooltip1;


    @Key("ctnh.multiblock.hybrid_mixer.tooltip.2")
    @CN("机器真实电压等级为配方电压等级和应力等级的较小值。应力输入仓要求转速至少为64，应力输入仓转速为256时，应力等级+1")
    @EN("The machine's actual voltage tier is the lower of the recipe voltage tier and stress tier. Stress input hatches require at least 64 RPM; at 256 RPM, the stress tier increases by 1")
    public static Lang hybridMixerTooltip2;


    @Key("ctnh.multiblock.hybrid_mixer.tooltip.3")
    @CN("混合动力超频：应力等级和配方电压等级每同时提升一级，运行速度*4")
    @EN("Hybrid overclocking: each time both stress tier and recipe voltage tier increase by one, processing speed ×4")
    public static Lang hybridMixerTooltip3;


    @Key("ctnh.multiblock.hybrid_mixer.tooltip.4")
    @CN("当转速超过64时，使配方时间*0.8。转速超过128时配方时间和电压速度将随着转速提升进一步减少")
    @EN("Above 64 RPM, recipe time ×0.8. Above 128 RPM, recipe time and voltage speed decrease further as RPM increases")
    public static Lang hybridMixerTooltip4;


    @Key("ctnh.multiblock.industrial_altar.info.current_lp")
    @CN("当前含有lp量:%d")
    @EN("Current LP amount: %d")
    public static Lang industrialAltarInfoCurrentLp;


    @Key("ctnh.multiblock.industrial_altar.info.max_lp")
    @CN("最大lp量:%d")
    @EN("Max LP amount: %d")
    public static Lang industrialAltarInfoMaxLp;


    @CN("更强大的土高炉，你的炼钢好帮手")
    @EN("A more powerful primitive blast furnace, your best helper for steelmaking")
    public static Lang industrialPrimitiveBlastFurnaceTooltip0;


    @CN("工业土高炉在持续运行配方时，会不断升温，而一旦中止，则会迅速冷却")
    @EN("The industrial primitive blast furnace will continuously heat up while running a recipe, and will cool down rapidly once the process is stopped")
    public static Lang industrialPrimitiveBlastFurnaceTooltip1;


    @CN("温度越高，工业土高炉的并行数越高，最高为8并行")
    @EN("The higher the temperature, the higher the parallelism of the industrial primitive blast furnace, up to a maximum of 8 parallelism")
    public static Lang industrialPrimitiveBlastFurnaceTooltip2;


    @CN("温度越高，工业土高炉的效率越高，最高为两倍效率")
    @EN("The higher the temperature, the higher the efficiency of the industrial primitive blast furnace, up to a maximum of double efficiency")
    public static Lang industrialPrimitiveBlastFurnaceTooltip3;



    @CN("使用离子交换树脂分离稀土元素并去除杂质。")
    @EN("Uses ion-exchange resin to separate rare-earth elements and remove impurities.")
    public static Lang ionExchangerTooltip1;


    @CN("参数范围：柱流量 5～40 BV/h；洗脱液 pH 0.50～4.00。")
    @EN("Parameter range: Column Flow 5-40 BV/h; Eluent pH 0.50-4.00.")
    public static Lang ionExchangerTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang ionExchangerTooltip3;


    @CN("调参原料：1000 mB 盐酸。")
    @EN("Retuning Material: 1000 mB Hydrochloric Acid.")
    public static Lang ionExchangerTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang ionExchangerTooltip5;


    @CN("真是一个大罐子")
    @EN("This is truly a large container.")
    public static Lang largeBottleTooltip0;


    @CN("可以存储10000桶液体")
    @EN("Can store up to 10,000 buckets of liquid.")
    public static Lang largeBottleTooltip1;


    @CN("与大型发酵罐一起使用时，其中的液体会以§e100mb/s§r的速度消耗")
    @EN("When used with a large fermenting tank, its liquid will be consumed at a rate of §e100mb/s§r.")
    public static Lang largeBottleTooltip2;


    @CN("高效工业化发酵生产")
    @EN("Efficient Industrial Fermentation")
    public static Lang largeFermentingTankTooltip0;


    @CN("可接入附属结构，在对应位置连接上一个大发酵瓶后，可以根据发酵瓶中的液体种类提升保底效率：水(50%)，简易培养基(150%)，无菌培养基(200%)")
    @EN("Can connect auxiliary structures. By attaching a large fermentation bottle with a specific liquid type, the minimum efficiency increases: Water (50%), Basic Medium (150%), Sterile Medium (200%).")
    public static Lang largeFermentingTankTooltip1;


    @CN("全维度集气")
    @EN("Dimensional Gas Collection Chamber")
    public static Lang largeGasCollectionChamberTooltip0;


    @CN("这台机器可以收集任意维度的气体")
    @EN("This machine can collect gases from any dimension")
    public static Lang largeGasCollectionChamberTooltip1;


    @CN("由于它的产量较大，建议你用ME输出总成来收集产物")
    @EN("Since its output is large, it is recommended to use an ME Output Assembly to collect the products")
    public static Lang largeGasCollectionChamberTooltip2;


    @Key("ctnh.multiblock.large_miner_zpm.tooltip.0")
    @CN("听说你很担心矿物的来源？")
    @EN("Heard you're worried about the source of minerals?")
    public static Lang largeMinerZpmTooltip0;


    @CN("钢质合金炉")
    @EN("Steel Alloy Furnace")
    public static Lang largeSteelAlloyFurnaceTooltip0;


    @CN("钢质熔炉")
    @EN("Steel Furnace")
    public static Lang largeSteelFurnaceTooltip0;


    @Key("ctnh.multiblock.lasersorter.recipe.cwut")
    @CN("所需的基础算力：%d")
    @EN("Required base computation: %d")
    public static Lang lasersorterRecipeCwut;


    @Key("ctnh.multiblock.magic_fuel_generator.tip")
    @CN("精炼天地之魔精")
    @EN("Refined Essence of Heaven and Earth")
    public static Lang magicFuelGeneratorTip;


    @Key("ctnh.multiblock.mana_reactor.tooltip.0")
    @CN("工业魔力奠基者")
    @EN("Industrial Mana Foundation")
    public static Lang manaReactorTooltip0;


    @Key("ctnh.multiblock.mana_reactor.tooltip.1")
    @CN("允许使用并行控制仓")
    @EN("Allows parallel control hatches")
    public static Lang manaReactorTooltip1;


    @Key("ctnh.multiblock.mana_turbine.info.consumption_rate")
    @CN("消耗速率：%d")
    @EN("Consumption Rate：%d")
    public static Lang manaTurbineInfoConsumptionRate;


    @Key("ctnh.multiblock.mana_turbine.info.efficiency")
    @CN("发电效率：%d%%")
    @EN("Generating Efficiency：%d%%")
    public static Lang manaTurbineInfoEfficiency;


    @CN("丐版鸿蒙之眼")
    @EN("Poor version of the Primordial Eye")
    public static Lang martialMoralityEyeTooltip0;


    @CN("原始时代时消耗64000mb的蒸汽和64个原石")
    @EN("Consumes 64000mb of steam and 64 raw stones in the early stages")
    public static Lang martialMoralityEyeTooltip1;


    @CN("产出主世界和暮色森林以及月球的矿")
    @EN("Produces ores from the Overworld, Twilight Forest, and the Moon")
    public static Lang martialMoralityEyeTooltip2;


    @CN("随着电压等级提高能够解锁更多配方")
    @EN("Unlocks more recipes as the voltage level increases")
    public static Lang martialMoralityEyeTooltip3;


    @CN("在前期比坠星好用")
    @EN("More useful than falling stars in the early stages")
    public static Lang martialMoralityEyeTooltip4;


    @CN("结构中心似乎存在着神秘力量，充满危险的气息，请远离！")
    @EN("The center of the structure seems to emanate a mysterious force, filled with an aura of danger. Stay away!")
    public static Lang martialMoralityEyeTooltip5;


    @CN("结构来源:Twist Space Technology")
    @EN("Structure source: Twist Space Technology")
    public static Lang martialMoralityEyeTooltip6;


    @CN("§7自动化放牧")
    @EN("§7Automated Grazing")
    public static Lang meadowTooltip0;


    @CN("输入配方所需n倍应力时，获得n并行")
    @EN("Gains n parallel processing when provided with n× the required stress.")
    public static Lang meadowTooltip1;


    @CN("可以同时养殖不同动物")
    @EN("Allows breeding of different animals at the same time.")
    public static Lang meadowTooltip2;


    @CN("§7只有动物跑出去时，你才知道你不是在种菜！")
    @EN("§7Only when the animals run away will you realize that you are not growing crops!")
    public static Lang meadowTooltip3;


    @Key("ctnh.multiblock.mega_lcr.info.coil")
    @CN("当前线圈温度:%s")
    @EN("Current Coil Temperature: %s")
    public static Lang megaLcrInfoCoil;


    @Key("ctnh.multiblock.mega_lcr.info.speed")
    @CN("当前配方时间倍率:%s")
    @EN("Current Recipe Time Multiplier: %s")
    public static Lang megaLcrInfoSpeed;


    @CN("§b具有4个异步线程§r")
    @EN("§bHas 4 asynchronous threads§r")
    public static Lang megaLcrTooltip0;


    @CN("使用§d异步线程控制仓§r以配置多线程运行模式")
    @EN("Use §dAsynchronous Thread Control Hatches§r to configure multithreaded operation")
    public static Lang megaLcrTooltip1;


    @Key("ctnh.multiblock.meteor_capturer.tooltip.0")
    @CN("§8为什么陨石总能落在陨石坑里？§r\n该机器无法超频")
    @EN("§8Why do meteors always land in meteor craters?§r\nThis machine cannot be overclocked")
    public static Lang meteorCapturerTooltip0;


    @Key("ctnh.multiblock.meteor_capturer.tooltip.1")
    @CN("消耗少量引物和大量的生命源质，从外太空拉取满是矿石的陨石。")
    @EN("Consumes a small amount of primer and a large amount of Life Essence to pull ore-rich meteors from outer space.")
    public static Lang meteorCapturerTooltip1;


    @Key("ctnh.multiblock.meteor_capturer.tooltip.2")
    @CN("配方需要大量的输入输出空间，建议使用高级输入总成。")
    @EN("Recipes require a large amount of input/output space; Advanced Input Hatches are recommended.")
    public static Lang meteorCapturerTooltip2;


    @Key("ctnh.multiblock.meteor_capturer.tooltip.3")
    @CN("陨石会在多方块结构上方的空腔内生成（真的）。不要在里面放置人或设备。")
    @EN("Meteors generate in the cavity above the multiblock structure (really). Do not place people or equipment inside.")
    public static Lang meteorCapturerTooltip3;


    @Key("ctnh.multiblock.meteor_capturer.tooltip.4")
    @CN("半径大于13的配方会破坏多方块结构（不存在这种配方）。")
    @EN("Recipes with a radius greater than 13 will destroy the multiblock structure (no such recipes exist).")
    public static Lang meteorCapturerTooltip4;


    @CN("浩瀚能量，天地震动")
    @EN("Vast energy, the earth trembles")
    public static Lang naqReactorMk3Tooltip1;


    @CN("利用超能燃料进行发电,无镍等离子体时无法完全消耗燃料")
    @EN("Generates power using supercharged fuel - cannot fully consume fuel without nickel plasma")
    public static Lang naqReactorMk3Tooltip2;


    @CN("机器构型中必须有一个动力仓")
    @EN("A power core must be present in the machine configuration")
    public static Lang naqReactorMk3Tooltip3;


    @CN("随着内核温度上升,发电效率增大")
    @EN("As the core temperature increases, power generation efficiency improves")
    public static Lang naqReactorMk3Tooltip4;


    @CN("简易太阳能发电")
    @EN("Basic Solar Power Generation")
    public static Lang photovoltaicPowerStationEnergeticTooltip0;


    @CN("§e基础产能功率：§r512 EU/t")
    @EN("§eBase Output:§r 512 EU/t")
    public static Lang photovoltaicPowerStationEnergeticTooltip1;


    @CN("只在白天工作，不同维度会对太阳能发电的效率产生影响，基础产能功率为在主世界正午的功率")
    @EN("Operates only during daytime. Efficiency varies across dimensions. Base output reflects noon in the Overworld")
    public static Lang photovoltaicPowerStationEnergeticTooltip2;


    @CN("高效太阳能发电")
    @EN("Advanced Solar Power Generation")
    public static Lang photovoltaicPowerStationPulsatingTooltip0;


    @CN("§e基础产能功率：§r2048 EU/t")
    @EN("§eBase Output:§r 2048 EU/t")
    public static Lang photovoltaicPowerStationPulsatingTooltip1;


    @CN("只在白天工作，不同维度会对太阳能发电的效率产生影响，基础产能功率为在主世界正午的功率")
    @EN("Operates only during daytime. Efficiency varies across dimensions. Base output reflects noon in the Overworld")
    public static Lang photovoltaicPowerStationPulsatingTooltip2;


    @CN("究极太阳能发电")
    @EN("Ultimate Solar Power Generation")
    public static Lang photovoltaicPowerStationVibrantTooltip0;


    @CN("§e基础产能功率：§r8192 EU/t")
    @EN("§eBase Output:§r 8192 EU/t")
    public static Lang photovoltaicPowerStationVibrantTooltip1;


    @CN("只在白天工作，不同维度会对太阳能发电的效率产生影响，基础产能功率为在主世界正午的功率")
    @EN("Operates only during daytime. Efficiency varies across dimensions. Base output reflects noon in the Overworld")
    public static Lang photovoltaicPowerStationVibrantTooltip2;


    @CN("氤氲之气，凝为霜露")
    @EN("The dense air condenses into frost and dew")
    public static Lang plasmaCondenserTooltip1;


    @Key("ctnh.multiblock.quasar_eye.tooltip.0")
    @CN("§9魔力§r的§c终极奥秘§r，足以制造§5类星体§r的装置掌握在§6你§r的手中")
    @EN("§9Mana's§r §cUltimate Mystery§r, a device capable of creating §5quasars§r now rests in §6your§r hands")
    public static Lang quasarEyeTooltip0;


    @Key("ctnh.multiblock.quasar_eye.tooltip.1")
    @CN("该机器启动需要§r初始魔力燃料消耗§R，查阅EMI以查找消耗量")
    @EN("Machine activation requires §rinitial mana fuel consumption§R; consult EMI for the exact amount")
    public static Lang quasarEyeTooltip1;


    @Key("ctnh.multiblock.quasar_eye.tooltip.10")
    @CN("在创生模式下释放所有积累的电量，使用高级燃料可以使输出获得倍乘。同时每积累1000E EU就额外产出一份气体产出,积累电量小于1E时无法启动创生模式")
    @EN("In Creation Mode, release all accumulated power. Advanced fuels multiply the output. Every 1000E EU accumulated produces one additional gas output; Creation Mode cannot start with less than 1E stored power")
    public static Lang quasarEyeTooltip10;


    @Key("ctnh.multiblock.quasar_eye.tooltip.11")
    @CN("§b好消息§r：这个机器不会爆炸，§c但我不保证未来它不会爆炸！§r")
    @EN("§bGood news§r: this machine will not explode, §cbut I cannot guarantee it will not explode in the future!§r")
    public static Lang quasarEyeTooltip11;


    @Key("ctnh.multiblock.quasar_eye.tooltip.2")
    @CN("在能量等级高时启动能量等级低的配方§b不需要启动花费§r")
    @EN("Activating lower-tier recipes at high energy tiers §bdoes not require an activation cost§r")
    public static Lang quasarEyeTooltip2;


    @Key("ctnh.multiblock.quasar_eye.tooltip.3")
    @CN("§5符文能量§r控制着输出的强度，输入§b五级符文§r来增强符文能量，以加强你的输出,使用§5类星体符文§r产生大量符文能量")
    @EN("§5Rune Energy§r governs output strength. Input §bTier V Runes§r to increase rune energy and strengthen your output; use §5Quasar Runes§r to generate massive amounts of rune energy")
    public static Lang quasarEyeTooltip3;


    @Key("ctnh.multiblock.quasar_eye.tooltip.4")
    @CN("该机器获取符文能量逻辑为：在§5每次配方运行前§r读取并消耗每类可消耗符文§c最多各一个§r")
    @EN("Rune energy is acquired as follows: §5before each recipe operation§r, read and consume §cat most one§r of each consumable rune type")
    public static Lang quasarEyeTooltip4;


    @Key("ctnh.multiblock.quasar_eye.tooltip.5")
    @CN("§c注意§r：符文能量越高，其消耗速度就§c越快§r，且符文能量低于50时§c效率将会减半！§r")
    @EN("§cWarning§r: the higher the rune energy, the §cfaster§r it is consumed; when rune energy is below 50, §cefficiency is halved!§r")
    public static Lang quasarEyeTooltip5;


    @Key("ctnh.multiblock.quasar_eye.tooltip.6")
    @CN("该机器能量效率为log((符文能量)/50)+1，最大能量效率为(1+能量等级)")
    @EN("Energy efficiency is log((rune energy) / 50) + 1, with a maximum of (1 + energy tier)")
    public static Lang quasarEyeTooltip6;


    @Key("ctnh.multiblock.quasar_eye.tooltip.7")
    @CN("该机器拥有时间并行，消耗量和持续时间均会乘上并行数，且并行数为效率*5")
    @EN("The machine has time parallelism: consumption and duration are multiplied by parallelism, and parallelism equals efficiency × 5")
    public static Lang quasarEyeTooltip7;


    @Key("ctnh.multiblock.quasar_eye.tooltip.8")
    @CN("该机器燃料消耗量为1-0.05*Math.max((rune_energy-50)/50,0.75)")
    @EN("Fuel consumption is 1 - 0.05 * Math.max((rune_energy - 50) / 50, 0.75)")
    public static Lang quasarEyeTooltip8;


    @Key("ctnh.multiblock.quasar_eye.tooltip.9")
    @CN("在普通模式下发电时积将发电量的1%积累入类星体之眼之中，你每有25符文能量，就可以额外积累1%")
    @EN("In normal mode, 1% of generated power is accumulated in the Quasar Eye; every 25 rune energy grants an additional 1% accumulation")
    public static Lang quasarEyeTooltip9;


    @CN("需要通入8192应力使其内部活塞压实待加工料")
    @EN("Requires 8,192 Stress to activate internal pistons for compacting materials")
    public static Lang sinteringKilnTooltip0;


    @CN("无情的杀戮机器")
    @EN("A merciless killing machine")
    public static Lang slaughterHouseTooltip0;


    @CN("输入总线放入电动刷怪笼后，机器会自动输出对应怪物的战利品，可放入多个电动刷怪笼")
    @EN("When powered spawners are placed in the input bus, the machine will automatically output corresponding mob drops. Multiple powered spawners can be inserted")
    public static Lang slaughterHouseTooltip1;


    @CN("电压每升高1级，虚拟刷怪量会增加4（HV为4）")
    @EN("Each voltage tier increase adds +4 to virtual spawn count (HV provides 4)")
    public static Lang slaughterHouseTooltip2;


    @CN("怪物血量和护甲值越高，配方运行所需时间越长")
    @EN("Higher mob health and armor values will increase processing time")
    public static Lang slaughterHouseTooltip3;


    @CN("武器的伤害和附魔会减少配方运行的时间")
    @EN("Weapon damage and enchantments will reduce processing time")
    public static Lang slaughterHouseTooltip4;


    @CN("时运等附魔也能生效")
    @EN("Fortune and other enchantments also take effect")
    public static Lang slaughterHouseTooltip5;


    @CN("所有配方速度提高50%！")
    @EN("All recipes are 50% faster!")
    public static Lang superEbfTooltip0;


    @CN("生产资料与剩余价值")
    @EN("Means of Production and Surplus Value")
    public static Lang sweatShopTooltip0;


    @CN("工厂内的村民数量决定了工作效率，配方耗时x(2/村民数量)")
    @EN("The number of villagers in the factory determines efficiency. Recipe time x (2 / number of villagers)")
    public static Lang sweatShopTooltip1;


    @CN("工厂内的有效员工数量受限于工厂大小，初始上限为4，工厂长度每增加2格上限+1")
    @EN("The effective number of workers in the factory is limited by the factory size. Initial limit: 4 workers; for every 4 blocks added to the factory length, the limit increases by 1.")
    public static Lang sweatShopTooltip2;


    @CN("放入的生产资料(机器)决定了可以工作的配方：\n动力辊压机----卷板机配方\n动力搅拌机----搅拌机配方\n车床----车床配方\n离心机----离心机配方\n烈焰人燃烧室----提取机配方\n工作盆----流体成型配方\n粉碎轮----研磨机配方\n动力锯----线材轧机配方\n激光加工器----激光蚀刻配方\n==============================")
    @EN("The production materials (machines) placed determine the available recipes:\nPowered Rolling Machine ---- Rolling Mill Recipes\nPowered Mixer ---- Mixer Recipes\nLathe ---- Lathe Recipes\nCentrifuge ---- Centrifuge Recipes\nBlaze Burner ---- Extractor Recipes\nWork Basin ---- Fluid Forming Recipes\nCrushing Wheel ---- Grinder Recipes\nPowered Saw ---- Wire Rolling Machine Recipes\nLaser Processor ---- Laser Etching Recipes\n==============================")
    public static Lang sweatShopTooltip3;


    @CN("放入的生产资料(机器)数量决定了对应工作配方的并行数：并行数 = sqrt(机器数)")
    @EN("The number of production materials (machines) placed determines the parallelism of corresponding recipes: Parallelism = sqrt(number of machines)")
    public static Lang sweatShopTooltip4;


    @CN("放入机械手可以提高整体配方运行速度，配方耗时x (1/1 + 0.25 * sqrt(机械手数))")
    @EN("Adding robotic arms improves the overall recipe execution speed. Recipe time x (1 / 1 + 0.25 * sqrt(number of robotic arms))")
    public static Lang sweatShopTooltip5;


    @CN("放入机器的多样性会提高配方运行速度")
    @EN("The diversity of machines placed improves recipe execution speed.")
    public static Lang sweatShopTooltip6;


    @CN("基础配方耗时为2倍，配方所需的电压等级越高，基础耗时x(配方等级的平方)")
    @EN("The base recipe time is 2x. The higher the recipe voltage tier, the more the base time is multiplied by the square of the recipe tier.")
    public static Lang sweatShopTooltip7;


    @CN("每5秒机器会消耗(真实员工数量)份简易营养餐")
    @EN("Every 5 seconds, the machine consumes (number of employees) servings of Simple Worker Meals.")
    public static Lang sweatShopTooltip8;


    @CN("用蒸汽温暖你的心")
    @EN("Warm your heart with steam")
    public static Lang underfloorHeatingSystemTooltip0;


    @CN("地暖系统依靠蒸汽供暖，占地一个区块，能对§a周围5*5的区块§r产生供暖，供暖只会在地暖上方十格内生效")
    @EN("The underfloor heating system uses steam for heating. Occupying one chunk, it can heat §aa 5×5 chunk area§r around it. Heating only works within 10 blocks above the system")
    public static Lang underfloorHeatingSystemTooltip1;


    @CN("铜砖瓦会生锈，生锈后地暖系统的供暖能力会减弱")
    @EN("Copper brick tiles will rust over time, reducing the heating efficiency of the system when rusted")
    public static Lang underfloorHeatingSystemTooltip2;


    @CN("可以调节速率，以降低供暖功率并减少蒸汽消耗，最低降至25%")
    @EN("Adjustable rate allows reducing heating power and steam consumption, with minimum setting at 25%")
    public static Lang underfloorHeatingSystemTooltip3;



    @CN("在真空和受控升温条件下烧结、蒸馏稀土材料。")
    @EN("Sinters and distils rare-earth materials under vacuum and controlled heating.")
    public static Lang vacuumSinteringTowerTooltip1;


    @CN("参数范围：真空压力 5～500 Pa；升温速率 5～80 K/min。")
    @EN("Parameter range: Vacuum Pressure 5-500 Pa; Heating Rate 5-80 K/min.")
    public static Lang vacuumSinteringTowerTooltip2;


    @CN("校准后自动运行；材料不足或额度用尽时暂停。")
    @EN("Runs automatically after calibration; pauses when materials or quota run out.")
    public static Lang vacuumSinteringTowerTooltip3;


    @CN("调参原料：500 mB 氮气。")
    @EN("Retuning Material: 500 mB Nitrogen.")
    public static Lang vacuumSinteringTowerTooltip4;


    @CN("维护材料见机器 GUI 界面。")
    @EN("Maintenance material: See the machine GUI.")
    public static Lang vacuumSinteringTowerTooltip5;


    @CN("取天材，掘地精")
    @EN("Harvesting heaven's materials, digging the earth's essence")
    public static Lang voidMinerTooltip0;


    @CN("虚空采矿场自动生成并提取矿石")
    @EN("The Void Miner automatically generates and extracts ores")
    public static Lang voidMinerTooltip1;


    @CN("如果你对矿物需求极大，虚空采矿机是必不可少的帮手")
    @EN("If you have a huge demand for minerals, the Void Miner is an essential helper")
    public static Lang voidMinerTooltip2;


    @CN("每次工作消耗100,000B钻井液，升降温度时消耗极寒之凛冰和烈焰之炽焱")
    @EN("Each operation consumes 100,000 B of drilling fluid; Cryotheum and Pyrotheum are consumed when adjusting temperature")
    public static Lang voidMinerTooltip3;


    @CN("在奇数次运行前，机器会试图消耗烈焰之炽焱来升温。初始烈焰之炽焱消耗量为1000mb,若成功消耗,则温度将会增加 ⌊(消耗量 ÷ 100)⌋,接着消耗量将会自乘以 1.02")
    @EN("Before odd-numbered operations, the machine attempts to consume Pyrotheum to raise the temperature. Initial consumption is 1000 mB; if successful, the temperature increases by floor(consumption / 100), then consumption is multiplied by 1.02.")
    public static Lang voidMinerTooltip4;


    @CN("在偶数次运行前，机器会试图消耗极寒之凛冰来降温。初始极寒之凛冰消耗量为1000mb,若成功消耗,则温度将会降低 ⌊(消耗量 ÷ 100)⌋,接着消耗量将会自乘以 1.02")
    @EN("Before even-numbered operations, the machine attempts to consume Cryotheum to lower the temperature. Initial consumption is 1000 mB; if successful, the temperature decreases by floor(consumption / 100), then consumption is multiplied by 1.02.")
    public static Lang voidMinerTooltip5;


    @CN("温度越高，虚空采矿场的产出倍率越高")
    @EN("The higher the temperature, the higher the Void Miner's output multiplier")
    public static Lang voidMinerTooltip6;


    @CN("当温度达到25000K时，虚空采矿机将进入强制降温模式，直至温度降至0K时，恢复正常工作模式")
    @EN("At 25,000 K, the Void Miner enters forced cooling mode until the temperature reaches 0 K, then resumes normal operation.")
    public static Lang voidMinerTooltip7;


    @CN("请交替输入烈焰之炽焱和极寒之凛冰来控制温度")
    @EN("Alternate Pyrotheum and Cryotheum inputs to control the temperature.")
    public static Lang voidMinerTooltip8;


    @Key("ctnh.multiblock.water_power_station.info.0")
    @CN("水量：%d")
    @EN("Water Flow: %d")
    public static Lang waterPowerStationInfo0;


    @Key("ctnh.multiblock.water_power_station.info.1")
    @CN("线圈效率：%d%%")
    @EN("Coil Efficiency: %d%%")
    public static Lang waterPowerStationInfo1;


    @Key("ctnh.multiblock.water_power_station.info.2")
    @CN("产能功率：%d/%d EU/t")
    @EN("Power Output: %d/%d EU/t")
    public static Lang waterPowerStationInfo2;


    @Key("ctnh.multiblock.water_power_station.tooltip.0")
    @CN("环保能源！")
    @EN("Eco-Friendly Energy!")
    public static Lang waterPowerStationTooltip0;


    @Key("ctnh.multiblock.water_power_station.tooltip.1")
    @CN("发电量和以控制器为中心，机器长度为半径，高为4的范围内的水量成正比")
    @EN("Power generation is proportional to the amount of water within a radius equal to the machine length and height of 4, centered on the controller.")
    public static Lang waterPowerStationTooltip1;


    @Key("ctnh.multiblock.water_power_station.tooltip.2")
    @CN("发电量随机在0.6至1的倍率间波动")
    @EN("Power output fluctuates randomly between a multiplier of 0.6 to 1.")
    public static Lang waterPowerStationTooltip2;


    @Key("ctnh.multiblock.zenith_machine.info.max_parallel")
    @CN("最大并行数：%d")
    @EN("Max Parallels：%d")
    public static Lang zenithMachineInfoMaxParallel;


    @Key("ctnh.multiblock.zenith_machine.info.now_parallel")
    @CN("当前并行数：%d")
    @EN("Now Parallels：%d")
    public static Lang zenithMachineInfoNowParallel;


    @CN("普通离心机模式下会获得8并行")
    @EN("Provides 8 parallel operations in normal centrifuge mode")
    public static Lang superCentrifugeParallel;


    @Key("gtceu.astronomical_observatory")
    @CN("天文台")
    @EN("Astronomical Observatory")
    public static Lang gtceuAstronomicalObservatory;


    @Key("gtceu.bedrock_drilling_rigs")
    @CN("基岩钻机")
    @EN("Bedrock Drilling Rigs")
    public static Lang gtceuBedrockDrillingRigs;


    @Key("gtceu.big_dam")
    @CN("大坝")
    @EN("Big Dam")
    public static Lang gtceuBigDam;


    @Key("gtceu.coke_oven")
    @CN("焦炉")
    @EN("Coke Oven")
    public static Lang gtceuCokeOven;


    @Key("gtceu.naq_mk1")
    @CN("超级燃料")
    @EN("Super Fuel")
    public static Lang gtceuNaqMk1;


    @Key("gtceu.photovoltaic_power")
    @CN("光伏发电")
    @EN("Photovoltaic Powering")
    public static Lang gtceuPhotovoltaicPower;


    @Key("gtceu.plasma_condenser")
    @CN("等离子冷凝")
    @EN("Plasma Condensation")
    public static Lang gtceuPlasmaCondenser;


    @Key("gtceu.slaughter_house")
    @CN("屠宰场")
    @EN("Slaughter House")
    public static Lang gtceuSlaughterHouse;


    @Key("gtceu.underfloor_heating_system")
    @CN("地暖")
    @EN("Underfloor Heating")
    public static Lang gtceuUnderfloorHeatingSystem;


    @CN("超速离心")
    @EN("Super Centrifuge")
    public static Lang superCentrifuge;


    @CN("超声破碎")
    @EN("Ultrasonic Disruptor")
    public static Lang ultrasonicApparatus;


    @Key("zenith_machine_sp")
    @CN("§5灵能灯塔屹立不倒！")
    @EN("§5The Psionic Beacon Stands Unyielding!")
    public static Lang zenithMachineSp;


    public static void init(){}
    public static final MultiblockMachineDefinition UNDERFLOOR_HEATING_SYSTEM = REGISTRATE.multiblock("underfloor_heating_system", UnderfloorHeatingMachine::new)
            .cnLangValue("地暖")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.UNDERFLOOR_HEATING_SYSTEM)
            .tooltips(underfloorHeatingSystemTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    underfloorHeatingSystemTooltip1.translate(),
                    underfloorHeatingSystemTooltip2.translate(),
                    underfloorHeatingSystemTooltip3.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAAAAAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAA@AAAAAAA")
                    .where("A", Predicates.blocks(AllBlocks.COPPER_SHINGLES.getStandard().get())
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.UNAFFECTED, true).get()))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(Create.asResource("block/copper/copper_shingles"),
                    GTCEu.id("block/multiblock/multiblock_tank"))
            .beforeWorking((machine, recipe) -> {
                var efficiency = ((UnderfloorHeatingMachine) machine).getEfficiency();
                machine.self().getHolder().self().getPersistentData().putDouble("efficiency", efficiency);
                return null;
            })
            .recipeModifier((machine, group, recipe) -> {
                if (machine instanceof UnderfloorHeatingMachine underfloorHeatingMachine) {
                    recipe.multiplyInputs(underfloorHeatingMachine.rate / 100);
                }
                return null;
            })
            .onWorking(machine -> {
                if (machine instanceof UnderfloorHeatingMachine) {
                    var pos = machine.self().getPos();
                    var facing = machine.self().getFrontFacing();
                    double efficiency = machine.self().getHolder().self().getPersistentData().getDouble("efficiency");
                    if (machine.self().getOffsetTimer() % 20 == 0) {
                        efficiency = ((UnderfloorHeatingMachine) machine).getEfficiency();
                        machine.self().getHolder().self().getPersistentData().putDouble("efficiency", efficiency);
                    }
                    AABB range = switch (facing) {
                        case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-39, 0, -32), pos.offset(40, 16, 47)));
                        case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-40, 0, -47), pos.offset(39, 16, 32)));
                        case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-32, 0, -40), pos.offset(47, 16, 39)));
                        case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-47, 0, -39), pos.offset(32, 16, 40)));
                        default -> throw new IllegalStateException("Unexpected value: " + facing);
                    };
                    UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.put(range, efficiency * ((UnderfloorHeatingMachine) machine).rate / 100);
                }
                return true;
            })
            .afterWorking(machine -> {
                var pos = machine.self().getPos();
                var facing = machine.self().getFrontFacing();
                AABB range = switch (facing) {
                    case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-39, 0, -32), pos.offset(40, 16, 47)));
                    case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-40, 0, -47), pos.offset(39, 16, 32)));
                    case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-32, 0, -40), pos.offset(47, 16, 39)));
                    case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-47, 0, -39), pos.offset(32, 16, 40)));
                    default -> throw new IllegalStateException("Unexpected value: " + facing);
                };
                UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.remove(range);
            })
            .register();


    public static final MultiblockMachineDefinition ASTRONOMICAL_OBSERVATORY = REGISTRATE.multiblock("astronomical_observatory", AstronomicalMachine::new)
            .cnLangValue("天文台")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ASTRONOMICAL_OBSERVATORY)
            .tooltips(astronomicalTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    astronomicalTooltip1.translate())
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("   BBB   ", "   BBB   ", "   BBB   ", "   BBB   ", "   BBB   ", "   RDR   ", "         ", "         ", "         ")
                    .aisle("  BBBBB  ", "  B   B  ", "  B E B  ", "  B F B  ", "  B   B  ", "  R   R  ", "   RDR   ", "         ", "         ")
                    .aisle(" BBBBBBB ", " B     B ", " B  E  B ", " B  F  B ", " B     B ", " R     R ", "  R   R  ", "   RDR   ", "         ")
                    .aisle("BBBBBBBBB", "B       B", "B   E   B", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle("BBBBBBBBB", "B       B", "BEEEEEEEB", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle("BBBBBBBBB", "B       B", "B   E   B", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle(" BBBBBBB ", " B     B ", " B  E  B ", " B  F  B ", " B     B ", " R     R ", "  R   R  ", "   RDR   ", "         ")
                    .aisle("  BBBBB  ", "  B   B  ", "  B E B  ", "  B F B  ", "  B   B  ", "  R   R  ", "   RDR   ", "         ", "         ")
                    .aisle("   BBB   ", "   B@B   ", "   BAB   ", "   BBB   ", "   BBB   ", "   RDR   ", "         ", "         ", "         ")
                    .where("A", abilities(CTNHPartAbility.CIRCUIT))
                    .where(" ", Predicates.any())
                    .where("R", Predicates.blocks(CTNHBlocks.CASING_REFLECT_LIGHT.get()))
                    .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(1).setPreviewCount(1))
                    )
                    .where("D", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("E", Predicates.blocks(GTMachines.HULL[GTValues.HV].getBlock()))
                    .where("F", Predicates.blocks(Blocks.DAYLIGHT_DETECTOR))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                    GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_ENERGETIC =
            registerPhotovoltaicPowerStation("energetic", 1, CTNHBlocks.ENERGETIC_PHOTOVOLTAIC_BLOCK,
                    "充能光伏发电站");
    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_PULSATING =
            registerPhotovoltaicPowerStation("pulsating", 4, CTNHBlocks.PULSATING_PHOTOVOLTAIC_BLOCK,
                    "脉冲光伏发电站");
    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_VIBRANT =
            registerPhotovoltaicPowerStation("vibrant", 16, CTNHBlocks.VIBRANT_PHOTOVOLTAIC_BLOCK,
                    "振动光伏发电站");

    private static Lang photovoltaicPowerStationTooltip(String tier, int index) {
        return switch (tier) {
            case "energetic" -> switch (index) {
                case 0 -> photovoltaicPowerStationEnergeticTooltip0;
                case 1 -> photovoltaicPowerStationEnergeticTooltip1;
                case 2 -> photovoltaicPowerStationEnergeticTooltip2;
                default -> throw new IllegalArgumentException("Unsupported photovoltaic tooltip index: " + index);
            };
            case "pulsating" -> switch (index) {
                case 0 -> photovoltaicPowerStationPulsatingTooltip0;
                case 1 -> photovoltaicPowerStationPulsatingTooltip1;
                case 2 -> photovoltaicPowerStationPulsatingTooltip2;
                default -> throw new IllegalArgumentException("Unsupported photovoltaic tooltip index: " + index);
            };
            case "vibrant" -> switch (index) {
                case 0 -> photovoltaicPowerStationVibrantTooltip0;
                case 1 -> photovoltaicPowerStationVibrantTooltip1;
                case 2 -> photovoltaicPowerStationVibrantTooltip2;
                default -> throw new IllegalArgumentException("Unsupported photovoltaic tooltip index: " + index);
            };
            default -> throw new IllegalArgumentException("Unsupported photovoltaic power station tier: " + tier);
        };
    }

    public static MultiblockMachineDefinition registerPhotovoltaicPowerStation(String tier, int basicRate,
                                                                                BlockEntry<?> photovoltaicBlock,
                                                                                String cnName) {
        return REGISTRATE.multiblock("photovoltaic_power_station_" + tier, holder -> new PhotovoltaicPowerStationMachine(holder, basicRate))
                .cnLangValue(cnName)
                .rotationState(RotationState.NON_Y_AXIS)
                .tooltips(photovoltaicPowerStationTooltip(tier, 0).translate().withStyle(ChatFormatting.GRAY),
                        photovoltaicPowerStationTooltip(tier, 1).translate(),
                        photovoltaicPowerStationTooltip(tier, 2).translate())
                .appearanceBlock(CTNHBlocks.CASING_REFLECT_LIGHT)
                .allowExtendedFacing(false)
                .allowFlip(false)
                .pattern(definition -> FactoryStaticBlockPattern.start()
                        .aisle("#AAAAAAA#", "#########", "#AAAAAAA#", "####B####", "####B####", "####B####", "#########")
                        .aisle("AAAAAAAAA", "##AAAAA##", "AAAAAAAAA", "#########", "#########", "##CCCCC##", "#CC###CC#")
                        .aisle("AAAAAAAAA", "#AA###AA#", "AAADDDAAA", "#########", "#########", "##CEEEC##", "#CE###EC#")
                        .aisle("AAAAAAAAA", "#A#####A#", "AADDDDDAA", "#########", "#########", "##CEEEC##", "#CE###EC#")
                        .aisle("AAAAAAAAA", "#A#####A#", "AADDDDDAA", "#########", "#########", "##CEEEC##", "#CE###EC#")
                        .aisle("AAAAAAAAA", "#A#####A#", "AADDDDDAA", "#########", "#########", "##CEEEC##", "#CE###EC#")
                        .aisle("AAAAAAAAA", "#AA###AA#", "AAADDDAAA", "#########", "#########", "##CEEEC##", "#CE###EC#")
                        .aisle("AAAAAAAAA", "##AAAAA##", "AAAAAAAAA", "#########", "#########", "##CCCCC##", "#CC###CC#")
                        .aisle("#AAA@AAA#", "#########", "#AAAAAAA#", "####B####", "####B####", "####B####", "#########")
                        .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                        .where("A", Predicates.blocks(CASING_REFLECT_LIGHT.get())
                                .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY)
                                        .setMinGlobalLimited(1)
                                        .setMaxGlobalLimited(2)
                                        .setPreviewCount(2)
                                )
                                .or(Predicates.autoAbilities(true, false, false))
                        )
                        .where("B", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                        .where("#", Predicates.any())
                        .where("D", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                        .where("E", Predicates.blocks(photovoltaicBlock.get()), false)
                        .where("C", Predicates.blocks(CASING_REFLECT_LIGHT.get()), false)
                        .build())
                .workableCasingModel(CTNHCore.id("block/casings/reflect_light_casing"),
                        GTCEu.id("block/multiblock/generator/large_steam_turbine"))
                .register();
    }

    public static final MultiblockMachineDefinition WIND_POWER_ARRAY = WindPowerArrayRegister.register("wind_power_array",1,CASING_STEEL_SOLID, GTMaterials.Steel,"machine_casing_solid_steel", "风力发电阵列");
    public static final MultiblockMachineDefinition ADVANCED_WIND_POWER_ARRAY = WindPowerArrayRegister.register("advanced_wind_power_array",2,CASING_STAINLESS_CLEAN,GTMaterials.StainlessSteel,"machine_casing_clean_stainless_steel", "进阶风力发电阵列");
    public static final MultiblockMachineDefinition SUPER_WIND_POWER_ARRAY = WindPowerArrayRegister.register("super_wind_power_array",3,CASING_TUNGSTENSTEEL_ROBUST, TungstenSteel,"machine_casing_robust_tungstensteel", "超级风力发电阵列");

    public static final MultiblockMachineDefinition SLAUGHTER_HOUSE = REGISTRATE.multiblock("slaughter_house", SlaughterHouseMachine::new)
            .cnLangValue("屠宰场")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SLAUGHTER_HOUSE)
            .tooltips(slaughterHouseTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    slaughterHouseTooltip1.translate(),
                    slaughterHouseTooltip2.translate(),
                    slaughterHouseTooltip3.translate().withStyle(ChatFormatting.RED),
                    slaughterHouseTooltip4.translate().withStyle(ChatFormatting.GREEN),
                    slaughterHouseTooltip5.translate())
            .recipeModifiers(SlaughterHouseMachine::recipeModifier, GTRecipeModifiers.BATCH_MODE)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "ABBBA", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "ABBBA")
                    .aisle("BAAAB", "BDDDB", "CDDDC", "CDDDC", "CDDDC", "CDDDC", "BAAAB")
                    .aisle("BAAAB", "BD#DB", "CD#DC", "CD#DC", "CD#DC", "CD#DC", "BAEAB")
                    .aisle("BAAAB", "BDDDB", "CDDDC", "CDDDC", "CDDDC", "CDDDC", "BAAAB")
                    .aisle("AB@BA", "ABBBA", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "ABBBA")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("D", Predicates.blocks(EIOBlocks.DARK_STEEL_BARS.get()))
                    .where("E", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public final static MultiblockMachineDefinition COKE_TOWER = REGISTRATE.multiblock("coke_tower", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("焦化塔")
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.PYROLYSE_RECIPES)
            .tooltips(cokeTowerTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    cokeTowerTooltip1.translate())
            .recipeModifiers(GTRecipeModifiers::multiSmelterParallel, BATCH_MODE)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("BDDDB", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CCHCC")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("ABBBA", "ACCCA", "AC@CA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .where("A", Predicates.frames(GTMaterials.StainlessSteel))
                    .where("B", Predicates.blocks(HEAT_VENT.get()))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(130)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(FIREBOX_TITANIUM.get()))
                    .where("E", Predicates.heatingCoils())
                    .where("F", Predicates.blocks(CASING_INVAR_HEATPROOF.get()))
                    .where("G", Predicates.blocks(GTBlocks.CASING_STEEL_PIPE.get()))
                    .where("H", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((controller, components) -> {
                if (controller.isFormed() && controller instanceof CoilWorkableElectricMultiblockMachine machine) {
                    var coilTier = machine.getTraitOrThrow(CoilMachineTrait.class).getCoilTier();
                    components.add(Component.translatable("gtceu.multiblock.pyrolyse_oven.speed", coilTier == 0 ? 75 : 50 * (coilTier + 15)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/machines/fluid_heater"))
            .register();

    public final static MultiblockMachineDefinition BEDROCK_DRILLING_RIGS = REGISTRATE.multiblock("bedrock_drilling_rigs", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("基岩钻机")
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.BEDROCK_DRILLING_RIGS)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH)
            .tooltips(CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate())
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .appearanceBlock(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#######", "AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .aisle("#######", "A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######", "A#####A", "##BCB##", "###C###", "##CCC##", "##CCC##", "##CCC##", "A#BCB#A")
                    .aisle("###E###", "A##C##A", "##CCC##", "##CDC##", "##CDC##", "##CDC##", "##CDC##", "A#CCC#A")
                    .aisle("#######", "A#####A", "##BCB##", "###C###", "##CCC##", "##C@C##", "##CCC##", "A#BCB#A")
                    .aisle("#######", "A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######", "AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .where("A", Predicates.blocks(GCYMBlocks.CASING_SECURE_MACERATION.get()))
                    .where("#", Predicates.any())
                    .where("B", Predicates.frames(GTMaterials.TungstenCarbide))
                    .where("C", Predicates.blocks(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING.get()).setMinGlobalLimited(20)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.INPUT_LASER).setPreviewCount(2)))
                    .where("D", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("E", Predicates.blocks(Blocks.BEDROCK))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public final static MultiblockMachineDefinition NAQ_REACTOR_MK3 = REGISTRATE.multiblock("naq_reactor_mk3", NaqReactorMachine::new)
            .cnLangValue("超级硅岩反应堆")
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.NAQ_MK1)
            .generator(true)
            .recipeModifier(NaqReactorMachine::recipeModifier, true)
            .tooltips(naqReactorMk3Tooltip1.translate().withStyle(ChatFormatting.GRAY))
            .tooltips(naqReactorMk3Tooltip2.translate())
            .tooltips(naqReactorMk3Tooltip3.translate())
            .tooltips(naqReactorMk3Tooltip4.translate())
            .tooltips(CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate())
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("        BBBBBBB        ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "        BBBBBBB        ")
                    .aisle("      BBBBBBBBBBB      ", "          DDD          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          DDD          ", "      BBBBBBBBBBB      ")
                    .aisle("    BBBBBBBBBBBBBBB    ", "        BBBBBBB        ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "        BBBBBBB        ", "    BBBBBBBBBBBBBBB    ")
                    .aisle("   BBBBBBBBBBBBBBBBB   ", "      BBBBBBBBBBB      ", "          EEE          ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "          EEE          ", "      BBBBBBBBBBB      ", "   BBBBBBBBBBBBBBBBB   ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "     BBBBBBBBBBBBB     ", "          EEE          ", "           E           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "          EEE          ", "          EEE          ", "     BBBBBBBBBBBBB     ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "    BBBBBBBBBBBBBBB    ", "        EEEEEEE        ", "           E           ", "           E           ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "          CDC          ", "         FFFFF         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "         FFFFF         ", "          CDC          ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "           E           ", "          EEE          ", "        EEEEEEE        ", "    BBBBBBBBBBBBBBB    ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBEEEBEEEBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          DDD          ", "       FF     FF       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "       FF     FF       ", "          DDD          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBEEEBEEEBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBBEBBBEBBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      FF       FF      ", "        F     F        ", "                       ", "                       ", "         FFFFF         ", "        FFFFFFF        ", "         FFFFF         ", "                       ", "                       ", "        F     F        ", "      FF       FF      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBBEBBBEBBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEBBEBBBEBBEE     ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      F         F      ", "       F       F       ", "                       ", "                       ", "        F     F        ", "       FF     FF       ", "        F     F        ", "                       ", "                       ", "       F       F       ", "      F         F      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "     EEBBEBBBEBBEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEEEEEBEEEEEE     ", "         EEEEE         ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "     F           F     ", "      F         F      ", "                       ", "                       ", "       F       F       ", "      FF   G   FF      ", "       F       F       ", "                       ", "                       ", "      F         F      ", "     F           F     ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "         EEEEE         ", "     EEEEEEBEEEEEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "CDBBBBBBBBBBBBBBBBBBBDC", " CDEEEEBBEHHHEBBEEEEDC ", "  CD  EEEEEEEEEEE  DC  ", "  CD      EEE      DC  ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     F     E     F     ", "      F         F      ", "                       ", "                       ", "       F   G   F       ", "      FF  GGG  FF      ", "       F   G   F       ", "                       ", "                       ", "      F         F      ", "     F     E     F     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "  CD      EEE      DC  ", "  CDEEEEEEEEEEEEEEEDC  ", " CDEEEEBBEHHHEBBEEEEDC ", "CDBBBBBBBBBBBBBBBBBBBDC", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "DDBBBBBBBBBBBBBBBBBBBDD", " DDEEEBBBBHIHBBBBEEEDD ", "  DDEEEEEEEIEEEEEEEDD  ", "  DDDEEEEEEIEEEEEEDDD  ", "   DDD    EIE    DDD   ", "   DDD    EIE    DDD   ", "    DDD   EIE   DDD    ", "    DDD   EIE   DDD    ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     F    EIE    F     ", "      F    E    F      ", "                       ", "           G           ", "       F  GGG  F       ", "      FF GGGGG FF      ", "       F  GGG  F       ", "           G           ", "                       ", "      F    E    F      ", "     F    EIE    F     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "    DDD   EIE   DDD    ", "    DDD   EIE   DDD    ", "   DDD    EIE    DDD   ", "   DDD    EIE    DDD   ", "  DDDEEEEEEIEEEEEEDDD  ", "  DDEEEEEEEIEEEEEEEDD  ", " DDEEEBBBBHIHBBBBEEEDD ", "DDBBBBBBBBBBBBBBBBBBBDD", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "CDBBBBBBBBBBBBBBBBBBBDC", " CDEEEEBBEHHHEBBEEEEDC ", "  CD  EEEEEEEEEEE  DC  ", "  CD      EEE      DC  ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     F     E     F     ", "      F         F      ", "                       ", "                       ", "       F   G   F       ", "      FF  GGG  FF      ", "       F   G   F       ", "                       ", "                       ", "      F         F      ", "     F     E     F     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "  CD      EEE      DC  ", "  CDEEEEEEEEEEEEEEEDC  ", " CDEEEEBBEHHHEBBEEEEDC ", "CDBBBBBBBBBBBBBBBBBBBDC", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEEEEEBEEEEEE     ", "         EEEEE         ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "     F           F     ", "      F         F      ", "                       ", "                       ", "       F       F       ", "      FF   G   FF      ", "       F       F       ", "                       ", "                       ", "      F         F      ", "     F           F     ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "         EEEEE         ", "     EEEEEEBEEEEEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEBBEBBBEBBEE     ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      F         F      ", "       F       F       ", "                       ", "                       ", "        F     F        ", "       FF     FF       ", "        F     F        ", "                       ", "                       ", "       F       F       ", "      F         F      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "     EEBBEBBBEBBEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBBEBBBEBBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      FF       FF      ", "        F     F        ", "                       ", "                       ", "         FFFFF         ", "        FFFFFFF        ", "         FFFFF         ", "                       ", "                       ", "        F     F        ", "      FF       FF      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBBEBBBEBBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBEEEBEEEBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          DDD          ", "       FF     FF       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "       FF     FF       ", "          DDD          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBEEEBEEEBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "    BBBBBBBBBBBBBBB    ", "        EEEEEEE        ", "          EEE          ", "           E           ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "          CDC          ", "         FFFFF         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "         FFFFF         ", "          CDC          ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "           E           ", "          EEE          ", "        EEEEEEE        ", "    BBBBBBBBBBBBBBB    ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "     BBBBBBBBBBBBB     ", "          EEE          ", "          EEE          ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "          EEE          ", "          EEE          ", "     BBBBBBBBBBBBB     ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("   BBBBBBBBBBBBBBBBB   ", "      BBBBBBBBBBB      ", "          EEE          ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "          EEE          ", "      BBBBBBBBBBB      ", "   BBBBBBBBBBBBBBBBB   ")
                    .aisle("    BBBBBBBBBBBBBBB    ", "        BBBBBBB        ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "        BBBBBBB        ", "    BBBBBBBBBBBBBBB    ")
                    .aisle("      BBBBBBBBBBB      ", "          DDD          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          DDD          ", "      BBBBBBBBBBB      ")
                    .aisle("        BBB@BBB        ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "        BBBBBBB        ")
                    .where(" ", Predicates.any())
                    .where("B", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(abilities(PartAbility.OUTPUT_LASER).setPreviewCount(1)))
                    .where("C", Predicates.frames(GTMaterials.Naquadria))
                    .where("D", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get()))
                    .where("E", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get()))
                    .where("F", Predicates.blocks(CTNHBlocks.PLASMA_COOLED_CORE.get()))
                    .where("G", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKI.get()))
                    .where("H", Predicates.blocks(CTNHBlocks.REACTOR_CONDENSATION_BLOCK.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("I", Predicates.blocks(ATOMS_SPLIT_BLOCKS.get()))
                    .build()
            )

            .workableCasingModel(CTNHCore.id("block/casings/nq_alloy_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public static final MultiblockMachineDefinition[] COMPRESSED_FUSION_REACTOR = CTNHMachineUtils.registerTieredMultis("compressed_fusion_reactor",
            (holder, tier) -> new FusionReactorMachine(holder, tier),
            (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("Fusion Reactor Computer MK %s".formatted(FormattingUtil.toRomanNumeral(tier - 3)))
                    .recipeType(GTRecipeTypes.FUSION_RECIPES)
                    .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, FusionReactorMachine::recipeModifier)
                    .tooltips(
                            Component.translatable("gtceu.machine.fusion_reactor.capacity",
                                    FusionReactorMachine.calculateEnergyStorageFactor(tier, 16) / 1000000L),
                            Component.translatable("gtceu.machine.fusion_reactor.overclocking"))
                    .tooltips(CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate())
                    .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))

                    .appearanceBlock(() -> CTNHFusionCasingType.getCasingState(tier))
                    .pattern((definition) -> {
                        TraceabilityPredicate casing = Predicates.blocks(CTNHFusionCasingType.getCasingState(tier));
                        return FactoryBlockPattern.start()
                                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCIBICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                    FCCCCCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCCCCCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCCCCCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCCCCCF                    ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                    FCCCCCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCCCCCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCCCCCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCCCCCF                    ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                                               ", "                                               ", "                    FCPPPCF                    ", "                    FCISICF                    ", "                    FCPPPCF                    ", "                                               ", "                                               ")
                                .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                                .where("B", Predicates.blocks(FUSION_GLASS.get()))
                                .where("C", casing)
                                .where("P", casing.or(abilities(PartAbility.PARALLEL_HATCH)))
                                .where("I", casing.or(abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(16))
                                        .or(abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(16)))
                                .where("F", Predicates.blocks(CTNHFusionCasingType.getFrameState(tier)))
                                .where("H", Predicates.blocks(CTNHFusionCasingType.getCompressedCoilState(tier)))
                                .where("E", casing.or(abilities(PartAbility.INPUT_ENERGY)).or(abilities(PartAbility.INPUT_LASER).setPreviewCount(16)))
                                .where("#", Predicates.any())
                                .where(" ", Predicates.any())
                                .build();//结构相关代码取自GTL
                    })
                    .workableCasingModel(CTNHFusionCasingType.getCasingType(tier).getTexture(), GTCEu.id("block/multiblock/fusion_reactor"))
                    .register(),
            GTValues.LuV, GTValues.ZPM, GTValues.UV);
    public final static MultiblockMachineDefinition SWEATSHOP = REGISTRATE.multiblock("sweat_shop", FactoryMachine::new)
            .cnLangValue("§4血汗工厂")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CENTRIFUGE_RECIPES, GTRecipeTypes.LATHE_RECIPES, GTRecipeTypes.BENDER_RECIPES,
                    GTRecipeTypes.MACERATOR_RECIPES, GTRecipeTypes.MIXER_RECIPES, GTRecipeTypes.EXTRACTOR_RECIPES,
                    GTRecipeTypes.WIREMILL_RECIPES, GTRecipeTypes.LASER_ENGRAVER_RECIPES, GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES)
            .recipeModifiers(FactoryMachine::recipeModifier, OC_NON_PERFECT, BATCH_MODE)
            .tooltips(sweatShopTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .tooltips(sweatShopTooltip1.translate())
            .tooltips(sweatShopTooltip2.translate())
            .tooltips(sweatShopTooltip3.translate())
            .tooltips(sweatShopTooltip4.translate())
            .tooltips(sweatShopTooltip5.translate())
            .tooltips(sweatShopTooltip6.translate())
            .tooltips(sweatShopTooltip7.translate())
            .tooltips(sweatShopTooltip8.translate())
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start(RelativeDirection.LEFT, RelativeDirection.UP, RelativeDirection.BACK)
                    .aisle("aaaaa", "aadaa", "aaaaa", "aaaaa")
                    .aisle("ccccc", "a b a", "e   e", "ccccc").setRepeatable(3, 16)
                    .aisle("aaaaa", "aaaaa", "aaaaa", "aaaaa")
                    .where("a", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("b", Predicates.blocks(AllBlocks.ANDESITE_CASING.get()))
                    .where("c", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("d", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("e", Predicates.blocks(Blocks.IRON_BARS))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

    public final static MultiblockMachineDefinition PLASMA_CONDENSER = REGISTRATE.multiblock("plasma_condenser", WorkableElectricMultiblockMachine::new)
            .cnLangValue("等离子冷凝器")
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.PLASMA_CONDENSER_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, OC_NON_PERFECT, BATCH_MODE)
            .tooltips(plasmaCondenserTooltip1.translate().withStyle(ChatFormatting.GRAY),
                    CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate(),
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .appearanceBlock(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####AAA#####", "#####AAA#####", "#####AAA#####")
                    .aisle("####AAAAA####", "####BCCCB####", "####AAAAA####")
                    .aisle("##AAAAAAAAA##", "##AACABACAA##", "##AAAAAAAAA##")
                    .aisle("##AAA###AAA##", "##ACA###ACA##", "##AAA###AAA##")
                    .aisle("#AAA#####AAA#", "#BCA#####ACB#", "#AAA#####AAA#")
                    .aisle("AAA#######AAA", "ACA#######ACA", "AAA#######AAA")
                    .aisle("AAA#######AAA", "ACB#######BCA", "AAA#######AAA")
                    .aisle("AAA#######AAA", "ACA#######ACA", "AAA#######AAA")
                    .aisle("#AAA#####AAA#", "#BCA#####ACB#", "#AAA#####AAA#")
                    .aisle("##AAA###AAA##", "##ACA###ACA##", "##AAA###AAA##")
                    .aisle("##AAAAAAAAA##", "##AACABACAA##", "##AAAAAAAAA##")
                    .aisle("####AAAAA####", "####BCCCB####", "####AAAAA####")
                    .aisle("#####AAA#####", "#####A@A#####", "#####AAA#####")
                    .where("A", Predicates.blocks(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE.get()).setMinGlobalLimited(160)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(abilities(PartAbility.PARALLEL_HATCH))
                            .or(abilities(PartAbility.INPUT_LASER))
                            .or(abilities(PartAbility.INPUT_ENERGY)))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(FUSION_GLASS.get()))
                    .where("C", Predicates.blocks(CTNHBlocks.PLASMA_COOLED_CORE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"),
                    GTCEu.id("block/multiblock/fusion_reactor"))
            .register();



    public final static MultiblockMachineDefinition MEADOW = REGISTRATE.multiblock("meadow", MeadowMachine::new)
            .cnLangValue("§6牧场")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MEADOW)
            .recipeModifier(MeadowMachine::stressCrossParallel)
            .tooltips(meadowTooltip0.translate(),
                    meadowTooltip1.translate(),
                    meadowTooltip2.translate(),
                    meadowTooltip3.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBBBBBBBBBB", "JCCCJCCCCCC", "J###J######", "JJJJJD#####", "EEEEE######", "###########")
                    .aisle("BBBBFFFBBBB", "CEE####GG#C", "#E#####GG##", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBFFFBBBB", "CE#####GG#C", "###########", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBFFFBBBB", "C#######G#C", "###########", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBBFFBBBB", "J###J#####C", "J###J######", "JJJJJD#####", "EEEEE######", "###########")
                    .aisle("BEEBFFFHHHB", "C#########C", "###########", "DDDDDD#####", "###########", "###########")
                    .aisle("BEEBFFFHHHB", "C######II#C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFHHHB", "C#######I#C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFBHHB", "C#########C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFBHHB", "C########IC", "###########", "###########", "###########", "###########")
                    .aisle("BBBBB@BBBBB", "CCCCCCCCCCC", "###########", "###########", "###########", "###########")
                    .where("B", Predicates.blocks(Blocks.DIRT)
                            .or(Predicates.blocks(Blocks.GRASS_BLOCK))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(CTPPPartAbility.INPUT_KINETIC)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(Blocks.OAK_FENCE))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(Blocks.OAK_STAIRS))
                    .where("E", Predicates.blocks(Blocks.HAY_BLOCK))
                    .where("F", Predicates.blocks(Blocks.DIRT_PATH))
                    .where("G", Predicates.blocks(Blocks.BONE_BLOCK))
                    .where("H", Predicates.blocks(Blocks.WATER))
                    .where("I", Predicates.blocks(Blocks.LILY_PAD))
                    .where("J", Predicates.blocks(Blocks.OAK_LOG))
                    .build()
            )
            .workableCasingModel(ResourceLocation.tryParse("minecraft:block/dirt"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public final static MultiblockMachineDefinition LARGE_BOTTLE = REGISTRATE.multiblock("large_bottle", holder -> new LargeBottleMachine(holder, 10000 * 1000, null))
            .cnLangValue("发酵瓶")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(DUMMY_RECIPES)
            .tooltips(largeBottleTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    largeBottleTooltip1.translate(),
                    largeBottleTooltip2.translate())
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##AAAAA##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "##CCCCC##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "#########", "#########", "#########", "#########", "#########", "#########", "#########")
                    .aisle("#AAAAAAA#", "#B#####B#", "#B#####B#", "#BAAAAAB#", "#C#####C#", "#B#####B#", "#B#####B#", "#B#####B#", "#BBBBBBB#", "###BBB###", "#########", "#########", "#########", "#########", "#########")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "##BBBBB##", "###CCC###", "###BBB###", "###BBB###", "###BBB###", "###AAA###")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "#BB###BB#", "##CDDDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B###E###B", "B###E###B", "BAAAEAAAB", "C###E###C", "B###E###B", "B###E###B", "B###E###B", "#B##E##B#", "#BB#E#BB#", "##CDEDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "#BB###BB#", "##CDDDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "##BBBBB##", "###CCC###", "###BBB###", "###BBB###", "###BBB###", "###AAA###")
                    .aisle("#AAAAAAA#", "#B#####B#", "#B#####B#", "#BAAAAAB#", "#C#####C#", "#B#####B#", "#B#####B#", "#B#####B#", "#BBBBBBB#", "###BBB###", "#########", "#########", "#########", "#########", "#########")
                    .aisle("##AA@AA##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "##CCCCC##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "#########", "#########", "#########", "#########", "#########", "#########", "#########")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.heatingCoils())
                    .where("D", Predicates.blocks(FILTER_CASING.get()))
                    .where("E", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public final static MultiblockMachineDefinition FERMENTING_TANK = REGISTRATE.multiblock("fermenting_tank", FermentingTankMachine::new)
            .cnLangValue("发酵罐")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FERMENTING)
            .tooltips(fermentingTankTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"),
                    CTNHCommonTooltips.SUBTICK_PARALLEL,
                    Component.literal("=========================================================="),
                    fermentingTankTooltip1.translate().withStyle(ChatFormatting.GREEN),
                    fermentingTankTooltip2.translate(),
                    fermentingTankTooltip3.translate())
            .recipeModifiers(FermentingTankMachine::recipeModifier, GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("C   C", "C   C", "CCCCC", "H   H", "H   H", "H   H", "DAAAD")
                    .aisle("     ", " GGG ", "CGGGC", " MMM ", " GGG ", " GGG ", "AAAAA")
                    .aisle("     ", " GGG ", "CG GC", " M M ", " G G ", " G G ", "AABAA")
                    .aisle("     ", " GGG ", "CGGGC", " MMM ", " GGG ", " GGG ", "AAAAA")
                    .aisle("C   C", "CAKAC", "CAAAC", "H   H", "H   H", "H   H", "DAAAD")
                    .where("C", Predicates.frames(GTMaterials.Steel))
                    .where("H", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("D", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()))
                    .where("B", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("G", Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where(" ", Predicates.air())
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public final static MultiblockMachineDefinition LARGE_FERMENTING_TANK = REGISTRATE.multiblock("large_fermenting_tank", LargeFermentingTankMachine::new)
            .cnLangValue("大型发酵罐")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FERMENTING)
            .tooltips(largeFermentingTankTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"),
                    CTNHCommonTooltips.SUBTICK_PARALLEL,
                    Component.literal("=========================================================="),
                    fermentingTankTooltip1.translate().withStyle(ChatFormatting.GREEN),
                    fermentingTankTooltip2.translate(),
                    fermentingTankTooltip3.translate(),
                    largeFermentingTankTooltip1.translate())
            .recipeModifiers((machine, group, recipe) -> {
                var failure = FermentingTankMachine.recipeModifier(machine, group, recipe);
                return failure != null ? failure : CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 8);
            }, GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##########AAAAAAAAAAA", "##########ABBBBBBBBBA", "##########ABBBBBBBBBA", "##########AAAAAAAAAAA", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############AAAAAAA##", "############AABBBAA##", "############AABBBAA##", "############AABBBAA##", "############AAAAAAA##")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "############CCCCCCC##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############CCCCCCC##", "###########DABBBBBAD#", "###########DB#####BD#", "###########DB#####BD#", "###########DB#####BD#", "###########DAAAAAAAD#")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CCDAAADCC#", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "###########CCDAAADCC#", "##########AAABBBBBAAA", "##########ABB#####BBA", "##########ABB#####BBA", "##########ABB#####BBA", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CDAAAAADC#", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "############DFFFFFD##", "############DFFFFFD##", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "###########CDAAAAADC#", "##########ABBBBBBBBBA", "AAAAA#####A#########A", "ABBBA#####A#########A", "AAAAA#####A#########A", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B###B###", "#############B###B###", "#############B###B###", "#############F###F###", "#############F###F###", "#############B###B###", "#############B###B###", "#############B###B###", "###########CAAFFFAAC#", "##########ABBBBBBBBBA", "AHHHAAAAAAA#########B", "B###BBBBBBB#########B", "AAAAAAAAAAA#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B#H#B###", "#############B#H#B###", "#############B#H#B###", "#############F#H#F###", "#############F#H#F###", "#############B#H#B###", "#############B#H#B###", "#############B#H#B###", "###########CAAFIFAAC#", "##########ABBBBBBBBBA", "AHHHBBBBBBB#########B", "B###################B", "AAAAABBBBBB#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B###B###", "#############B###B###", "#############B###B###", "#############F###F###", "#############F###F###", "#############B###B###", "#############B###B###", "#############B###B###", "###########CAAFFFAAC#", "##########ABBBBBBBBBA", "AHHHAAAAAAA#########B", "B###BBBBBBB#########B", "AAAAAAAAAAA#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CDAAAAADC#", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "############DFFFFFD##", "############DFFFFFD##", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "###########CDAAAAADC#", "##########ABBBBBBBBBA", "AAAAA#####A#########A", "ABBBA#####A#########A", "AAAAA#####A#########A", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CCDAAADCC#", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "###########CCDAAADCC#", "##########AAABBBBBAAA", "##########ABB#####BBA", "##########ABB#####BBA", "##########ABB#####BBA", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "############CCCCCCC##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############CCCCCCC##", "###########DABBBBBAD#", "###########DB#####BD#", "###########DB#####BD#", "###########DB#####BD#", "###########DAAAAAAAD#")
                    .aisle("##########AJJJJ@JJJJA", "##########AJJJJJJJJJA", "##########AJJJJJJJJJA", "##########AJJJJJJJJJA", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############AAAAAAA##", "############AABBBAA##", "############AABBBAA##", "############AABBBAA##", "############AAAAAAA##")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.blocks(Blocks.SMOOTH_STONE_SLAB))
                    .where("D", Predicates.frames(GTMaterials.Invar))
                    .where("F", Predicates.heatingCoils())
                    .where("H", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("I", Predicates.blocks(CASING_TITANIUM_GEARBOX.get()))
                    .where("J", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public final static MultiblockMachineDefinition DIGESTION_TANK = REGISTRATE.multiblock("digestion_tank", DigestingTankMachine::new)
            .cnLangValue("化粪池")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.DIGESTING)
            .tooltips(digestionTankTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    digestionTankTooltip1.translate().withStyle(ChatFormatting.GREEN),
                    digestionTankTooltip2.translate())
            .recipeModifiers(BioMachine::recipeModifier, OC_NON_PERFECT, BATCH_MODE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCC", "CAAAC", "CCCCC")
                    .aisle("CCCCC", "AWWWA", "CDDDC")
                    .aisle("CCCCC", "CAKAC", "CGGGC")
                    .where("C", Predicates.blocks(Blocks.BRICKS))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.blocks(Blocks.IRON_TRAPDOOR))
                    .where("A", Predicates.blocks(Blocks.BRICKS)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("G", Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where("W", Predicates.blocks(Blocks.WATER))
                    .build()
            )
            .workableCasingModel(ResourceLocation.tryParse("minecraft:block/bricks"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public final static MultiblockMachineDefinition BLAZE_BLAST_FURNACE = REGISTRATE.multiblock("blaze_blast_furnace", BlazeBlastFurnaceMachine::new)
            .cnLangValue("§c炽焱高炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(BlazeBlastFurnaceMachine::recipeModifier, GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .appearanceBlock(CTNHBlocks.BLAZE_BLAST_FURNACE_CASING)
            .tooltips(blazeBlastFurnaceTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    blazeBlastFurnaceTooltip1.translate(),
                    blazeBlastFurnaceTooltip2.translate(),
                    blazeBlastFurnaceTooltip3.translate().withStyle(ChatFormatting.DARK_GREEN),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("GGG", "MMM", "MMM", "GGG")
                    .aisle("GGG", "M M", "M M", "GBG")
                    .aisle("GKG", "MMM", "MMM", "GGG")
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("B", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("G", Predicates.blocks(CTNHBlocks.BLAZE_BLAST_FURNACE_CASING.get()).setMinGlobalLimited(4)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where(" ", Predicates.air())
                    .build()
            )
            .workableCasingModel(CTNHCore.id("block/casings/blaze_blast_furnace_casing"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    // Come from GTCA
    public static final MultiblockMachineDefinition SUPER_EBF = REGISTRATE
            .multiblock("super_ebf", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("超级电力高炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(PARALLEL_HATCH, (machine, group, recipe) -> {
                recipe.multiplyDuration(0.5);
                return null;
            }, GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXXXX", "FFXXXFF", "F#####F", "F#####F", "F#####F", "FFXXXFF", "XXXVXXX", "##XXX##", "#######")
                    .aisle("XXXXXXX", "FXCCCXF", "##CCC##", "##III##", "##CCC##", "FXCCCXF", "XXXXXXX", "#XXXXX#", "##XXX##")
                    .aisle("XXXXXXX", "XCC#CCX", "#CC#CC#", "#I###I#", "#CC#CC#", "XCC#CCX", "XXXXXXX", "XXXHXXX", "#X###X#")
                    .aisle("XXXXXXX", "XC###CX", "#C###C#", "#I###I#", "#C###C#", "XC###CX", "VXXXXXV", "XXHHHXX", "#X###X#")
                    .aisle("XXXXXXX", "XCC#CCX", "#CC#CC#", "#I###I#", "#CC#CC#", "XCC#CCX", "XXXXXXX", "XXXHXXX", "#X###X#")
                    .aisle("XXXXXXX", "FXCCCXF", "##CCC##", "##III##", "##CCC##", "FXCCCXF", "XXXXXXX", "#XXXXX#", "##XXX##")
                    .aisle("XXXSXXX", "FFXXXFF", "F#####F", "F#####F", "F#####F", "FFXXXFF", "XXXVXXX", "##XXX##", "#######")
                    .where("S", Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where("F", Predicates.frames(GTMaterials.Tungsten))
                    .where("V", Predicates.blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                    .where("I", Predicates.blocks(HEAT_VENT.get()))
                    .where("X", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(158)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, true, true)))
                    .where("H", abilities(PartAbility.MUFFLER))
                    .where("C", Predicates.heatingCoils())
                    .where("#", Predicates.any())
                    .build()
            )
            .recoveryItems(
                    () -> new ItemLike[]{MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), CTNHCore.id("block/overlay/super_ebf"))
            .tooltips(
                    CTNHCommonTooltips.PARALLEL_HATCH,
                    superEbfTooltip0.translate()
            )
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                            Component.translatable(FormattingUtil.formatNumbers(coilMachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() +
                                            100L * Math.max(0, coilMachine.getTier() - MV)) + "K")
                                    .withStyle(ChatFormatting.RED)));
                }
            })
            .register();
    //Come from GTCA
    public static final MultiblockMachineDefinition MEGA_OIL_CRACKING_UNIT = REGISTRATE
            .multiblock("mega_oil_cracking_unit", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("巨型原油裂解厂")
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.CRACKING_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers::crackerOverclock, BATCH_MODE)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("HHHHHHHHHHHHH", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#")
                    .aisle("HHHHHHHHHHHHH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HHGGGGGGGGGHH")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#HGGGGGGGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C###C###H#", "#H#C#C#C#C#H#", "#H#C###C###H#", "#G#C#C#C#C#G#", "#HGGGHHHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C#C#C#C#H#", "#O#C#C#C#C#I#", "#H#C#C#C#C#H#", "#G#C#C#C#C#G#", "#HGGGHAHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C###C###H#", "#H#C#C#C#C#H#", "#H#C###C###H#", "#G#C#C#C#C#G#", "#HGGGHHHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#HGGGGGGGGGH#")
                    .aisle("HHHHHHHHHHHHH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HHGGGGGGGGGHH")
                    .aisle("HHHHHHXHHHHHH", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#")
                    .where("X", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("H", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(12)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, true, true)))
                    .where("#", Predicates.any())
                    .where("C", Predicates.heatingCoils())
                    .where("G", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("I", abilities(PartAbility.IMPORT_FLUIDS))
                    .where("A", abilities(PartAbility.IMPORT_FLUIDS))
                    .where("O", abilities(PartAbility.EXPORT_FLUIDS))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/cracking_unit"))
            .tooltips(
                    CTNHCommonTooltips.PARALLEL_HATCH,
                    Component.translatable("gtceu.machine.cracker.tooltip.1")
            )
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.cracking_unit.energy",
                            100 - 10 * coilMachine.getTraitOrThrow(CoilMachineTrait.class).getCoilTier()));
                }
            })
            .register();
    //Come from GTCA
    public static final MultiblockMachineDefinition MEGA_LCR = REGISTRATE
            .multiblock("mega_lcr", RecipeElectricMultiblockMachine::new)
            .cnLangValue("巨型化学反应釜")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.LARGE_CHEMICAL_RECIPES)
            .appearanceBlock(CASING_PTFE_INERT)
            .recipeModifiers(GTRecipeModifiers.OC_PERFECT_SUBTICK, GTRecipeModifiers.BATCH_MODE)
            .pattern(definition ->
                    FactoryBlockPattern.start()
                            .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "CGCGC", "CGEGC", "CGCGC", "CCCCC")
                            .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("N", Predicates.heatingCoils())
                            .where("G", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                            .where("P", Predicates.blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                            .where("#", Predicates.air())
                            .where("C", Predicates.blocks(CASING_PTFE_INERT.get()).setMinGlobalLimited(80)
                                    .or(Predicates.autoAbilities(definition.getRecipeTypes(),false, false, true, true, true,
                                            true))
                                    .or(Predicates.autoAbilities(true, true, false))
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                            .setMaxGlobalLimited(4).setPreviewCount(4))
                            )
                            .build()
            )
            .workableCasingModel(
                    GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"),
                    CTNHCore.id("block/overlay/super_ebf"))
            .tooltips(
                    CTNHCommonTooltips.PERFECT_OVERCLOCK,
                    megaLcrTooltip0.translate(),
                    megaLcrTooltip1.translate()
            )
            .register();
    public static final MultiblockMachineDefinition IV_CHEMICAL_GENERATOR = registerChemicalGenerator(
            "iv_chemical_generator", IV,
            CASING_TUNGSTENSTEEL_TURBINE, CASING_TUNGSTENSTEEL_GEARBOX, FIREBOX_TUNGSTENSTEEL, CASING_TUNGSTENSTEEL_PIPE,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_tungstensteel"),
            CTNHCore.id("block/overlay/super_chemical"));

    public static final MultiblockMachineDefinition EV_CHEMICAL_GENERATOR = registerChemicalGenerator(
            "ev_chemical_generator", EV,
            CASING_TITANIUM_TURBINE, CASING_TITANIUM_GEARBOX, FIREBOX_TITANIUM, CASING_TITANIUM_PIPE,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"),
            CTNHCore.id("block/overlay/super_chemical"));

    //Comes from GTCA
    public static MultiblockMachineDefinition registerChemicalGenerator(String name, int tier,
                                                                        Supplier<? extends Block> casing,
                                                                        Supplier<? extends Block> gear,
                                                                        Supplier<? extends Block> firebox,
                                                                        Supplier<? extends Block> pipe,
                                                                        ResourceLocation casingTexture,
                                                                        ResourceLocation overlayModel) {
        return REGISTRATE.multiblock(name, holder -> new ChemicalGeneratorMachine(holder, tier))
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(CTNHRecipeTypes.CHEMICAL_GENERATOR)
                .generator(true)
                .recipeModifier(ChemicalGeneratorMachine::recipeModifier, true)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("III", "PPP", "III")
                        .aisle("III", "P#P", "III")
                        .aisle("III", "PPP", "III")
                        .aisle("III", "CSC", "III")
                        .aisle("III", "FGF", "III")
                        .aisle("IMI", "IDI", "III")
                        .where("M", Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where("P", Predicates.blocks(GTBlocks.CASING_PTFE_INERT.get()))
                        .where("#", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                        .where("C", Predicates.blocks(GTBlocks.COIL_CUPRONICKEL.get()))
                        .where("S", Predicates.blocks(pipe.get()))
                        .where("F", Predicates.blocks(firebox.get()))
                        .where("G", Predicates.blocks(gear.get()))
                        .where("I", Predicates.blocks(casing.get()).setMinGlobalLimited(30)
                                .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                .or(Predicates.autoAbilities(true, true, true)))
                        .where("D",
                                Predicates.ability(PartAbility.OUTPUT_ENERGY,
                                                Stream.of(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV).filter(t -> t >= tier)
                                                        .mapToInt(Integer::intValue).toArray())
                                        .addTooltips(Component.translatable("gtceu.multiblock.pattern.error.limited.1",
                                                GTValues.VN[tier])))
                        .build())
                .recoveryItems(
                        () -> new ItemLike[]{MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
                .workableCasingModel(casingTexture, overlayModel)
                .tooltips(
                        Component.translatable("gtceu.universal.tooltip.base_production_eut", V[tier]),
                        tier > EV ?
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_extreme",
                                        V[tier] * 4) :
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_regular",
                                        V[tier] * 3))
                .register();
    }

    public static final MultiblockMachineDefinition INDUSTRIAL_PRIMITIVE_BLAST_FURNACE = REGISTRATE.multiblock("industrial_primitive_blast_furnace", IndustrialPrimitiveBlastFurnaceMachine::new)
            .cnLangValue("工业土高炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.PRIMITIVE_BLAST_FURNACE_RECIPES)
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(industrialPrimitiveBlastFurnaceTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    industrialPrimitiveBlastFurnaceTooltip1.translate(),
                    industrialPrimitiveBlastFurnaceTooltip2.translate().withStyle(ChatFormatting.GREEN),
                    industrialPrimitiveBlastFurnaceTooltip3.translate().withStyle(ChatFormatting.GREEN))
            .recipeModifiers(IndustrialPrimitiveBlastFurnaceMachine::recipeModifier, BATCH_MODE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CAAAC", " CCC ", " CCC ", " CCC ", "  C  ", "  C  ", "  C  ")
                    .aisle("ABBBA", "C   C", "C   C", "C   C", " C C ", " C C ", " C C ")
                    .aisle("CCBCC", "FC CF", "FC CF", "FC CF", "  C  ", "  C  ", "  C  ")
                    .aisle("CAAAC", " AKA ", " CAC ", " CCC ", "     ", "     ", "     ")
                    .where("C", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get()))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.frames(GTMaterials.Bronze))
                    .where("A", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("B", Predicates.blocks(GTBlocks.FIREBOX_BRONZE.get()))
                    .where(" ", Predicates.any())
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/multiblock/steam_oven"))
            .register();
    public static final MultiblockMachineDefinition VOID_MINER = REGISTRATE.multiblock("void_miner", VoidMinerProcessingMachine::new)
            .cnLangValue("虚空采矿场")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.VOID_MINER)
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .tooltips(voidMinerTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    voidMinerTooltip1.translate(),
                    voidMinerTooltip2.translate(),
                    voidMinerTooltip3.translate(),
                    voidMinerTooltip4.translate(),
                    voidMinerTooltip5.translate(),
                    voidMinerTooltip6.translate().withStyle(ChatFormatting.GOLD),
                    voidMinerTooltip7.translate().withStyle(ChatFormatting.AQUA),
                    voidMinerTooltip8.translate())
            .recipeModifiers(VoidMinerProcessingMachine::recipeModifier, OC_NON_PERFECT)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCC", "XF   FX", "XF   FX", "XXXXXXX", "XF   FX", "XF   FX", "XF   FX", " F   F ", "       ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "F     F", "F     F", "X     X", "F     F", "F     F", "FX   XF", "FX   XF", " FFFFF ", "       ", "       ", "       ")
                    .aisle("CCBBBCC", "       ", "   A   ", "X  F  X", "   F   ", "   F   ", "  PGP  ", "  PGP  ", " FVFVF ", "  #F#  ", "  #F#  ", "  ###  ")
                    .aisle("CCBBBCC", "   A   ", "  AAA  ", "X FPF X", "  FPF  ", "  FPF  ", "  GGG  ", "  GGG  ", " FFXFF ", "  FXF  ", "  FXF  ", "  #F#  ")
                    .aisle("CCBBBCC", "       ", "   A   ", "X  F  X", "   F   ", "   F   ", "  PGP  ", "  PGP  ", " FVFVF ", "  #F#  ", "  #F#  ", "  ###  ")
                    .aisle("CCCCCCC", "F     F", "F     F", "X     X", "F     F", "F     F", "FX   XF", "FX   XF", " FFFFF ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "XF   FX", "XF   FX", "XXXYXXX", "XF   FX", "XF   FX", "XF   FX", " F   F ", "       ", "       ", "       ", "       ")
                    .where("C", Predicates.blocks(DARK_CONCRETE.get()))
                    .where("B", Predicates.blocks(REINFORCED_DEEPSLATE))
                    .where("A", Predicates.blocks(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING.get()))
                    .where("V", Predicates.blocks(HEAT_VENT.get()))
                    .where("F", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("P", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("G", Predicates.blocks(CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("X", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(abilities(PartAbility.PARALLEL_HATCH))
                            .or(abilities(PartAbility.INPUT_LASER))
                            .or(abilities(PartAbility.INPUT_ENERGY))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.air())
                    .where(" ", Predicates.any())
                    .where("Y", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static final MultiblockMachineDefinition SINTERING_KILN = REGISTRATE.multiblock("sintering_kiln", WorkableElectricMultiblockMachine::new)
            .cnLangValue("烧结窑")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SINTERING_KILN)
            .tooltips(sinteringKilnTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .appearanceBlock(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "#AAA#", "#AAA#", "#ADA#", "#####")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "#AAA#", "#A@A#", "#ADA#", "#####")
                    .where("A", Predicates.blocks(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS.get()).setMinGlobalLimited(85)
                            .or(abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                            .or(abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1)))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get()))
                    .where("C", Predicates.blocks(Blocks.PISTON))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", abilities(CTPPPartAbility.INPUT_KINETIC).setExactLimit(1)
                            .or(Predicates.blocks(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS.get())))
                    .build()
            )
            .workableCasingModel(CTNHCore.id("block/high_grade_coke_oven_bricks"), GTCEu.id("block/machines/alloy_smelter"))
            .register();
    public static final MultiblockMachineDefinition ULTIMATE_COMBUSTION_ENGINE = CTNHMachineUtils.registerLargeCombustionEngine(
            "ultimate_combustion_engine", ZPM,
            CASING_NAQUADAH_BLOCK, CASING_NAQUADAH_GEARBOX, CASING_ULTIMATE_ENGINE_INTAKE,
            CTNHCore.id("block/casings/nq_casing"),
            GTCEu.id("block/multiblock/generator/extreme_combustion_engine"),
            "无尽内燃引擎");

    public static final MultiblockMachineDefinition CHEMICAL_VAPOR_DEPOSITION_MACHINE = REGISTRATE.multiblock("chemical_vapor_deposition_machine", WorkableElectricMultiblockMachine::new)
            .cnLangValue("化学气相沉积器")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CHEMICAL_VAPOR_DEPOSITION)
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "AAABBBA", "AAABBBA")
                    .aisle("AAAAAAA", "ACADDDA", "AAABBBA")
                    .aisle("AAAAAAA", "A@ABBBA", "AAABBBA")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("C", Predicates.blocks(GCYMBlocks.MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                    .where("D", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition MARTIAL_MORALITY_EYE = REGISTRATE.multiblock("martial_morality_eye", MartialMoralityEyeMachine::new)
            .cnLangValue("武德之眼")
            .allowExtendedFacing(false)
            .recipeType(CTNHRecipeTypes.MARTIAL_MORALITY_EYE)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .tooltips(martialMoralityEyeTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    martialMoralityEyeTooltip1.translate(),
                    martialMoralityEyeTooltip2.translate(),
                    martialMoralityEyeTooltip3.translate(),
                    martialMoralityEyeTooltip4.translate(),
                    martialMoralityEyeTooltip5.translate().withStyle(ChatFormatting.RED),
                    martialMoralityEyeTooltip6.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "############AAAAAAAAA############", "###############A#A###############", "############AAAAAAAAA############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "##############BBBBB##############", "#############BBABABB#############", "#########AAAABAABAABAAAA#########", "#############BBBBBBB#############", "#########AAAABAABAABAAAA#########", "#############BBABABB#############", "##############BBBBB##############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#############BBBBBBB#############", "############BB#####BB############", "############B##CCC##B############", "#######AAA##B#CDDDC#B##AAA#######", "##########BBB#CDDDC#BBB##########", "#######AAA##B#CDDDC#B##AAA#######", "############B##CCC##B############", "############BB#####BB############", "#############BBBBBBB#############", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "######AA#################AA######", "########BB#############BB########", "######AA#################AA######", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "################D################", "#################################", "#################################", "#################################", "#################################", "######A###################A######", "#####AA###################AA#####", "######ABDD#############DDBA######", "#####AA###################AA#####", "######A###################A######", "#################################", "#################################", "#################################", "#################################", "################D################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#######E#################E#######", "#######C#################C#######", "####AA#C#################C#AA####", "######BD#################DB######", "####AA#C#################C#AA####", "#######C#################C#######", "#######E#################E#######", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "####A#######################A####", "###AA#######################AA###", "####ABD###################DBA####", "###AA#######################AA###", "####A#######################A####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#####C#####################C#####", "##AA#C#####################C#AA##", "####BD#####################DB####", "##AA#C#####################C#AA##", "#####C#####################C#####", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##A###########################A##", "###BD#######################DB###", "##A###########################A##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "###############A#A###############", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#AA###########################AA#", "###BD#######################DB###", "#AA###########################AA#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("###############A#A###############", "###############A#A###############", "#############BBBBBBB#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############BBBBBBB#############", "###############A#A###############", "###############A#A###############")
                    .aisle("###############A#A###############", "##############BBBBB##############", "############BB#####BB############", "#################################", "#################################", "#######E#################E#######", "#################################", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "#B#############################B#", "AB#############################BA", "#B#############################B#", "AB#############################BA", "#B#############################B#", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#################################", "#######E#################E#######", "#################################", "#################################", "############BB#####BB############", "##############BBBBB##############", "###############A#A###############")
                    .aisle("###############A#A###############", "#############BBABABB#############", "############B##CCC##B############", "#################################", "######A###################A######", "#######C#################C#######", "####A#######################A####", "#####C#####################C#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "#B#############################B#", "#B#############################B#", "AAC###########################CAA", "#BC###########################CB#", "AAC###########################CAA", "#B#############################B#", "#B#############################B#", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####C#####################C#####", "####A#######################A####", "#######C#################C#######", "######A###################A######", "#################################", "############B##CCC##B############", "#############BBABABB#############", "###############A#A###############")
                    .aisle("############AAAAAAAAA############", "#########AAAABAABAABAAAA#########", "#######AAA##B#CDDDC#B##AAA#######", "######AA#################AA######", "#####AA###################AA#####", "####AA#C#################C#AA####", "###AA#######################AA###", "##AA#C#####################C#AA##", "##A###########################A##", "#AA###########################AA#", "#A#############################A#", "#A#############################A#", "AAB###########################BAA", "AB#############################BA", "AAC###########################CAA", "AAD###########################DAA", "ABD###########################DBA", "AAD###########################DAA", "AAC###########################CAA", "AB#############################BA", "AAB###########################BAA", "#A#############################A#", "#A#############################A#", "#AA###########################AA#", "##A###########################A##", "##AA#C#####################C#AA##", "###AA#######################AA###", "####AA#C#################C#AA####", "#####AA###################AA#####", "######AA#################AA######", "#######AAA##B#CDDDC#B##AAA#######", "#########AAAABAABAABAAAA#########", "############AAAAAAAAA############")
                    .aisle("###############A#A###############", "#############BBBBBBB#############", "##########BBB#CDDDC#BBB##########", "########BB#############BB########", "######ABDD#############DDBA######", "######BD#################DB######", "####ABD###################DBA####", "####BD#####################DB####", "###BD#######################DB###", "###BD#######################DB###", "##B###########################B##", "##B###########################B##", "##B###########################B##", "#B#############################B#", "#BC###########################CB#", "ABD###########################DBA", "#BD###########################DB#", "ABD###########################DBA", "#BC###########################CB#", "#B#############################B#", "##B###########################B##", "##B###########################B##", "##B###########################B##", "###BD#######################DB###", "###BD#######################DB###", "####BD#####################DB####", "####ABD###################DBA####", "######BD#################DB######", "######ABDD#############DDBA######", "########BB#############BB########", "##########BBB#CDDDC#BBB##########", "#############BBBBBBB#############", "###############A#A###############")
                    .aisle("############AAAAAAAAA############", "#########AAAABAABAABAAAA#########", "#######AAA##B#CDDDC#B##AAA#######", "######AA#################AA######", "#####AA###################AA#####", "####AA#C#################C#AA####", "###AA#######################AA###", "##AA#C#####################C#AA##", "##A###########################A##", "#AA###########################AA#", "#A#############################A#", "#A#############################A#", "AAB###########################BAA", "AB#############################BA", "AAC###########################CAA", "AAD###########################DAA", "ABD###########################DBA", "AAD###########################DAA", "AAC###########################CAA", "AB#############################BA", "AAB###########################BAA", "#A#############################A#", "#A#############################A#", "#AA###########################AA#", "##A###########################A##", "##AA#C#####################C#AA##", "###AA#######################AA###", "####AA#C#################C#AA####", "#####AA###################AA#####", "######AA#################AA######", "#######AAA##B#CDDDC#B##AAA#######", "#########AAAABAABAABAAAA#########", "############AAAAAAAAA############")
                    .aisle("###############A#A###############", "#############BBABABB#############", "############B##CCC##B############", "#################################", "######A###################A######", "#######C#################C#######", "####A#######################A####", "#####C#####################C#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "#B#############################B#", "#B#############################B#", "AAC###########################CAA", "#BC###########################CB#", "AAC###########################CAA", "#B#############################B#", "#B#############################B#", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####C#####################C#####", "####A#######################A####", "#######C#################C#######", "######A###################A######", "#################################", "############B##CCC##B############", "#############BBABABB#############", "###############A#A###############")
                    .aisle("###############A#A###############", "##############BBBBB##############", "############BB#####BB############", "#################################", "#################################", "#######E#################E#######", "#################################", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "#B#############################B#", "AB#############################BA", "#B#############################B#", "AB#############################BA", "#B#############################B#", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#################################", "#######E#################E#######", "#################################", "#################################", "############BB#####BB############", "##############BBBBB##############", "###############A#A###############")
                    .aisle("###############A#A###############", "###############A#A###############", "#############BBBBBBB#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############BBBBBBB#############", "###############A#A###############", "###############A#A###############")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#AA###########################AA#", "###BD#######################DB###", "#AA###########################AA#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "###############A#A###############", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##A###########################A##", "###BD#######################DB###", "##A###########################A##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#####C#####################C#####", "##AA#C#####################C#AA##", "####BD#####################DB####", "##AA#C#####################C#AA##", "#####C#####################C#####", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "####A#######################A####", "###AA#######################AA###", "####ABD###################DBA####", "###AA#######################AA###", "####A#######################A####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#######E#################E#######", "#######C#################C#######", "####AA#C#################C#AA####", "######BD#################DB######", "####AA#C#################C#AA####", "#######C#################C#######", "#######E#################E#######", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "################D################", "#################################", "#################################", "#################################", "#################################", "######A###################A######", "#####AA###################AA#####", "######ABDD#############DDBA######", "#####AA###################AA#####", "######A###################A######", "#################################", "#################################", "#################################", "#################################", "################D################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "######AA#################AA######", "########BB#############BB########", "######AA#################AA######", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#############BBBBBBB#############", "############BB#####BB############", "############B##CCC##B############", "#######AAA##B#CDDDC#B##AAA#######", "##########BBB#CDDDC#BBB##########", "#######AAA##B#CDDDC#B##AAA#######", "############B##CCC##B############", "############BB#####BB############", "#############BBBBBBB#############", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "##############BBBBB##############", "#############BBABABB#############", "#########AAAABAABAABAAAA#########", "#############BBBBBBB#############", "#########AAAABAABAABAAAA#########", "#############BBABABB#############", "##############BBBBB##############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############AAAAAAA#############", "############AAFFFFFAA############", "############AFFFFFFFA############", "############AFFAAAFFA############", "############AFFA@AFFA############", "############AFFAAAFFA############", "############AFFFFFFFA############", "############AAFFFFFAA############", "#############AAAAAAA#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .where("A", Predicates.blocks(Blocks.BRICKS))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(OAK_PLANKS))
                    .where("C", Predicates.blocks(Blocks.LAPIS_BLOCK))
                    .where("D", Predicates.blocks(Blocks.BOOKSHELF))
                    .where("E", Predicates.blocks(Blocks.CHISELED_STONE_BRICKS))
                    .where("F", Predicates.blocks(CASING_BRONZE_BRICKS.get())
                            .or(abilities(PartAbility.STEAM))
                            .or(abilities(PartAbility.IMPORT_ITEMS))
                            .or(abilities(PartAbility.EXPORT_ITEMS))
                            .or(abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(abilities(PartAbility.INPUT_ENERGY))
                            .or(abilities(PartAbility.EXPORT_FLUIDS))
                            .or(abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .model(createWorkableCasingMachineModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), GTCEu.id("block/multiblock/fusion_reactor"))
                    .andThen(b -> b.addDynamicRenderer(MartialMoralityEyeRender::new)))
            //.workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public static final MultiblockMachineDefinition ADVANCED_COKE_OVEN = REGISTRATE.multiblock("advanced_coke_oven", PrimitiveWorkableMachine::new)
            .cnLangValue("高级焦炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.COKE_OVEN_RECIPES)
            .recipeModifiers((machine, group, recipe) -> {
                var failure = CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 32);
                if (failure != null) return failure;
                recipe.multiplyDuration((double) 300 / recipe.duration);
                return null;
            })
            .appearanceBlock(HIGH_GRADE_COKE_OVEN_BRICKS)
            .tooltips(advancedCokeOvenTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    advancedCokeOvenTooltip1.translate(),
                    advancedCokeOvenTooltip2.translate(),
                    advancedCokeOvenTooltip3.translate(),
                    advancedCokeOvenTooltip4.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBB", "BBB", "BBB")
                    .aisle("BBB", "B#B", "BBB")
                    .aisle("BBB", "B@B", "BBB")
                    .where("B", Predicates.blocks(HIGH_GRADE_COKE_OVEN_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.EXPORT_ITEMS))
                            .or(abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(9))
                            .or(abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(9)))
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTNHCore.id("block/high_grade_coke_oven_bricks"), GTCEu.id("block/machines/arc_furnace"))
            .register();

    public static final MultiblockMachineDefinition DIMENSIONAL_GAS_COLLECTION_CHAMBER = REGISTRATE.multiblock("dimensional_gas_collection_chamber", WorkableElectricMultiblockMachine::new)
            .cnLangValue("维度集气室")
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.DIMENSIONAL_GAS_COLLECTION)
            .recipeModifiers(OC_PERFECT_SUBTICK, BATCH_MODE)
            .appearanceBlock(PLASTCRETE)
            .tooltips(
                    largeGasCollectionChamberTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    largeGasCollectionChamberTooltip1.translate(),
                    largeGasCollectionChamberTooltip2.translate(),
                    CTNHCommonTooltips.PERFECT_OVERCLOCK
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("ABBBA", "BCCCB", "BCCCB", "BCCCB", "ABBBA")
                    .aisle("ABBBA", "BDDDB", "BDEDB", "BDDDB", "ABBBA")
                    .aisle("ABBBA", "BCCCB", "BCCCB", "BCCCB", "ABBBA")
                    .aisle("AAAAA", "ABBBA", "AB@BA", "ABBBA", "AAAAA")
                    .where("A", Predicates.blocks(PLASTCRETE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(FILTER_CASING.get()))
                    .where("C", Predicates.blocks(HERMETIC_CASING_HV.get()))
                    .where("D", Predicates.blocks(CASING_ASSEMBLY_LINE.get()))
                    .where("E", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/cleanroom/plascrete"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public static final MultiblockMachineDefinition CONDENSING_DISCRETE = REGISTRATE.multiblock("condensing_discrete", holder -> new ProcessControlledCoilMultiblockMachine(holder, ProcessControlProfile.CONDENSING_DISCRETE))
            .cnLangValue("冷凝离散塔")
            .allowExtendedFacing(false)
            .recipeType(CTNHRecipeTypes.CONDENSING_DISCRETE)
            .tooltips(condensingDiscreteTooltip1.translate(),
                    condensingDiscreteTooltip2.translate(),
                    condensingDiscreteTooltip3.translate(),
                    condensingDiscreteTooltip4.translate(),
                    condensingDiscreteTooltip5.translate())
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_ALUMINIUM_FROSTPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###########", "####BBB####", "####AAA####", "####AAA####", "####BBB####", "####AAA####", "####AAA####", "####BBB####", "####AAA####", "####AAA####", "####BBB####", "###########")
                    .aisle("###########", "###BBBBB###", "###A###A###", "###A###A###", "###B###B###", "###A###A###", "###A###A###", "###B###B###", "###A###A###", "###A###A###", "###BBBBB###", "###########")
                    .aisle("###########", "##BBBBBBB##", "##A#####A##", "##A#GGG#A##", "##B#####B##", "##A#####A##", "##A#GGG#A##", "##B#####B##", "##A#####A##", "##A#GGG#A##", "##BBBBBBB##", "####EEE####")
                    .aisle("###########", "##BBBBBBB##", "##A#####A##", "##C#GGG#A##", "##B#####B##", "##A#####A##", "##C#GGG#A##", "##B#####B##", "##A#####A##", "##C#GGG#A##", "##BBBBBBB##", "####ESE####")
                    .aisle("###########", "##BBBBBBB##", "##A#####A##", "##A#GGG#A##", "##B#####B##", "##A#####A##", "##A#GGG#A##", "##B#####B##", "##A#####A##", "##A#GGG#A##", "##BBBBBBB##", "####EEE####")
                    .aisle("###########", "###BBBBB###", "###A###A###", "###A###A###", "###B###B###", "###A###A###", "###A###A###", "###B###B###", "###A###A###", "###A###A###", "###BBBBB###", "###########")
                    .aisle("###########", "####BBB####", "####AAA####", "####ACA####", "####BBB####", "####AAA####", "####ACA####", "####BBB####", "####AAA####", "####ACA####", "####BBB####", "###########")
                    .aisle("###########", "####AAA####", "####A@A####", "####AAA####", "###########", "###########", "###########", "###########", "###########", "###########", "###########", "###########")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_ALUMINIUM_FROSTPROOF.get()).setMinGlobalLimited(72)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("C", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("E", Predicates.blocks(HEAT_VENT.get()))
                    .where("G", Predicates.blocks(FILTER_CASING.get()))
                    .where("S", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_frost_proof"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition OXIDATION_ROASTING_FURNACE = REGISTRATE.multiblock("oxidation_roasting_furnace", holder -> new ProcessControlledCoilMultiblockMachine(holder, ProcessControlProfile.OXIDATION_ROASTING))
            .cnLangValue("氧化焙烧炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.OXIDATION_ROASTING)
            .tooltips(oxidationRoastingFurnaceTooltip1.translate(),
                    oxidationRoastingFurnaceTooltip2.translate(),
                    oxidationRoastingFurnaceTooltip3.translate(),
                    oxidationRoastingFurnaceTooltip4.translate(),
                    oxidationRoastingFurnaceTooltip5.translate())
            .recipeModifiers(GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .appearanceBlock(CASING_INVAR_HEATPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#######", "##BBB##", "#BBBBB#", "BBBBBBB", "BBBBBBB", "BBBBBBB", "#BBBBD#", "##BBBD#", "#####S#")
                    .aisle("#######", "##AAA##", "#A###A#", "A#####A", "A#####A", "A#####A", "#A###A#", "##AAA##", "#######")
                    .aisle("#BBBBB#", "#BAAAB#", "#AEEEA#", "AE###EA", "AE###EA", "AE###EA", "#AEEEA#", "##AAA##", "#######")
                    .aisle("#######", "##BBB##", "#B###B#", "B#####B", "B#####B", "B#####B", "#B###B#", "##BBB##", "#######")
                    .aisle("#######", "##AAA##", "#A###A#", "A#####A", "A#####A", "A#####A", "#A###A#", "##AAA##", "#######")
                    .aisle("#######", "##AAA##", "#AEEEA#", "AE###EA", "AE###EA", "AE###EA", "#AEEEA#", "##AAA##", "#######")
                    .aisle("#######", "##AAA##", "#A###A#", "A#####A", "A#####A", "A#####A", "#A###A#", "##AAA##", "#######")
                    .aisle("#######", "##BBB##", "#B###B#", "B#####B", "B#####B", "B#####B", "#B###B#", "##BBB##", "#######")
                    .aisle("#BBBBB#", "#BAAAB#", "#AEEEA#", "AE###EA", "AE###EA", "AE###EA", "#AEEEA#", "##AAA##", "#######")
                    .aisle("#######", "##AAA##", "#A###A#", "A#####A", "A#####A", "A#####A", "#A###A#", "##AAA##", "#######")
                    .aisle("#######", "##BBB##", "#BBBBB#", "BBBBBBB", "BBBBBBB", "BBBBBBB", "#BBBBB#", "##BBB##", "#######")
                    .aisle("#######", "#######", "#BB@BB#", "#BGGGB#", "#BGGGB#", "#BGGGB#", "#BBBBB#", "#######", "#######")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_INVAR_HEATPROOF.get()).setMinGlobalLimited(80)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("D", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("E", Predicates.heatingCoils())
                    .where("G", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("S", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((machine, list) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
                    list.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(coilMachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_heatproof"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition HIGH_PRESSURE_ALKALI_DIGESTER = REGISTRATE.multiblock("high_pressure_alkali_digester", holder -> new ProcessControlledElectricMultiblockMachine(holder, ProcessControlProfile.HIGH_PRESSURE_ALKALI_DIGESTION))
            .cnLangValue("高压碱煮釜")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.HIGH_PRESSURE_ALKALI_DIGESTION)
            .tooltips(highPressureAlkaliDigesterTooltip1.translate(),
                    highPressureAlkaliDigesterTooltip2.translate(),
                    highPressureAlkaliDigesterTooltip3.translate(),
                    highPressureAlkaliDigesterTooltip4.translate(),
                    highPressureAlkaliDigesterTooltip5.translate())
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_TITANIUM_STABLE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#########", "###BBB###", "##BBBBB##", "#BBBBBBB#", "#BBBBBBB#", "#BBBBBBB#", "##BBBBB##", "###BBB###", "#########")
                    .aisle("#########", "###AAA###", "##A###A##", "#A#####A#", "#A#####A#", "#A#####A#", "##A###AD#", "###AAA#D#", "#######S#")
                    .aisle("##BBBBB##", "##BAAAB##", "##A###A##", "#A#####A#", "#A##G##A#", "#A##G##A#", "##A#G#A##", "###AGA###", "####D####")
                    .aisle("#########", "###BBB###", "##B###B##", "#B#####B#", "#B#####B#", "#B#####B#", "##B###B##", "###BBB###", "####D####")
                    .aisle("#########", "###AAA###", "##A###A##", "#A#####A#", "#A#####A#", "#A#####A#", "##A###A##", "###AAA###", "####D####")
                    .aisle("#########", "###AAA###", "##A###A##", "#A#####A#", "#A##G##A#", "#A##G##A#", "##A#G#A##", "###AGA###", "####D####")
                    .aisle("#########", "###BBB###", "##B###B##", "#B#####B#", "#B#####B#", "#B#####B#", "##B###B##", "###BBB###", "####D####")
                    .aisle("#########", "###AAA###", "##A###A##", "#A#####A#", "#A#####A#", "#A#####A#", "##A###A##", "###AAA###", "####D####")
                    .aisle("##BBBBB##", "##BAAAB##", "##A###A##", "#A#####A#", "#A##G##A#", "#A##G##A#", "##A#G#A##", "###AGA###", "####D####")
                    .aisle("#########", "###BBB###", "##B###B##", "#B#####B#", "#B#####B#", "#B#####B#", "##B###B##", "###BBB###", "#########")
                    .aisle("#########", "###BBB###", "##BBBBB##", "#BBBBBBB#", "#BBBBBBB#", "#BBBBBBB#", "##BBBBB##", "###BBB###", "#########")
                    .aisle("#########", "#########", "##BB@BB##", "##BAAAB##", "##BAAAB##", "##BAAAB##", "##BBBBB##", "#########", "#########")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_TITANIUM_STABLE.get()).setMinGlobalLimited(72)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("D", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("G", Predicates.blocks(CASING_TITANIUM_GEARBOX.get()))
                    .where("S", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_stable_titanium"), GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static final MultiblockMachineDefinition SOLVENT_EXTRACTION_TOWER = REGISTRATE.multiblock("solvent_extraction_tower", holder -> new ProcessControlledElectricMultiblockMachine(holder, ProcessControlProfile.SOLVENT_EXTRACTION))
            .cnLangValue("溶剂萃取塔")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SOLVENT_EXTRACTION)
            .tooltips(solventExtractionTowerTooltip1.translate(),
                    solventExtractionTowerTooltip2.translate(),
                    solventExtractionTowerTooltip3.translate(),
                    solventExtractionTowerTooltip4.translate(),
                    solventExtractionTowerTooltip5.translate())
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_PTFE_INERT)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###########", "##BBB######", "##AAA######", "##AAA######", "##BBB######", "##AAA######", "##AAA######", "##BBB######", "##AAA######", "##AAA######", "##BBB######", "##BBB######", "###########")
                    .aisle("###########", "#BBBBB##BBB", "#A###A##CBB", "#A###A##CBB", "#B###B##CBB", "#A###A##BBB", "#A###A#####", "#B###B#####", "#A###A#####", "#A###A#####", "#B###B#####", "#BBBBB#####", "###########")
                    .aisle("###########", "BBBBBBB#BBB", "A#####A#B#B", "A#EEE#A#B#B", "B#####B#B#B", "A#####A#BBB", "A#EEE#A####", "B#####B####", "A#####A####", "A#EEE#A####", "B#####B####", "BBBBBBB####", "###########")
                    .aisle("###########", "BBBBBBB#BBB", "A#####A#B#B", "C#EEE#DDD#B", "B#####B#B#B", "A#####A#BDB", "C#EEE#A##D#", "B#####B##D#", "A#####A##D#", "C#EEE#DDDD#", "B#####B####", "BBBDBBB####", "###S#######")
                    .aisle("###########", "BBBBBBB#BBB", "A#####A#B#B", "A#EEE#A#B#B", "B#####B#B#B", "A#####A#BBB", "A#EEE#A####", "B#####B####", "A#####A####", "A#EEE#A####", "B#####B####", "BBBBBBB####", "###########")
                    .aisle("###########", "#BBBBB##BBB", "#A###A##CBB", "#A###A##CBB", "#B###B##CBB", "#A###A##BBB", "#A###A#####", "#B###B#####", "#A###A#####", "#A###A#####", "#B###B#####", "#BBBBB#####", "###########")
                    .aisle("###########", "##BBB######", "##AAA######", "##ACA######", "##BBB######", "##AAA######", "##ACA######", "##BBB######", "##AAA######", "##ACA######", "##BBB######", "##BBB######", "###########")
                    .aisle("###########", "##AAA######", "##A@A######", "##AAA######", "###########", "###########", "###########", "###########", "###########", "###########", "###########", "###########", "###########")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_PTFE_INERT.get()).setMinGlobalLimited(64)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("C", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("D", Predicates.blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where("E", Predicates.blocks(FILTER_CASING.get()))
                    .where("S", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"), GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static final MultiblockMachineDefinition REDUCTION_PRECIPITATION_TANK = REGISTRATE.multiblock("reduction_precipitation_tank", holder -> new ProcessControlledElectricMultiblockMachine(holder, ProcessControlProfile.REDUCTION_PRECIPITATION))
            .cnLangValue("还原沉淀槽")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.REDUCTION_PRECIPITATION)
            .tooltips(reductionPrecipitationTankTooltip1.translate(),
                    reductionPrecipitationTankTooltip2.translate(),
                    reductionPrecipitationTankTooltip3.translate(),
                    reductionPrecipitationTankTooltip4.translate(),
                    reductionPrecipitationTankTooltip5.translate())
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###########", "###########", "##AAA######", "##AAA######", "##AAA######", "##AAA######", "##AAA######", "###########", "###########")
                    .aisle("###########", "##BBB######", "#A###A#####", "#A###A#####", "#A###A#####", "#A###A#####", "#A###A#####", "##BBB######", "###########")
                    .aisle("###########", "#BBBBB###B#", "A#####A##F#", "A#####A##F#", "A#####A##F#", "A#####A##B#", "A#####A####", "#B###B#####", "###########")
                    .aisle("###########", "#BBBBB##BBB", "A#####A#BFB", "C##G##DDBFB", "A##G##A#BFB", "C##G##A#BBB", "A##G##A##D#", "#B#D#B###D#", "###G#######")
                    .aisle("###########", "#BBBBB###B#", "A#####A##F#", "A#####A##F#", "A#####A##F#", "A#####A##B#", "A#####A####", "#B###B#####", "###########")
                    .aisle("###########", "##BBB######", "#A###A#####", "#A###A#####", "#A###A#####", "#A###A#####", "#A###A#####", "##BBB######", "###########")
                    .aisle("###########", "###########", "##AAA######", "##ACA######", "##AAA######", "##ACA######", "##AAA######", "###########", "###########")
                    .aisle("###########", "##AAA######", "##A@A######", "##AAA######", "###########", "###########", "###########", "###########", "###########")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(48)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("C", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("D", Predicates.blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where("F", Predicates.blocks(FILTER_CASING.get()))
                    .where("G", Predicates.blocks(CASING_STAINLESS_STEEL_GEARBOX.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static final MultiblockMachineDefinition ION_EXCHANGER = REGISTRATE.multiblock("ion_exchanger", holder -> new ProcessControlledCoilMultiblockMachine(holder, ProcessControlProfile.ION_EXCHANGE))
            .cnLangValue("离子交换机")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ION_EXCHANGER)
            .tooltips(ionExchangerTooltip1.translate(),
                    ionExchangerTooltip2.translate(),
                    ionExchangerTooltip3.translate(),
                    ionExchangerTooltip4.translate(),
                    ionExchangerTooltip5.translate())
            .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(GCYMBlocks.CASING_CORROSION_PROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###AAAAA###", "#ABCBBBCBA#", "#BBCBBBCBB#", "#ABCBBBCBA#", "###########")
                    .aisle("#AACAAACAA#", "AC#DEEED#CA", "BF#DEEED#FB", "AC#DEEED#CA", "#AACAAACAA#")
                    .aisle("#AACAAACAA#", "BF#DEEED#FB", "BF#DEEED#FB", "BF#DEEED#FB", "#AACAAACAA#")
                    .aisle("#AACAAACAA#", "AC#DEEED#CA", "BF#DEEED#FB", "AC#DEEED#CA", "#AACAAACAA#")
                    .aisle("###AA@AA###", "#ABCBBBCBA#", "#BBCBBBCBB#", "#ABCBBBCBA#", "###########")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(GCYMBlocks.CASING_CORROSION_PROOF.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("C", Predicates.blocks(GTBlocks.CASING_PTFE_INERT.get()))
                    .where("D", Predicates.frames(GTMaterials.Polytetrafluoroethylene))
                    .where("E", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GTMaterials.Epoxy).get()))
                    .where("F", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/corrosion_proof_casing"), GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static final MultiblockMachineDefinition LARGE_STEEL_FURNACE = REGISTRATE.multiblock("large_steel_furnace", WorkableElectricMultiblockMachine::new)
            .cnLangValue("大型钢制熔炉")
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.FURNACE_RECIPES)
            .recipeModifiers((machine, group, recipe) -> CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 32), GTRecipeModifiers.OC_PERFECT_SUBTICK)
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(largeSteelFurnaceTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .tooltips(CTNHCommonTooltips.commonTooltipPerfectOverclock.translate().withStyle(ChatFormatting.GREEN))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA", "BBB", "BBB", "#B#")
                    .aisle("AAA", "BCB", "BAB", "#B#")
                    .aisle("AAA", "B@B", "BBB", "#B#")
                    .where("A", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("B", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition LARGE_STEEL_ALLOY_FURNACE = REGISTRATE.multiblock("large_steel_alloy_furnace", WorkableElectricMultiblockMachine::new)
            .cnLangValue("大型钢制合金炉")
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifiers((machine, group, recipe) -> CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 32), GTRecipeModifiers.OC_PERFECT_SUBTICK)
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(largeSteelAlloyFurnaceTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .tooltips(CTNHCommonTooltips.commonTooltipPerfectOverclock.translate().withStyle(ChatFormatting.GREEN))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABA", "CCC", "CBC", "CCC")
                    .aisle("BBB", "CCC", "BDB", "CCC")
                    .aisle("ABA", "C@C", "CBC", "CCC")
                    .where("A", Predicates.frames(GTMaterials.Steel))
                    .where("B", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("C", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("D", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/machines/alloy_smelter"))
            .register();

    public static final MultiblockMachineDefinition[] LARGE_DIGITAL_MINER = CTNHMachineUtils.registerTieredMultis("large_digital_miner",
            (holder, tier) -> new LargeDigitalMinerMachine(holder, tier, 64 / tier, 2 * tier - 5, tier,
                    8 - (tier - 5)),
            (tier, builder) -> {
                var casing = switch (tier) {
                    case EV -> CASING_STEEL_SOLID;
                    case IV -> CASING_TITANIUM_STABLE;
                    case LuV -> CASING_TUNGSTENSTEEL_ROBUST;
                    case ZPM -> CASING_OSMIRIDIUM;
                    default -> throw new IllegalArgumentException("Unsupported large miner tier: " + tier);
                };
                var frame = switch (tier) {
                    case EV -> Steel;
                    case IV -> Titanium;
                    case LuV -> TungstenSteel;
                    case ZPM -> Osmiridium;
                    default -> throw new IllegalArgumentException("Unsupported large miner tier: " + tier);
                };
                var casingTexture = switch (tier) {
                    case EV -> GTCEu.id("block/casings/solid/machine_casing_solid_steel");
                    case IV -> GTCEu.id("block/casings/solid/machine_casing_stable_titanium");
                    case LuV -> GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel");
                    case ZPM -> CTNHCore.id("block/casings/osmiridium_casing");
                    default -> throw new IllegalArgumentException("Unsupported large miner tier: " + tier);
                };
                return builder
                        .cnLangValue(VNF[tier] + "大型数字采矿机")
                        .langValue(VNF[tier] + " Large Digital Miner")
                        .rotationState(RotationState.NON_Y_AXIS)
                        .appearanceBlock(casing)
                        .tooltips(Component.translatable("gtceu.machine.large_miner." + VN[tier].toLowerCase() + ".tooltip"),
                                Component.translatable("gtceu.machine.miner.multi.description"))
                        .tooltipBuilder((stack, tooltip) -> {
                            int workingAreaChunks = 2 * tier - 5;
                            tooltip.add(Component.translatable("gtceu.machine.miner.fluid_usage", 8 - (tier - 5),
                                    DrillingFluid.getLocalizedName()));
                            tooltip.add(Component.translatable("gtceu.universal.tooltip.working_area_chunks",
                                    workingAreaChunks, workingAreaChunks));
                            tooltip.add(Component.translatable("gtceu.universal.tooltip.energy_tier_range",
                                    GTValues.VNF[tier], GTValues.VNF[tier + 1]));
                        })
                        .pattern(definition -> FactoryBlockPattern.start()
                                .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                                .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                                .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                                .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                                .where("X", Predicates.blocks(casing.get())
                                        .or(abilities(PartAbility.EXPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
                                        .or(abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
                                        .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                                .setMaxGlobalLimited(2).setPreviewCount(1)))
                                .where("C", Predicates.blocks(casing.get()))
                                .where("F", Predicates.frames(frame))
                                .where("#", Predicates.any())
                                .build())
                        .workableCasingModel(casingTexture, GTCEu.id("block/multiblock/large_miner"))
                        .register();
            }, EV, IV, LuV, ZPM);

//    public static final MultiblockMachineDefinition ZPM_LARGE_MINER = REGISTRATE.multiblock("zpm_large_miner", LargeDigitalMinerMachine::new)
//            .rotationState(RotationState.NON_Y_AXIS)
//            .tooltips(
//                    Component.translatable("ctnh.multiblock.large_miner_zpm.tooltip.0"),
//                    Component.translatable("gtceu.machine.miner.multi.description"))
//            .tooltipBuilder((stack, tooltip) -> {
//                int workingAreaChunks = (2 * ZPM - 5);
//                tooltip.add(Component.translatable("gtceu.machine.miner.multi.modes"));
//                tooltip.add(Component.translatable("gtceu.machine.miner.multi.production"));
//                tooltip.add(Component.translatable("gtceu.machine.miner.fluid_usage", 8 - (ZPM - 5),
//                        DrillingFluid.getLocalizedName()));
//                tooltip.add(Component.translatable("gtceu.universal.tooltip.working_area_chunks",
//                        workingAreaChunks, workingAreaChunks));
//                tooltip.add(Component.translatable("gtceu.universal.tooltip.energy_tier_range",
//                        GTValues.VNF[ZPM], GTValues.VNF[ZPM + 1]));
//            })
//            .pattern((definition) -> FactoryBlockPattern.start()
//                    .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
//                    .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
//                    .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
//                    .where("S", Predicates.controller(Predicates.blocks(definition.get())))
//                    .where("X", Predicates.blocks(CASING_OSMIRIDIUM.get())
//                            .or(abilities(PartAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
//                            .or(abilities(PartAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
//                            .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
//                                    .setMaxGlobalLimited(2).setPreviewCount(1)))
//                    .where("C", Predicates.blocks(CASING_OSMIRIDIUM.get()))
//                    .where("F", Predicates.frames(GTMaterials.Osmiridium))
//                    .where("#", Predicates.any())
//                    .build())
//            .workableCasingModel(CTNHCore.id("block/casings/osmiridium_casing"), GTCEu.id("block/multiblock/large_miner"))
//            .register();
    public static final MultiblockMachineDefinition DECAY_POOLS = REGISTRATE.multiblock("decay_pools_machine", WorkableElectricMultiblockMachine::new)
            .cnLangValue("衰变罐")
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.DECAY_POOLS)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .tooltips(decayPoolsTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    decayPoolsTooltip1.translate(),
                    decayPoolsTooltip2.translate(),
                    decayPoolsTooltip3.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##A#A##", "##A#A##", "##AAA##", "##AAA##", "##AAA##", "###A###", "#######")
                    .aisle("#######", "##AAA##", "#ABBBA#", "#ABBBA#", "#ABBBA#", "##AAA##", "###A###")
                    .aisle("A#AAA#A", "AABBBAA", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("A#AAA#A", "AABBBAA", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("#######", "##AAA##", "#ABBBA#", "#ABBBA#", "#ABBBA#", "##AAA##", "###A###")
                    .aisle("##A#A##", "##A#A##", "##AAA##", "##A@A##", "##AAA##", "###A###", "#######")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, CTNHMaterials.Cerrobase140).get()))
                    .where("C", Predicates.any())
                    .where("D", Predicates.blocks(RADIATION_PROOF_MACHINE_CASING.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public static final MultiblockMachineDefinition FUEL_REFINING_FACTORY = REGISTRATE.multiblock("fuel_refining_factory", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("燃料精炼厂")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FUEL_REFINING)
            .recipeModifier(GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####ABBBA#####", "#####BCCCB#####", "#####BCCCB#####", "#####BCCCB#####", "#####ABBBA#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("###DDBEEEBDD###", "###DEEDFDEED###", "###DDBDGDBDD###", "###DBBDBDBBD###", "######EAE######", "######EEE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("####BBEEEBB####", "###EHHBFBHHE###", "####BBBCBBB####", "###E##BBB##E###", "######BBB######", "######EEE######", "######EEE######", "####BBBBBBB####", "####DDDDDDD####", "####EEEEEEE####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("#D#B##EEE##B#D#", "#DEHBBBFBBBHED#", "#D#B###C###B#D#", "#DED#######DED#", "###D#######D###", "###D##EEE##D###", "###D##DFD##D###", "###BEEECEEEB###", "###DHHHHHHHD###", "###EBBBBBBBE###", "######D#D######", "######D#D######", "######D#D######", "######D#D######", "######EEE######", "######DDD######", "######BBB######", "###############", "###############")
                    .aisle("#DB###EEE###BD#", "#EHB##DFD##BHE#", "#DB####C####BD#", "#B###########B#", "###############", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHBBBBBBBHD##", "##EBF#####FBE##", "####F#####F####", "####F#####F####", "####EEEEEEE####", "###############", "#####EEEEE#####", "#####DHHHD#####", "#####BACAB#####", "######ACA######", "###############")
                    .aisle("ABB###EEE###BBA", "BEHB##DFD##BHEB", "BBB####C####BBB", "BB###########BB", "A#############A", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHB#####BHD##", "##EB#######BE##", "###############", "#####BBBBB#####", "####EAAAAAE####", "###############", "####EBEEEBE####", "####DHDFDHD####", "####BB#C#BB####", "#####EEEEE#####", "######EEE######")
                    .aisle("BEEEEEEEEEEEEEB", "IDBBDDDFDDDBBDJ", "IDB###DKD###BDJ", "IDB###DKD###BDJ", "BEB###DKD###BEB", "#EEEEEDKDEEEEE#", "##EDDDDFDDDDE##", "##BE##DKD##EB##", "##DHB#DKD#BHD##", "##EB##DKD##BE##", "###D##DKD##D###", "###D#BDKDB#D###", "###DEADKDAED###", "###D##DKD##D###", "###EEEDKDEEE###", "###DHDLLLDHD###", "###BA#LLL#AB###", "####AELLLEA####", "#####EMMME#####")
                    .aisle("BEEEEEEEEEEEEEB", "IFFFFFFFFFFFFFJ", "IGCCCCKFKCCCCGJ", "IBB###KFK###BBJ", "BAB###KFK###BAB", "#EEEEEKFKEEEEE#", "##EFFFFFFFFFE##", "##BCCCKFKCCCB##", "##DHB#KFK#BHD##", "##EB##KFK##BE##", "######KFK######", "#####BKFKB#####", "####EAKFKAE####", "######KFK######", "###EEEKFKEEE###", "###DHFLLLFHD###", "###BCCLOLCCB###", "####CELLLEC####", "#####EMMME#####")
                    .aisle("BEEEEEEEEEEEEEB", "IDBBDDDFDDDBBDJ", "IDB###DKD###BDJ", "IDB###DKD###BDJ", "BEB###DKD###BEB", "#EEEEEDKDEEEEE#", "##EDDDDFDDDDE##", "##BE##DKD##EB##", "##DHB#DKD#BHD##", "##EB##DKD##BE##", "###D##DKD##D###", "###D#BDKDB#D###", "###DEADKDAED###", "###D##DKD##D###", "###EEEDKDEEE###", "###DHDLLLDHD###", "###BA#LLL#AB###", "####AELLLEA####", "#####EMMME#####")
                    .aisle("ABB###EEE###BBA", "BEHB##DFD##BHEB", "BBB####C####BBB", "BB###########BB", "A#############A", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHB#####BHD##", "##EB#######BE##", "###############", "#####BBBBB#####", "####EAAAAAE####", "###############", "####EBEEEBE####", "####DHDFDHD####", "####BB#C#BB####", "#####EEEEE#####", "######EEE######")
                    .aisle("#DB###EEE###BD#", "#EHB##DFD##BHE#", "#DB####C####BD#", "#B###########B#", "###############", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHBBBBBBBHD##", "##EBF#####FBE##", "####F#####F####", "####F#####F####", "####EEEEEEE####", "###############", "#####EEEEE#####", "#####DHHHD#####", "#####BACAB#####", "######ACA######", "###############")
                    .aisle("#D#B##EEE##B#D#", "#DEHBBBFBBBHED#", "#D#B###C###B#D#", "#DED#######DED#", "###D#######D###", "###D##EEE##D###", "###D##DFD##D###", "###BEEECEEEB###", "###DHHHHHHHD###", "###EBBBBBBBE###", "######D#D######", "######D#D######", "######D#D######", "######D#D######", "######EEE######", "######DDD######", "######BBB######", "###############", "###############")
                    .aisle("####BBEEEBB####", "###EHHBFBHHE###", "####BBBCBBB####", "###E##BBB##E###", "######BBB######", "######EEE######", "######EEE######", "####BBBBBBB####", "####DDDDDDD####", "####EEEEEEE####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("###DDBEEEBDD###", "###DEEDFDEED###", "###DDBDGDBDD###", "###DBBDBDBBD###", "######EAE######", "######EEE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("####DNNNNND####", "####ENN@NNE####", "####DNNNNND####", "####EEDEDEE####", "######EAE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("B", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("C", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GTMaterials.BlackSteel).get()))
                    .where("D", Predicates.frames(GTMaterials.BlackSteel))
                    .where("E", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("F", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("G", Predicates.blocks(HERMETIC_CASING_LV.get()))
                    .where("H", Predicates.heatingCoils())
                    .where("I", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GTMaterials.RedSteel).get()))
                    .where("J", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GTMaterials.BlueSteel).get()))
                    .where("K", Predicates.blocks(HERMETIC_CASING_HV.get()))
                    .where("L", Predicates.blocks(BLAZE_BLAST_FURNACE_CASING.get()))
                    .where("M", abilities(PartAbility.MUFFLER).setExactLimit(9))
                    .where("N", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("O", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.block, Ignitium)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();
    public static final MultiblockMachineDefinition VACUUM_SINTERING_TOWER = REGISTRATE.multiblock("vacuum_sintering_tower", holder -> new ProcessControlledCoilMultiblockMachine(holder, ProcessControlProfile.VACUUM_SINTERING))
            .cnLangValue("真空烧结厂")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.VACUUM_SINTERING)
            .tooltips(vacuumSinteringTowerTooltip1.translate(),
                    vacuumSinteringTowerTooltip2.translate(),
                    vacuumSinteringTowerTooltip3.translate(),
                    vacuumSinteringTowerTooltip4.translate(),
                    vacuumSinteringTowerTooltip5.translate())
            .recipeModifiers((machine, group, recipe) -> CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 16), GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(CASING_TITANIUM_STABLE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###########", "###BBB#####", "##BBBBB####", "#BBBBBBB###", "#BBBBBBB###", "#BBBBBBB###", "##BBBBB####", "###BBB#####", "###########")
                    .aisle("###########", "###AAA#####", "##A###A####", "#A#####A###", "#A#####A###", "#A#####A###", "##A###A####", "###AAA#####", "###########")
                    .aisle("##AAAAA##AA", "##AAAAA##GG", "##AEEEA##GG", "#AE###EA#DG", "#AE###EDDD#", "#AE###EA###", "##AEEEA####", "###AAA#####", "###########")
                    .aisle("###########", "###BBB#####", "##B###B####", "#B#####B#D#", "#B#####B###", "#B#####B###", "##B###B####", "###BBB#####", "###########")
                    .aisle("###########", "###AAA#####", "##A###A####", "#A#####A#D#", "#A#####A###", "#A#####A###", "##A###A####", "###AAA#####", "###########")
                    .aisle("#########AA", "###AAA###GG", "##AEEEA##GG", "#AE###EA#DG", "#AE###EDDD#", "#AE###EA###", "##AEEEA####", "###AAA#####", "###########")
                    .aisle("###########", "###AAA#####", "##A###A####", "#A#####A#D#", "#A#####A###", "#A#####A###", "##A###A####", "###AAA#####", "###########")
                    .aisle("###########", "###BBB#####", "##B###B####", "#B#####B#D#", "#B#####B###", "#B#####B###", "##B###B####", "###BBB#####", "###########")
                    .aisle("##AAAAA##AA", "##AAAAA##GG", "##AEEEA##GG", "#AE###EA#DG", "#AE###EDDDS", "#AE###EA###", "##AEEEA####", "###AAA#####", "###########")
                    .aisle("###########", "###AAA#####", "##A###A####", "#A#####A###", "#A#####A###", "#A#####A###", "##A###A####", "###AAA#####", "###########")
                    .aisle("###########", "###BBB#####", "##BBBBB####", "#BBBBBBB###", "#BBBBBBB###", "#BBBBBBB###", "##BBBBB####", "###BBB#####", "###########")
                    .aisle("###########", "###########", "##BB@BB####", "##BAAAB####", "##BACAB####", "##BAAAB####", "##BBBBB####", "###########", "###########")
                    .where("#", Predicates.air())
                    .where("A", Predicates.blocks(CASING_TITANIUM_STABLE.get()).setMinGlobalLimited(80)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_INVAR_HEATPROOF.get()))
                    .where("C", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("D", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("E", Predicates.heatingCoils())
                    .where("G", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("S", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((machine, list) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
                    list.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(coilMachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_stable_titanium"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public static final MultiblockMachineDefinition CRYSTALLIZER = REGISTRATE.multiblock("crystallizer", holder -> new ProcessControlledCoilMultiblockMachine(holder, ProcessControlProfile.CRYSTALLIZATION))
            .cnLangValue("结晶器")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CRYSTALLIZER)
            .tooltips(crystallizerTooltip1.translate(),
                    crystallizerTooltip2.translate(),
                    crystallizerTooltip3.translate(),
                    crystallizerTooltip4.translate(),
                    crystallizerTooltip5.translate())
            .recipeModifiers((machine, group, recipe) -> CTNHRecipeModifiers.accurateParallel(machine, group, recipe, 16), GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .appearanceBlock(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AABBBAA", "AA###AA", "AABBBAA", "AACCC##", "AA#####", "AA#####", "AA#####", "AA#####", "AA#####", "AA#####", "AA#####")
                    .aisle("ABBBBBA", "A#####A", "AB#B##A", "ACDDDC#", "A#BBB##", "A######", "A######", "A######", "A######", "A######", "AAA####")
                    .aisle("BBEBEBB", "##EBE##", "B#BBB#B", "CD###DC", "#B###B#", "##BBB##", "###F###", "###F###", "###F###", "###F###", "#AAB###")
                    .aisle("BBBBBBB", "##BBB##", "BBBBBBB", "CD#G#DC", "#B#G#B#", "##BEB##", "##HEH##", "##HEH##", "##HEH##", "##HEH##", "##BBB##")
                    .aisle("BBEBEBB", "##EBE##", "B#BBB#B", "CD###DC", "#B###B#", "##BBB##", "###F###", "###F###", "###F###", "###F###", "###B###")
                    .aisle("ABBBBBA", "A#####A", "AB#B#BA", "#CDDDC#", "##BBB##", "#######", "#######", "#######", "#######", "#######", "#######")
                    .aisle("AAB@BAA", "AA###AA", "AABBBAA", "##CCC##", "#######", "#######", "#######", "#######", "#######", "#######", "#######")
                    .where("#", Predicates.any())
                    .where("A", Predicates.frames(GTMaterials.Tungsten))
                    .where("B", Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("C", Predicates.heatingCoils())
                    .where("D", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .where("E", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("F", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("G", Predicates.blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                    .where("H", Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((machine, list) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
                    list.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(coilMachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/gcym/high_temperature_smelting_casing"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public static final MultiblockMachineDefinition SEAWATER_DESALTING_FACTORY = REGISTRATE.multiblock("seawater_desalting_factory", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("海水晒盐工厂")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.DESALTING)
            .tooltips(desaltingFactoryTooltip0.translate().withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"))
            .recipeModifiers(GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("C   C", "C   C", "CGGGC", " GGG ")
                    .aisle("     ", "     ", "GMMMG", "G###G")
                    .aisle("     ", "     ", "GMBMG", "G###G")
                    .aisle("     ", "     ", "GMMMG", "G###G")
                    .aisle("C   C", "C   C", "CGKGC", " GGG ")
                    .where("C", Predicates.frames(GTMaterials.Steel))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("B", abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("G", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("#", Predicates.blocks(Blocks.WATER))
                    .where(" ", Predicates.any())
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getTraitOrThrow(CoilMachineTrait.class).getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition BIO_REACTOR = REGISTRATE.multiblock("bio_reactor", BioMachine::new)
            .cnLangValue("生物反应器")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.BIO_REACTOR)
            .tooltips(bioReactorTooltip0.translate().withStyle(ChatFormatting.GRAY))
            .recipeModifiers(BioMachine::recipeModifier, OC_NON_PERFECT, BATCH_MODE)
            .appearanceBlock(BIO_REACTOR_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AA@AA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .where("A", Predicates.blocks(BIO_REACTOR_CASING.get()).setMinGlobalLimited(35)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CLEANROOM_GLASS.get()))
                    .where("#", Predicates.air())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(CTNHCore.id("block/casings/bio_reactor_casing"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();


    public static final MultiblockMachineDefinition SUPER_CENTRIFUGE = REGISTRATE.multiblock("super_centrifuge", WorkableElectricMultiblockMachine::new)
            .cnLangValue("超速离心机")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CENTRIFUGE_RECIPES, CTNHRecipeTypes.DIFFERENTIAL_CENTRIFUGE_RECIPES)
            .appearanceBlock(CASING_TITANIUM_STABLE)
            .recipeModifiers((metaMachine, group, gtRecipe) -> {
                if (gtRecipe.getType().equals(GTRecipeTypes.CENTRIFUGE_RECIPES)) {
                    return CTNHRecipeModifiers.accurateParallel(metaMachine, group, gtRecipe, 8);
                }
                return null;
            }, GTRecipeModifiers.OC_NON_PERFECT)
            .tooltips(superCentrifuge.translate().withStyle(ChatFormatting.GRAY),
                    superCentrifugeParallel.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                .aisle("#BBB#", "BBBBB", "#BBB#", "BBBBB", "#BBB#")
                .aisle("BBBBB", "B#C#B", "B#C#B", "B#C#B", "BBBBB")
                .aisle("BBBBB", "BC#CB", "BC#CB", "BC#CB", "BBBBB")
                .aisle("BBBBB", "B#C#B", "B#C#B", "B#C#B", "BBBBB")
                .aisle("#BBB#", "BBBBB", "#B@B#", "BBBBB", "#BBB#")
                .where("B", Predicates.blocks(CASING_TITANIUM_STABLE.get()).setMinGlobalLimited(40)
                        .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                .where("#", Predicates.any())
                .where("C", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_stable_titanium"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
    public static final MultiblockMachineDefinition ULTRASONIC_APPARATUS = REGISTRATE.multiblock("ultrasonic_apparatus", CoilWorkableElectricMultiblockMachine::new)
            .cnLangValue("超声破碎仪")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ULTRASONICATION_RECIPES)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .tooltips(ultrasonicApparatus.translate().withStyle(ChatFormatting.GRAY))
            .pattern(definition -> FactoryBlockPattern.start()
                .aisle("##BCFCB##", "#BBCFCBB#", "##BCFCB##")
                .aisle("#BBCFCBB#", "BDEEEEEDB", "#BBCFCBB#")
                .aisle("##BCFCB##", "#BBC@CBB#", "##BCFCB##")
                .where("#", Predicates.any())
                .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                .where("C", Predicates.heatingCoils())
                .where("D", Predicates.blocks(CASING_STAINLESS_STEEL_GEARBOX.get()))
                .where("E", Predicates.blocks(CASING_STEEL_PIPE.get()))
                .where("F",Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();
}
// spotless:on
