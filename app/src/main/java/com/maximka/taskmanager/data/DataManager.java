package com.maximka.taskmanager.data;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.realm.TaskDataRealm;
import com.maximka.taskmanager.utils.Assertion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public final class DataManager {
    @NonNull private final Realm realm;

    public DataManager() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }

    public Observable<List<TaskData>> getAllTasks() {
        return realm.where(TaskDataRealm.class)
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
        realm.executeTransactionAsync(backgroundRealm -> backgroundRealm.insertOrUpdate(taskDataRealm));
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
