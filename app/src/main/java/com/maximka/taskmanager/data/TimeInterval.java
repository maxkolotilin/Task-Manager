package com.maximka.taskmanager.data;

import com.maximka.taskmanager.utils.Assertion;

import java.util.concurrent.TimeUnit;

public final class TimeInterval {
    private static final int MINUTES_IN_HOUR = 60;

    private final long mTotalSeconds;

    public TimeInterval(final long totalSeconds) {
        Assertion.nonNegative(totalSeconds);

        mTotalSeconds = totalSeconds;
    }

    public TimeInterval(final long hours, final long minutes) {
        Assertion.nonNegative(hours);
        Assertion.nonNegative(minutes);

        mTotalSeconds = TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes);
    }

    public long getHoursComponent() {
        return TimeUnit.SECONDS.toHours(mTotalSeconds);
    }

    public long getMinutesComponent() {
        return TimeUnit.SECONDS.toMinutes(mTotalSeconds) % MINUTES_IN_HOUR;
    }

    public long getTotalSeconds() {
        return mTotalSeconds;
    }
}
