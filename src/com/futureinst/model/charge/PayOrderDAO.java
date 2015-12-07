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
public class PayOrderDAO implements Serializable {

    /**添加支付订单
     * id : 8
     * orderNo : c019399a3bdb6e898af83b95aaedacf1
     * goodsId : 0
     * userId : 4
     * chargeId :
     * ctime : 1449224000456
     * status : 0
     * goods : {"id":2,"title":"1元买100未币","description":"1元买100未币","cnyprice":1,"tradeCurrency":100,"orderNum":6,"dealNum":0,"ctime":1449217219000,"status":1}
     * channel :
     * statusStr : 待付款
     */

    @SerializedName("id")
    private int id;
    @SerializedName("orderNo")
    private String orderNo;
    @SerializedName("goodsId")
    private int goodsId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("chargeId")
    private String chargeId;
    @SerializedName("ctime")
    private long ctime;
    @SerializedName("status")
    private int status;
    /**
     * id : 2
     * title : 1元买100未币
     * description : 1元买100未币
     * cnyprice : 1
     * tradeCurrency : 100
     * orderNum : 6
     * dealNum : 0
     * ctime : 1449217219000
     * status : 1
     */

    @SerializedName("goods")
    private GoodsDAO goods;
    @SerializedName("channel")
    private String channel;
    @SerializedName("statusStr")
    private String statusStr;

    public static PayOrderDAO objectFromData(String str) {

        return new Gson().fromJson(str, PayOrderDAO.class);
    }

    public static PayOrderDAO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), PayOrderDAO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PayOrderDAO> arrayPayOrderDAOFromData(String str) {

        Type listType = new TypeToken<ArrayList<PayOrderDAO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<PayOrderDAO> arrayPayOrderDAOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<PayOrderDAO>>() {
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

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public GoodsDAO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDAO goods) {
        this.goods = goods;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public long getCtime() {
        return ctime;
    }

    public int getStatus() {
        return status;
    }

    public String getChannel() {
        return channel;
    }

    public String getStatusStr() {
        return statusStr;
    }

    @Override
    public String toString() {
        return "PayOrderDAO{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", goodsId=" + goodsId +
                ", userId=" + userId +
                ", chargeId='" + chargeId + '\'' +
                ", ctime=" + ctime +
                ", status=" + status +
                ", goods=" + goods +
                ", channel='" + channel + '\'' +
                ", statusStr='" + statusStr + '\'' +
                '}';
    }
}
