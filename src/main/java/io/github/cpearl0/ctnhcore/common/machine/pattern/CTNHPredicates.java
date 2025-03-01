package io.github.cpearl0.ctnhcore.common.machine.pattern;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class CTNHPredicates {
    static TraceabilityPredicate autoLaserAbilities(GTRecipeType... recipeType) {
        TraceabilityPredicate predicate = Predicates.autoAbilities(recipeType, false, false, true, true, true, true);
        for(var type : recipeType) {
            if (type.getMaxInputs(EURecipeCapability.CAP) > 0) {
                predicate = predicate.or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1)).or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1).setPreviewCount(1));
                break;
            }

            if (type.getMaxOutputs(EURecipeCapability.CAP) > 0) {
                predicate = predicate.or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1)).or(Predicates.abilities(PartAbility.OUTPUT_LASER).setMaxGlobalLimited(1).setPreviewCount(1));
                break;
            }
        }
        return predicate;
    }
    static TraceabilityPredicate tierBlock(Map<Integer, Supplier<?>> map, String tierType) {
        BlockInfo[] blockInfos = new BlockInfo[map.size()];
        int index = 0;

        for(Iterator var4 = map.values().iterator(); var4.hasNext(); ++index) {
            Supplier<?> blockSupplier = (Supplier)var4.next();
            Block block = (Block)blockSupplier.get();
            blockInfos[index] = BlockInfo.fromBlockState(block.defaultBlockState());
        }

        return (new TraceabilityPredicate((state) -> {
            BlockState blockState = state.getBlockState();
            Iterator var4 = map.entrySet().iterator();

            Map.Entry entry;
            do {
                if (!var4.hasNext()) {
                    return false;
                }

                entry = (Map.Entry)var4.next();
            } while(!blockState.is((Block)((Supplier)entry.getValue()).get()));

            int tier = (Integer)entry.getKey();
            int type = (Integer)state.getMatchContext().getOrPut(tierType, tier);
            if (type != tier) {
                state.setError(new PatternStringError("ctnh.machine.pattern.error.tier"));
                return false;
            } else {
                return true;
            }
        }, () -> {
            return blockInfos;
        })).addTooltips(Component.translatable("ctnh.machine.pattern.error.tier"));
    }
}
