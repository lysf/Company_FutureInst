package com.futureinst.player.util;

public class PlayTimeUtil {
	public static String getPlayTime(int time){
		String playtime;
		int minute = time/60;
		int second = time%60;
		if(minute<10){
			if(second<10){
				playtime = "0"+String.valueOf(minute)+":"+"0"+String.valueOf(second);
			}else{
				playtime = "0"+String.valueOf(minute)+":"+String.valueOf(second);
			}
		}else{
			if(second<10){
				playtime = String.valueOf(minute)+":"+"0"+String.valueOf(second);
			}else{
				playtime = String.valueOf(minute)+":"+String.valueOf(second);
			}
		}
		return playtime;
	}
}
