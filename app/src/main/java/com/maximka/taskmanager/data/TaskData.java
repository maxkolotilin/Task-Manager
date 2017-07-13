package com.maximka.taskmanager.data;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;
import java.util.UUID;

public final class TaskData {
    @NonNull private final String mId;
    @NonNull private final String mTitle;
    @NonNull private final String mDescription;
    @NonNull private final Date mStartDate;
    @NonNull private final Date mDueDate;
    @NonNull private final Percent mProgressPercent;
    @NonNull private final TaskState mState;
    @NonNull private final TimeInterval mEstimatedTime;

    private TaskData(@NonNull final String id,
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(@NonNull final TaskData copy) {
        Assertion.nonNull(copy);

        return newBuilder()
                .withId(copy.getId())
                .withTitle(copy.getTitle())
                .withDescription(copy.getDescription())
                .withStartDate(copy.getStartDate())
                .withDueDate(copy.getDueDate())
                .withState(copy.getState())
                .withProgressPercent(copy.getProgressPercent())
                .withEstimatedTime(copy.getEstimatedTime());
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


    public static final class Builder {
        private String mId = UUID.randomUUID().toString();
        private Percent mProgressPercent = Percent.zero();
        private TaskState mState = TaskState.NEW;
        private Date mStartDate = new Date();
        private String mTitle;
        private String mDescription;
        private Date mDueDate;
        private TimeInterval mEstimatedTime;

        private Builder() {}

        @NonNull
        public Builder withId(@NonNull final String id) {
            mId = id;
            return this;
        }

        @NonNull
        public Builder withTitle(@NonNull final String title) {
            mTitle = title;
            return this;
        }

        @NonNull
        public Builder withDescription(@NonNull final String description) {
            mDescription = description;
            return this;
        }

        @NonNull
        public Builder withStartDate(@NonNull final Date startDate) {
            mStartDate = startDate;
            return this;
        }

        @NonNull
        public Builder withDueDate(@NonNull final Date dueDate) {
            mDueDate = dueDate;
            return this;
        }

        @NonNull
        public Builder withProgressPercent(@NonNull final Percent progressPercent) {
            mProgressPercent = progressPercent;
            return this;
        }

        @NonNull
        public Builder withState(@NonNull final TaskState state) {
            mState = state;
            return this;
        }

        @NonNull
        public Builder withEstimatedTime(@NonNull final TimeInterval estimatedTime) {
            mEstimatedTime = estimatedTime;
            return this;
        }

        @NonNull
        public TaskData build() {
            return new TaskData(mId,
                                mTitle,
                                mDescription,
                                mStartDate,
                                mDueDate,
                                mProgressPercent,
                                mState,
                                mEstimatedTime);
        }
    }
}
