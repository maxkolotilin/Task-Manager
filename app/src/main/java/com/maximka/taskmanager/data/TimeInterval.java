package com.maximka.taskmanager.data;

import java.util.concurrent.TimeUnit;

public final class TimeInterval {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;

    private final long mTotalSeconds;

    public TimeInterval(final long totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("totalSeconds must be non-negative");
        }

        mTotalSeconds = totalSeconds;
    }

    public long getHoursComponent() {
        return TimeUnit.SECONDS.toHours(mTotalSeconds);
    }

    public long getMinutesComponent() {
        return TimeUnit.SECONDS.toMinutes(mTotalSeconds) % MINUTES_IN_HOUR;
    }

    public long getSecondsComponent() {
        return mTotalSeconds % SECONDS_IN_MINUTE;
    }

    public long getTotalSeconds() {
        return mTotalSeconds;
    }
}
