package com.futureinst.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "futureinst.db";
	private static final int VERSION = 1;

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建缓存数据表
		db.execSQL("CREATE TABLE IF NOT EXISTS datacache (id integer primary key autoincrement, path varchar(100) unique, json varchar(500))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS datacache");
		onCreate(db);
	}
}
