package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.data.DimensionMarker;
import net.minecraft.resources.ResourceLocation;

import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.createAndRegister;

public class CTNHDimensionMarkers {
    public static final DimensionMarker MOON = createAndRegister(new ResourceLocation("ad_astra:moon"),
            1, new ResourceLocation("ad_astra:moon_stone"), "dimension.ad_astra:moon");
    public static final DimensionMarker MARS = createAndRegister(new ResourceLocation("ad_astra:mars"),
            2, new ResourceLocation("ad_astra:mars_stone"), "dimension.ad_astra:mars");
    public static final DimensionMarker VENUS = createAndRegister(new ResourceLocation("ad_astra:venus"),
            3, new ResourceLocation("ad_astra:venus_stone"), "dimension.ad_astra:venus");
    public static final DimensionMarker MERCURY = createAndRegister(new ResourceLocation("ad_astra:mercury"),
            3, new ResourceLocation("ad_astra:mercury_stone"), "dimension.ad_astra:mercury");
    public static final DimensionMarker GLACIO = createAndRegister(new ResourceLocation("ad_astra:glacio"),
            7, new ResourceLocation("ad_astra:glacio_stone"), "dimension.ad_astra:glacio");
    public static final DimensionMarker JUPITER = createAndRegister(new ResourceLocation("ad_extendra:jupiter"),
            7, new ResourceLocation("ad_extendra:jupiter_stone"), "dimension.ad_extendra:jupiter");
    public static final DimensionMarker AETHER = createAndRegister(new ResourceLocation("aether:the_aether"),
            2, new ResourceLocation("aether:holystone"), "dimension.aether:the_aether");
    public static final DimensionMarker TWILIGHTFOREST = createAndRegister(new ResourceLocation("twilightforest:twilight_forest"),
            7, new ResourceLocation("minecraft:oak_leaves"), "dimension.twilightforest:twilight_forest");
    public static final DimensionMarker ALFHEIM = createAndRegister(new ResourceLocation("mythicbotany:alfheim"),
            3, new ResourceLocation("botania:livingrock"), "dimension.mythicbotany:alfheim");

    public static void init() {

    }
}
