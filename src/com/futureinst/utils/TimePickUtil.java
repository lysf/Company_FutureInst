package com.futureinst.utils;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.widget.wheelview.ScreenInfo;
import com.futureinst.widget.wheelview.WheelMain;

public class TimePickUtil {
	public static void timePick(Activity activity,TextView timeView){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		LayoutInflater inflater = LayoutInflater
				.from(activity);
		final View timepickerview = inflater.inflate(
				R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(activity);
		WheelMain wheelMain = new WheelMain(timepickerview, 1,timeView);
		wheelMain.screenheight = screenInfo.getHeight();
		wheelMain.initDateTimePicker(year, month, day, hour, min);
		Dialog dialog = DialogShow.showDialog(activity, timepickerview,Gravity.BOTTOM);
		
		dialog.show();
	}
}
