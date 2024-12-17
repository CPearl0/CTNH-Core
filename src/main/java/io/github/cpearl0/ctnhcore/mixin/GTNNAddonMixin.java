package io.github.cpearl0.ctnhcore.mixin;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import dev.arbor.gtnn.GTNNAddon;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GTNNAddon.class, remap = false)
public class GTNNAddonMixin {
    @Inject(method = "registerTagPrefixes", at = @At("RETURN"))
    public void CTNH$modifyTagPrefix(CallbackInfo ci) {
        TagPrefix.get("moon_stone").addSecondaryMaterial(new MaterialStack(CTNHMaterials.Moonstone, TagPrefix.dust.materialAmount()));
        TagPrefix.get("mars_stone").addSecondaryMaterial(new MaterialStack(CTNHMaterials.Marsstone, TagPrefix.dust.materialAmount()));
        TagPrefix.get("venus_stone").addSecondaryMaterial(new MaterialStack(CTNHMaterials.Venusstone, TagPrefix.dust.materialAmount()));
        TagPrefix.get("mercury_stone").addSecondaryMaterial(new MaterialStack(CTNHMaterials.Mercurystone, TagPrefix.dust.materialAmount()));
        TagPrefix.get("glacio_stone").addSecondaryMaterial(new MaterialStack(CTNHMaterials.Glaciostone, TagPrefix.dust.materialAmount()));
    }
}
