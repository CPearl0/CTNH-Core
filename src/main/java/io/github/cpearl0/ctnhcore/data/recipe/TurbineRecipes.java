package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import io.github.cpearl0.ctnhcore.registry.CTNHMultiblockMachines;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;

public class TurbineRecipes {
    public static void init(Consumer<FinishedRecipe> provider){
        ASSEMBLY_LINE_RECIPES.recipeBuilder("hyper_plasma_turbine")
                .inputItems(GTMultiMachines.LARGE_PLASMA_TURBINE)
                .inputItems(CustomTags.UV_CIRCUITS,16)
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL,4)
                .inputItems(TagPrefix.plateDense,Darmstadtium,4)
                .inputItems(NEUTRONIUM_REINFORCED_TURBINE_CASING)
                .outputItems(CTNHMultiblockMachines.HYPER_PLASMA_TURBINE)
                .stationResearch(b->b
                        .researchStack(GTMultiMachines.LARGE_PLASMA_TURBINE.asStack())
                        .CWUt(128)
                        .EUt(VA[ZPM]))
                .duration(600)
                .EUt(VA[ZPM])
                .save(provider);

        ASSEMBLY_LINE_RECIPES.recipeBuilder("neutronium_reinforced_turbine_casing")
                .inputItems(GTBlocks.CASING_TUNGSTENSTEEL_TURBINE)
                .inputItems(TagPrefix.plateDense,Osmiridium,6)
                .inputFluids(Neutronium.getFluid(144*16))
                .outputItems(NEUTRONIUM_REINFORCED_TURBINE_CASING,4)
                .stationResearch(b->b
                        .researchStack(GTBlocks.CASING_TUNGSTENSTEEL_TURBINE.asStack())
                        .CWUt(128)
                        .EUt(VA[ZPM]))
                .duration(60)
                .EUt(VA[ZPM])
                .save(provider);

        ASSEMBLY_LINE_RECIPES.recipeBuilder("hyper_plasma_turbine_rotor")
                .inputItems(TagPrefix.block,Neutronium)
                .inputItems(TagPrefix.plateDouble,Neutronium,4)
                .inputItems(TagPrefix.plateDouble,Neutronium,4)
                .inputItems(TagPrefix.plateDouble,Neutronium,4)
                .inputItems(TagPrefix.plateDouble,Neutronium,4)
                .inputFluids(Neutronium.getFluid(144*48))
                .outputItems(HYPER_PLASMA_TURBINE_ROTOR,1)
                .stationResearch(b->b
                        .researchStack(GTItems.TURBINE_ROTOR.asStack())
                        .CWUt(128)
                        .EUt(VA[ZPM]))
                .duration(60)
                .EUt(VA[ZPM])
                .save(provider);
    }
}
