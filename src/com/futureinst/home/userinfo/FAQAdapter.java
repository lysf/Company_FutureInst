package com.futureinst.home.userinfo;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.usermodel.FaqDAO;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FAQAdapter extends BaseAdapter {
	private Context context;
	private List<FaqDAO> list;
	public FAQAdapter(Context context){
		this.context = context;
		list = new ArrayList<FaqDAO>();
	}
	public void setList(List<FaqDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list!=null?list.get(position):null;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_faq, null, false);
		FaqDAO item = list.get(position);
		TextView tv_question = ViewHolder.get(convertView, R.id.tv_question);
		tv_question.setText(item.getTitle());
		return convertView;
	}

}
