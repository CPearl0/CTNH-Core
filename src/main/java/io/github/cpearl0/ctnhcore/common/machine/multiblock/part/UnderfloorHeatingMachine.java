package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

import java.util.List;

public class UnderfloorHeatingMachine extends WorkableMultiblockMachine implements IDisplayUIMachine{

    public double rate = 100;
    public UnderfloorHeatingMachine(IMachineBlockEntity holder){
        super(holder);
    }
    public double getRate(){
        return this.rate;
    }
    public void addDisplayText(List<Component> textList) {
        IDisplayUIMachine.super.addDisplayText(textList);
        if (isFormed()) {
            var throttleText = Component.translatable("ctnh.multiblock.underfloor_heating_system.rate",
                            ChatFormatting.AQUA.toString() + getRate() + "%")
                    .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Component.translatable("ctnh.multiblock.underfloor_heating_system.rate.tooltip"))));
            textList.add(throttleText);

            var buttonText = Component.translatable("ctnh.multiblock.underfloor_heating_system.rate_modify");
            buttonText.append(" ");
            buttonText.append(ComponentPanelWidget.withButton(Component.literal("[-]"), "sub"));
            buttonText.append(" ");
            buttonText.append(ComponentPanelWidget.withButton(Component.literal("[+]"), "add"));
            textList.add(buttonText);
        }
    }

    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (!clickData.isRemote) {
            int result = componentData.equals("add") ? 5 : -5;
            this.rate = Mth.clamp(rate + result, 25, 100);
        }
    }
}
