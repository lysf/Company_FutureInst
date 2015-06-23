package com.futureinst.model.order;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class DealOrderInfo extends BaseModel{
	private int size;
	private List<DealOrderDAO> eventclears;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<DealOrderDAO> getEventclears() {
		return eventclears;
	}
	public void setEventclears(List<DealOrderDAO> eventclears) {
		this.eventclears = eventclears;
	}
	@Override
	public String toString() {
		return "DealOrderInfo [size=" + size + ", eventclears=" + eventclears
				+ "]";
	}
	
}
