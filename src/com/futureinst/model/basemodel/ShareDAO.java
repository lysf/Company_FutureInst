package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShareDAO implements Serializable{
	private String title;
	private String image;
	private String content;
	private String content_weibo;
	private String link;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_weibo() {
		return content_weibo;
	}
	public void setContent_weibo(String content_weibo) {
		this.content_weibo = content_weibo;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
