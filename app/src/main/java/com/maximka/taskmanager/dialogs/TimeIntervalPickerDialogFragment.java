package com.maximka.taskmanager.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.maximka.taskmanager.data.TimeInterval;

public final class TimeIntervalPickerDialogFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener, DialogWithAttachableConsumer<TimeInterval> {

    @NonNull private Optional<Consumer<TimeInterval>> mResultConsumer = Optional.empty();

    public static TimeIntervalPickerDialogFragment newInstance(@Nullable final Consumer<TimeInterval> consumer) {
        final TimeIntervalPickerDialogFragment dialogFragment = new TimeIntervalPickerDialogFragment();
        dialogFragment.attachResultConsumer(consumer);

        return dialogFragment;
    }

    @Override
    public void attachResultConsumer(@Nullable final Consumer<TimeInterval> consumer) {
        mResultConsumer = Optional.ofNullable(consumer);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, 0, 0, true);
    }

    public void onTimeSet(@NonNull final TimePicker view, final int hours, final int minutes) {
        mResultConsumer.ifPresent(timeIntervalConsumer ->
                                      timeIntervalConsumer.accept(new TimeInterval(hours, minutes)));
    }
}
