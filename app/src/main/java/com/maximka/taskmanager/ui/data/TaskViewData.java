package com.maximka.taskmanager.ui.data;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.formatters.DateFormatter;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

public final class TaskViewData {
    @NonNull private final String mId;
    @NonNull private final String mTitle;
    @NonNull private final String mDescription;
    @NonNull private final Date mStartDate;
    @NonNull private final Date mDueDate;
    @NonNull private final TaskState mState;
    @NonNull private final Percent mProgress;
    @NonNull private final TimeInterval mEstimatedTime;

    public static TaskViewData from (@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        return new TaskViewData(taskData.getId(),
                                taskData.getTitle(),
                                taskData.getDescription(),
                                taskData.getStartDate(),
                                taskData.getDueDate(),
                                taskData.getState(),
                                taskData.getProgressPercent(),
                                taskData.getEstimatedTime());
    }

    private TaskViewData(@NonNull final String id,
                         @NonNull final String title,
                         @NonNull final String description,
                         @NonNull final Date startDate,
                         @NonNull final Date dueDate,
                         @NonNull final TaskState state,
                         @NonNull final Percent progress,
                         @NonNull final TimeInterval estimatedTime) {

        Assertion.nonNull(id, title, description, startDate, dueDate, state, progress, estimatedTime);

        mId = id;
        mTitle = title;
        mDescription = description;
        mStartDate = startDate;
        mDueDate = dueDate;
        mState = state;
        mProgress = progress;
        mEstimatedTime = estimatedTime;
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
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getFormattedStartDate() {
        return DateFormatter.format(mStartDate);
    }

    @NonNull
    public String getFormattedDueDate() {
        return DateFormatter.format(mDueDate);
    }

    @NonNull
    public Date getDueDate() {
        return mDueDate;
    }

    @NonNull
    public TaskState getState() {
        return mState;
    }

    @NonNull
    public Percent getProgress() {
        return mProgress;
    }

    @NonNull
    public TimeInterval getEstimatedTime() {
        return mEstimatedTime;
    }
}
