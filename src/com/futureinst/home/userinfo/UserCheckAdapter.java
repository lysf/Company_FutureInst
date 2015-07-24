package com.futureinst.home.userinfo;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.usermodel.UserCheckDAO;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserCheckAdapter extends BaseAdapter {
	private Context context;
	private List<UserCheckDAO> list;
	public UserCheckAdapter(Context context){
		this.context = context;
		list = new ArrayList<UserCheckDAO>();
	}
	public void setList(List<UserCheckDAO> list){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_check, null, false);
		UserCheckDAO item = list.get(position);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		TextView tv_detail = ViewHolder.get(convertView, R.id.tv_detail);
		TextView tv_change = ViewHolder.get(convertView, R.id.tv_change);
		View viewDiver = ViewHolder.get(convertView, R.id.view_diver);
		TextView tv_current_balance = ViewHolder.get(convertView, R.id.tv_current_balance);
		tv_time.setText(TimeUtil.longToString(item.getCtime(), TimeUtil.FORMAT_DATE_TIME_SECOND));
		tv_detail.setText(item.getTitle());
		String change = "";
		SpannableStringBuilder stringBuilder;
		if(item.getBalanceChange() >= 0){
			change = "+"+String.format("%.1f", item.getBalanceChange());
			stringBuilder = new SpannableStringBuilder(change);
//			stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv_change.setBackgroundColor(context.getResources().getColor(R.color.gain_red));
			tv_change.setTextColor(context.getResources().getColor(R.color.gain_red));
		}else{
			change = "-"+String.format("%.1f", Math.abs(item.getBalanceChange()));
			stringBuilder = new SpannableStringBuilder(change);
//			stringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.login_title_layout_back)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv_change.setBackgroundColor(context.getResources().getColor(R.color.gain_blue));
			tv_change.setTextColor(context.getResources().getColor(R.color.gain_blue));
		}
		tv_change.setText(stringBuilder);
		tv_current_balance.setText(String.format("%.1f", item.getBalanceCurr()));
		if(position == list.size() - 1) viewDiver.setVisibility(View.GONE);
		else viewDiver.setVisibility(View.VISIBLE);
		return convertView;
	}

}
