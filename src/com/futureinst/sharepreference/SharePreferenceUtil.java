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
	private String FOLLOW = "FOLLOW";//关注我的人数
	private String AD = "AD";//记录广告显示的时间
	private String INSTALLTAG = "INSTALLTAG";//安装标记
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
	private String ISUPDATEVERSION = "ISUPDATEVERSION";//是否在下载新版本
	private String UPDATE = "UPDATE";//是否第一次登陆
	private String ASSET = "ASSET";//可交易未币
	private String ASSURE = "ASSURE";//保证金;
	private String EXCHANGE = "EXCHANGE";//可消费未币
	private String GUIDE_1 = "GUIDE_1";
	private String GUIDE_2 = "GUIDE_2";
	private String GUIDE_3 = "GUIDE_3";
	private String GUIDE_4 = "GUIDE_4";
	private String GUIDE_5 = "GUIDE_5";
	private String GUIDE_6 = "GUIDE_6";
	private String GUIDE_7 = "GUIDE_7";
	private String GUIDE_8 = "GUIDE_8";
	
	private String CID = "CID";
	private String CTYPE = "CTYPE";
	private String STATUS = "STATUS";
	//关注我的人
	public void setFollow(int follow){
		editor.putInt(FOLLOW, follow);
		editor.commit();
	}
	public int getFollow(){
		return mSharedPreferences.getInt(FOLLOW, 0);
	}
	//记录广告显示时间
	public void setADTime(String time){
		editor.putString(AD, time);
		editor.commit();
	}
	public String getADTime(){
		return mSharedPreferences.getString(AD, "1");
	}
	//安装标记
	public boolean getInstallTag(){
		return mSharedPreferences.getBoolean(INSTALLTAG, false);
	}
	//设置已安装
	public void setInstallTag(){
		editor.putBoolean(INSTALLTAG, true);
		editor.commit();
	}
	//余额
	public float getAsset(){
		return mSharedPreferences.getFloat(ASSET, 0f);
	}
	public void setAsset(float asset){
		editor.putFloat(ASSET, asset);
		editor.commit();
	}
	//保证金
	public float getAssure(){
		return mSharedPreferences.getFloat(ASSURE, 0f);
	}
	public void setAssure(float assure){
		editor.putFloat(ASSURE, assure);
		editor.commit();
	}
	public boolean getGuide1(){
		return mSharedPreferences.getBoolean(GUIDE_1, false);
	}
	public void setGuide1(){
		editor.putBoolean(GUIDE_1, true);
		editor.commit();
	}
	public boolean getIsUpdateVersion(){
		return mSharedPreferences.getBoolean(ISUPDATEVERSION, false);
	}
	public void setIsUpdateVersion(boolean isUpdateVersion){
		editor.putBoolean(ISUPDATEVERSION, isUpdateVersion);
		editor.commit();
	}
	public boolean getGuide2(){
		return mSharedPreferences.getBoolean(GUIDE_2, false);
	}
	public void setGuide2(){
		editor.putBoolean(GUIDE_2, true);
		editor.commit();
	}
	public boolean getGuide3(){
		return mSharedPreferences.getBoolean(GUIDE_3, false);
	}
	public void setGuide3(){
		editor.putBoolean(GUIDE_3, true);
		editor.commit();
	}
	public boolean getGuide4(){
		return mSharedPreferences.getBoolean(GUIDE_4, false);
	}
	public void setGuide4(){
		editor.putBoolean(GUIDE_4, true);
		editor.commit();
	}
	public boolean getGuide5(){
		return mSharedPreferences.getBoolean(GUIDE_5, false);
	}
	public void setGuide5(){
		editor.putBoolean(GUIDE_5, true);
		editor.commit();
	}
	public boolean getGuide6(){
		return mSharedPreferences.getBoolean(GUIDE_6, false);
	}
	public void setGuide6(){
		editor.putBoolean(GUIDE_6, true);
		editor.commit();
	}
	public boolean getGuide7(){
		return mSharedPreferences.getBoolean(GUIDE_7, false);
	}
	public void setGuide7(){
		editor.putBoolean(GUIDE_7, true);
		editor.commit();
	}
	public boolean getGuide8(){
		return mSharedPreferences.getBoolean(GUIDE_8, false);
	}
	public void setGuide8(){
		editor.putBoolean(GUIDE_8, true);
		editor.commit();
	}
	
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
	
