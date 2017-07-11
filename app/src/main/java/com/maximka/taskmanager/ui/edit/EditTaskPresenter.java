package com.maximka.taskmanager.ui.edit;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

final class EditTaskPresenter {
    @NonNull private final EditTaskView mView;
    @NonNull private final Navigator mNavigator;
    @NonNull private final DataManager mDataManager;

    public EditTaskPresenter(@NonNull final EditTaskView view, @NonNull final Navigator navigator) {
        Assertion.nonNull(view, navigator);

        mView = view;
        mNavigator = navigator;
        mDataManager = new DataManager();
    }

    @SuppressWarnings("ConstantConditions")
    public void createNewTask(@NonNull final EditTaskInputValues inputValues,
                              @NonNull final Optional<String> editedTaskId) {
        Assertion.nonNull(inputValues, editedTaskId);

        if (inputValues.isValid()) {
            final String title = Objects.requireNonNull(inputValues.getTitle());
            final String description = Objects.requireNonNull(inputValues.getDescription());
            final Date dueDate = Objects.requireNonNull(inputValues.getDueDate());
            final TimeInterval estimatedTime = Objects.requireNonNull(inputValues.getEstimatedTime());

            final TaskData newTask =
                    editedTaskId.map(mDataManager::getCachedTaskData)
                                .map(taskData ->
                                        new TaskData(taskData.getId(),
                                                     title,
                                                     description,
                                                     taskData.getStartDate(),
                                                     dueDate,
                                                     taskData.getProgressPercent(),
                                                     taskData.getState(),
                                                     estimatedTime))

                                .orElse(new TaskData(title,
                                                     description,
                                                     new Date(),
                                                     dueDate,
                                                     Percent.zero(),
                                                     TaskState.NEW,
                                                     estimatedTime));

            mDataManager.createOrUpdateTask(newTask);

            mView.hideKeyboard();
            mNavigator.navigateBack();
        } else {
            mView.showErrorMessage();
        }
    }

    public void loadExistedTaskData(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        mView.setExistedTaskData(mDataManager.getCachedTaskData(taskId));
    }

    public void onViewDestroyed() {
        mDataManager.close();
    }
}
