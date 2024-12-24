package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.mo_guang.ctpp.recipe.CTPPRecipeBuilder;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class BigDamRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTPPRecipeBuilder.of(GTCEu.id("big_dam"),CTNHRecipeTypes.BIG_DAM)
                .outputStress(1048576)
                .inputFluids(GTMaterials.Lubricant.getFluid(50))
                .addData("output_stress", 1048576)
                .duration(200)
                .save(provider);
    }
}
