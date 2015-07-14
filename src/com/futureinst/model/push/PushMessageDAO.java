package com.futureinst.model.push;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PushMessageDAO implements Serializable{
	private String id;
	private Long time;
	private String text;
	private boolean isRead;
	
	public PushMessageDAO() {
		super();
	}
	public PushMessageDAO(String id, String text, boolean isRead,Long time) {
		super();
		this.id = id;
		this.text = text;
		this.isRead = isRead;
		this.time = time;
	}
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "PushMessageDAO [id=" + id + ", time=" + time + ", text="
				+ text + ", isRead=" + isRead + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
