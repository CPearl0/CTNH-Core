package io.github.cpearl0.ctnhcore.recipe;


import com.simibubi.create.foundation.fluid.FluidIngredient;

import io.github.cpearl0.ctnhcore.registry.CTNHRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class LiquidBurningRecipe implements Recipe<FluidRecipeWrapper> {

    protected final ResourceLocation id;
    protected FluidIngredient fluidIngredients;
    protected int burnTime;
    protected boolean superheated;

    //@SuppressWarnings("deprecation")
    public LiquidBurningRecipe(ResourceLocation id, FluidIngredient fluid, int burnTime, boolean superheated) {
        this.id = id;
        this.fluidIngredients = fluid;
        this.burnTime = burnTime;
        this.superheated = superheated;
    }

    @Override
    public boolean matches(FluidRecipeWrapper wrapper, Level world) {
        if(fluidIngredients == null)
            return false;
        if(wrapper == null)
            return false;
        if(wrapper.fluid == null)
            return false;
        return fluidIngredients.test(wrapper.fluid);
    }

    @Override
    public ItemStack assemble(FluidRecipeWrapper pContainer, RegistryAccess pRegistryAccess) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CTNHRecipes.LIQUID_BURNING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return CTNHRecipes.LIQUID_BURNING_TYPE.get();
    }

    public FluidIngredient getFluidIngredient() {
        return fluidIngredients;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public boolean isSuperheated() {
        return this.superheated;
    }
}
