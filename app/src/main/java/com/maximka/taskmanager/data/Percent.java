package com.maximka.taskmanager.data;


import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;

public final class Percent {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;

    private final int mPercent;

    public static Percent zero() {
        return new Percent(MIN_VALUE);
    }

    public Percent(@IntRange(from=MIN_VALUE, to=MAX_VALUE) final int percent) {
        if (percent < MIN_VALUE || percent > MAX_VALUE)
        {
            throw new IllegalArgumentException("Progress percent value must be an integer in range [0, 100]");
        }

        this.mPercent = percent;
    }

    public boolean isAtMax() {
        return mPercent == MAX_VALUE;
    }

    public int asInt() {
        return mPercent;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (obj != null && obj instanceof Percent) {
            final Percent other = (Percent) obj;

            return mPercent == other.mPercent;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPercent);
    }
}
