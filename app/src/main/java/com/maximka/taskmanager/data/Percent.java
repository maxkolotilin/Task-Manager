package com.maximka.taskmanager.data;


import android.support.annotation.IntRange;

public final class Percent {
    private final int mPercent;

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
}
