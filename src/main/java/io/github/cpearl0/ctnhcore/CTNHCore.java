package io.github.cpearl0.ctnhcore;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.mojang.logging.LogUtils;
import io.github.cpearl0.ctnhcore.client.ClientProxy;
import io.github.cpearl0.ctnhcore.common.CommonProxy;
import io.github.cpearl0.ctnhcore.event.EventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CTNHCore.MODID)
public class CTNHCore
{
    public static final String MODID = "ctnhcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CTNHCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(MachineDefinition.class, EventHandler::registerMachines);
        modEventBus.addGenericListener(GTRecipeType.class, EventHandler::registerRecipeTypes);
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MODID, name);
    }
}
