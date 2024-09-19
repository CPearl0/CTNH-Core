package io.github.cpearl0.ctnhcore.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.item.ProgramItem;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHItems {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.ITEM);
    }

    public static ItemEntry<Item> GREAT_ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("great_astronomy_circuit_1", Item::new)
            .lang("Great Astronomy Circuit I")
            .register();
    public static ItemEntry<AstronomyCircuitItem> ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("astronomy_circuit_1", properties -> new AstronomyCircuitItem(properties, 1, GREAT_ASTRONOMY_CIRCUIT_1))
            .lang("Astronomy Circuit I")
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
