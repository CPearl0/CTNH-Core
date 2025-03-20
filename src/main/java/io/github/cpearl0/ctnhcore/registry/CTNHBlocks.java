package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.gregtechceu.gtceu.common.data.GTModels;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.block.CoilType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import org.moddingx.libx.base.tile.BlockEntityBase;

import java.util.function.Supplier;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.BLOCK);
    }

    public static final BlockEntry<Block> CASING_REFLECT_LIGHT = createCasingBlock("reflect_light_casing",
            CTNHCore.id("block/casings/reflect_light_casing"));
    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_ASSEMBLY_CONTROL = createCasingBlock("advance_machine_casing_assembly_control",
            CTNHCore.id("block/advance_machine_casing_assembly_control"));
    public static final BlockEntry<Block> CASING_OSMIRIDIUM = createCasingBlock("osmiridium_casing",
            CTNHCore.id("block/casings/osmiridium_casing"));
    public static final BlockEntry<Block> CASING_TUNGSTENCU_DIAMOND_PLATING = createCasingBlock("tungstencu_diamond_plating_casing",
            CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"));
    public static final BlockEntry<Block> ENERGETIC_PHOTOVOLTAIC_BLOCK = createCasingBlock("energetic_photovoltaic_block",
            CTNHCore.id("block/energetic_photovoltaic_block"));
    public static final BlockEntry<Block> PULSATING_PHOTOVOLTAIC_BLOCK = createCasingBlock("pulsating_photovoltaic_block",
            CTNHCore.id("block/pulsating_photovoltaic_block"));
    public static final BlockEntry<Block> VIBRANT_PHOTOVOLTAIC_BLOCK = createCasingBlock("vibrant_photovoltaic_block",
            CTNHCore.id("block/vibrant_photovoltaic_block"));
    public static final BlockEntry<Block> ZENITH_CASING_BLOCK = createCasingBlock("zenith_casing",
            CTNHCore.id("block/casings/zenith_casing"));
    public static final BlockEntry<Block> CASING_NAQUADAH_BLOCK = createCasingBlock("naquadah_casing_block",
            CTNHCore.id("block/casings/nq_casing"));
    public static final BlockEntry<Block> CASING_NAQUADAH_ALLOY_BLOCK = createCasingBlock("naquadah_alloy_casing_block",
            CTNHCore.id("block/casings/nq_alloy_casing"));
    public static final BlockEntry<Block> CASING_NEUTRONIUM_ALLOY_BLOCK = createCasingBlock("neutronium_alloy_casing_block",
            CTNHCore.id("block/casings/nq_neutronium_casing"));
    public static final BlockEntry<Block> CASING_ANTIFREEZE_HEATPROOF_MACHINE = createCasingBlock("antifreeze_heatproof_machine_casing",
            CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"));
    public static final BlockEntry<Block> CASING_ADVANCED_HYPER = createCasingBlock("advanced_hyper_casing",
            CTNHCore.id("block/casings/advanced_hyper_casing"));
    public static final BlockEntry<Block> CASING_HYPER = createCasingBlock("hyper_casing",
            CTNHCore.id("block/casings/hyper_casing"));
    public static final BlockEntry<Block> CASING_SPACE_ELEVATOR_MECHANICAL = createCasingBlock(
            "space_elevator_mechanical_casing", CTNHCore.id("block/space_elevator_mechanical_casing"));
    public static final BlockEntry<Block> HIGH_GRADE_COKE_OVEN_BRICKS = createCasingBlock(
            "high_strength_concrete", CTNHCore.id("block/high_grade_coke_oven_bricks"));
    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_GRATE = createCasingBlock(
            "advance_machine_casing_grate", CTNHCore.id("block/advance_machine_casing_grate"));
    public static final BlockEntry<Block> BLAZE_BLAST_FURNACE_CASING = createCasingBlock(
            "blaze_blast_furnace_casing",CTNHCore.id("block/casings/blaze_blast_furnace_casing"));
    public static final BlockEntry<Block> MANA_STEEL_CASING = createCasingBlock(
            "mana_steel_casing",CTNHCore.id("block/casings/mana_steel_casing"));
    public static final BlockEntry<Block> TERRA_STEEL_CASING = createCasingBlock(
            "terra_steel_casing",CTNHCore.id("block/casings/terra_steel_casing"));
    public static final BlockEntry<Block> ZENITH_EYE = createCasingBlock(
            "zenith_eye",CTNHCore.id("block/zenith_eye"));
    public static final BlockEntry<Block> NATURAL_ECOLOGICAL_SHELL_CASING = createCasingBlock("natural_ecological_shell_casing",
            CTNHCore.id("block/casings/natural_ecological_shell_casing"));
    public static final BlockEntry<Block> REACTOR_CONDENSATION_BLOCK = createCasingBlock(
            "reactor_condensation_block",CTNHCore.id("block/reactor_condensation_block"));
    public static final BlockEntry<Block> ELEMENTIUM_CASING = createCasingBlock(
            "elementium_casing",CTNHCore.id("block/casings/elementium_casing"));
    public static final BlockEntry<Block> ALF_STEEL_CASING = createCasingBlock(
            "alfsteel_casing",CTNHCore.id("block/casings/alfsteel_casing"));
    public static final BlockEntry<Block> ZENITH_CASING_GEARBOX = createCasingBlock(
            "zenith_casing_gearbox",CTNHCore.id("block/zenith_casing_gearbox"));
    public static final BlockEntry<Block> DEPTH_FORCE_FIELD_STABILIZING_CASING = createCasingBlock(
            "depth_force_field_stabilizing_casing",CTNHCore.id("block/casings/depth_force_field_stabilizing_casing"));
    public static final BlockEntry<Block> CASING_NAQUADAH_GEARBOX = createCasingBlock("naquadah_gearbox",
            CTNHCore.id("block/casings/gearbox/machine_casing_gearbox_naquadah"));
    public static final BlockEntry<Block> BIO_REACTOR_CASING = createCasingBlock("bio_reactor_casing",
            CTNHCore.id("block/casings/bio_reactor_casing"));
    public static final BlockEntry<Block> ELEMENTIUM_PIPE_CASING = createCasingBlock("elementium_pipe_casing",
            CTNHCore.id("block/casings/pipe/elementium_pipe_casing"));
    public static final BlockEntry<Block> ELEMENTIUM_NORMAL_FLUID_PIPE = createCasingBlock("elementium_normal_fluid_pipe",
            CTNHCore.id("block/casings/pipe/elementium_normal_fluid_pipe"));
    public static final BlockEntry<Block> CASING_MANASTEEL_GEARBOX = createCasingBlock("mana_steel_gearbox_casing",
            CTNHCore.id("block/casings/gearbox/mana_steel_gearbox_casing"));
    public static final BlockEntry<Block> QUASAR_ENERGY_STABILIZATION_CASING = createCasingBlock("quasar_energy_stabilization_casing",
            CTNHCore.id("block/casings/quasar_energy_stabilization_casing"));
    public static final BlockEntry<Block> TWISTED_FUSION_CASING = createCasingBlock("twisted_fusion_casing",
            CTNHCore.id("block/casings/twisted_fusion_casing"));
    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_SOLID_STEEL = createCasingBlock("advance_machine_casing_solid_steel",
            CTNHCore.id("block/casings/advance_machine_casing_solid_steel"));
    public static final BlockEntry<Block> WIDESPEEDINGPIPE =createCasingBlock("widespeedingpipe",CTNHCore.id("block/widespeedingpipe"));


    public static final BlockEntry<ActiveBlock> RESERVOIR_COMPUTING_CASING = createActiveCasing("reservoir_computing_casing",
            "block/flux/reservoir_computing_casing");
    public static final BlockEntry<ActiveBlock> SPACE_ELEVATOR_POWER_CORE = createActiveCasing("space_elevator_power_core",
            "block/flux/space_elevator_power_core");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKI = createActiveCasing("annihilate_core_mki",
            "block/flux/annihilate_core_mk1");
    public static final BlockEntry<ActiveBlock> ADVANCE_MACHINE_CASING_ASSEMBLY_LINE = createActiveCasing("advance_machine_casing_assembly_line",
            "block/flux/advance_machine_casing_assembly_line");

    public static  final BlockEntry<ActiveBlock> ARC_CELL =createActiveCasing("arc_cell","block/flux/arc_cell");
    public static final BlockEntry<ActiveBlock> PLASMA_COOLED_CORE = createActiveCasing("plasma_cooled_core",
            "block/flux/plasma_cooled_core");
    public static final BlockEntry<ActiveBlock> CASING_ULTIMATE_ENGINE_INTAKE = createActiveCasing(
            "ultimate_engine_intake_casing", "block/variant/ultimate_engine_intake");

    public static final BlockEntry<CoilBlock> COIL_ABYSALALLOY = createCoilBlock(CoilType.ABYSSALALLOY);
    public static final BlockEntry<CoilBlock> COIL_TITANSTEEL = createCoilBlock(CoilType.TITANSTEEL);
    public static final BlockEntry<CoilBlock> COIL_PIKYONIUM = createCoilBlock(CoilType.PIKYONIUM);
    public static final BlockEntry<CoilBlock> COIL_BLACKTITANIUM = createCoilBlock(CoilType.BLACKTITANIUM);
    public static final BlockEntry<CoilBlock> COIL_STARMETAL = createCoilBlock(CoilType.STARMETAL);
    public static final BlockEntry<CoilBlock> COIL_INFINITY = createCoilBlock(CoilType.INFINITYY);
    public static final BlockEntry<CoilBlock> COIL_ULTRA_MANA= createCoilBlock(CoilType.ULTRA_MANA);
    public static final BlockEntry<Block> BRONZE_FRAMED_GLASS = createGlassCasingBlock(
            "bronze_framed_glass",CTNHCore.id("block/casings/bronze_framed_glass"), () -> RenderType::cutoutMipped);

    public static final BlockEntry<RotatedPillarBlock> TEST_CASING = createRotateCasing("test_machine_casing", "t1");
    public static final BlockEntry<RotatedPillarBlock> ATOMS_SPLIT_BLOCKS = createRotateCasing("atoms_split_blocks", "atomssplit");

    public static final BlockEntry<RotatedPillarBlock> ASTRAL_LOG = createLogLikeBlock("astral_log");
    public static final BlockEntry<RotatedPillarBlock> PEPPER_CRATE = createLogLikeBlock("pepper_crate");
    public static final BlockEntry<RotatedPillarBlock> GARLIC_CRATE = createLogLikeBlock("garlic_crate");
    public static final BlockEntry<RotatedPillarBlock> CASSAVA_CRATE = createLogLikeBlock("cassava_crate");
    public static final BlockEntry<RotatedPillarBlock> FRUIT_CAFE_CRATE = createLogLikeBlock("fruit_cafe_crate");
    public static final BlockEntry<RotatedPillarBlock> ASPARAGUS_CRATE = createLogLikeBlock("asparagus_crate");
    public static final BlockEntry<Block> ASTRAL_STONE = createStoneLikeBlock("astral_stone", CTNHCore.id("block/stones/astral_stone"));
    public static final BlockEntry<FallingBlock> ASTRAL_SAND = createSandLikeBlock("astral_sand", CTNHCore.id("block/sands/astral_sand"));
    public static final BlockEntry<Block> EYE_RENDER = REGISTRATE.block("eye_render", Block::new)
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll("eye_render", CTNHCore.id("block/casings/eye_render")));
            })
            .properties(properties -> properties.strength(-1,3600000))
            .simpleBlockEntity(BlockEntityBase::new)
            .item(BlockItem::new)
            .build()
            .register();
    public static void init() {

    }
    // Utils
    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }
    private static BlockEntry<Block> createGlassCasingBlock(String name, ResourceLocation texture, Supplier<Supplier<RenderType>> type) {
        return createCasingBlock(name, GlassBlock::new, texture, () -> Blocks.GLASS, type);
    }
    @SuppressWarnings("all")
    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(type)
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
        BlockEntry<CoilBlock> coilBlock = REGISTRATE.block("%s_coil_block".formatted(coilType.getName()), p -> new CoilBlock(p, coilType))
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
    }
    @SuppressWarnings("all")
    private static BlockEntry<RotatedPillarBlock> createRotateCasing(String name, String map) {
        return REGISTRATE.block(name, RotatedPillarBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(CTNHModels.createMapCasingModel(name, map))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }
    public static BlockEntry<Block> createStoneLikeBlock(String name, ResourceLocation texture) {
        return REGISTRATE.block(name, Block::new)
                .initialProperties(() -> Blocks.STONE)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }
    private static BlockEntry<RotatedPillarBlock> createLogLikeBlock(String name) {
        return REGISTRATE.block(name, RotatedPillarBlock::new)
                .initialProperties(() -> Blocks.OAK_WOOD)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate((ctx,prov)->prov.logBlock(ctx.getEntry()))
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .item(BlockItem::new)
                .build()
                .register();
    }
    public static BlockEntry<FallingBlock> createSandLikeBlock(String name, ResourceLocation texture) {
        return REGISTRATE.block(name, FallingBlock::new)
                .initialProperties(() -> Blocks.SAND)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
                })
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)).addLayer(() -> RenderType::cutoutMipped)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .item(BlockItem::new)
                .build()
                .register();
    }
}
