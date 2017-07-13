package com.maximka.taskmanager.formatters;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.utils.Assertion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    @NonNull private final static SimpleDateFormat sFormatter =
                                new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.US);

    @NonNull
    public static String format(@NonNull final Date date) {
        Assertion.nonNull(date);
        return sFormatter.format(date);
    }

    private DateFormatter() {
    }
}
