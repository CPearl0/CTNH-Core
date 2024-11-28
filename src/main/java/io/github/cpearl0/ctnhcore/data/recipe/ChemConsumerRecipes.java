package io.github.cpearl0.ctnhcore.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ChemConsumerRecipes {
    public static class Fuel {
        public int duration;
        public int amount;
        public Material material;
        public String name;
        public Fuel(String name, Material material, int amount, int duration) {
            this.name = name;
            this.material = material;
            this.amount = amount;
            this.duration = duration;
        }

    }
    private static ArrayList<Fuel> Fuels;
    public static void init(Consumer<FinishedRecipe> provider) {
        fuelsArrayInit();
        for(Fuel fuelType : Fuels){
            int duration = fuelType.duration;
            int amount = fuelType.amount;
            var Fuel = fuelType.material.getFluid(amount);
            CTNHRecipeTypes.CHEMICAL_GENERATOR.recipeBuilder(fuelType.name)
                    .EUt(-32)
                    .duration(duration)
                    .inputFluids(Fuel)
                    .save(provider);

        }
    }

    private static void fuelsArrayInit() {
        Fuels = new ArrayList<>();

        /// recipe name, fuel, amount, duration
        Fuels.add(new Fuel("dsa", GTMaterials.DilutedSulfuricAcid, 20, 20));
        Fuels.add(new Fuel("aa", GTMaterials.AceticAcid, 8, 30));
        Fuels.add(new Fuel("dha", GTMaterials.DilutedHydrochloricAcid, 15, 25));
        Fuels.add(new Fuel("sa", GTMaterials.SulfuricAcid, 10, 56));
        Fuels.add(new Fuel("na", GTMaterials.NitricAcid, 10, 75));
        Fuels.add(new Fuel("ha", GTMaterials.HydrochloricAcid, 8, 76));
        Fuels.add(new Fuel("merc", GTMaterials.Mercury, 12, 95));
        Fuels.add(new Fuel("pa", GTMaterials.PhosphoricAcid, 9, 52));
        Fuels.add(new Fuel("hfa", GTMaterials.HydrofluoricAcid, 8, 112));
        Fuels.add(new Fuel("pta", GTMaterials.PhthalicAcid, 6, 90));
        Fuels.add(new Fuel("fa", GTMaterials.FormicAcid, 6, 60));
    }
}
