package com.futureinst.widget;

import android.view.animation.Interpolator;

public class CustomInterpolator implements Interpolator {
	 /**
     * 
     */
    public CustomInterpolator() {
    }
 
 
    @Override
    public float getInterpolation(float input) {
//        return (float)(Math.sin(2 * mCycles * Math.PI * input));
//    	return t * t * (((mTension + 1) * t) - mTension);
    	return (float)(Math.cos((input - 3) * Math.PI) / 2.0f)-0.5f;
    }
 
	
}
