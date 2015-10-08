package com.futureinst.personalinfo.other;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.record.UserRecordDAO;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.TimeUtil;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PersonalAttentionAdapter extends BaseAdapter {
	private Context context;
	private List<UserRecordDAO> list;
	private SharePreferenceUtil preferenceUtil;
	public PersonalAttentionAdapter(Context context) {
		this.context = context;
		list = new ArrayList<UserRecordDAO>();
		preferenceUtil = SharePreferenceUtil.getInstance(context);
	}
	public void refresh(List<UserRecordDAO> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	public void setList(List<UserRecordDAO> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	public List<UserRecordDAO> getList() {
		return list;
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
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_personal_attention, parent, false);
		RoundedImageView imageView = ViewHolder.get(convertView, R.id.iv_headImg);
		TextView tv_nickName = ViewHolder.get(convertView, R.id.tv_nickName);
		TextView tv_assert = ViewHolder.get(convertView, R.id.tv_assert);
		TextView tv_ranking = ViewHolder.get(convertView, R.id.tv_ranking);
		TextView tv_gain = ViewHolder.get(convertView, R.id.tv_gain);
		TextView tv_order = ViewHolder.get(convertView, R.id.tv_order);//上次下单时间
		final UserRecordDAO item = list.get(position);
		ImageLoader.getInstance().displayImage(item.getUser().getHeadImage(), imageView, ImageLoadOptions.getOptions(R.drawable.logo));
		tv_nickName.setText(item.getUser().getName());
		tv_assert.setText(item.getAsset()+"");
		tv_ranking.setText(item.getRank()+"");
		tv_gain.setText(String.format("%3d", (int)(item.getGainEvent()*1.0f/item.getAllEvent()*100))+"%");
		
		String time = "上次下单时间: ";
		if(!TextUtils.isEmpty(item.getUser().getLastOrderTime())){
			time += TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(item.getUser().getLastOrderTime()));
		}else{
			time +="暂无数据";
		}
		tv_order.setText(time);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(item.getUser().getId() == preferenceUtil.getID()){
					return;
				}
				Intent intent = new Intent(context,PersonalShowActivity.class);
				intent.putExtra("id", item.getUser().getId()+"");
				context.startActivity(intent);
			}
		});
		return convertView;
	}

}
