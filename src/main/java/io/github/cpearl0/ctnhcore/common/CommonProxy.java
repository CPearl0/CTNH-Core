package io.github.cpearl0.ctnhcore.common;

import io.github.cpearl0.ctnhcore.registry.CTNHCreativeModeTabs;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;

public class CommonProxy {
    public CommonProxy() {
        init();
    }

    public static void init() {
        CTNHCreativeModeTabs.init();
        CTNHRegistration.REGISTRATE.registerRegistrate();
    }
}
