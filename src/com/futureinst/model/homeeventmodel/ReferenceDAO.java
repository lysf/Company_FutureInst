package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReferenceDAO implements Serializable{
	private Long id;
	private Long eventId;
	private String title;
	private String url;
	private int status;
	private Long ctime;
	private String from;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "ReferenceDAO [id=" + id + ", eventId=" + eventId + ", title="
				+ title + ", url=" + url + ", status=" + status + ", ctime="
				+ ctime + "]";
	}
	
}
