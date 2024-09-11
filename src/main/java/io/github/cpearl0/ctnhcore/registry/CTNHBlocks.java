package io.github.cpearl0.ctnhcore.registry;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.block.LiquidBlazeBurnerBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.DyeColor;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }
    public static final BlockEntry<LiquidBlazeBurnerBlock> LIQUID_BLAZE_BURNER = CTNHRegistration.REGISTRATE.block("liquid_blaze_burner",  LiquidBlazeBurnerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .lang("Liquid Blaze Burner")
            .properties(p -> p.mapColor(DyeColor.GRAY))
            .properties(p -> p.lightLevel(BlazeBurnerBlock::getLight))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .register();
    public static void init() {

    }
}
