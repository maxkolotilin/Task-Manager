package com.maximka.taskmanager.ui.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.formatters.DateFormatter;
import com.maximka.taskmanager.formatters.TimeIntervalFormatter;
import com.maximka.taskmanager.ui.activity.FloatingActionButtonOwner;
import com.maximka.taskmanager.ui.navigation.DialogManager;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;
import com.maximka.taskmanager.utils.KeyboardUtils;

import java.util.Date;

public final class CreateTaskFragment extends Fragment implements CreateTaskView {
    private TextInputEditText mTitleEditView;
    private TextInputEditText mDescriptionEditView;
    private TextInputEditText mDueDateEditView;
    private TextInputEditText mEstimatedTimeEditView;
    private CreateFragmentPresenter mPresenter;
    private DialogManager mDialogManager;

    public static CreateTaskFragment newInstance() {
        return new CreateTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_task_fragment, container, false);
        mTitleEditView = (TextInputEditText) rootView.findViewById(R.id.input_title);
        mDescriptionEditView = (TextInputEditText) rootView.findViewById(R.id.input_description);
        mDueDateEditView = (TextInputEditText) rootView.findViewById(R.id.input_due_date);
        mEstimatedTimeEditView = (TextInputEditText) rootView.findViewById(R.id.input_estimated_time);

        Assertion.nonNull(mTitleEditView, mDescriptionEditView, mDueDateEditView, mEstimatedTimeEditView);

        mPresenter = new CreateFragmentPresenter(this, new Navigator(getFragmentManager()));
        initDialogManager();

        ((FloatingActionButtonOwner) getActivity())
                .setUpFloatingButton(R.drawable.ic_fab_save,
                        v -> mPresenter.createNewTask(getCurrentInputValues()));

        return rootView;
    }

    private void initDialogManager() {
        mDialogManager = new DialogManager(getFragmentManager());
        mDialogManager.attachDueDateCallback(this::updateDueDate);
        mDialogManager.attachEstimatedTimeCallback(this::updateEstimatedTime);

        mDueDateEditView.setOnClickListener(v ->
                mDialogManager.showDatePickerDialog(this::updateDueDate)
        );
        mEstimatedTimeEditView.setOnClickListener(v ->
                mDialogManager.showEstimatedTimePickerDialog(this::updateEstimatedTime)
        );
    }

    private void updateDueDate(@NonNull final Date dueDate) {
        Assertion.nonNull(dueDate);
        mDueDateEditView.setText(DateFormatter.format(dueDate));
    }

    private void updateEstimatedTime(@NonNull final TimeInterval estimatedTime) {
        Assertion.nonNull(estimatedTime);
        mEstimatedTimeEditView.setText(TimeIntervalFormatter.format(estimatedTime, getActivity()));
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(mTitleEditView, R.string.create_task_error_message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(mTitleEditView);
    }

    @NonNull
    private CreateTaskInputValues getCurrentInputValues() {
        return new CreateTaskInputValues(mTitleEditView.getText().toString(),
                                         mDescriptionEditView.getText().toString(),
                                         DateFormatter.parse(mDueDateEditView.getText().toString()),
                                         TimeIntervalFormatter.parse(mEstimatedTimeEditView.getText().toString(),
                                                                     getActivity()));
    }
}
