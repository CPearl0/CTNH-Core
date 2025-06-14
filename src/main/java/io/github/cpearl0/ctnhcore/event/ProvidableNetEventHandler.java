package io.github.cpearl0.ctnhcore.event;

import io.github.cpearl0.ctnhcore.common.machine.trait.providable_net.*;
import net.minecraftforge.event.TickEvent;

import java.util.*;

public class ProvidableNetEventHandler {
    public static Set<ProvidableNetInfo<?>> nets =  new HashSet<>();
    public static Set<ProvidableNetInfo<?>> dirtyNets = new HashSet<>();
    public static List<ProvidableNetInfo<?>> removingNets = new ArrayList<>();
    public static void onPreTick(TickEvent.LevelTickEvent event) {
        long time = event.level.getGameTime();
        if(event.level.isClientSide() &&time % 20 !=0)return;
        nets.stream()
                .filter(net -> event.level == net.getLevel())
                .forEach(ProvidableNetInfo::preTick);
    }
    public static void onPostTick(TickEvent.LevelTickEvent event) {
        long time = event.level.getGameTime();
        if(event.level.isClientSide()&&time % 20 !=0)return;
        for (ProvidableNetInfo<?> dirtyNet : dirtyNets) {
            if (event.level == dirtyNet.getLevel() && time >= dirtyNet.getDeadTime()) {
                dirtyNet.invalidate();
            }
        }
        removingNets.forEach(removingNet -> dirtyNets.remove(removingNet));
        removingNets.clear();
        nets.stream()
                .filter(net -> event.level == net.getLevel())
                .forEach(ProvidableNetInfo::postTick);
    }
}
