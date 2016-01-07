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
 * Created by hao on 2016/1/6.
 */
public class TaskDAO implements Serializable {

    /**
     * name : login
     * title : 开启APP
     * award : 50
     * num : 1
     * status : 1
     * numName :
     */

    @SerializedName("name")
    private String name;
    @SerializedName("title")
    private String title;
    @SerializedName("award")
    private int award;
    @SerializedName("num")
    private int num;
    @SerializedName("status")
    private int status;
    @SerializedName("numName")
    private String numName;
    private int flag;//(0:进行中；1:已完成，未领取；2:已领取)
    private String progress;//(flag为0时有用)

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public static TaskDAO objectFromData(String str) {

        return new Gson().fromJson(str, TaskDAO.class);
    }

    public static TaskDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), TaskDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TaskDAO> arrayTaskDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<TaskDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<TaskDAO> arrayTaskDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<TaskDAO>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNumName(String numName) {
        this.numName = numName;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getAward() {
        return award;
    }

    public int getNum() {
        return num;
    }

    public int getStatus() {
        return status;
    }

    public String getNumName() {
        return numName;
    }
}
