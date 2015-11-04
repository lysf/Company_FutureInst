package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RankDAO implements Serializable{
	private long user_id;
	private String user_head_image;
	private String name;
	private float foreIndex;
	private float foreIndexNew;
	private int currRank;
	private int lastRank;

	public float getForeIndexNew() {
		return foreIndexNew;
	}

	public void setForeIndexNew(float foreIndexNew) {
		this.foreIndexNew = foreIndexNew;
	}

	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUser_head_image() {
		return user_head_image;
	}
	public void setUser_head_image(String user_head_image) {
		this.user_head_image = user_head_image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getForeIndex() {
		return foreIndex;
	}
	public void setForeIndex(float foreIndex) {
		this.foreIndex = foreIndex;
	}
	public int getCurrRank() {
		return currRank;
	}
	public void setCurrRank(int currRank) {
		this.currRank = currRank;
	}
	public int getLastRank() {
		return lastRank;
	}
	public void setLastRank(int lastRank) {
		this.lastRank = lastRank;
	}
	@Override
	public String toString() {
		return "RankDAO [name=" + name + ", foreIndex=" + foreIndex
				+ ", currRank=" + currRank + ", lastRank=" + lastRank + "]";
	}
}
