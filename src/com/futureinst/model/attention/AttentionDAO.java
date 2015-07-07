package com.futureinst.model.attention;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class AttentionDAO implements Serializable {
	private Long userId;
	private QueryEventDAO event;
	private float followPrice;
	private Long ctime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
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
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "AttentionDAO [userId=" + userId + ", event=" + event + ", followPrice=" + followPrice + ", ctime="
				+ ctime + "]";
	}
}
