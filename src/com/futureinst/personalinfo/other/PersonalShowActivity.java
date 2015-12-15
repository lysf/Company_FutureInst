package com.futureinst.personalinfo.other;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.global.Premit;
import com.futureinst.home.article.AritlceActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class PersonalShowActivity extends BaseActivity {
    private ImageView iv_headImg;
    private TextView tv_userName, tv_description, tv_attention, tv_attend, tv_order, tv_ranking;
    private TextView tv_total_assure;
    private Button btn_attention;
    private TableRow[] tows;
    private String id;
    private UserInformationInfo userInfo;
    private ImageView[] iv_permit;
    private TextView tv_win;
    private Button btn_transfer_accounts;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_show);
        setTitle(R.string.show_homepage);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        initView();
        peer_info_query_user_record(id);
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        iv_headImg = (ImageView) findViewById(R.id.iv_headImg);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_attention = (TextView) findViewById(R.id.tv_attention);//他关注的人
        tv_attend = (TextView) findViewById(R.id.tv_attend);//关注他的人
        tv_order = (TextView) findViewById(R.id.tv_order);//上次下单时间
        tv_ranking = (TextView) findViewById(R.id.tv_ranking);
        tv_win = (TextView) findViewById(R.id.tv_win);//胜率
        btn_transfer_accounts = (Button) findViewById(R.id.btn_transfer_accounts);//转账
        tows = new TableRow[3];
        tv_total_assure = (TextView) findViewById(R.id.tv_total_assure);
        btn_attention = (Button) findViewById(R.id.btn_attention);
        tows[0] = (TableRow) findViewById(R.id.tableRow0);
        tows[1] = (TableRow) findViewById(R.id.tableRow1);
        tows[2] = (TableRow) findViewById(R.id.tableRow_article);
        btn_attention.setOnClickListener(clickListener);
        tows[0].setOnClickListener(clickListener);
        tows[1].setOnClickListener(clickListener);
        tows[2].setOnClickListener(clickListener);

        iv_permit = new ImageView[2];
        iv_permit[0] = (ImageView) findViewById(R.id.iv_1);
        iv_permit[1] = (ImageView) findViewById(R.id.iv_2);
        tv_attention.setOnClickListener(clickListener);
        tv_attend.setOnClickListener(clickListener);
        btn_transfer_accounts.setOnClickListener(clickListener);
    }

    private void initUserinfo(UserRecordDAO dao) {
        ImageLoader.getInstance().displayImage(dao.getUser().getHeadImage(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
        tv_userName.setText(dao.getUser().getName());
        tv_description.setText(dao.getUser().getDescription());
        tv_total_assure.setText(String.format("%.2f", dao.getAsset()));
        tv_ranking.setText(dao.getRank() + "");
        if (dao.getAllEvent() == 0) {
            tv_win.setText("0%");
        } else {
            tv_win.setText((int) (dao.getGainEvent() * 1.0f / dao.getAllEvent() * 100) + "%");
        }

        String time = "上次下单时间: ";
        if (!TextUtils.isEmpty(dao.getUser().getLastOrderTime())) {
            time += TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(dao.getUser().getLastOrderTime()));
        } else {
            time += "暂无数据";
        }
        tv_order.setText(time);
        tv_attention.setText("被关注" + dao.getUser().getPeertoNum() + "");//他关注的人
        tv_attend.setText("关注" + dao.getUser().getTopeerNum() + "");//关注他的人
        if (userInfo.getRelation().equals("none")) {
            btn_attention.setText("关注");
        } else {
            btn_attention.setText("取消关注");
        }

        if (!judgePremit(Premit.order, userInfo.getPermit_list())) {
            iv_permit[0].setVisibility(View.INVISIBLE);
        } else {
            iv_permit[0].setVisibility(View.VISIBLE);
        }

        if (!judgePremit(Premit.gain, userInfo.getPermit_list())) {
            iv_permit[1].setVisibility(View.INVISIBLE);
        } else {
            iv_permit[1].setVisibility(View.VISIBLE);
        }

//		if(!judgePremit(Premit.follow_me, userInfo.getPermit_list())){
//			iv_permit[2].setVisibility(View.INVISIBLE);
//		}else{
//			iv_permit[2].setVisibility(View.VISIBLE);
//		}
//
//		if(!judgePremit(Premit.me_follow, userInfo.getPermit_list())){
//			iv_permit[3].setVisibility(View.INVISIBLE);
//		}else{
//			iv_permit[3].setVisibility(View.VISIBLE);
//		}


    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tableRow0://预测中事件
                    if (!judgePremit(Premit.order, userInfo.getPermit_list())) {
                        showToast(message(userInfo.getPeerPermitMap().getOrder()));
                        return;
                    }

                    Intent intent0 = new Intent(PersonalShowActivity.this, PersonalForecastActivity.class);
                    intent0.putExtra("id", id);
                    startActivity(intent0);

                    break;
                case R.id.tableRow1://他的战绩
                    if (!judgePremit(Premit.gain, userInfo.getPermit_list())) {
                        showToast(message(userInfo.getPeerPermitMap().getGain()));
                        return;
                    }
                    Intent intent1 = new Intent(PersonalShowActivity.this, PersonalRecordActivity.class);
                    intent1.putExtra("id", id);
                    intent1.putExtra("record", userInfo.getUser_record());
                    startActivity(intent1);
                    break;
                case R.id.tableRow2://他的评论
                    Intent intent2 = new Intent(PersonalShowActivity.this, PersonalCommentActivity.class);
                    intent2.putExtra("id", id);
                    startActivity(intent2);
                    break;
                case R.id.tv_attend://他关注的人
                    if (!judgePremit(Premit.me_follow, userInfo.getPermit_list())) {
                        showToast(message(userInfo.getPeerPermitMap().getMe_follow()));
                        return;
                    }
                    Intent intent3 = new Intent(PersonalShowActivity.this, PersonalAttentionActivity.class);
                    intent3.putExtra("id", id);
                    startActivity(intent3);
                    break;
                case R.id.tv_attention://关注他的人
                    if (!judgePremit(Premit.follow_me, userInfo.getPermit_list())) {
                        showToast(message(userInfo.getPeerPermitMap().getFollow_me()));
                        return;
                    }
                    Intent intent4 = new Intent(PersonalShowActivity.this, PersonalAttendActivity.class);
                    intent4.putExtra("id", id);
                    startActivity(intent4);
                    break;
                case R.id.btn_attention://关注
                    if (userInfo.getRelation().equals("none")) {
                        operation_peer_follow(id, "follow");
                    } else {
                        operation_peer_follow(id, "unfollow");
                    }
                    break;
                case R.id.tableRow_article:
                    Intent intentArticle = new Intent(PersonalShowActivity.this, AritlceActivity.class);
                    intentArticle.putExtra("from", false);
                    intentArticle.putExtra("user", userInfo.getUser_record().getUser());
                    startActivity(intentArticle);
                    break;
                case R.id.btn_transfer_accounts://转帐

                    break;
            }

        }
    };

    private String message(String permit) {
        if ("none".equals(permit)) {
            return "他不开放查看";
        } else if ("follow".equals(permit)) {
            return "请先关注他再查看";
        }
        return "您无权查看";
    }

    //判断该用户是否有权限查看该项
    public boolean judgePremit(String permit, String[] permits) {
        if (permits == null) {
//			MyToast.showToast(this, "您无权查看", 0);
            return false;
        }
        for (String p : permits) {
            if (p.equals(permit))
                return true;
        }
//		MyToast.showToast(this, "您无权查看", 0);
        return false;
    }

    //关注操作 operation(操作类型): follow(添加)，unfollow(取消)
    private void operation_peer_follow(String peer_id, final String operation) {
        progressDialog.progressDialog();
        Content.isPull = true;
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.operation_peer_follow.name(), PostType.peer.name(),
                        httpPostParams.operation_peer_follow(preferenceUtil.getUUid(), preferenceUtil.getID() + "", peer_id, operation)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        Content.isPull = false;
                        if (response == null)
                            return;
                        String message = "关注成功";
                        peer_info_query_user_record(id);
                        if (operation.equals("unfollow")) {
                            message = "您已取消关注";
                            btn_attention.setText("关注");
                            userInfo.setRelation("none");
                        } else {
                            btn_attention.setText("取消关注");
                            userInfo.setRelation("follow");
                        }
                        sendBroadcast(new Intent("relation"));
                        MyToast.getInstance().showToast(PersonalShowActivity.this, message, 1);
                    }
                });
    }

    //查看基本信息
    private void peer_info_query_user_record(String peer_id) {
        progressDialog.progressDialog();
        Content.isPull = true;
        httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.peer_info_query_user_record.name(), PostType.peer_info.name(),
                        httpPostParams.peer_info_query_user_record(preferenceUtil.getUUid(), preferenceUtil.getID() + "", peer_id)),
                UserInformationInfo.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        Content.isPull = false;
                        if (response == null)
                            return;
                        UserInformationInfo userInformationInfo = (UserInformationInfo) response;
                        userInfo = userInformationInfo;
                        initUserinfo(userInformationInfo.getUser_record());
                    }
                });
    }

    //赠送未币
    private void p2p_give_exchange (String to_user_id,final String exchange) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(httpPostParams.getPostParams(PostMethod.p2p_give_exchange.name(), PostType.user_info.name(),
                        httpPostParams.p2p_give_exchange(preferenceUtil.getID() + "", preferenceUtil.getUUid(), to_user_id,exchange)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null)
                            return;
                        MyToast.getInstance().showToast(PersonalShowActivity.this,exchange+"未币赠送成功！",1);
                    }
                });
    }


}
