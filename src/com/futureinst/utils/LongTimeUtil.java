package com.futureinst.utils;

public class LongTimeUtil {
	// 根据毫秒求出时间
	public static String longTimeUtil(Long longTime) {
		Long second = longTime/1000;
		String result = "";
		
		int days = (int) (second / (60*60*24));
		int hours = (int) ((second - days*(60*60*24))/(60*60));
		int minus = (int) ((second - days*(60*60*24) - hours*(60*60))/60);
		int seconds = (int) ((second - days*(60*60*24) - hours*(60*60) - minus*60));
		
//		 days + "  |  " + String.format("%02d", hours) + ":"
//				+ String.format("%02d", minus) + ":"
//				+ String.format("%02d", seconds);
		 if(days > 0) result = days+"天"+hours+"时";
		 else if(hours > 0) result = hours+"时"+minus+"分";
		 else if(minus > 0) result = minus + "分"+seconds+"秒";
		 else if(seconds > 0)result = seconds+"秒";
		 else result = "0秒";
		 return result;
	}
}
