package com.futureinst.model.comment;

import java.io.Serializable;

import com.futureinst.model.usermodel.UserDAO;

public class CommentDAO implements Serializable {
	private static final long serialVersionUID = 542372009377888327L;
	private Long id;
	private Long eventId;
	private UserDAO user;
	private String content;
	private Long ctime;
	private int attitude;
	private int status;
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
	public UserDAO getUser() {
		return user;
	}
	public void setUser(UserDAO user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public int getAttitude() {
		return attitude;
	}
	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CommentDAO [id=" + id + ", eventId=" + eventId + ", user=" + user + ", content=" + content + ", ctime="
				+ ctime + ", attitude=" + attitude + ", status=" + status + "]";
	}
	
}
