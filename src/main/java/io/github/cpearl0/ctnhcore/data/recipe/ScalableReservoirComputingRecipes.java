package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes.SCALABLE_RESERVOIR_COMPUTING;

public class ScalableReservoirComputingRecipes {
    public static void init(Consumer<FinishedRecipe> provider){

        SCALABLE_RESERVOIR_COMPUTING.recipeBuilder("player")
                .duration(20)
                .EUt(V[ZPM]*64)
                .circuitMeta(1)
                .addData("maxCWUt",512)
                .addData("wetwareDuration",200)
                .addData("sacrifice","minecraft:player")
                .save(provider);
    }

}
