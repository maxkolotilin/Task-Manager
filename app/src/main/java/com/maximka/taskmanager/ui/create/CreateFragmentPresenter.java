package com.maximka.taskmanager.ui.create;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;

final class CreateFragmentPresenter {
    @NonNull private final CreateTaskView mView;
    @NonNull private final Navigator mNavigator;
    @NonNull private final DataManager mDataManager;

    public CreateFragmentPresenter(@NonNull final CreateTaskView view, @NonNull final Navigator navigator) {
        Assertion.nonNull(view, navigator);

        mView = view;
        mNavigator = navigator;
        mDataManager = new DataManager();
    }

    public void createNewTask(@NonNull final CreateTaskInputValues inputValues) {
        Assertion.nonNull(inputValues);

        if (inputValues.isValid()) {
            final TaskData newTask = new TaskData(Objects.requireNonNull(inputValues.getTitle()),
                                                  Objects.requireNonNull(inputValues.getDescription()),
                                                  new Date(),
                                                  Objects.requireNonNull(inputValues.getDueDate()),
                                                  Percent.zero(),
                                                  TaskState.NEW,
                                                  Objects.requireNonNull(inputValues.getEstimatedTime()));

            mDataManager.createOrUpdateTask(newTask);

            mView.hideKeyboard();
            mNavigator.navigateBack();
        } else {
            mView.showErrorMessage();
        }
    }

    public void onViewDestroyed() {
        mDataManager.close();
    }
}
