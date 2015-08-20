package com.futureinst.model.push;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class PushMessageDAO implements Serializable{
	private String id;
	private String event_id;
	private Long time;
	private String text;
	private String title;
	private String type;
	private boolean isRead;
	private QueryEventDAO event;
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
	
	public QueryEventDAO getEvent() {
		return event;
	}
	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
