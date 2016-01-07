package com.futureinst.home;



import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.net.HttpPath;
import com.futureinst.share.JavaScriptObject;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.CustomWebViewClient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class ShoopFragment extends BaseFragment {
	private WebView webView;
    private SwipeRefreshLayout swipe_container;
    private WebSettings webSettings;
	private String cookie1,cookie2 ;
	private String url ;
	private long time;
	private SharePreferenceUtil preferenceUtil;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.view_webview);
		initView();
	}
	private void initView() {
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		time = System.currentTimeMillis();
		url = HttpPath.SHOP;
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        webView = (WebView) findViewById(R.id.webView);
		cookie1 = "user_id="+preferenceUtil.getID() + HttpPath.Cookie;
		cookie2 = "uuid="+preferenceUtil.getUUid() + HttpPath.Cookie;
        synCookies(getContext(), url);
		webSettings = webView.getSettings();
		webSettings.setAppCacheEnabled(false);
//		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setUseWideViewPort(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavaScriptObject(getContext()), "obj");
		webSettings.setLoadWithOverviewMode(true);
		webView.loadUrl(url);

		webView.setWebChromeClient(new ChromeClient());
        swipe_container.setColorSchemeColors(R.color.holo_blue_light,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });
        webView.setWebViewClient(new CustomWebViewClient(getContext()));
    }

    @Override
	public void onResume() {
		super.onResume();
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
                    //隐藏进度条
                    swipe_container.setRefreshing(false);
                } else {
                    if (!swipe_container.isRefreshing())
                        swipe_container.setRefreshing(true);
                }
                super.onProgressChanged(view, newProgress);

	        } 
	        @Override
	        public void onReceivedTitle(WebView view, String title) {
	        super.onReceivedTitle(view, title);
//	        setTitle(title);
	        }
	    }
	 
}
