package com.maximka.taskmanager.data;

import io.realm.RealmObject;

public class TaskDataRealm extends RealmObject {
    private String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String mTitle) {
        this.mTitle = mTitle;
    }
}
