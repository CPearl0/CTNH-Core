package io.github.cpearl0.ctnhcore.event;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.data.tags.BiomeTagsLoader;
import com.gregtechceu.gtceu.data.tags.DamageTagsLoader;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import io.github.cpearl0.ctnhcore.data.CTNHBlockInfo;
import io.github.cpearl0.ctnhcore.registry.*;
import io.github.cpearl0.ctnhcore.registry.sound.CTNHSoundDefinitionsProvider;
import io.github.cpearl0.ctnhcore.registry.worldgen.*;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Set;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
    public static void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        CTNHMachines.init();
        CTNHItems.init();
        CTNHBlocks.init();
        CTNHMultiblockMachines.init();
        GTMachineModify.init();
        CTNHBlockInfo.init();
        CTNHBlockEntities.init();
    }

    public static void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        CTNHRecipeTypes.init();
    }

    public static void registerDimensionMarkers(GTCEuAPI.RegisterEvent<ResourceLocation, DimensionMarker> event) {
        CTNHDimensionMarkers.init();
    }
    public static void registerChanceLogic(GTCEuAPI.RegisterEvent<ResourceLocation, ChanceLogic> event){
        CTNHChanceLogic.init();
    }

    @SubscribeEvent
    public static void registerMaterials(MaterialEvent event) {
        MaterialProperties.addBaseType(CTNHPropertyKeys.NUCLEAR);
        CTNHMaterials.init();
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        var registries = event.getLookupProvider();
        if (event.includeClient()) {
             generator.addProvider(true, new CTNHSoundDefinitionsProvider(packOutput, CTNHCore.MODID, existingFileHelper));
        }
        if (event.includeServer()) {
            var set = Set.of(CTNHCore.MODID);
            generator.addProvider(true, new BiomeTagsLoader(packOutput, registries, existingFileHelper));
                DatapackBuiltinEntriesProvider provider = generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                        packOutput, registries, new RegistrySetBuilder()
                        .add(Registries.BIOME, CTNHBiomes::bootstrap)
                        .add(Registries.CONFIGURED_FEATURE, CTNHConfiguredFeatures::bootstrap)
                        .add(Registries.PLACED_FEATURE, CTNHPlacements::bootstrap)
                        .add(Registries.DIMENSION_TYPE, CTNHDimensionTypes::bootstrap)
                        .add(Registries.LEVEL_STEM, CTNHDimensions::bootstrap)
                        .add(Registries.NOISE_SETTINGS, CTNHNoiseGenerationSettings::bootstrap)
                        .add(Registries.DENSITY_FUNCTION, CTNHDensityFunctions::bootstrap)
                        .add(Registries.DAMAGE_TYPE,CTNHDamageTypes::bootstrap),
                        set));
        }
    }
}
