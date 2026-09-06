package io.github.cpearl0.ctnhcore.data.recipe.chain;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.data.materials.BauxiteProcessingMaterials;
import io.github.cpearl0.ctnhcore.data.materials.BiodieselFertileSoilMaterials;
import io.github.cpearl0.ctnhcore.data.materials.NaquadahMaterials;
import io.github.cpearl0.ctnhcore.data.materials.StonePowderMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import net.minecraft.data.recipes.FinishedRecipe;

import com.magicbee.ctnhmana.registry.CMRecipeTypes;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHItems.STONE_PROCESS_CATALYST;

/** Converted from server_scripts/src/gtceu/chain/StonedustChain.js */
public class StonedustChain {

    public static void init(Consumer<FinishedRecipe> provider) {
        // 从 StonedustChain.js 迁移

        // 催化剂配方
        CTNHRecipeTypes.DIFFERENTIAL_CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("stone_dust_with_catalyst"))
                .inputItems(dust, Stone, 60)
                .notConsumable(STONE_PROCESS_CATALYST.asStack())
                .outputItems(ChemicalHelper.get(dustTiny, StonePowderMaterials.INERT_RESIDUES))
                .outputItems(ChemicalHelper.get(dustSmall, StonePowderMaterials.OXIDIZED_RESIDUES, 2))
                .outputItems(ChemicalHelper.get(dustSmall, StonePowderMaterials.HEAVY_OXIDIZED_RESIDUES, 2))
                .outputItems(ChemicalHelper.get(dustSmall, Magnetite))
                .inputFluids(HydrofluoricAcid.getFluid(12000))
                .outputFluids(BauxiteProcessingMaterials.RED_MUD.getFluid(75))
                .outputFluids(StonePowderMaterials.FLUOROSILICIC_ACID.getFluid(2000))
                .EUt(480).duration(200)
                .save(provider);

        // 产线配方
        // 搅拌：石头粉 + 氢氟酸 -> 污浊六氟硅酸
        MIXER_RECIPES.recipeBuilder(CTNHCore.id("dirty_hexafluorosilicic_acid"))
                .inputItems(dust, Stone, 24)
                .inputFluids(HydrofluoricAcid.getFluid(6000))
                .outputFluids(StonePowderMaterials.DIRTY_HEXAFLUOROSILICIC_ACID.getFluid(3000))
                .EUt(100).duration(40)
                .save(provider);

        // 离心：污浊六氟硅酸 -> 稀释六氟硅酸 + 石头残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("dilute_hexafluorosilicic_acid"))
                .inputFluids(StonePowderMaterials.DIRTY_HEXAFLUOROSILICIC_ACID.getFluid(3000))
                .outputFluids(StonePowderMaterials.DILUTE_HEXAFLUOROSILICIC_ACID.getFluid(3000))
                .outputItems(dust, StonePowderMaterials.STONE_RESIDUE, 12)
                .duration(40).EUt(100)
                .save(provider);

        // 蒸馏：稀释六氟硅酸 -> 水 + 氟硅酸
        DISTILLATION_RECIPES.recipeBuilder(CTNHCore.id("fluorosilicic_acid"))
                .inputFluids(StonePowderMaterials.DILUTE_HEXAFLUOROSILICIC_ACID.getFluid(3000))
                .outputFluids(Water.getFluid(2000))
                .outputFluids(StonePowderMaterials.FLUOROSILICIC_ACID.getFluid(1000))
                .duration(160).EUt(200)
                .save(provider);

        // 化学反应：石头残渣 -> 精良残渣 + 磁铁矿
        CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("uncommon_residues_dust"))
                .inputItems(dust, StonePowderMaterials.STONE_RESIDUE, 24)
                .outputItems(dust, StonePowderMaterials.UNCOMMON_RESIDUES)
                .outputItems(ChemicalHelper.get(dustSmall, Magnetite))
                .inputFluids(BauxiteProcessingMaterials.IMPURE_SODIUM_ALUMINATE_SOLUTION.getFluid(1000))
                .outputFluids(BauxiteProcessingMaterials.IMPURE_SODIUM_ALUMINATE_SOLUTION.getFluid(925))
                .outputFluids(BauxiteProcessingMaterials.RED_MUD.getFluid(75))
                .duration(40).EUt(100)
                .save(provider);

        // 冷冻机：氟 -> 液态氟
        VACUUM_RECIPES.recipeBuilder(CTNHCore.id("liquid_fluorine"))
                .inputFluids(Fluorine.getFluid(1000))
                .outputFluids(BiodieselFertileSoilMaterials.LIQUID_FLUORINE.getFluid(1000))
                .EUt(1920).duration(240)
                .save(provider);

        // 化学反应：液态氧 + 液态氟 -> 二氟化二氧
        CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("dioxygendifluoride"))
                .inputFluids(Oxygen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .inputFluids(BiodieselFertileSoilMaterials.LIQUID_FLUORINE.getFluid(2000))
                .outputFluids(StonePowderMaterials.DIOXYGENDIFLUORIDE.getFluid(1000))
                .duration(80).EUt(200)
                .save(provider);

        // 化学反应：精良残渣 -> 待分离氧化金属残渣
        CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("partially_oxidized_residues_dust"))
                .inputItems(dust, StonePowderMaterials.UNCOMMON_RESIDUES)
                .outputItems(dust, StonePowderMaterials.PARTIALLY_OXIDIZED_RESIDUES)
                .inputFluids(StonePowderMaterials.DIOXYGENDIFLUORIDE.getFluid(1000))
                .duration(80).EUt(100)
                .save(provider);

        // 离心：待分离氧化金属残渣 -> 纯净残渣 + 氧化残渣溶液
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("oxidized_residual_solution"))
                .inputItems(dust, StonePowderMaterials.PARTIALLY_OXIDIZED_RESIDUES, 10)
                .outputItems(dust, StonePowderMaterials.INERT_RESIDUES)
                .inputFluids(DistilledWater.getFluid(10000))
                .outputFluids(StonePowderMaterials.OXIDIZED_RESIDUAL_SOLUTION.getFluid(10000))
                .duration(200).EUt(100)
                .save(provider);

        // 脱水机：氧化残渣溶液 -> 氧化残渣 + 重氧化残渣
        CTNHRecipeTypes.DEHYDRATOR_RECIPES.recipeBuilder(CTNHCore.id("oxidized_residues_dust"))
                .outputItems(dust, StonePowderMaterials.OXIDIZED_RESIDUES)
                .outputItems(dust, StonePowderMaterials.HEAVY_OXIDIZED_RESIDUES)
                .inputFluids(StonePowderMaterials.OXIDIZED_RESIDUAL_SOLUTION.getFluid(2000))
                .duration(80).EUt(3000)
                .save(provider);

        // 电弧炉：氧化残渣 -> 金属残渣
        BLAST_RECIPES.recipeBuilder(CTNHCore.id("metallic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.OXIDIZED_RESIDUES, 10)
                .outputItems(dust, StonePowderMaterials.METALLIC_RESIDUES)
                .inputFluids(Hydrogen.getFluid(60000))
                .outputFluids(StonePowderMaterials.DILUTE_HYDROFLUORIC_ACID.getFluid(40000))
                .duration(1600).EUt(2000).blastFurnaceTemp(3500)
                .save(provider);

        // 电弧炉：重氧化残渣 -> 重金属残渣
        BLAST_RECIPES.recipeBuilder(CTNHCore.id("heavy_metallic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.HEAVY_OXIDIZED_RESIDUES, 10)
                .outputItems(dust, StonePowderMaterials.HEAVY_METALLIC_RESIDUES)
                .inputFluids(Hydrogen.getFluid(60000))
                .outputFluids(StonePowderMaterials.DILUTE_HYDROFLUORIC_ACID.getFluid(40000))
                .duration(1600).EUt(2000).blastFurnaceTemp(3500)
                .save(provider);

        // 蒸馏：稀氢氟酸 -> 水 + 氢氟酸
        DISTILLATION_RECIPES.recipeBuilder(CTNHCore.id("hydrofluoric_acid"))
                .inputFluids(StonePowderMaterials.DILUTE_HYDROFLUORIC_ACID.getFluid(2000))
                .outputFluids(Water.getFluid(1000))
                .outputFluids(HydrofluoricAcid.getFluid(1000))
                .duration(80).EUt(200)
                .save(provider);
        // 魔力转化器：金属残渣分离
        CMRecipeTypes.MANA_TRANSFORMER_RECIPES.recipeBuilder(CTNHCore.id("metallic_residues_dust_seperate"))
                .inputItems(dust, StonePowderMaterials.METALLIC_RESIDUES, 10)
                .outputItems(dust, StonePowderMaterials.DIAMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.PARAMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.FERROMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.UNCOMMON_RESIDUES)
                .duration(80).EUt(8000)
                .save(provider);

        // 魔力转化器：重金属残渣分离
        CMRecipeTypes.MANA_TRANSFORMER_RECIPES.recipeBuilder(CTNHCore.id("heavy_metallic_residues_dust_seperate"))
                .inputItems(dust, StonePowderMaterials.HEAVY_METALLIC_RESIDUES, 10)
                .outputItems(dust, StonePowderMaterials.HEAVY_DIAMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.HEAVY_PARAMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.HEAVY_FERROMAGNETIC_RESIDUES, 3)
                .outputItems(dust, StonePowderMaterials.EXOTIC_HEAVY_RESIDUES)
                .duration(80).EUt(8000)
                .save(provider);

        // 离心：铁磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("ferromagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.FERROMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Iron))
                .outputItems(ChemicalHelper.get(dustSmall, Nickel))
                .outputItems(ChemicalHelper.get(dustSmall, Cobalt))
                .duration(100).EUt(3000)
                .save(provider);

        // 离心：抗磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("diamagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.DIAMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Calcium))
                .outputItems(ChemicalHelper.get(dustSmall, Zinc))
                .outputItems(ChemicalHelper.get(dustSmall, Copper))
                .outputItems(ChemicalHelper.get(dustSmall, Gallium))
                .outputItems(ChemicalHelper.get(dustSmall, Beryllium))
                .outputItems(ChemicalHelper.get(dustSmall, Tin))
                .duration(100).EUt(3000)
                .save(provider);

        // 离心：顺磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("paramagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.PARAMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Sodium))
                .outputItems(ChemicalHelper.get(dustSmall, Potassium))
                .outputItems(ChemicalHelper.get(dustSmall, Magnesium))
                .outputItems(ChemicalHelper.get(dustSmall, Titanium))
                .outputItems(ChemicalHelper.get(dustSmall, Vanadium))
                .outputItems(ChemicalHelper.get(dustSmall, Manganese))
                .duration(100).EUt(3000)
                .save(provider);

        // 离心：重顺磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("heavy_paramagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.HEAVY_PARAMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Thorium))
                .outputItems(ChemicalHelper.get(dustSmall, Uranium238))
                .outputItems(ChemicalHelper.get(dustSmall, Tungsten))
                .outputItems(ChemicalHelper.get(dustSmall, Hafnium))
                .outputItems(ChemicalHelper.get(dustSmall, Tantalum))
                .outputItems(ChemicalHelper.get(dustSmall, Thallium))
                .duration(100).EUt(3000)
                .save(provider);

        // 离心：重抗磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("heavy_diamagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.HEAVY_DIAMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Lead))
                .outputItems(ChemicalHelper.get(dustSmall, Cadmium))
                .outputItems(ChemicalHelper.get(dustSmall, Indium))
                .outputItems(ChemicalHelper.get(dustSmall, Gold))
                .outputItems(ChemicalHelper.get(dustSmall, Bismuth))
                .outputFluids(Mercury.getFluid(36))
                .duration(120).EUt(3000)
                .save(provider);

        // 离心：重铁磁性残渣
        CENTRIFUGE_RECIPES.recipeBuilder(CTNHCore.id("heavy_ferromagnetic_residues_dust"))
                .inputItems(dust, StonePowderMaterials.HEAVY_FERROMAGNETIC_RESIDUES, 6)
                .outputItems(ChemicalHelper.get(dustSmall, Dysprosium))
                .duration(120).EUt(3000)
                .save(provider);

        // 大型化学反应：清洗纯净残渣
        LARGE_CHEMICAL_RECIPES.recipeBuilder(CTNHCore.id("clean_inert_residues"))
                .inputItems(dust, StonePowderMaterials.INERT_RESIDUES, 10)
                .notConsumableFluid(FluoroantimonicAcid.getFluid(1000))
                .outputItems(dust, StonePowderMaterials.CLEAN_INERT_RESIDUES, 10)
                .outputItems(dust, NaquadahMaterials.NaquadahOxideMixture)
                .duration(320).EUt(200)
                .save(provider);
    }
}
