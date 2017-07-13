package com.maximka.taskmanager.data.realm;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskDataRealm extends RealmObject {
    public static final String ID = "mId";
    public static final String START_DATE = "mStartDate";

    @PrimaryKey
    @NonNull
    private String mId = UUID.randomUUID().toString();
    private String mTitle;
    private String mDescription;
    private Date mStartDate;
    private Date mDueDate;
    private int mProgressPercent;
    private int mState;
    private long mEstimatedTime;      // in seconds

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull final String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(final Date startDate) {
        mStartDate = startDate;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(final Date dueDate) {
        mDueDate = dueDate;
    }

    public Percent getProgressPercent() {
        return new Percent(mProgressPercent);
    }

    public void setProgressPercent(@NonNull final Percent progressPercent) {
        Assertion.nonNull(progressPercent);
        mProgressPercent = progressPercent.asInt();
    }

    public TaskState getState() {
        return TaskState.fromInt(mState);
    }

    public void setState(@NonNull final TaskState state) {
        Assertion.nonNull(state);
        mState = state.toInt();
    }

    public TimeInterval getEstimatedTime() {
        return new TimeInterval(mEstimatedTime);
    }

    public void setEstimatedTime(@NonNull final TimeInterval estimatedTime) {
        Assertion.nonNull(estimatedTime);
        mEstimatedTime = estimatedTime.getTotalSeconds();
    }
}
