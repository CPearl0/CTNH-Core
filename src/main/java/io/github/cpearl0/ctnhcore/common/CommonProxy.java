package io.github.cpearl0.ctnhcore.common;

import io.github.cpearl0.ctnhcore.Config;
import io.github.cpearl0.ctnhcore.data.CTNHCoreDatagen;
import io.github.cpearl0.ctnhcore.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {
    public CommonProxy() {
        init();
    }

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CTNHCreativeModeTabs.init();
        CTNHItems.init();
        CTNHBlocks.init();
        CTNHBlockEntities.init();
        CTNHRegistration.REGISTRATE.registerRegistrate();

        CTNHRecipes.init(modEventBus);

        CTNHCoreDatagen.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
