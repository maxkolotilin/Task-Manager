package com.maximka.taskmanager.ui.screens.edit;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.ui.base.BasePresenter;
import com.maximka.taskmanager.ui.data.EditTaskInputValues;
import com.maximka.taskmanager.ui.data.TaskViewData;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

final class EditTaskPresenter extends BasePresenter<EditTaskView> {
    @NonNull private final DataManager mDataManager = new DataManager();
    @NonNull private final Navigator mNavigator;

    EditTaskPresenter(@NonNull final EditTaskView view, @NonNull final Navigator navigator) {
        super(view);
        Assertion.nonNull(navigator);

        mNavigator = navigator;
    }

    @Override
    public void init() {}

    void createNewTask(@NonNull final EditTaskInputValues inputValues,
                       @NonNull final Optional<String> editedTaskId) {
        Assertion.nonNull(inputValues, editedTaskId);

        if (inputValues.isValid()) {

            editedTaskId.flatMap(mDataManager::getCachedTaskData)
                        .map(TaskData::newBuilder)
                        .or(() -> Optional.of(TaskData.newBuilder()))
                        .map(builder ->
                                 builder.withTitle(inputValues.getTitle())
                                        .withDescription(inputValues.getDescription())
                                        .withDueDate(inputValues.getDueDate())
                                        .withEstimatedTime(inputValues.getEstimatedTime())
                                        .build())
                        .ifPresent(mDataManager::createOrUpdateTask);

            runWithView(EditTaskView::hideKeyboard);
            mNavigator.navigateBack();

        } else {
            runWithView(EditTaskView::showInvalidInputMessage);
        }
    }

    void loadExistedTaskData(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        mDataManager.getCachedTaskData(taskId)
                    .map(TaskViewData::from)
                    .executeIfAbsent(() -> {
                            runWithView(EditTaskView::showNotFoundErrorMessage);
                            mNavigator.navigateBack();
                    })
                    .ifPresent(viewData ->
                            runWithView(view -> view.setExistedTaskData(viewData))
                    );
    }
}
