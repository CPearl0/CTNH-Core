package io.github.cpearl0.ctnhcore.registry.worldgen;

import io.github.cpearl0.ctnhcore.CTNHCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

import java.lang.reflect.Field;

public class CTNHDensityFunctions {
    public static final ResourceKey<DensityFunction> DEPTH = ResourceKey.create(Registries.DENSITY_FUNCTION, CTNHCore.id("depth"));
    public static final ResourceKey<DensityFunction> FACTOR = ResourceKey.create(Registries.DENSITY_FUNCTION, CTNHCore.id("factor"));
    public static final ResourceKey<DensityFunction> SLOPED_CHEESE = ResourceKey.create(Registries.DENSITY_FUNCTION, CTNHCore.id("sloped_cheese"));
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE = ResourceKey.create(Registries.DENSITY_FUNCTION, CTNHCore.id("base_3d_noise"));
    public static final ResourceKey<DensityFunction> FINAL_DENSITY = ResourceKey.create(Registries.DENSITY_FUNCTION, CTNHCore.id("final_density"));
    public static void bootstrap(BootstapContext<DensityFunction> ctx){
        Class<?> noiseRouter = NoiseRouterData.class;
        // 获取私有字段
        Field field = null;
        Field field2 = null;
        Field field3 = null;
        Field field4 = null;
        Field field5 = null;
        try {
            field = noiseRouter.getDeclaredField("ENTRANCES");
            field2 = noiseRouter.getDeclaredField("SPAGHETTI_2D");
            field3 = noiseRouter.getDeclaredField("SPAGHETTI_ROUGHNESS_FUNCTION");
            field4 = noiseRouter.getDeclaredField("PILLARS");
            field5 = noiseRouter.getDeclaredField("NOODLE");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        field2.setAccessible(true);
        field3.setAccessible(true);
        field4.setAccessible(true);
        field5.setAccessible(true);
        ResourceKey<DensityFunction> ENTRANCES = null;
        ResourceKey<DensityFunction> SPAGHETTI_2D = null;
        ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = null;
        ResourceKey<DensityFunction> PILLARS = null;
        ResourceKey<DensityFunction> NOODLE = null;
        try {
            ENTRANCES = (ResourceKey<DensityFunction>) field.get(noiseRouter);
            SPAGHETTI_2D = (ResourceKey<DensityFunction>) field2.get(noiseRouter);
            SPAGHETTI_ROUGHNESS_FUNCTION = (ResourceKey<DensityFunction>) field3.get(noiseRouter);
            PILLARS = (ResourceKey<DensityFunction>) field4.get(noiseRouter);
            NOODLE = (ResourceKey<DensityFunction>) field5.get(noiseRouter);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        var holderGetter = ctx.lookup(Registries.NOISE);
        var holderGetter2 = ctx.lookup(Registries.DENSITY_FUNCTION);
        DensityFunction densityFunction1 = DensityFunctions.mul(DensityFunctions.constant(-1),DensityFunctions.cacheOnce(DensityFunctions.blendAlpha()));
        DensityFunction densityFunction2 = DensityFunctions.add(DensityFunctions.constant(1),densityFunction1);
        DensityFunction densityFunction3 = DensityFunctions.mul(DensityFunctions.blendOffset(), densityFunction2);
        DensityFunction densityFunction4 = DensityFunctions.add(DensityFunctions.constant(-0.5037500262260437), DensityFunctions.spline(CubicSpline.constant(0.02F))).clamp(-0.81,2.5);
        DensityFunction densityFunction5 = DensityFunctions.mul(densityFunction4, DensityFunctions.cacheOnce(DensityFunctions.blendAlpha()));
        DensityFunction densityFunction6 = DensityFunctions.add(densityFunction3, densityFunction5);
        ctx.register(DEPTH, DensityFunctions.add(DensityFunctions.yClampedGradient(-64,320,1.5,-1.5), DensityFunctions.flatCache(DensityFunctions.cache2d(densityFunction6))));
        ctx.register(FACTOR, DensityFunctions.flatCache(DensityFunctions.cache2d(
                DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(10), DensityFunctions.add(DensityFunctions.constant(1), DensityFunctions.mul(DensityFunctions.constant(-1), DensityFunctions.cacheOnce(DensityFunctions.blendAlpha())))),
                        DensityFunctions.add(DensityFunctions.spline(CubicSpline.constant(4)).clamp(0,8),DensityFunctions.cacheOnce(DensityFunctions.blendAlpha()))
                        ))));
        ctx.register(BASE_3D_NOISE, BlendedNoise.createUnseeded(0.25,0.2,80,90,8));
        ctx.register(SLOPED_CHEESE, DensityFunctions.add(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(BASE_3D_NOISE)), DensityFunctions.mul(DensityFunctions.constant(4), DensityFunctions.mul(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(FACTOR)), DensityFunctions.add(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(DEPTH)),
                DensityFunctions.mul(DensityFunctions.noise(holderGetter.getOrThrow(Noises.JAGGED), 1500, 0).halfNegative(),
                        DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.add(DensityFunctions.mul(DensityFunctions.spline(CubicSpline.constant(0)).clamp(0, 1.28), DensityFunctions.cacheOnce(DensityFunctions.blendAlpha())),
                                DensityFunctions.mul(DensityFunctions.constant(0), DensityFunctions.add(DensityFunctions.constant(1), DensityFunctions.mul(DensityFunctions.constant(-1), DensityFunctions.cacheOnce(DensityFunctions.blendAlpha())))))))))).quarterNegative())));

        DensityFunction densityFunction7 = DensityFunctions.add(DensityFunctions.constant(1.5) , DensityFunctions.mul(DensityFunctions.constant(-0.64), new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(SLOPED_CHEESE)))).clamp(0,0.5);
        DensityFunction densityFunction8 = DensityFunctions.add(DensityFunctions.constant(0.27), DensityFunctions.noise(holderGetter.getOrThrow(Noises.CAVE_CHEESE), 1, 0.666666)).clamp(-1, 1);
        DensityFunction densityFunction9 = DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(4), DensityFunctions.noise(holderGetter.getOrThrow(Noises.CAVE_LAYER), 1, 8).square()),
                DensityFunctions.add(densityFunction7, densityFunction8));
        DensityFunction densityFunction10 = DensityFunctions.min(DensityFunctions.min(densityFunction9, new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(ENTRANCES))), DensityFunctions.add(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(SPAGHETTI_2D)), new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(SPAGHETTI_ROUGHNESS_FUNCTION))));
        DensityFunction densityFunction11 = DensityFunctions.rangeChoice(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(PILLARS)), -1000000, 0.03, DensityFunctions.constant(-1000000), new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(PILLARS)));
        DensityFunction densityFunction12 = DensityFunctions.add(DensityFunctions.constant(10), DensityFunctions.rangeChoice(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(SLOPED_CHEESE)), -1000000, 1.5625,
                DensityFunctions.min(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(SLOPED_CHEESE)), DensityFunctions.mul(DensityFunctions.constant(5), new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(ENTRANCES)))),
                DensityFunctions.max(densityFunction10, densityFunction11)));
        DensityFunction densityFunction13 = DensityFunctions.min(new DensityFunctions.HolderHolder(holderGetter2.getOrThrow(NOODLE)), DensityFunctions.mul(DensityFunctions.constant(0.64), DensityFunctions.interpolated(DensityFunctions.blendDensity(DensityFunctions.add(DensityFunctions.constant(-10),
                DensityFunctions.mul(densityFunction12, DensityFunctions.yClampedGradient(296,320,1,0)))))).squeeze());

        ctx.register(FINAL_DENSITY, DensityFunctions.max(DensityFunctions.yClampedGradient(-64,-63,1,-1), densityFunction13));
    }
}
