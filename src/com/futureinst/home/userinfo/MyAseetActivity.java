package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.usermodel.UserInformationDAO;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyAseetActivity extends BaseActivity {
	private UserInformationDAO userInfo;
	private TextView tv_asset;// 可交易未币
	private TextView tv_assure;// 保证金
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(R.string.asset);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_my_aseet);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView(){
		userInfo = (UserInformationDAO) getIntent().getSerializableExtra("userInfo");
		tv_asset = (TextView) findViewById(R.id.tv_asset);
		tv_assure = (TextView) findViewById(R.id.tv_assure);
		tv_asset.setText(String.format("%.1f", userInfo.getAsset()));
		tv_assure.setText(String.format("%.1f", userInfo.getAssure()));
	}
}
