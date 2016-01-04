package com.futureinst.personalinfo.other;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyToast;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

/**
 * Created by hao on 2015/12/29.
 */
public class TransferAccountActivity extends BaseActivity{
    private UserInformationInfo userInfo;
    private UserInformationInfo myInfo;
    private RoundedImageView iv_me,iv_other;
    private TextView tv_me_assure,tv_other_name;
    private EditText et_transfer_account;
    private Button btn_transfer_accounts;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer_account);
        setTitle("转账");
        getLeftImageView().setImageResource(R.drawable.back);
        initView();
        query_user_record();
        initOther(userInfo.getUser_record().getUser());
    }

    private void initView() {
        userInfo = (UserInformationInfo) getIntent().getSerializableExtra("userInfo");
        iv_me = (RoundedImageView) findViewById(R.id.iv_me);
        iv_other = (RoundedImageView) findViewById(R.id.iv_other);
        tv_me_assure = (TextView) findViewById(R.id.tv_me);
        tv_other_name = (TextView) findViewById(R.id.tv_other_name);
        et_transfer_account = (EditText) findViewById(R.id.et_transfer_account);
        btn_transfer_accounts = (Button) findViewById(R.id.btn_transfer_accounts);
        btn_transfer_accounts.setOnClickListener(clickListener);
    }

    private void initOther(UserDAO user){
        if(user == null) return;
        ImageLoader.getInstance().displayImage(user.getHeadImage(),iv_other, ImageLoadOptions.getOptions(R.drawable.logo));
        tv_other_name.setText(user.getName());
    }

    private void initMyInfo(UserInformationInfo myInfo) {
        if(myInfo == null) return;
        ImageLoader.getInstance().displayImage(myInfo.getUser_record().getUser().getHeadImage(),iv_me, ImageLoadOptions.getOptions(R.drawable.logo));
        tv_me_assure.setText(String.format("%.2f", myInfo.getUser_record().getExchange()));//可消费未币
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_transfer_accounts:
                    String account = et_transfer_account.getText().toString().trim();
                    if(judgeAccount(account)){
                        TransferAccountComitTip.showChargeTip(TransferAccountActivity.this,
                                userInfo.getUser_record().getUser(),account);
                    }
                    break;
            }
        }
    };

    private boolean judgeAccount(String account){
        if(TextUtils.isEmpty(account)){
            MyToast.getInstance().showToast(this,"请输入要转账的未币数额",0);
            return false;
        }
        if(Double.parseDouble(account) > myInfo.getUser_record().getExchange()){
            MyToast.getInstance().showToast(this,"您的可消费未币不足",0);
            return  false;
        }
        if(Double.parseDouble(account) < 2000){
            MyToast.getInstance().showToast(this,"最低转账金额需为2000未币",0);
            return  false;
        }
        return true;
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
                UserInformationInfo.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null){
                            finish();
                            return;
                        }
                        myInfo = (UserInformationInfo) response;
                        if(myInfo != null){
                            initMyInfo(myInfo);
                        }

                    }


                });
    }
}
