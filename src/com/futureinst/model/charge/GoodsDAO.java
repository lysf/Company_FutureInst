package com.futureinst.model.charge;

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
 * Created by hao on 2015/12/4.
 */
public class GoodsDAO implements Serializable{
    /**
     * id : 2
     * title : 1元买100未币
     * description : 1元买100未币
     * cnyprice : 1
     * tradeCurrency : 100
     * orderNum : 4
     * dealNum : 0
     * ctime : 1449217219000
     * status : 1
     */

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("cnyprice")
    private String cnyprice;
    @SerializedName("tradeCurrency")
    private int tradeCurrency;
    @SerializedName("orderNum")
    private int orderNum;
    @SerializedName("dealNum")
    private int dealNum;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("status")
    private int status;

    public static GoodsDAO objectFromData(String str) {

        return new Gson().fromJson(str, GoodsDAO.class);
    }

    public static GoodsDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GoodsDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GoodsDAO> arrayGoodsDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodsDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GoodsDAO> arrayGoodsDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GoodsDAO>>() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCnyprice(String cnyprice) {
        this.cnyprice = cnyprice;
    }

    public void setTradeCurrency(int tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCnyprice() {
        return cnyprice;
    }

    public int getTradeCurrency() {
        return tradeCurrency;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getDealNum() {
        return dealNum;
    }

    public long getCtime() {
        return ctime;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "GoodsDAO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cnyprice=" + cnyprice +
                ", tradeCurrency=" + tradeCurrency +
                ", orderNum=" + orderNum +
                ", dealNum=" + dealNum +
                ", ctime=" + ctime +
                ", status=" + status +
                '}';
    }
}
