package com.futureinst.model.usermodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class UserCheckInfo extends BaseModel {
	private int size;
	private List<UserCheckDAO> checks;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<UserCheckDAO> getChecks() {
		return checks;
	}
	public void setChecks(List<UserCheckDAO> checks) {
		this.checks = checks;
	}
	@Override
	public String toString() {
		return "UserCheckInfo [size=" + size + ", checks=" + checks + "]";
	}
}
