package io.github.cpearl0.ctnhcore.registry;

import com.enderio.base.common.init.EIOBlocks;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.common.block.GTStandingSignBlock;
import com.gregtechceu.gtceu.common.data.*;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.PhotovoltaicPowerStationMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.SlaughterHouseMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.UnderfloorHeatingMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.WindPowerArrayMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import java.awt.*;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHMultiblockMachines {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }

    public static final MultiblockMachineDefinition UNDERFLOOR_HEATING_SYSTEM = REGISTRATE.multiblock("underfloor_heating_system", UnderfloorHeatingMachine::new)
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
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, false).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, true).get()))
                            .or(Predicates.blocks(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.UNAFFECTED, true).get()))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(Create.asResource("block/copper/copper_shingles"),
                    GTCEu.id("block/multiblock/multiblock_tank"), false)
            .beforeWorking((machine, recipe) -> {
                var efficiency = ((UnderfloorHeatingMachine) machine).getEfficiency();
                machine.self().getHolder().self().getPersistentData().putDouble("efficiency", efficiency);
                return true;
            })
            .recipeModifier((machine, recipe, params, result) -> {
                if (machine instanceof UnderfloorHeatingMachine underfloorHeatingMachine) {
                    var new_recipe = recipe.copy();
                    new_recipe.inputs.put(FluidRecipeCapability.CAP, new_recipe.copyContents(new_recipe.inputs, ContentModifier.of((double) underfloorHeatingMachine.rate / 100, 0)).get(FluidRecipeCapability.CAP));
                    return new_recipe;
                }
                return recipe;
            })
            .onWorking(machine -> {
                if (machine instanceof UnderfloorHeatingMachine) {
                    var pos = machine.self().getPos();
                    var facing = machine.self().getFrontFacing();
                    double efficiency = machine.self().getHolder().self().getPersistentData().getDouble("efficiency");
                    if (machine.self().getOffsetTimer() % 20 == 0) {
                        efficiency = ((UnderfloorHeatingMachine) machine).getEfficiency();
                        machine.self().getHolder().self().getPersistentData().putDouble("efficiency", efficiency);
                    }
                    AABB range = switch (facing) {
                        case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-39, 0, -32), pos.offset(40, 16, 47)));
                        case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-40, 0, -47), pos.offset(39, 16, 32)));
                        case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-32, 0, -40), pos.offset(47, 16, 39)));
                        case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-47, 0, -39), pos.offset(32, 16, 40)));
                        default -> throw new IllegalStateException("Unexpected value: " + facing);
                    };
                    UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.put(range, efficiency * ((UnderfloorHeatingMachine) machine).rate / 100);
                }
                return true;
            })
            .afterWorking(machine -> {
                var pos = machine.self().getPos();
                var facing = machine.self().getFrontFacing();
                AABB range = switch (facing) {
                    case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-39, 0, -32), pos.offset(40, 16, 47)));
                    case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-40, 0, -47), pos.offset(39, 16, 32)));
                    case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(-32, 0, -40), pos.offset(47, 16, 39)));
                    case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-47, 0, -39), pos.offset(32, 16, 40)));
                    default -> throw new IllegalStateException("Unexpected value: " + facing);
                };
                UnderfloorHeatingSystemTempModifier.UNDERFLOOR_HEATING_SYSTEM_RANGE.remove(range);
            })
            .register();


    public static final MultiblockMachineDefinition ASTRONOMICAL_OBSERVATORY = REGISTRATE.multiblock("astronomical_observatory", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ASTRONOMICAL_OBSERVATORY)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("   BBB   ", "   BBB   ", "   CCC   ", "   BBB   ", "   BBB   ", "   RDR   ", "         ", "         ", "         ")
                    .aisle("  BBBBB  ", "  B   B  ", "  C E C  ", "  B F B  ", "  B   B  ", "  R   R  ", "   RDR   ", "         ", "         ")
                    .aisle(" BBBBBBB ", " B     B ", " C  E  C ", " B  F  B ", " B     B ", " R     R ", "  R   R  ", "   RDR   ", "         ")
                    .aisle("BBBBBBBBB", "B       B", "C   E   C", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle("BBBBBBBBB", "B       B", "CEEEEEEEC", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle("BBBBBBBBB", "B       B", "C   E   C", "B   F   B", "B       B", "R       R", " R     R ", "  R   R  ", "   RDR   ")
                    .aisle(" BBBBBBB ", " B     B ", " C  E  C ", " B  F  B ", " B     B ", " R     R ", "  R   R  ", "   RDR   ", "         ")
                    .aisle("  BBBBB  ", "  B   B  ", "  C E C  ", "  B F B  ", "  B   B  ", "  R   R  ", "   RDR   ", "         ", "         ")
                    .aisle("   BBB   ", "   B@B   ", "   CAC   ", "   BBB   ", "   BBB   ", "   RDR   ", "         ", "         ", "         ")
                    .where("A", Predicates.abilities(CTNHPartAbility.CIRCUIT))
                    .where(" ", Predicates.any())
                    .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                    .where("R", Predicates.blocks(CTNHBlocks.CASING_REFLECT_LIGHT.get()))
                    .where("C", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("D", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("E", Predicates.blocks(GTMachines.HULL[GTValues.HV].getBlock()))
                    .where("F", Predicates.blocks(Blocks.DAYLIGHT_DETECTOR))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
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
                                if (!AstronomyCircuitItem.gainData(circuit, machine.self().getLevel()))
                                    return;
                                circuitBus.getInventory().setStackInSlot(0, new ItemStack(((AstronomyCircuitItem) circuit.getItem()).getFinishedItem().get()));
                            }
                        });
            })
            .register();

    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_ENERGETIC =
            registerPhotovoltaicPowerStation("energetic", 1, CTNHBlocks.ENERGETIC_PHOTOVOLTAIC_BLOCK);
    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_PULSATING =
            registerPhotovoltaicPowerStation("pulsating", 4, CTNHBlocks.PULSATING_PHOTOVOLTAIC_BLOCK);
    public static final MultiblockMachineDefinition PHOTOVOLTAIC_POWER_STATION_VIBRANT =
            registerPhotovoltaicPowerStation("vibrant", 16, CTNHBlocks.VIBRANT_PHOTOVOLTAIC_BLOCK);

    public static MultiblockMachineDefinition registerPhotovoltaicPowerStation(String tier, int basicRate, BlockEntry<?> photovoltaicBlock) {
        return REGISTRATE.multiblock("photovoltaic_power_station_" + tier, holder -> new PhotovoltaicPowerStationMachine(holder, basicRate))
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(CTNHRecipeTypes.PHOTOVOLTAIC_POWER)
                .appearanceBlock(CTNHBlocks.CASING_REFLECT_LIGHT)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAAAAAA")
                        .aisle("ABBBBBA")
                        .aisle("ABBBBBA")
                        .aisle("ABBBBBA")
                        .aisle("ABBBBBA")
                        .aisle("ABBBBBA")
                        .aisle("AAA@AAA")
                        .where("A", Predicates.blocks(CTNHBlocks.CASING_REFLECT_LIGHT.get())
                                .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                        .where("B", Predicates.blocks(photovoltaicBlock.get()))
                        .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                        .build())
                .workableCasingRenderer(CTNHCore.id("block/casings/reflect_light_casing"),
                        GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
                .recipeModifier(PhotovoltaicPowerStationMachine::recipeModifier)
                .register();
    }

    public static final MultiblockMachineDefinition WIND_POWER_ARRAY = REGISTRATE.multiblock("wind_power_array", holder -> new WindPowerArrayMachine(holder,1))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.WIND_POWER_ARRAY)
            .appearanceBlock(CASING_STEEL_SOLID)
            .recipeModifier(WindPowerArrayMachine::WindPowerArrayRecipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA","   ","   ","   ","BBB")
                    .aisle("AAA"," A "," A "," A ","BAB")
                    .aisle("A@A","   ","   ","   ","BBB")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.Steel))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),GTCEu.id("block/multiblock/implosion_compressor"),false)
            .register();
    public static final MultiblockMachineDefinition ADVANCED_WIND_POWER_ARRAY = REGISTRATE.multiblock("advanced_wind_power_array", holder -> new WindPowerArrayMachine(holder,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.WIND_POWER_ARRAY)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .recipeModifier(WindPowerArrayMachine::WindPowerArrayRecipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA","   ","   ","   ","BBB")
                    .aisle("AAA"," A "," A "," A ","BAB")
                    .aisle("A@A","   ","   ","   ","BBB")
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.StainlessSteel))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),GTCEu.id("block/multiblock/implosion_compressor"),false)
            .register();
    public static final MultiblockMachineDefinition SUPER_WIND_POWER_ARRAY = REGISTRATE.multiblock("super_wind_power_array", holder -> new WindPowerArrayMachine(holder,3))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.WIND_POWER_ARRAY)
            .appearanceBlock(CASING_TUNGSTENSTEEL_ROBUST)
            .recipeModifier(WindPowerArrayMachine::WindPowerArrayRecipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA","   ","   ","   ","BBB")
                    .aisle("AAA"," A "," A "," A ","BAB")
                    .aisle("A@A","   ","   ","   ","BBB")
                    .where("A", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition SLAUGHTER_HOUSE = REGISTRATE.multiblock("slaughter_house", SlaughterHouseMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SLAUGHTER_HOUSE)
            .recipeModifier((machine, recipe, params, result) -> {
                if (!(machine instanceof SlaughterHouseMachine slaughterHouseMachine))
                    return recipe;
                var newRecipe = recipe.copy();
                var timeCost = slaughterHouseMachine.timeCost;
                newRecipe.duration = (int) (40 * timeCost);
                return newRecipe;
            })
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "ABBBA", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "ABBBA")
                    .aisle("BAAAB", "BDDDB", "CDDDC", "CDDDC", "CDDDC", "CDDDC", "BAAAB")
                    .aisle("BAAAB", "BD#DB", "CD#DC", "CD#DC", "CD#DC", "CD#DC", "BAEAB")
                    .aisle("BAAAB", "BDDDB", "CDDDC", "CDDDC", "CDDDC", "CDDDC", "BAAAB")
                    .aisle("AB@BA", "ABBBA", "CCCCC", "CCCCC", "CCCCC", "CCCCC", "ABBBA")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("D", Predicates.blocks(EIOBlocks.DARK_STEEL_BARS.get()))
                    .where("E", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition BIG_DAM = REGISTRATE.multiblock("big_dam", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.BIG_DAM)
            .appearanceBlock(() -> Blocks.STONE_BRICKS)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAIIIIIAAAAAAA", "AAAAAAIIIIIAAAAAAA", "AAAAAAIIIIIAAAAAA#", "AAAAAAIIIIIAAAAAA#", "AAAAAAIIIIIAAAAA##", "AAAAAAAAAAAAAAAA##", "AAAAAAAAAAAAAAAA##", "AAAAAAAAAAAAAAA###", "AAAAAAAAAAAAA#####", "AAAAAAAAAAA#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A#####HHHHH##AEEEA", "A###HH#####HH####A", "A##H#########H###A", "A#H###########H###", "A#H###########H###", "AH#############H##", "AH#####GGG#####H##", "AH#####G#G#####H##", "AH#####GGG#####H##", "AH#############H##", "A#H###########H###", "A#H###########H###", "A##H#########H####", "AAAEHH#####HH#####", "AEEE##HHHHH#######")
                    .aisle("ABBBBBBBBBBBBBBBBA", "A####CDDDDDC#AEEEA", "A###DDDDDDDDD####A", "A##DDFD###DFDD###A", "A#DDF#D###D#FDD###", "ACDF##D###D##FDC##", "ADDDDDDDDDDDDDDD##", "ADD###DGGGD###DD##", "ADD###DG#GD###DD##", "ADD###DGGGD###DD##", "ADDDDDDDDDDDDDDD##", "ACDF##D###D##FDC##", "A#DDF#D###D#FDD###", "A##DDFD###DFDD####", "AAAEDDDDDDDDD#####", "AEEE#CDDDDDC######")
                    .aisle("AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA", "AAAAAAAIIIIIAAAAAA", "AAAAAAIIIIIAAAAAAA", "AAAAAAII@IIAAAAAA#", "AAAAAAIIIIIAAAAAA#", "AAAAAAIIIIIAAAAA##", "AAAAAAAAAAAAAAAA##", "AAAAAAAAAAAAAAAA##", "AAAAAAAAAAAAAAA###", "AAAAAAAAAAAAA#####", "AAAAAAAAAAA#######")
                    .where("A", Predicates.blocks(Blocks.STONE_BRICKS))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(Blocks.SMOOTH_STONE))
                    .where("C", Predicates.blocks(TREATED_WOOD_FENCE.get()))
                    .where("D", Predicates.blocks(TREATED_WOOD_PLANK.get()))
                    .where("E", Predicates.blocks(Blocks.WATER))
                    .where("F", Predicates.blocks(TREATED_WOOD_STAIRS.get()))
                    .where("G", Predicates.blocks(AllBlocks.ANDESITE_CASING.get()))
                    .where("H", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TreatedWood).get()))
                    .where("I", Predicates.blocks(Blocks.STONE_BRICKS)
                            .or(Predicates.abilities(PartAbility.OUTPUT_KINETIC))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(new ResourceLocation("block/stone_bricks"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public final static MultiblockMachineDefinition COKE_OVEN = REGISTRATE.multiblock("coke_oven", CoilWorkableElectricMultiblockMachine::new)
                .rotationState(RotationState.ALL)
                .recipeType(GTRecipeTypes.PYROLYSE_RECIPES)
                .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers::pyrolyseOvenOverclock)
                .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
                .pattern(definition -> FactoryBlockPattern.start()
                .aisle("ABBBA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                .aisle("BDDDB", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CCHCC")
                .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                .aisle("ABBBA", "ACCCA", "AC@CA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                .where("A", Predicates.frames(GTMaterials.StainlessSteel))
                .where("B", Predicates.blocks(GCyMBlocks.HEAT_VENT.get()))
                .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                        .setMinGlobalLimited(60)
                        .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                        .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                .where("#", Predicates.any())
                .where("D", Predicates.blocks(FIREBOX_TITANIUM.get()))
                .where("E", Predicates.heatingCoils())
                .where("F", Predicates.blocks(CASING_INVAR_HEATPROOF.get()))
                .where("G", Predicates.blocks(GTBlocks.CASING_STEEL_PIPE.get()))
                .where("H", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                        .build())
         .additionalDisplay((controller, components) -> {
              if (controller.isFormed() && controller instanceof CoilWorkableElectricMultiblockMachine machine) {
              components.add(Component.translatable("gtceu.multiblock.pyrolyse_oven.speed", machine.getCoilTier() == 0 ? 75 : 50 * (machine.getCoilTier() + 15)));
              }
              })
                .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/machines/fluid_heater"))
                .register();

    public static void init() {
    }
}
