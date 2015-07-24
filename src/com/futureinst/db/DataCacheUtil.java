package com.futureinst.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DataCacheUtil {
	private DBManager dbManager;
	private static DataCacheUtil dataCacheUtil;
	private DataCacheUtil(Context context){
		dbManager = DBManager.getInstances(context);
	}
	public static DataCacheUtil getInstance(Context context){
		if(dataCacheUtil == null){
			dataCacheUtil = new DataCacheUtil(context);
		}
		return dataCacheUtil;
	}
	//获取缓存数据
	public String getCache(String path){
		String response = null;
		Cursor cursor = dbManager.querry("datacache", null, "path = ?",
				new String[] { path }, null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			response = cursor.getString(cursor
					.getColumnIndex("json"));
			
		}
		cursor.close();
		return response;
	}
	public void addOrReplaCecache(String path,String response){
		Cursor cursor = null;
		cursor= dbManager.querry("datacache", null, "path = ?", new String[]{path}, null, null, null, null);
		ContentValues values = new ContentValues();
		values.put("path", path);
		values.put("json", response);
		if(cursor != null && cursor.getCount() > 0){
			dbManager.replace("datacache", null, values);
		}else{
			dbManager.insertSQLite("datacache", null,
					values);
		}
	}
}
