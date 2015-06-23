package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.DealOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HoldDealAdapter extends BaseAdapter {
	private Context context;
	private List<DealOrderDAO> list;
	public HoldDealAdapter(Context context){
		this.context = context;
		list = new ArrayList<DealOrderDAO>();
	}
	public void setList(List<DealOrderDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_holder_order, null, false);
		if(position%2==0){
			convertView.setBackgroundColor(context.getResources().getColor(R.color.text_color_white));
		}else{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.text_color_e));
		}
		DealOrderDAO item = list.get(position);
			TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
			TextView tv_buy_price = ViewHolder.get(convertView, R.id.tv_buy_price);
			TextView tv_sell_price = ViewHolder.get(convertView, R.id.tv_sell_price);
			TextView tv_buy_num = ViewHolder.get(convertView, R.id.tv_buy_num);
			TextView tv_sell_num = ViewHolder.get(convertView, R.id.tv_sell_num);
			TextView tv_gain = ViewHolder.get(convertView, R.id.tv_gain);
			TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
			tv_name.setText(item.getEvent().getTitle());
			tv_buy_price.setText(String.format("%.1f", item.getBuyPrice()));
			tv_sell_price.setText(String.format("%.1f", item.getSellPrice()));
			tv_buy_num.setText(item.getBuyNum()+"");
			tv_sell_num.setText(item.getSellNum()+"");
			if(item.getGain()<0){
				tv_gain.setText("-" + String.format("%.1f", Math.abs(item.getGain())));
				tv_gain.setBackgroundColor(context.getResources().getColor(R.color.gain_blue));
			}else{
				tv_gain.setText("+" + String.format("%.1f", Math.abs(item.getGain())));
				tv_gain.setBackgroundColor(context.getResources().getColor(R.color.gain_red));
			}
			tv_time.setText(TimeUtil.longToString(item.getEvent().getClearTime(), TimeUtil.FORMAT_DATE_TIME_SECOND_point));
		return convertView;
	}

}
