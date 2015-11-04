package com.futureinst.global;

import android.os.Environment;

public class Content {
	public static String DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FutureInst/";
	public static String UPDATE_URL = "";
	public static String PICTURE_PATH = DIR + "image/";
	public static String CAMERA_PATH = ""; 
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static boolean isPull = false;
	public static boolean is_aoto_refresh_event_price = false;
	public static boolean disable_app_sign_in = true;
	public static int aoto_refresh_event_price_interval = 5;
	public static int main_list_fresh_interval = 30;
	public static int statusHeight = 20;

    public static String DEVICE_ID ="";

}
