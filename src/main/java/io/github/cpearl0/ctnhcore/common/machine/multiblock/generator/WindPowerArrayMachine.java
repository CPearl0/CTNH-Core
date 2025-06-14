package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.IWorkable;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
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
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.trait.providable_net.IProviableNetHandlerMachine;
import io.github.cpearl0.ctnhcore.common.machine.trait.providable_net.ProvidableNetHandler;
import io.github.cpearl0.ctnhcore.utils.MathUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WindPowerArrayMachine extends MultiblockControllerMachine implements IProviableNetHandlerMachine,IFancyUIMachine, IDisplayUIMachine, IWorkable {
    public static final Material fluidMaterial = GTMaterials.Lubricant;
    private EnergyContainerList energyContainer;
    private List<FluidHatchPartMachine> fluidParts;

    long basicRate;
    final int fluidAmount;
    public WindPowerArrayMachine(IMachineBlockEntity holder,int tier) {
        super(holder);
        this.basicRate = GTValues.V[tier];
        this.fluidAmount = tier;
    }

    ////////////////////
    /// 生命周期      ///
    /// ///////////////
    @Nullable
    protected TickableSubscription tickSubs;
    protected void updateTickSubscription() {
        if (isFormed && isWorkingEnabled) {
            tickSubs = subscribeServerTick(tickSubs, this::tick);
        } else if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }
    @Override
    public void onLoad() {
        super.onLoad();
        if (this.isFormed() && getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        isActive = false;
        fluidParts = null;
        energyContainer = null;
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
        if(getLevel() instanceof ServerLevel)
            quitNet();
    }
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
        isActive = false;
        if(fluidParts!=null){
            fluidParts.clear();
            fluidParts = null;
        }
        energyContainer = null;
        if(getLevel() instanceof ServerLevel)
            quitNet();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        updateBasicRate();
        updateEnergyContainer();
        updateFluidParts();
        updateEfficiencyPara();

        if (getLevel() instanceof ServerLevel serverLevel) {
            joinNet();
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    public void updateEnergyContainer() {
        List<IEnergyContainer> containers = new ArrayList<>();

        for (IMultiPart part : getParts())
            part.self().holder.self()
                    .getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER)
                    .ifPresent(containers::add);

        energyContainer = new EnergyContainerList(containers);
    }
    public void updateFluidParts() {
        fluidParts = getParts().stream()
               .filter(part -> part instanceof FluidHatchPartMachine fpm
                       && fpm.tank.getCapabilityIO() == IO.IN)
               .map(part -> (FluidHatchPartMachine) part)
               .collect(Collectors.toList());
    }
    public static HashMap<String,Integer> dimentionRate = new HashMap<>();
    static{
        dimentionRate.put("minecraft:overworld",1);
        dimentionRate.put("minecraft:nether",2);
        dimentionRate.put("aether:the_aether",2);
        dimentionRate.put("minecraft:the_end",4);
        dimentionRate.put("ad_astra:mars",0);
        dimentionRate.put("ad_astra:venus",16);
        dimentionRate.put("ad_astra:mercury",0);
    }
    public void updateBasicRate() {
        assert getLevel() != null;
        var dimension = getLevel().dimension().location().toString();

        basicRate *= (long) dimentionRate.getOrDefault(dimension, 1);


    }

    /// ////////////////////////////////
    ////////  风电网络           ////////
    //////// ///////////////////////
    @Override
    @SuppressWarnings("DataFlowIssue")
    public List<IProviableNetHandlerMachine> getNeighbor() {
        // BBB
        // BBB
        // B@B
        List<IProviableNetHandlerMachine> ret = new ArrayList<>();
        BlockPos center = getPos().relative(getFrontFacing().getOpposite());
        for(Direction direction : Direction.values()) {
            int ibegin = 2, iend = 4;
            if(!direction.getAxis().isHorizontal()){
                ibegin = 5;
                iend = 6;
            }
            for (int i = ibegin; i <= iend; i++) {
                BlockPos targetCenter = center.relative(direction, i);
                for (Direction neighbourFacing : Direction.values()) {
                    BlockPos targetPos = targetCenter.relative(neighbourFacing);
                    if (getLevel().getBlockState(targetPos).getBlock() instanceof MetaMachineBlock machineBlock
                            && machineBlock.getMachine(getLevel(), targetPos) instanceof WindPowerArrayMachine target
                            && target.isFormed()
                            && target.getFrontFacing() == neighbourFacing)
                        ret.add(target);
                }
            }
        }
        return ret;
    }

    @Getter
    @Nonnull
    ProvidableNetHandler<WindPowerArrayMachine> netHandler = new ProvidableNetHandler<WindPowerArrayMachine>(this) {};

    @Override
    public boolean canProvide() {
        return fluidParts!=null && !fluidParts.isEmpty();
    }

    @Override
    public int getStorage() {
        assert canProvide();
        return fluidParts!=null ? MachineUtils.getFluidStorageBrute(fluidMaterial.getFluid(),fluidParts) : 0;
    }

    @Override
    public int checkAndConsume(int amount) {
        assert canProvide();
        return fluidParts!=null ? MachineUtils.inputFluidBrute(fluidMaterial.getFluid(amount),fluidParts) : amount;
    }
    void joinNet(){
        netHandler.join();
    }
    void quitNet() {
        netHandler.invalidate();
    }

    /// ///////////////////////////////
    /// /        运行逻辑/       ////
    /// //////////////////////////
    boolean needFluid = true;
    float altitude_boost = 1.0f;
    private float getEfficiency(){
        assert getLevel() != null;
        int weather_boost = 1;
        if(getLevel().isRaining())
            weather_boost = 2;
        if(getLevel().isThundering())
            weather_boost = 4;
        return (1+0.3f*MathUtils.fastLog2(netHandler.getNetSize()))
                        * weather_boost
                        * altitude_boost;
    }
    private void updateEfficiencyPara(){
        altitude_boost = 1.0f + (Mth.clamp(getPos().getY(), 64, 256)-64) / (256f-64f);
    }
    @SuppressWarnings("DataFlowIssue")
    public void tick(){
        if(!isWorkingEnabled || !netHandler.ensureNetInfo())return;
        long time = getLevel().getGameTime();
        if(time%20==0) {
            isActive = !needFluid || netHandler.checkAndConsume(fluidAmount);
            needFluid = true;
        }
        if(isActive) {
            needFluid = energyContainer.addEnergy((long) (basicRate * getEfficiency())) != 0;
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (isFormed && getLevel() instanceof ServerLevel) {
            MultiblockDisplayText.builder(textList, isFormed())
                    .setWorkingStatus(isWorkingEnabled,isActive)
                    .addWorkingStatusLine()
                    .addCurrentEnergyProductionLine(needFluid?(long) (basicRate * getEfficiency()):0)
                    .addEnergyProductionLine(basicRate,(long) (basicRate * getEfficiency()));
            textList.add(Component.translatable("info.ctnhcore.network_machine", netHandler.getNetSize()));
            textList.add(Component.translatable("info.ctnhcore.network_machine_efficiency", String.format("%.1f",getEfficiency())));
            if(netHandler.getDeadTime()!=-1)
                textList.add(Component.translatable("info.ctnhcore.network_dirty", (netHandler.getDeadTime() - getLevel().getGameTime())/ 20 ));
        }
    }
    @Override
    @SuppressWarnings("DataFlowIssue")
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

    @Override
    @SuppressWarnings("DataFlowIssue")
    public int getProgress() {
        return isFormed()?(int) (getLevel().getGameTime() % 20):0;
    }

    @Override
    public int getMaxProgress() {
        return isFormed()?20:0;
    }

    @DescSynced
    private boolean isActive = false;
    @Override
    public boolean isActive(){
        return isActive && needFluid;
    }

    @Getter @Setter
    @DescSynced
    @Persisted
    boolean isWorkingEnabled = true;

}
