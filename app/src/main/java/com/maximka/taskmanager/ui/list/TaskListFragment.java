package com.maximka.taskmanager.ui.list;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.preferences.Preferences;
import com.maximka.taskmanager.recycler.adapter.ListAdapter;
import com.maximka.taskmanager.ui.base.BaseFragment;
import com.maximka.taskmanager.ui.list.menu.StateFilter;
import com.maximka.taskmanager.ui.list.menu.SortField;
import com.maximka.taskmanager.ui.data.TaskSummaryViewData;
import com.maximka.taskmanager.ui.list.recycler.TaskListDiffCallback;
import com.maximka.taskmanager.ui.list.recycler.TaskSummaryViewHolder;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

import java.util.Collections;
import java.util.List;

public final class TaskListFragment extends BaseFragment<TaskListPresenter> implements TaskListView {
    private static final int COLUMN_COUNT_IN_LANDSCAPE = 2;

    private RecyclerView mTaskListRecyclerView;
    private View mEmptyView;
    private ListAdapter<TaskSummaryViewData> mTaskListAdapter;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.task_list_fragment;
    }

    @Override
    protected int getFloatingButtonIcon() {
        return R.drawable.ic_fab_create;
    }

    @Override
    protected View.OnClickListener getOnFloatingButtonClickListener() {
        return v -> getPresenter().goToCreateScreen();
    }

    @Override
    protected boolean showToolbarBackArrow() {
        return false;
    }

    @NonNull
    @Override
    protected TaskListPresenter createPresenter() {
        return new TaskListPresenter(this,
                                     new Navigator(getFragmentManager()),
                                     Preferences.get(getActivity()));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mTaskListRecyclerView = (RecyclerView) rootView.findViewById(R.id.task_list);
        mEmptyView = rootView.findViewById(R.id.empty_view);

        Assertion.nonNull(mTaskListRecyclerView, mEmptyView);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
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
                               .withItemClickListener((position, data) -> getPresenter().goToDetailsScreen(data))
                               .build();

        mTaskListRecyclerView.setAdapter(mTaskListAdapter);
    }

    @NonNull
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

        final Preferences preferences = Preferences.get(getContext());
        setMenuItemChecked(menu, preferences.getStateFilter().getMenuItemId());
        setMenuItemChecked(menu, preferences.getSortField().getMenuItemId());
    }

    private void setMenuItemChecked(@NonNull final Menu menu, @IdRes final int itemId) {
        Assertion.nonNull(menu);
        menu.findItem(itemId).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        item.setChecked(true);
        final int id = item.getItemId();
        final TaskListPresenter presenter = getPresenter();

        return StateFilter.getStateFilter(id)
                          .map(stateFilter -> {
                              presenter.setStateFilter(stateFilter);
                              return true;
                          })
                          .or(() -> SortField.getSortField(id)
                                             .map(sortField -> {
                                                 presenter.setSortField(sortField);
                                                 return true;
                                             })
                          )
                          .orElse(super.onOptionsItemSelected(item));
    }

    @Override
    public void updateTaskListData(@NonNull final List<TaskSummaryViewData> newTaskList) {
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
        showSnack(R.string.task_loading_error);
    }
}
