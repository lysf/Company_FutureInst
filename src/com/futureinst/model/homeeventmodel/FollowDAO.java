package com.futureinst.model.homeeventmodel;

public class FollowDAO {
	private int shareAward;
	private long userId;
	private QueryEventDAO event;
	private float followPrice;
	private long ctime;
	public int getShareAward() {
		return shareAward;
	}
	public void setShareAward(int shareAward) {
		this.shareAward = shareAward;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public QueryEventDAO getEvent() {
		return event;
	}
	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}
	public float getFollowPrice() {
		return followPrice;
	}
	public void setFollowPrice(float followPrice) {
		this.followPrice = followPrice;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
}
