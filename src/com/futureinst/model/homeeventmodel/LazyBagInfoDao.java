package com.futureinst.model.homeeventmodel;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class LazyBagInfoDao implements Serializable {
	private int size;
	private List<LazyBagDAO> bags;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<LazyBagDAO> getBags() {
		return bags;
	}
	public void setBags(List<LazyBagDAO> bags) {
		this.bags = bags;
	}
	@Override
	public String toString() {
		return "LazyBagInfo [size=" + size + ", bags=" + bags + "]";
	}
}
