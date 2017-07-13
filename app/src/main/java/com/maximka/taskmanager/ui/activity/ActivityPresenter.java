package com.maximka.taskmanager.ui.activity;

import android.support.annotation.NonNull;

import com.maximka.taskmanager.ui.navigation.Navigator;
import com.maximka.taskmanager.utils.Assertion;

final class ActivityPresenter {
    @NonNull private final ActivityView mView;
    @NonNull private final Navigator mNavigator;

    ActivityPresenter(@NonNull final ActivityView view, @NonNull final Navigator navigator) {
        Assertion.nonNull(view, navigator);

        mView = view;
        mNavigator = navigator;
    }

    public void showTaskListScreen() {
        mNavigator.navigateToTaskListScreen();
    }

    public void onToolbarBackPressed() {
        mNavigator.navigateBack();
    }
}
