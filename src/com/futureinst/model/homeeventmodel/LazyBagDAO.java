package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LazyBagDAO implements Serializable{
	private Long id;
	private Long eventId;
	private String title;
	private String content;
	private int status;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return "LazyBagDAO [id=" + id + ", eventId=" + eventId + ", title="
				+ title + ", content=" + content + ", status=" + status
				+ ", ctime=" + ctime + "]";
	}
}
