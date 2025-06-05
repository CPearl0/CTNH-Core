package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import io.github.cpearl0.ctnhcore.registry.*;
import io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials;
import io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterialsInfo;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Mendelevium;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.CIRCUIT;
import static dev.arbor.gtnn.data.GTNNMaterials.Thorium232;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.*;

public class NuclearRecipes {
    public static void init(Consumer<FinishedRecipe> provider){
        Thorium233.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, NuclearMaterials.Thorium233);
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Thorium232);
        Protactinium233.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Protactinium233);
        Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Uranium234);
        Uranium235.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Uranium235);
        Uranium239.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Uranium239);
        Uranium233.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Uranium233);
        Uranium238.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Uranium238);
        Neptunium235.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Neptunium235);
        Neptunium237.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Neptunium237);
        Neptunium239.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Neptunium239);
        Plutonium239.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Plutonium239);
        Plutonium240.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Plutonium240);
        Plutonium241.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Plutonium241);
        Plutonium244.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Plutonium244);
        Plutonium245.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Plutonium245);
        Americium241.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Americium241);
        Americium243.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Americium243);
        Americium245.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Americium245);
        Curium245.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Curium245);
        Curium247.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Curium247);
        Curium246.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Curium246);
        Curium250.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Curium250);
        Curium251.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Curium251);
        Berkelium247.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Berkelium247);
        Berkelium249.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Berkelium249);
        Berkelium251.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Berkelium251);
        Californium251.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Californium251);
        Californium253.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Californium253);
        Californium252.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Californium252);
        Californium256.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Californium256);
        Californium257.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Californium257);
        Einsteinium253.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Einsteinium253);
        Einsteinium255.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Einsteinium255);
        Einsteinium257.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Einsteinium257);
        Fermium257.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Fermium257);
        Fermium258.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Fermium258);
        Fermium259.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Fermium259);
        Fermium262.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Fermium262);
        Fermium263.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Fermium263);
        Mendelevium259.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Mendelevium259);
        Mendelevium261.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Mendelevium261);
        Mendelevium263.getProperty(CTNHPropertyKeys.NUCLEAR).generateRecipes(provider, Mendelevium263);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("protactinium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Protactinium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 560, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Protactinium233).asStack(), 1000, 30)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Thorium).asStack(3), 8000, 200)
                .duration(300).EUt(30)
                        .save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("thorium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Thorium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 560, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Protactinium233).asStack(), 1000, 30)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Uranium).asStack(3), 8000, 200)
                .duration(300).EUt(30).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("uranium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Uranium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 760, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Uranium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Neptunium).asStack(3), 8000, 200)
                .duration(300).EUt(60).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("neptunium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Neptunium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 760, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Neptunium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Plutonium).asStack(3), 8000, 200)
                .duration(300).EUt(120).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("plutonium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Plutonium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 1330, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Plutonium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Americium).asStack(3), 8000, 200)
                .duration(300).EUt(240).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("americium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Americium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 1780, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Americium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Curium).asStack(3), 8000, 200)
                .duration(300).EUt(480).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("curium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Curium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 2370, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Curium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Berkelium).asStack(3), 8000, 200)
                .duration(300).EUt(960).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("berkelium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Berkelium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 3160, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Berkelium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Californium).asStack(3), 8000, 200)
                .duration(300).EUt(1920).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("californium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Californium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 4220, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Californium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Einsteinium).asStack(3), 8000, 200)
                .duration(300).EUt(3840).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("einsteinium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Einsteinium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 5630, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Einsteinium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Fermium).asStack(3), 8000, 200)
                .duration(300).EUt(7680).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("fermium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Fermium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 7500, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Fermium).asStack(), 8000, 200)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Mendelevium).asStack(3), 8000, 200)
                .duration(300).EUt(15360).save(provider);

        GTRecipeTypes.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("mendelevium_waste")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.waste, Mendelevium).asStack())
                .chancedOutput(CTNHItems.NUCLEAR_WASTE.asStack(), 10000, 0)
                .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, Mendelevium).asStack(), 8000, 200)
                .duration(300).EUt(30720).save(provider);

        NuclearComposition.COMPOSITIONS.forEach(com -> {
            String name = com.name;
            int complexity = com.complexity;
            var composition = com.composition;
            GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder(name + "_hexafluoride")
                    .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, GTMaterials.get(name)).asStack())
                    .inputFluids(Fluorine.getFluid(6000))
                    .outputFluids(GTMaterials.get(name + "_hexafluoride").getFluid(1000))
                    .EUt(120)
                    .duration(200 * complexity / 100)
                    .save(provider);

            var builder = CTNHRecipeTypes.GAS_CENTRIFUGE_RECIPES.recipeBuilder(name)
                    .duration(500 * complexity / 100)
                    .EUt(960)
                    .inputFluids(GTMaterials.get(name + "_hexafluoride").getFluid(20000));

            composition.forEach(m -> {
                    builder.outputFluids(GTMaterials.get(m.key + "_hexafluoride").getFluid(m.value * 2))
                            .save(provider);
                    // [Mat + F6] + 3H2O -> [Mat + F6 + 3H2O]
                    GTRecipeTypes.CRACKING_RECIPES.recipeBuilder(m.key + "steam_cracked")
                            .inputFluids(GTMaterials.get(m.key + "_hexafluoride").getFluid(1000))
                            .inputFluids(Steam.getFluid(3000))
                            .outputFluids(GTMaterials.get(m.key + "_hexafluoride_steam_cracked").getFluid(1000))
                            .duration(40 * complexity / 100).EUt(120).save(provider);
                    // [Mat + F6 + 3H2O] -> [Mat + 2O] + 6HF + O (O lost)
                    GTRecipeTypes.BLAST_RECIPES.recipeBuilder(m.key + "_oxide")
                            .circuitMeta(0)
                            .inputFluids(GTMaterials.get(m.key + "_hexafluoride_steam_cracked").getFluid(1000))
                            .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear,GTMaterials.get(m.key).getProperty(CTNHPropertyKeys.NUCLEAR).getOxideMaterial()).asStack())
                            .outputFluids(HydrofluoricAcid.getFluid(6000))
                            .blastFurnaceTemp(600)
                            .duration(600 * complexity / 100)
                            .EUt(120)
                            .save(provider);
            });
        });
        CTNHRecipeTypes.GAS_CENTRIFUGE_RECIPES.recipeBuilder("uranium")
                .duration(500)
                .EUt(960)
                .inputFluids(UraniumHexafluoride.getFluid(20000))
                .outputFluids(Uranium234Hexafluoride.getFluid(20))
                .outputFluids(EnrichedUraniumHexafluoride.getFluid(2000))
                .outputFluids(DepletedUraniumHexafluoride.getFluid(17980))
                .save(provider);
        GTRecipeTypes.CRACKING_RECIPES.recipeBuilder("uranium_234_steam_cracked")
                .inputFluids(Uranium234Hexafluoride.getFluid(1000))
                .inputFluids(Steam.getFluid(3000))
                .outputFluids(Uranium234HexafluorideSteamCracked.getFluid(1000))
                .duration(40 * 100 / 100).EUt(120).save(provider);
        // [Mat + F6 + 3H2O] -> [Mat + 2O] + 6HF + O (O lost)
        GTRecipeTypes.BLAST_RECIPES.recipeBuilder("uranium_234_oxide")
                .circuitMeta(0)
                .inputFluids(Uranium234HexafluorideSteamCracked.getFluid(1000))
                .outputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear,Uranium234.getProperty(CTNHPropertyKeys.NUCLEAR).getOxideMaterial()).asStack())
                .outputFluids(HydrofluoricAcid.getFluid(6000))
                .blastFurnaceTemp(600)
                .duration(600 * 100 / 100)
                .EUt(120)
                .save(provider);

        CTNHRecipeTypes.HOT_COOLANT_TURBINE_RECIPES.recipeBuilder("steam")
                .inputFluids(CTNHMaterials.HotSteam.getFluid(54))
                .outputFluids(Steam.getFluid(54))
                .EUt(-32)
                .duration(60)
                .save(provider);
        CTNHRecipeTypes.HOT_COOLANT_TURBINE_RECIPES.recipeBuilder("deuterium")
                .inputFluids(CTNHMaterials.HotDeuterium.getFluid(18))
                .outputFluids(Deuterium.getFluid(18))
                .EUt(-32)
                .duration(60)
                .save(provider);
        CTNHRecipeTypes.HOT_COOLANT_TURBINE_RECIPES.recipeBuilder("sodium")
                .inputFluids(CTNHMaterials.HotSodium.getFluid(10))
                .outputFluids(Sodium.getFluid(10))
                .EUt(-32)
                .duration(60)
                .save(provider);
        CTNHRecipeTypes.HOT_COOLANT_TURBINE_RECIPES.recipeBuilder("sodium_potassium")
                .inputFluids(CTNHMaterials.HotSodiumPotassium.getFluid(9))
                .outputFluids(SodiumPotassium.getFluid(9))
                .EUt(-32)
                .duration(60)
                .save(provider);
        GTRecipeTypes.FORMING_PRESS_RECIPES.recipeBuilder("shielded_reactor_casing")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plateDense, Lead).asStack(12),
                        GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plateDense, StainlessSteel).asStack(2),
                        GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plateDense, VanadiumSteel).asStack(4))
                .outputItems(CTNHBlocks.CASING_SHIELDED_REACTOR.get().asItem().getDefaultInstance().copyWithCount(4))
                .EUt(500)
                .duration(1500)
                .save(provider);
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("nuclear_reactor")
                .inputItems(CTNHBlocks.CASING_SHIELDED_REACTOR.get().asItem().getDefaultInstance().copyWithCount(4),
                   GTItems.SENSOR_EV.asStack(2),
                   GTItems.ELECTRIC_MOTOR_EV.asStack(2),
                   GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.plate, Ultimet).asStack(2))
                .outputItems(CTNHMultiblockMachines.NUCLEAR_REACTOR.asStack())
                .EUt(1920)
                .duration(400)
                .save(provider);
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("hot_coolant_turbine")
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.gear, Stellite100).asStack(4),
                    GTMachines.HULL[GTValues.EV].asStack())
                .inputItems(CustomTags.EV_CIRCUITS, 2)
                .outputItems(CTNHMultiblockMachines.HOT_COOLANT_TURBINE.asStack())
                .EUt(1920)
                .duration(400)
                .save(provider);

        // GT铀和钚处理
        VanillaRecipeHelper.addShapelessRecipe(provider, "uranium_235", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Uranium235).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Uranium235).asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "uranium_238", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Uranium238).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Uranium238).asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "pluotnium_239", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Plutonium239).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Plutonium239).asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "pluotnium_241", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Plutonium241).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Plutonium241).asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "thorium_232", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Thorium232).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Thorium232).asStack());
        VanillaRecipeHelper.addShapelessRecipe(provider, "thorium_233", GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.nuclear, Thorium233).asStack(), GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust, Thorium).asStack());

        NuclearMaterialsInfo.decayMaterial.forEach(material -> {
            var nuclear = material.getProperty(CTNHPropertyKeys.NUCLEAR);
            nuclear.fertileDecay.forEach((fertile, amount) ->{
                CTNHRecipeTypes.DECAY_POOLS.recipeBuilder(material.getName() + "_oxide_decay")
                        .duration(600).EUt(30)
                        .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, nuclear.getOxideMaterial()).asStack())
                        .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, fertile.getProperty(CTNHPropertyKeys.NUCLEAR).getOxideMaterial()).asStack(), 9000, 100)
                        .save(provider);
                CTNHRecipeTypes.DECAY_POOLS.recipeBuilder(material.getName() + "_carbide_decay")
                        .duration(600).EUt(30)
                        .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, nuclear.getCarbideMaterial()).asStack())
                        .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, fertile.getProperty(CTNHPropertyKeys.NUCLEAR).getCarbideMaterial()).asStack(), 9000, 100)
                        .save(provider);
                CTNHRecipeTypes.DECAY_POOLS.recipeBuilder(material.getName() + "_nitride_decay")
                        .duration(600).EUt(30)
                        .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, nuclear.getNitrideMaterial()).asStack())
                        .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, fertile.getProperty(CTNHPropertyKeys.NUCLEAR).getNitrideMaterial()).asStack(), 9000, 100)
                        .save(provider);
                CTNHRecipeTypes.DECAY_POOLS.recipeBuilder(material.getName() + "_zirconium_decay")
                        .duration(600).EUt(30)
                        .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, nuclear.getZirconiumAlloyMaterial()).asStack())
                        .chancedOutput(GTMaterialItems.MATERIAL_ITEMS.get(CTNHTagPrefixes.fuel, fertile.getProperty(CTNHPropertyKeys.NUCLEAR).getZirconiumAlloyMaterial()).asStack(), 9000, 100)
                        .save(provider);
            });

        });
    }
}
