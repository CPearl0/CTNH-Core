package io.github.cpearl0.ctnhcore;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import io.github.cpearl0.ctnhcore.data.recipe.AstronomicalObservatoryRecipes;
import io.github.cpearl0.ctnhcore.data.recipe.MachinesRecipes;
import io.github.cpearl0.ctnhcore.data.recipe.UnderfloorHeatingSystemRecipes;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;
import io.github.cpearl0.ctnhcore.registry.CTNHTagPrefixes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class CTNHCoreGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return CTNHRegistration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return CTNHCore.MODID;
    }

    @Override
    public void registerTagPrefixes() {
        CTNHTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        UnderfloorHeatingSystemRecipes.init(provider);
        AstronomicalObservatoryRecipes.init(provider);
        MachinesRecipes.init(provider);
    }
}
