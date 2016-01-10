package com.futureinst.personalinfo.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.home.userinfo.checkorder.UserCheckActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;

import org.json.JSONException;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by hao on 2015/12/29.
 */
public class TransferAccountComitTip {
    public static void showChargeTip(final Activity activity, final UserDAO receivedUser, final String account){
        View view = LayoutInflater.from(activity).inflate(R.layout.view_transfer_account,null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        TextView tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        TextView tv_account = (TextView) view.findViewById(R.id.tv_account);
        tv_account.setText(NumberFormat.getCurrencyInstance(Locale.CHINA).format(Double.parseDouble(account)));
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        tv_tip.setText("向" + receivedUser.getName() + "转账");
        final Dialog dialog = DialogShow.showDialog(activity, view, Gravity.CENTER);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p2p_give_exchange(activity,receivedUser.getId()+"",account);
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //赠送未币
    private static void p2p_give_exchange (final Activity activity,String to_user_id,final String exchange) {
        final MyProgressDialog progressDialog = MyProgressDialog.getInstance(activity);
        HttpResponseUtils httpResponseUtils = HttpResponseUtils.getInstace(activity);
        HttpPostParams httpPostParams = HttpPostParams.getInstace();
        SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(activity);

        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(httpPostParams.getPostParams(PostMethod.p2p_give_exchange.name(), PostType.user_info.name(),
                        httpPostParams.p2p_give_exchange(preferenceUtil.getID() + "", preferenceUtil.getUUid(), to_user_id, exchange)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null)
                            return;
                        MyToast.getInstance().showToast(activity, exchange + "未币赠送成功！", 1);
                        Intent intent = new Intent(activity, UserCheckActivity.class);
                        intent.putExtra("transfer",true);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
    }

}
