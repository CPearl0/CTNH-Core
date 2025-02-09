package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.*;

@Setter
@Getter
public class VoidMinerProcessingMachine extends WorkableElectricMultiblockMachine {

    private int currentTemperature = 0;  // 初始温度为0K
    private static final int MAX_TEMP = 25000;
    private static final int MIN_TEMP = 0;
    private boolean isOverheated = false; // 过热状态标志
    private int fluidAmount = FLUID_AMOUNT; // 定义为类的成员变量
    // 流体消耗量（单位：mB）
    private static final int FLUID_AMOUNT = 100;  // 每次消耗的流体量（可调整）
    private int nextPyrotheumAmount = 100;  // 初始为 100mb
    private int nextCryotheumAmount = 100;  // 初始为 100mb
    private static final Random random = new Random();
    private int fluidCycle = 1;

    public VoidMinerProcessingMachine(IMachineBlockEntity holder) {
        super(holder);
    }


    public boolean onWorking(@Nullable GTRecipe recipe) {
        if (getOffsetTimer() % 20 == 0) {

            int fluidPerSecond = 100000000;

            // 创建流体对象
            FluidStack drillingFluid = new FluidStack(
                    Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:drilling_fluid"))),
                    fluidPerSecond
            );

            // 检查流体是否足够
            boolean hasEnoughFluid = MachineUtils.canInputFluid(drillingFluid, this);

            if (hasEnoughFluid) {
                // 流体足够，消耗流体
                MachineUtils.inputFluid(drillingFluid, this);

                // 执行配方其他的工作（例如温度变化等）
                super.onWorking();
            } else {
                // 流体不足，停止配方执行（可以清空输出等）
                recipe.outputs.clear();  // 清空产出物品
            }


            int temperature = getCurrentTemperature();

            if (isOverheated) {
                // 降温逻辑
                temperature = Math.max(MIN_TEMP, temperature - 100); // 每个游戏刻降温1K
                setCurrentTemperature(temperature);
                if (temperature == MIN_TEMP) {
                    isOverheated = false; // 降温结束，恢复正常状态
                }
                return false; // 无法继续运行
            }
            // 检查当前温度
            if (temperature >= MAX_TEMP) {
                isOverheated = true; // 进入过热状态
                nextPyrotheumAmount = FLUID_AMOUNT;
                nextCryotheumAmount = FLUID_AMOUNT;
            }



            if (fluidCycle == 1) {  // 偶数次使用 Pyrotheum
                int currentFluidAmount = nextPyrotheumAmount;  // 使用更新后的流体量

                // 创建 Pyrotheum 流体
                FluidStack pyrotheumFluid = new FluidStack(
                        Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:pyrotheum"))),
                        currentFluidAmount
                );

                // 检查输入仓是否有足够流体
                boolean isFluidSufficient = MachineUtils.inputFluid(pyrotheumFluid, this);

                if (isFluidSufficient) {
                    // 流体足够，执行温度变化
                    int temperatureIncrease = currentFluidAmount / 100;
                    temperature = Math.min(MAX_TEMP, temperature + temperatureIncrease);
                    setCurrentTemperature(temperature);

                    // 更新流体消耗量
                    nextPyrotheumAmount = (int) Math.floor(currentFluidAmount * 1.02);  // 更新流体量
                }
                // 如果流体不足，什么也不做，机器继续工作，温度不变

                // 切换到下一轮流体
                fluidCycle = 2;  // 下一次使用 Cryotheum
            } else if (fluidCycle == 2) {  // 奇数次使用 Cryotheum
                int currentFluidAmount = nextCryotheumAmount;  // 使用更新后的流体量

                // 创建 Cryotheum 流体
                FluidStack cryotheumFluid = new FluidStack(
                        Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:cryotheum"))),
                        currentFluidAmount
                );

                // 检查输入仓是否有足够流体
                boolean isFluidSufficient = MachineUtils.inputFluid(cryotheumFluid, this);

                if (isFluidSufficient) {
                    // 流体足够，执行温度变化
                    int temperatureDecrease = currentFluidAmount / 100;
                    temperature = Math.max(MIN_TEMP, temperature - temperatureDecrease);
                    setCurrentTemperature(temperature);

                    // 更新流体消耗量
                    nextCryotheumAmount = (int) Math.floor(currentFluidAmount * 1.02);  // 更新流体量
                }
                // 如果流体不足，什么也不做，机器继续工作，温度不变

                // 切换到下一轮流体
                fluidCycle = 1;  // 下一次使用 Pyrotheum
            }
        }
        return super.onWorking();
    }

    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        // 设定所需的流体量（100,000,000 mb）
        int requiredAmount = 100000000;

        // 创建流体对象
        FluidStack drillingFluid = new FluidStack(
                Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:drilling_fluid"))),
                requiredAmount
        );

        // 检测流体是否足够
        boolean hasEnoughFluid = MachineUtils.canInputFluid(drillingFluid, this);

        // 如果流体不足，阻止配方运行
        if (!hasEnoughFluid) {
            return false;
        }

        // 流体充足，允许配方运行
        return super.beforeWorking(recipe);
    }

    // 更新GUI显示文本
    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);

        if (isOverheated) {
            textList.add(Component.translatable("ctnh.multiblock.blast_furnace.overheat").withStyle(ChatFormatting.RED));
        }
        textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(currentTemperature + "K").withStyle(ChatFormatting.RED)));
        textList.add(Component.translatable("ctnh.void_miner.pyrotheum", nextPyrotheumAmount + " mB").withStyle(ChatFormatting.GOLD));
        textList.add(Component.translatable("ctnh.void_miner.cryotheum", nextCryotheumAmount + " mB").withStyle(ChatFormatting.AQUA));
    }



    public int getParallelCount() {
        if (currentTemperature >= 24000) {
            return 16;  // 温度达到24000时并行数为16
        } else if (currentTemperature >= 20000) {
            return 12;  // 温度达到20000时并行数为12
        } else if (currentTemperature >= 17000) {
            return 8;   // 温度达到17000时并行数为8
        } else if (currentTemperature >= 12000) {
            return 4;   // 温度达到12000时并行数为4
        } else if (currentTemperature >= 10000) {
            return 2;   // 温度达到10000时并行数为2
        } else {
            return 1;   // 温度小于10000时并行数为1
        }
    }

    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof VoidMinerProcessingMachine reactorMachine) {
            int parallelCount = reactorMachine.getParallelCount();

            // 每种物品的基础数量
            int baseAmount = 640;

            // 输出物品的数量会根据并行数调整
            int adjustedAmount = baseAmount * parallelCount;

            // 获取带有 '#c:raw_ores' 标签的所有物品
            List<ItemStack> rawOreItems = getRawOreItems();

            // 确保 rawOreItems 不是空的
            if (rawOreItems.isEmpty()) {
                return ModifierFunction.IDENTITY;  // 如果没有符合条件的物品，返回不修改配方
            }

            // 随机打乱物品顺序
            Collections.shuffle(rawOreItems, random);

            // 创建一个物品列表
            List<Content> itemList = new ArrayList<>();

            // 如果我们有至少10个符合条件的物品，输出它们
            for (int i = 0; i < Math.min(10, rawOreItems.size()); i++) {
                // 获取当前随机选中的矿物
                ItemStack rawOreItem = rawOreItems.get(i);

                // 创建物品的内容并添加到 itemList
                if (!rawOreItem.isEmpty()) {
                    // 创建一个大小为 adjustedAmount 的物品
                    SizedIngredient ingredient = SizedIngredient.create(Ingredient.of(rawOreItem), adjustedAmount);

                    // 使用 SizedIngredient 创建 Content，并确保数量正确
                    itemList.add(new Content(ingredient, adjustedAmount, adjustedAmount, 0, null, null));
                }
            }

            // 修改配方的输出
            if (!itemList.isEmpty()) {
                recipe.outputs.put(ItemRecipeCapability.CAP, itemList);
            }

            // 继续处理配方逻辑
            return recipe1 -> recipe;
        }

        return ModifierFunction.IDENTITY;  // 返回默认修改器
    }

    // 获取所有带有 '#c:raw_ores' 标签的物品
    private static List<ItemStack> getRawOreItems() {
        List<ItemStack> rawOreItems = new ArrayList<>();

        // 创建 '#c:raw_ores' 标签的 TagKey
        TagKey<Item> rawOresTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("c", "raw_ores"));

        // 遍历所有注册的物品
        for (Item item : ForgeRegistries.ITEMS) {
            // 获取物品的默认实例
            ItemStack itemStack = item.getDefaultInstance();

            // 检查物品是否属于 '#c:raw_ores' 标签
            if (itemStack.is(rawOresTag)) {
                rawOreItems.add(itemStack);
            }
        }

        return rawOreItems;
    }


}


