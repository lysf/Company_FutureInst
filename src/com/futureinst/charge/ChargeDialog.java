package com.futureinst.charge;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.futureinst.R;
import com.futureinst.model.charge.PayOrderDAO;
import com.futureinst.net.HttpPath;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyProgressDialog;
import com.pingplusplus.android.PaymentActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hao on 2015/12/11.
 */
public class ChargeDialog {
    private static final int REQUEST_CODE_PAYMENT = 110;
    private PayOrderDAO order;

    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    private static final String CHANNEL_JDPAY_WAP = "jdpay_wap";
    /**
     * 易付宝支付渠道
     */
    private static final String CHANNEL_YEEPAY_WAP = "yeepay_wap";
    private Button wechatButton;
    private Button alipayButton;
    private Dialog dialog;

    private Activity activity;
    private MyProgressDialog progressDialog;
    private HttpResponseUtils httpResponseUtils;
    private HttpPostParams httpPostParams;
    private SharePreferenceUtil preferenceUtil;
    public ChargeDialog(Activity activity) {
        this.activity = activity;

        progressDialog = MyProgressDialog.getInstance(activity);
        httpResponseUtils = HttpResponseUtils.getInstace(activity);
        httpPostParams = HttpPostParams.getInstace();
        preferenceUtil = SharePreferenceUtil.getInstance(activity);
    }
    public void setPayOrder(PayOrderDAO order){
        this.order = order;
    }

    public void chargeDialog(){
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_charge,null,false);
        wechatButton = (Button) view.findViewById(R.id.wechatButton);
        alipayButton = (Button) view.findViewById(R.id.alipayButton);
        wechatButton.setOnClickListener(clickListener);
        alipayButton.setOnClickListener(clickListener);
        view.findViewById(R.id.cancelButton).setOnClickListener(clickListener);
        dialog = DialogShow.showDialog(activity, view, Gravity.BOTTOM);
        dialog.show();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.alipayButton://支付宝
                    postJson(CHANNEL_ALIPAY,order.getOrderNo(),order.getId()+"");
                    dialog.dismiss();
                    break;
                case R.id.wechatButton://微信
                    postJson(CHANNEL_WECHAT,order.getOrderNo(),order.getId()+"");
                    dialog.dismiss();
                    break;
                case R.id.cancelButton://
                    dialog.dismiss();
                    break;
            }
        }
    };


    private void postJson(String channel,String order_no,String order_id) {
        //按键点击之后的禁用，防止重复点击
        wechatButton.setOnClickListener(null);
        alipayButton.setOnClickListener(null);
        progressDialog.progressDialog();
        httpResponseUtils.postJson(
                httpPostParams.getPostParams(PostMethod.get_charge_for_pay_order.name(), PostType.pay.name(),
                        httpPostParams.get_charge_for_pay_order(preferenceUtil.getID() + "", preferenceUtil.getUUid(),
                                channel, order_no,order_id)),
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        //登录成功
                        progressDialog.cancleProgress();
                        if (response == null) {
                            wechatButton.setOnClickListener(clickListener);
                            alipayButton.setOnClickListener(clickListener);
                            return;
                        }
                        JSONObject jsonObject = new JSONObject((String)response);
                        Message message = Message.obtain();
                        message.what = GET_ORDER_MESSAGE;
                        message.obj = jsonObject.getJSONObject("charge").toString();
                        handler.sendMessage(message);
                    }
                });
    }
    private final int GET_ORDER_MESSAGE = -9;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_ORDER_MESSAGE:
                    String data = (String) msg.obj;
                    Intent intent = new Intent();
                    String packageName = activity.getPackageName();
                    ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                    intent.setComponent(componentName);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
                    activity.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                    break;
            }
        }
    };

}
