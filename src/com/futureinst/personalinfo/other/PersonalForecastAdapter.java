package com.futureinst.personalinfo.other;


import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.model.other.OtherForecastDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalForecastAdapter extends BaseAdapter {
	private Context context;
	private List<OtherForecastDAO> list;
	private boolean haveGain ;
	private boolean isTagRecord = false;
	public PersonalForecastAdapter(Context context) {
		this.context = context;
		list = new ArrayList<OtherForecastDAO>();
	}
	public void setGain(boolean haveGain){
		this.haveGain =haveGain;
	}
	public void refresh(List<OtherForecastDAO> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	public void setList(List<OtherForecastDAO> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	public List<OtherForecastDAO> getList() {
		return list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_personal_forecast, parent, false);
		LinearLayout ll_top = ViewHolder.get(convertView, R.id.ll_top);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		TextView tv_attitude = ViewHolder.get(convertView, R.id.tv_attitude);
		LinearLayout ll_gain = ViewHolder.get(convertView, R.id.ll_gain);
		TextView tv_gain = ViewHolder.get(convertView, R.id.tv_gain);
		LinearLayout ll_forecast = ViewHolder.get(convertView, R.id.ll_forecast);
		
		ll_top.setVisibility(View.VISIBLE);
		if(haveGain){
			ll_gain.setVisibility(View.VISIBLE);
		}else{
			ll_gain.setVisibility(View.GONE);
		}
		
		
		final OtherForecastDAO item = list.get(position);
		
		String time = TimeUtil.longToString(item.getMtime(), TimeUtil.FORMAT_DATE_YEAR_MONTH_DAY_1);
		if(isTagRecord){
			time = TimeUtil.longToString(item.getEvent().getClearTime(), TimeUtil.FORMAT_DATE_YEAR_MONTH_DAY_1);
		}
		tv_time.setText(time);
		tv_title.setText(item.getEvent().getTitle());
		if(item.getBuyNum() > item.getSellNum()){//看好
		tv_attitude.setText("看好");
		tv_attitude.setTextColor(context.getResources().getColor(R.color.gain_red));
		} else{
			tv_attitude.setText("不看好");
			tv_attitude.setTextColor(context.getResources().getColor(R.color.gain_blue));
		}
		
		tv_gain.setText(String.format("%.2f", item.getGain()));
		if(item.getGain() > 0){
			tv_gain.setText("+"+String.format("%.2f", item.getGain()));
			tv_gain.setTextColor(context.getResources().getColor(R.color.gain_red));
		}else{
			tv_gain.setTextColor(context.getResources().getColor(R.color.gain_blue));
		}
		if(haveGain){
			tv_attitude.setTextColor(context.getResources().getColor(R.color.text_color_6));
		}
		if(position > 0){
			String foreTime = TimeUtil.longToString(list.get(position-1).getMtime(), TimeUtil.FORMAT_DATE_YEAR_MONTH_DAY_1);
			if(isTagRecord){
				foreTime = TimeUtil.longToString(list.get(position-1).getMtime(), TimeUtil.FORMAT_DATE_YEAR_MONTH_DAY_1);
			}
			if(foreTime.equals(time))
				ll_top.setVisibility(View.GONE);
		}
		ll_forecast.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EventDetailActivity.class);
				intent.putExtra("eventId", item.getEvent().getId()+"");
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	public void setTagRecord(){//判断是否是战绩分类中的事件
		this.isTagRecord = true;
	}
}
