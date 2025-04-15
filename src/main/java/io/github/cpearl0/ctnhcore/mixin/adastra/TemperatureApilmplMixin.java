package io.github.cpearl0.ctnhcore.mixin.adastra;

import earth.terrarium.adastra.common.systems.TemperatureApiImpl;
import io.github.cpearl0.ctnhcore.common.enchantment.VacuumSealEnchantment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TemperatureApiImpl.class)
public class TemperatureApilmplMixin {
    @Inject(method = "entityTick", at = @At("HEAD"), cancellable = true, remap = false)
    public void entityTick(ServerLevel level, LivingEntity entity, CallbackInfo ci) {
        if (VacuumSealEnchantment.hasFullEnchant(entity)) {
            ci.cancel();
        }
    }
}
