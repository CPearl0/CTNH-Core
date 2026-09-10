package io.github.cpearl0.ctnhcore.common.machine.multiblock;

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
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeMultiblockMachine;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.*;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.mo_guang.ctpp.common.blockentity.IKineticBlockEntityExtension;
import com.mo_guang.ctpp.common.blockentity.KineticMachineBlockEntity;
import com.mo_guang.ctpp.common.machine.IKineticMachine;
import com.mo_guang.ctpp.common.machine.multiblock.part.KineticPartMachine;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KineticElectricMultiblockMachine extends RecipeMultiblockMachine implements IFancyUIMachine,
                                              IDisplayUIMachine, ITieredMachine, IOverclockMachine {

    @CN("动力转速不足：当前 %d，至少需要 64")
    @EN("Insufficient kinetic speed: currently %d, at least 64 required")
    public static Lang insufficientKineticSpeed;

    public KineticElectricMultiblockMachine(IMachineBlockEntity holder) {
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
    public int Ktier = 0;
    protected boolean isGenerator = false;

    //////////////////////////////////////
    // *** Multiblock Lifecycle ***//
    //////////////////////////////////////
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        if (this.rotateBlocks != null && getLevel() != null) {
            for (Long pos : this.rotateBlocks) {
                BlockEntity blockEntity = getLevel().getBlockEntity(BlockPos.of(pos));
                if (blockEntity instanceof KineticBlockEntity kineticBlockEntity &&
                        kineticBlockEntity instanceof IKineticBlockEntityExtension extension) {
                    extension.setCTNHVisualSpeed(0);
                    extension.setCTNHInMultiblock(false);
                }
            }
        }
        this.energyContainer = null;
        this.tier = 0;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        this.energyContainer = getEnergyContainer();
        getKineticContainer();
        this.rotateBlocks = (LongSet) this.getMultiblockState().getMatchContext().getOrDefault("roBlocks",
                LongSets.emptySet());
        for (Long pos : this.rotateBlocks) {
            BlockEntity blockEntity = getLevel().getBlockEntity(BlockPos.of(pos));
            if (blockEntity instanceof KineticBlockEntity kineticBlockEntity &&
                    kineticBlockEntity instanceof IKineticBlockEntityExtension extension) {
                extension.setCTNHInMultiblock(true);
            }
        }
        this.updateActiveBlocks(this.recipeLogic.isWorking());

        this.tier = getTier();
    }

    public int getTier() {
        var Ktier = this.Ktier;
        if (this.speed >= 256) Ktier += 1.0;
        var Etier = GTUtil.getFloorTierByVoltage(getOverclockVoltage());
        return Math.min(Ktier, Etier);
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

            while (a.hasNext()) {
                Long pos = a.next();
                BlockPos blockPos = BlockPos.of(pos);
                BlockEntity blockEntity = ((Level) Objects.requireNonNull(this.getLevel())).getBlockEntity(blockPos);
                this.updateRotateBlock(active, blockEntity);
            }
        }
    }

    public void updateRotateBlock(boolean active, BlockEntity blockEntity) {
        if (blockEntity instanceof KineticBlockEntity kineticBlockEntity &&
                kineticBlockEntity instanceof IKineticBlockEntityExtension extension &&
                !(kineticBlockEntity instanceof KineticMachineBlockEntity)) {
            extension.setCTNHVisualSpeed(active ? this.speed - 1 : 0);
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
    public void getKineticContainer() {
        // 只限输入不能输出
        for (IMultiPart part : this.getParts()) {
            if (part instanceof KineticPartMachine kineticPart) {
                if (kineticPart.getIO() == IO.IN) {
                    this.speed = Math.min(256, Math.abs(kineticPart.getKineticHolder().getSpeed()));
                    if (kineticPart.getKineticDefinition().torque > this.maxTorque) {
                        this.Ktier = Math.max(((KineticPartMachine) part).getTier(), Ktier);
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
        var handlers = getCapabilitiesFlat(IO.IN, EURecipeCapability.CAP);
        if (handlers.isEmpty()) handlers = getCapabilitiesFlat(IO.OUT, EURecipeCapability.CAP);
        for (IRecipeHandler<?> handler : handlers) {
            if (handler instanceof IEnergyContainer container) {
                containers.add(container);
            }
        }
        return new EnergyContainerList(containers);
    }

    public Component beforeWorking(@NotNull GTRecipe recipe) {
        var failReason = super.beforeWorking(recipe);
        if (failReason != null) return failReason;
        this.previousSpeed = this.speed;
        this.speed = 256.0F;

        for (IMultiPart part : this.getParts()) {
            if (part instanceof IKineticMachine kineticPart) {
                if (this.inputPartsMax.contains(kineticPart.getKineticHolder().getBlockPos())) {
                    this.speed = Math.min(256, Math.abs(kineticPart.getKineticHolder().getSpeed()));
                }
            }
        }

        if (this.speed != this.previousSpeed) {
            this.updateRotateBlocks(true);
        }
        if (this.speed < 64) {
            return insufficientKineticSpeed.translate((int) this.speed);
        }

        return null;
    }

    @Override
    public long getOverclockVoltage() {
        if (this.energyContainer == null) {
            this.energyContainer = getEnergyContainer();
        }
        if (this.isGenerator) {
            // Generators
            return energyContainer.getEffectiveVoltage();
        } else {
            // Machines
            return energyContainer.getEffectiveVoltage();
        }
    }

    public int getOverclockTier() {
        return getTier();
    }

    @Override
    public void setOverclockTier(int tier) {}
}
