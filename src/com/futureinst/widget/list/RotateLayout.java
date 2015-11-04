package com.futureinst.widget.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.futureinst.R;

public class RotateLayout extends LinearLayout {

	public RotateLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public RotateLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private Bitmap bm;
	private View view;
	private ImageView imageview;
	private float curDegrees = 0;
	private float curHeight = 1;
	void init() {
		view = LayoutInflater.from(getContext()).inflate(R.layout.rotatelayout,
				this);
		bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.loading_icon);
		
		imageview = (ImageView) view.findViewById(R.id.ImageView1);
		imageview.setImageBitmap(bm);
	}

	public void rotateAnimation() {
		RotateAnimation animation = new RotateAnimation(360f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		LinearInterpolator lin = new LinearInterpolator();
		animation.setInterpolator(lin);
		animation.setDuration(1000);
		animation.setRepeatCount(-1);
		animation.setFillAfter(true);
		imageview.startAnimation(animation);
	}

	public void celarAniamtion() {
		imageview.clearAnimation();
	}

	/**
	 * @param roate
	 */
	public void roate(float roate) {
		
		int bmpW = bm.getWidth();
		 int bmpH = bm.getHeight();
		Matrix mt = new Matrix();
	
		curHeight -= roate;
		if(curHeight < 0.01) curHeight = 0.01f;
		float scale = curHeight/bmpH/4;
		if(scale > 1) scale = 1;
		mt.postScale(scale, scale);
		
		mt.postRotate((curDegrees = curDegrees - roate)*2, bmpW / 2, bmpH / 2);
		if(bmpW == 0 || bmpH == 0) bmpW = bmpH = 1;
		Bitmap resizeBmp = Bitmap.createBitmap(bm, 0, 0, bmpW, bmpH, mt, true);
		imageview.setScaleType(ScaleType.CENTER);
		imageview.setImageBitmap(resizeBmp);

	}

	/**
	 * 
	 */
	public void reset() {
		if (isComplete) {
			return;
		}
		isComplete = true;
	}

	private boolean isComplete = false;

	/**
	 * 
	 */
	public void toup() {
		celarAniamtion();
		curHeight = 1;
		isComplete = false;
//		scrollTo(0,0);
	}
	
	public class  baseAnimationListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}