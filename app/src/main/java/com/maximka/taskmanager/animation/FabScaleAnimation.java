package com.maximka.taskmanager.animation;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.maximka.taskmanager.utils.Assertion;

import rx.functions.Action0;

public final class FabScaleAnimation {
    private static final float NORMAL_SCALE = 1f;
    private static final float SCALED = 0.15f;
    private static final float PIVOT = 0.5f;

    public static void applyScaleAnimation(@NonNull final FloatingActionButton actionButton,
                                           final int duration,
                                           @NonNull final Action0 onAnimationMiddle) {
        Assertion.nonNull(onAnimationMiddle, actionButton);
        Assertion.nonNegative(duration);

        final int halfDuration = duration / 2;
        actionButton.clearAnimation();
        actionButton.startAnimation(getShrinkAnimation(halfDuration,
                                    () -> {
                                        onAnimationMiddle.call();
                                        actionButton.startAnimation(getExpandAnimation(halfDuration));
                                    })
        );
    }

    private static ScaleAnimation getExpandAnimation(final int duration) {
        final ScaleAnimation expand =  new ScaleAnimation(SCALED,
                                                          NORMAL_SCALE,
                                                          SCALED,
                                                          NORMAL_SCALE,
                                                          Animation.RELATIVE_TO_SELF,
                                                          PIVOT,
                                                          Animation.RELATIVE_TO_SELF,
                                                          PIVOT);
        expand.setDuration(duration);
        expand.setInterpolator(new AccelerateInterpolator());

        return expand;
    }

    private static ScaleAnimation getShrinkAnimation(final int duration,
                                                     @NonNull final Action0 onAnimationEnd) {
        Assertion.nonNull(onAnimationEnd);

        final ScaleAnimation shrink =  new ScaleAnimation(NORMAL_SCALE,
                                                          SCALED,
                                                          NORMAL_SCALE,
                                                          SCALED,
                                                          Animation.RELATIVE_TO_SELF,
                                                          PIVOT,
                                                          Animation.RELATIVE_TO_SELF,
                                                          PIVOT);
        shrink.setDuration(duration);
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onAnimationEnd.call();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return shrink;
    }

    private FabScaleAnimation() {
    }
}
