package com.futureinst.model.homeeventmodel;

import java.util.List;
import java.util.Map;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.CommentDAO;

@SuppressWarnings("serial")
public class QueryEventInfoDAO extends BaseModel {
	private int size;
	Map<String,List<CommentDAO>> commentMap;
	private List<QueryEventDAO> events;

	public Map<String, List<CommentDAO>> getCommentMap() {
		return commentMap;
	}

	public void setCommentMap(Map<String, List<CommentDAO>> commentMap) {
		this.commentMap = commentMap;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<QueryEventDAO> getEvents() {
		return events;
	}
	public void setEvents(List<QueryEventDAO> events) {
		this.events = events;
	}
	@Override
	public String toString() {
		return "QueryEventInfoDAO [size=" + size + ", events=" + events + "]";
	}
	
}
