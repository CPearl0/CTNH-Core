package io.github.cpearl0.ctnhcore.data.recipe;

import com.mo_guang.ctpp.recipe.CTPPRecipeBuilder;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class MeadowRecipes {
    public static void init(Consumer<FinishedRecipe> provider){
        CTPPRecipeBuilder.of(CTNHCore.id("meadow"),CTNHRecipeTypes.MEADOW)
                .inputStress(2048)
                .duration(200)
                .save(provider);
    }
}
