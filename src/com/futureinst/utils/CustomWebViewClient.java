package com.futureinst.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient{
	private MyProgressDialog progressDialog;
	public CustomWebViewClient(Context context){
		progressDialog = MyProgressDialog.getInstance(context);
	}
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
			view.loadUrl(url);
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
