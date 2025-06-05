package io.github.cpearl0.ctnhcore.mixin.gtnn;

import dev.arbor.gtnn.worldgen.GTOreVein;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTOreVein.class)
public class GTOreVeinMixin {
    @Inject(method = "oreRemove", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void oreRemove(CallbackInfo ci) {
        ci.cancel();
    }
}
