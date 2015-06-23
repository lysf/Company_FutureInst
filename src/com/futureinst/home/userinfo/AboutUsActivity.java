package com.futureinst.home.userinfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;

public class AboutUsActivity extends BaseActivity {
	private Button[] btns;
	private WebView webView;
	private int position = 0;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about_us);
		setTitle(getResources().getString(R.string.about_us));
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
//		setTitleBackGround(getResources().getColor(R.color.login_title_layout_back));
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	private void initView(){
		btns = new Button[3];
		btns[0] = (Button) findViewById(R.id.btn_future);
		btns[1] = (Button) findViewById(R.id.btn_media_report);
		btns[2] = (Button) findViewById(R.id.btn_contact_us);
		btns[0].setSelected(true);
		btns[0].setOnClickListener(clickListener);
		btns[1].setOnClickListener(clickListener);
		btns[2].setOnClickListener(clickListener);
		webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(false);
		webView.loadUrl("file:///android_asset/about.html");
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			btns[0].setSelected(false);
			btns[1].setSelected(false);
			btns[2].setSelected(false);
			switch (v.getId()) {
			case R.id.btn_future://未来研究所
				if(position == 0) return;
				btns[0].setSelected(true);
				webView.loadUrl("file:///android_asset/about.html");
				position = 0;
				break;
			case R.id.btn_media_report://媒体报道
				if(position == 1) return;
				btns[1].setSelected(true);
				webView.loadUrl("file:///android_asset/media_about.html");
				position = 1;
				break;
			case R.id.btn_contact_us://联系我们
				if(position == 2) return;
				btns[2].setSelected(true);
				webView.loadUrl("file:///android_asset/tell_us.html");
				position = 2;
				break;
			}
		}
	};
}
