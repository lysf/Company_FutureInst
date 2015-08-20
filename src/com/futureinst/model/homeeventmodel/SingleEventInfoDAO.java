package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class SingleEventInfoDAO extends BaseModel {
	private QueryEventDAO event;

	public QueryEventDAO getEvent() {
		return event;
	}

	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "SingleEventInfoDAO [event=" + event + "]";
	}
	
}
