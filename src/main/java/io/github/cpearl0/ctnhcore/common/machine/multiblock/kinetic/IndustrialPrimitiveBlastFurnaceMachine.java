package io.github.cpearl0.ctnhcore.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;

import java.util.List;

public class IndustrialPrimitiveBlastFurnaceMachine extends NoEnergyMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(IndustrialPrimitiveBlastFurnaceMachine.class,
            WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);
    @Nullable
    protected TickableSubscription temperatureSubs;
    public int currentTemperature = 0;
    @Getter
    public int maxTemperature = 2400;
    public int heatSpeed = 5;
    public int basicTemperature = 0;
    public IndustrialPrimitiveBlastFurnaceMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof IndustrialPrimitiveBlastFurnaceMachine imachine) {
            var parallel = imachine.getParallelCount();
            return ModifierFunction.builder()
                    .inputModifier(ContentModifier.multiplier(parallel))
                    .outputModifier(ContentModifier.multiplier(parallel))
                    .durationMultiplier((int) (1.25 - (imachine.currentTemperature - imachine.basicTemperature) / (imachine.maxTemperature - imachine.basicTemperature) * 0.75) * parallel)
                    .parallels(parallel)
                    .build();
        }
        return ModifierFunction.IDENTITY;
    }

    public int getParallelCount() {
        if (currentTemperature < 600) {
            return 1;
        }
        else if (currentTemperature < 1200) {
            return 2;
        }
        else if (currentTemperature < 1800) {
            return 4;
        }
        else {
            return 8;
        }
    }
    @Override
    public void addDisplayText(List<Component> textList) {
        if (isFormed()) {
            textList.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature", Component.literal(currentTemperature + "K").withStyle(ChatFormatting.RED)));
            textList.add(Component.translatable("ctnh.industrial_primitive_blast_furnace.parallel_count", getParallelCount()));
        }
        super.addDisplayText(textList);
    }
    @Override
    public void onLoad() {
        super.onLoad();

        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateSteamSubscription));
        }
    }
    protected void updateSteamSubscription() {
        if (currentTemperature >= basicTemperature) {
            temperatureSubs = subscribeServerTick(temperatureSubs, this::updateCurrentTemperature);
        } else if (temperatureSubs != null) {
            temperatureSubs.unsubscribe();
            temperatureSubs = null;
        }
    }
    protected void updateCurrentTemperature() {
        basicTemperature = (int) TemperatureUtil.getWorldTemperature(getLevel(),getPos()) + 273;
        if (recipeLogic.isWorking()) {
            if (getOffsetTimer() % 10 == 0) {
                if (currentTemperature < getMaxTemperature()) {
                    currentTemperature = Mth.clamp(currentTemperature + heatSpeed, basicTemperature, getMaxTemperature());
                }
            }
        } else if (currentTemperature > basicTemperature) {
            currentTemperature -= getCoolDownRate();
        }
    }
    protected int getCoolDownRate() {
        return 1;
    }
}
