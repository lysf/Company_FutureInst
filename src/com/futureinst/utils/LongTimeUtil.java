package com.futureinst.utils;

public class LongTimeUtil {
	// 根据毫秒求出时间
	public static String longTimeUtil(Long longTime) {
		Long second = longTime/1000;
		
		
		int days = (int) (second / (60*60*24));
		int hours = (int) ((second - days*(60*60*24))/(60*60));
		int minus = (int) ((second - days*(60*60*24) - hours*(60*60))/60);
		int seconds = (int) ((second - days*(60*60*24) - hours*(60*60) - minus*60));
		
		return days + "  |  " + String.format("%02d", hours) + ":"
				+ String.format("%02d", minus) + ":"
				+ String.format("%02d", seconds);
	}
}
