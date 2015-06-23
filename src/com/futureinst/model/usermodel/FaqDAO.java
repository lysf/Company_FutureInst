package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FaqDAO implements Serializable{
	private Long id;
	private String title;
	private String content;
	private int status;
	private Long ctime;
	private int level;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "FaqDAO [id=" + id + ", title=" + title + ", content=" + content
				+ ", status=" + status + ", ctime=" + ctime + ", level="
				+ level + "]";
	}
}
