package io.github.cpearl0.ctnhcore.mixin.gtceu;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.machines.GTCreateMachines;
import com.gregtechceu.gtceu.common.machine.KineticMachineDefinition;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.BiFunction;

@Mixin(GTCreateMachines.class)
public class GTCreateMachinesMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/common/data/machines/GTCreateMachines;registerTieredMachines(Ljava/lang/String;Ljava/util/function/BiFunction;Ljava/util/function/BiFunction;Ljava/util/function/BiFunction;Lcom/tterrag/registrate/util/nullness/NonNullSupplier;Z[I)[Lcom/gregtechceu/gtceu/common/machine/KineticMachineDefinition;"), index = 1, remap = false)
    private static BiFunction<Integer, ResourceLocation, KineticMachineDefinition> kinetic_input_output_box(BiFunction<Integer, ResourceLocation, KineticMachineDefinition> definitionFactory){
        return (tier, id) -> {
            return (new KineticMachineDefinition(id, definitionFactory.apply(1, null).isSource, (float) GTValues.V[tier] * 4)).setFrontRotation(true);
        };
    }

}
