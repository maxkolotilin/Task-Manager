package com.maximka.taskmanager.utils;

import android.support.annotation.NonNull;
import android.util.SparseArray;

public final class CollectionsUtils {

    public static int getKeyForValue(@NonNull final SparseArray<?> sparseArray, @NonNull final Object value) {
        Assertion.nonNull(sparseArray, value);

        for (int i = 0; i < sparseArray.size(); i++) {

            final int key = sparseArray.keyAt(i);
            if (value.equals(sparseArray.get(key))) {
                return key;
            }
        }

        throw new IllegalArgumentException("No value " + value + " in " + sparseArray);
    }

    private CollectionsUtils() {
    }
}
