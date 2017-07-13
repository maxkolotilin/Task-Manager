package com.maximka.taskmanager.ui.screens.details;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.base.BaseView;
import com.maximka.taskmanager.ui.data.TaskViewData;

interface TaskDetailsView extends BaseView {
    void showErrorMessage();
    void updateTaskDataViews(@NonNull final TaskViewData taskData);
}
