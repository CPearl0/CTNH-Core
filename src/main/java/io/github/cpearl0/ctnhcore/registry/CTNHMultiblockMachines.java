package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHMultiblockMachines {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }
/*
    public static final MultiblockMachineDefinition MY_COKE_OVEN = REGISTRATE.multiblock("bio_reactor", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.ASSEMBLER_RECIPES)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AA@AA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(35)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE)).setMinGlobalLimited(1))
                    .where("B", Predicates.blocks(CLEANROOM_GLASS.get()))
                    .where("#", Predicates.air())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_coke_bricks"),
                    GTCEu.id("block/multiblock/coke_oven"))
            .register();
*/
    public static void init() {

    }
}
