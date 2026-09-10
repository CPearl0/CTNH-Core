package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotoVoltaicDroneStation;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.RecipeElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.handler.RecipeHandlerGroup;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.ctnhlang.CN;
import com.ctnhlang.EN;
import earth.terrarium.adastra.api.planets.Planet;
import org.jetbrains.annotations.NotNull;
import tech.vixhentx.mcmod.ctnhlib.langprovider.Lang;

import java.util.List;

public class SpacePhotovoltaicBaseStation extends RecipeElectricMultiblockMachine implements ITieredMachine {

    @CN("当前光伏方块等级:%d")
    @EN("Current photovoltaic block tier: %d")
    public static Lang spacephotovoltaicbasestationInfoPvcTier0;

    @CN("当前结构耐热等级:%d")
    @EN("Current structure heat-resistance tier: %d")
    public static Lang spacephotovoltaicbasestationInfoPvcTier1;

    @CN("当前结构发电量:%.2f")
    @EN("Current power generation: %.2f")
    public static Lang spacephotovoltaicbasestationInfoPvcTier2;

    @CN("当前维度光倍率:%d")
    @EN("Current dimensional light multiplier: %d")
    public static Lang spacephotovoltaicbasestationInfoPvcTier3;

    public SpacePhotovoltaicBaseStation(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    IPBData data;

    @Persisted
    private int pv_level = 0;
    @Persisted
    private int heat = 1;
    @Persisted
    private boolean orbit = false;
    @Persisted
    private double muti = 1;
    @Persisted
    public BlockPos Drone_location;

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        Drone_location = getPos();
        Object type = this.getMultiblockState().getMatchContext().get("IPBData");
        if (type instanceof IPBData coil) {
            this.data = coil;
            this.heat = data.getheatlevel();
            this.pv_level = data.getTier();
            muti = dimension_check();

        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.isFormed()) {
            Drone_location = getPos();
            Object type = this.getMultiblockState().getMatchContext().get("IPBData");
            if (type instanceof IPBData coil) {
                this.data = coil;
                this.heat = data.getheatlevel();
                this.pv_level = data.getTier();
                muti = dimension_check();
            }
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        pv_level = 0;
        heat = 0;
        orbit = false;
        Drone_location = getPos();
    }

    public double dimension_check() {
        var level = getLevel();
        var dimension = level.dimension();
        orbit = false;
        double rate = 1;
        if (dimension == Planet.MOON_ORBIT || dimension == Planet.VENUS_ORBIT || dimension == Planet.MERCURY_ORBIT ||
                dimension == Planet.MARS_ORBIT || dimension == Planet.GLACIO_ORBIT) {
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

    @CN("该配方需要在空间站维度运行")
    @EN("Needs orbit enviroment to run this recipe")
    static Lang orbit_only;

    @Override
    public Component beforeWorking(@NotNull GTRecipe recipe) {
        if (recipe.data.getBoolean("orbit") && !orbit) {
            return orbit_only.translate();
        }

        return super.beforeWorking(recipe);
    }

    public static Component recipeModifier(MetaMachine machine, RecipeHandlerGroup group, @NotNull GTRecipe recipe) {
        if (machine instanceof SpacePhotovoltaicBaseStation pmachine) {
            var level = pmachine.getLevel();
            var pos = pmachine.getPos();
            var EUt = 0;
            var dmachine = getMachine(level, pmachine.Drone_location);
            if (dmachine instanceof PhotoVoltaicDroneStation pvdrone) {
                if (pvdrone.isActive()) {
                    EUt += pvdrone.getEut();
                }
            }

            if (recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_ASSEMBER)) {
                var tier = recipe.data.getInt("tier");
                var input = recipe.data.getInt("input");
                var duration = 1.0;
                var true_eut = pmachine.muti * pmachine.heat * 131072 + EUt;
                var parallel = Math.max((true_eut / input), 0.01); // 真实并行
                if (parallel < 1) {
                    duration = 1 / (parallel * parallel);
                    parallel = 1;
                } else {
                    duration = ((int) (parallel));
                }
                // var new_recipe = recipe;
                //
                // new_recipe.tickOutputs.put(EURecipeCapability.CAP, EURecipeCapability.makeEUContent(new
                // EnergyStack(1)));
                // recipe = new_recipe;
                var maxparallel = ParallelLogic.getParallelAmount(group, recipe, (int) parallel);
                recipe.multiplyAllContents(maxparallel);
                recipe.multiplyDuration(1 / duration);
                recipe.parallels *= maxparallel;
                return null;
            }
            if (recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_GENERATOR)) {
                var true_eut = EUt + pmachine.muti * 131072 * pmachine.heat;

                recipe.multiplyEUt(true_eut);
                return null;
            }
        }
        return RecipeModifier.nullWrongType(SpacePhotovoltaicBaseStation.class, machine);
    }

    public void addDisplayText(List<Component> textList) {
        textList.add(textList.size(),
                spacephotovoltaicbasestationInfoPvcTier0.translate(String.format("%d", heat)));
        textList.add(textList.size(),
                spacephotovoltaicbasestationInfoPvcTier1.translate(String.format("%d", heat)));
        textList.add(textList.size(), spacephotovoltaicbasestationInfoPvcTier2.translate(
                String.format("%.2f", muti * 131072 * heat)));
        textList.add(textList.size(), spacephotovoltaicbasestationInfoPvcTier3.translate(
                String.format("%.1f", muti)));
        super.addDisplayText(textList);
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }
}
