package com.maximka.taskmanager.utils;

import java.util.Collection;

public class Assertion {
    private Assertion() {
    }

    public static void nonNull(final Object ...objects) {
        nonNullContent(objects);
    }

    public static void nonNullContent(final Collection<?> collection) {
        nonNullContent(collection.toArray());
    }

    private static void nonNullContent(final Object[] objects) {
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] == null) {
                throw new NullPointerException("Argument " + i + " is null!");
            }
        }
    }

    public static void nonNegative(final long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value is negative: " + value);
        }
    }
}
