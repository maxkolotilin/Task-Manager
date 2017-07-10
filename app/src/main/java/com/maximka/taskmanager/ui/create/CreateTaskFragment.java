package com.maximka.taskmanager.ui.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximka.taskmanager.R;

public final class CreateTaskFragment extends Fragment {

    public static CreateTaskFragment newInstance() {
        return new CreateTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_task_fragment, container, false);
        return rootView;
    }
}
