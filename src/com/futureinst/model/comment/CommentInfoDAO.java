package com.futureinst.model.comment;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class CommentInfoDAO extends BaseModel {
	private List<CommentDAO> comments;

	public List<CommentDAO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDAO> comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "CommentInfoDAO [comments=" + comments + "]";
	}
}
