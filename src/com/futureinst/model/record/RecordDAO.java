package com.futureinst.model.record;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RecordDAO implements Serializable {
	private Long userId;
	private int tag;
	private float allGain;
	private float avgGain;
	private int allEvent;
	private int gainEvent;
	private Long rank;
	private Long lastRank;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
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
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Long getLastRank() {
		return lastRank;
	}
	public void setLastRank(Long lastRank) {
		this.lastRank = lastRank;
	}
	@Override
	public String toString() {
		return "RecordDAO [userId=" + userId + ", tag=" + tag + ", allGain=" + allGain + ", avgGain=" + avgGain
				+ ", allEvent=" + allEvent + ", gainEvent=" + gainEvent + ", rank=" + rank + ", lastRank=" + lastRank
				+ "]";
	}
}
