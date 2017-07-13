package com.maximka.taskmanager.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.formatters.ProgressPercentFormatter;
import com.maximka.taskmanager.formatters.TaskStateFormatter;
import com.maximka.taskmanager.formatters.TimeIntervalFormatter;
import com.maximka.taskmanager.ui.base.BaseFragment;
import com.maximka.taskmanager.ui.data.TaskViewData;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

public final class TaskDetailsFragment extends BaseFragment<TaskDetailsPresenter> implements TaskDetailsView {
    private static final String TASK_ID_ARG = "taskId";

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mStartDateTextView;
    private TextView mDueDateTextView;
    private TextView mEstimatedTimeTextView;
    private TextView mStateTextView;
    private TextView mProgressTextView;
    private SeekBar mProgressSeekBar;

    public static TaskDetailsFragment newInstance(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        final Bundle arguments = new Bundle();
        arguments.putString(TASK_ID_ARG, taskId);

        final TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.task_details_fragment;
    }

    @Override
    protected int getFloatingButtonIcon() {
        return R.drawable.ic_fab_edit;
    }

    @Override
    protected View.OnClickListener getOnFloatingButtonClickListener() {
        return v -> getPresenter().onEditButtonClicked();
    }

    @Override
    protected boolean showToolbarBackArrow() {
        return true;
    }

    @NonNull
    @Override
    protected TaskDetailsPresenter createPresenter() {
        return new TaskDetailsPresenter(this,
                                        new Navigator(getFragmentManager()),
                                        getStringArgument(TASK_ID_ARG).orElseThrow(IllegalArgumentException::new));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title);
        mDescriptionTextView = (TextView) rootView.findViewById(R.id.description);
        mStartDateTextView = (TextView) rootView.findViewById(R.id.start_date);
        mDueDateTextView = (TextView) rootView.findViewById(R.id.due_date);
        mEstimatedTimeTextView = (TextView) rootView.findViewById(R.id.estimated_time);
        mStateTextView = (TextView) rootView.findViewById(R.id.state);
        mProgressTextView = (TextView) rootView.findViewById(R.id.progress);
        mProgressSeekBar = (SeekBar) rootView.findViewById(R.id.progress_bar);

        Assertion.nonNull(mTitleTextView,
                          mDescriptionTextView,
                          mStartDateTextView,
                          mDueDateTextView,
                          mEstimatedTimeTextView,
                          mStateTextView,
                          mProgressSeekBar,
                          mProgressTextView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@NonNull final SeekBar seekBar, final int progress, final boolean fromUser) {
                mProgressTextView.setText(getString(R.string.task_progress_format_string, progress));
            }

            @Override
            public void onStartTrackingTouch(@NonNull final SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(@NonNull final SeekBar seekBar) {
                getPresenter().updateTaskProgress(new Percent(seekBar.getProgress()));
            }
        });
    }

    @Override
    public void showErrorMessage() {
        showToast(R.string.detail_task_load_error);
    }

    @Override
    public void updateTaskDataViews(@NonNull final TaskViewData taskData) {
        Assertion.nonNull(taskData);

        final Context context = getActivity();
        final Percent progress = taskData.getProgress();

        mTitleTextView.setText(taskData.getTitle());
        mDescriptionTextView.setText(taskData.getDescription());

        mStartDateTextView.setText(getString(R.string.details_start_date_format_string,
                                             taskData.getFormattedStartDate()));

        mDueDateTextView.setText(getString(R.string.details_due_date_format_string,
                                           taskData.getFormattedDueDate()));

        mEstimatedTimeTextView.setText(getString(R.string.details_estimated_time_format_string,
                                                 TimeIntervalFormatter.format(taskData.getEstimatedTime(),
                                                                              context)));

        mStateTextView.setText(TaskStateFormatter.format(taskData.getState(), context));
        mProgressTextView.setText(ProgressPercentFormatter.format(progress, context));
        mProgressSeekBar.setProgress(progress.asInt());
    }
}
