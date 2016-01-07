package com.futureinst.model.dailytask;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2016/1/6.
 */
public class DailyTaskMapInfoDAO extends BaseModel implements Serializable {
    private Map<String,Object> daily_task;
    private List<TaskDAO> task_list;

    public Map<String, Object> getDaily_task() {
        return daily_task;
    }

    public void setDaily_task(Map<String, Object> daily_task) {
        this.daily_task = daily_task;
    }

    public List<TaskDAO> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TaskDAO> task_list) {
        this.task_list = task_list;
    }

    @Override
    public String toString() {
        return "DailyTaskMapInfoDAO{" +
                "daily_task=" + daily_task +
                ", task_list=" + task_list +
                '}';
    }
}
