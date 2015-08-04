package com.futureinst.home.userinfo;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewFragment extends BaseFragment {
	private WebView webView;
	private final String ABOUT = "file:///android_asset/about.html";
	private final String MEDIA_ABOUT = "file:///android_asset/media_about.html";
	private final String TELL_US = "file:///android_asset/tell_us.html";
	private String[] urls;
	private int position = 0;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.webview);
		position = getArguments().getInt("index");
		urls = new String[]{ABOUT,MEDIA_ABOUT,TELL_US};
		webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		webView.loadUrl(urls[position]);
	}

}
