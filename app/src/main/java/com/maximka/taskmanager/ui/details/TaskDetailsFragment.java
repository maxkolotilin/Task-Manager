package com.maximka.taskmanager.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.data.Percent;
import com.maximka.taskmanager.data.TaskData;
import com.maximka.taskmanager.formatters.DateFormatter;
import com.maximka.taskmanager.formatters.ProgressPercentFormatter;
import com.maximka.taskmanager.formatters.TaskStateFormatter;
import com.maximka.taskmanager.formatters.TimeIntervalFormatter;
import com.maximka.taskmanager.ui.activity.FloatingActionButtonOwner;
import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

public final class TaskDetailsFragment extends Fragment implements TaskDetailsView {
    private static final String TASK_ID_ARG = "taskId";

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mStartDateTextView;
    private TextView mDueDateTextView;
    private TextView mEstimatedTimeTextView;
    private TextView mStateTextView;
    private TextView mProgressTextView;
    private SeekBar mProgressSeekBar;
    private TaskDetailsPresenter mPresenter;

    public static TaskDetailsFragment newInstance(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        final Bundle arguments = new Bundle();
        arguments.putString(TASK_ID_ARG, taskId);

        final TaskDetailsFragment fragment = new TaskDetailsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.task_details_fragment, container, false);
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

        final String taskId = getTaskIdFromArguments();
        mPresenter = new TaskDetailsPresenter(this, new Navigator(getFragmentManager()), taskId);
        mPresenter.init();

        ((FloatingActionButtonOwner) getActivity())
                .setUpFloatingButton(R.drawable.ic_fab_edit,
                                     v -> mPresenter.onEditButtonClicked());

        mProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@NonNull final SeekBar seekBar, final int progress, final boolean fromUser) {
                mProgressTextView.setText(getString(R.string.task_progress_format_string, progress));
            }

            @Override
            public void onStartTrackingTouch(@NonNull final SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(@NonNull final SeekBar seekBar) {
                mPresenter.updateTaskProgress(new Percent(seekBar.getProgress()));
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    @NonNull
    private String getTaskIdFromArguments() {
        return Optional.ofNullable(getArguments())
                       .map(bundle -> bundle.getString(TASK_ID_ARG))
                       .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(mTitleTextView, R.string.detail_task_load_error, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void updateTaskDataViews(@NonNull final TaskData taskData) {
        Assertion.nonNull(taskData);

        final Context context = getActivity();
        final Percent progress = taskData.getProgressPercent();

        mTitleTextView.setText(taskData.getTitle());
        mDescriptionTextView.setText(taskData.getDescription());

        mStartDateTextView.setText(getString(R.string.details_start_date_format_string,
                                             DateFormatter.format(taskData.getStartDate())));

        mDueDateTextView.setText(getString(R.string.details_due_date_format_string,
                                           DateFormatter.format(taskData.getDueDate())));

        mEstimatedTimeTextView.setText(getString(R.string.details_estimated_time_format_string,
                                                 TimeIntervalFormatter.format(taskData.getEstimatedTime(),
                                                                              context)));

        mStateTextView.setText(TaskStateFormatter.format(taskData.getState(), context));
        mProgressTextView.setText(ProgressPercentFormatter.format(progress, context));
        mProgressSeekBar.setProgress(progress.asInt());
    }
}
