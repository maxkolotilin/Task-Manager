package com.maximka.taskmanager.dialogs;

import android.support.annotation.Nullable;

import com.annimon.stream.function.Consumer;

public interface DialogWithAttachableConsumer<T> {
    void attachResultConsumer(@Nullable final Consumer<T> consumer);
}
