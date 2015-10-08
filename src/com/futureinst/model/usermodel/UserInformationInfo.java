package com.futureinst.model.usermodel;

import java.util.Arrays;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.record.UserRecordDAO;

@SuppressWarnings("serial")
public class UserInformationInfo extends BaseModel {
	private String relation;//"none"
	private PermitDAO peerPermitMap;
	private UserRecordDAO user_record;
	private String[] permit_list;//order(预测中事件),gain(战绩),follow_me（关注我的人）,me_follow（我关注的人）
	public UserRecordDAO getUser_record() {
		return user_record;
	}
	
	public PermitDAO getPeerPermitMap() {
		return peerPermitMap;
	}

	public void setPeerPermitMap(PermitDAO peerPermitMap) {
		this.peerPermitMap = peerPermitMap;
	}

	public String[] getPermit_list() {
		return permit_list;
	}

	public void setPermit_list(String[] permit_list) {
		this.permit_list = permit_list;
	}

	public void setUser_record(UserRecordDAO user_record) {
		this.user_record = user_record;
	}
	
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "UserInformationInfo [user_record=" + user_record + ", permit_list=" + Arrays.toString(permit_list)
				+ "]";
	}

	
}
