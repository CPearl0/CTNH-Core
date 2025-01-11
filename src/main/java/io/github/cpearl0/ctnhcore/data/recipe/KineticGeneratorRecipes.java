package io.github.cpearl0.ctnhcore.data.recipe;

import com.mo_guang.ctpp.CTPP;
import com.mo_guang.ctpp.recipe.CTPPRecipeBuilder;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class KineticGeneratorRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTPPRecipeBuilder.of(CTPP.id("seasson"), CTNHRecipeTypes.SEASON_STEAM_RECIPES)
                .outputStress(8192)
                .circuitMeta(1)
                .addData("stress", 8192)
                .duration(3000)
                .save(provider);
    }
}
