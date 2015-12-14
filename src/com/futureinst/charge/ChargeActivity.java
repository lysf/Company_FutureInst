package com.futureinst.charge;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.charge.PayOrderDAO;
import com.futureinst.net.HttpPath;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.PingppLog;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by hao on 2015/12/1.
 */
public class ChargeActivity extends BaseActivity implements View.OnClickListener {
    //    public static final String URL = "http://218.244.151.190/demo/charge";
    public static final String URL = HttpPath.CHARGEURL;

    private static final int REQUEST_CODE_PAYMENT = 1;
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
    private String currentAmount = "";

    private Button wechatButton;
    private Button alipayButton;
    private Button upmpButton;
    private Button bfbButton;
    private Button jdpayButton;
    private Button yfbpayButton;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge);
//        setTitle("支付");
//        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));

        order = (PayOrderDAO) getIntent().getSerializableExtra("order");
        wechatButton = (Button) findViewById(R.id.wechatButton);
        alipayButton = (Button) findViewById(R.id.alipayButton);
        upmpButton = (Button) findViewById(R.id.upmpButton);
        bfbButton = (Button) findViewById(R.id.bfbButton);
        jdpayButton = (Button) findViewById(R.id.jdpayButton);
        yfbpayButton = (Button) findViewById(R.id.yfbpayButton);

        wechatButton.setOnClickListener(ChargeActivity.this);
        alipayButton.setOnClickListener(ChargeActivity.this);
        upmpButton.setOnClickListener(ChargeActivity.this);
        bfbButton.setOnClickListener(ChargeActivity.this);
        jdpayButton.setOnClickListener(ChargeActivity.this);
        yfbpayButton.setOnClickListener(ChargeActivity.this);
        findViewById(R.id.cancelButton).setOnClickListener(ChargeActivity.this);
        PingppLog.DEBUG = true;

    }

    public void onClick(View view) {

//        String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
//        String cleanString = amountText.toString().replaceAll(replaceable, "");
//        int amount = Integer.valueOf(new BigDecimal(cleanString).toString());

        // 支付宝，微信支付，银联，百度钱包 按键的点击响应处理
        if (view.getId() == R.id.upmpButton) {
            postJson(CHANNEL_UPACP);

        } else if (view.getId() == R.id.alipayButton) {
            postJson(CHANNEL_ALIPAY);
        } else if (view.getId() == R.id.wechatButton) {
            postJson(CHANNEL_WECHAT);
        } else if (view.getId() == R.id.bfbButton) {
            postJson(CHANNEL_BFB);
        } else if (view.getId() == R.id.jdpayButton) {
            postJson(CHANNEL_JDPAY_WAP);
        } else if (view.getId() == R.id.yfbpayButton) {
            postJson(CHANNEL_YEEPAY_WAP);
        }else if(view.getId() == R.id.cancelButton){
            finish();
        }
    }


    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        wechatButton.setOnClickListener(ChargeActivity.this);
        alipayButton.setOnClickListener(ChargeActivity.this);
        upmpButton.setOnClickListener(ChargeActivity.this);
        bfbButton.setOnClickListener(ChargeActivity.this);
        jdpayButton.setOnClickListener(ChargeActivity.this);
        yfbpayButton.setOnClickListener(ChargeActivity.this);

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
            }
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        Builder builder = new Builder(ChargeActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }


    private void postJson(String channel) {
        //按键点击之后的禁用，防止重复点击
        wechatButton.setOnClickListener(null);
        alipayButton.setOnClickListener(null);
        upmpButton.setOnClickListener(null);
        bfbButton.setOnClickListener(null);
        yfbpayButton.setOnClickListener(null);
        jdpayButton.setOnClickListener(null);
        progressDialog.progressDialog();
        httpResponseUtils.postJson(
                httpPostParams.getPostParams(PostMethod.get_test_charge.name(), PostType.pay.name(),
                        httpPostParams.get_test_charge(preferenceUtil.getID() + "", preferenceUtil.getUUid(), channel)),
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        //登录成功
                        progressDialog.cancleProgress();
                        if (response == null) {
                            wechatButton.setOnClickListener(ChargeActivity.this);
                            alipayButton.setOnClickListener(ChargeActivity.this);
                            upmpButton.setOnClickListener(ChargeActivity.this);
                            bfbButton.setOnClickListener(ChargeActivity.this);
                            jdpayButton.setOnClickListener(ChargeActivity.this);
                            yfbpayButton.setOnClickListener(ChargeActivity.this);
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
                    String packageName = getPackageName();
                    ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                    intent.setComponent(componentName);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                    break;
            }
        }
    };

}
