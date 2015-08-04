package com.futureinst.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class CustomWebViewClient extends WebViewClient{
//	private MyProgressDialog progressDialog;
//	private ProgressBar progressBar;
	public CustomWebViewClient(Context context){
//		progressDialog = MyProgressDialog.getInstance(context);
//		this.progressBar = progressBar;
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
