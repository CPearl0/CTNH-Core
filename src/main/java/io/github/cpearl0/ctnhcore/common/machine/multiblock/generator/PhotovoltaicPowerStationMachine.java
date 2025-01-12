package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
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

public class PhotovoltaicPowerStationMachine extends WorkableElectricMultiblockMachine {
    public static final int START_TIME = 23000;
    public static final int END_TIME = 13000;
    public final int BASIC_RATE;

    public PhotovoltaicPowerStationMachine(IMachineBlockEntity holder, int basicRate, Object... args) {
        super(holder, args);
        BASIC_RATE = basicRate;
    }

    @Nullable
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof PhotovoltaicPowerStationMachine powerStationMachine)) {
            return ModifierFunction.NULL;
        }
        var level = machine.getLevel();
        assert level != null;
        var time = level.getDayTime() % 24000;
        if (time > START_TIME) {
            time -= START_TIME;
        }
        else if (time < END_TIME) {
            time += 24000 - START_TIME;
        }
        else { // Invalid Time
            return ModifierFunction.NULL;
        }

        var rate = Math.sin((double) time / (END_TIME + 24000 - START_TIME) * Math.PI);
        var basic_rate = powerStationMachine.BASIC_RATE;
        var dimension = level.dimension();
        if (dimension == Level.OVERWORLD || dimension.location().getPath().equals("twilightforest:twilight_forest") || dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate *= 1;
        } else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate *= 2;
        } else if (dimension == Planet.MOON || dimension == Planet.MOON_ORBIT) {
            rate *= 4;
        } else if (dimension == Planet.VENUS || dimension == Planet.VENUS_ORBIT) {
            rate *= 6;
        } else if (dimension == Planet.MERCURY || dimension == Planet.MERCURY_ORBIT) {
            rate *= 16;
        } else if (dimension == Planet.MARS || dimension == Planet.MARS_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate *= 32;
        }
        machine.getHolder().self().getPersistentData().putDouble("energy", rate * basic_rate * 512);
        return ModifierFunction.builder().eutMultiplier(rate*basic_rate).build();
    }

    @Override
    public boolean keepSubscribing() {
        return true;
    }

    public static final int VALID = 0;
    public static final int NIGHT = 1;
    public static final int SHADOWED = 2;

    private int isValidPhotovoltaicPower() {
        var time = Objects.requireNonNull(getLevel()).getDayTime() % 24000;
        if (time > END_TIME && time < START_TIME) {
            getHolder().self().getPersistentData().putInt("valid", 1);
            return NIGHT;
        }

        var facing = getFrontFacing();
        var pos = getHolder().pos();
        switch (facing) {
            case NORTH -> {
                for (var x = -2; x <= 2; x++) {
                    for (var z = 1; z < 6; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putInt("valid", 2);
                            return SHADOWED;
                        }
                    }
                }
            }
            case SOUTH -> {
                for (var x = -2; x <= 2; x++) {
                    for (var z = -5; z < 0; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putInt("valid", 2);
                            return SHADOWED;
                        }
                    }
                }
            }
            case WEST -> {
                for (var x = 1; x < 6; x++) {
                    for (var z = -2; z <= 2; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putInt("valid", 2);
                            return SHADOWED;
                        }
                    }
                }
            }
            case EAST -> {
                for (var x = -5; x < 0; x++) {
                    for (var z = -2; z <= 2; z++) {
                        if (!getLevel().canSeeSky(pos.offset(x, 1, z))) {
                            getHolder().self().getPersistentData().putInt("valid", 2);
                            return SHADOWED;
                        }
                    }
                }
            }
        }
        getHolder().self().getPersistentData().putInt("valid", 0);
        return VALID;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (isValidPhotovoltaicPower() != VALID)
            return false;
        return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            var valid = isValidPhotovoltaicPower();
            var outputEnergy = getHolder().self().getPersistentData().getDouble("energy");
            var voltageName = GTValues.VNF[GTUtil.getTierByVoltage((long) outputEnergy)];
            if (valid == SHADOWED) {
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station_invalid").withStyle(ChatFormatting.RED));
            } else if (valid == NIGHT) {
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station_night").withStyle(ChatFormatting.RED));
            } else {
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station1", String.format("%.1f", (outputEnergy / (BASIC_RATE * 512) * 100))));
                textList.add(Component.translatable("ctnh.multiblock.photovoltaic_power_station2", FormattingUtil.formatNumbers(outputEnergy), voltageName));
            }
        }
    }
}
