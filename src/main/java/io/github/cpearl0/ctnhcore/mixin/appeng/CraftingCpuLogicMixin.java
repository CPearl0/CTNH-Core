package io.github.cpearl0.ctnhcore.mixin.appeng;

import appeng.api.networking.energy.IEnergyService;
import appeng.crafting.execution.CraftingCpuLogic;
import appeng.crafting.execution.ElapsedTimeTracker;
import appeng.crafting.execution.ExecutingCraftingJob;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.me.service.CraftingService;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = CraftingCpuLogic.class, remap = false)
public class CraftingCpuLogicMixin {
    @Shadow
    private ExecutingCraftingJob job;
    @Shadow @Final
    CraftingCPUCluster cluster;
    @Unique
    long CTNHCore$tickCount = 0L;

    @Inject(method = "tickCraftingLogic", at = @At(value = "INVOKE", target = "Lappeng/me/cluster/implementations/CraftingCPUCluster;getCoProcessors()I"), cancellable = true)
    private void tick(IEnergyService eg, CraftingService cc, CallbackInfo ci){
        CTNHCore$tickCount = this.cluster.getLevel().getGameTime();

        long stored = 0;

        try {
            Class<?> clazz = job.getClass();
            Field field = clazz.getDeclaredField("timeTracker");
            field.setAccessible(true);
            ElapsedTimeTracker timeTracker = (ElapsedTimeTracker) field.get(job);
            stored = timeTracker.getRemainingItemCount();
        }catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        if(CTNHCore$tickCount % 20 != 0 && stored > 1000000)
        {
            ci.cancel();
        }
    }
}
