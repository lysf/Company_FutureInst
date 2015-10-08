package com.futureinst.model.other;

import java.io.Serializable;
import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class OtherForecastInfo extends BaseModel implements Serializable{
	private List<OtherForecastDAO> eventclears;

	public List<OtherForecastDAO> getEventclears() {
		return eventclears;
	}

	public void setEventclears(List<OtherForecastDAO> eventclears) {
		this.eventclears = eventclears;
	}

	@Override
	public String toString() {
		return "OtherForecastInfo [eventclears=" + eventclears + "]";
	}
	
}
