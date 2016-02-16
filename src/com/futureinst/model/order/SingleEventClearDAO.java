package com.futureinst.model.order;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class SingleEventClearDAO implements Serializable {
	private String ueid;
	private Long userId;
	private QueryEventDAO event;
	private float buyPrice;
	private int buyNum;
	private float sellPrice;
	private int sellNum;
	private float gain;
	private Long ctime;
	private Long mtime;
	private int status;
	private float pk0Volume = 0; //pk模式看好总未币量
	private float pk1Volume = 0; //pk模式不看好总未币量
	public SingleEventClearDAO(){
		super();
	}
	public SingleEventClearDAO(float buyPrice, int buyNum, float sellPrice, int sellNum) {
		this.buyPrice = buyPrice;
		this.buyNum = buyNum;
		this.sellPrice = sellPrice;
		this.sellNum = sellNum;
	}

	public float getPk1Volume() {
		return pk1Volume;
	}

	public void setPk1Volume(float pk1Volume) {
		this.pk1Volume = pk1Volume;
	}

	public float getPk0Volume() {
		return pk0Volume;
	}

	public void setPk0Volume(float pk0Volume) {
		this.pk0Volume = pk0Volume;
	}

	public String getUeid() {
		return ueid;
	}
	public void setUeid(String ueid) {
		this.ueid = ueid;
	}
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
	public float getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public float getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
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
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public Long getMtime() {
		return mtime;
	}
	public void setMtime(Long mtime) {
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
		return "SingleEventClearDAO [ueid=" + ueid + ", userId=" + userId + ", event=" + event + ", buyPrice="
				+ buyPrice + ", buyNum=" + buyNum + ", sellPrice=" + sellPrice + ", sellNum=" + sellNum + ", gain="
				+ gain + ", ctime=" + ctime + ", mtime=" + mtime + ", status=" + status + "]";
	}
	
}
