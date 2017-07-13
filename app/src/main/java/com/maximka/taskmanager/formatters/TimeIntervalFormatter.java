package com.maximka.taskmanager.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.Assertion;

public class TimeIntervalFormatter {

    @NonNull
    public static String format(@NonNull final TimeInterval timeInterval, @NonNull final Context context) {
        Assertion.nonNull(timeInterval, context);

        return context.getResources()
                      .getString(R.string.estimated_time_format_string,
                                 timeInterval.getHoursComponent(),
                                 timeInterval.getMinutesComponent());
    }

    private TimeIntervalFormatter() {
    }
}
