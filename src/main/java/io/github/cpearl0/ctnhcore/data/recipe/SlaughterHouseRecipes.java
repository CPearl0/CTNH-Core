package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class SlaughterHouseRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.SLAUGHTER_HOUSE.recipeBuilder("kill")
                .inputFluids(GTMaterials.Lubricant.getFluid(2))
                .duration(60)
                .EUt(480)
                .save(provider);
    }
}
