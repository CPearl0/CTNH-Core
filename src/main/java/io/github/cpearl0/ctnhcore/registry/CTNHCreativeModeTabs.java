package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.world.item.CreativeModeTab;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> MACHINE = REGISTRATE.defaultCreativeTab("machine",
                builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("machine", REGISTRATE))
                        .icon(CTNHMultiblockMachines.ASTRONOMICAL_OBSERVATORY::asStack)
                        .title(REGISTRATE.addLang("itemGroup", CTNHCore.id("machine"), "CTNH Machines"))
                        .build())
        .register();
    public static RegistryEntry<CreativeModeTab> ITEM = REGISTRATE.defaultCreativeTab("item",
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("item", REGISTRATE))
                            .icon(CTNHItems.ASTRONOMY_CIRCUIT_1::asStack)
                            .title(REGISTRATE.addLang("itemGroup", CTNHCore.id("item"), "CTNH Items"))
                            .build())
            .register();
    public static RegistryEntry<CreativeModeTab> BLOCK = REGISTRATE.defaultCreativeTab("block",
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("block", REGISTRATE))
                            .icon(CTNHBlocks.CASING_REFLECT_LIGHT::asStack)
                            .title(REGISTRATE.addLang("itemGroup", CTNHCore.id("block"), "CTNH Blocks"))
                            .build())
            .register();

    public static void init() {

    }
}
