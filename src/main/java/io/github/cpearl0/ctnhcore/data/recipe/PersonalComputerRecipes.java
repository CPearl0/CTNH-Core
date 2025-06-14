package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import io.github.cpearl0.ctnhcore.common.item.ProgramItem;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;

public class PersonalComputerRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        for (var program : ProgramItem.PROGRAMS) {
            var name = BuiltInRegistries.ITEM.getKey(program).getPath();
            if (program != CTNHItems.PROGRAM_EMPTY.get()) {
                CTNHRecipeTypes.PERSONAL_COMPUTER.recipeBuilder("copy_" + name)
                        .circuitMeta(10)
                        .inputItems(CTNHItems.PROGRAM_EMPTY)
                        .notConsumable(program)
                        .outputItems(program)
                        .duration(40)
                        .EUt(30)
                        .save(provider);
            }
        }
        CTNHRecipeTypes.PERSONAL_COMPUTER.recipeBuilder("program_rocket_1")
                .circuitMeta(0)
                .inputItems(CTNHItems.PROGRAM_EMPTY)
                .notConsumable(CTNHItems.PROGRAM_ROCKET_CORE_1)
                .notConsumable(CTNHItems.GREAT_ASTRONOMY_CIRCUIT_1)
                .outputItems(CTNHItems.PROGRAM_ROCKET_1)
                .duration(40)
                .EUt(30)
                .save(provider);

        CTNHRecipeTypes.PERSONAL_COMPUTER.recipeBuilder("astronomy_circuit_force")
                .circuitMeta(3)
                .inputItems(CTNHItems.ASTRONOMY_CIRCUIT_1)
                .duration(60)
                .EUt(VA[HV])
                .outputItems(CTNHItems.GREAT_ASTRONOMY_CIRCUIT_1)
                .save(provider);

        CTNHRecipeTypes.PERSONAL_COMPUTER.recipeBuilder("program_rocket_1_force")
                .circuitMeta(3)
                .inputItems(CTNHItems.PROGRAM_EMPTY)
                .notConsumable(CTNHItems.GREAT_ASTRONOMY_CIRCUIT_1)
                .duration(60)
                .EUt(VA[HV])
                .outputItems(CTNHItems.PROGRAM_ROCKET_1)
                .save(provider);
    }
}
