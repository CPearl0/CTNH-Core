package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CircuitBusPartMachine;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHMultiblockMachines {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }

    public static final MultiblockMachineDefinition UNDERFLOOR_HEATING_SYSTEM = REGISTRATE.multiblock("underfloor_heating_system", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.UNDERFLOOR_HEATING_SYSTEM)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAAAAAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAABAAAAAAA")
                    .aisle("AAAAAAAA@AAAAAAA")
                    .where("A", Predicates.blocks(AllBlocks.COPPER_SHINGLES.getStandard().get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(Create.asResource("block/copper/copper_shingles"),
                    GTCEu.id("block/multiblock/multiblock_tank"), false)
            .onWorking(machine -> {
                var pos = machine.self().getPos();
                var facing = machine.self().getFrontFacing();
                AABB range = switch (facing) {
                    case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-23, 0, -16), pos.offset(24, 10, 31)));
                    case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-24, 0, -31), pos.offset(23, 10, 16)));
                    case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-16, 0, -24), pos.offset(31, 10, 23)));
                    case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-31, 0, -23), pos.offset(16, 10, 24)));
                    default -> throw new IllegalStateException("Unexpected value: " + facing);
                };
                UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.add(range);
                return true;
            })
            .afterWorking(machine -> {
                var pos = machine.self().getPos();
                var facing = machine.self().getFrontFacing();
                AABB range = switch (facing) {
                    case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-23, 0, -16), pos.offset(24, 10, 31)));
                    case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-24, 0, -31), pos.offset(23, 10, 16)));
                    case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-16, 0, -24), pos.offset(31, 10, 23)));
                    case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-31, 0, -23), pos.offset(16, 10, 24)));
                    default -> throw new IllegalStateException("Unexpected value: " + facing);
                };
                UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.remove(range);
            })
            .register();

    public static final MultiblockMachineDefinition ASTRONOMICAL_OBSERVATORY = REGISTRATE.multiblock("astronomical_observatory", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ASTRONOMICAL_OBSERVATORY)
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA", "AAA", "AAA", "AAA", "AAA", "AAA")
                    .aisle("AAA", "A A", "A A", "A A", "A A", "AAA")
                    .aisle("AAA", "A@A", "ABA", "AAA", "AAA", "AAA")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.abilities(CTNHPartAbility.CIRCUIT))
                    .where(" ", Predicates.blocks(Blocks.AIR))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/multiblock/assembly_line"))
            .beforeWorking((machine, recipe) -> {
                final boolean[] begin = {false};
                ((MultiblockControllerMachine) machine.self()).getParts().stream()
                        .filter(part -> part instanceof CircuitBusPartMachine)
                        .findFirst()
                        .ifPresent(bus -> {
                            var circuitBus = (CircuitBusPartMachine) bus;
                            if (!circuitBus.getInventory().isEmpty()) {
                                var circuit = circuitBus.getInventory().getStackInSlot(0);
                                begin[0] = AstronomyCircuitItem.workInLevel(circuit, machine.self().getLevel());
                            }
                        });
                return begin[0];
            })
            .afterWorking(machine -> {
                ((MultiblockControllerMachine) machine.self()).getParts().stream()
                        .filter(part -> part instanceof CircuitBusPartMachine)
                        .findFirst()
                        .ifPresent(bus -> {
                            var circuitBus = (CircuitBusPartMachine) bus;
                            if (!circuitBus.getInventory().isEmpty()) {
                                var circuit = circuitBus.getInventory().getStackInSlot(0);
                                AstronomyCircuitItem.gainData(circuit, machine.self().getLevel());
                            }
                        });
            })
            .register();

    public static void init() {

    }
}
