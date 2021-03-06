package com.futureinst.utils;

import com.futureinst.global.TaskType;
import com.futureinst.model.dailytask.DailyTaskInfoDAO;

/**
 * Created by hao on 2015/11/27.
 */
public class TaskTipUtil {
    public static boolean isShowTip(DailyTaskInfoDAO dailyTaskInfo) {
        if (dailyTaskInfo.getDaily_task() == null) {
            return false;
        } else {
            //有完成的任务，还未领取奖励
            if (dailyTaskInfo.getDaily_task().getFinishedTasks().size() > dailyTaskInfo.getDaily_task().getAwardedTasks().size()) {
                return true;
            }
        }
        return false;
    }

}
