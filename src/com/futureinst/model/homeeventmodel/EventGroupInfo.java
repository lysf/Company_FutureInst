package com.futureinst.model.homeeventmodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventGroupInfo extends BaseModel {
	private int size;
	private List<EventGroupDAO> groups;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<EventGroupDAO> getGroups() {
		return groups;
	}
	public void setGroups(List<EventGroupDAO> groups) {
		this.groups = groups;
	}
	@Override
	public String toString() {
		return "EventGroupInfo [size=" + size + ", groups=" + groups + "]";
	}
}
