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
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.common.data.GTBlocks.compassNodeExist;
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
    public static final BlockEntry<Block> ANNIHILATE_CORE = createCasingBlock("annihilate_core",
            CTNHCore.id("block/annihilate_core"));
    public static final BlockEntry<Block> ANNIHILATE_CORE1 = createCasingBlock("annihilate_core1",
            CTNHCore.id("block/annihilate_core1"));
    public static final BlockEntry<Block> ANNIHILATE_CORE2 = createCasingBlock("annihilate_core2",
            CTNHCore.id("block/annihilate_core2"));
    public static final BlockEntry<Block> CASING_ADVANCED_HYPER = createCasingBlock("advanced_hyper_casing",
            CTNHCore.id("block/advanced_hyper_casing"));
    public static final BlockEntry<Block> CASING_HYPER = createCasingBlock("hyper_casing",
            CTNHCore.id("block/hyper_casing"));
    //public static final BlockEntry<Block> MODULE_CONNECTOR = createCasingBlock(
            //"module_connector", CTNHCore.id("block/module_connector"));
    //public static final BlockEntry<Block> CASING_SPACE_ELEVATOR_MECHANICAL_CASING = createCasingBlock(
            //"space_elevator_mechanical_casing", CTNHCore.id("block/space_elevator_mechanical_casing"));
    //public static final BlockEntry<Block> HIGH_STRENGTH_CONCRETE = createCasingBlock(
            //"high_strength_concrete", CTNHCore.id("block/casings/module_base/side"));

    //public static final BlockEntry<Block> SPACE_ELEVATOR_INTERNAL_SUPPORT = createSidedCasingBlock(
            //"space_elevator_internal_support", CTNHCore.id("block/casings/space_elevator_internal_support"));
    //public static final BlockEntry<Block> SPACE_MODULE_BASE = createSidedCasingBlock(
            //"module_base", CTNHCore.id("block/casings/module_base"));

    //public static final BlockEntry<ActiveBlock> SPACE_POWER_CORE = createActiveCasing("space_elevator_power_core",
            //"block/space_elevator_power_core");
    //public static final BlockEntry<ActiveBlock> SPACE_ELEVATOR_SUPPORT = createActiveCasing("space_elevator_support",
            //"block/space_elevator_support");
    public static final BlockEntry<CoilBlock> COIL_ABYSALALLOY = createCoilBlock(CoilType.ABYSSALALLOY);
    public static final BlockEntry<CoilBlock> COIL_TITANSTEEL = createCoilBlock(CoilType.TITANSTEEL);
    public static final BlockEntry<CoilBlock> COIL_PIKYONIUM = createCoilBlock(CoilType.PIKYONIUM);
    public static final BlockEntry<CoilBlock> COIL_BLACKTITANIUM = createCoilBlock(CoilType.BLACKTITANIUM);
    public static final BlockEntry<CoilBlock> COIL_STARMETAL = createCoilBlock(CoilType.STARMETAL);
    public static final BlockEntry<CoilBlock> COIL_INFINITY = createCoilBlock(CoilType.INFINITY);
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
                .blockstate((ctx, prov) -> {
                    ActiveBlock block = ctx.getEntry();
                    ModelFile inactive = prov.models().getExistingFile(coilType.getTexture());
                    ModelFile active = prov.models().getExistingFile(coilType.getTexture().withSuffix("_bloom"));
                    prov.getVariantBuilder(block).partialState().with(ActiveBlock.ACTIVE, false).modelForState().modelFile(inactive).addModel().partialState().with(ActiveBlock.ACTIVE, true).modelForState().modelFile(active).addModel();
                })
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(prov.name(ctx), coilType.getTexture()))
                //.onRegister(compassNodeExist(GTCompassSections.BLOCKS, "coil_block"))
                .build()
                .register();
        GTCEuAPI.HEATING_COILS.put(coilType, coilBlock);
        return coilBlock;
    }

}
