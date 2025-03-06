package io.github.cpearl0.ctnhcore.common;

import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import io.github.cpearl0.ctnhcore.CTNHConfig;
import io.github.cpearl0.ctnhcore.data.CTNHCoreDatagen;
import io.github.cpearl0.ctnhcore.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {
    public CommonProxy() {
        init();
        IEventBus modEventBus = FMLJavaModLoadingContext
                .get().getModEventBus();
        modEventBus.addListener(this::addMaterialFlag);
    }

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CTNHCreativeModeTabs.init();
        CTNHBlockEntities.init();
        CTNHRegistration.REGISTRATE.registerRegistrate();

        CTNHRecipes.init(modEventBus);
        CTNHTemperatureModifierRegister.init();
        CTNHCoreDatagen.init();
        CTNHConfig.init();
    }

    public void addMaterialFlag(MaterialEvent event) {
        GTMaterialAddon.init();
    }
}
