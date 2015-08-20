package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventPriceInfo extends BaseModel {
	private EventPriceDAOInfo price;
	private QueryEventDAO event;
	public EventPriceDAOInfo getPrice() {
		return price;
	}

	public void setPrice(EventPriceDAOInfo price) {
		this.price = price;
	}
	
	public QueryEventDAO getEvent() {
		return event;
	}

	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "EventPriceInfo [price=" + price + ", event=" + event + "]";
	}

	
}
