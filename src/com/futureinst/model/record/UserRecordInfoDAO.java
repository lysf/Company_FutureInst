package com.futureinst.model.record;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.dailytask.TaskDAO;

import java.util.List;

@SuppressWarnings("serial")
public class UserRecordInfoDAO extends BaseModel {
	private UserRecordDAO user_record;
    private List<TaskDAO> task_list;

	public UserRecordDAO getUser_record() {
		return user_record;
	}

	public void setUser_record(UserRecordDAO user_record) {
		this.user_record = user_record;
	}

    public List<TaskDAO> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TaskDAO> task_list) {
        this.task_list = task_list;
    }

    @Override
    public String toString() {
        return "UserRecordInfoDAO{" +
                "user_record=" + user_record +
                ", task_list=" + task_list +
                '}';
    }
}
