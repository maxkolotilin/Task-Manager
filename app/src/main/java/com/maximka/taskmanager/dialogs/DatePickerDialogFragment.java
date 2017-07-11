package com.maximka.taskmanager.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, DialogWithAttachableConsumer<Date> {

    @NonNull private Optional<Consumer<Date>> mResultConsumer = Optional.empty();

    @NonNull
    public static DatePickerDialogFragment newInstance(@Nullable final Consumer<Date> consumer) {
        final DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
        dialogFragment.attachResultConsumer(consumer);

        return dialogFragment;
    }

    @Override
    public void attachResultConsumer(@Nullable final Consumer<Date> consumer) {
        mResultConsumer = Optional.ofNullable(consumer);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, currentYear, currentMonth, currentDay);
    }

    public void onDateSet(@NonNull final DatePicker view, final int year, final int month, final int day) {
        final Date inputDate = new GregorianCalendar(year, month, day).getTime();
        mResultConsumer.ifPresent(dateConsumer -> dateConsumer.accept(inputDate));
    }
}
