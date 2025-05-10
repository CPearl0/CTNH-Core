package io.github.cpearl0.ctnhcore.mixin.gtnn;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import dev.arbor.gtnn.api.recipe.GTNNBuilder;
import dev.arbor.gtnn.data.GTNNElement;
import dev.arbor.gtnn.data.materials.FirstMaterials;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialBuilder;
import io.github.cpearl0.ctnhcore.api.data.material.CTNHMaterialIconSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.arbor.gtnn.data.GTNNMaterials.Builder;
import static dev.arbor.gtnn.data.GTNNMaterials.*;

@Mixin(FirstMaterials.class)
public class FirstMaterialMixin {
    @Inject(method = "init", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void init(CallbackInfo ci) {
        SpaceNeutronium =
                Builder("space_neutronium").ingot().fluid().ore().dust().color(0x11111b)
                        .iconSet(MaterialIconSet.SHINY).element(GTNNElement.INSTANCE.getSpNt())
                        .blastTemp(9900, BlastProperty.GasTier.HIGH, GTValues.VA[GTValues.ZPM], 21825).buildAndRegister();

        Infinity = Builder("infinity").ingot().fluid().ore().dust().color(0xFFFFFF)
                .iconSet(MaterialIcons.InfinityIcon).element(GTNNElement.INSTANCE.getIF2())
                .flags(
                        MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_ROD,
                        MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.GENERATE_RING,
                        MaterialFlags.GENERATE_ROUND, MaterialFlags.GENERATE_GEAR,
                        MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_BOLT_SCREW,
                        MaterialFlags.GENERATE_FRAME, MaterialFlags.GENERATE_DENSE
                )
                .blastTemp(10800, BlastProperty.GasTier.HIGHEST, GTValues.VA[GTValues.UHV], 54562).buildAndRegister();

        InfinityCatalyst =
                Builder("infinity_catalyst").dust().ore().color(0xE5E2E1).iconSet(MaterialIconSet.SAND)
                        .element(GTNNElement.INSTANCE.getIF()).buildAndRegister();
        Thorium232 = new CTNHMaterialBuilder(GTCEu.id("thorium_232"))
                .nuclear(true, false)
                .color(0)
                .element(GTNNElement.INSTANCE.getTh232())
                .iconSet(CTNHMaterialIconSet.BASIC)
                .radioactiveHazard(1)
                .register();
        ci.cancel();
    }
}
