package com.futureinst.newbieguide;

import com.futureinst.R;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class NewbieGuide2 {
	public NewbieGuide2(Activity activity,boolean isHavaPrice){
		showDialog(activity,isHavaPrice).show();
	}
	private Dialog showDialog(Activity context,final boolean isHavaPrice){
		final SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(context);
		final Dialog dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.choose_dialog_2, null);
		final ImageView guide1 = (ImageView) view.findViewById(R.id.guide1);
				guide1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						guide1.setVisibility(View.GONE);
					}
				});
				
				final ImageView guide2 = (ImageView) view.findViewById(R.id.guide2);
				guide2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						guide2.setVisibility(View.GONE);
					}
				});
				final ImageView guide3 = (ImageView) view.findViewById(R.id.guide3);
				guide3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						guide3.setVisibility(View.GONE);
					}
				});
				final ImageView guide4 = (ImageView) view.findViewById(R.id.guide4);
				guide4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(isHavaPrice){
							preferenceUtil.setGuide7();
							guide4.setVisibility(View.GONE);
						}else{
							dialog.dismiss();
						}
					}
				});
				final ImageView guide5 = (ImageView) view.findViewById(R.id.guide5);
				guide5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		
		dialog.setContentView(view);
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		Window window = dialog.getWindow();
		//设置显示动画  
		window.setWindowAnimations(R.anim.dialog_open);  
		WindowManager.LayoutParams wl = window.getAttributes();  
		wl.width = screenWidth;
		wl.height = screenHeight;
		
		//设置显示位置  
		dialog.onWindowAttributesChanged(wl);
		//设置点击外围解散  
		dialog.setCanceledOnTouchOutside(false); 
		return dialog;
	}
}
