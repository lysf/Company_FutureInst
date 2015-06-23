package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInformationDAO implements Serializable{
	private Long userId;
	private float allGain;
	private float avgGain;
	private int allEvent;
	private int gainEvent;
	private int foreIndex;
	private int rank;
	private int lastRank;
	private float asset;
	private float assure;
	private int status;
	private UserDAO user;
	
	public int getLastRank() {
		return lastRank;
	}
	public void setLastRank(int lastRank) {
		this.lastRank = lastRank;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public float getAllGain() {
		return allGain;
	}
	public void setAllGain(float allGain) {
		this.allGain = allGain;
	}
	public float getAvgGain() {
		return avgGain;
	}
	public void setAvgGain(float avgGain) {
		this.avgGain = avgGain;
	}
	public int getAllEvent() {
		return allEvent;
	}
	public void setAllEvent(int allEvent) {
		this.allEvent = allEvent;
	}
	public int getGainEvent() {
		return gainEvent;
	}
	public void setGainEvent(int gainEvent) {
		this.gainEvent = gainEvent;
	}
	public int getForeIndex() {
		return foreIndex;
	}
	public void setForeIndex(int foreIndex) {
		this.foreIndex = foreIndex;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public float getAsset() {
		return asset;
	}
	public void setAsset(float asset) {
		this.asset = asset;
	}
	public float getAssure() {
		return assure;
	}
	public void setAssure(float assure) {
		this.assure = assure;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserDAO getUser() {
		return user;
	}
	public void setUser(UserDAO user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserInformationDAO [userId=" + userId + ", allGain=" + allGain
				+ ", avgGain=" + avgGain + ", allEvent=" + allEvent
				+ ", gainEvent=" + gainEvent + ", foreIndex=" + foreIndex
				+ ", rank=" + rank + ", asset=" + asset + ", assure=" + assure
				+ ", status=" + status + ", user=" + user + "]";
	}
}
