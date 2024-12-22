package io.github.cpearl0.ctnhcore.registry;

import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CTNHRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CTNHCore.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CTNHCore.MODID);

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }

    public static void init(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
