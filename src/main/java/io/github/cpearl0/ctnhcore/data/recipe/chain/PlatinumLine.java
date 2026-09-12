package io.github.cpearl0.ctnhcore.data.recipe.chain;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.recipe.PlantCasingCondition;
import io.github.cpearl0.ctnhcore.data.materials.PlatinumLineMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.CTNHTagPrefixes;
import io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials.*;

public class PlatinumLine {

    public static void init(Consumer<FinishedRecipe> provider) {
        // 从 PlatinumChain.js 迁移的配方

        // --- 铂钯催化剂 ---
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder(CTNHCore.id("palladium_on_platinum"))
                .inputItems(TagPrefix.dust, Platinum)
                .inputItems(TagPrefix.dust, Palladium)
                .outputItems(CTNHTagPrefixes.catalyst, PalladiumOnPlatinum)
                .circuitMeta(32)
                .duration(160)
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        CTNHRecipeTypes.CHEMICAL_PLANT_RECIPES.recipeBuilder(CTNHCore.id("enriched_naquadah_reduction_2"))
                .inputItems(CTNHTagPrefixes.catalyst, CTNHMaterials.PalladiumOnPlatinum)
                .inputFluids(Hydrogen.getFluid(9000))
                .inputFluids(Nitrogen.getFluid(3000))
                .outputFluids(Ammonia.getFluid(3000))
                .EUt(GTValues.VA[GTValues.MV])
                .duration(600)
                .addCondition(new PlantCasingCondition(2))
                .save(provider);

        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("platinum_slurry_aqua_regia"))
                .inputItems(dust, PlatinumGroupSludge, 3) // 铂族矿渣
                .inputFluids(GTMaterials.AquaRegia.getFluid(6000))// 王水
                .outputFluids(PlatinumLineMaterials.GoldPlatinumPalladiumAcidSolution.getFluid(1000)) // 金-铂-钯酸性溶液
                .outputFluids(GTMaterials.NitricOxide.getFluid(1000)) // 一氧化氮
                .outputFluids(GTMaterials.Water.getFluid(2000)) // 水
                .outputItems(dust, PlatinumLineMaterials.PlatinumGroupResidue, 1) // 铂族残渣
                .EUt(GTValues.VA[GTValues.HV])
                .duration(640)
                .save(provider);

        // 溶液线
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("denitrate_gold_platinum_palladium_solution")) // 除硝金-铂-钯酸性溶液
                .inputFluids(PlatinumLineMaterials.GoldPlatinumPalladiumAcidSolution.getFluid(1000)) // 金-铂-钯酸性溶液
                .inputFluids(GTMaterials.HydrochloricAcid.getFluid(2000)) // 盐酸
                .outputFluids(GTMaterials.NitricOxide.getFluid(500)) // 一氧化氮
                .outputFluids(GTMaterials.Chlorine.getFluid(2000)) // 气态氯
                .outputFluids(PlatinumLineMaterials.DenitratedGoldPlatinumPalladiumSolution.getFluid(500)) // 除硝金-铂-钯酸性溶液
                .outputFluids(PlatinumLineMaterials.GoldPlatinumPalladiumAcidSolution.getFluid(500)) // 金-铂-钯酸性溶液
                .EUt(GTValues.VA[GTValues.HV])
                .duration(320)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ferrous_reduction_denitrated_solution"))
                .inputFluids(PlatinumLineMaterials.DenitratedGoldPlatinumPalladiumSolution.getFluid(1000))// 除硝金-铂-钯酸性溶液
                .inputFluids(PlatinumLineMaterials.FerrousSulfate.getFluid(1000))// 硫酸亚铁溶液
                .outputFluids(PlatinumLineMaterials.FerricSulfate.getFluid(1000))// 硫酸铁溶液
                .outputItems(dust, Gold, 1)// 金粉
                .outputFluids(PlatinumLineMaterials.ChloroplatinicChloropalladicSolution.getFluid(1000))// 氯铂酸-氯钯酸混合溶液
                .EUt(GTValues.VA[GTValues.MV])
                .duration(240)
                .save(provider);
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("palladium_palladium_platinum_oi_new_process"))
                .inputItems(dust, PlatinumLineMaterials.PlatinumOre, 7)
                .inputFluids(Chlorine.getFluid(8000))
                .inputFluids(HydrochloricAcid.getFluid(4000))
                .outputFluids(PlatinumLineMaterials.ChloroplatinicChloropalladicSolution.getFluid(1000))
                .EUt(GTValues.VA[GTValues.EV])
                .duration(360)
                .save(provider);
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("palladium_platinum_oi_new_process"))
                .inputItems(dust, PlatinumLineMaterials.PalladiumOre, 7)
                .inputFluids(Chlorine.getFluid(4000))
                .inputFluids(HydrochloricAcid.getFluid(2000))
                .inputItems(dust, AmmoniumChloride, 10)
                .outputFluids(PlatinumLineMaterials.ChloropalladicAcidMixture.getFluid(3000))
                .EUt(GTValues.VA[GTValues.EV])
                .duration(400)
                .save(provider);

        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chloroplatinate_synthesis"))
                .inputItems(dust, AmmoniumChloride, 4) // 氯化铵
                .inputFluids(PlatinumLineMaterials.ChloroplatinicChloropalladicSolution.getFluid(3000)) // 氯铂酸-氯钯酸混合溶液
                .outputItems(dust, PlatinumLineMaterials.AmmoniumChloroplatinate, 9) // 氯铂酸铵
                .outputFluids(GTMaterials.HydrochloricAcid.getFluid(2000)) // 盐酸
                .outputFluids(PlatinumLineMaterials.ChloropalladicAcidMixture.getFluid(3000)) // 氯钯酸混合物
                .EUt(GTValues.VA[GTValues.MV])
                .duration(400)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chloroplatinate_calcination"))
                .inputItems(dust, PlatinumLineMaterials.AmmoniumChloroplatinate, 9) // 氯铂酸铵
                .outputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .outputItemsRanged(ChemicalHelper.get(dust, PlatinumLineMaterials.SpongePlatinum), UniformInt.of(1, 3)) // 海绵铂
                .outputFluids(GTMaterials.Chlorine.getFluid(4000)) // 氯气
                .blastFurnaceTemp(500)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(600)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("sponge_platinum_calcination"))
                .inputItems(dust, PlatinumLineMaterials.SpongePlatinum, 1) // 海绵铂
                .outputItems(TagPrefix.ingot, GTMaterials.Platinum, 1) // 铂锭
                .blastFurnaceTemp(1700)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(500)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("palladium_ammonia_precipitation"))
                .inputFluids(PlatinumLineMaterials.ChloropalladicAcidMixture.getFluid(1000)) // 氯钯酸混合物
                .inputFluids(PlatinumLineMaterials.AmmoniaMonohydrate.getFluid(800)) // 一水合氨
                .outputItems(dust, PlatinumLineMaterials.Diamminedichloropalladium, 2) // 二氯二氨络亚钯
                .outputFluidsRanged(new FluidStack(GTMaterials.HydrochloricAcid.getFluid(), 1),
                        UniformInt.of(1000, 1200)) // 盐酸
                .EUt(GTValues.VA[GTValues.HV])
                .duration(320)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("palladium_ammine_hydrogen_reduction"))
                .inputItems(dust, PlatinumLineMaterials.Diamminedichloropalladium, 5) // 二氯二氨络亚钯
                .inputFluids(GTMaterials.Hydrogen.getFluid(2000)) // 氢气
                .outputItemsRanged(ChemicalHelper.get(dust, PlatinumLineMaterials.SpongePalladium), UniformInt.of(1, 2)) // 海绵钯
                .outputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .blastFurnaceTemp(500)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(400)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("sponge_palladium_smelting"))
                .inputItems(dust, PlatinumLineMaterials.SpongePalladium, 1)
                .outputItems(TagPrefix.ingot, GTMaterials.Palladium, 1)
                .blastFurnaceTemp(1500)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(540)
                .save(provider);
        // 残渣线
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("platinum_residue_smelting"))
                .inputItems(dust, PlatinumLineMaterials.PlatinumGroupResidue, 1) // 铂族残渣
                .inputItems(dust, PlatinumLineMaterials.Litharge, 2) // 密陀僧
                .inputItems(dust, GTMaterials.Carbon, 1) // 碳粉
                .outputItems(dust, PlatinumLineMaterials.NobleLead, 1) // 贵铅
                .outputFluids(GTMaterials.CarbonMonoxide.getFluid(1000)) // 一氧化碳
                .blastFurnaceTemp(2000)
                .EUt(GTValues.VA[GTValues.EV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder(CTNHCore.id("noble_lead_nitric_leach"))
                .inputItems(dust, PlatinumLineMaterials.NobleLead, 1) // 贵铅
                .inputFluids(GTMaterials.NitricAcid.getFluid(1000)) // 硝酸
                .outputFluids(PlatinumLineMaterials.NitricLeachSolution.getFluid(1000)) // 硝酸浸出液
                .outputItems(dust, PlatinumLineMaterials.EnrichedInertMixture, 1) // 富集惰性混合物
                .EUt(GTValues.VA[GTValues.HV])
                .duration(240)
                .save(provider);
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("nitric_leach_electrolysis"))
                .inputFluids(PlatinumLineMaterials.NitricLeachSolution.getFluid(1000))
                .outputItems(dust, GTMaterials.Silver, 1) // 银粉
                .outputItems(dust, GTMaterials.Lead, 1) // 铅粉
                .outputFluids(GTMaterials.NitrogenDioxide.getFluid(1000)) // 二氧化氮
                .EUt(1920)
                .duration(300)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("enriched_inert_mixture_sulfate_conversion"))
                .inputItems(dust, PlatinumLineMaterials.EnrichedInertMixture, 2) // 富集惰性混合物
                .inputFluids(GTMaterials.SodiumBisulfate.getFluid(144 * 42)) // 熔融硫酸氢钠
                .outputFluids(GTMaterials.RhodiumSulfate.getFluid(1000)) // 硫酸铑
                .outputItems(dust, SodiumSulfate, 21) // 硫酸钠
                .outputItems(dust, PlatinumLineMaterials.PreciousMetalMixture, 1) // 贵金属混合物
                .blastFurnaceTemp(1200)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(400)
                .save(provider);
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder(CTNHCore.id("rhodium_sulfate_solution_mixing"))
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .inputFluids(GTMaterials.RhodiumSulfate.getFluid(100)) // 硫酸铑
                .outputFluids(PlatinumLineMaterials.RhodiumSulfateSolution.getFluid(1000)) // 硫酸铑溶液
                .EUt(120)
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("rhodium_sulfate_to_hydroxide"))
                .inputFluids(PlatinumLineMaterials.RhodiumSulfateSolution.getFluid(10000)) // 硫酸铑溶液
                .inputItems(dust, GTMaterials.SodiumHydroxide, 18) // 氢氧化钠
                .outputItems(dust, PlatinumLineMaterials.RhodiumHydroxide, 8) // 氢氧化铑
                .outputItems(dust, SodiumSulfate, 21) // 硫酸钠
                .EUt(120)
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("rhodium_hydroxide_to_chlororhodic_acid"))
                .inputItems(dust, PlatinumLineMaterials.RhodiumHydroxide, 4) // 氢氧化铑
                .inputFluids(GTMaterials.HydrochloricAcid.getFluid(6000)) // 盐酸
                .outputFluids(PlatinumLineMaterials.ChlororhodicAcid.getFluid(1000)) // 氯铑酸
                .outputFluids(GTMaterials.Water.getFluid(3000)) // 水
                .EUt(GTValues.VA[GTValues.MV])
                .duration(300)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("chlororhodic_acid_to_ammonium_solution"))
                .inputFluids(PlatinumLineMaterials.ChlororhodicAcid.getFluid(1000)) // 氯铑酸
                .inputFluids(PlatinumLineMaterials.AmmoniaMonohydrate.getFluid(3000)) // 一水合氨
                .outputFluids(PlatinumLineMaterials.AmmoniumChlororhodateSolution.getFluid(4000)) // 氯铑酸铵溶液
                .EUt(GTValues.VA[GTValues.MV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.DISTILLATION_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororhodate_full_distill"))
                .inputFluids(PlatinumLineMaterials.AmmoniumChlororhodateSolution.getFluid(4000)) // 氯铑酸铵溶液
                .outputFluids(PlatinumLineMaterials.ConcentratedAmmoniumChlororhodate.getFluid(1000)) // 浓缩氯铑酸铵溶液
                .outputFluids(GTMaterials.Water.getFluid(3000)) // 水
                .EUt(GTValues.VA[GTValues.HV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.AUTOCLAVE_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororhodate_crystallization"))
                .circuitMeta(2)
                .inputFluids(PlatinumLineMaterials.ConcentratedAmmoniumChlororhodate.getFluid(1000)) // 浓缩氯铑酸铵溶液
                .inputItems(dust, PlatinumLineMaterials.AmmoniumChlororhodate, 1) // 氯铑酸铵
                .outputItems(dust, PlatinumLineMaterials.AmmoniumChlororhodate, 2) // 氯铑酸铵
                .EUt(GTValues.VA[GTValues.HV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.AUTOCLAVE_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororhodate_crystallization_two"))
                .circuitMeta(1)
                .inputFluids(PlatinumLineMaterials.ConcentratedAmmoniumChlororhodate.getFluid(1000)) // 浓缩氯铑酸铵溶液
                .outputItems(dust, PlatinumLineMaterials.AmmoniumChlororhodate, 1) // 氯铑酸铵
                .EUt(GTValues.VA[GTValues.HV])
                .duration(360)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororhodate_hydrogen_reduction"))
                .inputItems(dust, PlatinumLineMaterials.AmmoniumChlororhodate, 1) // 氯铑酸铵
                .inputFluids(GTMaterials.Hydrogen.getFluid(3000)) // 氢气
                .outputItems(dust, PlatinumLineMaterials.SpongeRhodium, 1) // 海绵铑
                .outputItems(dust, GTMaterials.AmmoniumChloride, 3) // 氯化铵
                .outputFluids(GTMaterials.Chlorine.getFluid(3000)) // 氯气
                .blastFurnaceTemp(1200)
                .EUt(GTValues.VA[GTValues.EV])
                .duration(400)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("sponge_rhodium_smelting"))
                .inputItems(dust, PlatinumLineMaterials.SpongeRhodium, 1) // 海绵铑
                .outputItems(TagPrefix.ingot, GTMaterials.Rhodium, 1) // 铑锭
                .blastFurnaceTemp(1966)
                .EUt(GTValues.VA[GTValues.EV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("lead_to_litharge_oxidation"))
                .inputItems(dust, GTMaterials.Lead, 1) // 铅粉
                .inputFluids(GTMaterials.Oxygen.getFluid(1000)) // 氧气
                .outputItems(dust, PlatinumLineMaterials.Litharge, 2) // 密陀僧（PbO）
                .EUt(GTValues.VA[GTValues.LV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("precious_metal_oxidation"))
                .inputItems(dust, PlatinumLineMaterials.PreciousMetalMixture, 1) // 贵金属混合物
                .inputFluids(PlatinumLineMaterials.SodiumPeroxide.getFluid(144 * 4)) // 熔融过氧化钠
                .inputFluids(GTMaterials.SodiumHydroxide.getFluid(144 * 6)) // 氢氧化钠溶液
                .outputFluids(PlatinumLineMaterials.SodiumOsmateRuthenateSolution.getFluid(2000)) // 锇钌酸钠溶液
                .outputItems(dust, PlatinumLineMaterials.IridiumDioxide, 3) // 二氧化铱
                .EUt(GTValues.VA[GTValues.EV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.ARC_FURNACE_RECIPES.recipeBuilder(CTNHCore.id("sodium_peroxide_synthesis"))
                .inputItems(dust, GTMaterials.Sodium, 2) // 钠粉
                .inputFluids(GTMaterials.Oxygen.getFluid(2000)) // 氧气
                .outputItems(dust, PlatinumLineMaterials.SodiumPeroxide, 4) // 过氧化钠
                .EUt(GTValues.VA[GTValues.LV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("osmate_ruthenate_chlorination"))
                .inputFluids(PlatinumLineMaterials.SodiumOsmateRuthenateSolution.getFluid(2000)) // 锇钌酸钠溶液
                .inputFluids(GTMaterials.Chlorine.getFluid(4000)) // 氯气
                .outputFluids(PlatinumLineMaterials.SodiumOsmateRuthenateChlorideSolution.getFluid(6000)) // 氯化锇钌酸钠溶液
                .EUt(GTValues.VA[GTValues.HV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.DISTILLATION_RECIPES.recipeBuilder(CTNHCore.id("osmium_ruthenium_distillation"))
                .inputFluids(PlatinumLineMaterials.SodiumOsmateRuthenateChlorideSolution.getFluid(6000)) // 氯化锇钌酸钠溶液
                .outputItems(dust, GTMaterials.Salt, 8) // 盐
                .outputFluids(GTMaterials.Water.getFluid(1500)) // 水
                .outputFluids(GTMaterials.OsmiumTetroxide.getFluid(1000)) // 四氧化锇
                .outputFluids(GTMaterials.RutheniumTetroxide.getFluid(1000)) // 四氧化钌
                .EUt(GTValues.VA[GTValues.EV])
                .duration(600)
                .save(provider);
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("osmium_tetroxide_reduction"))
                .inputFluids(GTMaterials.OsmiumTetroxide.getFluid(1000)) // 四氧化锇
                .inputFluids(GTMaterials.Ethanol.getFluid(1000)) // 乙醇
                .inputFluids(GTMaterials.HydrochloricAcid.getFluid(6000)) // 盐酸
                .outputFluids(PlatinumLineMaterials.ChlorosmicAcidGas.getFluid(1000)) // 氯锇酸
                .outputFluids(GTMaterials.Water.getFluid(4000)) // 水
                .outputFluids(PlatinumLineMaterials.Acetaldehyde.getFluid(1000)) // 乙醛
                .EUt(GTValues.VA[GTValues.HV])
                .duration(300)
                .save(provider);
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ruthenium_tetroxide_reduction"))
                .inputFluids(GTMaterials.RutheniumTetroxide.getFluid(1000)) // 四氧化钌
                .inputFluids(GTMaterials.Ethanol.getFluid(1000)) // 乙醇
                .inputFluids(GTMaterials.HydrochloricAcid.getFluid(6000)) // 盐酸
                .outputFluids(PlatinumLineMaterials.ChlororuthenicAcidGas.getFluid(1000)) // 氯钌酸
                .outputFluids(GTMaterials.Water.getFluid(4000)) // 水
                .outputFluids(PlatinumLineMaterials.Acetaldehyde.getFluid(1000)) // 乙醛
                .EUt(GTValues.VA[GTValues.HV])
                .duration(300)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlorosmate_synthesis"))
                .inputFluids(PlatinumLineMaterials.ChlorosmicAcidGas.getFluid(1000)) // 氯锇酸
                .inputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .outputItems(dust, PlatinumLineMaterials.AmmoniumChlorosmate, 1) // 氯锇酸铵
                .outputFluids(GTMaterials.HydrochloricAcid.getFluid(2000)) // 盐酸
                .EUt(GTValues.VA[GTValues.EV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlorosmate_decomposition"))
                .inputItems(dust, PlatinumLineMaterials.AmmoniumChlorosmate, 1) // 氯锇酸铵
                .inputFluids(GTMaterials.Hydrogen.getFluid(4000)) // 氢气
                .outputItems(dust, GTMaterials.Osmium, 1) // 锇粉
                .outputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .outputFluids(GTMaterials.HydrochloricAcid.getFluid(4000)) // 盐酸
                .blastFurnaceTemp(800)
                .EUt(GTValues.VA[GTValues.EV])
                .duration(260)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororuthenate_synthesis"))
                .inputFluids(PlatinumLineMaterials.ChlororuthenicAcidGas.getFluid(1000)) // 氯钌酸
                .inputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .outputItems(dust, PlatinumLineMaterials.AmmoniumChlororuthenate, 1) // 氯钌酸铵
                .outputFluids(GTMaterials.HydrochloricAcid.getFluid(2000)) // 盐酸
                .EUt(GTValues.VA[GTValues.EV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("ammonium_chlororuthenate_decomposition"))
                .inputItems(dust, PlatinumLineMaterials.AmmoniumChlororuthenate, 1) // 氯钌酸铵
                .inputFluids(GTMaterials.Hydrogen.getFluid(4000)) // 氢气
                .outputItems(dust, GTMaterials.Ruthenium, 1) // 钌粉
                .outputItems(dust, GTMaterials.AmmoniumChloride, 4) // 氯化铵
                .outputFluids(GTMaterials.HydrochloricAcid.getFluid(4000)) // 盐酸
                .blastFurnaceTemp(800)
                .EUt(GTValues.VA[GTValues.EV])
                .duration(260)
                .save(provider);
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("iridium_oxide_carbon_reduction"))
                .inputItems(dust, PlatinumLineMaterials.IridiumDioxide, 3) // 二氧化铱
                .inputItems(dust, GTMaterials.Carbon, 2) // 碳粉
                .outputItems(dust, GTMaterials.Iridium, 1) // 铱粉
                .outputFluids(GTMaterials.CarbonMonoxide.getFluid(2000)) // 一氧化碳
                .blastFurnaceTemp(1400)
                .EUt(GTValues.VA[GTValues.IV])
                .duration(600)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ferrous_sulfate_synthesis"))
                .circuitMeta(2)
                .inputItems(dust, GTMaterials.Iron, 2) // 铁粉
                .inputFluids(GTMaterials.SulfuricAcid.getFluid(3000)) // 硫酸
                .outputFluids(PlatinumLineMaterials.FerrousSulfate.getFluid(1000)) // 硫酸亚铁溶液
                .outputFluids(GTMaterials.Hydrogen.getFluid(4000)) // 氢气
                .EUt(GTValues.VA[GTValues.LV])
                .duration(200)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("ferric_sulfate_reduction"))
                .circuitMeta(1)
                .inputFluids(PlatinumLineMaterials.FerricSulfate.getFluid(2000)) // 硫酸铁溶液
                .inputItems(dust, GTMaterials.Iron, 1) // 铁粉
                .inputFluids(GTMaterials.SulfuricAcid.getFluid(3000)) // 硫酸
                .outputFluids(PlatinumLineMaterials.FerrousSulfate.getFluid(3000)) // 硫酸亚铁溶液
                .EUt(GTValues.VA[GTValues.LV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("anhydrous_ferric_sulfate_electrolysis"))
                .inputFluids(PlatinumLineMaterials.FerricSulfate.getFluid(1000)) // 硫酸铁溶液
                .outputItems(dust, GTMaterials.Iron, 2) // 铁粉
                .outputItems(dust, GTMaterials.Sulfur, 3) // 硫粉
                .outputFluids(GTMaterials.Oxygen.getFluid(6000)) // 氧气
                .EUt(GTValues.VA[GTValues.HV])
                .duration(800)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("acetaldehyde_hydrogenation"))
                .notConsumable(dust, GTMaterials.Platinum, 1)
                .inputFluids(PlatinumLineMaterials.Acetaldehyde.getFluid(1000)) // 乙醛
                .inputFluids(GTMaterials.Hydrogen.getFluid(2000)) // 氢气
                .outputFluids(GTMaterials.Ethanol.getFluid(1000)) // 乙醇
                .EUt(GTValues.VA[GTValues.MV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("nh4_cl"))// 氯化铵
                .circuitMeta(2)
                .inputFluids(Ammonia.getFluid(1000))
                .inputFluids(HydrochloricAcid.getFluid(1000))
                .outputItems(dust, AmmoniumChloride, 2)
                .EUt(GTValues.VA[GTValues.HV])
                .duration(100)
                .save(provider);
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("nh3_h2o"))// 一水合氨
                .inputFluids(Ammonia.getFluid(1000))
                .inputFluids(Water.getFluid(1000))
                .outputFluids(PlatinumLineMaterials.AmmoniaMonohydrate.getFluid(1000))
                .EUt(GTValues.VA[GTValues.LV])
                .duration(20)
                .save(provider);
        GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES.recipeBuilder(CTNHCore.id("nh4_cl_solidfication"))
                .inputFluids(AmmoniumChloride.getFluid(500))
                .outputItems(dust, AmmoniumChloride, 1)
                .EUt(GTValues.VA[GTValues.LV])
                .duration(20)
                .save(provider);
        GTRecipeTypes.EXTRACTOR_RECIPES.recipeBuilder(CTNHCore.id("nh4_cl_extractor"))
                .inputItems(dust, AmmoniumChloride, 1)
                .outputFluids(AmmoniumChloride.getFluid(500))
                .EUt(GTValues.VA[GTValues.LV])
                .duration(20)
                .save(provider);
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("decomposition_electrolyzing_cooperite"))
                .duration(648)
                .inputItems(ChemicalHelper.get(TagPrefix.dust, GTMaterials.Cooperite))
                .outputItems(
                        ChemicalHelper.get(TagPrefix.dust, PlatinumLineMaterials.PlatinumOre, 3),
                        ChemicalHelper.get(TagPrefix.dust, GTMaterials.Nickel),
                        ChemicalHelper.get(TagPrefix.dust, GTMaterials.Sulfur),
                        ChemicalHelper.get(TagPrefix.dust, PlatinumLineMaterials.PalladiumOre))
                .EUt(60).save(provider);
    }
}
