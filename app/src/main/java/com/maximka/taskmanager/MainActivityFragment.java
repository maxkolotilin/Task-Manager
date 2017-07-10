package com.maximka.taskmanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximka.taskmanager.data.TaskDataRealm;

import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Realm.getDefaultInstance()
                .where(TaskDataRealm.class)
                .findAllAsync()
                .asObservable();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
