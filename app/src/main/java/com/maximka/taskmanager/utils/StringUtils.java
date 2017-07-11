package com.maximka.taskmanager.utils;

import android.support.annotation.Nullable;

public final class StringUtils {

    public static boolean isEmpty(@Nullable final String string) {
        return string == null || string.isEmpty();
    }

    private StringUtils() {
    }
}
