package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpFileDAO implements Serializable{
	private String method;
	private int status;
	private String src;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "UpFileDAO [method=" + method + ", status=" + status + ", src=" + src + "]";
	}
	
}
