package io.github.cpearl0.ctnhcore.api;


import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.addon.AddonFinder;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.data.chemical.material.IMaterialRegistryManager;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.gregtechceu.gtceu.config.ConfigHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.github.cpearl0.ctnhcore.common.block.SpaceStructuralFramework;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.block.PhotovoltaicBlock;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPSFData;
import lombok.Generated;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

public class CTNHAPI {
    public static GTCEu instance;
    public static IMaterialRegistryManager materialManager;
    private static boolean highTier;
    private static boolean highTierInitialized;
    public static final Map<IPBData, Supplier<PhotovoltaicBlock>> PhotovoltaicBlock = new HashMap();
    public static final Map<IPSFData,Supplier<SpaceStructuralFramework>> SpaceStructuralFramework =new HashMap();

    public CTNHAPI() {
    }

    @Internal
    public static void initializeHighTier() {
        if (highTierInitialized) {
            throw new IllegalStateException("High-Tier is already initialized.");
        } else {
            highTier = ConfigHolder.INSTANCE.machines.highTierContent || AddonFinder.getAddons().stream().anyMatch(IGTAddon::requiresHighTier) || GTCEu.isDev();
            highTierInitialized = true;
            if (isHighTier()) {
                GTCEu.LOGGER.info("High-Tier is Enabled.");
            } else {
                GTCEu.LOGGER.info("High-Tier is Disabled.");
            }

        }
    }

    @Generated
    public static boolean isHighTier() {
        return highTier;
    }

    public static class RegisterEvent<K, V> extends GenericEvent<V> implements IModBusEvent {
        private final GTRegistry<K, V> registry;

        public RegisterEvent(GTRegistry<K, V> registry, Class<V> clazz) {
            super(clazz);
            this.registry = registry;
        }

        public void register(K key, V value) {
            if (this.registry != null) {
                this.registry.register(key, value);
            }

        }

        public static class RL<V> extends com.gregtechceu.gtceu.api.GTCEuAPI.RegisterEvent<ResourceLocation, V> {
            public RL(GTRegistry<ResourceLocation, V> registry, Class<V> clazz) {
                super(registry, clazz);
            }
        }

        public static class String<V> extends com.gregtechceu.gtceu.api.GTCEuAPI.RegisterEvent<java.lang.String, V> {
            public String(GTRegistry<java.lang.String, V> registry, Class<V> clazz) {
                super(registry, clazz);
            }
        }
    }
}
