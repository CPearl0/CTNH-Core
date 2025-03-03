package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class AcceleratorRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.ACCELERATOR_UP.recipeBuilder("up_nu")
                .circuitMeta(22)
                .duration(50)
                .addData("type","addnu")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_UP.recipeBuilder("up_proton")
                .circuitMeta(23)
                .duration(50)
                .addData("type","addproton")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_UP.recipeBuilder("up_element")
                .circuitMeta(24)
                .duration(50)
                .addData("type","addelement")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_DOWN.recipeBuilder("up_nu")
                .circuitMeta(22)
                .duration(50)
                .addData("type","addnu")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_DOWN.recipeBuilder("up_proton")
                .circuitMeta(23)
                .duration(50)
                .addData("type","addproton")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_DOWN.recipeBuilder("up_element")
                .circuitMeta(24)
                .duration(50)
                .addData("type","addelement")
                .EUt(32678)
                .save(provider);
        CTNHRecipeTypes.ACCELERATOR_UP.recipeBuilder("testanti1")
                .duration(1000)
                .circuitMeta(12)
                .EUt(100)
                .addData("type","nu")
                .addData("speed",1000)
                .addData("darkmatter","nu")
                .save(provider);

    }
}
