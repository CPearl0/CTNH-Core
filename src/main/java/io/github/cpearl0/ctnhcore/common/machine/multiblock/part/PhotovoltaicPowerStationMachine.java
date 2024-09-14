package io.github.cpearl0.ctnhcore.common.machine.multiblock.part;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import earth.terrarium.adastra.api.planets.Planet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class PhotovoltaicPowerStationMachine  extends WorkableElectricMultiblockMachine {
    public final int BASIC_RATE;

    public PhotovoltaicPowerStationMachine(IMachineBlockEntity holder, int basicRate, Object... args) {
        super(holder, args);
        BASIC_RATE = basicRate;
    }

    @Override
    public boolean keepSubscribing() {
        return true;
    }

    private boolean isValidPhotovoltaicPower() {
        var time = Objects.requireNonNull(getLevel()).dayTime();
        if (time > 12000 && time < 23000)
            return false;
        var facing = getFrontFacing();
        var pos = getHolder().pos();
        switch (facing) {
            case NORTH -> {
                for (var x = -2; x < 2; x++) {
                    for (var z = 1; z < 6; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putBoolean("valid", false);
                            return false;
                        }
                    }
                }
            }
            case SOUTH -> {
                for (var x = -2; x < 2; x++) {
                    for (var z = -5; z < 0; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putBoolean("valid", false);
                            return false;
                        }
                    }
                }
            }
            case WEST -> {
                for (var x = 1; x < 6; x++) {
                    for (var z = -2; z < 2; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putBoolean("valid", false);
                            return false;
                        }
                    }
                }
            }
            case EAST -> {
                for (var x = -5; x < 0; x++) {
                    for (var z = -2; z < 2; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putBoolean("valid", false);
                            return false;
                        }
                    }
                }
            }
        }
        getHolder().self().getPersistentData().putBoolean("valid", true);
        return true;
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof PhotovoltaicPowerStationMachine powerStationMachine))
            return null;
        var level = machine.getLevel();
        assert level != null;
        var time = level.dayTime();
        var rate = Math.sin((double) time / 12000 * 3.14);
        var basic_rate = powerStationMachine.BASIC_RATE;
        var dimension = level.dimension();
        if (dimension == Level.OVERWORLD ||
                dimension.location().getPath().equals("twilightforest:twilight_forest") ||
                dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate *= 1;
        } else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate *= 2;
        } else if (dimension == Planet.MOON ||
                dimension == Planet.MOON_ORBIT) {
            rate *= 4;
        } else if (dimension == Planet.VENUS ||
                dimension == Planet.VENUS_ORBIT) {
            rate *= 6;
        } else if (dimension == Planet.MERCURY ||
                dimension == Planet.MERCURY_ORBIT) {
            rate *= 16;
        } else if (dimension == Planet.MARS ||
                dimension == Planet.MARS_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.GLACIO ||
                dimension == Planet.GLACIO_ORBIT) {
            rate *= 32;
        }
        var newrecipe = recipe.copy();
        newrecipe.tickOutputs.put(EURecipeCapability.CAP, newrecipe.copyContents(newrecipe.tickOutputs, ContentModifier.of(rate * basic_rate, 0)).get(EURecipeCapability.CAP));
        machine.getHolder().self().getPersistentData().putDouble("energy", rate * basic_rate * 512);
        return newrecipe;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (!isValidPhotovoltaicPower())
            return false;
        return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            var valid = getHolder().self().getPersistentData().getBoolean("valid");
            var outputEnergy = getHolder().self().getPersistentData().getDouble("energy");
            var voltageName = GTValues.VNF[GTUtil.getTierByVoltage((long) outputEnergy)];
            if (!valid) {
                textList.add(textList.size(), Component.translatable("multiblock.ctnh.photovoltaic_power_station_invalid").withStyle(ChatFormatting.RED));
            }
            textList.add(textList.size(), Component.translatable("multiblock.ctnh.photovoltaic_power_station1", (outputEnergy / 2048 * 100)));
            textList.add(textList.size(), Component.translatable("multiblock.ctnh.photovoltaic_power_station2", FormattingUtil.formatNumbers(outputEnergy), voltageName));
        }
    }
}
