package com.maximka.taskmanager.ui.list;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.ui.list.recycler.TaskDataSummary;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import rx.Observable;
import rx.Subscription;

final class TaskListPresenter {
    @NonNull private final TaskListView mView;
    @NonNull private final DataManager dataManager;
    @NonNull private final Navigator mNavigator;
    @NonNull private Optional<Subscription> mActiveSubscription = Optional.empty();

    TaskListPresenter(@NonNull final TaskListView view, @NonNull final Navigator navigator) {
        Assertion.nonNull(view, navigator);
        mView = view;
        mNavigator = navigator;
        dataManager = new DataManager();
    }

    public void init() {
        mActiveSubscription =
                Optional.of(dataManager.getAllTasks()
                                       .flatMap(taskList -> Observable.from(taskList)
                                                                      .map(TaskDataSummary::from)
                                                                      .toList())
                                       .subscribe(taskDataSummaries -> {
                                                      if (taskDataSummaries.isEmpty()) {
                                                          mView.showEmptyTaskListView();
                                                      } else {
                                                          mView.showTaskListView();
                                                          mView.updateTaskListData(taskDataSummaries);
                                                      }
                                                  },
                                                  throwable -> mView.showErrorMessage())
                );
    }

    public void onViewDestroyed() {
        mActiveSubscription.ifPresent(Subscription::unsubscribe);
        dataManager.close();
    }

    public void goToCreateScreen() {
        mNavigator.navigateToCreateScreen();
    }

    public void goToDetailsScreen(@NonNull final TaskDataSummary taskDataSummary) {
        Assertion.nonNull(taskDataSummary);

        mNavigator.navigateToDetailsScreen(taskDataSummary.getId());
    }
}
