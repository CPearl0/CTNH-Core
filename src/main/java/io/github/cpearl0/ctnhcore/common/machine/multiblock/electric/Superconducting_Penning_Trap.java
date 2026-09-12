package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import com.ctnhlang.CN;
import com.ctnhlang.EN;
import com.ctnhlang.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;
import tech.vixhentx.mcmod.ctnhlib.utils.MachineUtils;

import java.util.List;

public class Superconducting_Penning_Trap extends RecipeElectricMultiblockMachine
                                          implements ITieredMachine, IExplosionMachine, IControllable {

    @CN("反电子量:%d")
    @EN("Positron amount: %d")
    public static Lang antiElectric;

    @CN("反中子量:%d")
    @EN("Antineutron amount: %d")
    public static Lang antiNu;

    @CN("反质子量:%d")
    @EN("Antiproton amount: %d")
    public static Lang antiProton;

    @CN("§c警告：供电不足，约束场即将失效！")
    @EN("§cWarning: insufficient power; the containment field is about to fail!")
    public static Lang noEnergyWaring;

    @CN("约束危险物质")
    @EN("Contain hazardous materials")
    public static Lang restoreDanger;

    @Key("ctnh.trap_electric")
    @CN("当前存储电量:%deu")
    @EN("Current stored power: %d EU")
    public static Lang trapElectric;

    @CN("供电不足：约束场需要持续供电，机器无法运行")
    @EN("Insufficient power: the containment field needs a continuous power supply, the machine cannot run")
    public static Lang insufficientPower;

    @CN("允许存储电量上限:%deu")
    @EN("Maximum stored power: %d EU")
    public static Lang trapElectricMax;

    public BlockPos pos;
    public Level level;
    public boolean isconnect = false;
    public int anti_electron = 0;
    public int anti_proton = 0;
    public int anti_nu = 0;
    public int max_eu = Integer.MAX_VALUE;
    public String AL = "anti_electron";
    public String AP = "anti_proton";
    public String AN = "anti_nu";
    public int tickwarring = 0;
    private EnergyContainerList energyContainer;
    private int energy = 0;
    public boolean no_energy_waring = false;
    public double consume_mutiple = 1.0;
    @Nullable
    protected TickableSubscription tickSubs;

    public Superconducting_Penning_Trap(IMachineBlockEntity holder) {
        super(holder);
    }

    // 初始化
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        this.energyContainer = getEnergyContainer();
        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.energyContainer = EnergyContainerList.EMPTY;
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }

    protected void updateTickSubscription() {
        if (isFormed) {
            tickSubs = subscribeServerTick(tickSubs, this::tick);
        } else if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    public boolean danger() {
        if (anti_nu > 0 || anti_proton > 0 || anti_electron > 0) {
            return true;
        }
        return false;
    }

    private void consumeEnergy() {
        int energyToConsume = 8192;
        if (isconnect) {
            energyToConsume += 32768;
        }
        energyToConsume += (anti_electron + anti_proton + anti_nu) / 10;
        energy = energyToConsume;
        this.energyContainer = getEnergyContainer();
        if (this.energyContainer.getEnergyStored() >= energyToConsume) {
            long consumed = this.energyContainer.removeEnergy(energyToConsume);
            if (consumed == energyToConsume) {
                getRecipeLogic().setStatus(RecipeLogic.Status.WORKING);
                no_energy_waring = false;
                tickwarring = 0;
            } else {
                no_energy_waring = true;
                tickwarring += 1;
                if (tickwarring >= 100) {
                    doExplosion(3f);
                }
                getRecipeLogic().setWaiting(insufficientPower.translate());
            }
        } else {
            this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored());
            no_energy_waring = true;
            tickwarring += 1;
            if (tickwarring > 200 && danger()) {
                doExplosion(9f);
            }
            getRecipeLogic().setWaiting(insufficientPower.translate());
        }
    }

    public void tick() {
        if (isWorkingEnabled()) consumeEnergy();
        var level = getLevel();
        var pos = MachineUtils.getOffset(this, 0, 20, 6);

        if (isActive()) {
            if (getMachine(level, pos) instanceof WideParticleAccelerator gmachine) {
                isconnect = true;
            }
        } else {
            if (danger()) {
                tickwarring += 1;
            }

        }
    }

    // public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
    // if (machine instanceof Superconducting_Penning_Trap dmachine) {
    // var level = dmachine.self().getLevel();
    // var pos = dmachine.self().getPos();
    //
    // pos = MachineUtils.getOffset(dmachine,0, 20, 6);
    // if (getMachine(level, pos) instanceof WideParticleAccelerator gmachine) {
    // dmachine.pos = pos;
    // dmachine.level = level;
    // dmachine.isconnect = true;
    // }
    // int muti=(int)((dmachine.anti_electron+ dmachine.anti_nu+ dmachine.anti_proton)/1000);
    // return ModifierFunction.builder()
    // .eutMultiplier(Math.max(muti,1))
    // .build();
    // }
    // return ModifierFunction.NULL;
    // }
    @Override
    public void addDisplayText(List<Component> textList) {
        if (isconnect) {
            textList.add(textList.size(), WideParticleAccelerator.connect.translate());
        }
        if (no_energy_waring) {
            textList.add(textList.size(), noEnergyWaring.translate());
        }
        MultiblockDisplayText.builder(textList, isFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .setWorkingStatusKeys(
                        "gtceu.multiblock.idling",
                        "gtceu.multiblock.idling",
                        restoreDanger.key())
                .addEnergyUsageExactLine(energy)
                .addWorkingStatusLine();
        textList.add(textList.size(), trapElectricMax.translate(String.format("%d", anti_nu)));
        textList.add(textList.size(), antiElectric.translate(String.format("%d", anti_electron)));
        textList.add(textList.size(), antiNu.translate(String.format("%d", anti_nu)));
        textList.add(textList.size(), antiProton.translate(String.format("%d", anti_proton)));
        textList.add(textList.size(), antiElectric.translate(String.format("%d", anti_electron)));
    }

    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        if (!forDrop) {
            tag.putDouble(AL, anti_electron);
            tag.putDouble(AP, anti_proton);
            tag.putDouble(AN, anti_nu);
        }
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        anti_electron = tag.contains(AL) ? tag.getInt(AL) : 0;
        anti_proton = tag.contains(AP) ? tag.getInt(AP) : 0;
        anti_nu = tag.contains(AN) ? tag.getInt(AN) : 0;
    }
}
