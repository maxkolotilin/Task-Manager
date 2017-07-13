package com.maximka.taskmanager.ui.screens.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.formatters.DateFormatter;
import com.maximka.taskmanager.formatters.TimeIntervalFormatter;
import com.maximka.taskmanager.ui.base.BaseFragment;
import com.maximka.taskmanager.ui.data.EditTaskInputDataSaver;
import com.maximka.taskmanager.ui.data.EditTaskInputValues;
import com.maximka.taskmanager.ui.data.TaskViewData;
import com.maximka.taskmanager.dialogs.DialogManager;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;
import com.maximka.taskmanager.utils.KeyboardUtils;

import java.util.Date;

public final class EditTaskFragment extends BaseFragment<EditTaskPresenter> implements EditTaskView {
    private static final String TASK_ID_ARG = "taskId";

    private TextInputEditText mTitleEditView;
    private TextInputEditText mDescriptionEditView;
    private TextInputEditText mDueDateEditView;
    private TextInputEditText mEstimatedTimeEditView;
    private DialogManager mDialogManager;
    private EditTaskInputDataSaver mInputDataSaver;

    public static EditTaskFragment newCreateTaskInstance() {
        return new EditTaskFragment();
    }

    public static EditTaskFragment newEditTaskInstance(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        final Bundle arguments = new Bundle();
        arguments.putString(TASK_ID_ARG, taskId);

        final EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.task_edit_fragment;
    }

    @Override
    protected int getFloatingButtonIcon() {
        return R.drawable.ic_fab_save;
    }

    @Override
    protected View.OnClickListener getOnFloatingButtonClickListener() {
        return v -> getPresenter().createNewTask(getCurrentInputValues(), getStringArgument(TASK_ID_ARG));
    }

    @Override
    protected boolean showToolbarBackArrow() {
        return true;
    }

    @NonNull
    @Override
    protected EditTaskPresenter createPresenter() {
        return new EditTaskPresenter(this, new Navigator(getFragmentManager()));
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputDataSaver = EditTaskInputDataSaver.from(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mTitleEditView = (TextInputEditText) rootView.findViewById(R.id.input_title);
        mDescriptionEditView = (TextInputEditText) rootView.findViewById(R.id.input_description);
        mDueDateEditView = (TextInputEditText) rootView.findViewById(R.id.input_due_date);
        mEstimatedTimeEditView = (TextInputEditText) rootView.findViewById(R.id.input_estimated_time);

        Assertion.nonNull(mTitleEditView, mDescriptionEditView, mDueDateEditView, mEstimatedTimeEditView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDialogManager();

        if (savedInstanceState == null) {
            getStringArgument(TASK_ID_ARG).ifPresent(getPresenter()::loadExistedTaskData);
        }
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
        mInputDataSaver = EditTaskInputDataSaver.newBuilder(mInputDataSaver)
                                                .withDueDate(dueDate)
                                                .build();
    }

    private void updateEstimatedTime(@NonNull final TimeInterval estimatedTime) {
        Assertion.nonNull(estimatedTime);

        mEstimatedTimeEditView.setText(TimeIntervalFormatter.format(estimatedTime, getActivity()));
        mInputDataSaver = EditTaskInputDataSaver.newBuilder(mInputDataSaver)
                                                .withEstimatedTime(estimatedTime)
                                                .build();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        mInputDataSaver.save(outState);
    }

    @Override
    public void showInvalidInputMessage() {
        showSnack(R.string.edit_task_invalid_input_message);
    }

    @Override
    public void showNotFoundErrorMessage() {
        showToast(R.string.edit_task_not_found_message);
    }

    @Override
    public void setExistedTaskData(@NonNull final TaskViewData taskData) {
        Assertion.nonNull(taskData);

        mTitleEditView.setText(taskData.getTitle());
        mDescriptionEditView.setText(taskData.getDescription());
        updateDueDate(taskData.getDueDate());
        updateEstimatedTime(taskData.getEstimatedTime());
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(mTitleEditView);
    }

    @NonNull
    private EditTaskInputValues getCurrentInputValues() {
        return new EditTaskInputValues(mTitleEditView.getText().toString(),
                                       mDescriptionEditView.getText().toString(),
                                       mInputDataSaver.getDueDate(),
                                       mInputDataSaver.getEstimatedTime());
    }
}
