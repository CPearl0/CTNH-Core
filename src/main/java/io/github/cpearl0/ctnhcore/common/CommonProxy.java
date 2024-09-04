package io.github.cpearl0.ctnhcore.common;

import io.github.cpearl0.ctnhcore.data.CTNHCoreDatagen;
import io.github.cpearl0.ctnhcore.registry.CTNHCreativeModeTabs;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;

public class CommonProxy {
    public CommonProxy() {
        init();
    }

    public static void init() {
        CTNHCreativeModeTabs.init();
        CTNHItems.init();
        CTNHRegistration.REGISTRATE.registerRegistrate();

        CTNHCoreDatagen.init();
    }
}
