package com.futureinst.model.homeeventmodel;

import java.io.Serializable;
import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventPriceDAOInfo implements Serializable {
	private int size;
	private List<EventPriceDAO> prices;
	private List<EventBuyDAO> buys;
	private List<EventSellDAO> sells;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<EventPriceDAO> getPrices() {
		return prices;
	}
	public void setPrices(List<EventPriceDAO> prices) {
		this.prices = prices;
	}
	public List<EventBuyDAO> getBuys() {
		return buys;
	}
	public void setBuys(List<EventBuyDAO> buys) {
		this.buys = buys;
	}
	public List<EventSellDAO> getSells() {
		return sells;
	}
	public void setSells(List<EventSellDAO> sells) {
		this.sells = sells;
	}
	@Override
	public String toString() {
		return "EventPriceDAOInfo [size=" + size + ", prices=" + prices
				+ ", buys=" + buys + ", sells=" + sells + "]";
	}
}
