package com.maximka.taskmanager.data;


import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;

public final class Percent {
    private final int mPercent;

    public static Percent zero() {
        return new Percent(0);
    }

    public Percent(@IntRange(from=0, to=100) final int percent) {
        if (percent < 0 || percent > 100)
        {
            throw new IllegalArgumentException("Progress percent value must be an integer in range [0, 100]");
        }

        this.mPercent = percent;
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
