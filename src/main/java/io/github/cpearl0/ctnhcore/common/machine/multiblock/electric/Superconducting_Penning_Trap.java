package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Generator;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.Arc_Reactor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Superconducting_Penning_Trap  extends WorkableElectricMultiblockMachine implements ITieredMachine {

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
    public Superconducting_Penning_Trap(IMachineBlockEntity holder)
    {
        super(holder);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof Superconducting_Penning_Trap dmachine) {
            var level = dmachine.self().getLevel();
            var pos = dmachine.self().getPos();

            pos = MachineUtils.getOffset(dmachine,0, 20, 6);
            if (getMachine(level, pos) instanceof WideParticleAccelerator gmachine) {
                dmachine.pos = pos;
                dmachine.level = level;
                dmachine.isconnect = true;
            }
            int muti=(int)((dmachine.anti_electron+ dmachine.anti_nu+ dmachine.anti_proton)/1000);
            return ModifierFunction.builder()
                    .eutMultiplier(Math.max(muti,1))
                    .build();
        }
        return ModifierFunction.NULL;
    }
    @Override
    public void addDisplayText(List<Component> textList) {
        if(isconnect) {
            textList.add(textList.size(), Component.translatable("ctnh.connect"));
        }
            textList.add(textList.size(),Component.translatable("ctnh.trap_electric_max",String.format("%d",max_eu)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_electric",String.format("%d",anti_electron)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_nu",String.format("%d",anti_nu)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_proton",String.format("%d",anti_proton)));
            textList.add(textList.size(),Component.translatable("ctnh.anti_electric",String.format("%d",anti_electron)));

        super.addDisplayText(textList);
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

