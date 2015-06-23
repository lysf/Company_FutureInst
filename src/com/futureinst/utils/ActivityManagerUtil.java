package com.futureinst.utils;

import java.util.ArrayList;

import android.app.Activity;

public class ActivityManagerUtil {
	
	public static ArrayList<Activity> arrayList = new ArrayList<Activity>();
	public static ArrayList<Activity> labelList = new ArrayList<Activity>();
	
	/**
	 * 当进入一个新的activity的时候便把这个activity放入ArrayList中
	 * @param activity
	 */
	public static void addActivity(Activity activity){
		if(!arrayList.contains(activity)){
			arrayList.add(activity);
		}
	}
	/**
	 * 结束arrayList中的所有的activity
	 */
	public static void finishActivity(){
		Activity activity = null;
		int length = arrayList.size() - 1;
		for(int i = length; i >= 0; i--){
			activity = arrayList.get(i);
			if(activity != null){
				activity.finish();
				arrayList.remove(i);
			}
		}
	}
	/**
	 * 结束arrayList中的所有的activity(除第一个外)
	 */
	public static void finishOthersActivity(){
		Activity activity = null;
		int length = arrayList.size() - 1;
		for(int i = length; i > 0; i--){
			activity = arrayList.get(i);
			if(activity != null){
				activity.finish();
				arrayList.remove(i);
			}
		}
	}
	
	/**
	 * 结束arrayList中的除给定的activity外的所有
	 */
	public static void finishOthersActivity(Activity act){
		Activity activity = null;
		int length = arrayList.size() - 1;
		for(int i = length; i > 0; i--){
			activity = arrayList.get(i);
			if(activity != null && activity != act){
				activity.finish();
				arrayList.remove(i);
			}
		}
	}
	/**
	 * 结束arrayList中已有的指定activity
	 * @param activity
	 */
	public static void finishOtherZctivity(Activity activity){
		Activity act = null;
		int length = arrayList.size() - 1;
		for(int i = length; i >= 0; i--){
			act = arrayList.get(i);
			if(act != null && act == activity){
				act.finish();
				arrayList.remove(i);
			}
		}
	}
	
	public static void addLabelActivity(Activity activity){
		if(!labelList.contains(activity)){
			labelList.add(activity);
		}
	}
	
	public static ArrayList<Activity> getLabelActivity(){
		return ActivityManagerUtil.labelList;
	}
	
	public static void RemoveLabelActivity(Activity activity){
		if(labelList.contains(activity)){
			labelList.remove(activity);
		}
	}
	
	
	
}
