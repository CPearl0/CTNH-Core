package io.github.cpearl0.ctnhcore.data.recipe;

import io.github.cpearl0.ctnhcore.data.recipe.create.CreateRecipes;
import io.github.cpearl0.ctnhcore.data.recipe.immersiveaircraft.ImmersiveAircraftRecipes;
import io.github.cpearl0.ctnhcore.data.recipe.modmodify.EIORecipes;
import io.github.cpearl0.ctnhcore.data.recipe.modmodify.omnicells.QuantumOmniRecipes;

import tech.vixhentx.mcmod.ctnhlib.data.recipe.RecipeRemovalHelper;

import java.util.List;

/**
 * 配方删除中心。
 *
 * <p>
 * 所有删除统一通过 {@link #remove(RemoveFilter)} 入口。
 *
 * <p>
 * 支持的 filter 字段：
 * <ul>
 * <li>{@code id} — 精确 ID（String 或 List&lt;String&gt;）</li>
 * <li>{@code idRegex} — ID正则</li>
 * <li>{@code mod} — 模组ID</li>
 * <li>{@code type} — ID 派生类型（{@code namespace:first-path-segment}）</li>
 * <li>{@code not} — 排除条件（KubeJS 风格的反选 filter）</li>
 * <li>{@code or} — 任一子条件匹配</li>
 * </ul>
 *
 * <p>
 * 通用过滤和 {@code RecipeManager.apply()} 注入由 CTNH-Lib 提供。
 */
public class RecipeRemoval {

    private RecipeRemoval() {}

    public static List<RecipeRemovalHelper.RemoveFilter> getFilters() {
        return RecipeRemovalHelper.getFilters();
    }

    /**
     * Registers an ID-only filter. Top-level fields are combined with AND.
     */
    public static void remove(RecipeRemovalHelper.RemoveFilter filter) {
        RecipeRemovalHelper.remove(filter);
    }

    // ========== 主入口 ==========

    /**
     * 初始化所有删除规则。所有删除通过 {@link #remove(RemoveFilter)} 注册，
     * 最终在 {@code RecipeManagerApplyMixin} 中统一处理。
     */
    public static void init() {

        // ===== 外部类删除（与 RecipeRemoval 同模块/相关模块） =====
        EIORecipes.eioRemovals();
        QuantumOmniRecipes.omniRemovals();
        CreateRecipes.createRemovals();
        ImmersiveAircraftRecipes.immersiveAircraftRemovals();
        SophisticatedStorageRecipes.sophisticatedStorageRemovals();

        // ===== 本类删除（按模组/功能分组） =====
        adAstraRemovals();
        adExtendraRemovals();
        ae2Removals();
        ae2additionsRemovals();
        ae2csRemovals();
        ae2thingsRemovals();
        aetherRemovals();
        angelblockrenewedRemovals();
        apotheosisRemovals();
        arsNouveauRemovals();
        avaritiaRemovals();
        biofactoryRemovals();
        biomancyRemovals();
        botaniaRemovals();
        biomesoplentyRemovals();
        createdieselgeneratorsRemovals();
        createoreexcavationRemovals();
        ctnhcoreRemovals();
        deepAetherRemovals();
        delightRemovals();
        expatternproviderRemovals();
        extrabotanyRemovals();
        farmersdelightRemovals();
        functionalstorageRemovals();
        gtceuRemovals();
        hostilenetworksRemovals();
        javdRemovals();
        legendarysurvivaloverhaulRemovals();
        mae2Removals();
        merequesterRemovals();
        minecraftRemovals();
        miscRemovals();
        mynethersdelightRemovals();
        mythicbotanyRemovals();
        pccardRemovals();
        sophisticatedbackpacksRemovals();
        tetranichematerialsRemovals();
        tfmgRemovals();
        thermalRemovals();
        twilightforestRemovals();
        vintageimprovementsRemovals();
    }

    // ========== 按模组/功能分组的删除方法 ==========

    public static void adAstraRemovals() {
        remove(new RemoveFilter().idRegex("ad_astra:(.*)"));
        remove(new RemoveFilter().id("ad_astra:desh_fluid_pipe"));
        remove(new RemoveFilter().id("ad_astra:ostrum_fluid_pipe"));
        remove(new RemoveFilter().id("ad_astra:fluid_pipe_duct"));
    }

    public static void adExtendraRemovals() {
        remove(new RemoveFilter().mod("ad_extendra").type("ad_extendra:smelting"));
    }

    public static void ae2Removals() {
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_components_cell_1k_part"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_components_cell_4k_part"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_components_cell_16k_part"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_components_cell_64k_part"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_components_cell_256k_part"));
        remove(new RemoveFilter().id("ae2:network/cables/glass_fluix"));
        remove(new RemoveFilter().id("ae2:network/cables/covered_fluix"));
        remove(new RemoveFilter().id("ae2:network/blocks/pattern_providers_interface"));
        remove(new RemoveFilter().id("ae2:network/blocks/interfaces_interface"));
        remove(new RemoveFilter().id("ae2:transform/certus_quartz_crystals"));
        remove(new RemoveFilter().type("ae2:inscriber"));
        remove(new RemoveFilter().type("expatternprovider:cutter"));
        remove(new RemoveFilter().id("ae2:network/blocks/energy_energy_acceptor_alt"));
        remove(new RemoveFilter().id("ae2:network/blocks/energy_energy_acceptor"));
        remove(new RemoveFilter().id("ae2:network/blocks/crystal_processing_charger"));
        remove(new RemoveFilter().id("ae2:network/blocks/inscribers"));
        remove(new RemoveFilter().id("ae2:network/parts/quartz_fiber_part"));
        remove(new RemoveFilter().id("ae2:network/parts/storage_bus"));
        remove(new RemoveFilter().id("ae2:network/crafting/patterns_blank"));
        remove(new RemoveFilter().id("ae2:network/parts/terminals_pattern_encoding"));
        remove(new RemoveFilter().id("ae2:network/crafting/molecular_assembler"));
        remove(new RemoveFilter().id("ae2:materials/basiccard"));
        remove(new RemoveFilter().id("ae2:materials/advancedcard"));
        remove(new RemoveFilter().id("ae2:inscriber/silicon_print"));
        remove(new RemoveFilter().id("ae2:inscriber/ender_dust"));
        remove(new RemoveFilter().id("ae2:inscriber/certus_quartz_dust"));
        remove(new RemoveFilter().id("create:milling/compat/ae2/ender_pearl"));
        remove(new RemoveFilter().id("create:milling/compat/ae2/certus_quartz"));
        remove(new RemoveFilter().id("ae2:network/crafting/cpu_crafting_unit"));
        remove(new RemoveFilter().id("ae2:network/cells/item_cell_housing"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_cell_housing"));
        remove(new RemoveFilter().id("ae2:network/wireless_booster"));
        remove(new RemoveFilter().id("ae2:decorative/quartz_glass"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_cell_1k"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_cell_4k"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_cell_16k"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_cell_64k"));
        remove(new RemoveFilter().id("ae2:network/cells/item_storage_cell_256k"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_storage_cell_1k"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_storage_cell_4k"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_storage_cell_16k"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_storage_cell_64k"));
        remove(new RemoveFilter().id("ae2:network/cells/fluid_storage_cell_256k"));
        remove(new RemoveFilter().id("ae2:network/wireless_part"));
        remove(new RemoveFilter().id("ae2:network/blocks/storage_drive"));
        remove(new RemoveFilter().id("ae2:network/parts/import_bus"));
        remove(new RemoveFilter().id("ae2:network/parts/export_bus"));
    }

    public static void ae2additionsRemovals() {
        remove(new RemoveFilter().idRegex("ae2additions:.*disk_item_256k.*"));
        remove(new RemoveFilter().idRegex("ae2additions:.*disk_fluid_housing.*"));
        remove(new RemoveFilter().idRegex("ae2additions:.*super_cell_housing.*"));
    }

    public static void ae2csRemovals() {
        remove(new RemoveFilter().idRegex("ae2cs:(.*)"));
    }

    public static void ae2thingsRemovals() {
        remove(new RemoveFilter().idRegex("ae2things:.*disk_housing.*"));
        remove(new RemoveFilter().id("ae2things:cells/disk_drive_1k"));
    }

    public static void aetherRemovals() {
        remove(new RemoveFilter().id("aether:skyroot_crafting_table"));
        remove(new RemoveFilter().id("aether:skyroot_chest"));
        remove(new RemoveFilter().id("aether:aether_saddle"));
        remove(new RemoveFilter().id("aether:moa_egg_pumpkin_pie"));
        remove(new RemoveFilter().idRegex("aether:skyroot_(.*)"));
    }

    public static void angelblockrenewedRemovals() {
        remove(new RemoveFilter().id("angelblockrenewed:angel_block"));
    }

    public static void apotheosisRemovals() {
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_light"));
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_light_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/spawn_count"));
        remove(new RemoveFilter().id("apotheosis:spawner/spawn_count_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/max_nearby"));
        remove(new RemoveFilter().id("apotheosis:spawner/max_nearby_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/baby"));
        remove(new RemoveFilter().id("apotheosis:spawner/baby_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/redstone_control"));
        remove(new RemoveFilter().id("apotheosis:spawner/redstone_control_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/no_ai"));
        remove(new RemoveFilter().id("apotheosis:spawner/no_ai_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/min_delay"));
        remove(new RemoveFilter().id("apotheosis:spawner/min_delay_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/max_delay"));
        remove(new RemoveFilter().id("apotheosis:spawner/max_delay_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_conditions"));
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_conditions_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/player_range"));
        remove(new RemoveFilter().id("apotheosis:spawner/player_range_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_players"));
        remove(new RemoveFilter().id("apotheosis:spawner/ignore_players_inverted"));
        remove(new RemoveFilter().id("apotheosis:spawner/spawn_range"));
        remove(new RemoveFilter().id("apotheosis:spawner/spawn_range_inverted"));
    }

    public static void arsNouveauRemovals() {
        remove(new RemoveFilter().id("ars_nouveau:novice_spell_book"));
    }

    public static void avaritiaRemovals() {
        remove(new RemoveFilter().id("minecraft:end_portal_frame"));
        remove(new RemoveFilter().id("avaritia:star_fuel"));
        remove(new RemoveFilter().id("avaritia:star_fuel_alternate"));
        remove(new RemoveFilter().id("minecraft:star_fuel"));
        remove(new RemoveFilter().id("avaritia:botania_mana_tablet"));
        remove(new RemoveFilter().id("avaritia:infinity_ingot"));
        remove(new RemoveFilter().id("avaritia:infinity_catalyst_eternal"));
        remove(new RemoveFilter().id("avaritia:infinity_catalyst"));
        remove(new RemoveFilter().idRegex("avaritia:(.*)neutron_collector"));
        remove(new RemoveFilter().idRegex("avaritia:(.*)neutron_compressor"));
        remove(new RemoveFilter().id("avaritia:extreme_crafting_table"));
    }

    public static void biofactoryRemovals() {
        remove(new RemoveFilter().id("biofactory:compacting/flesh_block_from_flesh_bits"));
    }

    public static void biomancyRemovals() {
        remove(new RemoveFilter().type("biomancy:bio_brewing"));
        remove(new RemoveFilter().type("biomancy:bio_forging"));
        remove(new RemoveFilter().type("biomancy:digesting"));
    }

    public static void botaniaRemovals() {
        remove(new RemoveFilter().id("botania:terra_plate/terrasteel_ingot"));
    }

    public static void biomesoplentyRemovals() {
        remove(new RemoveFilter().id("biomesoplenty:tnt_from_bop_sand"));
    }

    public static void createdieselgeneratorsRemovals() {
        remove(new RemoveFilter().id("createdieselgenerators:basin_fermenting/fermented_spider_eye"));
        remove(new RemoveFilter().id("createdieselgenerators:basin_fermenting/fermentable"));
        remove(new RemoveFilter().id("createdieselgenerators:compression_molding/bucket"));
        remove(new RemoveFilter().idRegex("createdieselgenerators:(?:crushing/wood_chip_.*|cutting/wood_chips)"));
        remove(new RemoveFilter().id("createdieselgenerators:distillation/acid"));
        remove(new RemoveFilter().id("createdieselgenerators:distillation/superheated_crude_oil"));
        remove(new RemoveFilter().id("createdieselgenerators:bulk_fermenting/fermentable"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/engine_piston_from_rods"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/burner"));
        remove(new RemoveFilter().id("createdieselgenerators:mixing/asphalt_block"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/asphalt_block"));
        remove(new RemoveFilter().id("createdieselgenerators:mixing/biodiesel"));
        remove(new RemoveFilter().id("createdieselgenerators:compacting/plant_oil"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/pumpjack_hole"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/pumpjack_bearing"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/pumpjack_head"));
        remove(new RemoveFilter().id("createdieselgenerators:mechanical_crafting/pumpjack_crank"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/huge_diesel_engine"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/engine_pistone"));
        remove(new RemoveFilter().id("createdieselgenerators:crafting/diesel_engine"));
    }

    public static void createoreexcavationRemovals() {
        remove(new RemoveFilter().idRegex("createoreexcavation:ore_vein_type(.*)"));
        remove(new RemoveFilter().idRegex("createoreexcavation:drilling\\/(.*)"));
        remove(new RemoveFilter().id("createoreexcavation:vein_finder"));
    }

    public static void ctnhcoreRemovals() {
        remove(new RemoveFilter().id("ctnhcore:assembler/cover_ender_fluid_link"));
    }

    public static void deepAetherRemovals() {
        remove(new RemoveFilter().id("deep_aether:skyroot_crafting_table"));
        remove(new RemoveFilter().id("deep_aether:pumpkin_pie"));
    }

    public static void delightRemovals() {
        remove(new RemoveFilter().idRegex("(.*)delight:.*_knife(?:_smithing)?"));
    }

    public static void expatternproviderRemovals() {
        remove(new RemoveFilter().id("expatternprovider:ei"));
        remove(new RemoveFilter().id("expatternprovider:epp"));
        remove(new RemoveFilter().id("expatternprovider:ei_upgrade"));
        remove(new RemoveFilter().id("expatternprovider:epp_upgrade"));
        remove(new RemoveFilter().id("expatternprovider:ebus_out"));
        remove(new RemoveFilter().id("expatternprovider:ebus_in"));
        remove(new RemoveFilter().id("expatternprovider:ebus_upgrade"));
        remove(new RemoveFilter().id("expatternprovider:epa"));
        remove(new RemoveFilter().id("expatternprovider:epa_upgrade"));
        remove(new RemoveFilter().id("expatternprovider:ex_drive"));
        remove(new RemoveFilter().id("expatternprovider:ex_drive_upgrade"));
        remove(new RemoveFilter().id("expatternprovider:tag_storage_bus"));
        remove(new RemoveFilter().id("expatternprovider:tag_export_bus"));
        remove(new RemoveFilter().id("expatternprovider:ex_molecular_assembler"));
        remove(new RemoveFilter().id("expatternprovider:ingredient_buffer"));
        remove(new RemoveFilter().id("expatternprovider:ex_inscriber"));
        remove(new RemoveFilter().id("expatternprovider:ex_charger"));
        remove(new RemoveFilter().id("expatternprovider:circuit_cutter"));
    }

    public static void extrabotanyRemovals() {
        remove(new RemoveFilter().id("extrabotany:terra_plate/the_universe"));
        remove(new RemoveFilter().mod("extrabotany").type("extrabotany:petal_apothecary"));
        remove(new RemoveFilter().mod("extrabotany").type("extrabotany:terra_plate"));
    }

    public static void farmersdelightRemovals() {
        remove(new RemoveFilter().id("farmersdelight:paper_from_tree_bark"));
    }

    public static void functionalstorageRemovals() {
        remove(new RemoveFilter().id("functionalstorage:oak_drawer_alternate_x1"));
        remove(new RemoveFilter().id("functionalstorage:oak_drawer_alternate_x2"));
        remove(new RemoveFilter().id("functionalstorage:oak_drawer_alternate_x4"));
        remove(new RemoveFilter().id("functionalstorage:fluid_2"));
        remove(new RemoveFilter().id("functionalstorage:fluid_4"));
    }

    public static void gtceuRemovals() {

        remove(new RemoveFilter().id("gtceu:shaped/ulv_machine_hull"));
        remove(new RemoveFilter().id("gtceu:shaped/casing_hsse_sturdy"));
        remove(new RemoveFilter().id("gtceu:assembler/casing_hsse_sturdy"));
        remove(new RemoveFilter().id("gtceu:shaped/diamond_sword"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/titanium_from_tetrachloride"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/titaniumtetrachloride"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/tungstic_acid_electrolysis"));
        remove(new RemoveFilter().id("gtceu:neutron_activator/naquadah"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/iridium_chloride"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/iridium_dioxide_dissolving"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/iridium_metal_residue_processh"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/iridium_chloride"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/iridium_chloride_separation"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/raw_platinum_separation"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/raw_platinum_separation"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/refined_platinum_salt_dust_ebf"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/iridium_metal_residue_process"));
        remove(new RemoveFilter().id("gtceu:smelting/smelt_dust_bedrock_dust_to_ingot"));
        remove(new RemoveFilter().id("gtceu:arc_furnace/arc_bedrock_dust_dust"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/indium_concentrate_separation"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/indium_concentrate_separation_4x"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/indium_concentrate_separation_4x"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_aluminium_sulfite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/phosphoric_acid_from_pentoxide"));
        remove(new RemoveFilter().id("gtceu:shaped/large_bronze_boiler"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/soda_ash_from_carbon_dioxide"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/blast_adamantite"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_to_plate"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_gear"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_block"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_to_ingot"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_small_gear"));
        remove(new RemoveFilter().id("gtceu:fluid_solidifier/solidify_adamantite_to_nugget"));
        remove(new RemoveFilter().id("gtceu:shaped/plate_double_graphite_ir_plate"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/iridium_dioxide_dissolving"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/iridium_chloride_separation"));
        remove(new RemoveFilter().id("gtceu:dehydrator/xenoauric_fluoroantimonic_acid"));
        remove(new RemoveFilter().id("gtceu:assembly_line/energy_hatch_uhv"));
        remove(new RemoveFilter().id("gtceu:rocket_engine/rp_1_mixed_fuel"));
        remove(new RemoveFilter().id("gtceu:rocket_engine/methylhydrazine_nitrate_rocket_fuel"));
        remove(new RemoveFilter().id("gtceu:rocket_engine/udmh_rocket_fuel"));
        remove(new RemoveFilter().id("gtceu:rocket_engine/dense_hydrazine_mixed_fuel"));
        remove(new RemoveFilter().id("gtceu:gas_turbine/coal_gas"));
        remove(new RemoveFilter().id("gtceu:gas_turbine/wood_gas"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/naphtha"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/diesel"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/light_fuel"));
        remove(new RemoveFilter().id("gtceu:shaped/filter_casing_sterile"));
        remove(new RemoveFilter().id("gtceu:shaped/maintenance_hatch_cleaning"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/calcite_from_quicklime"));
        remove(new RemoveFilter().id("gtceu:extractor/extract_osmium_tetroxide_dust"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/biodiesel"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/cetane_diesel"));
        remove(new RemoveFilter().id("gtceu:gas_turbine/benzene"));
        remove(new RemoveFilter().id("gtceu:gas_turbine/nitrobenzene"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/hydrogen_peroxide"));
        remove(new RemoveFilter().id("gtceu:distillation/distill_fermented_biomass"));
        remove(new RemoveFilter().id("gtceu:pyrolyse_oven/bio_chaff_to_fermented_biomass"));
        remove(new RemoveFilter().id("gtceu:pyrolyse_oven/bio_chaff_to_biomass"));
        remove(new RemoveFilter().id("gtceu:fermenter/fermented_biomass"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/iodine_solution"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/iodine_solution"));
        remove(new RemoveFilter().id("gtceu:assembler/cover_ender_fluid_link"));
        remove(new RemoveFilter().id("gtceu:assembler/space_helmet"));
        remove(new RemoveFilter().id("gtceu:shaped/space_suit"));
        remove(new RemoveFilter().id("gtceu:shaped/space_pants"));
        remove(new RemoveFilter().id("gtceu:shaped/space_boots"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/rutile_from_ilmenite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_green_sapphire"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_sapphire"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_ruby"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_pyrope"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_granite_red"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_potassium_feldspar"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_pollucite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_kyanite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/bauxite_electrolysis"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_topaz"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_spodumene"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_spessartine"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_sodalite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_mica"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_lepidolite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_lazurite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_grossular"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_glauconite_sand"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_emerald"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_blue_topaz"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_biotite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_alunite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_almandine"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_chromite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/platinum_group_sludge_tiny_dust1"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/pgs_from_pentlandite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/platinum_group_sludge_dust1_lv"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/pgs_from_chalcopyrite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/pgs_from_chalcocite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/pgs_from_tetrahedrite"));
        remove(new RemoveFilter().id("gtceu:large_chemical_reactor/pgs_from_bornite"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/platinum_group_sludge_tiny_dust1"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/pgs_from_pentlandite"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/platinum_group_sludge_dust1_lv"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/pgs_from_chalcopyrite"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/pgs_from_chalcocite"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/pgs_from_tetrahedrite"));
        remove(new RemoveFilter().id("gtceu:chemical_reactor/pgs_from_bornite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_andradite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_ferrosilite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_wollastonite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_obsidian"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_talc"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_soapstone"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/bentonite_electrolysis"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_asbestos"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_uvarovite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_fullers_earth"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_silicon_dioxide"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_silicon_fluoride"));
        remove(new RemoveFilter().id("gtceu:vacuum_freezer/liquid_oxygen"));
        remove(new RemoveFilter().id("gtceu:shapeless/dust_brass"));
        remove(new RemoveFilter().id("gtceu:shapeless/dust_bronze"));
        remove(new RemoveFilter().id("gtceu:shapeless/potin_dust"));
        remove(new RemoveFilter().id("gtceu:shaped/coated_board"));
        remove(new RemoveFilter().id("gtceu:shapeless/coated_board_1x"));
        remove(new RemoveFilter().id("gtceu:shaped/basic_circuit_board"));
        remove(new RemoveFilter().id("gtceu:shaped/vacuum_tube"));
        remove(new RemoveFilter().id("gtceu:assembler/vacuum_tube_plain"));
        remove(new RemoveFilter().id("gtceu:assembler/vacuum_tube_red_alloy"));
        remove(new RemoveFilter().id("gtceu:assembler/vacuum_tube_red_alloy_annealed"));
        remove(new RemoveFilter().id("gtceu:shaped/small_gear_andesite_alloy"));
        remove(new RemoveFilter().id("gtceu:shaped/gear_andesite_alloy"));
        remove(new RemoveFilter().id("gtceu:shaped/small_gear_wood"));
        remove(new RemoveFilter().id("gtceu:shaped/gear_treated_wood"));
        remove(new RemoveFilter().id("gtceu:shaped/steam_turbine_lv"));
        remove(new RemoveFilter().id("gtceu:shapeless/iron_magnetic_stick"));
        remove(new RemoveFilter().id("gtceu:shaped/steam_turbine_mv"));
        remove(new RemoveFilter().id("gtceu:shaped/steam_turbine_hv"));
        remove(new RemoveFilter().id("gtceu:combustion_generator/raw_oil"));
        remove(new RemoveFilter().id("gtceu:assembler/oak_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/spruce_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/birch_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/jungle_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/acacia_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/dark_oak_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/mangrove_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/cherry_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/bamboo_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/crimson_stairs"));
        remove(new RemoveFilter().id("gtceu:assembler/warped_stairs"));
        remove(new RemoveFilter().id("gtceu:extractor/extract_ammonium_chloride_dust"));
        remove(new RemoveFilter().id("gtceu:extruder/nan_certificate"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_clay"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_wolframite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_tarkianite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_rheniite"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_palladium_sulfide"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_ruthenium_amalgam"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_osmium_iron_spinel"));
        remove(new RemoveFilter().id("gtceu:smashing_factory_recipes/smashing_factory_recipes/macerate_rail"));
        remove(new RemoveFilter().id("gtceu:smashing_factory_recipes/smashing_factory_recipes/macerate_powered_rail"));
        remove(new RemoveFilter()
                .id("gtceu:smashing_factory_recipes/smashing_factory_recipes/macerate_activator_rail"));
        remove(new RemoveFilter().id("gtceu:smashing_factory_recipes/smashing_factory_recipes/macerate_detector_rail"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/blast_high_temp_wrought_precursor"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/blast_high_temp_wrought_precursor_gas"));
        remove(new RemoveFilter().id("gtceu:vacuum_freezer/cool_hot_high_temp_wrought_precursor_ingot"));
        remove(new RemoveFilter().id("gtceu:shaped/bronze_primitive_blast_furnace"));
        remove(new RemoveFilter().id("gtceu:smelting/wrought_iron_nugget"));
        remove(new RemoveFilter().idRegex("gtceu:shaped\\/foil_(.*)"));
        remove(new RemoveFilter().idRegex("gtceu:shaped\\/spring_(.*)"));
        remove(new RemoveFilter().id("gtceu:shapeless/block_decompress_ender_eye"));
        remove(new RemoveFilter().id("gtceu:forge_hammer/hammer_ender_eye_block_to_gem"));
        remove(new RemoveFilter().id("gtceu:shapeless/pumpkin_pie_from_dough"));
        remove(new RemoveFilter().id("gtceu:research_station/1x_gtceu_wetware_processor_assembly"));
        remove(new RemoveFilter().id("gtceu:research_station/1x_gtceu_wetware_processor_computer"));
        remove(new RemoveFilter().id("gtceu:assembler/assembly_line_casing"));
        remove(new RemoveFilter().id("gtceu:assembler/assembly_control_casing"));
        remove(new RemoveFilter().id("gtceu:electrolyzer/decomposition_electrolyzing_borax"));
        remove(new RemoveFilter().id("gtceu:electric_blast_furnace/naq_ingot"));

        remove(new RemoveFilter().id("gtceu:electrolyzer/zeolite_electrolysis"));
        // 移除硅晶圆直接激光蚀刻成 RAM/LPIC/SSoC 晶圆的配方
        remove(new RemoveFilter().id("gtceu:laser_engraver/engrave_ram_*"));
        remove(new RemoveFilter().id("gtceu:laser_engraver/engrave_lpic_*"));
        remove(new RemoveFilter().id("gtceu:laser_engraver/engrave_ssoc_*"));
        // 移除微处理器系列旧配方（LV~IV 段），改为 HV 段新配方
        remove(new RemoveFilter().id("gtceu:circuit_assembler/microprocessor_lv*"));
        remove(new RemoveFilter().id("gtceu:circuit_assembler/processor_mv*"));
        remove(new RemoveFilter().id("gtceu:circuit_assembler/processor_assembly_hv*"));
        remove(new RemoveFilter().id("gtceu:circuit_assembler/workstation_ev*"));
        remove(new RemoveFilter().id("gtceu:circuit_assembler/mainframe_iv*"));
        // 焊锡（soldering_alloy）焊料变体一并移除
//        remove(new RemoveFilter().id("gtceu:circuit_assembler/microprocessor_lv_soldering_alloy"));
//        remove(new RemoveFilter().id("gtceu:circuit_assembler/processor_mv_soldering_alloy"));
//        remove(new RemoveFilter().id("gtceu:circuit_assembler/processor_assembly_hv_soldering_alloy"));
//        remove(new RemoveFilter().id("gtceu:circuit_assembler/workstation_ev_soldering_alloy"));
//        remove(new RemoveFilter().id("gtceu:circuit_assembler/mainframe_iv_soldering_alloy"));
//        remove(new RemoveFilter().id("gtceu:centrifuge/decomposition_centrifuging__redstone"));

        // 移除 GTCEu 默认 LV~IV 电动马达与 LV 组件工作台配方，统一改为 CTNH 工作台配方
        remove(new RemoveFilter().idRegex(
                "gtceu:shaped/(?:electric_motor_(?:lv_(?:iron|steel)|mv|hv|ev|iv)|electric_piston_lv|conveyor_module_lv_.*|electric_pump_lv_.*|robot_arm_lv|fluid_regulator_lv|emitter_lv)"));
        // 移除 GTCEu 默认 LV 组件组装机配方，统一改为 CTNH 组装机配方（与 CTNH 工作台配方一致）
        remove(new RemoveFilter().idRegex(
                "gtceu:assembler/(?:electric_motor_lv_(?:iron|steel)|electric_piston_lv|conveyor_module_lv_.*|electric_pump_lv_.*|robot_arm_lv|fluid_regulator_lv|emitter_lv)"));
        // 移除 CTPP 基础构件工作台配方，统一改为 CTNH 工作台配方（齿轮改为小齿轮）
        remove(new RemoveFilter().id("gtceu:basic_mechanism"));
        // 移除 GTCEu 默认 LV 两极磁化机工作台配方，统一改为 CTNH 工作台配方
        remove(new RemoveFilter().id("gtceu:shaped/lv_polarizer"));
        // 移除 GTCEu 默认 LV 热力离心机工作台配方，统一改为 CTNH 工作台配方
        remove(new RemoveFilter().id("gtceu:shaped/lv_thermal_centrifuge"));
        // 移除 GTCEu 默认 LV/MV 电子电路工作台配方，统一改为 CTNH 序列装配配方
        remove(new RemoveFilter().id("gtceu:shaped/electronic_circuit_lv"));
        remove(new RemoveFilter().id("gtceu:shaped/electronic_circuit_mv"));
    }

    public static void hostilenetworksRemovals() {
        remove(new RemoveFilter().mod("hostilenetworks"));
    }

    public static void javdRemovals() {
        remove(new RemoveFilter().id("javd:portal_block"));
    }

    public static void legendarysurvivaloverhaulRemovals() {
        remove(new RemoveFilter().id("legendarysurvivaloverhaul:thermometer"));
    }

    public static void mae2Removals() {
        remove(new RemoveFilter().idRegex("mae2:.*x_crafting_accelerator.*"));
    }

    public static void merequesterRemovals() {
        remove(new RemoveFilter().id("merequester:requester"));
    }

    public static void minecraftRemovals() {
        remove(new RemoveFilter().id("minecraft:end_portal_frame"));
        remove(new RemoveFilter().id("minecraft:bricks"));
        remove(new RemoveFilter().id("minecraft:lightning_rod"));
        remove(new RemoveFilter().id("minecraft:iron_trapdoor"));
    }

    public static void miscRemovals() {
        remove(new RemoveFilter().type("twilightforest:uncrafting_table"));
        // createdieselgenerators:hammering 全部 4 个配方（iron/copper/gold/brass sheet）由数据包 ctnh_create_iron_plate 覆盖为 GT
        // 板，不再删除
        remove(new RemoveFilter().idRegex("thermal:.*constantan.*"));
    }

    public static void mynethersdelightRemovals() {
        remove(new RemoveFilter().id("mynethersdelight:tnt_alt"));
    }

    public static void mythicbotanyRemovals() {
        remove(new RemoveFilter().idRegex("mythicbotany:.*_runic_altar"));
    }

    public static void pccardRemovals() {
        remove(new RemoveFilter().id("pccard:item/card_programmed_circuit"));
    }

    public static void tetranichematerialsRemovals() {
        remove(new RemoveFilter().id("tetranichematerials:red_gold_powder"));
        remove(new RemoveFilter().idRegex("tetranichematerials:.*lockwood_ingot.*"));
    }

    public static void tfmgRemovals() {
        remove(new RemoveFilter().id("tfmg:sequenced_assembly/steel_mechanism"));
        remove(new RemoveFilter().id("tfmg:sequenced_assembly/turbine_engine"));
        remove(new RemoveFilter().id("tfmg:distillation/heavy_oil"));
        remove(new RemoveFilter().idRegex("tfmg:.*screw.*"));
        remove(new RemoveFilter().idRegex("tfmg:.*turbine_blade.*"));
    }

    public static void thermalRemovals() {
        remove(new RemoveFilter().idRegex("thermal:.*constantan_ingot.*"));
    }

    public static void twilightforestRemovals() {
        remove(new RemoveFilter().id("twilightforest:wood/sorting_planks"));
        remove(new RemoveFilter().id("twilightforest:wood/mangrove_planks"));
        remove(new RemoveFilter().id("twilightforest:wood/dark_planks"));
        remove(new RemoveFilter().id("twilightforest:wood/time_planks"));
        remove(new RemoveFilter().id("twilightforest:wood/transformation_planks"));
        remove(new RemoveFilter().id("twilightforest:wood/mining_planks"));
    }

    public static void sophisticatedbackpacksRemovals() {
        // sophisticatedbackpacks/sophisticatedbackpacks.js:
        // event.remove({ output: 'sophisticatedbackpacks:void_upgrade' })
        remove(new RemoveFilter().id("sophisticatedbackpacks:void_upgrade"));
    }

    public static void vintageimprovementsRemovals() {
        remove(new RemoveFilter().id("vintageimprovements:craft/centrifuge"));
        remove(new RemoveFilter().type("vintageimprovements:coiling"));
        remove(new RemoveFilter().id("vintageimprovements:craft/spring_coiling_machine"));
        remove(new RemoveFilter().id("vintageimprovements:mechanical_crafting/lathe"));
        remove(new RemoveFilter().id("vintageimprovements:craft/vacuum_chamber"));
        remove(new RemoveFilter().id("vintageimprovements:craft/vibrating_table"));
        remove(new RemoveFilter().id("vintageimprovements:craft/curving_press"));
        remove(new RemoveFilter().id("vintageimprovements:craft/laser"));
        remove(new RemoveFilter().id("vintageimprovements:mechanical_crafting/helve_hammer"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/cobalt_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/rhodium_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/uranium_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/rose_gold_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/vanadium_ingot"));
        // 统一到 GT 钒材料：删除 Vintage Improvements 钒锭产出配方
        remove(new RemoveFilter().id("vintageimprovements:craft/vanadium_nuggets_to_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:craft/vanadium_block_to_ingots"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/invar_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/lead_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/tin_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/andesite_alloy"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/bronze_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/silver_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/platinum_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/palladium_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/zinc_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/nickel_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:pressing/osmium_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:sequenced_assembly/recipe_card"));
        remove(new RemoveFilter().id("vintageimprovements:copper_sulfate"));
        remove(new RemoveFilter().id("vintageimprovements:pressurizing/sulfuric_acid"));
        remove(new RemoveFilter().id("vintageimprovements:pressurizing/sulfur_trioxide_alt"));
        remove(new RemoveFilter().id("vintageimprovements:pressurizing/sulfur_trioxide"));
        remove(new RemoveFilter().id("vintageimprovements:pressurizing/sulfur_dioxide"));
        remove(new RemoveFilter().id(
                "vintageimprovements:pressurizing/compat/sulfur_dioxide_from_dust"));
        remove(new RemoveFilter().id("vintageimprovements:craft/sulfur_items_to_block"));
        remove(new RemoveFilter().id("vintageimprovements:craft/belt_grinder"));
        remove(new RemoveFilter().id("vintageimprovements:craft/grinder_belt"));
        remove(new RemoveFilter().id("vintageimprovements:craft/tin_rod"));
        remove(new RemoveFilter().idRegex("vintageimprovements:curving/.*_sheet"));
        remove(new RemoveFilter().idRegex("vintageimprovements:craft/.*_(?:rod|wire)"));
        remove(new RemoveFilter().idRegex("vintageimprovements:rolling/.*"));
        remove(new RemoveFilter().id("vintageimprovements:coiling/iron_ingot"));
        remove(new RemoveFilter().id("vintageimprovements:coiling/iron_rod"));
        remove(new RemoveFilter().id("vintageimprovements:curving/iron_sheet"));
        remove(new RemoveFilter().id("vintageimprovements:craft/steel_rod"));
        remove(new RemoveFilter().id("vintageimprovements:craft/nickel_rod"));
        remove(new RemoveFilter().id("vintageimprovements:craft/sulfur_item_to_nuggets"));
        remove(new RemoveFilter().id("vintageimprovements:craft/sulfur_nuggets_to_item"));
        remove(new RemoveFilter().id("vintageimprovements:craft/sulfur_block_to_items"));
    }

    /**
     * Compatibility type for existing Core recipe-removal declarations.
     */
    public static class RemoveFilter extends RecipeRemovalHelper.RemoveFilter {}
}
