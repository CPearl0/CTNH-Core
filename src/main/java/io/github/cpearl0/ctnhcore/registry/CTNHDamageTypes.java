package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.api.data.damagesource.DamageTypeData;
import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

public class CTNHDamageTypes {
    public static void init(){}
    public static final DamageTypeData COMPUTATION_SACRIFICE = new DamageTypeData.Builder()
            .simpleId(CTNHCore.id("computation_sacrifice"))
            .tag(DamageTypeTags.BYPASSES_ARMOR,DamageTypeTags.BYPASSES_RESISTANCE,DamageTypeTags.BYPASSES_INVULNERABILITY)
            .build();

    public static void bootstrap(BootstapContext<DamageType> ctx) {
        DamageTypeData.allInNamespace(CTNHCore.MODID).forEach(data -> data.register(ctx));
    }
}
