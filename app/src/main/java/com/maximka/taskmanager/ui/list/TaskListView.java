package com.maximka.taskmanager.ui.list;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.base.BaseView;
import com.maximka.taskmanager.ui.data.TaskSummaryViewData;

import java.util.List;

interface TaskListView extends BaseView {
    void updateTaskListData(@NonNull final List<TaskSummaryViewData> newTaskList);
    void showEmptyTaskListView();
    void showTaskListView();
    void showErrorMessage();
}
