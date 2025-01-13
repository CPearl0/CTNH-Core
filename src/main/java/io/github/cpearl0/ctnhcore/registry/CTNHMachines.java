package io.github.cpearl0.ctnhcore.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
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
import com.gregtechceu.gtceu.client.renderer.machine.MaintenanceHatchPartRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.OverlayTieredMachineRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.RotorHolderMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.machine.multiblock.part.CleaningMaintenanceHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.RotorHolderPartMachine;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import io.github.cpearl0.ctnhcore.CTNHCore;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CTNHPartAbility;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CircuitBusPartMachine;
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
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMachines;
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

    public static final MachineDefinition[] PERSONAL_COMPUTER = registerSimpleMachines("personal_computer",
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

    public static void init() {

    }
}
