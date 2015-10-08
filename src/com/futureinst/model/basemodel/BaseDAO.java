package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDAO implements Serializable{
	private int status;
	private String errinfo;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	@Override
	public String toString() {
		return "BaseDAO [status=" + status + ", errinfo=" + errinfo + "]";
	}
}
