package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.gregtechceu.gtceu.common.data.GTModels;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.block.CoilType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.function.Supplier;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.BLOCK);
    }

    public static final BlockEntry<Block> CASING_REFLECT_LIGHT = createCasingBlock("reflect_light_casing",
            CTNHCore.id("block/casings/reflect_light_casing"));
    public static final BlockEntry<Block> CASING_TUNGSTENCU_DIAMOND_PLATING = createCasingBlock("tungstencu_diamond_plating_casing",
            CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"));
    public static final BlockEntry<Block> ENERGETIC_PHOTOVOLTAIC_BLOCK = createCasingBlock("energetic_photovoltaic_block",
            CTNHCore.id("block/energetic_photovoltaic_block"));
    public static final BlockEntry<Block> PULSATING_PHOTOVOLTAIC_BLOCK = createCasingBlock("pulsating_photovoltaic_block",
            CTNHCore.id("block/pulsating_photovoltaic_block"));
    public static final BlockEntry<Block> VIBRANT_PHOTOVOLTAIC_BLOCK = createCasingBlock("vibrant_photovoltaic_block",
            CTNHCore.id("block/vibrant_photovoltaic_block"));
    public static final BlockEntry<Block> CASING_NAQUADAH_BLOCK = createCasingBlock("naquadah_casing_block",
            CTNHCore.id("block/casings/nq_casing"));
    public static final BlockEntry<Block> CASING_NAQUADAH_ALLOY_BLOCK = createCasingBlock("naquadah_alloy_casing_block",
            CTNHCore.id("block/casings/nq_alloy_casing"));
    public static final BlockEntry<Block> CASING_ANTIFREEZE_HEATPROOF_MACHINE = createCasingBlock("antifreeze_heatproof_machine_casing",
            CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"));
    public static final BlockEntry<Block> CASING_ADVANCED_HYPER = createCasingBlock("advanced_hyper_casing",
            CTNHCore.id("block/advanced_hyper_casing"));
    public static final BlockEntry<Block> CASING_HYPER = createCasingBlock("hyper_casing",
            CTNHCore.id("block/hyper_casing"));
    public static final BlockEntry<Block> CASING_SPACE_ELEVATOR_MECHANICAL = createCasingBlock(
            "space_elevator_mechanical_casing", CTNHCore.id("block/space_elevator_mechanical_casing"));
    public static final BlockEntry<Block> HIGH_STRENGTH_CONCRETE = createCasingBlock(
            "high_strength_concrete", CTNHCore.id("block/casings/module_base/side"));

    public static final BlockEntry<ActiveBlock> SPACE_ELEVATOR_POWER_CORE = createActiveCasing("space_elevator_power_core",
            "block/flux/space_elevator_power_core");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKI = createActiveCasing("annihilate_core_mki",
            "block/flux/annihilate_core_mk1");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKII = createActiveCasing("annihilate_core_mkii",
            "block/flux/annihilate_core_mk2");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKIII = createActiveCasing("annihilate_core_mkiii",
            "block/flux/annihilate_core_mk3");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKIV = createActiveCasing("annihilate_core_mkiv",
            "block/flux/annihilate_core_mk4");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKV = createActiveCasing("annihilate_core_mkv",
            "block/flux/annihilate_core_mk5");
    public static final BlockEntry<ActiveBlock> PLASMA_COOLED_CORE = createActiveCasing("plasma_cooled_core",
            "block/flux/plasma_cooled_core");

    public static final BlockEntry<CoilBlock> COIL_ABYSALALLOY = createCoilBlock(CoilType.ABYSSALALLOY);
    public static final BlockEntry<CoilBlock> COIL_TITANSTEEL = createCoilBlock(CoilType.TITANSTEEL);
    public static final BlockEntry<CoilBlock> COIL_PIKYONIUM = createCoilBlock(CoilType.PIKYONIUM);
    public static final BlockEntry<CoilBlock> COIL_BLACKTITANIUM = createCoilBlock(CoilType.BLACKTITANIUM);
    public static final BlockEntry<CoilBlock> COIL_STARMETAL = createCoilBlock(CoilType.STARMETAL);
    public static final BlockEntry<CoilBlock> COIL_INFINITY = createCoilBlock(CoilType.INFINITYY);
    public static void init() {

    }
    // Utils
    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }
    @SuppressWarnings("all")
    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
                })
                .tag(TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("forge", "mineable/wrench")), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }
    @SuppressWarnings("all")
    private static BlockEntry<CoilBlock> createCoilBlock(ICoilType coilType) {
        BlockEntry<CoilBlock> coilBlock = REGISTRATE
                .block("%s_coil_block".formatted(coilType.getName()), p -> new CoilBlock(p, coilType))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(CTNHModels.createCoilModel("%s_coil_block".formatted(coilType.getName()), coilType))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
        GTCEuAPI.HEATING_COILS.put(coilType, coilBlock);
        return coilBlock;
    }

    @SuppressWarnings("all")
    public static BlockEntry<ActiveBlock> createActiveCasing(String name, String baseModelPath) {
        return REGISTRATE.block(name, ActiveBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createActiveModel(CTNHCore.id(baseModelPath)))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(prov.name(ctx), CTNHCore.id(baseModelPath)))
                .build()
                .register();
    }//来自GTleisure


}
