package com.futureinst.model.homeeventmodel;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ReferenceDAOInfo implements Serializable{
	private int size ;
	private List<ReferenceDAO> refers;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<ReferenceDAO> getRefer() {
		return refers;
	}
	public void setRefer(List<ReferenceDAO> refers) {
		this.refers = refers;
	}
	@Override
	public String toString() {
		return "ReferenceDAOInfo [size=" + size + ", refers=" + refers + "]";
	}
}
