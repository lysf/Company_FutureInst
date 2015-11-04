package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.LazyBagDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyBagAdapter extends BaseAdapter {
	private Context context;
	private List<LazyBagDAO> list;
	public LazyBagAdapter(Context context){
		this.context = context;
		list = new ArrayList<LazyBagDAO>();
	}
	public void setList(List<LazyBagDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		return list!=null?list.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_whole_incident, null, false);
			LazyBagDAO item = list.get(position);
			TextView tv_time = ViewHolder.get(convertView, R.id.tv_incident_time);
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_incident_title);
			TextView tv_content = ViewHolder.get(convertView, R.id.tv_incident_content);
				if(!TextUtils.isEmpty(item.getHtime())){
					tv_time.setText(item.getHtime());
				}else{
					tv_time.setText("背景信息");
				}
			tv_title.setText(item.getTitle());
			tv_content.setText(item.getContent());
		return convertView;
	}

}
