package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils.canInputFluid;
import static io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils.inputFluid;

public class VoidMinerProcessingMachine extends WorkableElectricMultiblockMachine {

    // 定义初始温度和最大温度
    private int currentTemperature = 300;  // 初始温度为300K
    private static final int MAX_TEMP = 25000;
    private static final int MIN_TEMP = 300;
    private static final int HEAT_SPEED = 10; // 每次消耗烈焰之赤炎升温的速度

    // 流体消耗量（单位：mB）
    private static final int FLUID_AMOUNT = 10;  // 每次消耗的流体量（可调整）

    public VoidMinerProcessingMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    // 声明标志变量
    private boolean isCoolingDown = false;  // 默认不在降温模式

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            int temperature = getCurrentTemperature();

            // 强制降温模式逻辑
            if (isCoolingDown || temperature >= MAX_TEMP) {
                isCoolingDown = true; // 确保强制降温模式被激活
                FluidStack cryotheumFluid = CTNHMaterials.Cryotheum.getFluid(FLUID_AMOUNT);
                if (MachineUtils.inputFluid(cryotheumFluid, this)) {
                    temperature = Math.max(MIN_TEMP, temperature - 100); // 降温
                    setCurrentTemperature(temperature);

                    // 如果温度降到 300K，退出强制降温模式
                    if (temperature <= MIN_TEMP) {
                        isCoolingDown = false; // 恢复正常运行状态
                    }

                    getRecipeLogic().interruptRecipe(); // 禁止正常配方运行
                    return true;
                } else {
                    getRecipeLogic().setProgress(0); // 流体不足时停止
                    return false;
                }
            }

            // 正常配方运行逻辑
            FluidStack pyrotheumFluid = CTNHMaterials.Pyrotheum.getFluid(FLUID_AMOUNT);
            if (MachineUtils.inputFluid(pyrotheumFluid, this)) {
                temperature = Math.min(MAX_TEMP, temperature + HEAT_SPEED);
                setCurrentTemperature(temperature);
                return true;
            }

            getRecipeLogic().setProgress(0); // 流体不足时停止
            return false;
        }

        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(GTRecipe recipe) {
        int temperature = getCurrentTemperature();

        // 强制降温模式逻辑
        if (isCoolingDown || temperature >= MAX_TEMP) {
            isCoolingDown = true; // 确保强制降温模式被激活
            FluidStack cryotheumFluid = CTNHMaterials.Cryotheum.getFluid(FLUID_AMOUNT);
            if (!MachineUtils.canInputFluid(cryotheumFluid, this)) {
                getRecipeLogic().interruptRecipe(); // 禁止正常配方运行
                return false;
            }
            return true; // 允许降温配方运行
        }

        // 正常运行检查
        FluidStack pyrotheumFluid = CTNHMaterials.Pyrotheum.getFluid(FLUID_AMOUNT);
        if (!MachineUtils.canInputFluid(pyrotheumFluid, this)) {
            getRecipeLogic().interruptRecipe(); // 禁止正常配方运行
            return false;
        }

        return super.beforeWorking(recipe);
    }

    // 获取当前温度
    public int getCurrentTemperature() {
        return currentTemperature;
    }

    // 设置当前温度
    public void setCurrentTemperature(int temperature) {
        this.currentTemperature = temperature;
    }

    // 更新GUI显示文本
    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        AtomicInteger pyrotheumAmount = new AtomicInteger();
        AtomicInteger cryotheumAmount = new AtomicInteger();

        getParts().forEach(part -> {
            part.getRecipeHandlers().forEach(handler -> {
                if (handler.getHandlerIO() == IO.IN) {
                    handler.getContents().forEach(content -> {
                        if (content instanceof FluidStack fluid) {
                            if (fluid.getFluid().equals(CTNHMaterials.Pyrotheum.getFluid())) {
                                pyrotheumAmount.addAndGet(fluid.getAmount());
                            } else if (fluid.getFluid().equals(CTNHMaterials.Cryotheum.getFluid())) {
                                cryotheumAmount.addAndGet(fluid.getAmount());
                            }
                        }
                    });
                }
            });
        });

        textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(currentTemperature + "K").withStyle(ChatFormatting.RED)));
        textList.add(Component.translatable("ctnh.blaze_blast_furnace.pyrotheum", pyrotheumAmount + " mB"));
        textList.add(Component.translatable("ctnh.void_miner.cryotheum", cryotheumAmount + " mB"));
    }

    // 修改配方的时长
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        int temperature = ((VoidMinerProcessingMachine) machine).getCurrentTemperature();
        var modifierFunction = ModifierFunction.builder();

        // 根据温度修改配方的时长
        if (temperature >= 20000) {
            modifierFunction.durationModifier(ContentModifier.multiplier((double) 100 / recipe.duration));
        } else if (temperature >= 15000) {
            modifierFunction.durationModifier(ContentModifier.multiplier((double) 300 / recipe.duration));
        } else if (temperature >= 10000) {
            modifierFunction.durationModifier(ContentModifier.multiplier((double) 600 / recipe.duration));
        } else if (temperature >= 5000) {
            modifierFunction.durationModifier(ContentModifier.multiplier((double) 1200 / recipe.duration));
        }
        return modifierFunction.build();
    }
}