package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.Pattern.CTNHBlockMaps;
import io.github.cpearl0.ctnhcore.api.Pattern.CTNHBoilerFireboxType;
import io.github.cpearl0.ctnhcore.common.block.CoilType;
import io.github.cpearl0.ctnhcore.common.block.PhotovoltaicBlock;
import io.github.cpearl0.ctnhcore.common.block.SpaceStructuralFramework;
import io.github.cpearl0.ctnhcore.common.block.TurbineRotorBlock;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.block.blockdata.ISSFData;
import io.github.cpearl0.ctnhcore.common.item.TurbineRotorItem;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.common.data.GTBlocks.ALL_FIREBOXES;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

@SuppressWarnings("removal")
public class CTNHBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.BLOCK);
        String[][] casingVariants = {
                { "machine_casing_bronze_plated_bricks", "青铜" },
                { "machine_casing_solid_steel", "钢" },
                { "machine_casing_frost_proof", "铝" },
                { "machine_casing_clean_stainless_steel", "不锈钢" },
                { "machine_casing_stable_titanium", "钛" },
                { "machine_casing_robust_tungstensteel", "钨钢" },
                { "machine_casing_palladium_substation", "镀铑钯" },
                { "machine_casing_inert_ptfe", "四氟乙烯" },
                { "machine_casing_heatproof", "殷钢" },
                { "machine_casing_sturdy_hsse_green", "坚固HSSE绿" },
        };
        for (String[] variant : casingVariants) {
            registerCasingVariants(variant[0], variant[1],
                    CTNHCore.id("block/" + variant[0]));
        }
    }

    public static final BlockEntry<Block> CASING_REFLECT_LIGHT = createCasingBlock(
            "reflect_light_casing", "反光机械方块", CTNHCore.id("block/casings/reflect_light_casing"));
    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_ASSEMBLY_CONTROL = createCasingBlock(
            "advance_machine_casing_assembly_control", "进阶线程控制器方块",
            CTNHCore.id("block/advance_machine_casing_assembly_control"));
    public static final BlockEntry<Block> CASING_OSMIRIDIUM = createCasingBlock(
            "osmiridium_casing", "铱锇合金机械方块", CTNHCore.id("block/casings/osmiridium_casing"));
    public static final BlockEntry<Block> CASING_TUNGSTENCU_DIAMOND_PLATING = createCasingBlock(
            "tungstencu_diamond_plating_casing", "W-Cu覆膜金刚石机械方块",
            CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"));

    public static final BlockEntry<Block> CASING_NAQUADAH_BLOCK = createCasingBlock(
            "naquadah_casing_block", "铿铀强化硅岩铕机械方块", CTNHCore.id("block/casings/nq_casing"));
    public static final BlockEntry<Block> CASING_NAQUADAH_ALLOY_BLOCK = createCasingBlock(
            "naquadah_alloy_casing_block", "三钛强化中子素硅岩合金机械方块", CTNHCore.id("block/casings/nq_alloy_casing"));
    public static final BlockEntry<Block> CASING_NEUTRONIUM_ALLOY_BLOCK = createCasingBlock(
            "neutronium_alloy_casing_block", "以太强化超能中子基岩合金钅达智能机械方块", CTNHCore.id("block/casings/nq_neutronium_casing"));
    public static final BlockEntry<Block> CASING_ANTIFREEZE_HEATPROOF_MACHINE = createCasingBlock(
            "antifreeze_heatproof_machine_casing", "等离子冷凝机械方块",
            CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"));
    public static final BlockEntry<Block> CASING_ADVANCED_HYPER = createCasingBlock(
            "advanced_hyper_casing", "暗物质强化超能硅岩机械方块", CTNHCore.id("block/casings/advanced_hyper_casing"));
    public static final BlockEntry<Block> CASING_HYPER = createCasingBlock(
            "hyper_casing", "黑钚强化硅岩合金机械方块", CTNHCore.id("block/casings/hyper_casing"));
    public static final BlockEntry<Block> CASING_SPACE_ELEVATOR_MECHANICAL = createCasingBlock(
            "space_elevator_mechanical_casing", "太空电梯机械方块", CTNHCore.id("block/space_elevator_mechanical_casing"));
    public static final BlockEntry<Block> HIGH_GRADE_COKE_OVEN_BRICKS = createCasingBlock(
            "high_strength_concrete", "高级焦炉砖", CTNHCore.id("block/high_grade_coke_oven_bricks"));
    public static final BlockEntry<Block> PLANT_OIL_MASS = createCasingBlock(
            "plant_oil_mass", "植物油脂块", CTNHCore.id("block/plant_oil_mass"));
    public static final BlockEntry<SlabBlock> ANDESITE_ALLOY_SLAB = REGISTRATE
            .block("andesite_alloy_slab", SlabBlock::new)
            .cnlang("安山合金台阶")
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .blockstate((ctx, prov) -> {
                var texture = ResourceLocation.parse("create:block/andesite_block");
                prov.slabBlock(ctx.getEntry(),
                        prov.models().slab("andesite_alloy_slab", texture, texture, texture),
                        prov.models().slabTop("andesite_alloy_slab_top", texture, texture, texture),
                        prov.models().cubeAll("andesite_alloy", texture));
            })
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item(BlockItem::new)
            .build()
            .register();
    public static final BlockEntry<RotatedPillarBlock> CARBONIZED_LOG = REGISTRATE
            .block("carbonized_log", RotatedPillarBlock::new)
            .cnlang("碳化原木")
            .initialProperties(() -> Blocks.OAK_LOG)
            .blockstate((ctx, prov) -> prov.logBlock(ctx.getEntry()))
            .tag(BlockTags.MINEABLE_WITH_AXE)
            .item(BlockItem::new)
            .build()
            .register();
    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_GRATE = createCasingBlock(
            "advance_machine_casing_grate", "进阶装配线格栅方块", CTNHCore.id("block/advance_machine_casing_grate"));
    public static final BlockEntry<Block> BLAZE_BLAST_FURNACE_CASING = createCasingBlock(
            "blaze_blast_furnace_casing", "炽焱高炉机械外壳", CTNHCore.id("block/casings/blaze_blast_furnace_casing"));

    public static final BlockEntry<Block> NATURAL_ECOLOGICAL_SHELL_CASING = createCasingBlock(
            "natural_ecological_shell_casing", "环保机械外壳",
            CTNHCore.id("block/casings/natural_ecological_shell_casing"));
    public static final BlockEntry<Block> REACTOR_CONDENSATION_BLOCK = createCasingBlock(
            "reactor_condensation_block", "反应堆冷凝方块", CTNHCore.id("block/reactor_condensation_block"));

    public static final BlockEntry<Block> CASING_NAQUADAH_GEARBOX = createCasingBlock("naquadah_gearbox", "硅岩合金齿轮箱机械方块",
            CTNHCore.id("block/casings/gearbox/machine_casing_gearbox_naquadah"));
    public static final BlockEntry<Block> BIO_REACTOR_CASING = createCasingBlock("bio_reactor_casing", "生物反应器外壳",
            CTNHCore.id("block/casings/bio_reactor_casing"));
    public static final BlockEntry<Block> ADVANCED_BIO_REACTOR_CASING = createCasingBlock("advanced_bio_reactor_casing",
            "高级生物反应器外壳",
            CTNHCore.id("block/casings/advanced_bio_reactor_casing"));

    public static final BlockEntry<Block> SUPER_FREEZE_BLOCK = createCasingBlock("super_machine_casing_frost_proof",
            "超级冷冻外壳",
            CTNHCore.id("block/casings/super_machine_casing_frost_proof"));

    public static final BlockEntry<Block> ADVANCE_MACHINE_CASING_SOLID_STEEL = createCasingBlock(
            "advance_machine_casing_solid_steel", "特种钢质外壳",
            CTNHCore.id("block/casings/advance_machine_casing_solid_steel"));
    public static final BlockEntry<Block> WIDESPEEDINGPIPE = createCasingBlock(
            "widespeedingpipe", "广粒子加速器通道", CTNHCore.id("block/widespeedingpipe"));
    public static final BlockEntry<Block> STELLAR_RADIATION_ROUTER_CASING = createCasingBlock(
            "stellar_radiation_router_casing", "恒星辐射分流方块",
            CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"));
    public static final BlockEntry<Block> CASING_SHIELDED_REACTOR = createCasingBlock(
            "shielded_reactor_casing", "覆层核反应堆外壳", CTNHCore.id("block/casings/shielded_reactor_casing"));
    public static final BlockEntry<Block> NEUTRONIUM_REINFORCED_TURBINE_CASING = createCasingBlock(
            "neutronium_reinforced_turbine_casing", "中子素强化涡轮外壳",
            CTNHCore.id("block/casings/neutronium_reinforced_turbine_casing"));

    public static final BlockEntry<Block> PROCESS_MACHINE_CASING = createCasingBlock(
            "process_machine_casing", "洁净机械方块", CTNHCore.id("block/casings/solid/process_machine_casing"));

    public static final BlockEntry<Block> RADIATION_PROOF_MACHINE_CASING = createCasingBlock(
            "radiation_proof_machine_casing", "防辐射机械方块",
            CTNHCore.id("block/casings/solid/radiation_proof_machine_casing"));

    public static final BlockEntry<Block> FIELD_RESTRICTION_CASING = createCasingBlock(
            "field_restriction_casing", "立场约束机械方块", CTNHCore.id("block/casings/solid/field_restriction_casing"));

    public static final BlockEntry<Block> CASING_POLYBENZIMIDAZOLE_PIPE = createCasingBlock(
            "polybenzimidazole_pipe", "聚苯并咪唑管道方块", CTNHCore.id("block/casings/pipe/polybenzimidazole_pipe"));

    public static final BlockEntry<Block> BRONZE_CASING = createCasingBlock(
            "bronze_casing", "青铜机械方块", CTNHCore.id("block/casings/bronze_casing"));

    public static final BlockEntry<Block> MANA_STEEL_TUNGSTENSTEEL_GEARBOX_CASING = createCasingBlock(
            "mana_steel_tungstensteel_gearbox_casing", "魔力钢钨钢齿轮箱机械方块",
            CTNHCore.id("block/casings/mana_steel_tungstensteel_gearbox_casing"));

    public static final BlockEntry<Block> SCULK_CASING = REGISTRATE
            .block("sculk_casing", Block::new)
            .cnlang("幽匿机械方块")
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll("sculk_casing",
                        CTNHCore.id("block/casings/sculk_casing")));
            })
            .tag(CustomTags.MINEABLE_WITH_WRENCH,
                    BlockTags.MINEABLE_WITH_PICKAXE,
                    AllTags.AllBlockTags.CASING.tag)
            .item(BlockItem::new)
            .build()
            .register();

    public static final BlockEntry<Block> IRIDIUM_CASING = createCasingBlock(
            "iridium_casing", "铱机械方块", CTNHCore.id("block/casings/solid/iridium_casing"));

    public static final BlockEntry<Block> ADVANCED_FILTER_CASING = createCasingBlock(
            "advanced_filter_casing", "高级过滤器机械方块", CTNHCore.id("block/casings/solid/advanced_filter_casing"));

    public static final BlockEntry<Block> WHITE_ELEVATOR_CASING = createCasingBlock(
            "white_elevator_casing", "白色电梯机械方块", CTNHCore.id("block/casings/space_elevator/white_elevator_casing"));

    public static final BlockEntry<Block> DARK_BLUE_ELEVATOR_CASING = createCasingBlock(
            "dark_blue_elevator_casing", "深蓝色电梯机械方块",
            CTNHCore.id("block/casings/space_elevator/dark_blue_elevator_casing"));

    public static final BlockEntry<Block> LIGHT_BLUE_ELEVATOR_CASING = createCasingBlock(
            "light_blue_elevator_casing", "浅蓝色电梯机械方块",
            CTNHCore.id("block/casings/space_elevator/light_blue_elevator_casing"));

    public static final BlockEntry<Block> DARK_GRAY_ELEVATOR_CASING = createCasingBlock(
            "dark_gray_elevator_casing", "深灰色电梯机械方块",
            CTNHCore.id("block/casings/space_elevator/dark_gray_elevator_casing"));

    public static final BlockEntry<Block> CABLE_ELEVATOR_CASING = createCasingBlock(
            "cable_elevator_casing", "电梯线缆机械方块", CTNHCore.id("block/casings/space_elevator/cable_elevator_casing"));

    public static final BlockEntry<Block> WHITE_CONTAINER_BLOCK = createCasingBlock(
            "white_container_block", "白色集装箱方块", CTNHCore.id("block/casings/space_elevator/white_container_block"));

    public static final BlockEntry<Block> SCIFI_ELEVATOR_CASING = REGISTRATE.block("scifi_elevator_casing", Block::new)
            .cnlang("科幻风机械方块")
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((ctx, prov) -> {
                BlockModelBuilder model = prov.models()
                        .withExistingParent(ctx.getName(), GTCEu.id("block/cube/tinted/bottom_top"))
                        .texture("top", CTNHCore.id("block/casings/space_elevator/scifi_elevator_casing_top"))
                        .texture("bottom", CTNHCore.id("block/casings/space_elevator/scifi_elevator_casing_top"))
                        .texture("side", CTNHCore.id("block/casings/space_elevator/scifi_elevator_casing"));
                prov.simpleBlock(ctx.getEntry(), model);
            })
            .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
            .item(BlockItem::new)
            .build()
            .register();

    public static final BlockEntry<Block> ELEVATOR_STRUCT_CASING = REGISTRATE
            .block("elevator_struct_casing", Block::new)
            .cnlang("太空电梯结构支持方块")
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((ctx, prov) -> {
                BlockModelBuilder model = prov.models()
                        .withExistingParent(ctx.getName(), GTCEu.id("block/cube/tinted/bottom_top"))
                        .texture("top", CTNHCore.id("block/casings/space_elevator/elevator_struct_casing_top"))
                        .texture("bottom", CTNHCore.id("block/casings/space_elevator/elevator_struct_casing_top"))
                        .texture("side", CTNHCore.id("block/casings/space_elevator/elevator_struct_casing"));
                prov.simpleBlock(ctx.getEntry(), model);
            })
            .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
            .item(BlockItem::new)
            .build()
            .register();

    public static final BlockEntry<ActiveBlock> SUPERCOOLED_BLOCK = createActiveCasing("supercooled_bloock", "超级冷冻机械线圈",
            "block/flux/plasma_cooled_core");
    public static final BlockEntry<ActiveBlock> RESERVOIR_COMPUTING_CASING = createActiveCasing(
            "reservoir_computing_casing", "高能突触机器外壳",
            "block/flux/reservoir_computing_casing");
    public static final BlockEntry<ActiveBlock> SPACE_ELEVATOR_POWER_CORE = createActiveCasing(
            "space_elevator_power_core", "太空电梯维持反应堆核心",
            "block/flux/space_elevator_power_core");
    public static final BlockEntry<ActiveBlock> ANNIHILATE_CORE_MKI = createActiveCasing("annihilate_core_mki",
            "超级硅岩反应堆核心",
            "block/flux/annihilate_core_mk1");
    public static final BlockEntry<ActiveBlock> ADVANCE_MACHINE_CASING_ASSEMBLY_LINE = createActiveCasing(
            "advance_machine_casing_assembly_line", "进阶装配线外壳",
            "block/flux/advance_machine_casing_assembly_line");

    public static final BlockEntry<ActiveBlock> ARC_CELL = createActiveCasing("arc_cell", "电弧发生器",
            "block/flux/arc_cell");
    public static final BlockEntry<ActiveBlock> PLASMA_COOLED_CORE = createActiveCasing("plasma_cooled_core",
            "等离子交换热线圈方块",
            "block/flux/plasma_cooled_core");
    public static final BlockEntry<ActiveBlock> CASING_ULTIMATE_ENGINE_INTAKE = createActiveCasing(
            "ultimate_engine_intake_casing", "无尽引擎进气机械方块", "block/variant/ultimate_engine_intake");

    public static final BlockEntry<CoilBlock> COIL_ABYSALALLOY = createCoilBlock(CoilType.ABYSSALALLOY, "渊狱合金线圈");
    public static final BlockEntry<CoilBlock> COIL_TITANSTEEL = createCoilBlock(CoilType.TITANSTEEL, "泰坦钢线圈");
    public static final BlockEntry<CoilBlock> COIL_PIKYONIUM = createCoilBlock(CoilType.PIKYONIUM, "皮卡优线圈");
    public static final BlockEntry<CoilBlock> COIL_BLACKTITANIUM = createCoilBlock(CoilType.BLACKTITANIUM,
            "黑钛合金线圈");
    public static final BlockEntry<CoilBlock> COIL_STARMETAL = createCoilBlock(CoilType.STARMETAL, "星辉线圈");
    public static final BlockEntry<CoilBlock> COIL_INFINITY = createCoilBlock(CoilType.INFINITYY, "无尽线圈");
    // public static final BlockEntry<CoilBlock> COIL_ULTRA_MANA = createCoilBlock(CoilType.ULTRA_MANA);

    public static final BlockEntry<Block> BRONZE_FRAMED_GLASS = createGlassCasingBlock(
            "bronze_framed_glass", "青铜镶边玻璃", CTNHCore.id("block/casings/bronze_framed_glass"),
            () -> RenderType::cutoutMipped);

    public static final BlockEntry<Block> BRASS_FRAMED_GLASS = createGlassCasingBlock(
            "brass_framed_glass", "黄铜镶边玻璃", CTNHCore.id("block/casings/space_elevator/brass_framed_glass"),
            () -> RenderType::cutoutMipped);

    public static final BlockEntry<Block> BLUE_FRAMED_GLASS = createGlassCasingBlock(
            "blue_framed_glass", "蓝色镶边玻璃", CTNHCore.id("block/casings/space_elevator/blue_framed_glass"),
            () -> RenderType::cutoutMipped);

    public static final BlockEntry<Block> HOT_GLASS = REGISTRATE
            .block("hot_glass", Block::new)
            .cnlang("热玻璃")
            .initialProperties(() -> Blocks.GLASS)
            .addLayer(() -> RenderType::translucent)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(),
                    prov.models().cubeAll("hot_glass", CTNHCore.id("block/hot_glass"))))
            .item(UnplaceableBlockItem::new)
            .build()
            .register();

    public static final BlockEntry<RotatedPillarBlock> TEST_CASING = createRotateCasing("test_machine_casing", "t1");
    public static final BlockEntry<RotatedPillarBlock> ATOMS_SPLIT_BLOCKS = createRotateCasing("atoms_split_blocks",
            "atomssplit", "原子裂解方块");
    public static final BlockEntry<PhotovoltaicBlock> VIBRANT_PHOTOVOLTAIC_BLOCK = createPhotovoltaicBlock(
            PhotovoltaicBlock.PhotovoltaicType.VIBRANT_PHOTOVOLTAIC_BLOCK,
            ("block/vibrant_photovoltaic_block"), "振动光伏方块");
    public static final BlockEntry<PhotovoltaicBlock> ENERGETIC_PHOTOVOLTAIC_BLOCK = createPhotovoltaicBlock(
            PhotovoltaicBlock.PhotovoltaicType.ENERGETIC_PHOTOVOLTAIC_BLOCK,
            ("block/energetic_photovoltaic_block"), "充能光伏方块");
    public static final BlockEntry<PhotovoltaicBlock> PULSATING_PHOTOVOLTAIC_BLOCK = createPhotovoltaicBlock(
            PhotovoltaicBlock.PhotovoltaicType.PULSATING_PHOTOVOLTAIC_BLOCK,
            ("block/pulsating_photovoltaic_block"), "脉冲光伏方块");
    public static final BlockEntry<ActiveBlock> PV_COIL = createActiveCasing("pv_coil", "光伏线圈方块", "block/flux/pv_coil");

    // Fireboxes
    public static final BlockEntry<ActiveBlock> NAQUADAH_FIREBOX = createFireboxCasing(
            CTNHBoilerFireboxType.NAQUADAH_FIREBOX, "硅岩燃烧室");

    public static final BlockEntry<PhotovoltaicBlock> PHOTON_PRESS_COND_BLOCK = createPhotovoltaicBlock(
            PhotovoltaicBlock.PhotovoltaicType.PHOTON_PRESS_COND_BLOCK,
            "block/photon_press_cond_block", "光压传导光伏方块");

    public static final BlockEntry<Block> HIGH_SPEED_PIPE_BLOCK = REGISTRATE
            .block("high_speed_pipe_block", Block::new)
            .cnlang("高速管道方块")
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.getEntry(),
                        prov.models().cubeAll(ctx.getName(), CTNHCore.id("block/speedingpipe")));
            })
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
            .item(BlockItem::new)
            .build()
            .register();

    private static BlockEntry<ActiveBlock> createFireboxCasing(BoilerFireboxType type, String cnName) {
        var block = REGISTRATE
                .block("%s_casing".formatted(type.name()), ActiveBlock::new)
                .cnlang(cnName)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)

                .blockstate(CTNHModels.createFireboxModel(type))

                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
        ALL_FIREBOXES.put(type, block);
        return block;
    }

    public static BlockEntry<TurbineRotorBlock> HYPER_PLASMA_TURBINE_ROTOR = createTurbineRotorBlock(
            "hyper_plasma_turbine_rotor", 1, 1, 1, 1, "超極等离子涡轮转子");

    public static BlockEntry<SpaceStructuralFramework> NQ_EXCITE_CARBON_CARBON_NANOFIBER_STRUCTURAL_BLOCK = createSpaceStructuralFrame(
            SpaceStructuralFramework.SpaceStructuralFrameworkType.NQ_EXCITE_CARBON_CARBON_NANOFIBER_STRUCTURAL_BLOCK,
            "block/nq_excite_carbon_carbon_nanofiber_structural_block");

    public static void init() {
        // generateHyperRotorBlocks();
    }

    public static final class UnplaceableBlockItem extends BlockItem {

        public UnplaceableBlockItem(Block block, Item.Properties properties) {
            super(block, properties);
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            return InteractionResult.FAIL;
        }
    }

    // Utils
    public static BlockEntry<Block> createCasingBlock(String name, String cnName, ResourceLocation texture) {
        return createCasingBlock(name, cnName, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }

    private static BlockEntry<Block> createGlassCasingBlock(String name, String cnName, ResourceLocation texture,
                                                            Supplier<Supplier<RenderType>> type) {
        return createCasingBlock(name, cnName, GlassBlock::new, texture, () -> Blocks.GLASS, type);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      String cnName,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .cnlang(cnName)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .blockstate((ctx, prov) -> {
                    prov.simpleBlock(ctx.getEntry(), prov.models().cubeAll(name, texture));
                })
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    @SuppressWarnings("all")
    private static BlockEntry<CoilBlock> createCoilBlock(ICoilType coilType, String cnName) {
        BlockEntry<CoilBlock> coilBlock = REGISTRATE
                .block("%s_coil_block".formatted(coilType.getName()), p -> new CoilBlock(p, coilType))
                .cnlang(cnName)
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
    private static BlockEntry<PhotovoltaicBlock> createPhotovoltaicBlock(IPBData pbdata, String location,
                                                                         String cnName) {
        var photovoltaicblock = REGISTRATE.block("%s".formatted(pbdata.getPhotovoltaicName()),
                p -> new PhotovoltaicBlock(p, pbdata))
                .cnlang(cnName)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(CTNHModels.createpvModel(pbdata.getPhotovoltaicName(), location))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();

        CTNHBlockMaps.PhotovoltaicBlock.put(pbdata, photovoltaicblock);
        return photovoltaicblock;
    }

    @SuppressWarnings("all")
    private static BlockEntry<SpaceStructuralFramework> createSpaceStructuralFrame(ISSFData pbdata, String location) {
        var ssfblock = REGISTRATE.block("%s".formatted(pbdata.getSpaceStructuralFrameworkName()),
                p -> new SpaceStructuralFramework(p, pbdata))
                .cnlang("硅岩激发碳纳米太空结构方块")
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(CTNHModels.createssfModel(pbdata.getSpaceStructuralFrameworkName(), location))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();

        CTNHBlockMaps.SpaceStructuralFramework.put(pbdata, ssfblock);
        return ssfblock;
    }

    @SuppressWarnings("all")
    public static BlockEntry<ActiveBlock> createActiveCasing(String name, String cnName, String baseModelPath) {
        return REGISTRATE.block(name, ActiveBlock::new)
                .cnlang(cnName)
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
        return createRotateCasing(name, map, null);
    }

    private static BlockEntry<RotatedPillarBlock> createRotateCasing(String name, String map, String cnName) {
        var builder = REGISTRATE.block(name, RotatedPillarBlock::new);
        if (cnName != null) builder.cnlang(cnName);
        return builder
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(CTNHModels.createMapCasingModel(name, map))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static BlockEntry<TurbineRotorBlock> createTurbineRotorBlock(String name, int R, int G, int B, int A,
                                                                        String cnName) {
        return REGISTRATE.block(name, TurbineRotorBlock.create(R, G, B, A))
                .cnlang(cnName)
                .initialProperties(() -> Blocks.OBSIDIAN)
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(),
                        prov.models().cubeAll(name, ResourceLocation.tryParse("minecraft:block/iron_block"))))
                .item(TurbineRotorItem::new)
                .build()
                .register();
    }

    private static void registerCasingVariants(String baseName, String cnPrefix, ResourceLocation texture) {
        // Slab
        REGISTRATE.block(baseName + "_slab", SlabBlock::new)
                .cnlang(cnPrefix + "台阶")
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(),
                        prov.models().slab(baseName + "_slab", texture, texture, texture),
                        prov.models().slabTop(baseName + "_slab_top", texture, texture, texture),
                        prov.models().cubeAll(baseName, texture)))
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new).build().register();

        // Wall
        REGISTRATE.block(baseName + "_wall", WallBlock::new)
                .cnlang(cnPrefix + "墙")
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .blockstate((ctx, prov) -> {
                    prov.wallBlock(ctx.getEntry(), texture);
                    prov.models().cubeAll(baseName + "_wall", texture);
                })
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new).build().register();

        // Stairs — StairBlock 构造函数需要 (BlockState, Properties)
        REGISTRATE.block(baseName + "_stairs", p -> new StairBlock(Blocks.IRON_BLOCK.defaultBlockState(), p))
                .cnlang(cnPrefix + "楼梯")
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), texture))
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new).build().register();

        // Fence
        REGISTRATE.block(baseName + "_fence", FenceBlock::new)
                .cnlang(cnPrefix + "栅栏")
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .blockstate((ctx, prov) -> {
                    prov.fenceBlock(ctx.getEntry(), texture);
                    prov.models().cubeAll(baseName + "_fence", texture);
                })
                .tag(CustomTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new).build().register();
    }
}
