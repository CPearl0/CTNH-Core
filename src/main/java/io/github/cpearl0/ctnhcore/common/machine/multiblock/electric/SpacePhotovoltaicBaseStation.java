package io.github.cpearl0.ctnhcore.common.machine.multiblock.electric;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.api.planets.Planet;
import io.github.cpearl0.ctnhcore.common.block.blockdata.IPBData;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.MachineUtils;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.generator.PhotoVoltaicDroneStation;
import io.github.cpearl0.ctnhcore.registry.CTNHRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SpacePhotovoltaicBaseStation extends WorkableElectricMultiblockMachine implements ITieredMachine {
    public SpacePhotovoltaicBaseStation(IMachineBlockEntity holder, Object... args) {
        super(holder, args);

    }
    @Persisted IPBData data;

    @Persisted private int pv_level=0;
    @Persisted  private int heat=1;
    @Persisted private  boolean orbit=false;
    @Persisted private double muti=1;
    @Persisted public BlockPos Drone_location;
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        Drone_location=getPos();
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
        Drone_location=getPos();
    }
    //#############闪存绑定事件#############//



    public double dimension_check() {
        var level = getLevel();
        var dimension = level.dimension();
        orbit=false;
        double rate = 1;
        if(dimension==Planet.MOON_ORBIT || dimension == Planet.VENUS_ORBIT|| dimension == Planet.MERCURY_ORBIT|| dimension == Planet.MARS_ORBIT|| dimension == Planet.GLACIO_ORBIT)
        {
            orbit=true;
            rate*=4;
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
            rate *= 16;
        } else if (dimension == Planet.GLACIO || dimension == Planet.GLACIO_ORBIT) {
            rate *= 32;
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
//        FluidStack pyrotheumFluid = new FluidStack(
//                Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("gtceu:pyrotheum"))),
//                1000
//        );
//            boolean isFluidSufficient = MachineUtils.inputFluid(pyrotheumFluid, this);
//            if (isFluidSufficient) {
//                freeze+=2;
//            }
//            if(freeze<muti)
//            {
//                return false;
//            }

        return super.beforeWorking(recipe);
    }
    public static ModifierFunction recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof SpacePhotovoltaicBaseStation pmachine) {
            var level = pmachine.getLevel();
            var pos = pmachine.getPos();
            var EUt=0;
            var dmachine=getMachine(level,pmachine.Drone_location);
            if(dmachine instanceof PhotoVoltaicDroneStation pvdrone)
            {
                if(pvdrone.isActive())
                {
                    EUt+=pvdrone.getEut();
                }
            }


                if (recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_ASSEMBER)) {
                    var tier = recipe.data.getInt("tier");
                    var input = recipe.data.getInt("input");
                    var duration = 1.0;
                    var true_eut=pmachine.muti * pmachine.heat * 16384+EUt;
                    var parallel = Math.max((true_eut / input), 0.01); //真实并行
                    if (parallel < 1) {
                        duration = 1 / (parallel * parallel);
                        parallel = 1;
                    }
                    else {
                        duration = ((int) (parallel));
                    }
                    var new_recipe = recipe;

                    new_recipe.tickOutputs.put(EURecipeCapability.CAP, EURecipeCapability.makeEUContent((long) 1));
                    recipe = new_recipe;
                    var maxparallel = ParallelLogic.getParallelAmount(machine, recipe, (int) parallel);
                    return ModifierFunction.builder()
                            .parallels(maxparallel)
                            .durationMultiplier(1 / duration)
                            .inputModifier(ContentModifier.multiplier(maxparallel))
                            .outputModifier(ContentModifier.multiplier(maxparallel))
                            .build();
                }
                if (recipe.recipeType.equals(CTNHRecipeTypes.PHOTOVOLTAIC_GENERATOR)) {
                    var true_eut=EUt+ pmachine.muti*16384* pmachine.heat;
                    recipe.tickOutputs.put(EURecipeCapability.CAP, EURecipeCapability.makeEUContent((long) true_eut));
                    return ModifierFunction.builder()
                            .build();
                }
            }
            return ModifierFunction.NULL;
        }

    public void addDisplayText(List<Component> textList) {
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.0",String.format("%d",heat)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.1",String.format("%d",heat)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.2",String.format("%.2f",muti*16384*heat)));
        textList.add(textList.size(),Component.translatable("ctnh.pvc_tier.3",String.format("%.1f",muti)));
        super.addDisplayText(textList);
    }
    @Override
    public boolean dampingWhenWaiting() {
        return false;
    }
}
