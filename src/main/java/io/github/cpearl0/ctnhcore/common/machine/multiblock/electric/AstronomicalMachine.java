package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import io.github.cpearl0.ctnhcore.common.item.AstronomyCircuitItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.CircuitBusPartMachine;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AstronomicalMachine extends WorkableElectricMultiblockMachine {
    public static final int START_TIME = 23000;
    public static final int END_TIME = 13000;
    public AstronomicalMachine(IMachineBlockEntity holder) {
        super(holder);
    }
    private boolean isValidPhotovoltaicPower() {
        var time = Objects.requireNonNull(getLevel()).getDayTime() % 24000;
        return time > END_TIME && time < START_TIME;
    }
    @Override
    public boolean keepSubscribing() {
        return true;
    }
    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (!isValidPhotovoltaicPower())
            return false;
        final boolean[] begin = {false};
        getParts().stream()
                .filter(part -> part instanceof CircuitBusPartMachine)
                .findFirst()
                .ifPresent(bus -> {
                    var circuitBus = (CircuitBusPartMachine) bus;
                    if (!circuitBus.getInventory().isEmpty()) {
                        var circuit = circuitBus.getInventory().getStackInSlot(0);
                        begin[0] = AstronomyCircuitItem.workInLevel(circuit, getLevel());
                    }
                });
        return begin[0];
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        if (isFormed()) {
            if (!isValidPhotovoltaicPower()) {
                textList.add(Component.translatable("ctnh.multiblock.astronomical.invalid").withStyle(ChatFormatting.RED));
            }
            else {
                super.addDisplayText(textList);
            }
        }
    }

    @Override
    public void afterWorking() {
        getParts().stream()
                .filter(CircuitBusPartMachine.class::isInstance)
                .findFirst()
                .ifPresent(bus -> {
                    var circuitBus = (CircuitBusPartMachine) bus;
                    if (!circuitBus.getInventory().isEmpty()) {
                        var circuit = circuitBus.getInventory().getStackInSlot(0);
                        if (!AstronomyCircuitItem.gainData(circuit, getLevel()))
                            return;
                        circuitBus.getInventory().setStackInSlot(0, new ItemStack(((AstronomyCircuitItem) circuit.getItem()).getFinishedItem().get()));
                    }
                });
        super.afterWorking();
    }
}
