package com.maximka.taskmanager.dialogs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.dialogs.DatePickerDialogFragment;
import com.maximka.taskmanager.dialogs.DialogWithAttachableConsumer;
import com.maximka.taskmanager.dialogs.TimeIntervalPickerDialogFragment;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

public final class DialogManager {
    @NonNull private final static String DUE_DATE_PICKER_TAG = "dueDatePickerTag";
    @NonNull private final static String ESTIMATED_TIME_PICKER_TAG = "estimateTimePickerTag";

    @NonNull private final FragmentManager mFragmentManager;

    public DialogManager(@NonNull final FragmentManager fragmentManager) {
        Assertion.nonNull(fragmentManager);

        mFragmentManager = fragmentManager;
    }

    public void attachDueDateCallback(@Nullable final Consumer<Date> resultConsumer) {
        attachCallback(resultConsumer, DUE_DATE_PICKER_TAG);
    }


    public void attachEstimatedTimeCallback(@Nullable final Consumer<TimeInterval> resultConsumer) {
        attachCallback(resultConsumer, ESTIMATED_TIME_PICKER_TAG);
    }

    @SuppressWarnings("unchecked")
    private <T> void attachCallback(@Nullable final Consumer<T> resultConsumer,
                                    @NonNull final String tag) {
        Assertion.nonNull(tag);

        Optional.ofNullable(mFragmentManager.findFragmentByTag(tag))
                .map(DialogWithAttachableConsumer.class::cast)
                .ifPresent(dialog -> dialog.attachResultConsumer(resultConsumer));
    }

    public void showDatePickerDialog(@Nullable final Consumer<Date> resultConsumer) {
        DatePickerDialogFragment.newInstance(resultConsumer)
                                .show(mFragmentManager, DUE_DATE_PICKER_TAG);
    }

    public void showEstimatedTimePickerDialog(@Nullable final Consumer<TimeInterval> resultConsumer) {
        TimeIntervalPickerDialogFragment.newInstance(resultConsumer)
                                        .show(mFragmentManager, ESTIMATED_TIME_PICKER_TAG);
    }
}
