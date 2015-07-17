package com.futureinst.home.attention;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.model.attention.AttentionDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.ImageUtils;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionAdapter extends BaseAdapter {
//	private Map<String, CountDownTimer> mCountDownTimerMap;
	private Context context;
	private List<AttentionDAO> list;
	public MyAttentionAdapter(Context context) {
		this.context = context;
		list = new ArrayList<AttentionDAO>();
//		mCountDownTimerMap = new HashMap<String, CountDownTimer>();
	}
	public void setList(List<AttentionDAO> list) {
		this.list = list;
//		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list==null?null:list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) 
		  convertView = LayoutInflater.from(context).inflate(R.layout.item_attention,null);
		final AttentionDAO item = list.get(position); 
		ImageView imageView = ViewHolder.get(convertView, R.id.image);
		final  TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		TextView tv_currPrice =ViewHolder.get(convertView, R.id.tv_currPrice); 
		TextView tv_priceChange = ViewHolder.get(convertView, R.id.tv_priceChange);
		TextView tv_involve = ViewHolder.get(convertView, R.id.tv_involve);
		Button btn_forecast = ViewHolder.get(convertView, R.id.btn_forecast);
		btn_forecast.setVisibility(View.GONE);
		ImageUtils.getInstance(context).getImage(imageView, item.getEvent().getImgsrc(), R.drawable.image_top_default);
//		ImageLoader.getInstance().displayImage(item.getEvent().getImgsrc(), imageView, ImageLoadOptions.getOptions(R.drawable.view_shap));
		tv_title.setText(item.getEvent().getTitle());
		tv_currPrice.setText(String.format("%.1f", item.getEvent().getCurrPrice()));
		if(item.getEvent().getPriceChange() >= 0){
			tv_priceChange.setText("+"+String.format("%.1f", item.getEvent().getPriceChange()));
			tv_priceChange.setBackgroundColor(context.getResources().getColor(R.color.gain_red));
		}else{
			tv_priceChange.setText("-"+String.format("%.1f", Math.abs(item.getEvent().getPriceChange())));
			tv_priceChange.setBackgroundColor(context.getResources().getColor(R.color.gain_blue));
		}
		tv_involve.setText(item.getEvent().getInvolve()+"");
		tv_time.setText(item.getEvent().getStatusStr());
		long remainTime = item.getEvent().getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
		if(item.getEvent().getStatusStr()!=null && item.getEvent().getStatusStr().equals("交易中")){
			 tv_time.setText(LongTimeUtil.longTimeUtil(remainTime));
		}
		
//		if (mCountDownTimerMap.get(item.getEvent().getId()+"") != null) 
//			mCountDownTimerMap.get(item.getEvent().getId()+"").cancel();
//			
//		long remainTime = item.getEvent().getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
//		if(item.getEvent().getStatusStr()!=null && !item.getEvent().getStatusStr().equals("交易中"))
//			remainTime = 0L;
//		CountDownTimer countDownTimer = new CountDownTimer(remainTime, 1000L) {
//			public void onTick(long millisUntilFinished) {
//				 tv_time.setText(LongTimeUtil.longTimeUtil(millisUntilFinished));
//			}
//			public void onFinish() {
//					tv_time.setText(item.getEvent().getStatusStr());
//			}
//		}.start();
//		mCountDownTimerMap.put(item.getEvent().getId()+"", countDownTimer);
		return convertView;
	}
}
