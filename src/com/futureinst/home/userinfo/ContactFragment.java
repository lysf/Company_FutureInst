package com.futureinst.home.userinfo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.utils.DialogShow;

public class ContactFragment extends BaseFragment {
    private ImageView iv_tell;
    private LinearLayout ll_feed;//意见反馈
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_contact);
        initView();
    }

    private void initView() {
        iv_tell = (ImageView) findViewById(R.id.iv_tell);
        ll_feed = (LinearLayout) findViewById(R.id.ll_feed);
        iv_tell.setOnClickListener(onClickListener);
        ll_feed.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_tell://打电话
                    phoneTip();
                    break;
                case R.id.ll_feed://意见反馈
                    startActivity(new Intent(getContext(),FeedBackActivity.class));
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
