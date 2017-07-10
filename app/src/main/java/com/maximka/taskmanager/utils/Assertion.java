package com.maximka.taskmanager.utils;

public class Assertion {
    private Assertion() {
    }

    public static void nonNull(final Object ...objects) {
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] == null) {
                throw new NullPointerException("Argument " + i + " is null!");
            }
        }
    }
}
