package com.futureinst.home.search;

import java.util.ArrayList;
import java.util.List;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private Context context;
	private List<QueryEventDAO> list;
	public SearchAdapter(Context context){
		this.context = context;
		list = new ArrayList<QueryEventDAO>();
	}
	public void setList(List<QueryEventDAO> list){
		this.list = list;
		notifyDataSetChanged();
	}
	//清空数据
	public void clearList(){
		this.list.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		
		return list !=null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list != null ? list.get(arg0) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.item_search_gridview, null, false);
			QueryEventDAO item = list.get(position);
			ImageView imageView = ViewHolder.get(convertView, R.id.iv_pic);
			TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
			TextView tv_price = ViewHolder.get(convertView, R.id.tv_price);
			TextView tv_personNum = ViewHolder.get(convertView, R.id.tv_personNum);
			ImageLoader.getInstance().displayImage(item.getImgsrc(), imageView, ImageLoadOptions.getOptions(R.drawable.edit_back));
			tv_title.setText(item.getTitle());
			tv_price.setText(String.format("%.1f", item.getCurrPrice()));
			tv_personNum.setText(String.format("%05d", item.getInvolve()));
			if(item.getInvolve()>99999){
				tv_personNum.setText("99999+");
			}
		return convertView;
	}

}
