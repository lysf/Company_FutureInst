package com.futureinst.push;



import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.home.userinfo.ShoopActivity;
import com.futureinst.login.LoginActivity;
import com.futureinst.net.HttpPath;
import com.futureinst.share.JavaScriptObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class PushWebActivity extends BaseActivity {
	private WebView webView;
	private WebSettings webSettings;
    private SwipeRefreshLayout swipe_container;
	private String cookie1,cookie2 ;
	private String url ;
	private String title;
	private boolean judgeIsLogin;
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
		title = getIntent().getStringExtra("title");
		if(title.length() > 15){
			title = title.substring(0, 15) + "...";
		}
		if(!TextUtils.isEmpty(title)){
			setTitle(title);
		}
		webView = (WebView) findViewById(R.id.webView);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		cookie1 = "user_id="+preferenceUtil.getID() + HttpPath.Cookie;
		cookie2 = "uuid="+preferenceUtil.getUUid() + HttpPath.Cookie;
		webSettings = webView.getSettings();
		webSettings.setAppCacheEnabled(false);
		webSettings.setUseWideViewPort(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		synCookies(this, url);
		webView.loadUrl(url);
		webView.addJavascriptInterface(new JavaScriptObject(this), "obj");
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
        webView.setWebViewClient(new CustomWebViewClient(this));
    }
	@Override
	protected void onResume() {
		super.onResume();
		if(judgeIsLogin 
				&& cookie2.equals("uuid="+ null + HttpPath.Cookie)){
			cookie1 = "user_id="+preferenceUtil.getID() + HttpPath.Cookie;
			cookie2 = "uuid="+preferenceUtil.getUUid() + HttpPath.Cookie;
			synCookies(this, url);
			webView.loadUrl(url);
		}
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
        
    }
	
	public class CustomWebViewClient extends WebViewClient{
		private Context context;
		public CustomWebViewClient(Context context){
			this.context = context;
		}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url.contains("?id")){
				Intent intent = new Intent(context, ShoopActivity.class);
				intent.putExtra("url", url);
				context.startActivity(intent);
			}else if(url.contains("mweb/login") && !judgeIsLogin){
				Intent intent = new Intent(context, LoginActivity.class);
				intent.putExtra("login", true);
				context.startActivity(intent);
				judgeIsLogin = true;
			}else{
				view.loadUrl(url);
			}
			return true;
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
//			progressDialog.progressDialog();
		}

		public void onPageFinished(WebView view, String url) {
//			progressDialog.cancleProgress();
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
//			progressDialog.cancleProgress();
		}
		
	}
}
