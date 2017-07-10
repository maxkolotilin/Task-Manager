package com.maximka.taskmanager.ui.list;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.list.recycler.TaskDataSummary;

import java.util.List;

interface TaskListView {
    void updateTaskListData(@NonNull final List<TaskDataSummary> newTaskList);
    void showEmptyTaskListView();
    void showTaskListView();
    void showErrorMessage();
}
