package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class EventRelatedInfo extends BaseModel {
	private EventRelatedDAO related;

	public EventRelatedDAO getRelated() {
		return related;
	}

	public void setRelated(EventRelatedDAO related) {
		this.related = related;
	}

	@Override
	public String toString() {
		return "EventRelatedInfo [related=" + related + "]";
	}
}
