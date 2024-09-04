package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.world.item.CreativeModeTab;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> MACHINE = REGISTRATE.defaultCreativeTab("machine",
                builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("machine", REGISTRATE))
                        .icon(() -> GTMachines.ELECTROLYZER[GTValues.LV].asStack())
                        .title(REGISTRATE.addLang("itemGroup", CTNHCore.id("machine"), "CTNH Machines"))
                        .build())
        .register();
    public static RegistryEntry<CreativeModeTab> ITEM = REGISTRATE.defaultCreativeTab("item",
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("item", REGISTRATE))
                            .icon(() -> CTNHItems.ASTRONOMY_CIRCUIT.asStack())
                            .title(REGISTRATE.addLang("itemGroup", CTNHCore.id("item"), "CTNH Items"))
                            .build())
            .register();

    public static void init() {

    }
}
