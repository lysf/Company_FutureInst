package com.futureinst.home;


import java.util.List;

import kankan.wheel.widget.adapters.WheelViewAdapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.EventGroupDAO;
import com.futureinst.utils.ViewHolder;

public class WheelAdapter implements WheelViewAdapter{
	private Context context;
	private List<EventGroupDAO> list;
	public WheelAdapter(Context context,List<EventGroupDAO> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public View getEmptyItem(View arg0, ViewGroup arg1) {
		return null;
	}

	@Override
	public View getItem(int position, View convertView, ViewGroup arg2) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_home_title, null, false);
			
		EventGroupDAO item = list.get(position);
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
		tv_title.setText(item.getTitle());
		return convertView;
	}

	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
