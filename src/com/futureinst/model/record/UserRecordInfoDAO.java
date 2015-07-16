package com.futureinst.model.record;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class UserRecordInfoDAO extends BaseModel {
	private UserRecordDAO user_record;

	public UserRecordDAO getUser_record() {
		return user_record;
	}

	public void setUser_record(UserRecordDAO user_record) {
		this.user_record = user_record;
	}

	@Override
	public String toString() {
		return "UserRecordInfoDAO [user_record=" + user_record + "]";
	}
}
