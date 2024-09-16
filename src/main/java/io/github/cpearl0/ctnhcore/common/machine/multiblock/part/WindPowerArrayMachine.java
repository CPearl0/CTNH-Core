package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindPowerArrayMachine extends WorkableElectricMultiblockMachine {
    public Map<WindPowerArrayMachine, BlockPos> MachineNet = new HashMap<>();
    public WindPowerArrayMachine(IMachineBlockEntity holder){
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if(getOffsetTimer() % 20 == 0){
            MachineNet.clear();
            MachineNet.put(this,getPos());
            for(int x = -10; x < 11; x ++){
                for(int y = -10; y < 11; y ++){
                    for(int z = -10; z < 11; z ++){
                        if(x == 0 && y == 0 && z == 0){
                        }
                        else {
                            if(MetaMachine.getMachine(getLevel(),getPos().offset(x,y,z)) instanceof WindPowerArrayMachine machine){
                                if(machine.isWorkingEnabled()){
//                                    machine.MachineNet.forEach((WindMachine,pos) ->{
//                                        if(pos != getPos()){
//                                            MachineNet.putIfAbsent(WindMachine,pos);
//                                        }
//                                    });
                                    MachineNet.putAll(machine.MachineNet);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(MachineNet.size());
        }
        return super.onWorking();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(Component.translatable("info.ctnhcore.network_machine",MachineNet.size()));
    }
}
