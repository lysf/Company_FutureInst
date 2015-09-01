package com.futureinst.utils;

import java.util.HashMap;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;

public class CustomStatUtil {
	/**
	 * 
	 * @Title: onEvent   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param context
	 * @param: @param id 事件ID
	 * @param: @param m 当前事件的属性和取值
	 * @param: @param value      
	 * @return: void      
	 * @throws
	 */
	public static void onEvent(Context context, String id, HashMap<String,String> m, int value){
	    m.put("__ct__", String.valueOf(value));
	    MobclickAgent.onEvent(context, id, m);
	}
}
