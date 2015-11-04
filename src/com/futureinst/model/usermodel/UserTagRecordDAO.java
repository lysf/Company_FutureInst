package com.futureinst.model.usermodel;

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
 * Created by hao on 2015/11/9.
 */
public class UserTagRecordDAO implements Serializable {

    /**
     * userId : 5
     * tag : 3
     * allGain : 7728.13
     * avgGain : 193.2
     * allEvent : 40
     * gainEvent : 22
     * foreIndexNew : 83.75
     * rank : 44
     * lastRank : 4725
     * foreIndex : 3350
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("tag")
    private int tag;
    @SerializedName("allGain")
    private double allGain;
    @SerializedName("avgGain")
    private double avgGain;
    @SerializedName("allEvent")
    private int allEvent;
    @SerializedName("gainEvent")
    private int gainEvent;
    @SerializedName("foreIndexNew")
    private double foreIndexNew;
    @SerializedName("rank")
    private int rank;
    @SerializedName("lastRank")
    private int lastRank;
    @SerializedName("foreIndex")
    private int foreIndex;

    public static UserTagRecordDAO objectFromData(String str) {

        return new Gson().fromJson(str, UserTagRecordDAO.class);
    }

    public static UserTagRecordDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), UserTagRecordDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<UserTagRecordDAO> arrayUserTagRecordDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<UserTagRecordDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<UserTagRecordDAO> arrayUserTagRecordDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<UserTagRecordDAO>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setAllGain(double allGain) {
        this.allGain = allGain;
    }

    public void setAvgGain(double avgGain) {
        this.avgGain = avgGain;
    }

    public void setAllEvent(int allEvent) {
        this.allEvent = allEvent;
    }

    public void setGainEvent(int gainEvent) {
        this.gainEvent = gainEvent;
    }

    public void setForeIndexNew(double foreIndexNew) {
        this.foreIndexNew = foreIndexNew;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setLastRank(int lastRank) {
        this.lastRank = lastRank;
    }

    public void setForeIndex(int foreIndex) {
        this.foreIndex = foreIndex;
    }

    public int getUserId() {
        return userId;
    }

    public int getTag() {
        return tag;
    }

    public double getAllGain() {
        return allGain;
    }

    public double getAvgGain() {
        return avgGain;
    }

    public int getAllEvent() {
        return allEvent;
    }

    public int getGainEvent() {
        return gainEvent;
    }

    public double getForeIndexNew() {
        return foreIndexNew;
    }

    public int getRank() {
        return rank;
    }

    public int getLastRank() {
        return lastRank;
    }

    public int getForeIndex() {
        return foreIndex;
    }
}
