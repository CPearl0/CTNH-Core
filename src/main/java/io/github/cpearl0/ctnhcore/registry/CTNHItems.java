package io.github.cpearl0.ctnhcore.registry;

import appeng.core.AEConfig;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.cpearl0.ctnhcore.common.item.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.StringUtils;

import static com.gregtechceu.gtceu.common.data.GTItems.attach;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHItems {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.ITEM);
    }

    public static ItemEntry<Item> GREAT_ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("great_astronomy_circuit_1", Item::new)
            .lang("Great Astronomy Circuit I")
            .register();
    public static ItemEntry<ComponentItem> SIMPLE_NUTRITIOUS_MEAL = REGISTRATE
            .item("simple_nutritious_meal", ComponentItem::create)
            .lang("Simple Nutritious meal")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.simple_nutritious_meal.tooltip.1").withStyle(ChatFormatting.GRAY));
            })))
            .register();
    public static ItemEntry<ComponentItem> ECOLOGICAL_STAR = REGISTRATE
            .item("ecological_star", ComponentItem::create)
            .lang("Ecological Star")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("item.ctnh.ecological_star.desc").withStyle(ChatFormatting.GREEN));
            })))
            .register();
    public static ItemEntry<ComponentItem> SCULK_CELL = REGISTRATE
            .item("sculk_cell", ComponentItem::create)
            .lang("Sculk Cell")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("item.sculk_cell.desc").withStyle(ChatFormatting.DARK_GRAY));
            })))
            .register();
    public static ItemEntry<Item> ANIMAL_EXCRETA = REGISTRATE
            .item("animal_excreta",Item::new)
            .lang("Animal Excreta")
            .register();
    public static ItemEntry<Item> HORIZEN_RUNE = REGISTRATE
            .item("horizen_rune",Item::new)
            .lang("horizen_rune")
            .register();
    public static ItemEntry<Item> STARLIGHT_RUNE = REGISTRATE
            .item("starlight_rune",Item::new)
            .lang("starlight_rune")
            .register();
    public static ItemEntry<Item> TWIST_RUNE = REGISTRATE
            .item("twist_rune",Item::new)
            .lang("twist_rune")
            .register();
    public static ItemEntry<Item> QUASAR_RUNE = REGISTRATE
            .item("quasar_rune",Item::new)
            .lang("quasar_rune")
            .register();
    public static ItemEntry<Item> PROLIFERATION_RUNE = REGISTRATE
            .item("proliferation_rune",Item::new)
            .lang("proliferation_rune")
            .register();
    public static ItemEntry<ComponentItem> ANTI_INF_MATTER =REGISTRATE
            .item("anti_inf_matter",ComponentItem::create)
            .lang("anti_inf_matter")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.anti_inf_matter.1").withStyle(ChatFormatting.BLACK));
                list.add(Component.translatable("ctnh.anti_inf_matter.2").withStyle(ChatFormatting.DARK_GRAY));
            })))
            .register();
    public static ItemEntry<ComponentItem> ANTIHYDROGEN_CONTAINS =REGISTRATE
            .item("anti_h_contains",ComponentItem::create)
            .lang("anti_h_contains")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.anti_matter_contains").withStyle(ChatFormatting.DARK_RED));
            })))
            .register();
    public static ItemEntry<ComponentItem> ANTIOXYGEN_CONTAINS =REGISTRATE
            .item("anti_o_contains",ComponentItem::create)
            .lang("anti_o_contains")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.anti_matter_contains").withStyle(ChatFormatting.DARK_RED));
            })))
            .register();
    public static ItemEntry<Item> TUMOR = REGISTRATE
            .item("tumor",Item::new)
            .lang("Tumor")
            .register();
    public static ItemEntry<Item> REFINED_IRON_INGOT = REGISTRATE
            .item("refined_iron_ingot",Item::new)
            .lang("Refined Iron Ingot")
            .register();
    public static ItemEntry<Item> CORROSIVE_CORE = REGISTRATE
            .item("corrosive_core",Item::new)
            .lang("Corrosive Core")
            .register();
    public static ItemEntry<Item> VENGEFUL_CORE = REGISTRATE
            .item("vengeful_core",Item::new)
            .lang("Vengeful Core")
            .register();
    public static ItemEntry<Item> DESTRUCTIVE_CORE = REGISTRATE
            .item("destructive_core",Item::new)
            .lang("Destructive Core")
            .register();
    public static ItemEntry<Item> STEADFAST_CORE = REGISTRATE
            .item("steadfast_core",Item::new)
            .lang("Steadfast Core")
            .register();
    public static ItemEntry<ComponentItem> TESTING_TERMINAL = REGISTRATE
            .item("testing_terminal",ComponentItem::create)
            .lang("Test Terminal")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new TestingTerminalBehavior()))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.testing_terminal.tooltip.1").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("ctnh.testing_terminal.tooltip.2"));
            })))
            .register();
    public static ItemEntry<MEAdvancedTerminalItem> ME_ADVANCED_TERMINAL = REGISTRATE
            .item("me_advanced_terminal",
                    MEAdvancedTerminalItem::new
            )
            .lang("Me Advanced Terminal")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(new MEAdvancedTerminalBehavior()))
//            .model((ctx, prov) -> prov.generated(ctx))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.me_advanced_terminal.tooltip.1").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("ctnh.me_advanced_terminal.tooltip.2").withStyle(ChatFormatting.GRAY));
                list.add(Component.translatable("ctnh.me_advanced_terminal.tooltip.3").withStyle(ChatFormatting.GRAY));
            })))
            .register();

    public static ItemEntry<AstronomyCircuitItem> ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("astronomy_circuit_1", properties -> new AstronomyCircuitItem(properties, 1, GREAT_ASTRONOMY_CIRCUIT_1))
            .lang("Astronomy Circuit I")
            .register();
    public static ItemEntry<ThrowItem> BOSS_SUMMONER = REGISTRATE
            .item("boss_summoner", ThrowItem::new)
            .lang("Boss Summoner")
            .onRegister(attach(new BossSummonerBehavior(1)))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.boss_summoner.use").withStyle(ChatFormatting.RED));
            })))
            .register();

    public static ItemEntry<ThrowItem> ADVANCED_BOSS_SUMMONER = REGISTRATE
            .item("advanced_boss_summoner", ThrowItem::new)
            .lang("Advanced Boss Summoner")
            .onRegister(attach(new BossSummonerBehavior(2)))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.boss_summoner.use").withStyle(ChatFormatting.DARK_RED));
            })))
            .register();
    public static ItemEntry<IDroneItem>PV_DRONE_PROTOTYPE=REGISTRATE
            .item("photovoltaic_drone_prototype",holder->new IDroneItem(holder,0,512,16, () -> Items.AIR))
            .lang("pv_drone_prototype")
            .register(); // 确保调用 register() 方法
    public static ItemEntry<IDroneItem>PV_DRONE_TIER1=REGISTRATE
            .item("photovoltaic_drone_tier1",holder->new IDroneItem(holder,1,8192,64, () -> Items.AIR))
            .lang("pv_drone_tier1")
            .register(); // 确保调用 register() 方法
    public static ItemEntry<IDroneItem>PV_DRONE_TIER2=REGISTRATE
            .item("photovoltaic_drone_tier2",holder->new IDroneItem(holder,2,32678*2,256, () -> Items.AIR))
            .lang("pv_drone_tier2")
            .register(); // 确保调用 register() 方法
    public static ItemEntry<IDroneItem>MODULAR_DYSON_SWARM_T1=REGISTRATE
            .item("modular_dyson_swarm_tier1",holder->new IDroneItem(holder,4,32678*4*4,64, () -> Items.AIR))
            .lang("modular_dyson_swarm_tier1")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.dyson_tier1").withStyle(ChatFormatting.DARK_RED));
            })))
            .register(); // 确保调用 register() 方法
    public static ItemEntry<IDroneItem>MODULAR_DYSON_SWARM_T2=REGISTRATE
            .item("modular_dyson_swarm_tier2",holder->new IDroneItem(holder,8,32678*4*4*4,128, () -> Items.AIR))
            .lang("modular_dyson_swarm_tier2")
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.dyson_tier2").withStyle(ChatFormatting.DARK_RED));
            })))
            .register(); // 确保调用 register() 方法
    public static ItemEntry<ConnectTerminalItem> PV_TERMINAL=REGISTRATE
            .item("pv_terminal",holder->new ConnectTerminalItem(holder))
            .lang("pv_terminal")
            .register();
    public static ItemEntry<IDataItem> RESEARCH_DATASET=REGISTRATE
            .item("research_dataset",holder->new IDataItem(holder))
            .lang("research_dataset")
            .register();
    public static ItemEntry<IDataItem> RESEARCH_DATASET_LIVING_MATERIAL=REGISTRATE
            .item("research_dataset_lm",holder->new IDataItem(holder))
            .lang("research_dataset_lm")
            .register();
    public static ItemEntry<Item> NUCLEAR_WASTE = REGISTRATE
            .item("nuclear_waste", Item::new)
            .lang("Nuclear Waste")
            .register();
    public static ItemEntry<ProgramItem> PROGRAM_EMPTY = registerProgramItem("empty");
    public static ItemEntry<ProgramItem> PROGRAM_ROCKET_CORE_1 = registerProgramItem("rocket_core_1", "Tier 1 Rocket Core");
    public static ItemEntry<ProgramItem> PROGRAM_ROCKET_1 = registerProgramItem("rocket_1", "Tier 1 Rocket Control");

    public static ItemEntry<ProgramItem> registerProgramItem(String id) {
        return registerProgramItem(id, StringUtils.capitalize(id));
    }

    public static ItemEntry<ProgramItem> registerProgramItem(String id, String name) {
        return REGISTRATE.item("program_" + id, ProgramItem::new)
                .lang(name + " Program")
                .register();
    }

    public static void init() {

    }
}
