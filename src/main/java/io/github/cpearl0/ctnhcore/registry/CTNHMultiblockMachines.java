package io.github.cpearl0.ctnhcore.registry;

import com.enderio.base.common.init.EIOBlocks;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.common.block.FusionCasingBlock;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlock;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.block.CTNHFusionCasingBlock;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import vazkii.botania.common.block.BotaniaBlock;
import vazkii.botania.common.block.BotaniaBlocks;

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
                    .aisle("AAA","###","###","###","BBB")
                    .aisle("AAA","#A#","#A#","#A#","BAB")
                    .aisle("A@A","###","###","###","BBB")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.Steel))
                    .where("#",Predicates.air())
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
                    .aisle("AAA","###","###","###","BBB")
                    .aisle("AAA","#A#","#A#","#A#","BAB")
                    .aisle("A@A","###","###","###","BBB")
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.StainlessSteel))
                    .where("#",Predicates.air())
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
                    .aisle("AAA","###","###","###","BBB")
                    .aisle("AAA","#A#","#A#","#A#","BAB")
                    .aisle("A@A","###","###","###","BBB")
                    .where("A", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("#",Predicates.air())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition SLAUGHTER_HOUSE = REGISTRATE.multiblock("slaughter_house", SlaughterHouseMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SLAUGHTER_HOUSE)
            .recipeModifier(SlaughterHouseMachine::recipeModifier)
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
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
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
                    .build())
            .workableCasingRenderer(new ResourceLocation("block/stone_bricks"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public final static MultiblockMachineDefinition COKE_OVEN = REGISTRATE.multiblock("coke_oven", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.PYROLYSE_RECIPES)
            .tooltips(Component.translatable("ctnh.multiblock.parallelize.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.pyrolyse_oven")))
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers::multiSmelterParallel)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("BDDDB", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CCHCC")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("ABBBA", "ACCCA", "AC@CA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .where("A", Predicates.frames(GTMaterials.StainlessSteel))
                    .where("B", Predicates.blocks(GCyMBlocks.HEAT_VENT.get()))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(130)
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

    public final static MultiblockMachineDefinition BEDROCK_DRILLING_RIGS = REGISTRATE.multiblock("bedrock_drilling_rigs", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.BEDROCK_DRILLING_RIGS)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH)
            .appearanceBlock(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#######","AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .aisle("#######","A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######","A#####A", "##BCB##", "###C###", "##CCC##", "##CCC##", "##CCC##", "A#BCB#A")
                    .aisle("###E###","A##C##A", "##CCC##", "##CDC##", "##CDC##", "##CDC##", "##CDC##", "A#CCC#A")
                    .aisle("#######","A#####A", "##BCB##", "###C###", "##CCC##", "##C@C##", "##CCC##", "A#BCB#A")
                    .aisle("#######","A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######","AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .where("A", Predicates.blocks(GCyMBlocks.CASING_SECURE_MACERATION.get()))
                    .where("#", Predicates.any())
                    .where("B", Predicates.frames(GTMaterials.TungstenCarbide))
                    .where("C", Predicates.blocks(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("D", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("E", Predicates.blocks(Blocks.BEDROCK))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/tungstencu_diamond_plating_casing"),
                    GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public final static MultiblockMachineDefinition NAQ_REACTOR_MK1 = REGISTRATE.multiblock("naq_reactor_mk1", holder -> new NaqReactorMachine(holder,1))
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.NAQ_MK1)
            .generator(true)
            .recipeModifier(NaqReactorMachine::recipeModifier,true)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_BLOCK)
            .pattern(definition -> FactoryBlockPattern.start()
            .aisle("##AAA##", "##BBB##", "##BBB##", "##BBB##", "##BBB##", "##BBB##", "##AAA##")
            .aisle("#AAAAA#", "#A###A#", "#A###A#", "#A#C#A#", "#A###A#", "#A###A#", "#AAAAA#")
            .aisle("AAAAAAA", "B#####B", "B##C##B", "B#CCC#B", "B##C##B", "B#####B", "AAAAAAA")
            .aisle("AAAAAAA", "B##C##B", "B#CCC#B", "BCCCCCB", "B#CCC#B", "B##C##B", "AAAAAAA")
            .aisle("AAAAAAA", "B#####B", "B##C##B", "B#CCC#B", "B##C##B", "B#####B", "AAAAAAA")
            .aisle("#AAAAA#", "#A###A#", "#A###A#", "#A#C#A#", "#A###A#", "#A###A#", "#AAAAA#")
            .aisle("##A@A##", "##BBB##", "##BBB##", "##BBB##", "##BBB##", "##BBB##", "##AAA##")
            .where("#", Predicates.any())
            .where("A", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
            .where("B", Predicates.blocks(FUSION_GLASS.get()))
            .where("C", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE.get()))
            .where("@", Predicates.controller(Predicates.blocks(definition.get())))
            .build()
        )
            .tooltips(
            Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UEV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk1",GTValues.V[GTValues.UEV] * 8))
            .workableCasingRenderer(CTNHCore.id("block/casings/nq_casing"), GTCEu.id("block/multiblock/fusion_reactor"), false)
                            .register();

    public final static MultiblockMachineDefinition NAQ_REACTOR_MK2 = REGISTRATE.multiblock("naq_reactor_mk2", holder -> new NaqReactorMachine(holder,2))
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.NAQ_MK1)
            .generator(true)
            .recipeModifier(NaqReactorMachine::recipeModifier,true)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK)
            .pattern(definition -> FactoryBlockPattern.start()
            .aisle("###############", "###############", "######AAA######", "###############", "###############")
            .aisle("#######A#######", "######AAA######", "####AABCBAA####", "######AAA######", "#######A#######")
            .aisle("###############", "####AA###AA####", "###ADDAAADDA###", "####AA###AA####", "###############")
            .aisle("###A#######A###", "###A#######A###", "##ABAA###AABA##", "###A#######A###", "###A#######A###")
            .aisle("###############", "##A#########A##", "#ADA#######ADA#", "##A#########A##", "###############")
            .aisle("#######A#######", "##A###EFE###A##", "#ADA#AFFFA#ADA#", "##A###EFE###A##", "#######A#######")
            .aisle("######EEE######", "#A###EAGAE###A#", "ABA##FGGGF##ABA", "#A###EAGAE###A#", "######EEE######")
            .aisle("#A###AEEEA###A#", "#A###FGGGF###A#", "ACA##FGCGF##ACA", "#A###FGGGF###A#", "#A###AEEEA###A#")
            .aisle("######EEE######", "#A###EAGAE###A#", "ABA##FGGGF##ABA", "#A###EAGAE###A#", "######EEE######")
            .aisle("#######A#######", "##A###EFE###A##", "#ADA#AFFFA#ADA#", "##A###EFE###A##", "#######A#######")
            .aisle("###############", "##A#########A##", "#ADA#######ADA#", "##A#########A##", "###############")
            .aisle("###A#######A###", "###A#######A###", "##ABAA###AABA##", "###A#######A###", "###A#######A###")
            .aisle("###############", "####AA###AA####", "###ADDAAADDA###", "####AA###AA####", "###############")
            .aisle("#######A#######", "######AAA######", "####AABCBAA####", "######AAA######", "#######A#######")
            .aisle("###############", "###############", "######A@A######", "###############", "###############")
            .where("#", Predicates.any())
            .where("A", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
            .where("B", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
            .where("C", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.SamariumMagnetic).get()))
            .where("D", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE.get()))
            .where("E", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get()))
            .where("F", Predicates.blocks(FUSION_GLASS.get()))
            .where("G", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE1.get()))
            .where("@", Predicates.controller(Predicates.blocks(definition.get())))
            .build()
        )
            .tooltips(
            Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UIV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk2",GTValues.V[GTValues.UIV] * 16))
            .workableCasingRenderer(CTNHCore.id("block/casings/nq_alloy_casing"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public static final MultiblockMachineDefinition[] COMPRESSED_FUSION_REACTOR = CTNHMachines.registerTieredMultis("compressed_fusion_reactor",
            (holder, tier) -> new FusionReactorMachine(holder, tier) {

                @Override
                public long getMaxVoltage() {
                    return super.getOverclockVoltage();
                }
            }, (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("Fusion Reactor Computer MK %s".formatted(FormattingUtil.toRomanNumeral(tier - 3)))
                    .recipeType(GTRecipeTypes.FUSION_RECIPES)
                    .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, FusionReactorMachine::recipeModifier)
                    .tooltips(
                            Component.translatable("gtceu.machine.fusion_reactor.capacity",
                                    FusionReactorMachine.calculateEnergyStorageFactor(tier, 16) / 1000000L),
                            Component.translatable("gtceu.machine.fusion_reactor.overclocking"))
                    .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
                    .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))

                    .appearanceBlock(() -> CTNHFusionCasingBlock.getCasingState(tier))
                    .pattern((definition) -> {
                        TraceabilityPredicate casing = Predicates.blocks(CTNHFusionCasingBlock.getCasingState(tier));
                        return FactoryBlockPattern.start()
                                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCIBICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                                               ", "                                               ", "                    FCPPPCF                    ", "                    FCISICF                    ", "                    FCPPPCF                    ", "                                               ", "                                               ")
                                .where('S', Predicates.controller(Predicates.blocks(definition.get())))
                                .where('B', Predicates.blocks(FUSION_GLASS.get()))
                                .where('C', casing)
                                .where('P', casing.or(Predicates.abilities(PartAbility.PARALLEL_HATCH)))
                                .where('I', casing.or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(16))
                                        .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(16)))
                                .where('F', Predicates.blocks(CTNHFusionCasingBlock.getFrameState(tier)))
                                .where('H', Predicates.blocks(CTNHFusionCasingBlock.getCompressedCoilState(tier)))
                                .where('E', casing.or(Predicates.abilities(PartAbility.INPUT_ENERGY)).or(Predicates.abilities(PartAbility.INPUT_LASER).setPreviewCount(16)))
                                .where('#', Predicates.air())
                                .where(' ', Predicates.any())
                                .build();
                    })
                    .workableCasingRenderer(CTNHFusionCasingBlock.getCasingType(tier).getTexture(), GTCEu.id("block/multiblock/fusion_reactor"))
                    .register(),
            GTValues.LuV, GTValues.ZPM, GTValues.UV);

    public final static MultiblockMachineDefinition PLASMA_CONDENSER = REGISTRATE.multiblock("plasma_condenser", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.PLASMA_CONDENSER_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.plasma_condenser")))
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("       ", "   a   ", "  aaa  ", "  aaa  ", "  aaa  ", "   a   ", "       ")
                    .aisle("   a   ", "  aaa  ", " aaaaa ", " aabaa ", " aaaaa ", "  aaa  ", "   a   ")
                    .aisle("  aaa  ", " aaaaa ", "aabbbaa", "aabcbaa", "aabbbaa", " aaaaa ", "  aaa  ")
                    .aisle(" aaaaa ", "aaabaaa", "aabcbaa", "abcccba", "aabcbaa", "aaabaaa", " aaaaa ")
                    .aisle("  aaa  ", " aaaaa ", "aabbbaa", "aabcbaa", "aabbbaa", " aaaaa ", "  aaa  ")
                    .aisle("   a   ", "  aaa  ", " aaaaa ", " aabaa ", " aaaaa ", "  aaa  ", "   a   ")
                    .aisle("       ", "   a   ", "  aaa  ", "  ada  ", "  aaa  ", "   a   ", "       ")
                    .where("a", Predicates.blocks(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE.get())
                            .setMinGlobalLimited(120)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("b", Predicates.blocks(CTNHBlocks.NEUTRONIUM_PIPE_CASING.get()))
                    .where("c", Predicates.blocks(CTNHBlocks.LASER_COOLING_CASING.get()))
                    .where("d", Predicates.controller(Predicates.blocks(definition.get())))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

    public final static MultiblockMachineDefinition JIADESPACE_ELEVATOR = REGISTRATE.multiblock("space_elevator", CoilWorkableElectricMultiblockMachine::new)//这个高炉是占位符
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(CTNHRecipeTypes.SPACE_ELEVATOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.space_elevator")))
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CTNHBlocks.SPACE_ELEVATOR_MECHANICAL_CASING)
            .pattern(definition -> FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.DOWN, RelativeDirection.FRONT)
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                    .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                XXX                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                    .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                    .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                    .aisle("              FD - DF              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "             F C - C F             ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              XC - CX              ", "              XC - CX              ", "              XC - CX              ", "           FFFFDDMDDFFFF           ", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                    .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                    .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                    .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                X~X                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                    .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("X", Predicates.blocks(Blocks.OAK_PLANKS)
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("E", Predicates.blocks(Blocks.DARK_OAK_LOG))
                    .where("H", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TreatedWood)))
                    .where("F", Predicates.blocks(Blocks.STRIPPED_DARK_OAK_LOG))
                    .where("C", Predicates.blocks(Blocks.BRICKS))
                    .where("A", Predicates.blocks(Blocks.STONE))
                    .where("D", Predicates.blocks(Blocks.OAK_PLANKS))
                    .where("M", Predicates.blocks(Blocks.MAGMA_BLOCK))
                    .where("G", Predicates.blocks(Blocks.MUDDY_MANGROVE_ROOTS))
                    .where("V", Predicates.any().or(Predicates.blocks(Blocks.MUSHROOM_STEM).setPreviewCount(1)))
                    .where("-", Predicates.air())
                    .where(" ", Predicates.any())
                    .build())
            //.renderer(SpaceElevatorRenderer::new)
            .hasTESR(true)
            .register();

    public final static MultiblockMachineDefinition CHEMICAL_PLANT = REGISTRATE.multiblock("chemical_plant", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.LARGE_CHEMICAL_RECIPES)
            .tooltips(Component.translatable("ctnh.machine.eut_multiplier.tooltip", 0.6))
            .tooltips(Component.translatable("ctnh.machine.duration_multiplier.tooltip", 0.5))
            .tooltips(Component.translatable("ctnh.machine.chemical_plant.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.large_chemical_reactor")))
            .recipeModifier(CTNHRecipeModifiers::chemicalPlantOverclock)
            .appearanceBlock(GTBlocks.CASING_PTFE_INERT)
            .pattern((definition) -> FactoryBlockPattern.start()
                    .aisle("b   b", "bbbbb", "b   b", "bbbbb", "b   b")
                    .aisle("bbbbb", "bcccb", "bdddb", "bcccb", "bbbbb")
                    .aisle("b   b", "bdddb", "bcccb", "bdddb", "b   b")
                    .aisle("bbbbb", "bcccb", "bdddb", "bcccb", "bbbbb")
                    .aisle("b   b", "abbbb", "b   b", "bbbbb", "b   b")
                    .where("a", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("b", Predicates.blocks(GTBlocks.CASING_PTFE_INERT.get())
                            .setMinGlobalLimited(60)
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("c", Predicates.heatingCoils())
                    .where("d", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where(" ", Predicates.any())
                    .build())
            .additionalDisplay(CTNHMachines.CHEMICAL_PLANT_DISPLAY)
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"), GTCEu.id("block/machines/chemical_reactor"))
            .register();

    public static void init() {

    }
}
