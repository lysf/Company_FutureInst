package com.futureinst.model.push;

import java.io.Serializable;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class PushMessageDAO implements Serializable{
	private String id;
	private String event_id;
	private String no_notice ="0";
	private Long time;
	private String text;
	private String title;
	private String href;
	private String type ;//event  表示事件详情页;comment 表示评论页;rank 表示排名页,type为url时，配合参数href，表示打开特定网页
	private String peer_id;//关注者id
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
	
	public String getPeer_id() {
		return peer_id;
	}
	public void setPeer_id(String peer_id) {
		this.peer_id = peer_id;
	}
	public String getHref() {
		return href;
	}
	
	public String getNo_notice() {
		return no_notice;
	}
	public void setNo_notice(String no_notice) {
		this.no_notice = no_notice;
	}
	public void setHref(String href) {
		this.href = href;
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
	@Override
	public String toString() {
		return "PushMessageDAO [id=" + id + ", event_id=" + event_id + ", time=" + time + ", text=" + text + ", title="
				+ title + ", href=" + href + ", type=" + type + ", isRead=" + isRead + ", event=" + event + "]";
	}
	
}
