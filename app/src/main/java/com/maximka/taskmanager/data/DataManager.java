package com.maximka.taskmanager.data;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.data.realm.TaskDataRealm;
import com.maximka.taskmanager.utils.Assertion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public final class DataManager {
    @NonNull private final Realm mRealm;

    public DataManager() {
        mRealm = Realm.getDefaultInstance();
    }

    public void close() {
        mRealm.close();
    }

    public Observable<List<TaskData>> getAllTasksObservable() {
        return mRealm.where(TaskDataRealm.class)
                     .findAllAsync()
                     .asObservable()
                     .filter(RealmResults::isLoaded)
                     .flatMap(taskDataRealms -> Observable.from(taskDataRealms)
                                                          .map(this::mapToTaskData)
                                                          .toList());
    }

    public void createOrUpdateTask(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        final TaskDataRealm taskDataRealm = mapToTaskDataRealm(taskData);
        mRealm.executeTransactionAsync(backgroundRealm -> backgroundRealm.insertOrUpdate(taskDataRealm));
    }

    public Optional<Observable<TaskData>> getCachedTaskDataObservable(@NonNull final String id) {
        Assertion.nonNull(id);

        return Optional.ofNullable(mRealm.where(TaskDataRealm.class)
                                         .equalTo(TaskDataRealm.ID, id)
                                         .findFirst())

                       .map(taskDataRealm -> taskDataRealm.asObservable()
                                            .cast(TaskDataRealm.class)
                                            .map(this::mapToTaskData));
    }

    public Optional<TaskData> getCachedTaskData(@NonNull final String id) {
        Assertion.nonNull(id);

        return getCachedTaskDataObservable(id)
                    .map(taskDataObservable -> taskDataObservable.toBlocking()
                                                                 .first());
    }

    private TaskData mapToTaskData(@NonNull final TaskDataRealm taskDataRealm) {
        Assertion.nonNull(taskDataRealm);

        return new TaskData(taskDataRealm.getId(),
                            taskDataRealm.getTitle(),
                            taskDataRealm.getDescription(),
                            taskDataRealm.getStartDate(),
                            taskDataRealm.getDueDate(),
                            taskDataRealm.getProgressPercent(),
                            taskDataRealm.getState(),
                            taskDataRealm.getEstimatedTime());
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
