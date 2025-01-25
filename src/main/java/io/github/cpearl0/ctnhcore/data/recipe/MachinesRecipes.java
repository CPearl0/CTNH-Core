package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import earth.terrarium.botarium.common.fluid.utils.FluidIngredient;
import io.github.cpearl0.ctnhcore.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.HULL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraftforge.fluids.FluidStack;
import wayoftime.bloodmagic.common.fluid.BloodMagicFluids;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.api.GTValues.ZPM;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.HULL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Osmiridium;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;

public class MachinesRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "underfloor_heating_system",
                CTNHMultiblockMachines.UNDERFLOOR_HEATING_SYSTEM.asStack(),
                "SPS", "IwI", "SPS",
                'S', new ItemStack(AllBlocks.COPPER_SHINGLES.getStandard().get()),
                'P', GTBlocks.CASING_BRONZE_PIPE.asStack(),
                'I', new UnificationEntry(TagPrefix.plate, GTMaterials.Iron));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "extreme_engine_intake_casing",
                CTNHBlocks.CASING_ULTIMATE_ENGINE_INTAKE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft), "PhP",
                "RFR", "PwP", 'R', new UnificationEntry(TagPrefix.rotor, GTMaterials.NaquadahAlloy), 'F',
                CTNHBlocks.CASING_NAQUADAH_BLOCK.asStack(), 'P',
                new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.NaquadahAlloy));
        ASSEMBLER_RECIPES.recipeBuilder("zpm_large_miner")
                .inputItems(HULL[ZPM])
                .inputItems(frameGt, Osmiridium, 4)
                .inputItems(CustomTags.ZPM_CIRCUITS, 4)
                .inputItems(ELECTRIC_MOTOR_ZPM, 4)
                .inputItems(ELECTRIC_PUMP_ZPM, 4)
                .inputItems(CONVEYOR_MODULE_ZPM, 4)
                .inputItems(gear, Osmiridium, 4)
                .circuitMeta(2)
                .outputItems(CTNHMultiblockMachines.ZPM_LARGE_MINER)
                .duration(400).EUt(VA[ZPM]).save(provider);
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
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_naquadah_alloy_gearbox",
                CTNHBlocks.CASING_NAQUADAH_GEARBOX.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft), "PhP", "GFG",
                "PwP", 'P', new UnificationEntry(TagPrefix.plate, GTMaterials.NaquadahAlloy), 'F',
                new UnificationEntry(frameGt, GTMaterials.NaquadahAlloy), 'G',
                new UnificationEntry(gear, GTMaterials.NaquadahAlloy));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "extreme_engine_intake_casing",
                CTNHBlocks.CASING_ULTIMATE_ENGINE_INTAKE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft), "PhP",
                "RFR", "PwP", 'R', new UnificationEntry(TagPrefix.rotor, GTMaterials.NaquadahAlloy), 'F',
                CTNHBlocks.CASING_NAQUADAH_BLOCK.asStack(), 'P',
                new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.NaquadahAlloy));
        ASSEMBLER_RECIPES.recipeBuilder("zpm_large_miner")
                .inputItems(HULL[ZPM])
                .inputItems(frameGt, Osmiridium, 4)
                .inputItems(CustomTags.ZPM_CIRCUITS, 4)
                .inputItems(ELECTRIC_MOTOR_ZPM, 4)
                .inputItems(ELECTRIC_PUMP_ZPM, 4)
                .inputItems(CONVEYOR_MODULE_ZPM, 4)
                .inputItems(gear, Osmiridium, 4)
                .circuitMeta(2)
                .outputItems(CTNHMultiblockMachines.ZPM_LARGE_MINER)
                .duration(400).EUt(VA[ZPM]).save(provider);
        CTNHRecipeTypes.BEAMS.recipeBuilder("test")
                .circuitMeta(24)
                .inputFluids(CTNHMaterials.Zenith_essence.getFluid(10))
                .EUt(999)
                .duration(1500)
                .addData("required_mana",0)
                .addData("mana",1)
                .save(provider);
        CTNHRecipeTypes.NANO_GENERATOR.recipeBuilder("tester")
                .circuitMeta(24)
                .inputItems(CTNHItems.HORIZEN_RUNE,1)
                .EUt(-1)
                .duration(10)
                .save(provider);
    }

}
