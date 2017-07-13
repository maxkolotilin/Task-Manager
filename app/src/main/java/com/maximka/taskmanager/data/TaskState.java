package com.maximka.taskmanager.data;


public enum TaskState {
    NEW(0),
    IN_PROGRESS(1),
    DONE(2);

    private final int mState;

    TaskState(final int state) {
        mState = state;
    }

    public int toInt() {
        return mState;
    }

    public static TaskState fromInt(final int state) {
        if (state == 0)
        {
            return NEW;
        } else if (state == 1) {
            return IN_PROGRESS;
        } else if (state == 2) {
            return DONE;
        } else {
            throw new IllegalArgumentException("Invalid task state");
        }
    }
}
