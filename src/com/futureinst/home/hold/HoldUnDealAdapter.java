package com.futureinst.home.hold;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.order.UnDealOrderDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HoldUnDealAdapter extends BaseAdapter {
	private List<UnDealOrderDAO> list;
	private Context context;
	private String[] types;
	public HoldUnDealAdapter(Context context){
		this.context = context;
		list = new ArrayList<UnDealOrderDAO>();
		types = new String[]{"限价\n买进" ,"市价\n买进","限价\n卖空 ","市价\n卖空"};
	}
	public void setList(List<UnDealOrderDAO> list){
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
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_unhold_order, null, false);
		if(position%2==0){
			convertView.setBackgroundColor(context.getResources().getColor(R.color.text_color_white));
		}else{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.text_color_e));
		}
		UnDealOrderDAO item = list.get(position);
		TextView tv_event_name = ViewHolder.get(convertView, R.id.tv_event_name);
		TextView tv_event_type = ViewHolder.get(convertView, R.id.tv_event_type);
		TextView tv_limit_price = ViewHolder.get(convertView, R.id.tv_limit_price);
		TextView tv_current_price = ViewHolder.get(convertView, R.id.tv_current_price);
		TextView tv_orderNum = ViewHolder.get(convertView, R.id.tv_orderNum);
		TextView tv_residue = ViewHolder.get(convertView, R.id.tv_residue);
		TextView tv_dead_time = ViewHolder.get(convertView, R.id.tv_dead_time);
		
		tv_event_name.setText(item.getEvent().getTitle());
		tv_event_type.setText(types[item.getType()-1]);
		tv_limit_price.setText(String.format("%.1f", item.getPrice()));;
		if(item.getType() == 2 || item.getType() == 4){
			tv_limit_price.setText("——");
		}
		tv_current_price.setText(String.format("%.1f", item.getEvent().getCurrPrice()));
		tv_orderNum.setText(item.getNum()+"");
		tv_residue.setText(item.getNum()-item.getDealNum()+"");
		tv_dead_time.setText(TimeUtil.longToString(item.getDeadTime(), TimeUtil.FORMAT_DATE_TIME_SECOND_point));
		
		return convertView;
	}

}
