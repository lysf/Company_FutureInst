package com.futureinst.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ProgressBar;

import com.futureinst.R;



public class MyProgressDialog {
	private  Dialog mProgressDialog;
	private static MyProgressDialog progressDialog;
	private MyProgressDialog(Context context) {
		super();
		mProgressDialog = new Dialog(context,R.style.theme_dialog_alert);
		mProgressDialog.setContentView(R.layout.window_layout);
		mProgressDialog.setCancelable(true);
	}
	public static MyProgressDialog getInstance(Context context){
		progressDialog = new MyProgressDialog(context);
		return progressDialog;
	}
	public  void progressDialog(){
		mProgressDialog
		.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				//						((Object) context).finish();
			}
		});
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	public  void cancleProgress(){
		if (mProgressDialog!=null&&mProgressDialog.isShowing()) {  
			mProgressDialog.dismiss();  
		}
	}
	public boolean getIsShow(){
		if(mProgressDialog.isShowing()){
			return true;
		}
		return false;
	}
}

