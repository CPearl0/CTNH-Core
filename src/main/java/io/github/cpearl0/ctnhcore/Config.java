package io.github.cpearl0.ctnhcore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DISABLE_FTBULTIMINE_ON_GT_ORES = BUILDER
            .comment("Disable FTBUltimine on GregTech Ores")
            .define("disableFTBUltimineOnGTOres", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean disableFTBUltimineOnGTOres;

    @SubscribeEvent
    public static void onLoad(ModConfigEvent event) {
        disableFTBUltimineOnGTOres = DISABLE_FTBULTIMINE_ON_GT_ORES.get();
    }
}
