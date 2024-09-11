package io.github.cpearl0.ctnhcore.common;

import io.github.cpearl0.ctnhcore.Config;
import io.github.cpearl0.ctnhcore.data.CTNHCoreDatagen;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHCreativeModeTabs;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class CommonProxy {
    public CommonProxy() {
        init();
    }

    public static void init() {
        CTNHCreativeModeTabs.init();
        CTNHItems.init();
        CTNHBlocks.init();
        CTNHRegistration.REGISTRATE.registerRegistrate();

        CTNHCoreDatagen.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
