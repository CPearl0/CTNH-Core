package io.github.cpearl0.ctnhcore.mixin.gtnn;

import com.gregtechceu.gtceu.GTCEu;
import dev.arbor.gtnn.data.materials.FirstMaterials;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialBuilder;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialIconSet;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHPropertyKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.arbor.gtnn.data.GTNNMaterials.Thorium232;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.*;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterials.Thorium232HexafluorideSteamCracked;
import static io.github.cpearl0.ctnhcore.registry.nuclear.NuclearMaterialsInfo.fertileMaterial;

@Mixin(FirstMaterials.class)
public class FirstMaterialMixin {
    @Inject(method = "init", at = @At("TAIL"), remap = false)
    public void init(CallbackInfo ci) {

    }
}
