package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PermitDAO implements Serializable{
	private String order;
	private String me_follow;
	private String follow_me;
	private String gain;
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getMe_follow() {
		return me_follow;
	}
	public void setMe_follow(String me_follow) {
		this.me_follow = me_follow;
	}
	public String getFollow_me() {
		return follow_me;
	}
	public void setFollow_me(String follow_me) {
		this.follow_me = follow_me;
	}
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}
}
