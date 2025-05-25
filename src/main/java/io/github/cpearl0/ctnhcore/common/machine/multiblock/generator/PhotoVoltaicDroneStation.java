package io.github.cpearl0.ctnhcore.common.machine.multiblock.generator;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.capability.IObjectHolder;
import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import earth.terrarium.adastra.api.planets.Planet;
import io.github.cpearl0.ctnhcore.common.item.IDroneItem;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.LaserSorter;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.part.DroneHolderMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHMaterials;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhotoVoltaicDroneStation extends WorkableElectricMultiblockMachine {

    public PhotoVoltaicDroneStation(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
    @Persisted
    private DroneHolderMachine droneholder;
    @Persisted
    private boolean orbit=false;
    @Persisted
    @Getter
    private int eut=0;
    @Persisted
    private double num=0.0;
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {
            if (part instanceof DroneHolderMachine) {
                droneholder=(DroneHolderMachine)part;
            }

        }
        if(droneholder==null)
        {
            onStructureInvalid();
        }

    }
    @Override
    public void onStructureInvalid() {
        droneholder=null;
        // recheck the ability to make sure it wasn't the one broken
        for (IMultiPart part : getParts()) {
            if (part instanceof DroneHolderMachine holder) {
                holder.setLocked(false);
            }
        }
        super.onStructureInvalid();
    }
    private List<Integer> GetValidDrone()
    {
        List<Integer> droneList = new ArrayList<>();
        for(int slot=0;slot<=14;slot++)
        {
            if(!droneholder.getHeldItem(slot).isEmpty())
            {
                droneList.add(slot);
            }
        }
        return droneList;
    }
    private void ConsumeDrone()
    {
        var drone=GetValidDrone();
        if(drone.isEmpty())return;
        var x=sigmoid(drone.size(),0.25,9);
        num=x;
        for(int i=0;i<drone.size();i++)
        {
            if(Math.random()<x)
            {
                droneholder.consumeItem(drone.get(i));
            }
        }
    }
    public int GetDronePower()
    {
        var drone=GetValidDrone();
        if(drone.isEmpty())return 1;
        int x=0;
        for(int i=0;i<drone.size();i++)
        {
            var holder=droneholder.getHeldItem(drone.get(i));
            var item=(IDroneItem)holder.getItem();
            x=x+item.eut;
        }
        return x;
    }
    public double dimension_check() {
        var level = getLevel();
        var dimension = level.dimension();
        orbit=false;
        double rate = 1;
        if(dimension== Planet.MOON_ORBIT || dimension == Planet.VENUS_ORBIT|| dimension == Planet.MERCURY_ORBIT|| dimension == Planet.MARS_ORBIT|| dimension == Planet.GLACIO_ORBIT)
        {
            orbit=true;
            rate*=2;
        }

        if (dimension == Level.OVERWORLD || dimension.location().getPath().equals("twilightforest:twilight_forest") || dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate *= 0.5;
        }
        else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate *= 1;
        } else if (dimension == Planet.MOON || dimension == Planet.MOON_ORBIT) {
            rate *= 1.5;
        } else if (dimension == Planet.VENUS || dimension == Planet.VENUS_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.MERCURY || dimension == Planet.MERCURY_ORBIT) {
            rate *= 4;
        } else if (dimension == Planet.MARS || dimension == Planet.MARS_ORBIT) {
            rate *= 8;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate *= 16;
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
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        droneholder.setLocked(true);
        return super.beforeWorking(recipe);
    }
    @Override
    public void afterWorking() {
        ConsumeDrone();
        super.afterWorking();
        droneholder.setLocked(false);
    }

    public static double sigmoid(double x, double k, double c) {
        return 1.0 / (1.0 + Math.exp(-k * (x - c)));
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, GTRecipe recipe) {

        if (machine instanceof PhotoVoltaicDroneStation lmachine) {
            var eut=lmachine.GetDronePower()* lmachine.dimension_check();
            var pa=1;
            if (machine instanceof IMultiController controller) {
                if (controller.isFormed()) {
                    int parallels = (Integer)controller.getParallelHatch()
                            .map(IParallelHatch::getCurrentParallel)
                            .orElse(0);
                    if (parallels > 0) {
                        pa=parallels;
                    }

                }
            }
            lmachine.eut=(int)eut;
            return ModifierFunction.builder()
                    .durationMultiplier(pa)
                    .build();

        }

        return ModifierFunction.NULL;
    }
    public void addDisplayText(List<Component> textList) {
        textList.add(textList.size(),Component.translatable("ctnh.pvdrone.t1",String.format("%d",eut)));
        textList.add(textList.size(),Component.translatable("ctnh.pvdrone.t2",String.format("%.4f",num)));
        super.addDisplayText(textList);
    }
}
