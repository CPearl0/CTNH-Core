package io.github.cpearl0.ctnhcore.data.recipe;

import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class WindPowerArrayRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.WIND_POWER_ARRAY.recipeBuilder("wind_power")
                .inputFluids(Lubricant.getFluid(1))
                .duration(20)
                .EUt(-32)
                .save(provider);
        CTNHRecipeTypes.WIND_POWER_ARRAY.recipeBuilder("wind_power_no_lubricant")
                .circuitMeta(0)
                .duration(20)
                .EUt(-24)
                .save(provider);
    }
}
