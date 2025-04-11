package io.github.cpearl0.ctnhcore.mixin.apotheosis;

import dev.shadowsoffire.apotheosis.ench.enchantments.masterwork.EarthsBoonEnchant;
import net.minecraftforge.event.level.BlockEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EarthsBoonEnchant.class)
public class EarthsBoonEnchantMixin {
    @Inject(method = "provideBenefits", at = @At("HEAD"), cancellable = true, remap = false)
    public void provideBenefits(BlockEvent.BreakEvent e, CallbackInfo ci) {
        ci.cancel();
    }
}
