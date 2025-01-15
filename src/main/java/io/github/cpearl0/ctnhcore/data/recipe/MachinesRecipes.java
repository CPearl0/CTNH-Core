package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.registry.CTNHItems;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHMultiblockMachines;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Consumer;

public class MachinesRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "underfloor_heating_system",
                CTNHMultiblockMachines.UNDERFLOOR_HEATING_SYSTEM.asStack(),
                "SPS", "IwI", "SPS",
                'S', new ItemStack(AllBlocks.COPPER_SHINGLES.getStandard().get()),
                'P', GTBlocks.CASING_BRONZE_PIPE.asStack(),
                'I', new UnificationEntry(TagPrefix.plate, GTMaterials.Iron));
        CTNHRecipeTypes.QUASAR_EYE.recipeBuilder("generator1")
                .circuitMeta(0)
                .inputFluids(CTNHMaterials.Mana.getFluid(500000))
                .EUt(-67108864)
                .duration(100)
                .addData("consumption",1000000)
                .save(provider);
        CTNHRecipeTypes.QUASAR_EYE.recipeBuilder("generator2")
                .circuitMeta(1)
                .inputFluids(CTNHMaterials.Zenith_essence.getFluid(5000))
                .EUt(-134217728)
                .duration(150)
                .addData("consumption",100000)
                .save(provider);
    }

}
