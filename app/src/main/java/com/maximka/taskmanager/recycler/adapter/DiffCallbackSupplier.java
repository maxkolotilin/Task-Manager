package com.maximka.taskmanager.recycler.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

public interface DiffCallbackSupplier<T> {
    DiffUtil.Callback getDiffCallback(@NonNull final List<T> oldList, @NonNull final List<T> newList);
}
