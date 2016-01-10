package com.futureinst.model.charge;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;

/**
 * Created by hao on 2015/12/4.
 */
public class PayOrderInfo extends BaseModel implements Serializable {
    private PayOrderDAO pay_order;

    public PayOrderDAO getPay_order() {
        return pay_order;
    }

    public void setPay_order(PayOrderDAO pay_order) {
        this.pay_order = pay_order;
    }

    @Override
    public String toString() {
        return "PayOrderInfo{" +
                "pay_order=" + pay_order +
                '}';
    }
}
