package com.futureinst.login;

import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.content.Context;

public class SaveUserInfo {
	public static void saveUserInfo(Context context,UserDAO user){
		SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(context);
		preferenceUtil.setID(user.getId());
		preferenceUtil.setUUid(user.getUuid());
		preferenceUtil.setName(user.getName());
		preferenceUtil.setGender(user.getGender());
		preferenceUtil.setHeadImage(user.getHeadImage());
		preferenceUtil.setdescription(user.getDescription());
		preferenceUtil.setCID(user.getCid());
		preferenceUtil.setCType(user.getCtype());
		preferenceUtil.setStatus(user.getStatus());
	}
}
