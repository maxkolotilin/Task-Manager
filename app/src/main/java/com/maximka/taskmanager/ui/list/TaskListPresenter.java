package com.maximka.taskmanager.ui.list;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.ui.list.menu.FilterField;
import com.maximka.taskmanager.ui.list.menu.SortField;
import com.maximka.taskmanager.ui.list.recycler.TaskDataSummary;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

final class TaskListPresenter {
    private static final Map<FilterField, Func1<TaskData, Boolean>> sFilterMap =
            new EnumMap<FilterField, Func1<TaskData, Boolean>>(FilterField.class) {{
                put(FilterField.ALL, taskData -> true);
                put(FilterField.NEW, taskData -> taskData.getState() == TaskState.NEW);
                put(FilterField.IN_PROGRESS, taskData -> taskData.getState() == TaskState.IN_PROGRESS);
                put(FilterField.DONE, taskData -> taskData.getState() == TaskState.DONE);
            }};

    private static final Map<SortField, Func2<TaskData, TaskData, Integer>> sComparatorMap =
            new EnumMap<SortField, Func2<TaskData, TaskData, Integer>>(SortField.class) {{
                put(SortField.START_DATE,
                        (first, second) -> compareDatesDesc(first.getStartDate(), second.getStartDate()));

                put(SortField.DUE_DATE,
                        (first, second) -> compareDatesAsc(first.getDueDate(), second.getDueDate()));

                put(SortField.PROGRESS,
                        (first, second) -> first.getProgressPercent().asInt() - second.getProgressPercent().asInt());
            }};

    private static int compareDatesAsc(@NonNull final Date first, @NonNull final Date second) {
        Assertion.nonNull(first, second);
        return (int)(first.getTime() - second.getTime());
    }

    private static int compareDatesDesc(@NonNull final Date first, @NonNull final Date second) {
        Assertion.nonNull(first, second);
        return compareDatesAsc(second, first);
    }

    @NonNull private final TaskListView mView;
    @NonNull private final DataManager dataManager;
    @NonNull private final Navigator mNavigator;
    @NonNull private Optional<Subscription> mActiveSubscription = Optional.empty();
    @NonNull private Func1<TaskData, Boolean> mFilter = sFilterMap.get(FilterField.ALL);
    @NonNull private Func2<TaskData, TaskData, Integer> mComparator = sComparatorMap.get(SortField.START_DATE);

    TaskListPresenter(@NonNull final TaskListView view, @NonNull final Navigator navigator) {
        Assertion.nonNull(view, navigator);
        mView = view;
        mNavigator = navigator;
        dataManager = new DataManager();
    }

    public void init() {
        mView.showTaskListView();
        mActiveSubscription =
                Optional.of(dataManager.getAllTasksObservable()
                                       .flatMap(taskList -> Observable.from(taskList)
                                                                      .filter(mFilter)
                                                                      .sorted(mComparator)
                                                                      .map(TaskDataSummary::from)
                                                                      .toList())
                                       .subscribe(taskDataSummaries -> {
                                                      if (taskDataSummaries.isEmpty()) {
                                                          mView.showEmptyTaskListView();
                                                      } else {
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

    public void setFilter(@NonNull final FilterField filterField) {
        Assertion.nonNull(filterField);
        mFilter = sFilterMap.get(filterField);
        Assertion.nonNull(mFilter);

        reinit();
    }

    public void setSort(@NonNull final SortField sortField) {
        Assertion.nonNull(sortField);
        mComparator = sComparatorMap.get(sortField);
        Assertion.nonNull(mComparator);

        reinit();
    }

    private void reinit() {
        mActiveSubscription.ifPresent(Subscription::unsubscribe);
        init();
    }
}
