package com.futureinst.model.dailytask;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/11/25.
 */
public class DailyTaskDAO implements Serializable{

    /**
     * uday : 4_2015-11-25
     * userId : 4
     * date : 2015-11-25
     * ctime : 1448381518000
     * orderNum : 0
     * comNum : 2
     * status : 0
     * finishedTasks : ["login","com2"]
     * awardedTasks : ["login","com2"]
     */

    @SerializedName("uday")
    private String uday;
    @SerializedName("userId")
    private int userId;
    @SerializedName("date")
    private String date;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("orderNum")
    private int orderNum;
    @SerializedName("comNum")
    private int comNum;
    @SerializedName("status")
    private int status;
    @SerializedName("finishedTasks")
    private List<String> finishedTasks;
    @SerializedName("awardedTasks")
    private List<String> awardedTasks;

    public static DailyTaskDAO objectFromData(String str) {

        return new Gson().fromJson(str, DailyTaskDAO.class);
    }

    public static DailyTaskDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), DailyTaskDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<DailyTaskDAO> arrayDailyTaskDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<DailyTaskDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<DailyTaskDAO> arrayDailyTaskDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<DailyTaskDAO>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setUday(String uday) {
        this.uday = uday;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setComNum(int comNum) {
        this.comNum = comNum;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFinishedTasks(List<String> finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    public void setAwardedTasks(List<String> awardedTasks) {
        this.awardedTasks = awardedTasks;
    }

    public String getUday() {
        return uday;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public long getCtime() {
        return ctime;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getComNum() {
        return comNum;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getFinishedTasks() {
        return finishedTasks;
    }

    public List<String> getAwardedTasks() {
        return awardedTasks;
    }
}
