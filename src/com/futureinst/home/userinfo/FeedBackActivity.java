package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.utils.MyToast;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class FeedBackActivity extends BaseActivity {
	private EditText et_feedback;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("意见反馈");
		setRight("发送");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_feedback);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		//发送意见
		if(TextUtils.isEmpty(et_feedback.getText().toString().trim())){
			MyToast.showToast(FeedBackActivity.this, "请填写您的意见和建议", 0);
			return;
		}
		
		
	}
	private void initView() {
		et_feedback = (EditText) findViewById(R.id.et_feedback);
		
		
	}

}
