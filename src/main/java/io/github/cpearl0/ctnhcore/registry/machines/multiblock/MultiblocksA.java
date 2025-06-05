package io.github.cpearl0.ctnhcore.registry.machines.multiblock;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeModifiers;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static io.github.cpearl0.ctnhcore.registry.CTNHRegistration.REGISTRATE;

public class MultiblocksA {
    public static void init(){}
    public static final MultiblockMachineDefinition SUPER_CENTRIFUGE = REGISTRATE.multiblock("super_centrifuge", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeTypes(GTRecipeTypes.CENTRIFUGE_RECIPES, CTNHRecipeTypes.DIFFERENTIAL_CENTRIFUGE_RECIPES)
            .appearanceBlock(CASING_TITANIUM_STABLE)
            .recipeModifier((metaMachine, gtRecipe) -> {
                if (gtRecipe.getType().equals(GTRecipeTypes.CENTRIFUGE_RECIPES)) {
                    return CTNHRecipeModifiers.accurateParallel(metaMachine, gtRecipe, 8);
                }
                return ModifierFunction.IDENTITY;
            })
            .tooltips(Component.translatable("super_centrifuge").withStyle(ChatFormatting.GRAY),
                    Component.translatable("ctnh.super_centrifuge.parallel"))
            .pattern(definition -> FactoryBlockPattern.start()
                .aisle("#BBB#", "BBBBB", "#BBB#", "BBBBB", "#BBB#")
                .aisle("BBBBB", "B#C#B", "B#C#B", "B#C#B", "BBBBB")
                .aisle("BBBBB", "BC#CB", "BC#CB", "BC#CB", "BBBBB")
                .aisle("BBBBB", "B#C#B", "B#C#B", "B#C#B", "BBBBB")
                .aisle("#BBB#", "BBBBB", "#B@B#", "BBBBB", "#BBB#")
                .where("B", Predicates.blocks(CASING_TITANIUM_STABLE.get()).setMinGlobalLimited(40)
                        .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                .where("#", Predicates.any())
                .where("C", Predicates.blocks(CASING_TITANIUM_PIPE.get()))
                .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_stable_titanium"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
    public static final MultiblockMachineDefinition ULTRASONIC_APPARATUS = REGISTRATE.multiblock("ultrasonic_apparatus", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(CTNHRecipeTypes.ULTRASONICATION_RECIPES)
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .tooltips(Component.translatable("ultrasonic_apparatus").withStyle(ChatFormatting.GRAY))
            .pattern(definition -> FactoryBlockPattern.start()
                .aisle("##BCFCB##", "#BBCFCBB#", "##BCFCB##")
                .aisle("#BBCFCBB#", "BDEEEEEDB", "#BBCFCBB#")
                .aisle("##BCFCB##", "#BBC@CBB#", "##BCFCB##")
                .where("#", Predicates.any())
                .where("B", Predicates.blocks(CASING_STAINLESS_CLEAN.get()))
                .where("C", Predicates.heatingCoils())
                .where("D", Predicates.blocks(CASING_STAINLESS_STEEL_GEARBOX.get()))
                .where("E", Predicates.blocks(CASING_STEEL_PIPE.get()))
                .where("F",Predicates.blocks(CASING_STAINLESS_CLEAN.get())
                    .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                .where("@", Predicates.controller(Predicates.blocks(definition.get())))
                .build()
            )
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/implosion_compressor"), false)
            .register();
}
