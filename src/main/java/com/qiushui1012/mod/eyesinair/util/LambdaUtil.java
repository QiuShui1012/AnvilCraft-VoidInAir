package com.qiushui1012.mod.eyesinair.util;

import org.jetbrains.annotations.UnknownNullability;
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
}
