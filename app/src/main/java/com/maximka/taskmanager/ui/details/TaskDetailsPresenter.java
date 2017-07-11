package com.maximka.taskmanager.ui.details;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import rx.Subscription;

final class TaskDetailsPresenter {
    @NonNull private final TaskDetailsView mView;
    @NonNull private final Navigator mNavigator;
    @NonNull private final DataManager mDataManager;
    @NonNull private final String mTaskId;
    @NonNull private Optional<TaskData> mCurrentTaskData = Optional.empty();
    @NonNull private Optional<Subscription> mTaskSubscription = Optional.empty();

    public TaskDetailsPresenter(@NonNull final TaskDetailsView view,
                                @NonNull final Navigator navigator,
                                @NonNull final String taskId) {
        Assertion.nonNull(view, navigator, taskId);

        mView = view;
        mNavigator = navigator;
        mTaskId = taskId;
        mDataManager = new DataManager();
    }

    public void init() {
        mTaskSubscription =
                Optional.of(mDataManager.getTask(mTaskId)
                                        .subscribe(taskData -> {
                                                       mCurrentTaskData = Optional.of(taskData);
                                                       mView.updateTaskDataViews(taskData);
                                                   },
                                                   throwable -> mView.showErrorMessage())
                );
    }

    public void updateTaskProgress(@NonNull final Percent progress) {
        Assertion.nonNull(progress);

        mCurrentTaskData.ifPresent(taskData -> {
            final TaskState state = progress.isAtMax() ? TaskState.DONE : TaskState.IN_PROGRESS;
            mDataManager.createOrUpdateTask(new TaskData(taskData.getId(),
                                                         taskData.getTitle(),
                                                         taskData.getDescription(),
                                                         taskData.getStartDate(),
                                                         taskData.getDueDate(),
                                                         progress,
                                                         state,
                                                         taskData.getEstimatedTime()));
        });
    }

    public void onEditButtonClicked() {
        mNavigator.navigateToEditScreen(mTaskId);
    }

    public void onViewDestroyed() {
        mTaskSubscription.ifPresent(Subscription::unsubscribe);
        mDataManager.close();
    }
}
