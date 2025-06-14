package io.github.cpearl0.ctnhcore.common.machine.trait.providable_net;

import javax.annotation.*;

public class ProvidableNetHandler<MachineType extends IProviableNetHandlerMachine> {
    ProvidableNetInfo<MachineType> netInfo;
    //不为null表示能输入
    @Nullable
    ProviderInfo<MachineType> providerData;
    MachineType self;
    public ProvidableNetHandler(MachineType self){
        this.self = self;
    }
    public boolean ensureNetInfo(){
        if(netInfo == null)return false;
        if(netInfo.dirty) {
            netInfo = null;
            join();
            return true;
        }
        return true;
    }
    //读取并更新
    private ProvidableNetInfo<MachineType> getNetInfo(){
        if(netInfo==null)return null;
        return netInfo = netInfo.getFather();
    }
    public void join(){
        if (netInfo != null)return;
        netInfo = new ProvidableNetInfo<MachineType>(self.getLevel());
        if(self.canProvide()){
            providerData = new ProviderInfo<>(self);
            netInfo.chainHead = providerData;
            netInfo.chainTail = providerData;
        }

        var neighbours = self.getNeighbor();
        for (IProviableNetHandlerMachine neighbour : neighbours) {
            @SuppressWarnings("noinspection unchecked")
            ProvidableNetInfo<MachineType> neighbourNet = (ProvidableNetInfo<MachineType>) neighbour.getNetHandler().getNetInfo();
            if (neighbourNet!=null && neighbourNet.isAlive()) {
                netInfo.merge(neighbourNet);
            }
        }
    }
    public void invalidate(){
        if(netInfo==null) return;
        netInfo.markDirty();
        if(providerData != null){
            providerData.removeFromNet(netInfo);
        }
        providerData = null;
        netInfo = null;
    }
    //对网络的provider进行遍历和消耗,返回是否充足
    public boolean checkAndConsume(int amount){
        getNetInfo();
        if(netInfo.storage < amount)return false;
        netInfo.storage -= amount;
        return true;
    }
    public int getNetSize(){
        assert netInfo != null;
        return getNetInfo().netSize;
    }
    public long getDeadTime(){
        assert netInfo != null;
        return getNetInfo().deadTime;
    }
}
