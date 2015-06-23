package com.futureinst.home;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.EventGroupDAO;
import com.futureinst.model.homeeventmodel.EventGroupDAO;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeTitleListViewAdapter extends BaseAdapter {
	private List<EventGroupDAO> list;
	private int index = 0;
	private int primaryTitle = 0;
	private Context context;
	private int[] gravity;
	public HomeTitleListViewAdapter(Context context,int primaryTitle){
		this.context = context;
		this.primaryTitle = primaryTitle;
		list = new ArrayList<EventGroupDAO>();
		gravity = new int[]{Gravity.CENTER,Gravity.LEFT,Gravity.RIGHT};
	}
	public void setList(List<EventGroupDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list==null?null:list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_home_title, null, false);
			EventGroupDAO item = list.get(position);
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
			tv_title.setText(item.getTitle());
//			tv_title.setTextSize(16);
			tv_title.setAlpha(0.8f);
			if(primaryTitle == 4 || primaryTitle == 6 || primaryTitle == 7 || primaryTitle == 8){
				tv_title.setTextColor(context.getResources().getColor(R.color.text_color_3));
			}
//			if(index == position){
//				tv_title.setTextSize(20);
//				tv_title.setAlpha(1.0f);
//			}
			
//			tv_title.setGravity(gravity[(int)(Math.random()*3)]);
			tv_title.setGravity(Gravity.CENTER);
			
		return convertView;
	}
	public void setIndex(int index){
		this.index = index;
		notifyDataSetChanged();
	}
}
