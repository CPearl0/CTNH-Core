package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.mo_guang.ctpp.recipe.CTPPRecipeBuilder;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class SinteringRecipes {
    public static void init(Consumer<FinishedRecipe> provider){
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_1"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Hematite).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_2"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Iron).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_3"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Goethite).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_4"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Magnetite).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_5"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Pyrite).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
        CTPPRecipeBuilder.of(CTNHCore.id("refined_iron_ingot_6"), CTNHRecipeTypes.SINTERING_KILN)
                .inputStress(8192)
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.YellowLimonite).get().asItem().getDefaultInstance().copyWithCount(32))
                .inputItems(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.dust,GTMaterials.Coke).get().asItem().getDefaultInstance().copyWithCount(16))
                .outputItems(CTNHItems.REFINED_IRON_INGOT.get().getDefaultInstance().copyWithCount(6))
                .duration(200)
                .save(provider);
    }
}
