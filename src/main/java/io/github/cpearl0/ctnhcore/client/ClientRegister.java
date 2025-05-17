package io.github.cpearl0.ctnhcore.client;

import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.client.model.TurbineRotorModel;
import io.github.cpearl0.ctnhcore.client.renderer.TurbineRotorRender;
import io.github.cpearl0.ctnhcore.registry.CTNHBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CTNHCore.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegister {
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CTNHCore.LOGGER.info("Registering Models...");
        event.registerLayerDefinition(TurbineRotorModel.LAYER_LOCATION, TurbineRotorModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CTNHCore.LOGGER.info("Registering Renderers...");
        event.registerBlockEntityRenderer(CTNHBlockEntities.TURBINE_ROTOR.get(), TurbineRotorRender::new);
    }
}
