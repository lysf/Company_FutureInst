package com.futureinst.model.record;

import java.io.Serializable;

import com.futureinst.model.usermodel.UserDAO;

@SuppressWarnings("serial")
public class UserRecordDAO implements Serializable {
	private Long userId;
	private float allGain;
	private float avgGain;
	private int allEvent;
	private int gainEvent;
	private float foreIndex;
	private int rank;
	private int lastRank;
	private float asset;
	private float assure;
	private float exchange;
	private int status;
	private int articleNum;
	private int articleLoveNum;
	private int articleAward;
	private int articleAwardNum;
	private int articleReadNum;
	
	private UserDAO user;

	public int getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}

	public int getArticleLoveNum() {
		return articleLoveNum;
	}

	public void setArticleLoveNum(int articleLoveNum) {
		this.articleLoveNum = articleLoveNum;
	}

	public int getArticleAward() {
		return articleAward;
	}

	public void setArticleAward(int articleAward) {
		this.articleAward = articleAward;
	}

	public int getArticleAwardNum() {
		return articleAwardNum;
	}

	public void setArticleAwardNum(int articleAwardNum) {
		this.articleAwardNum = articleAwardNum;
	}

	public int getArticleReadNum() {
		return articleReadNum;
	}

	public void setArticleReadNum(int articleReadNum) {
		this.articleReadNum = articleReadNum;
	}

	public float getExchange() {
		return exchange;
	}
	public void setExchange(float exchange) {
		this.exchange = exchange;
	}
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
	public float getForeIndex() {
		return foreIndex;
	}
	public void setForeIndex(float foreIndex) {
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
