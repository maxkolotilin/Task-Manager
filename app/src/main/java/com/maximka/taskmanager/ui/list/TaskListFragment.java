package com.maximka.taskmanager.ui.list;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.recycler.adapter.ListAdapter;
import com.maximka.taskmanager.ui.activity.FloatingActionButtonOwner;
import com.maximka.taskmanager.ui.list.menu.FilterField;
import com.maximka.taskmanager.ui.list.menu.SortField;
import com.maximka.taskmanager.ui.list.recycler.TaskDataSummary;
import com.maximka.taskmanager.ui.list.recycler.TaskListDiffCallback;
import com.maximka.taskmanager.ui.list.recycler.TaskSummaryViewHolder;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Collections;
import java.util.List;

public class TaskListFragment extends Fragment implements TaskListView {
    private static final int COLUMN_COUNT_IN_LANDSCAPE = 2;
    private static final SparseArray<FilterField> sIdToFilterField = new SparseArray<FilterField>() {{
        put(R.id.filter_all, FilterField.ALL);
        put(R.id.filter_new, FilterField.NEW);
        put(R.id.filter_in_progress, FilterField.IN_PROGRESS);
        put(R.id.filter_done, FilterField.DONE);
    }};
    private static final SparseArray<SortField> sIdToSortField = new SparseArray<SortField>() {{
        put(R.id.sort_start_date, SortField.START_DATE);
        put(R.id.sort_due_date, SortField.DUE_DATE);
        put(R.id.sort_progress, SortField.PROGRESS);
    }};

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

        setHasOptionsMenu(true);

        ((FloatingActionButtonOwner) getActivity())
                .setUpFloatingButton(R.drawable.ic_fab_create,
                                     v -> mPresenter.goToCreateScreen());

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setDisplayHomeAsUpEnabled(false);

        initRecyclerView();
        mPresenter = new TaskListPresenter(this, new Navigator(getFragmentManager()));
        mPresenter.init();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    private void initRecyclerView() {
        mTaskListRecyclerView.setHasFixedSize(true);
        mTaskListRecyclerView.setLayoutManager(getLayoutManager());

        mTaskListAdapter =
                new ListAdapter.Builder<>(getActivity(),
                                          Collections.emptyList(),
                                          TaskSummaryViewHolder::new,
                                          () -> R.layout.task_summary_item)

                               .withDiffCallback(TaskListDiffCallback::new)
                               .withItemClickListener((position, data) -> mPresenter.goToDetailsScreen(data))
                               .build();

        mTaskListRecyclerView.setAdapter(mTaskListAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        final Context context = getActivity();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(context, COLUMN_COUNT_IN_LANDSCAPE);
        } else {
            return new LinearLayoutManager(context);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        item.setChecked(true);
        final int id = item.getItemId();

        return Optional.ofNullable(sIdToFilterField.get(id))
                       .map(filterField -> {
                           mPresenter.setFilter(filterField);
                           return true;
                       })
                       .or(() -> Optional.ofNullable(sIdToSortField.get(id))
                                         .map(sortField -> {
                                             mPresenter.setSort(sortField);
                                             return true;
                                         })
                       )
                       .orElse(false)

                || super.onOptionsItemSelected(item);
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
