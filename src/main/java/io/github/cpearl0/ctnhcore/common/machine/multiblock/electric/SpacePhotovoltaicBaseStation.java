package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import earth.terrarium.adastra.api.planets.Planet;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SpacePhotovoltaicBaseStation extends WorkableElectricMultiblockMachine {
    public SpacePhotovoltaicBaseStation(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }
    @Persisted IPBData data;

    @Persisted private int pv_level=0;
    @Persisted  private int heat=1;
    @Persisted private  boolean orbit=false;
    @Persisted private double muti=1;
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        Object type = this.getMultiblockState().getMatchContext().get("IPBData");
        if (type instanceof IPBData coil) {
            this.data = coil;
            this.heat= data.getheatlevel();
            this.pv_level= data.getTier();
            muti=dimension_check();
        }

    }
    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        pv_level=0;
        heat=0;
        orbit=false;
    }
    public double dimension_check() {
        var level = getLevel();
        var dimension = level.dimension();
        orbit=false;
        double rate = 1;
        if(dimension==Planet.MOON_ORBIT || dimension == Planet.VENUS_ORBIT|| dimension == Planet.MERCURY_ORBIT|| dimension == Planet.MARS_ORBIT|| dimension == Planet.GLACIO_ORBIT)
        {
            orbit=true;
            rate*=1.5;
        }

        if (dimension == Level.OVERWORLD || dimension.location().getPath().equals("twilightforest:twilight_forest") || dimension.location().getPath().equals("mythicbotany:alfheim")) {
            rate *= 0.5;
        }
        else if (dimension == AetherDimensions.AETHER_LEVEL) {
            rate *= 1;
        } else if (dimension == Planet.MOON || dimension == Planet.MOON_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.VENUS || dimension == Planet.VENUS_ORBIT) {
            rate *= 4;
        } else if (dimension == Planet.MERCURY || dimension == Planet.MERCURY_ORBIT) {
            rate *= 8;
        } else if (dimension == Planet.MARS || dimension == Planet.MARS_ORBIT) {
            rate *= 2;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate *= 0.5;
        }

        return rate;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe)
    {
        var freeze=heat*8;
        if(recipe.data.getBoolean("orbit") && !orbit)
        {
            return false;
        }
        FluidStack pyrotheumFluid = new FluidStack(
                Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:pyrotheum"))),
                1000
        );
            boolean isFluidSufficient = MachineUtils.inputFluid(pyrotheumFluid, this);
            if (isFluidSufficient) {
                freeze+=2;
            }
            if(freeze<muti)
            {
                return false;
            }



        return super.beforeWorking(recipe);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if(machine instanceof SpacePhotovoltaicBaseStation pmachine){
            if(recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_ASSEMBER)) {
                var EUt = RecipeHelper.getOutputEUt(recipe);
                var tier = recipe.data.getInt("tier");
                var input = recipe.data.getInt("input");
                var output = 8192 * tier / 2;
                var duration=1.0;
                if (pmachine.pv_level < tier) {
                    return ModifierFunction.NULL;
                }

                var parallel = Math.max((pmachine.muti * tier*8192/input),0.01);
                if(parallel<1)
                {
                    duration=1/(parallel*parallel);
                    parallel=1;
                }
                else
                {
                    duration=((int)(parallel));
                }
                var new_recipe = recipe;

                new_recipe.tickOutputs.put(EURecipeCapability.CAP, EURecipeCapability.makeEUContent((long) 1));
                recipe = new_recipe;
                var maxparallel = ParallelLogic.getParallelAmount(machine, recipe, (int)parallel);
                return ModifierFunction.builder()
                        .parallels(maxparallel)
                        .durationMultiplier(1/duration)
                        .inputModifier(ContentModifier.multiplier(maxparallel))
                        .outputModifier(ContentModifier.multiplier(maxparallel))
                        .build();
            }
            if(recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_GENERATOR))
            {
                return ModifierFunction.builder()
                        .eutMultiplier(pmachine.muti)
                        .build();
            }
        }
        return ModifierFunction.NULL;
    }
    public void addDisplayText(List<Component> textList) {
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.0",String.format("%d",pv_level)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.1",String.format("%d",heat)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.2",String.format("%.2f",muti*8192*tier)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.3",String.format("%.1f",muti)));
        super.addDisplayText(textList);
    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}
