package com.futureinst.home.forecast;

import com.futureinst.R;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TypeAdapter extends BaseAdapter {
	private int index = 0;
	private Context context;
	private String[] list;
	public TypeAdapter(Context context) {
		this.context = context;
		list = context.getResources().getStringArray(R.array.home_title);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) 
			convertView = LayoutInflater.from(context).inflate(R.layout.item_title_type, null, false);
		TextView tv_type = ViewHolder.get(convertView, R.id.tv_type);
		View line = ViewHolder.get(convertView, R.id.view_line);
		if(index == position){
			tv_type.setSelected(true);
			line.setSelected(true);
		}else{
			tv_type.setSelected(false);
			line.setSelected(false);
		}
		tv_type.setText(list[position]);
		return convertView;
	}
	public void setIndex(int index){
		this.index = index;
		notifyDataSetChanged();
	}
}
