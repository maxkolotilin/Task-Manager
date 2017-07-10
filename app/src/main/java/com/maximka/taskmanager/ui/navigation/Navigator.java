package com.maximka.taskmanager.ui.navigation;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.ui.create.CreateTaskFragment;
import com.maximka.taskmanager.ui.list.TaskListFragment;

public final class Navigator {
    private static final String CREATE_FRAGMENT_TAG = "tagCreateFragment";
    private static final String LIST_FRAGMENT_TAG = "tagListFragment";
    private static final String DETAILS_FRAGMENT_TAG = "tagDetailsFragment";

    public static void navigateToCreateScreen(@NonNull final FragmentManager fragmentManager)
    {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,
                                        R.anim.exit_to_left,
                                        R.anim.enter_from_left,
                                        R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, CreateTaskFragment.newInstance(), CREATE_FRAGMENT_TAG);
        transaction.addToBackStack(CREATE_FRAGMENT_TAG);
        transaction.commit();
    }

    public static void navigateToTaskListFragment(@NonNull final FragmentManager fragmentManager) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, TaskListFragment.newInstance(), LIST_FRAGMENT_TAG);
        transaction.commit();
    }

    private Navigator() {
    }
}
