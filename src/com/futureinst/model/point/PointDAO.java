package com.futureinst.model.point;

import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.model.usermodel.UserDAO;
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
 * Created by hao on 2015/10/28.
 */
public class PointDAO implements Serializable{
    /**
     * id : 1
     * eventId : 50
     * userId : 4
     * title : aaaa
     * mtime : 1445533607000
     * ctime : 1445533607000
     * readNum : 0
     * awardNum : 0
     * award : 0
     * status : 0
     */

    @SerializedName("id")
    private int id;
    @SerializedName("eventId")
    private int eventId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("title")
    private String title;
    @SerializedName("mtime")
    private long mtime;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("readNum")
    private int readNum;
    @SerializedName("awardNum")
    private int awardNum;
    @SerializedName("award")
    private int award;
    @SerializedName("status")
    private int status;
    private String statusStr;
    private QueryEventDAO event;
    private UserDAO user;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public QueryEventDAO getEvent() {
        return event;
    }

    public void setEvent(QueryEventDAO event) {
        this.event = event;
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public static PointDAO objectFromData(String str) {

        return new Gson().fromJson(str, PointDAO.class);
    }

    public static PointDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), PointDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PointDAO> arrayPointDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<PointDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<PointDAO> arrayPointDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<PointDAO>>() {
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

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public void setAwardNum(int awardNum) {
        this.awardNum = awardNum;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public long getMtime() {
        return mtime;
    }

    public long getCtime() {
        return ctime;
    }

    public int getReadNum() {
        return readNum;
    }

    public int getAwardNum() {
        return awardNum;
    }

    public int getAward() {
        return award;
    }

    public int getStatus() {
        return status;
    }
}
