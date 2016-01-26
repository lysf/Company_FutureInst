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
 * Created by hao on 2016/1/26.
 */
public class RankingUser implements Serializable{

    /**
     * id : 54
     * name : zapwalker
     * gender : 1
     * headImage : http://www.futureinst.com//images/future_events/future_8222982614563968357_event.jpeg
     * description : 。。
     * ctime : 1435589342000
     * lastOrderTime : 1448946107000
     * status : 0
     * peertoNum : 2
     * topeerNum : 7
     * commentNum : 0
     * onoff : 15728640
     */

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private int gender;
    @SerializedName("headImage")
    private String headImage;
    @SerializedName("description")
    private String description;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("lastOrderTime")
    private long lastOrderTime;
    @SerializedName("status")
    private int status;
    @SerializedName("peertoNum")
    private int peertoNum;
    @SerializedName("topeerNum")
    private int topeerNum;
    @SerializedName("commentNum")
    private int commentNum;
    @SerializedName("onoff")
    private int onoff;

    public static RankingUser objectFromData(String str) {

        return new Gson().fromJson(str, RankingUser.class);
    }

    public static RankingUser objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), RankingUser.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RankingUser> arrayRankingUserFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankingUser>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RankingUser> arrayRankingUserFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RankingUser>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setLastOrderTime(long lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPeertoNum(int peertoNum) {
        this.peertoNum = peertoNum;
    }

    public void setTopeerNum(int topeerNum) {
        this.topeerNum = topeerNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getDescription() {
        return description;
    }

    public long getCtime() {
        return ctime;
    }

    public long getLastOrderTime() {
        return lastOrderTime;
    }

    public int getStatus() {
        return status;
    }

    public int getPeertoNum() {
        return peertoNum;
    }

    public int getTopeerNum() {
        return topeerNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public int getOnoff() {
        return onoff;
    }
}
