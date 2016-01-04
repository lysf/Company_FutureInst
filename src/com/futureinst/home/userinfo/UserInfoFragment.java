package com.futureinst.home.userinfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.charge.ChargeActivity;
import com.futureinst.charge.ChargeGoodsListActivity;
import com.futureinst.db.PushMessageCacheUtil;
import com.futureinst.global.Content;
import com.futureinst.home.HomeActivity;
import com.futureinst.home.article.AritlceActivity;
import com.futureinst.home.hold.HoldingActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.basemodel.UpFileDAO;
import com.futureinst.model.dailytask.DailyTaskInfoDAO;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.personalinfo.other.PersonalAttendActivity;
import com.futureinst.personalinfo.other.PersonalAttentionActivity;
import com.futureinst.personalinfo.other.PersonalRecordActivity;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.todaytask.TodayTaskActivity;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.TaskTipUtil;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.Utils;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

public class UserInfoFragment extends BaseFragment {
    private UserRecordDAO userInformationDAO;
    private MyProgressDialog progressDialog;
    private SharePreferenceUtil preferenceUtil;
    private HttpPostParams httpPostParams;
    private HttpResponseUtils httpResponseUtils;
    private RoundedImageView iv_headImag;
    private TextView tv_userName, tv_description;
    private TextView tv_message_count;
    private ImageView iv_message;
    private TableRow[] tableRows;
    private PushMessageCacheUtil messageCacheUtil;
    private TextView tv_useableIcon, tv_depositCash, tv_useableSaleIcon, tv_ranking, tv_attend, tv_attention;
    private boolean isStart;
    private TextView  tv_order;
    private BroadcastReceiver receiver;
    private LinearLayout ll_modify;
    private TextView tv_win;
    private TextView tv_follow_add;
    private ImageView iv_ranking;
    private ImageView iv_set;
    private ImageView iv_todayTask;
    private ImageView iv_daily;

    private Switch btn_switch;


    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_userinfo);
        initView();
        setClickListener();
        query_user_record();
        getMessageCount();
        isStart = true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getCurrentTab() == 3
                && isStart) {
            query_user_record();
            query_user_daily_task();
            getMessageCount();
        }
        super.onResume();
    }

    // 获取未读消息数量
    private void getMessageCount() {
        int count = messageCacheUtil.getUnReadMessage();
        if (count > 0) {
            tv_message_count.setText(count + "");
            tv_message_count.setVisibility(View.VISIBLE);
        } else {
            tv_message_count.setText("0");
            tv_message_count.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {

        messageCacheUtil = PushMessageCacheUtil.getInstance(getContext());
        progressDialog = MyProgressDialog.getInstance(getContext());
        preferenceUtil = SharePreferenceUtil.getInstance(getContext());
        httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
        httpPostParams = HttpPostParams.getInstace();
        userInformationDAO = new UserRecordDAO();
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_message_count = (TextView) findViewById(R.id.tv_message_count);
        tv_useableSaleIcon = (TextView) findViewById(R.id.tv_useableSaleIcon);
        tv_attention = (TextView) findViewById(R.id.tv_attention);
        tv_attend = (TextView) findViewById(R.id.tv_attend);
        tv_order = (TextView) findViewById(R.id.tv_order);
        iv_message = (ImageView) findViewById(R.id.iv_message);
        iv_headImag = (RoundedImageView) findViewById(R.id.iv_headImg);
        tv_follow_add = (TextView) findViewById(R.id.tv_follow_add);
        iv_ranking = (ImageView) findViewById(R.id.iv_ranking);
        iv_set = (ImageView) findViewById(R.id.iv_set);
        iv_todayTask = (ImageView) findViewById(R.id.iv_today_task_message);
        iv_daily = (ImageView) findViewById(R.id.iv_daily);
        if(preferenceUtil.getDailyTaskClick()){
            iv_daily.setVisibility(View.INVISIBLE);
        }else{
            iv_daily.setVisibility(View.VISIBLE);
        }


        tableRows = new TableRow[5];
        ll_modify = (LinearLayout) findViewById(R.id.ll_modify);
        tableRows[0] = (TableRow) findViewById(R.id.tableRow0);
        tableRows[1] = (TableRow) findViewById(R.id.tableRow1);
        tableRows[2] = (TableRow) findViewById(R.id.tableRow2);
        tableRows[3] = (TableRow) findViewById(R.id.tableRow3);
        tableRows[4] = (TableRow) findViewById(R.id.tableRow_todayTask);

        tv_useableIcon = (TextView) findViewById(R.id.tv_useableIcon);
        tv_depositCash = (TextView) findViewById(R.id.tv_depositCash);

        tv_ranking = (TextView) findViewById(R.id.tv_ranking);
        tv_win = (TextView) findViewById(R.id.tv_win);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("modifyName")) {
                    String value = intent.getStringExtra("value");
                    tv_userName.setText(value);
                } else if (action.equals("modifyDescription")) {
                    String value = intent.getStringExtra("value");
                    tv_description.setText(value);
                } else if (action.equals("newPushMessage")) {
                    getMessageCount();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("modifyName");
        filter.addAction("modifyDescription");
        filter.addAction("newPushMessage");
        getContext().registerReceiver(receiver, filter);

        btn_switch = (Switch) findViewById(R.id.btn_switch);
        btn_switch.setChecked(preferenceUtil.getServerOnline());
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    preferenceUtil.setServer(true);
                }else{
                    preferenceUtil.setServer(false);
                }
            }
        });

    }

    // 初始化视图
    private void initData(UserRecordDAO userInfo) {
        if (userInfo.getUser().getPeertoNum() > preferenceUtil.getFollow()) {
            tv_follow_add.setText("+" + (userInfo.getUser().getPeertoNum() - preferenceUtil.getFollow()));
            tv_follow_add.setVisibility(View.VISIBLE);
        } else {
            tv_follow_add.setVisibility(View.GONE);
        }
        preferenceUtil.setFollow(userInfo.getUser().getPeertoNum());



        if (!TextUtils.isEmpty(userInfo.getUser().getName())) {
            tv_userName.setText(userInfo.getUser().getName());
        }
        if (!TextUtils.isEmpty(userInfo.getUser().getDescription())) {
            tv_description.setText(userInfo.getUser().getDescription());
        }
        tv_useableIcon.setText(String.format("%.2f", userInfo.getAsset()));
        tv_depositCash.setText(String.format("%.2f", userInfo.getAssure()));
        tv_useableSaleIcon.setText(String.format("%.2f", userInfo.getExchange()));
        tv_ranking.setText(userInfo.getRank() + "");
        if (userInfo.getLastRank() > userInfo.getRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_down));
        } else if (userInfo.getLastRank() < userInfo.getRank()) {
            iv_ranking.setImageDrawable(getResources().getDrawable(R.drawable.iv_up));
        } else {
            iv_ranking.setVisibility(View.INVISIBLE);
        }
        if (userInfo.getRank() == 0) {
            tv_ranking.setText("-");
            iv_ranking.setVisibility(View.INVISIBLE);
        }
        tv_attend.setText("关注 " + userInfo.getUser().getTopeerNum() + "");
        tv_attention.setText("被关注 " + userInfo.getUser().getPeertoNum() + "");

        String time = "上次下单时间: ";
        if (!TextUtils.isEmpty(userInfo.getUser().getLastOrderTime())) {
            time += TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(userInfo.getUser().getLastOrderTime()));
        } else {
            time += "暂无数据";
        }
        tv_order.setText(time);
        if (iv_headImag.getTag() == null || !iv_headImag.getTag().equals(userInfo.getUser().getHeadImage())) {
            ImageLoader.getInstance().displayImage(userInfo.getUser().getHeadImage(), iv_headImag, ImageLoadOptions.getOptions(R.drawable.logo));
            iv_headImag.setTag(userInfo.getUser().getHeadImage());
        }

        if (userInfo.getAllEvent() == 0) {
            tv_win.setText("0%");
        } else {
            tv_win.setText((int) (userInfo.getGainEvent() * 1.0f / userInfo.getAllEvent() * 100) + "%");
        }
    }

    private void setClickListener() {
        findViewById(R.id.ll_charge).setOnClickListener(clickListener);
        iv_message.setOnClickListener(clickListener);
        iv_set.setOnClickListener(clickListener);
        tv_attend.setOnClickListener(clickListener);
        tv_attention.setOnClickListener(clickListener);
        tableRows[0].setOnClickListener(clickListener);
        tableRows[1].setOnClickListener(clickListener);
        tableRows[2].setOnClickListener(clickListener);
        tableRows[3].setOnClickListener(clickListener);
        tableRows[4].setOnClickListener(clickListener);

        ll_modify.setOnClickListener(clickListener);
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_modify://修改用户信息
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intentName = new Intent(getActivity(), ModifyPersoanlInfoActivity.class);
                    intentName.putExtra("user", userInformationDAO.getUser());
                    startActivity(intentName);
                    break;
                case R.id.ll_charge:// 充值
                    startActivity(new Intent(getContext(), ChargeGoodsListActivity.class));
                    break;
                case R.id.iv_message:// 消息
                    startActivity(new Intent(getActivity(), PushMessageActivity.class));
                    tv_message_count.setVisibility(View.INVISIBLE);
                    break;
                case R.id.iv_set://设置
                    Intent setIntent = new Intent(getActivity(),HomeSetActivity.class);
                    setIntent.putExtra("userInfo",userInformationDAO);
                    startActivity(setIntent);
                    break;
                case R.id.tableRow_todayTask://今日任务
                    preferenceUtil.setDailyTaskClick();
                    iv_daily.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getActivity(), TodayTaskActivity.class));
                    break;
                case R.id.tableRow0://预测中事件
                    Intent intent0 = new Intent(getActivity(), HoldingActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.tableRow1://战绩
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intent1 = new Intent(getActivity(), PersonalRecordActivity.class);
                    intent1.putExtra("id", preferenceUtil.getID() + "");
                    intent1.putExtra("isMe", true);
                    startActivity(intent1);
                    break;
                case R.id.tableRow2:// 对账单
                    startActivity(new Intent(getActivity(), UserCheckActivity.class));
                    break;
                case R.id.tableRow3://文章
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intentArticle = new Intent(getActivity(), AritlceActivity.class);
                    intentArticle.putExtra("from",true);
                    intentArticle.putExtra("user",userInformationDAO);
                    startActivity(intentArticle);
                    break;
                case R.id.tv_attend://我关注的人
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intent3 = new Intent(getActivity(), PersonalAttentionActivity.class);
                    intent3.putExtra("id", preferenceUtil.getID() + "");
                    intent3.putExtra("isMe", true);
                    startActivity(intent3);
                    break;
                case R.id.tv_attention://关注我的人
                    if(userInformationDAO == null ){
                        return;
                    }
                    Intent intent4 = new Intent(getActivity(), PersonalAttendActivity.class);
                    intent4.putExtra("id", preferenceUtil.getID() + "");
                    intent4.putExtra("isMe", true);
                    tv_follow_add.setVisibility(View.GONE);
                    startActivity(intent4);
                    break;

            }
        }
    };



    // 获取用户信息
    private void query_user_record() {
		progressDialog.progressDialog();
        httpResponseUtils
                .postJson(
                        httpPostParams.getPostParams(PostMethod.query_user_record.name(), PostType.user_info.name(),
                                httpPostParams.query_user_record(preferenceUtil.getID() + "",
                                        preferenceUtil.getUUid())),
                        UserInformationInfo.class, new PostCommentResponseListener() {
                            @Override
                            public void requestCompleted(Object response) throws JSONException {
                                progressDialog.cancleProgress();
                                if (response == null)
                                    return;
                                UserInformationInfo userInformationInfo = (UserInformationInfo) response;
                                userInformationDAO = userInformationInfo.getUser_record();
                                initData(userInformationDAO);
                            }
                        });
    }

    //查询今日任务
    private void query_user_daily_task() {
        if(TextUtils.isEmpty(preferenceUtil.getUUid())){
            return;
        }
        httpResponseUtils.postJson(
                httpPostParams.getPostParams(
                        PostMethod.query_user_daily_task.name(),
                        PostType.daily_task.name(),
                        httpPostParams.query_user_daily_task(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
                DailyTaskInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        if (response == null)
                            return;
                        DailyTaskInfoDAO dailyTaskInfo = (DailyTaskInfoDAO) response;
                        if(TaskTipUtil.isShowTip(dailyTaskInfo)){
                            iv_todayTask.setVisibility(View.VISIBLE);
                        }else{
                            iv_todayTask.setVisibility(View.INVISIBLE);
                        }

                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
        }
    }


}
