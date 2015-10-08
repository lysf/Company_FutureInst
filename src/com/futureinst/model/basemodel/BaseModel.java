package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseModel extends BaseDAO implements Serializable{
	private String method;
	private String type;
	private Long curr_time;
	private String info;
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(Long curr_time) {
		this.curr_time = curr_time;
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
		return "BaseModel [method=" + method + ", type=" + type + ", curr_time=" + curr_time + ", info=" + info + "]";
	}
	
}
