package com.maximka.taskmanager.ui.data;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.formatters.DateFormatter;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

public final class TaskSummaryViewData {
    @NonNull private final String mId;
    @NonNull private final String mTitle;
    @NonNull private final String mDueDate;
    @NonNull private final Percent mProgressPercent;
    @NonNull private final TaskState mState;
    @ColorRes private final int mDueDateColor;

    public static TaskSummaryViewData from(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        final Date dueDate = taskData.getDueDate();
        @ColorRes final int colorResId =
                dueDate.before(new Date()) ? R.color.text_color_warning : R.color.text_color_secondary;

        return new TaskSummaryViewData(taskData.getId(),
                                       taskData.getTitle(),
                                       DateFormatter.format(dueDate),
                                       taskData.getProgressPercent(),
                                       taskData.getState(),
                                       colorResId);
    }

    private TaskSummaryViewData(@NonNull final String id,
                                @NonNull final String title,
                                @NonNull final String dueDate,
                                @NonNull final Percent progressPercent,
                                @NonNull final TaskState state,
                                @ColorRes final int dueDateColor) {

        Assertion.nonNull(id, title, dueDate, progressPercent, state);

        mId = id;
        mTitle = title;
        mDueDate = dueDate;
        mProgressPercent = progressPercent;
        mState = state;
        mDueDateColor = dueDateColor;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDueDate() {
        return mDueDate;
    }

    @NonNull
    public Percent getProgressPercent() {
        return mProgressPercent;
    }

    @NonNull
    public TaskState getState() {
        return mState;
    }

    @ColorRes
    public int getDueDateColor() {
        return mDueDateColor;
    }
}
