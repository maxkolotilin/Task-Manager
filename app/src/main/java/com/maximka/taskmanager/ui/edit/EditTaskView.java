package com.maximka.taskmanager.ui.edit;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.data.TaskData;

interface EditTaskView {
    void showErrorMessage();
    void hideKeyboard();
    void setExistedTaskData(@NonNull final TaskData taskData);
}
