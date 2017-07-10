package com.maximka.taskmanager.ui.list.recycler;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.maximka.taskmanager.utils.Assertion;

import java.util.List;

public final class TaskListDiffCallback extends DiffUtil.Callback {
    @NonNull private final List<TaskDataSummary> mOldList;
    @NonNull private final List<TaskDataSummary> mNewList;

    public TaskListDiffCallback(@NonNull final List<TaskDataSummary> oldList,
                                @NonNull final List<TaskDataSummary> newList) {
        Assertion.nonNullContent(oldList);
        Assertion.nonNullContent(newList);

        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        final String oldItemId = mOldList.get(oldItemPosition).getId();
        final String newItemId = mNewList.get(newItemPosition).getId();

        return oldItemId.equals(newItemId);
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        final TaskDataSummary oldItem = mOldList.get(oldItemPosition);
        final TaskDataSummary newItem = mNewList.get(newItemPosition);

        return oldItem.getDueDate().equals(newItem.getDueDate())
                && oldItem.getProgressPercent().equals(newItem.getProgressPercent())
                && oldItem.getState().equals(newItem.getState())
                && oldItem.getTitle().equals(newItem.getTitle());
    }
}
