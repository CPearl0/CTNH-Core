package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.simibubi.create.AllBlocks;
import io.github.cpearl0.ctnhcore.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.HULL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

import io.github.cpearl0.ctnhcore.registry.CTNHBlocks;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.api.GTValues.ZPM;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Osmiridium;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes.*;
import static twilightforest.init.TFItems.STEELEAF_INGOT;

public class MachinesRecipes {
    public static Component MONITOR = new Component(Stream.of(new Object[][] {
            { FALLBACK, COVER_SCREEN },
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
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
        COMPRESSOR_RECIPES.recipeBuilder("steelleaf")
                .duration(300)
                .EUt(2)
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .outputItems(STEELEAF_INGOT,1)
                .save(provider);
        MIXER_RECIPES.recipeBuilder("plantfood")
                .duration(500)
                .EUt(28)
                .inputItems(dust, CTNHMaterials.SpiritAsh, 4)
                .inputItems(dust, Apatite, 4)
                .outputItems(FERTILIZER,16)
                .save(provider);
        CTNHRecipeTypes.QUASAR_EYE.recipeBuilder("generator1")
                .circuitMeta(0)
                .inputFluids(CTNHMaterials.Mana.getFluid(100000))
                .EUt(-33554432*2)
                .duration(200)
                .addData("consumption",1000000)
                .addData("tier",1)
                .addData("active",1)
                .save(provider);
        CTNHRecipeTypes.ARC_REACTOR.recipeBuilder("test")
                .EUt(8192)
                .duration(20)
                .circuitMeta(1)
                .save(provider);
        CTNHRecipeTypes.TRAP_ENERGY.recipeBuilder("test")
                .EUt(1)
                .duration(10)
                .circuitMeta(1)
                .save(provider);
        CTNHRecipeTypes.VOID_MINER.recipeBuilder("void")
                .EUt(32678*64)
                .duration(20*100)
                .circuitMeta(1)
                .save(provider);
        CTNHRecipeTypes.MANA_GENERATOR.recipeBuilder("mana1")
                .EUt(-28)
                .duration(20)
                .circuitMeta(1)
                .inputFluids(CTNHMaterials.Mana.getFluid(1000))
                .save(provider);
        CTNHRecipeTypes.PVDRONE.recipeBuilder("blank")
                .duration(100)
                .circuitMeta(1)
                .save(provider);
        CTNHRecipeTypes.COMPILER_RECIPE.recipeBuilder("test")
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .inputItems(dust, CTNHMaterials.SteelLeaf, 1)
                .outputItems(dust, CTNHMaterials.SpiritAsh, 1)
                .addData("1",48)
                .addData("2",30)
                .addData("3",30)
                .addData("noisea",20)
                .addData("noiseb",2)
                .addData("range",120)
                .duration(20*30)
                .EUt(1000000)
                .save(provider);
        COMBUSTION_GENERATOR_FUELS.recipeBuilder("end")
                .inputFluids(CTNHMaterials.NQ_END_OF_GASOLINE.getFluid(1))
                .duration(1000)
                .EUt(-320)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("naquadah_gearbox_casing")
                .inputItems(plate, NaquadahAlloy, 4)
                .inputItems(gear, NaquadahAlloy, 2)
                .inputItems(frameGt, NaquadahAlloy)
                .circuitMeta(4)
                .outputItems(CTNHBlocks.CASING_NAQUADAH_GEARBOX.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16).save(provider);
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
                .inputFluids(PCBCoolant.getFluid(100))
                .EUt(1)
                .duration(100)
                .addData("required_mana",0)
                .addData("mana",0)
                .save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("empty_program")
                .inputItems(wireFine,RedAlloy,8)
                .inputItems(plate,Steel,2)
                .inputFluids(FluidIngredient.of(1000, Fluids.WATER))
                .outputItems(CTNHItems.PROGRAM_EMPTY.asStack())
                .EUt(30)
                .duration(200)
                .save(provider);
        registerMachineRecipe(provider,CTNHMachines.PERSONAL_COMPUTER,"PDP",
                "CAC", "PBP", 'A', CraftingComponent.HULL, 'C', ROTOR, 'P', CABLE, 'D', CIRCUIT, 'B' , SENSOR);
        registerMachineRecipe(provider,CTNHMachines.CIRCUIT_BUS, "A","B", 'A', MONITOR , 'B', CraftingComponent.HULL);
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder("astronomy_circuit")
                .inputItems(PLASTIC_CIRCUIT_BOARD)
                .inputItems(CENTRAL_PROCESSING_UNIT, 2)
                .inputItems(NAND_MEMORY_CHIP, 32)
                .inputItems(RANDOM_ACCESS_MEMORY, 4)
                .inputItems(wireFine, Tin, 16)
                .inputItems(plate, Polyethylene, 4)
                .outputItems(CTNHItems.ASTRONOMY_CIRCUIT_1.asStack())
                .solderMultiplier(2)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(400).EUt(90).save(provider);
        PHOTOVOLTAIC_GENERATOR.recipeBuilder("test")
                .duration(20)
                .EUt(-8192)
                .circuitMeta(1)
                .save(provider);
        PHOTOVOLTAIC_ASSEMBER.recipeBuilder("test")
                .duration(20)
                .EUt(-1)
                .circuitMeta(1)
                .addData("input",32678)
                .save(provider);
    }

}
