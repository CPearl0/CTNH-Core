package io.github.cpearl0.ctnhcore.mixin.legendarysurvival;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sfiomn.legendarysurvivaloverhaul.common.temperature.AltitudeModifier;
import sfiomn.legendarysurvivaloverhaul.config.Config;

@Mixin(AltitudeModifier.class)
public class AltitudeModifierMixin {
    @Inject(method = "getWorldInfluence", at = @At(value = "RETURN", ordinal = 2), remap = false, cancellable = true)
    public void getWorldInfluence(Player player, Level level, BlockPos pos, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(((pos.getY() - 64.0f) / 64.0f * ((float) Config.Baked.altitudeModifier)));
    }
}
