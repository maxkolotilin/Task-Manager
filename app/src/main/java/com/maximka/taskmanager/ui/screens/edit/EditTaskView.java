package com.maximka.taskmanager.ui.screens.edit;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.base.BaseView;
import com.maximka.taskmanager.ui.data.TaskViewData;

interface EditTaskView extends BaseView {
    void showInvalidInputMessage();
    void showNotFoundErrorMessage();
    void hideKeyboard();
    void setExistedTaskData(@NonNull final TaskViewData taskData);
}
