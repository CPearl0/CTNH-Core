package io.github.cpearl0.ctnhcore.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class AstralPlanetSpecialEffects extends DimensionSpecialEffects {
    public AstralPlanetSpecialEffects() {
        super(Float.NaN, true, SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
        return vec3.multiply(
                v * 0.94 + 0.06,
                v * 0.94 + 0.06,
                v * 0.91 + 0.09);
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }
    @Nullable
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        // Prevent the FogRenderer from rendering the sunrise if the sun isn't setting in the west.
        return getSunriseColor(timeOfDay, partialTicks, 14180147);
    }

    @Nullable
    public static float[] getSunriseColor(float timeOfDay, float partialTicks, int sunColor) {
        float timeCos = Mth.cos(timeOfDay * (float) (Math.PI * 2));
        if (timeCos >= -0.4f && timeCos <= 0.4f) {
            float time = timeCos / 0.4f * 0.5f + 0.5f;
            float alpha = 1 - (1 - Mth.sin(time * (float) Math.PI)) * 0.99F;
            alpha *= alpha;
            var rgba = new float[4];
            rgba[0] = time * 0.3f + FastColor.ARGB32.red(sunColor) / 255f * 0.7f;
            rgba[1] = time * time * 0.7f + FastColor.ARGB32.green(sunColor) / 255f * 0.5f;
            rgba[2] = FastColor.ARGB32.blue(sunColor) / 255f * 0.6f;
            rgba[3] = alpha;
            return rgba;
        } else {
            return null;
        }
    }
}
