package com.futureinst.comment;

import com.futureinst.R;
import com.futureinst.model.comment.CommentDAO;
import com.futureinst.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentDetailAdapter extends BaseAdapter {
	private Context context;
	private List<CommentDAO> list;
	private boolean attitude;
	public CommentDetailAdapter(Context context,boolean attitude) {
		this.context = context;
		list = new ArrayList<CommentDAO>();
		this.attitude = attitude;
	}
	public List<CommentDAO> getList() {
		return list;
	}
	public void setList(List<CommentDAO> list) {
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
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) 
			convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_detail, null);
		ImageView iv_type = ViewHolder.get(convertView, R.id.iv_type);
		TextView tv_agree = ViewHolder.get(convertView, R.id.tv_agree);
		TextView tv_comment = ViewHolder.get(convertView, R.id.tv_comment);
		CommentDAO item = list.get(position);
		String comment = "@ "+item.getUser().getName()+"：  "+item.getContent();
		tv_comment.setText(comment);
		return convertView;
	}

}
