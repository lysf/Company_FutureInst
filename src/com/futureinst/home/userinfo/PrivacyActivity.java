package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PrivacyActivity extends BaseActivity {
	private WebView webView;
	private WebSettings webSetting;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("隐私和服务条款");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.activity_privacy);
		initView();
	}

	private void initView() {
		webView = (WebView) findViewById(R.id.webView);
		webSetting = webView.getSettings();
		webSetting.setAppCacheEnabled(true);
		webSetting.setSupportZoom(false);
		webSetting.setBuiltInZoomControls(false);
		webView.loadUrl("file:///android_asset/privacy.html");
		
	}

}
