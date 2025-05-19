package io.github.cpearl0.ctnhcore.client.renderer.utils;

import com.mojang.math.Axis;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public abstract class RenderUtils {
    public static EnumMap<Direction, Axis> directionAxises = new EnumMap<>(Direction.class);
    static {
        directionAxises.put(Direction.NORTH, Axis.ZN);
        directionAxises.put(Direction.SOUTH, Axis.ZP);
        directionAxises.put(Direction.EAST, Axis.XP);
        directionAxises.put(Direction.WEST, Axis.XN);
        directionAxises.put(Direction.UP, Axis.YP);
        directionAxises.put(Direction.DOWN, Axis.YN);
    }
    public static EnumMap<Direction, Vector3i> dircetionVectors = new EnumMap<>(Direction.class);
    static {
        dircetionVectors.put(Direction.NORTH, new Vector3i(0, 0, -1));
        dircetionVectors.put(Direction.SOUTH, new Vector3i(0, 0, 1));
        dircetionVectors.put(Direction.EAST, new Vector3i(1, 0, 0));
        dircetionVectors.put(Direction.WEST, new Vector3i(-1, 0, 0));
        dircetionVectors.put(Direction.UP, new Vector3i(0, 1, 0));
        dircetionVectors.put(Direction.DOWN, new Vector3i(0, -1, 0));
    }
    public static HashMap<Vector3i, Axis> vectorToAxis = new HashMap<>();
    static {
        vectorToAxis.put(new Vector3i(0, 0, -1), Axis.ZN);
        vectorToAxis.put(new Vector3i(0, 0, 1), Axis.ZP);
        vectorToAxis.put(new Vector3i(1, 0, 0), Axis.XP);
        vectorToAxis.put(new Vector3i(-1, 0, 0), Axis.XN);
        vectorToAxis.put(new Vector3i(0, 1, 0), Axis.YP);
        vectorToAxis.put(new Vector3i(0, -1, 0), Axis.YN);
    }
    public static Vector3i cross(Vector3i v1, Vector3i v2) {
        return new Vector3i(v1.y() * v2.z() - v1.z() * v2.y(), v1.z() * v2.x() - v1.x() * v2.z(), v1.x() * v2.y() - v1.y() * v2.x());
    }
    /**
     * 实际上是叉乘
     * @param facing 方块的实际朝向
     * @param relative 在模型中希望面对玩家的朝向
     * @return 返回模型的朝向轴,在这个轴上逆时针旋转90度就可以得到正确的模型方向
     */
    @Nullable
    public static Axis getFacingAxis(Direction facing,Direction relative) {
//        Quaternionf a = (Quaternionf) directionAxises.get(facing);
//        Quaternionf b = (Quaternionf) directionAxises.get(relative);
//        return (Axis)a.mul(b);
        //打表出奇迹
        var a = dircetionVectors.get(facing);
        var b = dircetionVectors.get(relative);
        var c = cross(a, b);
//        if(c.equals(new Vector3i(0, 0, 0)))return vectorToAxis.get(a);
        return vectorToAxis.get(c);
    }
    //这两函数可以在多方快成型判断用
    public static boolean isLeftHand(Direction controller,Direction part) {
        return getFacingAxis(part, controller) == Axis.YP;
    }
    public static boolean isRightHand(Direction controller,Direction part) {
        return getFacingAxis(part, controller) == Axis.YN;
    }

}
