package com.futureinst.model.charge;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**获得用户的所有支付订单
 * Created by hao on 2015/12/4.
 */
public class PayOrdersInfo extends BaseModel implements Serializable {
    private List<PayOrderDAO> payOrders;

    public List<PayOrderDAO> getPayOrders() {
        return payOrders;
    }

    public void setPayOrders(List<PayOrderDAO> payOrders) {
        this.payOrders = payOrders;
    }

    @Override
    public String toString() {
        return "PayOrdersInfo{" +
                "payOrders=" + payOrders +
                '}';
    }
}
