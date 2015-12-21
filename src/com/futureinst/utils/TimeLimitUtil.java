package com.futureinst.utils;


import android.util.Log;

import java.util.Calendar;

/**
 * Created by hao on 2015/11/25.
 */
public class TimeLimitUtil {
    public static final float minAM = 11.5f;
    public static final float maxAM = 14.5f;
    public static final float minPM = 19.0f;
    public static final float maxPM = 22.0f;
    public static final float start = 10.0f;//上班时间
    public static final float end = 19.0f;//下班时间

    public static int judgeTaskAM(long currentTime){
        return judgeBetweenTimes(currentTime,minAM,maxAM);
    }
    public static int judgeTaskPM(long currentTime){
        return judgeBetweenTimes(currentTime,minPM,maxPM);
    }
    public static boolean judgeIsWorkDay(long currentTime){
        return  judgeWorkDay(currentTime);
    }
    //判断是否是工作日（假期除外,只能判断周一至周五）
    private static boolean judgeWorkDay(long currentTime){
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
       int week =  currentCalendar.get(Calendar.DAY_OF_WEEK) -1; //(0~6)
        Log.i("time","---------week--->>"+week);
        if(week > 0 && week < 6){//周一至周五
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            minCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            minCalendar.set(Calendar.HOUR_OF_DAY, (int)start);
            minCalendar.set(Calendar.MINUTE, (int)(start%1*60));
            minCalendar.set(Calendar.SECOND, 0);
            minCalendar.set(Calendar.MILLISECOND, 0);

            Calendar maxCalendar = Calendar.getInstance();
            maxCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            maxCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            maxCalendar.set(Calendar.HOUR_OF_DAY, (int)end);
            maxCalendar.set(Calendar.MINUTE, (int)(end%1*60));
            maxCalendar.set(Calendar.SECOND, 0);
            maxCalendar.set(Calendar.MILLISECOND, 0);
            Log.i("time","-----------hour--->>"+currentCalendar.get(Calendar.HOUR_OF_DAY));
            if(currentTime >= minCalendar.getTimeInMillis()
                    && currentTime <= maxCalendar.getTimeInMillis()){//在工作时间
                return true;
            }

        }
        return false;
    }


    private static int judgeBetweenTimes(long currentTime,float min,float max){
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
        minCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
        minCalendar.set(Calendar.HOUR_OF_DAY, (int)min);
        minCalendar.set(Calendar.MINUTE, (int)(min%1*60));
        minCalendar.set(Calendar.SECOND, 0);
        minCalendar.set(Calendar.MILLISECOND, 0);

        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
        maxCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
        maxCalendar.set(Calendar.HOUR_OF_DAY, (int)max);
        maxCalendar.set(Calendar.MINUTE, (int)(max%1*60));
        maxCalendar.set(Calendar.SECOND, 0);
        maxCalendar.set(Calendar.MILLISECOND, 0);

        if(minCalendar.getTimeInMillis() > currentTime){//时间未到
            return -1;
        }else if(maxCalendar.getTimeInMillis() < currentTime){//已过期
            return 1;
        }else{
            return 0;
        }
    }

}
