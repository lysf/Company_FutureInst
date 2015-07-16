package com.futureinst.model.order;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.QueryEventDAO;

@SuppressWarnings("serial")
public class SingleEventInfoDAO extends BaseModel {
	private QueryEventDAO event;
	private SingleEventUser user;
	public QueryEventDAO getEvent() {
		return event;
	}
	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}
	public SingleEventUser getUser() {
		return user;
	}
	public void setUser(SingleEventUser user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "SingleEventInfoDAO [event=" + event + ", user=" + user + "]";
	}
}
