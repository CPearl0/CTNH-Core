package io.github.cpearl0.ctnhcore.data.recipe.age;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class HVRecipes {

    // 这里存放的是高压(hv)时期的配方
    public static void init(Consumer<FinishedRecipe> provider) {
        addHVMotorRecipes(provider);
    }

    private static void addHVMotorRecipes(Consumer<FinishedRecipe> provider) {
        // HV电动马达（琥珀金导线环绕磁化钢杆，不锈钢板底座）
        VanillaRecipeHelper.addShapedRecipe(provider, CTNHCore.id("crafttable/electric_motor_hv"),
                GTItems.ELECTRIC_MOTOR_HV.asStack(),
                " A ", "ABA", "CAC",
                'A', ChemicalHelper.get(TagPrefix.wireGtDouble, GTMaterials.Electrum),
                'B', ChemicalHelper.get(TagPrefix.rod, GTMaterials.SteelMagnetic),
                'C', ChemicalHelper.get(TagPrefix.plate, GTMaterials.StainlessSteel));

        // HV电动马达组装机配方（与工作台同材料）
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("assembler/electric_motor_hv"))
                .inputItems(TagPrefix.wireGtDouble, GTMaterials.Electrum, 4)
                .inputItems(TagPrefix.rod, GTMaterials.SteelMagnetic)
                .inputItems(TagPrefix.plate, GTMaterials.StainlessSteel, 2)
                .outputItems(GTItems.ELECTRIC_MOTOR_HV)
                .duration(100).EUt(GTValues.VA[GTValues.LV]).save(provider);
    }
}
