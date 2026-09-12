package io.github.cpearl0.ctnhcore.data.recipe.age;

import io.github.cpearl0.ctnhcore.CTNHCore;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import com.mo_guang.ctpp.data.recipe.builder.create.MechanicalCraftingRecipeBuilder;
import com.mo_guang.ctpp.registry.CreateMaterials;
import com.unrealdinnerbone.javd.JAVDRegistry;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.mo_guang.ctpp.registry.CTPPBlocks.VOLTAGE_TERMINALS;

public class MVRecipes {

    // 这里存放的是中压(mv)时期的配方
    public static void init(Consumer<FinishedRecipe> provider) {
        addVoltageTerminalRecipe(provider);
        // 传送门方块（动力合成：暗影钢与黑钢双层板，MV机壳）
        MechanicalCraftingRecipeBuilder.builder(CTNHCore.id("javd_portal_block"))
                .pattern("AAAAA", "ABCBA", "ACDCA", "ABCBA", "AAAAA")
                .key('A', ChemicalHelper.get(TagPrefix.plateDouble, CreateMaterials.ShadowSteel))
                .key('B', CustomTags.HV_CIRCUITS)
                .key('C', ChemicalHelper.get(TagPrefix.plateDouble, GTMaterials.BlackSteel))
                .key('D', GTBlocks.MACHINE_CASING_MV.asStack())
                .output(new ItemStack(JAVDRegistry.PORTAL_BLOCK_ITEM.get()))
                .save(provider);
        // MV电动马达（铜镍导线环绕磁化钢杆，铝板底座）
        VanillaRecipeHelper.addShapedRecipe(provider, CTNHCore.id("crafttable/electric_motor_mv"),
                GTItems.ELECTRIC_MOTOR_MV.asStack(),
                " A ", "ABA", "CAC",
                'A', ChemicalHelper.get(TagPrefix.wireGtDouble, GTMaterials.Cupronickel),
                'B', ChemicalHelper.get(TagPrefix.rod, GTMaterials.SteelMagnetic),
                'C', ChemicalHelper.get(TagPrefix.plate, GTMaterials.Aluminium));

        // MV电动马达组装机配方（与工作台同材料）
        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("assembler/electric_motor_mv"))
                .inputItems(TagPrefix.wireGtDouble, GTMaterials.Cupronickel, 4)
                .inputItems(TagPrefix.rod, GTMaterials.SteelMagnetic)
                .inputItems(TagPrefix.plate, GTMaterials.Aluminium, 2)
                .outputItems(GTItems.ELECTRIC_MOTOR_MV)
                .duration(100).EUt(VA[LV]).save(provider);
    }

    public static void addVoltageTerminalRecipe(Consumer<FinishedRecipe> provider) {
        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_ulv")).duration(100).EUt(VA[MV])
                .inputItems(plate, Iron)
                .inputItems(VOLTAGE_COIL_ULV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[ULV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_lv")).duration(100).EUt(VA[MV])
                .inputItems(plate, Iron)
                .inputItems(VOLTAGE_COIL_LV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[LV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_mv")).duration(100).EUt(VA[MV])
                .inputItems(plate, Steel)
                .inputItems(VOLTAGE_COIL_MV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[MV])
                .addMaterialInfo(true)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_hv")).duration(100).EUt(VA[HV])
                .inputItems(plate, Steel)
                .inputItems(VOLTAGE_COIL_HV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[HV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_ev")).duration(100).EUt(VA[EV])
                .inputItems(plate, Neodymium)
                .inputItems(VOLTAGE_COIL_EV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[EV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(CTNHCore.id("voltage_terminal_iv")).duration(100).EUt(VA[IV])
                .inputItems(plate, Neodymium)
                .inputItems(VOLTAGE_COIL_IV)
                .circuitMeta(1)
                .outputItems(VOLTAGE_TERMINALS[IV])
                .save(provider);
    }
}
