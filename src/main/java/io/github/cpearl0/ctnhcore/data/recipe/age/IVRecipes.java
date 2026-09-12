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

public class IVRecipes {

    // 这里存放的是强导压(iv)时期的配方
    public static void init(Consumer<FinishedRecipe> provider) {
        addIVMotorRecipes(provider);
    }

    private static void addIVMotorRecipes(Consumer<FinishedRecipe> provider) {
        // IV电动马达（石墨烯导线环绕钕磁杆，钨钢板底座）
        VanillaRecipeHelper.addShapedRecipe(provider, CTNHCore.id("crafttable/electric_motor_iv"),
                GTItems.ELECTRIC_MOTOR_IV.asStack(),
                " A ", "ABA", "CAC",
                'A', ChemicalHelper.get(TagPrefix.wireGtDouble, GTMaterials.Graphene),
                'B', ChemicalHelper.get(TagPrefix.rod, GTMaterials.NeodymiumMagnetic),
                'C', ChemicalHelper.get(TagPrefix.plate, GTMaterials.TungstenSteel));

        // IV电动马达组装机配方（与工作台同材料）
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("assembler/electric_motor_iv"))
                .inputItems(TagPrefix.wireGtDouble, GTMaterials.Graphene, 4)
                .inputItems(TagPrefix.rod, GTMaterials.NeodymiumMagnetic)
                .inputItems(TagPrefix.plate, GTMaterials.TungstenSteel, 2)
                .outputItems(GTItems.ELECTRIC_MOTOR_IV)
                .duration(100).EUt(GTValues.VA[GTValues.LV]).save(provider);
    }
}
