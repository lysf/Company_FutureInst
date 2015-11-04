package com.futureinst.model.usermodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class RankInfo extends BaseModel {
	private int size;
	private List<RankDAO> ranks;
	private List<String> follows;
	private List<String> friends;

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getFollows() {
		return follows;
	}
	public void setFollows(List<String> follows) {
		this.follows = follows;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<RankDAO> getRanks() {
		return ranks;
	}
	public void setRanks(List<RankDAO> ranks) {
		this.ranks = ranks;
	}
	@Override
	public String toString() {
		return "RankInfo [size=" + size + ", ranks=" + ranks + "]";
	}
	
}
