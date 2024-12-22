package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class DemonWillGeneratorRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.DEMON_WILL_GENERATOR_RECIPE.recipeBuilder("generator")
                .circuitMeta(0)
                .EUt(-32)
                .duration(80)
                .save(provider);
    }
}
