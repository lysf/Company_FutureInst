package com.futureinst.charge;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.charge.PayOrdersInfo;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.record.UserRecordInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;

import org.json.JSONException;

public class ChargeRecordActivity extends BaseActivity {
    private ListView lv_charge_record;
    private ChargeRecordAdapter adapter;
    private TextView tv_empty;
    private TextView tv_total_charge;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge_record);
        setTitle("充值记录");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();
        query_user_record();
        get_all_charge_goods();
    }

    private void initView() {
        tv_total_charge = (TextView) findViewById(R.id.tv_total_charge);
        lv_charge_record = (ListView) findViewById(R.id.lv_charge_record);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        adapter = new ChargeRecordAdapter(this);
        lv_charge_record.setAdapter(adapter);
    }
    private void initData(UserRecordDAO userRecordDAO){
        tv_total_charge.setText(String.format("%.2f",userRecordDAO.getAllCharge()));
    }


    //获取可购买的商品清单
    private void get_all_charge_goods(){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.get_pay_orders_for_user.name(), PostType.pay.name(),
                        httpPostParams.get_pay_orders_for_user(preferenceUtil.getID() + "", preferenceUtil.getUUid())),
                PayOrdersInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        PayOrdersInfo payOrdersInfo = (PayOrdersInfo) response;
                        if (payOrdersInfo.getPayOrders() != null) {
                            adapter.setList(payOrdersInfo.getPayOrders());
                        }
                        lv_charge_record.setEmptyView(tv_empty);
                    }
                });
    }

    // 获取个人信息
    private void query_user_record() {
        progressDialog.progressDialog();
        httpResponseUtils.postJson(
                httpPostParams.getPostParams(
                        PostMethod.query_user_record.name(),
                        PostType.user_info.name(),
                        httpPostParams.query_user_record(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
                UserRecordInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        if (response == null)
                            return;
                        UserRecordInfoDAO userRecordInfoDAO = (UserRecordInfoDAO) response;
                        initData(userRecordInfoDAO.getUser_record());

                    }
                });
    }
}
