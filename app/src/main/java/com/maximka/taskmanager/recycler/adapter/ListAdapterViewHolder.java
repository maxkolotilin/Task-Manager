package com.maximka.taskmanager.recycler.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maximka.taskmanager.utils.Assertion;

public abstract class ListAdapterViewHolder<T> extends RecyclerView.ViewHolder {
    public ListAdapterViewHolder(@NonNull final View itemView) {
        super(itemView);
        Assertion.nonNull(itemView);
    }

    public abstract void bind(@NonNull final T data);
}
