package com.futureinst.model.homeeventmodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class QueryEventInfoDAO extends BaseModel {
	private int size;
	private List<QueryEventDAO> events;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<QueryEventDAO> getEvents() {
		return events;
	}
	public void setEvents(List<QueryEventDAO> events) {
		this.events = events;
	}
	@Override
	public String toString() {
		return "QueryEventInfoDAO [size=" + size + ", events=" + events + "]";
	}
	
}
