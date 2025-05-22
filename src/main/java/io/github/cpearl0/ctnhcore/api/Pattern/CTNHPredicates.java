package io.github.cpearl0.ctnhcore.api.Pattern;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.block.CoilBlock;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import io.github.cpearl0.ctnhcore.api.CTNHAPI;
import io.github.cpearl0.ctnhcore.common.block.SpaceStructuralFramework;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.block.PhotovoltaicBlock;
import io.github.cpearl0.ctnhcore.common.block.blockdata.ISSFData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class CTNHPredicates {
    public static TraceabilityPredicate PhotovoltaicBlock() {
        return (new TraceabilityPredicate((blockWorldState) -> {
            BlockState blockState = blockWorldState.getBlockState();

            for(Map.Entry<IPBData, Supplier<PhotovoltaicBlock>> entry : CTNHAPI.PhotovoltaicBlock.entrySet()) {
                if (blockState.is((Block)((Supplier)entry.getValue()).get())) {
                    IPBData stats = (IPBData)entry.getKey();
                    Object currentCoil = blockWorldState.getMatchContext().getOrPut("IPBData", stats);
                    if (!currentCoil.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("ctnh.error.pv"));
                        return false;
                    }

                    return true;
                }
            }

            return false;
        }, () -> (BlockInfo[])CTNHAPI.PhotovoltaicBlock.entrySet().stream().sorted(Comparator.comparingInt((value) -> ((IPBData)value.getKey()).getTier())).map((pb) -> BlockInfo.fromBlockState(((PhotovoltaicBlock)((Supplier)pb.getValue()).get()).defaultBlockState())).toArray((x$0) -> new BlockInfo[x$0]))).addTooltips(new Component[]{Component.translatable("ctnh.error.pv")});
    }
    public static TraceabilityPredicate SpaceStructuralFrameworkBlock() {
        return (new TraceabilityPredicate((blockWorldState) -> {
            BlockState blockState = blockWorldState.getBlockState();

            for(Map.Entry<ISSFData, Supplier<SpaceStructuralFramework>> entry : CTNHAPI.SpaceStructuralFramework.entrySet()) {
                if (blockState.is((Block)((Supplier)entry.getValue()).get())) {
                    ISSFData stats = (ISSFData)entry.getKey();
                    Object currentCoil = blockWorldState.getMatchContext().getOrPut("ISSFData", stats);
                    if (!currentCoil.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("ctnh.error.issf"));
                        return false;
                    }

                    return true;
                }
            }

            return false;
        }, () -> (BlockInfo[])CTNHAPI.SpaceStructuralFramework.entrySet().stream().sorted(Comparator.comparingInt((value) -> ((ISSFData)value.getKey()).getTier())).map((pb) -> BlockInfo.fromBlockState(((SpaceStructuralFramework)((Supplier)pb.getValue()).get()).defaultBlockState())).toArray((x$0) -> new BlockInfo[x$0]))).addTooltips(new Component[]{Component.translatable("ctnh.error.pv")});
    }
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
    public static TraceabilityPredicate reactorCore() {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (Map.Entry<Integer, Supplier<Block>> entry : CTNHAPI.ReactorCoreBlock.entrySet()) {
                if (blockState.is(entry.getValue().get())) {
                    var heat = entry.getKey();
                    int current_heat = (int) blockWorldState.getMatchContext().getOrPut("ReactorCore", 0);
                    current_heat += heat;
                    blockWorldState.getMatchContext().set("ReactorCore", current_heat);
                    return true;
                }
            }
            return false;
        }, () -> CTNHAPI.ReactorCoreBlock.entrySet().stream()
                // sort to make autogenerated jei previews not pick random coils each game load
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(block-> BlockInfo.fromBlockState(block.getValue().get().defaultBlockState()))
                .toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("ctnh.multiblock.pattern.error.reactor"));
    }
//    public static TraceabilityPredicate NuclearReactorComponent() {
//
//    }
}
