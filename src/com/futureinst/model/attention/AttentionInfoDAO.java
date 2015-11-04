package com.futureinst.model.attention;

import java.util.List;
import java.util.Map;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.CommentDAO;

@SuppressWarnings("serial")
public class AttentionInfoDAO extends BaseModel {
	private List<AttentionDAO> follows;

	public Map<String, List<CommentDAO>> getCommentMap() {
		return commentMap;
	}

	public void setCommentMap(Map<String, List<CommentDAO>> commentMap) {
		this.commentMap = commentMap;
	}

	private Map<String, List<CommentDAO>> commentMap;
	public List<AttentionDAO> getFollows() {
		return follows;
	}

	public void setFollows(List<AttentionDAO> follows) {
		this.follows = follows;
	}

	@Override
	public String toString() {
		return "AttentionInfoDAO [follows=" + follows + "]";
	}
}
