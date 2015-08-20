package com.futureinst.utils;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
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
		if(url.equals("js-callterminal://userLogin/userLoginCallback")){
//			Intent intent = new Intent(context, LoginActivity.class);
//			intent.putExtra("login", true);
//			context.startActivity(intent);
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
