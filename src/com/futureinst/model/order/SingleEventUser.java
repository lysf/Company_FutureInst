package com.futureinst.model.order;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SingleEventUser implements Serializable {
	private int follow;//1:已关注，0：未关注
	private int share_award;
	private float if_yes;
	private float if_no;
	private SingleEventClearDAO event_clear;
	public float getIf_yes() {
		return if_yes;
	}
	public void setIf_yes(float if_yes) {
		this.if_yes = if_yes;
	}
	public float getIf_no() {
		return if_no;
	}
	public void setIf_no(float if_no) {
		this.if_no = if_no;
	}
	public int getFollow() {
		return follow;
	}
	public void setFollow(int follow) {
		this.follow = follow;
	}
	public SingleEventClearDAO getEvent_clear() {
		return event_clear;
	}
	public void setEvent_clear(SingleEventClearDAO event_clear) {
		this.event_clear = event_clear;
	}
	public int getShare_award() {
		return share_award;
	}
	public void setShare_award(int share_award) {
		this.share_award = share_award;
	}
	@Override
	public String toString() {
		return "SingleEventUser [follow=" + follow + ", share_award=" + share_award + ", if_yes=" + if_yes + ", if_no="
				+ if_no + ", event_clear=" + event_clear + "]";
	}
	
}
