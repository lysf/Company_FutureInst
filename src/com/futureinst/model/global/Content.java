package com.futureinst.model.global;

import android.os.Environment;

public class Content {
	public static String DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FutureInst/";
	public static String UPDATE_URL = "";
	public static String PICTURE_PATH = DIR + "image/";
	public static String CAMERA_PATH = ""; 
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static boolean isPull = false;
}
