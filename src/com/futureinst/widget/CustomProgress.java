package com.futureinst.widget;

import com.futureinst.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class CustomProgress extends ImageView {
	private Animation animation;
	public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		setImage(context, R.drawable.loading_icon);
	}

	public CustomProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setImage(context, R.drawable.loading_icon);
	}

	public CustomProgress(Context context) {
		super(context);
		setImage(context, R.drawable.loading_icon);
		// TODO Auto-generated constructor stub
	}
	private void setImage(Context context,int resId){
		setImageDrawable(getResources().getDrawable(resId));
		animation = AnimationUtils.loadAnimation(context, R.anim.progress_rotate);
//		animation.setInterpolator(new CustomInterpolator());
		animation.setInterpolator(new LinearInterpolator());
		
	}
	public void startAnima(){
		setAnimation(animation);
		startAnimation(animation);
	}
	public void stopAnima(){
		if(animation.hasStarted()){
			clearAnimation();
		}
	}
}
