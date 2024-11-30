package io.github.cpearl0.ctnhcore.registry;

import appeng.core.definitions.AEBlocks;
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
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.MultiblockTankMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.CopperBlockSet;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.arbor.gtnn.data.GTNNMaterials;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.coldsweat.UnderfloorHeatingSystemTempModifier;
import io.github.cpearl0.ctnhcore.common.block.CTNHFusionCasingType;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.*;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.ChemicalGeneratorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.NaqReactorMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotovoltaicPowerStationMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.WindPowerArrayMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic.MeadowMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.DemonWillMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.ManaLargeTurbineMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.magic.ManaMachine;
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
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
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
                    .where("B", Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
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
                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    .or(Predicates.abilities(PartAbility.OUTPUT_LASER).setPreviewCount(1)))
            .where("B", Predicates.blocks(SUPERCONDUCTING_COIL.get()))
            .where("C", Predicates.blocks(MATERIAL_BLOCKS.get(TagPrefix.block,GTMaterials.SamariumMagnetic).get()))
            .where("D", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKI.get()))
            .where("E", Predicates.blocks(CTNHBlocks.CASING_NAQUADAH_BLOCK.get()))
            .where("F", Predicates.blocks(FUSION_GLASS.get()))
            .where("G", Predicates.blocks(CTNHBlocks.ANNIHILATE_CORE_MKII.get()))
            .where("@", Predicates.controller(Predicates.blocks(definition.get())))
            .build()
        )
            .tooltips(
            Component.translatable("gtceu.universal.tooltip.base_production_eut", GTValues.V[GTValues.UIV]),
                    Component.translatable("ctnh.machine.naq_reactor_machine.tooltip.boost_mk2",GTValues.V[GTValues.UIV] * 16),
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
            .aisle("A                                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      D                   D      ", "      C                   C      ", "      E                   E      ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      F                   F      ", "      C                   C      ", "      I       JJJJJ       I      ", "  D  BKB  D           D  BKB  D  ", "  C  GKG  C           C  GKG  C  ", " CE B K B EC         CE B K B EC ", "BCE F K F ECB       BCE F K F ECB", "  C B K B C           C B K B C  ", "  D  GKG  D           D  GKG  D  ", "     BKB                 BKB     ", "      I                   Z      ", "      C                   C      ", "      F                   F      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             LBLBLBL             ", "           JJJMMMMMJJJ           ", "      B       JLJLJ       B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "          BMMM     MMMB          ", "           JLJ     JLJ           ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               BFB               ", "              E H E              ", "                                 ", "                                 ", "              FBHBF              ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "              EEEEE              ", "                                 ", "        B               B        ", "      D                   D      ", "      C       NNNNN       C      ", "     CEC                 CEC     ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                H                ", "             OO   OO             ", "           FE       EF           ", "           E         E           ", "       JM               MJ       ", "        J               J        ", "             N     N             ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               OOO               ", "            O       O            ", "           E         E           ", "                                 ", "       JM               MJ       ", "        L               L        ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               M M               ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "               O O               ", "             O     O             ", "           O         O           ", "                                 ", "       P                 L       ", "       JM               MJ       ", "        J               J        ", "           N         N           ", "                                 ", "                K                ", "                I                ", "                F                ", "                F                ", "                I                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("              MMMMM              ", "              M   M              ", "              M   M              ", "              M   M              ", "              MMMMM              ", "              M M M              ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "               QMQ               ", "              MR RM              ", "              I   I              ", "              F   F              ", "              F   F              ", "              I   I              ", "              O S O              ", "                S                ", "           O    S    O           ", "          E     S     E          ", "       B E      S      E B       ", "      JM                 MJ      ", "       J                 J       ", "         FN           NF         ", "                                 ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("             MMMMMMM             ", "               OOO               ", "               OOO               ", "               OOO               ", "              MOOOM              ", "               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                T                ", "                T                ", "                T                ", "                T                ", "                T                ", "                U                ", "                G                ", "                G                ", "                G                ", "                G                ", "               E E               ", "               F F               ", "              QF FQ              ", "             MR   RM             ", "             K     K             ", "             G  S  G             ", "             G  S  G             ", "             K  S  K             ", "             O SVS O             ", "            O  SVS  O            ", "               SVS               ", "         BE    SVS    EB         ", "       P       SVS       L       ", "      JM        S        MJ      ", "       L                 L       ", "         BN           NB         ", "                W                ", "              K   K              ", "              G   G              ", "              G   G              ", "              K   K              ", "               W W               ", "               X X               ", "               X X               ", "               W W               ", "                U                ", "                Y                ", "                Y                ", "                U                ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("            MMMMMMMMM            ", "               OOO               ", "               OOO               ", "               OOO               ", "              MOOOM              ", "             MMMMMMM             ", "              M F M              ", "                                 ", "                F                ", "                C                ", "                C                ", "               TUT               ", "               TUT               ", "               TUT               ", "               TUT               ", "               TUT               ", "               UUU               ", "               GFG               ", "               GFG               ", "               GFG               ", "               GFG               ", "                I                ", "              M   M              ", "              M   M              ", "                                 ", "                S                ", "               SVS               ", "               SVS               ", "               SVS               ", "              SVVVS              ", "           HO SVVVS OH           ", "              SVVVS              ", "         FE   SVVVS   EF         ", "       B H     SVS     H B       ", "      JM       SVS       MJ      ", "       J        S        J       ", "         HN     W     NH         ", "               WWW               ", "             K     K             ", "             I     I             ", "             F     F             ", "             F     F             ", "             I     I             ", "                                 ", "                                 ", "                                 ", "               UUU               ", "               YFY               ", "               YFY               ", "               UUU               ", "                U                ", "                C                ", "                C                ", "                F                ")
            .aisle("             MMMMMMM             ", "               OOO               ", "               O@O               ", "               OOO               ", "              MOOOM              ", "               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                T                ", "                T                ", "                T                ", "                T                ", "                T                ", "                U                ", "                G                ", "                G                ", "                G                ", "                G                ", "               E E               ", "               F F               ", "              QF FQ              ", "             MR   RM             ", "             K     K             ", "             G  S  G             ", "             G  S  G             ", "             K SVS K             ", "             O SVS O             ", "            O  SVS  O            ", "               SVS               ", "         BE    SVS    EB         ", "       P        S        L       ", "      JM        S        MJ      ", "       L                 L       ", "         BN           NB         ", "                W                ", "              K   K              ", "              G   G              ", "              G   G              ", "              K   K              ", "               W W               ", "               X X               ", "               X X               ", "               W W               ", "                U                ", "                Y                ", "                Y                ", "                U                ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("              MMMMM              ", "              M   M              ", "              M   M              ", "              M   M              ", "              MMMMM              ", "              M M M              ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "               QMQ               ", "              MR RM              ", "              I   I              ", "              F   F              ", "              F   F              ", "              I S I              ", "              O S O              ", "                S                ", "           O    S    O           ", "          E     S     E          ", "       B E             E B       ", "      JM                 MJ      ", "       J                 J       ", "         FN           NF         ", "                                 ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("               MMM               ", "                                 ", "                                 ", "                                 ", "                                 ", "                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               M M               ", "               K K               ", "               G G               ", "               G G               ", "               K K               ", "               O O               ", "             O     O             ", "           O         O           ", "                                 ", "       P                 L       ", "       JM               MJ       ", "        J               J        ", "           N         N           ", "                                 ", "                K                ", "                I                ", "                F                ", "                F                ", "                I                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                M                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               OOO               ", "            O       O            ", "           E         E           ", "                                 ", "       JM               MJ       ", "        L               L        ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                H                ", "             OO   OO             ", "           FE       EF           ", "           E           E         ", "       JM               MJ       ", "        J               J        ", "             N     N             ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "              EEEEE              ", "                                 ", "        B               B        ", "      D                   D      ", "      C       NNNNN       C      ", "      E                   E      ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               BFB               ", "              E H E              ", "                                 ", "                                 ", "              FBHBF              ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "          BMMM     MMMB          ", "           JLJ     JLJ           ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             LBLBLBL             ", "           JJJMMMMMJJJ           ", "      B       JLJLJ       B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      F                   F      ", "      C                   C      ", "      I       JJJJJ       I      ", "  D  BKB  D           D  BKB  D  ", "  C  GKG  C           C  GKG  C  ", " CE B K B EC         CE B K B EC ", "BCE F K F ECB       BCE F K F ECB", "  C B K B C           C B K B C  ", "  D  GKG  D           D  GKG  D  ", "     BKB                 BKB     ", "      Z                   I      ", "      C                   C      ", "      F                   F      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "      G                   G      ", "  C  H H  C           C  H H  C  ", "  C       C           C       C  ", "     H H                 H H     ", "      G                   G      ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "  B   F   B           B   F   B  ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      D                   D      ", "      C                   C      ", "     CEC                 CEC     ", "    BCECB               BCECB    ", "      C                   C      ", "      D                   D      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      C                   C      ", "      C                   C      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
            .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      B                   B      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                A")
            .where("A", Predicates.blocks(Blocks.WHITE_CONCRETE))
            .where(" ", Predicates.any())
            .where("B", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_STAIRS.get()))
            .where("C", Predicates.blocks(BloodMagicBlocks.DUNGEON_BRICK_WALL.get()))
            .where("D", Predicates.blocks(Blocks.NETHER_BRICK_FENCE))
            .where("E", Predicates.blocks(Blocks.BLACK_CONCRETE))
            .where("F", Predicates.blocks(Blocks.END_ROD))
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
                .or(Predicates.abilities(PartAbility.INPUT_KINETIC)))
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
            .recipeModifiers(FermentingTankMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
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
            .recipeModifier((machine,recipe, params, result) -> {
                var newrecipe =  FermentingTankMachine.recipeModifier(machine,recipe,params,result);
                return GTRecipeModifiers.accurateParallel(machine,newrecipe,8,false).getFirst();
            })
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
    public final static MultiblockMachineDefinition DIGESTION_TANK = REGISTRATE.multiblock("digestion_tank", DigestionTankMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.DIGESTING)
            .tooltips(Component.translatable("digestion_tank_introduction").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.digestion_tank.bio_growth_mechanism").withStyle(ChatFormatting.GREEN),
                    Component.translatable("ctnh.digestion_tank.bio_growth_temperature"))
            .recipeModifiers(DigestionTankMachine::recipeModifier,GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
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
                .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_STEEL_GEARBOX.get()))
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
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_STEEL_GEARBOX.get()))
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
                    .where("B", Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1)
                            .or(Predicates.abilities(PartAbility.ROTOR_HOLDER).setExactLimit(1)))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STAINLESS_STEEL_GEARBOX.get()))
                    .where("S", Predicates.blocks(CTNHBlocks.ALF_STEEL_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
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
                    .where("I", Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
                    .where("X", Predicates.blocks(CASING_STAINLESS_CLEAN.get()).setMinGlobalLimited(158)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where("H", Predicates.abilities(PartAbility.MUFFLER))
                    .where("C", Predicates.heatingCoils())
                    .where("#", Predicates.air())
                    .build()
            )
            .recoveryItems(
                    () -> new ItemLike[]{GTItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
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
            .compassSections(GTCompassSections.TIER[MV])
            .compassNodeSelf()
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
            .compassSections(GTCompassSections.TIER[EV])
            .compassNodeSelf()
            .register();
    //Come from GTCA
    public static final MultiblockMachineDefinition MEGA_LCR = REGISTRATE
            .multiblock("mega_lcr", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.LARGE_CHEMICAL_RECIPES)
            .appearanceBlock(CASING_PTFE_INERT)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
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
                        () -> new ItemLike[]{GTItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
                .workableCasingRenderer(casingTexture, overlayModel)
                .tooltips(
                        Component.translatable("gtceu.universal.tooltip.base_production_eut", V[tier]),
                        tier > EV ?
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_extreme",
                                        V[tier] * 4) :
                                Component.translatable("gtceu.machine.large_combustion_engine.tooltip.boost_regular",
                                        V[tier] * 3))
                .compassSections(GTCompassSections.TIER[EV])
                .compassNode("chemical_generator")
                .register();
    }
    public static void init() {

    }
}
