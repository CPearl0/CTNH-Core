package io.github.cpearl0.ctnhcore.coldsweat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;

import java.util.HashMap;
import java.util.Map;

public class UnderfloorHeatingSystemTempModifier extends ModifierBase {
    public static final Map<AABB,Double> UNDERFLOOR_HEATING_SYSTEM_RANGE = new HashMap<>();
    public UnderfloorHeatingSystemTempModifier() {super();}

    @Override
    public float getWorldInfluence(@Nullable Player player, Level level, BlockPos pos) {
//        if (!(entity instanceof Player))
//            return temp -> temp;
        double heat = 30 * UNDERFLOOR_HEATING_SYSTEM_RANGE.entrySet().stream()
                .filter(range -> range.getKey().contains(pos.getCenter()))
                .map(Map.Entry::getValue)
                .reduce(0.0, Double::sum);
        return (float) heat;
    }
}
