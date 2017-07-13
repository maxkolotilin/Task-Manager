package com.maximka.taskmanager.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.maximka.taskmanager.utils.Assertion;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends BaseView> {
    @NonNull private final CompositeSubscription mSubscriptions = new CompositeSubscription();
    @NonNull private Optional<T> mView = Optional.empty();

    public BasePresenter(@NonNull final T view) {
        Assertion.nonNull(view);

        mView = Optional.of(view);
    }

    protected abstract void init();

    @CallSuper
    protected void onViewDestroyed() {
        mSubscriptions.unsubscribe();
        mView = Optional.empty();
    }

    protected void runWithView(@NonNull final Consumer<T> viewConsumer) {
        Assertion.nonNull(viewConsumer);

        mView.ifPresent(viewConsumer);
    }

    protected void addSubscription(@NonNull final Subscription subscription) {
        Assertion.nonNull(subscription);

        mSubscriptions.add(subscription);
    }

    protected void addSubscription(@NonNull final Optional<Subscription> subscriptionOpt) {
        Assertion.nonNull(subscriptionOpt);

        subscriptionOpt.ifPresent(this::addSubscription);
    }
}
