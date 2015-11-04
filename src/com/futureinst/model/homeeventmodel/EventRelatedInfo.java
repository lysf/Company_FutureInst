package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventRelatedInfo extends BaseModel {
	private ReferenceDAOInfo refer;
	private LazyBagInfoDao lazybag;
	private QueryEventDAO event;

	public ReferenceDAOInfo getRefer() {
		return refer;
	}

	public void setRefer(ReferenceDAOInfo refer) {
		this.refer = refer;
	}

	public LazyBagInfoDao getLazybag() {
		return lazybag;
	}

	public void setLazybag(LazyBagInfoDao lazybag) {
		this.lazybag = lazybag;
	}

	public QueryEventDAO getEvent() {
		return event;
	}

	public void setEvent(QueryEventDAO event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "EventRelatedInfo{" +
				"refer=" + refer +
				", lazybag=" + lazybag +
				", event=" + event +
				'}';
	}
}
