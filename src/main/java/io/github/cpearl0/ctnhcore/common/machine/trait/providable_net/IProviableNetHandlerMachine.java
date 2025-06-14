package io.github.cpearl0.ctnhcore.common.machine.trait.providable_net;

import net.minecraft.world.level.Level;

import java.util.List;

public interface IProviableNetHandlerMachine {
    List<IProviableNetHandlerMachine> getNeighbor();
    ProvidableNetHandler<?> getNetHandler();
    boolean canProvide();
    int getStorage();
    //返回值:处理剩下的
    //参见ProviderInfo.postTick
    int checkAndConsume(int amount);
    Level getLevel();
}
