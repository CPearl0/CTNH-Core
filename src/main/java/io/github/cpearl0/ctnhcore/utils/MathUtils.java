package io.github.cpearl0.ctnhcore.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MathUtils {
    public static double angleBetween(Vec3 vec1, Vec3 vec2){
        double dotProduct = vec1.dot(vec2);
        double lengths = vec1.length() * vec2.length();
        double cosTheta = dotProduct / lengths;

        return Math.toDegrees(Math.acos(cosTheta));
    }
    public static float[] dirToRot(Vec3 vec) {
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;

        double yaw = Math.toDegrees(Mth.atan2(-x, z));
        double pitch = Math.toDegrees(Mth.atan2(-y, Math.sqrt(x * x + z * z)));

        return new float[]{(float) yaw, (float) pitch};
    }
}
