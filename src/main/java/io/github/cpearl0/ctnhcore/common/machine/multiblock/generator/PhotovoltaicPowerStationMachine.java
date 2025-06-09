package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.IWorkable;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.FancyMachineUIWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;
import earth.terrarium.adastra.api.planets.Planet;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotovoltaicPowerStationMachine extends MultiblockControllerMachine implements IFancyUIMachine,IDisplayUIMachine, IWorkable {
    //const
    public static final int START_TIME = 23000;
    public static final int END_TIME = 13000;

    public final int BASIC_RATE;
    private int rate_mul = 0;

    private long lastOutputEnergy;
//    @Override
    @Getter @Setter
    private boolean isWorkingEnabled = true;
    private EnergyContainerList energyContainer;
    public PhotovoltaicPowerStationMachine(IMachineBlockEntity holder, int basicRate) {
        super(holder);
        BASIC_RATE = basicRate * 512;
    }

    //最好成型再用
    public void updateEnergyContainer() {
        List<IEnergyContainer> containers = new ArrayList<>();

        for (IMultiPart part : getParts())
            part.self().holder.self()
                    .getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER)
                    .ifPresent(containers::add);

        energyContainer = new EnergyContainerList(containers);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.isFormed() && getLevel() instanceof ServerLevel serverLevel) {
            updateEnergyContainer();
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        energyContainer = null;
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
        energyContainer = null;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        updateEnergyContainer();
        //计算发电效率
        var dimension = getLevel().dimension();
        if (dimension == Level.OVERWORLD || dimension.location().getPath().equals("twilightforest:twilight_forest") || dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate_mul = 1;
        } else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate_mul = 2;
        } else if (dimension == Planet.MOON || dimension == Planet.MOON_ORBIT) {
            rate_mul = 4;
        } else if (dimension == Planet.VENUS || dimension == Planet.VENUS_ORBIT) {
            rate_mul = 6;
        } else if (dimension == Planet.MERCURY || dimension == Planet.MERCURY_ORBIT) {
            rate_mul = 16;
        } else if (dimension == Planet.MARS || dimension == Planet.MARS_ORBIT) {
            rate_mul = 2;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate_mul = 32;
        }

        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    /// ///////////////////////////////
    /// /        运行逻辑/       ////
    /// //////////////////////////
    @Nullable
    protected TickableSubscription tickSubs;
    protected void updateTickSubscription() {
        if (isFormed) {
            tickSubs = subscribeServerTick(tickSubs, this::tick);
        } else if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }
    public void tick(){
        var level = getLevel();
        assert level != null;
        //获取时间
        var time = level.getDayTime() % 24000;
        if (time > START_TIME) {
            time -= START_TIME;
        }
        else if (time < END_TIME) {
            time += 24000 - START_TIME;
        }
        else return;

        //计算发电功率
        lastOutputEnergy = (long)(Math.sin((double) time / (END_TIME + 24000 - START_TIME) * Math.PI)
                * BASIC_RATE
                * rate_mul);
        energyContainer.addEnergy(lastOutputEnergy);
    }


    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getMaxProgress() {
        return 0;
    }

    @Override
    public boolean isActive() {
        return lastStatus == Status.VALID;
    }

    //末影b灯怎么讨厌Enum吗
    public enum Status {
        VALID,
        INVALID,
        NIGHT
    }
    Status lastStatus = Status.INVALID;
    private Status getPowerState() {
        var time = Objects.requireNonNull(getLevel()).getDayTime() % 24000;
        if(time%20!=0)return lastStatus;
        if(lastStatus !=Status.VALID || time%1200==0) {//一分钟扫一次,应该不卡?
            if (time > END_TIME && time < START_TIME) {
                return lastStatus = Status.NIGHT;
            }

            var facing = getFrontFacing();
            var pos = getHolder().pos();
            switch (facing) {
                case NORTH -> {
                    for (var x = -2; x <= 2; x++) {
                        for (var z = 1; z < 6; z++) {
                            if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                                return lastStatus = Status.INVALID;
                            }
                        }
                    }
                }
                case SOUTH -> {
                    for (var x = -2; x <= 2; x++) {
                        for (var z = -5; z < 0; z++) {
                            if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                                return lastStatus = Status.INVALID;
                            }
                        }
                    }
                }
                case WEST -> {
                    for (var x = 1; x < 6; x++) {
                        for (var z = -2; z <= 2; z++) {
                            if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                                return lastStatus = Status.INVALID;
                            }
                        }
                    }
                }
                case EAST -> {
                    for (var x = -5; x < 0; x++) {
                        for (var z = -2; z <= 2; z++) {
                            if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                                return lastStatus = Status.INVALID;
                            }
                        }
                    }
                }
            }
        }
        return lastStatus = Status.VALID;
    }
    /// /////////////////
    ///     UI         //
    /// /////////////////
    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        if (isFormed()) {
            var valid = getPowerState();
            var voltageName = GTValues.VNF[GTUtil.getTierByVoltage(lastOutputEnergy)];
            MultiblockDisplayText.builder(textList, isFormed())
                    .setWorkingStatus(isWorkingEnabled,valid == Status.VALID)
                    .addWorkingStatusLine();

            if(valid == Status.VALID) {
                //gtceu.multiblock.generation_eu
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station1", String.format("%.1f", (lastOutputEnergy * 100f / BASIC_RATE))));
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station2", FormattingUtil.formatNumbers(lastOutputEnergy), voltageName));
            }
            else{
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station_"+valid.name().toLowerCase()).withStyle(ChatFormatting.RED));
            }
        }
    }
    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 182 + 8, 117 + 8);
        group.addWidget(new DraggableScrollableWidgetGroup(4, 4, 182, 117).setBackground(getScreenTexture())
                .addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()))
                .addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                        .textSupplier(this.getLevel().isClientSide ? null : this::addDisplayText)
                        .setMaxWidthLimit(200)
                        .clickHandler(this::handleDisplayClick)));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        return new ModularUI(198, 208, this, entityPlayer).widget(new FancyMachineUIWidget(this, 198, 208));
    }
}
