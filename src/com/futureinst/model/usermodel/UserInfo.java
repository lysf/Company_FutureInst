package com.futureinst.model.usermodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class UserInfo extends BaseModel {
	private UserDAO user;

	public UserDAO getUser() {
		return user;
	}

	public void setUser(UserDAO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserInfo [user=" + user + "]";
	}
}
