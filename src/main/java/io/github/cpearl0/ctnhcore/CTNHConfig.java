package io.github.cpearl0.ctnhcore;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = CTNHCore.MODID)
public class CTNHConfig {
    public static CTNHConfig INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(CTNHConfig.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }
    @Configurable
    @Configurable.Comment("FTB's plugins")
    public FTBPlugin ftbPlugin = new FTBPlugin();
    public static class FTBPlugin{
        @Configurable
        @Configurable.Comment({"Disable FTBUltimine on GregTech Ores", "Default: true"})
        public boolean disableFTBUltimineOnGTOres = true;
    }
}
