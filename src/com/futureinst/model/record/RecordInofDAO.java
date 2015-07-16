package com.futureinst.model.record;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class RecordInofDAO extends BaseModel {
	private List<RecordDAO> tag_records;

	public List<RecordDAO> getTag_records() {
		return tag_records;
	}

	public void setTag_records(List<RecordDAO> tag_records) {
		this.tag_records = tag_records;
	}

	@Override
	public String toString() {
		return "RecordInofDAO [tag_records=" + tag_records + "]";
	}
}
