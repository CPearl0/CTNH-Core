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
    @Configurable
    @Configurable.Comment("Mechanical Machine's Buffer")
    public Kinetic kinetic = new Kinetic();
    public static class FTBPlugin{
        @Configurable
        @Configurable.Comment({"Enable FTBUltimine on GregTech Ores", "Default: false"})
        public boolean enableFTBUltimineOnGTOres = false;
    }
    public static class Kinetic{
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical pressor machines", "Default: 64"})
        @Configurable.Range(min = 16, max = 256)
        public int pressorRpmRequirement = 64;
        @Configurable
        @Configurable.Comment({"Mechanical Pressor's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float pressorSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Pressor's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float pressorStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical mixer machines", "Default: 64"})
        @Configurable.Range(min = 16, max = 256)
        public int mixerRpmRequirement = 64;
        @Configurable
        @Configurable.Comment({"Mechanical Mixer's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float mixerSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Mixer's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float mixerStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical centrifuge machines", "Default: 64"})
        @Configurable.Range(min = 16, max = 256)
        public int centrifugeRpmRequirement = 64;
        @Configurable
        @Configurable.Comment({"Mechanical Centrifuge's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float centrifugeSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Centrifuge's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float centrifugeStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical sifter machines", "Default: 128"})
        @Configurable.Range(min = 16, max = 256)
        public int sifterRpmRequirement = 128;
        @Configurable
        @Configurable.Comment({"Mechanical Sifter's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float sifterSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Sifter's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float sifterStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical extractor machines", "Default: 128"})
        @Configurable.Range(min = 16, max = 256)
        public int extractorRpmRequirement = 128;
        @Configurable
        @Configurable.Comment({"Mechanical Extractor's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float extractorSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Extractor's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float extractorStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical lathe machines", "Default: 128"})
        @Configurable.Range(min = 16, max = 256)
        public int latheRpmRequirement = 128;
        @Configurable
        @Configurable.Comment({"Mechanical Lathe's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float latheSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Lathe's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float latheStressRequirement = 512;
        @Configurable
        @Configurable.Comment({"The rpm requirement for mechanical laser machines", "Default: 128"})
        @Configurable.Range(min = 16, max = 256)
        public int laserRpmRequirement = 128;
        @Configurable
        @Configurable.Comment({"Mechanical Laser's Speed Multiplier relative to its voltage level", "Default: 2"})
        @Configurable.DecimalRange(min = 0.5, max = 4.0)
        public float laserSpeedMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Mechanical Laser's stress requirement(This value will be multiplied by its basic EUt cost)", "Default: 512"})
        @Configurable.DecimalRange(min = 1.0,max = 1024.0)
        public float laserStressRequirement = 512;
    }
}
