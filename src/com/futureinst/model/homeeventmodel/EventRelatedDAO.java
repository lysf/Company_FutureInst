package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

import com.futureinst.model.comment.CommentInfoDAO;

@SuppressWarnings("serial")
public class EventRelatedDAO implements Serializable{
	private ReferenceDAOInfo refer;
	private CommentInfoDAO comment;
	public ReferenceDAOInfo getRefer() {
		return refer;
	}

	public void setRefer(ReferenceDAOInfo refer) {
		this.refer = refer;
	}
	public CommentInfoDAO getComment() {
		return comment;
	}

	public void setComment(CommentInfoDAO comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "EventRelatedDAO [refer=" + refer + ", comment=" + comment + "]";
	}
	
}
