package com.futureinst.service;


import com.futureinst.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UpdateDialogShow {
	public static Dialog showDialog(Context context,View view,int screenWidth,
	int screenHeigh){
		Dialog dialog = new Dialog(context,R.style.choose_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		//设置显示动画  
		window.setWindowAnimations(R.anim.dialog_open);  
		WindowManager.LayoutParams wl = window.getAttributes();  
		wl.width = screenWidth * 7 / 10;
		wl.gravity = Gravity.CENTER;
		
		//设置显示位置  
		dialog.onWindowAttributesChanged(wl);//设置点击外围解散  
		dialog.setCanceledOnTouchOutside(true); 
		
//		WindowManager.LayoutParams lp = window.getAttributes();
//		lp.height = (screenWidth*4/7);
		window.setAttributes(wl);
		dialog.show();
		return dialog;
	}
}
