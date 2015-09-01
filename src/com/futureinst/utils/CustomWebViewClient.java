package com.futureinst.utils;



import com.futureinst.home.userinfo.ShoopActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient{
//	private MyProgressDialog progressDialog;
//	private ProgressBar progressBar;
	private Context context;
	public CustomWebViewClient(Context context){
		this.context = context;
//		progressDialog = MyProgressDialog.getInstance(context);
//		this.progressBar = progressBar;
	}
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(url.contains("?id")){
			Intent intent = new Intent(context, ShoopActivity.class);
			intent.putExtra("url", url);
//			String title = "未来研究所";
//			if(!url.endsWith("d")){
//				title = url.substring(url.indexOf("?")+4, url.length());
//			}
//			intent.putExtra("title", title);
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
