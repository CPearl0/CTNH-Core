package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.Pattern.CTNHPredicates;
import io.github.cpearl0.ctnhcore.client.renderer.ArcBlockRender;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Generator;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Reactor;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.NanoscaleTriboelectricGenerator;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotoVoltaicDroneStation;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.machines.CTNHMachines;
import io.github.cpearl0.ctnhcore.utils.CTNHCommonTooltips;
import io.github.cpearl0.ctnhcore.utils.CTNHMachineUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.AssemblyLineMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.generator.LargeTurbineMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.api.pattern.util.RelativeDirection.*;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterialBlocks.MATERIAL_BLOCKS;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.BATCH_MODE;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.OC_NON_PERFECT;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.DUMMY_RECIPES;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static committee.nova.mods.avaritia.init.registry.ModBlocks.neutron;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static net.minecraft.world.level.block.Blocks.*;

// spotless:off
public class MultiblocksB {

    @Key("ctnh.dwof.tooltip")
    @CN("运行时为模型积累数据，至多将模型提高至[进阶]等级")
    @EN("Accumulates data for the model during operation, raising it up to [Advanced] at most")
    public static Lang dwofTooltip;


    @Key("ctnh.eternal_engine.1")
    @CN("当前发电量:%d EU /tick")
    @EN("Current power output: %d EU/tick")
    public static Lang eternalEngine1;


    @Key("ctnh.eternal_engine.2")
    @CN("累计的工作时间:%.2f s/36000 s")
    @EN("Total operating time: %.2f s/36000 s")
    public static Lang eternalEngine2;


    @Key("ctnh.magic.generator")
    @CN("精炼天地之魔精")
    @EN("Refined Essence of Heaven and Earth")
    public static Lang magicGenerator;


    @Key("ctnh.magic.generator.1")
    @CN("具有8并行，每秒基础消耗12mB液态魔力，电压每超过§7LV§r一级，消耗量变为原来的两倍")
    @EN("Has 8 parallel operations and consumes 12 mB of Liquid Mana per second at base. For each voltage tier above §7LV§r, consumption doubles")
    public static Lang magicGenerator1;


    @CN("物质撕裂器")
    @EN("Matter Ripper")
    public static Lang arcgeneratorTooltip1;


    @CN("该机器必须配合电弧生成器使用，要求电弧生成器必须在该机器主方块上方5格，当完成链接时，电弧生成器会显示已完成连接")
    @EN("This machine must be used with an Arc Generator. The Arc Generator must be five blocks above this machine's controller. When linked, the Arc Generator shows that the connection is complete")
    public static Lang arcgeneratorTooltip2;


    @CN("当电弧强度小于配方最小电弧强度时，配方将无法运行")
    @EN("If arc intensity is below the recipe's minimum, the recipe cannot run")
    public static Lang arcgeneratorTooltip3;


    @CN("当电弧强度大于配方最大电弧强度时，配方将以(机器电弧强度-配方需求电弧强度)/(满功率需求电弧强度-需求电弧强度)的效率运行。效率允许超过100%，但不能超过机器最大发电效率")
    @EN("When arc intensity exceeds the recipe maximum, the recipe runs at (machine arc intensity - recipe required arc intensity) / (full-power required arc intensity - required arc intensity) efficiency. Efficiency may exceed 100%, but cannot exceed the machine maximum power generation efficiency")
    public static Lang arcgeneratorTooltip4;


    @CN("当效率未达100%时，因为湮灭的不完全，将产生少量额外产出")
    @EN("When efficiency is below 100%, incomplete annihilation produces a small amount of extra output")
    public static Lang arcgeneratorTooltip5;


    @CN("§b最大支持电弧强度:1000")
    @EN("§bMaximum Supported Arc Intensity: 1000")
    public static Lang arcgeneratorTooltipArcT11;


    @CN("§c最大发电效率:75%")
    @EN("§cMaximum Power Generation Efficiency: 75%")
    public static Lang arcgeneratorTooltipArcT12;


    @CN("§b最大支持电弧强度:10000")
    @EN("§bMaximum Supported Arc Intensity: 10000")
    public static Lang arcgeneratorTooltipArcT21;


    @CN("§c最大发电效率:125%")
    @EN("§cMaximum Power Generation Efficiency: 125%")
    public static Lang arcgeneratorTooltipArcT22;


    @CN("§b最大支持电弧强度:50000")
    @EN("§bMaximum Supported Arc Intensity: 50000")
    public static Lang arcgeneratorTooltipArcT31;


    @CN("§c最大发电效率:225%")
    @EN("§cMaximum Power Generation Efficiency: 225%")
    public static Lang arcgeneratorTooltipArcT32;


    @CN("分子撕裂器")
    @EN("Molecular Ripper")
    public static Lang arcgeneratorTooltipT21;


    @CN("原子撕裂器")
    @EN("Atomic Ripper")
    public static Lang arcgeneratorTooltipT31;


    @CN("该机器必须配合电弧撕裂者使用，要求电弧生成器必须在电弧撕裂者主方块上方5格，当完成链接时，电弧生成器会显示桥接已启用")
    @EN("This machine must be used with an Arc Ripper. The Arc Generator must be five blocks above the Arc Ripper's controller; when linked, the Arc Generator shows that the bridge is enabled")
    public static Lang arcreactorTooltip1;


    @CN("机器基础每次运行配方产生10电弧强度，无法超频，高等级机器具有更高并行数")
    @EN("The machine produces 10 arc intensity each time it runs a recipe, cannot be overclocked, and higher-tier machines have more parallelism")
    public static Lang arcreactorTooltip2;


    @CN("该机器并行数:1")
    @EN("Machine parallelism: 1")
    public static Lang arcreactorTooltipT1;


    @CN("§b泪水如凛冰般落下")
    @EN("§bTears fall like Cryotheum")
    public static Lang cryotheumFreezerTip0;


    @CN("每次运行配方消耗5*并行mb极寒之凛冰，电压每高于§9IV§r一级，这个消耗就翻4倍")
    @EN("Each recipe operation consumes 5 × parallel mB of Cryotheum; for each voltage tier above §9IV§r, consumption quadruples")
    public static Lang cryotheumFreezerTip1;


    @CN("初始具有4并行和3泪之晶点数，可以在升级界面加点。每消耗10000mb凛冰，就获得一点点数，随后将目标翻四倍")
    @EN("Starts with 4 parallel operations and 3 Tear Crystal points; add points in the upgrade screen. Every 10000 mB of Cryotheum consumed grants one point, then the target amount quadruples")
    public static Lang cryotheumFreezerTip2;


    @CN("菌群孕育，菌种滋长")
    @EN("Microbial incubation, fungal proliferation")
    public static Lang cultivationRoomTooltip1;


    @CN("运用好这台机器来繁殖那些难以获取的真菌和细菌")
    @EN("Utilize this machine to cultivate hard-to-obtain fungi and bacteria")
    public static Lang cultivationRoomTooltip2;


    @CN("§6永§b不§d损§a耗，你在担心什么？")
    @EN("§6E§bver§dlast§aing—what are you worried about?")
    public static Lang fluidDrillingRigDepletionInf;


    @CN("§6钻取来自无尽之中的流体之海")
    @EN("§6Drilling a sea of fluids from the Infinite")
    public static Lang fluidDrillingRigDescriptionInf;


    @CN("手植千木，绿荫千秋")
    @EN("Plant trees by hand, create shade for millennia")
    public static Lang forestSeaTooltip1;


    @CN("林海树场是一个只消耗水来产出大量木材的大机器")
    @EN("The Forest Sea is a massive machine that consumes only water to produce large quantities of lumber")
    public static Lang forestSeaTooltip2;


    @CN("每5s进行一次水储量的判定")
    @EN("Performs water storage check every 5 seconds")
    public static Lang forestSeaTooltip3;


    @CN("水充足时，增加1%的湿度值")
    @EN("When water is sufficient, increases humidity by 1%")
    public static Lang forestSeaTooltip4;


    @CN("水不足时，减少10%的湿度值")
    @EN("When water is insufficient, decreases humidity by 10%")
    public static Lang forestSeaTooltip5;


    @CN("配方运行时间不变，但并行值会随湿度值与电压等级上升")
    @EN("Recipe processing time remains constant, but parallel value increases with humidity and voltage tier")
    public static Lang forestSeaTooltip6;


    @CN("比温室好！")
    @EN("Better than greenhouses!")
    public static Lang forestSeaTooltip7;


    @CN("持续调整激光频率")
    @EN("Continuously adjusts the laser frequency")
    public static Lang lasersorterTooltip0;


    @CN("配方类型：激光分配/激光蚀刻")
    @EN("Recipe types: Laser Distribution / Laser Etching")
    public static Lang lasersorterTooltip1;


    @CN("输入的算力每比基础算力多一倍，将一次超频转化为无损超频（即运行速度*2），该效果转化的次数不超过你能超频的等级（即上限为将你所有的有损超频转化为无损）")
    @EN("Each time input computation doubles the base requirement, one lossy overclock is converted into a perfect overclock (processing speed ×2). Conversions cannot exceed the machine's overclock tier, up to converting all lossy overclocks into perfect overclocks")
    public static Lang lasersorterTooltip10;


    @CN("§c如果输入的算力不为整数倍，则以上所有的增益全部无效且最终所需时间*4")
    @EN("§cIf input computation is not an integer multiple, all bonuses above are disabled and final time ×4")
    public static Lang lasersorterTooltip11;


    @CN("本机器需要消耗算力才能运行")
    @EN("This machine consumes computation to operate")
    public static Lang lasersorterTooltip2;


    @CN("————————激光蚀刻模式————————")
    @EN("————————Laser Etching Mode————————")
    public static Lang lasersorterTooltip3;


    @CN("LuV及以下的电压固定基础请求8算力，电压每高于LuV一级，请求的基础算力翻倍")
    @EN("At LuV and below, the base computation requirement is fixed at 8 CWU; for each voltage tier above LuV, the base requirement doubles")
    public static Lang lasersorterTooltip4;


    @CN("输入的算力如果为基础请求算力的整数倍，则最终输出*1.25，并行等同于⌊(输入的算力/基础请求算力)⌋的三次方")
    @EN("If input computation is an integer multiple of the base requirement, final output ×1.25 and parallelism = floor(input computation / base requirement)^3")
    public static Lang lasersorterTooltip5;


    @CN("输入的算力每比基础算力多一倍，将一次超频转化为无损超频（即运行速度*2），该效果转化的次数不超过你能超频的等级（即上限为将你所有的有损超频转化为无损）")
    @EN("Each time input computation doubles the base requirement, one lossy overclock is converted into a perfect overclock (processing speed ×2). Conversions cannot exceed the machine's overclock tier, up to converting all lossy overclocks into perfect overclocks")
    public static Lang lasersorterTooltip6;


    @CN("————————激光分配模式————————")
    @EN("————————Laser Distribution Mode————————")
    public static Lang lasersorterTooltip7;


    @CN("配方给出请求算力，如果配方没有给出则按照激光蚀刻模式的公式计算")
    @EN("The recipe specifies the required computation; if it does not, calculate it using the Laser Etching formula")
    public static Lang lasersorterTooltip8;


    @CN("输入的算力如果为基础请求算力的整数倍，则并行等同于⌊(输入的算力/基础请求算力)⌋的三次方")
    @EN("If input computation is an integer multiple of the base requirement, parallelism = floor(input computation / base requirement)^3")
    public static Lang lasersorterTooltip9;


    @CN("利用摩擦热的力量")
    @EN("Utilize the power of friction heat")
    public static Lang nanoGeneratorTooltip0;


    @CN("最大并行数:2048")
    @EN("Maximum parallel count: 2048")
    public static Lang nanoGeneratorTooltip1;


    @CN("每有1并行数，总体发电量提升2%\n实际运行时间为配方时间*sqrt(并行数)")
    @EN("For each parallel process, total power generation increases by 4%\nActual operation time is recipe time * sqrt(parallel count)")
    public static Lang nanoGeneratorTooltip2;


    @CN("在控制器内放入特定材料可提升倍率，但也有概率消耗\n无材料：0.8倍率\n橡胶片：1.0倍率,并行数/512几率消耗\n聚乙烯片：1.6倍率，并行数/1024几率消耗\n硅橡胶片：2.4倍率，并行数/4096几率消耗\n聚四氟乙烯片：3.2倍率，并行数/65535几率消耗\n丁苯橡胶片：4.6倍率，并行数/131070几率消耗\n聚苯并咪唑片：5倍率，并行数/1048576几率消耗")
    @EN("Inserting specific materials into the controller increases the multiplier, but each material may also be consumed\nNo material: 0.8x multiplier\nRubber Sheet: 1.0x multiplier, consumption chance = parallel count / 512\nPolyethylene Sheet: 1.6x multiplier, consumption chance = parallel count / 1024\nSilicone Rubber Sheet: 2.4x multiplier, consumption chance = parallel count / 4096\nPTFE Sheet: 3.2x multiplier, consumption chance = parallel count / 65535\nStyrene-Butadiene Rubber Sheet: 4.6x multiplier, consumption chance = parallel count / 131070\nPolybenzimidazole Sheet: 5x multiplier, consumption chance = parallel count / 1048576")
    public static Lang nanoGeneratorTooltip3;


    @CN("将生物的进化之道完全放任于碳基生物的自然演变是一种低效且缓慢的做法，现在我们将亲自编码每一个基因序列，将我们的至臻完美编译在神经元的逻辑之中")
    @EN("Leaving the evolution of living beings entirely to the natural evolution of carbon-based life is inefficient and slow. We will now encode every genetic sequence ourselves, compiling our ultimate perfection into neuronal logic")
    public static Lang neuroMatrixCompilerTip0;


    @CN("神经矩阵编码器（CMP）是一台编译神经序列的机器，其不同于其他机器，不执行正常的输入逻辑，无法超频")
    @EN("The Neural Matrix Compiler (CMP) compiles neural sequences. Unlike other machines, it does not use normal input logic and cannot be overclocked")
    public static Lang neuroMatrixCompilerTip01;


    @CN("该机器的输入由6个神经矩阵研究舱室组成，每个舱室在结构完成时将被编码，所有研究舱室必须为同一等级，编码完成后，每个研究舱室将会显示他们所属的片区（并未实现）现在片区分配固定为：机器主方块左前方为1，右前方为2，左后方为3，右后方为4")
    @EN("The machine input consists of 6 Neural Matrix Research Chambers. Each chamber is encoded when the structure is completed, and all research chambers must be the same tier. After encoding, each chamber displays its assigned region (not implemented). Regions are currently fixed as follows: the front-left of the controller is 1, front-right is 2, back-left is 3, and back-right is 4")
    public static Lang neuroMatrixCompilerTip1;


    @CN("如果运算失败，则定义噪声结果函数f(x1,x2,x3)=ax1+bx2+cx3+d+ϵ,根据噪声决定片区，噪声波动最多翻倍5倍，片区误差项为0.9-1.1间，则噪声变为0.5倍率")
    @EN("If computation fails, define the noise result function f(x1,x2,x3)=ax1+bx2+cx3+d+ϵ. The region is determined by noise; noise fluctuation can be multiplied by up to 5, and if the region error term is between 0.9 and 1.1, noise becomes a 0.5 multiplier")
    public static Lang neuroMatrixCompilerTip10;


    @CN("最终编译数据集将获得三个信息，信息1代表本次的方程，信息2代表噪声结果函数获得的结果值对于y的倍率，信息3代表误差项的比率")
    @EN("The final compiled dataset contains three pieces of information: information 1 is the equation used, information 2 is the result value from the noise function as a multiplier of y, and information 3 is the error-term ratio")
    public static Lang neuroMatrixCompilerTip11;


    @CN("在执行相同配方时方程不会重置，在执行配方或者结构重新成型时，重置y和方程")
    @EN("The equation is not reset when executing the same recipe. It resets y and the equation when a recipe is executed or the structure is re-formed")
    public static Lang neuroMatrixCompilerTip12;


    @CN("该机器的输入§c必须严格按照EMI的物品顺序§9从左到右§r从第一行到第二行放置在§91-5片区§r，同时在第六片区放置§9研究数据集§r§r,任何错误的放置或者外部舱室的放置都会导致机器故障并在对应舱室显示故障")
    @EN("Machine inputs §cmust strictly follow the EMI item order§9, placed §rfrom left to right§r and from the first row to the second row in §9Regions 1-5§r. Place the §9Research Dataset§r in Region 6. Any incorrect placement or external chamber placement will cause a machine fault, shown in the corresponding chamber")
    public static Lang neuroMatrixCompilerTip2;


    @CN("必须保证所有神经矩阵研究舱室的等级不低于配方等级，否则配方不会运行")
    @EN("All Neural Matrix Research Chambers must be at least the recipe tier; otherwise the recipe will not run")
    public static Lang neuroMatrixCompilerTip3;


    @CN("————————机器总体机制————————")
    @EN("————————Overall Machine Mechanics————————")
    public static Lang neuroMatrixCompilerTip4;


    @CN("每次检测到新的配方时，机器将§6完美诉诸于随机§r，生成函数F(x1,x2,x3)=§6y=ax1+bx2+cx3+d§r，其中，x1,x2,x3为期望的片区所消耗的物品数量，同时在给定范围内随机x1,x2,x3,获取答案y")
    @EN("Whenever a new recipe is detected, the machine §6entrusts itself to randomness§r and generates F(x1,x2,x3)=§6y=ax1+bx2+cx3+d§r, where x1, x2, and x3 are the quantities of items consumed by the target regions. It then randomly chooses x1, x2, and x3 within the given ranges to obtain y")
    public static Lang neuroMatrixCompilerTip5;


    @CN("当配方执行时，在开始逻辑运算，1-5片区将各自运行5s,运行完毕时将消耗舱室内所有物品来取得函数")
    @EN("When a recipe runs, logical computation begins. Regions 1-5 each run for 5 s; when finished, they consume all items in their chambers to obtain the function")
    public static Lang neuroMatrixCompilerTip6;


    @CN("在片区1-5执行完毕后，进行持续5s的总计算流程，在此过程中给出x1,x2,x3，计算得到计算值y，与真实比较，进行最终编译运算")
    @EN("After Regions 1-5 finish, a total computation process runs for 5 s. During this process, x1, x2, and x3 are supplied, the calculated y is compared with the true value, and the final compilation is performed")
    public static Lang neuroMatrixCompilerTip7;


    @CN("————————最终编译运算————————")
    @EN("————————Final Compilation————————")
    public static Lang neuroMatrixCompilerTip8;


    @CN("最终编译运算将比较真实y与结果y，如果结果y的值在真实值y的0.9-1.1倍内，则运算成功，将编译数据集变为配方输出")
    @EN("Final compilation compares true y with result y. If result y is within 0.9 to 1.1 times true y, the computation succeeds and the compiled dataset becomes the recipe output")
    public static Lang neuroMatrixCompilerTip9;


    @CN("片区1-3：用于提供函数F(x)的真实x1,x2,x3")
    @EN("Regions 1-3: provide the actual x1, x2, and x3 for function F(x)")
    public static Lang neuroMatrixCompilerTipPart1;


    @CN("片区4：代表函数F(x)的常数量d，同时决定噪声ϵ")
    @EN("Region 4: represents the constant d in function F(x) and determines noise ϵ")
    public static Lang neuroMatrixCompilerTipPart2;


    @CN("片区5：此片区用为神经编译提供电路板支持，决定噪声ϵ波动，如果提供电路板大于配方给定值则不造成噪声影响")
    @EN("Region 5: provides circuit-board support for neural compilation and determines noise ϵ fluctuation. If the provided circuit board exceeds the recipe requirement, it causes no noise impact")
    public static Lang neuroMatrixCompilerTipPart3;


    @CN("片区6：收集最终编译结果的片区，在完成一次逻辑运算流程后，将根据结果对神经数据集进行修改")
    @EN("Region 6: collects the final compilation result and modifies the neural dataset based on the result after a logical computation cycle")
    public static Lang neuroMatrixCompilerTipPart4;


    @CN("§4转底炉的复仇")
    @EN("§4The Revenge of the Rotary Kiln")
    public static Lang plasmaAlloyTooltip1;


    @CN("§c速度增幅超过5000%时，最终产物量将会在0%-50%中浮动！")
    @EN("§cWhen the speed bonus exceeds 5000%, final output fluctuates between 0% and 50%!")
    public static Lang plasmaAlloyTooltip10;


    @CN("允许使用§b激光仓§r，使用激光仓时最终速度将除以4，速度低于原速度时拒绝运行")
    @EN("Allows §blaser hatches§r; when using a laser hatch, final speed is divided by 4, and operation is rejected if it falls below the original speed")
    public static Lang plasmaAlloyTooltip11;


    @CN("线圈温度每有1800K，获得4点并行，线圈温度超过10000K时，获得(线圈温度-10000)/10000的额外加速")
    @EN("Every 1800 K of coil temperature grants 4 parallel operations; above 10000 K, it grants an additional speed multiplier of (coil temperature - 10000) / 10000")
    public static Lang plasmaAlloyTooltip2;


    @CN("运行前消耗(并行数*对应等离子体消耗)的等离子体，获得额外加速")
    @EN("Consumes (parallel count × corresponding plasma consumption) of plasma before operation to gain an additional speed boost")
    public static Lang plasmaAlloyTooltip3;


    @CN("氦等离子体：消耗500*并行的等离子体，速度+100%")
    @EN("Helium plasma: consumes 500 × parallel plasma, speed +100%")
    public static Lang plasmaAlloyTooltip4;


    @CN("氧，氮等离子体：消耗300*并行的等离子体，速度+200%")
    @EN("Oxygen or nitrogen plasma: consumes 300 × parallel plasma, speed +200%")
    public static Lang plasmaAlloyTooltip5;


    @CN("镍，铁等离子体：消耗200*并行的等离子体，速度+300%")
    @EN("Nickel or iron plasma: consumes 200 × parallel plasma, speed +300%")
    public static Lang plasmaAlloyTooltip6;


    @CN("消耗特殊的冶炼等离子体可以获得额外的速度加成，§c但是同样会将增加你冶炼的风险")
    @EN("Special smelting plasmas grant additional speed bonuses, §cbut also increase the risk of your smelting process")
    public static Lang plasmaAlloyTooltip7;


    @CN("压缩精金等离子：消耗固定100等离子体，使速度*5,使消耗电压翻倍（§c这可能导致配方不运行，请使用多安能源仓）")
    @EN("Compressed Enriched Naquadah Plasma: consumes a fixed 100 plasma, speed ×5, and doubles voltage consumption (§cwhich may prevent recipes from running; use multi-amp energy hatches)")
    public static Lang plasmaAlloyTooltip8;


    @CN("精炼超能以太等离子体：消耗50*并行等离子体，使速度*10,§c使最终产物在80%-100%中浮动")
    @EN("Refined Super Energetic Aether Plasma: consumes 50 × parallel plasma, speed ×10, §cand makes final output fluctuate between 80% and 100%")
    public static Lang plasmaAlloyTooltip9;


    @CN("戴森云计划")
    @EN("Dyson Swarm Project")
    public static Lang pvdroneTooltip0;


    @CN("允许使用并行控制仓，并行数为运行时间倍率")
    @EN("Allows parallel control hatches; parallelism equals the processing-time multiplier")
    public static Lang pvdroneTooltip1;


    @CN("为光伏基站提供电力增幅，使用光伏绑定终端来为这两个结构绑定")
    @EN("Provides a power boost to the photovoltaic station; use the Photovoltaic Binding Terminal to bind these two structures")
    public static Lang pvdroneTooltip2;


    @CN("将无人机放入无人机支架以开始发送无人机，每5秒和运行结束时，每个无人机都有一定概率消耗，无人机发电同样受维度和空间站增幅")
    @EN("Place drones in the drone holder to begin transmitting them. Each drone has a chance to be consumed every 5 seconds and when operation ends; drone generation is also affected by dimension and station bonuses")
    public static Lang pvdroneTooltip3;


    @CN("无人机的消耗概率公式为1.0 / (1.0 + Math.exp(-0.25* (x - 9)))")
    @EN("Drone consumption chance = 1.0 / (1.0 + Math.exp(-0.25 * (x - 9)))")
    public static Lang pvdroneTooltip4;


    @CN("使用无人机收集陨石时，产出的倍率公式为0.1*Math.sqrt(无人机电压之和)，倍率小于1时无产出，无法超频")
    @EN("When drones collect meteors, output multiplier = 0.1 × sqrt(sum of drone voltages). No output below multiplier 1; cannot be overclocked")
    public static Lang pvdroneTooltip5;


    @CN("来自§b某个神秘东方大国§r的工业力量")
    @EN("From §bA certain mysterious eastern country§r's industrial power.")
    public static Lang sinopeChemicalTooltip0;


    @CN("格雷员工不骗格雷员工，并行是真实的")
    @EN("Gray employees don’t deceive gray employees, parallel is real.")
    public static Lang sinopeChemicalTooltip1;


    @CN("没有外壳等级要求，配方不需要催化剂")
    @EN("No shell level requirements, recipes don't need catalysts.")
    public static Lang sinopeChemicalTooltip2;


    @CN("并行数与中心的方块有关")
    @EN("Parallel count is related to the central block.")
    public static Lang sinopeChemicalTooltip3;


    @CN("硅岩块:8并行")
    @EN("Silicon rock block: 8 parallel")
    public static Lang sinopeChemicalTooltip4;


    @CN("富集硅岩块:32并行")
    @EN("Enriched silicon rock block: 32 parallel")
    public static Lang sinopeChemicalTooltip5;


    @CN("超能硅岩块:128并行")
    @EN("Super silicon rock block: 128 parallel")
    public static Lang sinopeChemicalTooltip6;


    @CN("每一点实际的并行数减少0.5%的能耗和运行时间，至多减少25%(独立乘区)")
    @EN("Each point of actual parallel reduces energy consumption and operation time by 0.5%, up to a maximum reduction of 25% (independently multiplied)")
    public static Lang sinopeChemicalTooltip7;


    @CN("线圈每提供1800K，运行速度+100%")
    @EN("Each coil providing 1800K increases the operation speed by +100%")
    public static Lang sinopeChemicalTooltip8;


    @CN("§c任何虚假的并行都将绳之以法!§r")
    @EN("§cAny false parallel will be punished!§r")
    public static Lang sinopeChemicalTooltip9;


    @CN("§6光辉灿烂的太空之路")
    @EN("§6A Glorious Path Through Space")
    public static Lang spacephotovoltaicbasestationTooltip0;


    @CN("在太空发电模式下，星球类型和光伏方块的等级都会提升发电量，在空间站被视为无重力环境，且发电量*4,消耗特定材料以进一步提升发电量")
    @EN("In Space Power Generation mode, planet type and photovoltaic block tier increase power output. The station is treated as a zero-gravity environment, multiplying output by 4; consuming specific materials increases output further")
    public static Lang spacephotovoltaicbasestationTooltip2;


    @CN("在太空光伏组装模式下，不消耗EUt，发电量将锁定为1，根据配方的模拟F功率来计算速度和并行量")
    @EN("In Space Photovoltaic Assembly mode, no EUt is consumed and power output is locked at 1. Speed and parallelism are calculated from the recipe's simulated F power")
    public static Lang spacephotovoltaicbasestationTooltip3;


    @CN("光伏等级，光照强度共同决定了是否可以执行太空组装配方，光伏方块耐热性和耐热结构方块决定了可以获得的光照最大倍率，太空结构方块决定了可以使用的光伏方块等级和是否可以使用附属结构")
    @EN("Photovoltaic tier and light intensity determine whether space assembly recipes can run. Photovoltaic block heat resistance and heat-resistant structural blocks determine the maximum light multiplier. Space structural blocks determine the usable photovoltaic tier and whether auxiliary structures are allowed")
    public static Lang spacephotovoltaicbasestationTooltip4;


    @CN("在太空光伏组装模式下，最终并行为(太空发电模式下发电量/模拟功率),最终时间倍率为(模拟功率/太空发电模式下发电量)，当太空发电模式下发电量小于模拟功率时，最终时间倍率将变为平方")
    @EN("In Space Photovoltaic Assembly mode, final parallelism = (Space Power Generation output / simulated power), and final time multiplier = (simulated power / Space Power Generation output). If Space Power Generation output is lower than simulated power, the final time multiplier is squared")
    public static Lang spacephotovoltaicbasestationTooltip5;


    @CN("§6该结构将持续拓展，这还不是它的完全体状态！")
    @EN("§6This structure will continue to expand; it is not yet complete!")
    public static Lang spacephotovoltaicbasestationTooltipEx;


    @CN("粒子加速集成者")
    @EN("Particle Accelerator Integrator")
    public static Lang wideAcceleratorTooltip0;


    @CN("允许§9使用激光仓§r和§a变电仓§r")
    @EN("Allows the use of §9laser hatches§r and §avoltage converter hatches§r")
    public static Lang wideAcceleratorTooltip1;


    @CN("注意:本机器用电量极高，且暂时无法做到只能计算正确并行，使用低电压可能导致§c配方无法运行§r或者§c跳电§r,建议搭配§9激光仓§r使用，如遇配方不工作，请报告给作者")
    @EN("WARNING: Extreme power consumption. Improper voltage may cause §crecipe failure§r or §ccircuit tripping§r. Recommended with §9laser hatches§r. Reduce parallelism if malfunction occurs")
    public static Lang wideAcceleratorTooltip10;


    @CN("可以与约束器链接传递部分粒子。§c警告：如果没有链接约束器，不要随意尝试某些危险的配方§r (目前还是饼)")
    @EN("Particle transfer available via containment links. §cCAUTION: Hazardous recipes require pre-installed containment systems§r (Currently conceptual)")
    public static Lang wideAcceleratorTooltip11;


    @CN("通过三个轨道加速三种粒子")
    @EN("Accelerates three types of particles through three beamlines")
    public static Lang wideAcceleratorTooltip2;


    @CN("本机器只要求粒子速度大于配方需求，不满足需求无法运行。粒子速度不会超过50Gev，这台机器非常安全，不会引发爆炸。")
    @EN("The machine only requires particle velocities to exceed the recipe requirements; it cannot run otherwise. Particle velocity is capped at 50 GeV, making the machine very safe and preventing explosions.")
    public static Lang wideAcceleratorTooltip3;


    @CN("允许使用§b并行控制仓§r，不适用并行控制仓则使用默认值")
    @EN("Allows the use of §bparallel control hatches§r; without one, the default value is used")
    public static Lang wideAcceleratorTooltip4;


    @CN("在运行一般配方时如无§b并行控制仓§r,默认使用16并行")
    @EN("When running normal recipes without a §bparallel control hatch§r, the default is 16 parallel processes")
    public static Lang wideAcceleratorTooltip5;


    @CN("如果粒子速度过慢，则什么都不会产生")
    @EN("Insufficient particle velocity yields no products")
    public static Lang wideAcceleratorTooltip6;


    @CN("本机器可以存储至多50E EU能量，存储的能量可以在加速界面调整三种粒子速度，每调整1M ev粒子速度需要消耗10M EU存储的能量，按住shift时可以一次性更改10M ev粒子速度，按住ctrl时可以一次性更改100M ev粒子速度")
    @EN("The machine can store up to 50E EU. Stored energy can be used to adjust the three particle velocities in the acceleration interface. Each 1 MeV increase in particle velocity consumes 10M EU from stored energy. Hold Shift to change velocity by 10 MeV at once; hold Ctrl to change it by 100 MeV at once.")
    public static Lang wideAcceleratorTooltip7;


    @CN("三种粒子轨道速度之和每有1M ev，每tick便消耗机器存储的100EU能量，电量不足时，每tick粒子速度会衰减10%")
    @EN("For every 1 MeV in the sum of the three particle velocities, the machine consumes 100 EU of stored energy per tick. If power is insufficient, particle velocity decays by 10% per tick.")
    public static Lang wideAcceleratorTooltip8;


    @CN("机器没有运行时，默认使用存储能源舱室的所有能源为机器充能。机器运行时仍然使用能源仓的能量。但在机器运行时除非机器存储电量不够，否则能源仓不会为机器充能")
    @EN("When the machine is idle, it uses all energy in the energy-storage hatches to charge itself by default. While running, it continues to use energy from the energy hatches. However, during operation, the energy hatches will not charge the machine unless its stored energy is insufficient.")
    public static Lang wideAcceleratorTooltip9;


    @Key("ctnh.plasma_alloy.tooltip.recipe")
    @CN("配方类型：合金冶炼炉")
    @EN("Recipe Type: Alloy Blast Smelter")
    public static Lang plasmaAlloyTooltipRecipe;


    @Key("ctnh.u_sinope.1")
    @CN("配方类型：蒸馏塔/蒸馏室/裂化机/流体加热机/流体固化机/真空石化处理/???")
    @EN("Recipe types: Distillation Tower / Distillation Room / Cracker / Fluid Heater / Fluid Solidifier / Vacuum Petrochemical Processing / ???")
    public static Lang uSinope1;


    @Key("ctnh.u_sinope.2")
    @CN("§c它那究极的结构已然无法让你的EMI承受，你需要寻求蓝图的帮忙，同时在修改结构时最好直接破坏主方块以避免检测卡死游戏")
    @EN("§cIts ultimate structure is too much for your EMI to bear; seek help from blueprints. When modifying the structure, it is best to break the controller directly to avoid detection freezing the game")
    public static Lang uSinope2;


    @Key("ctnh.u_sinope.3")
    @CN("巨型的结构只能在真空建立，否则巨大的结构将会使周围坍缩（效率减少99.99%）")
    @EN("This giant structure can only be built in a vacuum; otherwise the surrounding area will collapse (efficiency reduced by 99.99%)")
    public static Lang uSinope3;


    @Key("ctnh.u_sinope.4")
    @CN("除非你使用四维工程学材料，否则它无法再承受UIV即以上的线圈，效率将减少99.99%")
    @EN("Unless four-dimensional engineering materials are used, it cannot withstand coils at UIV or above; efficiency is reduced by 99.99%")
    public static Lang uSinope4;


    @Key("ctnh.u_sinope.5")
    @CN("线圈等级决定了最大的配方等级，你最大只能使用线圈电压等级+1的配方等级，否则效率减少99%")
    @EN("Coil tier determines the maximum recipe tier. You can use at most a recipe tier one above the coil voltage tier; otherwise efficiency is reduced by 99%")
    public static Lang uSinope5;


    @Key("ctnh.u_sinope.6")
    @CN("允许使用激光仓，但你的配方电压等级必须达到OPV，否则效率将减少99%")
    @EN("Laser hatches are allowed, but the recipe voltage tier must reach OPV; otherwise efficiency is reduced by 99%")
    public static Lang uSinope6;


    @Key("ctnh.u_sinope.7")
    @CN("对于常规配方，该巨构拥有8^（电压等级）的并行，最高不超过2^32，在能源仓等级达到OPV时解锁无损超频，配方等级每超过UHV一级，处理速度+555%,每100点并行使处理速度增加333%,如果使用了四维工程学材料，则速度额外增加5000%")
    @EN("For normal recipes, this multiblock has 8^(voltage tier) parallel operations, capped at 2^32. Lossless overclocking unlocks when the energy hatch reaches OPV. Each recipe tier above UHV adds 555% processing speed; every 100 parallels adds 333% processing speed. Four-dimensional engineering materials add another 5000% speed")
    public static Lang uSinope7;


    @Key("ctnh.u_sinope.8")
    @CN("对于该巨构特有的配方类型具有特殊机制：时间固定为100秒，并行固定为10，电压每超过UHV一级，则时间减少10秒，并行增加10,如果使用了四维工程学材料且线圈等级大于等于UIV，则时间固定为1秒")
    @EN("This multiblock's unique recipe types use special mechanics: time is fixed at 100 seconds and parallelism at 10; for each voltage tier above UHV, time decreases by 10 seconds and parallelism increases by 10. If four-dimensional engineering materials are used and the coil tier is at least UIV, time is fixed at 1 second")
    public static Lang uSinope8;


    @Key("ctnh.u_sinope.story.1")
    @CN("在战争没有开始前，人们曾团结在一起，一齐建造这工业的巴别巨塔")
    @EN("Before the war began, people stood united and built this industrial Tower of Babel together")
    public static Lang uSinopeStory1;


    @Key("ctnh.u_sinope.story.2")
    @CN("直到那场永恒的战争，这座真空巨塔化为永恒的残骸，随着战争的双方破碎在真空中")
    @EN("Until the Eternal War, when this vacuum tower became eternal ruins, shattered in the vacuum along with both sides of the war")
    public static Lang uSinopeStory2;


    @Key("ctnh.u_sinope.story.3")
    @CN("你已无法再知晓那场战争的双方是否已经相互毁灭，但你直到，这座巨型结构将宣告着人类的复兴")
    @EN("You can no longer know whether both sides of that war destroyed each other, but you know this giant structure will herald humanity's revival")
    public static Lang uSinopeStory3;




    public final static MultiblockMachineDefinition SILICA_ROCK_FUEL_REFINERY = REGISTRATE.multiblock("silica_rock_fuel_refinery", WorkableElectricMultiblockMachine::new)
            .cnLangValue("硅岩燃料精炼厂")
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.SILICA_ROCK_FUEL_REFINERY)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_BLOCK)
            .recipeModifiers(OC_NON_PERFECT)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##AAA##", "##ABA##", "##AAA##", "###B###", "###B###", "###A###")
                    .aisle("###A###", "##AAA##", "###C###", "##ADA##", "###A###", "###A###")
                    .aisle("A##A##A", "AAADAAA", "A#ACA#A", "#AACAA#", "##ADA##", "###C###")
                    .aisle("AAAAAAA", "BADDDAB", "ACCDCCA", "BDCDCDB", "BADDDAB", "AACCCAA")
                    .aisle("A##A##A", "AAADAAA", "A#ACA#A", "#AACAA#", "##ADA##", "###C###")
                    .aisle("###A###", "##AAA##", "###C###", "##ADA##", "###A###", "###A###")
                    .aisle("##AAA##", "##A@A##", "##AAA##", "###B###", "###B###", "###A###")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_NAQUADAH_BLOCK.get()).setMinGlobalLimited(75)
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.NaquadahEnriched))
                    .where("C", Predicates.blocks(ANNIHILATE_CORE_MKI.get()))
                    .where("D", Predicates.blocks(PLASMA_COOLED_CORE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel((CTNHCore.id("block/casings/nq_casing")), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();


    public static MultiblockMachineDefinition NANOGENERATOR = REGISTRATE.multiblock("nanogenetor", NanoscaleTriboelectricGenerator::new)
            .cnLangValue("纳米摩擦发电机")
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.NANO_GENERATOR)
            .generator(true)
            .recipeModifier(NanoscaleTriboelectricGenerator::recipeModifier)
            .tooltips(nanoGeneratorTooltip0.translate(),
                    nanoGeneratorTooltip1.translate(),
                    nanoGeneratorTooltip2.translate(),
                    nanoGeneratorTooltip3.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###B###", "###C###", "###C###", "###C###", "###C###", "###C###", "###D###", "###D###", "###D###", "###D###", "###E###", "###F###", "###F###", "###D###")
                    .aisle("##EEE##", "##B#B##", "##C#C##", "##C#C##", "##C#C##", "##C#C##", "##C#C##", "##D#D##", "##D#D##", "##DED##", "##E#E##", "##F#F##", "##F#F##", "##DDD##")
                    .aisle("#EEEEE#", "#GHHHI#", "#BHHHB#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#DHHHD#", "#DHHHD#", "#E###E#", "#F###F#", "#F###F#", "#DJJJD#")
                    .aisle("EEEEEEE", "K#HLH#M", "N#HLH#G", "B#HLH#B", "C#HLH#C", "C#HLH#C", "C#HLH#C", "C#HLH#C", "C#HLH#C", "DEHLHEO", "E#####E", "F#####F", "F#####F", "DDJJJDD")
                    .aisle("#EEEEE#", "#GHHHI#", "#BHHHB#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#CHHHC#", "#DHHHD#", "#DHHHD#", "#E###E#", "#F###F#", "#F###F#", "#DJJJD#")
                    .aisle("##EEE##", "##B#B##", "##C#C##", "##C#C##", "##C#C##", "##C#C##", "##C#C##", "##D#D##", "##D#D##", "##DED##", "##E#E##", "##F#F##", "##F#F##", "##DDD##")
                    .aisle("###B###", "###C###", "###C###", "###C###", "###C###", "###C###", "###D###", "###D###", "###D###", "###D###", "###E###", "###F###", "###F###", "###D###")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.block, GTMaterials.Polyethylene)))
                    .where("C", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("D", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("E", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("F", Predicates.blocks(MACHINE_CASING_MV.get()))
                    .where("G", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.block, GTMaterials.Polyethylene)))
                    .where("H", (Predicates.any()))
                    .where("I", (abilities(PartAbility.OUTPUT_ENERGY)))
                    .where("J", Predicates.blocks(PISTON))
                    .where("K", abilities(PartAbility.IMPORT_ITEMS))
                    .where("L", (Predicates.any()))
                    .where("M", abilities(PartAbility.MAINTENANCE))
                    .where("N", abilities(PartAbility.EXPORT_ITEMS))
                    .where("O", Predicates.controller(Predicates.blocks(definition.get())))

                    .build())
            .workableCasingModel((GTCEu.id("block/casings/solid/machine_casing_solid_steel")), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition FOREST_SEA_TREE_FARM = REGISTRATE.multiblock("forest_sea_tree_farm", ForestMachine::new)
            .cnLangValue("林海树场")
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.WOOD_BIONICS)
            .appearanceBlock(CTNHBlocks.NATURAL_ECOLOGICAL_SHELL_CASING)
            .recipeModifiers(ForestMachine::recipeModifier, OC_NON_PERFECT)
            .tooltips(forestSeaTooltip1.translate().withStyle(ChatFormatting.GRAY),
                    forestSeaTooltip2.translate(),
                    forestSeaTooltip3.translate(),
                    forestSeaTooltip4.translate(),
                    forestSeaTooltip5.translate(),
                    forestSeaTooltip6.translate(),
                    forestSeaTooltip7.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA", "AAA", "AAA")
                    .aisle("AAA", "A#A", "AAA")
                    .aisle("AAA", "A@A", "AAA")
                    .where("A", Predicates.blocks(NATURAL_ECOLOGICAL_SHELL_CASING.get())
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel((CTNHCore.id("block/casings/natural_ecological_shell_casing")), CTNHCore.id("block/overlay/forest_sea_tree_farm"))
            .register();
    public final static MultiblockMachineDefinition SINOPE_CHEMICAL = REGISTRATE.multiblock("sinope_chemical", SinopeChemical::new)
            .cnLangValue("SINOPE化工厂")
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.SINOPE, GTRecipeTypes.CRACKING_RECIPES)
            .recipeModifiers(SinopeChemical::recipeModifier, OC_NON_PERFECT)
            .tooltips(sinopeChemicalTooltip0.translate(),
                    sinopeChemicalTooltip1.translate(),
                    sinopeChemicalTooltip2.translate(),
                    sinopeChemicalTooltip3.translate(),
                    sinopeChemicalTooltip4.translate(),
                    sinopeChemicalTooltip5.translate(),
                    sinopeChemicalTooltip6.translate(),
                    sinopeChemicalTooltip7.translate(),
                    sinopeChemicalTooltip8.translate(),
                    sinopeChemicalTooltip9.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAAAA", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BBBBBBB#", "#########", "#########", "#########", "#########", "#########", "#BBBBBBB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BCCCCCB#", "##DDDDD##", "##EEEEE##", "##EEEEE##", "##EEEEE##", "##DDDDD##", "#BCCCCCB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BCFFFCB#", "##DFFFD##", "##EGGGE##", "##EGGGE##", "##EGGGE##", "##DFFFD##", "#BCFFFCB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BCFFFCB#", "##DFFFD##", "##EG#GE##", "##EGHGE##", "##EG#GE##", "##DFFFD##", "#BCFFFCB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BCFFFCB#", "##DFFFD##", "##EGGGE##", "##EGGGE##", "##EGGGE##", "##DFFFD##", "#BCFFFCB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BCCCCCB#", "##DD@DD##", "##EEEEE##", "##EEEEE##", "##EEEEE##", "##DDDDD##", "#BCCCCCB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "#BBBBBBB#", "#########", "#########", "#########", "#########", "#########", "#BBBBBBB#", "AAAAAAAAA")
                    .aisle("AAAAAAAAA", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "A#######A", "AAAAAAAAA")
                    .where("A", Predicates.blocks(CASING_ASSEMBLY_CONTROL.get()).setMinGlobalLimited(170)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.any())
                    .where("B", Predicates.frames(GTMaterials.Naquadah))
                    .where("C", Predicates.blocks(MACHINE_CASING_ZPM.get()))
                    .where("D", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_GEARBOX.get()))
                    .where("E", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("F", Predicates.blocks(MACHINE_CASING_ZPM.get()))
                    .where("G", Predicates.heatingCoils())
                    .where("H", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, Naquadah).get())
                            .or(Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, Naquadria).get()))
                            .or(Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, NaquadahEnriched).get()))
                    )
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel((GTCEu.id("block/casings/mechanic/machine_casing_assembly_control")), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();
    public final static MultiblockMachineDefinition WIDE_PARTICLE_ACCELERATOR = REGISTRATE.multiblock("wide_particle_accelerator", WideParticleAccelerator::new)
            .cnLangValue("广粒子加速器")
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.ACCELERATOR_UP)
            .recipeModifiers(WideParticleAccelerator::recipeModifier, OC_NON_PERFECT)
            .tooltips(wideAcceleratorTooltip0.translate(),
                    wideAcceleratorTooltip1.translate(),
                    wideAcceleratorTooltip2.translate(),
                    wideAcceleratorTooltip3.translate(),
                    wideAcceleratorTooltip4.translate(),
                    wideAcceleratorTooltip5.translate(),
                    wideAcceleratorTooltip6.translate(),
                    wideAcceleratorTooltip7.translate(),
                    wideAcceleratorTooltip8.translate(),
                    wideAcceleratorTooltip9.translate(),
                    wideAcceleratorTooltip10.translate(),
                    wideAcceleratorTooltip11.translate()
            )

            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###########################", "###########################", "###########################", "#############C#############", "############CDC############", "###########CD#DC###########", "############CDC############", "#############C#############", "###########################", "###########################", "###########################")
                    .aisle("###########################", "###########################", "#############C#############", "############CDC############", "###########C###C###########", "##########CD###DC##########", "###########C###C###########", "############CDC############", "#############C#############", "###########################", "###########################")
                    .aisle("############CCC############", "############DDD############", "############CDC############", "###########CDDDC###########", "#########CCD###DCC#########", "#########DDD###DDD#########", "#########CCD###DCC#########", "###########CDDDC###########", "############CDC############", "############DDD############", "############CCC############")
                    .aisle("###########CCFCC###########", "###########DDFDD###########", "###########CCCCC###########", "############CDC############", "########CC#C###C#CC########", "########DDCD###DCDD########", "########CC#C###C#CC########", "############CDC############", "###########CCCCC###########", "###########DDFDD###########", "###########CCFCC###########")
                    .aisle("##########CCGGGCC##########", "##########DDGGGDD##########", "##########CCGGGCC##########", "###########GGGGG###########", "#######CC##GCDCG##CC#######", "#######DD##GD#DG##DD#######", "#######CC##GCDCG##CC#######", "###########GGGGG###########", "##########CCGGGCC##########", "##########DDGGGDD##########", "##########CCGGGCC##########")
                    .aisle("#########CC#####CC#########", "#########DD#####DD#########", "#########CC#####CC#########", "##########G#####G##########", "######CC##G#HIH#G##CC######", "######DD##G#I#I#G##DD######", "######CC##G#HIH#G##CC######", "##########G#####G##########", "#########CC#####CC#########", "#########DD#####DD#########", "#########CC#####CC#########")
                    .aisle("########CC#######CC########", "########DD#######DD########", "########CC#######CC########", "#########G#######G#########", "#####CC##G##HIH##G##CC#####", "#####DD##G##I#I##G##DD#####", "#####CC##G##HIH##G##CC#####", "#########G#######G#########", "########CC#######CC########", "########DD#######DD########", "########CC#######CC########")
                    .aisle("#######CC#########CC#######", "#######DD#########DD#######", "#######CC#########CC#######", "########G#########G########", "####CC##G###HIH###G##CC####", "####DD##G###I#I###G##DD####", "####CC##G###HIH###G##CC####", "########G#########G########", "#######CC#########CC#######", "#######DD#########DD#######", "#######CC#########CC#######")
                    .aisle("######CC###########CC######", "######DD###########DD######", "######CC###########CC######", "#######G###########G#######", "###CC##G####HIH####G##CC###", "###DD##G####I#I####G##DD###", "###CC##G####HIH####G##CC###", "#######G###########G#######", "######CC###########CC######", "######DD###########DD######", "######CC###########CC######")
                    .aisle("#####CC#############CC#####", "#####DD#############DD#####", "#####CC#############CC#####", "######G#############G######", "##CC##G#####HIH#####G##CC##", "##DD##G#####I#I#####G##DD##", "##CC##G#####HIH#####G##CC##", "######G#############G######", "#####CC#############CC#####", "#####DD#############DD#####", "#####CC#############CC#####")
                    .aisle("####CC###############CC####", "####DD###############DD####", "####CC###############CC####", "#####G###############G#####", "##C##G######HIH######G##C##", "#CDC#G######I#I######G#CDC#", "##C##G######HIH######G##C##", "#####G###############G#####", "####CC###############CC####", "####DD###############DD####", "####CC###############CC####")
                    .aisle("###CC#######JJJ#######CC###", "###DD#######JJJ#######DD###", "###CC#######JJJ#######CC###", "##C#G#######JJJ#######G#C##", "#CDCG#######HIH#######GCDC#", "CDDDG#######I#I#######GDDDC", "#CDCG#######HIH#######GCDC#", "##C#G#######JJJ#######G#C##", "###CC#######JJJ#######CC###", "###DD#######JJJ#######DD###", "###CC#######JJJ#######CC###")
                    .aisle("##CCG######J###J######GCC##", "##DDG######J###J######GDD##", "##CCG######J###J######GCC##", "#CDCG######JFFFJ######GCDC#", "C###CHHHHHHH###HHHHHHHC###C", "D###DIIIIIII###IIIIIIID###D", "C###CHHHHHHH###HHHHHHHC###C", "#CDCG######JFFFJ######GCDC#", "##CCG######J###J######GCC##", "##DDG######J###J######GDD##", "##CCG######J###J######GCC##")
                    .aisle("##CFG######J#H#J######GFC##", "##DFG######J#K#J######GFD##", "#CDCG######J#F#J######GCDC#", "CDDDG######JFFFJ######GDDDC", "D###DIIIIIII#F#IIIIIIID###D", "D############F############D", "D###DIIIIIII#F#IIIIIIID###D", "CDDDG######JFFFJ######GDDDC", "#CDCG######J#F#J######GCDC#", "##DFG######J#K#J######GFD##", "##CFG######J#H#J######GFC##")
                    .aisle("##CCG######J###J######GCC##", "##DDG######J###J######GDD##", "##CCG######J###J######GCC##", "#CDCG######JFFFJ######GCDC#", "C###CHHHHHHH###HHHHHHHC###C", "D###DIIIIIII###IIIIIIID###D", "C###CHHHHHHH###HHHHHHHC###C", "#CDCG######JFFFJ######GCDC#", "##CCG######J###J######GCC##", "##DDG######J###J######GDD##", "##CCG######J###J######GCC##")
                    .aisle("###CC#######JJJ#######CC###", "###DD#######JJJ#######DD###", "###CC#######JJJ#######CC###", "##C#G#######JJJ#######G#C##", "#CDCG#######HIH#######GCDC#", "CDDDG#######I#I#######GDDDC", "#CDCG#######HIH#######GCDC#", "##C#G#######JJJ#######G#C##", "###CC#######JJJ#######CC###", "###DD#######JJJ#######DD###", "###CC#######JJJ#######CC###")
                    .aisle("####CC###############CC####", "####DD###############DD####", "####CC###############CC####", "#####G###############G#####", "##C##G######HIH######G##C##", "#CDC#G######I#I######G#CDC#", "##C##G######HIH######G##C##", "#####G###############G#####", "####CC###############CC####", "####DD###############DD####", "####CC###############CC####")
                    .aisle("#####CC#############CC#####", "#####DD#############DD#####", "#####CC#############CC#####", "######G#############G######", "##CC##G#####HIH#####G##CC##", "##DD##G#####I#I#####G##DD##", "##CC##G#####HIH#####G##CC##", "######G#############G######", "#####CC#############CC#####", "#####DD#############DD#####", "#####CC#############CC#####")
                    .aisle("######CC###########CC######", "######DD###########DD######", "######CC###########CC######", "#######G###########G#######", "###CC##G####HIH####G##CC###", "###DD##G####I#I####G##DD###", "###CC##G####HIH####G##CC###", "#######G###########G#######", "######CC###########CC######", "######DD###########DD######", "######CC###########CC######")
                    .aisle("#######CC#########CC#######", "#######DD#########DD#######", "#######CC#########CC#######", "########G#########G########", "####CC##G###HIH###G##CC####", "####DD##G###I#I###G##DD####", "####CC##G###HIH###G##CC####", "########G#########G########", "#######CC#########CC#######", "#######DD#########DD#######", "#######CC#########CC#######")
                    .aisle("########CC#######CC########", "########DD#######DD########", "########CC#######CC########", "#########G#######G#########", "#####CC##G##HIH##G##CC#####", "#####DD##G##I#I##G##DD#####", "#####CC##G##HIH##G##CC#####", "#########G#######G#########", "########CC#######CC########", "########DD#######DD########", "########CC#######CC########")
                    .aisle("#########CC#####CC#########", "#########DD#####DD#########", "#########CC#####CC#########", "##########G#####G##########", "######CC##G#HIH#G##CC######", "######DD##G#I#I#G##DD######", "######CC##G#HIH#G##CC######", "##########G#####G##########", "#########CC#####CC#########", "#########DD#####DD#########", "#########CC#####CC#########")
                    .aisle("##########CCGGGCC##########", "##########DDGGGDD##########", "##########CCGGGCC##########", "###########GGGGG###########", "#######CC##GCDCG##CC#######", "#######DD##GD#DG##DD#######", "#######CC##GCDCG##CC#######", "###########GGGGG###########", "##########CCGGGCC##########", "##########DDGGGDD##########", "##########CCGGGCC##########")
                    .aisle("###########CCFCC###########", "###########DDFDD###########", "###########CCCCC###########", "############CDC############", "########CC#C###C#CC########", "########DDCD###DCDD########", "########CC#C###C#CC########", "############CDC############", "###########CCCCC###########", "###########DDFDD###########", "###########CCFCC###########")
                    .aisle("############CCC############", "############DDD############", "############CDC############", "###########CDDDC###########", "#########CCD###DCC#########", "#########DDD###DDD#########", "#########CCD###DCC#########", "###########CDDDC###########", "############CDC############", "############DDD############", "############CCC############")
                    .aisle("###########################", "###########################", "#############C#############", "############CDC############", "###########C###C###########", "##########CD###DC##########", "###########C###C###########", "############CDC############", "#############C#############", "###########################", "###########################")
                    .aisle("###########################", "###########################", "###########################", "#############C#############", "############CDC############", "###########CDEDC###########", "############CDC############", "#############C#############", "###########################", "###########################", "###########################")
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_NAQUADAH_BLOCK.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(2))
                            .or(abilities(PartAbility.SUBSTATION_INPUT_ENERGY).setMaxGlobalLimited(2))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                    )
                    .where("D", Predicates.blocks(WIDESPEEDINGPIPE.get()))
                    .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.blocks(neutron.get()))
                    .where("G", Predicates.blocks(HERMETIC_CASING_UHV.get()))
                    .where("H", Predicates.blocks(MACHINE_CASING_UHV.get()))
                    .where("I", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
                    .where("J", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("K", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .build())
            .workableCasingModel((GTCEu.id("block/casings/mechanic/machine_casing_assembly_control")), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition ARC_GENERATOR = REGISTRATE.multiblock("arc_generator", holder -> new Arc_Generator(holder, 0.75, 1000))
            .cnLangValue("电弧撕裂者")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ARC_GENERATOR)
            .generator(true)
            .recipeModifier(Arc_Generator::recipeModifier, true)
            .tooltips(arcgeneratorTooltip1.translate(),
                    arcgeneratorTooltipArcT11.translate(),
                    arcgeneratorTooltipArcT12.translate(),
                    arcgeneratorTooltip2.translate(),
                    arcgeneratorTooltip3.translate(),
                    arcgeneratorTooltip4.translate(),
                    arcgeneratorTooltip5.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################").aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGGGGGGHCCCCCGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGCCCCCHGGGGGGCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG####CIC####CB###", "##BDF####IJI####FDB##", "###BC####CIC####GB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BH####IJI####HB###", "##BDH####JJJ####HDB##", "##BFH####IJI####HFB##", "##BFB###########BFB##", "##BFB###########BFB##")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC####CIC####GB###", "##BDF####IJI####FDB##", "###BG####CIC####CB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGCCCCCHGGGGGGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGGGGGGHCCCCCGCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBEBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_NONCONDUCTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    )

                    .where("C", Predicates.frames(TungstenSteel))
                    .where("D", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.blocks(ARC_CELL.get()))
                    .where("G", Predicates.blocks(COIL_HSSG.get()))
                    .where("H", Predicates.blocks(HERMETIC_CASING_IV.get()))
                    .where("I", Predicates.blocks(MACHINE_CASING_IV.get()))
                    .where("J", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, TungstenSteel).get()))
                    .build()
            )
            .model(createWorkableCasingMachineModel(GTCEu.id("block/casings/gcym/nonconducting_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
                    .andThen(b -> b.addDynamicRenderer(ArcBlockRender::new)))
            .register();
    public final static MultiblockMachineDefinition ARC_GENERATOR_MK1 = REGISTRATE.multiblock("arc_generator_mk1", holder -> new Arc_Generator(holder, 1.25, 10000))
            .cnLangValue("超压电弧撕裂者MK1")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ARC_GENERATOR)
            .generator(true)
            .recipeModifier(Arc_Generator::recipeModifier, true)
            .tooltips(arcgeneratorTooltipT21.translate(),
                    arcgeneratorTooltipArcT21.translate(),
                    arcgeneratorTooltipArcT22.translate(),
                    arcgeneratorTooltip2.translate(),
                    arcgeneratorTooltip3.translate(),
                    arcgeneratorTooltip4.translate(),
                    arcgeneratorTooltip5.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################").aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGGGGGGHCCCCCGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGCCCCCHGGGGGGCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG####CIC####CB###", "##BDF####IJI####FDB##", "###BC####CIC####GB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BH####IJI####HB###", "##BDH####JJJ####HDB##", "##BFH####IJI####HFB##", "##BFB###########BFB##", "##BFB###########BFB##")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC####CIC####GB###", "##BDF####IJI####FDB##", "###BG####CIC####CB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGCCCCCHGGGGGGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGGGGGGHCCCCCGCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBEBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_NONCONDUCTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    )

                    .where("C", Predicates.frames(TungstenSteel))
                    .where("D", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.blocks(ARC_CELL.get()))
                    .where("G", Predicates.blocks(COIL_HSSG.get()))
                    .where("H", Predicates.blocks(HERMETIC_CASING_IV.get()))
                    .where("I", Predicates.blocks(MACHINE_CASING_IV.get()))
                    .where("J", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, TungstenSteel).get()))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/gcym/nonconducting_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition ARC_GENERATOR_MK2 = REGISTRATE.multiblock("arc_generator_mk2", holder -> new Arc_Generator(holder, 2.25, 50000))
            .cnLangValue("过载电弧撕裂者MK1")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ARC_GENERATOR)
            .generator(true)
            .recipeModifier(Arc_Generator::recipeModifier, true)
            .tooltips(arcgeneratorTooltipT31.translate(),
                    arcgeneratorTooltipArcT31.translate(),
                    arcgeneratorTooltipArcT32.translate(),
                    arcgeneratorTooltip2.translate(),
                    arcgeneratorTooltip3.translate(),
                    arcgeneratorTooltip4.translate(),
                    arcgeneratorTooltip5.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################").aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGGGGGGHCCCCCGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGCCCCCHGGGGGGCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG###########CB###", "##BDF###########FDB##", "###BC###########GB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BG####CIC####CB###", "##BDF####IJI####FDB##", "###BC####CIC####GB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BH####IJI####HB###", "##BDH####JJJ####HDB##", "##BFH####IJI####HFB##", "##BFB###########BFB##", "##BFB###########BFB##")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC####CIC####GB###", "##BDF####IJI####FDB##", "###BG####CIC####CB###", "###B#############B###", "###B#############B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###BC###########GB###", "##BDF###########FDB##", "###BG###########CB###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCGCCCCCHGGGGGGCB##", "#BCDGFFFFFHFFFFFGDCB#", "##BCGGGGGGHCCCCCGCB##", "###B######B######B###", "##########B##########")
                    .aisle("###B#############B###", "###B#############B###", "###B#############B###", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBBBBBBBCDCB#", "BCDFDDDDDDDDDDDDDFDCB", "#BCDCBBBBBFBBBBBCDCB#", "##BCB####BFB####BCB##", "###B#####BFB#####B###")
                    .aisle("#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "#BCDCBBBBBEBBBBBCDCB#", "##BCB#####B#####BCB##", "###B######B######B###", "##########B##########")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "##BCB###########BCB##", "###B#############B###", "#####################", "#####################")
                    .aisle("#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "###B#############B###", "#####################", "#####################", "#####################")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_NONCONDUCTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    )

                    .where("C", Predicates.frames(TungstenSteel))
                    .where("D", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.blocks(ARC_CELL.get()))
                    .where("G", Predicates.blocks(COIL_HSSG.get()))
                    .where("H", Predicates.blocks(HERMETIC_CASING_IV.get()))
                    .where("I", Predicates.blocks(MACHINE_CASING_IV.get()))
                    .where("J", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, TungstenSteel).get()))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/gcym/nonconducting_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition ARC_REACTOR = REGISTRATE.multiblock("arc_reactor", holder -> new Arc_Reactor(holder, 10))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ARC_REACTOR)
            .recipeModifier(Arc_Reactor::recipeModifier)
            .tooltips(arcreactorTooltip1.translate(),
                    arcreactorTooltip2.translate(),
                    arcreactorTooltipT1.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#########B########", "#########B########", "#########B########", "#########B########", "#########B########", "#########B########", "##################", "##################")
                    .aisle("########BDB#######", "########BDB#######", "########BDB#######", "########BDB#######", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("#########B########", "#########B########", "#########B########", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("##################", "##################", "##################", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("##################", "##################", "##################", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("##################", "#########B########", "#########B########", "#########B########", "######BBBDBBB#####", "#########B########", "#########B########", "#########B########")
                    .aisle("##################", "#########B########", "########BDB#######", "#######BBDBB######", "######BDDDDDB#####", "#######BBDBB######", "########BDB#######", "#########B########")
                    .aisle("##B#############B#", "##B######B######B#", "##B####BBDBB####B#", "##B####B###B####B#", "##BBBBBD###DBBBBB#", "#######B###B######", "#######BBDBB######", "#########B########")
                    .aisle("#BDB###########BDB", "#BDB##BBBBBBB##BDB", "#BDB##BDDDDDB##BDB", "#BDBBBBD###DBBBBDB", "#BDDDDDD###DDDDDDB", "#BBBBBBD###DBBBBBB", "######BDDDDDB#####", "######BBBBBBB#####")
                    .aisle("##B#############B#", "##B######B######B#", "##B####BBDBB####B#", "##B####B###B####B#", "##BBBBBD###DBBBBB#", "#######B###B######", "#######BBDBB######", "#########B########")
                    .aisle("##################", "#########B########", "########BDB#######", "#######BBDBB######", "######BDDDDDB#####", "#######BBDBB######", "########BDB#######", "#########B########")
                    .aisle("##################", "#########B########", "#########B########", "#########B########", "######BBBDBBB#####", "#########B########", "#########B########", "#########B########")
                    .aisle("##################", "##################", "##################", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("##################", "##################", "##################", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("#########B########", "#########B########", "#########B########", "#########B########", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("########BDB#######", "########BDB#######", "########BDB#######", "########BDB#######", "########BDB#######", "#########B########", "##################", "##################")
                    .aisle("#########B########", "#########@########", "#########B########", "#########B########", "#########B########", "#########B########", "##################", "##################")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_NONCONDUCTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    )
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.blocks(ARC_CELL.get()))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/nonconducting_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();

    public final static MultiblockMachineDefinition SUPERCONDUCTING_PENNING_TRAP = REGISTRATE.multiblock("superconducting_penning_trap", Superconducting_Penning_Trap::new)
            .cnLangValue("超导潘宁势阱")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(DUMMY_RECIPES)
//            .recipeModifiers(Superconducting_Penning_Trap::recipeModifier)
            .tooltips(Component.translatable("ctnh.trap.1"),
                    Component.translatable("ctnh.trap.2"),
                    Component.translatable("ctnh.trap.3"),
                    Component.translatable("ctnh.trap.4"),
                    Component.translatable("ctnh.trap.5")
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A###############BBBBBBB###############A", "###############CCCCCCCCC###############", "###############CCCCCCCCC###############", "###############CCCCCCCCC###############", "################BBBBBBB################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#############BBBEEEEEEEBBB#############", "############CCC#########CCC############", "############CCC#########CCC############", "############CCC#########CCC############", "#############BBBEEEEEEEBBB#############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###########BBEEEEEEEEEEEEEBB###########", "##########CC###############CC##########", "##########CC###############CC##########", "##########CC###############CC##########", "###########BBEEEEEEEEEEEEEBB###########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#########BBEEEEEEEEEEEEEEEEEBB#########", "########CC###################CC########", "########CC###################CC########", "########CC###################CC########", "#########BBEEEEEEEEEEEEEEEEEBB#########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("########BEEEEEEEBBBEBBBEEEEEEEB########", "#######C#########CCCCC#########C#######", "#######C#########CCCCC#########C#######", "#######C#########CCCCC#########C#######", "########BEEEEEEEBBBBBBBEEEEEEEB########", "#################FFFFF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FFHFF#################", "#######################################", "##################D#D##################", "#################GGBGG#################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#######BEEEEEBBBIIIEIIIBBBEEEEEB#######", "######C#######CCC#####CCC#######C######", "######C#######CCC#####CCC#######C######", "######C#######CCC#####CCC#######C######", "#######BEEEEEBBB#######BBBEEEEEB#######", "##############FFF#####FFF##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############FFF#FFF#FFF##############", "#######################################", "##################D#D##################", "##############GGG##B##GGG##############", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("######BEEEEBBIIIIIIEIIIIIIBBEEEEB######", "#####C######CC###########CC######C#####", "#####C######CC###########CC######C#####", "#####C######CC###########CC######C#####", "######BEEEEBB#############BBEEEEB######", "############FF###########FF############", "############JJ###########JJ############", "############JJ###########JJ############", "############JJ###########JJ############", "############JJ###########JJ############", "############FF####FFF####FF############", "#######################################", "##################D#D##################", "############GG#####B#####GG############", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#####BEEEBBIIIIIIIIEIIIIIIIIBBEEEB#####", "####C#####CC###############CC#####C####", "####C#####CC###############CC#####C####", "####C#####CC###############CC#####C####", "#####BEEEBB#################BBEEEB#####", "##########FF###############FF##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########FF######FFF######FF##########", "#######################################", "##################D#D##################", "##########GG#######B#######GG##########", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("####BEEEBIIIIIIIIIIEIIIIIIIIIIBEEEB####", "###C#####C###################C#####C###", "###C#####C###################C#####C###", "###C#####C###################C#####C###", "####BEEEB#####################BEEEB####", "#########F###################F#########", "#########G###################G#########", "#########G###################G#########", "#########G###################G#########", "#########G#######FFFFF#######G#########", "#########F#######GGHGG#######F#########", "#########G#######FFFFF#######G#########", "#########G#######BBBBB#######G#########", "#########F#######GGGGG#######F#########", "#################KKDKK#################", "#################DDDDD#################", "#################KKKKK#################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###BEEEBIIIIIIIIIIIEIIIIIIIIIIIBEEEB###", "###C####C#####################C####C###", "###C####C#####################C####C###", "###C####C#####################C####C###", "###BEEEB#######################BEEEB###", "########F#####################F########", "########G#####################G########", "########G#####################G########", "########G#####################G########", "########G#######FF###FF#######G########", "########F#######GG###GG#######F########", "########G#######FF###FF#######G########", "########G#######BB###BB#######G########", "########FFF#####GG###GG#####FFF########", "################KK###KK################", "################DD#D#DD################", "################KK###KK################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###BEEEBIIIIIIIIIIIEIIIIIIIIIIIBEEEB###", "##C#L##C#######################C####C##", "##C####C#######################C####C##", "##C####C#######################C####C##", "###BEEEB#######################BEEEB###", "#######F#######################F#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######FF#####FF#######J#######", "#######F#######GG#####GG#######F#######", "###############FF#####FF###############", "###############BB#####BB###############", "#######G#FFF###GG#####GG###FFF#G#######", "###############KK#####KK###############", "###############DD##D##DD###############", "###############KK#####KK###############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("##BEEEBIIIIIIIIIIIIEIIIIIIIIIIIIBEEEB##", "##C####C#######################C####C##", "##C####C#######################C####C##", "##C####C#######################C####C##", "##BEEEB#########################BEEEB##", "#######F#######################F#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######################J#######", "#######J######FF#######FF######J#######", "#######F######GG#######GG######F#######", "##############FF#######FF##############", "##############BB#######BB##############", "#######G##FFFFGG#######GGFFFF##G#######", "##############KK#######KK##############", "##############DD###D###DD##############", "##############KK#######KK##############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("##BEEEBIIIIIIIIIIIIEIIIIIIIIIIIIBEEEB##", "#C####C#########################C####C#", "#C####C#########################C####C#", "#C####C#########################C####C#", "##BEEEB#########################BEEEB##", "######F#########################F######", "######J#########################J######", "######J#########################J######", "######J#########################J######", "######J######FF#########FF######J######", "######F######GG#########GG######F######", "#############FF#########FF#############", "#############BB#########BB#############", "######G####FFGG#########GGFF####G######", "#############KK#########KK#############", "#############DD####D####DD#############", "#############KK#########KK#############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "#C####C#########################C####C#", "#C####C#########################C####C#", "#C####C#########################C####C#", "#BEEEB###########################BEEEB#", "######F#########################F######", "######J#########################J######", "######J#########################J######", "######J#########################J######", "######J#####FF###########FF#####J######", "######F#####GG###########GG#####F######", "############FF###########FF############", "############BB###########BB############", "######G####FGG###########GGF####G######", "############KK###########KK############", "############DD#####D#####DD############", "############KK###########KK############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "#C###C###########################C###C#", "#C###C###########################C###C#", "#C###C###########################C###C#", "#BEEEB###########################BEEEB#", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J#####FF#############FF#####J#####", "#####F#####GG#############GG#####F#####", "###########FF#############FF###########", "###########BB#############BB###########", "#####G#####GG#############GG#####G#####", "###########KK#############KK###########", "###########DD######D######DD###########", "###########KK#############KK###########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "C####C###########################C####C", "C####C###########################C####C", "C####C###########################C####C", "#BEEEB###########################BEEEB#", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J####FF###############FF####J#####", "#####F####GG###############GG####F#####", "##########FF###############FF##########", "##########BB###############BB##########", "#####G####GG###############GG####G#####", "##########KK###############KK##########", "##########DD#######D#######DD##########", "##########KK###############KK##########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C####C###########################C####C", "C####C###########################C####C", "C####C###########################C####C", "BEEEB#############################BEEEB", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###FF#################FF###J#####", "#####F###GG#################GG###F#####", "#########FF#################FF#########", "#########BB#################BB#########", "#####G###GG#################GG###G#####", "#########KK#################KK#########", "#########DD########D########DD#########", "#########KK#################KK#########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C###C#############################C###C", "C###C#############################C###C", "C###C#############################C###C", "BEEEB#############################BEEEB", "####F#############################F####", "####F#############################F####", "####F#############################F####", "####F#############################F####", "####F###FF#########M#########FF###F####", "####F###GG########MHM########GG###F####", "########FF#########M#########FF########", "########BB###################BB########", "####G###GG###################GG###G####", "########KK###################KK########", "########DD#########D#########DD########", "########KK###################KK########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C###C##############M##############C###C", "C###C##############M##############C###C", "C###C##############M##############C###C", "BEEEB##############M##############BEEEB", "####F##############M##############F####", "####G##############M##############G####", "####G##############M##############G####", "####G##############M##############G####", "####G###F#########BBB#########F###G####", "####FFFFG########MBNBM########GFFFF####", "########F#########BBB#########F########", "DDDDDDDDB##########M##########BDDDDDDDD", "####G###G##########M##########G###G####", "DDDDDDDDK##########M##########KDDDDDDDD", "########D##########D##########D########", "########K#####################K########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEEEEEEEEEEEEEEEEIEEEEEEEEEEEEEEEEEEB", "C###C#############MEM#############C###C", "C###C#############MEM#############C###C", "C###C#############MEM#############C###C", "BEEEB#############MEM#############BEEEB", "####F#############MEM#############F####", "####G#############MEM#############G####", "####G#############MEM#############G####", "####G#############MEM#############G####", "####G###F########MBOBM########F###G####", "####HFFFH########HNNNH########HFFFH####", "########F########MBOBM########F########", "########B#########MOM#########B########", "BBBBBBBBG#########MOM#########GBBBBBBBB", "########D#########MOM#########D########", "########DDDDDDDDDDDODDDDDDDDDDD########", "########K##########O##########K########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C###C##############M##############C###C", "C###C##############M##############C###C", "C###C##############M##############C###C", "BEEEB##############M##############BEEEB", "####F##############M##############F####", "####G##############M##############G####", "####G##############M##############G####", "####G##############M##############G####", "####G###F#########BBB#########F###G####", "####FFFFG########MBNBM########GFFFF####", "########F#########BBB#########F########", "DDDDDDDDB##########M##########BDDDDDDDD", "####G###G##########M##########G###G####", "DDDDDDDDK##########M##########KDDDDDDDD", "########D##########D##########D########", "########K#####################K########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C###C#############################C###C", "C###C#############################C###C", "C###C#############################C###C", "BEEEB#############################BEEEB", "####F#############################F####", "####F#############################F####", "####F#############################F####", "####F#############################F####", "####F###FF#########M#########FF###F####", "####F###GG########MHM########GG###F####", "########FF#########M#########FF########", "########BB###################BB########", "####G###GG###################GG###G####", "########KK###################KK########", "########DD#########D#########DD########", "########KK###################KK########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("BEEEBIIIIIIIIIIIIIIEIIIIIIIIIIIIIIBEEEB", "C####C###########################C####C", "C####C###########################C####C", "C####C###########################C####C", "BEEEB#############################BEEEB", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###FF#################FF###J#####", "#####F###GG#################GG###F#####", "#########FF#################FF#########", "#########BB#################BB#########", "#####G###GG#################GG###G#####", "#########KK#################KK#########", "#########DD########D########DD#########", "#########KK#################KK#########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "C####C###########################C####C", "C####C###########################C####C", "C####C###########################C####C", "#BEEEB###########################BEEEB#", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J####FF###############FF####J#####", "#####F####GG###############GG####F#####", "##########FF###############FF##########", "##########BB###############BB##########", "#####G####GG###############GG####G#####", "##########KK###############KK##########", "##########DD#######D#######DD##########", "##########KK###############KK##########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "#C###C###########################C###C#", "#C###C###########################C###C#", "#C###C###########################C###C#", "#BEEEB###########################BEEEB#", "#####F###########################F#####", "#####J###########################J#####", "#####J###########################J#####", "#####J###########################J#####", "#####J#####FF#############FF#####J#####", "#####F#####GG#############GG#####F#####", "###########FF#############FF###########", "###########BB#############BB###########", "#####G#####GG#############GG#####G#####", "###########KK#############KK###########", "###########DD######D######DD###########", "###########KK#############KK###########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#BEEEBIIIIIIIIIIIIIEIIIIIIIIIIIIIBEEEB#", "#C####C#########################C####C#", "#C####C#########################C####C#", "#C####C#########################C####C#", "#BEEEB###########################BEEEB#", "######F#########################F######", "######J#########################J######", "######J#########################J######", "######J#########################J######", "######J#####FF###########FF#####J######", "######F#####GG###########GG#####F######", "############FF###########FF############", "############BB###########BB############", "######G####FGG###########GGF####G######", "############KK###########KK############", "############DD#####D#####DD############", "############KK###########KK############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("##BEEEBIIIIIIIIIIIIEIIIIIIIIIIIIBEEEB##", "#C####C#########################C####C#", "#C####C#########################C####C#", "#C####C#########################C####C#", "##BEEEB#########################BEEEB##", "######F#########################F######", "######J#########################J######", "######J#########################J######", "######J#########################J######", "######J######FF#########FF######J######", "######F######GG#########GG######F######", "#############FF#########FF#############", "#############BB#########BB#############", "######G####FFGG#########GGFF####G######", "#############KK#########KK#############", "#############DD####D####DD#############", "#############KK#########KK#############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("##BEEEBIIIIIIIIIIIIEIIIIIIIIIIIIBEEEB##", "##C####C#######################C####C##", "##C####C#######################C####C##", "##C####C#######################C####C##", "##BEEEB#########################BEEEB##", "#######F#######################F#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######################J#######", "#######J######FF#######FF######J#######", "#######F######GG#######GG######F#######", "##############FF#######FF##############", "##############BB#######BB##############", "#######G##FFFFGG#######GGFFFF##G#######", "##############KK#######KK##############", "##############DD###D###DD##############", "##############KK#######KK##############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###BEEEBIIIIIIIIIIIEIIIIIIIIIIIBEEEB###", "##C####C#######################C####C##", "##C####C#######################C####C##", "##C####C#######################C####C##", "###BEEEB#######################BEEEB###", "#######F#######################F#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######################J#######", "#######J#######FF#####FF#######J#######", "#######F#######GG#####GG#######F#######", "###############FF#####FF###############", "###############BB#####BB###############", "#######G#FFF###GG#####GG###FFF#G#######", "###############KK#####KK###############", "###############DD##D##DD###############", "###############KK#####KK###############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###BEEEBIIIIIIIIIIIEIIIIIIIIIIIBEEEB###", "###C####C#####################C####C###", "###C####C#####################C####C###", "###C####C#####################C####C###", "###BEEEB#######################BEEEB###", "########F#####################F########", "########G#####################G########", "########G#####################G########", "########G#####################G########", "########G#######FF###FF#######G########", "########F#######GG###GG#######F########", "########G#######FF###FF#######G########", "########G#######BB###BB#######G########", "########FFF#####GG###GG#####FFF########", "################KK###KK################", "################DD#D#DD################", "################KK###KK################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("####BEEEBIIIIIIIIIIEIIIIIIIIIIBEEEB####", "###C#####C###################C#####C###", "###C#####C###################C#####C###", "###C#####C###################C#####C###", "####BEEEB#####################BEEEB####", "#########F###################F#########", "#########G###################G#########", "#########G###################G#########", "#########G###################G#########", "#########G#######FFFFF#######G#########", "#########F#######GGHGG#######F#########", "#########G#######FFFFF#######G#########", "#########G#######BBBBB#######G#########", "#########F#######GGGGG#######F#########", "#################KKDKK#################", "#################DDDDD#################", "#################KKKKK#################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#####BEEEBBIIIIIIIIEIIIIIIIIBBEEEB#####", "####C#####CC###############CC#####C####", "####C#####CC###############CC#####C####", "####C#####CC###############CC#####C####", "#####BEEEBB#################BBEEEB#####", "##########FF###############FF##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########JJ###############JJ##########", "##########FF######FFF######FF##########", "#######################################", "##################D#D##################", "##########GG#######B#######GG##########", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("######BEEEEBBIIIIIIEIIIIIIBBEEEEB######", "#####C######CC###########CC######C#####", "#####C######CC###########CC######C#####", "#####C######CC###########CC######C#####", "######BEEEEBB#############BBEEEEB######", "############FF###########FF############", "############JJ###########JJ############", "############JJ###########JJ############", "############JJ###########JJ############", "############JJ###########JJ############", "############FF####FFF####FF############", "#######################################", "##################D#D##################", "############GG#####B#####GG############", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#######BEEEEEBBBIIIEIIIBBBEEEEEB#######", "######C#######CCC#####CCC#######C######", "######C#######CCC#####CCC#######C######", "######C#######CCC#####CCC#######C######", "#######BEEEEEBBB#######BBBEEEEEB#######", "##############FFF#####FFF##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############JJJ#####JJJ##############", "##############FFF#FFF#FFF##############", "#######################################", "##################D#D##################", "##############GGG##B##GGG##############", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("########BEEEEEEEBBBEBBBEEEEEEEB########", "#######C#########CCCCC#########C#######", "#######C#########CCCCC#########C#######", "#######C#########CCCCC#########C#######", "########BEEEEEEEBBBBBBBEEEEEEEB########", "#################FFFFF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FGGGF#################", "#################FFHFF#################", "#######################################", "##################D#D##################", "#################GGBGG#################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#########BBEEEEEEEEEEEEEEEEEBB#########", "########CC###################CC########", "########CC###################CC########", "########CC###################CC########", "#########BBEEEEEEEEEEEEEEEEEBB#########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("###########BBEEEEEEEEEEEEEBB###########", "##########CC###############CC##########", "##########CC###############CC##########", "##########CC###############CC##########", "###########BBEEEEEEEEEEEEEBB###########", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("#############BBBEEEEEEEBBB#############", "############CCC#########CCC############", "############CCC#########CCC############", "############CCC#########CCC############", "#############BBBEEEEEEEBBB#############", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################")
                    .aisle("A###############BBBBBBB################", "###############CCCCCCCCC###############", "###############CCCC@CCCC###############", "###############CCCCCCCCC###############", "################BBBBBBB################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "##################D#D##################", "###################B###################", "##################D#D##################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "#######################################", "######################################A")
                    .where("#", Predicates.any())
                    .where("A", Predicates.any())
                    .where("B", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("C", Predicates.blocks(CASING_NAQUADAH_ALLOY_BLOCK.get()))
                    .where("D", Predicates.blocks(ADVANCED_COMPUTER_CASING.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY)))
                    .where("E", Predicates.blocks(WIDESPEEDINGPIPE.get()))
                    .where("F", Predicates.blocks(MACHINE_CASING_ZPM.get()))
                    .where("G", Predicates.blocks(neutron.get()))
                    .where("H", Predicates.blocks(HERMETIC_CASING_ZPM.get()))
                    .where("I", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("J", Predicates.blocks(FUSION_GLASS.get()))
                    .where("K", Predicates.blocks(COMPUTER_HEAT_VENT.get()))
                    .where("L", Predicates.any())
                    .where("M", Predicates.frames(Neutronium))
                    .where("N", Predicates.blocks(FUSION_CASING.get()))
                    .where("O", Predicates.blocks(BATTERY_EMPTY_TIER_III.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel((CTNHCore.id("block/casings/nq_alloy_casing")), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public static final MultiblockMachineDefinition ADVANCED_ASSEMBLY_LINE = REGISTRATE
            .multiblock("advance_assembly_line", AssemblyLineMachine::new)
            .cnLangValue("进阶装配线")
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ASSEMBLY_LINE_RECIPES)
            .alwaysTryModifyRecipe(true)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, OC_NON_PERFECT, GTRecipeModifiers.BATCH_MODE)
            .tooltips(CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate(),
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .appearanceBlock(ADVANCE_MACHINE_CASING_SOLID_STEEL)
            .pattern(definition -> FactoryBlockPattern.start(BACK, UP, RIGHT)
                    .aisle("FIF", "RTR", "SAG", "#Y#")
                    .aisle("FIF", "RTR", "DAG", "#Y#").setRepeatable(3, 15)
                    .aisle("FOF", "RTR", "DAG", "#Y#")
                    .where("S", Predicates.controller(blocks(definition.getBlock())))
                    .where("F", blocks(ADVANCE_MACHINE_CASING_SOLID_STEEL.get())
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1).setPreviewCount(1)))
                    .where("O",
                            abilities(PartAbility.EXPORT_ITEMS)
                                    .addTooltips(Component.translatable("gtceu.multiblock.pattern.location_end")))
                    .where("Y",
                            blocks(ADVANCE_MACHINE_CASING_SOLID_STEEL.get())
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(16))
                                    .or(abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(16)))
                    .where("I", abilities(PartAbility.IMPORT_ITEMS))
                    .where("G", blocks(ADVANCE_MACHINE_CASING_GRATE.get()))
                    .where("A", blocks(ADVANCE_MACHINE_CASING_ASSEMBLY_CONTROL.get()))
                    .where("R", blocks(FUSION_GLASS.get()))
                    .where("T", blocks(ADVANCE_MACHINE_CASING_ASSEMBLY_LINE.get()))
                    .where("D", dataHatchPredicate(blocks(ADVANCE_MACHINE_CASING_GRATE.get())))
                    .where("#", Predicates.any())
                    .build())
            .partSorter(AssemblyLineMachine::partSorter)
            .workableCasingModel(CTNHCore.id("block/casings/advance_machine_casing_solid_steel"),
                    GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public final static MultiblockMachineDefinition CultivationRoom = REGISTRATE.multiblock("cultivationroom", WorkableElectricMultiblockMachine::new)
            .cnLangValue("培养室")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CULTIVATION_ROOM)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, OC_NON_PERFECT, BATCH_MODE)
            .tooltips(cultivationRoomTooltip1.translate().withStyle(ChatFormatting.GREEN),
                    cultivationRoomTooltip2.translate(),
                    CTNHCommonTooltips.PARALLEL_HATCH,
                    CTNHCommonTooltips.gtceuMultiblockLaserTooltip.translate()
            )
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ACCCA", "ADDDA", "ADDDA", "ADDDA", "AAAAA", "ABBBA", "AAAAA")
                    .aisle("AAAAA", "BBBBB", "CDDDC", "DEEED", "DEEED", "DEEED", "ADDDA", "BBBBB", "AAAAA")
                    .aisle("AAAAA", "BBBBB", "CDDDC", "DEEED", "DEEED", "DEEED", "ADDDA", "BBBBB", "AAAAA")
                    .aisle("AAAAA", "BBBBB", "CDDDC", "DEEED", "DEEED", "DEEED", "ADDDA", "BBBBB", "AAAAA")
                    .aisle("AAAAA", "ABBBA", "AC@CA", "ADDDA", "ADDDA", "ADDDA", "AAAAA", "ABBBA", "AAAAA")
                    .where("A", blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("B", blocks(FILTER_CASING_STERILE.get()))
                    .where("C", Predicates.autoAbilities(definition.getRecipeTypes())
                            .or(abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1))
                            .or(blocks(CASING_STAINLESS_CLEAN.get())))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.blocks(CLEANROOM_GLASS.get()))
                    .where("E", Predicates.blocks(WATER))
                    .build()
            )
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/implosion_compressor"))
            .register();


    public final static MultiblockMachineDefinition PLASMA_ALLOY_BLAST_SMELTER = REGISTRATE.multiblock("plasma_alloy_blast_smelter", PlasmaAlloyBlast::new)
            .cnLangValue("等离子合金冶炼转底炉")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GCYMRecipeTypes.ALLOY_BLAST_RECIPES)
            .recipeModifiers(PlasmaAlloyBlast::recipeModifier, GTRecipeModifiers::ebfOverclock, BATCH_MODE)
            .tooltips(plasmaAlloyTooltip1.translate(),
                    plasmaAlloyTooltip11.translate(),
                    plasmaAlloyTooltip2.translate(),
                    plasmaAlloyTooltip3.translate(),
                    plasmaAlloyTooltip4.translate(),
                    plasmaAlloyTooltip5.translate(),
                    plasmaAlloyTooltip6.translate(),
                    plasmaAlloyTooltip7.translate(),
                    plasmaAlloyTooltip8.translate(),
                    plasmaAlloyTooltip9.translate(),
                    plasmaAlloyTooltip10.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A####BBBBBBB####A", "#####BBBBBBB#####", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################")
                    .aisle("###BBBBBBBBBBB###", "###BBBBBBBBBBB###", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################")
                    .aisle("##BBBBBBBBBBBBB##", "##BBBBBBBBBBBBB##", "####CCCCCCCCC####", "####CCCCCCCCC####", "#################", "#################", "#################", "#################", "#################", "####CCCCCCCCC####", "####CCCCCCCCC####", "#################", "#################", "#################", "#################", "#################", "#################")
                    .aisle("#BBBBBBBBBBBBBBB#", "#BBBBBBBBBBBBBBB#", "###CCDDDDDDDCC###", "###CCDDDDDDDCC###", "####C#######C####", "####C#######C####", "####C#######C####", "####C#######C####", "####C#######C####", "###CCDDDDDDDCC###", "###CCDDDDDDDCC###", "#################", "#################", "#################", "#################", "#################", "#####EEEEEEE#####")
                    .aisle("#BBBBBBBBBBBBBBB#", "#BBBBBBBBBBBBBBB#", "##CCDFFFFFFFDCC##", "##CCDGGGGGGGDCC##", "###C#########C###", "###C#########C###", "###C#########C###", "###C#########C###", "###C#########C###", "##CCDGGGGGGGDCC##", "##CCDHHHHHHHDCC##", "#################", "#################", "#################", "#################", "#################", "####EEEEEEEEE####")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFFFFFFFFDC##", "##CDGGGGGGGGGDC##", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "##CDGIGGIGGIGDC##", "##CDHIHHIHHIHDC##", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "###EEJEEJEEJEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFKKKKKFFDC##", "##CDGGLLLLLGGDC##", "######MNNNM######", "######MNNNM######", "######MNNNM######", "######MNNNM######", "######MNNNM######", "##CDGGLLLLLGGDC##", "##CDHHOOOOOHHDC##", "######PPPPP######", "######QQQQQ######", "######QQQQQ######", "######QQQQQ######", "######PPPPP######", "###EEEJEJEJEEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFK###KFFDC##", "##CDGGL###LGGDC##", "######N###N######", "######N###N######", "######N###N######", "######N###N######", "######N###N######", "##CDGGL###LGGDC##", "##CDHHO###OHHDC##", "######P###P######", "######Q###Q######", "######Q###Q######", "######Q###Q######", "######P###P######", "###EEEEJJJEEEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFK###KFFDC##", "##CDGGL###LGGDC##", "#####IN###NI#####", "#####IN###NI#####", "#####IN###NI#####", "#####IN###NI#####", "#####IN###NI#####", "##CDGIL###LIGDC##", "##CDHIO###OIHDC##", "#####IP###PI#####", "#####IQ###QI#####", "#####IQ###QI#####", "#####IQ###QI#####", "#####IP###PI#####", "###EEJJJEJJJEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFK###KFFDC##", "##CDGGL###LGGDC##", "######N###N######", "######N###N######", "######N###N######", "######N###N######", "######N###N######", "##CDGGL###LGGDC##", "##CDHHO###OHHDC##", "######P###P######", "######Q###Q######", "######Q###Q######", "######Q###Q######", "######P###P######", "###EEEEJJJEEEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFKKKKKFFDC##", "##CDGGLLLLLGGDC##", "######MNNNM######", "######MNNNM######", "######MNNNM######", "######MNNNM######", "######MNNNM######", "##CDGGLLLLLGGDC##", "##CDHHOOOOOHHDC##", "######PPPPP######", "######QQQQQ######", "######QQQQQ######", "######QQQQQ######", "######PPPPP######", "###EEEJEJEJEEE###")
                    .aisle("BBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBB", "##CDFFFFFFFFFDC##", "##CDGGGGGGGGGDC##", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "##CDGIGGIGGIGDC##", "##CDHIHHIHHIHDC##", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "#####I##I##I#####", "###EEJEEJEEJEE###")
                    .aisle("#BBBBBBBBBBBBBBB#", "#BBBBBBBBBBBBBBB#", "##CCDFFFFFFFDCC##", "##CCDGGGGGGGDCC##", "###C#########C###", "###C#########C###", "###C#########C###", "###C#########C###", "###C#########C###", "##CCDGGGGGGGDCC##", "##CCDHHHHHHHDCC##", "#################", "#################", "#################", "#################", "#################", "####EEEEEEEEE####")
                    .aisle("#BBBBBBBBBBBBBBB#", "#BBBBBBBBBBBBBBB#", "###CCDDDDDDDCC###", "###CCDDDDDDDCC###", "####C#######C####", "####C#######C####", "####C#######C####", "####C#######C####", "####C#######C####", "###CCDDDDDDDCC###", "###CCDDDDDDDCC###", "#################", "#################", "#################", "#################", "#################", "#####EEEEEEE#####")
                    .aisle("##BBBBBBBBBBBBB##", "##BBBBBBBBBBBBB##", "####CCCCCCCCC####", "####CCCCCCCCC####", "#################", "#################", "#################", "#################", "#################", "####CCCCCCCCC####", "####CCCCCCCCC####", "#################", "#################", "#################", "#################", "#################", "#################")
                    .aisle("###BBBBBBBBBBB###", "###BBBBBBBBBBB###", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################")
                    .aisle("A####BBBBBBB####A", "#####BBB@BBB#####", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "#################", "################A")
                    .where("A", Predicates.any())
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_HIGH_TEMPERATURE_SMELTING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.frames(Naquadria))
                    .where("D", Predicates.blocks(CASING_TUNGSTENCU_DIAMOND_PLATING.get()))
                    .where("E", Predicates.blocks(CASING_NAQUADAH_ALLOY_BLOCK.get()))
                    .where("F", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("G", Predicates.blocks(PLASMA_COOLED_CORE.get()))
                    .where("H", Predicates.blocks(HEAT_VENT.get()))
                    .where("I", Predicates.blocks(HERMETIC_CASING_ZPM.get()))
                    .where("J", Predicates.blocks(CASING_NEUTRONIUM_ALLOY_BLOCK.get()))
                    .where("K", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, NaquadahEnriched).get()))
                    .where("L", Predicates.blocks(FUSION_COIL.get()))
                    .where("M", Predicates.blocks(CASING_ULTIMATE_ENGINE_INTAKE.get()))
                    .where("N", Predicates.heatingCoils())
                    .where("O", Predicates.blocks(CASING_NAQUADAH_GEARBOX.get()))
                    .where("P", Predicates.blocks(CASING_ANTIFREEZE_HEATPROOF_MACHINE.get()))
                    .where("Q", Predicates.blocks(WIDESPEEDINGPIPE.get()))
                    .where("Q", Predicates.blocks(WIDESPEEDINGPIPE.get()))

                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/high_temperature_smelting_casing"), CTNHCore.id("block/overlay/super_ebf"))
            .register();
//    public final static MultiblockMachineDefinition UNIVERSE_SINOPE = REGISTRATE.multiblock("universe_sinope", holder -> new Arc_Reactor(holder, 10))
//            .rotationState(RotationState.NON_Y_AXIS)
//            .recipeType(CTNHRecipeTypes.ARC_REACTOR)
//            .recipeModifiers(PlasmaAlloyBlast::recipeModifier, GTRecipeModifiers::ebfOverclock)
//            .tooltips(Component.translatable("ctnh.u_sinope.story.1"),
//                    Component.translatable("ctnh.u_sinope.story.2"),
//                    Component.translatable("ctnh.u_sinope.story.3"),
//                    Component.translatable("ctnh.u_sinope.1"),
//                    Component.translatable("ctnh.u_sinope.2"),
//                    Component.translatable("ctnh.u_sinope.3"),
//                    Component.translatable("ctnh.u_sinope.4"),
//                    Component.translatable("ctnh.u_sinope.5"),
//                    Component.translatable("ctnh.u_sinope.6"),
//                    Component.translatable("ctnh.u_sinope.7"),
//                    Component.translatable("ctnh.u_sinope.8")
//            )
//            .pattern(definition -> FactoryBlockPattern.start()
//                    .aisle("A                   BB@BB                     ", "CCC               BB     BB               CCC ", "CDC           BBBB         BBBB           CDC ", "CCC           BDB           BDB           CCC ", "              BBB           BBB               ", "             B                 B              ", "          BBB                   BBB           ", "          BDB    EEEEEEEEEEE    BDB           ", "          BBB   EEEEEEEEEEEEE   BBB           ", "         B     EEEEEEEEEEEEEEE     B          ", "        B     EEEEEEEEEEEEEEEEE     B         ", "     BBB     EEEEEEEEEEEEEEEEEEE     BBB      ", "     BDB    EEEEEEEEEEEEEEEEEEEEE    BDB      ", "     BBB   EEEEEEEEEEEEEEEEEEEEEEE   BBB      ", "    B     EEEEEEEEEEEEEEEEEEEEEEEEE     B     ", " BBB     EEEEEEEEEEEEEEEEEEEEEEEEEEE     BBB  ", " BDB    EEEEEEEEEEEEEEEEEEEEEEEEEEEEE    BDB  ", " BBB   EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE   BBB  ", " B    EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE    B  ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      F", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", " B    EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE    B  ", " BBB   EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE   BBB  ", " BDB    EEEEEEEEEEEEEEEEEEEEEEEEEEEEE    BDB  ", " BBB     EEEEEEEEEEEEEEEEEEEEEEEEEEE     BBB  ", "    B     EEEEEEEEEEEEEEEEEEEEEEEEE     B     ", "     BBB   EEEEEEEEEEEEEEEEEEEEEEE   BBB      ", "     BDB    EEEEEEEEEEEEEEEEEEEEE    BDB      ", "     BBB     EEEEEEEEEEEEEEEEEEE     BBB      ", "        B     EEEEEEEEEEEEEEEEE     B         ", "         B     EEEEEEEEEEEEEEE     B          ", "          BBB   EEEEEEEEEEEEE   BBB           ", "          BDB    EEEEEEEEEEE    BDB           ", "          BBB                   BBB           ", "             B                 B              ", "              BBB           BBB               ", "CCC           BDB           BDB           CCC ", "CDC           BBBB         BBBB           CDC ", "CCC               BB     BB               CCC ", "                    BBFBB                     ")
//                    .aisle("                    FFFFF                     ", "BBB               FF     FF               BBB ", "BDB           BBBF         FBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             F                 F              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB    GGGGGGGGGGG    BBB           ", "         F      GGGGGGGGGGGGG      F          ", "        F      GGGGGGGGGGGGGGG      F         ", "     BBB      GGGGGGGGGGGGGGGGG      BBB      ", "     BDB     GGGGGGGGGGGGGGGGGGG     BDB      ", "     BBB    GGGGGGGGGGGGGGGGGGGGG    BBB      ", "    F      GGGGGGGGGGGGGGGGGGGGGGG      F     ", " BBB      GGGGGGGGGGGGGGGGGGGGGGGGG      BBB  ", " BDB     GGGGGGGGGGGGGGGGGGGGGGGGGGG     BDB  ", " BBB    GGGGGGGGGGGGGGGGGGGGGGGGGGGGG    BBB  ", " F     GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG     F  ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", " F     GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG     F  ", " BBB    GGGGGGGGGGGGGGGGGGGGGGGGGGGGG    BBB  ", " BDB     GGGGGGGGGGGGGGGGGGGGGGGGGGG     BDB  ", " BBB      GGGGGGGGGGGGGGGGGGGGGGGGG      BBB  ", "    F      GGGGGGGGGGGGGGGGGGGGGGG      F     ", "     BBB    GGGGGGGGGGGGGGGGGGGGG    BBB      ", "     BDB     GGGGGGGGGGGGGGGGGGG     BDB      ", "     BBB      GGGGGGGGGGGGGGGGG      BBB      ", "        F      GGGGGGGGGGGGGGG      F         ", "         F      GGGGGGGGGGGGG      F          ", "          BBB    GGGGGGGGGGG    BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             F                 F              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBF         FBBB           BDB ", "BBB               FF     FF               BBB ", "                    FFFFF                     ")
//                    .aisle("                    BBFBB                     ", "BBB               BB     BB               BBB ", "BDB           BBBB         BBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             B                 B              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         B       HHHHHHHHHHH       B          ", "        B       HHHHHHHHHHHHH       B         ", "     BBB       HHHHHHHHHHHHHHH       BBB      ", "     BDB      HHHHHHHHHHHHHHHHH      BDB      ", "     BBB     HHHHHHHHHHHHHHHHHHH     BBB      ", "    B       HHHHHHHHHHHHHHHHHHHHH       B     ", " BBB       HHHHHHHHHHHHHHHHHHHHHHH       BBB  ", " BDB      HHHHHHHHHHHHHHHHHHHHHHHHH      BDB  ", " BBB     HHHHHHHHHHHHHHHHHHHHHHHHHHH     BBB  ", " B      HHHHHHHHHHHHHHHHHHHHHHHHHHHHH      B  ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        F", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", " B      HHHHHHHHHHHHHHHHHHHHHHHHHHHHH      B  ", " BBB     HHHHHHHHHHHHHHHHHHHHHHHHHHH     BBB  ", " BDB      HHHHHHHHHHHHHHHHHHHHHHHHH      BDB  ", " BBB       HHHHHHHHHHHHHHHHHHHHHHH       BBB  ", "    B       HHHHHHHHHHHHHHHHHHHHH       B     ", "     BBB     HHHHHHHHHHHHHHHHHHH     BBB      ", "     BDB      HHHHHHHHHHHHHHHHH      BDB      ", "     BBB       HHHHHHHHHHHHHHH       BBB      ", "        B       HHHHHHHHHHHHH       B         ", "         B       HHHHHHHHHHH       B          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             B                 B              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBB         BBBB           BDB ", "BBB               BB     BB               BBB ", "                    BBFBB                     ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                 IIIIIIIIIII                  ", "     BBB        IIIIIIIIIIIII        BBB      ", "     BDB       IIIIIIIIIIIIIII       BDB      ", "     BBB      IIIIIIIIIIIIIIIII      BBB      ", "             IIIIIIIIIIIIIIIIIII              ", " BBB        IIIIIIIIIIIIIIIIIIIII        BBB  ", " BDB       IIIIIIIIIIIIIIIIIIIIIII       BDB  ", " BBB      IIIIIIIIIIIIIIIIIIIIIIIII      BBB  ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", " BBB      IIIIIIIIIIIIIIIIIIIIIIIII      BBB  ", " BDB       IIIIIIIIIIIIIIIIIIIIIII       BDB  ", " BBB        IIIIIIIIIIIIIIIIIIIII        BBB  ", "             IIIIIIIIIIIIIIIIIII              ", "     BBB      IIIIIIIIIIIIIIIII      BBB      ", "     BDB       IIIIIIIIIIIIIII       BDB      ", "     BBB        IIIIIIIIIIIII        BBB      ", "                 IIIIIIIIIII                  ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB         AAAAAAAAAAA         BBB      ", "     BDB        AAAAAAAAAAAAA        BDB      ", "     BBB       AAAAAAAAAAAAAAA       BBB      ", "              AAAAAAAAAAAAAAAAA               ", " BBB         AAAAAAAAAAAAAAAAAAA         BBB  ", " BDB        AAAAAAAAAAAAAAAAAAAAA        BDB  ", " BBB       AAAAAAAAAAAAAAAAAAAAAAA       BBB  ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", " BBB       AAAAAAAAAAAAAAAAAAAAAAA       BBB  ", " BDB        AAAAAAAAAAAAAAAAAAAAA        BDB  ", " BBB         AAAAAAAAAAAAAAAAAAA         BBB  ", "              AAAAAAAAAAAAAAAAA               ", "     BBB       AAAAAAAAAAAAAAA       BBB      ", "     BDB        AAAAAAAAAAAAA        BDB      ", "     BBB         AAAAAAAAAAA         BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB         JJJJJJJJJJJ         BDB      ", "     BBB        JJJJJJJJJJJJJ        BBB      ", "               JJJJJJJJJJJJJJJ                ", " BBB          JJJJJJJJJJJJJJJJJ          BBB  ", " BDB         JJJJJJJJJJJJJJJJJJJ         BDB  ", " BBB        JJJJJJJJJJJJJJJJJJJJJ        BBB  ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", " BBB        JJJJJJJJJJJJJJJJJJJJJ        BBB  ", " BDB         JJJJJJJJJJJJJJJJJJJ         BDB  ", " BBB          JJJJJJJJJJJJJJJJJ          BBB  ", "               JJJJJJJJJJJJJJJ                ", "     BBB        JJJJJJJJJJJJJ        BBB      ", "     BDB         JJJJJJJJJJJ         BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB         KKKKKKKKKKK         BBB      ", "                KKKKKKKKKKKKK                 ", " BBB           KKKKKKKKKKKKKKK           BBB  ", " BDB          KKKKKKKKKKKKKKKKK          BDB  ", " BBB         KKKKKKKKKKKKKKKKKKK         BBB  ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK          B  ", " BBB         KKKKKKKKKKKKKKKKKKK         BBB  ", " BDB          KKKKKKKKKKKKKKKKK          BDB  ", " BBB           KKKKKKKKKKKKKKK           BBB  ", "                KKKKKKKKKKKKK                 ", "     BBB         KKKKKKKKKKK         BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LLLLLLLLLLL                  ", " BBB            LLLLLLLLLLLLL            BBB  ", " BDB           LLLLLLLLLLLLLLL           BDB  ", " BBB          LLLLLLLLLLLLLLLLL          BBB  ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", " BBB          LLLLLLLLLLLLLLLLL          BBB  ", " BDB           LLLLLLLLLLLLLLL           BDB  ", " BBB            LLLLLLLLLLLLL            BBB  ", "                 LLLLLLLLLLL                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB     B     BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB     B     BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB     B     BBB           BDB ", "BBB           BDB    BOB    BDB           BBB ", "              BBB     B     BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB     B     BBB               ", "BBB           BDB    BOB    BDB           BBB ", "BDB           BBB     B     BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                   B                   BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB           BDB   BOAOB   BDB           BBB ", "              BBB    BOB    BBB               ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "                     CBC                      ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL           B  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                     CBC                      ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "              BBB    BOB    BBB               ", "BBB           BDB   BOAOB   BDB           BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB                   B                   BBB ", "                                              ")
//                    .aisle("                      B                       ", "BBB                  BOB                  BBB ", "BDB           BBB   BOAOB   BBB           BDB ", "BBB           BDB  BOAAAOB  BDB           BBB ", "              BBB   BOAOB   BBB               ", "                     BAB                      ", "          BBB        BAB        BBB           ", "          BDB        BAB        BDB           ", "          BBB        BAB        BBB           ", "                     BAB                      ", "                     BAB                      ", "     BBB             BAB             BBB      ", "     BDB             BAB             BDB      ", "     BBB             BAB             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", "OAAAAAAAAAAAALNNNNNNNNNNNNNNNNNLAAAAAAAAAAAAOB", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             BAB             BBB      ", "     BDB             BAB             BDB      ", "     BBB             BAB             BBB      ", "                     BAB                      ", "                     BAB                      ", "          BBB        BAB        BBB           ", "          BDB        BAB        BDB           ", "          BBB        BAB        BBB           ", "                     BAB                      ", "              BBB   BOAOB   BBB               ", "BBB           BDB  BOAAAOB  BDB           BBB ", "BDB           BBB   BOAOB   BBB           BDB ", "BBB                  BOB                  BBB ", "                      B                       ")
//                    .aisle("                                              ", "BBB                   B                   BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB           BDB   BOAOB   BDB           BBB ", "              BBB    BOB    BBB               ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "                     CBC                      ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                     CBC                      ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "              BBB    BOB    BBB               ", "BBB           BDB   BOAOB   BDB           BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB                   B                   BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB     B     BBB           BDB ", "BBB           BDB    BOB    BDB           BBB ", "              BBB     B     BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB     B     BBB               ", "BBB           BDB    BOB    BDB           BBB ", "BDB           BBB     B     BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                    LLLLL                     ", "BBB               LL     LL               BBB ", "BDB           BBBL         LBBB           BDB ", "BBB           BDB     B     BDB           BBB ", "              BBB           BBB               ", "             L                 L              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         L                         L          ", "        L                           L         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    L            LMMMMMMMMML            L     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " L           LNNNNNNNNNNNNNNNNNL           L  ", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "  B          MNNNNNNNNNNNNNNNNNM          B  L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", " L           LNNNNNNNNNNNNNNNNNL           L  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    L            LMMMMMMMMML            L     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        L                           L         ", "         L                         L          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             L                 L              ", "              BBB           BBB               ", "BBB           BDB     B     BDB           BBB ", "BDB           BBBL         LBBB           BDB ", "BBB               LL     LL               BBB ", "                    LLLLL                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    LLLLL                     ", "BBB               LL     LL               BBB ", "BDB           BBBL         LBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             L                 L              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         L                         L          ", "        L                           L         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    L            LMMMMMMMMML            L     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " L           LNNNNNNNNNNNNNNNNNL           L  ", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", " L           LNNNNNNNNNNNNNNNNNL           L  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    L            LMMMMMMMMML            L     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        L                           L         ", "         L                         L          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             L                 L              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBL         LBBB           BDB ", "BBB               LL     LL               BBB ", "                    LLLLL                     ")
//                    .aisle("                    PPPPP                     ", "BBB               PP     PP               BBB ", "BDB           BBBP         PBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             P                 P              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         P                         P          ", "        P                           P         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    P            LMMMMMMMMML            P     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " P           LNNNNNNNNNNNNNNNNNL           P  ", "P            MNNNNNNNNNNNNNNNNNM            P ", "P            MNNNNNNNNNNNNNNNNNM            P ", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "P            MNNNNNNNNNNNNNNNNNM            P ", "P            MNNNNNNNNNNNNNNNNNM            P ", " P           LNNNNNNNNNNNNNNNNNL           P  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    P            LMMMMMMMMML            P     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        P                           P         ", "         P                         P          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             P                 P              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBP         PBBB           BDB ", "BBB               PP     PP               BBB ", "                    PPPPP                     ")
//                    .aisle("                    PPPPP                     ", "BBB               PP     PP               BBB ", "BDB           BBBP         PBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             P                 P              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         P                         P          ", "        P                           P         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    P            LMMMMMMMMML            P     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " P           LNNNNNNNNNNNNNNNNNL           P  ", "P            MNNNNNNNNNNNNNNNNNM            P ", "P            MNNNNNNNNNNNNNNNNNM            P ", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "             MNNNNNNNNNNNNNNNNNM             P", "P            MNNNNNNNNNNNNNNNNNM            P ", "P            MNNNNNNNNNNNNNNNNNM            P ", " P           LNNNNNNNNNNNNNNNNNL           P  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    P            LMMMMMMMMML            P     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        P                           P         ", "         P                         P          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             P                 P              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBP         PBBB           BDB ", "BBB               PP     PP               BBB ", "                    PPPPP                     ")
//                    .aisle("                    LLLLL                     ", "BBB               LL     LL               BBB ", "BDB           BBBL         LBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             L                 L              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         L                         L          ", "        L                           L         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    L            LMMMMMMMMML            L     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " L           LNNNNNNNNNNNNNNNNNL           L  ", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", " L           LNNNNNNNNNNNNNNNNNL           L  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    L            LMMMMMMMMML            L     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        L                           L         ", "         L                         L          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             L                 L              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBL         LBBB           BDB ", "BBB               LL     LL               BBB ", "                    LLLLL                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    NNNNN                     ", "BBB               NN     NN               BBB ", "BDB           BBBN         NBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             N                 N              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         N                         N          ", "        N                           N         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    N            LMMMMMMMMML            N     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " N           LNNNNNNNNNNNNNNNNNL           N  ", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "             MNNNNNNNNNNNNNNNNNM             N", "N            MNNNNNNNNNNNNNNNNNM            N ", "N            MNNNNNNNNNNNNNNNNNM            N ", " N           LNNNNNNNNNNNNNNNNNL           N  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    N            LMMMMMMMMML            N     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        N                           N         ", "         N                         N          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             N                 N              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBN         NBBB           BDB ", "BBB               NN     NN               BBB ", "                    NNNNN                     ")
//                    .aisle("                    LLLLL                     ", "BBB               LL     LL               BBB ", "BDB           BBBL         LBBB           BDB ", "BBB           BDB     B     BDB           BBB ", "              BBB           BBB               ", "             L                 L              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         L                         L          ", "        L                           L         ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "    L            LMMMMMMMMML            L     ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " L           LNNNNNNNNNNNNNNNNNL           L  ", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "  B          MNNNNNNNNNNNNNNNNNM          B  L", "             MNNNNNNNNNNNNNNNNNM             L", "             MNNNNNNNNNNNNNNNNNM             L", "L            MNNNNNNNNNNNNNNNNNM            L ", "L            MNNNNNNNNNNNNNNNNNM            L ", " L           LNNNNNNNNNNNNNNNNNL           L  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "    L            LMMMMMMMMML            L     ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "        L                           L         ", "         L                         L          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             L                 L              ", "              BBB           BBB               ", "BBB           BDB     B     BDB           BBB ", "BDB           BBBL         LBBB           BDB ", "BBB               LL     LL               BBB ", "                    LLLLL                     ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB     B     BBB           BDB ", "BBB           BDB    BOB    BDB           BBB ", "              BBB     B     BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB     B     BBB               ", "BBB           BDB    BOB    BDB           BBB ", "BDB           BBB     B     BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                   B                   BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB           BDB   BOAOB   BDB           BBB ", "              BBB    BOB    BBB               ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "                     CBC                      ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                     CBC                      ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "              BBB    BOB    BBB               ", "BBB           BDB   BOAOB   BDB           BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB                   B                   BBB ", "                                              ")
//                    .aisle("                      B                       ", "BBB                  BOB                  BBB ", "BDB           BBB   BOAOB   BBB           BDB ", "BBB           BDB  BOAAAOB  BDB           BBB ", "              BBB   BOAOB   BBB               ", "                     BAB                      ", "          BBB        BAB        BBB           ", "          BDB        BAB        BDB           ", "          BBB        BAB        BBB           ", "                     BAB                      ", "                     BAB                      ", "     BBB             BAB             BBB      ", "     BDB             BAB             BDB      ", "     BBB             BAB             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", "OAAAAAAAAAAAALNNNNNNNNNNNNNNNNNLAAAAAAAAAAAAOB", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             BAB             BBB      ", "     BDB             BAB             BDB      ", "     BBB             BAB             BBB      ", "                     BAB                      ", "                     BAB                      ", "          BBB        BAB        BBB           ", "          BDB        BAB        BDB           ", "          BBB        BAB        BBB           ", "                     BAB                      ", "              BBB   BOAOB   BBB               ", "BBB           BDB  BOAAAOB  BDB           BBB ", "BDB           BBB   BOAOB   BBB           BDB ", "BBB                  BOB                  BBB ", "                      B                       ")
//                    .aisle("                                              ", "BBB                   B                   BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB           BDB   BOAOB   BDB           BBB ", "              BBB    BOB    BBB               ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "                     CBC                      ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                 LMMMLLLMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "BOAOBBBBBBBBBLNNNNNNNNNNNNNNNNNLBBBBBBBBBOAOB ", " BOBCCCCCCCCCLNNNNNNNNNNNNNNNNNLCCCCCCCCCBOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMLLLMMML                  ", "     BBB             CBC             BBB      ", "     BDB             CBC             BDB      ", "     BBB             CBC             BBB      ", "                     CBC                      ", "                     CBC                      ", "          BBB        CBC        BBB           ", "          BDB        CBC        BDB           ", "          BBB        CBC        BBB           ", "                     CBC                      ", "              BBB    BOB    BBB               ", "BBB           BDB   BOAOB   BDB           BBB ", "BDB           BBB    BOB    BBB           BDB ", "BBB                   B                   BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB     B     BBB           BDB ", "BBB           BDB    BOB    BDB           BBB ", "              BBB     B     BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", " BOB         MNNNNNNNNNNNNNNNNNM         BOB  ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB     B     BBB               ", "BBB           BDB    BOB    BDB           BBB ", "BDB           BBB     B     BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB     B     BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LMMMMMMMMML                  ", " BBB            LNNNNNNNNNNNL            BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", "             LNNNNNNNNNNNNNNNNNL              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "  B          MNNNNNNNNNNNNNNNNNM          B   ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             MNNNNNNNNNNNNNNNNNM              ", "             LNNNNNNNNNNNNNNNNNL              ", " BBB          LNNNNNNNNNNNNNNNL          BBB  ", " BDB           LNNNNNNNNNNNNNL           BDB  ", " BBB            LNNNNNNNNNNNL            BBB  ", "                 LMMMMMMMMML                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB     B     BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                 LLLLLLLLLLL                  ", " BBB            LLLLLLLLLLLLL            BBB  ", " BDB           LLLLLLLLLLLLLLL           BDB  ", " BBB          LLLLLLLLLLLLLLLLL          BBB  ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", "             LLLLLLLLLLLLLLLLLLL              ", " BBB          LLLLLLLLLLLLLLLLL          BBB  ", " BDB           LLLLLLLLLLLLLLL           BDB  ", " BBB            LLLLLLLLLLLLL            BBB  ", "                 LLLLLLLLLLL                  ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB                             BDB      ", "     BBB         KKKKKKKKKKK         BBB      ", "                KKKKKKKKKKKKK                 ", " BBB           KKKKKKKKKKKKKKK           BBB  ", " BDB          KKKKKKKKKKKKKKKKK          BDB  ", " BBB         KKKKKKKKKKKKKKKKKKK         BBB  ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", "            KKKKKKKKKKKKKKKKKKKKK             ", " BBB         KKKKKKKKKKKKKKKKKKK         BBB  ", " BDB          KKKKKKKKKKKKKKKKK          BDB  ", " BBB           KKKKKKKKKKKKKKK           BBB  ", "                KKKKKKKKKKKKK                 ", "     BBB         KKKKKKKKKKK         BBB      ", "     BDB                             BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB                             BBB      ", "     BDB         JJJJJJJJJJJ         BDB      ", "     BBB        JJJJJJJJJJJJJ        BBB      ", "               JJJJJJJJJJJJJJJ                ", " BBB          JJJJJJJJJJJJJJJJJ          BBB  ", " BDB         JJJJJJJJJJJJJJJJJJJ         BDB  ", " BBB        JJJJJJJJJJJJJJJJJJJJJ        BBB  ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", "           JJJJJJJJJJJJJJJJJJJJJJJ            ", " BBB        JJJJJJJJJJJJJJJJJJJJJ        BBB  ", " BDB         JJJJJJJJJJJJJJJJJJJ         BDB  ", " BBB          JJJJJJJJJJJJJJJJJ          BBB  ", "               JJJJJJJJJJJJJJJ                ", "     BBB        JJJJJJJJJJJJJ        BBB      ", "     BDB         JJJJJJJJJJJ         BDB      ", "     BBB                             BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                                              ", "     BBB         AAAAAAAAAAA         BBB      ", "     BDB        AAAAAAAAAAAAA        BDB      ", "     BBB       AAAAAAAAAAAAAAA       BBB      ", "              AAAAAAAAAAAAAAAAA               ", " BBB         AAAAAAAAAAAAAAAAAAA         BBB  ", " BDB        AAAAAAAAAAAAAAAAAAAAA        BDB  ", " BBB       AAAAAAAAAAAAAAAAAAAAAAA       BBB  ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", "          AAAAAAAAAAAAAAAAAAAAAAAAA           ", " BBB       AAAAAAAAAAAAAAAAAAAAAAA       BBB  ", " BDB        AAAAAAAAAAAAAAAAAAAAA        BDB  ", " BBB         AAAAAAAAAAAAAAAAAAA         BBB  ", "              AAAAAAAAAAAAAAAAA               ", "     BBB       AAAAAAAAAAAAAAA       BBB      ", "     BDB        AAAAAAAAAAAAA        BDB      ", "     BBB         AAAAAAAAAAA         BBB      ", "                                              ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                                              ", "BBB                                       BBB ", "BDB           BBB           BBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "                 IIIIIIIIIII                  ", "     BBB        IIIIIIIIIIIII        BBB      ", "     BDB       IIIIIIIIIIIIIII       BDB      ", "     BBB      IIIIIIIIIIIIIIIII      BBB      ", "             IIIIIIIIIIIIIIIIIII              ", " BBB        IIIIIIIIIIIIIIIIIIIII        BBB  ", " BDB       IIIIIIIIIIIIIIIIIIIIIII       BDB  ", " BBB      IIIIIIIIIIIIIIIIIIIIIIIII      BBB  ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", "         IIIIIIIIIIIIIIIIIIIIIIIIIII          ", " BBB      IIIIIIIIIIIIIIIIIIIIIIIII      BBB  ", " BDB       IIIIIIIIIIIIIIIIIIIIIII       BDB  ", " BBB        IIIIIIIIIIIIIIIIIIIII        BBB  ", "             IIIIIIIIIIIIIIIIIII              ", "     BBB      IIIIIIIIIIIIIIIII      BBB      ", "     BDB       IIIIIIIIIIIIIII       BDB      ", "     BBB        IIIIIIIIIIIII        BBB      ", "                 IIIIIIIIIII                  ", "                                              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "                                              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBB           BBB           BDB ", "BBB                                       BBB ", "                                              ")
//                    .aisle("                    BBFBB                     ", "BBB               BB     BB               BBB ", "BDB           BBBB         BBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             B                 B              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "         B       HHHHHHHHHHH       B          ", "        B       HHHHHHHHHHHHH       B         ", "     BBB       HHHHHHHHHHHHHHH       BBB      ", "     BDB      HHHHHHHHHHHHHHHHH      BDB      ", "     BBB     HHHHHHHHHHHHHHHHHHH     BBB      ", "    B       HHHHHHHHHHHHHHHHHHHHH       B     ", " BBB       HHHHHHHHHHHHHHHHHHHHHHH       BBB  ", " BDB      HHHHHHHHHHHHHHHHHHHHHHHHH      BDB  ", " BBB     HHHHHHHHHHHHHHHHHHHHHHHHHHH     BBB  ", " B      HHHHHHHHHHHHHHHHHHHHHHHHHHHHH      B  ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        F", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "        HHHHHHHHHHHHHHHHHHHHHHHHHHHHH        B", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", "B       HHHHHHHHHHHHHHHHHHHHHHHHHHHHH       B ", " B      HHHHHHHHHHHHHHHHHHHHHHHHHHHHH      B  ", " BBB     HHHHHHHHHHHHHHHHHHHHHHHHHHH     BBB  ", " BDB      HHHHHHHHHHHHHHHHHHHHHHHHH      BDB  ", " BBB       HHHHHHHHHHHHHHHHHHHHHHH       BBB  ", "    B       HHHHHHHHHHHHHHHHHHHHH       B     ", "     BBB     HHHHHHHHHHHHHHHHHHH     BBB      ", "     BDB      HHHHHHHHHHHHHHHHH      BDB      ", "     BBB       HHHHHHHHHHHHHHH       BBB      ", "        B       HHHHHHHHHHHHH       B         ", "         B       HHHHHHHHHHH       B          ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             B                 B              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBB         BBBB           BDB ", "BBB               BB     BB               BBB ", "                    BBFBB                     ")
//                    .aisle("                    FFFFF                     ", "BBB               FF     FF               BBB ", "BDB           BBBF         FBBB           BDB ", "BBB           BDB           BDB           BBB ", "              BBB           BBB               ", "             F                 F              ", "          BBB                   BBB           ", "          BDB                   BDB           ", "          BBB    GGGGGGGGGGG    BBB           ", "         F      GGGGGGGGGGGGG      F          ", "        F      GGGGGGGGGGGGGGG      F         ", "     BBB      GGGGGGGGGGGGGGGGG      BBB      ", "     BDB     GGGGGGGGGGGGGGGGGGG     BDB      ", "     BBB    GGGGGGGGGGGGGGGGGGGGG    BBB      ", "    F      GGGGGGGGGGGGGGGGGGGGGGG      F     ", " BBB      GGGGGGGGGGGGGGGGGGGGGGGGG      BBB  ", " BDB     GGGGGGGGGGGGGGGGGGGGGGGGGGG     BDB  ", " BBB    GGGGGGGGGGGGGGGGGGGGGGGGGGGGG    BBB  ", " F     GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG     F  ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "       GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG       F", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", "F      GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG      F ", " F     GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG     F  ", " BBB    GGGGGGGGGGGGGGGGGGGGGGGGGGGGG    BBB  ", " BDB     GGGGGGGGGGGGGGGGGGGGGGGGGGG     BDB  ", " BBB      GGGGGGGGGGGGGGGGGGGGGGGGG      BBB  ", "    F      GGGGGGGGGGGGGGGGGGGGGGG      F     ", "     BBB    GGGGGGGGGGGGGGGGGGGGG    BBB      ", "     BDB     GGGGGGGGGGGGGGGGGGG     BDB      ", "     BBB      GGGGGGGGGGGGGGGGG      BBB      ", "        F      GGGGGGGGGGGGGGG      F         ", "         F      GGGGGGGGGGGGG      F          ", "          BBB    GGGGGGGGGGG    BBB           ", "          BDB                   BDB           ", "          BBB                   BBB           ", "             F                 F              ", "              BBB           BBB               ", "BBB           BDB           BDB           BBB ", "BDB           BBBF         FBBB           BDB ", "BBB               FF     FF               BBB ", "                    FFFFF                     ")
//                    .aisle("                    BBFBB                     ", "CCC               BB     BB               CCC ", "CDC           BBBB         BBBB           CDC ", "CCC           BDB           BDB           CCC ", "              BBB           BBB               ", "             B                 B              ", "          BBB                   BBB           ", "          BDB    EEEEEEEEEEE    BDB           ", "          BBB   EEEEEEEEEEEEE   BBB           ", "         B     EEEEEEEEEEEEEEE     B          ", "        B     EEEEEEEEEEEEEEEEE     B         ", "     BBB     EEEEEEEEEEEEEEEEEEE     BBB      ", "     BDB    EEEEEEEEEEEEEEEEEEEEE    BDB      ", "     BBB   EEEEEEEEEEEEEEEEEEEEEEE   BBB      ", "    B     EEEEEEEEEEEEEEEEEEEEEEEEE     B     ", " BBB     EEEEEEEEEEEEEEEEEEEEEEEEEEE     BBB  ", " BDB    EEEEEEEEEEEEEEEEEEEEEEEEEEEEE    BDB  ", " BBB   EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE   BBB  ", " B    EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE    B  ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      F", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "      EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE      B", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", "B     EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     B ", " B    EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE    B  ", " BBB   EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE   BBB  ", " BDB    EEEEEEEEEEEEEEEEEEEEEEEEEEEEE    BDB  ", " BBB     EEEEEEEEEEEEEEEEEEEEEEEEEEE     BBB  ", "    B     EEEEEEEEEEEEEEEEEEEEEEEEE     B     ", "     BBB   EEEEEEEEEEEEEEEEEEEEEEE   BBB      ", "     BDB    EEEEEEEEEEEEEEEEEEEEE    BDB      ", "     BBB     EEEEEEEEEEEEEEEEEEE     BBB      ", "        B     EEEEEEEEEEEEEEEEE     B         ", "         B     EEEEEEEEEEEEEEE     B          ", "          BBB   EEEEEEEEEEEEE   BBB           ", "          BDB    EEEEEEEEEEE    BDB           ", "          BBB                   BBB           ", "             B                 B              ", "              BBB           BBB               ", "CCC           BDB           BDB           CCC ", "CDC           BBBB         BBBB           CDC ", "CCC               BB     BB               CCC ", "                    BBFBB                    A")
//
//                    .where("A", Predicates.any())
//                    .where("B", Predicates.frames(CTNHMaterials.Infinity))
//                    .where("C", Predicates.blocks(CASING_NAQUADAH_GEARBOX.get()))
//                    .where("D", Predicates.blocks(ADVANCED_COMPUTER_CASING.get()))
//                    .where("E", Predicates.blocks(ANNIHILATE_CORE_MKI.get()))
//                    .where("F", Predicates.blocks(MACHINE_CASING_UHV.get()))
//                    .where("G", Predicates.blocks(FUSION_CASING_MK3.get()))
//                    .where("H", Predicates.blocks(PLASMA_COOLED_CORE.get()))
//                    .where("I", Predicates.blocks(CASING_NAQUADAH_GEARBOX.get()))
//                    .where("J", Predicates.blocks(MACHINE_CASING_UHV.get()))
//                    .where("K", Predicates.blocks(CASING_ANTIFREEZE_HEATPROOF_MACHINE.get()))
//                    .where("L", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block, CTNHMaterials.Infinity).get()))
//                    .where("M", Predicates.blocks(FUSION_GLASS.get()))
//                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
//                    .where("N", heatingCoils())
//                    .where("O", Predicates.blocks(HIGH_POWER_CASING.get()))
//                    .where("P", Predicates.blocks(COMPUTER_CASING.get())
//                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
//
//                    .build())
//            .workableCasingModel(GTCEu.id("block/casings/gcym/nonconducting_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
//            .register();
//    public final static MultiblockMachineDefinition HYBRID_POWER_MIXER = REGISTRATE.multiblock("hybrid_power_mixer", Hybrid_Power_Mixer::new)
//            .rotationState(RotationState.NON_Y_AXIS)
//            .recipeType(GTRecipeTypes.MIXER_RECIPES)
//            .recipeModifiers(Hybrid_Power_Mixer::recipeModifier, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
//            .tooltips(Component.translatable("ctnh.multiblock.hybrid_mixer.tooltip.0"),
//                    Component.translatable("ctnh.multiblock.hybrid_mixer.tooltip.1"),
//                    Component.translatable("ctnh.multiblock.hybrid_mixer.tooltip.2"),
//                    Component.translatable("ctnh.multiblock.hybrid_mixer.tooltip.3"),
//                    Component.translatable("ctnh.multiblock.hybrid_mixer.tooltip.4")
//            )
//            .pattern(definition -> FactoryBlockPattern.start()
//                    .aisle("##BBB##", "##BBB##", "##BBB##", "###D###", "###D###", "###B###")
//                    .aisle("##BEB##", "##FGF##", "##FHF##", "##FHF##", "##FGF##", "##BIB##")
//                    .aisle("BBJEJBB", "BFJGJFB", "BFJHJFB", "#FJ#JF#", "#FJGJF#", "#BJIJB#")
//                    .aisle("BEEEEEB", "GGGGGGG", "BHHHHHB", "DH#H#HD", "DGGGGGD", "BIIIIIB")
//                    .aisle("BBJEJBB", "BFJGJFB", "BFJHJFB", "#FJ#JF#", "#FJGJF#", "#BJIJB#")
//                    .aisle("##BEB##", "##FGF##", "##FHF##", "##FHF##", "##FGF##", "##BIB##")
//                    .aisle("##BBB##", "##BCB##", "##BBB##", "###D###", "###D###", "###B###")
//                    .where("#", Predicates.any())
//                    .where("B", Predicates.blocks(CASING_OSMIRIDIUM.get())
//                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
//                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC)))
//
//                    .where("C", Predicates.controller(Predicates.blocks(definition.get())))
//                    .where("D", Predicates.frames(Titanium))
//                    .where("E", Predicates.blocks(ZENITH_CASING_GEARBOX.get()))
//                    .where("F", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
//                    .where("G", Predicates.blocks(COIL_ULTRA_MANA.get()))
//                    .where("H", Predicates.blocks(ELEMENTIUM_PIPE_CASING.get()))
//                    .where("I", Predicates.blocks(ELEMENTIUM_CASING.get()))
//                    .where("J", Predicates.blocks(HERMETIC_CASING_LuV.get()))
//                    .build())
//            .workableCasingModel(CTNHCore.id("block/casings/osmiridium_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
//            .register();
//        public final static MultiblockMachineDefinition COMPONENT_ASSEMBLY_LINE_CT = REGISTRATE.multiblock("component_assembly_line_ct", Hybrid_Power_Mixer::new)
//            .rotationState(RotationState.NON_Y_AXIS)
//                .recipeTypes(CTNHRecipeTypes.PVB_RECIPE, CTNHRecipeTypes.CHEMICAL_VAPOR_DEPOSITION)
//            .recipeModifiers(Hybrid_Power_Mixer::recipeModifier, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
//            .tooltips(Component.translatable("ctnh.mutiblock.hybrid_mixer.tooltip.0"),
//                    Component.translatable("ctnh.mutiblock.hybrid_mixer.tooltip.1"),
//                    Component.translatable("ctnh.mutiblock.hybrid_mixer.tooltip.2"),
//                    Component.translatable("ctnh.mutiblock.hybrid_mixer.tooltip.3"),
//                    Component.translatable("ctnh.mutiblock.hybrid_mixer.tooltip.4")
//            )
//            .pattern(definition -> FactoryBlockPattern.start()
//                    .aisle("ABBBBBBBBBA", "ACCCCCCCCCA", "@AAAAAAAAAA", "A#########A")
//                    .aisle("ADDDDDDDDDA", "B#########B", "AEEEEEEEEEA", "AAAAAAAAAAA")
//                    .aisle("ABBBBBBBBBA", "ACCCCCCCCCA", "AAAAAAAAAAA", "A#########A")
//                    .where("A", Predicates.blocks(AllBlocks.RAILWAY_CASING.get())
//                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
//                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC)))
//                    .where("B", Predicates.blocks(CASING_OSMIRIDIUM.get()))
//                    .where("C", Predicates.blocks(FUSION_GLASS.get()))
//                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
//                    .where("#", Predicates.any())
//                    .where("D", Predicates.blocks(AllBlocks.RAILWAY_CASING.get()))
//                    .where("E", Predicates.blocks(AllBlocks.DEPLOYER.get()))
//                    .build())
//
//            .workableCasingModel(CTPP.id("block/create/railway_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
//            .register();
    public final static MultiblockMachineDefinition COMBINED_VAPOR_DEPOSITION_FACILITY = REGISTRATE.multiblock("combined_vapor_deposition_facility", WorkableElectricMultiblockMachine::new)
            .cnLangValue("集成沉积工厂")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(CTNHRecipeTypes.PVB_RECIPE, CTNHRecipeTypes.CHEMICAL_VAPOR_DEPOSITION)
            .recipeModifiers(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(CTNHCommonTooltips.PERFECT_OVERCLOCK
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "BACAB", "BCCCB", "BACAB", "ABBBA")
                    .aisle("BDDDB", "AEFEA", "AGHGA", "AEFEA", "BDDDB")
                    .aisle("BDDDB", "AFFFA", "AIFIA", "AFFFA", "BDDDB")
                    .aisle("BDDDB", "AEFEA", "AGHGA", "AEFEA", "BDDDB")
                    .aisle("ABBBA", "BACAB", "BC@CB", "BACAB", "ABBBA")
                    .where("A", Predicates.blocks(MACHINE_CASING_LuV.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1).setPreviewCount(1))
                            .or(Predicates.autoAbilities(true, false, false))
                    )
                    .where("B", Predicates.blocks(HERMETIC_CASING_LuV.get()))
                    .where("C", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("E", Predicates.blocks(COIL_HSSG.get()))
                    .where("F", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("G", Predicates.blocks(CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("H", Predicates.blocks(MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                    .where("I", Predicates.blocks(MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                    .build())

            .workableCasingModel(GTCEu.id("block/casings/hpca/high_power_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition LaserSorder = REGISTRATE.multiblock("lasersorder", LaserSorter::new)
            .cnLangValue("激光分配仪")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(CTNHRecipeTypes.LS_RECIPE,GTRecipeTypes.LASER_ENGRAVER_RECIPES)
            .recipeModifiers(LaserSorter::recipeModifier, OC_NON_PERFECT)
            .tooltips(lasersorterTooltip0.translate(),
                    lasersorterTooltip1.translate(),
                    lasersorterTooltip2.translate(),
                    lasersorterTooltip3.translate(),
                    lasersorterTooltip4.translate(),
                    lasersorterTooltip5.translate(),
                    lasersorterTooltip6.translate(),
                    lasersorterTooltip7.translate(),
                    lasersorterTooltip8.translate(),
                    lasersorterTooltip9.translate(),
                    lasersorterTooltip10.translate(),
                    lasersorterTooltip11.translate()
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "AABBBAA", "AABBBAA", "AABBBAA", "AAAAAAA")
                    .aisle("ACCCCCA", "AC###CA", "AC###CA", "AC###CA", "ACCCCCA")
                    .aisle("ACCCCCA", "B#####B", "B#####B", "B#####B", "ACCDCCA")
                    .aisle("ACCCCCA", "B#####B", "B#####B", "B#####B", "ACDDDCA")
                    .aisle("ACCCCCA", "B#####B", "B#####B", "B#####B", "ACCDCCA")
                    .aisle("ACCCCCA", "AC###CA", "AC###CA", "AC###CA", "ACCCCCA")
                    .aisle("AAAAAAA", "AABBBAA", "AAB@BAA", "AABBBAA", "AAAAAAA")
                    .where("A", Predicates.blocks(COMPUTER_HEAT_VENT.get()))
                    .where("B", Predicates.blocks(HIGH_POWER_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(ADVANCED_COMPUTER_CASING.get()))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(FUSION_GLASS.get()))
                    .build())

            .workableCasingModel(GTCEu.id("block/casings/hpca/high_power_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition SPACEPHOTOVOLTAICBASESTATION = REGISTRATE.multiblock("space_photovoltai_cbase_station", SpacePhotovoltaicBaseStation::new)
            .cnLangValue("太空光伏基站")
            .allowExtendedFacing(false)
            .generator(true)
            .recipeTypes(CTNHRecipeTypes.PHOTOVOLTAIC_GENERATOR, CTNHRecipeTypes.PHOTOVOLTAIC_ASSEMBER)
            .recipeModifier(SpacePhotovoltaicBaseStation::recipeModifier,true)
            .tooltips(spacephotovoltaicbasestationTooltip0.translate(),
                    spacephotovoltaicbasestationTooltip2.translate(),
                    spacephotovoltaicbasestationTooltip3.translate(),
                    spacephotovoltaicbasestationTooltip4.translate(),
                    spacephotovoltaicbasestationTooltip5.translate(),
                    spacephotovoltaicbasestationTooltipEx.translate()

            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############BCDCCCDCB##############", "#############BCDCECDCB##############", "#############BCDCCCDCB##############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############CDDDDDC###############", "#############F#######F##############", "#############C###G###C##############", "#############F##H#H##F##############", "##############CDDDDDC###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############CCCCCCC###############", "#############F#######F##############", "############F#########F#############", "############C####G####C#############", "############F###H#H###F#############", "#############F#######F##############", "##############CCCCCCC###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############C#######C##############", "############F#########F#############", "###########F###########F############", "###########C#####G#####C############", "###########F####H#H####F############", "############F#########F#############", "#############C#######C##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############C#########C#############", "###########F###########F############", "##########F#############F###########", "##########C######G######C###########", "##########F#####H#H#####F###########", "###########F###########F############", "############C#########C#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############F#########F#############", "###########C###########C############", "##########F#############F###########", "#########F###############F##########", "#########C#######G#######C##########", "#########F######H#H######F##########", "##########F#############F###########", "###########C###########C############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############B#######B##############", "#############BFFFFFFFB##############", "#############F#######F##############", "############F#########F#############", "###########F###########F############", "##########C#############C###########", "#########F###############F##########", "########F#################F#########", "########C########G########C#########", "########F#######H#H#######F#########", "#########F###############F##########", "##########C#############C###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############F#########F#############", "###########F###########F############", "##########F#############F###########", "#########C###############C##########", "########F#################F#########", "#######F###################F########", "#######C#########G#########C########", "#######F########H#H########F########", "########F#################F#########", "#########C###############C##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############F#########F#############", "###########F###########F############", "##########F#############F###########", "#########F###############F##########", "########C#################C#########", "#######F###################F########", "######F#####################F#######", "######C##########G##########C#######", "######F#########H#H#########F#######", "#######F###################F########", "########C#################C#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############F#########F#############", "###########F###########F############", "##########F#############F###########", "#########F###############F##########", "########F#################F#########", "#######C###################C########", "######F#####################F#######", "#####F#######################F######", "#####C###########G###########C######", "#####F##########H#H##########F######", "######F#####################F#######", "#######C###################C########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "##############FFFFFFF###############", "#############F#######F##############", "############F#########F#############", "###########F###########F############", "##########F#############F###########", "#########F###############F##########", "########F#################F#########", "#######F###################F########", "######C#####################C#######", "#####F#######################F######", "####F#########################F#####", "####C############G############C#####", "####F###########H#H###########F#####", "#####F#######################F######", "######C#####################C#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFFFFFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "#############F###B###F##############", "############F####B####F#############", "###########F#####B#####F############", "##########F######B######F###########", "#########F#######B#######F##########", "########F########B########F#########", "#######F#########B#########F########", "######F##########B##########F#######", "#####C###########B###########C######", "####F############B############F#####", "###F#############B#############F####", "###C#############E#############C####", "###F############H#H############F####", "####F#########################F#####", "#####C#######################C######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############F#######F##############", "##############FFHEHFF###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("#############IJJJJJJJI##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "############F#########F#############", "###########F###########F############", "##########F#############F###########", "#########F###############F##########", "########F#################F#########", "#######F###################F########", "######F#####################F#######", "#####F#######################F######", "####C#########################C#####", "###F###########################F####", "##F#############################F###", "##C#############FKF#############C###", "##F############H###H############F###", "###F###########################F####", "####C#########################C#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############F#########F#############", "#############FEH###HEF##############", "################EEE#################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("############IJJJJJJJJJI#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "###########F###########F############", "##########F#############F###########", "#########F###############F##########", "########F#################F#########", "#######F###################F########", "######F#####################F#######", "#####F#######################F######", "####F#########################F#####", "###C###########################C####", "##F#############################F###", "#F###############################F##", "#C#############F#K#F#############C##", "#F############H#####H############F##", "##F#############################F###", "###C###########################C####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########F###########F############", "############FEH#####HEF#############", "###############E###E################", "################HHH#################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################")
                    .aisle("B####B#####IJJJJJJJJJJJI#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B####F#############F####B####B#", "B####B###F###############F###B####B#", "B####B##F#################F##B####B#", "B####B#F###################F#B####B#", "B####BF#####################FB####B#", "B####F#######################F####B#", "B###F#########################F###B#", "B##F###########################F##B#", "B#C#############################C#B#", "BF###############################FB#", "B#################################B#", "B#############F##K##F#############B#", "B############H#######H############B#", "#F###############################F##", "##C#############################C###", "###F###########################F####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########F#############F###########", "###########FEH#######HEF############", "##############E#####E###############", "###############HLLLH################", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "#########F####K#####K####F##########", "########F#####K#####K#####F#########", "#######F######K#####K######F########", "######F#####################F#######", "#####F#######################F######", "####F#########################F#####", "###F###########################F####", "##F#############################F###", "#C###############################C##", "C#################################C#", "###################################C", "#############F###K###F#############C", "############H#########H############C", "C#################################C#", "#C###############################C##", "##F#############################F###", "###F###########################F####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########FEH#########HEF###########", "#############E#######E##############", "##############JJJJJJJ###############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "#########F#####K###K#####F##########", "########F######K###K######F#########", "#######F#######K###K#######F########", "######F########K###K########F#######", "#####F#########K###K#########F######", "####F##########K###K##########F#####", "###F###########################F####", "##F#############################F###", "#C###############################C##", "D#################################D#", "###################################D", "############F####K####F############D", "###########H###########H###########D", "D#################################D#", "#C###############################C##", "##F#############################F###", "###F###########################F####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########FH###########HF###########", "############E#########E#############", "#############FJJJJJJJH##############", "############E#########E#############", "############D#########D#############", "############E#########E#############", "############D#########D#############", "############E#########E#############", "############D#########D#############", "############E#########E#############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "#########F######K#K######F##########", "########F#######K#K#######F#########", "#######F########K#K########F########", "######F#########K#K#########F#######", "#####F##########K#K##########F######", "####F###########K#K###########F#####", "###F############K#K############F####", "##F#############K#K#############F###", "#C##############K#K##############C##", "D###############K#K###############D#", "################K#K################C", "###########F####EKE####F###########C", "HHHHHHHHHHH#####G#G#####HHHHHHHHHHHC", "D###############G#G###############D#", "#C##############G#G##############C##", "##F#############G#G#############F###", "###F############G#G############F####", "####F###########G#G###########F#####", "#####F##########G#G##########F######", "######F#########G#G#########F#######", "#######F########G#G########F########", "########F#######G#G#######F#########", "#########F######G#G######F##########", "##########H#####G#G#####H###########", "###########E####G#G####E############", "############HLJJJJJJJLH#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B######K######B###########", "##########B######K######B###########", "##########B######K######B###########", "##########B######K######B###########", "##########B######K######B###########", "##########B######K######B###########", "##########B######K######B###########", "#########FB######K######BF##########", "########F#B######K######B#F#########", "#######F##B######K######B##F########", "######F###B######K######B###F#######", "#####F####B######K######B####F######", "####F#####B######K######B#####F#####", "###F######B######K######B######F####", "##F#######B######K######B#######F###", "#C########B######K######B########C##", "D#########B######K######B#########D#", "##########B######K######B##########C", "GGGGGGGGGGEKKKKKKEKKKKKKEGGGGGGGGGGE", "#################G#################C", "D################G################D#", "#C###############G###############C##", "##F##############G##############F###", "###F#############G#############F####", "####F############G############F#####", "#####F###########G###########F######", "######F##########G##########F#######", "#######F#########G#########F########", "########F########G########F#########", "#########F#######G#######F##########", "##########E######G######E###########", "###########E#####G#####E############", "############HLJJJJJJJLH#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "##########B#####K#K#####B###########", "#########F######K#K######F##########", "########F#######K#K#######F#########", "#######F########K#K########F########", "######F#########K#K#########F#######", "#####F##########K#K##########F######", "####F###########K#K###########F#####", "###F############K#K############F####", "##F#############K#K#############F###", "#C##############K#K##############C##", "D###############K#K###############D#", "################K#K################C", "###########F####EKE####F###########C", "HHHHHHHHHHH#####G#G#####HHHHHHHHHHHC", "D###############G#G###############D#", "#C##############G#G##############C##", "##F#############G#G#############F###", "###F############G#G############F####", "####F###########G#G###########F#####", "#####F##########G#G##########F######", "######F#########G#G#########F#######", "#######F########G#G########F########", "########F#######G#G#######F#########", "#########F######G#G######F##########", "##########H#####G#G#####H###########", "###########E####G#G####E############", "############HLJJJJJJJLH#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############", "############B#########B#############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "##########B####K###K####B###########", "#########F#####K###K#####F##########", "########F######K###K######F#########", "#######F#######K###K#######F########", "######F########K###K########F#######", "#####F#########K###K#########F######", "####F##########K###K##########F#####", "###F###########################F####", "##F#############################F###", "#C###############################C##", "D#################################D#", "###################################D", "############F####K####F############D", "###########H###########H###########D", "D#################################D#", "#C###############################C##", "##F#############################F###", "###F###########################F####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########FH###########HF###########", "############E#########E#############", "#############HJJJJJJJH##############", "############E#########E#############", "############D#########D#############", "############E#########E#############", "############D#########D#############", "############E#########E#############", "############D#########D#############", "############E#########E#############")
                    .aisle("##########BJJJJJJJJJJJJJB###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "##########B###K#####K###B###########", "#########F####K#####K####F##########", "########F#####K#####K#####F#########", "#######F######K#####K######F########", "######F#####################F#######", "#####F#######################F######", "####F#########################F#####", "###F###########################F####", "##F#############################F###", "#C###############################C##", "C#################################C#", "###################################C", "#############F###K###F#############C", "############H#########H############C", "C#################################C#", "#C###############################C##", "##F#############################F###", "###F###########################F####", "####F#########################F#####", "#####F#######################F######", "######F#####################F#######", "#######F###################F########", "########F#################F#########", "#########F###############F##########", "##########FEH#########HEF###########", "#############E#######E##############", "##############JJJJJJJ###############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############", "#############D#######D##############", "#############E#######E##############")
                    .aisle("B####B#####IJJJJJJJJJJJI#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B#####I#K#######K#I#####B####B#", "B####B####FL###########LF####B####B#", "B####B###FLL###########LLF###B####B#", "B####B##FLLL###########LLLF##B####B#", "B####B#FLLLL###########LLLLF#B####B#", "B####BFLLLLL###########LLLLLFB####B#", "B####FLLLLLL###########LLLLLLF####B#", "B###FLLLLLLL###########LLLLLLLF###B#", "B##FLLLLLLLL###########LLLLLLLLF##B#", "B#FLLLLLLLLL###########LLLLLLLLLF#B#", "BFLLLLLLLLLL###########LLLLLLLLLLFB#", "BLLLLLLLLLLL###########LLLLLLLLLLLB#", "BLLLLLLLLLLL##F##K##F##LLLLLLLLLLLB#", "BLLLLLLLLLLL#H#######H#LLLLLLLLLLLB#", "#FLLLLLLLLLL###########LLLLLLLLLLF##", "##FLLLLLLLLL###########LLLLLLLLLF###", "###FLLLLLLLL###########LLLLLLLLF####", "####FLLLLLLL###########LLLLLLLF#####", "#####FLLLLLL###########LLLLLLF######", "######FLLLLL###########LLLLLF#######", "#######FLLLL###########LLLLF########", "########FLLL###########LLLF#########", "#########FLL###########LLF##########", "##########FL###########LF###########", "###########FEH#######HEF############", "##############E#####E###############", "###############HLLLH################", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############", "##############D#####D###############", "##############E#####E###############")
                    .aisle("############IJJJJJJJJJI#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############I#########I#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L##F#K#F##L#############", "############L#H#####H#L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############L#########L#############", "############LEH#####HEL#############", "###############E###E################", "################HHH#################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################", "###############DBBBD################", "###############EBBBE################")
                    .aisle("#############IJJJJJJJI##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############I#######I##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L##FKF##L##############", "#############L#H###H#L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############L#######L##############", "#############LEH###HEL##############", "################EEE#################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################")
                    .aisle("##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBB@BBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBBBBBB###############", "##############BBHHHBB###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLLLLLL###############", "##############LLHHHLL###############", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "####################################", "###################################A")
                    .where("A", Predicates.any())
                    .where("#", Predicates.any())
                    .where("B", (CTNHPredicates.SpaceStructuralFrameworkBlock())
                            .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY))
                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                    )
                    .where("C", Predicates.blocks(PV_COIL.get()))
                    .where("D", Predicates.blocks(FUSION_CASING.get()))
                    .where("E", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("F", Predicates.blocks(STELLAR_RADIATION_ROUTER_CASING.get()))
                    .where("G", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
                    .where("H", Predicates.blocks(MACHINE_CASING_LuV.get()))
                    .where("I", Predicates.blocks(HERMETIC_CASING_ZPM.get()))
                    .where("J", CTNHPredicates.PhotovoltaicBlock())
                    .where("K", Predicates.blocks(COIL_NAQUADAH.get()))
                    .where("L", Predicates.blocks(FUSION_GLASS.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .appearanceBlock(NQ_EXCITE_CARBON_CARBON_NANOFIBER_STRUCTURAL_BLOCK)
            .workableCasingModel(CTNHCore.id("block/nq_excite_carbon_carbon_nanofiber_structural_block"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition HYPER_PLASMA_TURBINE = HyperPlasmaTurbineRegister.register();
    public final static MultiblockMachineDefinition PHOTOVOLTAIC_DRONE_STATION = REGISTRATE.multiblock("photovoltaic_drone_station", PhotoVoltaicDroneStation::new)
            .cnLangValue("光伏无人机道标基站")
            .allowExtendedFacing(false)
            .recipeTypes(CTNHRecipeTypes.PVDRONE)
            .recipeModifiers(PhotoVoltaicDroneStation::recipeModifier)
            .tooltips(pvdroneTooltip0.translate(),
                    pvdroneTooltip1.translate(),
                    pvdroneTooltip2.translate(),
                    pvdroneTooltip3.translate(),
                    pvdroneTooltip4.translate(),
                    pvdroneTooltip5.translate()

            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A#####BBBCCCBBB######", "#########CCC#########", "#########CCC#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######")
                    .aisle("####BBBBBBCBBBBBB####", "#########CFC#########", "##########G##########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF#########FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF#########FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF#########FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FFHHHHHHHHHFF####")
                    .aisle("##BBBBBBBBCBBBBBBBB##", "##BCCCC##CFC##CCCCB##", "##B#######G#######B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#############FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#############FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#############FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BFHH####H####HHFB##")
                    .aisle("##BBBBBBBBCBBBBBBBB##", "##CFFFC##CFC##CFFFC##", "###III####G####JJJ###", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F###############F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F###############F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F###############F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##FH######H######HF##")
                    .aisle("#BBBBBBBBBCBBBBBBBBB#", "##CFFFCCCCFCCCCFFFC##", "###III####G####JJJ###", "####K###########K####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H###########H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H###########H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H###########H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#FH#HHHHHHHHHHHHH#HF#")
                    .aisle("#BBBBBBBBBCBBBBBBBBB#", "##CFFFFFFFFFFFFFFFC##", "###IIIGGGGGGGGGJJJ###", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#F#################F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#F#################F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#F#################F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#FH#H#####H#####H#HF#")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "##CCCFFFFFFFFFFFCCC##", "#####GLLLLLLLLLG#####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "####CFFFFFFFFFFFC####", "#####GLLLLLLLLLG#####", "#######LLLLLLL#######", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "####CFFFFFFFFFFFC####", "#####GLLLLLLLLLG#####", "#######LLLLLLL#######", "########LLLLL########", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("CBBBBBBBBBCBBBBBBBBBC", "CCCCCFFFFFFFFFFFCCCCC", "C####GLLLLLLLLLG####C", "#######LLLLLLL#######", "########LLLLL########", "#########LLL#########", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "EH##H#####H#####H##HE")
                    .aisle("CCCCCCCCCCCCCCCCCCCCC", "CFFFFFFFFFFFFFFFFFFFC", "CGGGGGLLLLLLLLLGGGGGC", "#######LLLLLLL#######", "########LLLLL########", "#########LLL#########", "##########K##########", "##########H##########", "E#########H#########E", "C#########H#########C", "E#########H#########E", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "E#########H#########E", "C#########H#########C", "E#########H#########E", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "##########H##########", "E#########H#########E", "C#########H#########C", "E#########H#########E", "F#########H##########", "F#########H##########", "F#########H##########", "F#########H##########", "F#########H##########", "F#########H##########", "F#########H##########", "E#########H#########E", "CHHHHHHHHHHHHHHHHHHHC")
                    .aisle("CBBBBBBBBBCBBBBBBBBBC", "CCCCCFFFFFFFFFFFCCCCC", "C####GLLLLLLLLLG####C", "#######LLLLLLL#######", "########LLLLL########", "#########LLL#########", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "E###################E", "E###################E", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "E###################E", "EH##H#####H#####H##HE")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "####CFFFFFFFFFFFC####", "#####GLLLLLLLLLG#####", "#######LLLLLLL#######", "########LLLLL########", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "####CFFFFFFFFFFFC####", "#####GLLLLLLLLLG#####", "#######LLLLLLL#######", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("BBBBBBBBBBCBBBBBBBBBB", "##CCCFFFFFFFFFFFCCC##", "#####GLLLLLLLLLG#####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "F###################F", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "FH##H#####H#####H##HF")
                    .aisle("#BBBBBBBBBBBBBBBBBBB#", "##CFFFFFFCBCFFFFFFC##", "###MMMGGGCBCGGGNNN###", "#########CBC#########", "#########CBC#########", "#########COC#########", "#########CBC#########", "#########CBC#########", "#########CBC#########", "#F#######CBC#######F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#F#################F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#F#################F#", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#FH#H#####H#####H#HF#")
                    .aisle("#BBBBBBBBBCBBBBBBBBB#", "##CFFFCCCCBCCCCFFFC##", "###MMM#########NNN###", "####K###########K####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H####CBC####H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H###########H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#F##H###########H##F#", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "####H###########H####", "#FH#HHHHHHHHHHHHH#HF#")
                    .aisle("##BBBBBBBBCBBBBBBBB##", "##CFFFC##CBC##CFFFC##", "###MMM#########NNN###", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F######CBC######F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F###############F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##F###############F##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "##FH######H######HF##")
                    .aisle("##BBBBBBBBCBBBBBBBB##", "##BCCCC##CBC##CCCCB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#####CBC#####FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#############FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BF#############FB##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##B###############B##", "##BFHH####H####HHFB##")
                    .aisle("####BBBBBBCBBBBBB####", "#########CBC#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF###CBC###FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF#########FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FF#########FF####", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "####FFHHHHHHHHHFF####")
                    .aisle("######BBBCCCBBB######", "#########C@C#########", "#########CCC#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF######", "#########EEE#########", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#########EEE#########", "######FFFECEFFF#####A")
                    .where("A", Predicates.any())
                    .where("#", Predicates.any())
                    .where("B", CTNHPredicates.SpaceStructuralFrameworkBlock())
                    .where("C", Predicates.blocks(HIGH_POWER_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH))
                    )
                    .where("D", Predicates.any())
                    .where("E", Predicates.blocks(PV_COIL.get()))
                    .where("F", Predicates.blocks(FUSION_CASING.get()))
                    .where("G", Predicates.blocks(STELLAR_RADIATION_ROUTER_CASING.get()))
                    .where("H", Predicates.blocks(FUSION_GLASS.get()))
                    .where("I", Predicates.blocks(DIAMOND_BLOCK))
                    .where("J", Predicates.blocks(EMERALD_BLOCK))
                    .where("K", Predicates.blocks(BEACON))
                    .where("L", Predicates.blocks(NETHERITE_BLOCK))
                    .where("M", Predicates.blocks(GOLD_BLOCK))
                    .where("N", Predicates.blocks(IRON_BLOCK))
                    .where("O", Predicates.blocks(CTNHMachines.DRONEHOLDER.getBlock()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())

            .workableCasingModel(GTCEu.id("block/casings/hpca/high_power_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public static final MultiblockMachineDefinition GAS_CENTRIFUGE = REGISTRATE.multiblock("gas_centrifuge", WorkableElectricMultiblockMachine::new)
            .cnLangValue("气体离心机")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.GAS_CENTRIFUGE_RECIPES)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#CCC#", "#CCC#", "#####", "#####", "#####", "#####", "#####")
                    .aisle("CBBBC", "CBBBC", "#DED#", "#D#D#", "#D#D#", "#D#D#", "#D#D#")
                    .aisle("CBBBC", "CBBBC", "#EEE#", "#####", "#####", "#####", "#####")
                    .aisle("CBBBC", "CBBBC", "#DED#", "#D#D#", "#D#D#", "#D#D#", "#D#D#")
                    .aisle("#CCC#", "#C@C#", "#####", "#####", "#####", "#####", "#####")
                    .where("B", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("#", Predicates.air())
                    .where("D", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("E", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public static final MultiblockMachineDefinition HOT_COOLANT_TURBINE = REGISTRATE.multiblock("hot_coolant_turbine", holder -> new LargeTurbineMachine(holder, GTValues.EV))
            .cnLangValue("热冷却涡轮")
            .generator(true)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.HOT_COOLANT_TURBINE_RECIPES)
            .recipeModifier(LargeTurbineMachine::recipeModifier, true)
            .appearanceBlock(GTBlocks.CASING_TITANIUM_TURBINE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAA", "ASSA", "AAAA")
                    .aisle("ASSA", "BCCB", "ASSA")
                    .aisle("AAAA", "A@SA", "AAAA")
                    .where("A", Predicates.blocks(GTBlocks.CASING_TITANIUM_TURBINE.get()))
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(GTBlocks.CASING_TITANIUM_GEARBOX.get()))
                    .where("S", Predicates.blocks(GTBlocks.CASING_TITANIUM_TURBINE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"), GTCEu.id("block/multiblock/generator/large_steam_turbine"))
            .register();
    public final static MultiblockMachineDefinition  CRYOTHEUMFREEZER = REGISTRATE.multiblock("cryotheum_freezer", CryotheumFreezer::new)
            .cnLangValue("凛冰冷冻机")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.VACUUM_RECIPES)
            .recipeModifiers(CryotheumFreezer::recipeModifier, OC_NON_PERFECT, BATCH_MODE)
            .tooltips(cryotheumFreezerTip0.translate(),
                    cryotheumFreezerTip1.translate(),
                    cryotheumFreezerTip2.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A###BBBBB####", "####BCCCB####", "####BCCCB####", "####BCCCB####", "####BCCCB####", "####BCCCB####", "####BBBBB####")
                    .aisle("#BBBBBDBBBBB#", "#DDDBEDEBDDD#", "#BBBBEDEBBBB#", "#DDDBEFEBDDD#", "#BBBBEDEBBBB#", "#DDDBEDEBDDD#", "#BBBBBEBBBBB#")
                    .aisle("#B##BBBBB##B#", "#D##BCCCB##D#", "#B##BCCCB##B#", "#D##BCFCB##D#", "#B##BCCCB##B#", "#D##BCCCB##D#", "#B##BBBBB##B#")
                    .aisle("#B#########B#", "#D#########D#", "#B###CCC###B#", "#D###CFC###D#", "#B###CCC###B#", "#D#########D#", "#B#########B#")
                    .aisle("BBB#######BBB", "BBB##BBB##BBB", "BBB#BEEEB#BBB", "BBB#BEFEB#BBB", "BBB#BEEEB#BBB", "BBB##BBB##BBB", "BBB#######BBB")
                    .aisle("BBB##BBB##BBB", "CEC#BEEEB#CEC", "CECCEBBBECCEC", "CECCEBGBECCEC", "CECCEBBBECCEC", "CEC#BEEEB#CEC", "BBB##BBB##BBB")
                    .aisle("BDB##BDB##BDB", "CDC#BEDEB#CDC", "CDCCEBDBECCDC", "CFFFFGDGFFFFC", "CDCCEBDBECCDC", "CDC#BEDEB#CDC", "BEB##BDB##BEB")
                    .aisle("BBB##BBB##BBB", "CEC#BEEEB#CEC", "CECCEEGEECCEC", "CECCEGDGECCEC", "CECCEEGEECCEC", "CEC#BEEEB#CEC", "BBB##BBB##BBB")
                    .aisle("BBB#######BBB", "BBB##BBB##BBB", "BBB#BDDDB#BBB", "BBB#BDDDB#BBB", "BBB#BDDDB#BBB", "BBB##BBB##BBB", "BBB#######BBB")
                    .aisle("#############", "#############", "#####BBB#####", "#####BHB#####", "#####BBB#####", "#############", "############A")
                    .where("A", Predicates.any())
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(SUPER_FREEZE_BLOCK.get()).or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("C", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("D", Predicates.blocks(SUPERCOOLED_BLOCK.get()))
                    .where("E", Predicates.blocks(MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                    .where("F", Predicates.blocks(CASING_POLYBENZIMIDAZOLE_PIPE.get()))
                    .where("G", Predicates.blocks(HIGH_SPEED_PIPE_BLOCK.get()))
                    .where("H", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .appearanceBlock(SUPER_FREEZE_BLOCK)
            .workableCasingModel(CTNHCore.id("block/casings/super_machine_casing_frost_proof"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

    public final static MultiblockMachineDefinition NERUOMATRIXCOMPILER = REGISTRATE.multiblock("neruo_martix_compiler", NeuroMatrixCompiler::new)
            .cnLangValue("神经矩阵编译器")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.COMPILER_RECIPE)
            .appearanceBlock(CASING_ANTIFREEZE_HEATPROOF_MACHINE)
            .recipeModifiers(GTRecipeModifiers.TIER_CHECK, NeuroMatrixCompiler::recipeModifier)
            .tooltips(neuroMatrixCompilerTip0.translate(),
                    neuroMatrixCompilerTip01.translate(),
                    neuroMatrixCompilerTip1.translate(),
                    neuroMatrixCompilerTip2.translate(),
                    neuroMatrixCompilerTip3.translate(),
                    neuroMatrixCompilerTip4.translate(),
                    neuroMatrixCompilerTip5.translate(),
                    neuroMatrixCompilerTip6.translate(),
                    neuroMatrixCompilerTipPart1.translate(),
                    neuroMatrixCompilerTipPart2.translate(),
                    neuroMatrixCompilerTipPart3.translate(),
                    neuroMatrixCompilerTipPart4.translate(),
                    neuroMatrixCompilerTip7.translate(),
                    neuroMatrixCompilerTip8.translate(),
                    neuroMatrixCompilerTip9.translate(),
                    neuroMatrixCompilerTip10.translate(),
                    neuroMatrixCompilerTip11.translate(),

                    neuroMatrixCompilerTip12.translate())
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A############BBBBB#############", "#############CCDCC#############", "#############B#B#B#############", "###############################", "###############################", "###############################", "##############B#B##############", "#############CCCCC#############", "#############BBBBB#############")
                    .aisle("#############BBBBB#############", "#############CEFEC#############", "##############EFE##############", "##############EFE##############", "##############EFE##############", "##############EFE##############", "#############BEFEB#############", "#############CEFEC#############", "#############BBBBB#############")
                    .aisle("#############BBBBB#############", "#############CFGFC#############", "#############BFGFB#############", "##############FGF##############", "##############FGF##############", "##############FGF##############", "##############FGF##############", "#############CFGFC#############", "#############BBBBB#############")
                    .aisle("#############BBBBB#############", "#############CEHEC#############", "##############ECE##############", "##############ECE##############", "##############ECE##############", "##############ECE##############", "#############BECEB#############", "#############CECEC#############", "#############BBBBB#############")
                    .aisle("#############BBBBB#############", "#############CCHCC#############", "#############BBCBB#############", "###############################", "###############################", "###############################", "###############################", "#############CCCCC#############", "#############BBBBB#############")
                    .aisle("##############III##############", "##############FHF##############", "##############BCB##############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("##############III##############", "##############FHF##############", "##############BCB##############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("##############III##############", "##############FHF##############", "##############BCB##############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("#############IIIII#############", "#############IIHII#############", "#############IIIII#############", "#############FIIIF#############", "#############FFIFF#############", "#############FFIFF#############", "#############FIIIF#############", "#############IIIII#############", "#############IIIII#############")
                    .aisle("###########IIJJIJJII###########", "###########II##I##II###########", "###########FF##I##FF###########", "###########FF##I##FF###########", "###########FF##F##FF###########", "###########FF##F##FF###########", "###########FF##I##FF###########", "###########FF##I##FF###########", "###########IIKKIKKII###########")
                    .aisle("##########IJJJJIJJJJI##########", "##########I####I####I##########", "##########I####I####I##########", "##########I####F####I##########", "##########I####F####I##########", "##########I####F####I##########", "##########I####F####I##########", "##########I####I####I##########", "##########IKKKKIKKKKI##########")
                    .aisle("#########IJJJJJIJJJJJI#########", "#########I#####I#####I#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########IKKKKKIKKKKKI#########")
                    .aisle("#########IJJJJJIJJJJJI#########", "#########I#####I#####I#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########IKKKKKIKKKKKI#########")
                    .aisle("BBBBB###IJJJJJJIJJJJJJI###BBBBB", "CCCCC###I######I######I###CCCCC", "B#B#B###I######I######I###B#B#B", "########F######F######F########", "########F######F######F########", "########F######F######F########", "#B#B####F######F######F####B#B#", "CCCCC###I######I######I###CCCCC", "BBBBB###IKKKKKKIKKKKKKI###BBBBB")
                    .aisle("BBBBBIIIIJJJJJJIJJJJJJIIIIBBBBB", "CEFECFFFI######I######IFFFCEFEC", "#EFEBBBBI######I######IBBBBEFE#", "#EFE####I######I######I####EFE#", "#EFE####F######F######F####EFE#", "#EFE####F######F######F####EFE#", "BEFE####I######I######I####EFEB", "CEFEC###I######I######I###CEFEC", "BBBBB###IKKKKKKIKKKKKKI###BBBBB")
                    .aisle("BBBBBIIIIIIIIIIIIIIIIIIIIIBBBBB", "DFGHHHHHHIIIIIIIIIIIIIHHHHHHGCD", "BFGCCCCCIIIFFIIIIIFFIIICCCCCGCB", "#FGC####IIFFFFIIIFFFFII####CGC#", "#FGC####IFFFFFFIFFFFFFI####CGC#", "#FGC####IFFFFFFIFFFFFFI####CGC#", "#FGC####IIFFFFIIIFFFFII####CGC#", "CFGCC###IIIFFIIIIIFFIII###CCGCC", "BBBBB###IIIIIIILIIIIIII###BBBBB")
                    .aisle("BBBBBIIIIJJJJJJIJJJJJJIIIIBBBBB", "CEFECFFFI######I######IFFFCEFEC", "#EFEBBBBI######I######IBBBBEFE#", "#EFE####I######I######I####EFE#", "#EFE####F######F######F####EFE#", "#EFE####F######F######F####EFE#", "BEFE####I######I######I####EFEB", "CEFEC###I######I######I###CEFEC", "BBBBB###IKKKKKKIKKKKKKI###BBBBB")
                    .aisle("BBBBB###IJJJJJJIJJJJJJI###BBBBB", "CCCCC###I######I######I###CCCCC", "B#B#B###I######I######I###B#B#B", "########F######F######F########", "########F######F######F########", "########F######F######F########", "#B#B####F######F######F####B#B#", "CCCCC###I######I######I###CCCCC", "BBBBB###IKKKKKKIKKKKKKI###BBBBB")
                    .aisle("#########IJJJJJIJJJJJI#########", "#########I#####I#####I#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########IKKKKKIKKKKKI#########")
                    .aisle("#########IJJJJJIJJJJJI#########", "#########I#####I#####I#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########F#####F#####F#########", "#########IKKKKKIKKKKKI#########")
                    .aisle("#########IIJJJJIJJJJII#########", "#########FH####I####HF#########", "#########BI####I####IB#########", "##########I####F####I##########", "##########I####F####I##########", "##########I####F####I##########", "##########I####F####I##########", "##########I####I####I##########", "##########IKKKKIKKKKI##########")
                    .aisle("########IIIIIJJIJJIIIII########", "########FHFII##I##IIFHF########", "########BCBFF##I##FFBCB########", "###########FF##I##FF###########", "###########FF##F##FF###########", "###########FF##F##FF###########", "###########FF##I##FF###########", "###########FF##I##FF###########", "###########IIKKIKKII###########")
                    .aisle("#######III###IIIII###III#######", "#######FHF###II@II###FHF#######", "#######BCB###IIDII###BCB#######", "#############FIIIF#############", "#############FFIFF#############", "#############FFIFF#############", "#############FIIIF#############", "#############IIIII#############", "#############IIIII#############")
                    .aisle("######III#############III######", "######FHF#############FHF######", "######BCB#############BCB######", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("##BBBBBI###############IBBBBB##", "##CCCCHF###############FCCCCC##", "##B#BBCB###############BCBB#B##", "###############################", "###############################", "###############################", "###B#######################B###", "##CCCCC#################CCCCC##", "##BBBBB#################BBBBB##")
                    .aisle("##BBBBB#################BBBBB##", "##CEFEC#################CEFEC##", "###EFEB#################BEFE###", "###EFC###################CFE###", "###EFC###################CFE###", "###EFC###################CFE###", "##BEFC###################CFEB##", "##CEFCC#################CCFEC##", "##BBBBB#################BBBBB##")
                    .aisle("##BBBBB#################BBBBB##", "##CFGFC#################CFGCC##", "##BFGFB#################BFGCB##", "###FGF###################FGC###", "###FGF###################FGC###", "###FGF###################FG####", "###FGF###################FG####", "##CFGFC#################CFGCC##", "##BBBBB#################BBBBB##")
                    .aisle("##BBBBB#################BBBBB##", "##CEFEC#################CEFEC##", "###EFE###################EFE###", "###EFE###################EFE###", "###EFE###################EFE###", "###EFE###################EFE###", "##BEFEB#################BEFEB##", "##CEFEC#################CEFEC##", "##BBBBB#################BBBBB##")
                    .aisle("##BBBBB#################BBBBB##", "##CCDCC#################CCDCC##", "##B#B#B#################B#B#B##", "###############################", "###############################", "###############################", "###B#B###################B#B###", "##CCCCC#################CCCCC##", "##BBBBB#################BBBBB#A")
                    .where("A",Predicates.any())
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(ADVANCED_BIO_REACTOR_CASING.get()))
                    .where("C", Predicates.blocks(FUSION_CASING.get()))
                    .where("D", Predicates.abilities(CTNHPartAbility.COMPILER))
                    .where("E", Predicates.blocks(ADVANCED_COMPUTER_CASING.get()))
                    .where("F", Predicates.blocks(FUSION_CASING.get()))
                    .where("G", Predicates.blocks(HIGH_POWER_CASING.get()))
                    .where("H", Predicates.blocks(WIDESPEEDINGPIPE.get()))
                    .where("I", Predicates.blocks(CASING_ANTIFREEZE_HEATPROOF_MACHINE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("J", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("K", Predicates.frames(Naquadria))
                    .where("L", Predicates.blocks(CASING_NEUTRONIUM_ALLOY_BLOCK.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingModel(CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

public static final MultiblockMachineDefinition[] FLUID_DRILLING_INF = CTNHMachineUtils.registerTieredMultis(
        "fluid_drilling_inf", INFFluidDrillMachine::new, (tier, builder) -> builder
                .rotationState(RotationState.ALL)
                .langValue("%s Fluid Drilling Rig %s".formatted(VLVH[tier], VLVT[tier]))
                .recipeType(DUMMY_RECIPES)
                .tooltips(
                        fluidDrillingRigDescriptionInf.translate(),
                        fluidDrillingRigDepletionInf.translate(
                                ),
                        Component.translatable("gtceu.universal.tooltip.energy_tier_range", GTValues.VNF[tier],
                                GTValues.VNF[tier + 1]),
                        Component.translatable("gtceu.machine.fluid_drilling_rig.production",
                                INFFluidDrillMachine.getRigMultiplier(tier),
                                FormattingUtil.formatNumbers(INFFluidDrillMachine.getRigMultiplier(tier) * 2)))
                .appearanceBlock(() -> INFFluidDrillMachine.getCasingState(tier))
                .pattern((definition) -> FactoryBlockPattern.start()
                        .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                        .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                        .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                        .where('S', controller(blocks(definition.get())))
                        .where('X', blocks(INFFluidDrillMachine.getCasingState(tier)).setMinGlobalLimited(3)
                                .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                        .setMaxGlobalLimited(2))
                                .or(abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1)))
                        .where('C', blocks(INFFluidDrillMachine.getCasingState(tier)))
                        .where('F', blocks(INFFluidDrillMachine.getFrameState(tier)))
                        .where('#', any())
                        .build())
                .workableCasingModel(INFFluidDrillMachine.getBaseTexture(tier),
                        GTCEu.id("block/multiblock/fluid_drilling_rig"))
                .register(),
        UHV);
//    public static final MultiblockMachineDefinition INF_LARGE_MINER = REGISTRATE.multiblock("inf_large_miner", holder -> new LargeMinerMachine(holder, GTValues.UHV, 1, 99, 7, 9))
//            .rotationState(RotationState.NON_Y_AXIS)
//            .recipeType(GTRecipeTypes.MACERATOR_RECIPES)
//            .tooltips(
//                    Component.translatable("ctnh.multiblock.large_miner_zpm.tooltip.0"),
//                    Component.translatable("gtceu.machine.miner.multi.description"))
//            .tooltipBuilder((stack, tooltip) -> {
//                int workingAreaChunks =99;
//                tooltip.add(Component.translatable("gtceu.machine.miner.multi.modes"));
//                tooltip.add(Component.translatable("gtceu.machine.miner.multi.production"));
//                tooltip.add(Component.translatable("gtceu.machine.miner.fluid_usage", 9,
//                        DrillingFluid.getLocalizedName()));
//                tooltip.add(Component.translatable("gtceu.universal.tooltip.working_area_chunks",
//                        workingAreaChunks, workingAreaChunks));
//                tooltip.add(Component.translatable("gtceu.universal.tooltip.energy_tier_range",
//                        GTValues.VNF[UHV], GTValues.VNF[UHV + 1]));
//            })
//            .pattern((definition) -> FactoryBlockPattern.start()
//                    .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
//                    .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
//                    .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
//                    .where("S", Predicates.controller(Predicates.blocks(definition.get())))
//                    .where("X", Predicates.blocks(CTNHBlocks.CASING_NEUTRONIUM_ALLOY_BLOCK.get())
//                            .or(abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
//                            .or(abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
//                            .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
//                                    .setMaxGlobalLimited(2).setPreviewCount(1)))
//                    .where("C", Predicates.blocks(CTNHBlocks.CASING_NEUTRONIUM_ALLOY_BLOCK.get()))
//                    .where("F", Predicates.frames(Neutronium))
//                    .where("#", Predicates.any())
//                    .build())
//            .workableCasingModel(CTNHCore.id("block/casings/nq_neutronium_casing"), GTCEu.id("block/multiblock/large_miner"))
//            .register();
    public static void init() {}
}
// spotless:on
