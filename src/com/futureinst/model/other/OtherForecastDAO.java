package com.futureinst.model.other;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class OtherForecastDAO implements Serializable{
	private String ueid;
	private long userId;
	private QueryEventDAO event;
	private int buyNum;
	private int sellNum;
	private float gain;
	private long ctime;
	private long mtime;
	private int status;
	public String getUeid() {
		return ueid;
	}
	public void setUeid(String ueid) {
		this.ueid = ueid;
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
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public int getSellNum() {
		return sellNum;
	}
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}
	public float getGain() {
		return gain;
	}
	public void setGain(float gain) {
		this.gain = gain;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getMtime() {
		return mtime;
	}
	public void setMtime(long mtime) {
		this.mtime = mtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "OtherForecastDAO [ueid=" + ueid + ", userId=" + userId + ", event=" + event + ", buyNum=" + buyNum
				+ ", sellNum=" + sellNum + ", gain=" + gain + ", ctime=" + ctime + ", mtime=" + mtime + ", status="
				+ status + "]";
	}
	
	
}
