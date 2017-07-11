package com.maximka.taskmanager.ui.activity;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;

public interface FloatingActionButtonOwner {
    void setUpFloatingButton(@DrawableRes final int iconResId, @Nullable final View.OnClickListener listener);
}
