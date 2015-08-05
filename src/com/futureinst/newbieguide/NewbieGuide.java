package com.futureinst.newbieguide;

import com.futureinst.R;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class NewbieGuide {
	public NewbieGuide(Activity activity,int drawable,GuideClickInterface guideClickInterface){
		showDialog(activity, drawable, guideClickInterface).show();
	}
	private Dialog showDialog(Activity context,int drawable,final GuideClickInterface clickInterface){
		final Dialog dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_guide, null);
		ImageView iv_guide = (ImageView) view.findViewById(R.id.iv_guide);
		iv_guide.setImageDrawable(context.getResources().getDrawable(drawable));
		iv_guide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				clickInterface.guideClick();
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
