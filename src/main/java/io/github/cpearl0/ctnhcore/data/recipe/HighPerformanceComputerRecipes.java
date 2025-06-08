package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import io.github.cpearl0.ctnhcore.registry.CTNHMachines;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;

public class HighPerformanceComputerRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        Material[] rodMaterials ={GTMaterials.Platinum,GTMaterials.Ruthenium,GTMaterials.Osmium};
        for (int tier = GTValues.HV; tier <= GTValues.IV; tier++){
            var definition = CTNHMachines.HIGH_PERFORMANCE_COMPUTER[tier];
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("high_performance_computer_"+ GTValues.VN[tier])
                    .inputItems(CASING.getIngredient(tier),1)
                    .inputItems(TagPrefix.plate,rodMaterials[tier - GTValues.HV],6)
                    .inputItems(VOLTAGE_COIL.getIngredient(tier),4)
                    .inputItems(POWER_COMPONENT.getIngredient(tier),16)
                    .inputItems(CustomTags.BATTERIES_ARRAY[tier],4)
                    .inputItems(CABLE_QUAD.getIngredient(tier+1))
                    .inputItems(CustomTags.CIRCUITS_ARRAY[tier+2],16)   //喜欢吗
                    .circuitMeta(0)
                    .outputItems(definition)
                    .save(provider);
        }
    }
}
