package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import io.github.cpearl0.ctnhcore.common.item.IDroneItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.DroneHolderMachine;

import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.ctnhlang.CN;
import com.ctnhlang.EN;
import earth.terrarium.adastra.api.planets.Planet;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.ArrayList;
import java.util.List;

public class PhotoVoltaicDroneStation extends RecipeElectricMultiblockMachine {

    @CN("提供的能量:%d")
    @EN("Power supplied: %d")
    public static Lang pvdroneInfoT1;

    @CN("无人机消耗概率:%.4f")
    @EN("Drone consumption chance: %.4f")
    public static Lang pvdroneInfoT2;

    @CN("只能在轨道维度执行挖矿配方")
    @EN("Mining recipes can only run in orbit dimensions")
    public static Lang onlyInOrbit;

    public PhotoVoltaicDroneStation(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    private DroneHolderMachine droneholder;

    private boolean orbit = false;

    @Getter
    private int eut = 0;

    private double num = 0.0;

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {
            if (part instanceof DroneHolderMachine) {
                droneholder = (DroneHolderMachine) part;
            }

        }
        if (droneholder == null) {
            onStructureInvalid();
        }
    }

    @Override
    public void onStructureInvalid() {
        droneholder = null;
        // recheck the ability to make sure it wasn't the one broken
        for (IMultiPart part : getParts()) {
            if (part instanceof DroneHolderMachine holderMachine) {
                holderMachine.setLocked(false);
            }
        }
        super.onStructureInvalid();
    }

    private List<Integer> GetValidDrone() {
        List<Integer> droneList = new ArrayList<>();
        for (int slot = 0; slot <= 14; slot++) {
            if (!droneholder.getHeldItem(slot).isEmpty()) {
                droneList.add(slot);
            }
        }
        return droneList;
    }

    private void ConsumeDrone() {
        var drone = GetValidDrone();
        if (drone.isEmpty()) return;
        var x = sigmoid(drone.size(), 0.25, 9);
        num = x;
        for (int i = 0; i < drone.size(); i++) {
            if (Math.random() < x) {
                droneholder.consumeItem(drone.get(i));
            }
        }
    }

    public int GetDronePower() {
        var drone = GetValidDrone();
        if (drone.isEmpty()) return 1;
        int x = 0;
        for (int i = 0; i < drone.size(); i++) {
            var holder = droneholder.getHeldItem(drone.get(i));
            var item = (IDroneItem) holder.getItem();
            x = x + item.eut;
        }
        return x;
    }

    public double dimension_check() {
        var level = getLevel();
        var dimension = level.dimension();
        orbit = false;
        double rate = 1;
        if (dimension == Planet.MOON_ORBIT || dimension == Planet.VENUS_ORBIT || dimension == Planet.MERCURY_ORBIT ||
                dimension == Planet.MARS_ORBIT || dimension == Planet.GLACIO_ORBIT || dimension == Planet.EARTH_ORBIT) {
            orbit = true;
            rate *= 4;
        }

        if (dimension == Level.OVERWORLD || dimension.location().getPath().equals("twilightforest:twilight_forest") ||
                dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate *= 0.5;
        } else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate *= 1;
        } else if (dimension == Planet.MOON || dimension == Planet.MOON_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.VENUS || dimension == Planet.VENUS_ORBIT) {
            rate *= 4;
        } else if (dimension == Planet.MERCURY || dimension == Planet.MERCURY_ORBIT) {
            rate *= 8;
        } else if (dimension == Planet.MARS || dimension == Planet.MARS_ORBIT) {
            rate *= 16;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate *= 32;
        }

        return rate;
    }

    @Override
    public boolean onWorking() {
        if (getOffsetTimer() % 100 == 0) {
            ConsumeDrone();
        }
        return super.onWorking();
    }

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        droneholder.setLocked(true);
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        ConsumeDrone();
        super.afterWorking();
        droneholder.setLocked(false);
        eut = 0;
    }

    public static double sigmoid(double x, double k, double c) {
        return 1.0 / (1.0 + Math.exp(-k * (x - c)));
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, GTRecipe recipe) {
        if (machine instanceof PhotoVoltaicDroneStation lmachine) {
            var eut = lmachine.GetDronePower() * lmachine.dimension_check();
            var pa = 1;
            if (lmachine.isFormed()) {
                int parallels = (Integer) lmachine.getParallelHatch()
                        .map(IParallelHatch::getCurrentParallel)
                        .orElse(0);
                if (parallels > 0) {
                    pa = parallels;
                }

            }
            if (recipe.getOutputContents(ItemRecipeCapability.CAP).isEmpty())
                // 运行非挖矿配方时对光伏进行强化
                lmachine.eut = (int) eut;
            else if (!lmachine.orbit)  // 只能在轨道维度挖矿
                return onlyInOrbit.translate();
            else {
                recipe.multiplyOutputs((int) (0.1 * Math.sqrt(lmachine.GetDronePower())));
                return null;
            }
            recipe.multiplyDuration(pa);
            return null;

        }

        return RecipeModifier.nullWrongType(PhotoVoltaicDroneStation.class, machine);
    }

    public void addDisplayText(List<Component> textList) {
        textList.add(textList.size(),
                pvdroneInfoT1.translate(String.format("%d", eut)));
        textList.add(textList.size(),
                pvdroneInfoT2.translate(String.format("%.4f", num)));
        super.addDisplayText(textList);
    }
}
