package com.futureinst.home.userinfo;



import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.net.HttpPath;
import com.futureinst.share.JavaScriptObject;
import com.futureinst.utils.ActivityManagerUtil;
import com.futureinst.utils.CustomWebViewClient;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class ShoopActivity extends BaseActivity {
	private WebView webView;
    private SwipeRefreshLayout swipe_container;
	private WebSettings webSettings;
	private ProgressBar progressbar;
	private String cookie1,cookie2 ;
	private String url ;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		ActivityManagerUtil.addActivity(this);
		setTitle("未来福利社");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setContentView(R.layout.view_webview);
		initView();
	}
	private void initView() {
		url = getIntent().getStringExtra("url");
//		url = "http://qxu1142100261.my3w.com/futureinst/src/main/webapp/mweb/gift/test_ad1.html";
		webView = (WebView) findViewById(R.id.webView);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		progressbar = (ProgressBar) findViewById(R.id.progress);
		cookie1 = "user_id="+preferenceUtil.getID() + HttpPath.Cookie;
		cookie2 = "uuid="+preferenceUtil.getUUid() + HttpPath.Cookie;
		synCookies(this, url);
		webSettings = webView.getSettings();
		webSettings.setAppCacheEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavaScriptObject(this), "obj");
		webSettings.setLoadWithOverviewMode(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new CustomWebViewClient(this));
        swipe_container.setColorSchemeColors(R.color.holo_blue_light,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });
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
	        setTitle(title);
	        }
	    }
	 
}
