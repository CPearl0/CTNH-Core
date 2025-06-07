package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.config.ConfigHolder;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Generator;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Reactor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Superconducting_Penning_Trap  extends WorkableElectricMultiblockMachine implements ITieredMachine, IExplosionMachine, IControllable {

    public BlockPos pos;
    public Level level;
    public boolean isconnect=false;
    public int anti_electron=0;
    public int anti_proton=0;
    public int anti_nu=0;
    public int max_eu=Integer.MAX_VALUE;
    public String AL ="anti_electron";
    public String AP ="anti_proton";
    public String AN ="anti_nu";
    public int tickwarring=0;
    private IEnergyContainer energyContainer;
    private int energy=0;
    public boolean no_energy_waring=false;
    @Nullable
    protected TickableSubscription tickSubs;

    public Superconducting_Penning_Trap(IMachineBlockEntity holder)
    {
        super(holder);
    }
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        List<IEnergyContainer> energyContainers = new ArrayList<>();
        this.energyContainer=getEnergyContainer();
        if (getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getServer().tell(new TickTask(0, this::updateTickSubscription));
        }
    }
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.energyContainer = new EnergyContainerList(new ArrayList<>());

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
    public boolean danger()
    {
        if(anti_nu>0||anti_proton>0||anti_electron>0)
        {
            return true;
        }
        return false;
    }
    private void consumeEnergy() {
        int energyToConsume=8192;
        if(isconnect){
            energyToConsume+=32768;
        }
        energyToConsume+=(anti_electron+anti_proton+anti_nu)/10;
        energy=energyToConsume;
        this.energyContainer=getEnergyContainer();
        if (this.energyContainer.getEnergyStored() >= energyToConsume) {
                long consumed = this.energyContainer.removeEnergy(energyToConsume);
                if (consumed == energyToConsume) {
                    getRecipeLogic().setStatus(RecipeLogic.Status.WORKING);
                    no_energy_waring=false;
                    tickwarring =0;
                } else {
                    no_energy_waring=true;
                    tickwarring += 1;
                    if(tickwarring>=100)
                    {
                        doExplosion(3f);
                    }
                    getRecipeLogic().setWaiting(Component.translatable("gtceu.recipe_logic.insufficient_in"));
                }
        }
        else {
            this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored());
            no_energy_waring=true;
            tickwarring += 1;
            if(tickwarring>200&&danger())
            {
                doExplosion(9f);
            }
            getRecipeLogic().setWaiting(Component.translatable("gtceu.recipe_logic.insufficient_in"));
        }
    }
    public void tick() {
        if (isWorkingEnabled()) consumeEnergy();
        var level=getLevel();
        var pos=MachineUtils.getOffset(this,0,20,6);

        if (isActive()) {
            if (getMachine(level, pos) instanceof WideParticleAccelerator gmachine) {
                isconnect = true;
            }
        } else {
            if(danger()) {
                tickwarring += 1;
            }

        }
    }
//    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
//        if (machine instanceof Superconducting_Penning_Trap dmachine) {
//            var level = dmachine.self().getLevel();
//            var pos = dmachine.self().getPos();
//
//            pos = MachineUtils.getOffset(dmachine,0, 20, 6);
//            if (getMachine(level, pos) instanceof WideParticleAccelerator gmachine) {
//                dmachine.pos = pos;
//                dmachine.level = level;
//                dmachine.isconnect = true;
//            }
//            int muti=(int)((dmachine.anti_electron+ dmachine.anti_nu+ dmachine.anti_proton)/1000);
//            return ModifierFunction.builder()
//                    .eutMultiplier(Math.max(muti,1))
//                    .build();
//        }
//        return ModifierFunction.NULL;
//    }
    @Override
    public void addDisplayText(List<Component> textList) {
        if(isconnect) {
            textList.add(textList.size(), Component.translatable("ctnh.connect"));
        }
        if(no_energy_waring)
        {
            textList.add(textList.size(), Component.translatable("ctnh.no_energy_waring"));
        }
        MultiblockDisplayText.builder(textList, isFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .setWorkingStatusKeys(
                        "gtceu.multiblock.idling",
                        "gtceu.multiblock.idling",
                        "ctnh.restore_danger")
                .addEnergyUsageExactLine(energy)
                .addWorkingStatusLine();
            textList.add(textList.size(),Component.translatable("ctnh.trap_electric_max",String.format("%d",max_eu)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_electric",String.format("%d",anti_electron)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_nu",String.format("%d",anti_nu)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_proton",String.format("%d",anti_proton)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_electric",String.format("%d",anti_electron)));
    }
    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        if (!forDrop) {
            tag.putDouble(AL,anti_electron);
            tag.putDouble(AP,anti_proton);
            tag.putDouble(AN,anti_nu);
        }
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        anti_electron=tag.contains(AL)?tag.getInt(AL):0;
        anti_proton=tag.contains(AP)?tag.getInt(AP):0;
        anti_nu=tag.contains(AN)?tag.getInt(AN):0;
    }
    }

