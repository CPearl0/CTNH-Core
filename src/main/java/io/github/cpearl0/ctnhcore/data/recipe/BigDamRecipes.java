package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class BigDamRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.BIG_DAM.recipeBuilder("big_dam")
                .inputFluids(FluidIngredient.of(50, GTMaterials.Lubricant.getFluid()))
                .outputStress(1048576)
                .addData("output_stress", 1048576)
                .duration(200)
                .save(provider);
    }
}
