package com.maximka.taskmanager.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.recycler.adapter.ListAdapter;
import com.maximka.taskmanager.ui.list.recycler.TaskDataSummary;
import com.maximka.taskmanager.ui.list.recycler.TaskListDiffCallback;
import com.maximka.taskmanager.ui.list.recycler.TaskSummaryViewHolder;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Collections;
import java.util.List;

public class TaskListFragment extends Fragment implements TaskListView {
    private RecyclerView mTaskListRecyclerView;
    private View mEmptyView;
    private ListAdapter<TaskDataSummary> mTaskListAdapter;
    private TaskListPresenter mPresenter;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.task_list_fragment, container, false);
        mTaskListRecyclerView = (RecyclerView) rootView.findViewById(R.id.task_list);
        mEmptyView = rootView.findViewById(R.id.empty_view);
        Assertion.nonNull(mTaskListRecyclerView, mEmptyView);

        initRecyclerView();
        mPresenter = new TaskListPresenter(this);
        mPresenter.init();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    private void initRecyclerView() {
        final Context context = getActivity();

        mTaskListRecyclerView.setHasFixedSize(true);
        mTaskListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTaskListAdapter = new ListAdapter.Builder<>(context,
                                                    Collections.emptyList(),
                                                    TaskSummaryViewHolder::new,
                                                    () -> R.layout.task_summary_item)
                                         .withDiffCallback(TaskListDiffCallback::new)
                                         .build();
        mTaskListRecyclerView.setAdapter(mTaskListAdapter);
    }

    @Override
    public void updateTaskListData(@NonNull final List<TaskDataSummary> newTaskList) {
        Assertion.nonNullContent(newTaskList);
        mTaskListAdapter.setDataList(newTaskList);
    }

    @Override
    public void showEmptyTaskListView() {
        mTaskListRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTaskListView() {
        mTaskListRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(mTaskListRecyclerView, R.string.task_loading_error, Snackbar.LENGTH_LONG)
                .show();
    }
}
