package com.futureinst.model.homeeventmodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class FilingInfoDAO extends BaseModel {
	private List<QueryEventDAO> events;

	public List<QueryEventDAO> getEvents() {
		return events;
	}

	public void setEvents(List<QueryEventDAO> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "FilingInfoDAO [events=" + events + "]";
	}
	
}	
