package com.maximka.taskmanager.ui.list.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.formatters.ProgressPercentFormatter;
import com.maximka.taskmanager.formatters.TaskStateFormatter;
import com.maximka.taskmanager.recycler.adapter.ListAdapterViewHolder;
import com.maximka.taskmanager.utils.Assertion;
import com.maximka.taskmanager.formatters.DateFormatter;

import java.util.Date;

final public class TaskSummaryViewHolder extends ListAdapterViewHolder<TaskDataSummary> {
    @NonNull private final TextView mTitleView;
    @NonNull private final TextView mDueDate;
    @NonNull private final TextView mState;
    @NonNull private final TextView mProgress;

    public TaskSummaryViewHolder(@NonNull final View itemView) {
        super(itemView);
        mTitleView = (TextView) itemView.findViewById(R.id.task_title);
        mDueDate = (TextView) itemView.findViewById(R.id.task_due_date);
        mState = (TextView) itemView.findViewById(R.id.task_state);
        mProgress = (TextView) itemView.findViewById(R.id.task_progress);

        Assertion.nonNull(mTitleView, mDueDate, mState, mProgress);
    }

    @Override
    public void bind(@NonNull final TaskDataSummary data) {
        Assertion.nonNull(data);

        final Context context = itemView.getContext();
        final Resources resources = context.getResources();
        final Date dueDate = data.getDueDate();

        mTitleView.setText(data.getTitle());
        mDueDate.setText(resources.getString(R.string.task_item_due_date_format_string,
                                             DateFormatter.format(dueDate)));
        mState.setText(TaskStateFormatter.format(data.getState(), context));
        mProgress.setText(ProgressPercentFormatter.format(data.getProgressPercent(), context));

        final int colorResId = dueDate.before(new Date()) ? R.color.text_color_warning : R.color.text_color_secondary;
        mDueDate.setTextColor(resources.getColor(colorResId));
    }
}
