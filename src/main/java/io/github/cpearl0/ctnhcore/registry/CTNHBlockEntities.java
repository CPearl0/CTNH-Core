package io.github.cpearl0.ctnhcore.registry;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.cpearl0.ctnhcore.common.blockentity.TurbineRotorBE;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHBlockEntities {
    public static void init() {

    }
    public static BlockEntityEntry<TurbineRotorBE> TURBINE_ROTOR=REGISTRATE
            .blockEntity("turbine_rotor", TurbineRotorBE::new)
            .validBlocks(CTNHBlocks.HYPER_PLASMA_TURBINE_ROTOR)
            .register();
}
