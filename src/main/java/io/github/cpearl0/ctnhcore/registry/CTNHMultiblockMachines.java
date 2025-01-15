package io.github.cpearl0.ctnhcore.registry;

import appeng.core.definitions.AEBlocks;
import com.enderio.base.common.init.EIOBlocks;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
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
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.LargeMinerMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.MultiblockTankMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.mo_guang.ctpp.CTPPRegistration;
import com.mo_guang.ctpp.api.CTPPPartAbility;
import com.mo_guang.ctpp.common.machine.multiblock.KineticOutputMachine;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.arbor.gtnn.GTNN;
import dev.arbor.gtnn.data.GTNNCasingBlocks;
import dev.arbor.gtnn.data.GTNNItems;
import dev.arbor.gtnn.data.GTNNMachines;
import dev.arbor.gtnn.data.GTNNMaterials;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.block.CTNHFusionCasingType;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.IndustrialPrimitiveBlastFurnaceMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.MeadowMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.HEAT_VENT;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterialBlocks.MATERIAL_BLOCKS;
import static com.gregtechceu.gtceu.common.data.GTMaterialItems.MATERIAL_ITEMS;
import static com.gregtechceu.gtceu.common.data.GTMaterials.DrillingFluid;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Neutronium;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerLargeCombustionEngine;
import static io.github.cpearl0.ctnhcore.registry.CTNHBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;
import static net.minecraft.world.level.block.Blocks.*;

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
            .recipeModifier((machine, recipe) -> {
                if (machine instanceof UnderfloorHeatingMachine underfloorHeatingMachine) {
                    return ModifierFunction.builder().inputModifier(ContentModifier.multiplier((double) underfloorHeatingMachine.rate / 100)).build();
                }
                return ModifierFunction.IDENTITY;
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
            .tooltips(Component.translatable("slaughter_house").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.slaughter_house.mechanism"),
                    Component.translatable("ctnh.slaughter_house.parallel"),
                    Component.translatable("ctnh.slaughter_house.health"))
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

    public static final MultiblockMachineDefinition BIG_DAM = REGISTRATE.multiblock("big_dam", KineticOutputMachine::new)
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
                            .or(Predicates.abilities(CTPPPartAbility.OUTPUT_KINETIC))
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
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("BDDDB", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CFGFC", "CEGEC", "CCHCC")
                    .aisle("BDDDB", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "CFFFC", "CEEEC", "ACCCA")
                    .aisle("ABBBA", "ACCCA", "AC@CA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "ACCCA", "#ACA#")
                    .where("A", Predicates.frames(GTMaterials.StainlessSteel))
                    .where("B", Predicates.blocks(HEAT_VENT.get()))
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
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .appearanceBlock(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#######","AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .aisle("#######","A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######","A#####A", "##BCB##", "###C###", "##CCC##", "##CCC##", "##CCC##", "A#BCB#A")
                    .aisle("###E###","A##C##A", "##CCC##", "##CDC##", "##CDC##", "##CDC##", "##CDC##", "A#CCC#A")
                    .aisle("#######","A#####A", "##BCB##", "###C###", "##CCC##", "##C@C##", "##CCC##", "A#BCB#A")
                    .aisle("#######","A#####A", "#######", "#B###B#", "#######", "#######", "#######", "AB###BA")
                    .aisle("#######","AAAAAAA", "A#####A", "A#####A", "A#####A", "A#####A", "A#####A", "AAAAAAA")
                    .where("A", Predicates.blocks(GCYMBlocks.CASING_SECURE_MACERATION.get()))
                    .where("#", Predicates.any())
                    .where("B", Predicates.frames(GTMaterials.TungstenCarbide))
                    .where("C", Predicates.blocks(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setPreviewCount(2)))
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
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER).setPreviewCount(1)))
                    .where("B", Predicates.blocks(FUSION_GLASS.get()))
                    .where("C", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKI.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .tooltips(
                    Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UEV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk1",GTValues.V[GTValues.UEV] * 8),
                    Component.translatable("gtceu.multiblock.laser.tooltip"))
            .workableCasingRenderer(CTNHCore.id("block/casings/nq_casing"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public final static MultiblockMachineDefinition NAQ_REACTOR_MK2 = REGISTRATE.multiblock("naq_reactor_mk2", holder -> new NaqReactorMachine(holder,2))
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.NAQ_MK1)
            .generator(true)
            .recipeModifier(NaqReactorMachine::recipeModifier,true)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("               ", "               ", "       B       ", "               ", "               ")
                    .aisle("               ", "       B       ", "   BBBCDCBBB   ", "       B       ", "               ")
                    .aisle("       B       ", "   BBBCDCBBB   ", "  BBEEEDEEEBB  ", "   BBBCDCBBB   ", "       B       ")
                    .aisle("   B       B   ", "  BBB  B  BBB  ", " BBFBBCDCBBFBB ", "  BBB  B  BBB  ", "   B       B   ")
                    .aisle("               ", "  BB       BB  ", " BEBB  B  BBEB ", "  BB       BB  ", "               ")
                    .aisle("               ", "  B         B  ", " BEB       BEB ", "  B         B  ", "               ")
                    .aisle("               ", "  C         C  ", " CEC       CEC ", "  C         C  ", "               ")
                    .aisle("  B         B  ", " BDB       BDB ", "BDDDB     BDDD@", " BDB       BDB ", "  B         B  ")
                    .aisle("               ", "  C         C  ", " CEC       CEC ", "  C         C  ", "               ")
                    .aisle("               ", "  B         B  ", " BEB       BEB ", "  B         B  ", "               ")
                    .aisle("               ", "  BB       BB  ", " BEBB  B  BBEB ", "  BB       BB  ", "               ")
                    .aisle("   B       B   ", "  BBB  B  BBB  ", " BBFBBCDCBBFBB ", "  BBB  B  BBB  ", "   B       B   ")
                    .aisle("       B       ", "   BBBCDCBBB   ", "  BBEE D EEBB  ", "   BBBCDCBBB   ", "       B       ")
                    .aisle("               ", "       B       ", "   BBBCDCBBB   ", "       B       ", "               ")
                    .aisle("               ", "               ", "       B       ", "               ", "               ")
                    .where(" ", Predicates.any())
                    .where("B", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER).setPreviewCount(1)))
                    .where("C", Predicates.blocks(FUSION_GLASS.get()))
                    .where("D", Predicates.blocks(FUSION_COIL.get()))
                    .where("E", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKII.get()))
                    .where("F", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .tooltips(
                    Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UIV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk2",GTValues.V[GTValues.UIV] * 16),
                    Component.translatable("gtceu.multiblock.laser.tooltip"))

            .workableCasingRenderer(CTNHCore.id("block/casings/nq_alloy_casing"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public final static MultiblockMachineDefinition NAQ_REACTOR_MK3 = REGISTRATE.multiblock("naq_reactor_mk3", holder -> new NaqReactorMachine(holder,3))
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.NAQ_MK1)
            .generator(true)
            .recipeModifier(NaqReactorMachine::recipeModifier,true)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("        BBBBBBB        ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "        BBBBBBB        ")
                    .aisle("      BBBBBBBBBBB      ", "          DDD          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          DDD          ", "      BBBBBBBBBBB      ")
                    .aisle("    BBBBBBBBBBBBBBB    ", "        BBBBBBB        ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "        BBBBBBB        ", "    BBBBBBBBBBBBBBB    ")
                    .aisle("   BBBBBBBBBBBBBBBBB   ", "      BBBBBBBBBBB      ", "          EEE          ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "          EEE          ", "      BBBBBBBBBBB      ", "   BBBBBBBBBBBBBBBBB   ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "     BBBBBBBBBBBBB     ", "          EEE          ", "           E           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "          EEE          ", "          EEE          ", "     BBBBBBBBBBBBB     ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "    BBBBBBBBBBBBBBB    ", "        EEEEEEE        ", "           E           ", "           E           ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "          CDC          ", "         FFFFF         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "         FFFFF         ", "          CDC          ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "           E           ", "          EEE          ", "        EEEEEEE        ", "    BBBBBBBBBBBBBBB    ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBEEEBEEEBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          DDD          ", "       FF     FF       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "       FF     FF       ", "          DDD          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBEEEBEEEBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBBEBBBEBBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      FF       FF      ", "        F     F        ", "                       ", "                       ", "         FFFFF         ", "        FFFFFFF        ", "         FFFFF         ", "                       ", "                       ", "        F     F        ", "      FF       FF      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBBEBBBEBBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEBBEBBBEBBEE     ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      F         F      ", "       F       F       ", "                       ", "                       ", "        F     F        ", "       FF     FF       ", "        F     F        ", "                       ", "                       ", "       F       F       ", "      F         F      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "     EEBBEBBBEBBEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEEEEEBEEEEEE     ", "         EEEEE         ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "     F           F     ", "      F         F      ", "                       ", "                       ", "       F       F       ", "      FF   G   FF      ", "       F       F       ", "                       ", "                       ", "      F         F      ", "     F           F     ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "         EEEEE         ", "     EEEEEEBEEEEEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "CDBBBBBBBBBBBBBBBBBBBDC", " CDEEEEBBEHHHEBBEEEEDC ", "  CD  EEEEEEEEEEE  DC  ", "  CD      EEE      DC  ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     F     E     F     ", "      F         F      ", "                       ", "                       ", "       F   G   F       ", "      FF  GGG  FF      ", "       F   G   F       ", "                       ", "                       ", "      F         F      ", "     F     E     F     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "  CD      EEE      DC  ", "  CDEEEEEEEEEEEEEEEDC  ", " CDEEEEBBEHHHEBBEEEEDC ", "CDBBBBBBBBBBBBBBBBBBBDC", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "DDBBBBBBBBBBBBBBBBBBBDD", " DDEEEBBBBHIHBBBBEEEDD ", "  DDEEEEEEEIEEEEEEEDD  ", "  DDDEEEEEEIEEEEEEDDD  ", "   DDD    EIE    DDD   ", "   DDD    EIE    DDD   ", "    DDD   EIE   DDD    ", "    DDD   EIE   DDD    ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     F    EIE    F     ", "      F    E    F      ", "                       ", "           G           ", "       F  GGG  F       ", "      FF GGGGG FF      ", "       F  GGG  F       ", "           G           ", "                       ", "      F    E    F      ", "     F    EIE    F     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "     DD   EIE   DD     ", "    DDD   EIE   DDD    ", "    DDD   EIE   DDD    ", "   DDD    EIE    DDD   ", "   DDD    EIE    DDD   ", "  DDDEEEEEEIEEEEEEDDD  ", "  DDEEEEEEEIEEEEEEEDD  ", " DDEEEBBBBHIHBBBBEEEDD ", "DDBBBBBBBBBBBBBBBBBBBDD", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "CDBBBBBBBBBBBBBBBBBBBDC", " CDEEEEBBEHHHEBBEEEEDC ", "  CD  EEEEEEEEEEE  DC  ", "  CD      EEE      DC  ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     F     E     F     ", "      F         F      ", "                       ", "                       ", "       F   G   F       ", "      FF  GGG  FF      ", "       F   G   F       ", "                       ", "                       ", "      F         F      ", "     F     E     F     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "     CD   CEC   DC     ", "    CD    CEC    DC    ", "    CD    CEC    DC    ", "   CD     CEC     DC   ", "   CD     CEC     DC   ", "  CD      EEE      DC  ", "  CDEEEEEEEEEEEEEEEDC  ", " CDEEEEBBEHHHEBBEEEEDC ", "CDBBBBBBBBBBBBBBBBBBBDC", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEEEEEBEEEEEE     ", "         EEEEE         ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "     F           F     ", "      F         F      ", "                       ", "                       ", "       F       F       ", "      FF   G   FF      ", "       F       F       ", "                       ", "                       ", "      F         F      ", "     F           F     ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "         EEEEE         ", "     EEEEEEBEEEEEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle("BBBBBBBBBBBBBBBBBBBBBBB", "  BBBBBBBBBBBBBBBBBBB  ", "     EEBBEBBBEBBEE     ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      F         F      ", "       F       F       ", "                       ", "                       ", "        F     F        ", "       FF     FF       ", "        F     F        ", "                       ", "                       ", "       F       F       ", "      F         F      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "     EEBBEBBBEBBEE     ", "  BBBBBBBBBBBBBBBBBBB  ", "BBBBBBBBBBBBBBBBBBBBBBB")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBBEBBBEBBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "      FF       FF      ", "        F     F        ", "                       ", "                       ", "         FFFFF         ", "        FFFFFFF        ", "         FFFFF         ", "                       ", "                       ", "        F     F        ", "      FF       FF      ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBBEBBBEBBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle(" BBBBBBBBBBBBBBBBBBBBB ", "   BBBBBBBBBBBBBBBBB   ", "      BBEEEBEEEBB      ", "          EEE          ", "           E           ", "                       ", "                       ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          DDD          ", "       FF     FF       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "                       ", "                       ", "                       ", "         FFFFF         ", "       FF     FF       ", "          DDD          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "                       ", "                       ", "           E           ", "          EEE          ", "      BBEEEBEEEBB      ", "   BBBBBBBBBBBBBBBBB   ", " BBBBBBBBBBBBBBBBBBBBB ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "    BBBBBBBBBBBBBBB    ", "        EEEEEEE        ", "          EEE          ", "           E           ", "           D           ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "          CDC          ", "         FFFFF         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "         FFFFF         ", "          CDC          ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "           D           ", "           E           ", "          EEE          ", "        EEEEEEE        ", "    BBBBBBBBBBBBBBB    ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("  BBBBBBBBBBBBBBBBBBB  ", "     BBBBBBBBBBBBB     ", "          EEE          ", "          EEE          ", "           D           ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "           D           ", "          EEE          ", "          EEE          ", "     BBBBBBBBBBBBB     ", "  BBBBBBBBBBBBBBBBBBB  ")
                    .aisle("   BBBBBBBBBBBBBBBBB   ", "      BBBBBBBBBBB      ", "          EEE          ", "          DDD          ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "          DDD          ", "          EEE          ", "      BBBBBBBBBBB      ", "   BBBBBBBBBBBBBBBBB   ")
                    .aisle("    BBBBBBBBBBBBBBB    ", "        BBBBBBB        ", "          DDD          ", "          CDC          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          CDC          ", "          DDD          ", "        BBBBBBB        ", "    BBBBBBBBBBBBBBB    ")
                    .aisle("      BBBBBBBBBBB      ", "          DDD          ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "          DDD          ", "      BBBBBBBBBBB      ")
                    .aisle("        BBB@BBB        ", "          CDC          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          CDC          ", "        BBBBBBB        ")
                    .where(" ", Predicates.any())
                    .where("B", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_ALLOY_BLOCK.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER).setPreviewCount(1)))
                    .where("C", Predicates.frames(GTMaterials.Naquadria))
                    .where("D", Predicates.blocks(CTNHBlocks.DEPTH_FORCE_FIELD_STABILIZING_CASING.get()))
                    .where("E", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get()))
                    .where("F", Predicates.blocks(CTNHBlocks.PLASMA_COOLED_CORE.get()))
                    .where("G", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKIII.get()))
                    .where("H", Predicates.blocks(CTNHBlocks.CASING_HYPER.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("I", Predicates.blocks(CTNHBlocks.CASING_ADVANCED_HYPER.get()))
                    .build()
            )
            .tooltips(
                    Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UXV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk3",GTValues.V[GTValues.UXV] * 24),
                    Component.translatable("gtceu.multiblock.laser.tooltip"))

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

                    .appearanceBlock(() -> CTNHFusionCasingType.getCasingState(tier))
                    .pattern((definition) -> {
                        TraceabilityPredicate casing = Predicates.blocks(CTNHFusionCasingType.getCasingState(tier));
                        return FactoryBlockPattern.start()
                                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCIBICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                    FCCCCCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCCCCCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCCCCCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCCCCCF                    ")
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
                                .aisle("                    FCCCCCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCCCCCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCCCCCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCCCCCF                    ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                                               ", "                                               ", "                    FCPPPCF                    ", "                    FCISICF                    ", "                    FCPPPCF                    ", "                                               ", "                                               ")
                                .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                                .where("B", Predicates.blocks(FUSION_GLASS.get()))
                                .where("C", casing)
                                .where("P", casing.or(Predicates.abilities(PartAbility.PARALLEL_HATCH)))
                                .where("I", casing.or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(16))
                                        .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(16)))
                                .where("F", Predicates.blocks(CTNHFusionCasingType.getFrameState(tier)))
                                .where("H", Predicates.blocks(CTNHFusionCasingType.getCompressedCoilState(tier)))
                                .where("E", casing.or(Predicates.abilities(PartAbility.INPUT_ENERGY)).or(Predicates.abilities(PartAbility.INPUT_LASER).setPreviewCount(16)))
                                .where("#", Predicates.any())
                                .where(" ", Predicates.any())
                                .build();//结构相关代码取自GTL
                    })
                    .workableCasingRenderer(CTNHFusionCasingType.getCasingType(tier).getTexture(), GTCEu.id("block/multiblock/fusion_reactor"))
                    .register(),
            GTValues.LuV, GTValues.ZPM, GTValues.UV);
    public final static MultiblockMachineDefinition SWEATSHOP = REGISTRATE.multiblock("sweat_shop", FactoryMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CENTRIFUGE_RECIPES,GTRecipeTypes.LATHE_RECIPES,GTRecipeTypes.BENDER_RECIPES,
                    GTRecipeTypes.MACERATOR_RECIPES,GTRecipeTypes.MIXER_RECIPES,GTRecipeTypes.EXTRACTOR_RECIPES,
                    GTRecipeTypes.WIREMILL_RECIPES,GTRecipeTypes.LASER_ENGRAVER_RECIPES,GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES)
            .recipeModifiers(FactoryMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.1").withStyle(ChatFormatting.GRAY))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.2"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.3"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.4"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.5"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.6"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.7"))
            .tooltips(Component.translatable("ctnh.sweat_shop.tooltips.8"))
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("aaaaa", "aaaaa", "aaaaa", "aaaaa")
                    .aisle("ccccc", "a b a", "e   e", "ccccc").setRepeatable(3,16)
                    .aisle("aaaaa", "aadaa", "aaaaa", "aaaaa")
                    .where("a", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("b", Predicates.blocks(AllBlocks.ANDESITE_CASING.get()))
                    .where("c", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("d", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("e", Predicates.blocks(Blocks.IRON_BARS))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

    public  final  static  MultiblockMachineDefinition PLASMA_CONDENSER = REGISTRATE.multiblock("plasma_condenser", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.PLASMA_CONDENSER_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.plasma_condenser.tooltips.1").withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.multiblock.laser.tooltip"),
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .appearanceBlock(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####AAA#####", "#####AAA#####", "#####AAA#####")
                    .aisle("####AAAAA####", "####BCCCB####", "####AAAAA####")
                    .aisle("##AAAAAAAAA##", "##AACABACAA##", "##AAAAAAAAA##")
                    .aisle("##AAA###AAA##", "##ACA###ACA##", "##AAA###AAA##")
                    .aisle("#AAA#####AAA#", "#BCA#####ACB#", "#AAA#####AAA#")
                    .aisle("AAA#######AAA", "ACA#######ACA", "AAA#######AAA")
                    .aisle("AAA#######AAA", "ACB#######BCA", "AAA#######AAA")
                    .aisle("AAA#######AAA", "ACA#######ACA", "AAA#######AAA")
                    .aisle("#AAA#####AAA#", "#BCA#####ACB#", "#AAA#####AAA#")
                    .aisle("##AAA###AAA##", "##ACA###ACA##", "##AAA###AAA##")
                    .aisle("##AAAAAAAAA##", "##AACABACAA##", "##AAAAAAAAA##")
                    .aisle("####AAAAA####", "####BCCCB####", "####AAAAA####")
                    .aisle("#####AAA#####", "#####A@A#####", "#####AAA#####")
                    .where("A", Predicates.blocks(CTNHBlocks.CASING_ANTIFREEZE_HEATPROOF_MACHINE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY)))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(FUSION_GLASS.get()))
                    .where("C", Predicates.blocks(CTNHBlocks.PLASMA_COOLED_CORE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/antifreeze_heatproof_machine_casing"),
                    GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public final static MultiblockMachineDefinition DEMON_WILL_GENERATOR = REGISTRATE.multiblock("demon_will_generator", DemonWillMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(CTNHRecipeTypes.DEMON_WILL_GENERATOR_RECIPE)
            .generator(true)
            .recipeModifiers(DemonWillMachine::recipeModifier)
            .tooltips(Component.translatable("ctnh.demon_will_generator.tooltips.1").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.demon_will_generator.tooltips.2"),
                    Component.translatable("ctnh.demon_will_generator.tooltips.3"),
                    Component.translatable("ctnh.demon_will_generator.tooltips.4"),
                    Component.translatable("ctnh.demon_will_generator.tooltips.5"),
                    Component.translatable("ctnh.demon_will_generator.tooltips.6"),
                    Component.translatable("ctnh.demon_will_generator.tooltips.7"))
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      D                   D      ", "      C                   C      ", "      E                   E      ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      F                   F      ", "      C                   C      ", "      I       JJJJJ       I      ", "  D  BKB  D           D  BKB  D  ", "  C  GKG  C           C  GKG  C  ", " CE B K B EC         CE B K B EC ", "BCE F K F ECB       BCE F K F ECB", "  C B K B C           C B K B C  ", "  D  GKG  D           D  GKG  D  ", "     BKB                 BKB     ", "      I                   Z      ", "      C                   C      ", "      F                   F      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             LBLBLBL             ", "           JJJNNNNNJJJ           ", "      B       JLJLJ       B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "          BNNN     NNNB          ", "           JLJ     JLJ           ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               BFB               ", "              E H E              ", "                                 ", "                                 ", "              FBHBF              ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "              EEEEE              ", "                                 ", "        B               B        ", "      D                   D      ", "      C       NNNNN       C      ", "     CEC                 CEC     ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                H                ", "             OO   OO             ", "           FE       EF           ", "           E         E           ", "       JN               NJ       ", "        J               J        ", "             N     N             ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               OOO               ", "            O       O            ", "           E         E           ", "                                 ", "       JN               NJ       ", "        L               L        ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               M M               ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "               O O               ", "             O     O             ", "           O         O           ", "                                 ", "       P                 L       ", "       JN               NJ       ", "        J               J        ", "           N         N           ", "                                 ", "                K                ", "                I                ", "                F                ", "                F                ", "                I                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("              MMMMM              ", "              M   M              ", "              M   M              ", "              M   M              ", "              MMMMM              ", "              M M M              ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "               QMQ               ", "              MR RM              ", "              I   I              ", "              F   F              ", "              F   F              ", "              I   I              ", "              O S O              ", "                S                ", "           O    S    O           ", "          E     S     E          ", "       B E      S      E B       ", "      JN                 NJ      ", "       J                 J       ", "         FN           NF         ", "                                 ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("             MMMMMMM             ", "               OOO               ", "               OOO               ", "               OOO               ", "              MOOOM              ", "               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                T                ", "                T                ", "                T                ", "                T                ", "                T                ", "                U                ", "                G                ", "                G                ", "                G                ", "                G                ", "               E E               ", "               F F               ", "              QF FQ              ", "             MR   RM             ", "             K     K             ", "             G  S  G             ", "             G  S  G             ", "             K  S  K             ", "             O SVS O             ", "            O  SVS  O            ", "               SVS               ", "         BE    SVS    EB         ", "       P       SVS       L       ", "      JN        S        NJ      ", "       L                 L       ", "         BN           NB         ", "                W                ", "              K   K              ", "              G   G              ", "              G   G              ", "              K   K              ", "               W W               ", "               X X               ", "               X X               ", "               W W               ", "                U                ", "                Y                ", "                Y                ", "                U                ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("            MMMMMMMMM            ", "               OOO               ", "               OOO               ", "               OOO               ", "              MOOOM              ", "             MMMMMMM             ", "              M F M              ", "                                 ", "                F                ", "                C                ", "                C                ", "               TUT               ", "               TUT               ", "               TUT               ", "               TUT               ", "               TUT               ", "               UUU               ", "               GFG               ", "               GFG               ", "               GFG               ", "               GFG               ", "                I                ", "              M   M              ", "              M   M              ", "                                 ", "                S                ", "               SVS               ", "               SVS               ", "               SVS               ", "              SVVVS              ", "           HO SVVVS OH           ", "              SVVVS              ", "         FE   SVVVS   EF         ", "       B H     SVS     H B       ", "      JN       SVS       NJ      ", "       J        S        J       ", "         HN     W     NH         ", "               WWW               ", "             K     K             ", "             I     I             ", "             F     F             ", "             F     F             ", "             I     I             ", "                                 ", "                                 ", "                                 ", "               UUU               ", "               YFY               ", "               YFY               ", "               UUU               ", "                U                ", "                C                ", "                C                ", "                F                ")
                    .aisle("             MMMMMMM             ", "               OOO               ", "               O@O               ", "               OOO               ", "              MOOOM              ", "               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                T                ", "                T                ", "                T                ", "                T                ", "                T                ", "                U                ", "                G                ", "                G                ", "                G                ", "                G                ", "               E E               ", "               F F               ", "              QF FQ              ", "             MR   RM             ", "             K     K             ", "             G  S  G             ", "             G  S  G             ", "             K SVS K             ", "             O SVS O             ", "            O  SVS  O            ", "               SVS               ", "         BE    SVS    EB         ", "       P        S        L       ", "      JN        S        NJ      ", "       L                 L       ", "         BN           NB         ", "                W                ", "              K   K              ", "              G   G              ", "              G   G              ", "              K   K              ", "               W W               ", "               X X               ", "               X X               ", "               W W               ", "                U                ", "                Y                ", "                Y                ", "                U                ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("              MMMMM              ", "              M   M              ", "              M   M              ", "              M   M              ", "              MMMMM              ", "              M M M              ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "               QMQ               ", "              MR RM              ", "              I   I              ", "              F   F              ", "              F   F              ", "              I S I              ", "              O S O              ", "                S                ", "           O    S    O           ", "          E     S     E          ", "       B E             E B       ", "      JN                 NJ      ", "       J                 J       ", "         FN           NF         ", "                                 ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               M M               ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "               O O               ", "             O     O             ", "           O         O           ", "                                 ", "       P                 L       ", "       JN               NJ       ", "        J               J        ", "           N         N           ", "                                 ", "                K                ", "                I                ", "                F                ", "                F                ", "                I                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               OOO               ", "            O       O            ", "           E         E           ", "                                 ", "       JN               NJ       ", "        L               L        ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                H                ", "             OO   OO             ", "           FE       EF           ", "           E           E         ", "       JN               NJ       ", "        J               J        ", "             N     N             ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "              EEEEE              ", "                                 ", "        B               B        ", "      D                   D      ", "      C       NNNNN       C      ", "      E                   E      ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               BFB               ", "              E H E              ", "                                 ", "                                 ", "              FBHBF              ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "          BNNN     NNNB          ", "           JLJ     JLJ           ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             LBLBLBL             ", "           JJJNNNNNJJJ           ", "      B       JLJLJ       B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      F                   F      ", "      C                   C      ", "      I       JJJJJ       I      ", "  D  BKB  D           D  BKB  D  ", "  C  GKG  C           C  GKG  C  ", " CE B K B EC         CE B K B EC ", "BCE F K F ECB       BCE F K F ECB", "  C B K B C           C B K B C  ", "  D  GKG  D           D  GKG  D  ", "     BKB                 BKB     ", "      Z                   I      ", "      C                   C      ", "      F                   F      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      D                   D      ", "      C                   C      ", "     CEC                 CEC     ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                    .where("A", Predicates.blocks(Blocks.WHITE_CONCRETE))
                    .where(" ", Predicates.any())
                    .where("B", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_STAIRS.get()))
                    .where("C", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_WALL.get()))
                    .where("D", Predicates.blocks(Blocks.NETHER_BRICK_FENCE))
                    .where("E", Predicates.blocks(Blocks.BLACK_CONCRETE))
                    .where("F", Predicates.blocks(Blocks.IRON_BARS))
                    .where("G", Predicates.blocks(Blocks.RED_STAINED_GLASS))
                    .where("H", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_SLAB.get()))
                    .where("I", Predicates.blocks(LAMPS.get(DyeColor.RED).get()))
                    .where("J", Predicates.blocks(Blocks.POLISHED_BLACKSTONE_SLAB))
                    .where("K", Predicates.blocks(Blocks.RED_CONCRETE))
                    .where("L", Predicates.blocks(Blocks.POLISHED_BLACKSTONE_STAIRS))
                    .where("M", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_1.get()))
                    .where("N", Predicates.blocks(BloodMagicBlocks.BLANK_RUNE.get())
                            .or(Predicates.blocks(BloodMagicBlocks.SPEED_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.SPEED_RUNE_2.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.SACRIFICE_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.SACRIFICE_RUNE_2.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.SELF_SACRIFICE_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.SELF_SACRIFICE_RUNE_2.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.CAPACITY_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.CAPACITY_RUNE_2.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE_2.get())))
                    .where("O", Predicates.blocks(BloodMagicBlocks.OBSIDIAN_TILE_PATH.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("P", Predicates.blocks(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS))
                    .where("Q", Predicates.blocks(Blocks.RED_STAINED_GLASS_PANE))
                    .where("R", Predicates.blocks(Blocks.BLUE_STAINED_GLASS_PANE))
                    .where("S", Predicates.blocks(BotaniaBlocks.bifrostPerm))
                    .where("T", Predicates.blocks(Blocks.ORANGE_STAINED_GLASS_PANE))
                    .where("U", Predicates.blocks(Blocks.GRAY_CONCRETE))
                    .where("V", Predicates.blocks(AEBlocks.QUARTZ_VIBRANT_GLASS.block()))
                    .where("W", Predicates.blocks(Blocks.PURPLE_CONCRETE))
                    .where("X", Predicates.blocks(Blocks.PURPLE_STAINED_GLASS))
                    .where("Y", Predicates.blocks(Blocks.GRAY_STAINED_GLASS))
                    .where("Z", Predicates.blocks(BloodMagicBlocks.HELLFORGED_BLOCK.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(BloodMagic.rl("block/obsidiantilepath"), GTCEu.id("block/multiblock/vacuum_freezer"))
            .register();

    public final static MultiblockMachineDefinition MEADOW = REGISTRATE.multiblock("meadow", MeadowMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MEADOW)
            .recipeModifier(MeadowMachine::recipmeModifier)
            .tooltips(Component.translatable("meadow").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.meadow.basic"),
                    Component.translatable("ctnh.meadow.mechanism"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBBBBBBBBBB", "JCCCJCCCCCC", "J###J######", "JJJJJD#####", "EEEEE######", "###########")
                    .aisle("BBBBFFFBBBB", "CEE####GG#C", "#E#####GG##", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBFFFBBBB", "CE#####GG#C", "###########", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBFFFBBBB", "C#######G#C", "###########", "J###JD#####", "EEEEE######", "#EEE#######")
                    .aisle("BBBBBFFBBBB", "J###J#####C", "J###J######", "JJJJJD#####", "EEEEE######", "###########")
                    .aisle("BEEBFFFHHHB", "C#########C", "###########", "DDDDDD#####", "###########", "###########")
                    .aisle("BEEBFFFHHHB", "C######II#C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFHHHB", "C#######I#C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFBHHB", "C#########C", "###########", "###########", "###########", "###########")
                    .aisle("BEEBFFFBHHB", "C########IC", "###########", "###########", "###########", "###########")
                    .aisle("BBBBB@BBBBB", "CCCCCCCCCCC", "###########", "###########", "###########", "###########")
                    .where("B", Predicates.blocks(Blocks.DIRT)
                            .or(Predicates.blocks(Blocks.GRASS_BLOCK))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(CTPPPartAbility.INPUT_KINETIC)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(Blocks.OAK_FENCE))
                    .where("#", Predicates.any())
                    .where("D", Predicates.blocks(Blocks.OAK_STAIRS))
                    .where("E", Predicates.blocks(Blocks.HAY_BLOCK))
                    .where("F", Predicates.blocks(Blocks.DIRT_PATH))
                    .where("G", Predicates.blocks(Blocks.BONE_BLOCK))
                    .where("H", Predicates.blocks(Blocks.WATER))
                    .where("I", Predicates.blocks(Blocks.LILY_PAD))
                    .where("J",Predicates.blocks(Blocks.OAK_LOG))
                    .build()
            )
            .workableCasingRenderer(new ResourceLocation("block/dirt"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public final static MultiblockMachineDefinition LARGE_BOTTLE = REGISTRATE.multiblock("large_bottle",holder -> new MultiblockTankMachine(holder,10000*1000,null))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .tooltips(Component.translatable("large_bottle").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.large_bottle.basic"),
                    Component.translatable("ctnh.large_bottle.consume"))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##AAAAA##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "##CCCCC##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "#########", "#########", "#########", "#########", "#########", "#########", "#########")
                    .aisle("#AAAAAAA#", "#B#####B#", "#B#####B#", "#BAAAAAB#", "#C#####C#", "#B#####B#", "#B#####B#", "#B#####B#", "#BBBBBBB#", "###BBB###", "#########", "#########", "#########", "#########", "#########")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "##BBBBB##", "###CCC###", "###BBB###", "###BBB###", "###BBB###", "###AAA###")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "#BB###BB#", "##CDDDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B###E###B", "B###E###B", "BAAAEAAAB", "C###E###C", "B###E###B", "B###E###B", "B###E###B", "#B##E##B#", "#BB#E#BB#", "##CDEDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "#BB###BB#", "##CDDDC##", "##B###B##", "##B###B##", "##B###B##", "##AEEEA##")
                    .aisle("AAAAAAAAA", "B#######B", "B#######B", "BAAAAAAAB", "C#######C", "B#######B", "B#######B", "B#######B", "#B#####B#", "##BBBBB##", "###CCC###", "###BBB###", "###BBB###", "###BBB###", "###AAA###")
                    .aisle("#AAAAAAA#", "#B#####B#", "#B#####B#", "#BAAAAAB#", "#C#####C#", "#B#####B#", "#B#####B#", "#B#####B#", "#BBBBBBB#", "###BBB###", "#########", "#########", "#########", "#########", "#########")
                    .aisle("##AA@AA##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "##CCCCC##", "##BBBBB##", "##BBBBB##", "##BBBBB##", "#########", "#########", "#########", "#########", "#########", "#########", "#########")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.heatingCoils())
                    .where("D", Predicates.blocks(FILTER_CASING.get()))
                    .where("E", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public final static MultiblockMachineDefinition FERMENTING_TANK = REGISTRATE.multiblock("fermenting_tank",FermentingTankMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FERMENTING)
            .tooltips(Component.translatable("fermenting_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"),
                    Component.translatable("subtick_overclock").withStyle(ChatFormatting.YELLOW),
                    Component.literal("=========================================================="),
                    Component.translatable("ctnh.fermenting_tank.bio_growth_mechanism").withStyle(ChatFormatting.GREEN),
                    Component.translatable("ctnh.fermenting_tank.bio_growth_temperature"),
                    Component.translatable("ctnh.fermenting_tank.bio_growth"))
            .recipeModifiers(FermentingTankMachine::recipeModifier,GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("C   C", "C   C", "CCCCC", "H   H", "H   H", "H   H", "DAAAD")
                    .aisle("     ", " GGG ", "CGGGC", " MMM ", " GGG ", " GGG ", "AAAAA")
                    .aisle("     ", " GGG ", "CG GC", " M M ", " G G ", " G G ", "AABAA")
                    .aisle("     ", " GGG ", "CGGGC", " MMM ", " GGG ", " GGG ", "AAAAA")
                    .aisle("C   C", "CAKAC", "CAAAC", "H   H", "H   H", "H   H", "DAAAD")
                    .where("C", Predicates.frames(GTMaterials.Steel))
                    .where("H", Predicates.blocks(AllBlocks.METAL_GIRDER.get()))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("D", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("G", Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where(" ", Predicates.air())
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public final static MultiblockMachineDefinition LARGE_FERMENTING_TANK = REGISTRATE.multiblock("large_fermenting_tank", LargeFermentingTankMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FERMENTING)
            .tooltips(Component.translatable("fermenting_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"),
                    Component.translatable("subtick_overclock").withStyle(ChatFormatting.YELLOW),
                    Component.literal("=========================================================="),
                    Component.translatable("ctnh.fermenting_tank.bio_growth_mechanism").withStyle(ChatFormatting.GREEN),
                    Component.translatable("ctnh.fermenting_tank.bio_growth_temperature"),
                    Component.translatable("ctnh.fermenting_tank.bio_growth"),
                    Component.translatable("ctnh.large_fermenting_tank.bio_growth"))
            .recipeModifier((machine,recipe) -> FermentingTankMachine.recipeModifier(machine,recipe).andThen(CTNHRecipeModifiers.accurateParallel(machine,recipe,8)))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##########AAAAAAAAAAA", "##########ABBBBBBBBBA", "##########ABBBBBBBBBA", "##########AAAAAAAAAAA", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############AAAAAAA##", "############AABBBAA##", "############AABBBAA##", "############AABBBAA##", "############AAAAAAA##")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "############CCCCCCC##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############CCCCCCC##", "###########DABBBBBAD#", "###########DB#####BD#", "###########DB#####BD#", "###########DB#####BD#", "###########DAAAAAAAD#")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CCDAAADCC#", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "###########CCDAAADCC#", "##########AAABBBBBAAA", "##########ABB#####BBA", "##########ABB#####BBA", "##########ABB#####BBA", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CDAAAAADC#", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "############DFFFFFD##", "############DFFFFFD##", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "###########CDAAAAADC#", "##########ABBBBBBBBBA", "AAAAA#####A#########A", "ABBBA#####A#########A", "AAAAA#####A#########A", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B###B###", "#############B###B###", "#############B###B###", "#############F###F###", "#############F###F###", "#############B###B###", "#############B###B###", "#############B###B###", "###########CAAFFFAAC#", "##########ABBBBBBBBBA", "AHHHAAAAAAA#########B", "B###BBBBBBB#########B", "AAAAAAAAAAA#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B#H#B###", "#############B#H#B###", "#############B#H#B###", "#############F#H#F###", "#############F#H#F###", "#############B#H#B###", "#############B#H#B###", "#############B#H#B###", "###########CAAFIFAAC#", "##########ABBBBBBBBBA", "AHHHBBBBBBB#########B", "B###################B", "AAAAABBBBBB#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CAAFFFAAC#", "#############B###B###", "#############B###B###", "#############B###B###", "#############F###F###", "#############F###F###", "#############B###B###", "#############B###B###", "#############B###B###", "###########CAAFFFAAC#", "##########ABBBBBBBBBA", "AHHHAAAAAAA#########B", "B###BBBBBBB#########B", "AAAAAAAAAAA#########B", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CDAAAAADC#", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "############DFFFFFD##", "############DFFFFFD##", "############DBBBBBD##", "############DBBBBBD##", "############DBBBBBD##", "###########CDAAAAADC#", "##########ABBBBBBBBBA", "AAAAA#####A#########A", "ABBBA#####A#########A", "AAAAA#####A#########A", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "###########CCDAAADCC#", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "#############D###D###", "###########CCDAAADCC#", "##########AAABBBBBAAA", "##########ABB#####BBA", "##########ABB#####BBA", "##########ABB#####BBA", "##########AAAAAAAAAAA")
                    .aisle("##########AAAAAAAAAAA", "##########B#########B", "##########B#########B", "##########AAAAAAAAAAA", "############CCCCCCC##", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############CCCCCCC##", "###########DABBBBBAD#", "###########DB#####BD#", "###########DB#####BD#", "###########DB#####BD#", "###########DAAAAAAAD#")
                    .aisle("##########AJJJJ@JJJJA", "##########AJJJJJJJJJA", "##########AJJJJJJJJJA", "##########AJJJJJJJJJA", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "#####################", "############AAAAAAA##", "############AABBBAA##", "############AABBBAA##", "############AABBBAA##", "############AAAAAAA##")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("B", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.blocks(Blocks.SMOOTH_STONE_SLAB))
                    .where("D", Predicates.frames(GTMaterials.Invar))
                    .where("F", Predicates.heatingCoils())
                    .where("H", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("I", Predicates.blocks(CASING_TITANIUM_GEARBOX.get()))
                    .where("J", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public final static MultiblockMachineDefinition DIGESTION_TANK = REGISTRATE.multiblock("digestion_tank", BioMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.DIGESTING)
            .tooltips(Component.translatable("digestion_tank_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.digestion_tank.bio_growth_mechanism").withStyle(ChatFormatting.GREEN),
                    Component.translatable("ctnh.digestion_tank.bio_growth_temperature"))
            .recipeModifiers(BioMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCC", "CAAAC", "CCCCC")
                    .aisle("CCCCC", "AWWWA", "CDDDC")
                    .aisle("CCCCC", "CAKAC", "CGGGC")
                    .where("C", Predicates.blocks(Blocks.BRICKS))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.blocks(Blocks.IRON_TRAPDOOR))
                    .where("A", Predicates.blocks(Blocks.BRICKS)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("G", Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where("W", Predicates.blocks(Blocks.WATER))
                    .build()
            )
            .workableCasingRenderer(new ResourceLocation("block/bricks"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public final static MultiblockMachineDefinition BLAZE_BLAST_FURNACE = REGISTRATE.multiblock("blaze_blast_furnace", BlazeBlastFurnaceMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CTNHBlocks.BLAZE_BLAST_FURNACE_CASING)
            .tooltips(Component.translatable("blaze_blast_furnace").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.blaze_blast_furnace.consume"),
                    Component.translatable("ctnh.blaze_blast_furnace.energy"),
                    Component.translatable("ctnh.blaze_blast_furnace.parallel").withStyle(ChatFormatting.DARK_GREEN),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("GGG", "MMM", "MMM", "GGG")
                    .aisle("GGG", "M M", "M M", "GBG")
                    .aisle("GKG", "MMM", "MMM", "GGG")
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("B", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("G", Predicates.blocks(CTNHBlocks.BLAZE_BLAST_FURNACE_CASING.get()).setMinGlobalLimited(4)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where(" ", Predicates.air())
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/casings/blaze_blast_furnace_casing"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public final static MultiblockMachineDefinition MANA_MACERATOR = REGISTRATE.multiblock("mana_macerator",holder -> new ManaMachine(holder,8,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.MACERATOR_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.mana_macerator"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.basic_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBA", "AAAA", "ABBA")
                    .aisle("ABBA", "ACCA", "ABBA")
                    .aisle("ABBA", "A@DA", "ABBA")
                    .where("A", Predicates.blocks(BotaniaBlocks.livingrockPolished)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("C", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("D", Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();

    public final static MultiblockMachineDefinition MANA_BENDER = REGISTRATE.multiblock("mana_bender",holder -> new ManaMachine(holder,8,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BENDER_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.mana_bender"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.basic_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("EEEEE", "ABBBA", "ABBBA", "ACCCA")
                    .aisle("EDDDE", "B###B", "B###B", "CDDDC")
                    .aisle("EDDDE", "B#F#B", "B#F#B", "CDDDC")
                    .aisle("EDDDE", "B###B", "B###B", "CDDDC")
                    .aisle("EEEEE", "AE@EA", "AEEEA", "ACCCA")
                    .where("A", Predicates.blocks(BotaniaBlocks.livingrockPolished))
                    .where("B", Predicates.frames(GTNNMaterials.ManaSteel))
                    .where("C", Predicates.blocks(BotaniaBlocks.livingrockBrickStairs))
                    .where("D", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("#", Predicates.any())
                    .where("E",Predicates.blocks(BotaniaBlocks.livingrockPolished)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("F", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public final static MultiblockMachineDefinition ZENITH_LASER = REGISTRATE.multiblock("zenith_laser",holder -> new ZenithMachine(holder,24,25,60,20))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.LASER_ENGRAVER_RECIPES,CTNHRecipeTypes.PHASE_INVERSION)
            .appearanceBlock(CTNHBlocks.ZENITH_CASING_BLOCK)
            .recipeModifiers(ZenithMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.zenith_laser"),
                    Component.translatable("zenith_machine").withStyle(ChatFormatting.DARK_PURPLE),
                    Component.translatable("ctnh.super_mana_machine.mana_consume"),
                    Component.translatable("ctnh.zenith_laser_sp"),
                    Component.translatable("ctnh.zenith_machine_tip"),
                    Component.translatable("ctnh.zenith_waring"),
                    Component.translatable("ctnh.perfect_overclock"))

            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("EEEEE", "EAAAE", "EAAAE", "EEEEE")
                    .aisle("EECEE", "A###A", "A###A", "EDDDE")
                    .aisle("ECECE", "F#D#F", "F###F", "EDBDE")
                    .aisle("EECEE", "A###A", "A###A", "EDDDE")
                    .aisle("EEEEE", "EA@AE", "EAEAE", "EEEEE")
                    .where("A", Predicates.blocks(CTNHBlocks.DEPTH_FORCE_FIELD_STABILIZING_CASING.get()))
                    .where("B",Predicates.blocks(CTNHBlocks.ZENITH_EYE.get()))
                    .where("C", Predicates.blocks(CTNHBlocks.ALF_STEEL_CASING.get()))
                    .where("D", Predicates.blocks(CTNHBlocks.ZENITH_CASING_GEARBOX.get()))
                    .where("#", Predicates.any())
                    .where("E",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("F", Predicates.blocks(BotaniaBlocks.manaGlass))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))

                    .build()
            )
            .workableCasingRenderer((CTNHCore.id("block/casings/zenith_casing")), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();

    public final static MultiblockMachineDefinition MANA_WIREMILL = REGISTRATE.multiblock("mana_wiremill",holder -> new ManaMachine(holder,8,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.WIREMILL_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.mana_wiremill"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.basic_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA", "BBB", "CCC")
                    .aisle("AAA", "BBB", "CCC")
                    .aisle("AAA", "B@B", "CCC")
                    .where("A", Predicates.blocks(BotaniaBlocks.livingrockPolished)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("B", Predicates.frames(GTNNMaterials.Elementium))
                    .where("C", Predicates.blocks(CTNHBlocks.ELEMENTIUM_CASING.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public final static MultiblockMachineDefinition MANA_LATHE = REGISTRATE.multiblock("mana_lathe",holder -> new ManaMachine(holder,8,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.LATHE_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.mana_lathe"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.basic_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABA", "AAA", "AAA", "CAC")
                    .aisle("ABA", "D#D", "D#D", "CAC")
                    .aisle("ABA", "D#D", "D#D", "CAC")
                    .aisle("ABA", "D#D", "D#D", "CAC")
                    .aisle("ABA", "A@A", "AAA", "CAC")
                    .where("A", Predicates.blocks(BotaniaBlocks.livingrockPolished)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("B", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("C", Predicates.blocks(BotaniaBlocks.livingrockBrickStairs))
                    .where("D", Predicates.frames(GTNNMaterials.ManaSteel))
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public final static MultiblockMachineDefinition MANA_ASSEMBLER = REGISTRATE.multiblock("mana_assembler",holder -> new ManaMachine(holder,8,5))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.ASSEMBLER_RECIPES)
            .recipeModifiers(ManaMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.mana_assembler"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.advanced_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBBBA", "ABBBBBA", "ABBBBBA", "ACCCCCA", "AAAAAAA")
                    .aisle("BDEEEDB", "B#####B", "B#####B", "C#####C", "ABBBBBA")
                    .aisle("BEDFDEB", "B#####B", "B##G##B", "C#####C", "ABBBBBA")
                    .aisle("BEFDFEB", "B##H##B", "B#GHG#B", "C##H##C", "ABBBBBA")
                    .aisle("BEDFDEB", "B#####B", "B##G##B", "C#####C", "ABBBBBA")
                    .aisle("BDEEEDB", "B#####B", "B#####B", "C#####C", "ABBBBBA")
                    .aisle("ABBBBBA", "ABB@BBA", "ABBBBBA", "ACCCCCA", "AAAAAAA")
                    .where("A", Predicates.frames(CTNHMaterials.AlfSteel))
                    .where("B", Predicates.blocks(BotaniaBlocks.livingrockPolished)
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))

                    .where("C", Predicates.blocks(BotaniaBlocks.manaGlass))
                    .where("D", Predicates.blocks(CTNHBlocks.ELEMENTIUM_CASING.get()))
                    .where("E", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("#", Predicates.any())
                    .where("F", Predicates.blocks(CTNHBlocks.TERRA_STEEL_CASING.get()))
                    .where("G", Predicates.blocks(CASING_STAINLESS_STEEL_GEARBOX.get()))
                    .where("H", Predicates.blocks(CASING_TITANIUM_GEARBOX.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();

    public static final MultiblockMachineDefinition MANA_GENERATOR_TIER1 = REGISTRATE.multiblock("mana_generator_turbine_tier1",holder -> new ManaLargeTurbineMachine(holder,256, MV))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MANA_GENERATOR)
            .generator(true)
            .tooltips(Component.translatable("mana_generator_turbine_tier1").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.mana_generator_turbine_tier1.basic_power"),
                    Component.translatable("ctnh.mana_generator_turbine_tier1.restriction"),
                    Component.translatable("ctnh.mana_generator_turbine_rune"))
            .recipeModifier(ManaLargeTurbineMachine::recipeModifier)
            .appearanceBlock(CTNHBlocks.MANA_STEEL_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAA", "ASSA", "AAAA")
                    .aisle("ASSA", "BCCB", "ASSA")
                    .aisle("AAAA", "A@SA", "AAAA")
                    .where("A", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STEEL_GEARBOX.get()))
                    .where("S", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/casings/mana_steel_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static final MultiblockMachineDefinition MANA_GENERATOR_TIER2 = REGISTRATE.multiblock("mana_generator_turbine_tier2",holder -> new ManaLargeTurbineMachine(holder,512, GTValues.EV))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MANA_GENERATOR)
            .generator(true)
            .tooltips(Component.translatable("mana_generator_turbine_tier2").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.mana_generator_turbine_tier2.basic_power"),
                    Component.translatable("ctnh.mana_generator_turbine_tier2.restriction"),
                    Component.translatable("ctnh.mana_generator_turbine_rune"))
            .recipeModifier(ManaLargeTurbineMachine::recipeModifier)
            .appearanceBlock(CTNHBlocks.ELEMENTIUM_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAA", "ASSA", "AAAA")
                    .aisle("ASSA", "BCCB", "ASSA")
                    .aisle("AAAA", "A@SA", "AAAA")
                    .where("A", Predicates.blocks(CTNHBlocks.ELEMENTIUM_CASING.get()))
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("S", Predicates.blocks(CTNHBlocks.ELEMENTIUM_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/casings/elementium_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static final MultiblockMachineDefinition MANA_GENERATOR_TIER3 = REGISTRATE.multiblock("mana_generator_turbine_tier3",holder -> new ManaLargeTurbineMachine(holder,2048, GTValues.LuV))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MANA_GENERATOR)
            .generator(true)
            .tooltips(Component.translatable("mana_generator_turbine_tier3").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.mana_generator_turbine_tier3.basic_power"),
                    Component.translatable("ctnh.mana_generator_turbine_tier3.restriction"),
                    Component.translatable("ctnh.mana_generator_turbine_rune"))
            .recipeModifier(ManaLargeTurbineMachine::recipeModifier)
            .appearanceBlock(CTNHBlocks.TERRA_STEEL_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAA", "ASSA", "AAAA")
                    .aisle("ASSA", "BCCB", "ASSA")
                    .aisle("AAAA", "A@SA", "AAAA")
                    .where("A", Predicates.blocks(CTNHBlocks.TERRA_STEEL_CASING.get()))
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_STEEL_GEARBOX.get()))
                    .where("S", Predicates.blocks(CTNHBlocks.TERRA_STEEL_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/casings/terra_steel_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static final MultiblockMachineDefinition MANA_GENERATOR_TIER4 = REGISTRATE.multiblock("mana_generator_turbine_tier4",holder -> new ManaLargeTurbineMachine(holder,8192, GTValues.UV))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MANA_GENERATOR)
            .generator(true)
            .tooltips(Component.translatable("mana_generator_turbine_tier4").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.mana_generator_turbine_tier4.basic_power"),
                    Component.translatable("ctnh.mana_generator_turbine_tier4.restriction"),
                    Component.translatable("ctnh.mana_generator_turbine_rune"))
            .recipeModifier(ManaLargeTurbineMachine::recipeModifier)
            .appearanceBlock(CTNHBlocks.ALF_STEEL_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAA", "ASSA", "AAAA")
                    .aisle("ASSA", "BCCB", "ASSA")
                    .aisle("AAAA", "A@SA", "AAAA")
                    .where("A", Predicates.blocks(CTNHBlocks.ALF_STEEL_CASING.get()))
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_LASER).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("S", Predicates.blocks(CTNHBlocks.ALF_STEEL_CASING.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1)))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/casings/alfsteel_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    // Come from GTCA
    public static final MultiblockMachineDefinition SUPER_EBF = REGISTRATE
            .multiblock("super_ebf", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.BLAST_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK), CTNHRecipeModifiers::superEbfOverclock)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXXXX", "FFXXXFF", "F#####F", "F#####F", "F#####F", "FFXXXFF", "XXXVXXX", "##XXX##", "#######")
                    .aisle("XXXXXXX", "FXCCCXF", "##CCC##", "##III##", "##CCC##", "FXCCCXF", "XXXXXXX", "#XXXXX#", "##XXX##")
                    .aisle("XXXXXXX", "XCC#CCX", "#CC#CC#", "#I###I#", "#CC#CC#", "XCC#CCX", "XXXXXXX", "XXXHXXX", "#X###X#")
                    .aisle("XXXXXXX", "XC###CX", "#C###C#", "#I###I#", "#C###C#", "XC###CX", "VXXXXXV", "XXHHHXX", "#X###X#")
                    .aisle("XXXXXXX", "XCC#CCX", "#CC#CC#", "#I###I#", "#CC#CC#", "XCC#CCX", "XXXXXXX", "XXXHXXX", "#X###X#")
                    .aisle("XXXXXXX", "FXCCCXF", "##CCC##", "##III##", "##CCC##", "FXCCCXF", "XXXXXXX", "#XXXXX#", "##XXX##")
                    .aisle("XXXSXXX", "FFXXXFF", "F#####F", "F#####F", "F#####F", "FFXXXFF", "XXXVXXX", "##XXX##", "#######")
                    .where("S", Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where("F", Predicates.frames(GTMaterials.Tungsten))
                    .where("V", Predicates.blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                    .where("I", Predicates.blocks(HEAT_VENT.get()))
                    .where("X", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(158)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where("H", Predicates.abilities(PartAbility.MUFFLER))
                    .where("C", Predicates.heatingCoils())
                    .where("#", Predicates.air())
                    .build()
            )
            .recoveryItems(
                    () -> new ItemLike[]{MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), CTNHCore.id("block/overlay/super_ebf"), true)
            .tooltips(
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"),
                    Component.translatable("gtceu.machine.available_recipe_map_1.tooltip", Component.translatable("ctnh.super_ebf.recipe_type")),
                    Component.translatable("ctnh.machine.super_ebf.tooltip1")
            )
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                            Component.translatable(FormattingUtil.formatNumbers(coilMachine.getCoilType().getCoilTemperature() +
                                            100L * Math.max(0, coilMachine.getTier() - MV)) + "K")
                                    .withStyle(ChatFormatting.RED)));
                }
            })
            .register();
    //Come from GTCA
    public static final MultiblockMachineDefinition MEGA_OIL_CRACKING_UNIT = REGISTRATE
            .multiblock("mega_oil_cracking_unit", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.CRACKING_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK), GTRecipeModifiers::crackerOverclock)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("HHHHHHHHHHHHH", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#")
                    .aisle("HHHHHHHHHHHHH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HHGGGGGGGGGHH")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#HGGGGGGGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C###C###H#", "#H#C#C#C#C#H#", "#H#C###C###H#", "#G#C#C#C#C#G#", "#HGGGHHHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C#C#C#C#H#", "#O#C#C#C#C#I#", "#H#C#C#C#C#H#", "#G#C#C#C#C#G#", "#HGGGHAHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#H#C###C###H#", "#H#C#C#C#C#H#", "#H#C###C###H#", "#G#C#C#C#C#G#", "#HGGGHHHGGGH#")
                    .aisle("HHHHHHHHHHHHH", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#HGGGGGGGGGH#")
                    .aisle("HHHHHHHHHHHHH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HGGGGGGGGGGGH", "HHGGGGGGGGGHH")
                    .aisle("HHHHHHXHHHHHH", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#", "#H#########H#")
                    .where("X", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("H", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(12)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes(), true, false, true, false, false, false))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where("#", Predicates.air())
                    .where("C", Predicates.heatingCoils())
                    .where("G", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("I", Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                    .where("A", Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                    .where("O", Predicates.abilities(PartAbility.EXPORT_FLUIDS))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/cracking_unit"))
            .tooltips(
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"),
                    Component.translatable("gtceu.machine.cracker.tooltip.1")
            )
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.cracking_unit.energy",
                            100 - 10 * coilMachine.getCoilTier()));
                }
            })
            .tooltips(
                    Component.translatable("gtceu.machine.available_recipe_map_1.tooltip", Component.translatable("ctnh.mega_oil_cracker.recipe_type"))
            )
            .register();
    //Come from GTCA
    public static final MultiblockMachineDefinition MEGA_LCR = REGISTRATE
            .multiblock("mega_lcr", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.LARGE_CHEMICAL_RECIPES)
            .appearanceBlock(CASING_PTFE_INERT)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .pattern(definition ->
                    FactoryBlockPattern.start()
                            .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "G#N#G", "G#P#G", "G#N#G", "CCCCC")
                            .aisle("CCCCC", "CGCGC", "CGEGC", "CGCGC", "CCCCC")
                            .where("E", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("N", Predicates.blocks(COIL_CUPRONICKEL.get()))
                            .where("G", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                            .where("P", Predicates.blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                            .where("#", Predicates.air())
                            .where("C", Predicates.blocks(CASING_PTFE_INERT.get()).setMinGlobalLimited(100)
                                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                    .or(Predicates.autoAbilities(true, false, true)))
                            .build()
            )
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"),
                    CTNHCore.id("block/overlay/super_ebf/"),
                    true
            )
            .tooltips(
                    Component.translatable("gtceu.multiblock.parallelizable.tooltip"),
                    Component.translatable("gtceu.machine.available_recipe_map_1.tooltip", Component.translatable("ctnh.mega_lcr.recipe_type"))
            )
            .register();
    public static final MultiblockMachineDefinition IV_CHEMICAL_GENERATOR = registerChemicalGenerator(
            "iv_chemical_generator", IV,
            CASING_TUNGSTENSTEEL_TURBINE, CASING_TUNGSTENSTEEL_GEARBOX, FIREBOX_TUNGSTENSTEEL, CASING_TUNGSTENSTEEL_PIPE,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_tungstensteel"),
            CTNHCore.id("block/overlay/super_chemical"));

    public static final MultiblockMachineDefinition EV_CHEMICAL_GENERATOR = registerChemicalGenerator(
            "ev_chemical_generator", EV,
            CASING_TITANIUM_TURBINE, CASING_TITANIUM_GEARBOX, FIREBOX_TITANIUM, CASING_TITANIUM_PIPE,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"),
            CTNHCore.id("block/overlay/super_chemical"));

    //Comes from GTCA
    public static MultiblockMachineDefinition registerChemicalGenerator(String name, int tier,
                                                                        Supplier<? extends Block> casing,
                                                                        Supplier<? extends Block> gear,
                                                                        Supplier<? extends Block> firebox,
                                                                        Supplier<? extends Block> pipe,
                                                                        ResourceLocation casingTexture,
                                                                        ResourceLocation overlayModel) {
        return REGISTRATE.multiblock(name, holder -> new ChemicalGeneratorMachine(holder, tier))
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(CTNHRecipeTypes.CHEMICAL_GENERATOR)
                .generator(true)
                .recipeModifier(ChemicalGeneratorMachine::recipeModifier, true)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("III", "PPP", "III")
                        .aisle("III", "P#P", "III")
                        .aisle("III", "PPP", "III")
                        .aisle("III", "CSC", "III")
                        .aisle("III", "FGF", "III")
                        .aisle("IMI", "IDI", "III")
                        .where("M", Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where("P", Predicates.blocks(GTBlocks.CASING_PTFE_INERT.get()))
                        .where("#", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                        .where("C", Predicates.blocks(GTBlocks.COIL_CUPRONICKEL.get()))
                        .where("S", Predicates.blocks(pipe.get()))
                        .where("F", Predicates.blocks(firebox.get()))
                        .where("G", Predicates.blocks(gear.get()))
                        .where("I", Predicates.blocks(casing.get()).setMinGlobalLimited(30)
                                .or(Predicates.autoAbilities(definition.getRecipeTypes(), false, false, true, true, true, true))
                                .or(Predicates.autoAbilities(true, true, false)))
                        .where("D",
                                Predicates.ability(PartAbility.OUTPUT_ENERGY,
                                                Stream.of(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV).filter(t -> t >= tier)
                                                        .mapToInt(Integer::intValue).toArray())
                                        .addTooltips(Component.translatable("gtceu.multiblock.pattern.error.limited.1",
                                                GTValues.VN[tier])))
                        .build())
                .recoveryItems(
                        () -> new ItemLike[]{MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
                .workableCasingRenderer(casingTexture, overlayModel)
                .tooltips(
                        Component.translatable("gtceu.universal.tooltip.base_production_eut", V[tier]),
                        tier > EV ?
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_extreme",
                                        V[tier] * 4) :
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_regular",
                                        V[tier] * 3))
                .register();
    }
    public static final MultiblockMachineDefinition INDUSTRIAL_PRIMITIVE_BLAST_FURNACE = REGISTRATE.multiblock("industrial_primitive_blast_furnace", IndustrialPrimitiveBlastFurnaceMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.PRIMITIVE_BLAST_FURNACE_RECIPES)
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(Component.translatable("industrial_primitive_blast_furnace_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.industrial_primitive_blast_furnace.temperature"),
                    Component.translatable("ctnh.industrial_primitive_blast_furnace.parallel").withStyle(ChatFormatting.GREEN),
                    Component.translatable("ctnh.industrial_primitive_blast_furnace.efficiency").withStyle(ChatFormatting.GREEN))
            .recipeModifier(IndustrialPrimitiveBlastFurnaceMachine::recipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CAAAC", " CCC ", " CCC ", " CCC ", "  C  ", "  C  ", "  C  ")
                    .aisle("ABBBA", "C   C", "C   C", "C   C", " C C ", " C C ", " C C ")
                    .aisle("CCBCC", "FC CF", "FC CF", "FC CF", "  C  ", "  C  ", "  C  ")
                    .aisle("CAAAC", " AKA ", " CAC ", " CCC ", "     ", "     ", "     ")
                    .where("C", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get()))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("F", Predicates.frames(GTMaterials.Bronze))
                    .where("A", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    )
                    .where("B", Predicates.blocks(GTBlocks.FIREBOX_BRONZE.get()))
                    .where(" ", Predicates.any())
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/multiblock/steam_oven"), false)
            .register();
    public static final MultiblockMachineDefinition VOID_MINER = REGISTRATE.multiblock("void_miner", VoidMinerProcessingMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.VOID_MINER)
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .tooltips(Component.translatable("void_miner").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.void_miner.tooltip.0"),
                    Component.translatable("ctnh.void_miner.tooltip.1"),
                    Component.translatable("ctnh.void_miner.tooltip.2"),
                    Component.translatable("ctnh.void_miner.tooltip.3"),
                    Component.translatable("ctnh.void_miner.tooltip.4"),
                    Component.translatable("ctnh.void_miner.tooltip.5"),
                    Component.translatable("ctnh.void_miner.tooltip.6"),
                    Component.translatable("ctnh.void_miner.tooltip.7"))
            .recipeModifier(VoidMinerProcessingMachine::recipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCC", "XF   FX", "XF   FX", "XXXXXXX", "XF   FX", "XF   FX", "XF   FX", " F   F ", "       ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "F     F", "F     F", "X     X", "F     F", "F     F", "FX   XF", "FX   XF", " FFFFF ", "       ", "       ", "       ")
                    .aisle("CCBBBCC", "       ", "   A   ", "X  F  X", "   F   ", "   F   ", "  PGP  ", "  PGP  ", " FVFVF ", "  #F#  ", "  #F#  ", "  ###  ")
                    .aisle("CCBBBCC", "   A   ", "  AAA  ", "X FPF X", "  FPF  ", "  FPF  ", "  GGG  ", "  GGG  ", " FFXFF ", "  FXF  ", "  FXF  ", "  #F#  ")
                    .aisle("CCBBBCC", "       ", "   A   ", "X  F  X", "   F   ", "   F   ", "  PGP  ", "  PGP  ", " FVFVF ", "  #F#  ", "  #F#  ", "  ###  ")
                    .aisle("CCCCCCC", "F     F", "F     F", "X     X", "F     F", "F     F", "FX   XF", "FX   XF", " FFFFF ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "XF   FX", "XF   FX", "XXXYXXX", "XF   FX", "XF   FX", "XF   FX", " F   F ", "       ", "       ", "       ", "       ")
                    .where("C", Predicates.blocks(DARK_CONCRETE.get()))
                    .where("B", Predicates.blocks(Blocks.DEEPSLATE_TILES))
                    .where("A", Predicates.blocks(CTNHBlocks.CASING_TUNGSTENCU_DIAMOND_PLATING.get()))
                    .where("V", Predicates.blocks(HEAT_VENT.get()))
                    .where("F", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("P", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("G", Predicates.blocks(CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("X", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.air())
                    .where(" ", Predicates.any())
                    .where("Y", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/large_chemical_reactor"), false)
            .register();

    public static final MultiblockMachineDefinition SINTERING_KILN = REGISTRATE.multiblock("sintering_kiln", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SINTERING_KILN)
            .tooltips(Component.translatable("sintering_kiln_introduction").withStyle(ChatFormatting.GRAY))
            .appearanceBlock(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "#AAA#", "#AAA#", "#ADA#", "#####")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "ABBBA", "AB#BA", "ABCBA", "#AAA#")
                    .aisle("AAAAA", "#AAA#", "#A@A#", "#ADA#", "#####")
                    .where("A", Predicates.blocks(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1)))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get()))
                    .where("C", Predicates.blocks(Blocks.PISTON))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("D", Predicates.abilities(CTPPPartAbility.INPUT_KINETIC).setExactLimit(1)
                            .or(Predicates.blocks(CTNHBlocks.HIGH_GRADE_COKE_OVEN_BRICKS.get())))
                    .build()
            )
            .workableCasingRenderer(CTNHCore.id("block/high_grade_coke_oven_bricks"), GTCEu.id("block/machines/alloy_smelter"), false)
            .register();
    public static final MultiblockMachineDefinition ULTIMATE_COMBUSTION_ENGINE = registerLargeCombustionEngine(
            "ultimate_combustion_engine", LuV,
            CASING_NAQUADAH_BLOCK, CASING_NAQUADAH_GEARBOX, CASING_ULTIMATE_ENGINE_INTAKE,
            CTNHCore.id("block/casings/nq_casing"),
            GTCEu.id("block/multiblock/generator/extreme_combustion_engine"));

    public static final MultiblockMachineDefinition CHEMICAL_VAPOR_DEPOSITION_MACHINE = REGISTRATE.multiblock("chemical_vapor_deposition_machine", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CHEMICAL_VAPOR_DEPOSITION)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "AAABBBA", "AAABBBA")
                    .aisle("AAAAAAA", "ACADDDA", "AAABBBA")
                    .aisle("AAAAAAA", "A@ABBBA", "AAABBBA")
                    .where("A", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("C", Predicates.blocks(GCYMBlocks.MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                    .where("D", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition MARTIAL_MORALITY_EYE = REGISTRATE.multiblock("martial_morality_eye", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.MARTIAL_MORALITY_EYE)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .tooltips(Component.translatable("martial_morality_eye").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.martial_morality_eye.tooltip.0"),
                    Component.translatable("ctnh.martial_morality_eye.tooltip.1"),
                    Component.translatable("ctnh.martial_morality_eye.tooltip.2"),
                    Component.translatable("ctnh.martial_morality_eye.tooltip.3"),
                    Component.translatable("ctnh.martial_morality_eye.tooltip.4"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "############AAAAAAAAA############", "###############A#A###############", "############AAAAAAAAA############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "##############BBBBB##############", "#############BBABABB#############", "#########AAAABAABAABAAAA#########", "#############BBBBBBB#############", "#########AAAABAABAABAAAA#########", "#############BBABABB#############", "##############BBBBB##############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#############BBBBBBB#############", "############BB#####BB############", "############B##CCC##B############", "#######AAA##B#CDDDC#B##AAA#######", "##########BBB#CDDDC#BBB##########", "#######AAA##B#CDDDC#B##AAA#######", "############B##CCC##B############", "############BB#####BB############", "#############BBBBBBB#############", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "######AA#################AA######", "########BB#############BB########", "######AA#################AA######", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "################D################", "#################################", "#################################", "#################################", "#################################", "######A###################A######", "#####AA###################AA#####", "######ABDD#############DDBA######", "#####AA###################AA#####", "######A###################A######", "#################################", "#################################", "#################################", "#################################", "################D################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#######E#################E#######", "#######C#################C#######", "####AA#C#################C#AA####", "######BD#################DB######", "####AA#C#################C#AA####", "#######C#################C#######", "#######E#################E#######", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "####A#######################A####", "###AA#######################AA###", "####ABD###################DBA####", "###AA#######################AA###", "####A#######################A####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#####C#####################C#####", "##AA#C#####################C#AA##", "####BD#####################DB####", "##AA#C#####################C#AA##", "#####C#####################C#####", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##A###########################A##", "###BD#######################DB###", "##A###########################A##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "###############A#A###############", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#AA###########################AA#", "###BD#######################DB###", "#AA###########################AA#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("###############A#A###############", "###############A#A###############", "#############BBBBBBB#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############BBBBBBB#############", "###############A#A###############", "###############A#A###############")
                    .aisle("###############A#A###############", "##############BBBBB##############", "############BB#####BB############", "#################################", "#################################", "#######E#################E#######", "#################################", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "#B#############################B#", "AB#############################BA", "#B#############################B#", "AB#############################BA", "#B#############################B#", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#################################", "#######E#################E#######", "#################################", "#################################", "############BB#####BB############", "##############BBBBB##############", "###############A#A###############")
                    .aisle("###############A#A###############", "#############BBABABB#############", "############B##CCC##B############", "#################################", "######A###################A######", "#######C#################C#######", "####A#######################A####", "#####C#####################C#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "#B#############################B#", "#B#############################B#", "AAC###########################CAA", "#BC###########################CB#", "AAC###########################CAA", "#B#############################B#", "#B#############################B#", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####C#####################C#####", "####A#######################A####", "#######C#################C#######", "######A###################A######", "#################################", "############B##CCC##B############", "#############BBABABB#############", "###############A#A###############")
                    .aisle("############AAAAAAAAA############", "#########AAAABAABAABAAAA#########", "#######AAA##B#CDDDC#B##AAA#######", "######AA#################AA######", "#####AA###################AA#####", "####AA#C#################C#AA####", "###AA#######################AA###", "##AA#C#####################C#AA##", "##A###########################A##", "#AA###########################AA#", "#A#############################A#", "#A#############################A#", "AAB###########################BAA", "AB#############################BA", "AAC###########################CAA", "AAD###########################DAA", "ABD###########################DBA", "AAD###########################DAA", "AAC###########################CAA", "AB#############################BA", "AAB###########################BAA", "#A#############################A#", "#A#############################A#", "#AA###########################AA#", "##A###########################A##", "##AA#C#####################C#AA##", "###AA#######################AA###", "####AA#C#################C#AA####", "#####AA###################AA#####", "######AA#################AA######", "#######AAA##B#CDDDC#B##AAA#######", "#########AAAABAABAABAAAA#########", "############AAAAAAAAA############")
                    .aisle("###############A#A###############", "#############BBBBBBB#############", "##########BBB#CDDDC#BBB##########", "########BB#############BB########", "######ABDD#############DDBA######", "######BD#################DB######", "####ABD###################DBA####", "####BD#####################DB####", "###BD#######################DB###", "###BD#######################DB###", "##B###########################B##", "##B###########################B##", "##B###########################B##", "#B#############################B#", "#BC###########################CB#", "ABD###########################DBA", "#BD###########################DB#", "ABD###########################DBA", "#BC###########################CB#", "#B#############################B#", "##B###########################B##", "##B###########################B##", "##B###########################B##", "###BD#######################DB###", "###BD#######################DB###", "####BD#####################DB####", "####ABD###################DBA####", "######BD#################DB######", "######ABDD#############DDBA######", "########BB#############BB########", "##########BBB#CDDDC#BBB##########", "#############BBBBBBB#############", "###############A#A###############")
                    .aisle("############AAAAAAAAA############", "#########AAAABAABAABAAAA#########", "#######AAA##B#CDDDC#B##AAA#######", "######AA#################AA######", "#####AA###################AA#####", "####AA#C#################C#AA####", "###AA#######################AA###", "##AA#C#####################C#AA##", "##A###########################A##", "#AA###########################AA#", "#A#############################A#", "#A#############################A#", "AAB###########################BAA", "AB#############################BA", "AAC###########################CAA", "AAD###########################DAA", "ABD###########################DBA", "AAD###########################DAA", "AAC###########################CAA", "AB#############################BA", "AAB###########################BAA", "#A#############################A#", "#A#############################A#", "#AA###########################AA#", "##A###########################A##", "##AA#C#####################C#AA##", "###AA#######################AA###", "####AA#C#################C#AA####", "#####AA###################AA#####", "######AA#################AA######", "#######AAA##B#CDDDC#B##AAA#######", "#########AAAABAABAABAAAA#########", "############AAAAAAAAA############")
                    .aisle("###############A#A###############", "#############BBABABB#############", "############B##CCC##B############", "#################################", "######A###################A######", "#######C#################C#######", "####A#######################A####", "#####C#####################C#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "#B#############################B#", "#B#############################B#", "AAC###########################CAA", "#BC###########################CB#", "AAC###########################CAA", "#B#############################B#", "#B#############################B#", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####C#####################C#####", "####A#######################A####", "#######C#################C#######", "######A###################A######", "#################################", "############B##CCC##B############", "#############BBABABB#############", "###############A#A###############")
                    .aisle("###############A#A###############", "##############BBBBB##############", "############BB#####BB############", "#################################", "#################################", "#######E#################E#######", "#################################", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "#B#############################B#", "AB#############################BA", "#B#############################B#", "AB#############################BA", "#B#############################B#", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#################################", "#######E#################E#######", "#################################", "#################################", "############BB#####BB############", "##############BBBBB##############", "###############A#A###############")
                    .aisle("###############A#A###############", "###############A#A###############", "#############BBBBBBB#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##B###########################B##", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "AAB###########################BAA", "##B###########################B##", "##B###########################B##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############BBBBBBB#############", "###############A#A###############", "###############A#A###############")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#A#############################A#", "##B###########################B##", "#A#############################A#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "###############A#A###############", "#################################")
                    .aisle("#################################", "###############A#A###############", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#AA###########################AA#", "###BD#######################DB###", "#AA###########################AA#", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "###############A#A###############", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "##A###########################A##", "###BD#######################DB###", "##A###########################A##", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#####E#####################E#####", "#####C#####################C#####", "##AA#C#####################C#AA##", "####BD#####################DB####", "##AA#C#####################C#AA##", "#####C#####################C#####", "#####E#####################E#####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "####A#######################A####", "###AA#######################AA###", "####ABD###################DBA####", "###AA#######################AA###", "####A#######################A####", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "#############ECCDCCE#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#######E#################E#######", "#######C#################C#######", "####AA#C#################C#AA####", "######BD#################DB######", "####AA#C#################C#AA####", "#######C#################C#######", "#######E#################E#######", "#################################", "#################################", "#################################", "#################################", "#################################", "#############ECCDCCE#############", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "##############AAAAA##############", "################B################", "################D################", "################D################", "#################################", "#################################", "#################################", "#################################", "######A###################A######", "#####AA###################AA#####", "######ABDD#############DDBA######", "#####AA###################AA#####", "######A###################A######", "#################################", "#################################", "#################################", "#################################", "################D################", "################D################", "################B################", "##############AAAAA##############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#################################", "#################################", "#################################", "#################################", "#################################", "######AA#################AA######", "########BB#############BB########", "######AA#################AA######", "#################################", "#################################", "#################################", "#################################", "#################################", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "################B################", "################B################", "#############BBBBBBB#############", "############BB#####BB############", "############B##CCC##B############", "#######AAA##B#CDDDC#B##AAA#######", "##########BBB#CDDDC#BBB##########", "#######AAA##B#CDDDC#B##AAA#######", "############B##CCC##B############", "############BB#####BB############", "#############BBBBBBB#############", "################B################", "################B################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "##############BBBBB##############", "#############BBABABB#############", "#########AAAABAABAABAAAA#########", "#############BBBBBBB#############", "#########AAAABAABAABAAAA#########", "#############BBABABB#############", "##############BBBBB##############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "###############A#A###############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .aisle("#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#############AAAAAAA#############", "############AAFFFFFAA############", "############AFFFFFFFA############", "############AFFAAAFFA############", "############AFFA@AFFA############", "############AFFAAAFFA############", "############AFFFFFFFA############", "############AAFFFFFAA############", "#############AAAAAAA#############", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################", "#################################")
                    .where("A", Predicates.blocks(Blocks.BRICKS))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(OAK_PLANKS))
                    .where("C", Predicates.blocks(Blocks.LAPIS_BLOCK))
                    .where("D", Predicates.blocks(Blocks.BOOKSHELF))
                    .where("E", Predicates.blocks(Blocks.CHISELED_STONE_BRICKS))
                    .where("F", Predicates.blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(9))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public static final MultiblockMachineDefinition ADVANCED_COKE_OVEN = REGISTRATE.multiblock("advanced_coke_oven", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.COKE_OVEN_RECIPES)
            .recipeModifier((machine,recipe) -> CTNHRecipeModifiers.accurateParallel(machine,recipe,32).andThen(ModifierFunction.builder().eutMultiplier((double) 300 /recipe.duration).build()))
            .appearanceBlock(HIGH_GRADE_COKE_OVEN_BRICKS)
            .tooltips(Component.translatable("advanced_blast_furnace").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.advanced_blast_furnace.tooltip.0"),
                    Component.translatable("ctnh.advanced_blast_furnace.tooltip.1"),
                    Component.translatable("ctnh.advanced_blast_furnace.tooltip.2"),
                    Component.translatable("ctnh.advanced_blast_furnace.tooltip.3"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBB", "BBB", "BBB")
                    .aisle("BBB", "B#B", "BBB")
                    .aisle("BBB", "B@B", "BBB")
                    .where("B", Predicates.blocks(HIGH_GRADE_COKE_OVEN_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(9))
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(9)))
                    .where("#", Predicates.any())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/high_grade_coke_oven_bricks"), GTCEu.id("block/machines/arc_furnace"), false)
            .register();

    public static final MultiblockMachineDefinition DIMENSIONAL_GAS_COLLECTION_CHAMBER = REGISTRATE.multiblock("dimensional_gas_collection_chamber", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.DIMENSIONAL_GAS_COLLECTION)
            .recipeModifier((machine,recipe) -> GTRecipeModifiers.OC_PERFECT_SUBTICK.getModifier(machine,recipe).andThen(CTNHRecipeModifiers.accurateParallel(machine,recipe,1)))
            .appearanceBlock(PLASTCRETE)
            .tooltips(
                    Component.translatable("large_gas_collection_chamber").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.large_gas_collection_chamber.tooltip.0"),
                    Component.translatable("ctnh.large_gas_collection_chamber.tooltip.1"),
                    Component.translatable("ctnh.large_gas_collection_chamber.tooltip.2")
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("ABBBA", "BCCCB", "BCCCB", "BCCCB", "ABBBA")
                    .aisle("ABBBA", "BDDDB", "BDEDB", "BDDDB", "ABBBA")
                    .aisle("ABBBA", "BCCCB", "BCCCB", "BCCCB", "ABBBA")
                    .aisle("AAAAA", "ABBBA", "AB@BA", "ABBBA", "AAAAA")
                    .where("A", Predicates.blocks(PLASTCRETE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(FILTER_CASING.get()))
                    .where("C", Predicates.blocks(HERMETIC_CASING_HV.get()))
                    .where("D", Predicates.blocks(CASING_ASSEMBLY_LINE.get()))
                    .where("E", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/cleanroom/plascrete"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();

    public static final MultiblockMachineDefinition CONDENSING_DISCRETE = REGISTRATE.multiblock("condensing_discrete", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CONDENSING_DISCRETE)
            .appearanceBlock(CASING_ALUMINIUM_FROSTPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("A###A", "BBCBB", "BBBBB", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "BBBBB", "CCCCC")
                    .aisle("#BBB#", "BBBBB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "CBBBC")
                    .aisle("#BBB#", "CCACC", "BAAAB", "D#A#D", "DAAAD", "D#A#D", "DAAAD", "D#A#D", "DAAAD", "D#A#D", "BAAAB", "CBSBC")
                    .aisle("#BBB#", "BBCBB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "BF#FB", "BEAEB", "CBBBC")
                    .aisle("A###A", "BB@BB", "BBBBB", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "#BDB#", "BBBBB", "CCCCC")
                    .where("A", Predicates.frames(GTMaterials.BlueAlloy))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(CASING_ALUMINIUM_FROSTPROOF.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("C", Predicates.blocks(HEAT_VENT.get()))
                    .where("D", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("E", Predicates.blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("F", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("S", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_frost_proof"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition ION_EXCHANGER = REGISTRATE.multiblock("ion_exchanger", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ION_EXCHANGER)
            .appearanceBlock(CASING_HSSE_STURDY)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#AAAAA#", "AABBBAA", "ABBBBBA", "AABBBAA", "#AAAAA#", "#######")
                    .aisle("ACDCDCA", "A#EFE#A", "B#EGE#B", "A#EFE#A", "AADADAA", "#AAHAA#")
                    .aisle("ACDCDCA", "B#EFE#B", "B#EGE#B", "B#EFE#B", "AADADAA", "#AAHAA#")
                    .aisle("ACDCDCA", "A#EFE#A", "B#EGE#B", "A#EFE#A", "AADADAA", "#AAHAA#")
                    .aisle("#AA@AA#", "AABBBAA", "ABBBBBA", "AABBBAA", "#AAAAA#", "#######")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_HSSE_STURDY.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("C", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("D", Predicates.blocks(CTNHBlocks.CASING_OSMIRIDIUM.get()))
                    .where("E", Predicates.blocks(CASING_PTFE_INERT.get()))
                    .where("F", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("G", Predicates.frames(GTMaterials.BlueAlloy))
                    .where("H", Predicates.frames(GTMaterials.Tungsten))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_sturdy_hsse"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition LARGE_STEEL_FURNACE = REGISTRATE.multiblock("large_steel_furnace", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.FURNACE_RECIPES)
            .recipeModifier((machine,recipe) -> GTRecipeModifiers.OC_PERFECT_SUBTICK.getModifier(machine,recipe).andThen(CTNHRecipeModifiers.accurateParallel(machine,recipe,32)))
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(
                    Component.translatable("large_steel_alloy_furnace").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.0"),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.1"),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.2")
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAA", "BBB", "BBB", "#B#")
                    .aisle("AAA", "BCB", "BAB", "#B#")
                    .aisle("AAA", "B@B", "BBB", "#B#")
                    .where("A", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("B", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();

    public static final MultiblockMachineDefinition LARGE_STEEL_ALLOY_FURNACE = REGISTRATE.multiblock("large_steel_alloy_furnace", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifier((machine,recipe) -> GTRecipeModifiers.OC_PERFECT_SUBTICK.getModifier(machine,recipe).andThen(CTNHRecipeModifiers.accurateParallel(machine,recipe,32)))
            .appearanceBlock(CASING_PRIMITIVE_BRICKS)
            .tooltips(
                    Component.translatable("large_steel_alloy_furnace").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.0"),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.1"),
                    Component.translatable("ctnh.large_steel_furnaces.tooltip.2")
            )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABA", "CCC", "CBC", "CCC")
                    .aisle("BBB", "CCC", "BDB", "CCC")
                    .aisle("ABA", "C@C", "CBC", "CCC")
                    .where("A", Predicates.frames(GTMaterials.Steel))
                    .where("B", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("C", Predicates.blocks(CASING_PRIMITIVE_BRICKS.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("D", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_primitive_bricks"), GTCEu.id("block/machines/alloy_smelter"), false)
            .register();

    public static final MultiblockMachineDefinition ZPM_LARGE_MINER = REGISTRATE.multiblock("zpm_large_miner", holder -> new LargeMinerMachine(holder, GTValues.ZPM, 64/GTValues.ZPM, 2*GTValues.ZPM - 5,7,6))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.MACERATOR_RECIPES)
            .tooltips(
                    Component.translatable("ctnh.machine.large_miner.zpm.tooltip"),
                    Component.translatable("gtceu.machine.miner.multi.description"))
            .tooltipBuilder((stack, tooltip) -> {
                int workingAreaChunks = (2 * ZPM - 5);
                tooltip.add(Component.translatable("gtceu.machine.miner.multi.modes"));
                tooltip.add(Component.translatable("gtceu.machine.miner.multi.production"));
                tooltip.add(Component.translatable("gtceu.machine.miner.fluid_usage", 8 - (ZPM - 5),
                        DrillingFluid.getLocalizedName()));
                tooltip.add(Component.translatable("gtceu.universal.tooltip.working_area_chunks",
                        workingAreaChunks, workingAreaChunks));
                tooltip.add(Component.translatable("gtceu.universal.tooltip.energy_tier_range",
                        GTValues.VNF[ZPM], GTValues.VNF[ZPM + 1]));
            })
            .pattern((definition) -> FactoryBlockPattern.start()
                    .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                    .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                    .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                    .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("X", Predicates.blocks(CASING_OSMIRIDIUM.get())
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                    .setMaxGlobalLimited(2).setPreviewCount(1)))
                    .where("C", Predicates.blocks(CASING_OSMIRIDIUM.get()))
                    .where("F", Predicates.frames(GTMaterials.Osmiridium))
                    .where("#", Predicates.any())
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/osmiridium_casing"), GTCEu.id("block/multiblock/large_miner"), false)
            .register();
    public static final MultiblockMachineDefinition DECAY_POOLS = REGISTRATE.multiblock("decay_pools_machine",WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.DECAY_POOLS)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .tooltips(Component.translatable("decay_pools_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.decay_pools_machine.tooltip.0"),
                    Component.translatable("ctnh.decay_pools_machine.tooltip.1"),
                    Component.translatable("ctnh.decay_pools_machine.tooltip.2"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##A#A##", "##A#A##", "##AAA##", "##AAA##", "##AAA##", "###A###", "#######")
                    .aisle("#######", "##AAA##", "#ABBBA#", "#ABBBA#", "#ABBBA#", "##AAA##", "###A###")
                    .aisle("A#AAA#A", "AABBBAA", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("##AAA##", "#ABBBA#", "AB###BA", "AB#D#BA", "AB###BA", "#ABBBA#", "##AAA##")
                    .aisle("A#AAA#A", "AABBBAA", "ABC#CBA", "AB#D#BA", "ABC#CBA", "#ABBBA#", "##AAA##")
                    .aisle("#######", "##AAA##", "#ABBBA#", "#ABBBA#", "#ABBBA#", "##AAA##", "###A###")
                    .aisle("##A#A##", "##A#A##", "##AAA##", "##A@A##", "##AAA##", "###A###", "#######")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTNNMaterials.Cerrobase140).get()))
                    .where("C", Predicates.blocks(GTMachines.WORLD_ACCELERATOR[UV].getBlock()))
                    .where("D", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.Neutronium).get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static final MultiblockMachineDefinition FUEL_REFINING_FACTORY = REGISTRATE.multiblock("fuel_refining_factory", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.FUEL_REFINING)
            .recipeModifier(GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####ABBBA#####", "#####BCCCB#####", "#####BCCCB#####", "#####BCCCB#####", "#####ABBBA#####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("###DDBEEEBDD###", "###DEEDFDEED###", "###DDBDGDBDD###", "###DBBDBDBBD###", "######EAE######", "######EEE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("####BBEEEBB####", "###EHHBFBHHE###", "####BBBCBBB####", "###E##BBB##E###", "######BBB######", "######EEE######", "######EEE######", "####BBBBBBB####", "####DDDDDDD####", "####EEEEEEE####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("#D#B##EEE##B#D#", "#DEHBBBFBBBHED#", "#D#B###C###B#D#", "#DED#######DED#", "###D#######D###", "###D##EEE##D###", "###D##DFD##D###", "###BEEECEEEB###", "###DHHHHHHHD###", "###EBBBBBBBE###", "######D#D######", "######D#D######", "######D#D######", "######D#D######", "######EEE######", "######DDD######", "######BBB######", "###############", "###############")
                    .aisle("#DB###EEE###BD#", "#EHB##DFD##BHE#", "#DB####C####BD#", "#B###########B#", "###############", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHBBBBBBBHD##", "##EBF#####FBE##", "####F#####F####", "####F#####F####", "####EEEEEEE####", "###############", "#####EEEEE#####", "#####DHHHD#####", "#####BACAB#####", "######ACA######", "###############")
                    .aisle("ABB###EEE###BBA", "BEHB##DFD##BHEB", "BBB####C####BBB", "BB###########BB", "A#############A", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHB#####BHD##", "##EB#######BE##", "###############", "#####BBBBB#####", "####EAAAAAE####", "###############", "####EBEEEBE####", "####DHDFDHD####", "####BB#C#BB####", "#####EEEEE#####", "######EEE######")
                    .aisle("BEEEEEEEEEEEEEB", "IDBBDDDFDDDBBDJ", "IDB###DKD###BDJ", "IDB###DKD###BDJ", "BEB###DKD###BEB", "#EEEEEDKDEEEEE#", "##EDDDDFDDDDE##", "##BE##DKD##EB##", "##DHB#DKD#BHD##", "##EB##DKD##BE##", "###D##DKD##D###", "###D#BDKDB#D###", "###DEADKDAED###", "###D##DKD##D###", "###EEEDKDEEE###", "###DHDLLLDHD###", "###BA#LLL#AB###", "####AELLLEA####", "#####EMMME#####")
                    .aisle("BEEEEEEEEEEEEEB", "IFFFFFFFFFFFFFJ", "IGCCCCKFKCCCCGJ", "IBB###KFK###BBJ", "BAB###KFK###BAB", "#EEEEEKFKEEEEE#", "##EFFFFFFFFFE##", "##BCCCKFKCCCB##", "##DHB#KFK#BHD##", "##EB##KFK##BE##", "######KFK######", "#####BKFKB#####", "####EAKFKAE####", "######KFK######", "###EEEKFKEEE###", "###DHFLLLFHD###", "###BCCLOLCCB###", "####CELLLEC####", "#####EMMME#####")
                    .aisle("BEEEEEEEEEEEEEB", "IDBBDDDFDDDBBDJ", "IDB###DKD###BDJ", "IDB###DKD###BDJ", "BEB###DKD###BEB", "#EEEEEDKDEEEEE#", "##EDDDDFDDDDE##", "##BE##DKD##EB##", "##DHB#DKD#BHD##", "##EB##DKD##BE##", "###D##DKD##D###", "###D#BDKDB#D###", "###DEADKDAED###", "###D##DKD##D###", "###EEEDKDEEE###", "###DHDLLLDHD###", "###BA#LLL#AB###", "####AELLLEA####", "#####EMMME#####")
                    .aisle("ABB###EEE###BBA", "BEHB##DFD##BHEB", "BBB####C####BBB", "BB###########BB", "A#############A", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHB#####BHD##", "##EB#######BE##", "###############", "#####BBBBB#####", "####EAAAAAE####", "###############", "####EBEEEBE####", "####DHDFDHD####", "####BB#C#BB####", "#####EEEEE#####", "######EEE######")
                    .aisle("#DB###EEE###BD#", "#EHB##DFD##BHE#", "#DB####C####BD#", "#B###########B#", "###############", "######EEE######", "######DFD######", "##BE###C###EB##", "##DHBBBBBBBHD##", "##EBF#####FBE##", "####F#####F####", "####F#####F####", "####EEEEEEE####", "###############", "#####EEEEE#####", "#####DHHHD#####", "#####BACAB#####", "######ACA######", "###############")
                    .aisle("#D#B##EEE##B#D#", "#DEHBBBFBBBHED#", "#D#B###C###B#D#", "#DED#######DED#", "###D#######D###", "###D##EEE##D###", "###D##DFD##D###", "###BEEECEEEB###", "###DHHHHHHHD###", "###EBBBBBBBE###", "######D#D######", "######D#D######", "######D#D######", "######D#D######", "######EEE######", "######DDD######", "######BBB######", "###############", "###############")
                    .aisle("####BBEEEBB####", "###EHHBFBHHE###", "####BBBCBBB####", "###E##BBB##E###", "######BBB######", "######EEE######", "######EEE######", "####BBBBBBB####", "####DDDDDDD####", "####EEEEEEE####", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("###DDBEEEBDD###", "###DEEDFDEED###", "###DDBDGDBDD###", "###DBBDBDBBD###", "######EAE######", "######EEE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .aisle("####DNNNNND####", "####ENN@NNE####", "####DNNNNND####", "####EEDEDEE####", "######EAE######", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############", "###############")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("B", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("C", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.BlackSteel).get()))
                    .where("D", Predicates.frames(GTMaterials.BlackSteel))
                    .where("E", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where("F", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("G", Predicates.blocks(HERMETIC_CASING_LV.get()))
                    .where("H", Predicates.heatingCoils())
                    .where("I", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.RedSteel).get()))
                    .where("J", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.BlueSteel).get()))
                    .where("K", Predicates.blocks(HERMETIC_CASING_HV.get()))
                    .where("L", Predicates.blocks(BLAZE_BLAST_FURNACE_CASING.get()))
                    .where("M", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(9))
                    .where("N", Predicates.blocks(CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("O", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,CTNHMaterials.Ignitium).get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();
    public static final MultiblockMachineDefinition VACUUM_SINTERING_TOWER = REGISTRATE.multiblock("vacuum_sintering_tower", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.VACUUM_SINTERING)
            .tooltips(Component.translatable("vacuum_sintering_tower").withStyle(ChatFormatting.GRAY))
            .recipeModifier((machine,recipe) -> CTNHRecipeModifiers.accurateParallel(machine,recipe,16).compose(GTRecipeModifiers.ebfOverclock(machine,recipe)))
            .appearanceBlock(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "AAAAA", "A###A", "#####", "#####", "#####")
                    .aisle("AAAAA", "ABBBA", "ABSBA", "#BBB#", "#BBB#", "#####")
                    .aisle("AAAAA", "CBBBC", "DBEBD", "DB#BD", "DBBBD", "DDDDD")
                    .aisle("AAAAA", "ABBBA", "ABEBA", "#B#B#", "#BBB#", "##C##")
                    .aisle("AAAAA", "CBBBC", "DBEBD", "DB#BD", "DBBBD", "DDDDD")
                    .aisle("AAAAA", "ABBBA", "ABEBA", "#B#B#", "#BBB#", "##C##")
                    .aisle("AAAAA", "CBBBC", "DBEBD", "DB#BD", "DBBBD", "DDDDD")
                    .aisle("AAAAA", "ABBBA", "ABEBA", "#B#B#", "#BBB#", "##C##")
                    .aisle("AAAAA", "CBBBC", "DB@BD", "DBBBD", "DBBBD", "DDDDD")
                    .aisle("AAAAA", "AA#AA", "A###A", "#####", "#####", "#####")
                    .where("A", Predicates.blocks(CASING_INVAR_HEATPROOF.get()))
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                    .where("D", Predicates.frames(GTMaterials.TungstenSteel))
                    .where("E", Predicates.heatingCoils())
                    .where("S", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/high_temperature_smelting_casing"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static final MultiblockMachineDefinition CRYSTALLIZER = REGISTRATE.multiblock("crystallizer", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.CRYSTALLIZER)
            .tooltips(Component.translatable("crystallizer").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.crystallizer.basic"),
                    Component.translatable("ctnh.crystallizer.coolant"),
                    Component.translatable("ctnh.crystallizer.overclock"),
                    Component.translatable("ctnh.crystallizer.safe"))
            .recipeModifier((machine,recipe) -> CTNHRecipeModifiers.accurateParallel(machine,recipe,16).compose(GTRecipeModifiers.ebfOverclock(machine,recipe)))
            .appearanceBlock(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##AAAAA##", "###BCB###", "###BBB###", "#########", "#########", "#########", "#########", "#########", "#########")
                    .aisle("#AAAAAAA#", "##AACAA##", "###AAA###", "####D####", "####D####", "####D####", "####D####", "####A####", "#########")
                    .aisle("AAAAAAAAA", "#ADCCCDA#", "##DAAAD##", "##D###D##", "##D###D##", "##D###D##", "##D#A#D##", "##AAAAA##", "####B####")
                    .aisle("AAACCCAAA", "BACEEECAB", "BAAAEAAAB", "####F####", "####F####", "####F####", "###AEA###", "##AAAAA##", "###AAA###")
                    .aisle("AAACCCAAA", "CCCEGECCC", "BAAEGEAAB", "#D#FGF#D#", "#D#FGF#D#", "#D#FGF#D#", "#DAEEEAD#", "#AAACAAA#", "##BASAB##")
                    .aisle("AAACCCAAA", "BACEEECAB", "BAAAEAAAB", "####F####", "####F####", "####F####", "###AEA###", "##AAAAA##", "###AAA###")
                    .aisle("AAAAAAAAA", "#ADCCCDA#", "##DAAAD##", "##D###D##", "##D###D##", "##D###D##", "##D#A#D##", "##AAAAA##", "####B####")
                    .aisle("#AAAAAAA#", "##AACAA##", "###AAA###", "####D####", "####D####", "####D####", "####D####", "####A####", "#########")
                    .aisle("##AAAAA##", "###B@B###", "###BBB###", "#########", "#########", "#########", "#########", "#########", "#########")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_HSSE_STURDY.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(HEAT_VENT.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                    .where("D", Predicates.frames(GTMaterials.Tungsten))
                    .where("E", Predicates.heatingCoils())
                    .where("F", Predicates.blocks(CASING_LAMINATED_GLASS.get()))
                    .where("G", Predicates.blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                    .where("S", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_sturdy_hsse"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static final MultiblockMachineDefinition SEAWATER_DESALTING_FACTORY = REGISTRATE.multiblock("seawater_desalting_factory", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.DESALTING)
            .tooltips(Component.translatable("desalting_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
                    Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"))
            .recipeModifier(GTRecipeModifiers::ebfOverclock)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("C   C", "C   C", "CGGGC", " GGG ")
                    .aisle("     ", "     ", "GMMMG", "G###G")
                    .aisle("     ", "     ", "GMBMG", "G###G")
                    .aisle("     ", "     ", "GMMMG", "G###G")
                    .aisle("C   C", "C   C", "CGKGC", " GGG ")
                    .where("C", Predicates.frames(GTMaterials.Steel))
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("M", Predicates.heatingCoils())
                    .where("B", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                    .where("G", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(15)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("#", Predicates.blocks(Blocks.WATER))
                    .where(" ", Predicates.any())
                    .build())
            .additionalDisplay((machine, l) -> {
                if (machine.isFormed() && machine instanceof CoilWorkableElectricMultiblockMachine cmachine) {
                    l.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(cmachine.getCoilType().getCoilTemperature() + "K").withStyle(ChatFormatting.RED)));
                }
            })
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static final MultiblockMachineDefinition WATER_POWER_STATION = REGISTRATE.multiblock("water_power_station", WaterPowerStationMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.WATER_POWER)
            .recipeModifier(WaterPowerStationMachine::recipeModifier)
            .tooltips(Component.translatable("water_power_station").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.water_power_station.mechanism"),
                    Component.translatable("ctnh.water_power_station.random").withStyle(ChatFormatting.GREEN))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#BCB#", "#BCB#", "BBBBB", "#BBB#")
                    .aisle("#B#B#", "#BDB#", "BEFEB", "#BGB#").setRepeatable(1, 15)
                    .aisle("#C#C#", "#CDC#", "BEFEB", "#BCB#")
                    .aisle("#CCC#", "#CCC#", "BBHBB", "#B@B#")
                    .where("B", Predicates.frames(GTNNMaterials.ManaSteel))
                    .where("C", Predicates.blocks(MANA_STEEL_CASING.get()))
                    .where("#", Predicates.any())
                    .where("D", Predicates.heatingCoils())
                    .where("E", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("F", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("G", Predicates.abilities(PartAbility.OUTPUT_ENERGY))
                    .where("H", Predicates.abilities(PartAbility.IMPORT_FLUIDS))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/mana_steel_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static final  MultiblockMachineDefinition BIO_REACTOR = REGISTRATE.multiblock("bio_reactor",BioMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.BIO_REACTOR)
            .tooltips(Component.translatable("bio_reactor").withStyle(ChatFormatting.GRAY))
            .recipeModifiers(BioMachine::recipeModifier,GTRecipeModifiers.OC_NON_PERFECT_SUBTICK)
            .appearanceBlock(BIO_REACTOR_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AAAAA", "B###B", "B###B", "B###B", "AAAAA")
                    .aisle("AA@AA", "ABBBA", "ABBBA", "ABBBA", "AAAAA")
                    .where("A", Predicates.blocks(BIO_REACTOR_CASING.get()).setMinGlobalLimited(35)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CLEANROOM_GLASS.get()))
                    .where("#", Predicates.air())
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/bio_reactor_casing"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static final MultiblockMachineDefinition MANA_MIXER = REGISTRATE.multiblock("mana_mixer", holder -> new ManaMachine(holder,4,2))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.MIXER_RECIPES)
            .tooltips(Component.translatable("ctnh.mana_mixer"),
                    Component.translatable("mana_machine").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.advanced_mana_machine.mana_consume"),
                    Component.translatable("ctnh.perfect_overclock"))
            .appearanceBlock(MANA_STEEL_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#EEE#", "#EEE#", "#EEE#", "#EEE#", "#EEE#", "##B##")
                    .aisle("EEEEE", "E#A#E", "E###E", "E#A#E", "E###E", "##B##")
                    .aisle("EEEEE", "EAAAE", "E#A#E", "EAAAE", "E#C#E", "BBCBB")
                    .aisle("EEEEE", "E#A#E", "E###E", "E#A#E", "E###E", "##B##")
                    .aisle("#EEE#", "#E@E#", "#EEE#", "#EEE#", "#EEE#", "##B##")
                    .where("A", Predicates.blocks(ELEMENTIUM_PIPE_CASING.get()))
                    .where("B", Predicates.frames(GTNNMaterials.ManaSteel))
                    .where("C", Predicates.blocks(CASING_MANASTEEL_GEARBOX.get()))
                    .where("D", Predicates.blocks(MANA_STEEL_CASING.get()))
                    .where("#", Predicates.any())
                    .where("E",Predicates.blocks(MANA_STEEL_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .where("F", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(CTNHCore.id("block/casings/mana_steel_casing"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public final static MultiblockMachineDefinition ZENITH_CIRCUIT_ASSEMBLER = REGISTRATE.multiblock("zenith_circult_assembler",holder -> new ZenithMachine(holder,24,25,60,12))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES,CTNHRecipeTypes.RESONANT_MAGICAL_ASSEMBLY)
            .appearanceBlock(CTNHBlocks.ZENITH_CASING_BLOCK)
            .recipeModifiers(ZenithMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.zenith_circut_assember"),
                    Component.translatable("zenith_machine").withStyle(ChatFormatting.DARK_PURPLE),
                    Component.translatable("ctnh.super_mana_machine.mana_consume"),
                    Component.translatable("ctnh.zenith_circut_assember_sp"),
                    Component.translatable("ctnh.zenith_machine_tip"),
                    Component.translatable("ctnh.zenith_waring"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("####PPP####", "####PHP####", "####PPP####", "###########")
                    .aisle("###B#C#B###", "###D###D###", "###D###D###", "####BEB####")
                    .aisle("#BB##C##BB#", "#DD#####DD#", "#DD#####DD#", "###BBEBB###")
                    .aisle("B###CCC###B", "B#########B", "B#########B", "#BBBEEEBBB#")
                    .aisle("BCCCCBCCCCB", "H####A####H", "B#########B", "#EEEEHEEEE#")
                    .aisle("B###CCC###B", "B#########B", "B#########B", "#BBBEEEBBB#")
                    .aisle("#BB##C##BB#", "#DD#####DD#", "#DD#####DD#", "###BBEBB###")
                    .aisle("###B#C#B###", "###D###D###", "###D###D###", "####BEB####")
                    .aisle("####PPP####", "####P@P####", "####PPP####", "###########")
                    .where("A", Predicates.blocks(CTNHBlocks.ZENITH_CASING_GEARBOX.get()))
                    .where("#",Predicates.any())
                    .where("B",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get()))
                    .where("C",Predicates.blocks(CTNHBlocks.MANA_STEEL_CASING.get()))
                    .where("D",Predicates.blocks(CTNHBlocks.DEPTH_FORCE_FIELD_STABILIZING_CASING.get()))
                    .where("E",Predicates.blocks(CTNHBlocks.ALF_STEEL_CASING.get()))
                    .where("H",Predicates.blocks(CTNHBlocks.ZENITH_EYE.get()))
                    .where("P",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get())

                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("@",Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer((CTNHCore.id("block/casings/zenith_casing")), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();

    public final static MultiblockMachineDefinition ZENITH_DISTILLATION_TOWER = REGISTRATE.multiblock("zenith_distillation_tower",holder -> new ZenithMachine(holder,10,25,60,5))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.DISTILLATION_RECIPES)
            .appearanceBlock(CTNHBlocks.ZENITH_CASING_BLOCK)
            .recipeModifiers(ZenithMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.zenith_tower"),
                    Component.translatable("zenith_machine").withStyle(ChatFormatting.DARK_PURPLE),
                    Component.translatable("ctnh.super_mana_machine.mana_consume"),
                    Component.translatable("ctnh.zenith_machine_tip"),
                    Component.translatable("ctnh.zenith_waring"),
                    Component.translatable("ctnh.perfect_overclock"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "BBBBB", "BBEBB", "BEEEB", "BEEEB", "BEEEB", "BDDDB")
                    .aisle("#PPP#", "#PPP#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "BDDDB", "B###B", "E###E", "E###E", "E###E", "DEEED")
                    .aisle("#PAP#", "#P#P#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "#B#B#", "BD#DB", "E###E", "E###E", "E###E", "E###E", "DEEED")
                    .aisle("#P@P#", "#PPP#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "#BBB#", "BDDDB", "B###B", "E###E", "E###E", "E###E", "DEEED")
                    .aisle("#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "BBBBB", "BBEBB", "BEEEB", "BEEEB", "BEEEB", "BDDDB")
                    .where("#",Predicates.any())
                    .where("B",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get()))
                    .where("D",Predicates.blocks(CTNHBlocks.DEPTH_FORCE_FIELD_STABILIZING_CASING.get()))
                    .where("E",Predicates.blocks(BotaniaBlocks.manaGlass))
                    .where("A",Predicates.blocks(CTNHBlocks.ZENITH_EYE.get()))
                    .where("P",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get())
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("@",Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer((CTNHCore.id("block/casings/zenith_casing")), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public final static MultiblockMachineDefinition SCALABLE_RESERVOIR_COMPUTING = REGISTRATE.multiblock("scalable_reservoir_computing",holder -> new ZenithMachine(holder,10,25,60,5))
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.DISTILLATION_RECIPES)
            .appearanceBlock(CTNHBlocks.ZENITH_CASING_BLOCK)
            .recipeModifiers(ZenithMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.computer.a1"),
                    Component.translatable("ctnh.computer.a2").withStyle(ChatFormatting.DARK_PURPLE),
                    Component.translatable("ctnh.computer.a3"),
                    Component.translatable("ctnh.computer.a4"),
                    Component.translatable("ctnh.computer.a5"),
                    Component.translatable("ctnh.computer.a6"),
                    Component.translatable("ctnh.computer.a7"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("YX##########################X#","XWX#########XXXXXXX########XWX","XWX########X#######X#######XWX","#X########X#########X#######X#","##########X#########X#########","##########X#########X#########","##########X#########X#########","##########X#########X#########","##########X#########X#########","##########X#########X#########","###########X#######X##########","############XXXXXXX###########","#X##########################X#","XWX########################XWX","XWX########################XWX","#X##########################XY")
                    .aisle("XVX#########XXXXXXX########XVX","W#W########X#######X#######W#W","W#W#######X#########X######W#W","XVX######X###########X#####XVX","#########X###########X########","#########X###########X########","#########X###########X########","#########X###########X########","#########X###########X########","#########X###########X########","##########X#########X#########","###########X#######X##########","XVX#########XXXXXXX########XVX","W#W########################W#W","W#W########################W#W","XVX########################XVX")
                    .aisle("#XXXXX######XXXXXXX#####XXX#X#","XWX##XX####X#######X###XX##XWX","XWX###XX##X#########X#XX###XWX","#X#####XXX###########XX#####X#","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#######XXX###########XXX######","######XX##X#########X##XX#####","#####XX####X#######X####XX####","#X#XXX######XXXXXXX######XX#X#","XWX########################XWX","XWX########################XWX","#X##########################XY")
                    .aisle("YT##XX######XXXXXXX#####XX##T#","#####XX####X#######X###XX#####","######XX##X#########X#XX######","#T#####XXX###########XX#####T#","#########X###UUSUU###X########","#########X###USUSU###X########","#########X###SURUS###X########","#########X###USUSU###X########","#########X###UUSUU###X########","#######XXX###########XXX######","######XX##X#########X##XX#####","#####XX####X#######X####X#####","#T##XX######XXXXXXX#########T#","##############################","##############################","#T##########################TY")
                    .aisle("YT##########Q#####Q#########T#","###########Q#######Q##########","##########Q#########Q#########","#T#######Q###########Q######T#","########Q####UUSUU####Q#######","########Q#####SRSU####Q#######","########Q####SRPRS####Q#######","########Q#####SRSU####Q#######","########Q####UUSUU####Q#######","#########Q###########Q########","##########Q#########Q#########","###########Q#######Q##########","#T##########Q#####Q#########T#","#############QQQQQ############","##############################","#T##########################TY")
                    .aisle("YT##XX######XXXXXXX#####XX##T#","#####XX####X#######X###XX#####","######XX##X#########X#XX######","#T#####XXX###########XX#####T#","#########X###UUSUU###X########","#########X###USUSU###X########","#########X###S#RUS###X########","#########X###USUSU###X########","#########X###UUSUU###X########","#######XXX###########XXX######","######XX##X#########X##XX#####","#####XX####X#######X####X#####","#T##XX######XXXXXXX#########T#","##############################","##############################","#T##########################TY")
                    .aisle("YX#XXX######XXXXXXX#####XXX#X#","XWX##XX####X#######X###XX##XWX","XWX###XX##X#########X#XX###XWX","#X#####XXX###########XX#####X#","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#########X###UUUUU###X########","#######XXX###########XXX######","######XX##X#########X##XX#####","#####XX####X#######X####XX####","#X#XXX######XXXXXXX######XX#X#","XWX########################X#X","XWX########################X#X","#X##########################XY")
                    .aisle("XVX########################XVX","W#W#########XXXXXXX########W#W","W#W########X#######X#######W#W","XVX#######X#########X######XVX","#########X##########X#########","#########X##########X#########","#########X##########X#########","#########X##########X#########","#########X##########X#########","##########X#########X#########","###########X#######X##########","############XXXXXXX###########","XVX##########XXXXX#########XVX","W#W########################W#W","W#W########################W#W","XVX########################XVX")
                    .aisle("#X##########################X#","XWX########################X#X","XWX#########XXXXXXX########X#X","#X#########XOOO@OOOX########X#","##########XOOOOOOOOOX#########","#########XOOOOOOOOOOX#########","#########XOOOOOOOOOOX#########","#########XOOOOOOOOOOX#########","#########XOOOOOOOOOOX#########","##########XOOOOOOOOOX#########","###########XOOOOOOOX##########","############XXXXXXX###########","#X##########################X#","XWX########################XWX","XWX########################XWX","PX##########################XY")
                    .where("Y",Predicates.any())
                    .where("X",Predicates.blocks(GTBlocks.HIGH_POWER_CASING.get()))
                    .where("#",Predicates.any())
                    .where("W",Predicates.blocks(IRON_BARS))
                    .where("V",Predicates.blocks(GTBlocks.CASING_ASSEMBLY_CONTROL.get()))
                    .where("U",Predicates.blocks(BotaniaBlocks.alfglassPane))
                    .where("T",Predicates.blocks(GTBlocks.FUSION_COIL.get()))
                    .where("S",Predicates.blocks(CTNHBlocks.RESERVOIR_COMPUTING_CASING.get()))
                    .where("R",Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("Q",Predicates.blocks(CTNHBlocks.ZENITH_CASING_BLOCK.get()))
                    .where("P",Predicates.blocks(CTNHBlocks.CASING_ADVANCED_HYPER.get()))
                    .where("O",Predicates.blocks(BotaniaBlocks.manaGlass))
                    .where("@",Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer((CTNHCore.id("block/casings/zenith_casing")), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static MultiblockMachineDefinition SEASON_REACTOR = REGISTRATE.multiblock("season_reactor", season_reactor::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.SEASON_STEAM_RECIPES)
            // .appearanceBlock(GTBlocks.CASING_BRONZE_BRICKS)
            .tooltips(Component.translatable("ctnh.season.a1"),
                    Component.translatable("ctnh.season.a2"),
                    Component.translatable("ctnh.season.a3"))
            .recipeModifier(season_reactor::recipeModifier)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("YXY","XWX","KKK")
                    .aisle("XVX","WUW","KOK")
                    .aisle("Y@Y","XWX","KKK")
                    .where("Y",Predicates.blocks(BotaniaBlocks.livingwood))
                    .where("#",Predicates.any())
                    .where("W",Predicates.blocks(BotaniaBlocks.manaGlass))
                    .where("V",Predicates.blocks(DIRT))
                    .where("O",Predicates.blocks((BotaniaBlocks.manaPylon)))
                    .where("K",Predicates.blocks(BotaniaBlocks.livingrockBrickStairs))
                    .where("U",Predicates.blocks(BotaniaFlowerBlocks.pureDaisy))
                    .where("X", Predicates.abilities(CTPPPartAbility.OUTPUT_KINETIC).setExactLimit(1)
                            .or(Predicates.blocks(BotaniaBlocks.livingrockBrick))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                    )
                    .where("@",Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(ResourceLocation.tryParse("botania:block/polished_livingrock"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();

    public final static MultiblockMachineDefinition SILICA_ROCK_FUEL_REFINERY = REGISTRATE.multiblock("silica_rock_fuel_refinery", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(CTNHRecipeTypes.SILICA_ROCK_FUEL_REFINERY)
            .appearanceBlock(CTNHBlocks.CASING_NAQUADAH_BLOCK)
            .recipeModifiers(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("##AAA##", "##ABA##", "##AAA##", "###B###", "###B###", "###A###")
                    .aisle("###A###", "##AAA##", "###C###", "##ADA##", "###A###", "###A###")
                    .aisle("A##A##A", "AAADAAA", "A#ACA#A", "#AACAA#", "##ADA##", "###C###")
                    .aisle("AAAAAAA", "BADDDAB", "ACCDCCA", "BDCDCDB", "BADDDAB", "AACCCAA")
                    .aisle("A##A##A", "AAADAAA", "A#ACA#A", "#AACAA#", "##ADA##", "###C###")
                    .aisle("###A###", "##AAA##", "###C###", "##ADA##", "###A###", "###A###")
                    .aisle("##AAA##", "##A@A##", "##AAA##", "###B###", "###B###", "###A###")
                    .where("#", Predicates.any())
                    .where("A", Predicates.blocks(CASING_NAQUADAH_BLOCK.get()))
                    .where("B", Predicates.frames(GTMaterials.NaquadahEnriched))
                    .where("C", Predicates.blocks(ANNIHILATE_CORE_MKI.get()))
                    .where("D", Predicates.blocks(PLASMA_COOLED_CORE.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer((CTNHCore.id("block/casings/nq_casing")), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static MultiblockMachineDefinition ALTER = REGISTRATE.multiblock("alter", AlterLogic::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ALTER)
            // .appearanceBlock(GTBlocks.CASING_BRONZE_BRICKS)
            .recipeModifiers(ZenithMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .tooltips(Component.translatable("ctnh.alter.tips1"),
                    Component.translatable("ctnh.alter.tips2"),
                    Component.translatable("ctnh.alter.tips3"),
                    Component.translatable("ctnh.alter.tips4"))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "B#####B", "B#####B", "B#####B", "C#####C", "OEEEEEO", "#######", "#######", "#######")
                    .aisle("A#####A", "#ACCCA#", "#######", "#######", "#######", "E#####E", "##EEE##", "#######", "#######")
                    .aisle("A#####A", "#C###C#", "##CGC##", "#######", "#######", "E#####E", "#E###E#", "###E###", "#######")
                    .aisle("A#####A", "#C###C#", "##GAG##", "###H###", "###B###", "E##B##E", "#E#B#E#", "##EEE##", "###D###")
                    .aisle("A#####A", "#C###C#", "##CGC##", "#######", "#######", "E#####E", "#E###E#", "###E###", "#######")
                    .aisle("A#####A", "#ACCCA#", "#######", "#######", "#######", "E#####E", "##EEE##", "#######", "#######")
                    .aisle("AAAIAAA", "B#####B", "B#####B", "B#####B", "C#####C", "OEEEEEO", "#######", "#######", "#######")
                    .where("A", Predicates.blocks(BloodMagicBlocks.BLANK_RUNE.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where("B", Predicates.blocks(CHAIN))
                    .where("#", Predicates.any())
                    .where("C", Predicates.blocks(BloodMagicBlocks.DUNGEON_ORE.get()))
                    .where("D", Predicates.blocks(LAMPS.get(DyeColor.PURPLE).get()))
                    .where("O", Predicates.blocks(LAMPS.get(DyeColor.RED).get()))
                    .where("E", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_1.get()))
                    .where("F", Predicates.blocks(MYCELIUM))
                    .where("G", Predicates.blocks(BloodMagicBlocks.OBSIDIAN_PATH.get())
                            .or(Predicates.blocks(BloodMagicBlocks.CAPACITY_RUNE.get()))
                            .or(Predicates.blocks(BloodMagicBlocks.CAPACITY_RUNE_2.get()))
                    )

                    .where("H", Predicates.blocks(SOUL_LANTERN))
                    .where("I",Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer(BloodMagic.rl("block/blankrune"), GTCEu.id("block/multiblock/generator/large_steam_turbine"), false)
            .register();
    public static MultiblockMachineDefinition EYE_OF_QUASAR = REGISTRATE.multiblock("eye_of_quasar", Quasar_Eye::new)
            .rotationState(RotationState.ALL)
            .recipeType(CTNHRecipeTypes.QUASAR_EYE)
            .generator(true)
            .recipeModifier(Quasar_Eye::recipeModifier,true)
            .tooltips(Component.translatable("ctnh.quarsar.tips1"),
                    Component.translatable("ctnh.quarsar.tips2"),
                    Component.translatable("ctnh.quarsar.tips6"),
                    Component.translatable("ctnh.quarsar.tips3"),
                    Component.translatable("ctnh.quarsar.tips7"),
                    Component.translatable("ctnh.quarsar.tips4"),
                    Component.translatable("ctnh.quarsar.tips5")
                    )
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "#############CCCCC#############", "#############CCCCC#############", "###########BBCC#CCBB###########", "#############CCCCC#############", "#############CCCCC#############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DCD##############", "###########DDDCCCDDD###########", "#########BBEECCFCCEEBB#########", "###########DDDCCCDDD###########", "##############DCD##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "#########DDD##GGG##DDD#########", "#######BBEEEBBGFGBBEEEBB#######", "#########DDD##GGG##DDD#########", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "#######DDD####GGG####DDD#######", "######BEEEBB##GFG##BBEEEB######", "#######DDD####GGG####DDD#######", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "######DD######GGG######DD######", "#####BEEBB####GFG####BBEEB#####", "######DD######GGG######DD######", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "#####DD#######GGG#######DD#####", "####BEEB######GFG######BEEB####", "#####DD#######GGG#######DD#####", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################")
                    .aisle("##############D#D##############", "##############D#D##############", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "####DD########H#H########DD####", "###BEEB########F########BEEB###", "####DD########H#H########DD####", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################")
                    .aisle("##############D#D##############", "##############D#D##############", "##############EBE##############", "##############EEE##############", "##############EBE##############", "##############E#E##############", "##############D#D##############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############I###############", "####D#########III#########D####", "###BEB#######IIFII#######BEB###", "####D#########III#########D####", "###############I###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################")
                    .aisle("###############################", "##############DBD##############", "##############DED##############", "##############DED##############", "##############DBD##############", "##############D#D##############", "##############D#D##############", "###############################", "###############################", "##############DBD##############", "###############B###############", "###############################", "###############################", "###DD#####################DD###", "##BEEB#########F#########BEEB##", "###DD#####################DD###", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################")
                    .aisle("###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################", "##############D#D##############", "##############D#D##############", "##############DBD##############", "##############DBD##############", "###############J###############", "###############J###############", "###############J###############", "###D###########J###########D###", "##BEB######JJJJFJJJJ######BEB##", "###D###########J###########D###", "###############J###############", "###############J###############", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################")
                    .aisle("###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############J###############", "###############################", "###############################", "###############################", "##DD#######################DD##", "#BEEB#####J#########J#####BEEB#", "##DD#######################DD##", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############")
                    .aisle("###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "##D#########################D##", "#BEB######J#########J######BEB#", "##D#########################D##", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############")
                    .aisle("#############CCCCC#############", "##############DCD##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############I###############", "###############################", "###############J###############", "###############################", "###############################", "#C###########################C#", "#CD#########################DC#", "#CCB####I#J#########J#I####BCC#", "#CD#########################DC#", "#C###########################C#", "###############################", "###############################", "###############J###############", "###############################", "###############I###############", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DCD##############", "#############CCCCC#############")
                    .aisle("#######DD####CCCCC####DD#######", "#######DDD#DDDCCCDDD#DDD#######", "########EDDD##GGG##DDDE########", "#######DED####GGG####DED#######", "######DDED####GGG####DEDD######", "#####DD#ED####GGG####DE#DD#####", "####DD##DDD###H#H###DDD##DD####", "####D#####D###III###D#####D####", "###DD#####D#########D#####DD###", "###D#####DD####J####DD#####D###", "##DD#######################DD##", "##D#########################D##", "#CD#########################DC#", "#CCGGGGHI#############IHGGGGCC#", "#CCGGGG#I#J####K####J#I#GGGGCC#", "#CCGGGGHI#############IHGGGGCC#", "#CD#########################DC#", "##D#########################D##", "##DD#######################DD##", "###D###########J###########D###", "###DD#####################DD###", "####D#########III#########D####", "####DD########H#H########DD####", "#####DD#######GGG#######DD#####", "######DD######GGG######DD######", "#######DDD####GGG####DDD#######", "#########DDD##GGG##DDD#########", "###########DDDCCCDDD###########", "#############CCCCC#############")
                    .aisle("###########BBCC#CCBB###########", "#########BBEECC#CCEEBB#########", "#######BBEEEBBG#GBBEEEBB#######", "######BEEEBB##G#G##BBEEEB######", "#####BEEBB####G#G####BBEEB#####", "####BEEB######G#G######BEEB####", "###BEEB#################BEEB###", "###BEB#######II#II#######BEB###", "##BEEB####BB#######BB####BEEB##", "##BEB####BBJJJJ#JJJJBB####BEB##", "#BEEB####BJ#########JB####BEEB#", "#BEB######J#########J######BEB#", "#CCB####I#J#########J#I####BCC#", "#CCGGGG#I#J#########J#I#GGGGCC#", "#LFFFFFFFFF###KMK###FFFFFFFFFL#", "#CCGGGG#I#J#########J#I#GGGGCC#", "#CCB####I#J#########J#I####BCC#", "##EB######J#########J######BEB#", "##EEB#####J#########J#####BEEB#", "##BEB######JJJJ#JJJJ######BEB##", "###EEB###################BEEB##", "###BEB#######II#II#######BEB###", "####EEB#################BEEB###", "#####EEB######G#G######BEEB####", "######EEBB####G#G####BBEEB#####", "#######EEEBB##G#G##BBEEEB######", "#########EEEBBG#GBBEEEBB#######", "###########EECC#CCEEBB#########", "#############CC#CCBB###########")
                    .aisle("#######DD####CCCCC####DD#######", "#######DDD#DDDCCCDDD#DDD#######", "########EDDD##GGG##DDDE########", "#######DED####GGG####DED#######", "######DDED####GGG####DEDD######", "#####DD#ED####GGG####DE#DD#####", "####DD##DDD###H#H###DDD##DD####", "####D#####D###III###D#####D####", "###DD#####D#########D#####DD###", "###D#####DD####J####DD#####D###", "##DD#######################DD##", "##D#########################D##", "#CD#########################DC#", "#CCGGGGHI#############IHGGGGCC#", "#CCGGGG#I#J####K####J#I#GGGGCC#", "#CCGGGGHI#############IHGGGGCC#", "#CD#########################DC#", "##D#########################D##", "##DD#######################DD##", "###D###########J###########D###", "###DD#####################DD###", "####D#########III#########D####", "####DD########H#H########DD####", "#####DD#######GGG#######DD#####", "######DD######GGG######DD######", "#######DDD####GGG####DDD#######", "#########DDD##GGG##DDD#########", "###########DDDCCCDDD###########", "#############CCCCC#############")
                    .aisle("#############CCCCC#############", "##############DCD##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############I###############", "###############################", "###############J###############", "###############################", "###############################", "#C###########################C#", "#CD#########################DC#", "#CCB####I#J#########J#I####BCC#", "#CD#########################DC#", "#C###########################C#", "###############################", "###############################", "###############J###############", "###############################", "###############I###############", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DCD##############", "#############CCCCC#############")
                    .aisle("###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "##D#########################D##", "#BEB######J#########J######BEB#", "##D#########################D##", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############")
                    .aisle("###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############J###############", "###############################", "###############################", "###############################", "##DD#######################DD##", "#BEEB#####J#########J#####BEEB#", "##DD#######################DD##", "###############################", "###############################", "###############################", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############")
                    .aisle("###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################", "##############D#D##############", "##############D#D##############", "##############DBD##############", "##############DBD##############", "###############J###############", "###############J###############", "###############J###############", "###D###########J###########D###", "##BEB######JJJJFJJJJ######BEB##", "###D###########J###########D###", "###############J###############", "###############J###############", "###############J###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################")
                    .aisle("###############################", "##############DBD##############", "##############DED##############", "##############DED##############", "##############DBD##############", "##############D#D##############", "##############D#D##############", "###############################", "###############################", "##############DBD##############", "###############B###############", "###############################", "###############################", "###DD#####################DD###", "##BEEB#########F#########BEEB##", "###DD#####################DD###", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################")
                    .aisle("##############D#D##############", "##############D#D##############", "##############EBE##############", "##############EEE##############", "##############EBE##############", "##############E#E##############", "##############D#D##############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############I###############", "####D#########III#########D####", "###BEB#######IIFII#######BEB###", "####D#########III#########D####", "###############I###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "###############B###############", "###############################", "###############################")
                    .aisle("##############D#D##############", "##############D#D##############", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "####DD########H#H########DD####", "###BEEB########F########BEEB###", "####DD########H#H########DD####", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "#####DD#######GGG#######DD#####", "####BEEB######GFG######BEEB####", "#####DD#######GGG#######DD#####", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "######DD######GGG######DD######", "#####BEEBB####GFG####BBEEB#####", "######DD######GGG######DD######", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "#######DDD####GGG####DDD#######", "######BEEEBB##GFG##BBEEEB######", "#######DDD####GGG####DDD#######", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "#########DDD##GGG##DDD#########", "#######BBEEEBBGFGBBEEEBB#######", "#########DDD##GGG##DDD#########", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "##############DED##############", "##############DED##############", "##############DCD##############", "###########DDDCCCDDD###########", "#########BBEECCFCCEEBB#########", "###########DDDCCCDDD###########", "##############DCD##############", "##############DED##############", "##############DED##############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############B###############", "###############B###############", "#############CCCCC#############", "#############CCCCC#############", "###########BBCC@CCBB###########", "#############CCCCC#############", "#############CCCCC#############", "###############B###############", "###############B###############", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .aisle("###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################", "###############################")
                    .where("#", Predicates.any())
                    .where("B", Predicates.blocks(QUASAR_ENERGY_STABILIZATION_CASING.get()))
                    .where("C", Predicates.blocks(DEPTH_FORCE_FIELD_STABILIZING_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.OUTPUT_LASER))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                    )

                    .where("D", Predicates.blocks(FUSION_CASING_MK3.get()))
                    .where("E", Predicates.blocks(CASING_NAQUADAH_BLOCK.get()))
                    .where("F", Predicates.blocks(BotaniaBlocks.biomeBrickForest))
                    .where("G", Predicates.blocks(COIL_ULTRA_MANA.get()))
                    .where("H", Predicates.blocks(MACHINE_CASING_ZPM.get()))
                    .where("I", Predicates.blocks(TERRA_STEEL_CASING.get()))
                    .where("J", Predicates.blocks(ALF_STEEL_CASING.get()))
                    .where("K", Predicates.blocks(QUASAR_ENERGY_STABILIZATION_CASING.get()))
                    .where("L", Predicates.blocks(CASING_NAQUADAH_BLOCK.get()))
                    .where("M", Predicates.blocks(FUSION_COIL.get()))
                    .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                    .build())
            .workableCasingRenderer((CTNHCore.id("block/casings/depth_force_field_stabilizing_casing")), GTCEu.id("block/multiblock/fusion_reactor"), false)
            .register();
    public static void init() {

    }
}