package com.futureinst.utils;

import com.futureinst.R;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChooseSexUtil {
	//0表示未知、1表示男、2表示女
	public static void chooseSex(Activity activity,final TextView tv_sex){
		View  view = LayoutInflater.from(activity).inflate(R.layout.view_choose_sex, null, false);
		final Dialog dialog = DialogShow.showDialog(activity, view,Gravity.BOTTOM);
		TextView tv_male = (TextView) view.findViewById(R.id.tv_male);
		TextView tv_female = (TextView) view.findViewById(R.id.tv_female);
		TextView tv_guess = (TextView) view.findViewById(R.id.tv_guess);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		OnClickListener clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_male:
					tv_sex.setText("男");
					tv_sex.setTag("1");
					break;
				case R.id.tv_female:
					tv_sex.setText("女");
					tv_sex.setTag("2");
					break;
				case R.id.tv_guess:
					tv_sex.setText("未知");
					tv_sex.setTag("0");
					break;
				case R.id.tv_cancel:
					break;
				}
				dialog.dismiss();
			}
		};
		tv_male.setOnClickListener(clickListener);
		tv_female.setOnClickListener(clickListener);
		tv_guess.setOnClickListener(clickListener);
		tv_cancel.setOnClickListener(clickListener);
		dialog.show();
	}
}
