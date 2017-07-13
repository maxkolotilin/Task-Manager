package com.maximka.taskmanager.ui.navigation;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.annimon.stream.function.Consumer;
import com.maximka.taskmanager.R;
import com.maximka.taskmanager.ui.edit.EditTaskFragment;
import com.maximka.taskmanager.ui.details.TaskDetailsFragment;
import com.maximka.taskmanager.ui.list.TaskListFragment;
import com.maximka.taskmanager.utils.Assertion;

public final class Navigator {
    private static final String CREATE_FRAGMENT_TAG = "tagCreateFragment";
    private static final String LIST_FRAGMENT_TAG = "tagListFragment";
    private static final String DETAILS_FRAGMENT_TAG = "tagDetailsFragment";
    private static final String EDIT_FRAGMENT_TAG = "tagEditFragment";

    @NonNull private final FragmentManager mFragmentManager;

    public Navigator(@NonNull final FragmentManager fragmentManager) {
        Assertion.nonNull(fragmentManager);

        mFragmentManager = fragmentManager;
    }

    public void navigateToCreateScreen()
    {
        runInFragmentTransaction(
                getBackableTransactionBody(EditTaskFragment.newInstance(), CREATE_FRAGMENT_TAG)
        );
    }

    public void navigateToDetailsScreen(@NonNull final String taskId) {
        Assertion.nonNull(taskId);

        runInFragmentTransaction(
                getBackableTransactionBody(TaskDetailsFragment.newInstance(taskId), DETAILS_FRAGMENT_TAG)
        );
    }

    public void navigateToEditScreen(@NonNull final String taskId)
    {
        Assertion.nonNull(taskId);

        runInFragmentTransaction(
                getBackableTransactionBody(EditTaskFragment.newInstance(taskId), EDIT_FRAGMENT_TAG)
        );
    }

    public void navigateToTaskListScreen() {
        runInFragmentTransaction(transaction ->
            transaction.add(R.id.fragment_container, TaskListFragment.newInstance(), LIST_FRAGMENT_TAG)
        );
    }

    public void navigateBack() {
        mFragmentManager.popBackStack();
    }

    private void runInFragmentTransaction(@NonNull final Consumer<FragmentTransaction> transactionConsumer) {
        Assertion.nonNull(transactionConsumer);

        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transactionConsumer.accept(transaction);
        transaction.commit();
    }

    private Consumer<FragmentTransaction> getBackableTransactionBody(@NonNull final Fragment fragment,
                                                                     @NonNull final String tag) {
        Assertion.nonNull(fragment, tag);

        return transaction -> {
                transaction.setCustomAnimations(R.anim.enter_from_right,
                                                R.anim.exit_to_left,
                                                R.anim.enter_from_left,
                                                R.anim.exit_to_right);

                transaction.replace(R.id.fragment_container, fragment, tag);
                transaction.addToBackStack(tag);
        };
    }
}
