package com.maximka.taskmanager.recycler.adapter;

import android.support.annotation.NonNull;

public interface OnItemClickListener<T> {
    void onItemClicked(final int position, @NonNull final T data);
}
