package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import java.util.List;
import java.util.Objects;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ForestMachine extends WorkableElectricMultiblockMachine {
    // 湿度值
    private int humidity = 0;  // 初始湿度为0%
    private static final int MAX_HUMIDITY = 100;  // 最大湿度为100%
    private static final int MIN_HUMIDITY = 0;    // 最低湿度为0%

    // 流体消耗量（单位：mB）
    private static final int FLUID_AMOUNT = 10000;  // 每次消耗10000mb的水

    public ForestMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    // 处理工作状态
    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 200 == 0) { // 每200tick执行一次

            // 检查输入仓是否有水
            FluidStack waterFluid = new FluidStack(
                    Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(
                            new ResourceLocation("minecraft:water"))),
                    FLUID_AMOUNT
            );

            boolean isFluidSufficient = MachineUtils.inputFluid(waterFluid, this);  // 检查是否有足够流体

            // 如果流体充足，增加湿度
            if (isFluidSufficient) {
                increaseHumidity();
            } else {
                decreaseHumidity(10);  // 流体不足时减少湿度10%
            }

            // 继续执行配方
            return super.onWorking();
        }

        // 在空闲时也检查是否有流体并增加湿度
        checkAndIncreaseHumidity();
        return super.onWorking();  // 调用父类的 onWorking 方法
    }

    // 检查流体并增加湿度
    private void checkAndIncreaseHumidity() {
        // 检查流体是否足够
        FluidStack waterFluid = new FluidStack(
                Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(
                        new ResourceLocation("minecraft:water"))),
                FLUID_AMOUNT
        );

        boolean isFluidSufficient = MachineUtils.inputFluid(waterFluid, this);  // 检查是否有足够流体

        // 如果流体充足，增加湿度
        if (isFluidSufficient) {
            increaseHumidity();
        }
    }

    // 增加湿度
    private void increaseHumidity() {
        humidity = Math.min(humidity + 1, MAX_HUMIDITY);  // 湿度增加，最大不超过100%
    }

    // 降低湿度
    private void decreaseHumidity(int amount) {
        humidity = Math.max(humidity - amount, MIN_HUMIDITY);  // 湿度减少，最小不低于0%
    }

    // 根据湿度计算并行数，电压 * 湿度系数
    public int getParallelCount() {
        // 如果湿度为 0，默认并行数为 1
        int humidityCoefficient = humidity > 0 ? humidity : 1;  // 湿度大于0时才根据湿度计算，否则为1
        return (int) (getMaxVoltage() * (humidityCoefficient / 100.0));  // 并行数 = 电压 * 湿度系数
    }

    // recipeModifier 实现，根据湿度调整并行数
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof ForestMachine forestMachine) {
            int parallelCount = forestMachine.getParallelCount();

            return ModifierFunction.builder()
                    .inputModifier(ContentModifier.multiplier(parallelCount))  // 动态调整输入量
                    .outputModifier(ContentModifier.multiplier(parallelCount))  // 动态调整输出量
                    .durationMultiplier(1)  // 使用时间不变
                    .parallels(parallelCount)  // 根据湿度调整并行数
                    .build();
        }
        return ModifierFunction.IDENTITY;  // 默认无修改
    }

    // GUI显示文本
    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(Component.translatable("forestmachine.humidity", humidity + "%"));
        textList.add(Component.translatable("forestmachine.parallel_count", getParallelCount()));
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return new ManagedFieldHolder(ForestMachine.class, super.getFieldHolder());
    }
}