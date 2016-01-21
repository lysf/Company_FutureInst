package com.futureinst.home.userinfo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimeLimitUtil;
import com.futureinst.utils.Utils;

public class ContactFragment extends BaseFragment {
    private ImageView iv_tell;
    private LinearLayout ll_feed;//意见反馈
    private TextView tv_contact_wechat;
    private TextView tv_contact_qq;
    private final String QQ = "288434615";
    private final String WECHAT = "futureinstitute";
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_contact);
        initView();
    }

    private void initView() {
        iv_tell = (ImageView) findViewById(R.id.iv_tell);
        ll_feed = (LinearLayout) findViewById(R.id.ll_feed);
        tv_contact_wechat = (TextView) findViewById(R.id.tv_contact_wechat);
        tv_contact_qq = (TextView) findViewById(R.id.tv_contact_qq);

        iv_tell.setOnClickListener(onClickListener);
        ll_feed.setOnClickListener(onClickListener);

        tv_contact_qq.setOnClickListener(onClickListener);
        tv_contact_qq.setOnLongClickListener(onLongClickListener);

//        tv_contact_wechat.setOnClickListener(onClickListener);
//        tv_contact_wechat.setOnLongClickListener(onLongClickListener);

    }
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.tv_contact_qq:
                    Utils.copyText(getActivity(), QQ);
                    MyToast.getInstance().showToast(getActivity(), "QQ群号已复制到粘贴板", 1);
                    return true;
                case R.id.tv_contact_wechat:
                    Utils.copyText(getActivity(), WECHAT);
                    MyToast.getInstance().showToast(getActivity(), "微信号已复制到粘贴板", 1);
                    return true;
            }
            return false;
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_tell://打电话
                    long currTime = SystemTimeUtile.getInstance(System.currentTimeMillis()).getSystemTime();
                    if(TimeLimitUtil.judgeIsWorkDay(currTime)){
                        phoneTip();
                    }else{
                        MyToast.getInstance().showToast(getActivity(),"请于工作日时间来电，或采用以下方式提交您的问题，谢谢！",0);
                    }
                    break;
                case R.id.ll_feed://意见反馈
                    startActivity(new Intent(getContext(),FeedBackActivity.class));
                    break;
                case R.id.tv_contact_qq:
                    Toast.makeText(getContext(),"长按复制QQ群号",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_contact_wechat:
                    Toast.makeText(getContext(),"长按复制微信号",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    //退出
    private void phoneTip() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_event_order_config, null, false);
        final Dialog dialog = DialogShow.showDialog(getContext(), view, Gravity.CENTER);
        TextView tv_tips = (TextView) view.findViewById(R.id.tv_configMsg);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submit.setText("拨打");
        tv_tips.setText("拨打 010-64788187 ？");
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent("android.intent.action.CALL",
                        Uri.parse("tel:010-64788187"));
                startActivity(phoneIntent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
