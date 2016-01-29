package com.futureinst.charge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.userinfo.AboutUsActivity;
import com.futureinst.home.userinfo.FAQActivity;
import com.futureinst.home.userinfo.checkorder.UserCheckActivity;
import com.futureinst.model.charge.GoodsDAO;
import com.futureinst.model.charge.GoodsInfoDAO;
import com.futureinst.model.charge.PayOrderDAO;
import com.futureinst.model.charge.PayOrderInfo;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.record.UserRecordInfoDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.list.MyListView;

import org.json.JSONException;

/**
 * Created by hao on 2015/12/11.
 */
public class ChargeGoodsListFragment extends BaseFragment {
    private final int MSG_ADD_CHARGE_ORDER = -5;
    private final int MSG_CHARGE_COMPLETE = -4;
    private MyProgressDialog progressDialog ;
    private HttpPostParams httpPostParams;
    private HttpResponseUtils httpResponseUtils;
    private SharePreferenceUtil preferenceUtil;
    private MyListView lv_goods;
    private GoodsAdapter adapter;
    private ChargeDialog chargeDialog;
    private SwipeRefreshLayout swipe_container;

    private TextView tv_asset;//余额
    private ImageView iv_csCenter,iv_chargeRecord;
    private TextView tv_question;//常见问题

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_ADD_CHARGE_ORDER:
                    PayOrderDAO payOrderDAO = (PayOrderDAO) msg.obj;
                    //显示支付方式
//                    Intent intent = new Intent(getActivity(),ChargeActivity.class);
//                    intent.putExtra("order",payOrderDAO);
//                    startActivity(intent);
                    chargeDialog.setPayOrder(payOrderDAO);
                    chargeDialog.chargeDialog();
                    break;
                case MSG_CHARGE_COMPLETE:
                    progressDialog.progressDialog();
                    query_user_record();
                    break;
            }
        }
    };
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_charge_list);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        query_user_record();
        get_all_charge_goods();
    }

    private void initView() {
        progressDialog = MyProgressDialog.getInstance(getContext());
        preferenceUtil = SharePreferenceUtil.getInstance(getContext());
        httpPostParams = HttpPostParams.getInstace();
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
        chargeDialog = new ChargeDialog(getActivity());
        lv_goods = (MyListView) findViewById(R.id.lv_goods);
        adapter = new GoodsAdapter(getContext());
        lv_goods.setAdapter(adapter);
        lv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsDAO item = (GoodsDAO) adapter.getItem(position);
                if (item == null) return;
                add_pay_order(item.getId() + "");
            }
        });

        tv_asset = (TextView) findViewById(R.id.tv_asset);
        tv_question = (TextView) findViewById(R.id.tv_question);
        iv_csCenter = (ImageView) findViewById(R.id.iv_csCenter);
        iv_chargeRecord = (ImageView) findViewById(R.id.iv_chargeRecord);
        iv_csCenter.setOnClickListener(onClickListener);
        iv_chargeRecord.setOnClickListener(onClickListener);
        tv_question.setOnClickListener(onClickListener);

        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setColorSchemeColors(R.color.holo_blue_light,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query_user_record();
//                get_all_charge_goods();
            }
        });

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_question://常见问题
                    startActivity(new Intent(getContext(), FAQActivity.class));
                    break;
                case R.id.iv_csCenter://客服中心
                    Intent intentCS = new Intent(getContext(), AboutUsActivity.class);
                    intentCS.putExtra("position",2);
                    startActivity(intentCS);
                    break;
                case R.id.iv_chargeRecord://充值记录
                    Intent intentRecord = new Intent(getContext(), UserCheckActivity.class);
                    intentRecord.putExtra("charge",true);
                    startActivity(intentRecord);
                    break;
            }
        }
    };

    private void initData(UserRecordDAO dao){
        tv_asset.setText(String.format("%.2f",dao.getAsset()));
    }
    // 获取个人信息
    private void query_user_record() {
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
                        swipe_container.setRefreshing(false);
                        if (response == null)
                            return;
                        UserRecordInfoDAO userRecordInfoDAO = (UserRecordInfoDAO) response;
                        initData(userRecordInfoDAO.getUser_record());

                    }
                });
    }


    //获取可购买的商品清单
    private void get_all_charge_goods(){
        progressDialog.progressDialog();
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.get_all_charge_goods.name(), PostType.pay.name(), httpPostParams.get_all_charge_goods()),
                GoodsInfoDAO.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        GoodsInfoDAO goodsInfoDAO = (GoodsInfoDAO) response;
                        adapter.setList(goodsInfoDAO.getGoodsList());
                    }
                });
    }
    //添加支付订单
    private void add_pay_order(String goods_id){
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(httpPostParams.getPostParams(PostMethod.add_pay_order.name(), PostType.pay.name(),
                        httpPostParams.add_pay_order(preferenceUtil.getID() + "", preferenceUtil.getUUid(), goods_id)),
                PayOrderInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) return;
                        PayOrderInfo payOrderInfo = (PayOrderInfo) response;
                        if (payOrderInfo.getPay_order() == null) {
                            MyToast.getInstance().showToast(getActivity(), "订单添加失败", 0);
                            return;
                        }
                        Message message = Message.obtain();
                        message.what = MSG_ADD_CHARGE_ORDER;
                        message.obj = payOrderInfo.getPay_order();
                        handler.sendMessage(message);
                    }
                });
    }

    private static final int REQUEST_CODE_PAYMENT = 1;
    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
                handler.sendEmptyMessage(MSG_CHARGE_COMPLETE);
            }
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        int type = 0;
        if(title.equals("success")){
            str = "充值成功";
            type = 1;
        }else if(title.equals("cancel")){
            str = "充值取消";
        }else{
            if (null != msg1 && msg1.length() != 0) {
                str += "\n" + msg1;
            }
            if (null != msg2 && msg2.length() != 0) {
                str += "\n" + msg2;
            }
        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(str);
//        builder.setTitle("提示");
//        builder.setPositiveButton("确定", null);
//        builder.create().show();
        MyToast.getInstance().showToast(getActivity(),str,type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chargeDialog = null;
    }
}
