package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindPowerArrayMachine extends WorkableElectricMultiblockMachine {
    public int Threshold = 16;

    public double efficiency = 1;
    public int NetworkSize = 1;
    public static Map<WindPowerArrayMachine,BlockPos> MachineNet = new HashMap<>();
    public WindPowerArrayMachine(IMachineBlockEntity holder){
        super(holder);

    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        MachineNet.put(this,getPos());
        NetworkSize = getMachineNetworkSize();
        efficiency = (NetworkSize - 1)*0.1 + 1;
        return true;
    }

    @Override
    public boolean onWorking() {
        if(getOffsetTimer() % 20 == 0){
            NetworkSize = getMachineNetworkSize();
            efficiency = (NetworkSize - 1)*0.1 + 1;
        }
        return super.onWorking();
    }

    @Override
    public void afterWorking() {
        MachineNet.remove(this,getPos());
        NetworkSize = 1;
        efficiency = 1;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(Component.translatable("info.ctnhcore.network_machine",NetworkSize));
        textList.add(Component.translatable("info.ctnhcore.network_machine_efficiency",efficiency));
    }

    public static double distance(BlockPos p1, BlockPos p2) {
        return p1.distToCenterSqr(p2.getCenter());
         //Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(), 2));
    }

    // DFS 深度优先遍历，计算连通分量的大小
    public static int dfs(int node, boolean[] visited, List<List<Integer>> graph) {
        visited[node] = true;
        int size = 1;

        // 遍历所有相邻的未访问的节点
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                size += dfs(neighbor, visited, graph);
            }
        }

        return size;
    }

    public int getMachineNetworkSize() {
        // 机器网络的大小
        int n = MachineNet.size();

        // 将 MachineNet 中的条目转换为列表
        List<Map.Entry<WindPowerArrayMachine, BlockPos>> entryList = new ArrayList<>(MachineNet.entrySet());

        // 找到目标机器的索引
        int targetIndex = -1;
        for (int i = 0; i < n; i++) {
            if (entryList.get(i).getKey() == this) {
                targetIndex = i;
                break;
            }
        }

        // 如果找不到目标机器，则返回 0
        if (targetIndex == -1) {
            return 0;
        }

        // 创建图，使用邻接表
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 根据距离阈值构建图
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (distance(entryList.get(i).getValue(), entryList.get(j).getValue()) < Threshold) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        // DFS 遍历以找到与目标机器连通的所有机器
        boolean[] visited = new boolean[n];
        return dfs(targetIndex, visited, graph);
    }
}
