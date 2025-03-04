package io.github.cpearl0.ctnhcore.registry;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.item.BossSummonerBehavior;
import io.github.cpearl0.ctnhcore.common.item.ProgramItem;
import io.github.cpearl0.ctnhcore.common.item.TestingTerminalBehavior;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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
    public static ItemEntry<AstronomyCircuitItem> ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("astronomy_circuit_1", properties -> new AstronomyCircuitItem(properties, 1, GREAT_ASTRONOMY_CIRCUIT_1))
            .lang("Astronomy Circuit I")
            .register();
    public static ItemEntry<ComponentItem> BOSS_SUMMONER = REGISTRATE
            .item("boss_summoner", ComponentItem::create)
            .lang("Boss Summoner")
            .onRegister(attach(new BossSummonerBehavior(1)))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.boss_summoner.use").withStyle(ChatFormatting.RED));
            })))
            .register();
    public static ItemEntry<ComponentItem> ADVANCED_BOSS_SUMMONER = REGISTRATE
            .item("advanced_boss_summoner", ComponentItem::create)
            .lang("Advanced Boss Summoner")
            .onRegister(attach(new BossSummonerBehavior(2)))
            .onRegister(attach(new TooltipBehavior(list -> {
                list.add(Component.translatable("ctnh.boss_summoner.use").withStyle(ChatFormatting.RED));
            })))
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
