package com.futureinst.model.push;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PushMessageInfo implements Serializable{
	private List<PushMessageDAO> list;

	public List<PushMessageDAO> getList() {
		return list;
	}

	public void setList(List<PushMessageDAO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PushMessageInfo [list=" + list + "]";
	}

	
}
