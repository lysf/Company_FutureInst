package com.futureinst.model.attention;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class AttentionInfoDAO extends BaseModel {
	private List<AttentionDAO> follows;

	public List<AttentionDAO> getFollows() {
		return follows;
	}

	public void setFollows(List<AttentionDAO> follows) {
		this.follows = follows;
	}

	@Override
	public String toString() {
		return "AttentionInfoDAO [follows=" + follows + "]";
	}
}
