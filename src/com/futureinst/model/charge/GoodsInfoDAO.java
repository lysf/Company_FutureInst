package com.futureinst.model.charge;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 获取可购买的商品清单
 * Created by hao on 2015/12/4.
 */
public class GoodsInfoDAO extends BaseModel implements Serializable {
    private List<GoodsDAO> goodsList;

    public List<GoodsDAO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsDAO> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "GoodsInfoDAO{" +
                "goodsList=" + goodsList +
                '}';
    }
}
