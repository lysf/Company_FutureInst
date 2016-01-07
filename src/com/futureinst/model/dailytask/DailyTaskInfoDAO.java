package com.futureinst.model.dailytask;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao on 2015/11/25.
 */
public class DailyTaskInfoDAO extends BaseModel implements Serializable{
    private DailyTaskDAO daily_task;

    private List<TaskDAO> task_list;
    private int award;

    public DailyTaskDAO getDaily_task() {
        return daily_task;
    }

    public void setDaily_task(DailyTaskDAO daily_task) {
        this.daily_task = daily_task;
    }

    public List<TaskDAO> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TaskDAO> task_list) {
        this.task_list = task_list;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    @Override
    public String toString() {
        return "DailyTaskInfoDAO{" +
                "daily_task=" + daily_task +
                ", task_list=" + task_list +
                ", award=" + award +
                '}';
    }
}
