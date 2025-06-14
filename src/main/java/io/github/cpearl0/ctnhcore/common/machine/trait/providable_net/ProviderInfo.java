package io.github.cpearl0.ctnhcore.common.machine.trait.providable_net;

class ProviderInfo<MachineType extends IProviableNetHandlerMachine>  {
    MachineType self;
    ProviderInfo<MachineType> next = null;
    ProviderInfo<MachineType> prev = null;
    ProviderInfo(MachineType self) {
        this.self = self;
    }
    boolean hasPrev(){
        return prev != null;
    }
    boolean hasNext(){
        return next !=null;
    }
    void removeFromNet(ProvidableNetInfo<MachineType> netInfo){
        if(this == netInfo.chainHead) netInfo.chainHead = next;
        if(this == netInfo.chainTail) netInfo.chainTail = prev;
        if(next!=null)
            next.prev = prev;
        if(prev!=null)
            prev.next = next;

        this.next = null;
        this.prev = null;
    }
    void insertIn(ProviderInfo<MachineType> node){

        this.next = node;
        this.prev = node.prev;

        node.prev.next = this;
        node.prev = this;
    }
    void appendIn(ProviderInfo<MachineType> node){

        this.prev = node;
        node.next = this;
    }
}
