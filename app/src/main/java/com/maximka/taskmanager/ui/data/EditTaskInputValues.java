package com.maximka.taskmanager.ui.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.StringUtils;

import java.util.Date;

@SuppressWarnings("ConstantConditions")
public final class EditTaskInputValues {
    @Nullable private final String mTitle;
    @Nullable private final String mDescription;
    @Nullable private final Date mDueDate;
    @Nullable private final TimeInterval mEstimatedTime;

    public EditTaskInputValues(@Nullable final String title,
                               @Nullable final String description,
                               @Nullable final Date dueDate,
                               @Nullable final TimeInterval estimatedTime) {
        mTitle = title;
        mDescription = description;
        mDueDate = dueDate;
        mEstimatedTime = estimatedTime;
    }

    @NonNull
    public String getTitle() {
        return Objects.requireNonNull(mTitle);
    }

    @NonNull
    public String getDescription() {
        return Objects.requireNonNull(mDescription);
    }

    @NonNull
    public Date getDueDate() {
        return Objects.requireNonNull(mDueDate);
    }

    @NonNull
    public TimeInterval getEstimatedTime() {
        return Objects.requireNonNull(mEstimatedTime);
    }

    public boolean isValid() {
        return !StringUtils.isEmpty(mTitle)
                && !StringUtils.isEmpty(mDescription)
                && mDueDate != null
                && mEstimatedTime != null;
    }
}
