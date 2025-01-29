package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
public class NaqReactorMachine extends WorkableElectricMultiblockMachine implements ITieredMachine {

    // 设置当前温度
    // 获取当前温度
    // 定义初始温度和最大温度
    private int currentTemperature = 0;  // 初始温度为0K
    private static final int MAX_TEMP = 80000;  // 温度最高为80000K
    private static final int MIN_TEMP = 0;     // 温度最低为0K
    private static final int HEAT_SPEED = 10;  // 每次运行配方后温度上升1000K

    // 流体消耗量（单位：mB）
    private static final int FLUID_AMOUNT = 40;  // 每次消耗40mb的镍等离子体

    public NaqReactorMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 20 == 0) {
            int temperature = getCurrentTemperature();

            // 正常配方运行逻辑
            FluidStack nickelPlasmaFluid = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:nickel_plasma"))), FLUID_AMOUNT);
            if (MachineUtils.inputFluid(nickelPlasmaFluid, this)) {
                increaseTemperature();  // 每次配方运行温度增加10K
                return true;
            }

            getRecipeLogic().setProgress(0); // 流体不足时停止
            return false;
        }
        return super.onWorking();
    }

    // 增加温度，每次运行配方后温度增加1000K
    private void increaseTemperature() {
        currentTemperature = Math.min(currentTemperature + HEAT_SPEED, MAX_TEMP);
    }

    // 根据温度调整并行数
    public int getParallelCount() {
        if (currentTemperature >= 60000) {
            return 64;  // 温度达到60000时并行数为64
        } else if (currentTemperature >= 30000) {
            return 16;  // 温度达到30000时并行数为16
        } else if (currentTemperature >= 10000) {
            return 8;   // 温度达到10000时并行数为8
        } else if (currentTemperature >= 5000) {
            return 4;   // 温度达到5000时并行数为4
        } else if (currentTemperature >= 1000) {
            return 2;   // 温度达到1000时并行数为2
        } else {
            return 1;   // 温度小于1000时并行数为1
        }
    }

    // recipeModifier 实现，根据温度调整并行数
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof NaqReactorMachine reactorMachine) {
            int parallelCount = reactorMachine.getParallelCount();
            double eutMultiplier = parallelCount; // 并行数影响Eut输出量

            return ModifierFunction.builder()
                    .inputModifier(ContentModifier.multiplier(1))  // 输入不变
                    .outputModifier(ContentModifier.multiplier(1))  // 输出不变
                    .durationMultiplier(1)  // 使用时间不变
                    .parallels(parallelCount)  // 根据温度调整并行数
                    .eutMultiplier(eutMultiplier)  // 根据并行数调整Eut输出
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }

    // 更新GUI显示文本
    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        textList.add(Component.translatable("powergenerator.temperature", currentTemperature + "K"));
        textList.add(Component.translatable("powergenerator.nickel_consumption", FLUID_AMOUNT + "mb"));
        textList.add(Component.translatable("powergenerator.parallel_count", getParallelCount()));
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return new ManagedFieldHolder(NaqReactorMachine.class, super.getFieldHolder());
    }
}