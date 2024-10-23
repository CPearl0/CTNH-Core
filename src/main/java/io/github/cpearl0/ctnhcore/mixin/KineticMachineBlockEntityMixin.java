package io.github.cpearl0.ctnhcore.mixin;

import com.gregtechceu.gtceu.common.blockentity.KineticMachineBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = KineticMachineBlockEntity.class, remap = false)
public class KineticMachineBlockEntityMixin {
    @ModifyConstant(method = "scheduleWorking(FZ)F", constant = @Constant(floatValue = 256f))
    public float modifySpeed(float constant) {
        return 512f;
    }
}
