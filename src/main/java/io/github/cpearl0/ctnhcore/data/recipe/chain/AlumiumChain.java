package io.github.cpearl0.ctnhcore.data.recipe.chain;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static io.github.cpearl0.ctnhcore.data.materials.BauxiteProcessingMaterials.*;
import static io.github.cpearl0.ctnhcore.data.materials.GoldChainMaterials.SODIUM_HEXAFLUOROALUMINATE;
import static io.github.cpearl0.ctnhcore.registry.CTNHItems.BAUXITE_PROCESS_CATALYST;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials.Alumina;
import static io.github.cpearl0.ctnhcore.registry.material.CTNHMaterials.Cryolite;

public class AlumiumChain {

    public static void init(Consumer<FinishedRecipe> provider) {
        // 离心
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_green_sapphire"))
                .inputItems(dust, GreenSapphire, 5) // 绿色蓝宝石 Al2O3
                .outputItems(dust, Alumina, 5)
                .EUt(VA[HV])
                .duration(100)
                .save(provider);

        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_sapphire"))
                .inputItems(dust, Sapphire, 5) // 蓝宝石 Al2O3
                .outputItems(dust, Alumina, 5)
                .EUt(VA[HV])
                .duration(100)
                .save(provider);

        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_ruby"))
                .inputItems(dust, Ruby, 2) // 红宝石 CrAl2O3
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, Chromium, 1)
                .EUt(VA[HV])
                .duration(100)
                .save(provider);

        // 电解
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_pyrope"))
                .inputItems(dust, Pyrope, 10) // 镁铝榴石 Al2Mg3Si3O12
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputItems(dust, Magnesium, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .EUt(VA[MV])
                .duration(200)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_granite_red"))
                .inputItems(dust, GraniteRed, 2) // 红花岗岩 Al2(KAlSi3O8)O3
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, PotassiumFeldspar, 1)
                .EUt(VA[MV])
                .duration(60)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_potassium_feldspar"))
                .inputItems(dust, PotassiumFeldspar, 10) // 钾长石 KAlSi3O8
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 6)
                .outputItems(dust, Potassium, 2)
                .outputFluids(Oxygen.getFluid(1000))
                .EUt(VA[MV])
                .duration(200)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_pollucite"))
                .inputItems(dust, Pollucite, 10) // 铯榴石 Cs2Al2Si4(H2O)2O12
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 4)
                .outputItems(dust, Caesium, 2)
                .outputFluids(Water.getFluid(2000))
                .outputFluids(Oxygen.getFluid(1000))
                .EUt(VA[MV])
                .duration(280)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_kyanite"))
                .inputItems(dust, Kyanite, 2) // 蓝晶石 Al2SiO5
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 1)
                .EUt(VA[MV])
                .duration(80)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_spodumene"))
                .inputItems(dust, Spodumene, 8) // 锂辉石 LiAlSi2O6
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 4)
                .outputItems(dust, Lithium, 2)
                .outputFluids(Oxygen.getFluid(1000))
                .EUt(VA[MV])
                .duration(180)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_spessartine"))
                .inputItems(dust, Spessartine, 10) // 锰铝榴石 Al2Mn3Si3O12
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputItems(dust, Manganese, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .EUt(VA[MV])
                .duration(220)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_mica"))
                .inputItems(dust, Mica, 15) // 云母 KAl3Si3F2O10
                .outputItems(dust, Alumina, 3)
                .outputItems(dust, SiliconDioxide, 6)
                .outputItems(dust, Potassium, 2)
                .outputFluids(Fluorine.getFluid(4000))
                .EUt(VA[MV])
                .duration(380)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_lepidolite"))
                .inputItems(dust, Lepidolite, 12) // 锂云母 KLi3Al4F2O10
                .outputItems(dust, Alumina, 2)
                .outputItems(dust, Lithium, 3)
                .outputItems(dust, Potassium, 1)
                .outputFluids(Oxygen.getFluid(4000))
                .outputFluids(Fluorine.getFluid(2000))
                .EUt(VA[MV])
                .duration(160)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_grossular"))
                .inputItems(dust, Grossular, 10) // 钙铝榴石 Ca3Al2Si3O12
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputItems(dust, Calcium, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .EUt(VA[MV])
                .duration(220)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_glauconite_sand"))
                .inputItems(dust, GlauconiteSand, 14) // 海绿石砂 KMg2Al2Si3O12H2(H2O)
                .outputItems(dust, Alumina, 2)
                .outputItems(dust, Manganese, 2)
                .outputItems(dust, Potassium, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .outputFluids(Hydrogen.getFluid(2000))
                .outputFluids(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(220)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_emerald"))
                .inputItems(dust, Emerald, 13) // 绿宝石 Be3Al2Si6O18
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 6)
                .outputItems(dust, Beryllium, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .EUt(VA[MV])
                .duration(260)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_blue_topaz"))
                .inputItems(dust, BlueTopaz, 4) // 蓝黄玉 Al2SiO4F2
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 1)
                .outputFluids(Fluorine.getFluid(2000))
                .EUt(VA[MV])
                .duration(100)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_biotite"))
                .inputItems(dust, Biotite, 21) // 黑云母 KMg3Al3F2Si3O10
                .outputItems(dust, Magnesium, 6)
                .outputItems(dust, Alumina, 3)
                .outputItems(dust, SiliconDioxide, 3)
                .outputItems(dust, Potassium, 2)
                .outputFluids(Fluorine.getFluid(2000))
                .outputFluids(Oxygen.getFluid(5000))
                .EUt(VA[MV])
                .duration(440)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_alunite"))
                .inputItems(dust, Alunite, 16) // 明矾石 KAl2Si3H6O14
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, Potassium, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputFluids(Oxygen.getFluid(5000))
                .outputFluids(Hydrogen.getFluid(6000))
                .EUt(VA[MV])
                .duration(520)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_almandine"))
                .inputItems(dust, Almandine, 10) // 铁铝榴石 Al2Fe3Si3O12
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 3)
                .outputItems(dust, Iron, 3)
                .outputFluids(Oxygen.getFluid(3000))
                .EUt(VA[MV])
                .duration(200)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_clay"))
                .inputItems(dust, Clay, 18) // 粘土 Na2LiAl2Si2(H2O)6
                .outputItems(dust, Alumina, 1)
                .outputItems(dust, SiliconDioxide, 2)
                .outputItems(dust, Sodium, 2)
                .outputItems(dust, Lithium, 1)
                .outputFluids(Hydrogen.getFluid(12000))
                .EUt(VA[MV])
                .duration(180)
                .save(provider);

        // 高压电解
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_topaz"))
                .inputItems(dust, Topaz, 10) // 黄玉 Al2SiO5FH
                .outputItems(dust, Aluminium, 2)
                .outputItems(dust, Silicon)
                .outputFluids(Oxygen.getFluid(5000))
                .outputFluids(Hydrogen.getFluid(1000))
                .outputFluids(Fluorine.getFluid(1000))
                .EUt(VA[EV])
                .duration(200)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_sodalite"))
                .inputItems(dust, Sodalite, 11) // 方钠石 Al3Si3Na4Cl
                .outputItems(dust, Aluminium, 3)
                .outputItems(dust, Silicon, 3)
                .outputItems(dust, Sodium, 4)
                .outputFluids(Chlorine.getFluid(1000))
                .EUt(VA[EV])
                .duration(390)
                .save(provider);

        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_lazurite"))
                .inputItems(dust, Lazurite, 14) // 蓝金石 Al6Si6Ca8Na8
                .outputItems(dust, Aluminium, 3)
                .outputItems(dust, Silicon, 3)
                .outputItems(dust, Sodium, 4)
                .outputItems(dust, Calcium, 4)
                .EUt(VA[EV])
                .duration(460)
                .save(provider);

        // 电解氧化铝
        // LV
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_alumina_lv"))
                .inputItems(dust, Alumina, 10)
                .inputFluids(SODIUM_HEXAFLUOROALUMINATE.getFluid(1000))
                .outputItems(dust, Aluminium, 4)
                .outputItems(dust, SODIUM_FLUORIDE, 6)
                .outputItems(dust, ALUMINIUM_TRIFLUORIDE, 4)
                .outputFluids(Oxygen.getFluid(6000))
                .EUt(VA[LV])
                .duration(200)
                .save(provider);
        // HV
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_alumina_hv"))
                .circuitMeta(1)
                .inputItems(dust, Alumina, 10)
                .outputItems(dust, Aluminium, 4)
                .outputFluids(Oxygen.getFluid(6000))
                .EUt(VA[HV])
                .duration(400)
                .save(provider);

        // 六氟铝酸钠
        // 冰晶石
        GTRecipeTypes.EXTRACTOR_RECIPES.recipeBuilder(CTNHCore.id("sodium_hexafluoroaluminate_3"))
                .inputItems(dust, Cryolite, 10)
                .outputFluids(SODIUM_HEXAFLUOROALUMINATE.getFluid(1000))
                .EUt(VA[LV])
                .duration(100)
                .save(provider);
        // 合成
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("sodium_hexafluoroaluminate_comp"))
                .inputItems(dust, SodiumHydroxide, 18)
                .inputItems(dust, Alumina, 5)
                .inputFluids(HydrofluoricAcid.getFluid(12000))
                .outputFluids(SODIUM_HEXAFLUOROALUMINATE.getFluid(2000))
                .outputFluids(Water.getFluid(9000))
                .EUt(VA[MV])
                .duration(400)
                .save(provider);
        // 回收
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("sodium_hexafluoroaluminate_recy"))
                .inputItems(dust, SODIUM_FLUORIDE, 6)
                .inputItems(dust, ALUMINIUM_TRIFLUORIDE, 4)
                .outputFluids(SODIUM_HEXAFLUOROALUMINATE.getFluid(1000))
                .EUt(VA[MV])
                .duration(200)
                .save(provider);
        // 电解
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_sodium_hexafluoroaluminate"))
                .circuitMeta(1)
                .inputFluids(SODIUM_HEXAFLUOROALUMINATE.getFluid(1000))
                .outputItems(dust, SODIUM_FLUORIDE, 6)
                .outputItems(dust, ALUMINIUM_TRIFLUORIDE, 4)
                .EUt(VA[MV])
                .duration(200)
                .save(provider);

        // 铝土线
        // 碱浸
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder(CTNHCore.id("alkali_leach_bauxite"))
                .inputItems(dust, Bauxite, 13)
                .inputFluids(SODIUM_HYDROXIDE_SOLUTION.getFluid(8000))// 氢氧化钠溶液
                .outputFluids(SODIUM_HYDROXIDE_BAUXITE.getFluid(8000))// 氢氧化钠铝土混合物
                .EUt(VA[MV])
                .duration(80)
                .save(provider);
        // 碱溶液
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder(CTNHCore.id("sodium_hydroxide_solution"))
                .inputItems(dust, SodiumHydroxide, 3)
                .inputFluids(Water.getFluid(1000))
                .outputFluids(SODIUM_HYDROXIDE_SOLUTION.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .save(provider);
        // 加热沉淀
        GTRecipeTypes.FLUID_HEATER_RECIPES.recipeBuilder(CTNHCore.id("impure_aluminum_hydroxide_solution"))
                .inputFluids(SODIUM_HYDROXIDE_BAUXITE.getFluid(1000))// 氢氧化钠铝土混合物
                .outputFluids(IMPURE_SODIUM_ALUMINATE_SOLUTION.getFluid(1000))// 含杂偏铝酸钠溶液
                .EUt(VA[LV])
                .duration(30)
                .save(provider);
        // 水解氯化铝
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("alcl3_to_aloh3"))
                .inputItems(dust, ALUMINIUM_CHLORIDE, 4)
                .inputFluids(Water.getFluid(1500))
                .outputItems(dust, ALUMINIUM_HYDROXIDE, 7)
                .outputFluids(HydrochloricAcid.getFluid(3000))
                .blastFurnaceTemp(900)
                .EUt(VA[LV] * 3L)
                .duration(40)
                .save(provider);
        // 分离赤泥
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("red_mud"))
                .inputFluids(IMPURE_SODIUM_ALUMINATE_SOLUTION.getFluid(4000))// 含杂偏铝酸钠溶液
                .outputFluids(PURE_SODIUM_ALUMINATE_SOLUTION.getFluid(4000))// 纯净偏铝酸钠溶液
                .outputFluids(RED_MUD.getFluid(1000))
                .EUt(VA[MV])
                .duration(30)
                .save(provider);
        // 分离氢氧化铝
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("aluminium_hydroxide_dust"))
                .inputFluids(PURE_SODIUM_ALUMINATE_SOLUTION.getFluid(1000))// 纯净偏铝酸钠溶液
                .outputItems(dust, ALUMINIUM_HYDROXIDE, 7)// 氢氧化铝
                .outputFluids(SODIUM_HYDROXIDE_SOLUTION.getFluid(1000))
                .EUt(VA[MV])
                .duration(200)
                .save(provider);

        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("aluminium_hydroxide_dust_much"))
                .notConsumable(dust, ALUMINIUM_HYDROXIDE)
                .inputFluids(PURE_SODIUM_ALUMINATE_SOLUTION.getFluid(8000))
                .outputItems(dust, ALUMINIUM_HYDROXIDE, 56)
                .outputFluids(SODIUM_HYDROXIDE_SOLUTION.getFluid(8000))
                .EUt(VA[MV] * 2L)
                .duration(200)
                .save(provider);
        // 氢氧化铝脱水
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder(CTNHCore.id("alumina"))
                .inputItems(dust, ALUMINIUM_HYDROXIDE, 14)
                .outputItems(dust, Alumina, 5)
                .outputFluids(Water.getFluid(3000))
                .blastFurnaceTemp(1100)
                .EUt(VA[MV])
                .duration(200)
                .save(provider);
        // 中和赤泥
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder(CTNHCore.id("neutralised_red_mud"))
                .inputFluids(HydrochloricAcid.getFluid(3000))
                .inputFluids(RED_MUD.getFluid(1000))
                .outputFluids(NEUTRALISED_RED_MUD.getFluid(1000))// 中和赤泥
                .EUt(VA[MV])
                .duration(100)
                .save(provider);
        // 分离赤泥浆液
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("red_slurry"))
                .inputFluids(NEUTRALISED_RED_MUD.getFluid(1000))// 中和赤泥
                .outputFluids(RED_SLURRY.getFluid(1000))// 赤泥浆液
                .outputFluids(FERRIC_REE_CHLORIDE.getFluid(1000))// 含稀土氯化铁溶液
                .EUt(VA[MV])
                .duration(240)
                .save(provider);
        // 分离含氯稀土
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("rare_earth_chloride_solution_1"))
                .inputFluids(FERRIC_REE_CHLORIDE.getFluid(2000))
                .outputFluids(RARE_EARTH_CHLORIDE_SOLUTION.getFluid(1000))
                .outputFluids(Iron3Chloride.getFluid(1000))
                .EUt(VA[HV])
                .duration(320)
                .save(provider);
        // 硫酸钛酯
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("titanyl_sulfate"))
                .inputFluids(SulfuricAcid.getFluid(1000))
                .inputFluids(RED_SLURRY.getFluid(1000))
                .outputFluids(TITANYL_SULFATE.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .save(provider);
        // 四氯化钛
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("titanium_tetrachloride"))
                .inputFluids(HydrochloricAcid.getFluid(8000))
                .inputFluids(TITANYL_SULFATE.getFluid(2000))
                .outputFluids(TitaniumTetrachloride.getFluid(2000))
                .outputFluids(DilutedSulfuricAcid.getFluid(3000))
                .outputFluids(Water.getFluid(3000))
                .EUt(VA[HV] * 2L)
                .duration(100)
                .save(provider);

        // 电解氟化钠
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder(CTNHCore.id("electrolyzing_sodium_fluoride"))
                .inputItems(dust, SODIUM_FLUORIDE, 2)
                .outputItems(dust, Sodium, 1)
                .outputFluids(Fluorine.getFluid(1000))
                .EUt(VA[MV])
                .duration(160)
                .save(provider);
        // 水解氟化铝
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("hydrolyzing_aluminium_trifluoride"))
                .inputFluids(Water.getFluid(6000))
                .inputItems(dust, ALUMINIUM_TRIFLUORIDE, 8)
                .outputItems(dust, ALUMINIUM_HYDROXIDE, 14)
                .outputFluids(HydrofluoricAcid.getFluid(6000))
                .EUt(VA[MV])
                .duration(160)
                .save(provider);
        // 合成氟化钠
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("sodium_fluoride"))
                .inputFluids(Fluorine.getFluid(1000))
                .inputItems(dust, Sodium)
                .outputItems(dust, SODIUM_FLUORIDE, 2)
                .EUt(VA[MV])
                .duration(160)
                .save(provider);
        // 离心铝土矿
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("bauxite_dust"))
                .inputItems(dust, Bauxite)
                .outputItems(dust, Alumina)
                .chancedOutput(dust, Gallium, 2500, 0)
                .chancedOutput(dust, Rutile, 3000, 0)
                .EUt(VA[MV])
                .duration(240)
                .save(provider);
        // 催化剂处理铝土
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("catalyst_bauxite"))
                .inputItems(dust, Bauxite, 39)
                .notConsumable(BAUXITE_PROCESS_CATALYST)
                .inputFluids(HydrochloricAcid.getFluid(24000))
                .outputFluids(TITANIUM_TETRACHLORIDE_V.getFluid(3000))
                .outputFluids(FERRIC_REE_CHLORIDE.getFluid(1000))
                .outputFluids(Water.getFluid(12000))
                .outputItems(dust, Aluminium, 24)
                .EUt(VA[HV] * 2L)
                .duration(160)
                .save(provider);
    }
}
