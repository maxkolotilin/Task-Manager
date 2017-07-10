package com.maximka.taskmanager.ui.activity;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.DataManager;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.data.TaskState;
import com.maximka.taskmanager.data.TimeInterval;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Date;
import java.util.UUID;

final class ActivityPresenter {
    @NonNull private final ActivityView mView;

    ActivityPresenter(@NonNull final ActivityView view) {
        Assertion.nonNull(view);

        mView = view;
    }

    public void onCreateTaskButtonClicked() {
        // navigate to create screen
        final DataManager dataManager = new DataManager();
        dataManager.createOrUpdateTask(new TaskData(UUID.randomUUID().toString(),
                                                    "Title",
                                                    "Descr",
                                                    new Date(),
                                                    new Date(),
                                                    new Percent(12),
                                                    TaskState.NEW,
                                                    new TimeInterval(34210)));

        dataManager.close();
    }
}
