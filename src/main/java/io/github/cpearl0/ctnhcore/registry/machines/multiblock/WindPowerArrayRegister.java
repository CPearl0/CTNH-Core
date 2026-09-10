package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.WindPowerArrayMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;

import net.minecraft.world.level.block.Block;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.tterrag.registrate.util.entry.BlockEntry;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class WindPowerArrayRegister {

    @CN("§7§o风力狼群:真正的自然之力")
    @EN("§7§oWind Wolf: The TRUE POWER of NATURE")
    public static Lang windArrayTooltip0;

    @CN("§8---------------§a基础数据§8-----------------")
    @EN("§8-----------------§aBasic Data§8--------------------")
    public static Lang windArrayTooltip1;

    @CN("§f润滑油会从风力网络中抽取.")
    @EN("§fLubricant will be extracted from the network.")
    public static Lang windArrayTooltip10;

    @CN("§5顺应风力网络的工作规律,以抵挡自然之力的摧残")
    @EN("§5DO OBEY the rules to resist the DESTRUCTION from nature force.")
    public static Lang windArrayTooltip11;

    @CN("§f- 基础发电功率: §e%d EU/t  §7(地球)")
    @EN("§f- Basic Production: §e%d EU/t  §7(on earth)")
    public static Lang windArrayTooltip2;

    @CN("§f- 天气风力增益: §e雨天x2,雷雨x4")
    @EN("§f- Weather Boost: §eRainy x2, Thunder x4")
    public static Lang windArrayTooltip3;

    @CN("§f- 高度增益: §e Clamp(Y-64, 0, 256-64) / (256-64)")
    @EN("§f- Altitude Boost: §eClamp(Y-64, 0, 256-64) / (256-64)")
    public static Lang windArrayTooltip4;

    @CN("§f- 网络增益: §e 0.3*[log2(网络大小)]")
    @EN("§f- Network Boost: §e 0.3*[log2(NetSize)]")
    public static Lang windArrayTooltip5;

    @CN("§f增益乘算得到发电效率")
    @EN("§fAll boosts are multiplied to determine production.")
    public static Lang windArrayTooltip6;

    @CN("§f需要§e%d mB/s§f 润滑油以维护机器运行")
    @EN("§fRequires §e%d mB/s§f Lubricant.")
    public static Lang windArrayTooltip7;

    @CN("§8---------------§a风力网络§8-----------------")
    @EN("§8-----------------§aWind Network§8------------------")
    public static Lang windArrayTooltip8;

    @CN("§f所有结构对齐且间距<=1的风力发电机阵列会组成风力网络")
    @EN("§fAligned structure within a distance of <= 1 form a Wind Network.")
    public static Lang windArrayTooltip9;

    public static MultiblockMachineDefinition register(String name, int tier, BlockEntry<Block> casing, Material frame,
                                                       String renderCasing, String cnName) {
        return REGISTRATE.multiblock(name, holder -> new WindPowerArrayMachine(holder, tier))
                .cnLangValue(cnName)
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAA", "###", "###", "###", "BBB")
                        .aisle("AAA", "#A#", "#A#", "#A#", "BAB")
                        .aisle("A@A", "###", "###", "###", "BBB")
                        .where("A", Predicates.blocks(casing.get())
                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)
                                        .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setMinGlobalLimited(1))))
                        .where("B", Predicates.frames(frame))
                        .where("#", Predicates.air())
                        .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                        .build())
                .allowFlip(false)
                .allowExtendedFacing(false)
                .workableCasingModel(GTCEu.id("block/casings/solid/" + renderCasing),
                        GTCEu.id("block/multiblock/implosion_compressor"))
                .tooltips(windArrayTooltip0.translate(),
                        windArrayTooltip1.translate(),
                        windArrayTooltip2.translate(GTValues.V[tier]),
                        windArrayTooltip3.translate(),
                        windArrayTooltip4.translate(),
                        windArrayTooltip5.translate(),
                        windArrayTooltip6.translate(),
                        windArrayTooltip7.translate(tier),
                        windArrayTooltip8.translate(),
                        windArrayTooltip9.translate(),
                        windArrayTooltip10.translate(),
                        windArrayTooltip11.translate())
                .register();
    }
}
