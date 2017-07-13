package com.maximka.taskmanager.ui.list.menu;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.utils.CollectionsUtils;

import java.util.EnumMap;
import java.util.Map;

import rx.functions.Func1;

public enum StateFilter {
    ALL,
    NEW,
    IN_PROGRESS,
    DONE;

    private static final SparseArray<StateFilter> sMenuIdToValue =
            new SparseArray<StateFilter>() {{
                put(R.id.filter_all, StateFilter.ALL);
                put(R.id.filter_new, StateFilter.NEW);
                put(R.id.filter_in_progress, StateFilter.IN_PROGRESS);
                put(R.id.filter_done, StateFilter.DONE);
    }};

    private static final Map<StateFilter, Func1<TaskData, Boolean>> sValueToFilter =
            new EnumMap<StateFilter, Func1<TaskData, Boolean>>(StateFilter.class) {{
                put(StateFilter.ALL, taskData -> true);
                put(StateFilter.NEW, taskData -> taskData.getState() == TaskState.NEW);
                put(StateFilter.IN_PROGRESS, taskData -> taskData.getState() == TaskState.IN_PROGRESS);
                put(StateFilter.DONE, taskData -> taskData.getState() == TaskState.DONE);
    }};

    @NonNull
    public static Optional<StateFilter> getStateFilter(@IdRes final int id) {
        return Optional.ofNullable(sMenuIdToValue.get(id));
    }

    @IdRes
    public int getMenuItemId() {
        return CollectionsUtils.getKeyForValue(sMenuIdToValue, this);
    }

    @NonNull
    public Func1<TaskData, Boolean> getFilter() {
        return sValueToFilter.get(this);
    }
}
