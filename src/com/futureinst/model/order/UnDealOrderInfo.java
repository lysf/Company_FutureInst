package com.futureinst.model.order;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class UnDealOrderInfo extends BaseModel {
	private int size;
	private List<UnDealOrderDAO> orders;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<UnDealOrderDAO> getOrders() {
		return orders;
	}
	public void setOrders(List<UnDealOrderDAO> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "OrderInfo [size=" + size + ", orders=" + orders + "]";
	}
	
}
