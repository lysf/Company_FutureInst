package com.futureinst.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.futureinst.R;



public class MyProgressDialog {
	private  Dialog mProgressDialog;
	private static MyProgressDialog progressDialog;
	private MyProgressDialog(Context context) {
		super();
		mProgressDialog = new Dialog(context,R.style.theme_dialog_alert);
		mProgressDialog.setContentView(R.layout.window_layout);
	}
	public static MyProgressDialog getInstance(Context context){
		progressDialog = new MyProgressDialog(context);
		return progressDialog;
	}
	public  void progressDialog(){
		mProgressDialog
		.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
//										((Activity) context).finish();
			}
		});
		mProgressDialog.setCancelable(false);
		try {
			mProgressDialog.show();
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	public  void cancleProgress(){
		if (mProgressDialog!=null&&mProgressDialog.isShowing()) {  
			mProgressDialog.dismiss();  
		}
	}
	public boolean getIsShow(){
        return mProgressDialog.isShowing();
    }
}

