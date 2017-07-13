package com.maximka.taskmanager.ui.activity;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.animation.FabScaleAnimation;
import com.maximka.taskmanager.ui.navigation.Navigator;

public class MainActivity extends AppCompatActivity implements ActivityView, FloatingActionButtonOwner {
    private ActivityPresenter mPresenter;
    private FloatingActionButton mFloatingBtn;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingBtn = (FloatingActionButton) findViewById(R.id.fab);
        mPresenter = new ActivityPresenter(this, new Navigator(getSupportFragmentManager()));

        if (savedInstanceState == null) {
            mPresenter.showTaskListScreen();
        }
    }

    @Override
    public void setUpFloatingButton(@DrawableRes final int iconResId, @Nullable final View.OnClickListener listener) {
        FabScaleAnimation.applyScaleAnimation(mFloatingBtn,
                                              getResources().getInteger(R.integer.fragment_animation_duration),
                                              () -> {
                                                  mFloatingBtn.setImageResource(iconResId);
                                                  mFloatingBtn.setOnClickListener(listener);
                                              });
    }

    @Override
    public boolean onSupportNavigateUp() {
        mPresenter.onToolbarBackPressed();
        return true;
    }
}
