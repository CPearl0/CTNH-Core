package io.github.cpearl0.ctnhcore;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import io.github.cpearl0.ctnhcore.data.recipe.*;
import io.github.cpearl0.ctnhcore.registry.*;
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
    public void registerElements() {
        CTNHElements.init();
    }

    @Override
    public void registerOreVeins() {
        CTNHOres.init();
    }

    @Override
    public void registerFluidVeins() {
        CTNHFluidVeins.init();
    }

    @Override
    public void registerWorldgenLayers() {
        CTNHWorldgenLayers.init();
    }

    @Override
    public void registerSounds() {
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        UnderfloorHeatingSystemRecipes.init(provider);
        AstronomicalObservatoryRecipes.init(provider);
        PersonalComputerRecipes.init(provider);
        SlaughterHouseRecipes.init(provider);
        BigDamRecipes.init(provider);
        DemonWillGeneratorRecipes.init(provider);
        KineticGeneratorRecipes.init(provider);
        ChemConsumerRecipes.init(provider);
        WaterPowerStationRecipes.init(provider);
        MeadowRecipes.init(provider);
        SinteringRecipes.init(provider);
        Sinope_recipes.init(provider);
        MachinesRecipes.init(provider);
        AcceleratorRecipes.init(provider);
        NuclearRecipes.init(provider);
        ScalableReservoirComputingRecipes.init(provider);
        TurbineRecipes.init(provider);
        HighPerformanceComputerRecipes.init(provider);
    }
}
