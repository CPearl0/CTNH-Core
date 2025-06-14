package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.api.registry.registrate.MultiblockMachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.*;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.machine.simple.SimpleComputationMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CircuitBusPartMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CompilerMachine;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.DroneHolderMachine;
import io.github.cpearl0.ctnhcore.common.machine.simple.DigitalWosMachine;
import io.github.cpearl0.ctnhcore.common.machine.simple.HighPerformanceComputerMachine;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.OUT;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class CTNHMachines {
    static {
        REGISTRATE.creativeModeTab(() -> CTNHCreativeModeTabs.MACHINE);
    }

    public static final MachineDefinition[] CIRCUIT_BUS = registerTieredMachines("circuit_bus",
            CircuitBusPartMachine::new,
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " Circuit Bus")
                    .rotationState(RotationState.ALL)
                    .abilities(CTNHPartAbility.CIRCUIT)
                    .renderer(() -> new OverlayTieredMachineRenderer(tier, GTCEu.id("block/machine/part/item_bus.import")))
                    .register(),
            GTMachineUtils.ALL_TIERS);
    public static final MachineDefinition DRONEHOLDER = GTRegistration.REGISTRATE.machine("drone_holder", DroneHolderMachine::new)
                .langValue("drone Holder")
            .tier(UV)
            .rotationState(RotationState.ALL)
            .abilities(CTNHPartAbility.Drone)
            .renderer(() -> new OverlayTieredActiveMachineRenderer(UV, GTCEu.id("block/machine/part/object_holder"),
                    GTCEu.id("block/machine/part/object_holder_active")))
            .register();
    public static final MachineDefinition[] COMPILERMACHINE = registerTieredMachines("neuro_compiler", CompilerMachine::new,
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " Neuro Compiler")
                    .rotationState(RotationState.ALL)
                    .abilities(CTNHPartAbility.COMPILER)
                    .renderer(() -> new OverlayTieredMachineRenderer(tier, GTCEu.id("block/machine/part/item_bus.import")))
                    .register(),
            GTMachineUtils.ALL_TIERS);

    public static final MachineDefinition[] PERSONAL_COMPUTER = registerSimpleComputationMachines("personal_computer",
            CTNHRecipeTypes.PERSONAL_COMPUTER);

    public static final MachineDefinition[] PARALLEL_HATCH = registerTieredMachines("parallel_hatch",
            ParallelHatchPartMachine::new,
            (tier, builder) -> builder
                    .langValue(switch (tier) {
                        case UHV -> "Epic";
                        case UEV -> "Epic";
                        case UIV -> "Epic";
                        case UXV -> "Legendary";
                        case OpV -> "Eternal";
                        case MAX -> "MAX";
                        default -> "Simple"; // Should never be hit.
                    } + " Parallel Control Hatch")
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.PARALLEL_HATCH)
                    .workableTieredHullRenderer(GTCEu.id("block/machines/parallel_hatch_mk" + (tier - 4)))
                    .tooltips(Component.translatable("gtceu.machine.parallel_hatch_mk" + tier + ".tooltip"))
                    .register(),
            UHV, UEV, UIV, UXV, OpV, MAX);
    public static final MachineDefinition[] ENERGY_OUTPUT_HATCH_4A_LOWER = registerTieredMachines("energy_output_hatch_4a",
            (holder, tier) -> new EnergyHatchPartMachine(holder, tier, OUT, 4),
            (tier, builder) -> builder
                    .langValue(VNF[tier] + " 4A Dynamo Hatch")
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.OUTPUT_ENERGY)
                    .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                                    FormattingUtil.formatNumbers(V[tier]), VNF[tier]),
                            Component.translatable("gtceu.universal.tooltip.amperage_out", 4),
                            Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                    FormattingUtil
                                            .formatNumbers(EnergyHatchPartMachine.getHatchEnergyCapacity(tier, 4))),
                            Component.translatable("gtceu.machine.energy_hatch.output_hi_amp.tooltip"))
                    .overlayTieredHullRenderer("energy_hatch.output_4a")
                    .register(),
            GTValues.tiersBetween(LV, HV));
    public static final MachineDefinition[] ROTOR_HOLDER_EXTEND = registerTieredMachines("rotor_holder",
            RotorHolderPartMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Rotor Holder".formatted(VNF[tier]))
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.ROTOR_HOLDER)
                    .renderer(() -> new RotorHolderMachineRenderer(tier))
                    .tooltips(LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 0),
                            LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 1),
                            Component.translatable("gtceu.universal.disabled"))
                    .register(),
            GTValues.tiersBetween(ULV, MV));
    public static final MachineDefinition[] DIGITAL_WELL_OF_SUFFER = registerTieredMachines("digital_well_of_suffer",
            (holder, tier) -> new DigitalWosMachine(holder,tier,(tiers) -> tiers * 32000),
            (tier,builder) -> builder
                    .langValue("%s Digital Well of Suffer".formatted(VNF[tier]))
                    .recipeType(CTNHRecipeTypes.DIGITAL_WELL_OF_SUFFER)
                    .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id("digital_well_of_suffer"),CTNHRecipeTypes.DIGITAL_WELL_OF_SUFFER))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeModifier(DigitalWosMachine::recipeModifier)
                    .workableTieredHullRenderer(GTCEu.id("block/machines/digital_well_of_suffer"))
                    .register(),
            GTValues.tiersBetween(LV,UV));
    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for (int tier : tiers) {
            var register = REGISTRATE
                    .machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static MultiblockMachineDefinition[] registerTieredMultis(String name,
                                                                     BiFunction<IMachineBlockEntity, Integer, MultiblockControllerMachine> factory,
                                                                     BiFunction<Integer, MultiblockMachineBuilder, MultiblockMachineDefinition> builder,
                                                                     int... tiers) {
        MultiblockMachineDefinition[] definitions = new MultiblockMachineDefinition[GTValues.TIER_COUNT];
        for (int tier : tiers) {
            MultiblockMachineBuilder register = REGISTRATE.multiblock(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name, holder -> factory.apply(holder, tier)).tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static MachineDefinition[] registerSimpleMachines(String name,
                                                             GTRecipeType recipeType,
                                                             Int2IntFunction tankScalingFunction,
                                                             boolean hasPollutionDebuff,
                                                             int... tiers) {
        return registerTieredMachines(name,
                (holder, tier) -> new SimpleTieredMachine(holder, tier, tankScalingFunction), (tier, builder) -> {
                    if (hasPollutionDebuff) {
                        builder.recipeModifiers(GTRecipeModifiers.ENVIRONMENT_REQUIREMENT
                                                .apply(GTMedicalConditions.CARBON_MONOXIDE_POISONING, 100 * tier),
                                        GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                                .tooltips(defaultEnvironmentRequirement());
                    } else {
                        builder.recipeModifier(
                                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK));
                    }
                    return builder
                            .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName(name), VLVT[tier]))
                            .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(CTNHCore.id(name), recipeType))
                            .rotationState(RotationState.NON_Y_AXIS)
                            .recipeType(recipeType)
                            .workableTieredHullRenderer(CTNHCore.id("block/machines/" + name))
                            .tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, recipeType,
                                    tankScalingFunction.apply(tier), true))
                            .register();
                },
                tiers);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType,
                                                             Int2IntFunction tankScalingFunction,
                                                             boolean hasPollutionDebuff) {
        return registerSimpleMachines(name, recipeType, tankScalingFunction, hasPollutionDebuff, GTMachineUtils.ELECTRIC_TIERS);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType,
                                                             Int2IntFunction tankScalingFunction) {
        return registerSimpleMachines(name, recipeType, tankScalingFunction, false);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType) {
        return registerSimpleMachines(name, recipeType, GTMachineUtils.defaultTankSizeFunction);
    }

    /*
     *       算力单方块
     */
    public static MachineDefinition[] registerSimpleComputationMachines(String name,
                                                                        GTRecipeType recipeType,
                                                                        Int2IntFunction tankScalingFunction,
                                                                        boolean hasPollutionDebuff,
                                                                        int... tiers) {
        return registerTieredMachines(name,
                (holder, tier) -> new SimpleComputationMachine(holder, tier, tankScalingFunction), (tier, builder) -> {
                    if (hasPollutionDebuff) {
                        builder.recipeModifiers(GTRecipeModifiers.ENVIRONMENT_REQUIREMENT
                                                .apply(GTMedicalConditions.CARBON_MONOXIDE_POISONING, 100 * tier),
                                        GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                                .tooltips(defaultEnvironmentRequirement());
                    } else {
                        builder.recipeModifier(
                                GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK));
                    }
                    return builder
                            .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName(name), VLVT[tier]))
                            .editableUI(SimpleComputationMachine.EDITABLE_UI_CREATOR.apply(CTNHCore.id(name), recipeType))
                            .rotationState(RotationState.NON_Y_AXIS)
                            .recipeType(recipeType)
                            .workableTieredHullRenderer(CTNHCore.id("block/machines/" + name))
                            .tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, recipeType,
                                    tankScalingFunction.apply(tier), true))
                            .tooltips(Component.translatable("ctnh.tooltips.simplecomputationmachine"))
                            .register();
                },
                tiers);
    }

    public static MachineDefinition[] registerSimpleComputationMachines(String name, GTRecipeType recipeType,
                                                                        Int2IntFunction tankScalingFunction,
                                                                        boolean hasPollutionDebuff) {
        return registerSimpleComputationMachines(name, recipeType, tankScalingFunction, hasPollutionDebuff, GTMachineUtils.ELECTRIC_TIERS);
    }

    public static MachineDefinition[] registerSimpleComputationMachines(String name, GTRecipeType recipeType,
                                                                        Int2IntFunction tankScalingFunction) {
        return registerSimpleComputationMachines(name, recipeType, tankScalingFunction, false);
    }

    public static MachineDefinition[] registerSimpleComputationMachines(String name, GTRecipeType recipeType) {
        return registerSimpleComputationMachines(name, recipeType, GTMachineUtils.defaultTankSizeFunction);
    }



    public static Component environmentRequirement(MedicalCondition condition) {
        return Component.translatable("gtceu.recipe.environmental_hazard.reverse",
                Component.translatable("gtceu.medical_condition." + condition.name));
    }

    public static Component defaultEnvironmentRequirement() {
        return environmentRequirement(GTMedicalConditions.CARBON_MONOXIDE_POISONING);
    }

    public static Component[] workableTiered(int tier, long voltage, long energyCapacity, GTRecipeType recipeType,
                                             long tankCapacity, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents
                .add(input ?
                        Component.translatable("gtceu.universal.tooltip.voltage_in",
                                FormattingUtil.formatNumbers(voltage), GTValues.VNF[tier]) :
                        Component.translatable("gtceu.universal.tooltip.voltage_out",
                                FormattingUtil.formatNumbers(voltage), GTValues.VNF[tier]));
        tooltipComponents
                .add(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                        FormattingUtil.formatNumbers(energyCapacity)));
        if (recipeType.getMaxInputs(FluidRecipeCapability.CAP) > 0 ||
                recipeType.getMaxOutputs(FluidRecipeCapability.CAP) > 0)
            tooltipComponents
                    .add(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            FormattingUtil.formatNumbers(tankCapacity)));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static final BiConsumer<IMultiController, List<Component>> CHEMICAL_PLANT_DISPLAY = (controller, components) -> {
        if (controller.isFormed()) {
            double value = 1 - ((CoilWorkableElectricMultiblockMachine) controller).getCoilTier() * 0.05;
            components.add(Component.translatable("ctnh.machine.eut_multiplier.tooltip", FormattingUtil.formatNumbers(value * 0.6)));
            components.add(Component.translatable("ctnh.machine.duration_multiplier.tooltip", FormattingUtil.formatNumbers(value * 0.5)));
        }
    };

    public static final MachineDefinition STERILE_CLEANROOM_MAINTENANCE_HATCH = GTRegistration.REGISTRATE
            .machine("sterile_cleanroom_maintenance_hatch",
                    holder -> new CleaningMaintenanceHatchPartMachine(holder, CleanroomType.STERILE_CLEANROOM))
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                tooltips.add(Component.literal("  ").append(Component
                        .translatable(CleanroomType.STERILE_CLEANROOM.getTranslationKey()).withStyle(ChatFormatting.LIGHT_PURPLE)));
            })
            .renderer(() -> new MaintenanceHatchPartRenderer(6, CTNHCore.id("block/machine/part/maintenance.sterilecleaning")))
            .register();

    public static final MachineDefinition[] HIGH_PERFORMANCE_COMPUTER = registerTieredMachines("high_performance_computer",
            HighPerformanceComputerMachine::new,
            (tier,builder)-> builder.langValue("%s High Performance Computer".formatted(VNF[tier]))
                    .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName("high_performance_computer"), VLVT[tier]))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .renderer(()->new WorkableTieredHullMachineRenderer(tier,GTCEu.id("block/machines/high_performance_computer/" + VN[tier].toLowerCase(Locale.ROOT))))
                    .tooltips(Component.translatable("ctnhcore.machine.high_performance_computer.tooltip.0"),
                              Component.translatable("ctnhcore.machine.high_performance_computer.tooltip.1",(tier>=GTValues.HV?1<<(tier-GTValues.HV):0)),
                              Component.translatable("gtceu.universal.tooltip.voltage_in",FormattingUtil.formatNumbers(VA[tier]*HighPerformanceComputerMachine.getMaxInputOutputAmperageStatic()), VNF[tier]))   //输入电流16A
                    .register(),GTValues.tiersBetween(HV,IV))
            ;

    public static void init() {

    }
}
