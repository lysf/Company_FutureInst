package com.futureinst.model.order;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class UnDealOrderDAO implements Serializable{
	private Long id;
	private QueryEventDAO event;
	private Long userId;
	private int type;
	private float price;
	private int num;
	private float dealPrice;
	private int dealNum;
	private Long deadTime;
	private Long ctime;
	private int status;
	private String statusStr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public QueryEventDAO getEvent() {
		return event;
	}
	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(float dealPrice) {
		this.dealPrice = dealPrice;
	}
	public int getDealNum() {
		return dealNum;
	}
	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}
	public Long getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Long deadTime) {
		this.deadTime = deadTime;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	@Override
	public String toString() {
		return "UnDealOrderDAO [id=" + id + ", event=" + event + ", userId="
				+ userId + ", type=" + type + ", price=" + price + ", num="
				+ num + ", dealPrice=" + dealPrice + ", dealNum=" + dealNum
				+ ", deadTime=" + deadTime + ", ctime=" + ctime + ", status="
				+ status + ", statusStr=" + statusStr + "]";
	}
	
}
