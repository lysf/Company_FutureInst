package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventPriceDAO implements Serializable{
	private Long id;
	private Long eventId;
	private float price;
	private Long ctime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "EventPriceDAO [id=" + id + ", eventId=" + eventId + ", price="
				+ price + ", ctime=" + ctime + "]";
	}
}
