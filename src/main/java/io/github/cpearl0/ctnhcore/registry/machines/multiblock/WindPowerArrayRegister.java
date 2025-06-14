package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.WindPowerArrayMachine;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class WindPowerArrayRegister {
    public static MultiblockMachineDefinition register(String name, int tier, BlockEntry<Block> casing, Material frame,String renderCasing) {
        return REGISTRATE.multiblock(name, holder -> new WindPowerArrayMachine(holder, tier))
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAA", "###", "###", "###", "BBB")
                        .aisle("AAA", "#A#", "#A#", "#A#", "BAB")
                        .aisle("A@A", "###", "###", "###", "BBB")
                        .where("A", Predicates.blocks(casing.get())
                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)
                                        .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setMinGlobalLimited(1))))
                        .where("B", Predicates.frames(frame))
                        .where("#", Predicates.air())
                        .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                        .build())
                .allowFlip(false)
                .allowExtendedFacing(false)
                .workableCasingRenderer(GTCEu.id("block/casings/solid/"+renderCasing), GTCEu.id("block/multiblock/implosion_compressor"), false)
                .tooltips(Component.translatable("ctnh.multiblock.wind_array.tooltip0")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip1")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip2", GTValues.V[tier])
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip3")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip4")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip5")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip6")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip7",tier)
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip8")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip9")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip10")
                        , Component.translatable("ctnh.multiblock.wind_array.tooltip11")
                )
                .register();
    }
}
