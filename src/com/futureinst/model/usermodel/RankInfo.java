package com.futureinst.model.usermodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class RankInfo extends BaseModel {
	private int size;
	private List<RankDAO> ranks;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<RankDAO> getRanks() {
		return ranks;
	}
	public void setRanks(List<RankDAO> ranks) {
		this.ranks = ranks;
	}
	@Override
	public String toString() {
		return "RankInfo [size=" + size + ", ranks=" + ranks + "]";
	}
	
}
