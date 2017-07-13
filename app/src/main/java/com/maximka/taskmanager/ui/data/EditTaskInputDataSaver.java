package com.maximka.taskmanager.ui.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.Assertion;

import java.io.Serializable;
import java.util.Date;

public final class EditTaskInputDataSaver implements Serializable {
    private static final String SAVED_INPUT_KEY = "EditTaskInputDataSaverKey";

    @Nullable private final Date mDueDate;
    @Nullable private final TimeInterval mEstimatedTime;
    // Title and description are saved by Android framework.

    public static EditTaskInputDataSaver from(@Nullable final Bundle savedState) {
        return Optional.ofNullable(savedState)
                       .map(bundle -> (EditTaskInputDataSaver) bundle.getSerializable(SAVED_INPUT_KEY))
                       .orElse(new EditTaskInputDataSaver(null, null));
    }

    private EditTaskInputDataSaver(@Nullable final Date dueDate, @Nullable final TimeInterval estimatedTime) {
        mDueDate = dueDate;
        mEstimatedTime = estimatedTime;
    }

    public static Builder newBuilder(@NonNull final EditTaskInputDataSaver copy) {
        Assertion.nonNull(copy);

        return new Builder().withDueDate(copy.getDueDate())
                            .withEstimatedTime(copy.getEstimatedTime());
    }

    public void save(@NonNull final Bundle savedState) {
        Assertion.nonNull(savedState);

        savedState.putSerializable(SAVED_INPUT_KEY, this);
    }

    @Nullable
    public Date getDueDate() {
        return mDueDate;
    }

    @Nullable
    public TimeInterval getEstimatedTime() {
        return mEstimatedTime;
    }


    public static final class Builder {
        @Nullable private Date mDueDate;
        @Nullable private TimeInterval mEstimatedTime;

        private Builder() {}

        @NonNull
        public Builder withDueDate(@Nullable final Date date) {
            mDueDate = date;
            return this;
        }

        @NonNull
        public Builder withEstimatedTime(@Nullable final TimeInterval estimatedTime) {
            mEstimatedTime = estimatedTime;
            return this;
        }

        @NonNull
        public EditTaskInputDataSaver build() {
            return new EditTaskInputDataSaver(mDueDate, mEstimatedTime);
        }
    }
}
