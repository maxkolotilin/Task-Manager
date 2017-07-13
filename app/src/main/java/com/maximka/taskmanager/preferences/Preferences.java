package com.maximka.taskmanager.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.list.menu.SortField;
import com.maximka.taskmanager.ui.list.menu.StateFilter;
import com.maximka.taskmanager.utils.Assertion;

public final class Preferences {
    private static final String PREFERENCES_NAME = "taskManagerPrefs";
    private static final String STATE_FILTER_KEY = "stateFilterKey";
    private static final String SORT_FILED_KEY = "sortFieldKey";
    private static final int DEFAULT_STATE_FILTER = StateFilter.ALL.ordinal();
    private static final int DEFAULT_SORT_FIELD = SortField.START_DATE.ordinal();

    @NonNull private final SharedPreferences mSharedPreferences;

    public static Preferences get(@NonNull final Context context) {
        return new Preferences(context);
    }

    private Preferences(@NonNull final Context context) {
        Assertion.nonNull(context);

        mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setStateFilter(@NonNull final StateFilter filter) {
        setEnumValue(STATE_FILTER_KEY, filter);
    }

    public void setSortField(@NonNull final SortField sortField) {
        setEnumValue(SORT_FILED_KEY, sortField);
    }

    public StateFilter getStateFilter() {
        return StateFilter.values()[getEnumOrdinal(STATE_FILTER_KEY, DEFAULT_STATE_FILTER)];
    }

    public SortField getSortField() {
        return SortField.values()[getEnumOrdinal(SORT_FILED_KEY, DEFAULT_SORT_FIELD)];
    }

    private <T extends Enum<?>> void setEnumValue(@NonNull final String key, @NonNull final T value) {
        Assertion.nonNull(key, value);

        mSharedPreferences.edit()
                .putInt(key, value.ordinal())
                .apply();
    }

    private int getEnumOrdinal(@NonNull final String key, final int defaultOrdinal) {
        Assertion.nonNull(key);

        return mSharedPreferences.getInt(key, defaultOrdinal);
    }
}
