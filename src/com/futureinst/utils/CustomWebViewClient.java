package com.futureinst.utils;



import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.userinfo.ShoopActivity;
import com.futureinst.login.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
		}else if(url.contains("mweb/login")){
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("login", true);
			context.startActivity(intent);
		}else{
			view.loadUrl(url);
		}
		return true;
	}

	public void onPageStarted(WebView view, String url, Bitmap favicon) {
//		progressDialog.progressDialog();
	}

	public void onPageFinished(WebView view, String url) {
//		progressDialog.cancleProgress();
	}

	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
//		progressDialog.cancleProgress();
	}
	
}
