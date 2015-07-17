package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.DealOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.graphics.Color;
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
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_hold, null, false);
			
			DealOrderDAO item = list.get(position);
			TextView tv_status = ViewHolder.get(convertView, R.id.tv_status);
			String sttaus = "此预测将于"+TimeUtil.longToString(item.getEvent().getClearTime(), TimeUtil.FORMAT_DATE_TIME_SECOND)+"清算盈亏";
			tv_status.setText(sttaus);
			TextView tv_currentPrice = ViewHolder.get(convertView, R.id.tv_price);
			TextView tv_gain = ViewHolder.get(convertView, R.id.tv_gain);
			tv_currentPrice.setText(String.format("%.1f", item.getEvent().getCurrPrice()));
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
			tv_title.setText(item.getEvent().getTitle());
			TextView tv_buy = ViewHolder.get(convertView, R.id.tv_hold_buy);
			String buy = context.getResources().getString(R.string.unhold_1)+"\t"+item.getBuyNum()+"份      以\t"+String.format("%.1f", Math.abs(item.getBuyPrice()))+"\t";
			tv_buy.setText(buy);
			tv_buy.setVisibility(View.VISIBLE);
			TextView tv_sell = ViewHolder.get(convertView, R.id.tv_hold_sell);
//			TextView tv_think = ViewHolder.get(convertView, R.id.tv_think);
			String sell = context.getResources().getString(R.string.hold_1)+"\t"+item.getSellNum()+"份      以\t"+String.format("%.1f", Math.abs(item.getSellPrice()))+"\t";
			tv_sell.setText(sell);
			tv_sell.setVisibility(View.VISIBLE);
			if(item.getBuyNum() == 0){
				sell = context.getResources().getString(R.string.unhold_2)+"\t"+item.getSellNum()+"份      以\t"+String.format("%.1f", Math.abs(item.getSellPrice()))+"\t";
				tv_sell.setText(sell);
				tv_sell.setVisibility(View.VISIBLE);
				tv_buy.setVisibility(View.GONE);
//				tv_think.setVisibility(View.GONE);
			}else{
				if(item.getSellPrice()!=0){
					tv_sell.setVisibility(View.VISIBLE);
//					tv_think.setVisibility(View.VISIBLE);
				}else{
//					tv_think.setVisibility(View.GONE);
					tv_sell.setVisibility(View.GONE);
				}
			}
			
			if(item.getEvent().getStatusStr() !=null && item.getEvent().getStatusStr().equals("已清算")){
				tv_status.setText("已清算");
				tv_gain.setBackgroundColor(Color.WHITE);
				String gain = "";
				if(item.getGain() < 0){
					gain = " - "+String.format("%.1f", Math.abs(item.getGain()));
					tv_gain.setTextColor(context.getResources().getColor(R.color.gain_blue));
				}else{
					gain = " + "+String.format("%.1f", Math.abs(item.getGain()));
					tv_gain.setTextColor(context.getResources().getColor(R.color.gain_red));
				}
				tv_gain.setText(gain);
			}else{
				tv_gain.setText("最新成交价");
				tv_gain.setBackground(null);
				tv_gain.setTextColor(context.getResources().getColor(R.color.hold_status));
			}
		return convertView;
	}

}
