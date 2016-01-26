package com.futureinst.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "futureinst.db";
	private static final int VERSION = 4;

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建缓存数据表
		db.execSQL("CREATE TABLE IF NOT EXISTS datacache (id integer primary key autoincrement, path varchar(100) unique, json varchar(500))");
		db.execSQL("CREATE TABLE IF NOT EXISTS pushmessage (_id integer primary key autoincrement, userid varchar(100), id varchar(15),event_id varchar(15),time varchar(15),text varchar(100),title varchar(30),href varchar(30),type varchar(10), isread integer,peer_id varchar(10),category varchar(10),target_url varchar(30))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS datacache");
		db.execSQL("DROP TABLE IF EXISTS pushmessage");
		onCreate(db);
	}
}
