package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WindPowerArrayMachine extends WorkableElectricMultiblockMachine {
    public static Map<BlockPos,WindPowerArrayMachine> MachineNet = new HashMap<>();
    public static List<List<WindPowerArrayMachine>> NetGroup = new ArrayList<>();
    public static int Threshold = 16;
    public double efficiency = 1;
    public int altitude;
    public int NetworkSize = 1;
    @Getter
    private int Multi;
    @Getter
    private int tier;
    public WindPowerArrayMachine(IMachineBlockEntity holder,int tier){
        super(holder);
        altitude = getPos().getY();
        this.tier = tier;
        Multi = (int) Math.pow(4,tier - 1);
    }

    public static ModifierFunction WindPowerArrayRecipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof WindPowerArrayMachine wmachine) {
            int realAltitude = Mth.clamp(wmachine.altitude,64,256);
            return ModifierFunction.builder()
                    .eutMultiplier(wmachine.efficiency * ((double) (realAltitude + 128) / 192) * wmachine.Multi)
                    .inputModifier(ContentModifier.multiplier(wmachine.Multi))
                    .outputModifier(ContentModifier.multiplier(wmachine.Multi))
                    .build();
        }
        return ModifierFunction.NULL;
    }

    public static void clearNet() {
        if (MachineNet != null) {
            MachineNet = MachineNet.entrySet().stream().filter(entry -> entry.getValue() != null && entry.getValue().isActive()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    public static double distance(BlockPos p1, BlockPos p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(), 2));
    }

    public static int dfs(int node, boolean[] visited, List<List<Integer>> graph) {
        visited[node] = true;
        int size = 1;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                size += dfs(neighbor, visited, graph);
            }
        }

        return size;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (!MachineNet.containsKey(getPos())) {
            MachineNet.put(getPos(),this);
        }
        else {
            if (!MachineNet.get(getPos()).equals(this)){
                MachineNet.replace(getPos(),this);
            }
        }
        for(var machineNet: NetGroup) {
            if (machineNet.contains(this)) {
                NetworkSize = machineNet.size();
            }
        }
        efficiency = Math.sqrt(NetworkSize) * 0.2 + 1;
        return super.beforeWorking(recipe);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            for(var machineNet: NetGroup) {
                if (machineNet.contains(this)) {
                    NetworkSize = machineNet.size();
                }
            }
            efficiency = Math.sqrt(NetworkSize) * 0.2 + 1;
        }
        return super.onWorking();
    }

    @Override
    public void afterWorking() {
        MachineNet.remove(getPos(),this);
        super.afterWorking();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed) {
            textList.add(Component.translatable("info.ctnhcore.network_machine", NetworkSize));
            textList.add(Component.translatable("info.ctnhcore.network_machine_efficiency", efficiency));
        }
    }

    public static void groupByNetwork() {
        List<List<WindPowerArrayMachine>> groups = new ArrayList<>();
        Map<BlockPos, WindPowerArrayMachine> nodeMap = new HashMap<>(MachineNet); // 复制 MachineNet，避免修改原数据
        List<BlockPos> positions = new ArrayList<>(nodeMap.keySet());
        int n = positions.size();

        // 构造邻接表
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (distance(positions.get(i), positions.get(j)) < Threshold) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        // 记录访问状态
        boolean[] visited = new boolean[n];

        // 遍历所有节点，找到连通子图
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<WindPowerArrayMachine> group = new ArrayList<>();
                dfsGroup(i, visited, graph, positions, nodeMap, group);
                groups.add(group);
            }
        }

        NetGroup = groups;
    }

    private static void dfsGroup(
            int node,
            boolean[] visited,
            List<List<Integer>> graph,
            List<BlockPos> positions,
            Map<BlockPos, WindPowerArrayMachine> nodeMap,
            List<WindPowerArrayMachine> group
    ) {
        visited[node] = true;
        BlockPos position = positions.get(node);
        group.add(nodeMap.get(position));

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfsGroup(neighbor, visited, graph, positions, nodeMap, group);
            }
        }
    }
}
