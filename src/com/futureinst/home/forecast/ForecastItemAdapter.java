package com.futureinst.home.forecast;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.ViewHolder;
import com.futureinst.widget.WaterWaveView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForecastItemAdapter extends BaseAdapter {
	private List<QueryEventDAO> list;
	private Context context;
	public ForecastItemAdapter(Context context){
		this.context = context;
		list = new ArrayList<QueryEventDAO>();
	}
	public void setList(List<QueryEventDAO> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list!=null ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_forecast, null);
		QueryEventDAO item = list.get(position);
		ImageView iv_image = ViewHolder.get(convertView, R.id.iv_image);
		View view_line = ViewHolder.get(convertView, R.id.view_line);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
//		WaterWaveView wav = ViewHolder.get(convertView, R.id.wav);
		LinearLayout ll_circle = ViewHolder.get(convertView, R.id.ll_circle);
		ll_circle.removeAllViews();
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		View diver = ViewHolder.get(convertView, R.id.diver);
		if(position == 0){
			diver.setVisibility(View.GONE);
		}else{
			diver.setVisibility(View.VISIBLE);
		}
//		ImageUtils.getInstance(context).getImage(iv_image, item.getImgsrc(), R.drawable.image_top_default);
		if(iv_image.getTag()==null || !iv_image.getTag().equals(item.getImgsrc())){
			ImageLoader.getInstance().displayImage(item.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
			iv_image.setTag(item.getImgsrc());
		}
		tv_title.setText(item.getTitle());
		tv_time.setText(item.getStatusStr());
		//倒计时
		if(item.getStatusStr()!=null && item.getStatusStr().equals("交易中")){
			Long time = item.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
			tv_time.setText("剩余"+LongTimeUtil.longTimeUtil(time));
			view_line.setBackgroundColor(context.getResources().getColor(R.color.forecast_deal));
			tv_time.setBackgroundColor(context.getResources().getColor(R.color.forecast_deal));
		}else{
			view_line.setBackgroundColor(context.getResources().getColor(R.color.tab_text_selected));
			tv_time.setBackgroundColor(context.getResources().getColor(R.color.tab_text_selected));
		}
		WaterWaveView wav = new WaterWaveView(context);
		wav.setTextTop(String.format("%.2f", item.getCurrPrice()));
		if(item.getPriceChange() >= 0){
//			wav.setDown(false);
			wav.setColor(context.getResources().getColor(R.color.gain_red));
			wav.setTextBottom("+"+String.format("%.2f", item.getPriceChange()));
		}else{
//			wav.setDown(true);
			wav.setColor(context.getResources().getColor(R.color.gain_blue));
			wav.setTextBottom("-"+String.format("%.2f", Math.abs(item.getPriceChange())));
		}
		wav.setWaterLevel(item.getCurrPrice()/100);
		ll_circle.addView(wav);
		return convertView;
	}

}
