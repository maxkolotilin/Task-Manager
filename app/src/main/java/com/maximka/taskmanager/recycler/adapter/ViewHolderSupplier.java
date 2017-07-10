package com.maximka.taskmanager.recycler.adapter;

import android.support.annotation.NonNull;
import android.view.View;

public interface ViewHolderSupplier<T> {
    ListAdapterViewHolder<T> getViewHolder(@NonNull final View rootView);
}
