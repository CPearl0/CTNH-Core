package io.github.cpearl0.ctnhcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.FancyMachineUIWidget;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyUIProvider;
import com.gregtechceu.gtceu.api.gui.fancy.TooltipsPanel;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.GTUtil;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.mo_guang.ctpp.common.machine.IKineticMachine;
import com.mo_guang.ctpp.common.machine.KineticPartMachine;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KineticElectricMutiblockMachine extends WorkableMultiblockMachine implements IFancyUIMachine, IDisplayUIMachine, ITieredMachine,IOverclockMachine{
    public KineticElectricMutiblockMachine(IMachineBlockEntity holder) {
        super(holder, new Object[0]);
    }
    protected EnergyContainerList energyContainer;
    public float maxTorque = 0.0F;
    public int parallels = 1;
    public LongSet rotateBlocks;
    public float speed = 256.0F;
    public float previousSpeed = 0.0F;
    public List<BlockPos> inputPartsMax = new ArrayList();
    public int tier;
    public int Ktier=0;
    protected boolean isGenerator=false;
    //////////////////////////////////////
    // *** Multiblock Lifecycle ***//
    //////////////////////////////////////
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.energyContainer = null;
        this.tier = 0;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        this.energyContainer = getEnergyContainer();
        getKineticContainer();
        this.rotateBlocks = (LongSet)this.getMultiblockState().getMatchContext().getOrDefault("roBlocks", LongSets.emptySet());
        this.updateActiveBlocks(this.recipeLogic.isWorking());


        this.tier = getTier();
    }
    public int getTier() {

        var Ktier=this.Ktier;
        if(this.speed>=256)Ktier+=1.0;
        var Etier=GTUtil.getFloorTierByVoltage(getMaxVoltage());
        return Math.min(Ktier,Etier);
    }

    @Override
    public void onPartUnload() {
        super.onPartUnload();
        this.energyContainer = null;
        this.tier = 0;
    }
    //////////////////////////////////////
    // ********** ACTIVE ***********//
    //////////////////////////////////////
    public void updateActiveBlocks(boolean active) {
        super.updateActiveBlocks(active);
        this.updateRotateBlocks(active);
    }

    public void updateRotateBlocks(boolean active) {
        if (this.rotateBlocks != null) {
            var a = this.rotateBlocks.iterator();

            while(a.hasNext()) {
                Long pos = a.next();
                BlockPos blockPos = BlockPos.of(pos);
                BlockEntity blockEntity = ((Level)Objects.requireNonNull(this.getLevel())).getBlockEntity(blockPos);
                this.updateRotateBlock(active, blockEntity);
            }
        }

    }

    public void updateRotateBlock(boolean active, BlockEntity blockEntity) {
        if (blockEntity instanceof KineticBlockEntity kineticBlockEntity) {
            if (active) {
                kineticBlockEntity.setSpeed(this.speed);
                kineticBlockEntity.onSpeedChanged(this.previousSpeed);
                kineticBlockEntity.sendData();
            } else {
                kineticBlockEntity.setSpeed(0.0F);
                kineticBlockEntity.onSpeedChanged(this.speed);
                kineticBlockEntity.sendData();
            }
        }

    }

    //////////////////////////////////////
    // ********** GUI ***********//
    //////////////////////////////////////

    @Override
    public void addDisplayText(List<Component> textList) {
        int numParallels;
        boolean exact = false;
        if (recipeLogic.isActive() && recipeLogic.getLastRecipe() != null) {
            numParallels = recipeLogic.getLastRecipe().parallels;
            exact = true;
        } else {
            numParallels = getParallelHatch()
                    .map(IParallelHatch::getCurrentParallel)
                    .orElse(0);
        }

        MultiblockDisplayText.builder(textList, isFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .addEnergyUsageLine(energyContainer)
                .addEnergyTierLine(tier)
                .addMachineModeLine(getRecipeType(), getRecipeTypes().length > 1)
                .addParallelsLine(numParallels, exact)
                .addWorkingStatusLine()
                .addProgressLine(recipeLogic.getProgress(), recipeLogic.getMaxProgress(),
                        recipeLogic.getProgressPercent())
                .addOutputLines(recipeLogic.getLastRecipe());
        getDefinition().getAdditionalDisplay().accept(this, textList);
        IDisplayUIMachine.super.addDisplayText(textList);
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

    @Override
    public List<IFancyUIProvider> getSubTabs() {
        return getParts().stream().filter(Objects::nonNull).map(IFancyUIProvider.class::cast).toList();
    }

    @Override
    public void attachTooltips(TooltipsPanel tooltipsPanel) {
        for (IMultiPart part : getParts()) {
            part.attachFancyTooltipsToController(this, tooltipsPanel);
        }
    }
    //////////////////////////////////////
    // ****** RECIPE LOGIC *******//
    //////////////////////////////////////
    public void getKineticContainer()
    {
        // 只限输入不能输出
        for(IMultiPart part : this.getParts()) {
            if (part instanceof KineticPartMachine kineticPart) {
                if (kineticPart.getIO() == IO.IN) {
                    if (kineticPart.getKineticDefinition().torque > this.maxTorque) {
                        this.Ktier=Math.max(((KineticPartMachine) part).getTier(),Ktier);
                        this.maxTorque = kineticPart.getKineticDefinition().torque;
                        this.inputPartsMax.clear();
                        this.inputPartsMax.add(kineticPart.getKineticHolder().getBlockPos());
                    } else if (kineticPart.getKineticDefinition().torque == this.maxTorque) {
                        this.inputPartsMax.add(kineticPart.getKineticHolder().getBlockPos());
                    }
                }
            }
        }
    }
    public EnergyContainerList getEnergyContainer() {
        List<IEnergyContainer> containers = new ArrayList<>();
        var capabilities = capabilitiesProxy.get(IO.IN, EURecipeCapability.CAP);
        if (capabilities != null) {
            for (IRecipeHandler<?> handler : capabilities) {
                if (handler instanceof IEnergyContainer container) {
                    containers.add(container);
                }
            }
        } else {
            capabilities = capabilitiesProxy.get(IO.OUT, EURecipeCapability.CAP);
            if (capabilities != null) {
                for (IRecipeHandler<?> handler : capabilities) {
                    if (handler instanceof IEnergyContainer container) {
                        containers.add(container);
                    }
                }
            }
        }
        return new EnergyContainerList(containers);
    }
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        boolean result = super.beforeWorking(recipe);
        this.previousSpeed = this.speed;
        this.speed = 256.0F;

        for(IMultiPart part : this.getParts()) {
            if (part instanceof IKineticMachine kineticPart) {
                if (this.inputPartsMax.contains(kineticPart.getKineticHolder().getBlockPos())) {
                    this.speed = Math.min(this.speed, Math.abs(kineticPart.getKineticHolder().getSpeed()));
                }
            }
        }

        if (this.speed != this.previousSpeed) {
            this.updateRotateBlocks(result);
        }
        if(this.speed<64)
        {
            return false;
        }

        return result;
    }
    @Override
    public long getMaxVoltage() {
        if (this.energyContainer == null) {
            this.energyContainer = getEnergyContainer();
        }
        if (this.isGenerator) {
            // Generators
            long voltage = energyContainer.getOutputVoltage();
            long amperage = energyContainer.getOutputAmperage();
            if (amperage == 1) {
                // Amperage is 1 when the energy is not exactly on a tier.
                // The voltage for recipe search is always on tier, so take the closest lower tier.
                // List check is done because single hatches will always be a "clean voltage," no need
                // for any additional checks.
                return GTValues.V[GTUtil.getFloorTierByVoltage(voltage)];
            } else {
                return voltage;
            }
        } else {
            // Machines
            long highestVoltage = energyContainer.getHighestInputVoltage();
            if (energyContainer.getNumHighestInputContainers() > 1) {
                // allow tier + 1 if there are multiple hatches present at the highest tier
                int tier = GTUtil.getTierByVoltage(highestVoltage);
                return GTValues.V[Math.min(tier + 1, GTValues.MAX)];
            } else {
                return highestVoltage;
            }
        }
    }
    public int getOverclockTier() {
        return getTier();
    }

    @Override
    public int getMaxOverclockTier() {
        return getTier();
    }

    @Override
    public int getMinOverclockTier() {
        return getTier();
    }

    @Override
    public void setOverclockTier(int tier) {}
}
