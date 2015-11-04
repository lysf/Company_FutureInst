package com.futureinst.model.news;

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
 * Created by hao on 2015/11/1.
 */
public class NewsDAO implements Serializable{

    /**
     * id : 2
     * objectId : 317
     * objectType : user
     * operate : me_follow
     * message : <a href='./user/5'>哈啊哈</a>关注了<a href='./user/317'>Hancoc5</a>
     * permitType : 3
     * ctime : 1446267404000
     * status : 0
     */

    @SerializedName("id")
    private int id;
    @SerializedName("objectId")
    private int objectId;
    @SerializedName("objectType")
    private String objectType;
    @SerializedName("operate")
    private String operate;
    @SerializedName("message")
    private String message;
    @SerializedName("permitType")
    private int permitType;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("status")
    private int status;
    private UserDAO user;

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public static NewsDAO objectFromData(String str) {

        return new Gson().fromJson(str, NewsDAO.class);
    }

    public static NewsDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), NewsDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NewsDAO> arrayNewsDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewsDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<NewsDAO> arrayNewsDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<NewsDAO>>() {
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

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPermitType(int permitType) {
        this.permitType = permitType;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getOperate() {
        return operate;
    }

    public String getMessage() {
        return message;
    }

    public int getPermitType() {
        return permitType;
    }

    public long getCtime() {
        return ctime;
    }

    public int getStatus() {
        return status;
    }
}
