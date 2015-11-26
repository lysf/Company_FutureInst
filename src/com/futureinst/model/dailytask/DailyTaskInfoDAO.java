package com.futureinst.model.dailytask;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;

/**
 * Created by hao on 2015/11/25.
 */
public class DailyTaskInfoDAO extends BaseModel implements Serializable{
    private DailyTaskDAO daily_task;
    private int award;

    public DailyTaskDAO getDaily_task() {
        return daily_task;
    }

    public void setDaily_task(DailyTaskDAO daily_task) {
        this.daily_task = daily_task;
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
                ", award=" + award +
                '}';
    }
}
