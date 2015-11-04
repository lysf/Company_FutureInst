package com.futureinst.comment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

public class CommentApplyActivity extends BaseActivity {
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setTitle("回复评论");
        getRightButton().setText("发送");
        getRightButton().setBackgroundColor(Color.parseColor("#F8E71C"));
        setContentView(R.layout.activity_comment_apply);
    }
}
