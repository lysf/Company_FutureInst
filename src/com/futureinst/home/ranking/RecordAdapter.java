package com.futureinst.home.ranking;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.record.RecordDAO;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordAdapter extends BaseAdapter {
	private Context context;
	private List<RecordDAO> list;
	private String[] types;
	private int[] colors;
	public RecordAdapter(Context context) {
		this.context = context;
		list = new ArrayList<RecordDAO>();
		types = context.getResources().getStringArray(R.array.record_tag);
		colors = new int[]{R.color.type_1,R.color.type_2,R.color.type_3,R.color.type_4,
				R.color.type_5,R.color.type_6,R.color.type_7,R.color.type_8,R.color.type_9};
	}
	public void setList(List<RecordDAO> list) {
		this.list = list;
		notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_record, null);
		RecordDAO item = list.get(position);
		TextView tv_type = ViewHolder.get(convertView, R.id.tv_type);
		TextView tv_gainTotal = ViewHolder.get(convertView, R.id.tv_gainTotal);
		TextView tv_avgGain = ViewHolder.get(convertView, R.id.tv_avgGain);
		TextView tv_gainNumber = ViewHolder.get(convertView, R.id.tv_gainNumber);
		TextView tv_odds = ViewHolder.get(convertView, R.id.tv_odds);
		
		tv_type.setText(types[item.getTag() - 1]+context.getResources().getString(R.string.space));
		tv_gainTotal.setText(String.format("%.2f", item.getAllGain()));
		tv_avgGain.setText(String.format("%.2f", item.getAvgGain()));
		tv_gainNumber.setText(item.getGainEvent()+"");
		if(item.getAllEvent() == 0){
			tv_odds.setText("0");
		}else{
			tv_odds.setText(String.format("%3d", (int)(item.getGainEvent()*1.0f/item.getAllEvent()*100))+"%");
		}
		return convertView;
	}

}
