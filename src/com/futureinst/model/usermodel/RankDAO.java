package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RankDAO implements Serializable{
	private String name;
	private int foreIndex;
	private int currRank;
	private int lastRank;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getForeIndex() {
		return foreIndex;
	}
	public void setForeIndex(int foreIndex) {
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
