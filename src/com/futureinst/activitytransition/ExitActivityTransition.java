package com.futureinst.activitytransition;

import android.app.Activity;

import com.futureinst.activitytransition.core.MoveData;
import com.futureinst.activitytransition.core.TransitionAnimation;


/**
 * Created by takam on 2015/03/30.
 */
public class ExitActivityTransition {
    private final MoveData moveData;


    public ExitActivityTransition(MoveData moveData) {
        this.moveData = moveData;
    }

    public void exit(final Activity activity) {
        TransitionAnimation.startExitAnimation(moveData, new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
    }

}
