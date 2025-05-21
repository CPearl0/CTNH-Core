package io.github.cpearl0.ctnhcore.client;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.ModelDefinition;
import io.github.cpearl0.ctnhcore.client.renderer.TurbineRotorRender;
import io.github.cpearl0.ctnhcore.registry.CTNHBlockEntities;
import io.github.cpearl0.ctnhcore.registry.CTNHModelLayers;
import io.github.cpearl0.ctnhcore.registry.CTNHRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegister {
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CTNHCore.LOGGER.info("Registering Models...");
        CTNHModelLayers.init();
        var models = CTNHRegistration.REGISTRATE.getModels();
        for (ModelDefinition model : models) {
            event.registerLayerDefinition(model.LAYER_LOCATION, model.createBodyLayer);
        }
    }
    @Deprecated
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CTNHCore.LOGGER.info("Registering External Renderers...");
        event.registerBlockEntityRenderer(CTNHBlockEntities.TURBINE_ROTOR.get(), (ctx)->new TurbineRotorRender());
    }
}
