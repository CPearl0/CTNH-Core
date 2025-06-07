package io.github.cpearl0.ctnhcore;

import appeng.api.features.GridLinkables;
import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.mojang.logging.LogUtils;
import io.github.cpearl0.ctnhcore.client.ClientProxy;
import io.github.cpearl0.ctnhcore.common.CommonProxy;
import io.github.cpearl0.ctnhcore.common.item.MEAdvancedTerminalItem;
import io.github.cpearl0.ctnhcore.event.EventHandler;
import io.github.cpearl0.ctnhcore.registry.adventure.CTNHEnchantments;
import io.github.cpearl0.ctnhcore.registry.sound.CTNHSoundEvents;
import io.github.cpearl0.ctnhcore.registry.worldgen.CTNHOverworldRegion;
import io.github.cpearl0.ctnhcore.registry.worldgen.CTNHSurfaceRuleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import static io.github.cpearl0.ctnhcore.registry.CTNHItems.ME_ADVANCED_TERMINAL;

@Mod(CTNHCore.MODID)
public class CTNHCore
{
    public static final String MODID = "ctnhcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String CUSTOM_TAG_SOURCE = "CTNH Custom Tags";

    @SuppressWarnings("removal")
    public CTNHCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(MachineDefinition.class, EventHandler::registerMachines);
        modEventBus.addGenericListener(GTRecipeType.class, EventHandler::registerRecipeTypes);
        modEventBus.addGenericListener(DimensionMarker.class, EventHandler::registerDimensionMarkers);
        modEventBus.addGenericListener(ChanceLogic.class,EventHandler::registerChanceLogic);
        modEventBus.addListener(this::commonSetup);
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        CTNHSoundEvents.SOUND_EVENTS.register(modEventBus);
        CTNHEnchantments.Enchantments.register(modEventBus);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            Regions.register(new CTNHOverworldRegion(2));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, CTNHCore.MODID, CTNHSurfaceRuleData.customSurface());
        });
        GridLinkables.register(ME_ADVANCED_TERMINAL, MEAdvancedTerminalItem.LINKABLE_HANDLER);
    }
    public static ResourceLocation id(String name) {
        return new ResourceLocation(MODID, name);
    }

}
