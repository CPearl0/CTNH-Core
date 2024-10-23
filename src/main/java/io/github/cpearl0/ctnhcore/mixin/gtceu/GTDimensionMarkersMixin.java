package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.common.data.GTDimensionMarkers;
import io.github.cpearl0.ctnhcore.data.CTNHDimensionMarkers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTDimensionMarkers.class)
public class GTDimensionMarkersMixin {
    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci) {
        CTNHDimensionMarkers.init();
    }
}
