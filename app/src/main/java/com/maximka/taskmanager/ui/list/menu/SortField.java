package com.maximka.taskmanager.ui.list.menu;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.utils.Assertion;
import com.maximka.taskmanager.utils.CollectionsUtils;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import rx.functions.Func2;

public enum SortField {
    START_DATE,
    DUE_DATE,
    PROGRESS;

    private static final SparseArray<SortField> sIdToSortField =
            new SparseArray<SortField>() {{
                put(R.id.sort_start_date, SortField.START_DATE);
                put(R.id.sort_due_date, SortField.DUE_DATE);
                put(R.id.sort_progress, SortField.PROGRESS);
    }};

    private static final Map<SortField, Func2<TaskData, TaskData, Integer>> sSortFieldToComparator =
            new EnumMap<SortField, Func2<TaskData, TaskData, Integer>>(SortField.class) {{
                put(SortField.START_DATE,
                        (first, second) -> compareDatesDesc(first.getStartDate(), second.getStartDate()));

                put(SortField.DUE_DATE,
                        (first, second) -> compareDatesAsc(first.getDueDate(), second.getDueDate()));

                put(SortField.PROGRESS,
                        (first, second) -> first.getProgressPercent().asInt() - second.getProgressPercent().asInt());
    }};

    private static int compareDatesAsc(@NonNull final Date first, @NonNull final Date second) {
        Assertion.nonNull(first, second);
        return (int) Math.signum(first.getTime() - second.getTime());
    }

    private static int compareDatesDesc(@NonNull final Date first, @NonNull final Date second) {
        Assertion.nonNull(first, second);
        return compareDatesAsc(second, first);
    }

    @NonNull
    public static Optional<SortField> getSortField(@IdRes final int id) {
        return Optional.ofNullable(sIdToSortField.get(id));
    }

    @IdRes
    public int getMenuItemId() {
        return CollectionsUtils.getKeyForValue(sIdToSortField, this);
    }

    @NonNull
    public Func2<TaskData, TaskData, Integer> getComparator() {
        return sSortFieldToComparator.get(this);
    }
}
