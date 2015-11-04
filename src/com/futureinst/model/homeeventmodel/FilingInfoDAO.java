package com.futureinst.model.homeeventmodel;

import java.util.List;
import java.util.Map;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.CommentDAO;

@SuppressWarnings("serial")
public class FilingInfoDAO extends BaseModel {
	private List<QueryEventDAO> events;
	private Map<String, List<CommentDAO>> commentMap;
	public List<QueryEventDAO> getEvents() {
		return events;
	}

	public void setEvents(List<QueryEventDAO> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "FilingInfoDAO [events=" + events + "]";
	}

	public Map<String, List<CommentDAO>> getCommentMap() {
		return commentMap;
	}

	public void setCommentMap(Map<String, List<CommentDAO>> commentMap) {
		this.commentMap = commentMap;
	}
}
