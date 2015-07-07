package com.futureinst.utils;

import com.futureinst.R;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ShowPicDialog {
	//头像
		public static void showPicDialog(final Activity activity){
			View view = LayoutInflater.from(activity).inflate(R.layout.view_pic_dialog, null, false);
			final Dialog dialog = DialogShow.showDialog(activity, view,Gravity.BOTTOM);
			Button btn_camera = (Button) view.findViewById(R.id.btn_camera);
			Button btn_photo = (Button) view.findViewById(R.id.btn_photo);
			Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
			btn_camera.setOnClickListener(new OnClickListener() {//相机
				@Override
				public void onClick(View v) {
					ImageCompressUtil.selectImageFromCamera(activity);
					dialog.dismiss();
				}
			});
			btn_photo.setOnClickListener(new OnClickListener() {//相机
				@Override
				public void onClick(View v) {
					ImageCompressUtil.selectImageFromLocal(activity);
					dialog.dismiss();
				}
			});
			btn_cancel.setOnClickListener(new OnClickListener() {//取消
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
}
