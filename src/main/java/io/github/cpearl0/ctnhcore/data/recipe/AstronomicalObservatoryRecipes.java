package io.github.cpearl0.ctnhcore.data.recipe;

import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class AstronomicalObservatoryRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.ASTRONOMICAL_OBSERVATORY.recipeBuilder("observe").circuitMeta(0)
                .duration(20)
                .EUt(30)
                .save(provider);
    }
}
