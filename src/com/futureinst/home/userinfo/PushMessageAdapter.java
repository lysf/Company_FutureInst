package com.futureinst.home.userinfo;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PushMessageAdapter extends BaseAdapter {
	private Context context;
	private List<PushMessageDAO> list;
	public PushMessageAdapter(Context context){
		this.context = context;
		list = new ArrayList<PushMessageDAO>();
	}
	public void setList(List<PushMessageDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		return list!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_push_message, null, false);
		PushMessageDAO item = list.get(position);
		ImageView iv_status = ViewHolder.get(convertView, R.id.iv_type);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_message = ViewHolder.get(convertView, R.id.tv_message);
		tv_time.setText(TimeUtil.longToString(item.getTime(), TimeUtil.FORMAT_DATE_TIME_SECOND));
		if(item.isRead()){
			iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.point_gray));
		}else{
			iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.point_red));
		}
		tv_message.setText(item.getMessage());
		return convertView;
	}

}
