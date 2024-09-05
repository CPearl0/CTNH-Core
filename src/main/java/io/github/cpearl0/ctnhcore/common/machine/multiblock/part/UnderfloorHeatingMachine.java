package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.block.CopperBlockSet;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class UnderfloorHeatingMachine extends WorkableMultiblockMachine implements IDisplayUIMachine{
    @Getter
    public int rate = 100;
    public double steam_consumption_default = 8;

    public UnderfloorHeatingMachine(IMachineBlockEntity holder){
        super(holder);
    }

    public double getEfficiency() {
        var pos = self().getPos();
        var facing = self().getFrontFacing();
        var level = self().getLevel();
        AABB blocks = switch (facing) {
            case NORTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-7, 0, 0), pos.offset(8, 1, 15)));
            case SOUTH -> AABB.of(BoundingBox.fromCorners(pos.offset(-8, 0, -15), pos.offset(7, 1, 0)));
            case WEST -> AABB.of(BoundingBox.fromCorners(pos.offset(0, 0, -8), pos.offset(15, 1, 7)));
            case EAST -> AABB.of(BoundingBox.fromCorners(pos.offset(-15, 0, -7), pos.offset(0, 1, 8)));
            default -> throw new IllegalStateException("Unexpected value: " + facing);
        };
        int copper_shingles = (int) level.getBlockStates(blocks).map(BlockBehaviour.BlockStateBase::getBlock).filter(block -> block.getName().equals(AllBlocks.COPPER_SHINGLES.getStandard().get().getName())).count();
        int exposed_copper_shingles = (int) level.getBlockStates(blocks).map(BlockBehaviour.BlockStateBase::getBlock)
                .filter(block -> {
                    boolean b1 = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, true).get().getName());
                    boolean b2 = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.UNAFFECTED, true).get().getName());
                    boolean b = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.EXPOSED, false).get().getName());
                    return b1 || b2 || b;
                }).count();
        int weathered_copper_shingles = (int) level.getBlockStates(blocks).map(BlockBehaviour.BlockStateBase::getBlock)
                .filter(block -> {
                    boolean b1 = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, true).get().getName());
                    boolean b = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.WEATHERED, false).get().getName());
                    return b1 || b;
                }).count();
        int oxidized_copper_shingles = (int) level.getBlockStates(blocks).map(BlockBehaviour.BlockStateBase::getBlock)
                .filter(block -> {
                    boolean b1 = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, true).get().getName());
                    boolean b = block.getName().equals(AllBlocks.COPPER_SHINGLES.get(CopperBlockSet.BlockVariant.INSTANCE, WeatheringCopper.WeatherState.OXIDIZED, false).get().getName());
                    return b1 || b;
                }).count();
        return (copper_shingles + exposed_copper_shingles * 0.8 + weathered_copper_shingles * 0.75 + oxidized_copper_shingles * 0.6)/(copper_shingles + exposed_copper_shingles + weathered_copper_shingles + oxidized_copper_shingles);
    }

    public void addDisplayText(List<Component> textList) {
        IDisplayUIMachine.super.addDisplayText(textList);
        if (isFormed()) {
            if (!isWorkingEnabled()) {
                textList.add(Component.translatable("gtceu.multiblock.work_paused"));

            } else if (isActive()) {
                textList.add(Component.translatable("gtceu.multiblock.running"));
                int currentProgress = (int) (recipeLogic.getProgressPercent() * 100);
                textList.add(Component.translatable("gtceu.multiblock.progress", currentProgress));
            } else {
                textList.add(Component.translatable("gtceu.multiblock.idling"));
            }

            if (recipeLogic.isWaiting()) {
                textList.add(Component.translatable("gtceu.multiblock.steam.low_steam")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }
            textList.add(Component.translatable("ctnh.multiblock.underfloor_heating_system.steam_consumption",String.format("%.1f", steam_consumption_default*rate/100)));
            var rateText = Component.translatable("ctnh.multiblock.underfloor_heating_system.rate",
                            ChatFormatting.AQUA.toString() + getRate() + "%")
                    .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Component.translatable("ctnh.multiblock.underfloor_heating_system.rate.tooltip"))));
            textList.add(rateText);

            var buttonText = Component.translatable("ctnh.multiblock.underfloor_heating_system.rate_modify");
            buttonText.append(" ");
            buttonText.append(ComponentPanelWidget.withButton(Component.literal("[-]"), "sub"));
            buttonText.append(" ");
            buttonText.append(ComponentPanelWidget.withButton(Component.literal("[+]"), "add"));
            textList.add(buttonText);
            var efficiency = self().holder.self().getPersistentData().getDouble("efficiency");
            if(efficiency == 0){
                efficiency = getEfficiency();
            }
            textList.add(Component.translatable("multiblock.ctnhcore.underfloor_heating_system.efficiency",String.format("%.1f",efficiency * 100)));
        }
    }

    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (!clickData.isRemote) {
            int result = componentData.equals("add") ? 5 : -5;
            this.rate = Mth.clamp(rate + result, 25, 100);
        }
    }
}
