package io.github.cpearl0.ctnhcore.data.recipe.wood;

import io.github.cpearl0.ctnhcore.data.recipe.RecipeRemoval;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.ItemMaterialData;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.ItemMaterialInfo;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.BIO_CHAFF;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;

@SuppressWarnings("removal")
public class WoodMachineRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        registerGTWoodRecipes(provider);
        registerWoodRecipes(provider);
        registerPyrolyseOvenRecipes(provider);
    }

    private static final List<WoodTypeEntry> DEFAULT_ENTRIES = new ArrayList<>();

    private static List<WoodTypeEntry> getDefaultEntries() {
        if (DEFAULT_ENTRIES.isEmpty()) {
            WoodTypeEntries.addWoodTypeEntry(DEFAULT_ENTRIES);
        }

        return DEFAULT_ENTRIES;
    }

    public static void registerMaterialInfo() {
        for (WoodTypeEntry entry : getDefaultEntries()) {
            registerWoodMaterialInfo(entry);
        }
    }

    /**
     * Standardized processing for wood types
     */
    private static void registerWoodRecipes(Consumer<FinishedRecipe> provider) {
        if (ConfigHolder.INSTANCE.recipes.nerfWoodCrafting) {
            VanillaRecipeHelper.addShapedRecipe(provider, "stick_saw", new ItemStack(Items.STICK, 4), "s", "P", "P",
                    'P', ItemTags.PLANKS);
            VanillaRecipeHelper.addShapedRecipe(provider, "stick_normal", new ItemStack(Items.STICK, 2), "P", "P", 'P',
                    ItemTags.PLANKS);
        }

        for (WoodTypeEntry entry : getDefaultEntries()) {
            registerWoodTypeRecipe(provider, entry);
        }
    }

    /**
     * Adds all standard unification info and tag for a wood type
     *
     * @param entry the entry to register for
     */
    public static void registerWoodMaterialInfo(@NotNull WoodTypeEntry entry) {
        for (var log_ : entry.getLogs()) {
            if (log_ != null && entry.addLogOreDict) {
                ItemMaterialData.registerItemMaterialEntry(log_, log, entry.material);
            }
        }

        if (entry.addPlanksOreDict) {
            ItemMaterialData.registerItemMaterialEntry(entry.planks, planks, entry.material);
        }
        if (entry.addPlanksMaterialInfo) {
            ItemMaterialData.registerMaterialInfo(entry.planks,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M)));
        }

        if (entry.door != null) {
            if (entry.addDoorsOreDict) {
                ItemMaterialData.registerItemMaterialEntry(entry.door, door, entry.material);
            }
            if (entry.addDoorsMaterialInfo) {
                ItemMaterialData.registerMaterialInfo(entry.door, ConfigHolder.INSTANCE.recipes.hardWoodRecipes ?
                        new ItemMaterialInfo(new MaterialStack(entry.material, M * 2),
                                new MaterialStack(GTMaterials.Iron, M / 9)) : // screw
                        new ItemMaterialInfo(new MaterialStack(entry.material, M * 2)));
            }
        }

        if (entry.slab != null) {
            if (entry.addSlabsOreDict) {
                ItemMaterialData.registerItemMaterialEntry(entry.slab, slab, entry.material);
            }
            if (entry.addSlabsMaterialInfo) {
                ItemMaterialData.registerMaterialInfo(entry.slab,
                        new ItemMaterialInfo(new MaterialStack(entry.material, M / 2)));
            }
        }

        if (entry.fence != null) {
            if (entry.addFencesOreDict) {
                ItemMaterialData.registerItemMaterialEntry(entry.fence, fence, entry.material);
            }
            if (entry.addFencesMaterialInfo) {
                ItemMaterialData.registerMaterialInfo(entry.fence,
                        new ItemMaterialInfo(new MaterialStack(entry.material, M)));
            }
        }

        if (entry.fenceGate != null) {
            if (entry.addFenceGatesOreDict) {
                ItemMaterialData.registerItemMaterialEntry(entry.fenceGate, fenceGate, entry.material);
            }
            if (entry.addFenceGatesMaterialInfo) {
                ItemMaterialData.registerMaterialInfo(entry.fenceGate,
                        new ItemMaterialInfo(new MaterialStack(entry.material, M * 3)));
            }
        }

        if (entry.stairs != null) {
            if (entry.addStairsOreDict) {
                ItemMaterialData.registerItemMaterialEntry(entry.stairs, stairs, entry.material);
            }
            if (entry.addStairsMaterialInfo) {
                ItemMaterialData.registerMaterialInfo(entry.stairs,
                        new ItemMaterialInfo(new MaterialStack(entry.material, (3 * M) / 2)));
            }
        }

        if (entry.boat != null && entry.addBoatsMaterialInfo) {
            ItemMaterialData.registerMaterialInfo(entry.boat,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M * 5)));
        }

        if (entry.chestBoat != null && entry.addChestBoatsMaterialInfo) {
            ItemMaterialData.registerMaterialInfo(entry.chestBoat,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M * 13)));
        }

        if (entry.button != null && entry.addButtonsMaterialInfo) {
            ItemMaterialData.registerMaterialInfo(entry.button,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M / 6)));
        }

        if (entry.pressurePlate != null && entry.addPressurePlatesMaterialInfo) {
            ItemMaterialData.registerMaterialInfo(entry.pressurePlate,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M)));
        }
    }

    /**
     * Adds all standard recipes for a wood type
     *
     * @param entry the entry to register for
     */
    public static void registerWoodTypeRecipe(Consumer<FinishedRecipe> provider, @NotNull WoodTypeEntry entry) {
        final String name = entry.woodName;
        TagKey<Item> logTag = entry.logTag;
        boolean hasPlanksRecipe = entry.planksRecipeName != null;

        if (entry.strippedLog != null) {
            // strip log
            LATHE_RECIPES.recipeBuilder("strip_" + name + "_log")
                    .inputItems(entry.log)
                    .outputItems(entry.strippedLog)
                    .outputItems(dust, Wood, 1)
                    .duration(160).EUt(VA[ULV])
                    .save(provider);

            // lathe stripped log
            LATHE_RECIPES.recipeBuilder("lathe_stripped_" + name + "_log")
                    .inputItems(entry.strippedLog)
                    .outputItems(rodLong, Wood, 4)
                    .outputItems(dust, Wood, 1)
                    .duration(160).EUt(VA[ULV])
                    .save(provider);
        }

        if (entry.strippedWood != null) {
            // strip wood
            LATHE_RECIPES.recipeBuilder("strip_" + name + "_wood")
                    .inputItems(entry.wood)
                    .outputItems(entry.strippedWood)
                    .outputItems(dust, Wood, 1)
                    .duration(160).EUt(VA[ULV])
                    .save(provider);

            // lathe stripped wood
            LATHE_RECIPES.recipeBuilder("lathe_stripped_" + name + "_wood")
                    .inputItems(entry.strippedWood)
                    .outputItems(rodLong, Wood, 4)
                    .outputItems(dust, Wood, 1)
                    .duration(160).EUt(VA[ULV])
                    .save(provider);
        }

        if (entry.planks == null) {
            return;
        }

        if (entry.generateLogToPlankRecipe) {
            if (ConfigHolder.INSTANCE.recipes.nerfWoodCrafting) {
                VanillaRecipeHelper.addShapelessRecipe(provider,
                        hasPlanksRecipe ? entry.planksRecipeName : name + "_planks",
                        new ItemStack(entry.planks, 2), logTag);
            } else if (!hasPlanksRecipe) {
                VanillaRecipeHelper.addShapelessRecipe(provider, name + "_planks",
                        new ItemStack(entry.planks, 4), logTag);
            }

            // log -> plank saw crafting
            VanillaRecipeHelper.addShapedRecipe(provider, name + "_planks_saw",
                    new ItemStack(entry.planks, ConfigHolder.INSTANCE.recipes.nerfWoodCrafting ? 4 : 6),
                    "s", "L", 'L', logTag);

            // log -> plank cutting
            CUTTER_RECIPES.recipeBuilder(name + "_planks")
                    .inputItems(logTag)
                    .outputItems(new ItemStack(entry.planks, 6))
                    .outputItems(dust, Wood, 2)
                    .duration(200)
                    .EUt(VA[ULV])
                    .save(provider);
        }
        ItemMaterialData.registerMaterialInfo(entry.planks,
                new ItemMaterialInfo(new MaterialStack(GTMaterials.Wood, M)));

        // door
        if (entry.door != null) {
            final boolean hasDoorRecipe = entry.doorRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                String recipeName = hasDoorRecipe ? entry.doorRecipeName : name + "_door";
                if (entry.trapdoor != null) {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeName, new ItemStack(entry.door),
                            "PTd", "PRS", "PPs",
                            'P', entry.planks,
                            'T', entry.trapdoor,
                            'R', new MaterialEntry(ring, Iron),
                            'S', new MaterialEntry(screw, Iron));

                    // plank -> door assembling
                    ASSEMBLER_RECIPES.recipeBuilder(name + "_door")
                            .inputItems(entry.trapdoor)
                            .inputItems(new ItemStack(entry.planks, 4))
                            .inputFluids(Iron.getFluid(GTValues.L / 9))
                            .outputItems(entry.door)
                            .duration(400).EUt(4)
                            .addMaterialInfo(true, true).save(provider);
                } else {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeName, new ItemStack(entry.door),
                            "PTd", "PRS", "PPs",
                            'P', entry.planks,
                            'T', ItemTags.WOODEN_TRAPDOORS,
                            'R', new MaterialEntry(ring, Iron),
                            'S', new MaterialEntry(screw, Iron));

                    // plank -> door assembling
                    ASSEMBLER_RECIPES.recipeBuilder(name + "_door")
                            .inputItems(ItemTags.WOODEN_TRAPDOORS)
                            .inputItems(new ItemStack(entry.planks, 4))
                            .inputFluids(Iron.getFluid(GTValues.L / 9))
                            .outputItems(entry.door)
                            .duration(400).EUt(4).save(provider);
                }
            } else {
                if (!hasDoorRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_door", new ItemStack(entry.door, 3),
                            "PP", "PP", "PP",
                            'P', entry.planks);
                }

                ASSEMBLER_RECIPES.recipeBuilder(name + "_door")
                        .inputItems(new ItemStack(entry.planks, 6))
                        .outputItems(new ItemStack(entry.door, 3))
                        .circuitMeta(6)
                        .duration(600).EUt(4)
                        .addMaterialInfo(true)
                        .save(provider);
            }
        }

        // sign
        if (entry.sign != null && entry.slab != null) {
            final boolean hasSignRecipe = entry.signRecipeName != null;
            String recipeName = hasSignRecipe ? entry.signRecipeName : name + "_sign";
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                VanillaRecipeHelper.addShapedRecipe(provider, recipeName + "_iron", new ItemStack(entry.sign),
                        "LLL", "RPR", "sSd",
                        'P', entry.planks,
                        'R', new MaterialEntry(screw, Iron),
                        'L', entry.slab,
                        'S', entry.getStick());

                // plank -> sign assembling
                ASSEMBLER_RECIPES.recipeBuilder(recipeName + "_iron")
                        .circuitMeta(4)
                        .inputItems(new ItemStack(entry.slab, 1))
                        .inputItems(entry.getStick(), 1)
                        .inputFluids(Iron.getFluid(GTValues.L / 9))
                        .outputItems(entry.sign, 3)
                        .duration(200).EUt(4).save(provider);

                VanillaRecipeHelper.addShapedRecipe(provider, recipeName + "_steel", new ItemStack(entry.sign, 2),
                        "LLL", "RPR", "sSd",
                        'P', entry.planks,
                        'R', new MaterialEntry(screw, Steel),
                        'L', entry.slab,
                        'S', entry.getStick());

                // plank -> sign assembling
                ASSEMBLER_RECIPES.recipeBuilder(recipeName + "_steel")
                        .circuitMeta(4)
                        .inputItems(new ItemStack(entry.slab, 1))
                        .inputItems(entry.getStick(), 1)
                        .inputFluids(Steel.getFluid(GTValues.L / 9))
                        .outputItems(entry.sign, 5)
                        .duration(200).EUt(4).save(provider);
            } else {
                if (!hasSignRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeName + "_stick", new ItemStack(entry.sign, 3),
                            "PPP", "PPP", " S ",
                            'P', entry.planks,
                            'S', entry.getStick());
                }

                ASSEMBLER_RECIPES.recipeBuilder(name + "_sign")
                        .inputItems(new ItemStack(entry.planks), 6)
                        .inputItems(entry.getStick())
                        .outputItems(new ItemStack(entry.sign), 3)
                        .circuitMeta(4)
                        .duration(100).EUt(4).save(provider);
            }

            // hanging sign
            if (entry.hangingSign != null && entry.strippedLog != null) {
                final boolean hasHangingSignRecipe = entry.hangingSignRecipeName != null;
                String recipeNameHanging = hasHangingSignRecipe ? entry.hangingSignRecipeName : name + "_hanging_sign";

                if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeNameHanging, new ItemStack(entry.hangingSign),
                            "LLL", "C C", "RSR",
                            'C', Items.CHAIN,
                            'R', new MaterialEntry(ring, Iron),
                            'S', new ItemStack(entry.sign),
                            'L', new ItemStack(entry.slab));

                    VanillaRecipeHelper.addShapedRecipe(provider, recipeNameHanging + "_steel",
                            new ItemStack(entry.hangingSign, 2),
                            "LLL", "C C", "RSR",
                            'C', Items.CHAIN,
                            'R', new MaterialEntry(ring, Steel),
                            'S', new ItemStack(entry.sign),
                            'L', new ItemStack(entry.slab));

                    ASSEMBLER_RECIPES.recipeBuilder(name + "_hanging_sign")
                            .inputItems(entry.slab, 3)
                            .inputItems(entry.sign)
                            .inputItems(Items.CHAIN, 2)
                            .outputItems(entry.hangingSign)
                            .circuitMeta(5)
                            .duration(150).EUt(4).save(provider);
                } else {
                    if (!hasHangingSignRecipe) {
                        VanillaRecipeHelper.addShapedRecipe(provider, recipeNameHanging,
                                new ItemStack(entry.hangingSign, 6),
                                "C C", "LLL", "LLL",
                                'C', Items.CHAIN,
                                'L', entry.strippedLog);
                    }

                    ASSEMBLER_RECIPES.recipeBuilder(name + "_hanging_sign")
                            .inputItems(entry.strippedLog, 6)
                            .inputItems(new ItemStack(Items.CHAIN, 2))
                            .outputItems(entry.hangingSign, 6)
                            .circuitMeta(5)
                            .duration(100).EUt(4).save(provider);
                }
            }
        }

        // trapdoor
        if (entry.trapdoor != null) {
            final boolean hasTrapdoorRecipe = entry.trapdoorRecipeName != null;
            String recipeName = hasTrapdoorRecipe ? entry.trapdoorRecipeName : name + "_trapdoor";
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                VanillaRecipeHelper.addShapedRecipe(provider, recipeName + "_iron", new ItemStack(entry.trapdoor),
                        "BPS", "PdP", "SPB",
                        'P', entry.planks,
                        'B', new MaterialEntry(bolt, Iron),
                        'S', entry.getStick());

                // plank -> trapdoor assembling
                ASSEMBLER_RECIPES.recipeBuilder(recipeName + "_iron")
                        .circuitMeta(3)
                        .inputItems(new ItemStack(entry.planks, 2))
                        .inputFluids(Iron.getFluid(GTValues.L / 9))
                        .outputItems(entry.trapdoor)
                        .duration(200).EUt(4).save(provider);

                VanillaRecipeHelper.addShapedRecipe(provider, recipeName + "_steel", new ItemStack(entry.trapdoor, 2),
                        "BPS", "PdP", "SPB",
                        'P', entry.planks,
                        'B', new MaterialEntry(bolt, Steel),
                        'S', entry.getStick());

                // plank -> trapdoor assembling
                ASSEMBLER_RECIPES.recipeBuilder(recipeName + "_steel")
                        .circuitMeta(3)
                        .inputItems(new ItemStack(entry.planks, 2))
                        .inputFluids(Steel.getFluid(GTValues.L / 9))
                        .outputItems(entry.trapdoor, 2)
                        .duration(200).EUt(4).save(provider);
            } else {
                if (!hasTrapdoorRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeName, new ItemStack(entry.trapdoor, 2),
                            "PPP", "PPP",
                            'P', entry.planks);
                }

                ASSEMBLER_RECIPES.recipeBuilder(name + "_trapdoor")
                        .circuitMeta(3)
                        .inputItems(new ItemStack(entry.planks), 6)
                        .outputItems(new ItemStack(entry.trapdoor), 4)
                        .duration(100).EUt(4).save(provider);
            }
        }

        // stairs
        if (entry.stairs != null) {
            final boolean hasStairRecipe = entry.stairsRecipeName != null;
            if (entry.addStairsCraftingRecipe) {
                VanillaRecipeHelper.addShapedRecipe(provider,
                        hasStairRecipe ? entry.stairsRecipeName : name + "_stairs",
                        new ItemStack(entry.stairs, 4),
                        "P  ", "PP ", "PPP",
                        'P', entry.planks);
            }
        }

        // slab
        if (entry.slab != null) {
            if (entry.addSlabCraftingRecipe && !ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                VanillaRecipeHelper.addShapedRecipe(provider, name + "_slab", new ItemStack(entry.slab, 6),
                        "PPP", 'P', entry.planks);
            }

            // plank -> slab crafting
            VanillaRecipeHelper.addShapedRecipe(provider, name + "_slab_saw", new ItemStack(entry.slab, 2),
                    "sS", 'S', entry.planks);

            // plank -> slab cutting
            CUTTER_RECIPES.recipeBuilder(name + "_slab")
                    .inputItems(entry.planks)
                    .outputItems(new ItemStack(entry.slab, 2))
                    .duration(200).EUt(VA[ULV])
                    .addMaterialInfo(true)
                    .save(provider);
        }

        // fence
        if (entry.fence != null) {
            final boolean hasFenceRecipe = entry.fenceRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {

                VanillaRecipeHelper.addShapedRecipe(provider, hasFenceRecipe ? entry.fenceRecipeName : name + "_fence",
                        new ItemStack(entry.fence),
                        "PSP", "PSP", "PSP",
                        'P', entry.planks,
                        'S', entry.getStick());
            } else {
                if (!hasFenceRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_fence", new ItemStack(entry.fence, 3),
                            "PSP", "PSP",
                            'P', entry.planks,
                            'S', entry.getStick());
                }
            }

            // plank -> fence assembling
            ASSEMBLER_RECIPES.recipeBuilder(name + "_fence")
                    .inputItems(entry.planks)
                    .outputItems(entry.fence)
                    .circuitMeta(13)
                    .duration(100).EUt(4)
                    .addMaterialInfo(true)
                    .save(provider);
        }

        // fence gate
        if (entry.fenceGate != null) {
            final boolean hasFenceGateRecipe = entry.fenceGateRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {

                VanillaRecipeHelper.addShapedRecipe(provider,
                        hasFenceGateRecipe ? entry.fenceGateRecipeName : name + "_fence_gate",
                        new ItemStack(entry.fenceGate),
                        "F F", "SPS", "SPS",
                        'P', entry.planks,
                        'S', entry.getStick(),
                        'F', Items.FLINT);

                VanillaRecipeHelper.addShapedRecipe(provider, name + "_fence_gate_screws",
                        new ItemStack(entry.fenceGate, 2),
                        "IdI", "SPS", "SPS",
                        'P', entry.planks,
                        'S', entry.getStick(),
                        'I', new MaterialEntry(screw, Iron));
            } else {
                if (!hasFenceGateRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_fence_gate", new ItemStack(entry.fenceGate),
                            "SPS", "SPS",
                            'P', entry.planks,
                            'S', entry.getStick());
                }
            }

            // plank -> fence gate assembling
            ASSEMBLER_RECIPES.recipeBuilder(name + "_fence_gate")
                    .inputItems(new ItemStack(entry.planks, 2))
                    .inputItems(Tags.Items.RODS_WOODEN, 2)
                    .outputItems(entry.fenceGate)
                    .circuitMeta(2)
                    .duration(100).EUt(4)
                    .addMaterialInfo(true).save(provider);
        }

        // boat
        if (entry.boat != null) {
            final boolean hasBoatRecipe = entry.boatRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                if (entry.slab != null) {
                    VanillaRecipeHelper.addShapedRecipe(provider, hasBoatRecipe ? entry.boatRecipeName : name + "_boat",
                            new ItemStack(entry.boat),
                            "PHP", "PkP", "SSS",
                            'P', entry.planks,
                            'S', entry.slab,
                            'H', ItemTags.SHOVELS);
                }
            } else {
                if (!hasBoatRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_boat", new ItemStack(entry.boat),
                            "P P", "PPP",
                            'P', entry.planks);
                }
            }

            // plank -> boat assembling
            ASSEMBLER_RECIPES.recipeBuilder(name + "_boat")
                    .inputItems(new ItemStack(entry.planks, 5))
                    .outputItems(entry.boat)
                    .circuitMeta(15)
                    .duration(100).EUt(4)
                    .addMaterialInfo(true).save(provider);

            // chest boat
            if (entry.chestBoat != null) {
                final boolean hasChestBoatRecipe = entry.chestBoatRecipeName != null;
                String recipeName = hasChestBoatRecipe ? entry.chestBoatRecipeName : name + "_chest_boat";
                if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                    VanillaRecipeHelper.addShapedRecipe(provider, recipeName,
                            new ItemStack(entry.chestBoat),
                            " B ", "SCS", " w ",
                            'B', entry.boat,
                            'S', new MaterialEntry(bolt, Wood),
                            'C', Tags.Items.CHESTS_WOODEN);
                } else {
                    VanillaRecipeHelper.addShapelessRecipe(provider, recipeName,
                            new ItemStack(entry.chestBoat),
                            entry.boat, Tags.Items.CHESTS_WOODEN);
                }

                // boat -> chest boat assembling
                ASSEMBLER_RECIPES.recipeBuilder(name + "_chest_boat")
                        .inputItems(new ItemStack(entry.boat))
                        .inputItems(Tags.Items.CHESTS_WOODEN)
                        .outputItems(entry.chestBoat)
                        .circuitMeta(16)
                        .duration(100).EUt(4)
                        .addMaterialInfo(true).save(provider);
            }
        }

        // button
        if (entry.button != null) {
            final boolean hasButtonRecipe = entry.buttonRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                VanillaRecipeHelper.addShapedRecipe(provider, name + "_button", new ItemStack(entry.button, 6), "sP",
                        'P', new ItemStack(entry.pressurePlate));
            } else {
                if (!hasButtonRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_button", new ItemStack(entry.button), "P",
                            'P', new ItemStack(entry.planks));
                }
            }

            // plank -> button cutting
            CUTTER_RECIPES.recipeBuilder(name + "_button")
                    .inputItems(new ItemStack(entry.pressurePlate))
                    .outputItems(entry.button, 12)
                    .duration(250).EUt(VA[ULV]).save(provider);

            ItemMaterialData.registerMaterialInfo(entry.button,
                    new ItemMaterialInfo(new MaterialStack(entry.material, M / 9)));
        }

        // preesure plate
        if (entry.pressurePlate != null) {
            final boolean hasPressurePlateRecipe = entry.pressurePlateRecipeName != null;
            if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
                VanillaRecipeHelper.addShapedRecipe(provider, name + "_pressure_plate",
                        new ItemStack(entry.pressurePlate, 2), "SrS", "LCL", "SdS",
                        'S', new MaterialEntry(bolt, GTMaterials.Wood),
                        'L', entry.slab.asItem(),
                        'C', new MaterialEntry(spring, GTMaterials.Iron));

                ASSEMBLER_RECIPES.recipeBuilder(name + "_pressure_plate")
                        .inputItems(new ItemStack(entry.slab, 2))
                        .inputItems(spring, Iron)
                        .outputItems(entry.pressurePlate)
                        .circuitMeta(7)
                        .duration(100).EUt(VA[ULV]).save(provider);
            } else {
                if (!hasPressurePlateRecipe) {
                    VanillaRecipeHelper.addShapedRecipe(provider, name + "_pressure_plate",
                            new ItemStack(entry.pressurePlate), "PP",
                            'P', new ItemStack(entry.planks));
                }

                // slab -> pressure plate cutting
                CUTTER_RECIPES.recipeBuilder(name + "_pressure_plate")
                        .inputItems(new ItemStack(entry.slab))
                        .outputItems(entry.pressurePlate, 8)
                        .duration(250).EUt(VA[ULV]).save(provider);
            }

        }
    }

    /**
     * Standard recipes for GT woods
     */
    private static void registerGTWoodRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "treated_wood_stick",
                ChemicalHelper.get(rod, TreatedWood, ConfigHolder.INSTANCE.recipes.nerfWoodCrafting ? 2 : 4),
                "L", "L",
                'L', GTBlocks.TREATED_WOOD_PLANK.asItem());
        if (ConfigHolder.INSTANCE.recipes.nerfWoodCrafting) {
            VanillaRecipeHelper.addShapedRecipe(provider, "treated_wood_stick_saw",
                    ChemicalHelper.get(rod, TreatedWood, 4),
                    "s", "L", "L",
                    'L', GTBlocks.TREATED_WOOD_PLANK.asItem());
        }

        VanillaRecipeHelper.addShapedRecipe(provider, "rubber_wood",
                GTBlocks.RUBBER_WOOD.asStack(3),
                "LL", "LL", 'L', GTBlocks.RUBBER_LOG.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, "stripped_rubber_wood",
                GTBlocks.STRIPPED_RUBBER_WOOD.asStack(3),
                "LL", "LL", 'L', GTBlocks.STRIPPED_RUBBER_LOG.asStack());
    }

    public static void hardWoodRecipes() {
        Consumer<ResourceLocation> registry = resourceLocation -> {
            RecipeRemoval.remove(new RecipeRemoval.RemoveFilter().id(resourceLocation.toString()));
        };

        if (ConfigHolder.INSTANCE.recipes.nerfWoodCrafting) {
            registry.accept(new ResourceLocation("minecraft:stick"));
        }
        for (WoodTypeEntry entry : getDefaultEntries()) {
            hardWoodTypeRecipes(registry, entry);
        }
    }

    private static void hardWoodTypeRecipes(Consumer<ResourceLocation> registry, @NotNull WoodTypeEntry entry) {
        if (ConfigHolder.INSTANCE.recipes.nerfWoodCrafting) {
            if (entry.planksRecipeName != null) {
                registry.accept(new ResourceLocation(entry.modid, entry.planksRecipeName));
            }
        }

        if (ConfigHolder.INSTANCE.recipes.harderCharcoalRecipe) {
            if (entry.removeCharcoalRecipe) {
                registry.accept(new ResourceLocation("charcoal"));
            }
        }

        if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes) {
            if (entry.door != null) {
                // hard plank -> door crafting
                if (entry.doorRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.doorRecipeName));
                }
            }
            if (entry.slab != null) {
                if (ConfigHolder.INSTANCE.recipes.hardWoodRecipes && entry.slabRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.slabRecipeName));
                }
                if (entry.boat != null) {
                    // hard plank -> boat crafting
                    if (entry.boatRecipeName != null) {
                        registry.accept(new ResourceLocation(entry.modid, entry.boatRecipeName));
                    }
                    if (entry.chestBoatRecipeName != null) {
                        registry.accept(new ResourceLocation(entry.modid, entry.chestBoatRecipeName));
                    }
                }
            }
            if (entry.fence != null) {
                // hard plank -> fence crafting
                if (entry.fenceRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.fenceRecipeName));
                }
            }
            if (entry.fenceGate != null) {
                // hard plank -> fence gate crafting
                if (entry.fenceGateRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.fenceGateRecipeName));
                }
            }
            if (entry.trapdoor != null) {
                // hard plank -> trapdoor crafting
                if (entry.trapdoorRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.trapdoorRecipeName));
                }
            }
            if (entry.chestBoat != null) {
                if (entry.chestBoatRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.chestBoatRecipeName));
                }
            }
            if (entry.sign != null) {
                // hard plank -> sign crafting
                if (entry.signRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.signRecipeName));
                }
            }
            if (entry.hangingSign != null) {
                // hard plank -> hanging sign crafting
                if (entry.hangingSignRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.hangingSignRecipeName));
                }
            }
            if (entry.button != null) {
                // hard plank -> button crafting
                if (entry.buttonRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.buttonRecipeName));
                }
            }
            if (entry.pressurePlate != null) {
                // hard plank -> pressure plate crafting
                if (entry.pressurePlateRecipeName != null) {
                    registry.accept(new ResourceLocation(entry.modid, entry.pressurePlateRecipeName));
                }
            }
        }
    }

    private static void registerPyrolyseOvenRecipes(Consumer<FinishedRecipe> provider) {
        // Logs ================================================

        // Charcoal Byproducts
        PYROLYSE_RECIPES.recipeBuilder("log_to_charcoal_byproducts").circuitMeta(4)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(CharcoalByproducts.getFluid(4000))
                .duration(320).EUt(96)
                .save(provider);

        // Wood Tar
        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_tar").circuitMeta(9)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodTar.getFluid(1500))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_tar_nitrogen").circuitMeta(10)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodTar.getFluid(1500))
                .duration(320).EUt(96)
                .save(provider);

        // Wood Gas
        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_gas").circuitMeta(5)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodGas.getFluid(1500))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_gas_nitrogen").circuitMeta(6)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodGas.getFluid(1500))
                .duration(320).EUt(96)
                .save(provider);

        // Wood Vinegar
        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_vinegar").circuitMeta(7)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodVinegar.getFluid(3000))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("log_to_wood_vinegar_nitrogen").circuitMeta(8)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(WoodVinegar.getFluid(3000))
                .duration(320).EUt(96)
                .save(provider);

        // Creosote
        PYROLYSE_RECIPES.recipeBuilder("log_to_creosote").circuitMeta(1)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(Creosote.getFluid(4000))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("log_to_creosote_nitrogen").circuitMeta(2)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(Creosote.getFluid(4000))
                .duration(320).EUt(96)
                .save(provider);

        // Heavy Oil
        PYROLYSE_RECIPES.recipeBuilder("log_to_heavy_oil").circuitMeta(3)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .outputItems(dust, Ash, 4)
                .outputFluids(OilHeavy.getFluid(200))
                .duration(320).EUt(192)
                .save(provider);

        // Creosote
        PYROLYSE_RECIPES.recipeBuilder("coal_to_coke_creosote").circuitMeta(1)
                .inputItems(gem, Coal, 16)
                .outputItems(gem, Coke, 16)
                .outputFluids(Creosote.getFluid(8000))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coal_to_coke_creosote_nitrogen").circuitMeta(2)
                .inputItems(gem, Coal, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(gem, Coke, 16)
                .outputFluids(Creosote.getFluid(8000))
                .duration(320).EUt(96)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coal_block_to_coke_creosote").circuitMeta(1)
                .inputItems(block, Coal, 8)
                .outputItems(block, Coke, 8)
                .outputFluids(Creosote.getFluid(32000))
                .duration(2560).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coal_block_to_coke_creosote_nitrogen").circuitMeta(2)
                .inputItems(block, Coal, 8)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(block, Coke, 8)
                .outputFluids(Creosote.getFluid(32000))
                .duration(1280).EUt(96)
                .save(provider);

        // Biomass
        PYROLYSE_RECIPES.recipeBuilder("bio_chaff_to_fermented_biomass").EUt(10).duration(200)
                .inputItems(BIO_CHAFF)
                .circuitMeta(2)
                .inputFluids(Water.getFluid(1500))
                .outputFluids(FermentedBiomass.getFluid(1500))
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("bio_chaff_to_biomass").EUt(10).duration(900)
                .inputItems(BIO_CHAFF, 4)
                .circuitMeta(1)
                .inputFluids(Water.getFluid(4000))
                .outputFluids(Biomass.getFluid(5000))
                .save(provider);

        // Sugar to Charcoal
        PYROLYSE_RECIPES.recipeBuilder("sugar_to_charcoal").circuitMeta(1)
                .inputItems(dust, Sugar, 23)
                .outputItems(dust, Charcoal, 12)
                .outputFluids(Water.getFluid(1500))
                .duration(320).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("sugar_to_charcoal_nitrogen").circuitMeta(2)
                .inputItems(dust, Sugar, 23)
                .inputFluids(Nitrogen.getFluid(500))
                .outputItems(dust, Charcoal, 12)
                .outputFluids(Water.getFluid(1500))
                .duration(160).EUt(96)
                .save(provider);

        // COAL GAS ============================================

        // From Log
        PYROLYSE_RECIPES.recipeBuilder("log_to_coal_gas").circuitMeta(20)
                .inputItems(ItemTags.LOGS_THAT_BURN, 16)
                .inputFluids(Steam.getFluid(1000))
                .outputItems(Items.CHARCOAL, 20)
                .outputFluids(CoalGas.getFluid(2000))
                .duration(640).EUt(64)
                .save(provider);

        // From Coal
        PYROLYSE_RECIPES.recipeBuilder("coal_to_coal_gas").circuitMeta(22)
                .inputItems(gem, Coal, 16)
                .inputFluids(Steam.getFluid(1000))
                .outputItems(gem, Coke, 16)
                .outputFluids(CoalGas.getFluid(4000))
                .duration(320).EUt(96)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coal_block_to_coal_gas").circuitMeta(22)
                .inputItems(block, Coal, 8)
                .inputFluids(Steam.getFluid(4000))
                .outputItems(block, Coke, 8)
                .outputFluids(CoalGas.getFluid(16000))
                .duration(1280).EUt(96)
                .save(provider);

        // COAL TAR ============================================
        PYROLYSE_RECIPES.recipeBuilder("charcoal_to_coal_tar").circuitMeta(8)
                .inputItems(Items.CHARCOAL, 32)
                .chancedOutput(dust, Ash, 5000, 0)
                .outputFluids(CoalTar.getFluid(1000))
                .duration(640).EUt(64)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coal_to_coal_tar").circuitMeta(8)
                .inputItems(Items.COAL, 12)
                .chancedOutput(dust, DarkAsh, 5000, 0)
                .outputFluids(CoalTar.getFluid(3000))
                .duration(320).EUt(96)
                .save(provider);

        PYROLYSE_RECIPES.recipeBuilder("coke_to_coal_tar").circuitMeta(8)
                .inputItems(gem, Coke, 8)
                .chancedOutput(dust, Ash, 7500, 0)
                .outputFluids(CoalTar.getFluid(4000))
                .duration(320).EUt(96)
                .save(provider);
    }
}
