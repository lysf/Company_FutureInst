package com.futureinst.model.comment;

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
 * Created by hao on 2015/10/23.
 */
public class ArticleDAO implements Serializable{

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
    private QueryEventDAO event;
    private UserDAO user;
    private  String abstr;
    private int loveNum;
    private int commentNum;

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
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

    public static ArticleDAO objectFromData(String str) {

        return new Gson().fromJson(str, ArticleDAO.class);
    }

    public static ArticleDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), ArticleDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ArticleDAO> arrayArticleDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<ArticleDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ArticleDAO> arrayArticleDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ArticleDAO>>() {
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
