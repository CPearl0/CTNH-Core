package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaterPowerStationMachine extends CoilWorkableElectricMultiblockMachine {
    public int water = 0;
    public double efficiency = 1;
    public WaterPowerStationMachine(IMachineBlockEntity holder){
        super(holder);
        efficiency = 1 + 0.1 * getCoilTier();
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if(machine instanceof WaterPowerStationMachine wmachine) {
            var random = Math.random() * 0.4 + 0.6;
            return ModifierFunction.builder().eutMultiplier(wmachine.water * random * wmachine.efficiency / 100).build();
        }
        return ModifierFunction.NULL;
    }

    @Override
    public boolean onWorking() {
        if(getOffsetTimer() % 20 == 0){
            var level = getLevel();
            var length = getParts().size() + 2;
            var pos = getPos();
            water = 0;
            for (int xoffset = -length + 1; xoffset < length; xoffset++) {
                for (int zoffset = -length + 1; zoffset < length; zoffset++) {
                    for (int yoffset = 0; yoffset > -4; yoffset--) {
                        if (Math.sqrt(Math.pow(xoffset, 2) + Math.pow(zoffset, 2)) <= length) {
                            if (level.getBlockState(pos.offset(xoffset, yoffset, zoffset)).equals(Blocks.WATER.defaultBlockState())) {
                                water++;
                            }
                        }
                    }
                }
            }
        }
        return super.onWorking();
    }

    @Override
    public void addDisplayText(List<Component> textList) {
            if (isFormed()) {
                var outputEnergy = isActive() && recipeLogic.getLastRecipe() != null ?
                        RecipeHelper.getOutputEUt(recipeLogic.getLastRecipe()) : 0;
                var voltageName = GTValues.VNF[GTUtil.getTierByVoltage(outputEnergy)];
                textList.add(Component.translatable("multiblock.ctnh.water_power_station1", water));
                textList.add(Component.translatable("multiblock.ctnh.water_power_station.efficiency", String.format("%.1f",efficiency*100)));
                textList.add(Component.translatable("multiblock.ctnh.water_power_station2", FormattingUtil.formatNumbers(outputEnergy), voltageName));
            }
        super.addDisplayText(textList);
    }
}
//本多方块的创意来源于GTQT整合包，对其中的部分内容做了修改
