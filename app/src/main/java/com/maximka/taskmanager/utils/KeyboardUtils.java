package com.maximka.taskmanager.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {

    public static void hideKeyboard(final View view) {
        Assertion.nonNull(view);
        getInputMethodManager(view.getContext()).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static InputMethodManager getInputMethodManager(@NonNull final Context context) {
        Assertion.nonNull(context);
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private KeyboardUtils() {
    }
}
