package com.futureinst.db;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.home.pushmessage.Category;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PushMessageCacheUtil {
	private DBManager dbManager;
	private SharePreferenceUtil preferenceUtil;
	private static PushMessageCacheUtil messageCacheUtil;
	private PushMessageCacheUtil(Context context) {
		preferenceUtil = SharePreferenceUtil.getInstance(context);
		dbManager = DBManager.getInstances(context);
	}
	public static PushMessageCacheUtil getInstance(Context context){
		if(messageCacheUtil == null){
			messageCacheUtil = new PushMessageCacheUtil(context);
		}
		return messageCacheUtil;
	}

	//获取数据
		public List<PushMessageDAO> getPushMessage(String category){
			String userId = preferenceUtil.getID()+"";
			List<PushMessageDAO> list = new ArrayList<>();
			
			Cursor cursor = dbManager.querry("pushmessage", null, "userid = ? and category = ?",
					new String[] { userId,category }, null, null, null, null);
			while(cursor.moveToNext()){
				PushMessageDAO dao = new PushMessageDAO();
				dao.setId(cursor.getString(cursor.getColumnIndex("id")));
				dao.setEvent_id(cursor.getString(cursor.getColumnIndex("event_id")));
				dao.setTime(Long.valueOf(cursor.getString(cursor.getColumnIndex("time"))));
				dao.setText(cursor.getString(cursor.getColumnIndex("text")));
				dao.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				dao.setHref(cursor.getString(cursor.getColumnIndex("href")));
				dao.setType(cursor.getString(cursor.getColumnIndex("type")));
				dao.setPeer_id(cursor.getString(cursor.getColumnIndex("peer_id")));
                dao.setTarget_url(cursor.getString(cursor.getColumnIndex("target_url")));
                dao.setCategory(cursor.getString(cursor.getColumnIndex("category")));
				if(cursor.getInt(cursor.getColumnIndex("isread")) == 1){
					dao.setRead(true);
				}else{
					dao.setRead(false);
				}
				list.add(0, dao);
			}
			cursor.close();
			return list;
		}
		//添加消息
		public void addPushMessage(PushMessageDAO dao){
			String userId = preferenceUtil.getID()+"";
			ContentValues values = new ContentValues();
			values.put("userid", userId);
			values.put("id", dao.getId());
			values.put("event_id", dao.getEvent_id());
			values.put("time", dao.getTime()+"");
			values.put("text", dao.getText());
			values.put("title", dao.getTitle());
			values.put("href", dao.getHref());
			values.put("type", dao.getType());
			values.put("peer_id", dao.getPeer_id());
			values.put("target_url", dao.getTarget_url());
			values.put("category", dao.getCategory());
			if(dao.isRead()){
				values.put("isread", 1);
			}else{
				values.put("isread", 0);
			}
			
			dbManager.insertSQLite("pushmessage", null,
                    values);
		}
		//设置状态（是否已读）
		public void updateMesaage(String category){
			String userid = preferenceUtil.getID()+"";
			String[] whereArgs = null;
			String whereClause = null;
			if(category == null){
				whereClause = "userid = ? and isread = ? and category = ?";
				whereArgs = new String[]{userid,"0", Category.sys.name()};
			}else{
				whereClause = "userid = ? and isread = ? and category = ?";
				whereArgs = new String[]{userid,"0",category};
			}
			ContentValues values = new ContentValues();
			values.put("isread", 1);
			dbManager.updata("pushmessage", values, whereClause, whereArgs);
		}
		//获取未读消息数量
		public int getUnReadMessage(){
			String userId = preferenceUtil.getID()+"";
			Cursor cursor = dbManager.querry("pushmessage", null, "userid = ? and isread = ?",
					new String[] { userId,"0" }, null, null, null, null);
			return cursor.getCount();
		}
		//获取未读消息数量(分类)(account,表示账户类； fans 粉丝类；interact，点赞评论类；sys 系统类； )
		public int getUnReadMessage_category(String category){
			String userId = preferenceUtil.getID()+"";
			Cursor cursor = dbManager.querry("pushmessage", null, "userid = ? and isread = ? and category = ?",
					new String[] { userId,"0",category}, null, null, null, null);
			return cursor.getCount();
		}

}
