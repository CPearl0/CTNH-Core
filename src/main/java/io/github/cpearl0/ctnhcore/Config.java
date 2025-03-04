package io.github.cpearl0.ctnhcore;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@dev.toma.configuration.config.Config(id = CTNHCore.MODID)
public class Config {
    public static Config INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(Config.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }
    @Configurable
    @Configurable.Comment({"Disable FTBUltimine on GregTech Ores", "Default: true"})
    public static boolean disableFTBUltimineOnGTOres = true;
}
