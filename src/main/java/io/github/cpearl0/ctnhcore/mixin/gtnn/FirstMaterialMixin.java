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
        Thorium232.getProperties().ensureSet(CTNHPropertyKeys.NUCLEAR);
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Thorium233, 100);
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Protactinium233, 1000);
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .fertileDecay.put(Uranium233, 8900);
        fertileMaterial.add(Thorium232);
        CarbideThorium232 = new CTNHMaterialBuilder(GTCEu.id("carbide_thorium_232"))
                .nuclear(false, false)
                .color(0x2c9f2c)
                .components(Thorium232, 1, Carbon, 1)
                .iconSet(CTNHMaterialIconSet.CARBIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setCarbideMaterial(CarbideThorium232);
        OxideThorium232 = new CTNHMaterialBuilder(GTCEu.id("oxide_thorium_232"))
                .nuclear(false, false)
                .color(0x2c9f2c)
                .components(Thorium232, 1, Oxygen, 2)
                .iconSet(CTNHMaterialIconSet.OXIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setOxideMaterial(OxideThorium232);
        NitrideThorium232 = new CTNHMaterialBuilder(GTCEu.id("nitride_thorium_232"))
                .nuclear(false, false)
                .color(0x2c9f2c)
                .components(Thorium232, 3, Nitrogen, 2)
                .iconSet(CTNHMaterialIconSet.NITRIDE)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setNitrideMaterial(NitrideThorium232);
        ZirconiumAlloyThorium232 = new CTNHMaterialBuilder(GTCEu.id("zirconium_alloy_thorium_232"))
                .nuclear(false, false)
                .color(0x2c9f2c)
                .components(Thorium232, 1, Zirconium, 1)
                .iconSet(CTNHMaterialIconSet.ZIRCON_ALLOY)
                .radioactiveHazard(1)
                .flags(DISABLE_DECOMPOSITION).register();
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR)
                .setZirconiumAlloyMaterial(ZirconiumAlloyThorium232);
        Thorium232Hexafluoride = new CTNHMaterialBuilder(GTCEu.id("thorium_232_hexafluoride"))
                .gas()
                .color(0x2c9f2c)
                .components(Thorium232, 1, Fluorine, 6).flags(DISABLE_DECOMPOSITION)
                .radioactiveHazard(1)
                .register();
        Thorium232HexafluorideSteamCracked = new CTNHMaterialBuilder(GTCEu.id("thorium_232_hexafluoride_steam_cracked"))
                .gas()
                .color(0x2c9f2c)
                .radioactiveHazard(1)
                .register();
        Thorium232.getProperty(CTNHPropertyKeys.NUCLEAR).setBasicMaterial(Thorium);
    }
}
