package com.futureinst.home.eventdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.homeeventmodel.ReferenceDAO;
import com.futureinst.utils.CustomWebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class RefrenceNewsActivity extends BaseActivity {
	private ReferenceDAO referenceDAO;
	private WebView webView;
	private WebSettings webSettings;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_webview);
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		referenceDAO = (ReferenceDAO) getIntent().getSerializableExtra("news");
		String title = referenceDAO.getTitle();
		if(title.length()>8){
			title = title.substring(0, 8)+"...";
		}
		setTitle(title);
		webView = (WebView) findViewById(R.id.webView);
		webSettings = webView.getSettings();
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webView.loadUrl(referenceDAO.getUrl());
		webView.setWebViewClient(new CustomWebViewClient(this));
	}

}
