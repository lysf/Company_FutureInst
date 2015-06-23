package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseModel implements Serializable{
	private int status;
	private String errinfo;
	private String method;
	private String type;
	private Long curr_time;
	public Long getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(Long curr_time) {
		this.curr_time = curr_time;
	}
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "BaseModel [status=" + status + ", errinfo=" + errinfo
				+ ", method=" + method + ", type=" + type + ", curr_time="
				+ curr_time + "]";
	}
	
}
