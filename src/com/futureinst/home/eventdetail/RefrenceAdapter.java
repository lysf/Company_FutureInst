package com.futureinst.home.eventdetail;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.ReferenceDAO;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RefrenceAdapter extends BaseAdapter {
	private List<ReferenceDAO> list;
	private Context context;
	public RefrenceAdapter(Context context) {
		this.context = context;
		list = new ArrayList<ReferenceDAO>();
	}
	public void setList(List<ReferenceDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list!=null ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_refrence, null,false);
		ReferenceDAO item = list.get(position);
		TextView tv_refrence = ViewHolder.get(convertView, R.id.tv_refrence);
		tv_refrence.setText(item.getTitle());
		return convertView;
	}

}
