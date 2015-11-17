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
    private int attitude;//1表示看好，2表示不看好
    private int status;
	private long level;
	private int likeNum;
	private long lastChildId;
	private long parentId;
	private int childNum;
	private long replyTo;
	private long articleId;
	private String ctimeStr;

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public long getLastChildId() {
		return lastChildId;
	}

	public void setLastChildId(long lastChildId) {
		this.lastChildId = lastChildId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getChildNum() {
		return childNum;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	public long getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(long replyTo) {
		this.replyTo = replyTo;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getCtimeStr() {
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
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
