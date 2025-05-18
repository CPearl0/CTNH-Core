package io.github.cpearl0.ctnhcore.api.Pattern;

import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
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
}
