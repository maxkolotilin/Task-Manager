package com.maximka.taskmanager;

import android.app.Application;

import io.realm.Realm;

public final class TaskManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
