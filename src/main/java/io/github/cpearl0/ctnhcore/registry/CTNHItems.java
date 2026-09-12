package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.item.*;
import io.github.cpearl0.ctnhcore.common.item.debug.ReloadItem;
import io.github.cpearl0.ctnhcore.data.item.CrystalItems;
import io.github.cpearl0.ctnhcore.data.materials.ChemicalItems;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.ItemMaterialInfo;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.FoodStats;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import com.ctnhlang.Suffix;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import static com.gregtechceu.gtceu.common.data.GTItems.attach;
import static com.gregtechceu.gtceu.common.data.GTItems.materialInfo;
import static com.gregtechceu.gtceu.common.data.GTMachines.CREATIVE_TOOLTIPS;
import static io.github.cpearl0.ctnhcore.data.materials.BedrockMaterials.ADAMANTITE;
import static io.github.cpearl0.ctnhcore.data.materials.BedrockMaterials.SAMARIUM_DYSPROSIUM_TERBIUM_PERMANENT_MAGNET_ALLOY_MAGNETIC;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static io.github.cpearl0.ctnhcore.registry.machines.CTNHMachines.CREATIVE_ENERGY_COVER_DEF;

@Suffix("tooltip")
public class CTNHItems {

    @CN("更好的随机存取存储器")
    @EN("Advanced Random Access Memory")
    public static Lang advancedRamChipTooltip;

    @CN("更好的内存原料")
    @EN("Enhanced RAM Wafer")
    public static Lang advancedRamWaferTooltip;

    @Key("ctnh.item.dyson_tier1")
    @CN("集成性光伏无人机蜂群")
    @EN("Integrated Photovoltaic Drone Swarm")
    public static Lang itemDysonTier1;

    @Key("ctnh.item.dyson_tier2")
    @CN("§4我们的蜂群遮天蔽日")
    @EN("§4Our swarm blocks out the sky")
    public static Lang itemDysonTier2;

    @CN("基础堆温：%d°C")
    @EN("Base Reactor Temperature: %d°C")
    public static Lang itemNuclearReactorHeat;

    @Key("ctnh.item.runes.horizen_rune")
    @CN("视野所向之处")
    @EN("As Far as the Eye Can See")
    public static Lang itemRunesHorizenRune;

    @Key("ctnh.item.runes.proliferation_rune")
    @CN("金融与生物的本能")
    @EN("Finance and Biological Instinct")
    public static Lang itemRunesProliferationRune;

    @Key("ctnh.item.runes.quasar_rune")
    @CN("毁灭与创造交替")
    @EN("The Alternation of Destruction and Creation")
    public static Lang itemRunesQuasarRune;

    @Key("ctnh.item.runes.starlight_rune")
    @CN("Per Aspera Ad Astra")
    @EN("Per Aspera Ad Astra")
    public static Lang itemRunesStarlightRune;

    @Key("ctnh.item.runes.twist_rune")
    @CN("速度与人性的扭曲")
    @EN("The Distortion of Speed and Humanity")
    public static Lang itemRunesTwistRune;

    @CN("§l格雷科技-多方块结构终端-异步成型模式")
    @EN("§lGregTech Multiblock Structure Terminal - Asynchronous Formation Mode")
    public static Lang meAdvancedTerminalTooltip1;

    @CN("复刻了曾经的旗舰款，终端屏幕上闪烁着久违的画面")
    @EN("A replica of the former flagship model; its terminal screen flickers with a long-lost display")
    public static Lang meAdvancedTerminalTooltip2;

    @Key("ctnh.me_advanced_terminal.tooltip.3")
    @CN("通过ME无线访问点链接到网络")
    @EN("Connects to the network via an ME Wireless Access Point")
    public static Lang meAdvancedTerminalTooltip3;

    @CN("能维持机体基本的生理功能")
    @EN("Maintains the body's basic physiological functions")
    public static Lang simpleNutritiousMealTooltip1;

    @CN("用于检测多方块搭建时产生的错误")
    @EN("Use to check the error when building the multiblock")
    public static Lang testingTerminalTooltip1;

    @CN("右键多方块的主方块以显示错误信息")
    @EN("Right-click the controller to show the error info")
    public static Lang testingTerminalTooltip2;

    @CN("按住Shift右键切换普通/翻转模式")
    @EN("Right-click with Shift to change between Normal/Flipped mode")
    public static Lang testingTerminalTooltip3;

    @CN("蕴含生态圈的所有精华")
    @EN("Contains all the essence of an ecosystem")
    public static Lang itemEcologicalStarDesc;

    @CN("分化....")
    @EN("Differentiation....")
    public static Lang itemSculkCellDesc;

    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.ITEM);
    }

    public static ItemEntry<Item> GENERAL_CIRCUIT_ULV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_LV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_MV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_HV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_EV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_IV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_LUV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_ZPM;
    public static ItemEntry<Item> GENERAL_CIRCUIT_UV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_UHV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_UEV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_UIV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_UXV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_OPV;
    public static ItemEntry<Item> GENERAL_CIRCUIT_MAX;

    public static final ItemEntry<Item>[] GENERAL_CIRCUITS = new ItemEntry[15];

    private static void registerGeneralCircuits() {
        GENERAL_CIRCUIT_ULV = REGISTRATE.item("general_circuit_ulv", Item::new)
                .cnlang("ULV级电路板").lang("General Circuit ULV")
                .tag(CustomTags.ULV_CIRCUITS).register();
        GENERAL_CIRCUIT_LV = REGISTRATE.item("general_circuit_lv", Item::new)
                .cnlang("LV级电路板").lang("General Circuit LV")
                .tag(CustomTags.LV_CIRCUITS).register();
        GENERAL_CIRCUIT_MV = REGISTRATE.item("general_circuit_mv", Item::new)
                .cnlang("MV级电路板").lang("General Circuit MV")
                .tag(CustomTags.MV_CIRCUITS).register();
        GENERAL_CIRCUIT_HV = REGISTRATE.item("general_circuit_hv", Item::new)
                .cnlang("HV级电路板").lang("General Circuit HV")
                .tag(CustomTags.HV_CIRCUITS).register();
        GENERAL_CIRCUIT_EV = REGISTRATE.item("general_circuit_ev", Item::new)
                .cnlang("EV级电路板").lang("General Circuit EV")
                .tag(CustomTags.EV_CIRCUITS).register();
        GENERAL_CIRCUIT_IV = REGISTRATE.item("general_circuit_iv", Item::new)
                .cnlang("IV级电路板").lang("General Circuit IV")
                .tag(CustomTags.IV_CIRCUITS).register();
        GENERAL_CIRCUIT_LUV = REGISTRATE.item("general_circuit_luv", Item::new)
                .cnlang("LuV级电路板").lang("General Circuit LuV")
                .tag(CustomTags.LuV_CIRCUITS).register();
        GENERAL_CIRCUIT_ZPM = REGISTRATE.item("general_circuit_zpm", Item::new)
                .cnlang("ZPM级电路板").lang("General Circuit ZPM")
                .tag(CustomTags.ZPM_CIRCUITS).register();
        GENERAL_CIRCUIT_UV = REGISTRATE.item("general_circuit_uv", Item::new)
                .cnlang("UV级电路板").lang("General Circuit UV")
                .tag(CustomTags.UV_CIRCUITS).register();
        GENERAL_CIRCUIT_UHV = REGISTRATE.item("general_circuit_uhv", Item::new)
                .cnlang("UHV级电路板").lang("General Circuit UHV")
                .tag(CustomTags.UHV_CIRCUITS).register();
        GENERAL_CIRCUIT_UEV = REGISTRATE.item("general_circuit_uev", Item::new)
                .cnlang("UEV级电路板").lang("General Circuit UEV")
                .tag(CustomTags.UEV_CIRCUITS).register();
        GENERAL_CIRCUIT_UIV = REGISTRATE.item("general_circuit_uiv", Item::new)
                .cnlang("UIV级电路板").lang("General Circuit UIV")
                .tag(CustomTags.UIV_CIRCUITS).register();
        GENERAL_CIRCUIT_UXV = REGISTRATE.item("general_circuit_uxv", Item::new)
                .cnlang("UXV级电路板").lang("General Circuit UXV")
                .tag(CustomTags.UXV_CIRCUITS).register();
        GENERAL_CIRCUIT_OPV = REGISTRATE.item("general_circuit_opv", Item::new)
                .cnlang("OpV级电路板").lang("General Circuit OpV")
                .tag(CustomTags.OpV_CIRCUITS).register();
        GENERAL_CIRCUIT_MAX = REGISTRATE.item("general_circuit_max", Item::new)
                .cnlang("MAX级电路板").lang("General Circuit MAX")
                .tag(CustomTags.MAX_CIRCUITS).register();

        GENERAL_CIRCUITS[0] = GENERAL_CIRCUIT_ULV;
        GENERAL_CIRCUITS[1] = GENERAL_CIRCUIT_LV;
        GENERAL_CIRCUITS[2] = GENERAL_CIRCUIT_MV;
        GENERAL_CIRCUITS[3] = GENERAL_CIRCUIT_HV;
        GENERAL_CIRCUITS[4] = GENERAL_CIRCUIT_EV;
        GENERAL_CIRCUITS[5] = GENERAL_CIRCUIT_IV;
        GENERAL_CIRCUITS[6] = GENERAL_CIRCUIT_LUV;
        GENERAL_CIRCUITS[7] = GENERAL_CIRCUIT_ZPM;
        GENERAL_CIRCUITS[8] = GENERAL_CIRCUIT_UV;
        GENERAL_CIRCUITS[9] = GENERAL_CIRCUIT_UHV;
        GENERAL_CIRCUITS[10] = GENERAL_CIRCUIT_UEV;
        GENERAL_CIRCUITS[11] = GENERAL_CIRCUIT_UIV;
        GENERAL_CIRCUITS[12] = GENERAL_CIRCUIT_UXV;
        GENERAL_CIRCUITS[13] = GENERAL_CIRCUIT_OPV;
        GENERAL_CIRCUITS[14] = GENERAL_CIRCUIT_MAX;
    }

    public static ItemEntry<Item> GREAT_ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("great_astronomy_circuit_1", Item::new)
            .cnlang("完善的一阶航天数据芯片")
            .lang("Great Astronomy Circuit I")
            .register();
    public static ItemEntry<ComponentItem> SIMPLE_NUTRITIOUS_MEAL = REGISTRATE
            .item("simple_nutritious_meal", ComponentItem::create)
            .cnlang("简易营养餐")
            .lang("Simple Nutritious meal")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(simpleNutritiousMealTooltip1.translate()
                        .withStyle(ChatFormatting.GRAY));
            })))
            .register();
    public static ItemEntry<ComponentItem> ECOLOGICAL_STAR = REGISTRATE
            .item("ecological_star", ComponentItem::create)
            .cnlang("生态之星")
            .lang("Ecological Star")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(itemEcologicalStarDesc.translate().withStyle(ChatFormatting.GREEN));
            })))
            .register();
    public static ItemEntry<ComponentItem> SCULK_CELL = REGISTRATE
            .item("sculk_cell", ComponentItem::create)
            .cnlang("幽匿干细胞")
            .lang("Sculk Cell")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(itemSculkCellDesc.translate().withStyle(ChatFormatting.DARK_GRAY));
            })))
            .register();
    public static ItemEntry<Item> ANIMAL_EXCRETA = REGISTRATE
            .item("animal_excreta", Item::new)
            .cnlang("动物排泄物")
            .lang("Animal Excreta")
            .register();
    public static ItemEntry<Item> TUMOR = REGISTRATE
            .item("tumor", Item::new)
            .cnlang("肿瘤")
            .lang("Tumor")
            .register();
    public static ItemEntry<Item> REFINED_IRON_INGOT = REGISTRATE
            .item("refined_iron_ingot", Item::new)
            .cnlang("精炼铁方坯")
            .lang("Refined Iron Ingot")
            .register();
    public static ItemEntry<ComponentItem> BAUXITE_PROCESS_CATALYST = REGISTRATE
            .item("bauxite_process_catalyst", ComponentItem::create)
            .cnlang("铝土矿处理催化剂")
            .lang("Bauxite Process Catalyst")
            .onRegister(attach(new TooltipBehavior(
                    list -> list.add(Component.literal("村庄里的图书管理员掌握这个古老的技术，成为村庄英雄后他就会传授给你")))))
            .register();
    public static ItemEntry<Item> CRYSTAL_CATALYST = REGISTRATE
            .item("crystal_catalyst", Item::new)
            .cnlang("水晶催化剂")
            .lang("Crystal Catalyst")
            .register();

    public static ItemEntry<ComponentItem> ANTI_INF_MATTER = REGISTRATE
            .item("anti_inf_matter", ComponentItem::create)
            .cnlang("§0反无穷聚合体")
            .lang("§0Anti Inf Matter")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.anti_inf_matter.1").withStyle(ChatFormatting.BLACK));
                list.add(Component.translatable("ctnh.anti_inf_matter.2").withStyle(ChatFormatting.DARK_GRAY));
            })))
            .register();
    public static ItemEntry<ComponentItem> TESTING_TERMINAL = REGISTRATE
            .item("testing_terminal", ComponentItem::create)
            .cnlang("检测终端")
            .lang("Test Terminal")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new TestingTerminalBehavior()))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(testingTerminalTooltip1.translate().withStyle(ChatFormatting.GRAY));
                list.add(testingTerminalTooltip2.translate());
                list.add(testingTerminalTooltip3.translate());
            })))
            .register();
    public static ItemEntry<MEAdvancedTerminalItem> ME_ADVANCED_TERMINAL = REGISTRATE
            .item("me_advanced_terminal", MEAdvancedTerminalItem::new)
            .cnlang("GT-MBST-A v7.0.1")
            .lang("GT-MBST-A v7.0.1")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new MEAdvancedTerminalBehavior()))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(meAdvancedTerminalTooltip1.translate());
                list.add(meAdvancedTerminalTooltip2.translate().withStyle(ChatFormatting.GRAY));
            })))
            .register();
    public static ItemEntry<AstronomyCircuitItem> ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("astronomy_circuit_1",
                    properties -> new AstronomyCircuitItem(properties, 1, GREAT_ASTRONOMY_CIRCUIT_1))
            .cnlang("一阶航天数据芯片")
            .lang("Astronomy Circuit I")
            .register();
    public static ItemEntry<IDroneItem> PV_DRONE_PROTOTYPE = REGISTRATE
            .item("photovoltaic_drone_prototype", holder -> new IDroneItem(holder, 0, 512, 16, () -> Items.AIR))
            .cnlang("光伏无人机原型")
            .lang("pv_drone_prototype")
            .register();
    public static ItemEntry<IDroneItem> PV_DRONE_TIER1 = REGISTRATE
            .item("photovoltaic_drone_tier1", holder -> new IDroneItem(holder, 1, 8192 * 2, 64, () -> Items.AIR))
            .cnlang("标准化光伏无人机")
            .lang("pv_drone_tier1")
            .register();
    public static ItemEntry<IDroneItem> PV_DRONE_TIER2 = REGISTRATE
            .item("photovoltaic_drone_tier2", holder -> new IDroneItem(holder, 2, 32678 * 2, 256, () -> Items.AIR))
            .cnlang("共振结构化光伏无人机")
            .lang("pv_drone_tier2")
            .register();
    public static ItemEntry<ConnectTerminalItem> PV_TERMINAL = REGISTRATE
            .item("pv_terminal", holder -> new ConnectTerminalItem(holder))
            .cnlang("光伏绑定终端")
            .lang("pv_terminal")
            .register();
    public static ItemEntry<MultiblockHelper> multiblockHelper = REGISTRATE
            .item("mutiblock_helper", holder -> new MultiblockHelper(holder))
            .lang("mutiblock_helper")
            .register();
    public static ItemEntry<IDataItem> RESEARCH_DATASET = REGISTRATE
            .item("research_dataset", holder -> new IDataItem(holder))
            .cnlang("研究数据集")
            .lang("research_dataset")
            .register();
    public static ItemEntry<IDataItem> RESEARCH_DATASET_LIVING_MATERIAL = REGISTRATE
            .item("research_dataset_lm", holder -> new IDataItem(holder))
            .cnlang("研究数据集：活体金属")
            .lang("research_dataset_lm")
            .register();

    public static ItemEntry<Item> NUCLEAR_WASTE = REGISTRATE
            .item("nuclear_waste", Item::new)
            .cnlang("核废料")
            .lang("Nuclear Waste")
            .register();
    public static ItemEntry<Item> LEVEL_ITEM = REGISTRATE
            .item("level", Item::new)
            .lang("level")
            .register();
    public static ItemEntry<ProgramItem> PROGRAM_EMPTY = REGISTRATE
            .item("program_empty", ProgramItem::new)
            .cnlang("空白程序")
            .lang("Empty Program")
            .register();
    public static ItemEntry<ProgramItem> PROGRAM_ROCKET_CORE_1 = REGISTRATE
            .item("program_rocket_core_1", ProgramItem::new)
            .cnlang("一阶火箭核心代码")
            .lang("Tier 1 Rocket Core Program")
            .register();
    public static ItemEntry<ProgramItem> PROGRAM_ROCKET_1 = REGISTRATE
            .item("program_rocket_1", ProgramItem::new)
            .cnlang("一阶火箭控制代码")
            .lang("Tier 1 Rocket Control Program")
            .register();

    @CN("§7中子活化器在速度不达标时运行配方会产生废料")
    @EN("§7Neutron Activator will produce waste when the speed is insufficient")
    static Lang radioactive_waste;
    public static ItemEntry<ComponentItem> RADIOACTIVE_WASTE = REGISTRATE
            .item("radioactive_waste", ComponentItem::create)
            .cnlang("放射性废料")
            .lang("Radioactive Waste")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(radioactive_waste.translate()))))
            .register();

    @CN("§7用于制作T1重型合金板")
    @EN("§7Used for making Heavy Alloy Plate T1")
    static Lang heavy_ingot_t1;
    public static ItemEntry<ComponentItem> HEAVY_INGOT_T1 = REGISTRATE
            .item("heavy_ingot_t1", ComponentItem::create)
            .cnlang("T1重型锭")
            .lang("Heavy Alloy Ingot T1")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_ingot_t1.translate()))))
            .register();
    @CN("§7用于制作T2重型合金板")
    @EN("§7Used for making Heavy Alloy Plate T2")
    static Lang heavy_ingot_t2;
    public static ItemEntry<ComponentItem> HEAVY_INGOT_T2 = REGISTRATE
            .item("heavy_ingot_t2", ComponentItem::create)
            .cnlang("T2重型锭")
            .lang("Heavy Alloy Ingot T2")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_ingot_t2.translate()))))
            .register();
    @CN("§7用于制作T3重型合金板")
    @EN("§7Used for making Heavy Alloy Plate T3")
    static Lang heavy_ingot_t3;
    public static ItemEntry<ComponentItem> HEAVY_INGOT_T3 = REGISTRATE
            .item("heavy_ingot_t3", ComponentItem::create)
            .cnlang("T3重型锭")
            .lang("Heavy Alloy Ingot T3")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_ingot_t3.translate()))))
            .register();
    @CN("§7用于制作T4重型合金板")
    @EN("§7Used for making Heavy Alloy Plate T4")
    static Lang heavy_ingot_t4;
    public static ItemEntry<ComponentItem> HEAVY_INGOT_T4 = REGISTRATE
            .item("heavy_ingot_t4", ComponentItem::create)
            .cnlang("T4重型锭")
            .lang("Heavy Alloy Ingot T4")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_ingot_t4.translate()))))
            .register();

    @CN("§71阶")
    @EN("§7T1")
    static Lang heavy_plate_t1;
    public static ItemEntry<ComponentItem> HEAVY_PLATE_T1 = REGISTRATE
            .item("heavy_plate_t1", ComponentItem::create)
            .cnlang("T1重型合金板")
            .lang("Heavy Alloy Plate T1")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_plate_t1.translate()))))
            .register();
    @CN("§72阶")
    @EN("§7T2")
    static Lang heavy_plate_t2;
    public static ItemEntry<ComponentItem> HEAVY_PLATE_T2 = REGISTRATE
            .item("heavy_plate_t2", ComponentItem::create)
            .cnlang("T2重型合金板")
            .lang("Heavy Alloy Plate T2")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_plate_t2.translate()))))
            .register();
    @CN("§73阶")
    @EN("§7T3")
    static Lang heavy_plate_t3;
    public static ItemEntry<ComponentItem> HEAVY_PLATE_T3 = REGISTRATE
            .item("heavy_plate_t3", ComponentItem::create)
            .cnlang("T3重型合金板")
            .lang("Heavy Alloy Plate T3")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_plate_t3.translate()))))
            .register();
    @CN("§74阶")
    @EN("§7T4")
    static Lang heavy_plate_t4;
    public static ItemEntry<ComponentItem> HEAVY_PLATE_T4 = REGISTRATE
            .item("heavy_plate_t4", ComponentItem::create)
            .cnlang("T4重型合金板")
            .lang("Heavy Alloy Plate T4")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(heavy_plate_t4.translate()))))
            .register();

    @CN("§7用于制作1阶火箭")
    @EN("§7Used for making Rocket T1")
    static Lang chip_t1;
    public static ItemEntry<ComponentItem> CHIP_T1 = REGISTRATE
            .item("t1_chip", ComponentItem::create)
            .cnlang("T1芯片")
            .lang("Chip T1")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(chip_t1.translate()))))
            .register();
    @CN("§7用于制作2阶火箭")
    @EN("§7Used for making Rocket T2")
    static Lang chip_t2;
    public static ItemEntry<ComponentItem> CHIP_T2 = REGISTRATE
            .item("t2_chip", ComponentItem::create)
            .cnlang("T2芯片")
            .lang("Chip T2")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(chip_t2.translate()))))
            .register();
    @CN("§7用于制作3阶火箭")
    @EN("§7Used for making Rocket T3")
    static Lang chip_t3;
    public static ItemEntry<ComponentItem> CHIP_T3 = REGISTRATE
            .item("t3_chip", ComponentItem::create)
            .cnlang("T3芯片")
            .lang("Chip T3")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(chip_t3.translate()))))
            .register();
    @CN("§7用于制作4阶火箭")
    @EN("§7Used for making Rocket T4")
    static Lang chip_t4;
    public static ItemEntry<ComponentItem> CHIP_T4 = REGISTRATE
            .item("t4_chip", ComponentItem::create)
            .cnlang("T4芯片")
            .lang("Chip T4")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(text -> text.add(chip_t4.translate()))))
            .register();

    public static ItemEntry<ComponentItem> INVERTER = REGISTRATE
            .item("inverter", ComponentItem::create)
            .cnlang("逆变器")
            .lang("Inverter")
            .register();

    public static ItemEntry<ComponentItem> EncapsulatedUranium = REGISTRATE
            .item("encapsulated_uranium", ComponentItem::create)
            .cnlang("封装铀")
            .lang("Encapsulated Uranium")
            .register();

    public static ItemEntry<ComponentItem> EnrichedUraniumNugget = REGISTRATE
            .item("enriched_uranium_nugget", ComponentItem::create)
            .cnlang("浓缩铀粒")
            .lang("Enriched Uranium Nugget")
            .register();

    public static ItemEntry<ComponentItem> EnrichedUranium = REGISTRATE
            .item("enriched_uranium", ComponentItem::create)
            .cnlang("浓缩铀")
            .lang("Enriched Uranium")
            .register();

    public static ItemEntry<ComponentItem> EncapsulatedThorium = REGISTRATE
            .item("encapsulated_thorium", ComponentItem::create)
            .cnlang("封装钍")
            .lang("Encapsulated Thorium")
            .register();
    public static ItemEntry<ComponentItem> EnrichedThoriumNugget = REGISTRATE
            .item("enriched_thorium_nugget", ComponentItem::create)
            .cnlang("浓缩钍粒")
            .lang("Enriched Thorium Nugget")
            .register();

    public static ItemEntry<ComponentItem> EnrichedThorium = REGISTRATE
            .item("enriched_thorium", ComponentItem::create)
            .cnlang("浓缩钍")
            .lang("Enriched Thorium")
            .register();

    public static ItemEntry<ComponentItem> EncapsulatedPlutonium = REGISTRATE
            .item("encapsulated_plutonium", ComponentItem::create)
            .cnlang("封装钚")
            .lang("Encapsulated Plutonium")
            .register();

    public static ItemEntry<ComponentItem> EnrichedPlutoniumNugget = REGISTRATE
            .item("enriched_plutonium_nugget", ComponentItem::create)
            .cnlang("浓缩钚粒")
            .lang("Enriched Plutonium Nugget")
            .register();

    public static ItemEntry<ComponentItem> EnrichedPlutonium = REGISTRATE
            .item("enriched_plutonium", ComponentItem::create)
            .cnlang("浓缩钚")
            .lang("Enriched Plutonium")
            .register();

    public static ItemEntry<ComponentItem> NeutronSource = REGISTRATE
            .item("neutron_source", ComponentItem::create)
            .cnlang("中子源")
            .lang("Neutron Source")
            .register();

    public static ItemEntry<ComponentItem> QuarkCore = REGISTRATE
            .item("quark_core", ComponentItem::create)
            .cnlang("夸克核心")
            .lang("Quark Core")
            .register();

    public static ItemEntry<ComponentItem> PlateRadiationProtection = REGISTRATE
            .item("plate_radiation_protection", ComponentItem::create)
            .cnlang("防辐射板")
            .lang("Radiation Protection Plate")
            .register();

    public static ItemEntry<ComponentItem> COMPUTER = REGISTRATE
            .item("computer_circuit", ComponentItem::create)
            .cnlang("计算机芯片")
            .lang("Computer Chip")
            .properties(properties -> properties.rarity(Rarity.UNCOMMON))
            .register();

    public static ItemEntry<ComponentItem> COMPUTER_ADVANCED = REGISTRATE
            .item("computer_advanced_circuit", ComponentItem::create)
            .cnlang("高级计算机芯片")
            .lang("Advanced Computer Chip")
            .properties(properties -> properties.rarity(Rarity.RARE))
            .register();

    public static ItemEntry<ComponentItem> ADVANCED_RAM_WAFER = REGISTRATE
            .item("advanced_ram_wafer", ComponentItem::create)
            .cnlang("进阶RAM晶圆")
            .lang("Advanced Ram Wafer")
            .properties(p -> new Item.Properties().rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(advancedRamWaferTooltip.translate().withStyle(ChatFormatting.YELLOW));
            })))
            .register();
    public static ItemEntry<ComponentItem> ADVANCED_RAM_CHIP = REGISTRATE
            .item("advanced_ram_chip", ComponentItem::create)
            .cnlang("进阶RAM芯片")
            .lang("Advanced Ram Chip")
            .properties(p -> new Item.Properties().rarity(Rarity.UNCOMMON))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(advancedRamChipTooltip.translate().withStyle(ChatFormatting.YELLOW));
            })))
            .register();
    public static ItemEntry<Item> BSC_CHIP = REGISTRATE
            .item("bsc_chip", Item::new)
            .cnlang("BSC芯片")
            .lang("BSC Chip")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov, CTNHCore.id("item/chips/bsc_chip")))
            .register();
    public static ItemEntry<Item> BSC_WAFER = REGISTRATE
            .item("bsc_wafer", Item::new)
            .cnlang("BSC晶圆")
            .lang("BSC Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov, CTNHCore.id("item/chips/bsc_wafer")))
            .register();
    public static ItemEntry<Item> BSC_WAFER_MASKED = REGISTRATE
            .item("bsc_wafer_rubber_masked", Item::new)
            .cnlang("橡胶掩膜的BSC晶圆")
            .lang("Rubber-masked BSC Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov,
                    CTNHCore.id("item/chips/bsc_wafer_rubber_masked")))
            .register();
    public static ItemEntry<Item> LPIC_WAFER_MASKED = REGISTRATE
            .item("lpic_wafer_rubber_masked", Item::new)
            .cnlang("橡胶掩膜的LPIC晶圆")
            .lang("Rubber-masked LPIC Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov,
                    CTNHCore.id("item/chips/lpic_wafer_rubber_masked")))
            .register();
    public static ItemEntry<Item> RAM_WAFER_MASKED = REGISTRATE
            .item("ram_wafer_rubber_masked", Item::new)
            .cnlang("橡胶掩膜的RAM晶圆")
            .lang("Rubber-masked RAM Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov,
                    CTNHCore.id("item/chips/ram_wafer_rubber_masked")))
            .register();
    public static ItemEntry<Item> SSOC_WAFER_RUBBER_MASKED = REGISTRATE
            .item("ssoc_wafer_rubber_masked", Item::new)
            .cnlang("橡胶掩膜的简易SoC晶圆")
            .lang("Rubber-masked Simple SoC Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov,
                    CTNHCore.id("item/chips/ssoc_wafer_rubber_masked")))
            .register();
    public static ItemEntry<Item> RUBBER_MASKED_SILICON_WAFER = REGISTRATE
            .item("rubber_masked_silicon_wafer", Item::new)
            .cnlang("橡胶掩膜的硅晶圆")
            .lang("Rubber-masked Silicon Wafer")
            .model((ctx, prov) -> GTModels.createTextureModel(ctx, prov, GTCEu.id("item/naquadah_wafer")))
            .register();
    public static ItemEntry<ComponentItem> PRIMARY_STEW = REGISTRATE
            .item("primary_stew", ComponentItem::create)
            .cnlang("初级煲")
            .lang("Primary Stew")
            .properties(p -> new Item.Properties().rarity(Rarity.EPIC))
            .onRegister(attach(new FoodStats(new FoodProperties.Builder()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                    .build())))
            .onRegister(attach(new TooltipBehavior(list -> {})))
            .register();
    public static ItemEntry<ComponentItem> GALAXY_MEATBALL = REGISTRATE
            .item("galaxy_meatball", ComponentItem::create)
            .cnlang("银河肉丸")
            .lang("Galaxy Meatball")
            .properties(p -> new Item.Properties().rarity(Rarity.EPIC))
            .onRegister(attach(new FoodStats(new FoodProperties.Builder()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                    .build())))
            .onRegister(attach(new TooltipBehavior(list -> {})))
            .register();

    public static ItemEntry<ReloadItem> RELOAD_ITEM = REGISTRATE
            .item("recipe_reload_item", ReloadItem::new)
            .model(NonNullBiConsumer.noop())
            .register();

    public static ItemEntry<ComponentItem> VOLTAGE_COIL_UHV = REGISTRATE
            .item("uhv_voltage_coil", ComponentItem::create)
            .cnlang("极高压线圈")
            .lang("Ultra High Voltage Coil")
            .onRegister(materialInfo(new ItemMaterialInfo(new MaterialStack(ADAMANTITE, GTValues.M * 2),
                    new MaterialStack(SAMARIUM_DYSPROSIUM_TERBIUM_PERMANENT_MAGNET_ALLOY_MAGNETIC, GTValues.M / 2))))
            .register();
    public static ItemEntry<ComponentItem> CREATIVE_ENERGY_COVER_ITEM = REGISTRATE
            .item("creative_energy_cover", ComponentItem::create)
            .cnlang("创造能源覆盖板")
            .lang("Creative Energy Cover")
            .onRegister(attach(new CoverPlaceBehavior(CREATIVE_ENERGY_COVER_DEF),
                    new TooltipBehavior(lines -> CREATIVE_TOOLTIPS.accept(ItemStack.EMPTY, lines))))
            .register();

    public static ItemEntry<ComponentItem> HIGH_QUALITY_SOLID_FUEL = REGISTRATE
            .item("high_quality_solid_fuel", ComponentItem::create)
            .cnlang("高品质固体燃料")
            .lang("High Quality Solid Fuel")
            .onRegister(item -> item.burnTime(4800))
            .register();
    public static ItemEntry<Item> THERMOMETER_CASE = REGISTRATE
            .item("thermometer_case", Item::new)
            .cnlang("温度计外壳")
            .lang("Thermometer Case")
            .register();
    public static ItemEntry<Item> SPACE_FABRIC = REGISTRATE
            .item("space_fabric", Item::new)
            .cnlang("太空织物")
            .lang("Space Fabric")
            .register();
    public static ItemEntry<Item> TEMPERATURE_KEEPING_DEVICE = REGISTRATE
            .item("temperature_keeping_device", Item::new)
            .cnlang("保温装置")
            .lang("Temperature Keeping Device")
            .register();
    public static ItemEntry<Item> ECHO_CIRCUIT_BOARD = REGISTRATE
            .item("echo_circuit_board", Item::new)
            .cnlang("回响电路板")
            .lang("Echo Circuit Board")
            .register();
    public static ItemEntry<Item> ECHO_PRINTED_CIRCUIT_BOARD = REGISTRATE
            .item("echo_printed_circuit_board", Item::new)
            .cnlang("回响印制电路板")
            .lang("Echo Printed Circuit Board")
            .register();
    public static ItemEntry<ComponentItem> ECHO_PROCESSOR = REGISTRATE
            .item("echo_processor", ComponentItem::create)
            .cnlang("回响处理器")
            .lang("Echo Processor")
            .tag(CustomTags.ZPM_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> list.add(Component.literal("ZPM级电路板").withStyle(ChatFormatting.DARK_AQUA)))))
            .register();
    public static ItemEntry<ComponentItem> ECHO_PROCESSOR_ASSEMBLY = REGISTRATE
            .item("echo_processor_assembly", ComponentItem::create)
            .cnlang("回响处理器装配")
            .lang("Echo Processor Assembly")
            .tag(CustomTags.UV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> list.add(Component.literal("UV级电路板").withStyle(ChatFormatting.DARK_AQUA)))))
            .register();
    public static ItemEntry<ComponentItem> ECHO_PROCESSOR_COMPUTER = REGISTRATE
            .item("echo_processor_computer", ComponentItem::create)
            .cnlang("回响处理器计算机")
            .lang("Echo Processor Computer")
            .tag(CustomTags.UHV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> list.add(Component.literal("UHV级电路板").withStyle(ChatFormatting.DARK_AQUA)))))
            .register();
    public static ItemEntry<ComponentItem> ECHO_PROCESSOR_MAINFRAME = REGISTRATE
            .item("echo_processor_mainframe", ComponentItem::create)
            .cnlang("回响处理器主机")
            .lang("Echo Processor Mainframe")
            .tag(CustomTags.UEV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> list.add(Component.literal("UEV级电路板").withStyle(ChatFormatting.DARK_AQUA)))))
            .register();

    @CN("模块化位处理器")
    @EN("Modular Bit Processor")
    public static Lang modularBitProcessorTooltip;

    @CN("LV级电路板")
    @EN("LV Tier Circuit")
    public static Lang lvTierCircuitTooltip;
    @CN("MV级电路板")
    @EN("MV Tier Circuit")
    public static Lang mvTierCircuitTooltip;
    @CN("HV级电路板")
    @EN("HV Tier Circuit")
    public static Lang hvTierCircuitTooltip;
    @CN("EV级电路板")
    @EN("EV Tier Circuit")
    public static Lang evTierCircuitTooltip;

    public static ItemEntry<Item> SPECIAL_PLASTIC_CIRCUIT_BOARD = REGISTRATE
            .item("special_plastic_circuit_board", Item::new)
            .cnlang("特种塑料电路基板")
            .lang("Special Plastic Circuit Board")
            .register();
    public static ItemEntry<Item> SPECIAL_PLASTIC_PRINTED_CIRCUIT_BOARD = REGISTRATE
            .item("special_plastic_printed_circuit_board", Item::new)
            .cnlang("特种塑料印刷电路基板")
            .lang("Special Plastic Printed Circuit Board")
            .register();
    public static ItemEntry<ComponentItem> PRECISION_CIRCUIT = REGISTRATE
            .item("precision_circuit", ComponentItem::create)
            .cnlang("精密电路")
            .lang("Precision Circuit")
            .tag(CustomTags.LV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> {
                        list.add(modularBitProcessorTooltip.translate().withStyle(ChatFormatting.GRAY));
                        list.add(lvTierCircuitTooltip.translate().withStyle(style -> style.withColor(0x8B4513)));
                    })))
            .register();
    public static ItemEntry<ComponentItem> PRECISION_CIRCUIT_ASSEMBLY = REGISTRATE
            .item("precision_circuit_assembly", ComponentItem::create)
            .cnlang("精密电路集群")
            .lang("Precision Circuit Assembly")
            .tag(CustomTags.MV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> {
                        list.add(modularBitProcessorTooltip.translate().withStyle(ChatFormatting.GRAY));
                        list.add(mvTierCircuitTooltip.translate().withStyle(style -> style.withColor(0x8B4513)));
                    })))
            .register();
    public static ItemEntry<ComponentItem> PRECISION_CIRCUIT_COMPUTER = REGISTRATE
            .item("precision_circuit_computer", ComponentItem::create)
            .cnlang("精密电路计算机")
            .lang("Precision Circuit Computer")
            .tag(CustomTags.HV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> {
                        list.add(modularBitProcessorTooltip.translate().withStyle(ChatFormatting.GRAY));
                        list.add(hvTierCircuitTooltip.translate().withStyle(style -> style.withColor(0x8B4513)));
                    })))
            .register();
    public static ItemEntry<ComponentItem> PRECISION_CIRCUIT_MAINFRAME = REGISTRATE
            .item("precision_circuit_mainframe", ComponentItem::create)
            .cnlang("精密电路主机")
            .lang("Precision Circuit Mainframe")
            .tag(CustomTags.EV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(
                    list -> {
                        list.add(modularBitProcessorTooltip.translate().withStyle(ChatFormatting.GRAY));
                        list.add(evTierCircuitTooltip.translate().withStyle(style -> style.withColor(0x8B4513)));
                    })))
            .register();

    public static ItemEntry<Item> BIOLOGICAL_PATCH_TRANSISTOR = REGISTRATE
            .item("biological_patch_transistor", Item::new)
            .cnlang("生物贴片晶体管")
            .lang("Biological Patch Transistor")
            .register();
    public static ItemEntry<Item> BIOLOGICAL_PATCH_RESISTOR = REGISTRATE
            .item("biological_patch_resistor", Item::new)
            .cnlang("生物贴片电阻")
            .lang("Biological Patch Resistor")
            .register();
    public static ItemEntry<Item> BIOLOGICAL_PATCH_CAPACITOR = REGISTRATE
            .item("biological_patch_capacitor", Item::new)
            .cnlang("生物贴片电容器")
            .lang("Biological Patch Capacitor")
            .register();
    public static ItemEntry<Item> BIOLOGICAL_PATCH_DIODE = REGISTRATE
            .item("biological_patch_diode", Item::new)
            .cnlang("生物贴片二极管")
            .lang("Biological Patch Diode")
            .register();
    public static ItemEntry<Item> BIOLOGICAL_PATCH_INDUCTOR = REGISTRATE
            .item("biological_patch_inductor", Item::new)
            .cnlang("生物贴片电感器")
            .lang("Biological Patch Inductor")
            .register();
    public static ItemEntry<ComponentItem> SCP_500_BASE = REGISTRATE
            .item("scp_500_base", ComponentItem::create)
            .cnlang("SCP-500基底")
            .lang("SCP-500 Base")
            .onRegister(item -> item.attachComponents(
                    new FoodStats(new FoodProperties.Builder()
                            .alwaysEat()
                            .fast()
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 19980, 10), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 19980, 3), 1.0f)
                            .build()),
                    new IInteractionItem() {

                        @Override
                        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
                            if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
                                runCommand(player,
                                        "title @s title {\"text\":\"你在短时间内你将获得强大的恢复能力\",\"color\":\"red\"}");
                            }
                            return stack;
                        }
                    }))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.literal("SCP-500基底可以提供强大的恢复能力"));
            })))
            .register();
    public static ItemEntry<ComponentItem> SCP_500 = REGISTRATE
            .item("scp_500", ComponentItem::create)
            .cnlang("SCP-500")
            .lang("SCP-500")
            .onRegister(item -> item.attachComponents(
                    new FoodStats(new FoodProperties.Builder()
                            .alwaysEat()
                            .fast()
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 19980, 10), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 19980, 3), 1.0f)
                            .build()),
                    new IInteractionItem() {

                        @Override
                        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
                            if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {
                                runCommand(player, "medical_condition clear @p");
                                runCommand(player, "title @s title {\"text\":\"你的所有疾病已被治愈\",\"color\":\"green\"}");
                                runCommand(player,
                                        "title @s subtitle {\"text\":\"在短时间内你将获得强大的恢复能力\",\"color\":\"red\"}");
                            }
                            return stack;
                        }
                    }))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.literal("SCP-500可以治愈所有疾病"));
                list.add(Component.literal("在短时间内获得强大的恢复能力"));
            })))
            .register();

    public static ItemEntry<Item> CRASHED_RICE = REGISTRATE
            .item("crashed_rice", Item::new)
            .cnlang("嚼碎的饭团")
            .lang("Crashed Rice")
            .register();
    public static ItemEntry<Item> RUBBER_POWDER = REGISTRATE
            .item("rubber_powder", Item::new)
            .cnlang("预处理橡胶粉")
            .lang("Rubber Powder")
            .register();
    public static ItemEntry<Item> CLGS_ELECTRODE = REGISTRATE
            .item("clgs_electrode", Item::new)
            .cnlang("clgs阳光复合电级")
            .lang("CLGS Electrode")
            .register();
    public static ItemEntry<Item> THERMOTOLERANT_DISH = REGISTRATE
            .item("thermotolerant_dish", Item::new)
            .cnlang("耐热光性培养基")
            .lang("Thermotolerant Dish")
            .register();
    public static ItemEntry<Item> CLGS = REGISTRATE
            .item("clgs", Item::new)
            .cnlang("Cu(In, Ga)Se2阳光电池")
            .lang("CLGS Cell")
            .register();
    public static ItemEntry<Item> MEASUREMENT_PV_CELL = REGISTRATE
            .item("measurement_pv_cell", Item::new)
            .cnlang("测定光伏电池组")
            .lang("Measurement PV Cell")
            .register();
    public static ItemEntry<Item> PV_DRONE_RESEARCH_1 = REGISTRATE
            .item("pv_drone_research_1", Item::new)
            .cnlang("光伏无人机研究I")
            .lang("PV Drone Research I")
            .register();
    public static ItemEntry<Item> PV_DRONE_RESEARCH_2 = REGISTRATE
            .item("pv_drone_research_2", Item::new)
            .cnlang("光伏无人机研究II")
            .lang("PV Drone Research II")
            .register();
    public static ItemEntry<Item> PV_DRONE_RESEARCH_3 = REGISTRATE
            .item("pv_drone_research_3", Item::new)
            .cnlang("光伏无人机研究III")
            .lang("PV Drone Research III")
            .register();
    public static ItemEntry<Item> BRICK_MUD = REGISTRATE
            .item("brick_mud", Item::new)
            .cnlang("砖泥")
            .lang("Brick Mud")
            .register();
    public static ItemEntry<Item> BRICK_PREFORM = REGISTRATE
            .item("brick_preform", Item::new)
            .cnlang("砖胚")
            .lang("Brick Preform")
            .register();
    public static ItemEntry<Item> COKE_CLAY = REGISTRATE
            .item("coke_clay", Item::new)
            .cnlang("焦黏土")
            .lang("Coke Clay")
            .register();
    public static ItemEntry<Item> CIRCUIT_BOARD_M_ONE = REGISTRATE
            .item("circuit_board_m_one", Item::new)
            .cnlang("中间产物-M1")
            .lang("Circuit Board M1")
            .register();
    public static ItemEntry<Item> CIRCUIT_BOARD_M_TWO = REGISTRATE
            .item("circuit_board_m_two", Item::new)
            .cnlang("中间产物-M2")
            .lang("Circuit Board M2")
            .register();
    public static ItemEntry<Item> CIRCUIT_BOARD_M_THREE = REGISTRATE
            .item("circuit_board_m_three", Item::new)
            .cnlang("中间产物-M3")
            .lang("Circuit Board M3")
            .register();
    public static ItemEntry<Item> CIRCUIT_BOARD_M_FOUR = REGISTRATE
            .item("circuit_board_m_four", Item::new)
            .cnlang("中间产物-M4")
            .lang("Circuit Board M4")
            .register();

    public static ItemEntry<ComponentItem> METALLURGICAL_CATALYST = REGISTRATE
            .item("metallurgical_catalyst", ComponentItem::create)
            .cnlang("炼金催化剂")
            .lang("Metallurgical Catalyst")
            .onRegister(attach(new TooltipBehavior(list -> list.add(Component.literal("地狱的猪灵掌握这个技术，尝试与他们交易吧")))))
            .register();
    public static ItemEntry<ComponentItem> STONE_PROCESS_CATALYST = REGISTRATE
            .item("stone_process_catalyst", ComponentItem::create)
            .cnlang("石头粉处理催化剂")
            .lang("Stone Process Catalyst")
            .onRegister(
                    attach(new TooltipBehavior(list -> list.add(Component.literal("村庄里的石匠掌握这个古老的技术，成为村庄英雄后他就会传授给你")))))
            .register();
    public static ItemEntry<ComponentItem> PLATINUM_METAL_CATALYST_SHARD1 = REGISTRATE
            .item("platinum_metal_catalyst_shard1", ComponentItem::create)
            .cnlang("铂系金属催化碎片1")
            .lang("Platinum Metal Catalyst Shard 1")
            .onRegister(attach(new TooltipBehavior(list -> list.add(Component.literal("久远的时间使他们变成了水里的宝藏，通过钓鱼获得")))))
            .register();
    public static ItemEntry<ComponentItem> PLATINUM_METAL_CATALYST_SHARD2 = REGISTRATE
            .item("platinum_metal_catalyst_shard2", ComponentItem::create)
            .cnlang("铂系金属催化碎片2")
            .lang("Platinum Metal Catalyst Shard 2")
            .onRegister(attach(new TooltipBehavior(list -> list.add(Component.literal("深渊里的深潜一组掌握这个技术，尝试与他们交易吧")))))
            .register();
    public static ItemEntry<ComponentItem> YHARIM = REGISTRATE
            .item("yharim", ComponentItem::create)
            .cnlang("§6圣金源锭")
            .lang("Yharim Ingot")
            .onRegister(attach(
                    new TooltipBehavior(list -> list.add(Component.literal("你必须§6爱护蜜蜂§r才能激发这个锭的真正力量，哦你已经爱过蜜蜂了")))))
            .register();
    public static ItemEntry<ComponentItem> STRONGLY_INTERACTING_NEUTRON_REFLECTOR = REGISTRATE
            .item("strongly_interacting_neutron_reflector", ComponentItem::create)
            .cnlang("强相互作用力反射板")
            .lang("Strongly Interacting Neutron Reflector")
            .onRegister(attach(new TooltipBehavior(list -> list.add(Component.literal("§7硬度超越水滴")))))
            .register();
    public static ItemEntry<ComponentItem> COLORFUL_SOC = REGISTRATE
            .item("colorful_soc", ComponentItem::create)
            .cnlang("相变棱晶SOC")
            .lang("Colorful SOC")
            .onRegister(attach(new TooltipBehavior(list -> list.add(Component.literal("完美的色彩在此流动")))))
            .register();

    public static ItemEntry<Item> DEEP_DIVER_GEAR = REGISTRATE
            .item("deep_diver_gear", Item::new)
            .cnlang("深渊潜游装置")
            .lang("Deep Diver Gear")
            .tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.parse("curios:belt")))
            .register();
    public static ItemEntry<ComponentItem> TALLOW = REGISTRATE
            .item("tallow", ComponentItem::create)
            .cnlang("油脂")
            .lang("Tallow")
            .onRegister(item -> item.burnTime(1600))
            .register();
    public static ItemEntry<SnowCitySwordItem> SNOW_CITY_SWORD = REGISTRATE
            .item("snow_city_sword", SnowCitySwordItem::new)
            .cnlang("雪城的大保健")
            .lang("Snow City Sword")
            .properties(p -> p.rarity(Rarity.RARE).stacksTo(1))
            .register();
    public static ItemEntry<ArkOfHomoItem> ARK_OF_HOMO = REGISTRATE
            .item("ark_of_homo", ArkOfHomoItem::new)
            .cnlang("§c鸿§e蒙§9方§a舟")
            .lang("Ark of Homo")
            .properties(p -> p.rarity(Rarity.RARE).stacksTo(1))
            .register();
    public static ItemEntry<ComponentItem> ENDER_LIGHT = REGISTRATE
            .item("ender_light", ComponentItem::create)
            .cnlang("老灯的蜜汁小汉堡")
            .lang("Ender Light")
            .properties(p -> p.rarity(Rarity.RARE))
            .onRegister(attach(new FoodStats(new FoodProperties.Builder()
                    .nutrition(20)
                    .saturationMod(0.5f)
                    .meat()
                    .alwaysEat()
                    .fast()
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 1800, 0), 1.0f)
                    .effect(() -> {
                        var effect = ForgeRegistries.MOB_EFFECTS.getValue(
                                ResourceLocation.tryBuild("legendarysurvivaloverhaul", "heat_immunity"));
                        return effect != null ? new MobEffectInstance(effect, 20 * 1800, 0) : null;
                    }, 1.0f)
                    .effect(() -> {
                        var effect = ForgeRegistries.MOB_EFFECTS.getValue(
                                ResourceLocation.tryBuild("legendarysurvivaloverhaul", "cold_immunity"));
                        return effect != null ? new MobEffectInstance(effect, 20 * 1800, 0) : null;
                    }, 1.0f)
                    .build())))
            .register();

    private static void runCommand(ServerPlayer player, String command) {
        var server = player.getServer();
        if (server == null) return;
        server.getCommands().performPrefixedCommand(
                player.createCommandSourceStack(), command);
    }

    public static void init() {
        ChemicalItems.init();
        CrystalItems.init();
        registerGeneralCircuits();
    }
}
