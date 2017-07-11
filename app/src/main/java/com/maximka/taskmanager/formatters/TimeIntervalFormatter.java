package com.maximka.taskmanager.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.Assertion;

public class TimeIntervalFormatter {
    private static final String TIME_COMPONENTS_SEPARATOR = " ";

    public static String format(@NonNull final TimeInterval timeInterval, @NonNull final Context context) {
        Assertion.nonNull(timeInterval, context);

        return context.getResources()
                      .getString(R.string.estimated_time_format_string,
                                 timeInterval.getHoursComponent(),
                                 timeInterval.getMinutesComponent());
    }

    public static TimeInterval parse(@NonNull final String formattedTimeInterval, @NonNull final Context context) {
        Assertion.nonNull(formattedTimeInterval, context);

        try {
            final String[] components = formattedTimeInterval.split(TIME_COMPONENTS_SEPARATOR);
            final String hours = components[0].substring(0, components[0].length() - 1);
            final String minutes = components[1].substring(0, components[1].length() - 1);

            return new TimeInterval(Long.valueOf(hours), Long.valueOf(minutes));
        } catch (Exception e) {
            return null;
        }
    }

    private TimeIntervalFormatter() {
    }
}
