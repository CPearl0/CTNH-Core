package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GTItems.class)
public class GTItemMixin {
    @Inject(method = "cauldronInteraction", at = @At("HEAD"), remap = false, cancellable = true)
    private static <T extends Item> void cauldronInteraction(T item, CallbackInfo ci) {
        ci.cancel();
    }
}
