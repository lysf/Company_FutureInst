package com.futureinst.home.attention;

import java.util.HashMap;

import android.os.CountDownTimer;

/**
 * 保存数据
 * 
 * @author admin
 * 
 */
public class DataSaver {
	private HashMap<String, Long> mRemainTimeMap = new HashMap<String, Long>();

	private HashMap<String, CountDownTimer> mCountDownTimerMap = new HashMap<String, CountDownTimer>();

	public HashMap<String, Long> getmRemainTimeMap() {
		return mRemainTimeMap;
	}

	public void setmRemainTimeMap(HashMap<String, Long> mRemainTimeMap) {
		this.mRemainTimeMap = mRemainTimeMap;
	}

	public HashMap<String, CountDownTimer> getmCountDownTimerMap() {
		return mCountDownTimerMap;
	}

	public void setmCountDownTimerMap(HashMap<String, CountDownTimer> mCountDownTimerMap) {
		this.mCountDownTimerMap = mCountDownTimerMap;
	}
	
}
