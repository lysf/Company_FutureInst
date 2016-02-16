package com.futureinst.home.userinfo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.utils.ActivityManagerUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.Utils;
import com.igexin.sdk.PushManager;

/**
 * Created by hao on 2015/11/24.
 */
public class HomeSetActivity extends BaseActivity{
     private TableRow[] tableRows;
    private TextView tv_version;
    private UserRecordDAO userInformationDAO;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_set);
        ActivityManagerUtil.addActivity(this);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setTitle("设置");
        initView();
    }
    private void initView(){
        userInformationDAO = (UserRecordDAO) getIntent().getSerializableExtra("userInfo");
        tableRows = new TableRow[6];
        tableRows[0] = (TableRow) findViewById(R.id.tableRow5);
        tableRows[1] = (TableRow) findViewById(R.id.tableRow6);
        tableRows[2] = (TableRow) findViewById(R.id.tableRow7);
        tableRows[3] = (TableRow) findViewById(R.id.tableRow8);
        tableRows[4] = (TableRow) findViewById(R.id.tableRow9);
        tableRows[5] = (TableRow) findViewById(R.id.tableRow10);
        tv_version = (TextView) findViewById(R.id.tv_version);

        String version = "V" + Utils.getVersionName(this) + "   Build(" + Utils.getVersionCode(this) + ")";
        tv_version.setText(version);
        tableRows[0].setOnClickListener(clickListener);
        tableRows[1].setOnClickListener(clickListener);
        tableRows[2].setOnClickListener(clickListener);
        tableRows[3].setOnClickListener(clickListener);
        tableRows[4].setOnClickListener(clickListener);
        tableRows[5].setOnClickListener(clickListener);
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tableRow5://隐私
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intent = new Intent(HomeSetActivity.this, SecrtActivity.class);
                    intent.putExtra("permit", userInformationDAO.getUser().getPermitMap());
                    startActivity(intent);
                    break;
                case R.id.tableRow6:// 常见问题
                    startActivity(new Intent(HomeSetActivity.this, FAQActivity.class));
                    break;
                case R.id.tableRow7://意见反馈
                    startActivity(new Intent(HomeSetActivity.this, FeedBackActivity.class));
                    break;
                case R.id.tableRow8:// 隐私和服务条款
                    startActivity(new Intent(HomeSetActivity.this, PrivacyActivity.class));
                    break;
                case R.id.tableRow9:// 关于我们
                    startActivity(new Intent(HomeSetActivity.this, AboutUsActivity.class));
                    break;
                case R.id.tableRow10:// 退出登录
                    loginOut();
                    break;
            }
        }
    };

    //退出
    private void loginOut() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_event_order_config, null, false);
        final Dialog dialog = DialogShow.showDialog(this, view, Gravity.CENTER);
        TextView tv_tips = (TextView) view.findViewById(R.id.tv_configMsg);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        tv_tips.setText(getResources().getString(R.string.login_out_tip));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.isUpdate = false;
                PushManager.getInstance().stopService(HomeSetActivity.this.getApplicationContext());
                Intent intent = new Intent(HomeSetActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                preferenceUtil.setUUid(null);
                preferenceUtil.setID(0L);
                preferenceUtil.setName("");
                preferenceUtil.setGender(0);
                preferenceUtil.setFollow(0);

                startActivity(intent);
                ActivityManagerUtil.finishActivity();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.finishOtherZctivity(this);
    }
}
