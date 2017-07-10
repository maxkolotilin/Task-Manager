package com.maximka.taskmanager.ui.list.recycler;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

public final class TaskDataSummary {
    @NonNull private String mId;
    @NonNull private String mTitle;
    @NonNull private Date mDueDate;
    @NonNull private Percent mProgressPercent;
    @NonNull private TaskState mState;

    public static TaskDataSummary from(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        return new TaskDataSummary(taskData.getId(),
                                   taskData.getTitle(),
                                   taskData.getDueDate(),
                                   taskData.getProgressPercent(),
                                   taskData.getState());
    }

    private TaskDataSummary(@NonNull final String id,
                            @NonNull final String title,
                            @NonNull final Date dueDate,
                            @NonNull final Percent progressPercent,
                            @NonNull final TaskState state) {

        Assertion.nonNull(id, title, dueDate, progressPercent, state);

        mId = id;
        mTitle = title;
        mDueDate = dueDate;
        mProgressPercent = progressPercent;
        mState = state;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    String getTitle() {
        return mTitle;
    }

    @NonNull
    Date getDueDate() {
        return mDueDate;
    }

    @NonNull
    Percent getProgressPercent() {
        return mProgressPercent;
    }

    @NonNull
    TaskState getState() {
        return mState;
    }
}
