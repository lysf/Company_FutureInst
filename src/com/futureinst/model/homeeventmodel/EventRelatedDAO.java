package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventRelatedDAO implements Serializable{
	private ReferenceDAOInfo refer;

	public ReferenceDAOInfo getRefer() {
		return refer;
	}

	public void setRefer(ReferenceDAOInfo refer) {
		this.refer = refer;
	}

	@Override
	public String toString() {
		return "EventRelatedDAO [refer=" + refer + "]";
	}
	
}
