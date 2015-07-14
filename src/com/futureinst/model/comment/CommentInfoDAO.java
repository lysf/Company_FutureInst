package com.futureinst.model.comment;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class CommentInfoDAO extends BaseModel {
	private List<CommentDAO> comments;
	private int size;
	public List<CommentDAO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDAO> comments) {
		this.comments = comments;
	}
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "CommentInfoDAO [comments=" + comments + ", size=" + size + ", getComments()=" + getComments()
				+ ", getSize()=" + getSize() + ", getInfo()=" + getInfo() + ", getCurr_time()=" + getCurr_time()
				+ ", getStatus()=" + getStatus() + ", getErrinfo()=" + getErrinfo() + ", getMethod()=" + getMethod()
				+ ", getType()=" + getType() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	
}
