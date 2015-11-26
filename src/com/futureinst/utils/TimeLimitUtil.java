package com.futureinst.utils;


import java.util.Calendar;

/**
 * Created by hao on 2015/11/25.
 */
public class TimeLimitUtil {
    public static final float minAM = 11.5f;
    public static final float maxAM = 14.5f;
    public static final float minPM = 19.0f;
    public static final float maxPM = 22.0f;

    public static int judgeTaskAM(long currentTime){
        return judgeBetweenTimes(currentTime,minAM,maxAM);
    }
    public static int judgeTaskPM(long currentTime){
        return judgeBetweenTimes(currentTime,minPM,maxPM);
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
