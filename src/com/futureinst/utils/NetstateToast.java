package com.futureinst.utils;

import com.futureinst.R;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class NetstateToast {
	private Activity activity;
	private String message;
	private Toast toast;
	private static NetstateToast netstateToast;
	private NetstateToast(Activity activity,String message){
		this.message = message;
		this.activity = activity;
		toast = new Toast(activity);
	}
	public static NetstateToast getInstance(Activity activity,String message){
		if(netstateToast == null){
			netstateToast = new NetstateToast(activity, message);
		}
		return netstateToast;
	}
	public void showToast(){
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
//	    if(style == 0){//红色
	    	tv.setBackgroundColor(activity.getResources().getColor(R.color.redLight));
//	    }else{
//	    	tv.setBackgroundColor(activity.getResources().getColor(R.color.greenLight));
//	    }
//	    Toast toast = new Toast(activity);
//	    float hOffset = activity.getResources().getDimension(R.dimen.widget_height_35);
	    toast.setGravity(Gravity.TOP, 0, 0);
	    toast.setDuration(Toast.LENGTH_SHORT);
	    toast.setView(toastView);
	    toast.show();
	}
}
