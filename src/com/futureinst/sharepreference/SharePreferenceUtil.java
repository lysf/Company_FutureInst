package com.futureinst.sharepreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class SharePreferenceUtil {
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor editor;
	private static SharePreferenceUtil preferenceUtil;

	private SharePreferenceUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences("futureInst",
				Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static SharePreferenceUtil getInstance(Context context) {
		if (preferenceUtil == null) {
			preferenceUtil = new SharePreferenceUtil(context);
		}
		return preferenceUtil;
	}

	private String MOBILEPHONE = "MOBILEPHONE";// 手机号
	private String PASSWORD = "PASSWORD";// 密码
	private String UUID = "UUID";// 用户标识
	private String ID = "ID";// 用户id
	private String NAME = "NAME";// 用户名称
	private String GENDER = "GENDER";// 用户性别
	private String HEADIMAGE = "HEADIMAGE";// 用户头像
	private String DESCRIPTION = "DESCRIPTION";// 用户描述
	private String CLIENTID = "CLIENTID";//推送的cid
	private String ISFIRSTLOGIN = "ISFIRSTLOGIN";//是否第一次登陆
	private String UPDATE = "UPDATE";//是否第一次登陆
	
	private String CID = "CID";
	private String CTYPE = "CTYPE";
	private String STATUS = "STATUS";
	
	public boolean isFirstLogin(){
		return mSharedPreferences.getBoolean(ISFIRSTLOGIN, true);
	}
	public void setUnIsFirstLogin(){
		editor.putBoolean(ISFIRSTLOGIN, false);
		editor.commit();
	}
	//推送的cid
	public String getCLIENTID() {
		return mSharedPreferences.getString(CLIENTID, null);
	}
	public void setCLIENTID(String cid) {
		editor.putString(CLIENTID, cid);
		editor.commit();
	}
	// 手机号
	public String getMOBILEPHONE() {
		return mSharedPreferences.getString(MOBILEPHONE, null);
	}
	public void setMOBILEPHONE(String mOBILEPHONE) {
		editor.putString(MOBILEPHONE, mOBILEPHONE);
		editor.commit();
	}
	// 密码
	public String getPASSWORD() {
		return mSharedPreferences.getString(PASSWORD, null);
	}
	public void setPASSWORD(String pASSWORD) {
		editor.putString(PASSWORD, pASSWORD);
		editor.commit();
	}
	// uuid
	public String getUUid() {
		return mSharedPreferences.getString(UUID, null);
	}
	public void setUUid(String uuid) {
		editor.putString(UUID, uuid);
		editor.commit();
	}
	// id
	public Long getID() {
		return mSharedPreferences.getLong(ID, 0);
	}
	public void setID(Long id) {
		editor.putLong(ID, id);
		editor.commit();
	}
	// name
	public String getName() {
		return mSharedPreferences.getString(NAME, null);
	}
	public void setName(String name) {
		editor.putString(NAME, name);
		editor.commit();
	}
	//gender
	public int getGender(){
		return mSharedPreferences.getInt(GENDER, 0);
	}
	public void setGender(int gender){
		editor.putInt(GENDER, gender);
		editor.commit();
	}
	//headImage
	public String getHeadImage(){
		return mSharedPreferences.getString(HEADIMAGE, "");
	}
	public void setHeadImage(String headImage){
		editor.putString(HEADIMAGE, headImage);
		editor.commit();
	}
	//description
	public String getDescription(){
		return mSharedPreferences.getString(DESCRIPTION, "");
	}
	public void setdescription(String description){
		editor.putString(DESCRIPTION, description);
		editor.commit();
	}
	//cId
	public String getCID(){
		return mSharedPreferences.getString(CID, "");
	}
	public void setCID(String cid){
		editor.putString(CID, cid);
		editor.commit();
	}
	//cType
	public String getCType(){
		return mSharedPreferences.getString(CTYPE, "");
	}
	public void setCType(String cType){
		editor.putString(CTYPE, cType);
		editor.commit();
	}
	//status
	public String getStatus(){
		return mSharedPreferences.getString(STATUS, "");
	}
	public void setStatus(String status){
		editor.putString(STATUS, status);
		editor.commit();
	}
	//update
	public boolean getUpdate(){
		return mSharedPreferences.getBoolean(UPDATE, false);
	}
	public void setUpdate(boolean upDate) {
		editor.putBoolean(UPDATE, upDate);
		editor.commit();
	}
}
	
