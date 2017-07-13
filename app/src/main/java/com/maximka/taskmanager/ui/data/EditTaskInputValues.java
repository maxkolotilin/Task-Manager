package com.maximka.taskmanager.ui.data;

import android.support.annotation.Nullable;

import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.StringUtils;

import java.util.Date;

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

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public Date getDueDate() {
        return mDueDate;
    }

    @Nullable
    public TimeInterval getEstimatedTime() {
        return mEstimatedTime;
    }

    public boolean isValid() {
        return !StringUtils.isEmpty(mTitle)
                && !StringUtils.isEmpty(mDescription)
                && mDueDate != null
                && mEstimatedTime != null;
    }
}
