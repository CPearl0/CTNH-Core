package io.github.cpearl0.ctnhcore.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class UnderfloorHeatingSystemTempModifier extends TempModifier {
    public static final Set<AABB> UNDERFLOOR_HEATING_SYSTEM_RANGE = new HashSet<>();

    @Override
    protected Function<Double, Double> calculate(LivingEntity entity, Temperature.Trait trait) {
        if (!(entity instanceof Player))
            return temp -> temp;
        double heat = 12 * UNDERFLOOR_HEATING_SYSTEM_RANGE.stream()
                .filter(range -> range.contains(entity.getOnPos().getCenter()))
                .count();
        return temp -> temp + heat;
    }
}
