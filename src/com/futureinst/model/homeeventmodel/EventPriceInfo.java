package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventPriceInfo extends BaseModel {
	private EventPriceDAOInfo price;

	public EventPriceDAOInfo getPrice() {
		return price;
	}

	public void setPrice(EventPriceDAOInfo price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "EventPriceInfo [price=" + price + "]";
	}
}
