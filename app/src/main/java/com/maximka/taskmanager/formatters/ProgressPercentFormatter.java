package com.maximka.taskmanager.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.utils.Assertion;

public class ProgressPercentFormatter {

    public static String format(@NonNull final Percent percent, @NonNull final Context context) {
        Assertion.nonNull(percent, context);

        return context.getResources().getString(R.string.task_progress_format_string, percent.asInt());
    }

    private ProgressPercentFormatter() {
    }
}
