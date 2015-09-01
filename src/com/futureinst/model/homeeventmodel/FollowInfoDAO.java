package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class FollowInfoDAO extends BaseModel {
	private FollowDAO follow;

	public FollowDAO getFollow() {
		return follow;
	}

	public void setFollow(FollowDAO follow) {
		this.follow = follow;
	}
}
