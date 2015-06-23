package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class LazyBagInfo extends BaseModel {
	private LazyBagInfoDao lazybag;

	public LazyBagInfoDao getLazybag() {
		return lazybag;
	}

	public void setLazybag(LazyBagInfoDao lazybag) {
		this.lazybag = lazybag;
	}

	@Override
	public String toString() {
		return "LazyBagInfo [lazybag=" + lazybag + "]";
	}
}
