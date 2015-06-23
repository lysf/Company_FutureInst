package com.futureinst.utils;



import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.futureinst.R;



public class DialogShow {
	public static Dialog showDialog(Activity context,View view,int gravity){
		Dialog dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		wl.gravity = gravity;
		
		//设置显示位置  
		dialog.onWindowAttributesChanged(wl);
		//设置点击外围解散  
		dialog.setCanceledOnTouchOutside(true); 
		
//		WindowManager.LayoutParams lp = window.getAttributes();
//			lp.height = screenHeight*4/7;
//			window.setAttributes(wl);
		return dialog;
	}

}
