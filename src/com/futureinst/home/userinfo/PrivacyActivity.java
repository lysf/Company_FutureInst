package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

import android.os.Bundle;

public class PrivacyActivity extends BaseActivity {

	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("隐私和服务条款");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_privacy);
		initView();
	}

	private void initView() {
		
	}

}
