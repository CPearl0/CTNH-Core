package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class NaqReactorRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.NAQ_MK1.recipeBuilder("naq_mk1")
                .inputFluids(FluidIngredient.of(1, GTMaterials.Lubricant.getFluid()))
                .duration(100)
                .EUt(-32768)
                .save(provider);
    }
}
