package com.futureinst.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBManager dbManager;
	private DBOpenHelper dbHelper;

	public DBManager(Context context) {
		dbHelper = new DBOpenHelper(context);
	}

	public static DBManager getInstances(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		return dbManager;
	}

	// 插入数据库
	public synchronized boolean insertSQLite(String tableName,
			String nullColumnHack, ContentValues values) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			long raw = database.insert(tableName, nullColumnHack, values);
			if (raw > 0) {
				database.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			database.endTransaction();
		}
		return false;
	}

	// 插入和替换数据库
	public synchronized boolean replace(String tableName,
			String nullColumnHack, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long raw = db.replace(tableName, nullColumnHack, values);
		if (raw > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 查找数据
	public synchronized Cursor querry(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		cursor = db.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
		return cursor;
	}

	// 删除数据
	public synchronized int delete(String table, String whereClause, String[] whereArgs) {
		int count = 0;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		count = db.delete(table, whereClause, whereArgs);
		return count;
	}

	// 修改数据
	public synchronized int updata(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		int count = 0;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		count = db.update(table, values, whereClause, whereArgs);
		return count;
	}
}
