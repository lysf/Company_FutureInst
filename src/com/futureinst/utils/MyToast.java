package com.futureinst.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.futureinst.R;

public class MyToast {
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
	public static void showToast(Activity activity,String message,int style){
		DisplayMetrics dm = new DisplayMetrics();
	    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	   int mScreenWidth = dm.widthPixels;
	   int mScreenHeight = dm.heightPixels;
	   LayoutParams params = null;
	   View toastView = null;
	   if (toastView == null) {
	        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        params = new LinearLayout.LayoutParams(mScreenWidth, ViewGroup.LayoutParams.MATCH_PARENT);
	        toastView = inflater.inflate(R.layout.toast_custom_prompt, null);
	    }
	    TextView tv = (TextView) toastView.findViewById(R.id.tvTitleToast);
	    tv.setLayoutParams(params);
	    tv.setText(message);
	    if(style == 0){//红色
	    	tv.setBackgroundColor(activity.getResources().getColor(R.color.redLight));
	    }else{
	    	tv.setBackgroundColor(activity.getResources().getColor(R.color.greenLight));
	    }
	    Toast toast = new Toast(activity);
//	    float hOffset = activity.getResources().getDimension(R.dimen.widget_height_35);
	    toast.setGravity(Gravity.TOP, 0, 0);
	    toast.setDuration(Toast.LENGTH_SHORT);
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
