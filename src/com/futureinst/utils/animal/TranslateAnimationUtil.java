package com.futureinst.utils.animal;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by hao on 2015/12/29.
 */
public class TranslateAnimationUtil {
    public static void slideView(final View view, final float fromX,
                                 final float toX, final float fromY,
                                 final float toY,long durationMIllis,long startOffset, final OnAnimationFinished onAnimationFinished){
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX,toX,fromY,toY);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setDuration(durationMIllis);
        translateAnimation.setStartOffset(startOffset);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = (int) (view.getLeft() + (toX - fromX));
                int top = (int) (view.getTop() + (toY - fromY));
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left + width, top + height);
                if (onAnimationFinished!=null) {
                    onAnimationFinished.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
    }
    public interface OnAnimationFinished{
        void onAnimationFinished();
    }

}
