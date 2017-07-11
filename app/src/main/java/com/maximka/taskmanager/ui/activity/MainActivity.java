package com.maximka.taskmanager.ui.activity;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.maximka.taskmanager.R;
import com.maximka.taskmanager.animation.FabScaleAnimation;
import com.maximka.taskmanager.ui.navigation.Navigator;

public class MainActivity extends AppCompatActivity implements ActivityView, FloatingActionButtonOwner {
    @NonNull private final ActivityPresenter mPresenter = new ActivityPresenter(this);
    private FloatingActionButton mFloatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingBtn = (FloatingActionButton) findViewById(R.id.fab);

        if (savedInstanceState == null) {
            new Navigator(getSupportFragmentManager()).navigateToTaskListFragment();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
