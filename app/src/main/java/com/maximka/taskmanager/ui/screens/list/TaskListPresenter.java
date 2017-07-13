package com.maximka.taskmanager.ui.screens.list;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.preferences.Preferences;
import com.maximka.taskmanager.ui.base.BasePresenter;
import com.maximka.taskmanager.ui.screens.list.menu.StateFilter;
import com.maximka.taskmanager.ui.screens.list.menu.SortField;
import com.maximka.taskmanager.ui.data.TaskSummaryViewData;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import rx.Observable;
import rx.Subscription;

final class TaskListPresenter extends BasePresenter<TaskListView> {
    @NonNull private final DataManager mDataManager = new DataManager();
    @NonNull private final Navigator mNavigator;
    @NonNull private final Preferences mPreferences;
    @NonNull private Optional<Subscription> mTaskListSubscription = Optional.empty();

    TaskListPresenter(@NonNull final TaskListView view,
                      @NonNull final Navigator navigator,
                      @NonNull final Preferences preferences) {
        super(view);
        Assertion.nonNull(navigator, preferences);

        mNavigator = navigator;
        mPreferences = preferences;
    }

    @Override
    public void init() {
        subscribeToTaskList();
    }

    @Override
    protected void onViewDestroyed() {
        mTaskListSubscription.ifPresent(Subscription::unsubscribe);
        super.onViewDestroyed();
    }

    private void subscribeToTaskList() {
        mTaskListSubscription =
                Optional.of(
                        mDataManager.getAllTasksObservable()
                                    .flatMap(taskList ->
                                            Observable.from(taskList)
                                                      .filter(mPreferences.getStateFilter()
                                                                          .getFilter())
                                                      .sorted(mPreferences.getSortField()
                                                                          .getComparator())
                                                      .map(TaskSummaryViewData::from)
                                                      .toList()
                                    )
                                    .subscribe(taskDataSummaries -> {
                                                   if (taskDataSummaries.isEmpty()) {
                                                       runWithView(TaskListView::showEmptyTaskListView);
                                                   } else {
                                                       runWithView(view -> {
                                                           view.showTaskListView();
                                                           view.updateTaskListData(taskDataSummaries);
                                                       });
                                                   }
                                               },
                                               throwable -> {
                                                   throwable.printStackTrace();
                                                   runWithView(TaskListView::showErrorMessage);
                                               }
                                    )
                );
    }

    void goToCreateScreen() {
        mNavigator.navigateToCreateScreen();
    }

    void goToDetailsScreen(@NonNull final TaskSummaryViewData taskDataSummary) {
        Assertion.nonNull(taskDataSummary);

        mNavigator.navigateToDetailsScreen(taskDataSummary.getId());
    }

    void setStateFilter(@NonNull final StateFilter stateFilter) {
        Assertion.nonNull(stateFilter);

        mPreferences.setStateFilter(stateFilter);
        resubscribe();
    }

    void setSortField(@NonNull final SortField sortField) {
        Assertion.nonNull(sortField);

        mPreferences.setSortField(sortField);
        resubscribe();
    }

    private void resubscribe() {
        mTaskListSubscription.ifPresent(Subscription::unsubscribe);
        subscribeToTaskList();
    }
}
