package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class WaterPowerStationRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        CTNHRecipeTypes.WATER_POWER.recipeBuilder("water_power")
                .inputFluids(GTMaterials.Lubricant.getFluid(2))
                .duration(20)
                .EUt(-32)
                .save(provider);
    }
}
