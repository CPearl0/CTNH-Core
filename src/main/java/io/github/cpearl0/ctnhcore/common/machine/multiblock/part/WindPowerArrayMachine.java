package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class WindPowerArrayMachine extends WorkableElectricMultiblockMachine {
    public int Threshold = 16;
    public double efficiency = 1;
    public int altitude = 0;
    public int NetworkSize = 1;
    @Getter
    private int Multi;
    @Getter
    private int tier;
    public static Map<WindPowerArrayMachine,BlockPos> MachineNet = new HashMap<>();
    public WindPowerArrayMachine(IMachineBlockEntity holder,int tier){
        super(holder);
        altitude = getPos().getY();
        this.tier = tier;
        Multi = (int) Math.pow(4,tier - 1);
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if(!MachineNet.containsValue(getPos())){
            MachineNet.put(this,getPos());
        }
        NetworkSize = getMachineNetworkSize();
        efficiency = (NetworkSize - 1)*0.1 + 1;
        return super.beforeWorking(recipe);
    }
    public static GTRecipe WindPowerArrayRecipeModifier(MetaMachine machine, GTRecipe recipe, OCParams ocParams, OCResult ocResult){
        if(machine instanceof WindPowerArrayMachine){
            GTRecipe CopyRecipe = recipe.copy();
            int realAltitude = Mth.clamp(((WindPowerArrayMachine) machine).altitude,64,256);
            CopyRecipe.tickOutputs.put(EURecipeCapability.CAP,CopyRecipe.copyContents(CopyRecipe.tickOutputs, ContentModifier.of(((WindPowerArrayMachine) machine).efficiency* ((WindPowerArrayMachine) machine).Multi, (double) (realAltitude - 64) /3)).get(EURecipeCapability.CAP));
            return CopyRecipe;
        }
        return null;
    }
    @Override
    public boolean onWorking() {
        if(getOffsetTimer() % 20 == 0){
            NetworkSize = getMachineNetworkSize();
            efficiency = (NetworkSize - 1)*0.1 + 1;
            //System.out.println(MachineNet.size());
            //System.out.println(MachineNet);
        }
        return super.onWorking();
    }

    @Override
    public void afterWorking() {
        MachineNet.remove(this,getPos());
        //NetworkSize = 1;
        //efficiency = 1;
        super.afterWorking();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if(isFormed){
            textList.add(Component.translatable("info.ctnhcore.network_machine",NetworkSize));
            textList.add(Component.translatable("info.ctnhcore.network_machine_efficiency",efficiency));
        }
    }
    public static void clearNet() {
        if(MachineNet != null) {
            MachineNet = MachineNet.entrySet().stream().filter(entry -> entry.getKey() != null && entry.getKey().isActive()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }
    public static double distance(BlockPos p1, BlockPos p2) {
        //return p1.distToCenterSqr(p2.getCenter());
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(), 2));
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
