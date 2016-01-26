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

@SuppressWarnings("serial")
public class RankDAO implements Serializable{

    /**
     * period : 2015-10-01
     * scope : 0
     * userId : 54
     * tag : 2
     * allGain : 6593.35
     * avgGain : 2197.78
     * allEvent : 3
     * gainEvent : 3
     * foreIndexNew : 92.586
     * rank : 2
     * lastRank : 0
     * ctime : 1453723751000
     * user : {"id":54,"name":"zapwalker","gender":1,"headImage":"http://www.futureinst.com//images/future_events/future_8222982614563968357_event.jpeg","description":"。。","ctime":1435589342000,"lastOrderTime":1448946107000,"status":0,"peertoNum":2,"topeerNum":7,"commentNum":0,"onoff":15728640}
     */

    @SerializedName("period")
    private String period;
    @SerializedName("scope")
    private int scope;
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
    @SerializedName("ctime")
    private long ctime;
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

    @SerializedName("user")
    private UserEntity user;

    public static RankDAO objectFromData(String str) {

        return new Gson().fromJson(str, RankDAO.class);
    }

    public static RankDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), RankDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RankDAO> arrayRankDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RankDAO> arrayRankDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RankDAO>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setScope(int scope) {
        this.scope = scope;
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

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPeriod() {
        return period;
    }

    public int getScope() {
        return scope;
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

    public long getCtime() {
        return ctime;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class UserEntity {
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

        public static UserEntity objectFromData(String str) {

            return new Gson().fromJson(str, UserEntity.class);
        }

        public static UserEntity objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), UserEntity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<UserEntity> arrayUserEntityFromData(String str) {

            Type listType = new TypeToken<ArrayList<UserEntity>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<UserEntity> arrayUserEntityFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<UserEntity>>() {
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
}
