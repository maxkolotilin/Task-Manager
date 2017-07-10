package com.maximka.taskmanager.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.utils.Assertion;

import java.util.EnumMap;
import java.util.Map;

public class TaskStateFormatter {
    @NonNull private static final
    Map<TaskState, Integer> sStatusStringsMap =
                                new EnumMap<TaskState, Integer>(TaskState.class) {{
                                    put(TaskState.NEW, R.string.task_state_new);
                                    put(TaskState.IN_PROGRESS, R.string.task_state_in_progress);
                                    put(TaskState.DONE, R.string.task_state_done);
                                }};

    public static String format(@NonNull final TaskState state, @NonNull final Context context) {
        Assertion.nonNull(state, context);

        final Integer stringResId = sStatusStringsMap.get(state);
        if (stringResId == null) {
            throw new IllegalArgumentException("No string for TaskState.");
        } else {
            return context.getResources().getString(stringResId);
        }
    }

    private TaskStateFormatter() {
    }
}
