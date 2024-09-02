package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.world.item.CreativeModeTab;

public class CTNHCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> MACHINE = CTNHRegistration.REGISTRATE.defaultCreativeTab("machine",
                builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("machine", CTNHRegistration.REGISTRATE))
                        .icon(() -> GTMachines.ELECTROLYZER[GTValues.LV].asStack())
                        .title(CTNHRegistration.REGISTRATE.addLang("itemGroup", CTNHCore.id("machine"), "CTNH Machines"))
                        .build())
        .register();

    public static void init() {

    }
}
