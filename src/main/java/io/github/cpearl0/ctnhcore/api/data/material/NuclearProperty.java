package io.github.cpearl0.ctnhcore.api.data.material;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import dev.arbor.gtnn.data.GTNNMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.CTNHTagPrefixes;
import io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterialsInfo;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.Plutonium241;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Uranium235;
import static dev.arbor.gtnn.data.GTNNMaterials.Thorium232;

public class NuclearProperty implements IMaterialProperty {
    @Getter
    @Setter
    private Material oxideMaterial;
    @Getter
    @Setter
    private Material carbideMaterial;
    @Getter
    @Setter
    private Material NitrideMaterial;
    @Getter
    @Setter
    private Material zirconiumAlloyMaterial;
    @Getter
    @Setter
    private Material basicMaterial;
    @Getter
    @Setter
    private int heat;
    @Getter
    @Setter
    private boolean fissile;
    @Getter
    @Setter
    private boolean fertile;
    public Map<Material, Integer> fertileDecay = new HashMap<>();
    @Override
    public void verifyProperty(MaterialProperties materialProperties) {

    }
    public ItemStack getDepletedFuel(Material material) {
        return GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.DepletedFuel, material).asStack();
    }
    public ItemStack getFuel(Material material) {
        return GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, material).asStack();
    }
    public void generateRecipes(Consumer<FinishedRecipe> provider, Material thisMaterial) {
        String name = thisMaterial.getName();
        // 8F + C? + C?? + Fuel TRISO = Depleted Fuel + SiF4 + CF4
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("depleted_fuel_triso_" + name)
                .inputItems(getDepletedFuel(getCarbideMaterial()))
                .inputFluids(GTMaterials.Fluorine.getFluid(1000))
                .outputItems(getDepletedFuel(thisMaterial))
                .outputFluids(CTNHMaterials.siliconFluoride.getFluid(1000))
                .outputFluids(CTNHMaterials.carbonFluoride.getFluid(1000))
                .EUt(30)
                .duration(300)
                .save(provider);

        // FuelZr + 4Cl = ZrCl4
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder("depleted_zr_alloy_" + name)
                .inputItems(getDepletedFuel(getZirconiumAlloyMaterial()))
                .inputFluids(GTMaterials.Chlorine.getFluid(4000))
                .outputItems(getDepletedFuel(thisMaterial))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust ,CTNHMaterials.zirconiumTetrachloride).asStack(5))
                .EUt(30)
                .duration(300)
                .save(provider);

        // Fuel + O = [Fuel + O]
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("depleted_fuel_oxide_" + name)
                .inputItems(getDepletedFuel(thisMaterial))
                .inputFluids(GTMaterials.Oxygen.getFluid(1000))
                .outputItems(getDepletedFuel(getOxideMaterial()))
                .EUt(30)
                .duration(300)
                .save(provider);

        // HNO3 + [Fuel + O] + O = [Fuel + NO3 + H2O] & [Fuel + NO3 + H2O] + N2H4 + RP1 + C12H27O4P = Fuel2N3 + Red Oil [N2H4 + RP1 + C12H27O4P]
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder("depleted_fuel_nitrate_" + name)
                .notConsumable(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.Boron).asStack())
                .inputItems(getDepletedFuel(getOxideMaterial()))
                .inputFluids(GTMaterials.NitricAcid.getFluid(1000))
                .notConsumableFluid(GTNNMaterials.Hydrazine.getFluid(1000))
                .notConsumableFluid(GTNNMaterials.RP1.getFluid(1000))
                //.notConsumable(Fluid.of("")TributylPhosphate.getFluid(1000))
                .notConsumable(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.FerriteMixture).asStack())
                .outputItems(getDepletedFuel(getNitrideMaterial()))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, getBasicMaterial()).asStack())
                .EUt(30)
                .duration(2000)
                .save(provider);

        // Fuel2N3 = Waste + 3N
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder("waste_" + name)
                .inputItems(getDepletedFuel(getNitrideMaterial()))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, getBasicMaterial()).asStack())
                .outputFluids(GTMaterials.Nitrogen.getFluid(3000))
                .EUt(30)
                .duration(1000)
                .save(provider);

        // Fuel + Zr = [Fuel + Zr]
        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("zirconium_alloy_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, thisMaterial).asStack())
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.Zirconium).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getZirconiumAlloyMaterial()).asStack())
                .EUt(30).duration(300)
                .save(provider);

        // [Fuel + Zr] + 4Cl = Fuel + ZrCl4
        if (thisMaterial.equals(GTMaterials.Uranium238)
                || thisMaterial.equals(GTMaterials.Plutonium239)
                || thisMaterial.equals(Uranium235)
                || thisMaterial.equals(Plutonium241)
                || thisMaterial.equals(Thorium232)) {
            GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(name)
                    .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getZirconiumAlloyMaterial()).asStack())
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, thisMaterial).asStack())
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, CTNHMaterials.zirconiumTetrachloride).asStack(5))
                    .inputFluids(GTMaterials.Chlorine.getFluid(3000))
                    .EUt(30).duration(300)
                    .save(provider);
        }
        else {
            GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(name)
                    .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getZirconiumAlloyMaterial()).asStack())
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, thisMaterial).asStack())
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, CTNHMaterials.zirconiumTetrachloride).asStack(5))
                    .inputFluids(GTMaterials.Chlorine.getFluid(3000))
                    .EUt(30).duration(300)
                    .save(provider);
        }

        // Fuel + O = [Fuel + O]
        if(thisMaterial.equals(GTMaterials.Uranium238)
                || thisMaterial.equals(GTMaterials.Plutonium239)
                || thisMaterial.equals(Uranium235)
                || thisMaterial.equals(Plutonium241)
                || thisMaterial.equals(Thorium232)){
            GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("oxide_" + name)
                    .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, thisMaterial).asStack())
                    .inputFluids(GTMaterials.Oxygen.getFluid(1000))
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                    .EUt(30).duration(300)
                    .save(provider);
        }
        else{
            GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("oxide_" + name)
                    .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, thisMaterial).asStack())
                    .inputFluids(GTMaterials.Oxygen.getFluid(1000))
                    .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                    .EUt(30).duration(300)
                    .save(provider);
        }

        // [Fuel + O] + 2C = [Fuel + C] + CO
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("carbide_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.Carbon).asStack(2))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getCarbideMaterial()).asStack())
                .outputFluids(GTMaterials.CarbonMonoxide.getFluid(1000))
                .EUt(30).duration(300)
                .save(provider);

        // [Fuel + C] + 4O = CO2 + [Fuel + 2O]
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder("oxide_" + name + "_from_carbon")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getCarbideMaterial()).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                .inputFluids(GTMaterials.Oxygen.getFluid(4000))
                .outputFluids(GTMaterials.CarbonDioxide.getFluid(1000))
                .blastFurnaceTemp(1000).EUt(120).duration(2000)
                        .save(provider);

        // [Fuel + O] + [Fuel + C] + 3N = Fuel2N3 + CO
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("nitride_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getCarbideMaterial()).asStack())
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                .inputFluids(GTMaterials.Nitrogen.getFluid(3000))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getNitrideMaterial()).asStack())
                .outputFluids(GTMaterials.CarbonMonoxide.getFluid(1000))
                .EUt(30).duration(300)
                .save(provider);

        // Fuel2N3 + 4H2O + 3O = 2[Fuel + 2O] + H2O + NO2 + 2NH3
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder("oxide_" + name + "_from_nitride")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getNitrideMaterial()).asStack())
                .inputFluids(GTMaterials.Water.getFluid(3000))
                .inputFluids(GTMaterials.Oxygen.getFluid(3000))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                .outputFluids(GTMaterials.Ammonia.getFluid(2000))
                .outputFluids(GTMaterials.Water.getFluid(1000))
                .outputFluids(GTMaterials.NitrogenDioxide.getFluid(1000))
                .EUt(30).duration(300)
                .save(provider);

        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("fuel_carbide_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getCarbideMaterial()).asStack())
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.Graphite).asStack())
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, CTNHMaterials.siliconCarbide).asStack())
                .inputItems(GTItems.CARBON_FIBER_PLATE.asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, getCarbideMaterial()).asStack())
                .notConsumable(GTItems.SHAPE_MOLD_BALL.asStack())
                .EUt(30).duration(200)
                .save(provider);

        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("fuel_zirconium_alloy_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getZirconiumAlloyMaterial()).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, getZirconiumAlloyMaterial()).asStack())
                .notConsumable(GTItems.SHAPE_MOLD_BALL.asStack())
                .EUt(30).duration(200)
                .save(provider);

        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("fuel_nitride_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getNitrideMaterial()).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, getNitrideMaterial()).asStack())
                .notConsumable(GTItems.SHAPE_MOLD_BALL.asStack())
                .EUt(30).duration(200)
                .save(provider);

        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("fuel_oxide_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, getOxideMaterial()).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, getOxideMaterial()).asStack())
                .notConsumable(GTItems.SHAPE_MOLD_BALL.asStack())
                .EUt(30).duration(200)
                .save(provider);

        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("fuel_" + name)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, thisMaterial).asStack())
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, thisMaterial).asStack())
                .notConsumable(GTItems.SHAPE_MOLD_BALL.asStack())
                .EUt(30).duration(200)
                .save(provider);
        if(isFissile()) {
            List<Integer> counts = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
            counts.forEach(count -> {
                CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fuel_triso_" + name + count)
                        .duration(20000)
                        .EUt((long) ((getHeat() + count) * count * 2 * 0.8))
                        .circuitMeta(count)
                        .inputItems(getFuel(getCarbideMaterial()).copyWithCount(count))
                        .outputItems(getDepletedFuel(getCarbideMaterial()).copyWithCount(count))
                        .addData("heat", (getHeat() + count) * count * 2)
                        .save(provider);

                CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fuel_oxide_" + name + count)
                        .duration(8000)
                        .EUt((long) ((getHeat() + count) * count * 2 * 1.05))
                        .circuitMeta(count)
                        .inputItems(getFuel(getOxideMaterial()).copyWithCount(count))
                        .outputItems(getDepletedFuel(getOxideMaterial()).copyWithCount(count))
                        .addData("heat", (getHeat() + count) * count * 2)
                        .save(provider);

                CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fuel_nitride_" + name + count)
                        .duration(10000)
                        .EUt((long) ((getHeat() + count) * count * 2 * 1.1))
                        .circuitMeta(count)
                        .inputItems(getFuel(getNitrideMaterial()).copyWithCount(count))
                        .outputItems(getDepletedFuel(getNitrideMaterial()).copyWithCount(count))
                        .addData("heat", (getHeat() + count) * count * 2)
                        .save(provider);

                CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fuel_zirconium_alloy_" + name + count)
                        .duration(15000)
                        .EUt((long) ((getHeat() + count) * count * 2 * 1.2))
                        .circuitMeta(count)
                        .inputItems(getFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                        .outputItems(getDepletedFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                        .addData("heat", (getHeat() + count) * count * 2)
                        .save(provider);
                NuclearMaterialsInfo.fertileMaterial.forEach(material -> {
                    CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_triso_" + name + count + "_" + material.getName())
                            .duration(40000)
                            .EUt((long) ((getHeat() + count) * count / 2 * 0.8))
                            .circuitMeta(count)
                            .inputItems(getFuel(getCarbideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getCarbideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count)
                            .save(provider);

                    CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_oxide_" + name + count + "_" + material.getName())
                            .duration(16000)
                            .EUt((long) ((getHeat() + count) * count / 2 * 1.05))
                            .circuitMeta(count)
                            .inputItems(getFuel(getOxideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getOxideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count)
                            .save(provider);

                    CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_nitride_" + name + count + "_" + material.getName())
                            .duration(20000)
                            .EUt((long) ((getHeat() + count) * count / 2 * 1.1))
                            .circuitMeta(count)
                            .inputItems(getFuel(getNitrideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getNitrideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count)
                            .save(provider);

                    CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_zirconium_alloy_" + name + count + "_" + material.getName())
                            .duration(30000)
                            .EUt((long) ((getHeat() + count) * count / 2 * 1.2))
                            .circuitMeta(count)
                            .inputItems(getFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count)
                            .save(provider);

                    var builder1 = CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_triso_" + name + count + "_" + material.getName() + "_decay")
                            .duration(10000)
                            .EUt((long) ((getHeat() + count) * count * 0.8))
                            .circuitMeta(count + 10)
                            .inputItems(getFuel(getCarbideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getCarbideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count / 5);

                    var builder2 = CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_oxide_" + name + count + "_" + material.getName() + "_decay")
                            .duration(4000)
                            .EUt((long) ((getHeat() + count) * count * 1.05))
                            .circuitMeta(count + 10)
                            .inputItems(getFuel(getOxideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getOxideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count / 5);

                    var builder3 = CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_nitride_" + name + count + "_" + material.getName() + "_decay")
                            .duration(5000)
                            .EUt((long) ((getHeat() + count) * count * 1.1))
                            .circuitMeta(count + 10)
                            .inputItems(getFuel(getNitrideMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getNitrideMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count / 5);

                    var builder4 = CTNHRecipeTypes.NUCLEAR_REACTOR_RECIPES.recipeBuilder("fertile_fuel_zirconium_alloy_" + name + count + "_" + material.getName() + "_decay")
                            .duration(7500)
                            .EUt((long) ((getHeat() + count) * count * 1.2))
                            .circuitMeta(count + 10)
                            .inputItems(getFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                            .inputItems(getFuel(material).copyWithCount(9))
                            .outputItems(getDepletedFuel(getZirconiumAlloyMaterial()).copyWithCount(count))
                            .addData("heat", (getHeat() + count) * count / 5);

                    material.getProperty(CTNHPropertyKeys.NUCLEAR).fertileDecay.forEach((material1, amount) -> {
                            builder1.chancedOutput(getDepletedFuel(material1).copyWithCount(9), amount, 100).save(provider);
                            builder2.chancedOutput(getDepletedFuel(material1).copyWithCount(9), amount, 100).save(provider);
                            builder3.chancedOutput(getDepletedFuel(material1).copyWithCount(9), amount, 100).save(provider);
                            builder4.chancedOutput(getDepletedFuel(material1).copyWithCount(9), amount, 100).save(provider);
                    });
                });
            });
        }
    }
}
