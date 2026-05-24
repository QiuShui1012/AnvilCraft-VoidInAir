package com.qiushui1012.mod.voidinair.util;

import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public class LambdaUtil {
    public static <T> void noop(@Nullable T t) {}

    public static <T, U> void noop(@Nullable T t, @Nullable U u) {}

    public static <T, U, V> void noop(@Nullable T t, @Nullable U u, @Nullable V v) {}

    public static <T, U, V, W> void noop(@Nullable T t, @Nullable U u, @Nullable V v, @Nullable W w) {}

    public static <T> @UnknownNullability T identity(@Nullable T t) {
        return t;
    }

    public static Transformation qjdh(int rot) {
        return new Transformation(
            new Matrix4f()
                .translation(0.5F, 0.0F, 0.5F)
                .rotate(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(rot)))
                .translate(0.0F, 0.0F, -0.0625F)
                .scale(-1.0F, -1.0F, 1.0F)
        );
    }

    public static Transformation affw(Direction facing) {
        float offset = 0.25F;
        return new Transformation(
            new Vector3f(0.5F - facing.getStepX() * offset, 0.34375F, 0.5F - facing.getStepZ() * offset),
            Axis.YP.rotationDegrees(-facing.getOpposite().toYRot()),
            new Vector3f(-1.0F, -1.0F, 1.0F),
            null
        );
    }
}
