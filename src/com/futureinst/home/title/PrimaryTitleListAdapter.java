package com.futureinst.home.title;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.utils.ViewHolder;

public class PrimaryTitleListAdapter extends BaseAdapter{
	private Activity context;
	private String titles[];
	private int primaryTitle = 0;
	private int target = 0;
	public PrimaryTitleListAdapter(Activity context,int primaryTitle,int target){
		this.context = context;
		this.primaryTitle = primaryTitle;
		this.target = target;
		titles = context.getResources().getStringArray(R.array.home_title);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_second_title, null, false);
		TextView tv_title = ViewHolder.get(convertView, R.id.tv_second_title);
		View line = ViewHolder.get(convertView, R.id.view);
		if (primaryTitle == 4 || primaryTitle == 6 || primaryTitle == 7
				|| primaryTitle == 8) {
			tv_title.setTextColor(context.getResources().getColor(
					R.color.text_color_3));
			line.setBackgroundColor(context.getResources().getColor(
					R.color.text_color_3));
		}
		tv_title.setText(titles[position]);
		if(primaryTitle == position){
			line.setVisibility(View.VISIBLE);
		}else{
			line.setVisibility(View.INVISIBLE);
		}
		tv_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				primaryTitle = position;
				notifyDataSetChanged();
				Intent intent = new Intent("titleType");
				intent.putExtra("primaryTitle", primaryTitle);
				intent.putExtra("secondTitle", 0);
				context.sendBroadcast(intent);
				context.finish();
			}
		});
		return convertView;
	}
}
