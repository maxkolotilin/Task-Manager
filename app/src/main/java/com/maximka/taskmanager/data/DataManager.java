package com.maximka.taskmanager.data;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.realm.TaskDataRealm;
import com.maximka.taskmanager.utils.Assertion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

public final class DataManager {

    public Observable<List<TaskData>> getAllTasksObservable() {
        final Realm realm = Realm.getDefaultInstance();

        return realm.where(TaskDataRealm.class)
                    .findAllSortedAsync(TaskDataRealm.START_DATE, Sort.DESCENDING)
                    .asObservable()
                    .filter(RealmResults::isLoaded)
                    .flatMap(taskDataRealms -> Observable.from(taskDataRealms)
                                                         .map(this::mapToTaskData)
                                                         .toList()
                    )
                    .doOnUnsubscribe(realm::close);
    }

    public void createOrUpdateTask(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(backgroundRealm ->
                    backgroundRealm.insertOrUpdate(mapToTaskDataRealm(taskData))
            );
        } finally {
            realm.close();
        }
    }

    public Optional<Observable<TaskData>> getCachedTaskDataObservable(@NonNull final String id) {
        Assertion.nonNull(id);

        final Realm realm = Realm.getDefaultInstance();
        return Optional.ofNullable(realm.where(TaskDataRealm.class)
                                        .equalTo(TaskDataRealm.ID, id)
                                        .findFirst())

                       .map(taskDataRealm -> taskDataRealm.asObservable()
                                                          .cast(TaskDataRealm.class)
                                                          .map(this::mapToTaskData)
                                                          .doOnUnsubscribe(realm::close));
    }

    public Optional<TaskData> getCachedTaskData(@NonNull final String id) {
        Assertion.nonNull(id);

        return getCachedTaskDataObservable(id)
                    .map(taskDataObservable -> taskDataObservable.toBlocking()
                                                                 .first());
    }

    private TaskData mapToTaskData(@NonNull final TaskDataRealm taskDataRealm) {
        Assertion.nonNull(taskDataRealm);

        return TaskData.newBuilder()
                       .withId(taskDataRealm.getId())
                       .withTitle(taskDataRealm.getTitle())
                       .withDescription(taskDataRealm.getDescription())
                       .withStartDate(taskDataRealm.getStartDate())
                       .withDueDate(taskDataRealm.getDueDate())
                       .withState(taskDataRealm.getState())
                       .withProgressPercent(taskDataRealm.getProgressPercent())
                       .withEstimatedTime(taskDataRealm.getEstimatedTime())
                       .build();
    }

    private TaskDataRealm mapToTaskDataRealm(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        final TaskDataRealm taskDataRealm = new TaskDataRealm();
        taskDataRealm.setId(taskData.getId());
        taskDataRealm.setTitle(taskData.getTitle());
        taskDataRealm.setDescription(taskData.getDescription());
        taskDataRealm.setStartDate(taskData.getStartDate());
        taskDataRealm.setDueDate(taskData.getDueDate());
        taskDataRealm.setProgressPercent(taskData.getProgressPercent());
        taskDataRealm.setState(taskData.getState());
        taskDataRealm.setEstimatedTime(taskData.getEstimatedTime());

        return taskDataRealm;
    }
}
