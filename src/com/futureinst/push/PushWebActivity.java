package com.futureinst.push;


import java.util.HashMap;
import java.util.Map;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.net.HttpPath;
import com.futureinst.utils.CustomWebViewClient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class PushWebActivity extends BaseActivity {
	private WebView webView;
	private WebSettings webSettings;
	private ProgressBar progressbar;
	private String cookie1,cookie2 ;
	private String url ;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle(getResources().getString(R.string.app_name));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.view_webview);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView() {
		url = getIntent().getStringExtra("url");
		webView = (WebView) findViewById(R.id.webView);
		progressbar = (ProgressBar) findViewById(R.id.progress);
		cookie1 = "user_id="+preferenceUtil.getID() + HttpPath.Cookie;
		cookie2 = "uuid="+preferenceUtil.getUUid() + HttpPath.Cookie;
		synCookies(this, url);
		webSettings = webView.getSettings();
		webSettings.setAppCacheEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		Map<String, String> cookie = new HashMap<String, String>();
		webView.loadUrl(url);
		webView.setWebViewClient(new CustomWebViewClient(this));
		webView.setWebChromeClient(new ChromeClient());
	}
	private void synCookies(Context context, String url) {  
	     CookieSyncManager.createInstance(context);  
	     CookieManager cookieManager = CookieManager.getInstance();  
	    cookieManager.setAcceptCookie(true);  
	     cookieManager.removeAllCookie();//移除  
	     cookieManager.setCookie(url,cookie1);
	     cookieManager.setCookie(url,cookie2);
	     CookieSyncManager.getInstance().sync();  
	 } 
	class ChromeClient extends WebChromeClient{ 
        @Override 
        public void onProgressChanged(WebView view, int newProgress) { 

            //动态在标题栏显示进度条 
        	if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress); 

        } 
        
    }
}
