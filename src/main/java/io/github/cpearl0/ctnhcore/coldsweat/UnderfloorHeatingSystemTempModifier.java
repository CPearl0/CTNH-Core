package io.github.cpearl0.ctnhcore.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class UnderfloorHeatingSystemTempModifier extends TempModifier {
    public static final Map<AABB,Double> UNDERFLOOR_HEATING_SYSTEM_RANGE = new HashMap<>();

    @Override
    protected Function<Double, Double> calculate(LivingEntity entity, Temperature.Trait trait) {
        if (!(entity instanceof Player))
            return temp -> temp;
        double heat = 12 * UNDERFLOOR_HEATING_SYSTEM_RANGE.entrySet().stream()
                .filter(range -> range.getKey().contains(entity.getOnPos().getCenter()))
                .map(Map.Entry::getValue)
                .reduce(0.0, Double::sum);
        return temp -> temp + heat;
    }
}
