package io.github.cpearl0.ctnhcore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

public class CTNHCommonTooltips {

    @CN("·允许使用并行控制仓")
    @EN("Voltage levels increase the number of parallels")
    public static Lang commonTooltipParallelHatch;

    @CN("无损超频！")
    @EN("Perfect Overclock！")
    public static Lang commonTooltipPerfectOverclock;

    @CN("只能使用HV级能源仓及以下等级")
    @EN("Can only use HV-grade energy hatches and below")
    public static Lang commonTooltipSteelMachine0;

    @Key("ctnh.common_tooltip.steel_machine.1")
    @CN("最大并行为32")
    @EN("Maximum parallelism: 32")
    public static Lang commonTooltipSteelMachine1;

    @CN("当配方运行时间小于1t时,会自动计算并行")
    @EN("When recipe runtime is less than 1 tick, parallel calculations will be performed automatically.")
    public static Lang commonTooltipSubtickOverclock;

    @CN("允许使用激光仓")
    @EN("The use of the laser chamber is permitted")
    public static Lang gtceuMultiblockLaserTooltip;

    @CN("配方修改失败")
    @EN("Recipe modification failed")
    public static Lang gtceuRecipeModifierDefaultFail;

    @CN("该配方没有定义 EU 输出")
    @EN("This recipe defines no EU output")
    public static Lang recipeModifierNoEuOutput;

    @CN("机器输出功率不足：配方需要 %s EU/t，当前输出上限 %s EU/t")
    @EN("Insufficient machine output: recipe requires %s EU/t, current limit is %s EU/t")
    public static Lang recipeModifierInsufficientOutputPower;

    @CN("输入材料不足，无法运行一次配方")
    @EN("Not enough input materials to run the recipe once")
    public static Lang recipeModifierInsufficientInput;

    @CN("输出仓已满，配方产物无处存放")
    @EN("Output is full; the recipe products cannot be stored")
    public static Lang recipeModifierOutputFull;

    @CN("该配方类型不被这台机器支持")
    @EN("This recipe type is not supported by this machine")
    public static Lang recipeModifierUnsupportedRecipeType;

    public static MutableComponent PARALLEL_HATCH = commonTooltipParallelHatch.translate()
            .withStyle(ChatFormatting.GOLD);
    public static MutableComponent SUBTICK_PARALLEL = commonTooltipSubtickOverclock.translate()
            .withStyle(ChatFormatting.YELLOW);
    public static MutableComponent PERFECT_OVERCLOCK = commonTooltipPerfectOverclock.translate()
            .withStyle(ChatFormatting.GREEN);
    public static MutableComponent[] STEEL_MACHINE = new MutableComponent[] {
            commonTooltipSteelMachine0.translate(),
            // Component.translatable("ctnh.common_tooltip.steel_machine.1"),
            PERFECT_OVERCLOCK
    };
    public static MutableComponent[] MANA_MACHINE = new MutableComponent[] {
            Component.translatable("ctnh.common_tooltip.mana_machine.0").withStyle(ChatFormatting.GRAY),
            // Component.translatable("ctnh.common_tooltip.mana_machine.1"),
            Component.translatable("ctnh.common_tooltip.mana_machine.2"),
            Component.translatable("ctnh.common_tooltip.mana_machine.3"),
            Component.translatable("ctnh.common_tooltip.mana_machine.4")
    };

    public static MutableComponent[] MANA_GENERATOR = new MutableComponent[] {
            Component.translatable("ctnh.common_tooltip.mana_generator.0"),
            // Component.translatable("ctnh.common_tooltip.mana_generator.1"),
            Component.translatable("ctnh.common_tooltip.mana_generator.2"),
            Component.translatable("ctnh.common_tooltip.mana_generator.3")
    };
    public static MutableComponent BASIC_MANA_CONSUME = Component
            .translatable("ctnh.common_tooltip.basic_mana_consume");
    public static MutableComponent ADVANCED_MANA_CONSUME = Component
            .translatable("ctnh.common_tooltip.advanced_mana_consume");
    public static MutableComponent SUPER_MANA_CONSUME = Component
            .translatable("ctnh.common_tooltip.super_mana_consume");
    public static MutableComponent[] ZENITH_MACHINE = new MutableComponent[] {
            Component.translatable("ctnh.common_tooltip.zenith_machine.0").withStyle(ChatFormatting.DARK_PURPLE),
            Component.translatable("ctnh.common_tooltip.zenith_machine.1"),
            Component.translatable("ctnh.common_tooltip.zenith_machine.2"),
            CTNHCommonTooltips.SUPER_MANA_CONSUME,
            CTNHCommonTooltips.PERFECT_OVERCLOCK,
            // Component.translatable("ctnh.common_tooltip.mana_machine.1"),
            Component.translatable("ctnh.common_tooltip.mana_machine.2")
    };
}
