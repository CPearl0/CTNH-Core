package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
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

    @Override
    public boolean onWorking() {
        // 每20 tick检查一次
        if (getOffsetTimer() % 20 == 0) {
            // 获取当前的温度
            int temperature = getCurrentTemperature();

            // 进入强制降温模式，温度过高时只能降温
            if (temperature >= MAX_TEMP) {
                // 只有极寒之凌冰才能消耗并降温
                FluidStack cryotheumFluid = CTNHMaterials.Cryotheum.getFluid(FLUID_AMOUNT);
                if (MachineUtils.inputFluid(cryotheumFluid, this)) { // 调用接口方法
                    temperature = Math.max(MIN_TEMP, temperature - 100);  // 每次消耗100mB极寒之凌冰降温100K
                    setCurrentTemperature(temperature);
                    // 强制降温后不执行其他配方
                    getRecipeLogic().interruptRecipe();
                    return true;
                }
            }

            // 检查是否有足够的流体来维持正常运行
            FluidStack pyrotheumFluid = CTNHMaterials.Pyrotheum.getFluid(FLUID_AMOUNT);
            if (MachineUtils.inputFluid(pyrotheumFluid, this)) { // 调用接口方法
                // 使用烈焰之赤炎增加温度
                temperature = Math.min(MAX_TEMP, temperature + HEAT_SPEED);  // 每消耗1000mB烈焰之赤炎温度增加10K
                setCurrentTemperature(temperature);
                return true;  // 成功消耗烈焰之赤炎，继续运行
            }

            // 如果没有足够的流体，暂停机器运行
            getRecipeLogic().setProgress(0);
            return false;
        }

        return super.onWorking();
    }

    @Override
    public boolean beforeWorking(GTRecipe recipe) {
        // 在执行配方之前检查流体
        FluidStack pyrotheumFluid = CTNHMaterials.Pyrotheum.getFluid(FLUID_AMOUNT);
        if (!MachineUtils.canInputFluid(pyrotheumFluid, this)) { // 调用接口方法
            getRecipeLogic().interruptRecipe();
            return false;  // 没有足够的烈焰之赤炎，无法执行配方
        }

        // 检查是否处于强制降温模式
        if (getCurrentTemperature() >= MAX_TEMP) {
            FluidStack cryotheumFluid = CTNHMaterials.Cryotheum.getFluid(FLUID_AMOUNT);
            if (!MachineUtils.canInputFluid(cryotheumFluid, this)) { // 调用接口方法
                getRecipeLogic().interruptRecipe();
                return false;  // 没有足够的极寒之凌冰，无法降温
            }
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
    public static GTRecipe recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result) {
        int temperature = ((VoidMinerProcessingMachine) machine).getCurrentTemperature();
        GTRecipe newRecipe = recipe.copy();

        // 根据温度修改配方的时长
        if (temperature >= 20000) {
            newRecipe.duration = 100;
        } else if (temperature >= 15000) {
            newRecipe.duration = 300;
        } else if (temperature >= 10000) {
            newRecipe.duration = 600;
        } else if (temperature >= 5000) {
            newRecipe.duration = 1200;
        } else {
            newRecipe.duration = recipe.duration; // 保持原配方时长
        }

        return newRecipe;
    }
}