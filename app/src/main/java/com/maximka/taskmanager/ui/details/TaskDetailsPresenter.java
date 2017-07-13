package com.maximka.taskmanager.ui.details;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.ui.base.BasePresenter;
import com.maximka.taskmanager.ui.data.TaskViewData;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import rx.Subscription;

final class TaskDetailsPresenter extends BasePresenter<TaskDetailsView> {
    @NonNull private final DataManager mDataManager = new DataManager();
    @NonNull private final Navigator mNavigator;
    @NonNull private final String mTaskId;

    TaskDetailsPresenter(@NonNull final TaskDetailsView view,
                         @NonNull final Navigator navigator,
                         @NonNull final String taskId) {
        super(view);
        Assertion.nonNull(navigator, taskId);

        mNavigator = navigator;
        mTaskId = taskId;
    }

    @Override
    public void init() {
        addSubscription(subscribeOnCachedTask());
    }

    private Optional<Subscription> subscribeOnCachedTask() {
        return mDataManager
                .getCachedTaskDataObservable(mTaskId)
                .map(observable ->
                        observable.map(TaskViewData::from)
                                  .subscribe(
                                          viewData ->
                                                  runWithView(v -> v.updateTaskDataViews(viewData)),
                                          throwable ->
                                                  runWithView(TaskDetailsView::showErrorMessage)
                                )
                )
                .executeIfAbsent(() -> {
                        runWithView(TaskDetailsView::showErrorMessage);
                        mNavigator.navigateBack();
                });
    }

    void updateTaskProgress(@NonNull final Percent progress) {
        Assertion.nonNull(progress);

        mDataManager.getCachedTaskData(mTaskId)
                    .ifPresent(taskData -> {
                            final TaskState state =
                                    progress.isAtMax() ? TaskState.DONE : TaskState.IN_PROGRESS;

                            mDataManager.createOrUpdateTask(TaskData.newBuilder(taskData)
                                                                    .withState(state)
                                                                    .withProgressPercent(progress)
                                                                    .build());
                    });
    }

    void onEditButtonClicked() {
        mNavigator.navigateToEditScreen(mTaskId);
    }
}
