package com.futureinst.model.comment;

import java.util.List;
import java.util.Map;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class CommentInfoDAO extends BaseModel {
	private List<CommentDAO> parentComments;
	private QueryEventDAO event;
	private Map<String,CommentDAO> lastChildCommentMap;
	private List<String> loves;


	public Map<String, CommentDAO> getLastChildCommentMap() {
		return lastChildCommentMap;
	}

	public void setLastChildCommentMap(Map<String, CommentDAO> lastChildCommentMap) {
		this.lastChildCommentMap = lastChildCommentMap;
	}

	public List<String> getLoves() {
		return loves;
	}

	public void setLoves(List<String> loves) {
		this.loves = loves;
	}

	public QueryEventDAO getEvent() {
		return event;
	}

	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}

	private int size;
	public List<CommentDAO> getComments() {
		return parentComments;
	}

	public void setComments(List<CommentDAO> comments) {
		this.parentComments = comments;
	}
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "CommentInfoDAO [comments=" + parentComments + ", size=" + size + ", getComments()=" + getComments()
				+ ", getSize()=" + getSize() + ", getInfo()=" + getInfo() + ", getCurr_time()=" + getCurr_time()
				+ ", getStatus()=" + getStatus() + ", getErrinfo()=" + getErrinfo() + ", getMethod()=" + getMethod()
				+ ", getType()=" + getType() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	
}
