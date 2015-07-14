package com.futureinst.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.home.eventdetail.MyThread;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("HandlerLeak")
public class EventsFragment extends BaseFragment {
	private ImageLoader imageLoader;
	private QueryEventDAO item;
	private MyThread myThread;
	private ImageView imageView;
	private DisplayImageOptions imageLoadOptions;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
				if(msg.what == 1){
					Long time = msg.getData().getLong("time");
					TextView tv_time = (TextView)msg.obj;
					tv_time.setText(LongTimeUtil.longTimeUtil(time));
				}
		};
	};
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		item = (QueryEventDAO) getArguments().getSerializable("event");
		
	}
	
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.item_home_pager);
		initView();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(!isVisibleToUser){
			if(imageLoader!=null){
				imageLoader.pause();
			}
		}else{
			if(imageLoader!=null){
				imageLoader.resume();
			}
		}
	}
	private void initView() {
		imageLoader = ImageLoader.getInstance();
		imageLoader.clearMemoryCache();
		imageLoadOptions = ImageLoadOptions.getOptions(R.drawable.view_shap);
		imageView = (ImageView) findViewById(R.id.image);
		TextView tv_time = (TextView) findViewById(R.id.tv_time);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_currPrice = (TextView) findViewById(R.id.tv_currPrice);
		TextView tv_priceChange = (TextView) findViewById(R.id.tv_priceChange);
		TextView tv_involve = (TextView) findViewById(R.id.tv_involve);
		Button btn_forecast = (Button) findViewById(R.id.btn_forecast);
		imageLoader.displayImage(item.getImgsrc(), imageView, imageLoadOptions);
		tv_title.setText(item.getTitle());
		tv_currPrice.setText(String.format("%.1f", item.getCurrPrice()));
		if(item.getPriceChange() >= 0){
			tv_priceChange.setText("+"+String.format("%.1f", item.getPriceChange()));
			tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_red));
		}else{
			tv_priceChange.setText("-"+String.format("%.1f", Math.abs(item.getPriceChange())));
			tv_priceChange.setBackgroundColor(getResources().getColor(R.color.gain_blue));
		}
		tv_involve.setText(item.getInvolve()+"");
		tv_time.setText(item.getStatusStr());
		//倒计时
		if(item.getStatusStr()!=null && item.getStatusStr().equals("交易中")){
			 Long time = item.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();;
			tv_time.setText(LongTimeUtil.longTimeUtil(time));
			myThread = new MyThread(tv_time, time, 1,handler);
			myThread.start();
		}
		imageView.setOnClickListener(clickListener);
		btn_forecast.setOnClickListener(clickListener);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.image:
			case R.id.btn_forecast:
				//预测
				Intent intent = new Intent(getActivity(), EventDetailActivity.class);
				intent.putExtra("event", item);
				startActivity(intent);
				break;
			}
		}
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler = null;
		if(myThread !=null){
			myThread.stopThread();
			myThread = null;
		}
		if(imageLoader !=null){
			imageLoader.clearMemoryCache();
			imageLoader.cancelDisplayTask(imageView);
		}
		imageLoadOptions = null;
		imageLoader = null;
		if(imageView !=null){
			Bitmap bitmap = imageView.getDrawingCache();
			if(bitmap!=null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
			imageView = null;
		}
		handler = null;
		System.gc();
	}
}
