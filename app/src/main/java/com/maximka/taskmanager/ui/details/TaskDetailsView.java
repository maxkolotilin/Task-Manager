package com.maximka.taskmanager.ui.details;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.TaskData;

interface TaskDetailsView {
    void showErrorMessage();
    void updateTaskDataViews(@NonNull final TaskData taskData);
}
