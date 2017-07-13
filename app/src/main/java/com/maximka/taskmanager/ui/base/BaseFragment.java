package com.maximka.taskmanager.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.ui.activity.FloatingActionButtonOwner;
import com.maximka.taskmanager.utils.Assertion;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private View mRootView;
    @NonNull private Optional<T> mPresenterOpt = Optional.empty();

    @LayoutRes
    protected abstract int getLayoutResId();

    @DrawableRes
    protected abstract int getFloatingButtonIcon();

    protected abstract View.OnClickListener getOnFloatingButtonClickListener();

    protected abstract boolean showToolbarBackArrow();

    @NonNull
    protected abstract T createPresenter();

    @CallSuper
    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        Assertion.nonNull(mRootView);

        final Optional<AppCompatActivity> activity = Optional.of((AppCompatActivity) getActivity());

        activity.map(AppCompatActivity::getSupportActionBar)
                .ifPresent(actionBar -> actionBar.setDisplayHomeAsUpEnabled(showToolbarBackArrow()));

        activity.map(FloatingActionButtonOwner.class::cast)
                .ifPresent(owner -> owner.setUpFloatingButton(getFloatingButtonIcon(),
                                                              getOnFloatingButtonClickListener()));

        return mRootView;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenterOpt = Optional.of(createPresenter());
        mPresenterOpt.get().init();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        mPresenterOpt.get().onViewDestroyed();
        mPresenterOpt = Optional.empty();

        super.onDestroyView();
    }

    @NonNull
    protected T getPresenter() {
        return mPresenterOpt.orElseThrow(() ->
                new IllegalStateException("Call getPresenter() after onViewCreated"));
    }

    protected Optional<String> getStringArgument(@NonNull final String argKey) {
        Assertion.nonNull(argKey);

        return Optional.ofNullable(getArguments())
                       .map(bundle -> bundle.getString(argKey));
    }

    protected void showToast(@StringRes final int messageResId) {
        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_LONG)
                .show();
    }

    protected void showSnack(@StringRes final int messageResId) {
        Snackbar.make(mRootView, messageResId, Snackbar.LENGTH_LONG)
                .show();
    }
}
