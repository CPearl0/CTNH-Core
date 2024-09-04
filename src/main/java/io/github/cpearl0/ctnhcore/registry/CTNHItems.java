package io.github.cpearl0.ctnhcore.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHItems {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.ITEM);
    }

    public static ItemEntry<AstronomyCircuitItem> ASTRONOMY_CIRCUIT_1 = REGISTRATE
            .item("astronomy_circuit_1", properties -> new AstronomyCircuitItem(properties, 1))
            .lang("Astronomy Circuit I")
            .register();

    public static void init() {

    }
}
