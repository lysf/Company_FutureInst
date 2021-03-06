package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventSellDAO implements Serializable{
	private int num;
	private float price;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "EventSellDAO [num=" + num + ", price=" + price + "]";
	}
}
