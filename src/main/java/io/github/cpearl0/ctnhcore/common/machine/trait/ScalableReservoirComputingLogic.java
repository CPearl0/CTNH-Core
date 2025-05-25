package io.github.cpearl0.ctnhcore.common.machine.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import io.github.cpearl0.ctnhcore.common.machine.multiblock.electric.ScalableReservoirComputingMachine;
import io.github.cpearl0.ctnhcore.registry.CTNHDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ScalableReservoirComputingLogic extends RecipeLogic {

    ScalableReservoirComputingMachine machine;
    public LivingEntity lockedSacrifice;
    public ScalableReservoirComputingLogic(IRecipeLogicMachine machine) {
        super(machine);
        this.machine = (ScalableReservoirComputingMachine) machine;
    }

    @Override
    public void serverTick() {
        machine.updateSacrifice();
        super.serverTick();
    }

    @Override
    public Iterator<GTRecipe> searchRecipe() {
        GTRecipeType recipeType = machine.getRecipeType();
        if (!machine.hasProxies()) return null;
        var iterator = recipeType.getLookup()
                .getRecipeIterator(machine,
                        recipe -> !recipe.isFuel && recipe.matchRecipe(machine).isSuccess()
                                && recipe.matchTickRecipe(machine).isSuccess()
                                && matchSacrifice(recipe)
                );
        boolean any = false;
        while (iterator.hasNext()) {
            GTRecipe recipe = iterator.next();
            if (recipe == null) continue;
            any = true;
            break;
        }
        if (any) {
            iterator.reset();
            return iterator;
        }
        for (GTRecipeType.ICustomRecipeLogic logic : recipeType.getCustomRecipeLogicRunners()) {
            GTRecipe recipe = logic.createCustomRecipe(machine);
            if (recipe != null) return Collections.singleton(recipe).iterator();
        }
        return Collections.emptyIterator();
    }

    @Override
    protected boolean handleRecipeIO(GTRecipe recipe, IO io) {
        if(io == IO.OUT){
            var t = getRecipeOutputCWUt(recipe);
            if(t==machine.maxCWUt){
                machine.duration += getRecipeOutputLifeDuration(recipe);
            }
            else {
                machine.duration = getRecipeOutputLifeDuration(recipe);
                machine.maxCWUt = t;
            }
            markLastRecipeDirty();
            lockedSacrifice = null;
            machine.updateSacrifice();
        }
        return super.handleRecipeIO(recipe, io);
    }

    @Override
    public void setupRecipe(GTRecipe recipe) {
        LivingEntity sacrifice = getSacrifice(recipe);
        if(sacrifice != null) {
            sacrifice.hurt(CTNHDamageTypes.COMPUTATION_SACRIFICE.source(machine.getLevel()),Float.MAX_VALUE);
            if(sacrifice.isRemoved() || !sacrifice.isAlive())
                super.setupRecipe(recipe);
        }
    }

    @Nullable
    private LivingEntity getSacrifice(GTRecipe recipe) {
        if(machine.getAabb() == null)return null;
        if(lockedSacrifice == null) return null;

        EntityType<?> input = getRecipeInputSacrifice(recipe);
        if (input == null) return null;

        if (lockedSacrifice.isAlive() && machine.getAabb().contains(lockedSacrifice.position())
                && input.equals(lockedSacrifice.getType())) {
            return lockedSacrifice;
        }

        return null;
    }

    public static int getRecipeOutputCWUt(GTRecipe recipe) {
        return recipe.data.getInt("maxCWUt");
    }
    public static int getRecipeOutputLifeDuration(GTRecipe recipe) {
        return recipe.data.getInt("wetwareDuration");
    }
    @Nullable
    public static EntityType<?> getRecipeInputSacrifice(GTRecipe recipe) {
        var loc = new ResourceLocation( recipe.data.getString("sacrifice") );
        return ForgeRegistries.ENTITY_TYPES.getValue(loc);
    }
    public boolean matchSacrifice(GTRecipe recipe) {
        EntityType<?> input = getRecipeInputSacrifice(recipe);
        return input != null && lockedSacrifice != null && lockedSacrifice.isAlive()
                && input.equals(lockedSacrifice.getType());
    }
}
