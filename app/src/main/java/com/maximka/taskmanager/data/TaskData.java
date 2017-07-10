package com.maximka.taskmanager.data;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

public final class TaskData {
    @NonNull private String mId;
    @NonNull private String mTitle;
    @NonNull private String mDescription;
    @NonNull private Date mStartDate;
    @NonNull private Date mDueDate;
    @NonNull private Percent mProgressPercent;
    @NonNull private TaskState mState;
    @NonNull private TimeInterval mEstimatedTime;

    public TaskData(@NonNull final String id,
                    @NonNull final String title,
                    @NonNull final String description,
                    @NonNull final Date startDate,
                    @NonNull final Date dueDate,
                    @NonNull final Percent progressPercent,
                    @NonNull final TaskState state,
                    @NonNull final TimeInterval estimatedTime) {

        Assertion.nonNull(id, title, description, startDate, dueDate, progressPercent, state, estimatedTime);

        mId = id;
        mTitle = title;
        mDescription = description;
        mStartDate = startDate;
        mDueDate = dueDate;
        mProgressPercent = progressPercent;
        mState = state;
        mEstimatedTime = estimatedTime;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public Percent getProgressPercent() {
        return mProgressPercent;
    }

    public TaskState getState() {
        return mState;
    }

    public TimeInterval getEstimatedTime() {
        return mEstimatedTime;
    }
}
