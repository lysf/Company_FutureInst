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
public class Charge2Activity extends BaseActivity implements View.OnClickListener {
    public static final String URL = HttpPath.CHARGEURL;
//    public static final String URL = "http://218.244.151.190/demo/charge";

    private static final int REQUEST_CODE_PAYMENT = 1;

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

    private EditText amountEditText;
    private Button wechatButton;
    private Button alipayButton;
    private Button upmpButton;
    private Button bfbButton;
    private Button jdpayButton;
    private Button yfbpayButton;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge);
        setTitle("支付");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        wechatButton = (Button) findViewById(R.id.wechatButton);
        alipayButton = (Button) findViewById(R.id.alipayButton);
        upmpButton = (Button) findViewById(R.id.upmpButton);
        bfbButton = (Button) findViewById(R.id.bfbButton);
        jdpayButton = (Button) findViewById(R.id.jdpayButton);
        yfbpayButton = (Button) findViewById(R.id.yfbpayButton);

        wechatButton.setOnClickListener(Charge2Activity.this);
        alipayButton.setOnClickListener(Charge2Activity.this);
        upmpButton.setOnClickListener(Charge2Activity.this);
        bfbButton.setOnClickListener(Charge2Activity.this);
        jdpayButton.setOnClickListener(Charge2Activity.this);
        yfbpayButton.setOnClickListener(Charge2Activity.this);
        PingppLog.DEBUG = true;

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(currentAmount)) {
                    amountEditText.removeTextChangedListener(this);
                    String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    if (cleanString.equals("") || new BigDecimal(cleanString).toString().equals("0")) {
                        amountEditText.setText(null);
                    } else {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = NumberFormat.getCurrencyInstance(Locale.CHINA).format((parsed / 100));
                        currentAmount = formatted;
                        amountEditText.setText(formatted);
                        amountEditText.setSelection(formatted.length());
                    }
                    amountEditText.addTextChangedListener(this);
                }
            }
        });
    }

    public void onClick(View view) {
        String amountText = amountEditText.getText().toString();
        if (amountText.equals("")) return;

        String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
        String cleanString = amountText.toString().replaceAll(replaceable, "");
        int amount = Integer.valueOf(new BigDecimal(cleanString).toString());

        // 支付宝，微信支付，银联，百度钱包 按键的点击响应处理
        if (view.getId() == R.id.upmpButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_UPACP, amount));
            postJson(CHANNEL_UPACP);

        } else if (view.getId() == R.id.alipayButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, amount));
            postJson(CHANNEL_ALIPAY);
        } else if (view.getId() == R.id.wechatButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, amount));
            postJson(CHANNEL_WECHAT);
        } else if (view.getId() == R.id.bfbButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_BFB, amount));
            postJson(CHANNEL_BFB);
        } else if (view.getId() == R.id.jdpayButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_JDPAY_WAP, amount));
            postJson(CHANNEL_JDPAY_WAP);
        } else if (view.getId() == R.id.yfbpayButton) {
//            new PaymentTask().execute(new PaymentRequest(CHANNEL_YEEPAY_WAP, amount));
            postJson(CHANNEL_YEEPAY_WAP);
        }
    }

//    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//
//            //按键点击之后的禁用，防止重复点击
//            wechatButton.setOnClickListener(null);
//            alipayButton.setOnClickListener(null);
//            upmpButton.setOnClickListener(null);
//            bfbButton.setOnClickListener(null);
//            yfbpayButton.setOnClickListener(null);
//        }
//
//        @Override
//        protected String doInBackground(PaymentRequest... pr) {
//
//            PaymentRequest paymentRequest = pr[0];
//            String data = null;
//            String json = new Gson().toJson(paymentRequest);
//
//            try {
//                //向Your Ping++ Server SDK请求数据
//                data = postJson(URL, new JSONObject(httpPostParams.getPostParams(PostMethod.get_test_charge.name(), PostType.pay.name(),json)).toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return data;
//        }
//
//        /**
//         * 获得服务端的charge，调用ping++ sdk。
//         */
//        @Override
//        protected void onPostExecute(String data) {
//            if(null==data){
//                showMsg("请求出错", "请检查URL", "URL无法获取charge");
//                return;
//            }
//            Log.d("-------charge---", data);
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
//            intent.setComponent(componentName);
//            intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
//            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//        }
//
//    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        wechatButton.setOnClickListener(Charge2Activity.this);
        alipayButton.setOnClickListener(Charge2Activity.this);
        upmpButton.setOnClickListener(Charge2Activity.this);
        bfbButton.setOnClickListener(Charge2Activity.this);
        jdpayButton.setOnClickListener(Charge2Activity.this);
        yfbpayButton.setOnClickListener(Charge2Activity.this);

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
        Builder builder = new Builder(Charge2Activity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);

        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
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
                            wechatButton.setOnClickListener(Charge2Activity.this);
                            alipayButton.setOnClickListener(Charge2Activity.this);
                            upmpButton.setOnClickListener(Charge2Activity.this);
                            bfbButton.setOnClickListener(Charge2Activity.this);
                            jdpayButton.setOnClickListener(Charge2Activity.this);
                            yfbpayButton.setOnClickListener(Charge2Activity.this);
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

    class PaymentRequest {
        String channel;
//        int amount;

        public PaymentRequest(String channel, int amount) {
            this.channel = channel;
//            this.amount = amount;
        }

        public PaymentRequest(String channel) {
            this.channel = channel;
//            this.amount = amount;
        }
    }

}
