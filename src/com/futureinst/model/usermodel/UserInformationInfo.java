package com.futureinst.model.usermodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class UserInformationInfo extends BaseModel {
	private UserInformationDAO user_record;

	public UserInformationDAO getUser_record() {
		return user_record;
	}

	public void setUser_record(UserInformationDAO user_record) {
		this.user_record = user_record;
	}

	@Override
	public String toString() {
		return "UserInformationInfo [user_record=" + user_record + "]";
	}
}
