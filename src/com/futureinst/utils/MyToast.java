package com.futureinst.utils;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.utils.toast.ToastHelper;

import java.util.logging.Handler;

public class MyToast {
	private static MyToast myToast;
	private ToastHelper toast;
	public static MyToast getInstance(){
		if(myToast == null){
			myToast = new MyToast();

		}
		return myToast;
	}

	/**
	 * 
	 * @Title: showToast   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param activity
	 * @param: @param message
	 * @param: @param style      0:红色；1
	 * @return: void      
	 * @throws
	 */
	public  void showToast(Activity activity,String message,int style){
		DisplayMetrics dm = new DisplayMetrics();
	    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	   int mScreenWidth = dm.widthPixels;
	   int mScreenHeight = dm.heightPixels;
	   LayoutParams params = null;
	   View toastView = null;
	   if (toastView == null) {
	        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
	        toastView = inflater.inflate(R.layout.toast_custom_prompt, null);
	    }
	    TextView tv = (TextView) toastView.findViewById(R.id.tvTitleToast);
		ImageView iv_toast = (ImageView)toastView.findViewById(R.id.iv_tag);
	    tv.setLayoutParams(params);
	    tv.setText(message);
	    if(style == 0){//红色
			iv_toast.setImageDrawable(activity.getResources().getDrawable(R.drawable.toast_bad));
	    }else{
			iv_toast.setImageDrawable(activity.getResources().getDrawable(R.drawable.toast_ok));
	    }
		if(toast != null){
			toast.removeView();
			toast = null;
//            return;
		}
		toast = new ToastHelper(activity);
//	    float hOffset = activity.getResources().getDimension(R.dimen.widget_height_35);
//	    toast.setGravity(Gravity.CENTER, 0, 0);
	    toast.setDuration(ToastHelper.LENGTH_SHORT);
		toast.setAnimation(R.style.PopToast);
	    toast.setView(toastView);
	    toast.show();
	}
//	private  void calcScreenSize() {
//	    DisplayMetrics dm = new DisplayMetrics();
//	    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//	   int mScreenWidth = dm.widthPixels;d1gh
//	   int mScreenHeight = dm.heightPixels;
//	   activity.mScreenWidth = mScreenWidth;
//	    AppApplication.mScreenHeight = mScreenHeight;
//	}
}
