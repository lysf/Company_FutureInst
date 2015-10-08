package com.futureinst.model.usermodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.record.UserRecordDAO;

@SuppressWarnings("serial")
public class FollowInfoDAO extends BaseModel {
	private List<UserRecordDAO> follows;
	private List<UserRecordDAO> friends;

	public List<UserRecordDAO> getFollows() {
		return follows;
	}

	public void setFollows(List<UserRecordDAO> follows) {
		this.follows = follows;
	}

	public List<UserRecordDAO> getFriends() {
		return friends;
	}

	public void setFriends(List<UserRecordDAO> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "FollowInfoDAO [follows=" + follows + ", friends=" + friends + "]";
	}
	
	
	
}
