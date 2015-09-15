package com.futureinst.home.forecast;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.home.SystemTimeUtile;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.LongTimeUtil;
import com.futureinst.utils.Utils;
import com.futureinst.utils.ViewHolder;
import com.futureinst.utils.xutils.XUtilsImageLoader;
import com.futureinst.widget.MyTextView;
import com.futureinst.widget.waterwave.WaterWaveView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ForecastItemAdapter extends BaseAdapter {
//	private XUtilsImageLoader imageLoader;
	private List<QueryEventDAO> list;
	private Context context;
	public ForecastItemAdapter(Context context){
		this.context = context;
//		imageLoader = new XUtilsImageLoader(context);
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
		MyTextView tv_special = ViewHolder.get(convertView, R.id.tv_special);
		View view_line = ViewHolder.get(convertView, R.id.view_line);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		LinearLayout ll_circle = ViewHolder.get(convertView, R.id.ll_circle);
		ll_circle.removeAllViews();
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		View diver = ViewHolder.get(convertView, R.id.diver);
		int iv_h = Utils.dip2px(context, 180);
		if(position == 0){
			diver.setVisibility(View.GONE);
		}else{
			diver.setVisibility(View.VISIBLE);
		}
		if(iv_image.getTag()==null || !iv_image.getTag().equals(item.getImgsrc())){
			ImageLoader.getInstance().displayImage(item.getImgsrc(), iv_image, ImageLoadOptions.getOptions(R.drawable.image_top_default));
//			imageLoader.display(iv_image, item.getImgsrc());
			iv_image.setTag(item.getImgsrc());
		}
		if(item.getType() == 1 || item.getType() == 2){//专题或广告
			if(item.getType() == 1){
				tv_special.setVisibility(View.VISIBLE);
				tv_special.setText("专题");
			}else{
				tv_special.setVisibility(View.GONE);
				tv_special.setText("广告");
				iv_h = Utils.getScreenWidth(context)*210/640;
			}
			view_line.setVisibility(View.GONE);
			tv_title.setVisibility(View.GONE);
			tv_time.setVisibility(View.GONE);
			ll_circle.setVisibility(View.GONE);
			
		}else{
			tv_special.setVisibility(View.INVISIBLE);
			view_line.setVisibility(View.VISIBLE);
			tv_title.setVisibility(View.VISIBLE);
			tv_time.setVisibility(View.VISIBLE);
			ll_circle.setVisibility(View.VISIBLE);
			tv_title.setText(item.getLead());
			tv_time.setText(item.getStatusStr());
			//倒计时
			if(item.getStatusStr()!=null){
				if(item.getStatusStr().equals("交易中")){
					Long time = item.getTradeTime() - SystemTimeUtile.getInstance(0L).getSystemTime();
					tv_time.setText("剩余"+LongTimeUtil.longTimeUtil(time));
					view_line.setBackgroundColor(context.getResources().getColor(R.color.forecast_deal));
					tv_time.setBackgroundColor(context.getResources().getColor(R.color.forecast_deal));
				}else if(item.getStatusStr().equals("已清算")){
					view_line.setBackgroundColor(context.getResources().getColor(R.color.text_color_bf));
					tv_time.setBackgroundColor(context.getResources().getColor(R.color.text_color_bf));
				}else{
					view_line.setBackgroundColor(context.getResources().getColor(R.color.tab_text_selected));
					tv_time.setBackgroundColor(context.getResources().getColor(R.color.tab_text_selected));
				}
			}
			WaterWaveView wav = new WaterWaveView(context);
			wav.setTextTop(String.format("%.2f", item.getCurrPrice()));
			
			if(item.getPriceChange() >= 0){
				wav.setColor(context.getResources().getColor(R.color.gain_red));
				wav.setTextBottom("+"+String.format("%.2f", item.getPriceChange()));
			}else{
				wav.setColor(context.getResources().getColor(R.color.gain_blue));
				wav.setTextBottom("-"+String.format("%.2f", Math.abs(item.getPriceChange())));
			}
			if(item.getStatusStr().equals("已清算")){
				wav.setColor(Color.GRAY);
			}
			wav.setWaterLevel(item.getCurrPrice()/100);
			ll_circle.addView(wav);
		}
		android.widget.RelativeLayout.LayoutParams iv_layoutParams = new android.widget.RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, iv_h);
		iv_image.setLayoutParams(iv_layoutParams);
		return convertView;
	}

}
